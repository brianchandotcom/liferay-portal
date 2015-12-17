/**
 * Copyright 2012 Liferay Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.portal.servlet.jsp.compiler.internal;

import java.io.File;
import java.io.IOException;

import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

import org.apache.felix.utils.log.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

import org.phidias.compile.BundleJavaFileObject;
import org.phidias.compile.Constants;
import org.phidias.compile.JarJavaFileObject;
import org.phidias.compile.ResourceResolver;
import static org.phidias.compile.Constants.JAVA_PACKAGE;
import static org.phidias.compile.Constants.STAR;

public class BundleJavaFileManager
	extends ForwardingJavaFileManager<JavaFileManager>
	implements Constants {

	public BundleJavaFileManager(
		Bundle bundle, JavaFileManager javaFileManager,
		Logger logger, boolean verbose, ResourceResolver resourceResolver) {

		super(javaFileManager);

		_resourceResolver = resourceResolver;

		_logger = logger;
		_verbose = verbose;

		if (_verbose) {
			_logger.log(
				Logger.LOG_INFO,
				"Initializing compilation in OSGi for bundle " +
					bundle.getSymbolicName() + "-" + bundle.getVersion());
		}

		_bundleWiring = bundle.adapt(BundleWiring.class);

		List<BundleWire> providedWires = _bundleWiring.getRequiredWires(null);

		if (_verbose) {
			_logger.log(
				Logger.LOG_INFO,
				"Dependent BundleWirings included in this BundleJavaManager " +
					"context: ");
		}

		_bundleWirings = new LinkedHashSet<>();

		for (BundleWire bundleWire : providedWires) {
			BundleWiring providerWiring = bundleWire.getProviderWiring();

			if (!_bundleWirings.add(providerWiring)) {
				continue;
			}

			Bundle curBundle = providerWiring.getBundle();

			if (curBundle.getBundleId() == 0) {
				List<BundleCapability> bundleCapabilities =
					providerWiring.getCapabilities(
						BundleRevision.PACKAGE_NAMESPACE);

				for (BundleCapability bundleCapability : bundleCapabilities) {
					Map<String, Object> attributes =
						bundleCapability.getAttributes();

					Object packageNamespace = attributes.get(
						BundleRevision.PACKAGE_NAMESPACE);

					if (packageNamespace != null) {
						_systemCapabilities.add(packageNamespace);
					}
				}
			}

			if (_verbose) {
				_logger.log(
					Logger.LOG_INFO,
					"\t" + curBundle.getSymbolicName() + "-" +
						curBundle.getVersion());
			}
		}
	}

	public void addBundleWiring(BundleWiring bundleWiring) {
		_bundleWirings.add(bundleWiring);
	}

	@Override
	public ClassLoader getClassLoader(JavaFileManager.Location location) {
		if (location != StandardLocation.CLASS_PATH) {
			return fileManager.getClassLoader(location);
		}

		return _bundleWiring.getClassLoader();
	}

	@Override
	public String inferBinaryName(JavaFileManager.Location location, JavaFileObject file) {
		if ((location == StandardLocation.CLASS_PATH) &&
			(file instanceof BundleJavaFileObject)) {

			BundleJavaFileObject bundleJavaFileObject =
				(BundleJavaFileObject)file;

			if (_verbose) {
				_logger.log(
					Logger.LOG_INFO, "Infering binary name from " + bundleJavaFileObject);
			}

			return bundleJavaFileObject.inferBinaryName();
		}

		return fileManager.inferBinaryName(location, file);
	}

	@Override
	public Iterable<JavaFileObject> list(
			JavaFileManager.Location location, String packageName, Set<Kind> kinds,
			boolean recurse)
		throws IOException {

		List<JavaFileObject> javaFileObjects = new ArrayList<>();

		if ((location == StandardLocation.CLASS_PATH) && _verbose) {
			_logger.log(
				Logger.LOG_INFO,
				"List available sources for {location=" + location +
					", packageName=" + packageName + ", kinds=" + kinds +
						", recurse=" + recurse + "}");
		}

		String packagePath = packageName.replace('.', '/');

		if (!packageName.startsWith(JAVA_PACKAGE) &&
			(location == StandardLocation.CLASS_PATH)) {

			listFromDependencies(kinds, recurse, packagePath, javaFileObjects);
		}

		// When not in strict mode, the following ensures that if a standard
		// classpath location has been provided we include it. It allows the
		// framework to compile against libraries not deployed as OSGi bundles.
		// This is also needed in cases where the system.bundle exports extra
		// packages via the property 'org.osgi.framework.system.packages.extra'
		// or via extension bundles (fragments) which only supplement its
		// 'Export-Package' directive.

		if (packageName.startsWith(JAVA_PACKAGE) ||
			(location != StandardLocation.CLASS_PATH) ||
			(javaFileObjects.isEmpty() &&
				_systemCapabilities.contains(packageName))) {

			Iterable<JavaFileObject> localJavaFileObjects =
				fileManager.list(location, packagePath, kinds, recurse);

			for (JavaFileObject javaFileObject : localJavaFileObjects) {
				if ((location == StandardLocation.CLASS_PATH) && _verbose) {
					_logger.log(Logger.LOG_INFO, "\t" + javaFileObject);
				}

				javaFileObjects.add(javaFileObject);
			}
		}

		return javaFileObjects;
	}

	private String getClassNameFromPath(String resourceName) {
		if (resourceName.endsWith(".class")) {
			resourceName = resourceName.substring(0, resourceName.length() - 6);
		}

		return resourceName.replace('/', '.');
	}

	private JavaFileObject getJavaFileObject(
		URL resourceURL, String resourceName) {

		String protocol = resourceURL.getProtocol();

		String className = getClassNameFromPath(resourceName);

		if (protocol.equals("bundle") || protocol.equals("bundleresource")) {
			try {
				return new BundleJavaFileObject(
					resourceURL.toURI(), className, resourceURL);
			}
			catch (URISyntaxException urise) {
				if (_verbose) {
					_logger.log(Logger.LOG_ERROR, urise.getMessage(), urise);
				}
			}
		}
		else if (protocol.equals("jar")) {
			try {
				JarURLConnection jarUrlConnection =
					(JarURLConnection)resourceURL.openConnection();

				URL url = jarUrlConnection.getJarFileURL();

				return new JarJavaFileObject(
					url.toURI(), className, resourceURL, resourceName);
			}
			catch (Exception e) {
				if (_verbose) {
					_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
				}
			}
		}
		else if (protocol.equals("vfs")) {
			try {
				String filePath = resourceURL.getFile();

				int indexOf = filePath.indexOf(".jar") + 4;

				filePath =
					filePath.substring(0, indexOf) + "!" +
						filePath.substring(indexOf, filePath.length());

				File file = new File(filePath);

				URI uri = file.toURI();

				return new JarJavaFileObject(
					uri, className,
					new URL("jar:" + uri.toString()),
					resourceName);
			}
			catch (MalformedURLException murie) {
				if (_verbose) {
					_logger.log(Logger.LOG_ERROR, murie.getMessage(), murie);
				}
			}
		}

		return null;
	}

	private void list(
		String packagePath, Kind kind, int options, BundleWiring bundleWiring,
		List<JavaFileObject> javaFileObjects) {

		Collection<String> resources = _resourceResolver.resolveResources(
			bundleWiring, packagePath, STAR.concat(kind.extension), options);

		if ((resources == null) || resources.isEmpty()) {
			return;
		}

		for (String resourceName : resources) {
			URL resourceURL = _resourceResolver.getResource(
				bundleWiring, resourceName);

			JavaFileObject javaFileObject = getJavaFileObject(
				resourceURL, resourceName);

			if (javaFileObject == null) {
				if (_verbose) {
					_logger.log(
						Logger.LOG_INFO,
						"\tCould not create JavaFileObject for {" + resourceURL +
							"}");
				}

				continue;
			}

			if (_verbose) {
				_logger.log(Logger.LOG_INFO, "\t" + javaFileObject);
			}

			javaFileObjects.add(javaFileObject);
		}
	}

	private void listFromDependencies(
		Set<Kind> kinds, boolean recurse,
		String packagePath, List<JavaFileObject> javaFileObjects) {

		int options = recurse ? BundleWiring.LISTRESOURCES_RECURSE : 0;

		for (Kind kind : kinds) {
			if (kind.equals(Kind.CLASS)) {
				for (BundleWiring bundleWiring : _bundleWirings) {
					list(
						packagePath, kind, options, bundleWiring,
						javaFileObjects);
				}
			}

			if (javaFileObjects.isEmpty()) {
				list(
					packagePath, kind, options, _bundleWiring, javaFileObjects);
			}
		}
	}

	private final BundleWiring _bundleWiring;
	private final Set<BundleWiring> _bundleWirings;
	private final Logger _logger;
	private final ResourceResolver _resourceResolver;
	private final Set<Object> _systemCapabilities = new HashSet<>();
	private final boolean _verbose;

}
/* @generated */
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

package org.phidias.compile;

import java.io.File;
import java.io.IOException;

import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRequirement;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

import org.phidias.compile.internal.BasicResourceResolver;

public class BundleJavaManager
	extends ForwardingJavaFileManager<JavaFileManager>
	implements Constants {

	public BundleJavaManager(Bundle bundle, JavaFileManager javaFileManager)
		throws IOException {

		this(bundle, javaFileManager, null, false);
	}

	public BundleJavaManager(
			Bundle bundle, JavaFileManager javaFileManager,
			List<String> options)
		throws IOException {

		this(bundle, javaFileManager, options, false);
	}

	public BundleJavaManager(
			Bundle bundle, JavaFileManager javaFileManager,
			List<String> options, boolean strict)
		throws IOException {

		super(javaFileManager);

		_bundle = bundle;
		_javaFileManager = javaFileManager;
		_log = new TPhLog();

		setOptions(options);

		_strict = strict;

		_log.log(
			"Initializing compilation in OSGi for bundle " +
				_bundle.getSymbolicName() + "-" + _bundle.getVersion());

		_bundleWiring = _bundle.adapt(BundleWiring.class);

		_classLoader = _bundleWiring.getClassLoader();

		_packageRequirements = _bundleWiring.getRequirements(
			BundleRevision.PACKAGE_NAMESPACE);

		List<BundleWire> providedWires = _bundleWiring.getRequiredWires(null);

		_log.log(
			"Dependent BundleWirings included in this BundleJavaManager " +
				"context: ");

		_bundleWirings = new ArrayList<BundleWiring>();

		for (BundleWire bundleWire : providedWires) {
			BundleWiring providerWiring = bundleWire.getProviderWiring();

			if (_bundleWirings.contains(providerWiring)) {
				continue;
			}

			Bundle curBundle = providerWiring.getBundle();

			if (_strict && (curBundle.getBundleId() == 0)) {
				_systemBundleWiring = providerWiring;
			}

			_log.log(
				"\t" + curBundle.getSymbolicName() + "-" +
					curBundle.getVersion());

			_bundleWirings.add(providerWiring);
		}

		if (_strict && (_systemBundleWiring != null)) {
			_systemCapabilities = _systemBundleWiring.getCapabilities(
				BundleRevision.PACKAGE_NAMESPACE);
		}
	}

	public void addBundleRequirement(BundleRequirement bundleRequirement) {
		if (_packageRequirements.contains(bundleRequirement)) {
			return;
		}

		_packageRequirements.add(bundleRequirement);
	}

	public void addBundleWiring(BundleWiring bundleWiring) {
		if (_bundleWirings.contains(bundleWiring)) {
			return;
		}

		_bundleWirings.add(bundleWiring);
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	@Override
	public ClassLoader getClassLoader(Location location) {
		if (location != StandardLocation.CLASS_PATH) {
			return _javaFileManager.getClassLoader(location);
		}

		return getClassLoader();
	}

	@Override
	public String inferBinaryName(Location location, JavaFileObject file) {
		if ((location == StandardLocation.CLASS_PATH) &&
			(file instanceof BundleJavaFileObject)) {

			BundleJavaFileObject bundleJavaFileObject =
				(BundleJavaFileObject)file;

			_log.log("Infering binary name from " + bundleJavaFileObject);

			return bundleJavaFileObject.inferBinaryName();
		}

		return _javaFileManager.inferBinaryName(location, file);
	}

	@Override
	public Iterable<JavaFileObject> list(
			Location location, String packageName, Set<Kind> kinds,
			boolean recurse)
		throws IOException {

		List<JavaFileObject> javaFileObjects = new ArrayList<JavaFileObject>();

		if (location == StandardLocation.CLASS_PATH) {
			_log.log(
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
			(javaFileObjects.isEmpty() && hasPackageCapability(packageName))) {

			Iterable<JavaFileObject> localJavaFileObjects =
				_javaFileManager.list(location, packagePath, kinds, recurse);

			for (JavaFileObject javaFileObject : localJavaFileObjects) {
				if (location == StandardLocation.CLASS_PATH) {
					_log.log("\t" + javaFileObject);
				}

				javaFileObjects.add(javaFileObject);
			}
		}

		return javaFileObjects;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		_resourceResolver = resourceResolver;
	}

	private String getClassNameFromPath(String resourceName) {
		if (resourceName.endsWith(".class")) {
			resourceName = resourceName.substring(
				0, resourceName.length() - ".class".length());
		}

		return resourceName.replace('/', '.');
	}

	private URI getURI(URL url) {
		String protocol = url.getProtocol();

		if (protocol.equals("bundle") || protocol.equals("bundleresource")) {
			try {
				return url.toURI();
			}
			catch (Exception e) {
				_log.log(e);
			}
		}
		else if (protocol.equals("jar")) {
			try {
				JarURLConnection jarUrlConnection =
					(JarURLConnection)url.openConnection();

				return jarUrlConnection.getJarFileURL().toURI();
			}
			catch (Exception e) {
				_log.log(e);
			}
		}
		else if (protocol.equals("vfs")) {
			String file = url.getFile();

			int indexOf = file.indexOf(".jar") + 4;

			file =
				file.substring(0, indexOf) + "!" +
					url.getFile().substring(indexOf, url.getFile().length());

			return new File(file).toURI();
		}

		return null;
	}

	private JavaFileObject getJavaFileObject(
		URL resourceURL, String resourceName) {

		String protocol = resourceURL.getProtocol();

		String className = getClassNameFromPath(resourceName);

		URI uri = getURI(resourceURL);

		if (protocol.equals("bundle") || protocol.equals("bundleresource")) {
			try {
				return new BundleJavaFileObject(uri, className);
			}
			catch (Exception e) {
				_log.log(e);
			}
		}
		else if (protocol.equals("jar")) {
			return new JarJavaFileObject(
				uri, className, resourceURL, resourceName);
		}
		else if (protocol.equals("vfs")) {
			try {
				return new JarJavaFileObject(
					uri, className,
					new URL("jar:" + uri.toString()),
					resourceName);
			}
			catch (IOException e) {
				_log.log(e);
			}
		}

		return null;
	}

	private ResourceResolver getResourceResolver() {
		if (_resourceResolver != null) {
			return _resourceResolver;
		}

		_resourceResolver = new BasicResourceResolver();

		return _resourceResolver;
	}

	private boolean hasPackageCapability(String packageName) {
		if (!_strict) {
			return true;
		}

		// We only need to check if there is a matching system bundle capability
		// if mode is strict. Otherwise, allow loading classes from the defined
		// classpath.

		return (_systemBundleWiring != null) &&
			hasPackageCapability(_systemCapabilities, packageName);
	}

	private boolean hasPackageCapability(
		List<BundleCapability> capabilities, String packageName) {

		for (BundleCapability capability : capabilities) {
			Map<String, Object> attributes = capability.getAttributes();

			Object packageAttribute = attributes.get(
				BundleRevision.PACKAGE_NAMESPACE);

			if ((packageAttribute != null) &&
				packageAttribute.equals(packageName)) {

				return true;
			}
		}

		return false;
	}

	private void list(
		String packagePath, Kind kind, int options, BundleWiring bundleWiring,
		List<JavaFileObject> javaFileObjects) {

		ResourceResolver resourceResolver = getResourceResolver();

		Collection<String> resources = resourceResolver.resolveResources(
			bundleWiring, packagePath, STAR.concat(kind.extension), options);

		if ((resources == null) || resources.isEmpty()) {
			return;
		}

		for (String resourceName : resources) {
			URL resourceURL = resourceResolver.getResource(
				bundleWiring, resourceName);

			JavaFileObject javaFileObject = getJavaFileObject(
				resourceURL, resourceName);

			if (javaFileObject == null) {
				_log.log(
					"\tCould not create JavaFileObject for {" + resourceURL +
						"}");

				continue;
			}

			_log.log("\t" + javaFileObject);

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

	private void setOptions(List<String> options) {
		if (options == null) {
			return;
		}

		_options.addAll(options);

		if (_options.contains(OPT_VERBOSE)) {
			_log.out = System.err;
		}
	}

	private Bundle _bundle;
	private BundleWiring _bundleWiring;
	private ArrayList<BundleWiring> _bundleWirings;
	private ClassLoader _classLoader;
	private JavaFileManager _javaFileManager;
	private TPhLog _log;
	private List<String> _options = new ArrayList<String>();
	private List<BundleRequirement> _packageRequirements;
	private ResourceResolver _resourceResolver;
	private boolean _strict;
	private BundleWiring _systemBundleWiring;
	private List<BundleCapability> _systemCapabilities;

}
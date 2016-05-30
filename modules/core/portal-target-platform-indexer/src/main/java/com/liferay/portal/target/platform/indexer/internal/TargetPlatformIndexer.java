/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.target.platform.indexer.internal;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.resource.CapabilityBuilder;

import com.liferay.portal.target.platform.indexer.Indexer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.framework.namespace.BundleNamespace;
import org.osgi.framework.namespace.HostNamespace;
import org.osgi.framework.namespace.IdentityNamespace;
import org.osgi.framework.namespace.NativeNamespace;
import org.osgi.framework.namespace.PackageNamespace;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.resource.Capability;
import org.osgi.service.indexer.ResourceIndexer;
import org.osgi.service.indexer.impl.RepoIndex;
import org.osgi.service.repository.ContentNamespace;

/**
 * @author Raymond Augé
 */
public class TargetPlatformIndexer implements Indexer {

	public TargetPlatformIndexer(Bundle systemBundle, String... dirNames) {
		_systemBundle = systemBundle;
		_dirNames = dirNames;

		_config.put("compressed", "false");
		_config.put(
			"license.url", "https://www.liferay.com/downloads/ce-license");
		_config.put("pretty", "true");
		_config.put("repository.name", "Liferay Target Platform");
		_config.put("stylesheet", "http://www.osgi.org/www/obr2html.xsl");
	}

	@Override
	public void index(OutputStream outputStream) throws Exception {
		Path tempPath = Files.createTempDirectory(null);

		File tempDir = tempPath.toFile();

		_config.put("root.url", tempDir.getPath());

		Set<File> jarFiles = new LinkedHashSet<>();

		try {
			_processSystemBundle(tempDir, jarFiles);
			_processSystemPackagesExtra(tempDir, jarFiles);

			for (String dirName : _dirNames) {
				try (DirectoryStream<Path> directoryStream =
						Files.newDirectoryStream(Paths.get(dirName))) {

					Iterator<Path> iterator = directoryStream.iterator();

					while (iterator.hasNext()) {
						_addBundle(tempPath, iterator.next(), jarFiles);
					}
				}
			}

			ResourceIndexer resourceIndexer = new RepoIndex();

			resourceIndexer.index(jarFiles, outputStream, _config);
		}
		finally {
			PathUtil.deltree(tempPath);
		}
	}

	private void _addBundle(Path tempPath, Path jarPath, Set<File> jarFiles)
		throws IOException {

		Path tempJarPath = tempPath.resolve(jarPath.getFileName());

		Files.copy(
			jarPath, tempJarPath, StandardCopyOption.COPY_ATTRIBUTES,
			StandardCopyOption.REPLACE_EXISTING);

		jarFiles.add(tempJarPath.toFile());
	}

	private void _processBundle(Bundle bundle) throws Exception {
		BundleRevision bundleRevision = bundle.adapt(BundleRevision.class);

		for (Capability capability : bundleRevision.getCapabilities(null)) {
			String namespace = capability.getNamespace();

			CapabilityBuilder capabilityBuilder = new CapabilityBuilder(
				namespace);

			capabilityBuilder.addAttributes(capability.getAttributes());
			capabilityBuilder.addDirectives(capability.getDirectives());

			Attrs attrs = capabilityBuilder.toAttrs();

			if (capabilityBuilder.isPackage()) {
				attrs.remove(Constants.BUNDLE_SYMBOLIC_NAME_ATTRIBUTE);
				attrs.remove(Constants.BUNDLE_VERSION_ATTRIBUTE);

				String packageName = attrs.remove(
					PackageNamespace.PACKAGE_NAMESPACE);

				if (packageName != null) {
					_packagesParamters.put(packageName, attrs);
				}
			}
			else if (!_ignoredNamespaces.contains(namespace)) {
				Parameters parameters = new Parameters();

				if (namespace.equals(NativeNamespace.NATIVE_NAMESPACE)) {
					Set<String> keys = new LinkedHashSet<>(attrs.keySet());

					for (String key : keys) {
						if (!key.startsWith(NativeNamespace.NATIVE_NAMESPACE)) {
							attrs.remove(key);
						}
					}
				}

				parameters.put(namespace, attrs);

				_parametersList.add(parameters);
			}
		}
	}

	private void _processSystemBundle(File tempDir, Set<File> jarFiles)
		throws Exception {

		try (Jar jar = new Jar("system.bundle")) {
			_processBundle(_systemBundle);

			Manifest manifest = new Manifest();

			Attributes attributes = manifest.getMainAttributes();

			attributes.putValue(
				Constants.BUNDLE_SYMBOLICNAME, _systemBundle.getSymbolicName());
			attributes.putValue(
				Constants.BUNDLE_VERSION,
				_systemBundle.getVersion().toString());

			String exportPackage = _packagesParamters.toString();

			exportPackage = exportPackage.replace("version:Version", "version");

			attributes.putValue(Constants.EXPORT_PACKAGE, exportPackage);

			StringBuilder sb = new StringBuilder();

			for (Parameters parameter : _parametersList) {
				sb.append(parameter.toString());
				sb.append(",");
			}

			sb.setLength(sb.length() - 1);

			String capabilities = sb.toString();

			attributes.putValue(Constants.PROVIDE_CAPABILITY, capabilities);

			jar.setManifest(manifest);

			File jarFile = new File(
				tempDir,
				_systemBundle.getSymbolicName() + "-" +
					_systemBundle.getVersion() + ".jar");

			jar.write(jarFile);

			jarFiles.add(jarFile);
		}
	}

	private void _processSystemPackagesExtra(File tempDir, Set<File> jarFiles)
		throws Exception {

		URL url = _systemBundle.getResource(
			"META-INF/system.packages.extra.mf");

		try (Jar jar = new Jar("system.packages.extra");
			InputStream inputStream = url.openStream()) {

			Manifest manifest = new Manifest(inputStream);

			jar.setManifest(manifest);

			File jarFile = new File(tempDir, "system.packages.extra.jar");

			jar.write(jarFile);

			jarFiles.add(jarFile);
		}
	}

	private static final Set<String> _ignoredNamespaces = new HashSet<>();
	private static final Class<?> _targetPlatformMainClass =
		TargetPlatformIndexer.class;

	static {
		_ignoredNamespaces.add(BundleNamespace.BUNDLE_NAMESPACE);
		_ignoredNamespaces.add(ContentNamespace.CONTENT_NAMESPACE);
		_ignoredNamespaces.add(HostNamespace.HOST_NAMESPACE);
		_ignoredNamespaces.add(IdentityNamespace.IDENTITY_NAMESPACE);
		_ignoredNamespaces.add(PackageNamespace.PACKAGE_NAMESPACE);
	}

	private final Map<String, String> _config = new HashMap<>();
	private final String[] _dirNames;
	private final Parameters _packagesParamters = new Parameters();
	private final List<Parameters> _parametersList = new ArrayList<>();
	private final Bundle _systemBundle;

}
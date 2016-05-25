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

package com.liferay.portal.target.platform.indexer.main;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.resource.CapabilityBuilder;

import com.liferay.portal.target.platform.indexer.Indexer;
import com.liferay.portal.target.platform.indexer.internal.PathUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
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
public class TargetPlatformMain implements Indexer {

	public static void main(String[] args) throws Exception {
		String moduleFrameworkBaseDir = System.getProperty(
			"module.framework.base.dir");

		if (moduleFrameworkBaseDir == null) {
			System.err.println(
				"== -Dmodule.framework.base.dir must point to a valid path");

			return;
		}

		String moduleFrameworkModulesDir = System.getProperty(
			"module.framework.modules.dir");

		if (moduleFrameworkModulesDir == null) {
			moduleFrameworkModulesDir = moduleFrameworkBaseDir + "/modules/";
		}

		String moduleFrameworkPortalDir = System.getProperty(
			"module.framework.portal.dir");

		if (moduleFrameworkPortalDir == null) {
			moduleFrameworkPortalDir = moduleFrameworkBaseDir + "/portal/";
		}

		Path tempPath = Files.createTempDirectory(null);

		File targetPlatformDir = new File(
			moduleFrameworkBaseDir, DIR_NAME_TARGET_PLATFORM);

		if (!targetPlatformDir.exists() && !targetPlatformDir.mkdirs()) {
			System.err.printf(
				"== Unable to create directory %s\n", targetPlatformDir);

			return;
		}

		TargetPlatformMain targetPlatformMain = new TargetPlatformMain(
			moduleFrameworkBaseDir, moduleFrameworkModulesDir,
			moduleFrameworkPortalDir);

		try {
			File indexFile = targetPlatformMain.index(targetPlatformDir);

			System.out.println("== Wrote index file " + indexFile);
		}
		finally {
			PathUtil.deltree(tempPath);
		}
	}

	public TargetPlatformMain(
		String moduleFrameworkBaseDir, String moduleFrameworkModulesDir,
		String moduleFrameworkPortalDir) {

		_moduleFrameworkBaseDir = moduleFrameworkBaseDir;
		_moduleFrameworkModulesDir = moduleFrameworkModulesDir;
		_moduleFrameworkPortalDir = moduleFrameworkPortalDir;

		_config.put("compressed", "false");
		_config.put(
			"license.url", "https://www.liferay.com/downloads/ce-license");
		_config.put("pretty", "true");
		_config.put("repository.name", "Liferay Target Platform");
		_config.put("stylesheet", "http://www.osgi.org/www/obr2html.xsl");
	}

	@Override
	public File index(File outputFile) throws Exception {
		Path tempPath = Files.createTempDirectory(null);

		File tempDir = tempPath.toFile();

		_config.put("root.url", tempDir.getPath());

		Set<File> jarFiles = new LinkedHashSet<>();

		try {
			_processSystemBundle(tempDir, jarFiles);
			_processSystemPackagesExtra(tempDir, jarFiles);

			String[] moduleDirs = new String[] {
				_moduleFrameworkBaseDir + "/static/",
				_moduleFrameworkModulesDir, _moduleFrameworkPortalDir
			};

			for (String moduleDir : moduleDirs) {
				File dir = new File(moduleDir);

				if (!dir.isDirectory() || !dir.canRead()) {
					continue;
				}

				File[] childFiles = dir.listFiles(
					new FilenameFilter() {

						@Override
						public boolean accept(File dir, String name) {
							return name.endsWith(".jar");
						}

					});

				for (File childFile : childFiles) {
					_addBundle(jarFiles, childFile, tempDir);
				}
			}

			ResourceIndexer resourceIndexer = new RepoIndex();

			File tempIndexFile = new File(tempDir, "target.platform.index.xml");

			try (FileOutputStream fileOutputStream =
					new FileOutputStream(tempIndexFile)) {

				resourceIndexer.index(jarFiles, fileOutputStream, _config);
			}

			File indexFile = new File(outputFile, tempIndexFile.getName());

			Files.copy(
				tempIndexFile.toPath(), indexFile.toPath(),
				StandardCopyOption.COPY_ATTRIBUTES,
				StandardCopyOption.REPLACE_EXISTING);

			return indexFile;
		}
		finally {
			PathUtil.deltree(tempPath);
		}
	}

	private void _addBundle(Set<File> jarFiles, File bundleFile, File tempDir)
		throws IOException {

		File jarFile = new File(tempDir, bundleFile.getName());

		Files.copy(
			bundleFile.toPath(), jarFile.toPath(),
			StandardCopyOption.COPY_ATTRIBUTES,
			StandardCopyOption.REPLACE_EXISTING);

		jarFiles.add(jarFile);
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

		Framework framework = null;

		try (Jar jar = new Jar("system.bundle")) {
			ServiceLoader<FrameworkFactory> serviceLoader = ServiceLoader.load(
				FrameworkFactory.class);

			FrameworkFactory frameworkFactory = serviceLoader.iterator().next();

			Map<String, String> properties = new HashMap<>();

			properties.put(
				org.osgi.framework.Constants.FRAMEWORK_STORAGE,
				tempDir.getAbsolutePath());

			framework = frameworkFactory.newFramework(properties);

			framework.init();

			BundleContext bundleContext = framework.getBundleContext();

			Bundle systemBundle = bundleContext.getBundle(0);

			_processBundle(systemBundle);

			Manifest manifest = new Manifest();

			Attributes attributes = manifest.getMainAttributes();

			attributes.putValue(
				Constants.BUNDLE_SYMBOLICNAME, systemBundle.getSymbolicName());
			attributes.putValue(
				Constants.BUNDLE_VERSION, systemBundle.getVersion().toString());

			String exportPackage = _packagesParamters.toString().replace(
				"version:Version", "version");

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
				systemBundle.getSymbolicName() + "-" +
					systemBundle.getVersion() + ".jar");

			jar.write(jarFile);

			jarFiles.add(jarFile);
		}
		finally {
			framework.stop();
		}
	}

	private void _processSystemPackagesExtra(File tempDir, Set<File> jarFiles)
		throws Exception {

		try (Jar jar = new Jar("system.packages.extra")) {
			ClassLoader classLoader = _targetPlatformMainClass.getClassLoader();

			Manifest manifest = new Manifest(
				classLoader.getResourceAsStream(
					"META-INF/system.packages.extra.mf"));

			jar.setManifest(manifest);

			File jarFile = new File(tempDir, "system.packages.extra.jar");

			jar.write(jarFile);

			jarFiles.add(jarFile);
		}
	}

	private static final Set<String> _ignoredNamespaces = new HashSet<>();
	private static final Class<?> _targetPlatformMainClass =
		TargetPlatformMain.class;

	static {
		_ignoredNamespaces.add(BundleNamespace.BUNDLE_NAMESPACE);
		_ignoredNamespaces.add(ContentNamespace.CONTENT_NAMESPACE);
		_ignoredNamespaces.add(HostNamespace.HOST_NAMESPACE);
		_ignoredNamespaces.add(IdentityNamespace.IDENTITY_NAMESPACE);
		_ignoredNamespaces.add(PackageNamespace.PACKAGE_NAMESPACE);
	}

	private final Map<String, String> _config = new HashMap<>();
	private final String _moduleFrameworkBaseDir;
	private final String _moduleFrameworkModulesDir;
	private final String _moduleFrameworkPortalDir;
	private final Parameters _packagesParamters = new Parameters();
	private final List<Parameters> _parametersList = new ArrayList<>();

}
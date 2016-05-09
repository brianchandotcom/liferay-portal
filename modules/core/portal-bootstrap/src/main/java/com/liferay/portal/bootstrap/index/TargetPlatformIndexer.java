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

package com.liferay.portal.bootstrap.index;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Verifier;
import aQute.bnd.osgi.resource.CapabilityBuilder;

import com.liferay.portal.bootstrap.ModuleFrameworkImpl;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;
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

	public static void main(String[] args) throws Exception {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		File tempFolder = FileUtil.createTempFolder();

		com.liferay.portal.util.PropsUtil.set(
			PropsKeys.MODULE_FRAMEWORK_STATE_DIR,
			tempFolder.getCanonicalPath());

		PropsUtil.setProps(new PropsImpl());

		String[] moduleFrameworkAutoDeployDirs = PropsUtil.getArray(
			PropsKeys.MODULE_FRAMEWORK_INITIAL_BUNDLES);

		for (int i = 0; i < moduleFrameworkAutoDeployDirs.length; i++) {
			if (moduleFrameworkAutoDeployDirs[i].endsWith("@start")) {
				moduleFrameworkAutoDeployDirs[i] =
					moduleFrameworkAutoDeployDirs[i].substring(
						0, moduleFrameworkAutoDeployDirs[i].length() - 6);
			}
		}

		com.liferay.portal.util.PropsUtil.set(
			PropsKeys.MODULE_FRAMEWORK_INITIAL_BUNDLES,
			StringUtil.merge(moduleFrameworkAutoDeployDirs));

		String bsn = "com.liferay.target.platform";
		String version = ReleaseInfo.getVersion();

		File targetPlatformDir = new File(
			PropsValues.MODULE_FRAMEWORK_BASE_DIR, TARGET_PLATFORM);

		if (!targetPlatformDir.exists() && !targetPlatformDir.mkdirs()) {
			System.err.printf(
				"[ERROR] Cannot create directory %s because of file " +
					"permissions\n",
				targetPlatformDir);

			return;
		}

		TargetPlatformIndexer targetPlatformIndexer = new TargetPlatformIndexer(
			bsn, version);

		try {
			File indexFile = targetPlatformIndexer.index(targetPlatformDir);

			System.out.println("Wrote index file " + indexFile);
		}
		finally {
			FileUtil.deltree(tempFolder);
		}
	}

	public TargetPlatformIndexer(String bsn, String version) {
		_bsn = bsn;
		_version = version;

		_moduleFrameworkImpl = new ModuleFrameworkImpl();

		_indexerConfig = new HashMap<>();

		_indexerConfig.put("compressed", "false");
		_indexerConfig.put(
			"license.url", "https://www.liferay.com/downloads/ce-license");
		_indexerConfig.put("pretty", "true");
		_indexerConfig.put("repository.name", ReleaseInfo.getReleaseInfo());
		_indexerConfig.put(
			"stylesheet", "http://www.osgi.org/www/obr2html.xsl");
	}

	@Override
	public File index(File output) throws Exception {
		File tempFolder = FileUtil.createTempFolder();

		_indexerConfig.put("root.url", tempFolder.getCanonicalPath());

		_moduleFrameworkImpl.initFramework();
		_moduleFrameworkImpl.startFramework();

		Framework framework = _moduleFrameworkImpl.getFramework();

		BundleContext bundleContext = framework.getBundleContext();

		processBundle(bundleContext.getBundle(0));

		Manifest manifest = new Manifest();

		Attributes mainAttributes = manifest.getMainAttributes();

		mainAttributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
		mainAttributes.putValue(Constants.BUNDLE_SYMBOLICNAME, _bsn);
		mainAttributes.putValue(Constants.BUNDLE_VERSION, _version);

		String exportPackage = StringUtil.replace(
			_packages.toString(), "version:Version", "version");

		mainAttributes.putValue(Constants.EXPORT_PACKAGE, exportPackage);

		StringBundler sb = new StringBundler();

		for (Parameters parameter : _provided) {
			sb.append(parameter.toString());
			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		String capabilities = sb.toString();

		mainAttributes.putValue(Constants.PROVIDE_CAPABILITY, capabilities);
		mainAttributes.putValue(
			Constants.BUNDLE_DESCRIPTION, ReleaseInfo.getReleaseInfo());
		mainAttributes.putValue(
			Constants.BUNDLE_LICENSE,
			"http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt");
		mainAttributes.putValue(
			Constants.BUNDLE_COPYRIGHT,
			"Copyright (c) 2000-present All rights reserved.");
		mainAttributes.putValue(
			Constants.BUNDLE_VENDOR, ReleaseInfo.getVendor());

		Jar jar = new Jar("distro");

		jar.setManifest(manifest);

		try (Verifier verifier = new Verifier(jar)) {
			verifier.setProperty(
				Constants.FIXUPMESSAGES,
				"osgi.* namespace must not be specified with generic " +
					"capabilities");

			verifier.verify();
			verifier.getErrors();

			if (!verifier.isOk()) {
				List<String> errors = verifier.getErrors();

				sb = new StringBundler((errors.size() * 4) + 3);

				sb.append(_SHORT_NAME);
				sb.append(" failed with {");

				for (String error : verifier.getErrors()) {
					sb.append("[");
					sb.append(error);
					sb.append("]");
					sb.append(",");
				}

				sb.setIndex(sb.index() - 1);

				sb.append("}");

				throw new Exception(sb.toString());
			}

			File outputJar = new File(
				tempFolder, _bsn + "-" + _version + ".jar");

			jar.write(outputJar);

			Set<File> fileList = new LinkedHashSet<>();

			fileList.add(outputJar);

			for (String initialBundle :
					PropsValues.MODULE_FRAMEWORK_INITIAL_BUNDLES) {

				addInitialBundle(initialBundle, tempFolder, fileList);
			}

			File tempIndexFile = new File(
				tempFolder, _bsn + "-" + _version + "-index.xml");

			ResourceIndexer resourceIndexer = new RepoIndex();

			try (FileOutputStream fos = new FileOutputStream(tempIndexFile)) {
				resourceIndexer.index(fileList, fos, _indexerConfig);
			}

			File indexFile = new File(output, tempIndexFile.getName());

			FileUtil.copyFile(tempIndexFile, indexFile);

			return indexFile;
		}
		finally {
			FileUtil.deltree(tempFolder);

			_moduleFrameworkImpl.stopFramework(0);
		}
	}

	protected void addInitialBundle(
			String location, File tempDir, Set<File> fileList)
		throws IOException {

		int pos = location.indexOf('@');

		if (pos != -1) {
			location = location.substring(0, pos);
		}

		if (!location.startsWith("file:")) {
			location =
				"file:" + PropsValues.MODULE_FRAMEWORK_BASE_DIR + "/static/" +
					location;
		}

		URI uri = URI.create(location);

		File initialBundleFile = new File(uri);

		if (!initialBundleFile.exists() || !initialBundleFile.canRead()) {
			return;
		}

		File copy = new File(tempDir, initialBundleFile.getName());

		FileUtil.copyFile(initialBundleFile, copy);

		fileList.add(copy);
	}

	protected void processBundle(Bundle bundle) throws Exception {
		BundleRevision bundleRevision = bundle.adapt(BundleRevision.class);

		for (Capability capability : bundleRevision.getCapabilities(null)) {
			String namespace = capability.getNamespace();

			CapabilityBuilder cb = new CapabilityBuilder(namespace);

			cb.addAttributes(capability.getAttributes());
			cb.addDirectives(capability.getDirectives());

			Attrs attrs = cb.toAttrs();

			if (cb.isPackage()) {
				attrs.remove(Constants.BUNDLE_SYMBOLIC_NAME_ATTRIBUTE);
				attrs.remove(Constants.BUNDLE_VERSION_ATTRIBUTE);

				String packageName = attrs.remove(
					PackageNamespace.PACKAGE_NAMESPACE);

				if (packageName != null) {
					_packages.put(packageName, attrs);
				}
			}
			else if (!_ignoredNamespaces.contains(namespace)) {
				Parameters parameters = new Parameters();

				if (namespace.equals(NativeNamespace.NATIVE_NAMESPACE)) {
					Set<String> keySet = new LinkedHashSet<>(attrs.keySet());

					for (String key : keySet) {
						if (!key.startsWith(NativeNamespace.NATIVE_NAMESPACE)) {
							attrs.remove(key);
						}
					}
				}

				parameters.put(namespace, attrs);

				_provided.add(parameters);
			}
		}
	}

	private static final String _SHORT_NAME =
		TargetPlatformIndexer.class.getName();

	private static final Set<String> _ignoredNamespaces = new HashSet<>();

	static {
		_ignoredNamespaces.add(BundleNamespace.BUNDLE_NAMESPACE);
		_ignoredNamespaces.add(ContentNamespace.CONTENT_NAMESPACE);
		_ignoredNamespaces.add(HostNamespace.HOST_NAMESPACE);
		_ignoredNamespaces.add(IdentityNamespace.IDENTITY_NAMESPACE);
		_ignoredNamespaces.add(PackageNamespace.PACKAGE_NAMESPACE);
	}

	private final String _bsn;
	private final Map<String, String> _indexerConfig;
	private final ModuleFrameworkImpl _moduleFrameworkImpl;
	private final Parameters _packages = new Parameters();
	private final List<Parameters> _provided = new ArrayList<>();
	private final String _version;

}
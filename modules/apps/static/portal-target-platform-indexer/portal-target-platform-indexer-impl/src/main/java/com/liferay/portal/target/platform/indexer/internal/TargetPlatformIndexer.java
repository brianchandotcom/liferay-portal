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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

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
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;
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

	public TargetPlatformIndexer(
		Bundle systemBundle, List<File> additionalJarFiles,
		String... dirNames) {

		_systemBundle = systemBundle;
		_additionalJarFiles = additionalJarFiles;
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
			Object[] objects = _processSystemBundle(tempDir, jarFiles);

			String crc32 = (String)objects[0];
			int size = (int)objects[1];

			for (String dirName : _dirNames) {
				Path path = Paths.get(dirName);

				if (Files.notExists(path)) {
					continue;
				}

				try (DirectoryStream<Path> directoryStream =
						Files.newDirectoryStream(path, "*.jar")) {

					Iterator<Path> iterator = directoryStream.iterator();

					while (iterator.hasNext()) {
						_addBundle(tempPath, iterator.next(), jarFiles);
					}
				}
			}

			for (File additionalJarFile : _additionalJarFiles) {
				Path tempJarPath = tempPath.resolve(
					additionalJarFile.getName());

				Files.copy(
					additionalJarFile.toPath(), tempJarPath,
					StandardCopyOption.COPY_ATTRIBUTES,
					StandardCopyOption.REPLACE_EXISTING);

				jarFiles.add(tempJarPath.toFile());
			}

			ResourceIndexer resourceIndexer = new RepoIndex();

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			resourceIndexer.index(jarFiles, byteArrayOutputStream, _config);

			outputStream.write(
				_fixSystemBundleOSGiContent(
					byteArrayOutputStream.toString("UTF-8"), crc32, size));
		}
		finally {
			PathUtil.deltree(tempPath);
		}
	}

	private static String _filterJavaSEVesions(String versions) {
		StringBuilder sb = new StringBuilder(versions.length());

		for (String version : versions.split(",")) {
			if (version.compareTo(_MAX_SUPPORTED_JAVA_SE_VERSION) <= 0) {
				sb.append(version);
				sb.append(",");
			}
		}

		if (sb.length() != 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private void _addBundle(Path tempPath, Path jarPath, Set<File> jarFiles)
		throws IOException {

		if (!Files.exists(jarPath)) {
			return;
		}

		Path tempJarPath = tempPath.resolve(
			URLEncoder.encode(String.valueOf(jarPath.getFileName()), "UTF-8"));

		Files.copy(
			jarPath, tempJarPath, StandardCopyOption.COPY_ATTRIBUTES,
			StandardCopyOption.REPLACE_EXISTING);

		jarFiles.add(tempJarPath.toFile());
	}

	private byte[] _fixSystemBundleOSGiContent(
			String content, String crc32, long size)
		throws UnsupportedEncodingException {

		String url =
			_systemBundle.getSymbolicName() + "-" + _systemBundle.getVersion() +
				".jar";

		int index = content.indexOf(url);

		if (index == -1) {
			throw new IllegalStateException(
				"Indexed content:\n" + content +
					"\nMissing system bundle URL: " + url);
		}

		int start = content.lastIndexOf(_ATTRIBUTE_PREFIX_OSGI_CONTENT, index);

		if (start == -1) {
			throw new IllegalStateException(
				"Indexed content:\n" + content.substring(0, index) +
					"\nMissing OSGI content attribute: " +
						_ATTRIBUTE_PREFIX_OSGI_CONTENT);
		}

		start += _ATTRIBUTE_PREFIX_OSGI_CONTENT.length();

		int end = content.lastIndexOf("\"/>", index);

		String prefix = content.substring(0, start);

		String postfix = content.substring(end);

		String newContent = prefix.concat(crc32).concat(postfix);

		index = newContent.indexOf(url);

		index += url.length() + 3;

		start = newContent.indexOf(_ATTRIBUTE_PREFIX_SIZE, index);

		if (start == -1) {
			throw new IllegalStateException(
				"Indexed content:\n" + content + "\nMissing size attribute: " +
					_ATTRIBUTE_PREFIX_SIZE);
		}

		start += _ATTRIBUTE_PREFIX_SIZE.length();

		end = newContent.indexOf("\"/>", index);

		prefix = newContent.substring(0, start);
		postfix = newContent.substring(end);

		newContent = prefix.concat(String.valueOf(size)).concat(postfix);

		newContent = newContent.replace("\r\n", "\n");

		return newContent.getBytes("UTF-8");
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

	private Object[] _processSystemBundle(File tempDir, Set<File> jarFiles)
		throws Exception {

		try (Jar jar = new Jar("system.bundle")) {
			_processBundle(_systemBundle);

			Manifest manifest = new Manifest();

			Attributes attributes = manifest.getMainAttributes();

			attributes.putValue(
				Constants.BUNDLE_SYMBOLICNAME, _systemBundle.getSymbolicName());

			Version version = _systemBundle.getVersion();

			attributes.putValue(Constants.BUNDLE_VERSION, version.toString());

			String exportPackage = _packagesParamters.toString();

			exportPackage = exportPackage.replace("version:Version", "version");

			attributes.putValue(Constants.EXPORT_PACKAGE, exportPackage);

			StringBuilder sb = new StringBuilder();

			for (Parameters parameter : _parametersList) {
				String parameterString = parameter.toString();

				if (parameterString.startsWith("osgi.ee;osgi.ee=")) {
					Attrs attrs = parameter.get("osgi.ee");

					String osgiEE = attrs.get("osgi.ee");

					if (osgiEE.startsWith("JavaSE")) {
						String versions = attrs.get("version");

						attrs.put("version", _filterJavaSEVesions(versions));

						parameterString = parameter.toString();
					}
				}
				else if (parameterString.startsWith("eclipse.platform;")) {
					parameterString = _PARAMETER_STRING_OS_VERSION;
				}

				sb.append(parameterString);
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

			try (ZipFile zipFile = new ZipFile(jarFile)) {
				ZipEntry zipEntry = zipFile.getEntry("META-INF/MANIFEST.MF");

				ByteArrayOutputStream byteArrayOutputStream =
					new ByteArrayOutputStream();

				try (InputStream inputStream = zipFile.getInputStream(
						zipEntry)) {

					byte[] buffer = new byte[4096];

					int size = -1;

					while ((size = inputStream.read(buffer)) != -1) {
						byteArrayOutputStream.write(buffer, 0, size);
					}
				}

				CRC32 crc32 = new CRC32();

				crc32.update(byteArrayOutputStream.toByteArray());

				return new Object[] {
					Long.toHexString(crc32.getValue()),
					byteArrayOutputStream.size()
				};
			}
		}
	}

	private static final String _ATTRIBUTE_PREFIX_OSGI_CONTENT =
		"<attribute name=\"osgi.content\" value=\"";

	private static final String _ATTRIBUTE_PREFIX_SIZE =
		"<attribute name=\"size\" type=\"Long\" value=\"";

	private static final String _MAX_SUPPORTED_JAVA_SE_VERSION = "1.8.0";

	private static final String _PARAMETER_STRING_OS_VERSION =
		"eclipse.platform;osgi.os=linux;osgi.arch=x86_64;osgi.ws=gtk;osgi.nl=" +
			"en_US";

	private static final Set<String> _ignoredNamespaces = new HashSet<>();

	static {
		_ignoredNamespaces.add(BundleNamespace.BUNDLE_NAMESPACE);
		_ignoredNamespaces.add(ContentNamespace.CONTENT_NAMESPACE);
		_ignoredNamespaces.add(HostNamespace.HOST_NAMESPACE);
		_ignoredNamespaces.add(IdentityNamespace.IDENTITY_NAMESPACE);
		_ignoredNamespaces.add(NativeNamespace.NATIVE_NAMESPACE);
		_ignoredNamespaces.add(PackageNamespace.PACKAGE_NAMESPACE);
	}

	private final List<File> _additionalJarFiles;
	private final Map<String, String> _config = new HashMap<>();
	private final String[] _dirNames;
	private final Parameters _packagesParamters = new Parameters();
	private final List<Parameters> _parametersList = new ArrayList<>();
	private final Bundle _systemBundle;

}
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

package com.liferay.portal.tools.target.platform.indexer.client;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.liferay.portal.target.platform.indexer.Indexer;
import com.liferay.portal.target.platform.indexer.internal.DefaultIndexValidator;
import com.liferay.portal.target.platform.indexer.internal.LPKGIndexer;
import com.liferay.portal.target.platform.indexer.internal.TargetPlatformIndexerUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class TargetPlatformIndexerClient {

	public static void main(String[] args) throws Exception {
		String liferayHome = System.getProperty("liferay.home");

		if (liferayHome == null) {
			System.err.println(
				"== -Dliferay.home must point to a valid directory");

			return;
		}

		String portalLibDirName = System.getProperty("portal.lib.dir");

		if (portalLibDirName == null) {
			System.err.println(
				"== -Dportal.lib.dir must point to a valid directory");

			return;
		}

		BytesURLSupport.init();

		String indexesFileName = System.getProperty(
			"indexes.file",
			liferayHome + "/osgi/" + Indexer.DIR_NAME_TARGET_PLATFORM +
				"/target-platform-indexes-" + System.currentTimeMillis() +
					".zip");
		long stopWaitTimeout = Long.parseLong(
			System.getProperty("stop.wait.timeout", "30000"));
		String moduleFrameworkStaticDirName = System.getProperty(
			"module.framework.static.dir", liferayHome.concat("/osgi/static"));
		String moduleFrameworkModulesDirName = System.getProperty(
			"module.framework.modules.dir",
			liferayHome.concat("/osgi/modules"));
		String moduleFrameworkPortalDirName = System.getProperty(
			"module.framework.portal.dir", liferayHome.concat("/osgi/portal"));
		String moduleFrameworkMarketplaceDir = System.getProperty(
			"module.framework.marketplace.dir",
			liferayHome.concat("/osgi/marketplace"));

		List<URI> uris = _index(
			indexesFileName,
			Arrays.asList(new File(portalLibDirName, "util-taglib.jar")),
			stopWaitTimeout, moduleFrameworkStaticDirName,
			moduleFrameworkModulesDirName, moduleFrameworkPortalDirName,
			moduleFrameworkMarketplaceDir);

		if (_validate(uris)) {
			String integrityPropertiesFileName = System.getProperty(
				"integrity.properties",
				liferayHome + "/osgi/" + Indexer.DIR_NAME_TARGET_PLATFORM +
					"/integrity.properties");

			_updateIntegrityProperties(
				uris, Paths.get(integrityPropertiesFileName));
		}
	}

	private static List<URI> _index(
			String indexesFileName, List<File> additionalJarFiles,
			long stopWaitTimeout, String moduleFrameworkStaticDirName,
			String moduleFrameworkModulesDirName,
			String moduleFrameworkPortalDirName,
			String moduleFrameworkMarketplaceDirName)
		throws Exception {

		Path indexesFilePath = Paths.get(indexesFileName);

		if (Files.exists(indexesFilePath)) {
			return _loadIndexes(indexesFilePath);
		}

		List<URI> uris = new ArrayList<>();

		uris.add(
			_indexTargetPlatform(
				additionalJarFiles, stopWaitTimeout,
				moduleFrameworkStaticDirName, moduleFrameworkModulesDirName,
				moduleFrameworkPortalDirName));

		uris.addAll(
			_indexLPKGFiles(
				Utilities.listFiles(
					moduleFrameworkMarketplaceDirName, "*.lpkg")));

		_saveIndexes(indexesFileName, uris);

		return uris;
	}

	private static List<URI> _indexLPKGFiles(List<File> lpkgFiles)
		throws Exception {

		List<URI> uris = new ArrayList<>(lpkgFiles.size());

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		for (File lpkgFile : lpkgFiles) {
			LPKGIndexer lpkgIndexer = new LPKGIndexer(
				lpkgFile, Collections.<String>emptySet());

			lpkgIndexer.index(byteArrayOutputStream);

			String name = lpkgFile.getName();

			byte[] bytes = byteArrayOutputStream.toByteArray();

			URL url = BytesURLSupport.putBytes(
				name.substring(0, name.length() - 5), bytes);

			_writeIndexToLPKG(lpkgFile, bytes);

			byteArrayOutputStream.reset();

			uris.add(url.toURI());
		}

		return uris;
	}

	private static URI _indexTargetPlatform(
			List<File> additionalJarFiles, long stopWaitTimeout,
			String... dirNames)
		throws Exception {

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		TargetPlatformIndexerUtil.indexTargetPlatform(
			byteArrayOutputStream, additionalJarFiles, stopWaitTimeout,
			dirNames);

		URL url = BytesURLSupport.putBytes(
			"liferay-target-platform", byteArrayOutputStream.toByteArray());

		return url.toURI();
	}

	private static List<URI> _loadIndexes(Path indexesFilePath)
		throws IOException {

		final List<URI> uris = new ArrayList<>();

		try (FileSystem fileSystem = FileSystems.newFileSystem(
				indexesFilePath, null)) {

			for (Path rootPath : fileSystem.getRootDirectories()) {
				Files.walkFileTree(
					rootPath,
					new SimpleFileVisitor<Path>() {

						@Override
						public FileVisitResult visitFile(
								Path filePath,
								BasicFileAttributes basicFileAttributes)
							throws IOException {

							Path fileNamePath = filePath.getFileName();

							String fileName = fileNamePath.toString();

							if (!fileName.endsWith(".xml")) {
								return FileVisitResult.CONTINUE;
							}

							URL url = BytesURLSupport.putBytes(
								fileName.substring(0, fileName.length() - 4),
								Files.readAllBytes(filePath));

							try {
								uris.add(url.toURI());
							}
							catch (URISyntaxException urise) {
								throw new RuntimeException(urise);
							}

							return super.visitFile(
								filePath, basicFileAttributes);
						}

					});
			}
		}

		return uris;
	}

	private static void _saveIndexes(String indexesFileName, List<URI> uris)
		throws IOException {

		Path path = Paths.get(indexesFileName);

		Files.createDirectories(path.getParent());

		URI indexesURI = path.toUri();

		try (FileSystem fileSystem = FileSystems.newFileSystem(
				URI.create("jar:file:" + indexesURI.getPath()),
				Collections.singletonMap("create", "true"))) {

			for (URI uri : uris) {
				URL url = uri.toURL();

				String name = url.getFile();

				int index = name.lastIndexOf('/');

				if (index != -1) {
					name = name.substring(index + 1);
				}

				index = name.lastIndexOf('.');

				if (index != -1) {
					name = name.substring(0, index);
				}

				name = URLDecoder.decode(name, "UTF-8");

				try (InputStream inputStream = url.openStream()) {
					Files.copy(
						inputStream, fileSystem.getPath(name.concat(".xml")),
						StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}

		System.out.println("== Saved indexes file at " + indexesFileName);
	}

	private static void _updateIntegrityProperties(
			List<URI> uris, Path integrityPropertiesPath)
		throws Exception {

		Collections.sort(uris);

		StringBuilder sb = new StringBuilder();

		for (URI uri : uris) {
			sb.append(Utilities.toIntegrityKey(uri));
			sb.append('=');
			sb.append(Utilities.toChecksum(uri));
			sb.append('\n');
		}

		sb.setLength(sb.length() - 1);

		Files.createDirectories(integrityPropertiesPath.getParent());

		Files.write(
			integrityPropertiesPath, Collections.singleton(sb.toString()),
			StandardCharsets.UTF_8);

		System.out.println(
			"== Saved integrity.properties at " + integrityPropertiesPath);
	}

	private static boolean _validate(List<URI> uris) throws Exception {
		DefaultIndexValidator defaultIndexValidator = new DefaultIndexValidator(
			Collections.<URI>emptyList());

		long start = System.currentTimeMillis();

		try {
			List<String> messages = defaultIndexValidator.validate(uris);

			if (!messages.isEmpty()) {
				System.out.println("== Validation errors");

				for (String message : messages) {
					System.out.println("== " + message);
				}

				return false;
			}

			System.out.println("== Successfully validated");

			return true;
		}
		finally {
			long duration = System.currentTimeMillis() - start;

			System.out.printf(
				"== Time %02d:%02ds\n", MILLISECONDS.toMinutes(duration),
				MILLISECONDS.toSeconds(duration % 60000));
		}
	}

	private static void _writeIndexToLPKG(File lpkg, byte[] bytes)
		throws IOException {

		try (FileSystem fileSystem = FileSystems.newFileSystem(
				lpkg.toPath(), null)) {

			// Specifying StandardOpenOption list is a workaround for a bug in
			// com.sun.nio.zipfs.ZipFileSystemProvider#newOutputStream(
			// Path, OpenOption...) that does not follow the API contract
			// specified in java.nio.file.Files#write(Path, byte[],
			// OpenOption...) to automatically add the StandardOpenOption list

			Files.write(
				fileSystem.getPath("index.xml"), bytes,
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.WRITE);
		}
	}

}
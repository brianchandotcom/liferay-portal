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

import com.liferay.portal.target.platform.indexer.Indexer;
import com.liferay.portal.target.platform.indexer.internal.LPKGIndexer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Version;

/**
 * @author Raymond Augé
 */
public class LPKGIndexerMain {

	public static void main(String[] args) throws Exception {
		if ((args == null) || (args.length == 0)) {
			System.err.println(
				"== Usage: <cmd> <list of lpkg files or directories " +
					"containing lpkg files>");

			return;
		}

		String moduleFrameworkBaseDirName = System.getProperty(
			"module.framework.base.dir");

		if (moduleFrameworkBaseDirName == null) {
			System.err.println(
				"== -Dmodule.framework.base.dir= must point to a valid " +
					"directory");

			return;
		}

		Path outputDirPath = Paths.get(
			moduleFrameworkBaseDirName, Indexer.DIR_NAME_TARGET_PLATFORM);

		String outputDirName = System.getProperty("output.dir");

		if (outputDirName != null) {
			outputDirPath = Paths.get(outputDirName);
		}

		Files.createDirectories(outputDirPath);

		List<File> lpkgFiles = new ArrayList<>();

		for (String arg : args) {
			File file = new File(arg);

			if (!file.exists() || !file.canRead()) {
				continue;
			}

			if (!file.isDirectory()) {
				String name = file.getName();

				if (name.endsWith(".lpkg")) {
					lpkgFiles.add(file);
				}

				continue;
			}

			File[] childFiles = file.listFiles(
				new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".lpkg");
					}

				});

			for (File childFile : childFiles) {
				if (childFile.exists() && childFile.canRead()) {
					lpkgFiles.add(childFile);
				}
			}
		}

		if (lpkgFiles.isEmpty()) {
			System.err.println(
				"== No LPKG files found in " + Arrays.toString(args));

			return;
		}

		for (File lpkgFile : lpkgFiles) {
			LPKGIndexer lpkgIndexer = new LPKGIndexer(lpkgFile);

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			lpkgIndexer.index(byteArrayOutputStream);

			Path indexFilePath = _getIndexFilePath(outputDirPath, lpkgFile);

			Files.write(indexFilePath, byteArrayOutputStream.toByteArray());

			System.out.println("== Wrote index file " + indexFilePath);
		}
	}

	private static Path _getIndexFilePath(Path outputDirPath, File lpkgFile)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(lpkgFile)) {
			ZipEntry zipEntry = zipFile.getEntry(
				"liferay-marketplace.properties");

			if (zipEntry == null) {
				throw new Exception(
					lpkgFile + " does not have liferay-marketplace.properties");
			}

			Properties properties = new Properties();

			try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
				properties.load(inputStream);
			}

			String symbolicName = properties.getProperty("title");

			if ((symbolicName == null) || symbolicName.isEmpty()) {
				throw new Exception(
					lpkgFile + " does not have a valid symbolic name");
			}

			Version version = null;

			String versionString = properties.getProperty("version");

			try {
				version = new Version(versionString);
			}
			catch (IllegalArgumentException iae) {
				throw new Exception(
					lpkgFile + " does not have a valid version: " +
						versionString,
					iae);
			}

			return outputDirPath.resolve(
				symbolicName + "-" + version + "-index.xml");
		}
	}

}
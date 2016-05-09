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

import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.indexer.ResourceIndexer;
import org.osgi.service.indexer.impl.RepoIndex;

/**
 * @author Raymond Augé
 */
public class LPKGIndexer implements Indexer {

	public static void main(String[] args) throws Exception {
		if ((args == null) || (args.length == 0)) {
			System.err.println(
				"Usage: <cmd> <list of lpkg files or directories containing " +
					"lpkg files>");

			return;
		}

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		PropsUtil.setProps(new PropsImpl());

		File targetPlatformDir = new File(
			PropsValues.MODULE_FRAMEWORK_BASE_DIR, TARGET_PLATFORM);

		if (!targetPlatformDir.exists() && !targetPlatformDir.mkdirs()) {
			System.err.printf(
				"[ERROR] Cannot create directory %s because of file " +
					"permissions\n",
				targetPlatformDir);

			return;
		}

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

			File[] files = file.listFiles(
				new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".lpkg");
					}

				});

			for (File entry : files) {
				if (entry.exists() && entry.canRead()) {
					lpkgFiles.add(entry);
				}
			}
		}

		if (lpkgFiles.isEmpty()) {
			System.err.println(
				"No lpkg files were found in args " + Arrays.toString(args));

			return;
		}

		for (File lpkgFile : lpkgFiles) {
			LPKGIndexer lpkgIndexer = new LPKGIndexer(lpkgFile);

			lpkgIndexer.index(targetPlatformDir);
		}
	}

	public LPKGIndexer(File lpkgFile) throws IOException {
		_lpkgFile = lpkgFile;

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

		String bsn = _lpkgFile.getName();
		String version = "1.0.0";

		try (ZipFile zipFile = new ZipFile(_lpkgFile)) {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			Set<File> fileList = new LinkedHashSet<>();

			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();

				String shortName = FileUtil.getShortFileName(entry.getName());

				if (shortName.endsWith("liferay-marketplace.properties")) {
					Properties properties = new Properties();

					properties.load(zipFile.getInputStream(entry));

					bsn = properties.getProperty("title");
					version = properties.getProperty("version");

					continue;
				}
				else if (!shortName.endsWith(".jar")) {
					continue;
				}

				File tempFile = new File(tempFolder, shortName);

				FileUtil.write(tempFile, zipFile.getInputStream(entry));

				fileList.add(tempFile);
			}

			File tempIndexFile = new File(
				tempFolder, bsn + "-" + version + "-index.xml");

			ResourceIndexer resourceIndexer = new RepoIndex();

			try (FileOutputStream fos = new FileOutputStream(tempIndexFile)) {
				resourceIndexer.index(fileList, fos, _indexerConfig);
			}

			if (output.isDirectory()) {
				output = new File(output, tempIndexFile.getName());
			}

			FileUtil.copyFile(tempIndexFile, output);

			return output;
		}
		finally {
			FileUtil.deltree(tempFolder);
		}
	}

	private final Map<String, String> _indexerConfig;
	private final File _lpkgFile;

}
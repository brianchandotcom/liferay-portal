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

import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.target.platform.indexer.Indexer;

import java.io.File;
import java.io.FileOutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
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

	public LPKGIndexer(File lpkgFile) {
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
		Path tempFolder = Files.createTempDirectory(null);

		_indexerConfig.put("root.url", tempFolder.toFile().getCanonicalPath());

		String bsn = _lpkgFile.getName();
		String version = "1.0.0";

		try (ZipFile zipFile = new ZipFile(_lpkgFile)) {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			Set<File> fileList = new LinkedHashSet<>();

			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();

				String name = entry.getName();

				if (name.endsWith("liferay-marketplace.properties")) {
					Properties properties = new Properties();

					properties.load(zipFile.getInputStream(entry));

					bsn = properties.getProperty("title");
					version = properties.getProperty("version");

					continue;
				}
				else if (!name.endsWith(".jar")) {
					continue;
				}

				File tempFile = new File(tempFolder.toFile(), name);

				Files.copy(zipFile.getInputStream(entry), tempFile.toPath());

				fileList.add(tempFile);
			}

			File tempIndexFile = new File(
				tempFolder.toFile(), bsn + "-" + version + "-index.xml");

			ResourceIndexer resourceIndexer = new RepoIndex();

			try (FileOutputStream fos = new FileOutputStream(tempIndexFile)) {
				resourceIndexer.index(fileList, fos, _indexerConfig);
			}

			if (output.isDirectory()) {
				output = new File(output, tempIndexFile.getName());
			}

			Files.copy(
				tempIndexFile.toPath(), output.toPath(),
				StandardCopyOption.COPY_ATTRIBUTES,
				StandardCopyOption.REPLACE_EXISTING);

			return output;
		}
		finally {
			Util.deltree(tempFolder);
		}
	}

	private final Map<String, String> _indexerConfig;
	private final File _lpkgFile;

}
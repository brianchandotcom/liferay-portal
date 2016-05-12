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

import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.target.platform.indexer.Indexer;
import com.liferay.portal.target.platform.indexer.internal.LPKGIndexer;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FilenameFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		PropsUtil.setProps(new PropsImpl());

		File targetPlatformDir = new File(
			PropsValues.MODULE_FRAMEWORK_BASE_DIR, Indexer.TARGET_PLATFORM);

		if (!targetPlatformDir.exists() && !targetPlatformDir.mkdirs()) {
			System.err.printf(
				"== Cannot create directory %s\n", targetPlatformDir);

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
				"== No lpkg files found in " + Arrays.toString(args));

			return;
		}

		for (File lpkgFile : lpkgFiles) {
			LPKGIndexer lpkgIndexer = new LPKGIndexer(lpkgFile);

			File indexFile = lpkgIndexer.index(targetPlatformDir);

			System.out.println("== Wrote index file " + indexFile);
		}
	}

}
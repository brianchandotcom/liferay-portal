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

		String moduleFrameworkBaseDir = System.getProperty(
			"module.framework.base.dir");

		if (moduleFrameworkBaseDir == null) {
			System.err.println(
				"== -Dmodule.framework.base.dir= must point to a valid path");

			return;
		}

		File outputDir = new File(
			moduleFrameworkBaseDir, Indexer.DIR_NAME_TARGET_PLATFORM);

		String outputDirProperty = System.getProperty("output.dir");

		if (outputDirProperty != null) {
			outputDir = new File(outputDirProperty);
		}

		if (!outputDir.exists() && !outputDir.mkdirs()) {
			System.err.printf("== Unable to create directory %s\n", outputDir);

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

			File indexFile = lpkgIndexer.index(outputDir);

			System.out.println("== Wrote index file " + indexFile);
		}
	}

}
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

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

import com.liferay.portal.target.platform.indexer.internal.IndexValidator;

import java.io.File;
import java.io.FilenameFilter;

import java.net.URI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class IndexValidatorMain {

	public static void main(String[] args) throws Exception {
		if ((args == null) || (args.length == 0)) {
			System.err.println(
				"== Usage: <cmd> <list of index files or directories " +
					"containing index files>");

			return;
		}

		Boolean includeTargetPlatform = Boolean.getBoolean(
			"include.target.platform");

		String moduleFrameworkBaseDir = System.getProperty(
			"module.framework.base.dir");

		if (includeTargetPlatform && (moduleFrameworkBaseDir == null)) {
			System.err.println(
				"== -Dmodule.framework.base.dir must be set when " +
					"-Dinclude.target.platform is set.");

			return;
		}

		List<URI> indexURIs = new ArrayList<>();

		for (String arg : args) {
			File file = new File(arg);

			if (!file.exists() || !file.canRead()) {
				continue;
			}

			if (!file.isDirectory()) {
				String name = file.getName();

				if (name.endsWith(".xml") || name.endsWith(".xml.gz")) {
					indexURIs.add(file.toURI());
				}

				continue;
			}

			File[] childFiles = file.listFiles(
				new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						if (name.endsWith(".xml") || name.endsWith(".xml.gz")) {
							return true;
						}

						return false;
					}

				});

			for (File childFile : childFiles) {
				if (childFile.exists() && childFile.canRead()) {
					indexURIs.add(childFile.toURI());
				}
			}
		}

		if (indexURIs.isEmpty()) {
			System.err.println(
				"== No index files found in " + Arrays.toString(args));

			return;
		}

		IndexValidator indexValidator = new IndexValidator();

		indexValidator.setIncludeTargetPlatform(includeTargetPlatform);

		if (includeTargetPlatform) {
			indexValidator.setModuleFrameworkBaseDir(moduleFrameworkBaseDir);
		}

		long start = System.currentTimeMillis();

		try {
			List<String> messages = indexValidator.validate(indexURIs);

			if (!messages.isEmpty()) {
				System.out.println("== Validation errors");

				for (String message : messages) {
					System.out.println("== " + message);
				}
			}
			else {
				System.out.println("== Successfully validated");
			}
		}
		finally {
			long duration = System.currentTimeMillis() - start;

			System.out.printf(
				"== Time %02d:%02ds\n", MILLISECONDS.toMinutes(duration),
				MILLISECONDS.toSeconds(duration) -
					MINUTES.toSeconds(MILLISECONDS.toMinutes(duration)));
		}
	}

}
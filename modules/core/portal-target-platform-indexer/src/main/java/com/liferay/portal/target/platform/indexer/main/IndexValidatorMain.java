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

		List<URI> indexFiles = new ArrayList<>();

		for (String arg : args) {
			File file = new File(arg);

			if (!file.exists() || !file.canRead()) {
				continue;
			}

			if (!file.isDirectory()) {
				String name = file.getName();

				if (name.endsWith(".xml") || name.endsWith(".xml.gz")) {
					indexFiles.add(file.toURI());
				}

				continue;
			}

			File[] files = file.listFiles(
				new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						if (name.endsWith(".xml") || name.endsWith(".xml.gz")) {
							return true;
						}

						return false;
					}

				});

			for (File entry : files) {
				if (entry.exists() && entry.canRead()) {
					indexFiles.add(entry.toURI());
				}
			}
		}

		if (indexFiles.isEmpty()) {
			System.err.println(
				"== No index files found in " + Arrays.toString(args));

			return;
		}

		IndexValidator indexValidator = new IndexValidator();

		List<String> errors = indexValidator.validate(indexFiles);

		if (!errors.isEmpty()) {
			System.out.println("== Validation errors");

			for (String message : errors) {
				System.out.println("== " + message);
			}
		}
		else {
			System.out.println("== Successfully validated");
		}
	}

}
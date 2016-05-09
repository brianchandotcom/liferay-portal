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

import aQute.bnd.osgi.resource.ResourceBuilder;

import biz.aQute.resolve.ResolverValidator;
import biz.aQute.resolve.ResolverValidator.Resolution;

import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FilenameFilter;

import java.net.URI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class IndexValidator implements Validator {

	public static void main(String[] args) throws Exception {
		if ((args == null) || (args.length == 0)) {
			System.err.println(
				"Usage: <cmd> <list of index files or directories containing " +
					"index files>");

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
				"No index files were found in args " + Arrays.toString(args));

			return;
		}

		IndexValidator indexValidator = new IndexValidator();

		for (String message : indexValidator.validate(indexFiles)) {
			System.out.println(message);
		}
	}

	public void includeTargetPlatform(boolean includeTargetPlatform) {
		_includeTargetPlatform = includeTargetPlatform;
	}

	@Override
	public List<String> validate(List<URI> indexes) throws Exception {
		if (_includeTargetPlatform) {
			indexes = new ArrayList<>(indexes);

			File targetPlatformDir = new File(
				PropsValues.MODULE_FRAMEWORK_BASE_DIR, Indexer.TARGET_PLATFORM);

			if (targetPlatformDir.exists() && targetPlatformDir.canRead()) {
				File[] targetPlatformIndexes = targetPlatformDir.listFiles(
					new FilenameFilter() {

						@Override
						public boolean accept(File dir, String name) {
							if (name.endsWith(".xml") ||
								name.endsWith(".xml.gz")) {

								return true;
							}

							return false;
						}

					});

				for (File index : targetPlatformIndexes) {
					indexes.add(index.toURI());
				}
			}
		}

		try (ResolverValidator validator = new ResolverValidator()) {
			ResourceBuilder system = new ResourceBuilder();

			validator.setSystem(system.build());

			for (URI uri : indexes) {
				validator.addRepository(uri);
			}

			List<String> messages = new ArrayList<>();
			List<Resolution> results = validator.validate();

			validator.check();

			for (Resolution resolution : results) {
				String message = resolution.message;

				if (message == null) {
					continue;
				}

				if (message.startsWith(_MESSAGE_PREFIX)) {
					message = message.substring(_MESSAGE_PREFIX.length());
				}

				messages.add(message);
			}

			return messages;
		}
	}

	private static final String _MESSAGE_PREFIX =
		"Unable to resolve <<INITIAL>> version=null: ";

	private boolean _includeTargetPlatform;

}
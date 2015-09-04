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

package com.liferay.source.formatter.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BaseSourceProcessor;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class GitUtil {

	public static List<String> getLocalChangesFileNames(String baseDirName) 
		throws Exception {

		Runtime runtime = Runtime.getRuntime();

		Process process = null;

		try {
			process = runtime.exec("git add . --dry-run");
		}
		catch (IOException ioe) {
			String errorMessage = ioe.getMessage();

			if (errorMessage.contains("Cannot run program")) {
				System.out.println(
					"Add Git to your PATH system variable before executing " +
						"'ant format-source-local-changes'.");

				System.exit(0);
			}

			throw ioe;
		}

		String content = StringUtil.read(process.getInputStream());

		int gitLevel = -1;

		for (int i = 0; i < BaseSourceProcessor.PORTAL_MAX_DIR_LEVEL; i++) {
			File file = new File(baseDirName + ".git");

			if (file.exists()) {
				gitLevel = i;

				break;
			}

			baseDirName = "../" + baseDirName;
		}

		if (gitLevel == -1) {
			System.out.println(
				"Cannot retrieve files because .git directory is missing.");

			System.exit(1);
		}

		List<String> localChangesFileNames = new ArrayList<>();

		for (String line : StringUtil.splitLines(content)) {
			if (!line.startsWith("add '") ||
				(StringUtil.count(line, StringPool.SLASH) < gitLevel)) {

				continue;
			}

			line = line.substring(5, line.length() - 1);

			for (int i = 0; i < gitLevel; i++) {
				int x = line.indexOf(StringPool.SLASH);

				line = line.substring(x + 1);
			}

			localChangesFileNames.add(line);
		}

		return localChangesFileNames;
	}

}
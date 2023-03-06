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

package com.liferay.poshi.core.util;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class TestUtil {

	public static void printDiffs(
		String expectedFileName, String expectedContent, String actualFileName,
		String actualContent) {

		Patch<String> patch = DiffUtils.diff(
			_getLines(expectedContent), _getLines(actualContent));

		List<String> unifiedDiffLines = UnifiedDiffUtils.generateUnifiedDiff(
			expectedFileName, "Generated from " + actualFileName,
			_getLines(expectedContent), patch, 0);

		for (String line : unifiedDiffLines) {
			if (line.startsWith("+++") || line.startsWith("---")) {
				System.out.println(line);

				continue;
			}

			if (line.startsWith("+")) {
				System.out.println("\033[32m" + line + "\033[0m");

				continue;
			}

			if (line.startsWith("-")) {
				System.out.println("\033[31m" + line + "\033[0m");

				continue;
			}

			System.out.println(line);
		}
	}

	private static List<String> _getLines(String s) {
		List<String> lines = new LinkedList<>();

		String line = null;

		BufferedReader bufferedReader = new BufferedReader(new StringReader(s));

		try {
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}

			bufferedReader.close();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return lines;
	}

}
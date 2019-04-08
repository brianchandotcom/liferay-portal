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

package com.liferay.source.formatter.checks.util;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiSourceUtil {

	public static int[] getMultiLineCommentsPositions(String content) {
		Matcher matcher = _multiLineComments.matcher(content);
		List<Integer> multiLineCommentsPositions = new ArrayList<>();

		while (matcher.find()) {
			multiLineCommentsPositions.add(
				SourceUtil.getLineNumber(content, matcher.start()));
			multiLineCommentsPositions.add(
				SourceUtil.getLineNumber(content, matcher.end() - 1));
		}

		return ArrayUtil.toIntArray(multiLineCommentsPositions);
	}

	public static int[] getMultiLineStringPositions(String content) {
		Matcher matcher = _multiLineStringPattern.matcher(content);
		List<Integer> multiLineStringPositions = new ArrayList<>();

		while (matcher.find()) {
			multiLineStringPositions.add(
				SourceUtil.getLineNumber(content, matcher.start()));
			multiLineStringPositions.add(
				SourceUtil.getLineNumber(content, matcher.end() - 1));
		}

		return ArrayUtil.toIntArray(multiLineStringPositions);
	}

	public static boolean isInsideMultiLineComments(
		int lineNumber, int[] multiLineCommentsPositions) {

		for (int i = 0; i < multiLineCommentsPositions.length - 1; i = i + 2) {
			if (lineNumber < multiLineCommentsPositions[i]) {
				break;
			}

			if (lineNumber <= multiLineCommentsPositions[i + 1]) {
				return true;
			}
		}

		return false;
	}

	public static boolean isInsideMultiLineString(
		int lineNumber, int[] multiLineStringPositions) {

		for (int i = 0; i < multiLineStringPositions.length - 1; i = i + 2) {
			if (lineNumber < multiLineStringPositions[i]) {
				break;
			}

			if (lineNumber <= multiLineStringPositions[i + 1]) {
				return true;
			}
		}

		return false;
	}

	private static final Pattern _multiLineComments = Pattern.compile(
		"[ \t]/\\*.*?\\*/", Pattern.DOTALL);
	private static final Pattern _multiLineStringPattern = Pattern.compile(
		"[ \t]*.+ = '''.*?'''", Pattern.DOTALL);

}
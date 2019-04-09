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

	public static int[] getMultiLinePositions(
		String content, Pattern multiLinePattern) {

		List<Integer> multiLinePositions = new ArrayList<>();

		Matcher matcher = multiLinePattern.matcher(content);

		while (matcher.find()) {
			multiLinePositions.add(
				SourceUtil.getLineNumber(content, matcher.start()));
			multiLinePositions.add(
				SourceUtil.getLineNumber(content, matcher.end() - 1));
		}

		return ArrayUtil.toIntArray(multiLinePositions);
	}

	public static boolean isInsideMultiLines(
		int lineNumber, int[] multiLinePositions) {

		for (int i = 0; i < (multiLinePositions.length - 1); i += 2) {
			if (lineNumber < multiLinePositions[i]) {
				return false;
			}

			if (lineNumber <= multiLinePositions[i + 1]) {
				return true;
			}
		}

		return false;
	}

}
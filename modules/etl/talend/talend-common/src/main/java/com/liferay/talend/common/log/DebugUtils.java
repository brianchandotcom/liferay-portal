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

package com.liferay.talend.common.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class DebugUtils {

	public static void debugCurrentStackTrace(Logger logger) {
		if (logger.isDebugEnabled()) {
			logger.debug(
				"Actual thread's stack trace: " + _getCurrentStackTrace());
		}
	}

	public static void infoCurrentStackTrace(Logger logger) {
		if (logger.isInfoEnabled()) {
			logger.info(
				"Actual thread's stack trace: " + _getCurrentStackTrace());
		}
	}

	private static String _getCurrentStackTrace() {
		Throwable throwable = new Throwable();

		List<StackTraceElement> stackTraceElements = new ArrayList<>(
			Arrays.asList(throwable.getStackTrace()));

		Stream<StackTraceElement> stream = stackTraceElements.stream();

		return String.join(
			System.lineSeparator(),
			stream.map(
				element -> _toClassAndMethod(element.toString())
			).collect(
				Collectors.toList()
			));
	}

	private static String _toClassAndMethod(String line) {
		Matcher matcher = _stackLinePattern.matcher(line);

		if (!matcher.matches()) {
			return line;
		}

		return matcher.group(2) + matcher.group(1);
	}

	private DebugUtils() {
	}

	private static final Pattern _stackLinePattern = Pattern.compile(
		".+(\\.\\w+)\\((.+)\\)");

}
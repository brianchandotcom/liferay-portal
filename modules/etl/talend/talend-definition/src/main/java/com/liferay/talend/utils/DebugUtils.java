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

package com.liferay.talend.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;

/**
 * @author Zoltán Takács
 */
public class DebugUtils {

	public static void logCurrentStackTrace(Logger logger) {
		if (logger.isDebugEnabled()) {
			Throwable throwable = new Throwable();

			List<StackTraceElement> stackTraceElements = new ArrayList<>(
				Arrays.asList(throwable.getStackTrace()));

			Stream<StackTraceElement> stream = stackTraceElements.stream();

			List<String> stackTraces = stream.map(
				element -> element.toString()
			).collect(
				Collectors.toList()
			);

			logger.debug(
				"Actual thread's stacktrace: " +
					String.join(System.lineSeparator(), stackTraces));
		}
	}

	private DebugUtils() {
	}

}
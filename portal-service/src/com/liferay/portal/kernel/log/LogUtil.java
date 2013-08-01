/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.log;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class LogUtil {

	public static final boolean REMOVE_UNKNOWN_SOURCE = true;

	public static final int STACK_TRACE_LENGTH = 20;

	public static void debug(Log log, Properties props) {
		if (log.isDebugEnabled()) {
			for (String key : props.stringPropertyNames()) {
				String value = props.getProperty(key);

				log.debug(key + StringPool.EQUAL + value);
			}
		}
	}

	public static void log(Log log, Throwable throwable) {
		log(log, throwable, null);
	}

	public static void log(Log log, Throwable throwable, String message) {
		if (throwable == null) {
			if (Validator.isNotNull(message)) {
				log.error(message);

				return;
			}

			throw new IllegalArgumentException(
				"Either throwable or message must not be null");
		}

		Throwable cause = throwable;

		while (cause.getCause() != null) {
			cause = cause.getCause();
		}

		StackTraceElement[] steArray = cause.getStackTrace();

		// Make the stack trace more readable by limiting the number of
		// elements.

		if (steArray.length <= STACK_TRACE_LENGTH) {
			if (Validator.isNotNull(message)) {
				log.error(message, cause);
			}
			else {
				log.error(cause);
			}

			return;
		}

		int count = 0;

		List<StackTraceElement> steList = new ArrayList<StackTraceElement>();

		for (StackTraceElement ste : steArray) {

			// Make the stack trace more readable by removing elements that
			// refer to classes with no packages, or starts with a $, or are
			// Spring classes, or are standard reflection classes.

			String className = ste.getClassName();

			boolean addElement = true;

			if (REMOVE_UNKNOWN_SOURCE && (ste.getLineNumber() < 0)) {
				addElement = false;
			}

			if (className.startsWith("$") ||
				className.startsWith("java.lang.reflect.") ||
				className.startsWith("org.springframework.") ||
				className.startsWith("sun.reflect.")) {

				addElement = false;
			}

			if (addElement) {
				steList.add(ste);

				count++;
			}

			if (count >= STACK_TRACE_LENGTH) {
				break;
			}
		}

		steArray = steList.toArray(new StackTraceElement[steList.size()]);

		cause.setStackTrace(steArray);

		if (Validator.isNotNull(message)) {
			log.error(message, cause);
		}
		else {
			log.error(cause);
		}
	}

}
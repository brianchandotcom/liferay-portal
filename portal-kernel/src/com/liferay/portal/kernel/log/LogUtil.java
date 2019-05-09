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

package com.liferay.portal.kernel.log;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
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
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(
				props.size() + 1);

			props.list(UnsyncPrintWriterPool.borrow(unsyncStringWriter));

			log.debug(unsyncStringWriter.toString());
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
				"Throwable or message must be set");
		}

		Throwable causeThrowable = throwable;

		while (causeThrowable.getCause() != null) {
			causeThrowable = causeThrowable.getCause();
		}

		StackTraceElement[] stackTraceElements = causeThrowable.getStackTrace();

		// Make the stack trace more readable by limiting the number of
		// elements.

		if (stackTraceElements.length <= STACK_TRACE_LENGTH) {
			if (Validator.isNotNull(message)) {
				log.error(message, causeThrowable);
			}
			else {
				log.error(causeThrowable);
			}

			return;
		}

		int count = 0;

		List<StackTraceElement> stackTraceElementsList = new ArrayList<>();

		for (StackTraceElement stackTraceElement : stackTraceElements) {

			// Make the stack trace more readable by removing elements that
			// refer to classes with no packages, or starts with a $, or are
			// Spring classes, or are standard reflection classes.

			String className = stackTraceElement.getClassName();

			boolean addElement = true;

			if (REMOVE_UNKNOWN_SOURCE &&
				(stackTraceElement.getLineNumber() < 0)) {

				addElement = false;
			}

			if (className.startsWith("$") ||
				className.startsWith("java.lang.reflect.") ||
				className.startsWith("org.springframework.") ||
				className.startsWith("sun.reflect.")) {

				addElement = false;
			}

			if (addElement) {
				stackTraceElementsList.add(stackTraceElement);

				count++;
			}

			if (count >= STACK_TRACE_LENGTH) {
				break;
			}
		}

		stackTraceElements = stackTraceElementsList.toArray(
			new StackTraceElement[0]);

		causeThrowable.setStackTrace(stackTraceElements);

		if (Validator.isNotNull(message)) {
			log.error(message, causeThrowable);
		}
		else {
			log.error(causeThrowable);
		}
	}

}
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

package com.liferay.poshi.runner.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PoshiRunnerWarningException extends Exception {

	public static void addException(
		PoshiRunnerWarningException poshiRunnerWarningException) {

		_initPoshiRunnerWarningExceptions();

		List<PoshiRunnerWarningException> poshiRunnerWarningExceptions =
			_threadBasedPoshiRunnerWarningExceptions.get(_getThreadName());

		poshiRunnerWarningExceptions.add(poshiRunnerWarningException);
	}

	public static void clear() {
		_threadBasedPoshiRunnerWarningExceptions.remove(_getThreadName());
	}

	public static List<PoshiRunnerWarningException>
		getPoshiRunnerWarningExceptions() {

		_initPoshiRunnerWarningExceptions();

		return _threadBasedPoshiRunnerWarningExceptions.get(_getThreadName());
	}

	public PoshiRunnerWarningException(String msg) {
		super(msg);

		_initPoshiRunnerWarningExceptions();

		List<PoshiRunnerWarningException> poshiRunnerWarningExceptions =
			_threadBasedPoshiRunnerWarningExceptions.get(_getThreadName());

		poshiRunnerWarningExceptions.add(this);
	}

	public PoshiRunnerWarningException(String msg, Throwable throwable) {
		super(msg, throwable);

		_initPoshiRunnerWarningExceptions();

		List<PoshiRunnerWarningException> poshiRunnerWarningExceptions =
			_threadBasedPoshiRunnerWarningExceptions.get(_getThreadName());

		poshiRunnerWarningExceptions.add(this);
	}

	private static String _getThreadName() {
		Thread thread = Thread.currentThread();

		return thread.getName();
	}

	private static void _initPoshiRunnerWarningExceptions() {
		String threadName = _getThreadName();

		if (!_threadBasedPoshiRunnerWarningExceptions.containsKey(threadName)) {
			_threadBasedPoshiRunnerWarningExceptions.put(
				threadName, new ArrayList<>());
		}
	}

	private static final Map<String, List<PoshiRunnerWarningException>>
		_threadBasedPoshiRunnerWarningExceptions = Collections.synchronizedMap(
			new HashMap<>());

}
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
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PoshiRunnerWarningException extends Exception {

	public static void addException(
		PoshiRunnerWarningException poshiRunnerWarningException) {

		_poshiRunnerWarningExceptions.add(poshiRunnerWarningException);
	}

	public static List<PoshiRunnerWarningException>
		getPoshiRunnerWarningExceptions() {

		return _poshiRunnerWarningExceptions;
	}

	public PoshiRunnerWarningException(String msg) {
		super(msg);

		_poshiRunnerWarningExceptions.add(this);
	}

	public PoshiRunnerWarningException(String msg, Throwable throwable) {
		super(msg, throwable);

		_poshiRunnerWarningExceptions.add(this);
	}

	private static final List<PoshiRunnerWarningException>
		_poshiRunnerWarningExceptions = new ArrayList<>();

}
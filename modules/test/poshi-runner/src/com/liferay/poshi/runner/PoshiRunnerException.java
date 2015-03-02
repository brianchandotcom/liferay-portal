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

package com.liferay.poshi.runner;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */

public class PoshiRunnerException extends Exception {

	public PoshiRunnerException() {
		this(null, null);
	}

	public PoshiRunnerException(String msg) {
		this(msg, null);
	}

	public PoshiRunnerException(String msg, Throwable cause) {
		super(_getExceptionMessage(msg), cause);
	}

	public PoshiRunnerException(Throwable cause) {
		this(null, cause);
	}

	private static String _getExceptionMessage(String msg) {
		StringBuilder sb = new StringBuilder();

		if (msg == null) {
			sb.append("\nBUILD FAILED:");
		}
		else {
			sb.append("\n");
			sb.append(msg);
		}

		sb.append("\n");

		sb.append(PoshiRunnerStackTraceUtil.getStackTrace());

		return sb.toString();
	}

}
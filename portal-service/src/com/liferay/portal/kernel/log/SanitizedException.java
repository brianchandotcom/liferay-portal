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

/**
 * @author Tomas Polesovsky
 */
public class SanitizedException extends Exception {

	public SanitizedException() {
	}

	public SanitizedException(String message) {
		super(message);
	}

	public SanitizedException(
		String message, StackTraceElement[] stackTrace, Throwable cause) {

		super(message, cause);
		setStackTrace(stackTrace);
	}

	public SanitizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public SanitizedException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		String className = getClass().getName();
		String localizedMessage = getLocalizedMessage();

		if (localizedMessage != null) {
			return localizedMessage;
		}

		return className;
	}

}
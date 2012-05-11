/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.util.JavaDetector;

import java.security.AccessController;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 */
public class CheckerUtil {

	public static boolean isAccessControllerDoPrivileged(int frame) {
		frame++;

		Class<?> callerClass = Reflection.getCallerClass(frame);

		if (callerClass != AccessController.class) {
			return false;
		}

		Thread currentThread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

		if (JavaDetector.isIBM()) {
			frame++;
		}

		StackTraceElement stackTraceElement = stackTraceElements[frame];

		String methodName = stackTraceElement.getMethodName();

		return methodName.equals(_METHOD_NAME_DO_PRIVILEGED);
	}

	private static final String _METHOD_NAME_DO_PRIVILEGED = "doPrivileged";

}
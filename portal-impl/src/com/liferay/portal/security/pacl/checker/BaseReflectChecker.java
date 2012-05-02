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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.security.AccessController;

import org.springframework.util.ReflectionUtils;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public abstract class BaseReflectChecker extends BaseChecker {

	protected boolean hasRelect(String name, String action) {

		// JSP compiler

		if (isJSPCompiler(name, action)) {
			return true;
		}

		// Prefecth the necessary caller classes

		Class<?> callerClass7 = Reflection.getCallerClass(7);
		Class<?> callerClass8 = Reflection.getCallerClass(8);
		Class<?> callerClass9 = Reflection.getCallerClass(9);
		Class<?> callerClass10 = Reflection.getCallerClass(10);

		// java.lang.Class

		if ((callerClass7.getEnclosingClass() == Class.class) &&
			(callerClass8 == AccessController.class)) {

			Thread currentThread = Thread.currentThread();

			StackTraceElement[] stackTraceElements =
				currentThread.getStackTrace();

			StackTraceElement stackTraceElement = stackTraceElements[8];

			String methodName = stackTraceElement.getMethodName();

			if (methodName.equals(_METHOD_NAME_DO_PRIVILEGED)) {
				logReflect(callerClass7, 7);

				return true;
			}
		}

		// java.lang.Thread

		if ((callerClass9.getEnclosingClass() == Thread.class) &&
			(callerClass10 == AccessController.class)) {

			Thread currentThread = Thread.currentThread();

			StackTraceElement[] stackTraceElements =
				currentThread.getStackTrace();

			StackTraceElement stackTraceElement = stackTraceElements[10];

			String methodName = stackTraceElement.getMethodName();

			if (methodName.equals(_METHOD_NAME_DO_PRIVILEGED)) {
				logReflect(callerClass9, 9);

				return true;
			}
		}

		// org.springframework.util.ReflectionUtils

		if (callerClass7 == ReflectionUtils.class) {
			logReflect(callerClass7, 7);

			return true;
		}

		if (callerClass9 == ReflectionUtils.class) {
			logReflect(callerClass9, 9);

			return true;
		}

		// Reject

		if (_log.isDebugEnabled()) {
			_log.debug("Rejecting call stack:");

			for (int i = 5; i < 11; i++) {
				_log.debug("Frame " + i + " " + Reflection.getCallerClass(i));
			}
		}

		return false;
	}

	protected void logReflect(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allow frame " + frame + " with caller " + callerClass +
					" to reflect");
		}
	}

	private static final String _METHOD_NAME_DO_PRIVILEGED = "doPrivileged";

	private static Log _log = LogFactoryUtil.getLog(BaseReflectChecker.class);

}
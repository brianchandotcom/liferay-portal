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

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Jonathan McCann
 */
public class LogInterceptorUtil {

	public static void addError(String className, String error) {
		_logInterceptor.addError(className, error);
	}

	public static void addEvent(String className, long duration) {
		_logInterceptor.addEvent(className, duration);
	}

	public static void addWarning(String className, String warning) {
		_logInterceptor.addWarning(className, warning);
	}

	private static volatile LogInterceptor _logInterceptor =
		ServiceProxyFactory.newServiceTrackedInstance(
			LogInterceptor.class, LogInterceptorUtil.class, "_logInterceptor",
			false);

}
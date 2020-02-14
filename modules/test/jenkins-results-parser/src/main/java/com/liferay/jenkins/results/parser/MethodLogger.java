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

package com.liferay.jenkins.results.parser;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Michael Hashimoto
 */
public class MethodLogger implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		Object result = null;

		_logger.log(
			Level.INFO,
			JenkinsResultsParserUtil.combine(
				"Starting ", _getClassMethodName(method)));

		long start = System.currentTimeMillis();

		try {
			result = method.invoke(_object, args);
		}
		catch (Throwable t) {
			throw t;
		}
		finally {
			String duration = JenkinsResultsParserUtil.toDurationString(
				System.currentTimeMillis() - start);

			_logger.log(
				Level.INFO,
				JenkinsResultsParserUtil.combine(
					"Completed ", _getClassMethodName(method), " in ", duration,
					".\n"));
		}

		return result;
	}

	protected MethodLogger(Object object) {
		_object = object;

		Class<?> clazz = getClass();

		_logger = Logger.getLogger(clazz.getName());
	}

	private String _getClassMethodName(Method method) {
		Class<?> clazz = _object.getClass();

		return clazz.getSimpleName() + "." + method.getName();
	}

	private final Logger _logger;
	private final Object _object;

}
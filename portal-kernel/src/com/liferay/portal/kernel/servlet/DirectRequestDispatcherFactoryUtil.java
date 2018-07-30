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

package com.liferay.portal.kernel.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;

/**
 * @author Raymond Augé
 */
public class DirectRequestDispatcherFactoryUtil {

	public static DirectRequestDispatcherFactory
		getDirectRequestDispatcherFactory() {

		return _directRequestDispatcherFactory;
	}

	public static RequestDispatcher getRequestDispatcher(
		ServletContext servletContext, String path) {

		return getDirectRequestDispatcherFactory().getRequestDispatcher(
			servletContext, path);
	}

	public static RequestDispatcher getRequestDispatcher(
		ServletRequest servletRequest, String path) {

		return getDirectRequestDispatcherFactory().getRequestDispatcher(
			servletRequest, path);
	}

	public void setDirectRequestDispatcherFactory(
		DirectRequestDispatcherFactory directRequestDispatcherFactory) {

		_directRequestDispatcherFactory = directRequestDispatcherFactory;
	}

	private static DirectRequestDispatcherFactory
		_directRequestDispatcherFactory;

}
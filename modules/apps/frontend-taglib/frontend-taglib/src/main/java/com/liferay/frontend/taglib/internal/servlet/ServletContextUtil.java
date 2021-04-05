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

package com.liferay.frontend.taglib.internal.servlet;

import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategoryProvider;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntryProvider;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationRegistry;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(immediate = true, service = {})
public class ServletContextUtil {

	public static FormNavigatorCategoryProvider
		getFormNavigatorCategoryProvider() {

		return _formNavigatorCategoryProvider;
	}

	public static FormNavigatorEntryProvider getFormNavigatorEntryProvider() {
		return _formNavigatorEntryProvider;
	}

	public static ScreenNavigationRegistry getScreenNavigationRegistry() {
		return _screenNavigationRegistry;
	}

	public static ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(unbind = "-")
	protected void setFormNavigatorCategoryProvider(
		FormNavigatorCategoryProvider formNavigatorCategoryProvider) {

		_formNavigatorCategoryProvider = formNavigatorCategoryProvider;
	}

	@Reference(unbind = "-")
	protected void setFormNavigatorEntryProvider(
		FormNavigatorEntryProvider formNavigatorEntryProvider) {

		_formNavigatorEntryProvider = formNavigatorEntryProvider;
	}

	@Reference(unbind = "-")
	protected void setScreenNavigationRegistry(
		ScreenNavigationRegistry screenNavigationRegistry) {

		_screenNavigationRegistry = screenNavigationRegistry;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static FormNavigatorCategoryProvider _formNavigatorCategoryProvider;
	private static FormNavigatorEntryProvider _formNavigatorEntryProvider;
	private static ScreenNavigationRegistry _screenNavigationRegistry;
	private static ServletContext _servletContext;

}
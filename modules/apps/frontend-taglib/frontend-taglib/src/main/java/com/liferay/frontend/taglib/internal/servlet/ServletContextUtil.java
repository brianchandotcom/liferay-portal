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
import com.liferay.osgi.util.service.Snapshot;

import javax.servlet.ServletContext;

/**
 * @author Roberto Díaz
 */
public class ServletContextUtil {

	public static FormNavigatorCategoryProvider
		getFormNavigatorCategoryProvider() {

		return _formNavigatorCategoryProviderSnapshot.get();
	}

	public static FormNavigatorEntryProvider getFormNavigatorEntryProvider() {
		return _formNavigatorEntryProviderSnapshot.get();
	}

	public static ScreenNavigationRegistry getScreenNavigationRegistry() {
		return _screenNavigationRegistrySnapshot.get();
	}

	public static ServletContext getServletContext() {
		return _servletContextSnapshot.get();
	}

	private static final Snapshot<FormNavigatorCategoryProvider>
		_formNavigatorCategoryProviderSnapshot = new Snapshot<>(
			ServletContextUtil.class, FormNavigatorCategoryProvider.class);
	private static final Snapshot<FormNavigatorEntryProvider>
		_formNavigatorEntryProviderSnapshot = new Snapshot<>(
			ServletContextUtil.class, FormNavigatorEntryProvider.class);
	private static final Snapshot<ScreenNavigationRegistry>
		_screenNavigationRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, ScreenNavigationRegistry.class);
	private static final Snapshot<ServletContext> _servletContextSnapshot =
		new Snapshot<>(
			ServletContextUtil.class, ServletContext.class,
			"(osgi.web.symbolicname=com.liferay.frontend.taglib)");

}
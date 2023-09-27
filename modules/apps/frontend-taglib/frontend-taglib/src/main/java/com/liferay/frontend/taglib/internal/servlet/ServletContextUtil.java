/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
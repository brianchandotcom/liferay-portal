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

package com.liferay.portlet.imagegallerydisplay.display.context;

import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class IGDisplayContextProviderUtil {

	public static IGDisplayContextProvider getIGDisplayContextProvider() {
		return _igDisplayContextProvider;
	}

	public static IGViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileShortcut fileShortcut) {

		return getIGDisplayContextProvider().
			getIGFileVersionActionsDisplayContext(
				request, response, fileShortcut);
	}

	public static IGViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		return getIGDisplayContextProvider().
			getIGFileVersionActionsDisplayContext(
				request, response, fileVersion);
	}

	public void setIGDisplayContextProvider(
		IGDisplayContextProvider igDisplayContextProvider) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_igDisplayContextProvider = igDisplayContextProvider;
	}

	private static IGDisplayContextProvider _igDisplayContextProvider;

}
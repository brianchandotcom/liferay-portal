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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivan Zaera
 */
public class DLDisplayContextFactoryProviderUtil {

	public static DLDisplayContextFactoryProvider
		getDLDisplayContextFactoryProvider() {

		return _dlDisplayContextFactoryProvider;
	}

	public static DLEditFileEntryDisplayContext
		getDLEditFileEntryDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			DLFileEntryType dlFileEntryType) {

		return getDLDisplayContextFactoryProvider().
			getDLEditFileEntryDisplayContext(
				request, response, dlFileEntryType);
	}

	public static DLEditFileEntryDisplayContext
		getDLEditFileEntryDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileEntry fileEntry) {

		return getDLDisplayContextFactoryProvider().
			getDLEditFileEntryDisplayContext(request, response, fileEntry);
	}

	public static DLViewFileVersionDisplayContext
		getDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			DLFileShortcut dlFileShortcut) {

		return getDLDisplayContextFactoryProvider().
			getDLViewFileVersionDisplayContext(
				request, response, dlFileShortcut);
	}

	public static DLViewFileVersionDisplayContext
		getDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		return getDLDisplayContextFactoryProvider().
			getDLViewFileVersionDisplayContext(request, response, fileVersion);
	}

	public static DLViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			DLFileShortcut dlFileShortcut) {

		return getDLDisplayContextFactoryProvider().
			getIGFileVersionActionsDisplayContext(
				request, response, dlFileShortcut);
	}

	public static DLViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		return getDLDisplayContextFactoryProvider().
			getIGFileVersionActionsDisplayContext(
				request, response, fileVersion);
	}

	public void setDLDisplayContextFactoryProvider(
		DLDisplayContextFactoryProvider dlDisplayContextFactoryProvider) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_dlDisplayContextFactoryProvider = dlDisplayContextFactoryProvider;
	}

	private static DLDisplayContextFactoryProvider
		_dlDisplayContextFactoryProvider =
			new DLDisplayContextFactoryProviderImpl();

}
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

package com.liferay.document.library.taglib.internal.display.context;

import com.liferay.document.library.display.context.DLDisplayContextProvider;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.repository.model.FileVersion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alejandro Tardín
 */
public class DLViewFileVersionDisplayContextUtil {

	public static DLViewFileVersionDisplayContext
		getDLViewFileVersionDisplayContext(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FileVersion fileVersion) {

		DLDisplayContextProvider dlDisplayContextProvider =
			_dlDisplayContextProviderSnapshot.get();

		return dlDisplayContextProvider.getDLViewFileVersionDisplayContext(
			httpServletRequest, httpServletResponse, fileVersion);
	}

	private static final Snapshot<DLDisplayContextProvider>
		_dlDisplayContextProviderSnapshot = new Snapshot<>(
			DLViewFileVersionDisplayContextUtil.class,
			DLDisplayContextProvider.class);

}
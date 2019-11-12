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

package com.liferay.document.library.web.internal.util;

import com.liferay.document.library.util.DLAssetHelper;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = {})
public class DLAssetHelperUtil {

	public static long getAssetClassPK(
		FileEntry fileEntry, FileVersion fileVersion) {

		DLAssetHelper dlAssetHelper = getDLAssetHelper();

		return dlAssetHelper.getAssetClassPK(fileEntry, fileVersion);
	}

	public static DLAssetHelper getDLAssetHelper() {
		return _dlAssetHelper;
	}

	@Reference(unbind = "-")
	protected void setDLAssetHelper(DLAssetHelper dlAssetHelper) {
		_dlAssetHelper = dlAssetHelper;
	}

	private static DLAssetHelper _dlAssetHelper;

}
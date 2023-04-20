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

package com.liferay.image.uploader.web.internal.util;

import com.liferay.image.uploader.web.internal.helper.UploadImageHelper;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;

/**
 * @author Peter Fellwock
 */
public class UploadImageUtil {

	public static final String TEMP_IMAGE_FILE_NAME = "tempImageFileName";

	public static final String TEMP_IMAGE_FOLDER_NAME = "java.lang.Class";

	public static long getMaxFileSize(PortletRequest portletRequest) {
		UploadImageHelper uploadImageHelper = _uploadImageHelperSnapshot.get();

		return uploadImageHelper.getMaxFileSize(portletRequest);
	}

	public static FileEntry getTempImageFileEntry(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return TempFileEntryUtil.getTempFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			TEMP_IMAGE_FOLDER_NAME,
			ParamUtil.getString(portletRequest, TEMP_IMAGE_FILE_NAME));
	}

	private static final Snapshot<UploadImageHelper>
		_uploadImageHelperSnapshot = new Snapshot<>(
			UploadImageUtil.class, UploadImageHelper.class);

}
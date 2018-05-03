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

	public static FileEntry getTempImageFileEntry(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return TempFileEntryUtil.getTempFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			getTempImageFolderName(), getTempImageFileName(portletRequest));
	}

	public static String getTempImageFileName(PortletRequest portletRequest) {
		return ParamUtil.getString(portletRequest, "tempImageFileName");
	}

	public static String getTempImageFolderName() {
		Class<?> clazz = UploadImageUtil.class.getClass();

		return clazz.getName();
	}

}
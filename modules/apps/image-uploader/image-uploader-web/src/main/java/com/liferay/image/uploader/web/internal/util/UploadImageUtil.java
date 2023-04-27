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

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProviderUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

import javax.portlet.PortletRequest;

/**
 * @author Peter Fellwock
 */
public class UploadImageUtil {

	public static final String TEMP_IMAGE_FILE_NAME = "tempImageFileName";

	public static final String TEMP_IMAGE_FOLDER_NAME =
		UploadImageUtil.class.getName();

	public static long getMaxFileSize(PortletRequest portletRequest) {
		String currentLogoURL = portletRequest.getParameter("currentLogoURL");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (StringUtil.startsWith(
				currentLogoURL,
				themeDisplay.getPathImage() + "/user_female_portrait") ||
			StringUtil.startsWith(
				currentLogoURL,
				themeDisplay.getPathImage() + "/user_male_portrait") ||
			StringUtil.startsWith(
				currentLogoURL,
				themeDisplay.getPathImage() + "/user_portrait")) {

			UserFileUploadsSettings userFileUploadsSettings =
				_userFileUploadSettingsSnapshot.get();

			return userFileUploadsSettings.getImageMaxSize();
		}

		return UploadServletRequestConfigurationProviderUtil.getMaxSize();
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

	private static final Snapshot<UserFileUploadsSettings>
		_userFileUploadSettingsSnapshot = new Snapshot<>(
			UploadImageUtil.class, UserFileUploadsSettings.class);

}
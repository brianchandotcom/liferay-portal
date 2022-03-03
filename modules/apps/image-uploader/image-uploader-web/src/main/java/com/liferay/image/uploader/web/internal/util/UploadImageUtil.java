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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelper;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.users.admin.configuration.UserFileUploadsConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = UploadImageUtil.class
)
public class UploadImageUtil {

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

			return _userFileUploadsConfiguration.imageMaxSize();
		}

		return _uploadServletRequestConfigurationHelper.getMaxSize();
	}

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

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_userFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			UserFileUploadsConfiguration.class, properties);
	}

	@Reference(unbind = "-")
	protected void setUploadServletRequestConfigurationHelper(
		UploadServletRequestConfigurationHelper
			uploadServletRequestConfigurationHelper) {

		_uploadServletRequestConfigurationHelper =
			uploadServletRequestConfigurationHelper;
	}

	private static UploadServletRequestConfigurationHelper
		_uploadServletRequestConfigurationHelper;
	private static volatile UserFileUploadsConfiguration
		_userFileUploadsConfiguration;

}
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

package com.liferay.blogs.web.blogs.image.selector;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.selector.BaseImageSelectorUploadHandler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourcePermissionCheckerUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.blogs.CoverImageNameException;
import com.liferay.portlet.blogs.CoverImageSizeException;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Sergio González
 * @author Adolfo Pérez
 */
public abstract class BaseBlogsImageSelectorUploadHandler
	extends BaseImageSelectorUploadHandler {

	@Override
	public void checkPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException {

		boolean containsResourcePermission =
			ResourcePermissionCheckerUtil.containsResourcePermission(
				permissionChecker, BlogsPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_ENTRY);

		if (!containsResourcePermission) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, BlogsPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_ENTRY);
		}
	}

	@Override
	public void validateFile(String fileName, String contentType, long size)
		throws PortalException {

		String extension = FileUtil.getExtension(fileName);

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.BLOGS_IMAGE_EXTENSIONS, StringPool.COMMA);

		for (String imageExtension : imageExtensions) {
			if (StringPool.STAR.equals(imageExtension) ||
				imageExtension.equals(StringPool.PERIOD + extension)) {

				return;
			}
		}

		throw new CoverImageNameException(
			"Invalid cover image for file name " + fileName);
	}

	protected abstract long getMaxFileSize();

	@Override
	protected void handleUploadException(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortalException pe, JSONObject jsonObject)
		throws PortalException {

		jsonObject.put("success", Boolean.FALSE);

		if (pe instanceof AntivirusScannerException ||
			pe instanceof CoverImageNameException ||
			pe instanceof CoverImageSizeException ||
			pe instanceof FileNameException) {

			String errorMessage = StringPool.BLANK;
			int errorType = 0;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (pe instanceof AntivirusScannerException) {
				errorType =
					ServletResponseConstants.SC_FILE_ANTIVIRUS_EXCEPTION;
				AntivirusScannerException ase = (AntivirusScannerException)pe;

				errorMessage = themeDisplay.translate(ase.getMessageKey());
			}
			else if (pe instanceof CoverImageNameException) {
				errorType =
					ServletResponseConstants.SC_FILE_EXTENSION_EXCEPTION;
			}
			else if (pe instanceof CoverImageSizeException) {
				errorType = ServletResponseConstants.SC_FILE_SIZE_EXCEPTION;
			}
			else if (pe instanceof FileNameException) {
				errorType = ServletResponseConstants.SC_FILE_NAME_EXCEPTION;
			}

			JSONObject errorJSONObject = JSONFactoryUtil.createJSONObject();

			errorJSONObject.put("errorType", errorType);
			errorJSONObject.put("message", errorMessage);

			jsonObject.put("error", errorJSONObject);

			try {
				JSONPortletResponseUtil.writeJSON(
					portletRequest, portletResponse, jsonObject);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
		else {
			throw pe;
		}
	}

}
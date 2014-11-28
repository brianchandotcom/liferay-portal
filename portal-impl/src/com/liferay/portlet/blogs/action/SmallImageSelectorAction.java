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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.action.BaseImageSelectorAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourcePermissionCheckerUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.blogs.CoverImageNameException;
import com.liferay.portlet.blogs.CoverImageSizeException;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;

/**
 * @author Sergio González
 */
public class SmallImageSelectorAction extends BaseImageSelectorAction {

	@Override
	public void validateFile(
			String fileName, String contentType, long size)
		throws PortalException {

		long smallImageMaxFileSize = PrefsPropsUtil.getLong(
			PropsKeys.BLOGS_IMAGE_SMALL_MAX_SIZE);

		if (size > smallImageMaxFileSize) {
			throw new CoverImageSizeException();
		}

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
			"Invalid cover image for fileName " + fileName);
	}

	@Override
	public void checkPermission(
			long groupId, PermissionChecker permissionChecker)
		throws PortalException {

		boolean containsResourcePermission =
			ResourcePermissionCheckerUtil.containsResourcePermission(
				permissionChecker, BlogsPermission.RESOURCE_NAME, groupId,
				ActionKeys.ADD_ENTRY);

		if (!containsResourcePermission) {
			throw new PrincipalException();
		}
	}

}
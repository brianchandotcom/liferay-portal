/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class IGImagePermission {

	public static void check(
			PermissionChecker permissionChecker, IGImage image, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, image, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long imageId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, imageId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, IGImage image, String actionId)
		throws PortalException, SystemException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, image.getGroupId(), IGImage.class.getName(),
			image.getImageId(), PortletKeys.IMAGE_GALLERY_DISPLAY, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
			if (image.getFolderId() !=
					IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				IGFolder folder = image.getFolder();

				if (!IGFolderPermission.contains(
						permissionChecker, folder, ActionKeys.ACCESS) &&
					!IGFolderPermission.contains(
						permissionChecker, folder, ActionKeys.VIEW)) {

					return false;
				}
			}
		}

		if (permissionChecker.hasOwnerPermission(
				image.getCompanyId(), IGImage.class.getName(),
				image.getImageId(), image.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			image.getGroupId(), IGImage.class.getName(), image.getImageId(),
			actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long imageId, String actionId)
		throws PortalException, SystemException {

		IGImage image = IGImageLocalServiceUtil.getImage(imageId);

		return contains(permissionChecker, image, actionId);
	}

}
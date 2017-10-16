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

package com.liferay.knowledge.base.service.permission;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {"model.class.name=com.liferay.knowledge.base.model.KBArticle"},
	service = BaseModelPermissionChecker.class
)
public class KBArticlePermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, KBArticle kbArticle,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kbArticle, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, classPK, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, KBArticle kbArticle,
			String actionId)
		throws PortalException {

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			long parentResourceClassNameId =
				kbArticle.getParentResourceClassNameId();
			long parentResourcePrimKey = kbArticle.getParentResourcePrimKey();

			long kbFolderClassNameId = PortalUtil.getClassNameId(
				KBFolderConstants.getClassName());

			if ((parentResourcePrimKey ==
					KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) ||
				(parentResourceClassNameId == kbFolderClassNameId)) {

				if (!KBFolderPermission.contains(
						permissionChecker, kbArticle.getGroupId(),
						parentResourcePrimKey, actionId)) {

					return false;
				}
			}
			else {
				KBArticle parentKBArticle =
					KBArticleLocalServiceUtil.getKBArticle(
						parentResourcePrimKey);

				if (!contains(permissionChecker, parentKBArticle, actionId)) {
					return false;
				}
			}
		}

		if (permissionChecker.hasOwnerPermission(
				kbArticle.getCompanyId(), KBArticle.class.getName(),
				kbArticle.getRootResourcePrimKey(), kbArticle.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			kbArticle.getGroupId(), KBArticle.class.getName(),
			kbArticle.getRootResourcePrimKey(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		KBArticle kbArticle = KBArticleLocalServiceUtil.fetchLatestKBArticle(
			classPK, WorkflowConstants.STATUS_ANY);

		if (kbArticle == null) {
			kbArticle = KBArticleLocalServiceUtil.getKBArticle(classPK);
		}

		return contains(permissionChecker, kbArticle, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

}
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

package com.liferay.message.boards.internal.service.permission;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.message.boards.kernel.exception.NoSuchCategoryException;
import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.service.permission.MBPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.message.boards.kernel.model.MBCategory"
	},
	service = BaseModelPermissionChecker.class
)
public class MBCategoryPermission implements BaseModelPermissionChecker {

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		if (!_contains(permissionChecker, groupId, primaryKey, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MBCategory.class.getName(), primaryKey,
				actionId);
		}
	}

	private boolean _contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException {

		if (_mbBanLocalService.hasBan(groupId, permissionChecker.getUserId())) {
			return false;
		}

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return MBPermission.contains(permissionChecker, groupId, actionId);
		}

		MBCategory category = _mbCategoryLocalService.getCategory(categoryId);

		if (actionId.equals(ActionKeys.ADD_CATEGORY)) {
			actionId = ActionKeys.ADD_SUBCATEGORY;
		}

		String portletId = PortletProviderUtil.getPortletId(
			MBCategory.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = _stagingPermission.hasPermission(
			permissionChecker, category.getGroupId(),
			MBCategory.class.getName(), category.getCategoryId(), portletId,
			actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			try {
				while (categoryId !=
							MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

					category = _mbCategoryLocalService.getCategory(categoryId);

					if (!_hasPermission(
							permissionChecker, category, actionId)) {

						return false;
					}

					categoryId = category.getParentCategoryId();
				}
			}
			catch (NoSuchCategoryException nsce) {
				if (!category.isInTrash()) {
					throw nsce;
				}
			}

			return MBPermission.contains(
				permissionChecker, category.getGroupId(), actionId);
		}

		return _hasPermission(permissionChecker, category, actionId);
	}

	private boolean _hasPermission(
		PermissionChecker permissionChecker, MBCategory category,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				category.getCompanyId(), MBCategory.class.getName(),
				category.getCategoryId(), category.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				category.getGroupId(), MBCategory.class.getName(),
				category.getCategoryId(), actionId)) {

			return true;
		}

		return false;
	}

	@Reference
	private MBBanLocalService _mbBanLocalService;

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

	@Reference
	private StagingPermission _stagingPermission;

}
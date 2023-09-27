/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.permission.CPOptionCategoryPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPOptionCategory",
	service = ModelResourcePermission.class
)
public class CPOptionCategoryModelResourcePermission
	implements ModelResourcePermission<CPOptionCategory> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CPOptionCategory cpOptionCategory, String actionId)
		throws PortalException {

		cpOptionCategoryPermission.check(
			permissionChecker, cpOptionCategory, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpOptionCategoryId,
			String actionId)
		throws PortalException {

		cpOptionCategoryPermission.check(
			permissionChecker, cpOptionCategoryId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CPOptionCategory cpOptionCategory, String actionId)
		throws PortalException {

		return cpOptionCategoryPermission.contains(
			permissionChecker, cpOptionCategory, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpOptionCategoryId,
			String actionId)
		throws PortalException {

		return cpOptionCategoryPermission.contains(
			permissionChecker, cpOptionCategoryId, actionId);
	}

	@Override
	public String getModelName() {
		return CPOptionCategory.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CPOptionCategoryPermission cpOptionCategoryPermission;

	@Reference(
		target = "(resource.name=" + CPConstants.RESOURCE_NAME_PRODUCT + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}
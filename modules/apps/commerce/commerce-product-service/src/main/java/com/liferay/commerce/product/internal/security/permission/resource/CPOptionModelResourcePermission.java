/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.permission.CPOptionPermission;
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
	property = "model.class.name=com.liferay.commerce.product.model.CPOption",
	service = ModelResourcePermission.class
)
public class CPOptionModelResourcePermission
	implements ModelResourcePermission<CPOption> {

	@Override
	public void check(
			PermissionChecker permissionChecker, CPOption cpOption,
			String actionId)
		throws PortalException {

		cpOptionPermission.check(permissionChecker, cpOption, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpOptionId,
			String actionId)
		throws PortalException {

		cpOptionPermission.check(permissionChecker, cpOptionId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, CPOption cpOption,
			String actionId)
		throws PortalException {

		return cpOptionPermission.contains(
			permissionChecker, cpOption, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpOptionId,
			String actionId)
		throws PortalException {

		return cpOptionPermission.contains(
			permissionChecker, cpOptionId, actionId);
	}

	@Override
	public String getModelName() {
		return CPOption.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CPOptionPermission cpOptionPermission;

	@Reference(
		target = "(resource.name=" + CPConstants.RESOURCE_NAME_PRODUCT + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}
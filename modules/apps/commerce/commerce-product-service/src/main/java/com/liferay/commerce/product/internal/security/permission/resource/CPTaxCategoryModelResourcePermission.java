/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tancredi Covioli
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPTaxCategory",
	service = ModelResourcePermission.class
)
public class CPTaxCategoryModelResourcePermission
	implements ModelResourcePermission<CPTaxCategory> {

	@Override
	public void check(
			PermissionChecker permissionChecker, CPTaxCategory cpTaxCategory,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpTaxCategory, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPTaxCategory.class.getName(),
				cpTaxCategory.getCPTaxCategoryId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpTaxCategoryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpTaxCategoryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPTaxCategory.class.getName(),
				cpTaxCategoryId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, CPTaxCategory cpTaxCategory,
		String actionId) {

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin(cpTaxCategory.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				cpTaxCategory.getCompanyId(), CPTaxCategory.class.getName(),
				cpTaxCategory.getCPTaxCategoryId(), cpTaxCategory.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			0, CPTaxCategory.class.getName(),
			cpTaxCategory.getCPTaxCategoryId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpTaxCategoryId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_cpTaxCategoryLocalService.getCPTaxCategory(cpTaxCategoryId),
			actionId);
	}

	@Override
	public String getModelName() {
		return CPTaxCategory.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;

	@Reference(target = "(resource.name=" + CPConstants.RESOURCE_NAME_TAX + ")")
	private PortletResourcePermission _portletResourcePermission;

}
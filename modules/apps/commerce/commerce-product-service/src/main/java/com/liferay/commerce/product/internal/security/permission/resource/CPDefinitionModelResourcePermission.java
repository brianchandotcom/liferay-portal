/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.permission.CommerceCatalogPermission;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPDefinition",
	service = ModelResourcePermission.class
)
public class CPDefinitionModelResourcePermission
	implements ModelResourcePermission<CPDefinition> {

	@Override
	public void check(
			PermissionChecker permissionChecker, CPDefinition cpDefinition,
			String actionId)
		throws PortalException {

		commerceCatalogPermission.check(
			permissionChecker, cpDefinition.getCommerceCatalog(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpDefinitionId,
			String actionId)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		commerceCatalogPermission.check(
			permissionChecker, cpDefinition.getCommerceCatalog(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, CPDefinition cpDefinition,
			String actionId)
		throws PortalException {

		return commerceCatalogPermission.contains(
			permissionChecker, cpDefinition.getCommerceCatalog(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpDefinitionId,
			String actionId)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		return commerceCatalogPermission.contains(
			permissionChecker, cpDefinition.getCommerceCatalog(), actionId);
	}

	@Override
	public String getModelName() {
		return CPDefinition.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceCatalogPermission commerceCatalogPermission;

	@Reference
	protected CPDefinitionLocalService cpDefinitionLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.permission.CommerceCatalogPermission;
import com.liferay.commerce.product.service.CPConfigurationListLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPConfigurationList",
	service = ModelResourcePermission.class
)
public class CPConfigurationListModelResourcePermission
	implements ModelResourcePermission<CPConfigurationList> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CPConfigurationList cpConfigurationList, String actionId)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationList.getGroupId());

		commerceCatalogPermission.check(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpConfigurationListId,
			String actionId)
		throws PortalException {

		CPConfigurationList cpConfigurationList =
			cpConfigurationListLocalService.getCPConfigurationList(
				cpConfigurationListId);

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationList.getGroupId());

		commerceCatalogPermission.check(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CPConfigurationList cpConfigurationList, String actionId)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationList.getGroupId());

		return commerceCatalogPermission.contains(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpConfigurationListId,
			String actionId)
		throws PortalException {

		CPConfigurationList cpConfigurationList =
			cpConfigurationListLocalService.getCPConfigurationList(
				cpConfigurationListId);

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationList.getGroupId());

		return commerceCatalogPermission.contains(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public String getModelName() {
		return CPConfigurationList.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceCatalogLocalService commerceCatalogLocalService;

	@Reference
	protected CommerceCatalogPermission commerceCatalogPermission;

	@Reference
	protected CPConfigurationListLocalService cpConfigurationListLocalService;

}
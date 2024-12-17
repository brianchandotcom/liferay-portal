/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPConfigurationListRel;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.permission.CommerceCatalogPermission;
import com.liferay.commerce.product.service.CPConfigurationListLocalService;
import com.liferay.commerce.product.service.CPConfigurationListRelLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPConfigurationListRel",
	service = ModelResourcePermission.class
)
public class CPConfigurationListRelModelResourcePermission
	implements ModelResourcePermission<CPConfigurationListRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CPConfigurationListRel cpConfigurationListRel, String actionId)
		throws PortalException {

		CPConfigurationList cpConfigurationList =
			cpConfigurationListLocalService.getCPConfigurationList(
				cpConfigurationListRel.getCPConfigurationListId());

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationList.getGroupId());

		commerceCatalogPermission.check(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpConfigurationListRelId,
			String actionId)
		throws PortalException {

		CPConfigurationListRel cpConfigurationListRel =
			cpConfigurationListRelLocalService.getCPConfigurationListRel(
				cpConfigurationListRelId);

		CPConfigurationList cpConfigurationList =
			cpConfigurationListLocalService.getCPConfigurationList(
				cpConfigurationListRel.getCPConfigurationListId());

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationList.getGroupId());

		commerceCatalogPermission.check(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CPConfigurationListRel cpConfigurationListRel, String actionId)
		throws PortalException {

		CPConfigurationList cpConfigurationList =
			cpConfigurationListLocalService.getCPConfigurationList(
				cpConfigurationListRel.getCPConfigurationListId());

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationList.getGroupId());

		return commerceCatalogPermission.contains(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpConfigurationListRelId,
			String actionId)
		throws PortalException {

		CPConfigurationListRel cpConfigurationListRel =
			cpConfigurationListRelLocalService.getCPConfigurationListRel(
				cpConfigurationListRelId);

		CPConfigurationList cpConfigurationList =
			cpConfigurationListLocalService.getCPConfigurationList(
				cpConfigurationListRel.getCPConfigurationListId());

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

	@Reference
	protected CPConfigurationListRelLocalService
		cpConfigurationListRelLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.permission.CommerceCatalogPermission;
import com.liferay.commerce.product.service.CPConfigurationEntryLocalService;
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
	property = "model.class.name=com.liferay.commerce.product.model.CPConfigurationEntry",
	service = ModelResourcePermission.class
)
public class CPConfigurationEntryModelResourcePermission
	implements ModelResourcePermission<CPConfigurationEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CPConfigurationEntry cpConfigurationEntry, String actionId)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationEntry.getGroupId());

		commerceCatalogPermission.check(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpConfigurationEntryId,
			String actionId)
		throws PortalException {

		CPConfigurationEntry cpConfigurationEntry =
			cpConfigurationEntryLocalService.getCPConfigurationEntry(
				cpConfigurationEntryId);

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationEntry.getGroupId());

		commerceCatalogPermission.check(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CPConfigurationEntry cpConfigurationEntry, String actionId)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationEntry.getGroupId());

		return commerceCatalogPermission.contains(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpConfigurationEntryId,
			String actionId)
		throws PortalException {

		CPConfigurationEntry cpConfigurationEntry =
			cpConfigurationEntryLocalService.getCPConfigurationEntry(
				cpConfigurationEntryId);

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpConfigurationEntry.getGroupId());

		return commerceCatalogPermission.contains(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public String getModelName() {
		return CPConfigurationEntry.class.getName();
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
	protected CPConfigurationEntryLocalService cpConfigurationEntryLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.permission.CommerceCatalogPermission;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPInstanceUnitOfMeasure",
	service = ModelResourcePermission.class
)
public class CPInstanceUnitOfMeasureModelResourcePermission
	implements ModelResourcePermission<CPInstanceUnitOfMeasure> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, String actionId)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceUnitOfMeasure.getCPInstanceId());

		commerceCatalogPermission.check(
			permissionChecker, cpInstance.getCommerceCatalog(), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpInstanceUnitOfMeasureId,
			String actionId)
		throws PortalException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasure(
				cpInstanceUnitOfMeasureId);

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceUnitOfMeasure.getCPInstanceId());

		commerceCatalogPermission.check(
			permissionChecker, cpInstance.getCommerceCatalog(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, String actionId)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceUnitOfMeasure.getCPInstanceId());

		return commerceCatalogPermission.contains(
			permissionChecker, cpInstance.getCommerceCatalog(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpInstanceUnitOfMeasureId,
			String actionId)
		throws PortalException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasure(
				cpInstanceUnitOfMeasureId);

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceUnitOfMeasure.getCPInstanceId());

		return commerceCatalogPermission.contains(
			permissionChecker, cpInstance.getCommerceCatalog(), actionId);
	}

	@Override
	public String getModelName() {
		return CPInstanceUnitOfMeasure.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected CommerceCatalogPermission commerceCatalogPermission;

	@Reference
	protected CPInstanceLocalService cpInstanceLocalService;

	@Reference
	protected CPInstanceUnitOfMeasureLocalService
		cpInstanceUnitOfMeasureLocalService;

}
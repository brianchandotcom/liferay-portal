/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.base.CommerceRegionServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
public class CommerceRegionServiceImpl extends CommerceRegionServiceBaseImpl {

	@Override
	public CommerceRegion addCommerceRegion(
			long commerceCountryId, String name, String code, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommerceCountry commerceCountry =
			commerceCountryPersistence.findByPrimaryKey(commerceCountryId);

		CommercePermission.check(
			getPermissionChecker(), commerceCountry.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceRegionLocalService.addCommerceRegion(
			commerceCountry.getCommerceCountryId(), name, code, priority,
			active, serviceContext);
	}

	@Override
	public void deleteCommerceRegion(long commerceRegionId)
		throws PortalException {

		CommerceRegion commerceRegion =
			commerceRegionPersistence.findByPrimaryKey(commerceRegionId);

		CommercePermission.check(
			getPermissionChecker(), commerceRegion.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		commerceRegionLocalService.deleteCommerceRegion(commerceRegion);
	}

	@Override
	public CommerceRegion getCommerceRegion(long commerceRegionId)
		throws PortalException {

		return commerceRegionLocalService.getCommerceRegion(commerceRegionId);
	}

	@Override
	public List<CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active) {

		return commerceRegionLocalService.getCommerceRegions(
			commerceCountryId, active);
	}

	@Override
	public List<CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {

		return commerceRegionLocalService.getCommerceRegions(
			commerceCountryId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceRegion> getCommerceRegions(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {

		return commerceRegionLocalService.getCommerceRegions(
			commerceCountryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceRegionsCount(long commerceCountryId) {
		return commerceRegionLocalService.getCommerceRegionsCount(
			commerceCountryId);
	}

	@Override
	public int getCommerceRegionsCount(long commerceCountryId, boolean active) {
		return commerceRegionLocalService.getCommerceRegionsCount(
			commerceCountryId, active);
	}

	@Override
	public CommerceRegion updateCommerceRegion(
			long commerceRegionId, String name, String code, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommerceRegion commerceRegion =
			commerceRegionPersistence.findByPrimaryKey(commerceRegionId);

		CommercePermission.check(
			getPermissionChecker(), commerceRegion.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceRegionLocalService.updateCommerceRegion(
			commerceRegion.getCommerceRegionId(), name, code, priority, active,
			serviceContext);
	}

}
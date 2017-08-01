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

package com.liferay.commerce.address.service.impl;

import com.liferay.commerce.address.model.CommerceRegion;
import com.liferay.commerce.address.service.base.CommerceRegionServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceRegionServiceImpl extends CommerceRegionServiceBaseImpl {

	@Override
	public CommerceRegion addCommerceRegion(
			long commerceCountryId, String name, String abbreviation,
			double priority, boolean published, ServiceContext serviceContext)
		throws PortalException {

		return commerceRegionLocalService.addCommerceRegion(
			commerceCountryId, name, abbreviation, priority, published,
			serviceContext);
	}

	@Override
	public CommerceRegion deleteCommerceRegion(long commerceRegionId)
		throws PortalException {

		return commerceRegionLocalService.deleteCommerceRegion(
			commerceRegionId);
	}

	@Override
	public CommerceRegion fetchCommerceRegion(long commerceRegionId) {
		return commerceRegionLocalService.fetchCommerceRegion(commerceRegionId);
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
	public CommerceRegion updateCommerceRegion(
			long commerceRegionId, String name, String abbreviation,
			double priority, boolean published)
		throws PortalException {

		return commerceRegionLocalService.updateCommerceRegion(
			commerceRegionId, name, abbreviation, priority, published);
	}

}
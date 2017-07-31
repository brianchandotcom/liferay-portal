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
import com.liferay.commerce.address.service.base.CommerceRegionLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceRegionLocalServiceImpl
	extends CommerceRegionLocalServiceBaseImpl {

	@Override
	public CommerceRegion addCommerceRegion(
			long commerceCountryId, String name, String abbreviation,
			int priority, boolean published, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceRegionId = counterLocalService.increment();

		CommerceRegion commerceRegion = commerceRegionPersistence.create(
			commerceRegionId);

		commerceRegion.setGroupId(groupId);
		commerceRegion.setCompanyId(user.getCompanyId());
		commerceRegion.setUserId(user.getUserId());
		commerceRegion.setUserName(user.getFullName());
		commerceRegion.setCommerceCountryId(commerceCountryId);
		commerceRegion.setName(name);
		commerceRegion.setAbbreviation(abbreviation);
		commerceRegion.setPriority(priority);
		commerceRegion.setPublished(published);

		commerceRegionPersistence.update(commerceRegion);

		return commerceRegion;
	}

	@Override
	public CommerceRegion deleteCommerceRegion(CommerceRegion commerceRegion) {
		return commerceRegionPersistence.remove(commerceRegion);
	}

	@Override
	public CommerceRegion deleteCommerceRegion(long commerceRegionId)
		throws PortalException {

		CommerceRegion commerceRegion =
			commerceRegionPersistence.findByPrimaryKey(commerceRegionId);

		return commerceRegionLocalService.deleteCommerceRegion(commerceRegion);
	}

	@Override
	public void deleteCommerceRegions(long commerceCountryId) {
		List<CommerceRegion> commerceRegions =
			commerceRegionPersistence.findByCommerceCountryId(
				commerceCountryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceRegion commerceRegion : commerceRegions) {
			deleteCommerceRegion(commerceRegion);
		}
	}

	@Override
	public List<CommerceRegion> getCommerceRegions(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {

		return commerceRegionPersistence.findByCommerceCountryId(
			commerceCountryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceRegionsCount(long commerceCountryId) {
		return commerceRegionPersistence.countByCommerceCountryId(
			commerceCountryId);
	}

	@Override
	public CommerceRegion updateCommerceRegion(
			long commerceRegionId, String name, String abbreviation,
			int priority, boolean published)
		throws PortalException {

		CommerceRegion commerceRegion =
			commerceRegionPersistence.findByPrimaryKey(commerceRegionId);

		commerceRegion.setName(name);
		commerceRegion.setAbbreviation(abbreviation);
		commerceRegion.setPriority(priority);
		commerceRegion.setPublished(published);

		commerceRegionPersistence.update(commerceRegion);

		return commerceRegion;
	}

}
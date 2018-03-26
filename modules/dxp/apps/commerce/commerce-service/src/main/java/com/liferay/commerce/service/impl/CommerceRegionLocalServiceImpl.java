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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceRegionNameException;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.base.CommerceRegionLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
public class CommerceRegionLocalServiceImpl
	extends CommerceRegionLocalServiceBaseImpl {

	@Override
	public CommerceRegion addCommerceRegion(
			long commerceCountryId, String name, String code, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommerceCountry commerceCountry =
			commerceCountryPersistence.findByPrimaryKey(commerceCountryId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		validate(name);

		long commerceRegionId = counterLocalService.increment();

		CommerceRegion commerceRegion = commerceRegionPersistence.create(
			commerceRegionId);

		commerceRegion.setUuid(serviceContext.getUuid());
		commerceRegion.setGroupId(commerceCountry.getGroupId());
		commerceRegion.setCompanyId(user.getCompanyId());
		commerceRegion.setUserId(user.getUserId());
		commerceRegion.setUserName(user.getFullName());
		commerceRegion.setCommerceCountryId(commerceCountryId);
		commerceRegion.setName(name);
		commerceRegion.setCode(code);
		commerceRegion.setPriority(priority);
		commerceRegion.setActive(active);

		commerceRegionPersistence.update(commerceRegion);

		return commerceRegion;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceRegion deleteCommerceRegion(CommerceRegion commerceRegion)
		throws PortalException {

		// Commerce region

		commerceRegionPersistence.remove(commerceRegion);

		// Commerce addresses

		commerceAddressLocalService.deleteRegionCommerceAddresses(
			commerceRegion.getCommerceRegionId());

		return commerceRegion;
	}

	@Override
	public CommerceRegion deleteCommerceRegion(long commerceRegionId)
		throws PortalException {

		CommerceRegion commerceRegion =
			commerceRegionPersistence.findByPrimaryKey(commerceRegionId);

		return commerceRegionLocalService.deleteCommerceRegion(commerceRegion);
	}

	@Override
	public void deleteCommerceRegions(long commerceCountryId)
		throws PortalException {

		List<CommerceRegion> commerceRegions =
			commerceRegionPersistence.findByCommerceCountryId(
				commerceCountryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceRegion commerceRegion : commerceRegions) {
			deleteCommerceRegion(commerceRegion);
		}
	}

	@Override
	public List<CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active) {

		return commerceRegionPersistence.findByC_A(commerceCountryId, active);
	}

	@Override
	public List<CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {

		return commerceRegionPersistence.findByC_A(
			commerceCountryId, active, start, end, orderByComparator);
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
	public int getCommerceRegionsCount(long commerceCountryId, boolean active) {
		return commerceRegionPersistence.countByC_A(commerceCountryId, active);
	}

	@Override
	public CommerceRegion updateCommerceRegion(
			long commerceRegionId, String name, String code, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommerceRegion commerceRegion =
			commerceRegionPersistence.findByPrimaryKey(commerceRegionId);

		validate(name);

		commerceRegion.setName(name);
		commerceRegion.setCode(code);
		commerceRegion.setPriority(priority);
		commerceRegion.setActive(active);

		commerceRegionPersistence.update(commerceRegion);

		return commerceRegion;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new CommerceRegionNameException();
		}
	}

}
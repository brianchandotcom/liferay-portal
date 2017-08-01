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

import com.liferay.commerce.address.model.CommerceCountry;
import com.liferay.commerce.address.service.base.CommerceCountryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCountryLocalServiceImpl
	extends CommerceCountryLocalServiceBaseImpl {

	@Override
	public CommerceCountry addCommerceCountry(
			String name, boolean allowsBilling, boolean allowsShipping,
			String twoLettersISOCode, String threeLettersISOCode,
			int numericISOCode, boolean subjectToVAT, double priority,
			boolean published, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceCountryId = counterLocalService.increment();

		CommerceCountry commerceCountry = commerceCountryPersistence.create(
			commerceCountryId);

		commerceCountry.setGroupId(groupId);
		commerceCountry.setCompanyId(user.getCompanyId());
		commerceCountry.setUserId(user.getUserId());
		commerceCountry.setUserName(user.getFullName());
		commerceCountry.setName(name);
		commerceCountry.setAllowsBilling(allowsBilling);
		commerceCountry.setAllowsShipping(allowsShipping);
		commerceCountry.setTwoLettersISOCode(twoLettersISOCode);
		commerceCountry.setThreeLettersISOCode(threeLettersISOCode);
		commerceCountry.setNumericISOCode(numericISOCode);
		commerceCountry.setSubjectToVAT(subjectToVAT);
		commerceCountry.setPriority(priority);
		commerceCountry.setPublished(published);

		commerceCountryPersistence.update(commerceCountry);

		return commerceCountry;
	}

	@Override
	public CommerceCountry deleteCommerceCountry(
		CommerceCountry commerceCountry) {

		// Commerce country

		commerceCountryPersistence.remove(commerceCountry);

		//Commerce regions

		commerceRegionLocalService.deleteCommerceRegions(
			commerceCountry.getCommerceCountryId());

		return commerceCountry;
	}

	@Override
	public CommerceCountry deleteCommerceCountry(long commerceCountryId)
		throws PortalException {

		CommerceCountry commerceCountry =
			commerceCountryPersistence.findByPrimaryKey(commerceCountryId);

		return commerceCountryLocalService.deleteCommerceCountry(
			commerceCountry);
	}

	@Override
	public List<CommerceCountry> getCommerceCountries(
		int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return commerceCountryPersistence.findAll(
			start, end, orderByComparator);
	}

	@Override
	public CommerceCountry updateCommerceCountry(
			long commerceCountryId, String name, boolean allowsBilling,
			boolean allowsShipping, String twoLettersISOCode,
			String threeLettersISOCode, int numericISOCode,
			boolean subjectToVAT, double priority, boolean published)
		throws PortalException {

		CommerceCountry commerceCountry =
			commerceCountryPersistence.findByPrimaryKey(commerceCountryId);

		commerceCountry.setName(name);
		commerceCountry.setAllowsBilling(allowsBilling);
		commerceCountry.setAllowsShipping(allowsShipping);
		commerceCountry.setTwoLettersISOCode(twoLettersISOCode);
		commerceCountry.setThreeLettersISOCode(threeLettersISOCode);
		commerceCountry.setNumericISOCode(numericISOCode);
		commerceCountry.setSubjectToVAT(subjectToVAT);
		commerceCountry.setPriority(priority);
		commerceCountry.setPublished(published);

		commerceCountryPersistence.update(commerceCountry);

		return commerceCountry;
	}

}
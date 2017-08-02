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
import com.liferay.commerce.address.service.base.CommerceCountryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCountryServiceImpl extends CommerceCountryServiceBaseImpl {

	@Override
	public CommerceCountry addCommerceCountry(
			String name, boolean billingAllowed, boolean shippingAllowed,
			String twoLettersISOCode, String threeLettersISOCode,
			int numericISOCode, boolean subjectToVAT, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		return commerceCountryLocalService.addCommerceCountry(
			name, billingAllowed, shippingAllowed, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, subjectToVAT, priority, active,
			serviceContext);
	}

	@Override
	public CommerceCountry deleteCommerceCountry(long commerceCountryId)
		throws PortalException {

		return commerceCountryLocalService.deleteCommerceCountry(
			commerceCountryId);
	}

	@Override
	public CommerceCountry fetchCommerceCountry(long commerceCountryId) {
		return commerceCountryLocalService.fetchCommerceCountry(
			commerceCountryId);
	}

	@Override
	public List<CommerceCountry> getCommerceCountries(
		int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return commerceCountryLocalService.getCommerceCountries(
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceCountriesCount() {
		return commerceCountryLocalService.getCommerceCountriesCount();
	}

	@Override
	public CommerceCountry updateCommerceCountry(
			long commerceCountryId, String name, boolean billingAllowed,
			boolean shippingAllowed, String twoLettersISOCode,
			String threeLettersISOCode, int numericISOCode,
			boolean subjectToVAT, double priority, boolean active)
		throws PortalException {

		return commerceCountryLocalService.updateCommerceCountry(
			commerceCountryId, name, billingAllowed, shippingAllowed,
			twoLettersISOCode, threeLettersISOCode, numericISOCode,
			subjectToVAT, priority, active);
	}

}
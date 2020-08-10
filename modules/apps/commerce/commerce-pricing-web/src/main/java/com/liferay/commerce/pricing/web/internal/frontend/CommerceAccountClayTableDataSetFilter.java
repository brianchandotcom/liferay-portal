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

package com.liferay.commerce.pricing.web.internal.frontend;

import com.liferay.commerce.frontend.clay.data.set.ClayAutocompleteDataSetFilter;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetFilter;
import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.data.set.display.name=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_DISCOUNTS,
		"commerce.data.set.display.name=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRICE_LISTS
	},
	service = ClayDataSetFilter.class
)
public class CommerceAccountClayTableDataSetFilter
	extends ClayAutocompleteDataSetFilter {

	@Override
	public String getApiURL() {
		return "/o/headless-commerce-admin-account/v1.0/accounts?sort=name:asc";
	}

	@Override
	public String getId() {
		return "accountId";
	}

	@Override
	public String getItemKey() {
		return "id";
	}

	@Override
	public String getItemLabel() {
		return "name";
	}

	@Override
	public String getLabel() {
		return "account";
	}

	@Override
	public boolean isMultipleSelection() {
		return true;
	}

}
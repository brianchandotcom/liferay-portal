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

package com.liferay.commerce.pricing.web.internal.frontend.data.set.filter;

import com.liferay.commerce.pricing.web.internal.constants.CommercePricingFDSNames;
import com.liferay.frontend.data.set.filter.BaseAutocompleteFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"frontend.data.set.name=" + CommercePricingFDSNames.DISCOUNTS,
		"frontend.data.set.name=" + CommercePricingFDSNames.PRICE_LISTS
	},
	service = FDSFilter.class
)
public class CommerceChannelFDSFilter extends BaseAutocompleteFDSFilter {

	@Override
	public String getAPIURL() {
		return "/o/headless-commerce-admin-channel/v1.0/channels?sort=name:asc";
	}

	@Override
	public String getId() {
		return "channelId";
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
		return "channel";
	}

	@Override
	public boolean isMultipleSelection() {
		return true;
	}

}
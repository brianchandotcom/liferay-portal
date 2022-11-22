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

package com.liferay.commerce.warehouse.web.internal.frontend.data.set.filter;

import com.liferay.commerce.warehouse.web.internal.constants.CommerceInventoryWarehouseFDSNames;
import com.liferay.frontend.data.set.filter.BaseAutocompleteFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "frontend.data.set.name=" + CommerceInventoryWarehouseFDSNames.COMMERCE_INVENTORY_WAREHOUSE_QUALIFIER_CHANNELS,
	service = FDSFilter.class
)
public class CommerceInventoryWarehouseChannelAutocompleteFDSFilter
	extends BaseAutocompleteFDSFilter {

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
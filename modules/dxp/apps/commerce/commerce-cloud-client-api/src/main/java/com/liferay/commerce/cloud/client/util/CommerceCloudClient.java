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

package com.liferay.commerce.cloud.client.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cloud.client.exception.CommerceCloudClientException;
import com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public interface CommerceCloudClient {

	public void addCommerceForecastEntries(String json)
		throws CommerceCloudClientException;

	public JSONObject getOrderForecastConfiguration()
		throws CommerceCloudClientException;

	public void syncOrders(
			List<CommerceCloudForecastOrder> commerceCloudForecastOrders)
		throws CommerceCloudClientException;

	public void updateOrderForecastConfiguration(JSONObject jsonObject)
		throws CommerceCloudClientException;

}
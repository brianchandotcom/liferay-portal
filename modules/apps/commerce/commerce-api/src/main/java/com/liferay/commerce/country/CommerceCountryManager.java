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

package com.liferay.commerce.country;

import com.liferay.portal.kernel.model.Country;

import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public interface CommerceCountryManager {

	public List<Country> getBillingCountries(
		long companyId, boolean active, boolean billingAllowed);

	public List<Country> getBillingCountriesByChannelId(
		long channelId, int start, int end);

	public List<Country> getShippingCountries(
		long companyId, boolean active, boolean shippingAllowed);

	public List<Country> getShippingCountriesByChannelId(
		long channelId, int start, int end);

	public List<Country> getWarehouseCountries(long companyId, boolean all);

}
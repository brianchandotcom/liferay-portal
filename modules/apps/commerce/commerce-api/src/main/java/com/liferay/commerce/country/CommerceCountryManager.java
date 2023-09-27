/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
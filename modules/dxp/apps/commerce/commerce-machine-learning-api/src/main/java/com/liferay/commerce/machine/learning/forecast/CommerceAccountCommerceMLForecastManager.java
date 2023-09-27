/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.forecast;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;
import java.util.List;

/**
 * @author Riccardo Ferrari
 */
@ProviderType
public interface CommerceAccountCommerceMLForecastManager {

	public CommerceAccountCommerceMLForecast
			addCommerceAccountCommerceMLForecast(
				CommerceAccountCommerceMLForecast
					commerceAccountCommerceMLForecast)
		throws PortalException;

	public CommerceAccountCommerceMLForecast create();

	public CommerceAccountCommerceMLForecast
			getCommerceAccountCommerceMLForecast(
				long companyId, long forecastId)
		throws PortalException;

	public List<CommerceAccountCommerceMLForecast>
			getMonthlyRevenueCommerceAccountCommerceMLForecasts(
				long companyId, long[] commerceAccountIds, Date actualDate,
				int historyLength, int forecastLength)
		throws PortalException;

	public List<CommerceAccountCommerceMLForecast>
			getMonthlyRevenueCommerceAccountCommerceMLForecasts(
				long companyId, long[] commerceAccountIds, Date actualDate,
				int historyLength, int forecastLength, int start, int end)
		throws PortalException;

	public long getMonthlyRevenueCommerceAccountCommerceMLForecastsCount(
			long companyId, long[] commerceAccountIds, Date actualDate,
			int historyLength, int forecastLength)
		throws PortalException;

}
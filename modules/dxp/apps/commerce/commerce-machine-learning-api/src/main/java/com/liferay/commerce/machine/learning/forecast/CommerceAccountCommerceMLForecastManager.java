/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
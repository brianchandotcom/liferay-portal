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
public interface SkuCommerceMLForecastManager {

	public SkuCommerceMLForecast addSkuCommerceMLForecast(
			SkuCommerceMLForecast skuCommerceMLForecast)
		throws PortalException;

	public SkuCommerceMLForecast create();

	public List<SkuCommerceMLForecast> getMonthlyQuantitySkuCommerceMLForecasts(
			long companyId, String sku, Date actualDate, int historyLength,
			int forecastLength)
		throws PortalException;

	public List<SkuCommerceMLForecast> getMonthlyQuantitySkuCommerceMLForecasts(
			long companyId, String sku, Date actualDate, int historyLength,
			int forecastLength, int start, int end)
		throws PortalException;

	public List<SkuCommerceMLForecast> getMonthlyQuantitySkuCommerceMLForecasts(
			long companyId, String[] skus, Date actualDate, int historyLength,
			int forecastLength)
		throws PortalException;

	public List<SkuCommerceMLForecast> getMonthlyQuantitySkuCommerceMLForecasts(
			long companyId, String[] skus, Date actualDate, int historyLength,
			int forecastLength, int start, int end)
		throws PortalException;

	public long getMonthlyQuantitySkuCommerceMLForecastsCount(
			long companyId, String sku, Date actualDate, int historyLength,
			int forecastLength)
		throws PortalException;

	public long getMonthlyQuantitySkuCommerceMLForecastsCount(
			long companyId, String[] skus, Date actualDate, int historyLength,
			int forecastLength)
		throws PortalException;

	public SkuCommerceMLForecast getSkuCommerceMLForecast(
			long companyId, long forecastId)
		throws PortalException;

}
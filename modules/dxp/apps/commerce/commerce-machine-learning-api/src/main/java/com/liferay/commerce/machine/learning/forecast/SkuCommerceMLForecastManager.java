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
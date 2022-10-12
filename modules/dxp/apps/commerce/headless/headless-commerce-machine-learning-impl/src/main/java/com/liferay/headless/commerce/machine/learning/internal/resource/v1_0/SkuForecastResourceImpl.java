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

package com.liferay.headless.commerce.machine.learning.internal.resource.v1_0;

import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecastManager;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.SkuForecast;
import com.liferay.headless.commerce.machine.learning.internal.constants.CommerceMLForecastConstants;
import com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter.SkuForecastDTOConverter;
import com.liferay.headless.commerce.machine.learning.resource.v1_0.SkuForecastResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sku-forecast.properties",
	scope = ServiceScope.PROTOTYPE, service = SkuForecastResource.class
)
public class SkuForecastResourceImpl extends BaseSkuForecastResourceImpl {

	@Override
	public Page<SkuForecast> getSkuForecastsByMonthlyRevenuePage(
			Integer forecastLength, Date forecastStartDate,
			Integer historyLength, String[] skus, Pagination pagination)
		throws Exception {

		Date startDate = forecastStartDate;

		if (startDate == null) {
			startDate = new Date();
		}

		if (historyLength == null) {
			historyLength = CommerceMLForecastConstants.HISTORY_LENGTH_DEFAULT;
		}

		if (forecastLength == null) {
			forecastLength =
				CommerceMLForecastConstants.FORECAST_LENGTH_DEFAULT;
		}

		return Page.of(
			transform(
				_skuCommerceMLForecastManager.
					getMonthlyQuantitySkuCommerceMLForecasts(
						contextCompany.getCompanyId(), skus, startDate,
						historyLength, forecastLength,
						pagination.getStartPosition(),
						pagination.getEndPosition()),
				skuCommerceMLForecast -> _skuForecastDTOConverter.toDTO(
					skuCommerceMLForecast)),
			pagination,
			_skuCommerceMLForecastManager.
				getMonthlyQuantitySkuCommerceMLForecastsCount(
					contextCompany.getCompanyId(), skus, startDate,
					historyLength, forecastLength));
	}

	@Reference
	private SkuCommerceMLForecastManager _skuCommerceMLForecastManager;

	@Reference
	private SkuForecastDTOConverter _skuForecastDTOConverter;

}
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

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecastManager;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.SkuForecast;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.machine.learning.forecast.SkuCommerceMLForecast",
	service = {DTOConverter.class, SkuForecastDTOConverter.class}
)
public class SkuForecastDTOConverter
	implements DTOConverter<SkuCommerceMLForecast, SkuForecast> {

	@Override
	public String getContentType() {
		return SkuForecast.class.getSimpleName();
	}

	@Override
	public SkuCommerceMLForecast getObject(String externalReferenceCode)
		throws Exception {

		return _skuCommerceMLForecastManager.getSkuCommerceMLForecast(
			CompanyThreadLocal.getCompanyId(),
			GetterUtil.getLong(externalReferenceCode));
	}

	@Override
	public SkuForecast toDTO(
			DTOConverterContext dtoConverterContext,
			SkuCommerceMLForecast skuCommerceMLForecast)
		throws Exception {

		if (skuCommerceMLForecast == null) {
			return null;
		}

		return new SkuForecast() {
			{
				actual = skuCommerceMLForecast.getActual();
				forecast = skuCommerceMLForecast.getForecast();
				forecastLowerBound =
					skuCommerceMLForecast.getForecastLowerBound();
				forecastUpperBound =
					skuCommerceMLForecast.getForecastUpperBound();
				sku = skuCommerceMLForecast.getSku();
				timestamp = skuCommerceMLForecast.getTimestamp();
				unit = skuCommerceMLForecast.getTarget();
			}
		};
	}

	@Reference
	private SkuCommerceMLForecastManager _skuCommerceMLForecastManager;

}
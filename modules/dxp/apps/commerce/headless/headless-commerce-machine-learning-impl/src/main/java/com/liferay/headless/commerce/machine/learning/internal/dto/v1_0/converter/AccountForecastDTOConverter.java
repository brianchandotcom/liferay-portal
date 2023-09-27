/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.machine.learning.internal.dto.v1_0.converter;

import com.liferay.commerce.machine.learning.forecast.CommerceAccountCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.CommerceAccountCommerceMLForecastManager;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.AccountForecast;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = "model.class.name=com.liferay.commerce.machine.learning.forecast.model.CommerceAccountCommerceMLForecast",
	service = DTOConverter.class
)
public class AccountForecastDTOConverter
	implements DTOConverter
		<CommerceAccountCommerceMLForecast, AccountForecast> {

	@Override
	public String getContentType() {
		return CommerceAccountCommerceMLForecast.class.getSimpleName();
	}

	@Override
	public AccountForecast toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceMLForecastCompositeResourcePrimaryKey compositeResourcePrimKey =
			(CommerceMLForecastCompositeResourcePrimaryKey)
				dtoConverterContext.getId();

		CommerceAccountCommerceMLForecast commerceAccountCommerceMLForecast =
			_commerceAccountCommerceMLForecastManager.
				getCommerceAccountCommerceMLForecast(
					compositeResourcePrimKey.getCompanyId(),
					compositeResourcePrimKey.getForecastId());

		return new AccountForecast() {
			{
				account =
					commerceAccountCommerceMLForecast.getCommerceAccountId();
				actual = commerceAccountCommerceMLForecast.getActual();
				forecast = commerceAccountCommerceMLForecast.getForecast();
				forecastLowerBound =
					commerceAccountCommerceMLForecast.getForecastLowerBound();
				forecastUpperBound =
					commerceAccountCommerceMLForecast.getForecastUpperBound();
				timestamp = commerceAccountCommerceMLForecast.getTimestamp();
				unit = commerceAccountCommerceMLForecast.getTarget();
			}
		};
	}

	@Reference
	private CommerceAccountCommerceMLForecastManager
		_commerceAccountCommerceMLForecastManager;

}
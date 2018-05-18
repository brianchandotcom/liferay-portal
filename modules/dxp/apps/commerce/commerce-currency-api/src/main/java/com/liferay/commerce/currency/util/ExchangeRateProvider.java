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

package com.liferay.commerce.currency.util;

import com.liferay.commerce.currency.model.CommerceCurrency;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public interface ExchangeRateProvider {

	public BigDecimal getExchangeRate(
			CommerceCurrency primaryCommerceCurrency,
			CommerceCurrency secondaryCommerceCurrency)
		throws Exception;

}
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

package com.liferay.commerce.currency.service.util;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.ExchangeRateProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {"commerce.exchange.provider.key=ecb-exchange-rate-provider"},
	service = ExchangeRateProvider.class
)
public class ECBExchangeRateProvider implements ExchangeRateProvider {

	public static final String ECB_URL =
		"http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

	@Override
	public double getExchangeRate(
			CommerceCurrency primaryCommerceCurrency,
			CommerceCurrency secondaryCommerceCurrency)
		throws Exception {

		String primaryCurrencyCode = primaryCommerceCurrency.getCode();
		String secondaryCurrencyCode = secondaryCommerceCurrency.getCode();

		primaryCurrencyCode = StringUtil.toUpperCase(primaryCurrencyCode);
		secondaryCurrencyCode = StringUtil.toUpperCase(secondaryCurrencyCode);

		Document document = null;

		try {
			String xml = _http.URLtoString(ECB_URL);

			document = _saxReader.read(xml);
		}
		catch (Exception de) {
			if (_log.isDebugEnabled()) {
				_log.debug(de.getMessage(), de);
			}

			return 0;
		}

		Element rootElement = document.getRootElement();

		List<Element> rootCubeElements = rootElement.elements("Cube");

		Element rootCubeElement = rootCubeElements.get(0);

		List<Element> cubeParentElements = rootCubeElement.elements("Cube");

		Element cubeParentElement = cubeParentElements.get(0);

		List<Element> cubeElements = cubeParentElement.elements("Cube");

		double rateToPrimary = 0;
		double rateToSecondary = 0;

		for (Element cubeElement : cubeElements) {
			String currency = cubeElement.attributeValue("currency");
			double rate = GetterUtil.getDouble(
				cubeElement.attributeValue("rate"));

			if (currency.equals(primaryCurrencyCode)) {
				rateToPrimary = rate;
			}

			if (currency.equals(secondaryCurrencyCode)) {
				rateToSecondary = rate;
			}

			if ((rateToPrimary > 0) && (rateToSecondary > 0)) {
				break;
			}
		}

		if (primaryCurrencyCode.equals("EUR")) {
			rateToPrimary = 1;
		}

		if (secondaryCurrencyCode.equals("EUR")) {
			rateToSecondary = 1;
		}

		return Math.round((rateToSecondary / rateToPrimary) * 10000D) / 10000D;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ECBExchangeRateProvider.class);

	@Reference
	private Http _http;

	@Reference
	private SAXReader _saxReader;

}
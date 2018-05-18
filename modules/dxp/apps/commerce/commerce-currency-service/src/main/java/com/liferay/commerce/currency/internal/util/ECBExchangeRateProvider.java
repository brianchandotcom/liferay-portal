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

package com.liferay.commerce.currency.internal.util;

import com.liferay.commerce.currency.internal.configuration.ECBExchangeRateProviderConfiguration;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.ExchangeRateProvider;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;

import java.io.IOException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.net.URL;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	configurationPid = "com.liferay.commerce.currency.internal.configuration.ECBExchangeRateProviderConfiguration",
	immediate = true,
	property = "commerce.exchange.provider.key=european-central-bank",
	service = ExchangeRateProvider.class
)
public class ECBExchangeRateProvider implements ExchangeRateProvider {

	@Override
	public BigDecimal getExchangeRate(
			CommerceCurrency primaryCommerceCurrency,
			CommerceCurrency secondaryCommerceCurrency)
		throws Exception {

		String primaryCurrencyCode = primaryCommerceCurrency.getCode();
		String secondaryCurrencyCode = secondaryCommerceCurrency.getCode();

		primaryCurrencyCode = StringUtil.toUpperCase(primaryCurrencyCode);
		secondaryCurrencyCode = StringUtil.toUpperCase(secondaryCurrencyCode);

		String xml = null;

		int i = 0;

		while (Validator.isNull(xml)) {
			try {
				xml = _http.URLtoString(_getURL());
			}
			catch (IOException ioe) {
				if (i++ >= 10) {
					throw ioe;
				}
			}

			if (i >= 10) {
				throw new PortalException("Impossible to load " + _url);
			}
		}

		Document document = _saxReader.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> rootCubeElements = rootElement.elements("Cube");

		Element rootCubeElement = rootCubeElements.get(0);

		List<Element> cubeParentElements = rootCubeElement.elements("Cube");

		Element cubeParentElement = cubeParentElements.get(0);

		List<Element> cubeElements = cubeParentElement.elements("Cube");

		BigDecimal rateToPrimary = BigDecimal.ZERO;
		BigDecimal rateToSecondary = BigDecimal.ZERO;

		for (Element cubeElement : cubeElements) {
			String currency = cubeElement.attributeValue("currency");
			BigDecimal rate = new BigDecimal(
				cubeElement.attributeValue("rate"));

			if (currency.equals(primaryCurrencyCode)) {
				rateToPrimary = rate;
			}

			if (currency.equals(secondaryCurrencyCode)) {
				rateToSecondary = rate;
			}

			if ((rateToPrimary.compareTo(BigDecimal.ZERO) > 0) &&
				(rateToSecondary.compareTo(BigDecimal.ZERO) > 0)) {

				break;
			}
		}

		if (primaryCurrencyCode.equals("EUR")) {
			rateToPrimary = BigDecimal.ONE;
		}

		if (secondaryCurrencyCode.equals("EUR")) {
			rateToSecondary = BigDecimal.ONE;
		}

		return rateToSecondary.divide(rateToPrimary, 4, RoundingMode.HALF_EVEN);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		ECBExchangeRateProviderConfiguration
			ecbExchangeRateProviderConfiguration =
				ConfigurableUtil.createConfigurable(
					ECBExchangeRateProviderConfiguration.class, properties);

		_url = ecbExchangeRateProviderConfiguration.europeanCentralBankURL();
	}

	@Deactivate
	protected void deactivate() {
		_url = null;
	}

	private URL _getURL() throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL url = classLoader.getResource(_url);

		if (url == null) {
			url = new URL(_url);
		}

		return url;
	}

	@Reference
	private Http _http;

	@Reference
	private SAXReader _saxReader;

	private volatile String _url;

}
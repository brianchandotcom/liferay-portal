/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.util;

import com.liferay.commerce.currency.internal.configuration.ECBExchangeRateProviderConfiguration;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.ExchangeRateProvider;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;

import java.io.IOException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.net.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	configurationPid = "com.liferay.commerce.currency.internal.configuration.ECBExchangeRateProviderConfiguration",
	property = "commerce.exchange.provider.key=european-central-bank",
	service = ExchangeRateProvider.class
)
public class ECBExchangeRateProvider implements ExchangeRateProvider {

	@Override
	public BigDecimal getExchangeRate(
			CommerceCurrency primaryCommerceCurrency,
			CommerceCurrency secondaryCommerceCurrency)
		throws Exception {

		Map<String, BigDecimal> currencyRates = _getCurrencyRates();

		String primaryCurrencyCode = StringUtil.toUpperCase(
			primaryCommerceCurrency.getCode());
		String secondaryCurrencyCode = StringUtil.toUpperCase(
			secondaryCommerceCurrency.getCode());

		BigDecimal rateToPrimary = currencyRates.getOrDefault(
			primaryCurrencyCode, BigDecimal.ZERO);
		BigDecimal rateToSecondary = currencyRates.getOrDefault(
			secondaryCurrencyCode, BigDecimal.ZERO);

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

		_expirationTime = TimeUnit.SECONDS.toMillis(
			ecbExchangeRateProviderConfiguration.
				europeanCentralBankURLCacheExpirationTime());

		_timeStamp = 0;
	}

	private Map<String, BigDecimal> _getCurrencyRates() throws Exception {
		long currentTime = System.currentTimeMillis();

		long timestamp = currentTime - _expirationTime;

		if (timestamp < _timeStamp) {
			return _currencyRates;
		}

		synchronized (this) {
			if (timestamp < _timeStamp) {
				return _currencyRates;
			}

			Document document = null;

			int i = 0;

			while (document == null) {
				try {
					document = _saxReader.read(_http.URLtoString(_getURL()));
				}
				catch (IOException ioException) {
					if (i++ >= 10) {
						throw ioException;
					}
				}

				if (i >= 10) {
					throw new PortalException("Impossible to load " + _url);
				}
			}

			Element rootElement = document.getRootElement();

			List<Element> rootCubeElements = rootElement.elements("Cube");

			Element rootCubeElement = rootCubeElements.get(0);

			List<Element> cubeParentElements = rootCubeElement.elements("Cube");

			Element cubeParentElement = cubeParentElements.get(0);

			List<Element> cubeElements = cubeParentElement.elements("Cube");

			Map<String, BigDecimal> currencyRates = new HashMap<>();

			for (Element cubeElement : cubeElements) {
				currencyRates.put(
					cubeElement.attributeValue("currency"),
					new BigDecimal(cubeElement.attributeValue("rate")));
			}

			_currencyRates = currencyRates;

			_timeStamp = currentTime;

			return _currencyRates;
		}
	}

	private URL _getURL() throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL url = classLoader.getResource(_url);

		if (url == null) {
			url = new URL(_url);

			if (_isLocalNetworkURL(url)) {
				throw new PortalException(
					"Invalid European Central Bank URL: " + url);
			}
		}

		return url;
	}

	private boolean _isLocalNetworkURL(URL url) throws Exception {
		return InetAddressUtil.isLocalInetAddress(
			InetAddressUtil.getInetAddressByName(url.getHost()));
	}

	private volatile Map<String, BigDecimal> _currencyRates;
	private volatile long _expirationTime;

	@Reference
	private Http _http;

	@Reference
	private SAXReader _saxReader;

	private volatile long _timeStamp;
	private volatile String _url;

}
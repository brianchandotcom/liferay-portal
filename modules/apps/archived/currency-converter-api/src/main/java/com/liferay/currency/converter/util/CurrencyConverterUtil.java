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

package com.liferay.currency.converter.util;

import com.liferay.currency.converter.model.CurrencyConverter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class CurrencyConverterUtil {

	public static Map<String, String> getAllSymbols(Locale locale) {
		String key = locale.toString();

		Map<String, String> symbols = _symbolsPool.get(key);

		if (symbols != null) {
			return symbols;
		}

		symbols = new TreeMap<>();

		for (String symbol : _currencyConverterUtil._currencyIds) {
			symbols.put(LanguageUtil.get(locale, symbol), symbol);
		}

		_symbolsPool.put(key, symbols);

		return symbols;
	}

	public static CurrencyConverter getCurrencyConverter(String symbol) {
		WebCacheItem wci = new CurrencyConverterWebCacheItem(symbol);

		String key =
			CurrencyConverterUtil.class.getName() + StringPool.PERIOD + symbol;

		try {
			return (CurrencyConverter)WebCachePoolUtil.get(key, wci);
		}
		catch (ClassCastException cce) {
			WebCachePoolUtil.remove(key);
		}

		return null;
	}

	public static boolean isCurrency(String symbol) {
		return _currencyConverterUtil._currencyIds.contains(symbol);
	}

	private CurrencyConverterUtil() {
		_currencyIds = new HashSet<>();

		_currencyIds.add("AUD");
		_currencyIds.add("BGN");
		_currencyIds.add("BRL");
		_currencyIds.add("CAD");
		_currencyIds.add("CHF");
		_currencyIds.add("CNY");
		_currencyIds.add("CZK");
		_currencyIds.add("DKK");
		_currencyIds.add("EUR");
		_currencyIds.add("GBP");
		_currencyIds.add("HKD");
		_currencyIds.add("HRK");
		_currencyIds.add("HUF");
		_currencyIds.add("IDR");
		_currencyIds.add("ILS");
		_currencyIds.add("INR");
		_currencyIds.add("ISK");
		_currencyIds.add("JPY");
		_currencyIds.add("KRW");
		_currencyIds.add("MXN");
		_currencyIds.add("MYR");
		_currencyIds.add("NOK");
		_currencyIds.add("NZD");
		_currencyIds.add("PHP");
		_currencyIds.add("PLN");
		_currencyIds.add("RON");
		_currencyIds.add("RUB");
		_currencyIds.add("SEK");
		_currencyIds.add("SGD");
		_currencyIds.add("THB");
		_currencyIds.add("TRY");
		_currencyIds.add("USD");
		_currencyIds.add("ZAR");
	}

	private static final CurrencyConverterUtil _currencyConverterUtil =
		new CurrencyConverterUtil();
	private static final Map<String, Map<String, String>> _symbolsPool =
		new ConcurrentHashMap<>();

	private final Set<String> _currencyIds;

}
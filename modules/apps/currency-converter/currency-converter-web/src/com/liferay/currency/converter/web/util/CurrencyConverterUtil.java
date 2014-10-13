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

package com.liferay.currency.converter.web.util;

import com.liferay.currency.converter.web.model.CurrencyConverter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class CurrencyConverterUtil {

	public static Map<String, String> getAllSymbols(
		HttpServletRequest request) {

		Locale locale = request.getLocale();

		String key = locale.toString();

		Map<String, String> symbols = _symbolsPool.get(key);

		if (symbols != null) {
			return symbols;
		}

		symbols = new TreeMap<String, String>();

		for (String symbol : _instance._currencyIds) {
			symbols.put(LanguageUtil.get(request, symbol), symbol);
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
		return _instance._currencyIds.contains(symbol);
	}

	private CurrencyConverterUtil() {
		_currencyIds = new HashSet<String>();

		_currencyIds.add("ALL");
		_currencyIds.add("DZD");
		_currencyIds.add("XAL");
		_currencyIds.add("ARS");
		_currencyIds.add("AWG");
		_currencyIds.add("AUD");
		_currencyIds.add("BSD");
		_currencyIds.add("BHD");
		_currencyIds.add("BDT");
		_currencyIds.add("BBD");
		_currencyIds.add("BYR");
		_currencyIds.add("BZD");
		_currencyIds.add("BMD");
		_currencyIds.add("BTN");
		_currencyIds.add("BOB");
		_currencyIds.add("BRL");
		_currencyIds.add("GBP");
		_currencyIds.add("BND");
		_currencyIds.add("BGN");
		_currencyIds.add("BIF");
		_currencyIds.add("KHR");
		_currencyIds.add("CAD");
		_currencyIds.add("KYD");
		_currencyIds.add("XOF");
		_currencyIds.add("XAF");
		_currencyIds.add("CLP");
		_currencyIds.add("CNY");
		_currencyIds.add("COP");
		_currencyIds.add("KMF");
		_currencyIds.add("XCP");
		_currencyIds.add("CRC");
		_currencyIds.add("HRK");
		_currencyIds.add("CUP");
		_currencyIds.add("CYP");
		_currencyIds.add("CZK");
		_currencyIds.add("DKK");
		_currencyIds.add("DJF");
		_currencyIds.add("DOP");
		_currencyIds.add("XCD");
		_currencyIds.add("ECS");
		_currencyIds.add("EGP");
		_currencyIds.add("SVC");
		_currencyIds.add("ERN");
		_currencyIds.add("EEK");
		_currencyIds.add("ETB");
		_currencyIds.add("EUR");
		_currencyIds.add("FKP");
		_currencyIds.add("GMD");
		_currencyIds.add("GHC");
		_currencyIds.add("GIP");
		_currencyIds.add("XAU");
		_currencyIds.add("GTQ");
		_currencyIds.add("GNF");
		_currencyIds.add("HTG");
		_currencyIds.add("HNL");
		_currencyIds.add("HKD");
		_currencyIds.add("HUF");
		_currencyIds.add("ISK");
		_currencyIds.add("INR");
		_currencyIds.add("IDR");
		_currencyIds.add("IRR");
		_currencyIds.add("ILS");
		_currencyIds.add("JMD");
		_currencyIds.add("JPY");
		_currencyIds.add("JOD");
		_currencyIds.add("KZT");
		_currencyIds.add("KES");
		_currencyIds.add("KRW");
		_currencyIds.add("KWD");
		_currencyIds.add("LAK");
		_currencyIds.add("LVL");
		_currencyIds.add("LBP");
		_currencyIds.add("LSL");
		_currencyIds.add("LYD");
		_currencyIds.add("LTL");
		_currencyIds.add("MOP");
		_currencyIds.add("MKD");
		_currencyIds.add("MGF");
		_currencyIds.add("MWK");
		_currencyIds.add("MYR");
		_currencyIds.add("MVR");
		_currencyIds.add("MTL");
		_currencyIds.add("MRO");
		_currencyIds.add("MUR");
		_currencyIds.add("MXN");
		_currencyIds.add("MDL");
		_currencyIds.add("MNT");
		_currencyIds.add("MAD");
		_currencyIds.add("MZM");
		_currencyIds.add("NAD");
		_currencyIds.add("NPR");
		_currencyIds.add("ANG");
		_currencyIds.add("TRY");
		_currencyIds.add("NZD");
		_currencyIds.add("NIO");
		_currencyIds.add("NGN");
		_currencyIds.add("NOK");
		_currencyIds.add("OMR");
		_currencyIds.add("XPF");
		_currencyIds.add("PKR");
		_currencyIds.add("XPD");
		_currencyIds.add("PAB");
		_currencyIds.add("PGK");
		_currencyIds.add("PYG");
		_currencyIds.add("PEN");
		_currencyIds.add("PHP");
		_currencyIds.add("XPT");
		_currencyIds.add("PLN");
		_currencyIds.add("QAR");
		_currencyIds.add("ROL");
		_currencyIds.add("RON");
		_currencyIds.add("RUB");
		_currencyIds.add("RWF");
		_currencyIds.add("WST");
		_currencyIds.add("STD");
		_currencyIds.add("SAR");
		_currencyIds.add("SCR");
		_currencyIds.add("SLL");
		_currencyIds.add("XAG");
		_currencyIds.add("SGD");
		_currencyIds.add("SKK");
		_currencyIds.add("SIT");
		_currencyIds.add("SOS");
		_currencyIds.add("ZAR");
		_currencyIds.add("LKR");
		_currencyIds.add("SHP");
		_currencyIds.add("SDD");
		_currencyIds.add("SRG");
		_currencyIds.add("SZL");
		_currencyIds.add("SEK");
		_currencyIds.add("CHF");
		_currencyIds.add("SYP");
		_currencyIds.add("TWD");
		_currencyIds.add("TZS");
		_currencyIds.add("THB");
		_currencyIds.add("TOP");
		_currencyIds.add("TTD");
		_currencyIds.add("TND");
		_currencyIds.add("USD");
		_currencyIds.add("AED");
		_currencyIds.add("UGX");
		_currencyIds.add("UAH");
		_currencyIds.add("UYU");
		_currencyIds.add("VUV");
		_currencyIds.add("VEB");
		_currencyIds.add("VND");
		_currencyIds.add("YER");
		_currencyIds.add("ZMK");
		_currencyIds.add("ZWD");
	}

	private static final CurrencyConverterUtil _instance =
		new CurrencyConverterUtil();

	private static final Map<String, Map<String, String>> _symbolsPool =
		new ConcurrentHashMap<String, Map<String, String>>();

	private final Set<String> _currencyIds;

}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.currencyconverter.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portlet.currencyconverter.model.Currency;

import java.util.StringTokenizer;

/**
 * @author Brian Wing Shun Chan
 */
public class CurrencyWebCacheItem implements WebCacheItem {

	public CurrencyWebCacheItem(String symbol) {
		_symbol = symbol;
	}

	@Override
	public Object convert(String key) throws WebCacheException {
		String symbol = _symbol;
		double rate = 0.0;

		try {
			if (symbol.length() == 6) {
				String fromSymbol = symbol.substring(0, 3);
				String toSymbol = symbol.substring(3, 6);

				if (!CurrencyUtil.isCurrency(fromSymbol) ||
					!CurrencyUtil.isCurrency(toSymbol)) {

					throw new WebCacheException(symbol);
				}
			}
			else if (symbol.length() == 3) {
				if (!CurrencyUtil.isCurrency(symbol)) {
					throw new WebCacheException(symbol);
				}
			}
			else {
				throw new WebCacheException(symbol);
			}

			String text = HttpUtil.URLtoString(
				"http://finance.yahoo.com/d/quotes.csv?s=" +
					symbol + "=X&f=sl1d1t1c1ohgv&e=.csv");

			StringTokenizer st = new StringTokenizer(text, StringPool.COMMA);

			// Skip symbol

			st.nextToken();

			rate = GetterUtil.getDouble(
				st.nextToken().replace('"', ' ').trim());
		}
		catch (Exception e) {
			throw new WebCacheException(e);
		}

		return new Currency(symbol, rate);
	}

	@Override
	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	private static final long _REFRESH_TIME = Time.MINUTE * 20;

	private String _symbol;

}
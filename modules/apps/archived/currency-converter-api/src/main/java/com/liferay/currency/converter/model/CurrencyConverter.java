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

package com.liferay.currency.converter.model;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class CurrencyConverter implements Serializable {

	public static final String DEFAULT_FROM = "USD";

	public static final String DEFAULT_TO = "EUR";

	public CurrencyConverter(String symbol, double rate) {
		_symbol = symbol;
		_rate = rate;
	}

	public String getFromSymbol() {
		if ((_symbol != null) && (_symbol.length() == 6)) {
			return _symbol.substring(0, 3);
		}

		return DEFAULT_FROM;
	}

	public double getRate() {
		return _rate;
	}

	public String getSymbol() {
		return _symbol;
	}

	public String getToSymbol() {
		if ((_symbol != null) && (_symbol.length() == 6)) {
			return _symbol.substring(3, 6);
		}

		return DEFAULT_TO;
	}

	private final double _rate;
	private final String _symbol;

}
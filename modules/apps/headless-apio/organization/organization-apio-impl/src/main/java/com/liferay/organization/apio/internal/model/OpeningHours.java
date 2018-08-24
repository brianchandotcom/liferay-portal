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

package com.liferay.organization.apio.internal.model;

import com.liferay.apio.architect.functional.Try;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.function.Supplier;

/**
 * @author Javier Gamarra
 */
public class OpeningHours {

	public OpeningHours(
		String day, Supplier<Integer> openSupplier,
		Supplier<Integer> closeSupplier) {

		_day = day;
		_opens = _getHours(openSupplier.get());
		_closes = _getHours(closeSupplier.get());
	}

	public String getCloses() {
		return _closes;
	}

	public String getDay() {
		return _day;
	}

	public String getOpens() {
		return _opens;
	}

	private String _formatHour(int hour) {
		DecimalFormat decimalFormat = new DecimalFormat("00,00");

		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

		decimalFormatSymbols.setGroupingSeparator(':');

		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);

		decimalFormat.setGroupingSize(2);

		return decimalFormat.format(hour);
	}

	private String _getHours(Integer hours) {
		return Try.fromFallible(
			() -> hours
		).filter(
			value -> value != -1
		).map(
			this::_formatHour
		).orElse(
			null
		);
	}

	private final String _closes;
	private final String _day;
	private final String _opens;

}
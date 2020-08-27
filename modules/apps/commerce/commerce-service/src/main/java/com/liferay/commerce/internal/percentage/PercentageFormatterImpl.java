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

package com.liferay.commerce.internal.percentage;

import com.liferay.commerce.percentage.PercentageFormatter;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alec Sloan
 */
@Component(immediate = true, service = PercentageFormatter.class)
public class PercentageFormatterImpl implements PercentageFormatter {

	@Override
	public String getLocalizedPercentage(
		Locale locale, int maxFractionDigits, int minFractionDigits,
		BigDecimal percentage) {

		if (percentage.compareTo(BigDecimal.ONE) > 0) {
			percentage = percentage.multiply(new BigDecimal(.01));
		}

		NumberFormat decimalFormat = DecimalFormat.getPercentInstance(locale);

		decimalFormat.setMaximumFractionDigits(maxFractionDigits);
		decimalFormat.setMinimumFractionDigits(minFractionDigits);

		return decimalFormat.format(percentage);
	}

}
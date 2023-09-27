/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = PercentageFormatter.class)
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
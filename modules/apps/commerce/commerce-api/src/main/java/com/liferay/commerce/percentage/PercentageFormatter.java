/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.percentage;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Alec Sloan
 */
public interface PercentageFormatter {

	public String getLocalizedPercentage(
		Locale locale, int maxFractionDigits, int minFractionDigits,
		BigDecimal percentage);

}
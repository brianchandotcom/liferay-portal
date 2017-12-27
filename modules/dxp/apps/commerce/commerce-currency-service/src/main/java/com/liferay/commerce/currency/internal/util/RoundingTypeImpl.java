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

package com.liferay.commerce.currency.internal.util;

import com.liferay.commerce.currency.util.RoundingType;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.math.RoundingMode;

import java.text.DecimalFormat;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"rounding.type.name=" + RoundingTypeImpl.NAME,
		"rounding.type.priority:Integer=10"
	},
	service = RoundingType.class
)
public class RoundingTypeImpl implements RoundingType {

	public static final String NAME = "default";

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, NAME);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String round(double value) {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

		return decimalFormat.format(value);
	}

}
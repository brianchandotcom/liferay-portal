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

import com.liferay.commerce.currency.internal.configuration.RoundingTypeConfiguration;
import com.liferay.commerce.currency.util.RoundingType;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.currency.internal.configuration.RoundingTypeConfiguration",
	immediate = true,
	property = {
		"rounding.type.name=" + DefaultRoundingTypeImpl.NAME,
		"rounding.type.priority:Integer=10"
	},
	service = RoundingType.class
)
public class DefaultRoundingTypeImpl implements RoundingType {

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
	public String round(BigDecimal value) {
		DecimalFormat decimalFormat = new DecimalFormat(
			_roundingTypeConfiguration.formatPattern());

		decimalFormat.setMaximumFractionDigits(
			_roundingTypeConfiguration.maximumFractionDigits());
		decimalFormat.setMinimumFractionDigits(
			_roundingTypeConfiguration.minimumFractionDigits());
		decimalFormat.setRoundingMode(
			_roundingTypeConfiguration.roundingMode());

		return decimalFormat.format(value);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_roundingTypeConfiguration = ConfigurableUtil.createConfigurable(
			RoundingTypeConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_roundingTypeConfiguration = null;
	}

	private volatile RoundingTypeConfiguration _roundingTypeConfiguration;

}
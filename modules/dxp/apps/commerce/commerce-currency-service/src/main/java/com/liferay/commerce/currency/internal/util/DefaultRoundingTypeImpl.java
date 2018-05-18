/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
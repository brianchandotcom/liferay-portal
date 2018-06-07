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

import com.liferay.commerce.currency.configuration.RoundingTypeConfiguration;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.text.DecimalFormat;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(
	configurationPid = "com.liferay.commerce.currency.configuration.RoundingTypeConfiguration",
	immediate = true, service = CommercePriceFormatter.class
)
public class CommercePriceFormatterImpl implements CommercePriceFormatter {

	@Override
	public String format(BigDecimal price, Locale locale)
		throws PortalException {

		DecimalFormat decimalFormat = getDecimalFormat(null, locale);

		return decimalFormat.format(price);
	}

	@Override
	public String format(
			CommerceCurrency commerceCurrency, BigDecimal price, Locale locale)
		throws PortalException {

		DecimalFormat decimalFormat = getDecimalFormat(
			commerceCurrency, locale);

		String value = decimalFormat.format(price);

		if (commerceCurrency == null) {
			return value;
		}

		return value;
	}

	@Override
	public String format(long groupId, BigDecimal price, Locale locale)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(groupId);

		return format(commerceCurrency, price, locale);
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

	protected DecimalFormat getDecimalFormat(
			CommerceCurrency commerceCurrency, Locale locale)
		throws PortalException {

		String formatPattern = _roundingTypeConfiguration.formatPattern();
		int maxFractionDigits =
			_roundingTypeConfiguration.maximumFractionDigits();
		int minFractionDigits =
			_roundingTypeConfiguration.minimumFractionDigits();
		RoundingMode roundingMode = _roundingTypeConfiguration.roundingMode();

		if (commerceCurrency != null) {
			formatPattern = commerceCurrency.getFormatPattern(locale);

			if (Validator.isNull(formatPattern)) {
				Locale siteDefaultLocale = _portal.getSiteDefaultLocale(
					commerceCurrency.getGroupId());

				formatPattern = commerceCurrency.getFormatPattern(
					siteDefaultLocale);
			}

			maxFractionDigits = commerceCurrency.getMaxFractionDigits();
			minFractionDigits = commerceCurrency.getMinFractionDigits();
			roundingMode = RoundingMode.valueOf(
				commerceCurrency.getRoundingMode());
		}

		DecimalFormat decimalFormat = new DecimalFormat(formatPattern);

		decimalFormat.setMaximumFractionDigits(maxFractionDigits);
		decimalFormat.setMinimumFractionDigits(minFractionDigits);
		decimalFormat.setRoundingMode(roundingMode);

		return decimalFormat;
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private Portal _portal;

	private volatile RoundingTypeConfiguration _roundingTypeConfiguration;

}
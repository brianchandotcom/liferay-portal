/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.util;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.DecimalFormatSymbols;

import com.liferay.commerce.currency.configuration.RoundingTypeConfiguration;
import com.liferay.commerce.currency.constants.CommerceCurrencyConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(
	configurationPid = "com.liferay.commerce.currency.configuration.RoundingTypeConfiguration",
	service = CommercePriceFormatter.class
)
public class CommercePriceFormatterImpl implements CommercePriceFormatter {

	@Override
	public String format(BigDecimal price, Locale locale)
		throws PortalException {

		DecimalFormat decimalFormat = _getDecimalFormat(null, locale);

		return decimalFormat.format(price);
	}

	@Override
	public String format(
			CommerceCurrency commerceCurrency, BigDecimal price, Locale locale)
		throws PortalException {

		DecimalFormat decimalFormat = _getDecimalFormat(
			commerceCurrency, locale);

		return decimalFormat.format(price);
	}

	@Override
	public String formatAsRelative(
		CommerceCurrency commerceCurrency, BigDecimal relativePrice,
		Locale locale) {

		if (relativePrice.signum() == 0) {
			return StringPool.BLANK;
		}

		DecimalFormat decimalFormat = _getDecimalFormat(
			commerceCurrency, locale);

		if (relativePrice.signum() == -1) {
			return String.format(
				"%2s %s", StringPool.MINUS,
				decimalFormat.format(relativePrice.negate()));
		}

		return String.format(
			"%2s %s", StringPool.PLUS, decimalFormat.format(relativePrice));
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

	private DecimalFormat _getDecimalFormat(
		CommerceCurrency commerceCurrency, Locale locale) {

		String formatPattern = CommerceCurrencyConstants.DECIMAL_FORMAT_PATTERN;
		int maxFractionDigits =
			_roundingTypeConfiguration.maximumFractionDigits();
		int minFractionDigits =
			_roundingTypeConfiguration.minimumFractionDigits();
		RoundingMode roundingMode = _roundingTypeConfiguration.roundingMode();

		if (commerceCurrency != null) {
			formatPattern = commerceCurrency.getFormatPattern(locale);

			if (Validator.isNull(formatPattern)) {
				formatPattern = commerceCurrency.getFormatPattern(
					commerceCurrency.getDefaultLanguageId());
			}

			maxFractionDigits = commerceCurrency.getMaxFractionDigits();
			minFractionDigits = commerceCurrency.getMinFractionDigits();
			roundingMode = RoundingMode.valueOf(
				commerceCurrency.getRoundingMode());
		}

		DecimalFormat decimalFormat = new DecimalFormat(
			formatPattern, DecimalFormatSymbols.getInstance(locale));

		decimalFormat.setMaximumFractionDigits(maxFractionDigits);
		decimalFormat.setMinimumFractionDigits(minFractionDigits);
		decimalFormat.setRoundingMode(roundingMode.ordinal());

		return decimalFormat;
	}

	private volatile RoundingTypeConfiguration _roundingTypeConfiguration;

}
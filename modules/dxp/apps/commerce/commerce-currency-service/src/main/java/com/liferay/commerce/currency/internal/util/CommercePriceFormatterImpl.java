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
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.currency.util.RoundingType;
import com.liferay.commerce.currency.util.RoundingTypeServicesTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.text.DecimalFormat;

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
	configurationPid = "com.liferay.commerce.currency.internal.configuration.RoundingTypeConfiguration",
	immediate = true, service = CommercePriceFormatter.class
)
public class CommercePriceFormatterImpl implements CommercePriceFormatter {

	@Override
	public String format(BigDecimal price) {
		DecimalFormat decimalFormat = getDefaultDecimalFormat();

		return decimalFormat.format(price);
	}

	@Override
	public String format(CommerceCurrency commerceCurrency, BigDecimal price) {
		String roundingTypeName = null;

		if (commerceCurrency != null) {
			roundingTypeName = commerceCurrency.getRoundingType();
		}

		String value = null;

		RoundingType roundingType =
			_roundingTypeServicesTracker.getRoundingType(roundingTypeName);

		if (roundingType != null) {
			value = roundingType.round(price);
		}
		else {
			DecimalFormat decimalFormat = getDefaultDecimalFormat();

			value = decimalFormat.format(price);
		}

		if (commerceCurrency == null) {
			return value;
		}

		return commerceCurrency.getCode() + StringPool.SPACE + value;
	}

	@Override
	public String format(long groupId, BigDecimal price)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(groupId);

		return format(commerceCurrency, price);
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

	protected DecimalFormat getDefaultDecimalFormat() {
		DecimalFormat decimalFormat = new DecimalFormat(
			_roundingTypeConfiguration.formatPattern());

		decimalFormat.setMaximumFractionDigits(
			_roundingTypeConfiguration.maximumFractionDigits());
		decimalFormat.setMinimumFractionDigits(
			_roundingTypeConfiguration.minimumFractionDigits());
		decimalFormat.setRoundingMode(
			_roundingTypeConfiguration.roundingMode());

		return decimalFormat;
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	private volatile RoundingTypeConfiguration _roundingTypeConfiguration;

	@Reference
	private RoundingTypeServicesTracker _roundingTypeServicesTracker;

}
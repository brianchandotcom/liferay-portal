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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.configuration.CommerceOrderItemDecimalQuantityConfiguration;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.util.CommerceOrderItemQuantityFormatter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
	configurationPid = "com.liferay.commerce.configuration.CommerceOrderItemDecimalQuantityConfiguration",
	enabled = false, immediate = true,
	service = CommerceOrderItemQuantityFormatter.class
)
public class CommerceOrderItemQuantityFormatterImpl
	implements CommerceOrderItemQuantityFormatter {

	@Override
	public String format(CommerceOrderItem commerceOrderItem, Locale locale) {
		CPMeasurementUnit cpMeasurementUnit =
			commerceOrderItem.fetchCPMeasurementUnit();

		BigDecimal decimalQuantity = commerceOrderItem.getDecimalQuantity();

		if ((decimalQuantity == null) ||
			decimalQuantity.equals(BigDecimal.ZERO)) {

			if (cpMeasurementUnit == null) {
				return String.valueOf(commerceOrderItem.getQuantity());
			}

			return StringBundler.concat(
				commerceOrderItem.getQuantity(), StringPool.SPACE,
				cpMeasurementUnit.getName(locale));
		}

		DecimalFormat decimalFormat = _getDecimalFormat(locale);

		if (cpMeasurementUnit == null) {
			return decimalFormat.format(decimalQuantity);
		}

		return StringBundler.concat(
			decimalFormat.format(decimalQuantity), StringPool.SPACE,
			cpMeasurementUnit.getName(locale));
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commerceOrderItemDecimalQuantityConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceOrderItemDecimalQuantityConfiguration.class,
				properties);
	}

	@Deactivate
	protected void deactivate() {
		_commerceOrderItemDecimalQuantityConfiguration = null;
	}

	private DecimalFormat _getDecimalFormat(Locale locale) {
		DecimalFormat decimalFormat = new DecimalFormat(
			CommerceOrderConstants.DECIMAL_FORMAT_PATTERN,
			DecimalFormatSymbols.getInstance(locale));

		decimalFormat.setMaximumFractionDigits(
			_commerceOrderItemDecimalQuantityConfiguration.
				maximumFractionDigits());
		decimalFormat.setMinimumFractionDigits(
			_commerceOrderItemDecimalQuantityConfiguration.
				minimumFractionDigits());
		decimalFormat.setRoundingMode(
			_commerceOrderItemDecimalQuantityConfiguration.roundingMode());

		return decimalFormat;
	}

	private volatile CommerceOrderItemDecimalQuantityConfiguration
		_commerceOrderItemDecimalQuantityConfiguration;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.util;

import com.liferay.commerce.configuration.CommerceOrderItemDecimalQuantityConfiguration;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.util.CommerceOrderItemQuantityFormatter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.configuration.CommerceOrderItemDecimalQuantityConfiguration",
	service = CommerceOrderItemQuantityFormatter.class
)
public class CommerceOrderItemQuantityFormatterImpl
	implements CommerceOrderItemQuantityFormatter {

	@Override
	public String format(
			CommerceOrderItem commerceOrderItem,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, Locale locale)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit =
			commerceOrderItem.fetchCPMeasurementUnit();

		DecimalFormat decimalFormat = _getDecimalFormat(locale);
		BigDecimal quantity = commerceOrderItem.getQuantity();

		if (cpMeasurementUnit != null) {
			return StringBundler.concat(
				decimalFormat.format(quantity), StringPool.SPACE,
				cpMeasurementUnit.getName(locale));
		}

		if (cpInstanceUnitOfMeasure != null) {
			return String.valueOf(
				quantity.setScale(
					cpInstanceUnitOfMeasure.getPrecision(),
					RoundingMode.HALF_UP));
		}

		return decimalFormat.format(quantity);
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

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

}
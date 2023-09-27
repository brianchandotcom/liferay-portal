/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.fixed.internal.engine;

import com.liferay.commerce.exception.CommerceTaxEngineException;
import com.liferay.commerce.tax.CommerceTaxCalculateRequest;
import com.liferay.commerce.tax.CommerceTaxEngine;
import com.liferay.commerce.tax.CommerceTaxValue;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "commerce.tax.engine.key=" + FixedCommerceTaxEngine.KEY,
	service = CommerceTaxEngine.class
)
public class FixedCommerceTaxEngine implements CommerceTaxEngine {

	public static final String KEY = "fixed-tax";

	@Override
	public CommerceTaxValue getCommerceTaxValue(
			CommerceTaxCalculateRequest commerceTaxCalculateRequest)
		throws CommerceTaxEngineException {

		CommerceTaxValue commerceTaxValue = null;

		try {
			CommerceTaxFixedRate commerceTaxFixedRate =
				_commerceTaxFixedRateLocalService.fetchCommerceTaxFixedRate(
					commerceTaxCalculateRequest.getTaxCategoryId(),
					commerceTaxCalculateRequest.getCommerceTaxMethodId());

			if (commerceTaxFixedRate == null) {
				return new CommerceTaxValue(KEY, KEY, BigDecimal.ZERO);
			}

			BigDecimal rate = BigDecimal.valueOf(
				commerceTaxFixedRate.getRate());

			BigDecimal taxValue = rate;

			if (commerceTaxCalculateRequest.isPercentage()) {
				BigDecimal amount = commerceTaxCalculateRequest.getPrice();

				taxValue = amount.multiply(rate);

				BigDecimal denominator = _ONE_HUNDRED;

				if (commerceTaxCalculateRequest.isIncludeTax()) {
					denominator = _ONE_HUNDRED.add(rate);
				}

				taxValue = taxValue.divide(
					denominator, _SCALE, RoundingMode.HALF_EVEN);
			}

			commerceTaxValue = new CommerceTaxValue(KEY, KEY, taxValue);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			throw new CommerceTaxEngineException(portalException);
		}

		return commerceTaxValue;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(
			_getResourceBundle(locale), "fixed-tax-description");
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(_getResourceBundle(locale), KEY);
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	private static final int _SCALE = 10;

	private static final Log _log = LogFactoryUtil.getLog(
		FixedCommerceTaxEngine.class);

	@Reference
	private CommerceTaxFixedRateLocalService _commerceTaxFixedRateLocalService;

	@Reference
	private Language _language;

}
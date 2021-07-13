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

package com.liferay.commerce.tax.engine.fixed.internal.engine;

import com.liferay.commerce.exception.CommerceTaxEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.tax.CommerceTaxCalculateRequest;
import com.liferay.commerce.tax.CommerceTaxEngine;
import com.liferay.commerce.tax.CommerceTaxValue;
import com.liferay.commerce.tax.engine.fixed.configuration.CommerceTaxByAddressTypeConfiguration;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
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
	enabled = false, immediate = true,
	property = "commerce.tax.engine.key=" + ByAddressCommerceTaxEngine.KEY,
	service = CommerceTaxEngine.class
)
public class ByAddressCommerceTaxEngine implements CommerceTaxEngine {

	public static final String KEY = "by-address";

	@Override
	public CommerceTaxValue getCommerceTaxValue(
			CommerceTaxCalculateRequest commerceTaxCalculateRequest)
		throws CommerceTaxEngineException {

		long countryId = 0;
		long regionId = 0;
		String zip = StringPool.BLANK;

		long commerceAddressId =
			commerceTaxCalculateRequest.getCommerceBillingAddressId();

		if (isTaxAppliedToShippingAddress(
				commerceTaxCalculateRequest.getCommerceChannelGroupId())) {

			commerceAddressId =
				commerceTaxCalculateRequest.getCommerceShippingAddressId();
		}

		CommerceAddress commerceAddress =
			_commerceAddressLocalService.fetchCommerceAddress(
				commerceAddressId);

		if (commerceAddress != null) {
			countryId = commerceAddress.getCountryId();
			regionId = commerceAddress.getRegionId();
			zip = commerceAddress.getZip();
		}

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			_commerceTaxFixedRateAddressRelLocalService.
				fetchCommerceTaxFixedRateAddressRel(
					commerceTaxCalculateRequest.getCommerceTaxMethodId(),
					commerceTaxCalculateRequest.getTaxCategoryId(), countryId,
					regionId, zip);

		if (commerceTaxFixedRateAddressRel == null) {
			return null;
		}

		BigDecimal rate = BigDecimal.valueOf(
			commerceTaxFixedRateAddressRel.getRate());

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

		return new CommerceTaxValue(KEY, KEY, taxValue);
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.get(
			_getResourceBundle(locale), "by-address-tax-rate-description");
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(_getResourceBundle(locale), KEY);
	}

	protected boolean isTaxAppliedToShippingAddress(long groupId) {
		try {
			CommerceTaxByAddressTypeConfiguration
				commerceTaxByAddressTypeConfiguration =
					ConfigurationProviderUtil.getConfiguration(
						CommerceTaxByAddressTypeConfiguration.class,
						new GroupServiceSettingsLocator(
							groupId,
							CommerceTaxByAddressTypeConfiguration.class.
								getName()));

			return commerceTaxByAddressTypeConfiguration.
				taxAppliedToShippingAddress();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return false;
		}
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	private static final int _SCALE = 10;

	private static final Log _log = LogFactoryUtil.getLog(
		ByAddressCommerceTaxEngine.class);

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommerceTaxFixedRateAddressRelLocalService
		_commerceTaxFixedRateAddressRelLocalService;

}
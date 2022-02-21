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

package com.liferay.commerce.shipping.engine.fixed.web.internal;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceAddressRestrictionLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionRelLocalService;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "commerce.shipping.engine.key=" + ByWeightCommerceShippingEngine.KEY,
	service = CommerceShippingEngine.class
)
public class ByWeightCommerceShippingEngine implements CommerceShippingEngine {

	public static final String KEY = "by-weight";

	@Override
	public String getCommerceShippingOptionLabel(String name, Locale locale) {
		return ResourceBundleUtil.getString(_getResourceBundle(locale), name);
	}

	@Override
	public List<CommerceShippingOption> getCommerceShippingOptions(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			Locale locale)
		throws CommerceShippingEngineException {

		List<CommerceShippingOption> commerceShippingOptions =
			new ArrayList<>();

		try {
			commerceShippingOptions = _getCommerceShippingOptions(
				commerceOrder, locale);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return commerceShippingOptions;
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.get(
			_getResourceBundle(locale), "by-weight-description");
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(_getResourceBundle(locale), "variable-rate");
	}

	private List<CommerceShippingFixedOption> _getCommerceShippingFixedOptions(
		long groupId) {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.fetchCommerceShippingMethod(
				groupId, KEY);

		if (commerceShippingMethod == null) {
			return Collections.emptyList();
		}

		return _commerceShippingFixedOptionLocalService.
			getCommerceShippingFixedOptions(
				commerceShippingMethod.getCommerceShippingMethodId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	private CommerceShippingOption _getCommerceShippingOption(
			CommerceOrder commerceOrder, Locale locale,
			CommerceAddress commerceAddress,
			CommerceShippingFixedOption commerceShippingFixedOption)
		throws PortalException {

		double orderWeight = _commerceShippingHelper.getWeight(
			commerceOrder.getCommerceOrderItems());

		long commerceCountryId = 0;
		long commerceRegionId = 0;
		String zip = StringPool.BLANK;

		if (commerceAddress != null) {
			commerceCountryId = commerceAddress.getCountryId();
			commerceRegionId = commerceAddress.getRegionId();
			zip = commerceAddress.getZip();
		}

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			_commerceShippingFixedOptionRelLocalService.
				fetchCommerceShippingFixedOptionRel(
					commerceShippingFixedOption.
						getCommerceShippingFixedOptionId(),
					commerceCountryId, commerceRegionId, zip, orderWeight);

		if (commerceShippingFixedOptionRel == null) {
			return null;
		}

		String key = commerceShippingFixedOption.getKey();
		String name = commerceShippingFixedOption.getName(locale);

		if (_commerceShippingHelper.isFreeShipping(commerceOrder)) {
			return new CommerceShippingOption(key, name, BigDecimal.ZERO);
		}

		BigDecimal amount = commerceShippingFixedOptionRel.getFixedPrice();

		BigDecimal rateUnitWeightPrice =
			commerceShippingFixedOptionRel.getRateUnitWeightPrice();

		if (rateUnitWeightPrice.compareTo(BigDecimal.ZERO) > 0) {
			amount = amount.add(
				rateUnitWeightPrice.multiply(new BigDecimal(orderWeight)));
		}

		BigDecimal ratePercentage = new BigDecimal(
			commerceShippingFixedOptionRel.getRatePercentage());

		CommerceMoney commerceMoney = commerceOrder.getSubtotalMoney();

		BigDecimal orderPrice = commerceMoney.getPrice();

		amount = amount.add(
			ratePercentage.multiply(orderPrice.divide(new BigDecimal(100))));

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		String commerceCurrencyCode = commerceCurrency.getCode();

		if (!commerceCurrencyCode.equals(
				commerceChannel.getCommerceCurrencyCode())) {

			CommerceCurrency commerceChannelCommerceCurrency =
				_commerceCurrencyService.getCommerceCurrency(
					commerceOrder.getCompanyId(),
					commerceChannel.getCommerceCurrencyCode());

			amount = amount.divide(
				commerceChannelCommerceCurrency.getRate(),
				RoundingMode.valueOf(
					commerceChannelCommerceCurrency.getRoundingMode()));

			amount = amount.multiply(commerceCurrency.getRate());
		}

		return new CommerceShippingOption(key, name, amount);
	}

	private List<CommerceShippingOption> _getCommerceShippingOptions(
			CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		List<CommerceShippingOption> commerceShippingOptions =
			new ArrayList<>();

		long commerceCountryId = 0;

		CommerceAddress commerceAddress = commerceOrder.getShippingAddress();

		if (commerceAddress != null) {
			commerceCountryId = commerceAddress.getCountryId();
		}

		List<CommerceShippingFixedOption> commerceShippingFixedOptions =
			_getCommerceShippingFixedOptions(commerceOrder.getGroupId());

		for (CommerceShippingFixedOption commerceShippingFixedOption :
				commerceShippingFixedOptions) {

			boolean restricted =
				_commerceAddressRestrictionLocalService.
					isCommerceShippingMethodRestricted(
						commerceShippingFixedOption.
							getCommerceShippingMethodId(),
						commerceCountryId);

			if (restricted) {
				continue;
			}

			CommerceShippingOption commerceShippingOption =
				_getCommerceShippingOption(
					commerceOrder, locale, commerceAddress,
					commerceShippingFixedOption);

			if (commerceShippingOption != null) {
				commerceShippingOptions.add(commerceShippingOption);
			}
		}

		return commerceShippingOptions;
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ByWeightCommerceShippingEngine.class);

	@Reference
	private CommerceAddressRestrictionLocalService
		_commerceAddressRestrictionLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Reference
	private CommerceShippingFixedOptionRelLocalService
		_commerceShippingFixedOptionRelLocalService;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

}
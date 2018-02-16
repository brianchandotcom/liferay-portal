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

import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.service.CommerceAddressRestrictionService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionRelService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

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
	immediate = true,
	property = "commerce.shipping.engine.key=" + ByWeightCommerceShippingEngine.KEY,
	service = CommerceShippingEngine.class
)
public class ByWeightCommerceShippingEngine implements CommerceShippingEngine {

	public static final String KEY = "by-weight";

	@Override
	public String getCommerceShippingOptionLabel(String name, Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return ResourceBundleUtil.getString(
			resourceBundle, "by-weight-description");
	}

	@Override
	public List<CommerceShippingOption> getCommerceShippingOptions(
			CommerceOrder commerceOrder, Locale locale)
		throws CommerceShippingEngineException {

		List<CommerceShippingOption> commerceShippingOptions =
			new ArrayList<>();

		try {
			commerceShippingOptions = _getCommerceShippingOptions(
				commerceOrder, locale);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return commerceShippingOptions;
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "by-weight-description");
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "by-weight");
	}

	private List<CommerceShippingFixedOption> _getCommerceShippingFixedOptions(
		long groupId) {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.fetchCommerceShippingMethod(
				groupId, KEY);

		if (commerceShippingMethod == null) {
			return Collections.emptyList();
		}

		return
			_commerceShippingFixedOptionService.getCommerceShippingFixedOptions(
				commerceShippingMethod.getCommerceShippingMethodId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	private CommerceShippingOption _getCommerceShippingOption(
			CommerceOrder commerceOrder, Locale locale,
			CommerceAddress commerceAddress,
			CommerceShippingFixedOption commerceShippingFixedOption)
		throws PortalException {

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		double cartPrice = _commerceShippingHelper.getPrice(commerceOrderItems);
		double cartWeight = _commerceShippingHelper.getWeight(
			commerceOrderItems);

		CommerceShippingFixedOptionRel commerceShippingFixedOptionRel =
			_commerceShippingFixedOptionRelService.
				fetchCommerceShippingFixedOptionRel(
					commerceShippingFixedOption.
						getCommerceShippingFixedOptionId(),
					commerceAddress.getCommerceCountryId(),
					commerceAddress.getCommerceRegionId(),
					commerceAddress.getZip(), cartWeight);

		if (commerceShippingFixedOptionRel == null) {
			return null;
		}

		String name = commerceShippingFixedOption.getName(locale);

		double amount = commerceShippingFixedOptionRel.getFixedPrice();

		double rateUnitWeightPrice =
			commerceShippingFixedOptionRel.getRateUnitWeightPrice();

		if (rateUnitWeightPrice > 0) {
			amount += cartWeight * rateUnitWeightPrice;
		}

		amount +=
			(cartPrice / 100) *
				commerceShippingFixedOptionRel.getRatePercentage();

		return new CommerceShippingOption(name, name, amount);
	}

	private List<CommerceShippingOption> _getCommerceShippingOptions(
			CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		List<CommerceShippingOption> commerceShippingOptions =
			new ArrayList<>();

		CommerceAddress commerceAddress = commerceOrder.getShippingAddress();

		List<CommerceShippingFixedOption> commerceShippingFixedOptions =
			_getCommerceShippingFixedOptions(commerceOrder.getGroupId());

		for (CommerceShippingFixedOption commerceShippingFixedOption :
				commerceShippingFixedOptions) {

			boolean restricted =
				_commerceAddressRestrictionService.
					isCommerceShippingMethodRestricted(
						commerceShippingFixedOption.
							getCommerceShippingMethodId(),
						commerceAddress.getCommerceCountryId());

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
	private CommerceAddressRestrictionService
		_commerceAddressRestrictionService;

	@Reference
	private CommerceShippingFixedOptionRelService
		_commerceShippingFixedOptionRelService;

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

}
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
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.service.CommerceAddressRestrictionService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
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
	property = "commerce.shipping.engine.key=" + FixedCommerceShippingEngine.KEY,
	service = CommerceShippingEngine.class
)
public class FixedCommerceShippingEngine implements CommerceShippingEngine {

	public static final String KEY = "fixed";

	@Override
	public String getCommerceShippingOptionLabel(String name, Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return ResourceBundleUtil.getString(
			resourceBundle, "fixed-description");
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

		return LanguageUtil.get(resourceBundle, "fixed-description");
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "fixed");
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

			String name = commerceShippingFixedOption.getName(locale);
			double amount = commerceShippingFixedOption.getAmount();

			commerceShippingOptions.add(
				new CommerceShippingOption(name, name, amount));
		}

		return commerceShippingOptions;
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FixedCommerceShippingEngine.class);

	@Reference
	private CommerceAddressRestrictionService
		_commerceAddressRestrictionService;

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

}
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

package com.liferay.commerce.shipping.engine.fedex.internal;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.service.CommerceAddressRestrictionLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fedex.internal.util.FedExCommerceShippingOptionHelper;
import com.liferay.commerce.shipping.origin.locator.CommerceShippingOriginLocator;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "commerce.shipping.engine.key=" + FedExCommerceShippingEngine.KEY,
	service = CommerceShippingEngine.class
)
public class FedExCommerceShippingEngine implements CommerceShippingEngine {

	public static final String KEY = "fedex";

	@Override
	public String getCommerceShippingOptionLabel(String name, Locale locale) {
		return FedExCommerceShippingOptionHelper.getCommerceShippingOptionLabel(
			name, _getResourceBundle(locale));
	}

	@Override
	public List<CommerceShippingOption> getCommerceShippingOptions(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			Locale locale)
		throws CommerceShippingEngineException {

		try {
			CommerceAddress commerceAddress =
				commerceOrder.getShippingAddress();

			boolean restricted =
				_commerceAddressRestrictionLocalService.
					isCommerceShippingMethodRestricted(
						_getCommerceShippingMethodId(commerceOrder),
						commerceAddress.getCommerceCountryId());

			if (restricted) {
				return Collections.emptyList();
			}

			FedExCommerceShippingOptionHelper
				fedExCommerceShippingOptionHelper =
					new FedExCommerceShippingOptionHelper(
						commerceContext, commerceOrder,
						_commerceCurrencyLocalService, _commerceShippingHelper,
						_commerceShippingOriginLocator,
						_cpMeasurementUnitLocalService, _configurationProvider,
						_getResourceBundle(locale));

			return fedExCommerceShippingOptionHelper.
				getCommerceShippingOptions();
		}
		catch (CommerceShippingEngineException
					commerceShippingEngineException) {

			throw commerceShippingEngineException;
		}
		catch (Exception exception) {
			throw new CommerceShippingEngineException(exception);
		}
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.get(
			_getResourceBundle(locale), "fedex-description");
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(_getResourceBundle(locale), "fedex");
	}

	private long _getCommerceShippingMethodId(CommerceOrder commerceOrder) {
		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.fetchCommerceShippingMethod(
				commerceOrder.getGroupId(), KEY);

		if (commerceShippingMethod == null) {
			return 0;
		}

		return commerceShippingMethod.getCommerceShippingMethodId();
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Reference
	private CommerceAddressRestrictionLocalService
		_commerceAddressRestrictionLocalService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private CommerceShippingOriginLocator _commerceShippingOriginLocator;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

}
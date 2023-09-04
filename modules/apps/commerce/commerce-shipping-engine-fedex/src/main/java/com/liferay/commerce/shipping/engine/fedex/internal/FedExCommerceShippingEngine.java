/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceAddressRestrictionLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fedex.internal.helper.FedExCommerceShippingOptionHelper;
import com.liferay.commerce.shipping.origin.locator.CommerceShippingOriginLocator;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.language.Language;
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

		return _getCommerceShippingOptions(
			commerceContext, commerceOrder, locale);
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(_getResourceBundle(locale), "fedex-description");
	}

	@Override
	public List<CommerceShippingOption> getEnabledCommerceShippingOptions(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			Locale locale)
		throws CommerceShippingEngineException {

		return _getCommerceShippingOptions(
			commerceContext, commerceOrder, locale);
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(_getResourceBundle(locale), "fedex");
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

	private List<CommerceShippingOption> _getCommerceShippingOptions(
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
						commerceAddress.getCountryId());

			if (restricted) {
				return Collections.emptyList();
			}

			FedExCommerceShippingOptionHelper
				fedExCommerceShippingOptionHelper =
					new FedExCommerceShippingOptionHelper(
						_commerceChannelService, commerceContext, commerceOrder,
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

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Reference
	private CommerceAddressRestrictionLocalService
		_commerceAddressRestrictionLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

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

	@Reference
	private Language _language;

}
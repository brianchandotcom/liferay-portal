/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.web.internal;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressRestrictionLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierLocalService;
import com.liferay.commerce.shipping.engine.fixed.util.comparator.CommerceShippingFixedOptionPriorityComparator;
import com.liferay.commerce.shipping.engine.fixed.web.internal.util.CommerceShippingFixedOptionEngineUtil;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.commerce.util.comparator.CommerceShippingOptionPriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
	property = "commerce.shipping.engine.key=" + FixedCommerceShippingEngine.KEY,
	service = CommerceShippingEngine.class
)
public class FixedCommerceShippingEngine implements CommerceShippingEngine {

	public static final String KEY = "fixed";

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
				false, commerceOrder, locale);
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
		return _language.get(
			_getResourceBundle(locale), "fixed-shipping-description");
	}

	@Override
	public List<CommerceShippingOption> getEnabledCommerceShippingOptions(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			Locale locale)
		throws CommerceShippingEngineException {

		List<CommerceShippingOption> commerceShippingOptions =
			new ArrayList<>();

		try {
			commerceShippingOptions = _getCommerceShippingOptions(
				true, commerceOrder, locale);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return commerceShippingOptions;
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(_getResourceBundle(locale), "flat-rate");
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
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new CommerceShippingFixedOptionPriorityComparator());
	}

	private List<CommerceShippingOption> _getCommerceShippingOptions(
			boolean checkEligibility, CommerceOrder commerceOrder,
			Locale locale)
		throws PortalException {

		List<CommerceShippingOption> commerceShippingOptions =
			new ArrayList<>();

		long commerceCountryId = 0;

		CommerceAddress commerceAddress = commerceOrder.getShippingAddress();

		if (commerceAddress != null) {
			commerceCountryId = commerceAddress.getCountryId();
		}

		List<CommerceShippingFixedOption> commerceShippingFixedOptions = null;

		if (checkEligibility) {
			commerceShippingFixedOptions =
				CommerceShippingFixedOptionEngineUtil.
					getEligibleCommerceShippingFixedOptions(
						commerceOrder.getCommerceOrderTypeId(),
						_commerceShippingFixedOptionQualifierLocalService,
						_getCommerceShippingFixedOptions(
							commerceOrder.getGroupId()));
		}
		else {
			commerceShippingFixedOptions = _getCommerceShippingFixedOptions(
				commerceOrder.getGroupId());
		}

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

			String key = commerceShippingFixedOption.getKey();
			String name = commerceShippingFixedOption.getName(locale);
			double priority = commerceShippingFixedOption.getPriority();

			if (_commerceShippingHelper.isFreeShipping(commerceOrder)) {
				commerceShippingOptions.add(
					new CommerceShippingOption(
						BigDecimal.ZERO, KEY, key, name, priority));

				continue;
			}

			BigDecimal amount = commerceShippingFixedOption.getAmount();

			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
					commerceOrder.getGroupId());

			CommerceCurrency commerceCurrency =
				commerceOrder.getCommerceCurrency();

			String commerceCurrencyCode = commerceCurrency.getCode();

			if (!commerceCurrencyCode.equals(
					commerceChannel.getCommerceCurrencyCode())) {

				CommerceCurrency commerceChannelCommerceCurrency =
					_commerceCurrencyLocalService.getCommerceCurrency(
						commerceOrder.getCompanyId(),
						commerceChannel.getCommerceCurrencyCode());

				amount = amount.divide(
					commerceChannelCommerceCurrency.getRate(),
					RoundingMode.valueOf(
						commerceChannelCommerceCurrency.getRoundingMode()));

				amount = amount.multiply(commerceCurrency.getRate());
			}

			commerceShippingOptions.add(
				new CommerceShippingOption(amount, KEY, key, name, priority));
		}

		return ListUtil.sort(
			commerceShippingOptions,
			new CommerceShippingOptionPriorityComparator());
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FixedCommerceShippingEngine.class);

	@Reference
	private CommerceAddressRestrictionLocalService
		_commerceAddressRestrictionLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Reference
	private CommerceShippingFixedOptionQualifierLocalService
		_commerceShippingFixedOptionQualifierLocalService;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private Language _language;

}
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

package com.liferay.commerce.order.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.order.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.commerce.order.web.internal.model.OrderItem;
import com.liferay.commerce.price.CommerceOrderItemPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPSubscriptionInfo;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.CPSubscriptionType;
import com.liferay.commerce.product.util.CPSubscriptionTypeRegistry;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ORDER_ITEMS,
	service = ClayDataSetDataProvider.class
)
public class CommerceOrderItemDataSetDataProvider
	implements ClayDataSetDataProvider<OrderItem> {

	@Override
	public List<OrderItem> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<OrderItem> orderItems = new ArrayList<>();

		try {
			BaseModelSearchResult<CommerceOrderItem> baseModelSearchResult =
				_getBaseModelSearchResult(
					httpServletRequest, filter, pagination, sort);

			orderItems = _getOrderItems(
				baseModelSearchResult.getBaseModels(), httpServletRequest);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return orderItems;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		BaseModelSearchResult<CommerceOrderItem> baseModelSearchResult =
			_getBaseModelSearchResult(httpServletRequest, filter, null, null);

		return baseModelSearchResult.getLength();
	}

	private BaseModelSearchResult<CommerceOrderItem> _getBaseModelSearchResult(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		int start = 0;
		int end = 0;

		if (pagination != null) {
			start = pagination.getStartPosition();
			end = pagination.getEndPosition();
		}

		return _commerceOrderItemService.searchCommerceOrderItems(
			commerceOrderId, 0, filter.getKeywords(), start, end, sort);
	}

	private List<OrderItem> _getChildOrderItems(
			CommerceOrderItem commerceOrderItem,
			HttpServletRequest httpServletRequest)
		throws Exception {

		List<CommerceOrderItem> childCommerceOrderItems =
			_commerceOrderItemService.getChildCommerceOrderItems(
				commerceOrderItem.getCommerceOrderItemId());

		return _getOrderItems(childCommerceOrderItems, httpServletRequest);
	}

	private String _getDiscount(
			CommerceOrderItemPrice commerceOrderItemPrice, Locale locale)
		throws Exception {

		CommerceMoney discountAmountCommerceMoney =
			commerceOrderItemPrice.getDiscountAmount();

		return HtmlUtil.escape(discountAmountCommerceMoney.format(locale));
	}

	private String _getImage(CommerceOrderItem commerceOrderItem)
		throws Exception {

		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		return cpDefinition.getDefaultImageThumbnailSrc();
	}

	private List<OrderItem> _getOrderItems(
			List<CommerceOrderItem> commerceOrderItems,
			HttpServletRequest httpServletRequest)
		throws Exception {

		if (commerceOrderItems.isEmpty()) {
			return Collections.emptyList();
		}

		List<OrderItem> orderItems = new ArrayList<>();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Locale locale = themeDisplay.getLocale();

			Format dateTimeFormat = FastDateFormatFactoryUtil.getDate(
				locale, themeDisplay.getTimeZone());

			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			CommerceOrderItemPrice commerceOrderItemPrice =
				_commerceOrderPriceCalculation.getCommerceOrderItemPrice(
					commerceOrder.getCommerceCurrency(), commerceOrderItem);

			String name = commerceOrderItem.getName(locale);

			StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

			List<KeyValuePair> keyValuePairs =
				_cpInstanceHelper.getKeyValuePairs(
					commerceOrderItem.getCPDefinitionId(),
					commerceOrderItem.getJson(), locale);

			for (KeyValuePair keyValuePair : keyValuePairs) {
				stringJoiner.add(keyValuePair.getValue());
			}

			orderItems.add(
				new OrderItem(
					commerceOrderItem.getDeliveryGroup(),
					_getDiscount(commerceOrderItemPrice, locale),
					new ImageField(
						name, "rounded", "lg", _getImage(commerceOrderItem)),
					name, stringJoiner.toString(),
					commerceOrderItem.getCommerceOrderId(),
					commerceOrderItem.getCommerceOrderItemId(),
					_getChildOrderItems(commerceOrderItem, httpServletRequest),
					commerceOrderItem.getParentCommerceOrderItemId(),
					_getPrice(commerceOrderItemPrice, locale),
					commerceOrderItem.getQuantity(),
					_getRequestedDeliveryDateTime(
						dateTimeFormat,
						commerceOrderItem.getRequestedDeliveryDate()),
					commerceOrderItem.getSku(),
					_getSubscriptionDuration(
						commerceOrderItem, httpServletRequest),
					_getSubscriptionPeriod(
						commerceOrderItem, httpServletRequest),
					_getTotal(commerceOrderItemPrice, locale)));
		}

		return orderItems;
	}

	private String _getPeriodKey(
		HttpServletRequest httpServletRequest, String period, boolean plural) {

		if (plural) {
			return LanguageUtil.get(
				httpServletRequest,
				StringUtil.toLowerCase(period + CharPool.LOWER_CASE_S));
		}

		return LanguageUtil.get(httpServletRequest, period);
	}

	private String _getPrice(
			CommerceOrderItemPrice commerceOrderItemPrice, Locale locale)
		throws Exception {

		CommerceMoney unitPriceCommerceMoney =
			commerceOrderItemPrice.getUnitPrice();

		return HtmlUtil.escape(unitPriceCommerceMoney.format(locale));
	}

	private String _getRequestedDeliveryDateTime(
		Format dateTimeFormat, Date requestedDeliveryDate) {

		if (requestedDeliveryDate == null) {
			return StringPool.BLANK;
		}

		return dateTimeFormat.format(requestedDeliveryDate);
	}

	private String _getSubscriptionDuration(
			CommerceOrderItem commerceOrderItem,
			HttpServletRequest httpServletRequest)
		throws Exception {

		String subscriptionDuration = StringPool.BLANK;

		if (commerceOrderItem.isSubscription()) {
			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			if (commerceOrder.isOpen()) {
				CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

				if ((cpInstance == null) ||
					(cpInstance.getCPSubscriptionInfo() == null)) {

					return subscriptionDuration;
				}

				CPSubscriptionInfo cpSubscriptionInfo =
					cpInstance.getCPSubscriptionInfo();

				String period = StringPool.BLANK;

				CPSubscriptionType cpSubscriptionType =
					_cpSubscriptionTypeRegistry.getCPSubscriptionType(
						cpSubscriptionInfo.getSubscriptionType());

				if (cpSubscriptionType != null) {
					period = cpSubscriptionType.getLabel(LocaleUtil.US);
				}

				long duration = cpSubscriptionInfo.getMaxSubscriptionCycles();

				if (duration > 0) {
					subscriptionDuration = LanguageUtil.format(
						httpServletRequest, "duration-x-x",
						new Object[] {
							duration,
							_getPeriodKey(
								httpServletRequest, period, duration != 1)
						});
				}
			}
			else {
				CommerceSubscriptionEntry commerceSubscriptionEntry =
					_commerceSubscriptionEntryLocalService.
						fetchCommerceSubscriptionEntry(
							commerceOrderItem.getCommerceOrderItemId());

				if (commerceSubscriptionEntry == null) {
					return subscriptionDuration;
				}

				String period = StringPool.BLANK;

				CPSubscriptionType cpSubscriptionType =
					_cpSubscriptionTypeRegistry.getCPSubscriptionType(
						commerceSubscriptionEntry.getSubscriptionType());

				if (cpSubscriptionType != null) {
					period = cpSubscriptionType.getLabel(LocaleUtil.US);
				}

				long duration =
					commerceSubscriptionEntry.getMaxSubscriptionCycles();

				subscriptionDuration = LanguageUtil.format(
					httpServletRequest, "duration-x-x",
					new Object[] {
						duration,
						_getPeriodKey(httpServletRequest, period, duration != 1)
					});
			}
		}

		return subscriptionDuration;
	}

	private String _getSubscriptionPeriod(
			CommerceOrderItem commerceOrderItem,
			HttpServletRequest httpServletRequest)
		throws Exception {

		String subscriptionPeriod = StringPool.BLANK;

		if (commerceOrderItem.isSubscription()) {
			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			if (commerceOrder.isOpen()) {
				CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

				if ((cpInstance == null) ||
					(cpInstance.getCPSubscriptionInfo() == null)) {

					return subscriptionPeriod;
				}

				CPSubscriptionInfo cpSubscriptionInfo =
					cpInstance.getCPSubscriptionInfo();

				String period = StringPool.BLANK;

				CPSubscriptionType cpSubscriptionType =
					_cpSubscriptionTypeRegistry.getCPSubscriptionType(
						cpSubscriptionInfo.getSubscriptionType());

				if (cpSubscriptionType != null) {
					period = cpSubscriptionType.getLabel(LocaleUtil.US);
				}

				int subscriptionLength =
					cpSubscriptionInfo.getSubscriptionLength();

				subscriptionPeriod = LanguageUtil.format(
					httpServletRequest, "every-x-x",
					new Object[] {
						subscriptionLength,
						_getPeriodKey(
							httpServletRequest, period, subscriptionLength != 1)
					});
			}
			else {
				CommerceSubscriptionEntry commerceSubscriptionEntry =
					_commerceSubscriptionEntryLocalService.
						fetchCommerceSubscriptionEntry(
							commerceOrderItem.getCommerceOrderItemId());

				if (commerceSubscriptionEntry == null) {
					return subscriptionPeriod;
				}

				String period = StringPool.BLANK;

				CPSubscriptionType cpSubscriptionType =
					_cpSubscriptionTypeRegistry.getCPSubscriptionType(
						commerceSubscriptionEntry.getSubscriptionType());

				if (cpSubscriptionType != null) {
					period = cpSubscriptionType.getLabel(LocaleUtil.US);
				}

				int subscriptionLength =
					commerceSubscriptionEntry.getSubscriptionLength();

				subscriptionPeriod = LanguageUtil.format(
					httpServletRequest, "every-x-x",
					new Object[] {
						subscriptionLength,
						_getPeriodKey(
							httpServletRequest, period, subscriptionLength != 1)
					});
			}
		}

		return subscriptionPeriod;
	}

	private String _getTotal(
			CommerceOrderItemPrice commerceOrderItemPrice, Locale locale)
		throws Exception {

		CommerceMoney finalPriceCommerceMoney =
			commerceOrderItemPrice.getFinalPrice();

		return HtmlUtil.escape(finalPriceCommerceMoney.format(locale));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemDataSetDataProvider.class);

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPSubscriptionTypeRegistry _cpSubscriptionTypeRegistry;

}
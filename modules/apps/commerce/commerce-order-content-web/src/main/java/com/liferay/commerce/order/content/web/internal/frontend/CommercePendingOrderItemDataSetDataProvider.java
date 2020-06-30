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

package com.liferay.commerce.order.content.web.internal.frontend;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.order.content.web.internal.frontend.util.CommerceOrderClayTableUtil;
import com.liferay.commerce.order.content.web.internal.model.OrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPSubscriptionInfo;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.CPSubscriptionType;
import com.liferay.commerce.product.util.CPSubscriptionTypeRegistry;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDER_ITEMS,
	service = CommerceDataSetDataProvider.class
)
public class CommercePendingOrderItemDataSetDataProvider
	implements CommerceDataSetDataProvider<OrderItem> {

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		OrderFilterImpl orderFilterImpl = (OrderFilterImpl)filter;

		return _commerceOrderItemService.getCommerceOrderItemsCount(
			orderFilterImpl.getCommerceOrderId());
	}

	@Override
	public List<OrderItem> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<OrderItem> orderItems = new ArrayList<>();

		OrderFilterImpl orderFilterImpl = (OrderFilterImpl)filter;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrderItemService.getCommerceOrderItems(
				orderFilterImpl.getCommerceOrderId(),
				pagination.getStartPosition(), pagination.getEndPosition());

		CommerceOrder commerceOrder = null;
		Map<Long, List<CommerceOrderValidatorResult>>
			commerceOrderValidatorResultMap = null;

		if (!commerceOrderItems.isEmpty()) {
			CommerceOrderItem firstCommerceOrderItem = commerceOrderItems.get(
				0);

			commerceOrder = _commerceOrderService.getCommerceOrder(
				firstCommerceOrderItem.getCommerceOrderId());

			commerceOrderValidatorResultMap =
				_commerceOrderValidatorRegistry.
					getCommerceOrderValidatorResults(
						themeDisplay.getLocale(), commerceOrder);
		}

		try {
			for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
				String formattedUnitPrice = StringPool.BLANK;
				String formattedPromoPrice = StringPool.BLANK;
				String formattedDiscountAmount = StringPool.BLANK;
				String formattedFinalPrice = StringPool.BLANK;

				CommerceMoney unitPriceMoney =
					commerceOrderItem.getUnitPriceMoney();

				if (unitPriceMoney != null) {
					formattedUnitPrice = unitPriceMoney.format(
						themeDisplay.getLocale());
				}

				CommerceMoney promoPriceMoney =
					commerceOrderItem.getPromoPriceMoney();

				BigDecimal promoPrice = promoPriceMoney.getPrice();

				if ((promoPriceMoney != null) &&
					(promoPrice.compareTo(BigDecimal.ZERO) > 0)) {

					formattedPromoPrice = promoPriceMoney.format(
						themeDisplay.getLocale());
				}

				CommerceMoney finalPriceMoney =
					commerceOrderItem.getFinalPriceMoney();

				if (finalPriceMoney != null) {
					formattedFinalPrice = finalPriceMoney.format(
						themeDisplay.getLocale());
				}

				CommerceMoney discountAmount =
					commerceOrderItem.getDiscountAmountMoney();

				if (discountAmount != null) {
					formattedDiscountAmount = discountAmount.format(
						themeDisplay.getLocale());
				}

				List<CommerceOrderValidatorResult>
					commerceOrderValidatorResults =
						commerceOrderValidatorResultMap.get(
							commerceOrderItem.getCommerceOrderItemId());

				List<String> errorMessages = new ArrayList<>();

				for (CommerceOrderValidatorResult commerceOrderValidatorResult :
						commerceOrderValidatorResults) {

					errorMessages.add(
						commerceOrderValidatorResult.getLocalizedMessage());
				}

				String formattedSubscriptionPeriod = null;

				CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

				if ((cpInstance != null) &&
					(cpInstance.getCPSubscriptionInfo() != null)) {

					CPSubscriptionInfo cpSubscriptionInfo =
						cpInstance.getCPSubscriptionInfo();

					Locale locale = themeDisplay.getLocale();

					String period = StringPool.BLANK;

					CPSubscriptionType cpSubscriptionType =
						_cpSubscriptionTypeRegistry.getCPSubscriptionType(
							cpSubscriptionInfo.getSubscriptionType());

					if (cpSubscriptionType != null) {
						period = cpSubscriptionType.getLabel(locale);

						if (cpSubscriptionInfo.getSubscriptionLength() > 1) {
							period = LanguageUtil.get(
								locale,
								StringUtil.toLowerCase(
									cpSubscriptionType.getLabel(LocaleUtil.US) +
										CharPool.LOWER_CASE_S));
						}
					}

					formattedSubscriptionPeriod = LanguageUtil.format(
						locale, "every-x-x",
						new Object[] {
							cpSubscriptionInfo.getSubscriptionLength(), period
						});
				}

				List<KeyValuePair> keyValuePairs =
					_cpInstanceHelper.getKeyValuePairs(
						commerceOrderItem.getCPDefinitionId(),
						commerceOrderItem.getJson(), themeDisplay.getLocale());

				StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

				for (KeyValuePair keyValuePair : keyValuePairs) {
					stringJoiner.add(keyValuePair.getValue());
				}

				orderItems.add(
					new OrderItem(
						commerceOrderItem.getCommerceOrderItemId(),
						commerceOrderItem.getCommerceOrderId(),
						commerceOrderItem.getSku(),
						commerceOrderItem.getName(themeDisplay.getLocale()),
						stringJoiner.toString(), formattedUnitPrice,
						formattedPromoPrice, formattedDiscountAmount,
						commerceOrderItem.getQuantity(), formattedFinalPrice,
						_cpInstanceHelper.getCPInstanceThumbnailSrc(
							commerceOrderItem.getCPInstanceId()),
						CommerceOrderClayTableUtil.getViewShipmentURL(
							commerceOrderItem.getCommerceOrderId(),
							themeDisplay),
						0, ArrayUtil.toStringArray(errorMessages),
						formattedSubscriptionPeriod));
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return orderItems;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePendingOrderItemDataSetDataProvider.class);

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPSubscriptionTypeRegistry _cpSubscriptionTypeRegistry;

}
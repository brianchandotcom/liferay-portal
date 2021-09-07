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
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.order.content.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.commerce.order.content.web.internal.model.OrderItem;
import com.liferay.commerce.price.CommerceOrderItemPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPSubscriptionInfo;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.CPSubscriptionType;
import com.liferay.commerce.product.util.CPSubscriptionTypeRegistry;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
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
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDER_ITEMS,
	service = ClayDataSetDataProvider.class
)
public class PendingCommerceOrderItemDataSetDataProvider
	implements ClayDataSetDataProvider<OrderItem> {

	@Override
	public List<OrderItem> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		BaseModelSearchResult<CommerceOrderItem> baseModelSearchResult =
			_getBaseModelSearchResult(
				httpServletRequest, filter, pagination, sort);

		try {
			List<CommerceOrderItem> commerceOrderItems =
				baseModelSearchResult.getBaseModels();

			return _getOrderItems(commerceOrderItems, httpServletRequest);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return Collections.emptyList();
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		OrderFilterImpl orderFilterImpl = (OrderFilterImpl)filter;

		return _commerceOrderItemService.getCommerceOrderItemsCount(
			orderFilterImpl.getCommerceOrderId());
	}

	private String _formatDiscountAmount(
			CommerceOrderItemPrice commerceOrderItemPrice, Locale locale)
		throws Exception {

		if (commerceOrderItemPrice.getDiscountAmount() == null) {
			return StringPool.BLANK;
		}

		CommerceMoney discountAmountCommerceMoney =
			commerceOrderItemPrice.getDiscountAmount();

		return discountAmountCommerceMoney.format(locale);
	}

	private String _formatFinalPrice(
			CommerceOrderItemPrice commerceOrderItemPrice, Locale locale)
		throws Exception {

		if (commerceOrderItemPrice.getFinalPrice() == null) {
			return StringPool.BLANK;
		}

		CommerceMoney finalPriceCommerceMoney =
			commerceOrderItemPrice.getFinalPrice();

		return finalPriceCommerceMoney.format(locale);
	}

	private String _formatPromoPrice(
			CommerceOrderItemPrice commerceOrderItemPrice, Locale locale)
		throws Exception {

		CommerceMoney promoPriceCommerceMoney =
			commerceOrderItemPrice.getPromoPrice();

		if (promoPriceCommerceMoney == null) {
			return StringPool.BLANK;
		}

		BigDecimal price = promoPriceCommerceMoney.getPrice();

		if (price.compareTo(BigDecimal.ZERO) <= 0) {
			return StringPool.BLANK;
		}

		return promoPriceCommerceMoney.format(locale);
	}

	private String _formatSubscriptionPeriod(
			CommerceOrderItem commerceOrderItem, Locale locale)
		throws Exception {

		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

		if ((cpInstance == null) ||
			(cpInstance.getCPSubscriptionInfo() == null)) {

			return null;
		}

		CPSubscriptionInfo cpSubscriptionInfo =
			cpInstance.getCPSubscriptionInfo();

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

		return LanguageUtil.format(
			locale, "every-x-x",
			new Object[] {cpSubscriptionInfo.getSubscriptionLength(), period});
	}

	private String _formatUnitPrice(
			CommerceOrderItemPrice commerceOrderItemPrice, Locale locale)
		throws Exception {

		if (commerceOrderItemPrice.getUnitPrice() == null) {
			return StringPool.BLANK;
		}

		CommerceMoney unitPriceCommerceMoney =
			commerceOrderItemPrice.getUnitPrice();

		return unitPriceCommerceMoney.format(locale);
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

	private long _getCommerceOptionValueCPDefinitionId(
		CommerceOrderItem commerceOrderItem) {

		if (!commerceOrderItem.hasParentCommerceOrderItem()) {
			return commerceOrderItem.getCPDefinitionId();
		}

		return commerceOrderItem.getParentCommerceOrderItemCPDefinitionId();
	}

	private String[] _getCommerceOrderErrorMessages(
		CommerceOrderItem commerceOrderItem,
		Map<Long, List<CommerceOrderValidatorResult>>
			commerceOrderValidatorResultMap) {

		List<String> errorMessages = new ArrayList<>();

		List<CommerceOrderValidatorResult> commerceOrderValidatorResults =
			commerceOrderValidatorResultMap.get(
				commerceOrderItem.getCommerceOrderItemId());

		for (CommerceOrderValidatorResult commerceOrderValidatorResult :
				commerceOrderValidatorResults) {

			errorMessages.add(
				commerceOrderValidatorResult.getLocalizedMessage());
		}

		return ArrayUtil.toStringArray(errorMessages);
	}

	private String _getCommerceOrderOptions(
			CommerceOrderItem commerceOrderItem, Locale locale)
		throws Exception {

		StringJoiner stringJoiner = new StringJoiner(
			StringPool.COMMA_AND_SPACE);

		List<KeyValuePair> commerceOptionValueKeyValuePairs =
			_cpInstanceHelper.getKeyValuePairs(
				_getCommerceOptionValueCPDefinitionId(commerceOrderItem),
				commerceOrderItem.getJson(), locale);

		for (KeyValuePair keyValuePair : commerceOptionValueKeyValuePairs) {
			stringJoiner.add(keyValuePair.getValue());
		}

		return stringJoiner.toString();
	}

	private Map<Long, List<CommerceOrderValidatorResult>>
			_getCommerceOrderValidatorResultMap(
				List<CommerceOrderItem> commerceOrderItems, Locale locale)
		throws Exception {

		if (commerceOrderItems.isEmpty()) {
			return Collections.emptyMap();
		}

		CommerceOrderItem commerceOrderItem = commerceOrderItems.get(0);

		return _commerceOrderValidatorRegistry.getCommerceOrderValidatorResults(
			locale,
			_commerceOrderService.getCommerceOrder(
				commerceOrderItem.getCommerceOrderId()));
	}

	private List<OrderItem> _getOrderItems(
			List<CommerceOrderItem> commerceOrderItems,
			HttpServletRequest httpServletRequest)
		throws Exception {

		if (commerceOrderItems.isEmpty()) {
			return Collections.emptyList();
		}

		List<OrderItem> orderItems = new ArrayList<>();

		Locale locale = _portal.getLocale(httpServletRequest);

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			CommerceOrderItemPrice commerceOrderItemPrice =
				_commerceOrderPriceCalculation.getCommerceOrderItemPrice(
					commerceOrder.getCommerceCurrency(), commerceOrderItem);

			Map<Long, List<CommerceOrderValidatorResult>>
				commerceOrderValidatorResultMap =
					_getCommerceOrderValidatorResultMap(
						commerceOrderItems, locale);

			orderItems.add(
				new OrderItem(
					commerceOrderItem.getCommerceOrderItemId(),
					commerceOrderItem.getCommerceOrderId(),
					commerceOrderItem.getSku(),
					commerceOrderItem.getName(locale),
					_getCommerceOrderOptions(commerceOrderItem, locale),
					_getChildOrderItems(commerceOrderItem, httpServletRequest),
					commerceOrderItem.getParentCommerceOrderItemId(),
					_formatUnitPrice(commerceOrderItemPrice, locale),
					_formatPromoPrice(commerceOrderItemPrice, locale),
					_formatDiscountAmount(commerceOrderItemPrice, locale),
					commerceOrderItem.getQuantity(),
					_formatFinalPrice(commerceOrderItemPrice, locale),
					_cpInstanceHelper.getCPInstanceThumbnailSrc(
						commerceOrderItem.getCPInstanceId()),
					0,
					_getCommerceOrderErrorMessages(
						commerceOrderItem, commerceOrderValidatorResultMap),
					_formatSubscriptionPeriod(commerceOrderItem, locale)));
		}

		return orderItems;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PendingCommerceOrderItemDataSetDataProvider.class);

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPSubscriptionTypeRegistry _cpSubscriptionTypeRegistry;

	@Reference
	private Portal _portal;

}
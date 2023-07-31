/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.util;

import com.liferay.commerce.constants.CommercePriceConstants;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.CommerceOrderItemPrice;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderItemUtil {

	public static String formatDiscountAmount(
			CommerceOrderItemPrice commerceOrderItemPrice, Locale locale)
		throws Exception {

		if (commerceOrderItemPrice.isPriceOnApplication()) {
			return StringPool.DASH;
		}

		if (commerceOrderItemPrice.getDiscountAmount() == null) {
			return StringPool.BLANK;
		}

		CommerceMoney discountAmountCommerceMoney =
			commerceOrderItemPrice.getDiscountAmount();

		return discountAmountCommerceMoney.format(locale);
	}

	public static String formatPromoPrice(
			CommerceOrderItemPrice commerceOrderItemPrice, Locale locale)
		throws Exception {

		if (commerceOrderItemPrice.isPriceOnApplication()) {
			return StringPool.DASH;
		}

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

	public static String formatTotalPrice(
			CommerceOrderItemPrice commerceOrderItemPrice, Locale locale)
		throws Exception {

		if (commerceOrderItemPrice.isPriceOnApplication()) {
			return StringPool.DASH;
		}

		if (commerceOrderItemPrice.getFinalPrice() == null) {
			return StringPool.BLANK;
		}

		CommerceMoney finalPriceCommerceMoney =
			commerceOrderItemPrice.getFinalPrice();

		return finalPriceCommerceMoney.format(locale);
	}

	public static String formatUnitPrice(
			CommerceOrderItemPrice commerceOrderItemPrice, Language language,
			Locale locale)
		throws Exception {

		if (commerceOrderItemPrice.isPriceOnApplication()) {
			return language.get(
				locale,
				CommercePriceConstants.PRICE_VALUE_PRICE_ON_APPLICATION);
		}

		CommerceMoney unitPriceCommerceMoney =
			commerceOrderItemPrice.getUnitPrice();

		if (unitPriceCommerceMoney == null) {
			return StringPool.BLANK;
		}

		CommerceMoney promoPriceCommerceMoney =
			commerceOrderItemPrice.getPromoPrice();

		if (BigDecimalUtil.eq(
				unitPriceCommerceMoney.getPrice(), BigDecimal.ZERO) &&
			(promoPriceCommerceMoney != null) &&
			BigDecimalUtil.gt(
				promoPriceCommerceMoney.getPrice(), BigDecimal.ZERO)) {

			return language.get(
				locale,
				CommercePriceConstants.PRICE_VALUE_PRICE_ON_APPLICATION);
		}

		return unitPriceCommerceMoney.format(locale);
	}

	public static String getOptions(
			CommerceOrderItem commerceOrderItem,
			CPInstanceHelper cpInstanceHelper, Locale locale)
		throws Exception {

		StringJoiner stringJoiner = new StringJoiner(
			StringPool.COMMA_AND_SPACE);

		List<KeyValuePair> commerceOptionValueKeyValuePairs =
			cpInstanceHelper.getKeyValuePairs(
				_getCommerceOrderItemCPDefinitionId(commerceOrderItem),
				commerceOrderItem.getJson(), locale);

		for (KeyValuePair keyValuePair : commerceOptionValueKeyValuePairs) {
			stringJoiner.add(keyValuePair.getValue());
		}

		return stringJoiner.toString();
	}

	public static String getThumbnailURL(
			long accountEntryId, CPInstance cpInstance,
			CPInstanceHelper cpInstanceHelper)
		throws Exception {

		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		return cpInstanceHelper.getCPInstanceThumbnailSrc(
			accountEntryId, cpInstance.getCPInstanceId());
	}

	public static String getURL(
			CPDefinitionHelper cpDefinitionHelper, CPInstance cpInstance,
			ThemeDisplay themeDisplay)
		throws Exception {

		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		return cpDefinitionHelper.getFriendlyURL(
			cpInstance.getCPDefinitionId(), themeDisplay);
	}

	private static long _getCommerceOrderItemCPDefinitionId(
		CommerceOrderItem commerceOrderItem) {

		if (!commerceOrderItem.hasParentCommerceOrderItem()) {
			return commerceOrderItem.getCPDefinitionId();
		}

		return commerceOrderItem.getParentCommerceOrderItemCPDefinitionId();
	}

}
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

package com.liferay.commerce.order.content.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.content.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.commerce.order.content.web.internal.model.PreviewOrderItem;
import com.liferay.commerce.order.importer.item.CommerceOrderImporterItem;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterType;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterTypeRegistry;
import com.liferay.commerce.price.CommerceOrderItemPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
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
	property = "clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PREVIEW_COMMERCE_ORDER_ITEMS,
	service = ClayDataSetDataProvider.class
)
public class PreviewCommerceOrderItemDataSetDataProvider
	implements ClayDataSetDataProvider<PreviewOrderItem> {

	@Override
	public List<PreviewOrderItem> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		_commerceOrderImporterItems = _getCommerceOrderImporterItems(
			httpServletRequest);

		if (_commerceOrderImporterItems == null) {
			return Collections.emptyList();
		}

		IntegerWrapper integerWrapper = new IntegerWrapper();
		Locale locale = _portal.getLocale(httpServletRequest);

		return TransformUtil.transform(
			_commerceOrderImporterItems,
			commerceOrderImporterItem -> {
				String externalReferenceCode = StringPool.BLANK;

				CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
					commerceOrderImporterItem.getCPInstanceId());

				if (cpInstance != null) {
					externalReferenceCode =
						cpInstance.getExternalReferenceCode();
				}

				CommerceOrderItemPrice commerceOrderItemPrice =
					commerceOrderImporterItem.getCommerceOrderItemPrice();

				return new PreviewOrderItem(
					externalReferenceCode,
					_getImportStatus(commerceOrderImporterItem, locale),
					_getCommerceOrderOptions(commerceOrderImporterItem, locale),
					commerceOrderImporterItem.getName(locale),
					commerceOrderImporterItem.getQuantity(),
					commerceOrderImporterItem.getReplacingSKU(),
					integerWrapper.increment(),
					commerceOrderImporterItem.getSKU(),
					_formatFinalPrice(commerceOrderItemPrice, locale),
					_formatUnitPrice(commerceOrderItemPrice, locale));
			});
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		int itemsCount = 0;

		if (_commerceOrderImporterItems != null) {
			itemsCount = _commerceOrderImporterItems.size();
		}

		_commerceOrderImporterItems = null;

		return itemsCount;
	}

	private String _formatFinalPrice(
		CommerceOrderItemPrice commerceOrderItemPrice, Locale locale) {

		if ((commerceOrderItemPrice == null) ||
			(commerceOrderItemPrice.getFinalPrice() == null)) {

			return StringPool.BLANK;
		}

		CommerceMoney finalPriceCommerceMoney =
			commerceOrderItemPrice.getFinalPrice();

		try {
			return finalPriceCommerceMoney.format(locale);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return StringPool.BLANK;
	}

	private String _formatUnitPrice(
		CommerceOrderItemPrice commerceOrderItemPrice, Locale locale) {

		if ((commerceOrderItemPrice == null) ||
			(commerceOrderItemPrice.getUnitPrice() == null)) {

			return StringPool.BLANK;
		}

		CommerceMoney unitPriceCommerceMoney =
			commerceOrderItemPrice.getUnitPrice();

		try {
			return unitPriceCommerceMoney.format(locale);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return StringPool.BLANK;
	}

	private long _getCommerceOptionValueCPDefinitionId(
		CommerceOrderImporterItem commerceOrderImporterItem) {

		if (!commerceOrderImporterItem.hasParentCommerceOrderItem()) {
			return commerceOrderImporterItem.getCPDefinitionId();
		}

		return commerceOrderImporterItem.
			getParentCommerceOrderItemCPDefinitionId();
	}

	private List<CommerceOrderImporterItem> _getCommerceOrderImporterItems(
		HttpServletRequest httpServletRequest) {

		try {
			CommerceOrderImporterType commerceOrderImporterType =
				_commerceOrderImporterTypeRegistry.getCommerceOrderImporterType(
					ParamUtil.getString(
						httpServletRequest, "commerceOrderImporterTypeKey"));

			if (commerceOrderImporterType == null) {
				return null;
			}

			Object commerceOrderImporterItem =
				commerceOrderImporterType.getCommerceOrderImporterItem(
					httpServletRequest);

			if (commerceOrderImporterItem == null) {
				return null;
			}

			return commerceOrderImporterType.getCommerceOrderImporterItems(
				_commerceOrderService.getCommerceOrder(
					ParamUtil.getLong(httpServletRequest, "commerceOrderId")),
				commerceOrderImporterItem);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	private String _getCommerceOrderOptions(
			CommerceOrderImporterItem commerceOrderImporterItem, Locale locale)
		throws PortalException {

		StringJoiner stringJoiner = new StringJoiner(
			StringPool.COMMA_AND_SPACE);

		List<KeyValuePair> commerceOptionValueKeyValuePairs =
			_cpInstanceHelper.getKeyValuePairs(
				_getCommerceOptionValueCPDefinitionId(
					commerceOrderImporterItem),
				commerceOrderImporterItem.getJSON(), locale);

		for (KeyValuePair keyValuePair : commerceOptionValueKeyValuePairs) {
			stringJoiner.add(keyValuePair.getValue());
		}

		return stringJoiner.toString();
	}

	private String _getImportStatus(
		CommerceOrderImporterItem commerceOrderImporterItem, Locale locale) {

		if (commerceOrderImporterItem.isValid()) {
			return LanguageUtil.get(locale, "ok");
		}

		String[] errorMessages = commerceOrderImporterItem.getErrorMessages();

		return errorMessages[0];
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreviewCommerceOrderItemDataSetDataProvider.class);

	private List<CommerceOrderImporterItem> _commerceOrderImporterItems;

	@Reference
	private CommerceOrderImporterTypeRegistry
		_commerceOrderImporterTypeRegistry;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private Portal _portal;

}
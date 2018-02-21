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

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
* @author Marco Leo
 */
public class OrderSummaryCheckoutStepDisplayContext {

	public OrderSummaryCheckoutStepDisplayContext(
			CommerceOrderValidatorRegistry commerceOrderValidatorRegistry,
			CommercePriceCalculator commercePriceCalculator,
			CommercePriceFormatter commercePriceFormatter,
			CPInstanceHelper cpInstanceHelper,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_commerceOrderValidatorRegistry = commerceOrderValidatorRegistry;
		_commercePriceCalculator = commercePriceCalculator;
		_commercePriceFormatter = commercePriceFormatter;
		_cpInstanceHelper = cpInstanceHelper;
		_httpServletRequest = httpServletRequest;

		_commerceOrder = (CommerceOrder)httpServletRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_ORDER);
	}

	public CommerceOrder getCommerceOrder() {
		return _commerceOrder;
	}

	public String getCommerceOrderItemThumbnailSrc(
			CommerceOrderItem commerceOrderItem, ThemeDisplay themeDisplay)
		throws Exception {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpInstanceHelper.getCPAttachmentFileEntries(
				commerceOrderItem.getCPDefinitionId(),
				commerceOrderItem.getJson(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE);

		if (cpAttachmentFileEntries.isEmpty()) {
			CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

			return cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntries.get(0);

		FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

		if (fileEntry == null) {
			return null;
		}

		return DLUtil.getThumbnailSrc(fileEntry, themeDisplay);
	}

	public String getCommerceOrderSubtotal() throws PortalException {
		double subtotal = _commercePriceCalculator.getSubtotal(
			getCommerceOrder());

		return _commercePriceFormatter.format(_httpServletRequest, subtotal);
	}

	public Map<Long, List<CommerceOrderValidatorResult>>
			getCommerceOrderValidatorResults()
		throws PortalException {

		return _commerceOrderValidatorRegistry.getCommerceOrderValidatorResults(
			_commerceOrder);
	}

	public String getFormattedPrice(CommerceOrderItem commerceOrderItem)
		throws PortalException {

		double price = _commercePriceCalculator.getPrice(commerceOrderItem);

		return _commercePriceFormatter.format(_httpServletRequest, price);
	}

	public List<KeyValuePair> getKeyValuePairs(String json, Locale locale)
		throws PortalException {

		return _cpInstanceHelper.getKeyValuePairs(json, locale);
	}

	private final CommerceOrder _commerceOrder;
	private final CommerceOrderValidatorRegistry
		_commerceOrderValidatorRegistry;
	private final CommercePriceCalculator _commercePriceCalculator;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CPInstanceHelper _cpInstanceHelper;
	private final HttpServletRequest _httpServletRequest;

}
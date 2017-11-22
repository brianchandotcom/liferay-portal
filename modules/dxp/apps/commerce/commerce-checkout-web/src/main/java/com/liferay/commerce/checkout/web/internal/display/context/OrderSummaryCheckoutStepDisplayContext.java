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

import com.liferay.commerce.cart.CommerceCartValidatorRegistry;
import com.liferay.commerce.cart.CommerceCartValidatorResult;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
* @author Marco Leo
 */
public class OrderSummaryCheckoutStepDisplayContext {

	public OrderSummaryCheckoutStepDisplayContext(
			CommerceCartService commerceCartService,
			CommerceCartValidatorRegistry commerceCartValidatorRegistry,
			CommercePriceCalculator commercePriceCalculator,
			CommercePriceFormatter commercePriceFormatter,
			CPInstanceHelper cpInstanceHelper,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_commerceCartService = commerceCartService;
		_commerceCartValidatorRegistry = commerceCartValidatorRegistry;
		_commercePriceCalculator = commercePriceCalculator;
		_commercePriceFormatter = commercePriceFormatter;
		_cpInstanceHelper = cpInstanceHelper;
		_httpServletRequest = httpServletRequest;

		long commerceCartId = ParamUtil.getLong(
			httpServletRequest, "commerceCartId");

		_commerceCart = _commerceCartService.getCommerceCart(commerceCartId);
	}

	public CommerceCart getCommerceCart() {
		return _commerceCart;
	}

	public String getCommerceCartItemThumb(
			CommerceCartItem commerceCartItem, ThemeDisplay themeDisplay)
		throws Exception {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpInstanceHelper.getCPAttachmentFileEntries(
				commerceCartItem.getCPDefinitionId(),
				commerceCartItem.getJson(),
				CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE);

		if (cpAttachmentFileEntries.isEmpty()) {
			CPDefinition cpDefinition = commerceCartItem.getCPDefinition();

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

	public String getCommerceCartTotal() throws PortalException {
		CommerceCart commerceCart = getCommerceCart();

		double total = _commercePriceCalculator.getTotal(commerceCart);

		return _commercePriceFormatter.format(_httpServletRequest, total);
	}

	public Map<Long, List<CommerceCartValidatorResult>>
			getCommerceCartValidatorResults()
		throws PortalException {

		return _commerceCartValidatorRegistry.getCommerceCartValidatorResults(
			_commerceCart);
	}

	public String getFormattedPrice(CommerceCartItem commerceCartItem)
		throws PortalException {

		double price = _commercePriceCalculator.getPrice(commerceCartItem);

		return _commercePriceFormatter.format(_httpServletRequest, price);
	}

	public List<KeyValuePair> parseJSONString(String json, Locale locale)
		throws PortalException {

		return _cpInstanceHelper.parseJSONString(json, locale);
	}

	private final CommerceCart _commerceCart;
	private final CommerceCartService _commerceCartService;
	private final CommerceCartValidatorRegistry _commerceCartValidatorRegistry;
	private final CommercePriceCalculator _commercePriceCalculator;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CPInstanceHelper _cpInstanceHelper;
	private final HttpServletRequest _httpServletRequest;

}
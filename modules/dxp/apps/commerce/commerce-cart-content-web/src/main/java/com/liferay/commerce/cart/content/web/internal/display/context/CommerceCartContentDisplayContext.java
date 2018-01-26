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

package com.liferay.commerce.cart.content.web.internal.display.context;

import com.liferay.commerce.cart.CommerceCartValidatorRegistry;
import com.liferay.commerce.cart.CommerceCartValidatorResult;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartConstants;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.commerce.util.CommercePriceCalculator;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceCartContentDisplayContext {

	public CommerceCartContentDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		CommerceCartHelper commerceCartHelper,
		CommerceCartItemService commerceCartItemService,
		CommerceCartValidatorRegistry commerceCartValidatorRegistry,
		CommercePriceCalculator commercePriceCalculator,
		CommercePriceFormatter commercePriceFormatter,
		CPDefinitionHelper cpDefinitionHelper,
		CPInstanceHelper cpInstanceHelper) {

		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		_commerceCartHelper = commerceCartHelper;
		_commerceCartItemService = commerceCartItemService;
		_commerceCartValidatorRegistry = commerceCartValidatorRegistry;
		_commercePriceCalculator = commercePriceCalculator;
		_commercePriceFormatter = commercePriceFormatter;
		this.cpDefinitionHelper = cpDefinitionHelper;
		this.cpInstanceHelper = cpInstanceHelper;

		CPRequestHelper cpRequestHelper = new CPRequestHelper(
			httpServletRequest);

		liferayPortletRequest = cpRequestHelper.getLiferayPortletRequest();
		liferayPortletResponse = cpRequestHelper.getLiferayPortletResponse();
	}

	public CommerceCart getCommerceCart() throws PortalException {
		if (_commerceCart != null) {
			return _commerceCart;
		}

		_commerceCart = _commerceCartHelper.getCurrentCommerceCart(
			httpServletRequest, httpServletResponse, getCommerceCartType());

		return _commerceCart;
	}

	public long getCommerceCartId() throws PortalException {
		CommerceCart commerceCart = getCommerceCart();

		if (commerceCart == null) {
			return 0;
		}

		return commerceCart.getCommerceCartId();
	}

	public String getCommerceCartItemThumbnailSrc(
			CommerceCartItem commerceCartItem, ThemeDisplay themeDisplay)
		throws Exception {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			cpInstanceHelper.getCPAttachmentFileEntries(
				commerceCartItem.getCPDefinitionId(),
				commerceCartItem.getJson(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE);

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

	public String getCommerceCartSubtotal() throws PortalException {
		double subtotal = _commercePriceCalculator.getSubtotal(
			getCommerceCart());

		return _commercePriceFormatter.format(httpServletRequest, subtotal);
	}

	public int getCommerceCartType() {
		return ParamUtil.getInteger(
			httpServletRequest, "type", CommerceCartConstants.TYPE_CART);
	}

	public Map<Long, List<CommerceCartValidatorResult>>
			getCommerceCartValidatorResults()
		throws PortalException {

		return _commerceCartValidatorRegistry.getCommerceCartValidatorResults(
			_commerceCart);
	}

	public String getCPDefinitionURL(
			long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		return cpDefinitionHelper.getFriendlyURL(cpDefinitionId, themeDisplay);
	}

	public String getFormattedPrice(CommerceCartItem commerceCartItem)
		throws PortalException {

		double price = _commercePriceCalculator.getPrice(commerceCartItem);

		return _commercePriceFormatter.format(httpServletRequest, price);
	}

	public List<KeyValuePair> getKeyValuePairs(String json, Locale locale)
		throws PortalException {

		return cpInstanceHelper.getKeyValuePairs(json, locale);
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		return portletURL;
	}

	public SearchContainer<CommerceCartItem> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-items-were-found");

		int total = _commerceCartItemService.getCommerceCartItemsCount(
			getCommerceCartId());

		_searchContainer.setTotal(total);

		List<CommerceCartItem> results =
			_commerceCartItemService.getCommerceCartItems(
				getCommerceCartId(), _searchContainer.getStart(),
				_searchContainer.getEnd());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public boolean isValidCommerceCart() throws PortalException {
		return _commerceCartValidatorRegistry.isValid(_commerceCart);
	}

	public List<CommerceCartValidatorResult> validateCommerceCartItem(
			long commerceCartItemId)
		throws PortalException {

		CommerceCartItem commerceCartItem =
			_commerceCartItemService.fetchCommerceCartItem(commerceCartItemId);

		return _commerceCartValidatorRegistry.validate(commerceCartItem);
	}

	protected final CPDefinitionHelper cpDefinitionHelper;
	protected final CPInstanceHelper cpInstanceHelper;
	protected final HttpServletRequest httpServletRequest;
	protected final HttpServletResponse httpServletResponse;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;

	private CommerceCart _commerceCart;
	private final CommerceCartHelper _commerceCartHelper;
	private final CommerceCartItemService _commerceCartItemService;
	private final CommerceCartValidatorRegistry _commerceCartValidatorRegistry;
	private final CommercePriceCalculator _commercePriceCalculator;
	private final CommercePriceFormatter _commercePriceFormatter;
	private SearchContainer<CommerceCartItem> _searchContainer;

}
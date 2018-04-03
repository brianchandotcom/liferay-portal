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

import com.liferay.commerce.cart.content.web.internal.display.context.util.CommerceCartContentRequestHelper;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommercePriceCalculationLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
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
		CommerceOrderHelper commerceOrderHelper,
		CommerceOrderItemService commerceOrderItemService,
		CommerceOrderValidatorRegistry commerceOrderValidatorRegistry,
		CommercePriceCalculationLocalService
			commercePriceCalculationLocalService,
		CPDefinitionHelper cpDefinitionHelper,
		CPInstanceHelper cpInstanceHelper) {

		_httpServletResponse = httpServletResponse;
		_commerceOrderHelper = commerceOrderHelper;
		_commerceOrderItemService = commerceOrderItemService;
		_commerceOrderValidatorRegistry = commerceOrderValidatorRegistry;
		_commercePriceCalculationLocalService =
			commercePriceCalculationLocalService;

		this.cpDefinitionHelper = cpDefinitionHelper;
		this.cpInstanceHelper = cpInstanceHelper;

		commerceCartContentRequestHelper = new CommerceCartContentRequestHelper(
			httpServletRequest);
	}

	public CommerceOrder getCommerceOrder() throws PortalException {
		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		_commerceOrder = commerceOrderHelper.getCurrentCommerceOrder(
			commerceCartContentRequestHelper.getRequest());

		return _commerceOrder;
	}

	public long getCommerceOrderId() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		if (commerceOrder == null) {
			return 0;
		}

		return commerceOrder.getCommerceOrderId();
	}

	public String getCommerceOrderItemThumbnailSrc(
			CommerceOrderItem commerceOrderItem, ThemeDisplay themeDisplay)
		throws Exception {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			cpInstanceHelper.getCPAttachmentFileEntries(
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
		return _commercePriceCalculationLocalService.getFormattedOrderSubtotal(
			getCommerceOrder());
	}

	public Map<Long, List<CommerceOrderValidatorResult>>
			getCommerceOrderValidatorResults()
		throws PortalException {

		return _commerceOrderValidatorRegistry.getCommerceOrderValidatorResults(
			_commerceOrder);
	}

	public String getCPDefinitionURL(
			long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		return cpDefinitionHelper.getFriendlyURL(cpDefinitionId, themeDisplay);
	}

	public String getFormattedPrice(CommerceOrderItem commerceOrderItem)
		throws PortalException {

		return _commercePriceCalculationLocalService.getFormattedFinalPrice(
			commerceCartContentRequestHelper.getScopeGroupId(),
			commerceCartContentRequestHelper.getUserId(),
			commerceOrderItem.getCPInstanceId(),
			commerceOrderItem.getQuantity());
	}

	public List<KeyValuePair> getKeyValuePairs(String json, Locale locale)
		throws PortalException {

		return cpInstanceHelper.getKeyValuePairs(json, locale);
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			commerceCartContentRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(
			commerceCartContentRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			commerceCartContentRequestHelper.getRequest(), "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		return portletURL;
	}

	public SearchContainer<CommerceOrderItem> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			commerceCartContentRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("no-items-were-found");

		int total = _commerceOrderItemService.getCommerceOrderItemsCount(
			getCommerceOrderId());

		_searchContainer.setTotal(total);

		List<CommerceOrderItem> results =
			_commerceOrderItemService.getCommerceOrderItems(
				getCommerceOrderId(), _searchContainer.getStart(),
				_searchContainer.getEnd());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public boolean isValidCommerceOrder() throws PortalException {
		List<CommerceOrderItem> commerceOrderItems =
			_commerceOrder.getCommerceOrderItems();

		if (commerceOrderItems.isEmpty()) {
			return false;
		}

		return _commerceOrderValidatorRegistry.isValid(_commerceOrder);
	}

	public List<CommerceOrderValidatorResult> validateCommerceOrderItem(
			long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.fetchCommerceOrderItem(
				commerceOrderItemId);

		return _commerceOrderValidatorRegistry.validate(commerceOrderItem);
	}

	protected final CommerceCartContentRequestHelper
		commerceCartContentRequestHelper;
	protected final CPDefinitionHelper cpDefinitionHelper;
	protected final CPInstanceHelper cpInstanceHelper;

	private CommerceOrder _commerceOrder;
	private final CommerceOrderHelper _commerceOrderHelper;
	private final CommerceOrderItemService _commerceOrderItemService;
	private final CommerceOrderValidatorRegistry
		_commerceOrderValidatorRegistry;
	private final CommercePriceCalculationLocalService
		_commercePriceCalculationLocalService;
	private final HttpServletResponse _httpServletResponse;
	private SearchContainer<CommerceOrderItem> _searchContainer;

}
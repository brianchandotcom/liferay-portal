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
import com.liferay.commerce.cart.content.web.internal.portlet.configuration.CommerceCartContentPortletInstanceConfiguration;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.price.CommercePriceCalculation;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceCartContentDisplayContext {

	public CommerceCartContentDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderValidatorRegistry commerceOrderValidatorRegistry,
			CommercePriceCalculation commercePriceCalculation,
			CPDefinitionHelper cpDefinitionHelper,
			CPInstanceHelper cpInstanceHelper)
		throws PortalException {

		_commerceOrderItemService = commerceOrderItemService;
		_commerceOrderValidatorRegistry = commerceOrderValidatorRegistry;
		_commercePriceCalculation = commercePriceCalculation;

		this.cpDefinitionHelper = cpDefinitionHelper;
		this.cpInstanceHelper = cpInstanceHelper;

		commerceCartContentRequestHelper = new CommerceCartContentRequestHelper(
			httpServletRequest);

		commerceContext = commerceCartContentRequestHelper.getCommerceContext();

		PortletDisplay portletDisplay =
			commerceCartContentRequestHelper.getPortletDisplay();

		_commerceCartContentPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CommerceCartContentPortletInstanceConfiguration.class);
	}

	public CommerceOrder getCommerceOrder() throws PortalException {
		if (_commerceOrder != null) {
			return _commerceOrder;
		}

		return commerceContext.getCommerceOrder();
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
		CommerceOrder commerceOrder = getCommerceOrder();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		CommerceMoney commerceMoney = commerceCurrency.getZero();

		if (commerceOrder != null) {
			commerceMoney = _commercePriceCalculation.getOrderSubtotal(
				getCommerceOrder(), commerceContext);
		}

		return commerceMoney.toString();
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

	public String getDeleteURL(CommerceOrderItem commerceOrderItem) {
		LiferayPortletResponse liferayPortletResponse =
			commerceCartContentRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editCommerceOrderItem");
		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter(
			"redirect", commerceCartContentRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"commerceOrderItemId",
			String.valueOf(commerceOrderItem.getCommerceOrderItemId()));

		return portletURL.toString();
	}

	public String getDisplayStyle() {
		return _commerceCartContentPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_commerceCartContentPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			_displayStyleGroupId =
				commerceCartContentRequestHelper.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public String getFormattedPrice(CommerceOrderItem commerceOrderItem)
		throws PortalException {

		CommerceMoney commerceMoney = _commercePriceCalculation.getFinalPrice(
			commerceOrderItem.getCPInstanceId(),
			commerceOrderItem.getQuantity(), true, true, commerceContext);

		return commerceMoney.toString();
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

		long commerceOrderId = getCommerceOrderId();

		if (commerceOrderId == 0) {
			return _searchContainer;
		}

		int total = _commerceOrderItemService.getCommerceOrderItemsCount(
			commerceOrderId);

		_searchContainer.setTotal(total);

		List<CommerceOrderItem> results =
			_commerceOrderItemService.getCommerceOrderItems(
				commerceOrderId, _searchContainer.getStart(),
				_searchContainer.getEnd());

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public boolean isValidCommerceOrder() throws PortalException {
		if (_commerceOrder == null) {
			return false;
		}

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
	protected final CommerceContext commerceContext;
	protected final CPDefinitionHelper cpDefinitionHelper;
	protected final CPInstanceHelper cpInstanceHelper;

	private final CommerceCartContentPortletInstanceConfiguration
		_commerceCartContentPortletInstanceConfiguration;
	private CommerceOrder _commerceOrder;
	private final CommerceOrderItemService _commerceOrderItemService;
	private final CommerceOrderValidatorRegistry
		_commerceOrderValidatorRegistry;
	private final CommercePriceCalculation _commercePriceCalculation;
	private long _displayStyleGroupId;
	private SearchContainer<CommerceOrderItem> _searchContainer;

}
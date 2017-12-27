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

package com.liferay.commerce.shipping.engine.fixed.web.internal.display.context;

import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.shipping.engine.fixed.util.CommerceShippingEngineFixedUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionsDisplayContext
	extends BaseCommerceShippingFixedOptionDisplayContext
		<CommerceShippingFixedOption> {

	public CommerceShippingFixedOptionsDisplayContext(
		CommerceShippingMethodService commerceShippingMethodService,
		CommerceShippingFixedOptionService commerceShippingFixedOptionService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(commerceShippingMethodService, renderRequest, renderResponse);

		_commerceShippingFixedOptionService =
			commerceShippingFixedOptionService;
	}

	public String getEditURL(String functionName, boolean isNew, String url) {
		StringBuilder sb = new StringBuilder(11);

		sb.append("javascript:");
		sb.append(renderResponse.getNamespace());
		sb.append(functionName);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(isNew);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(StringPool.APOSTROPHE);
		sb.append(HtmlUtil.escapeJS(url));
		sb.append(StringPool.APOSTROPHE);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	@Override
	public String getScreenNavigationEntryKey() {
		return "shipping-options";
	}

	@Override
	public SearchContainer<CommerceShippingFixedOption> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("there-are-no-shipping-options");

		OrderByComparator<CommerceShippingFixedOption> orderByComparator =
			CommerceShippingEngineFixedUtil.
				getCommerceShippingFixedOptionOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceShippingFixedOptionService.
				getCommerceShippingFixedOptionsCount(
					getCommerceShippingMethodId());

		searchContainer.setTotal(total);

		List<CommerceShippingFixedOption> results =
			_commerceShippingFixedOptionService.getCommerceShippingFixedOptions(
				getCommerceShippingMethodId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

}
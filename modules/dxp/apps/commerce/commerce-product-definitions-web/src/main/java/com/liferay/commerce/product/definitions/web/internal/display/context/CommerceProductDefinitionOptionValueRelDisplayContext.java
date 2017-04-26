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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.internal.util.CommerceProductDefinitionsPortletUtil;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CommerceProductDefinitionOptionValueRelService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceProductDefinitionOptionValueRelDisplayContext
	extends BaseCommerceProductDefinitionsDisplayContext {

	public CommerceProductDefinitionOptionValueRelDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceProductDefinitionOptionValueRelService
				commerceProductDefinitionOptionValueRelService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			"rowIdsCommerceProductDefinitionOptionValueRel",
			"CommerceProductDefinitionOptionValueRel");

		setDefaultOrderByCol("priority");

		_commerceProductDefinitionOptionValueRelService =
			commerceProductDefinitionOptionValueRelService;
	}

	public CommerceProductDefinitionOptionRel
		getCommerceProductDefinitionOptionRel() throws PortalException {

		if (_commerceProductDefinitionOptionRel != null) {
			return _commerceProductDefinitionOptionRel;
		}

		_commerceProductDefinitionOptionRel =
			actionHelper.getCommerceProductDefinitionOptionRel(
				commerceProductRequestHelper.getRenderRequest());

		return _commerceProductDefinitionOptionRel;
	}

	public long getCommerceProductDefinitionOptionRelId()
		throws PortalException {

		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel =
			getCommerceProductDefinitionOptionRel();

		if (commerceProductDefinitionOptionRel == null) {
			return 0;
		}

		return commerceProductDefinitionOptionRel.
			getCommerceProductDefinitionOptionRelId();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewProductDefinitionOptionValueRels");

		return portletURL;
	}

	@Override
	public SearchContainer
		getSearchContainer() throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		SearchContainer<CommerceProductDefinitionOptionValueRel>
			searchContainer = new SearchContainer<>(
				liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceProductDefinitionOptionValueRel>
			orderByComparator =
				CommerceProductDefinitionsPortletUtil.
					getCommerceProductDefinitionOptionValueRelOrderByComparator(
						getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setEmptyResultsMessage(
			"no-product-options-value-were-found");
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceProductDefinitionOptionValueRelService.
				getCommerceProductDefinitionOptionValueRelsCount(
					getCommerceProductDefinitionOptionRelId());

		searchContainer.setTotal(total);

		List<CommerceProductDefinitionOptionValueRel> results =
			_commerceProductDefinitionOptionValueRelService.
				getCommerceProductDefinitionOptionValueRels(
					getCommerceProductDefinitionOptionRelId(),
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator);

		searchContainer.setResults(results);

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	private CommerceProductDefinitionOptionRel
		_commerceProductDefinitionOptionRel;
	private final CommerceProductDefinitionOptionValueRelService
		_commerceProductDefinitionOptionValueRelService;

}
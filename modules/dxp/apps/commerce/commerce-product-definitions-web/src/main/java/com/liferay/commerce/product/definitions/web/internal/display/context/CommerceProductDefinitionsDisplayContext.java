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
import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.service.CommerceProductDefinitionService;
import com.liferay.commerce.product.type.CommerceProductType;
import com.liferay.commerce.product.type.CommerceProductTypeServicesTracker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CommerceProductDefinitionsDisplayContext
	extends BaseCommerceProductDefinitionsDisplayContext {

	public CommerceProductDefinitionsDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceProductDefinitionService
				commerceProductDefinitionService,
			CommerceProductTypeServicesTracker
				commerceProductTypeServicesTracker)
		throws PortalException {

		super(
			actionHelper, httpServletRequest, "rowIdsCommerceProductDefinition",
			"CommerceProductDefinition");

		_commerceProductDefinitionService = commerceProductDefinitionService;
		_commerceProductTypeServicesTracker =
			commerceProductTypeServicesTracker;
	}

	public List<CommerceProductType> getCommerceProductTypes() {
		return _commerceProductTypeServicesTracker.getCommerceProductTypes();
	}

	@Override
	public SearchContainer getSearchContainer() throws PortalException {
		if (searchContainer != null) {
			return searchContainer;
		}

		SearchContainer<CommerceProductDefinition> searchContainer =
			new SearchContainer<>(
				liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceProductDefinition> orderByComparator =
			CommerceProductDefinitionsPortletUtil.
				getCommerceProductDefinitionOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceProductDefinitionService.
				getCommerceProductDefinitionsCount(getScopeGroupId());

		searchContainer.setTotal(total);

		List<CommerceProductDefinition> results =
			_commerceProductDefinitionService.getCommerceProductDefinitions(
				getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	private final CommerceProductDefinitionService
		_commerceProductDefinitionService;
	private final CommerceProductTypeServicesTracker
		_commerceProductTypeServicesTracker;

}
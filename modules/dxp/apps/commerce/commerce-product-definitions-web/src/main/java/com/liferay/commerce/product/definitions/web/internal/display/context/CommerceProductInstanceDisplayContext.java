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
import com.liferay.commerce.product.model.CommerceProductInstance;
import com.liferay.commerce.product.service.CommerceProductInstanceService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CommerceProductInstanceDisplayContext
	extends BaseCommerceProductDefinitionsDisplayContext {

	public CommerceProductInstanceDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceProductInstanceService
				commerceProductInstanceService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest, "rowIdsCommerceProductInstance",
			"CommerceProductInstance");

		setDefaultOrderByCol("sku");

		_commerceProductInstanceService = commerceProductInstanceService;
	}

	public List<CommerceProductDefinitionOptionRel>
			getCommerceProductDefinitionOptionRels()
		throws PortalException {

		return new ArrayList<>();
	}

	public SearchContainer
		getSearchContainer() throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		SearchContainer<CommerceProductInstance> searchContainer =
			new SearchContainer<>(
				liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceProductInstance> orderByComparator =
			CommerceProductDefinitionsPortletUtil.
				getCommerceProductInstanceOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setEmptyResultsMessage("no-skus-were-found");

		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceProductInstanceService.getCommerceProductInstancesCount(
				getCommerceProductDefinitionId());

		searchContainer.setTotal(total);

		List<CommerceProductInstance> results =
			_commerceProductInstanceService.getCommerceProductInstances(
				getCommerceProductDefinitionId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	private final CommerceProductInstanceService
		_commerceProductInstanceService;

}
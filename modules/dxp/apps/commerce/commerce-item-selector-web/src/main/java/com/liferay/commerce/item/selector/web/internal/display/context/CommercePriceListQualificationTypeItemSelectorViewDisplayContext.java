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

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.commerce.item.selector.web.internal.search.CommercePriceListQualificationTypeItemSelectorChecker;
import com.liferay.commerce.price.CommercePriceListQualificationType;
import com.liferay.commerce.price.CommercePriceListQualificationTypeRegistry;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListQualificationTypeItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext
		<CommercePriceListQualificationType> {

	public CommercePriceListQualificationTypeItemSelectorViewDisplayContext(
		CommercePriceListQualificationTypeRegistry
			commercePriceListQualificationTypeRegistry,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_commercePriceListQualificationTypeRegistry =
			commercePriceListQualificationTypeRegistry;

		setDefaultOrderByCol("key");
		setDefaultOrderByType("asc");
	}

	public SearchContainer<CommercePriceListQualificationType>
		getSearchContainer() throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("there-are-no-criteria");

		RowChecker rowChecker =
			new CommercePriceListQualificationTypeItemSelectorChecker(
				cpRequestHelper.getRenderResponse(),
				getCheckedCommercePriceListQualificationTypeKeys());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(rowChecker);

		List<CommercePriceListQualificationType> results =
			_commercePriceListQualificationTypeRegistry.
				getCommercePriceListQualificationTypes();

		searchContainer.setResults(results);
		searchContainer.setTotal(results.size());

		return searchContainer;
	}

	protected String[] getCheckedCommercePriceListQualificationTypeKeys() {
		return ParamUtil.getStringValues(
			cpRequestHelper.getRenderRequest(),
			"checkedCommercePriceListQualificationTypeKeys");
	}

	private final CommercePriceListQualificationTypeRegistry
		_commercePriceListQualificationTypeRegistry;

}
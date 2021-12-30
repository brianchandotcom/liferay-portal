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

import com.liferay.commerce.item.selector.web.internal.search.CommerceProductInstanceItemSelectorChecker;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.comparator.CPInstanceCreateDateComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceDisplayDateComparator;
import com.liferay.commerce.product.util.comparator.CPInstanceSkuComparator;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceProductInstanceItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext<CPInstance> {

	public CommerceProductInstanceItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName,
		CommercePriceEntryLocalService commercePriceEntryLocalService,
		CommercePriceListService commercePriceListService,
		CPInstanceService cpInstanceService) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_commercePriceEntryLocalService = commercePriceEntryLocalService;
		_commercePriceListService = commercePriceListService;
		_cpInstanceService = cpInstanceService;

		setDefaultOrderByCol("sku");
	}

	public Sort getCPInstanceSort(String orderByCol, String orderByType) {
		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(
				Field.CREATE_DATE + "_sortable", reverse);
		}
		else if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create(
				CPField.DISPLAY_DATE + "_Number_sortable", reverse);
		}
		else if (orderByCol.equals("sku")) {
			sort = SortFactoryUtil.create(
				CPField.SKU + "_String_sortable", reverse);
		}

		return sort;
	}

	@Override
	public PortletURL getPortletURL() {
		return PortletURLBuilder.create(
			super.getPortletURL()
		).setParameter(
			"commerceCatalogGroupId", getGroupId()
		).setParameter(
			"commercePriceListId", _getCommercePriceListId()
		).buildPortletURL();
	}

	@Override
	public SearchContainer<CPInstance> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-skus-were-found");

		OrderByComparator<CPInstance> orderByComparator =
			_getCPInstanceOrderByComparator(getOrderByCol(), getOrderByType());

		RowChecker rowChecker = new CommerceProductInstanceItemSelectorChecker(
			cpRequestHelper.getRenderResponse(),
			_commercePriceListService.fetchCommercePriceList(
				_getCommercePriceListId()),
			_commercePriceEntryLocalService);

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(rowChecker);

		Sort sort = getCPInstanceSort(getOrderByCol(), getOrderByType());

		BaseModelSearchResult<CPInstance> cpInstanceBaseModelSearchResult;

		if (getGroupId() > 0) {
			cpInstanceBaseModelSearchResult =
				_cpInstanceService.searchCPInstances(
					cpRequestHelper.getCompanyId(), getGroupId(), getKeywords(),
					WorkflowConstants.STATUS_APPROVED,
					searchContainer.getStart(), searchContainer.getEnd(), sort);
		}
		else {
			cpInstanceBaseModelSearchResult =
				_cpInstanceService.searchCPInstances(
					cpRequestHelper.getCompanyId(), getKeywords(),
					WorkflowConstants.STATUS_APPROVED,
					searchContainer.getStart(), searchContainer.getEnd(), sort);
		}

		List<CPInstance> cpInstances =
			cpInstanceBaseModelSearchResult.getBaseModels();
		int totalCPInstances = cpInstanceBaseModelSearchResult.getLength();

		searchContainer.setResults(cpInstances);
		searchContainer.setTotal(totalCPInstances);

		return searchContainer;
	}

	protected long getGroupId() {
		return ParamUtil.getLong(httpServletRequest, "commerceCatalogGroupId");
	}

	private long _getCommercePriceListId() {
		return ParamUtil.getLong(httpServletRequest, "commercePriceListId");
	}

	private OrderByComparator<CPInstance> _getCPInstanceOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CPInstance> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CPInstanceCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new CPInstanceDisplayDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("sku")) {
			orderByComparator = new CPInstanceSkuComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private final CommercePriceEntryLocalService
		_commercePriceEntryLocalService;
	private final CommercePriceListService _commercePriceListService;
	private final CPInstanceService _cpInstanceService;

}
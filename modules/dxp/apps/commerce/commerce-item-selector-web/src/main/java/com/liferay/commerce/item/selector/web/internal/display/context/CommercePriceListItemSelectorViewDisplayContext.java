/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.commerce.item.selector.web.internal.search.CommercePriceListItemSelectorChecker;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.price.list.util.comparator.CommercePriceListCreateDateComparator;
import com.liferay.commerce.price.list.util.comparator.CommercePriceListDisplayDateComparator;
import com.liferay.commerce.price.list.util.comparator.CommercePriceListPriorityComparator;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListItemSelectorViewDisplayContext
	extends BaseCommerceItemSelectorViewDisplayContext<CommercePriceList> {

	public CommercePriceListItemSelectorViewDisplayContext(
		CommercePriceListService commercePriceListService,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		super(httpServletRequest, portletURL, itemSelectedEventName);

		_commercePriceListService = commercePriceListService;

		setDefaultOrderByCol("create-date");
		setDefaultOrderByType("desc");
	}

	public SearchContainer<CommercePriceList> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("there-are-no-price-lists");

		OrderByComparator<CommercePriceList> orderByComparator =
			getCommercePriceListOrderByComparator(
				getOrderByCol(), getOrderByType());

		RowChecker rowChecker = new CommercePriceListItemSelectorChecker(
			cpRequestHelper.getRenderResponse(),
			getCheckedCommercePriceListIds());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(rowChecker);

		if (searchContainer.isSearch()) {
			Sort sort = getCommercePriceListSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CommercePriceList>
				commercePriceListBaseModelSearchResult =
					_commercePriceListService.searchCommercePriceLists(
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId(), getKeywords(),
						WorkflowConstants.STATUS_APPROVED,
						searchContainer.getStart(), searchContainer.getEnd(),
						sort);

			searchContainer.setTotal(
				commercePriceListBaseModelSearchResult.getLength());
			searchContainer.setResults(
				commercePriceListBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _commercePriceListService.getCommercePriceListsCount(
				themeDisplay.getScopeGroupId(),
				WorkflowConstants.STATUS_APPROVED);

			searchContainer.setTotal(total);

			List<CommercePriceList> results =
				_commercePriceListService.getCommercePriceLists(
					themeDisplay.getScopeGroupId(),
					WorkflowConstants.STATUS_APPROVED,
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	protected static OrderByComparator<CommercePriceList>
		getCommercePriceListOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CommercePriceList> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new CommercePriceListCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("display-date")) {
			orderByComparator = new CommercePriceListDisplayDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("priority")) {
			orderByComparator = new CommercePriceListPriorityComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	protected static Sort getCommercePriceListSort(
		String orderByCol, String orderByType) {

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		Sort sort = null;

		if (orderByCol.equals("create-date")) {
			sort = SortFactoryUtil.create(Field.CREATE_DATE, reverse);
		}
		else if (orderByCol.equals("display-date")) {
			sort = SortFactoryUtil.create("display-date", reverse);
		}
		else if (orderByCol.equals("priority")) {
			sort = SortFactoryUtil.create(Field.PRIORITY, reverse);
		}

		return sort;
	}

	protected long[] getCheckedCommercePriceListIds() {
		return ParamUtil.getLongValues(
			cpRequestHelper.getRenderRequest(), "checkedCommercePriceListIds");
	}

	private final CommercePriceListService _commercePriceListService;

}
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

package com.liferay.commerce.order.web.internal.frontend;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.web.internal.constants.CommerceOrderPortletConstants;
import com.liferay.commerce.order.web.internal.model.Order;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.search.facet.NegatableSimpleFacet;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_ALL_ORDERS,
		"commerce.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_COMPLETED_ORDERS,
		"commerce.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_OPEN_ORDERS,
		"commerce.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS,
		"commerce.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PROCESSING_ORDERS
	},
	service = CommerceDataSetDataProvider.class
)
public class CommerceOrderDataSetDataProvider
	implements CommerceDataSetDataProvider<Order> {

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		String activeTab = ParamUtil.getString(
			httpServletRequest, "activeTab",
			CommerceOrderPortletConstants.NAVIGATION_ITEM_ALL);

		SearchContext searchContext = buildSearchContext(
			_portal.getCompanyId(httpServletRequest), activeTab, filter, 0, 0,
			null);

		return (int)_commerceOrderLocalService.searchCommerceOrdersCount(
			searchContext);
	}

	@Override
	public List<Order> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Order> orders = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String activeTab = ParamUtil.getString(
			httpServletRequest, "activeTab",
			CommerceOrderPortletConstants.NAVIGATION_ITEM_ALL);

		Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(
			DateFormat.MEDIUM, DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		SearchContext searchContext = buildSearchContext(
			_portal.getCompanyId(httpServletRequest), activeTab, filter,
			pagination.getStartPosition(), pagination.getEndPosition(), sort);

		BaseModelSearchResult<CommerceOrder> baseModelSearchResult =
			_commerceOrderLocalService.searchCommerceOrders(searchContext);

		List<CommerceOrder> commerceOrders =
			baseModelSearchResult.getBaseModels();

		for (CommerceOrder commerceOrder : commerceOrders) {
			CommerceMoney totalMoney = commerceOrder.getTotalMoney();

			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
					commerceOrder.getGroupId());

			String commerceAccountId = String.valueOf(
				commerceOrder.getCommerceAccountId());

			String commerceAccountName = commerceOrder.getCommerceAccountName();

			if (Validator.isNull(commerceAccountName)) {
				commerceAccountId = null;
				commerceAccountName = LanguageUtil.get(
					_portal.getLocale(httpServletRequest), "guest");
			}

			orders.add(
				new Order(
					commerceAccountName, commerceAccountId,
					totalMoney.format(themeDisplay.getLocale()),
					commerceChannel.getName(),
					getCommerceOrderDateTime(commerceOrder, dateTimeFormat),
					new LabelField(
						CommerceOrderConstants.getStatusLabelStyle(
							commerceOrder.getStatus()),
						LanguageUtil.get(
							httpServletRequest,
							WorkflowConstants.getStatusLabel(
								commerceOrder.getStatus()))),
					commerceOrder.getCommerceOrderId(),
					new LabelField(
						CommerceOrderConstants.getOrderStatusLabelStyle(
							commerceOrder.getOrderStatus()),
						LanguageUtil.get(
							httpServletRequest,
							CommerceOrderConstants.getOrderStatusLabel(
								commerceOrder.getOrderStatus())))));
		}

		return orders;
	}

	protected SearchContext buildSearchContext(
			long companyId, String activeTab, Filter filter, int start, int end,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		_addFacetOrderStatus(searchContext, activeTab);
		_addFacetStatus(searchContext);

		searchContext.setAttribute(Field.ENTRY_CLASS_PK, filter.getKeywords());
		searchContext.setAttribute("faceted", Boolean.TRUE);
		searchContext.setAttribute("purchaseOrderNumber", filter.getKeywords());
		searchContext.setAttribute(
			"useSearchResultPermissionFilter", Boolean.FALSE);

		searchContext.setCompanyId(companyId);
		searchContext.setKeywords(filter.getKeywords());
		searchContext.setStart(start);
		searchContext.setEnd(end);

		long[] commerceChannelGroupIds = _getCommerceChannelGroupIds(companyId);

		if ((commerceChannelGroupIds != null) &&
			(commerceChannelGroupIds.length > 0)) {

			searchContext.setGroupIds(commerceChannelGroupIds);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setSorts(getSorts(activeTab, sort));

		return searchContext;
	}

	protected String getCommerceOrderDateTime(
		CommerceOrder commerceOrder, Format dateTimeFormat) {

		if (commerceOrder.getOrderDate() == null) {
			return dateTimeFormat.format(commerceOrder.getCreateDate());
		}

		return dateTimeFormat.format(commerceOrder.getOrderDate());
	}

	protected Sort[] getSorts(String activeTab, Sort sort) {
		if (sort != null) {
			return new Sort[] {sort};
		}

		if (activeTab.equals(
				CommerceOrderPortletConstants.NAVIGATION_ITEM_ALL) ||
			activeTab.equals(
				CommerceOrderPortletConstants.NAVIGATION_ITEM_OPEN)) {

			return new Sort[] {
				SortFactoryUtil.create(Field.CREATE_DATE + "_sortable", true),
				SortFactoryUtil.create(null, Sort.SCORE_TYPE, false)
			};
		}

		return new Sort[] {
			SortFactoryUtil.create("orderDate_sortable", true),
			SortFactoryUtil.create(null, Sort.SCORE_TYPE, false)
		};
	}

	private SearchContext _addFacetOrderStatus(
		SearchContext searchContext, String activeTab) {

		int[] orderStatuses = null;

		if (activeTab.equals("open")) {
			orderStatuses = CommerceOrderConstants.ORDER_STATUSES_OPEN;
		}
		else if (activeTab.equals("pending")) {
			orderStatuses = CommerceOrderConstants.ORDER_STATUSES_PENDING;
		}
		else if (activeTab.equals("processing")) {
			orderStatuses = CommerceOrderConstants.ORDER_STATUSES_PROCESSING;
		}
		else if (activeTab.equals("completed")) {
			orderStatuses = CommerceOrderConstants.ORDER_STATUSES_COMPLETED;
		}

		searchContext.setAttribute("orderStatuses", orderStatuses);

		return searchContext;
	}

	private SearchContext _addFacetStatus(SearchContext searchContext) {
		NegatableSimpleFacet negatableSimpleFacet = new NegatableSimpleFacet(
			searchContext);

		negatableSimpleFacet.setFieldName(Field.STATUS);
		negatableSimpleFacet.setNegated(true);
		negatableSimpleFacet.setStatic(true);

		FacetConfiguration facetConfiguration =
			negatableSimpleFacet.getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		dataJSONObject.put(
			"value", String.valueOf(WorkflowConstants.STATUS_DRAFT));

		searchContext.addFacet(negatableSimpleFacet);

		return searchContext;
	}

	private long[] _getCommerceChannelGroupIds(long companyId)
		throws PortalException {

		List<CommerceChannel> commerceChannels =
			_commerceChannelLocalService.searchCommerceChannels(companyId);

		Stream<CommerceChannel> stream = commerceChannels.stream();

		return stream.mapToLong(
			CommerceChannel::getGroupId
		).toArray();
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private Portal _portal;

}
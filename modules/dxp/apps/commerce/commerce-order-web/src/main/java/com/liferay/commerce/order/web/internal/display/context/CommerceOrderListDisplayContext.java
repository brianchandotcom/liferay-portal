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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.commerce.order.web.internal.search.CommerceOrderDisplayTerms;
import com.liferay.commerce.order.web.internal.search.CommerceOrderSearch;
import com.liferay.commerce.order.web.internal.search.facet.NegatableMultiValueFacet;
import com.liferay.commerce.order.web.security.permission.resource.CommerceOrderPermission;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.search.facet.NegatableSimpleFacet;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommercePriceCalculationLocalService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.facet.DateRangeFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderListDisplayContext {

	public CommerceOrderListDisplayContext(
		CommerceOrderHelper commerceOrderHelper,
		CommerceOrderLocalService commerceOrderLocalService,
		CommerceOrderNoteService commerceOrderNoteService,
		CommerceOrganizationService commerceOrganizationService,
		CommercePriceCalculationLocalService
			commercePriceCalculationLocalService,
		CommercePriceFormatter commercePriceFormatter,
		GroupLocalService groupLocalService, JSONFactory jsonFactory,
		RenderRequest renderRequest) {

		_commerceOrderHelper = commerceOrderHelper;
		_commerceOrderLocalService = commerceOrderLocalService;
		_commerceOrderNoteService = commerceOrderNoteService;
		_commerceOrganizationService = commerceOrganizationService;
		_commercePriceCalculationLocalService =
			commercePriceCalculationLocalService;
		_commercePriceFormatter = commercePriceFormatter;
		_groupLocalService = groupLocalService;
		_jsonFactory = jsonFactory;

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			renderRequest);

		ThemeDisplay themeDisplay =
			_commerceOrderRequestHelper.getThemeDisplay();

		_commerceOrderDateFormatDateTime =
			FastDateFormatFactoryUtil.getDateTime(
				DateFormat.MEDIUM, DateFormat.MEDIUM, themeDisplay.getLocale(),
				themeDisplay.getTimeZone());

		_keywords = ParamUtil.getString(renderRequest, "keywords");
		_showFilter = ParamUtil.getBoolean(renderRequest, "showFilter");
		_tabs1 = ParamUtil.getString(renderRequest, "tabs1", "pending");
	}

	public List<KeyValuePair> getAvailableAdvanceStatusKVPs()
		throws PortalException {

		if (_availableAdvanceStatusKVPs == null) {
			_initSearch();
		}

		return _availableAdvanceStatusKVPs;
	}

	public List<KeyValuePair> getAvailableOrderOrganizationKVPs()
		throws PortalException {

		if (_availableOrderOrganizationKVPs == null) {
			_initSearch();
		}

		return _availableOrderOrganizationKVPs;
	}

	public List<KeyValuePair> getAvailableOrderStatusKVPs()
		throws PortalException {

		if (_availableOrderStatusKVPs == null) {
			_initSearch();
		}

		return _availableOrderStatusKVPs;
	}

	public String getCommerceOrderDateTime(CommerceOrder commerceOrder) {
		return _commerceOrderDateFormatDateTime.format(
			commerceOrder.getCreateDate());
	}

	public int getCommerceOrderNotesCount(CommerceOrder commerceOrder)
		throws PortalException {

		if (CommerceOrderPermission.contains(
				_commerceOrderRequestHelper.getPermissionChecker(),
				commerceOrder, ActionKeys.UPDATE_DISCUSSION)) {

			return _commerceOrderNoteService.getCommerceOrderNotesCount(
				commerceOrder.getCommerceOrderId());
		}

		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			commerceOrder.getCommerceOrderId(), false);
	}

	public String getCommerceOrderPaymentStatus(CommerceOrder commerceOrder) {
		return LanguageUtil.get(
			_commerceOrderRequestHelper.getRequest(),
			CommerceOrderConstants.getPaymentStatusLabel(
				commerceOrder.getPaymentStatus()));
	}

	public String getCommerceOrderStatus(CommerceOrder commerceOrder) {
		return LanguageUtil.get(
			_commerceOrderRequestHelper.getRequest(),
			CommerceOrderConstants.getOrderStatusLabel(
				commerceOrder.getOrderStatus()));
	}

	public String getCommerceOrderTransitionMessage(String transitionName) {
		if (Validator.isNull(transitionName)) {
			transitionName = "proceed";
		}

		return LanguageUtil.get(
			_commerceOrderRequestHelper.getRequest(), transitionName);
	}

	public List<ObjectValuePair<Long, String>> getCommerceOrderTransitionOVPs(
			CommerceOrder commerceOrder)
		throws PortalException {

		List<ObjectValuePair<Long, String>> transitionOVPs = new ArrayList<>();

		PermissionChecker permissionChecker =
			_commerceOrderRequestHelper.getPermissionChecker();

		ObjectValuePair<Long, String> approveOVP = null;

		if (commerceOrder.isOpen() && commerceOrder.isPending() &&
			CommerceOrderPermission.contains(
				permissionChecker, commerceOrder,
				CommerceOrderActionKeys.APPROVE_COMMERCE_ORDER)) {

			approveOVP = new ObjectValuePair<>(0L, "approve");

			transitionOVPs.add(approveOVP);
		}

		int start = transitionOVPs.size();

		transitionOVPs.addAll(
			_commerceOrderHelper.getWorkflowTransitions(
				_commerceOrderRequestHelper.getUserId(), commerceOrder));

		if (approveOVP != null) {
			for (int i = start; i < transitionOVPs.size(); i++) {
				ObjectValuePair<Long, String> objectValuePair =
					transitionOVPs.get(i);

				String value = objectValuePair.getValue();

				if (value.equals(approveOVP.getValue())) {
					approveOVP.setValue("force-" + value);

					break;
				}
			}
		}

		return transitionOVPs;
	}

	public String getCommerceOrderValue(CommerceOrder commerceOrder)
		throws PortalException {

		double value = commerceOrder.getTotal();

		if (commerceOrder.isOpen()) {
			value = _commercePriceCalculationLocalService.getOrderSubtotal(
				commerceOrder);
		}

		return _commercePriceFormatter.format(
			commerceOrder.getSiteGroupId(), value);
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		if (_navigationItems == null) {
			_initNavigationItems();
		}

		return _navigationItems;
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceOrderRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("showFilter", String.valueOf(_showFilter));
		portletURL.setParameter("tabs1", _tabs1);

		return portletURL;
	}

	public SearchContainer<CommerceOrder> getSearchContainer()
		throws PortalException {

		if (_searchContainer == null) {
			_initSearch();
		}

		return _searchContainer;
	}

	public boolean isShowFilter() {
		return _showFilter;
	}

	private void _addFacetAdvanceStatus(
		SearchContext searchContext,
		CommerceOrderDisplayTerms commerceOrderDisplayTerms) {

		Facet facet = new SimpleFacet(searchContext);

		facet.setFieldName("advanceStatus");

		searchContext.addFacet(facet);

		searchContext.setAttribute(
			facet.getFieldId(), commerceOrderDisplayTerms.getAdvanceStatus());
	}

	private void _addFacetCreateDate(
		SearchContext searchContext,
		CommerceOrderDisplayTerms commerceOrderDisplayTerms) {

		Facet facet = new DateRangeFacet(searchContext);

		FacetConfiguration facetConfiguration = new FacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		JSONObject rangeJSONObject = _jsonFactory.createJSONObject();

		rangeJSONObject.put(
			"range", _getFacetCreateDateRange(commerceOrderDisplayTerms));

		JSONArray rangesJSONArray = _jsonFactory.createJSONArray();

		rangesJSONArray.put(rangeJSONObject);

		dataJSONObject.put("ranges", rangesJSONArray);

		facet.setFacetConfiguration(facetConfiguration);

		facet.setFieldName(Field.CREATE_DATE);
		facet.setStatic(true);

		searchContext.addFacet(facet);
	}

	private void _addFacetOrderOrganizationId(
		SearchContext searchContext,
		CommerceOrderDisplayTerms commerceOrderDisplayTerms) {

		Facet facet = new SimpleFacet(searchContext);

		facet.setFieldName("orderOrganizationId");

		searchContext.addFacet(facet);

		long orderOrganizationId =
			commerceOrderDisplayTerms.getOrderOrganizationId();

		if (orderOrganizationId > 0) {
			searchContext.setAttribute(
				facet.getFieldId(), String.valueOf(orderOrganizationId));
		}
	}

	private void _addFacetOrderStatus(
		SearchContext searchContext,
		CommerceOrderDisplayTerms commerceOrderDisplayTerms) {

		_addFacetOrderStatus(
			searchContext, _tabs1, commerceOrderDisplayTerms.getOrderStatus(),
			false);
	}

	private void _addFacetOrderStatus(
		SearchContext searchContext, String tabs1, int orderStatus,
		boolean staticFacet) {

		NegatableMultiValueFacet negatableMultiValueFacet =
			new NegatableMultiValueFacet(searchContext);

		negatableMultiValueFacet.setFieldName("orderStatus");

		searchContext.addFacet(negatableMultiValueFacet);

		boolean negated = false;
		int[] orderStatuses = null;

		if (tabs1.equals("open")) {
			orderStatuses = new int[] {
				CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS,
				CommerceOrderConstants.ORDER_STATUS_OPEN
			};
		}
		else if (tabs1.equals("pending")) {
			orderStatuses =
				new int[] {CommerceOrderConstants.ORDER_STATUS_TO_TRANSMIT};
		}
		else if (orderStatus == CommerceOrderConstants.ORDER_STATUS_ANY) {
			negated = true;

			orderStatuses = new int[] {
				CommerceOrderConstants.ORDER_STATUS_IN_PROGRESS,
				CommerceOrderConstants.ORDER_STATUS_OPEN,
				CommerceOrderConstants.ORDER_STATUS_TO_TRANSMIT
			};
		}
		else {
			orderStatuses = new int[] {orderStatus};
		}

		negatableMultiValueFacet.setNegated(negated);

		if (staticFacet) {
			negatableMultiValueFacet.setStatic(true);

			FacetConfiguration facetConfiguration =
				negatableMultiValueFacet.getFacetConfiguration();

			JSONObject dataJSONObject = facetConfiguration.getData();

			JSONArray valuesJSONArray = _jsonFactory.createJSONArray();

			for (int curOrderStatus : orderStatuses) {
				valuesJSONArray.put(curOrderStatus);
			}

			dataJSONObject.put("values", valuesJSONArray);
		}
		else {
			searchContext.setAttribute(
				negatableMultiValueFacet.getFieldId(),
				StringUtil.merge(orderStatuses));
		}
	}

	private void _addFacetStatus(SearchContext searchContext) {
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
	}

	private List<KeyValuePair> _buildFacetKeyValuePairs(
			SearchContext searchContext, String fieldName,
			UnsafeFunction<String, String, PortalException> labelFunction,
			String... excludedTerms)
		throws PortalException {

		Facet facet = searchContext.getFacet(fieldName);

		if (facet == null) {
			return Collections.emptyList();
		}

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		List<KeyValuePair> keyValuePairs = new ArrayList<>(
			termCollectors.size());

		HttpServletRequest httpServletRequest =
			_commerceOrderRequestHelper.getRequest();

		StringBundler sb = new StringBundler();

		for (TermCollector termCollector : termCollectors) {
			String term = termCollector.getTerm();

			if (ArrayUtil.contains(excludedTerms, term)) {
				continue;
			}

			String label = labelFunction.apply(term);

			if (Validator.isNull(label)) {
				continue;
			}

			sb.append(LanguageUtil.get(httpServletRequest, label));
			sb.append(" (");
			sb.append(termCollector.getFrequency());
			sb.append(CharPool.CLOSE_PARENTHESIS);

			keyValuePairs.add(new KeyValuePair(term, sb.toString()));

			sb.setIndex(0);
		}

		return keyValuePairs;
	}

	private NavigationItem _buildNavigationItem(String name)
		throws PortalException {

		NavigationItem navigationItem = new NavigationItem();

		if (_tabs1.equals(name)) {
			navigationItem.setActive(true);
		}

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("tabs1", name);

		navigationItem.setHref(portletURL.toString());

		String label = LanguageUtil.get(
			_commerceOrderRequestHelper.getRequest(), name);

		long count = _getNavigationItemCount(name);

		if (count > 0) {
			StringBundler sb = new StringBundler(4);

			sb.append(label);
			sb.append(" (");
			sb.append(count);
			sb.append(CharPool.CLOSE_PARENTHESIS);

			label = sb.toString();
		}

		navigationItem.setLabel(label);

		return navigationItem;
	}

	private SearchContext _buildSearchContext() throws PortalException {
		SearchContext searchContext = new SearchContext();

		CommerceOrderDisplayTerms commerceOrderDisplayTerms =
			(CommerceOrderDisplayTerms)_searchContainer.getDisplayTerms();

		_addFacetCreateDate(searchContext, commerceOrderDisplayTerms);
		_addFacetOrderStatus(searchContext, commerceOrderDisplayTerms);

		_addFacetStatus(searchContext);

		if (_tabs1.equals("transmitted")) {
			_addFacetAdvanceStatus(searchContext, commerceOrderDisplayTerms);
		}

		Group group = _groupLocalService.getGroup(
			_commerceOrderRequestHelper.getSiteGroupId());

		if (group.isOrganization()) {
			_addFacetOrderOrganizationId(
				searchContext, commerceOrderDisplayTerms);
		}

		searchContext.setAttribute(Field.ENTRY_CLASS_PK, _keywords);
		searchContext.setAttribute("faceted", Boolean.TRUE);
		searchContext.setAttribute(
			"siteGroupId", _commerceOrderRequestHelper.getSiteGroupId());
		searchContext.setAttribute(
			"useSearchResultPermissionFilter", Boolean.FALSE);

		searchContext.setCompanyId(_commerceOrderRequestHelper.getCompanyId());
		searchContext.setKeywords(_keywords);
		searchContext.setStart(_searchContainer.getStart());
		searchContext.setEnd(_searchContainer.getEnd());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Sort[] sorts = CommerceUtil.getCommerceOrderSorts(
			_searchContainer.getOrderByCol(),
			_searchContainer.getOrderByType());

		searchContext.setSorts(sorts);

		return searchContext;
	}

	private String _getEmptyResultsMessage(boolean filterByStatuses) {
		String pattern = "there-are-no-x-orders";

		CommerceOrderDisplayTerms commerceOrderDisplayTerms =
			(CommerceOrderDisplayTerms)_searchContainer.getDisplayTerms();

		if (Validator.isNotNull(_keywords) ||
			(commerceOrderDisplayTerms.getOrderOrganizationId() > 0) ||
			(commerceOrderDisplayTerms.getStartCreateDate() != null) ||
			(commerceOrderDisplayTerms.getEndCreateDate() != null) ||
			(filterByStatuses &&
			 (Validator.isNotNull(
				 commerceOrderDisplayTerms.getAdvanceStatus()) ||
			  (commerceOrderDisplayTerms.getOrderStatus() !=
				  CommerceOrderConstants.ORDER_STATUS_ANY)))) {

			pattern = "no-x-orders-were-found";
		}

		HttpServletRequest httpServletRequest =
			_commerceOrderRequestHelper.getRequest();

		String argument = StringUtil.toLowerCase(
			LanguageUtil.get(httpServletRequest, _tabs1),
			_commerceOrderRequestHelper.getLocale());

		return LanguageUtil.format(
			httpServletRequest, pattern, argument, false);
	}

	private String _getFacetCreateDateRange(
		CommerceOrderDisplayTerms commerceOrderDisplayTerms) {

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			PropsValues.INDEX_DATE_FORMAT_PATTERN);

		StringBundler sb = new StringBundler(5);

		sb.append(CharPool.OPEN_BRACKET);

		Date startCreateDate = commerceOrderDisplayTerms.getStartCreateDate();

		if (startCreateDate == null) {
			Calendar calendar = CalendarFactoryUtil.getCalendar(
				1970, Calendar.JANUARY, 1);

			startCreateDate = calendar.getTime();
		}

		sb.append(dateFormat.format(startCreateDate));

		sb.append(" TO ");

		Date endCreateDate = commerceOrderDisplayTerms.getEndCreateDate();

		if (endCreateDate == null) {
			Calendar calendar = CalendarFactoryUtil.getCalendar(
				3000, Calendar.DECEMBER, 31);

			endCreateDate = calendar.getTime();
		}

		sb.append(dateFormat.format(endCreateDate));

		sb.append(CharPool.CLOSE_BRACKET);

		return sb.toString();
	}

	private long _getNavigationItemCount(String tabs1) throws PortalException {
		SearchContext searchContext = new SearchContext();

		_addFacetOrderStatus(
			searchContext, tabs1, CommerceOrderConstants.ORDER_STATUS_ANY,
			true);
		_addFacetStatus(searchContext);

		searchContext.setAttribute(
			"siteGroupId", _commerceOrderRequestHelper.getSiteGroupId());
		searchContext.setAttribute(
			"useSearchResultPermissionFilter", Boolean.FALSE);

		searchContext.setCompanyId(_commerceOrderRequestHelper.getCompanyId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return _commerceOrderLocalService.searchCommerceOrdersCount(
			searchContext);
	}

	private void _initNavigationItems() throws PortalException {
		_navigationItems = new ArrayList<>(3);

		_navigationItems.add(_buildNavigationItem("open"));
		_navigationItems.add(_buildNavigationItem("pending"));
		_navigationItems.add(_buildNavigationItem("transmitted"));
	}

	private void _initSearch() throws PortalException {
		boolean filterByStatuses = false;

		if (_tabs1.equals("transmitted")) {
			filterByStatuses = true;
		}

		_searchContainer = new CommerceOrderSearch(
			_commerceOrderRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), filterByStatuses);

		_searchContainer.setEmptyResultsMessage(
			_getEmptyResultsMessage(filterByStatuses));

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_commerceOrderRequestHelper.getLiferayPortletResponse()));

		SearchContext searchContext = _buildSearchContext();

		BaseModelSearchResult<CommerceOrder> baseModelSearchResult =
			_commerceOrderLocalService.searchCommerceOrders(searchContext);

		_searchContainer.setTotal(baseModelSearchResult.getLength());
		_searchContainer.setResults(baseModelSearchResult.getBaseModels());

		_availableAdvanceStatusKVPs = _buildFacetKeyValuePairs(
			searchContext, "advanceStatus", key -> key);

		_availableOrderOrganizationKVPs = _buildFacetKeyValuePairs(
			searchContext, "orderOrganizationId",
			key -> {
				long organizationId = GetterUtil.getLong(key);

				if (organizationId <= 0) {
					return null;
				}

				Organization organization =
					_commerceOrganizationService.getOrganization(
						organizationId);

				return organization.getName();
			});

		if (filterByStatuses) {
			_availableOrderStatusKVPs = _buildFacetKeyValuePairs(
				searchContext, "orderStatus",
				key -> {
					int orderStatus = GetterUtil.getInteger(key);

					return CommerceOrderConstants.getOrderStatusLabel(
						orderStatus);
				},
				String.valueOf(CommerceOrderConstants.ORDER_STATUS_OPEN));
		}
		else {
			_availableOrderStatusKVPs = Collections.emptyList();
		}
	}

	private List<KeyValuePair> _availableAdvanceStatusKVPs;
	private List<KeyValuePair> _availableOrderOrganizationKVPs;
	private List<KeyValuePair> _availableOrderStatusKVPs;
	private final Format _commerceOrderDateFormatDateTime;
	private final CommerceOrderHelper _commerceOrderHelper;
	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final CommerceOrganizationService _commerceOrganizationService;
	private final CommercePriceCalculationLocalService
		_commercePriceCalculationLocalService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final GroupLocalService _groupLocalService;
	private final JSONFactory _jsonFactory;
	private final String _keywords;
	private List<NavigationItem> _navigationItems;
	private SearchContainer<CommerceOrder> _searchContainer;
	private final boolean _showFilter;
	private final String _tabs1;

}
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

package com.liferay.commerce.organization.order.web.internal.display.context;

import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.organization.order.web.internal.display.context.util.CommerceOrganizationOrderRequestHelper;
import com.liferay.commerce.organization.order.web.internal.search.CommerceOrderDisplayTerms;
import com.liferay.commerce.organization.order.web.internal.search.CommerceOrderSearch;
import com.liferay.commerce.organization.order.web.internal.search.facet.NegatableSimpleFacet;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommercePriceCalculationLocalService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
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
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
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
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.util.PropsValues;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrganizationOrderDisplayContext {

	public CommerceOrganizationOrderDisplayContext(
			CommerceAddressService commerceAddressService,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderLocalService commerceOrderLocalService,
			CommerceOrderNoteService commerceOrderNoteService,
			CommerceOrderService commerceOrderService,
			CommerceOrganizationHelper commerceOrganizationHelper,
			CommercePriceCalculationLocalService
				commercePriceCalculationLocalService,
			CommercePriceFormatter commercePriceFormatter,
			CommerceShippingEngineRegistry commerceShippingEngineRegistry,
			JSONFactory jsonFactory,
			ModelResourcePermission<CommerceOrder> modelResourcePermission,
			RenderRequest renderRequest,
			WorkflowTaskManager workflowTaskManager)
		throws PortalException {

		_commerceAddressService = commerceAddressService;
		_commerceOrderItemService = commerceOrderItemService;
		_commerceOrderLocalService = commerceOrderLocalService;
		_commerceOrderNoteService = commerceOrderNoteService;
		_commerceOrderService = commerceOrderService;
		_commercePriceCalculationLocalService =
			commercePriceCalculationLocalService;
		_commercePriceFormatter = commercePriceFormatter;
		_commerceShippingEngineRegistry = commerceShippingEngineRegistry;
		_jsonFactory = jsonFactory;
		_modelResourcePermission = modelResourcePermission;
		_workflowTaskManager = workflowTaskManager;

		_commerceOrganizationOrderRequestHelper =
			new CommerceOrganizationOrderRequestHelper(renderRequest);

		ThemeDisplay themeDisplay =
			_commerceOrganizationOrderRequestHelper.getThemeDisplay();

		_commerceOrderDateFormatDate = FastDateFormatFactoryUtil.getDate(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
		_commerceOrderDateFormatTime = FastDateFormatFactoryUtil.getTime(
			DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());
		_commerceOrderDateFormatDateTime =
			FastDateFormatFactoryUtil.getDateTime(
				DateFormat.MEDIUM, DateFormat.MEDIUM, themeDisplay.getLocale(),
				themeDisplay.getTimeZone());

		_organization = commerceOrganizationHelper.getCurrentOrganization(
			_commerceOrganizationOrderRequestHelper.getRequest());

		_commerceOrderId = ParamUtil.getLong(renderRequest, "commerceOrderId");
		_commerceOrderNoteId = ParamUtil.getLong(
			renderRequest, "commerceOrderNoteId");
		_keywords = ParamUtil.getString(renderRequest, "keywords");
		_showFilter = ParamUtil.getBoolean(renderRequest, "showFilter");
		_tabs1 = ParamUtil.getString(renderRequest, "tabs1", "pending");
	}

	public List<KeyValuePair> getAvailableAdvanceStatusOVPs()
		throws PortalException {

		if (_availableAdvanceStatusOVPs == null) {
			_initSearch();
		}

		return _availableAdvanceStatusOVPs;
	}

	public List<CommerceAddress> getAvailableCommerceOrderAddresses()
		throws PortalException {

		return _commerceAddressService.getCommerceAddresses(
			_organization.getGroupId(), Organization.class.getName(),
			_organization.getOrganizationId());
	}

	public List<KeyValuePair> getAvailableOrderStatusOVPs()
		throws PortalException {

		if (_availableOrderStatusOVPs == null) {
			_initSearch();
		}

		return _availableOrderStatusOVPs;
	}

	public CommerceOrder getCommerceOrder() throws PortalException {
		if ((_commerceOrder == null) && (_commerceOrderId > 0)) {
			_commerceOrder = _commerceOrderService.getCommerceOrder(
				_commerceOrderId);
		}

		return _commerceOrder;
	}

	public String getCommerceOrderCustomerId(CommerceOrder commerceOrder) {
		long customerId = commerceOrder.getOrderOrganizationId();

		if (customerId <= 0) {
			customerId = commerceOrder.getOrderUserId();
		}

		return String.valueOf(customerId);
	}

	public String getCommerceOrderCustomerName(CommerceOrder commerceOrder)
		throws PortalException {

		Organization organization = commerceOrder.getOrderOrganization();

		if (organization != null) {
			return organization.getName();
		}

		User orderUser = commerceOrder.getOrderUser();

		return orderUser.getFullName();
	}

	public String getCommerceOrderDate(CommerceOrder commerceOrder) {
		return _commerceOrderDateFormatDate.format(
			commerceOrder.getCreateDate());
	}

	public String getCommerceOrderDateTime(CommerceOrder commerceOrder) {
		return _commerceOrderDateFormatDateTime.format(
			commerceOrder.getCreateDate());
	}

	public String getCommerceOrderItemPrice(CommerceOrderItem commerceOrderItem)
		throws PortalException {

		return _commercePriceFormatter.format(
			_commerceOrganizationOrderRequestHelper.getSiteGroupId(),
			commerceOrderItem.getPrice());
	}

	public SearchContainer<CommerceOrderItem>
		getCommerceOrderItemsSearchContainer() throws PortalException {

		if (_commerceOrderItemsSearchContainer != null) {
			return _commerceOrderItemsSearchContainer;
		}

		_commerceOrderItemsSearchContainer = new SearchContainer<>(
			_commerceOrganizationOrderRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "this-order-has-no-items");

		int total = _commerceOrderItemService.getCommerceOrderItemsCount(
			_commerceOrderId);

		List<CommerceOrderItem> results =
			_commerceOrderItemService.getCommerceOrderItems(
				_commerceOrderId, _commerceOrderItemsSearchContainer.getStart(),
				_commerceOrderItemsSearchContainer.getEnd());

		_commerceOrderItemsSearchContainer.setTotal(total);
		_commerceOrderItemsSearchContainer.setResults(results);

		return _commerceOrderItemsSearchContainer;
	}

	public CommerceOrderNote getCommerceOrderNote() throws PortalException {
		if ((_commerceOrderNote == null) && (_commerceOrderNoteId > 0)) {
			_commerceOrderNote = _commerceOrderNoteService.getCommerceOrderNote(
				_commerceOrderNoteId);
		}

		return _commerceOrderNote;
	}

	public List<CommerceOrderNote> getCommerceOrderNotes(
			CommerceOrder commerceOrder)
		throws PortalException {

		if (hasPermission(
				commerceOrder,
				CommerceOrderActionKeys.
					MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES)) {

			return _commerceOrderNoteService.getCommerceOrderNotes(
				commerceOrder.getCommerceOrderId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
		}

		return _commerceOrderNoteService.getCommerceOrderNotes(
			commerceOrder.getCommerceOrderId(), false);
	}

	public int getCommerceOrderNotesCount(CommerceOrder commerceOrder)
		throws PortalException {

		if (hasPermission(
				commerceOrder,
				CommerceOrderActionKeys.
					MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES)) {

			return _commerceOrderNoteService.getCommerceOrderNotesCount(
				commerceOrder.getCommerceOrderId());
		}

		return _commerceOrderNoteService.getCommerceOrderNotesCount(
			commerceOrder.getCommerceOrderId(), false);
	}

	public String getCommerceOrderPaymentMethodName(CommerceOrder commerceOrder)
		throws PortalException {

		CommercePaymentMethod commercePaymentMethod =
			commerceOrder.getCommercePaymentMethod();

		if (commercePaymentMethod == null) {
			return StringPool.BLANK;
		}

		String name = commercePaymentMethod.getName(
			_commerceOrganizationOrderRequestHelper.getLocale());

		if (!commercePaymentMethod.isActive()) {
			StringBundler sb = new StringBundler(4);

			sb.append(name);
			sb.append(" (");
			sb.append(
				LanguageUtil.get(
					_commerceOrganizationOrderRequestHelper.getRequest(),
					"inactive"));
			sb.append(CharPool.CLOSE_PARENTHESIS);

			name = sb.toString();
		}

		return name;
	}

	public String getCommerceOrderShippingMethodName(
			CommerceOrder commerceOrder)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			_commerceOrder.getCommerceShippingMethod();

		if (commerceShippingMethod == null) {
			return StringPool.BLANK;
		}

		return commerceShippingMethod.getName(
			_commerceOrganizationOrderRequestHelper.getLocale());
	}

	public String getCommerceOrderShippingOptionName(
			CommerceOrder commerceOrder)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			commerceOrder.getCommerceShippingMethod();

		if (commerceShippingMethod == null) {
			return StringPool.BLANK;
		}

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

		return commerceShippingEngine.getCommerceShippingOptionLabel(
			commerceOrder.getShippingOptionName(),
			_commerceOrganizationOrderRequestHelper.getLocale());
	}

	public String getCommerceOrderStatus(CommerceOrder commerceOrder) {
		return LanguageUtil.get(
			_commerceOrganizationOrderRequestHelper.getRequest(),
			CommerceOrderConstants.getOrderStatusLabel(
				commerceOrder.getOrderStatus()));
	}

	public String getCommerceOrderTime(CommerceOrder commerceOrder) {
		return _commerceOrderDateFormatTime.format(
			commerceOrder.getCreateDate());
	}

	public String getCommerceOrderTransitionMessage(String transitionName) {
		if (Validator.isNull(transitionName)) {
			transitionName = "proceed";
		}

		return LanguageUtil.get(
			_commerceOrganizationOrderRequestHelper.getRequest(),
			transitionName);
	}

	public List<ObjectValuePair<Long, String>> getCommerceOrderTransitionOVPs(
			CommerceOrder commerceOrder)
		throws PortalException {

		List<ObjectValuePair<Long, String>> transitionOVPs = new ArrayList<>();

		if (!commerceOrder.isOpen()) {
			transitionOVPs.add(
				new ObjectValuePair<Long, String>(0L, "reorder"));
		}

		ObjectValuePair<Long, String> approveOVP = null;

		if (commerceOrder.isOpen() && commerceOrder.isPending() &&
			hasPermission(
				commerceOrder,
				CommerceOrderActionKeys.APPROVE_COMMERCE_ORDER)) {

			approveOVP = new ObjectValuePair<>(0L, "approve");

			transitionOVPs.add(approveOVP);
		}

		if (commerceOrder.isOpen() && commerceOrder.isApproved() &&
			hasPermission(
				commerceOrder,
				CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER)) {

			transitionOVPs.add(new ObjectValuePair<>(0L, "checkout"));
		}

		if (commerceOrder.isOpen() && commerceOrder.isDraft() &&
			!commerceOrder.isEmpty() &&
			hasPermission(commerceOrder, ActionKeys.UPDATE)) {

			transitionOVPs.add(new ObjectValuePair<>(0L, "submit"));
		}

		int start = transitionOVPs.size();

		_populateTransitionOVPs(transitionOVPs, commerceOrder, true);
		_populateTransitionOVPs(transitionOVPs, commerceOrder, false);

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

	public List<NavigationItem> getNavigationItems() {
		List<NavigationItem> navigationItems = new ArrayList<>(2);

		navigationItems.add(_getNavigationItem("pending", 0));
		navigationItems.add(_getNavigationItem("archived", 0));

		return navigationItems;
	}

	public Organization getOrganization() {
		return _organization;
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceOrganizationOrderRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		if (_commerceOrderId > 0) {
			portletURL.setParameter(
				"commerceOrderId", String.valueOf(_commerceOrderId));
		}
		else {
			portletURL.setParameter("tabs1", _tabs1);
			portletURL.setParameter("showFilter", String.valueOf(_showFilter));
		}

		return portletURL;
	}

	public SearchContainer<CommerceOrder> getSearchContainer()
		throws PortalException {

		if (_searchContainer == null) {
			_initSearch();
		}

		return _searchContainer;
	}

	public boolean hasPermission(CommerceOrder commerceOrder, String actionId)
		throws PortalException {

		return _modelResourcePermission.contains(
			_commerceOrganizationOrderRequestHelper.getPermissionChecker(),
			commerceOrder, actionId);
	}

	public boolean hasPermission(long commerceOrderId, String actionId)
		throws PortalException {

		return _modelResourcePermission.contains(
			_commerceOrganizationOrderRequestHelper.getPermissionChecker(),
			commerceOrderId, actionId);
	}

	public boolean isShowAddButton() {
		if (_tabs1.equals("pending")) {
			return true;
		}

		return false;
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

	private void _addFacetOrderStatus(
		SearchContext searchContext,
		CommerceOrderDisplayTerms commerceOrderDisplayTerms) {

		NegatableSimpleFacet negatableSimpleFacet = new NegatableSimpleFacet(
			searchContext);

		negatableSimpleFacet.setFieldName("orderStatus");

		searchContext.addFacet(negatableSimpleFacet);

		int orderStatus = commerceOrderDisplayTerms.getOrderStatus();
		boolean negated = false;

		if (_tabs1.equals("pending")) {
			orderStatus = CommerceOrderConstants.ORDER_STATUS_OPEN;
		}
		else if (orderStatus == CommerceOrderConstants.ORDER_STATUS_ANY) {
			orderStatus = CommerceOrderConstants.ORDER_STATUS_OPEN;
			negated = true;
		}

		negatableSimpleFacet.setNegated(negated);

		searchContext.setAttribute(
			negatableSimpleFacet.getFieldId(), String.valueOf(orderStatus));
	}

	private List<KeyValuePair> _buildFacetKeyValuePairs(
		SearchContext searchContext, String fieldName,
		Function<String, String> labelFunction, String... excludedTerms) {

		Facet facet = searchContext.getFacet(fieldName);

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		List<KeyValuePair> keyValuePairs = new ArrayList<>(
			termCollectors.size());

		HttpServletRequest httpServletRequest =
			_commerceOrganizationOrderRequestHelper.getRequest();

		StringBundler sb = new StringBundler();

		for (TermCollector termCollector : termCollectors) {
			String term = termCollector.getTerm();

			if (ArrayUtil.contains(excludedTerms, term)) {
				continue;
			}

			sb.append(
				LanguageUtil.get(
					httpServletRequest, labelFunction.apply(term)));
			sb.append(" (");
			sb.append(termCollector.getFrequency());
			sb.append(CharPool.CLOSE_PARENTHESIS);

			keyValuePairs.add(new KeyValuePair(term, sb.toString()));

			sb.setIndex(0);
		}

		return keyValuePairs;
	}

	private SearchContext _buildSearchContext() {
		SearchContext searchContext = new SearchContext();

		CommerceOrderDisplayTerms commerceOrderDisplayTerms =
			(CommerceOrderDisplayTerms)_searchContainer.getDisplayTerms();

		_addFacetCreateDate(searchContext, commerceOrderDisplayTerms);
		_addFacetOrderStatus(searchContext, commerceOrderDisplayTerms);

		boolean useSearchResultPermissionFilter = true;

		if (_tabs1.equals("archived")) {
			_addFacetAdvanceStatus(searchContext, commerceOrderDisplayTerms);

			useSearchResultPermissionFilter = false;
		}

		searchContext.setAttribute(Field.ENTRY_CLASS_PK, _keywords);

		searchContext.setAttribute(
			"useSearchResultPermissionFilter", useSearchResultPermissionFilter);

		searchContext.setCompanyId(
			_commerceOrganizationOrderRequestHelper.getCompanyId());
		searchContext.setGroupIds(new long[] {_organization.getGroupId()});
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
			_commerceOrganizationOrderRequestHelper.getRequest();

		String argument = StringUtil.toLowerCase(
			LanguageUtil.get(httpServletRequest, _tabs1),
			_commerceOrganizationOrderRequestHelper.getLocale());

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

	private NavigationItem _getNavigationItem(String name, int count) {
		NavigationItem navigationItem = new NavigationItem();

		if (_tabs1.equals(name)) {
			navigationItem.setActive(true);
		}

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("tabs1", name);

		navigationItem.setHref(portletURL.toString());

		String label = LanguageUtil.get(
			_commerceOrganizationOrderRequestHelper.getRequest(), name);

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

	private void _initSearch() throws PortalException {
		boolean filterByStatuses = true;

		if (_tabs1.equals("pending")) {
			filterByStatuses = false;
		}

		_searchContainer = new CommerceOrderSearch(
			_commerceOrganizationOrderRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), filterByStatuses);

		_searchContainer.setEmptyResultsMessage(
			_getEmptyResultsMessage(filterByStatuses));

		if (_tabs1.equals("pending")) {
			_searchContainer.setRowChecker(
				new EmptyOnClickRowChecker(
					_commerceOrganizationOrderRequestHelper.
						getLiferayPortletResponse()));
		}

		SearchContext searchContext = _buildSearchContext();

		BaseModelSearchResult<CommerceOrder> baseModelSearchResult =
			_commerceOrderLocalService.searchCommerceOrders(searchContext);

		_searchContainer.setTotal(baseModelSearchResult.getLength());
		_searchContainer.setResults(baseModelSearchResult.getBaseModels());

		if (_tabs1.equals("archived")) {
			_availableAdvanceStatusOVPs = _buildFacetKeyValuePairs(
				searchContext, "advanceStatus", Function.identity());

			_availableOrderStatusOVPs = _buildFacetKeyValuePairs(
				searchContext, "orderStatus",
				key -> {
					int orderStatus = GetterUtil.getInteger(key);

					return CommerceOrderConstants.getOrderStatusLabel(
						orderStatus);
				},
				String.valueOf(CommerceOrderConstants.ORDER_STATUS_OPEN));
		}
		else {
			_availableAdvanceStatusOVPs = Collections.emptyList();
			_availableOrderStatusOVPs = Collections.emptyList();
		}
	}

	private void _populateTransitionOVPs(
			List<ObjectValuePair<Long, String>> transitionOVPs,
			CommerceOrder commerceOrder, boolean searchByUserRoles)
		throws PortalException {

		long companyId = commerceOrder.getCompanyId();
		long userId = _commerceOrganizationOrderRequestHelper.getUserId();

		List<WorkflowTask> workflowTasks = _workflowTaskManager.search(
			companyId, userId, null, CommerceOrder.class.getName(),
			new Long[] {commerceOrder.getCommerceOrderId()}, null, null, false,
			searchByUserRoles, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);

		for (WorkflowTask workflowTask : workflowTasks) {
			long workflowTaskId = workflowTask.getWorkflowTaskId();

			List<String> transitionNames =
				_workflowTaskManager.getNextTransitionNames(
					companyId, userId, workflowTaskId);

			for (String transitionName : transitionNames) {
				transitionOVPs.add(
					new ObjectValuePair<>(workflowTaskId, transitionName));
			}
		}
	}

	private List<KeyValuePair> _availableAdvanceStatusOVPs;
	private List<KeyValuePair> _availableOrderStatusOVPs;
	private final CommerceAddressService _commerceAddressService;
	private CommerceOrder _commerceOrder;
	private final Format _commerceOrderDateFormatDate;
	private final Format _commerceOrderDateFormatDateTime;
	private final Format _commerceOrderDateFormatTime;
	private final long _commerceOrderId;
	private final CommerceOrderItemService _commerceOrderItemService;
	private SearchContainer<CommerceOrderItem>
		_commerceOrderItemsSearchContainer;
	private final CommerceOrderLocalService _commerceOrderLocalService;
	private CommerceOrderNote _commerceOrderNote;
	private final long _commerceOrderNoteId;
	private final CommerceOrderNoteService _commerceOrderNoteService;
	private final CommerceOrderService _commerceOrderService;
	private final CommerceOrganizationOrderRequestHelper
		_commerceOrganizationOrderRequestHelper;
	private final CommercePriceCalculationLocalService
		_commercePriceCalculationLocalService;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CommerceShippingEngineRegistry
		_commerceShippingEngineRegistry;
	private final JSONFactory _jsonFactory;
	private final String _keywords;
	private final ModelResourcePermission<CommerceOrder>
		_modelResourcePermission;
	private final Organization _organization;
	private SearchContainer<CommerceOrder> _searchContainer;
	private final boolean _showFilter;
	private final String _tabs1;
	private final WorkflowTaskManager _workflowTaskManager;

}
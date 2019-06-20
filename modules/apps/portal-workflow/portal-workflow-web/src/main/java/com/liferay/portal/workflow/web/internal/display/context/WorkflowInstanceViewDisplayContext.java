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

package com.liferay.portal.workflow.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.web.internal.search.WorkflowInstanceSearch;
import com.liferay.portal.workflow.web.internal.util.WorkflowInstancePortletUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class WorkflowInstanceViewDisplayContext
	extends BaseWorkflowInstanceDisplayContext {

	public WorkflowInstanceViewDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		super(liferayPortletRequest, liferayPortletResponse);
	}

	public String getAssetIconCssClass(WorkflowInstance workflowInstance) {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			workflowInstance);

		return workflowHandler.getIconCssClass();
	}

	public String getAssetTitle(WorkflowInstance workflowInstance) {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			workflowInstance);

		long classPK = getWorkflowContextEntryClassPK(
			workflowInstance.getWorkflowContext());

		String title = workflowHandler.getTitle(
			classPK, workflowInstanceRequestHelper.getLocale());

		if (title != null) {
			return HtmlUtil.escape(title);
		}

		return getAssetType(workflowInstance);
	}

	public String getAssetType(WorkflowInstance workflowInstance) {
		WorkflowHandler<?> workflowHandler = getWorkflowHandler(
			workflowInstance);

		return workflowHandler.getType(
			workflowInstanceRequestHelper.getLocale());
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getViewPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public String getDefinition(WorkflowInstance workflowInstance)
		throws PortalException {

		WorkflowDefinition workflowDefinition =
			WorkflowDefinitionManagerUtil.getWorkflowDefinition(
				workflowInstanceRequestHelper.getCompanyId(),
				workflowInstance.getWorkflowDefinitionName(),
				workflowInstance.getWorkflowDefinitionVersion());

		return HtmlUtil.escape(
			workflowDefinition.getTitle(
				LanguageUtil.getLanguageId(
					workflowInstanceRequestHelper.getRequest())));
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = WorkflowInstancePortletUtil.getDisplayStyle(
				liferayPortletRequest, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public Date getEndDate(WorkflowInstance workflowInstance) {
		return workflowInstance.getEndDate();
	}

	public DropdownItemList getFilterOptions(
		HttpServletRequest httpServletRequest) {

		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							new DropdownItemList() {
								{
									add(
										_getFilterNavigationDropdownItem(
											"all"));

									add(
										_getFilterNavigationDropdownItem(
											"pending"));

									add(
										_getFilterNavigationDropdownItem(
											"completed"));
								}
							});

						dropdownGroupItem.setLabel(
							LanguageUtil.get(httpServletRequest, "filter"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							new DropdownItemList() {
								{
									add(
										_getOrderByDropdownItem(
											"last-activity-date"));

									add(_getOrderByDropdownItem("end-date"));
								}
							});

						dropdownGroupItem.setLabel(
							LanguageUtil.get(httpServletRequest, "order-by"));
					});
			}
		};
	}

	public String getHeaderTitle() {
		return "workflow-submissions";
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(liferayPortletRequest, "keywords");

		return _keywords;
	}

	public Date getLastActivityDate(WorkflowInstance workflowInstance)
		throws PortalException {

		WorkflowLog workflowLog = getLatestWorkflowLog(workflowInstance);

		if (workflowLog == null) {
			return null;
		}

		return workflowLog.getCreateDate();
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(request, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(request, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = portalPreferences.getValue(
				WorkflowPortletKeys.USER_WORKFLOW, "instance-order-by-col",
				"last-activity-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(request, "saveOrderBy");

			if (saveOrderBy) {
				portalPreferences.setValue(
					WorkflowPortletKeys.USER_WORKFLOW, "instance-order-by-col",
					_orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(request, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = portalPreferences.getValue(
				WorkflowPortletKeys.USER_WORKFLOW, "instance-order-by-type",
				"asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(request, "saveOrderBy");

			if (saveOrderBy) {
				portalPreferences.setValue(
					WorkflowPortletKeys.USER_WORKFLOW, "instance-order-by-type",
					_orderByType);
			}
		}

		return _orderByType;
	}

	public List<WorkflowHandler<?>> getSearchableAssetsWorkflowHandlers() {
		List<WorkflowHandler<?>> searchableAssetsWorkflowHandlers =
			new ArrayList<>();

		List<WorkflowHandler<?>> workflowHandlers =
			WorkflowHandlerRegistryUtil.getWorkflowHandlers();

		for (WorkflowHandler<?> workflowHandler : workflowHandlers) {
			if (workflowHandler.isAssetTypeSearchable()) {
				searchableAssetsWorkflowHandlers.add(workflowHandler);
			}
		}

		return searchableAssetsWorkflowHandlers;
	}

	public WorkflowInstanceSearch getSearchContainer() throws PortalException {
		PortletURL portletURL = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_searchContainer = new WorkflowInstanceSearch(
			liferayPortletRequest, portletURL);

		_searchContainer.setResults(
			getSearchContainerResults(
				_searchContainer.getStart(), _searchContainer.getEnd(),
				_searchContainer.getOrderByComparator()));

		_searchContainer.setTotal(getSearchContainerTotal());

		setSearchContainerEmptyResultsMessage(_searchContainer);

		return _searchContainer;
	}

	public String getSearchURL() {
		PortletURL portletURL = getViewPortletURL();

		ThemeDisplay themeDisplay =
			workflowInstanceRequestHelper.getThemeDisplay();

		portletURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

		return portletURL.toString();
	}

	public String getSortingURL(HttpServletRequest httpServletRequest)
		throws PortletException {

		String orderByType = ParamUtil.getString(
			httpServletRequest, "orderByType", "asc");

		LiferayPortletResponse response =
			workflowInstanceRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = response.createRenderURL();

		portletURL.setParameter(
			"orderByType", Objects.equals(orderByType, "asc") ? "desc" : "asc");

		String instanceNavigation = ParamUtil.getString(
			httpServletRequest, "navigation");

		if (Validator.isNotNull(instanceNavigation)) {
			portletURL.setParameter("navigation", instanceNavigation);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		portletURL.setParameter("tab", WorkflowWebKeys.WORKFLOW_TAB_INSTANCE);

		return portletURL.toString();
	}

	public String getStatus(WorkflowInstance workflowInstance) {
		return LanguageUtil.get(
			workflowInstanceRequestHelper.getRequest(),
			HtmlUtil.escape(workflowInstance.getState()));
	}

	public int getTotalItems() throws PortalException {
		SearchContainer searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	public PortletURL getViewPortletURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tab", WorkflowWebKeys.WORKFLOW_TAB_INSTANCE);
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public ViewTypeItemList getViewTypes() {
		return new ViewTypeItemList(getViewPortletURL(), getDisplayStyle()) {
			{
				addListViewTypeItem();

				addTableViewTypeItem();
			}
		};
	}

	public boolean isDisabledManagementBar() throws PortalException {
		SearchContainer searchContainer = getSearchContainer();

		return !searchContainer.hasResults();
	}

	public boolean isNavigationAll() {
		if (Objects.equals(getNavigation(), "all")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationCompleted() {
		if (Objects.equals(getNavigation(), "completed")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationPending() {
		if (Objects.equals(getNavigation(), "pending")) {
			return true;
		}

		return false;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected String getAssetType(String keywords) {
		for (WorkflowHandler<?> workflowHandler :
				getSearchableAssetsWorkflowHandlers()) {

			String assetType = workflowHandler.getType(
				workflowInstanceRequestHelper.getLocale());

			if (StringUtil.equalsIgnoreCase(keywords, assetType)) {
				return workflowHandler.getClassName();
			}
		}

		return StringPool.BLANK;
	}

	protected Boolean getCompleted() {
		if (isNavigationAll()) {
			return null;
		}

		if (isNavigationCompleted()) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	protected WorkflowLog getLatestWorkflowLog(
			WorkflowInstance workflowInstance)
		throws PortalException {

		List<WorkflowLog> workflowLogs =
			WorkflowLogManagerUtil.getWorkflowLogsByWorkflowInstance(
				workflowInstanceRequestHelper.getCompanyId(),
				workflowInstance.getWorkflowInstanceId(), null, 0, 1,
				WorkflowComparatorFactoryUtil.getLogCreateDateComparator());

		if (workflowLogs.isEmpty()) {
			return null;
		}

		return workflowLogs.get(0);
	}

	protected List<WorkflowInstance> getSearchContainerResults(
			int start, int end, OrderByComparator<WorkflowInstance> comparator)
		throws PortalException {

		return WorkflowInstanceManagerUtil.search(
			workflowInstanceRequestHelper.getCompanyId(), null, getKeywords(),
			getKeywords(), getAssetType(getKeywords()), getKeywords(),
			getKeywords(), getCompleted(), start, end, comparator);
	}

	protected int getSearchContainerTotal() throws PortalException {
		return WorkflowInstanceManagerUtil.searchCount(
			workflowInstanceRequestHelper.getCompanyId(), null, getKeywords(),
			getKeywords(), getAssetType(getKeywords()), getKeywords(),
			getKeywords(), getCompleted());
	}

	protected String getWorkflowContextEntryClassName(
		Map<String, Serializable> workflowContext) {

		return (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
	}

	protected long getWorkflowContextEntryClassPK(
		Map<String, Serializable> workflowContext) {

		return GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
	}

	protected WorkflowHandler<?> getWorkflowHandler(
		WorkflowInstance workflowInstance) {

		String className = getWorkflowContextEntryClassName(
			workflowInstance.getWorkflowContext());

		return WorkflowHandlerRegistryUtil.getWorkflowHandler(className);
	}

	protected void setSearchContainerEmptyResultsMessage(
		WorkflowInstanceSearch searchContainer) {

		DisplayTerms searchTerms = searchContainer.getDisplayTerms();

		if (isNavigationAll()) {
			searchContainer.setEmptyResultsMessage("there-are-no-instances");
		}
		else if (isNavigationPending()) {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-pending-instances");
		}
		else {
			searchContainer.setEmptyResultsMessage(
				"there-are-no-completed-instances");
		}

		if (Validator.isNotNull(searchTerms.getKeywords())) {
			searchContainer.setEmptyResultsMessage(
				searchContainer.getEmptyResultsMessage() +
					"-with-the-specified-search-criteria");
		}
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getFilterNavigationDropdownItem(String navigation) {

		return dropdownItem -> {
			dropdownItem.setActive(Objects.equals(getNavigation(), navigation));
			dropdownItem.setHref(
				getViewPortletURL(), "navigation", navigation, "mvcPath",
				"/view.jsp");
			dropdownItem.setLabel(LanguageUtil.get(request, navigation));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(Objects.equals(getOrderByCol(), orderByCol));
			dropdownItem.setHref(getViewPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(LanguageUtil.get(request, orderByCol));
		};
	}

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private String _displayStyle;
	private String _keywords;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private WorkflowInstanceSearch _searchContainer;

}
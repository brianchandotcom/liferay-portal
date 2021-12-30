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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
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
import com.liferay.portal.kernel.workflow.search.WorkflowModelSearchResult;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
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

		String title = workflowHandler.getTitle(
			getWorkflowContextEntryClassPK(
				workflowInstance.getWorkflowContext()),
			workflowInstanceRequestHelper.getLocale());

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
		return PortletURLBuilder.create(
			getViewPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
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

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getFilterNavigationDropdownItem("all")
					).add(
						_getFilterNavigationDropdownItem("pending")
					).add(
						_getFilterNavigationDropdownItem("completed")
					).build());

				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "filter"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getOrderByDropdownItem("last-activity-date")
					).add(
						_getOrderByDropdownItem("end-date")
					).build());

				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "order-by"));
			}
		).build();
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

		WorkflowLog workflowLog = _getLatestWorkflowLog(workflowInstance);

		if (workflowLog == null) {
			return null;
		}

		return workflowLog.getCreateDate();
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(
			httpServletRequest, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			httpServletRequest, WorkflowPortletKeys.USER_WORKFLOW,
			"instance-order-by-col", "last-activity-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			httpServletRequest, WorkflowPortletKeys.USER_WORKFLOW,
			"instance-order-by-type", "asc");

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
		if (Objects.nonNull(_searchContainer)) {
			return _searchContainer;
		}

		_searchContainer = new WorkflowInstanceSearch(
			liferayPortletRequest,
			PortletURLUtil.getCurrent(
				liferayPortletRequest, liferayPortletResponse));

		WorkflowModelSearchResult<WorkflowInstance> workflowModelSearchResult =
			getWorkflowModelSearchResult(
				_searchContainer.getStart(), _searchContainer.getEnd(),
				_searchContainer.getOrderByComparator());

		_searchContainer.setResults(
			workflowModelSearchResult.getWorkflowModels());
		_searchContainer.setTotal(workflowModelSearchResult.getLength());

		setSearchContainerEmptyResultsMessage(_searchContainer);

		return _searchContainer;
	}

	public String getSearchURL() {
		ThemeDisplay themeDisplay =
			workflowInstanceRequestHelper.getThemeDisplay();

		return PortletURLBuilder.create(
			getViewPortletURL()
		).setParameter(
			"groupId", themeDisplay.getScopeGroupId()
		).buildString();
	}

	public String getSortingURL(HttpServletRequest httpServletRequest)
		throws PortletException {

		return PortletURLBuilder.createRenderURL(
			workflowInstanceRequestHelper.getLiferayPortletResponse()
		).setNavigation(
			() -> {
				String navigation = getNavigation();

				if (Validator.isNotNull(navigation)) {
					return navigation;
				}

				return null;
			}
		).setParameter(
			"orderByCol",
			() -> {
				String orderByCol = getOrderByCol();

				if (Validator.isNotNull(orderByCol)) {
					return orderByCol;
				}

				return null;
			}
		).setParameter(
			"orderByType",
			() -> {
				if (Objects.equals(getOrderByType(), "asc")) {
					return "desc";
				}

				return "asc";
			}
		).setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_INSTANCE
		).buildString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<WorkflowInstance> searchContainer =
			getSearchContainer();

		return searchContainer.getTotal();
	}

	public PortletURL getViewPortletURL() {
		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setNavigation(
			getNavigation()
		).setParameter(
			"orderByType", getOrderByType()
		).setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_INSTANCE
		).buildPortletURL();
	}

	public ViewTypeItemList getViewTypes() {
		return new ViewTypeItemList(getViewPortletURL(), getDisplayStyle()) {
			{
				addListViewTypeItem();

				addTableViewTypeItem();
			}
		};
	}

	public String getWorkflowContextEntryClassName(
		Map<String, Serializable> workflowContext) {

		return (String)workflowContext.get(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
	}

	public long getWorkflowContextEntryClassPK(
		Map<String, Serializable> workflowContext) {

		return GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
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

	protected WorkflowHandler<?> getWorkflowHandler(
		WorkflowInstance workflowInstance) {

		return WorkflowHandlerRegistryUtil.getWorkflowHandler(
			getWorkflowContextEntryClassName(
				workflowInstance.getWorkflowContext()));
	}

	protected WorkflowModelSearchResult<WorkflowInstance>
			getWorkflowModelSearchResult(
				int start, int end,
				OrderByComparator<WorkflowInstance> orderByComparator)
		throws PortalException {

		if (Objects.nonNull(workflowModelSearchResult)) {
			return workflowModelSearchResult;
		}

		workflowModelSearchResult =
			WorkflowInstanceManagerUtil.searchWorkflowInstances(
				workflowInstanceRequestHelper.getCompanyId(), null, true,
				getKeywords(), getKeywords(), getAssetType(getKeywords()),
				getKeywords(), getKeywords(), getCompleted(), true, start, end,
				orderByComparator);

		return workflowModelSearchResult;
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

	protected WorkflowModelSearchResult<WorkflowInstance>
		workflowModelSearchResult;

	private UnsafeConsumer<DropdownItem, Exception>
		_getFilterNavigationDropdownItem(String navigation) {

		return dropdownItem -> {
			dropdownItem.setActive(Objects.equals(getNavigation(), navigation));
			dropdownItem.setHref(
				getViewPortletURL(), "navigation", navigation, "mvcPath",
				"/view.jsp");
			dropdownItem.setLabel(
				LanguageUtil.get(httpServletRequest, navigation));
		};
	}

	private WorkflowLog _getLatestWorkflowLog(WorkflowInstance workflowInstance)
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

	private UnsafeConsumer<DropdownItem, Exception> _getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(Objects.equals(getOrderByCol(), orderByCol));
			dropdownItem.setHref(getViewPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(httpServletRequest, orderByCol));
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
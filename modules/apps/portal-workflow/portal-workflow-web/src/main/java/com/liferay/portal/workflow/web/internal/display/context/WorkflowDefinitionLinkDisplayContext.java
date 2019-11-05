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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.web.internal.constants.WorkflowDefinitionLinkResourcesConstants;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.web.internal.display.context.util.WorkflowDefinitionLinkRequestHelper;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearch;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearchEntry;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearchTerms;
import com.liferay.portal.workflow.web.internal.util.WorkflowDefinitionLinkPortletUtil;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionLinkSearchEntryLabelPredicate;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionLinkSearchEntryResourcePredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionLinkDisplayContext {

	public WorkflowDefinitionLinkDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService,
		ResourceBundleLoader resourceBundleLoader) {

		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;

		_liferayPortletRequest = PortalUtil.getLiferayPortletRequest(
			renderRequest);
		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			renderResponse);
		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_workflowDefinitionLinkRequestHelper =
			new WorkflowDefinitionLinkRequestHelper(renderRequest);

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);

		_resourceBundleLoader = resourceBundleLoader;
	}

	public WorkflowDefinition fetchDefaultWorkflowDefinition(String className)
		throws PortalException {

		WorkflowDefinitionLink defaultWorkflowDefinitionLink =
			_workflowDefinitionLinkLocalService.
				fetchDefaultWorkflowDefinitionLink(
					_workflowDefinitionLinkRequestHelper.getCompanyId(),
					className, 0, 0);

		if (defaultWorkflowDefinitionLink == null) {
			return null;
		}

		return WorkflowDefinitionManagerUtil.getLatestWorkflowDefinition(
			_workflowDefinitionLinkRequestHelper.getCompanyId(),
			defaultWorkflowDefinitionLink.getWorkflowDefinitionName());
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public String getDefaultWorkflowDefinitionLabel(String className)
		throws PortalException {

		if (isControlPanelPortlet()) {
			return LanguageUtil.get(
				_workflowDefinitionLinkRequestHelper.getRequest(),
				"no-workflow");
		}

		WorkflowDefinition defaultWorkflowDefinition =
			fetchDefaultWorkflowDefinition(className);

		if (defaultWorkflowDefinition == null) {
			return LanguageUtil.get(
				_workflowDefinitionLinkRequestHelper.getRequest(),
				"no-workflow");
		}

		String title = defaultWorkflowDefinition.getTitle(
			LanguageUtil.getLanguageId(
				_workflowDefinitionLinkRequestHelper.getLocale()));

		return LanguageUtil.format(
			_workflowDefinitionLinkRequestHelper.getRequest(), "default-x",
			title);
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
										_getOrderByDropdownItem(
											"resource",
											_getCurrentOrder(
												httpServletRequest)));

									add(
										_getOrderByDropdownItem(
											"workflow",
											_getCurrentOrder(
												httpServletRequest)));
								}
							});
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_workflowDefinitionLinkRequestHelper.
									getRequest(),
								"order-by"));
					});
			}
		};
	}

	public long getGroupId() {
		if (isControlPanelPortlet()) {
			return WorkflowConstants.DEFAULT_GROUP_ID;
		}

		ThemeDisplay themeDisplay =
			_workflowDefinitionLinkRequestHelper.getThemeDisplay();

		return themeDisplay.getSiteGroupIdOrLiveGroupId();
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_httpServletRequest, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
				"definition-link-order-by-col", "resource");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
					"definition-link-order-by-col", _orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_httpServletRequest, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = _portalPreferences.getValue(
				WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
				"definition-link-order-by-type", "asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
					"definition-link-order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK);
		portletURL.setParameter("tabs1", "default-configuration");

		String delta = ParamUtil.getString(_httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		portletURL.setParameter("orderByType", orderByType);

		return portletURL;
	}

	public ResourceBundle getResourceBundle() {
		return _resourceBundleLoader.loadResourceBundle(
			_workflowDefinitionLinkRequestHelper.getLocale());
	}

	public Map<String, String> getResourceTooltips() {
		return HashMapBuilder.put(
			WorkflowDefinitionLinkResourcesConstants.BLOGS_ENTRY,
			LanguageUtil.get(
				getResourceBundle(),
				"workflow-triggered-on-blog-post-submission")
		).put(
			WorkflowDefinitionLinkResourcesConstants.CALENDAR_BOOKING,
			LanguageUtil.get(
				getResourceBundle(), "workflow-triggered-on-event-submission")
		).put(
			WorkflowDefinitionLinkResourcesConstants.COMMENT,
			LanguageUtil.get(
				getResourceBundle(), "workflow-triggered-on-comment-submission")
		).put(
			WorkflowDefinitionLinkResourcesConstants.KNOWLEDGE_BASE_ARTICLE,
			LanguageUtil.get(
				getResourceBundle(), "workflow-triggered-on-article-submission")
		).put(
			WorkflowDefinitionLinkResourcesConstants.MESSAGE_BOARDS_MESSAGE,
			LanguageUtil.get(
				getResourceBundle(), "workflow-triggered-on-message-submission")
		).put(
			WorkflowDefinitionLinkResourcesConstants.PAGE_REVISION,
			LanguageUtil.get(
				getResourceBundle(),
				"workflow-triggered-on-page-modification-in-the-stage-" +
					"enviroment")
		).put(
			WorkflowDefinitionLinkResourcesConstants.USER,
			LanguageUtil.get(
				getResourceBundle(),
				"workflow-triggered-on-guest-user-account-submission")
		).put(
			WorkflowDefinitionLinkResourcesConstants.WEB_CONTENT_ARTICLE,
			LanguageUtil.get(
				getResourceBundle(),
				"workflow-triggered-on-web-content-submission")
		).put(
			WorkflowDefinitionLinkResourcesConstants.WIKI_PAGE,
			LanguageUtil.get(
				getResourceBundle(),
				"workflow-triggered-on-wiki-page-submission")
		).build();
	}

	public WorkflowDefinitionLinkSearch getSearchContainer()
		throws PortalException {

		WorkflowDefinitionLinkSearch searchContainer =
			new WorkflowDefinitionLinkSearch(
				_liferayPortletRequest, getPortletURL());

		WorkflowDefinitionLinkSearchTerms searchTerms =
			(WorkflowDefinitionLinkSearchTerms)searchContainer.getSearchTerms();

		OrderByComparator<WorkflowDefinitionLinkSearchEntry> orderByComparator =
			WorkflowDefinitionLinkPortletUtil.
				getWorkflowDefinitionLinkOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		List<WorkflowDefinitionLinkSearchEntry>
			workflowDefinitionLinkSearchEntries =
				createWorkflowDefinitionLinkSearchEntryList();

		if (searchTerms.isAdvancedSearch()) {
			workflowDefinitionLinkSearchEntries = filter(
				workflowDefinitionLinkSearchEntries, searchTerms.getResource(),
				searchTerms.getWorkflow(), searchTerms.isAndOperator());
		}
		else {
			workflowDefinitionLinkSearchEntries = filter(
				workflowDefinitionLinkSearchEntries, searchTerms.getKeywords(),
				searchTerms.getKeywords(), false);
		}

		int total = workflowDefinitionLinkSearchEntries.size();

		searchContainer.setTotal(total);

		Collections.sort(
			workflowDefinitionLinkSearchEntries,
			searchContainer.getOrderByComparator());

		List<WorkflowDefinitionLinkSearchEntry> results = ListUtil.subList(
			workflowDefinitionLinkSearchEntries, searchContainer.getStart(),
			searchContainer.getEnd());

		searchContainer.setResults(results);

		return searchContainer;
	}

	public String getSearchURL() {
		PortletURL portletURL = getPortletURL();

		ThemeDisplay themeDisplay =
			_workflowDefinitionLinkRequestHelper.getThemeDisplay();

		portletURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

		return portletURL.toString();
	}

	public String getSortingURL() throws PortletException {
		LiferayPortletResponse response =
			_workflowDefinitionLinkRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = response.createRenderURL();

		portletURL.setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK);
		portletURL.setParameter("orderByCol", getOrderByCol());

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		portletURL.setParameter(
			"orderByType", Objects.equals(orderByType, "asc") ? "desc" : "asc");

		return portletURL.toString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	public String getWorkflowDefinitionLabel(
		WorkflowDefinition workflowDefinition) {

		return workflowDefinition.getTitle(
			LanguageUtil.getLanguageId(
				_workflowDefinitionLinkRequestHelper.getRequest()));
	}

	public List<WorkflowDefinition> getWorkflowDefinitions()
		throws PortalException {

		return WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(
			_workflowDefinitionLinkRequestHelper.getCompanyId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			WorkflowComparatorFactoryUtil.getDefinitionNameComparator(true));
	}

	public String getWorkflowDefinitionValue(
		WorkflowDefinition workflowDefinition) {

		return HtmlUtil.escapeAttribute(workflowDefinition.getName()) +
			StringPool.AT + workflowDefinition.getVersion();
	}

	public boolean isControlPanelPortlet() {
		String portletName = getPortletName();

		if (portletName.equals(WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW)) {
			return true;
		}

		return false;
	}

	public boolean isDisabledManagementBar() throws PortalException {
		SearchContainer searchContainer = getSearchContainer();

		return !searchContainer.hasResults();
	}

	public boolean isWorkflowDefinitionEquals(
		WorkflowDefinition definition1, WorkflowDefinition definition2) {

		if ((definition1 == null) || (definition2 == null)) {
			return false;
		}

		if (Objects.equals(definition1.getName(), definition2.getName()) &&
			Objects.equals(
				definition1.getVersion(), definition2.getVersion())) {

			return true;
		}

		return false;
	}

	public boolean isWorkflowDefinitionSelected(
			WorkflowDefinition workflowDefinition, String className)
		throws PortalException {

		WorkflowDefinitionLink workflowDefinitionLink =
			getWorkflowDefinitionLink(className);

		if (workflowDefinitionLink == null) {
			return false;
		}

		String workflowDefinitionName =
			workflowDefinitionLink.getWorkflowDefinitionName();

		if (workflowDefinitionName.equals(workflowDefinition.getName()) &&
			(workflowDefinitionLink.getWorkflowDefinitionVersion() ==
				workflowDefinition.getVersion())) {

			return true;
		}

		return false;
	}

	public boolean showStripeMessage(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession(false);

		String showDefinitionLinkInfo = SessionClicks.get(
			httpSession, "show_definition_link_info", StringPool.BLANK);

		if (Validator.isNull(showDefinitionLinkInfo)) {
			SessionClicks.put(
				httpSession, "show_definition_link_info",
				"show_definition_link_info");

			return true;
		}

		return false;
	}

	protected Predicate<WorkflowDefinitionLinkSearchEntry> createPredicate(
		String resource, String workflowDefinitionLabel, boolean andOperator) {

		Predicate<WorkflowDefinitionLinkSearchEntry> predicate =
			new WorkflowDefinitionLinkSearchEntryResourcePredicate(resource);

		if (andOperator) {
			predicate = predicate.and(
				new WorkflowDefinitionLinkSearchEntryLabelPredicate(
					workflowDefinitionLabel));
		}
		else {
			predicate = predicate.or(
				new WorkflowDefinitionLinkSearchEntryLabelPredicate(
					workflowDefinitionLabel));
		}

		return predicate;
	}

	protected WorkflowDefinitionLinkSearchEntry
			createWorkflowDefinitionLinkSearchEntry(
				WorkflowHandler<?> workflowHandler)
		throws PortalException {

		String resource = ResourceActionsUtil.getModelResource(
			_workflowDefinitionLinkRequestHelper.getLocale(),
			workflowHandler.getClassName());

		return new WorkflowDefinitionLinkSearchEntry(
			workflowHandler.getClassName(), resource,
			getWorkflowDefinitionLabel(workflowHandler));
	}

	protected List<WorkflowDefinitionLinkSearchEntry>
			createWorkflowDefinitionLinkSearchEntryList()
		throws PortalException {

		List<WorkflowDefinitionLinkSearchEntry>
			workflowDefinitionLinkSearchEntries = new ArrayList<>();

		for (WorkflowHandler<?> workflowHandler : getWorkflowHandlers()) {
			WorkflowDefinitionLinkSearchEntry
				workflowDefinitionLinkSearchEntry =
					createWorkflowDefinitionLinkSearchEntry(workflowHandler);

			workflowDefinitionLinkSearchEntries.add(
				workflowDefinitionLinkSearchEntry);
		}

		return workflowDefinitionLinkSearchEntries;
	}

	protected List<WorkflowDefinitionLinkSearchEntry> filter(
		List<WorkflowDefinitionLinkSearchEntry>
			workflowDefinitionLinkSearchEntries,
		String resource, String workflowDefinitionLabel, boolean andOperator) {

		if (Validator.isNull(resource) &&
			Validator.isNull(workflowDefinitionLabel)) {

			return workflowDefinitionLinkSearchEntries;
		}

		Predicate<WorkflowDefinitionLinkSearchEntry> predicate =
			createPredicate(resource, workflowDefinitionLabel, andOperator);

		return ListUtil.filter(
			workflowDefinitionLinkSearchEntries,
			workflowDefinitionLinkSearchEntry -> predicate.test(
				workflowDefinitionLinkSearchEntry));
	}

	protected String getPortletName() {
		ThemeDisplay themeDisplay =
			_workflowDefinitionLinkRequestHelper.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getPortletName();
	}

	protected String getWorkflowDefinitionLabel(
			WorkflowHandler<?> workflowHandler)
		throws PortalException {

		List<WorkflowDefinition> workflowDefinitions = getWorkflowDefinitions();

		for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
			if (isWorkflowDefinitionSelected(
					workflowDefinition, workflowHandler.getClassName())) {

				return getWorkflowDefinitionLabel(workflowDefinition);
			}
		}

		return getDefaultWorkflowDefinitionLabel(
			workflowHandler.getClassName());
	}

	protected WorkflowDefinitionLink getWorkflowDefinitionLink(String className)
		throws PortalException {

		try {
			if (isControlPanelPortlet()) {
				return _workflowDefinitionLinkLocalService.
					getDefaultWorkflowDefinitionLink(
						_workflowDefinitionLinkRequestHelper.getCompanyId(),
						className, 0, 0);
			}

			return _workflowDefinitionLinkLocalService.
				getWorkflowDefinitionLink(
					_workflowDefinitionLinkRequestHelper.getCompanyId(),
					getGroupId(), className, 0, 0, true);
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nswdle, nswdle);
			}

			return null;
		}
	}

	protected List<WorkflowHandler<?>> getWorkflowHandlers() {
		List<WorkflowHandler<?>> workflowHandlers = null;

		if (isControlPanelPortlet()) {
			workflowHandlers =
				WorkflowHandlerRegistryUtil.getWorkflowHandlers();
		}
		else {
			workflowHandlers =
				WorkflowHandlerRegistryUtil.getScopeableWorkflowHandlers();
		}

		return ListUtil.filter(workflowHandlers, WorkflowHandler::isVisible);
	}

	private String _getCurrentOrder(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(
			httpServletRequest, "orderByCol", "resource");
	}

	private UnsafeConsumer<DropdownItem, Exception> _getOrderByDropdownItem(
		String orderByCol, String currentOrder) {

		return dropdownItem -> {
			dropdownItem.setActive(Objects.equals(currentOrder, orderByCol));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_workflowDefinitionLinkRequestHelper.getRequest(),
					orderByCol));
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowDefinitionLinkDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final ResourceBundleLoader _resourceBundleLoader;
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;
	private final WorkflowDefinitionLinkRequestHelper
		_workflowDefinitionLinkRequestHelper;

}
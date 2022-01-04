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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.web.internal.constants.WorkflowDefinitionLinkResourcesConstants;
import com.liferay.portal.workflow.web.internal.display.context.helper.WorkflowDefinitionLinkRequestHelper;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearch;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearchEntry;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionLinkSearchTerms;
import com.liferay.portal.workflow.web.internal.util.WorkflowDefinitionLinkPortletUtil;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionLinkSearchEntryLabelPredicate;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionLinkSearchEntryResourcePredicate;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionScopePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
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

		return LanguageUtil.format(
			_workflowDefinitionLinkRequestHelper.getRequest(), "default-x",
			defaultWorkflowDefinition.getTitle(
				LanguageUtil.getLanguageId(
					_workflowDefinitionLinkRequestHelper.getLocale())));
	}

	public DropdownItemList getFilterOptions(
		HttpServletRequest httpServletRequest) {

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getOrderByDropdownItem(
							"resource", _getCurrentOrder(httpServletRequest))
					).add(
						_getOrderByDropdownItem(
							"workflow", _getCurrentOrder(httpServletRequest))
					).build());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_workflowDefinitionLinkRequestHelper.getRequest(),
						"order-by"));
			}
		).build();
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
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
			"definition-link-order-by-col", "resource");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
			"definition-link-order-by-type", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/view.jsp"
		).setKeywords(
			() -> {
				String keywords = ParamUtil.getString(
					_httpServletRequest, "keywords");

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setTabs1(
			"default-configuration"
		).setParameter(
			"delta",
			() -> {
				String delta = ParamUtil.getString(
					_httpServletRequest, "delta");

				if (Validator.isNotNull(delta)) {
					return delta;
				}

				return null;
			}
		).setParameter(
			"orderByType", getOrderByType()
		).setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK
		).buildPortletURL();
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

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(
			WorkflowDefinitionLinkPortletUtil.
				getWorkflowDefinitionLinkOrderByComparator(
					getOrderByCol(), getOrderByType()));
		searchContainer.setOrderByType(getOrderByType());

		List<WorkflowDefinitionLinkSearchEntry>
			workflowDefinitionLinkSearchEntries =
				_createWorkflowDefinitionLinkSearchEntryList();

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

		searchContainer.setResults(
			ListUtil.subList(
				ListUtil.sort(
					workflowDefinitionLinkSearchEntries,
					searchContainer.getOrderByComparator()),
				searchContainer.getStart(), searchContainer.getEnd()));
		searchContainer.setTotal(workflowDefinitionLinkSearchEntries.size());

		return searchContainer;
	}

	public String getSearchURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"groupId",
			() -> {
				ThemeDisplay themeDisplay =
					_workflowDefinitionLinkRequestHelper.getThemeDisplay();

				return themeDisplay.getScopeGroupId();
			}
		).buildString();
	}

	public String getSortingURL() throws PortletException {
		return PortletURLBuilder.createRenderURL(
			_workflowDefinitionLinkRequestHelper.getLiferayPortletResponse()
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType",
			() -> {
				if (Objects.equals(getOrderByType(), "asc")) {
					return "desc";
				}

				return "asc";
			}
		).setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK
		).buildString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<WorkflowDefinitionLinkSearchEntry> searchContainer =
			getSearchContainer();

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

		if (_workflowDefinitions != null) {
			return _workflowDefinitions;
		}

		_workflowDefinitions = ListUtil.filter(
			WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(
				_workflowDefinitionLinkRequestHelper.getCompanyId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				WorkflowComparatorFactoryUtil.getDefinitionNameComparator(
					true)),
			new WorkflowDefinitionScopePredicate(
				WorkflowDefinitionConstants.SCOPE_ALL));

		return _workflowDefinitions;
	}

	public String getWorkflowDefinitionValue(
		WorkflowDefinition workflowDefinition) {

		return HtmlUtil.escapeAttribute(workflowDefinition.getName()) +
			StringPool.AT + workflowDefinition.getVersion();
	}

	public boolean isControlPanelPortlet() {
		if (Objects.equals(
				_getPortletName(),
				WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW)) {

			return true;
		}

		return false;
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
			_getWorkflowDefinitionLink(className);

		if (workflowDefinitionLink == null) {
			return false;
		}

		if (Objects.equals(
				workflowDefinitionLink.getWorkflowDefinitionName(),
				workflowDefinition.getName()) &&
			(workflowDefinitionLink.getWorkflowDefinitionVersion() ==
				workflowDefinition.getVersion())) {

			return true;
		}

		return false;
	}

	public boolean showStripeMessage(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession(false);

		if (Validator.isNull(
				SessionClicks.get(
					httpSession, "show_definition_link_info",
					StringPool.BLANK))) {

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

	protected List<WorkflowHandler<?>> getWorkflowHandlers(Group group) {
		List<WorkflowHandler<?>> workflowHandlers = null;

		if (isControlPanelPortlet()) {
			workflowHandlers =
				WorkflowHandlerRegistryUtil.getWorkflowHandlers();
		}
		else {
			workflowHandlers =
				WorkflowHandlerRegistryUtil.getScopeableWorkflowHandlers();
		}

		return ListUtil.filter(
			workflowHandlers,
			workflowHandler -> workflowHandler.isVisible(group));
	}

	private WorkflowDefinitionLinkSearchEntry
			_createWorkflowDefinitionLinkSearchEntry(
				WorkflowHandler<?> workflowHandler, Locale locale)
		throws PortalException {

		return new WorkflowDefinitionLinkSearchEntry(
			workflowHandler.getClassName(), workflowHandler.getType(locale),
			getWorkflowDefinitionLabel(workflowHandler));
	}

	private List<WorkflowDefinitionLinkSearchEntry>
			_createWorkflowDefinitionLinkSearchEntryList()
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<WorkflowDefinitionLinkSearchEntry>
			workflowDefinitionLinkSearchEntries = new ArrayList<>();

		for (WorkflowHandler<?> workflowHandler :
				getWorkflowHandlers(themeDisplay.getScopeGroup())) {

			WorkflowDefinitionLinkSearchEntry
				workflowDefinitionLinkSearchEntry =
					_createWorkflowDefinitionLinkSearchEntry(
						workflowHandler, themeDisplay.getLocale());

			workflowDefinitionLinkSearchEntries.add(
				workflowDefinitionLinkSearchEntry);
		}

		return workflowDefinitionLinkSearchEntries;
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

	private String _getPortletName() {
		ThemeDisplay themeDisplay =
			_workflowDefinitionLinkRequestHelper.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getPortletName();
	}

	private WorkflowDefinitionLink _getWorkflowDefinitionLink(String className)
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
		catch (NoSuchWorkflowDefinitionLinkException
					noSuchWorkflowDefinitionLinkException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(
					noSuchWorkflowDefinitionLinkException,
					noSuchWorkflowDefinitionLinkException);
			}

			return null;
		}
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
	private List<WorkflowDefinition> _workflowDefinitions;

}
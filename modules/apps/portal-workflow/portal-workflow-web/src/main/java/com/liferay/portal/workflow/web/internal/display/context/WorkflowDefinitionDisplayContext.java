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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPCreationMenu;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.exception.IncompleteWorkflowInstancesException;
import com.liferay.portal.workflow.web.internal.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.web.internal.display.context.util.WorkflowDefinitionRequestHelper;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionSearch;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionSearchTerms;
import com.liferay.portal.workflow.web.internal.util.WorkflowDefinitionPortletUtil;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionActivePredicate;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionDescriptionPredicate;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionTitlePredicate;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionDisplayContext {

	public WorkflowDefinitionDisplayContext(
		RenderRequest renderRequest, ResourceBundleLoader resourceBundleLoader,
		UserLocalService userLocalService) {

		_resourceBundleLoader = resourceBundleLoader;
		_userLocalService = userLocalService;

		_workflowDefinitionRequestHelper = new WorkflowDefinitionRequestHelper(
			renderRequest);
	}

	public boolean canPublishWorkflowDefinition() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (_companyAdministratorCanPublish &&
			permissionChecker.isCompanyAdmin()) {

			return true;
		}

		if (permissionChecker.isOmniadmin()) {
			return true;
		}

		return false;
	}

	public String getActive(WorkflowDefinition workflowDefinition) {
		HttpServletRequest httpServletRequest =
			_workflowDefinitionRequestHelper.getRequest();

		if (workflowDefinition.isActive()) {
			return LanguageUtil.get(httpServletRequest, "yes");
		}

		return LanguageUtil.get(httpServletRequest, "no");
	}

	public String getClearResultsURL(HttpServletRequest httpServletRequest) {
		PortletURL clearResultsURL = _getPortletURL(httpServletRequest);

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public Date getCreatedDate(WorkflowDefinition workflowDefinition)
		throws PortalException {

		List<WorkflowDefinition> workflowDefinitions = getWorkflowDefinitions(
			workflowDefinition.getName());

		WorkflowDefinition firstWorkflowDefinition = workflowDefinitions.get(0);

		return firstWorkflowDefinition.getModifiedDate();
	}

	public JSPCreationMenu getCreationMenu(PageContext pageContext) {
		if (!canPublishWorkflowDefinition()) {
			return null;
		}

		LiferayPortletResponse liferayPortletResponse =
			_workflowDefinitionRequestHelper.getLiferayPortletResponse();

		return new JSPCreationMenu(pageContext) {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							liferayPortletResponse.createRenderURL(), "mvcPath",
							"/definition/edit_workflow_definition.jsp");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_workflowDefinitionRequestHelper.getRequest(),
								"new-workflow"));
					});
			}
		};
	}

	public String getCreatorUserName(WorkflowDefinition workflowDefinition)
		throws PortalException {

		List<WorkflowDefinition> workflowDefinitions = getWorkflowDefinitions(
			workflowDefinition.getName());

		WorkflowDefinition firstWorkflowDefinition = workflowDefinitions.get(0);

		return getUserName(firstWorkflowDefinition);
	}

	public String getDescription(WorkflowDefinition workflowDefinition) {
		return HtmlUtil.escape(workflowDefinition.getDescription());
	}

	public String getDuplicateTitle(WorkflowDefinition workflowDefinition) {
		if (workflowDefinition == null) {
			return StringPool.BLANK;
		}

		ResourceBundle resourceBundle = getResourceBundle();

		String title = workflowDefinition.getTitle();

		String defaultLanguageId = LocalizationUtil.getDefaultLanguageId(title);

		String newTitle = LanguageUtil.format(
			resourceBundle, "copy-of-x",
			workflowDefinition.getTitle(defaultLanguageId));

		return LocalizationUtil.updateLocalization(
			title, "title", newTitle, defaultLanguageId);
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
											"all",
											_getCurrentNavigation(
												httpServletRequest),
											"all"));

									add(
										_getFilterNavigationDropdownItem(
											"published",
											_getCurrentNavigation(
												httpServletRequest),
											"published"));

									add(
										_getFilterNavigationDropdownItem(
											"not-published",
											_getCurrentNavigation(
												httpServletRequest),
											"not-published"));
								}
							});

						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_workflowDefinitionRequestHelper.getRequest(),
								"filter"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							new DropdownItemList() {
								{
									add(
										_getOrderByDropdownItem(
											"last-modified",
											_getCurrentOrder(
												httpServletRequest),
											httpServletRequest));

									add(
										_getOrderByDropdownItem(
											"title",
											_getCurrentOrder(
												httpServletRequest),
											httpServletRequest));
								}
							});

						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_workflowDefinitionRequestHelper.getRequest(),
								"order-by"));
					});
			}
		};
	}

	public String getManageSubmissionsLink() {
		return _buildErrorLink(
			"configure-submissions", getWorkflowInstancesPortletURL());
	}

	public Object[] getMessageArguments(
			IncompleteWorkflowInstancesException
				incompleteWorkflowInstancesException)
		throws PortletException {

		return new Object[] {
			String.valueOf(
				incompleteWorkflowInstancesException.
					getWorkflowInstancesCount()),
			getManageSubmissionsLink()
		};
	}

	public Object[] getMessageArguments(
			RequiredWorkflowDefinitionException
				requiredWorkflowDefinitionException)
		throws PortletException {

		List<WorkflowDefinitionLink> workflowDefinitionLinks =
			requiredWorkflowDefinitionException.getWorkflowDefinitionLinks();

		if (workflowDefinitionLinks.isEmpty()) {
			return new Object[0];
		}
		else if (workflowDefinitionLinks.size() == 1) {
			WorkflowDefinitionLink workflowDefinitionLink =
				workflowDefinitionLinks.get(0);

			return new Object[] {
				getLocalizedAssetName(workflowDefinitionLink.getClassName()),
				getConfigureAssignementLink()
			};
		}
		else if (workflowDefinitionLinks.size() == 2) {
			WorkflowDefinitionLink workflowDefinitionLink1 =
				workflowDefinitionLinks.get(0);
			WorkflowDefinitionLink workflowDefinitionLink2 =
				workflowDefinitionLinks.get(1);

			return new Object[] {
				getLocalizedAssetName(workflowDefinitionLink1.getClassName()),
				getLocalizedAssetName(workflowDefinitionLink2.getClassName()),
				getConfigureAssignementLink()
			};
		}
		else {
			int moreAssets = workflowDefinitionLinks.size() - 2;

			WorkflowDefinitionLink workflowDefinitionLink1 =
				workflowDefinitionLinks.get(0);
			WorkflowDefinitionLink workflowDefinitionLink2 =
				workflowDefinitionLinks.get(1);

			return new Object[] {
				getLocalizedAssetName(workflowDefinitionLink1.getClassName()),
				getLocalizedAssetName(workflowDefinitionLink2.getClassName()),
				moreAssets, getConfigureAssignementLink()
			};
		}
	}

	public String getMessageKey(
		IncompleteWorkflowInstancesException
			incompleteWorkflowInstancesException) {

		if (incompleteWorkflowInstancesException.getWorkflowInstancesCount() ==
				1) {

			return "there-is-x-unresolved-workflow-submission-x";
		}

		return "there-are-x-unresolved-workflow-submissions-x";
	}

	public String getMessageKey(
		RequiredWorkflowDefinitionException
			requiredWorkflowDefinitionException) {

		List<WorkflowDefinitionLink> workflowDefinitionLinks =
			requiredWorkflowDefinitionException.getWorkflowDefinitionLinks();

		if (workflowDefinitionLinks.isEmpty()) {
			return StringPool.BLANK;
		}
		else if (workflowDefinitionLinks.size() == 1) {
			return "workflow-in-use-remove-assignement-to-x-x";
		}
		else if (workflowDefinitionLinks.size() == 2) {
			return "workflow-in-use-remove-assignements-to-x-and-x-x";
		}

		return "workflow-in-use-remove-assignements-to-x-x-and-x-more-x";
	}

	public Date getModifiedDate(WorkflowDefinition workflowDefinition) {
		return workflowDefinition.getModifiedDate();
	}

	public String getName(WorkflowDefinition workflowDefinition) {
		return HtmlUtil.escape(workflowDefinition.getName());
	}

	public SearchContainer<WorkflowDefinition> getSearch(
			HttpServletRequest httpServletRequest, RenderRequest renderRequest,
			int status)
		throws PortalException {

		WorkflowDefinitionSearch workflowDefinitionSearch =
			new WorkflowDefinitionSearch(
				renderRequest, _getPortletURL(httpServletRequest));

		workflowDefinitionSearch.setEmptyResultsMessage(
			"no-workflow-definitions-are-defined");

		List<WorkflowDefinition> workflowDefinitions =
			WorkflowDefinitionManagerUtil.getLatestWorkflowDefinitions(
				_workflowDefinitionRequestHelper.getCompanyId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getWorkflowDefinitionOrderByComparator());

		WorkflowDefinitionSearchTerms searchTerms =
			new WorkflowDefinitionSearchTerms(renderRequest);

		if (searchTerms.isAdvancedSearch()) {
			workflowDefinitions = filter(
				workflowDefinitions, searchTerms.getDescription(),
				searchTerms.getTitle(), status, searchTerms.isAndOperator());
		}
		else {
			workflowDefinitions = filter(
				workflowDefinitions, searchTerms.getKeywords(),
				searchTerms.getKeywords(), status, false);
		}

		workflowDefinitionSearch.setTotal(workflowDefinitions.size());

		if (workflowDefinitions.size() >
				(workflowDefinitionSearch.getEnd() -
					workflowDefinitionSearch.getStart())) {

			workflowDefinitions = ListUtil.subList(
				workflowDefinitions, workflowDefinitionSearch.getStart(),
				workflowDefinitionSearch.getEnd());
		}

		workflowDefinitionSearch.setResults(workflowDefinitions);

		return workflowDefinitionSearch;
	}

	public String getSearchURL(HttpServletRequest httpServletRequest) {
		PortletURL portletURL = _getPortletURL(null);

		ThemeDisplay themeDisplay =
			_workflowDefinitionRequestHelper.getThemeDisplay();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
		portletURL.setParameter("tab", WorkflowWebKeys.WORKFLOW_TAB_DEFINITION);

		return portletURL.toString();
	}

	public String getSortingURL(HttpServletRequest httpServletRequest)
		throws PortletException {

		String orderByType = ParamUtil.getString(
			httpServletRequest, "orderByType", "asc");

		LiferayPortletResponse liferayPortletResponse =
			_workflowDefinitionRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"orderByType", Objects.equals(orderByType, "asc") ? "desc" : "asc");

		String definitionsNavigation = ParamUtil.getString(
			httpServletRequest, "definitionsNavigation");

		if (Validator.isNotNull(definitionsNavigation)) {
			portletURL.setParameter(
				"definitionsNavigation", definitionsNavigation);
		}

		return portletURL.toString();
	}

	public String getTitle(WorkflowDefinition workflowDefinition) {
		ThemeDisplay themeDisplay =
			_workflowDefinitionRequestHelper.getThemeDisplay();

		return HtmlUtil.escape(
			workflowDefinition.getTitle(themeDisplay.getLanguageId()));
	}

	public int getTotalItems(
			HttpServletRequest httpServletRequest, RenderRequest renderRequest,
			int status)
		throws PortalException {

		SearchContainer<?> searchContainer = getSearch(
			httpServletRequest, renderRequest, status);

		return searchContainer.getTotal();
	}

	public String getUserName(WorkflowDefinition workflowDefinition) {
		User user = _userLocalService.fetchUser(workflowDefinition.getUserId());

		if ((user == null) || user.isDefaultUser() ||
			Validator.isNull(user.getFullName())) {

			return null;
		}

		return user.getFullName();
	}

	public String getUserNameOrBlank(WorkflowDefinition workflowDefinition) {
		String userName = getUserName(workflowDefinition);

		if (userName == null) {
			userName = StringPool.BLANK;
		}

		return userName;
	}

	public List<WorkflowDefinition> getWorkflowDefinitions(String name)
		throws PortalException {

		return WorkflowDefinitionManagerUtil.getWorkflowDefinitions(
			_workflowDefinitionRequestHelper.getCompanyId(), name,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public int getWorkflowDefinitionsCount(
			WorkflowDefinition workflowDefinition)
		throws PortalException {

		return WorkflowDefinitionManagerUtil.getWorkflowDefinitionsCount(
			_workflowDefinitionRequestHelper.getCompanyId(),
			workflowDefinition.getName());
	}

	public List<WorkflowDefinition> getWorkflowDefinitionsOrderByDesc(
			String name)
		throws PortalException {

		List<WorkflowDefinition> workflowDefinitions = getWorkflowDefinitions(
			name);

		if (workflowDefinitions.size() <= 1) {
			return workflowDefinitions;
		}

		Collections.reverse(workflowDefinitions);

		return workflowDefinitions;
	}

	public void setCompanyAdministratorCanPublish(
		boolean companyAdministratorCanPublish) {

		_companyAdministratorCanPublish = companyAdministratorCanPublish;
	}

	protected Predicate<WorkflowDefinition> createPredicate(
		String description, String title, int status, boolean andOperator) {

		Predicate<WorkflowDefinition> predicate =
			new WorkflowDefinitionTitlePredicate(title);

		if (andOperator) {
			predicate = predicate.and(
				new WorkflowDefinitionDescriptionPredicate(description));
		}
		else {
			predicate = predicate.or(
				new WorkflowDefinitionDescriptionPredicate(description));
		}

		return predicate.and(new WorkflowDefinitionActivePredicate(status));
	}

	protected List<WorkflowDefinition> filter(
		List<WorkflowDefinition> workflowDefinitions, String description,
		String title, int status, boolean andOperator) {

		if ((status == WorkflowDefinitionConstants.STATUS_ALL) &&
			Validator.isNull(title) && Validator.isNull(description)) {

			return workflowDefinitions;
		}

		Predicate<WorkflowDefinition> predicate = createPredicate(
			description, title, status, andOperator);

		return ListUtil.filter(
			workflowDefinitions,
			workflowDefinition -> predicate.test(workflowDefinition));
	}

	protected String getConfigureAssignementLink() {
		return _buildErrorLink(
			"configure-assignments", getWorkflowDefinitionLinkPortletURL());
	}

	protected String getLocalizedAssetName(String className) {
		return ResourceActionsUtil.getModelResource(
			_workflowDefinitionRequestHelper.getLocale(), className);
	}

	protected ResourceBundle getResourceBundle() {
		return _resourceBundleLoader.loadResourceBundle(
			_workflowDefinitionRequestHelper.getLocale());
	}

	protected PortletURL getWorkflowDefinitionLinkPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_workflowDefinitionRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK);

		return portletURL;
	}

	protected OrderByComparator<WorkflowDefinition>
		getWorkflowDefinitionOrderByComparator() {

		String orderByCol = ParamUtil.getString(
			_workflowDefinitionRequestHelper.getRequest(), "orderByCol",
			"name");

		String orderByType = ParamUtil.getString(
			_workflowDefinitionRequestHelper.getRequest(), "orderByType",
			"asc");

		return WorkflowDefinitionPortletUtil.
			getWorkflowDefitionOrderByComparator(
				orderByCol, orderByType,
				_workflowDefinitionRequestHelper.getLocale());
	}

	protected PortletURL getWorkflowInstancesPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_workflowDefinitionRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW_INSTANCE,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view.jsp");

		return portletURL;
	}

	private String _buildErrorLink(String messageKey, PortletURL portletURL) {
		return StringUtil.replace(
			_HTML, new String[] {"[$RENDER_URL$]", "[$MESSAGE$]"},
			new String[] {
				portletURL.toString(),
				LanguageUtil.get(getResourceBundle(), messageKey)
			});
	}

	private String _getCurrentNavigation(
		HttpServletRequest httpServletRequest) {

		return ParamUtil.getString(
			httpServletRequest, "definitionsNavigation", "all");
	}

	private String _getCurrentOrder(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(httpServletRequest, "orderByCol", "title");
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getFilterNavigationDropdownItem(
			String navigation, String currentNavigation,
			String definitionsNavigation) {

		return dropdownItem -> {
			dropdownItem.setActive(
				Objects.equals(currentNavigation, navigation));

			dropdownItem.setHref(
				_getPortletURL(null), "definitionsNavigation",
				definitionsNavigation, "mvcPath", "/view.jsp", "tab",
				WorkflowWebKeys.WORKFLOW_TAB_DEFINITION);

			dropdownItem.setLabel(
				LanguageUtil.get(
					_workflowDefinitionRequestHelper.getRequest(), navigation));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception> _getOrderByDropdownItem(
		String orderByCol, String currentOrder,
		HttpServletRequest httpServletRequest) {

		return dropdownItem -> {
			dropdownItem.setActive(Objects.equals(currentOrder, orderByCol));
			dropdownItem.setHref(
				_getPortletURL(httpServletRequest), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_workflowDefinitionRequestHelper.getRequest(), orderByCol));
		};
	}

	private PortletURL _getPortletURL(HttpServletRequest httpServletRequest) {
		LiferayPortletResponse liferayPortletResponse =
			_workflowDefinitionRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		if (httpServletRequest == null) {
			return portletURL;
		}

		String definitionsNavigation = ParamUtil.getString(
			httpServletRequest, "definitionsNavigation");

		if (Validator.isNotNull(definitionsNavigation)) {
			portletURL.setParameter(
				"definitionsNavigation", definitionsNavigation);
		}

		String orderByType = ParamUtil.getString(
			httpServletRequest, "orderByType", "asc");

		portletURL.setParameter("orderByType", orderByType);

		return portletURL;
	}

	private static final String _HTML =
		"<a class='alert-link' href='[$RENDER_URL$]'>[$MESSAGE$]</a>";

	private boolean _companyAdministratorCanPublish;
	private final ResourceBundleLoader _resourceBundleLoader;
	private final UserLocalService _userLocalService;
	private final WorkflowDefinitionRequestHelper
		_workflowDefinitionRequestHelper;

}
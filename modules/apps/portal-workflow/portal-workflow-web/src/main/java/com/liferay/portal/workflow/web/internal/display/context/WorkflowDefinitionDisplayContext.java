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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPCreationMenu;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.exception.IncompleteWorkflowInstancesException;
import com.liferay.portal.workflow.web.internal.display.context.helper.WorkflowDefinitionRequestHelper;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionSearch;
import com.liferay.portal.workflow.web.internal.search.WorkflowDefinitionSearchTerms;
import com.liferay.portal.workflow.web.internal.util.WorkflowDefinitionPortletUtil;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionActivePredicate;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionDescriptionPredicate;
import com.liferay.portal.workflow.web.internal.util.filter.WorkflowDefinitionScopePredicate;
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

		if ((_companyAdministratorCanPublish &&
			 permissionChecker.isCompanyAdmin()) ||
			permissionChecker.isOmniadmin()) {

			return true;
		}

		return false;
	}

	public String getClearResultsURL(HttpServletRequest httpServletRequest) {
		return PortletURLBuilder.create(
			_getPortletURL(httpServletRequest)
		).setKeywords(
			StringPool.BLANK
		).buildString();
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

		return getUserName(workflowDefinitions.get(0));
	}

	public String getDescription(WorkflowDefinition workflowDefinition) {
		return HtmlUtil.escape(workflowDefinition.getDescription());
	}

	public String getDuplicateTitle(WorkflowDefinition workflowDefinition) {
		if (workflowDefinition == null) {
			return StringPool.BLANK;
		}

		String defaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			workflowDefinition.getTitle());

		String newTitle = LanguageUtil.format(
			getResourceBundle(), "copy-of-x",
			workflowDefinition.getTitle(defaultLanguageId));

		return LocalizationUtil.updateLocalization(
			workflowDefinition.getTitle(), "title", newTitle,
			defaultLanguageId);
	}

	public DropdownItemList getFilterOptions(
		HttpServletRequest httpServletRequest) {

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getFilterNavigationDropdownItem(
							"all", _getCurrentNavigation(httpServletRequest),
							"all")
					).add(
						_getFilterNavigationDropdownItem(
							"published",
							_getCurrentNavigation(httpServletRequest),
							"published")
					).add(
						_getFilterNavigationDropdownItem(
							"not-published",
							_getCurrentNavigation(httpServletRequest),
							"not-published")
					).build());

				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_workflowDefinitionRequestHelper.getRequest(),
						"filter"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getOrderByDropdownItem(
							httpServletRequest, "last-modified")
					).add(
						_getOrderByDropdownItem(httpServletRequest, "title")
					).build());

				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_workflowDefinitionRequestHelper.getRequest(),
						"order-by"));
			}
		).build();
	}

	public String getManageSubmissionsLink() {
		return _buildErrorLink(
			"configure-submissions", _getWorkflowInstancesPortletURL());
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
				_getLocalizedAssetName(workflowDefinitionLink.getClassName())
			};
		}
		else if (workflowDefinitionLinks.size() == 2) {
			WorkflowDefinitionLink workflowDefinitionLink1 =
				workflowDefinitionLinks.get(0);
			WorkflowDefinitionLink workflowDefinitionLink2 =
				workflowDefinitionLinks.get(1);

			return new Object[] {
				_getLocalizedAssetName(workflowDefinitionLink1.getClassName()),
				_getLocalizedAssetName(workflowDefinitionLink2.getClassName())
			};
		}
		else {
			WorkflowDefinitionLink workflowDefinitionLink1 =
				workflowDefinitionLinks.get(0);
			WorkflowDefinitionLink workflowDefinitionLink2 =
				workflowDefinitionLinks.get(1);

			return new Object[] {
				_getLocalizedAssetName(workflowDefinitionLink1.getClassName()),
				_getLocalizedAssetName(workflowDefinitionLink2.getClassName()),
				workflowDefinitionLinks.size() - 2
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
			return "workflow-is-in-use.-remove-its-assignment-to-x";
		}
		else if (workflowDefinitionLinks.size() == 2) {
			return "workflow-is-in-use.-remove-its-assignment-to-x-and-x";
		}

		return "workflow-is-in-use.-remove-its-assignment-to-x-x-and-x-more";
	}

	public Date getModifiedDate(WorkflowDefinition workflowDefinition) {
		return workflowDefinition.getModifiedDate();
	}

	public String getName(WorkflowDefinition workflowDefinition) {
		return HtmlUtil.escape(workflowDefinition.getName());
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_workflowDefinitionRequestHelper.getRequest(),
			WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
			"definition-order-by-col", "last-modified");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_workflowDefinitionRequestHelper.getRequest(),
			WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
			"definition-link-order-by-type", "asc");

		return _orderByType;
	}

	public SearchContainer<WorkflowDefinition> getSearch(
			HttpServletRequest httpServletRequest, RenderRequest renderRequest,
			int status)
		throws PortalException {

		if (Objects.nonNull(_workflowDefinitionSearch)) {
			return _workflowDefinitionSearch;
		}

		_workflowDefinitionSearch = new WorkflowDefinitionSearch(
			renderRequest, _getPortletURL(httpServletRequest));

		_workflowDefinitionSearch.setEmptyResultsMessage(
			"no-workflow-definitions-are-defined");

		List<WorkflowDefinition> workflowDefinitions =
			WorkflowDefinitionManagerUtil.getLatestWorkflowDefinitions(
				_workflowDefinitionRequestHelper.getCompanyId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				_getWorkflowDefinitionOrderByComparator());

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

		_workflowDefinitionSearch.setTotal(workflowDefinitions.size());

		if (workflowDefinitions.size() >
				(_workflowDefinitionSearch.getEnd() -
					_workflowDefinitionSearch.getStart())) {

			workflowDefinitions = ListUtil.subList(
				workflowDefinitions, _workflowDefinitionSearch.getStart(),
				_workflowDefinitionSearch.getEnd());
		}

		_workflowDefinitionSearch.setResults(workflowDefinitions);

		return _workflowDefinitionSearch;
	}

	public String getSearchURL(HttpServletRequest httpServletRequest) {
		return PortletURLBuilder.create(
			_getPortletURL(null)
		).setMVCPath(
			"/view.jsp"
		).setParameter(
			"groupId",
			() -> {
				ThemeDisplay themeDisplay =
					_workflowDefinitionRequestHelper.getThemeDisplay();

				return themeDisplay.getScopeGroupId();
			}
		).setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_DEFINITION
		).buildString();
	}

	public String getSortingURL(HttpServletRequest httpServletRequest)
		throws PortletException {

		return PortletURLBuilder.createRenderURL(
			_workflowDefinitionRequestHelper.getLiferayPortletResponse()
		).setParameter(
			"definitionsNavigation",
			() -> {
				String definitionsNavigation = ParamUtil.getString(
					httpServletRequest, "definitionsNavigation");

				if (Validator.isNotNull(definitionsNavigation)) {
					return definitionsNavigation;
				}

				return null;
			}
		).setParameter(
			"orderByCol",
			ParamUtil.getString(
				httpServletRequest, "orderByCol", "last-modified")
		).setParameter(
			"orderByType",
			() -> {
				if (Objects.equals(getOrderByType(), "asc")) {
					return "desc";
				}

				return "asc";
			}
		).buildString();
	}

	public String getTitle(WorkflowDefinition workflowDefinition) {
		if (workflowDefinition == null) {
			return _getLanguage("new-workflow");
		}

		if (Validator.isNull(workflowDefinition.getTitle())) {
			return _getLanguage("untitled-workflow");
		}

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
			new WorkflowDefinitionScopePredicate(
				WorkflowDefinitionConstants.SCOPE_ALL);

		if ((status == WorkflowConstants.STATUS_ANY) &&
			Validator.isNull(description) && Validator.isNull(title)) {

			return predicate;
		}

		predicate = predicate.and(new WorkflowDefinitionTitlePredicate(title));

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

		return ListUtil.filter(
			workflowDefinitions,
			createPredicate(description, title, status, andOperator));
	}

	protected ResourceBundle getResourceBundle() {
		return _resourceBundleLoader.loadResourceBundle(
			_workflowDefinitionRequestHelper.getLocale());
	}

	private String _buildErrorLink(String messageKey, PortletURL portletURL) {
		return StringUtil.replace(
			_HTML, new String[] {"[$RENDER_URL$]", "[$MESSAGE$]"},
			new String[] {
				portletURL.toString(),
				LanguageUtil.get(getResourceBundle(), messageKey)
			});
	}

	private String _getConfigureAssignementLink() {
		return _buildErrorLink(
			"configure-assignments", _getWorkflowDefinitionLinkPortletURL());
	}

	private String _getCurrentNavigation(
		HttpServletRequest httpServletRequest) {

		return ParamUtil.getString(
			httpServletRequest, "definitionsNavigation", "all");
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

	private String _getLanguage(String key) {
		return LanguageUtil.get(getResourceBundle(), key);
	}

	private String _getLocalizedAssetName(String className) {
		return ResourceActionsUtil.getModelResource(
			_workflowDefinitionRequestHelper.getLocale(), className);
	}

	private UnsafeConsumer<DropdownItem, Exception> _getOrderByDropdownItem(
		HttpServletRequest httpServletRequest, String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(Objects.equals(getOrderByCol(), orderByCol));
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

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	private PortletURL _getWorkflowDefinitionLinkPortletURL() {
		return PortletURLBuilder.createLiferayPortletURL(
			_workflowDefinitionRequestHelper.getLiferayPortletResponse(),
			WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW,
			PortletRequest.RENDER_PHASE
		).setMVCPath(
			"/view.jsp"
		).setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK
		).buildPortletURL();
	}

	private OrderByComparator<WorkflowDefinition>
		_getWorkflowDefinitionOrderByComparator() {

		return WorkflowDefinitionPortletUtil.
			getWorkflowDefitionOrderByComparator(
				ParamUtil.getString(
					_workflowDefinitionRequestHelper.getRequest(), "orderByCol",
					"name"),
				getOrderByType(), _workflowDefinitionRequestHelper.getLocale());
	}

	private PortletURL _getWorkflowInstancesPortletURL() {
		return PortletURLBuilder.createLiferayPortletURL(
			_workflowDefinitionRequestHelper.getLiferayPortletResponse(),
			WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW_INSTANCE,
			PortletRequest.RENDER_PHASE
		).setMVCPath(
			"/view.jsp"
		).buildPortletURL();
	}

	private static final String _HTML =
		"<a class='alert-link' href='[$RENDER_URL$]'>[$MESSAGE$]</a>";

	private boolean _companyAdministratorCanPublish;
	private String _orderByCol;
	private String _orderByType;
	private final ResourceBundleLoader _resourceBundleLoader;
	private final UserLocalService _userLocalService;
	private final WorkflowDefinitionRequestHelper
		_workflowDefinitionRequestHelper;
	private WorkflowDefinitionSearch _workflowDefinitionSearch;

}
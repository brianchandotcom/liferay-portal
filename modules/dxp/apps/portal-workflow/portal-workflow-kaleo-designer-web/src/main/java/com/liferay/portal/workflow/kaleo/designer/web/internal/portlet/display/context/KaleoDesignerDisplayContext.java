/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPCreationMenu;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.exception.IncompleteWorkflowInstancesException;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerActionKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDefinitionVersionPermission;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDesignerPermission;
import com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context.helper.KaleoDesignerRequestHelper;
import com.liferay.portal.workflow.kaleo.designer.web.internal.search.KaleoDefinitionVersionSearch;
import com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter.KaleoDefinitionVersionActivePredicate;
import com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter.KaleoDefinitionVersionScopePredicate;
import com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter.KaleoDefinitionVersionViewPermissionPredicate;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.runtime.action.ActionExecutorManager;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.util.comparator.KaleoDefinitionVersionActiveComparator;
import com.liferay.portal.workflow.kaleo.util.comparator.KaleoDefinitionVersionModifiedDateComparator;
import com.liferay.portal.workflow.kaleo.util.comparator.KaleoDefinitionVersionTitleComparator;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Rafael Praxedes
 */
public class KaleoDesignerDisplayContext {

	public KaleoDesignerDisplayContext(
		ActionExecutorManager actionExecutorManager,
		RenderRequest renderRequest,
		KaleoDefinitionVersionLocalService kaleoDefinitionVersionLocalService,
		PortletResourcePermission portletResourcePermission,
		ResourceBundleLoader resourceBundleLoader,
		UserLocalService userLocalService) {

		_actionExecutorManager = actionExecutorManager;
		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
		_portletResourcePermission = portletResourcePermission;
		_resourceBundleLoader = resourceBundleLoader;
		_userLocalService = userLocalService;

		_kaleoDesignerRequestHelper = new KaleoDesignerRequestHelper(
			renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public boolean canPublishWorkflowDefinition() {
		return _portletResourcePermission.contains(
			PermissionThreadLocal.getPermissionChecker(),
			_themeDisplay.getCompanyGroupId(),
			KaleoDesignerActionKeys.ADD_NEW_WORKFLOW);
	}

	public String getClearResultsURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(
				getPortletURL(),
				_kaleoDesignerRequestHelper.getLiferayPortletResponse())
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	public Date getCreatedDate(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		KaleoDefinitionVersion firstKaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.getFirstKaleoDefinitionVersion(
				kaleoDefinitionVersion.getCompanyId(),
				kaleoDefinitionVersion.getName());

		return firstKaleoDefinitionVersion.getCreateDate();
	}

	public JSPCreationMenu getCreationMenu(PageContext pageContext) {
		if (!canPublishWorkflowDefinition() ||
			!isSaveKaleoDefinitionVersionButtonVisible(null)) {

			return null;
		}

		LiferayPortletResponse liferayPortletResponse =
			_kaleoDesignerRequestHelper.getLiferayPortletResponse();

		return new JSPCreationMenu(pageContext) {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							liferayPortletResponse.createRenderURL(
								KaleoDesignerPortletKeys.KALEO_DESIGNER),
							"mvcPath", "/designer/edit_workflow_definition.jsp",
							"redirect",
							PortalUtil.getCurrentURL(
								_kaleoDesignerRequestHelper.getRequest()),
							"clearSessionMessage", "true");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_kaleoDesignerRequestHelper.getRequest(),
								"new-workflow"));
					});
			}
		};
	}

	public String getCreatorUserName(
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		return getUserName(
			_kaleoDefinitionVersionLocalService.getFirstKaleoDefinitionVersion(
				kaleoDefinitionVersion.getCompanyId(),
				kaleoDefinitionVersion.getName()));
	}

	public String getDuplicateTitle(KaleoDefinition kaleoDefinition) {
		if (kaleoDefinition == null) {
			return StringPool.BLANK;
		}

		String defaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			kaleoDefinition.getTitle());

		return LocalizationUtil.updateLocalization(
			kaleoDefinition.getTitle(), "title",
			LanguageUtil.format(
				_getResourceBundle(), "copy-of-x",
				kaleoDefinition.getTitle(defaultLanguageId)),
			defaultLanguageId);
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest httpServletRequest =
			_kaleoDesignerRequestHelper.getRequest();

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "order-by"));
			}
		).build();
	}

	public JSONArray getFunctionActionExecutorsJSONArray() throws Exception {
		return JSONUtil.putAll(
			_actionExecutorManager.getFunctionActionExecutorKeys());
	}

	public KaleoDefinition getKaleoDefinition(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		try {
			if (kaleoDefinitionVersion != null) {
				return kaleoDefinitionVersion.getKaleoDefinition();
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return null;
	}

	public int getKaleoDefinitionVersionCount(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		return _kaleoDefinitionVersionLocalService.
			getKaleoDefinitionVersionsCount(
				kaleoDefinitionVersion.getCompanyId(),
				kaleoDefinitionVersion.getName());
	}

	public OrderByComparator<KaleoDefinitionVersion>
		getKaleoDefinitionVersionOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<KaleoDefinitionVersion> orderByComparator = null;

		if (orderByCol.equals("title")) {
			orderByComparator = new KaleoDefinitionVersionTitleComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("last-modified")) {
			orderByComparator =
				new KaleoDefinitionVersionModifiedDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public List<KaleoDefinitionVersion> getKaleoDefinitionVersions(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		return _kaleoDefinitionVersionLocalService.getKaleoDefinitionVersions(
			kaleoDefinitionVersion.getCompanyId(),
			kaleoDefinitionVersion.getName(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS,
			new KaleoDefinitionVersionModifiedDateComparator(false));
	}

	public KaleoDefinitionVersionSearch getKaleoDefinitionVersionSearch(
		int status) {

		KaleoDefinitionVersionSearch kaleoDefinitionVersionSearch =
			new KaleoDefinitionVersionSearch(
				_kaleoDesignerRequestHelper.getLiferayPortletRequest(),
				getPortletURL());

		kaleoDefinitionVersionSearch.setOrderByCol(getOrderByCol());
		kaleoDefinitionVersionSearch.setOrderByComparator(
			getKaleoDefinitionVersionOrderByComparator(
				getOrderByCol(), getOrderByType()));
		kaleoDefinitionVersionSearch.setOrderByType(getOrderByType());

		_setKaleoDefinitionVersionSearchResults(
			kaleoDefinitionVersionSearch, status);

		return kaleoDefinitionVersionSearch;
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
				_getLocalizedAssetName(workflowDefinitionLink2.getClassName()),
				_getConfigureAssignementLink()
			};
		}

		WorkflowDefinitionLink workflowDefinitionLink1 =
			workflowDefinitionLinks.get(0);
		WorkflowDefinitionLink workflowDefinitionLink2 =
			workflowDefinitionLinks.get(1);

		return new Object[] {
			_getLocalizedAssetName(workflowDefinitionLink1.getClassName()),
			_getLocalizedAssetName(workflowDefinitionLink2.getClassName()),
			workflowDefinitionLinks.size() - 2, _getConfigureAssignementLink()
		};
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

	public Date getModifiedDate(KaleoDefinitionVersion kaleoDefinitionVersion) {
		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.getKaleoDefinition();

			return kaleoDefinition.getModifiedDate();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return kaleoDefinitionVersion.getModifiedDate();
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "orderByCol",
			"last-modified");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "orderByType", "asc");
	}

	public LiferayPortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_kaleoDesignerRequestHelper.getLiferayPortletResponse();

		LiferayPortletURL portletURL = liferayPortletResponse.createRenderURL(
			KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW);

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter("navigation", _getDefinitionsNavigation());

		String delta = ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
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

	public String getPublishKaleoDefinitionVersionButtonLabel(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		KaleoDefinition kaleoDefinition = getKaleoDefinition(
			kaleoDefinitionVersion);

		if ((kaleoDefinition != null) && kaleoDefinition.isActive()) {
			return "update";
		}

		return "publish";
	}

	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	public String getSearchContainerId() {
		return "kaleoDefinitionVersions";
	}

	public String getSortingURL() throws PortletException {
		PortletURL sortingURL = PortletURLUtil.clone(
			getPortletURL(),
			_kaleoDesignerRequestHelper.getLiferayPortletResponse());

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			sortingURL.setParameter(
				"orderByType",
				Objects.equals(orderByType, "asc") ? "desc" : "asc");
		}

		return sortingURL.toString();
	}

	public JSONArray getStatusesJSONArray() {
		return JSONUtil.putAll(
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					WorkflowConstants.LABEL_APPROVED)
			).put(
				"value", WorkflowConstants.STATUS_APPROVED
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					WorkflowConstants.LABEL_DENIED)
			).put(
				"value", WorkflowConstants.STATUS_DENIED
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					WorkflowConstants.LABEL_DRAFT)
			).put(
				"value", WorkflowConstants.STATUS_DRAFT
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					WorkflowConstants.LABEL_EXPIRED)
			).put(
				"value", WorkflowConstants.STATUS_EXPIRED
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					WorkflowConstants.LABEL_IN_TRASH)
			).put(
				"value", WorkflowConstants.STATUS_IN_TRASH
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					WorkflowConstants.LABEL_INACTIVE)
			).put(
				"value", WorkflowConstants.STATUS_INACTIVE
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					WorkflowConstants.LABEL_INCOMPLETE)
			).put(
				"value", WorkflowConstants.STATUS_INCOMPLETE
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					WorkflowConstants.LABEL_PENDING)
			).put(
				"value", WorkflowConstants.STATUS_PENDING
			),
			JSONUtil.put(
				"label",
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					WorkflowConstants.LABEL_SCHEDULED)
			).put(
				"value", WorkflowConstants.STATUS_SCHEDULED
			));
	}

	public String getTitle(KaleoDefinitionVersion kaleoDefinitionVersion) {
		if (kaleoDefinitionVersion == null) {
			return _getLanguage("new-workflow");
		}

		if (Validator.isNull(kaleoDefinitionVersion.getTitle())) {
			return _getLanguage("untitled-workflow");
		}

		ThemeDisplay themeDisplay =
			_kaleoDesignerRequestHelper.getThemeDisplay();

		return HtmlUtil.escape(
			kaleoDefinitionVersion.getTitle(themeDisplay.getLanguageId()));
	}

	public int getTotalItems(int status) {
		SearchContainer<?> searchContainer = getKaleoDefinitionVersionSearch(
			status);

		return searchContainer.getTotal();
	}

	public String getUserName(KaleoDefinitionVersion kaleoDefinitionVersion) {
		User user = _userLocalService.fetchUser(
			kaleoDefinitionVersion.getUserId());

		if ((user == null) || user.isGuestUser() ||
			Validator.isNull(user.getFullName())) {

			return null;
		}

		return user.getFullName();
	}

	public String getUserNameOrBlank(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		String userName = getUserName(kaleoDefinitionVersion);

		if (userName == null) {
			userName = StringPool.BLANK;
		}

		return userName;
	}

	public boolean isDefinitionInputDisabled(
		boolean previewBeforeRestore,
		KaleoDefinitionVersion kaleoDefinitionVersion,
		PermissionChecker permissionChecker) {

		if (previewBeforeRestore) {
			return true;
		}

		if ((kaleoDefinitionVersion == null) &&
			KaleoDesignerPermission.contains(
				permissionChecker, _themeDisplay.getCompanyGroupId(),
				KaleoDesignerActionKeys.ADD_NEW_WORKFLOW)) {

			return false;
		}

		try {
			if ((kaleoDefinitionVersion != null) &&
				KaleoDefinitionVersionPermission.contains(
					permissionChecker, kaleoDefinitionVersion,
					ActionKeys.UPDATE)) {

				return false;
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return true;
	}

	public boolean isPublishKaleoDefinitionVersionButtonVisible(
		PermissionChecker permissionChecker,
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		if (!canPublishWorkflowDefinition()) {
			return false;
		}

		if (kaleoDefinitionVersion != null) {
			try {
				return KaleoDefinitionVersionPermission.contains(
					permissionChecker, kaleoDefinitionVersion,
					ActionKeys.UPDATE);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		return KaleoDesignerPermission.contains(
			permissionChecker, _themeDisplay.getCompanyGroupId(),
			KaleoDesignerActionKeys.ADD_NEW_WORKFLOW);
	}

	public boolean isSaveKaleoDefinitionVersionButtonVisible(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		PermissionChecker permissionChecker =
			_kaleoDesignerRequestHelper.getPermissionChecker();

		if (kaleoDefinitionVersion != null) {
			KaleoDefinition kaleoDefinition = getKaleoDefinition(
				kaleoDefinitionVersion);

			if ((kaleoDefinition != null) && !kaleoDefinition.isActive()) {
				try {
					return KaleoDefinitionVersionPermission.contains(
						permissionChecker, kaleoDefinitionVersion,
						ActionKeys.UPDATE);
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException);
					}
				}
			}

			return false;
		}

		return KaleoDesignerPermission.contains(
			permissionChecker, _themeDisplay.getCompanyGroupId(),
			KaleoDesignerActionKeys.ADD_NEW_WORKFLOW);
	}

	public void setKaleoDesignerRequestHelper(RenderRequest renderRequest) {
		_kaleoDesignerRequestHelper = new KaleoDesignerRequestHelper(
			renderRequest);
	}

	private String _buildErrorLink(String messageKey, PortletURL portletURL) {
		return StringUtil.replace(
			_HTML, new String[] {"[$RENDER_URL$]", "[$MESSAGE$]"},
			new String[] {
				portletURL.toString(),
				LanguageUtil.get(_getResourceBundle(), messageKey)
			});
	}

	private String _getConfigureAssignementLink() {
		return _buildErrorLink(
			"configure-assignments", _getWorkflowDefinitionLinkPortletURL());
	}

	private String _getDefinitionsNavigation() {
		return ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "navigation", "all");
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getFilterNavigationDropdownItem(String definitionsNavigation) {

		return dropdownItem -> {
			dropdownItem.setActive(
				definitionsNavigation.equals(_getDefinitionsNavigation()));
			dropdownItem.setHref(
				getPortletURL(), "navigation", definitionsNavigation);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					definitionsNavigation));
		};
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			_getFilterNavigationDropdownItem("all")
		).add(
			_getFilterNavigationDropdownItem("not-published")
		).add(
			_getFilterNavigationDropdownItem("published")
		).build();
	}

	private String _getKeywords() {
		return ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "keywords");
	}

	private String _getLanguage(String key) {
		return LanguageUtil.get(_getResourceBundle(), key);
	}

	private String _getLocalizedAssetName(String className) {
		return ResourceActionsUtil.getModelResource(
			_kaleoDesignerRequestHelper.getLocale(), className);
	}

	private UnsafeConsumer<DropdownItem, Exception> _getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(), orderByCol));
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			_getOrderByDropdownItem("last-modified")
		).add(
			_getOrderByDropdownItem("title")
		).build();
	}

	private ResourceBundle _getResourceBundle() {
		return _resourceBundleLoader.loadResourceBundle(
			_kaleoDesignerRequestHelper.getLocale());
	}

	private PortletURL _getWorkflowDefinitionLinkPortletURL() {
		return PortletURLBuilder.createLiferayPortletURL(
			_kaleoDesignerRequestHelper.getLiferayPortletResponse(),
			KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW,
			PortletRequest.RENDER_PHASE
		).setMVCPath(
			"/view.jsp"
		).setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK
		).buildPortletURL();
	}

	private PortletURL _getWorkflowInstancesPortletURL() {
		return PortletURLBuilder.createLiferayPortletURL(
			_kaleoDesignerRequestHelper.getLiferayPortletResponse(),
			KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW_INSTANCE,
			PortletRequest.RENDER_PHASE
		).setMVCPath(
			"/view.jsp"
		).buildPortletURL();
	}

	private void _setKaleoDefinitionVersionSearchResults(
		SearchContainer<KaleoDefinitionVersion> searchContainer, int status) {

		List<KaleoDefinitionVersion> kaleoDefinitionVersions =
			_kaleoDefinitionVersionLocalService.
				getLatestKaleoDefinitionVersions(
					_kaleoDesignerRequestHelper.getCompanyId(), _getKeywords(),
					WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, searchContainer.getOrderByComparator());

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			new KaleoDefinitionVersionActivePredicate(status));

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			new KaleoDefinitionVersionScopePredicate(
				WorkflowDefinitionConstants.SCOPE_ALL));

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			new KaleoDefinitionVersionViewPermissionPredicate(
				_kaleoDesignerRequestHelper.getPermissionChecker(),
				_themeDisplay.getCompanyGroupId()));

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			kaleoDefinitionVersion -> {
				try {
					KaleoDefinition kaleoDefinition =
						kaleoDefinitionVersion.getKaleoDefinition();

					if (kaleoDefinition.getVersion() == GetterUtil.getFloat(
							kaleoDefinitionVersion.getVersion())) {

						return true;
					}
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException);
					}
				}

				return false;
			});

		KaleoDefinitionVersionActiveComparator
			kaleoDefinitionVersionActiveComparator =
				new KaleoDefinitionVersionActiveComparator();

		Collections.sort(
			kaleoDefinitionVersions,
			kaleoDefinitionVersionActiveComparator.thenComparing(
				searchContainer.getOrderByComparator()));

		List<KaleoDefinitionVersion> filteredKaleoDefinitionVersions =
			kaleoDefinitionVersions;

		searchContainer.setResultsAndTotal(
			() -> {
				if (filteredKaleoDefinitionVersions.size() >
						(searchContainer.getEnd() -
							searchContainer.getStart())) {

					return ListUtil.subList(
						filteredKaleoDefinitionVersions,
						searchContainer.getStart(), searchContainer.getEnd());
				}

				return filteredKaleoDefinitionVersions;
			},
			filteredKaleoDefinitionVersions.size());
	}

	private static final String _HTML =
		"<a class='alert-link' href='[$RENDER_URL$]'>[$MESSAGE$]</a>";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDesignerDisplayContext.class);

	private final ActionExecutorManager _actionExecutorManager;
	private final KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private KaleoDesignerRequestHelper _kaleoDesignerRequestHelper;
	private final PortletResourcePermission _portletResourcePermission;
	private final ResourceBundleLoader _resourceBundleLoader;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;

}
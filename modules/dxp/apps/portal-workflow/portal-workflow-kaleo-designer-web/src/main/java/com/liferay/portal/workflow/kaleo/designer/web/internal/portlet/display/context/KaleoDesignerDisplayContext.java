/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDefinitionVersionConstants;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerActionKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDefinitionVersionPermission;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDesignerPermission;
import com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context.util.KaleoDesignerRequestHelper;
import com.liferay.portal.workflow.kaleo.designer.web.internal.search.KaleoDefinitionVersionSearch;
import com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter.KaleoDefinitionVersionActivePredicateFilter;
import com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter.KaleoDefinitionVersionViewPermissionPredicateFilter;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.util.comparator.KaleoDefinitionVersionModifiedDateComparator;
import com.liferay.portal.workflow.kaleo.util.comparator.KaleoDefinitionVersionTitleComparator;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class KaleoDesignerDisplayContext {

	public KaleoDesignerDisplayContext(
		RenderRequest renderRequest,
		KaleoDefinitionVersionLocalService kaleoDefinitionVersionLocalService,
		ResourceBundleLoader resourceBundleLoader,
		UserLocalService userLocalService) {

		_renderRequest = renderRequest;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
		_resourceBundleLoader = resourceBundleLoader;
		_userLocalService = userLocalService;

		_kaleoDesignerRequestHelper = new KaleoDesignerRequestHelper(
			renderRequest);
	}

	public String getClearResultsURL() throws PortletException {
		PortletURL clearResultsURL = PortletURLUtil.clone(
			getPortletURL(),
			_kaleoDesignerRequestHelper.getLiferayPortletResponse());

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public Date getCreatedDate(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		KaleoDefinitionVersion firstKaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.getFirstKaleoDefinitionVersion(
				kaleoDefinitionVersion.getCompanyId(),
				kaleoDefinitionVersion.getName());

		return firstKaleoDefinitionVersion.getCreateDate();
	}

	public CreationMenu getCreationMenu() {
		if (!isSaveKaleoDefinitionVersionButtonVisible(null)) {
			return null;
		}

		return new CreationMenu() {
			{
				LiferayPortletResponse liferayPortletResponse =
					_kaleoDesignerRequestHelper.getLiferayPortletResponse();

				LiferayPortletURL liferayPortletURL =
					liferayPortletResponse.createRenderURL(
						KaleoDesignerPortletKeys.KALEO_DESIGNER);

				liferayPortletURL.setParameter(
					"mvcPath", "/designer/edit_kaleo_definition_version.jsp");
				liferayPortletURL.setParameter(
					"redirect",
					PortalUtil.getCurrentURL(
						_kaleoDesignerRequestHelper.getRequest()));
				liferayPortletURL.setParameter("clearSessionMessage", "true");

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(liferayPortletURL);

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

		KaleoDefinitionVersion firstKaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.getFirstKaleoDefinitionVersion(
				kaleoDefinitionVersion.getCompanyId(),
				kaleoDefinitionVersion.getName());

		return getUserName(firstKaleoDefinitionVersion);
	}

	public String getDuplicateTitle(KaleoDefinition kaleoDefinition) {
		if (kaleoDefinition == null) {
			return StringPool.BLANK;
		}

		String title = kaleoDefinition.getTitle();

		String defaultLanguageId = LocalizationUtil.getDefaultLanguageId(title);

		return LanguageUtil.format(
			getResourceBundle(), "copy-of-x",
			HtmlUtil.escape(kaleoDefinition.getTitle(defaultLanguageId)));
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest request = _kaleoDesignerRequestHelper.getRequest();

		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "order-by"));
					});
			}
		};
	}

	public KaleoDefinition getKaleoDefinition(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		try {
			if (kaleoDefinitionVersion != null) {
				return kaleoDefinitionVersion.getKaleoDefinition();
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
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

	public KaleoDefinitionVersionSearch getKaleoDefinitionVersionSearch() {
		KaleoDefinitionVersionSearch kaleoDefinitionVersionSearch =
			new KaleoDefinitionVersionSearch(
				_kaleoDesignerRequestHelper.getLiferayPortletRequest(),
				getPortletURL());

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<KaleoDefinitionVersion> orderByComparator =
			getKaleoDefinitionVersionOrderByComparator(orderByCol, orderByType);

		kaleoDefinitionVersionSearch.setOrderByCol(orderByCol);
		kaleoDefinitionVersionSearch.setOrderByComparator(orderByComparator);
		kaleoDefinitionVersionSearch.setOrderByType(orderByType);

		setKaleoDefinitionVersionSearchResults(kaleoDefinitionVersionSearch);

		return kaleoDefinitionVersionSearch;
	}

	public Object[] getMessageArguments(
			List<WorkflowDefinitionLink> workflowDefinitionLinks,
			HttpServletRequest request)
		throws PortletException {

		if (workflowDefinitionLinks.isEmpty()) {
			return new Object[0];
		}

		WorkflowDefinitionLink firstWorkflowDefinitionLink =
			workflowDefinitionLinks.get(0);

		if (workflowDefinitionLinks.size() == 1) {
			return new Object[] {
				getLocalizedAssetName(
					firstWorkflowDefinitionLink.getClassName()),
				getConfigureAssignementLink(request)
			};
		}

		WorkflowDefinitionLink secondWorkflowDefinitionLink =
			workflowDefinitionLinks.get(1);

		if (workflowDefinitionLinks.size() == 2) {
			return new Object[] {
				getLocalizedAssetName(
					firstWorkflowDefinitionLink.getClassName()),
				getLocalizedAssetName(
					secondWorkflowDefinitionLink.getClassName()),
				getConfigureAssignementLink(request)
			};
		}

		int moreAssetsCount = workflowDefinitionLinks.size() - 2;

		return new Object[] {
			getLocalizedAssetName(firstWorkflowDefinitionLink.getClassName()),
			getLocalizedAssetName(secondWorkflowDefinitionLink.getClassName()),
			moreAssetsCount, getConfigureAssignementLink(request)
		};
	}

	public String getMessageKey(
		List<WorkflowDefinitionLink> workflowDefinitionLinks) {

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

	public Date getModifiedDate(KaleoDefinitionVersion kaleoDefinitionVersion) {
		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.getKaleoDefinition();

			return kaleoDefinition.getModifiedDate();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return kaleoDefinitionVersion.getModifiedDate();
	}

	public String getOrderByCol() {
		String orderByCol = ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "orderByCol",
			"last-modified");

		return orderByCol;
	}

	public String getOrderByType() {
		String orderByType = ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "orderByType", "asc");

		return orderByType;
	}

	public LiferayPortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_kaleoDesignerRequestHelper.getLiferayPortletResponse();

		LiferayPortletURL portletURL = liferayPortletResponse.createRenderURL(
			KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW);

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter("navigation", getDefinitionsNavigation());

		String delta = ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String keywords = getKeywords();

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
		PortletURL portletURL = getPortletURL();

		return portletURL.toString();
	}

	public String getSearchContainerId() {
		return "kaleoDefinitionVersions";
	}

	public String getSortingURL() throws Exception {
		PortletURL sortingURL = PortletURLUtil.clone(
			getPortletURL(),
			_kaleoDesignerRequestHelper.getLiferayPortletResponse());

		String orderByType = ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "orderByType");

		sortingURL.setParameter(
			"orderByType", orderByType.equals("asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public String getTitle(KaleoDefinitionVersion kaleoDefinitionVersion) {
		if (kaleoDefinitionVersion == null) {
			return HtmlUtil.escape(getLanguage("new-workflow"));
		}

		if (Validator.isNull(kaleoDefinitionVersion.getTitle())) {
			return HtmlUtil.escape(getLanguage("untitled-workflow"));
		}

		ThemeDisplay themeDisplay =
			_kaleoDesignerRequestHelper.getThemeDisplay();

		return HtmlUtil.escape(
			kaleoDefinitionVersion.getTitle(themeDisplay.getLanguageId()));
	}

	public int getTotalItems() {
		SearchContainer<?> searchContainer = getKaleoDefinitionVersionSearch();

		return searchContainer.getTotal();
	}

	public String getUserName(KaleoDefinitionVersion kaleoDefinitionVersion) {
		User user = _userLocalService.fetchUser(
			kaleoDefinitionVersion.getUserId());

		if ((user == null) || user.isDefaultUser() ||
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

		if ((kaleoDefinitionVersion != null) &&
			KaleoDefinitionVersionPermission.contains(
				permissionChecker, kaleoDefinitionVersion, ActionKeys.UPDATE)) {

			return false;
		}

		return true;
	}

	public boolean isDisabledManagementBar() {
		if (hasResults()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isPublishKaleoDefinitionVersionButtonVisible(
		PermissionChecker permissionChecker,
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		if (kaleoDefinitionVersion != null) {
			return KaleoDefinitionVersionPermission.contains(
				permissionChecker, kaleoDefinitionVersion, ActionKeys.UPDATE);
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
				return KaleoDefinitionVersionPermission.contains(
					permissionChecker, kaleoDefinitionVersion,
					ActionKeys.UPDATE);
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

	protected String getConfigureAssignementLink(HttpServletRequest request)
		throws PortletException {

		PortletURL portletURL = getWorkflowDefinitionLinkPortletURL(request);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				_kaleoDesignerRequestHelper.getLocale());

		return StringUtil.replace(
			_HTML, new String[] {"[$RENDER_URL$]", "[$MESSAGE$]"},
			new String[] {
				portletURL.toString(),
				LanguageUtil.get(resourceBundle, "configure-assignments")
			});
	}

	protected String getDefinitionsNavigation() {
		return ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "navigation", "all");
	}

	protected Consumer<DropdownItem> getFilterNavigationDropdownItem(
		String definitionsNavigation) {

		return dropdownItem -> {
			dropdownItem.setActive(
				definitionsNavigation.equals(getDefinitionsNavigation()));
			dropdownItem.setHref(
				getPortletURL(), "navigation", definitionsNavigation);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(),
					definitionsNavigation));
		};
	}

	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(getFilterNavigationDropdownItem("all"));
				add(getFilterNavigationDropdownItem("not-published"));
				add(getFilterNavigationDropdownItem("published"));
			}
		};
	}

	protected String getKeywords() {
		return ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "keywords");
	}

	protected String getLanguage(String key) {
		return LanguageUtil.get(getResourceBundle(), key);
	}

	protected String getLocalizedAssetName(String className) {
		return ResourceActionsUtil.getModelResource(
			_kaleoDesignerRequestHelper.getLocale(), className);
	}

	protected Consumer<DropdownItem> getOrderByDropdownItem(String orderByCol) {
		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_kaleoDesignerRequestHelper.getRequest(), orderByCol));
		};
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(getOrderByDropdownItem("last-modified"));
				add(getOrderByDropdownItem("title"));
			}
		};
	}

	protected ResourceBundle getResourceBundle() {
		return _resourceBundleLoader.loadResourceBundle(
			_kaleoDesignerRequestHelper.getLocale());
	}

	protected PortletURL getWorkflowDefinitionLinkPortletURL(
		HttpServletRequest request) {

		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"tab", WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK);

		return portletURL;
	}

	protected boolean hasResults() {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected void setKaleoDefinitionVersionSearchResults(
		SearchContainer<KaleoDefinitionVersion> searchContainer) {

		String definitionsNavigation = getDefinitionsNavigation();

		int displayedStatus = KaleoDefinitionVersionConstants.STATUS_ALL;

		if (StringUtil.equals(definitionsNavigation, "published")) {
			displayedStatus = KaleoDefinitionVersionConstants.STATUS_PUBLISHED;
		}
		else if (StringUtil.equals(definitionsNavigation, "not-published")) {
			displayedStatus =
				KaleoDefinitionVersionConstants.STATUS_NOT_PUBLISHED;
		}

		List<KaleoDefinitionVersion> kaleoDefinitionVersions =
			_kaleoDefinitionVersionLocalService.
				getLatestKaleoDefinitionVersions(
					_kaleoDesignerRequestHelper.getCompanyId(), getKeywords(),
					WorkflowConstants.STATUS_ANY, searchContainer.getStart(),
					searchContainer.getEnd(),
					searchContainer.getOrderByComparator());

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			new KaleoDefinitionVersionActivePredicateFilter(displayedStatus));

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			new KaleoDefinitionVersionViewPermissionPredicateFilter(
				_kaleoDesignerRequestHelper.getPermissionChecker(),
				_themeDisplay.getCompanyGroupId()));

		searchContainer.setTotal(kaleoDefinitionVersions.size());

		if (kaleoDefinitionVersions.size() >
				(searchContainer.getEnd() - searchContainer.getStart())) {

			kaleoDefinitionVersions = ListUtil.subList(
				kaleoDefinitionVersions, searchContainer.getStart(),
				searchContainer.getEnd());
		}

		searchContainer.setResults(kaleoDefinitionVersions);
	}

	private static final String _HTML =
		"<a class='alert-link' href='[$RENDER_URL$]'>[$MESSAGE$]</a>";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDesignerDisplayContext.class);

	private final KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private KaleoDesignerRequestHelper _kaleoDesignerRequestHelper;
	private final RenderRequest _renderRequest;
	private final ResourceBundleLoader _resourceBundleLoader;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;

}
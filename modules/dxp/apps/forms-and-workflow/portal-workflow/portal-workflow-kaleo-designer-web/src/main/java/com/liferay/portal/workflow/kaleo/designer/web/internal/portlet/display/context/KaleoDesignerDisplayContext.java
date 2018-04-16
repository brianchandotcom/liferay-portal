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

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
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
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerActionKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDefinitionVersionPermission;
import com.liferay.portal.workflow.kaleo.designer.web.internal.permission.KaleoDesignerPermission;
import com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context.util.KaleoDesignerRequestHelper;
import com.liferay.portal.workflow.kaleo.designer.web.internal.search.KaleoDefinitionVersionSearchTerms;
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
			KaleoDefinitionVersionLocalService
				kaleoDefinitionVersionLocalService,
			ResourceBundleLoader resourceBundleLoader,
			UserLocalService userLocalService)
		throws PortalException {

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
		_resourceBundleLoader = resourceBundleLoader;
		_userLocalService = userLocalService;

		_kaleoDesignerRequestHelper = new KaleoDesignerRequestHelper(
			renderRequest);
	}

	public Date getCreatedDate(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		KaleoDefinitionVersion firstKaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.getFirstKaleoDefinitionVersion(
				kaleoDefinitionVersion.getCompanyId(),
				kaleoDefinitionVersion.getName());

		return firstKaleoDefinitionVersion.getCreateDate();
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
			kaleoDefinition.getTitle(defaultLanguageId));
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
			SearchContainer searchContainer) {

		boolean orderByAsc = false;
		String orderByCol = getOrderByCol(searchContainer);
		String orderByType = getOrderByType(searchContainer);

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
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		return _kaleoDefinitionVersionLocalService.getKaleoDefinitionVersions(
			kaleoDefinitionVersion.getCompanyId(),
			kaleoDefinitionVersion.getName(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS,
			new KaleoDefinitionVersionModifiedDateComparator(false));
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
		else {
			int moreAssetsCount = workflowDefinitionLinks.size() - 2;

			return new Object[] {
				getLocalizedAssetName(
					firstWorkflowDefinitionLink.getClassName()),
				getLocalizedAssetName(
					secondWorkflowDefinitionLink.getClassName()),
				moreAssetsCount, getConfigureAssignementLink(request)
			};
		}
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
		else {
			return "workflow-in-use-remove-assignements-to-x-x-and-x-more-x";
		}
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

	public String getOrderByCol(SearchContainer searchContainer) {
		String orderByCol = searchContainer.getOrderByCol();

		if (orderByCol != null) {
			return orderByCol;
		}

		return ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "orderByCol", "title");
	}

	public String getOrderByType(SearchContainer searchContainer) {
		String orderByType = searchContainer.getOrderByType();

		if (orderByType != null) {
			return orderByType;
		}

		return ParamUtil.getString(
			_kaleoDesignerRequestHelper.getRequest(), "orderByType", "asc");
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

	public List<KaleoDefinitionVersion> getSearchContainerResults(
			SearchContainer<KaleoDefinitionVersion> searchContainer, int status,
			PermissionChecker permissionChecker)
		throws PortalException {

		KaleoDefinitionVersionSearchTerms searchTerms =
			(KaleoDefinitionVersionSearchTerms)searchContainer.getSearchTerms();

		List<KaleoDefinitionVersion> kaleoDefinitionVersions =
			_kaleoDefinitionVersionLocalService.
				getLatestKaleoDefinitionVersions(
					_kaleoDesignerRequestHelper.getCompanyId(),
					searchTerms.getKeywords(), WorkflowConstants.STATUS_ANY,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			new KaleoDefinitionVersionActivePredicateFilter(status));

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			new KaleoDefinitionVersionViewPermissionPredicateFilter(
				permissionChecker, _themeDisplay.getCompanyGroupId()));

		searchContainer.setTotal(kaleoDefinitionVersions.size());

		if (kaleoDefinitionVersions.size() >
				(searchContainer.getEnd() - searchContainer.getStart())) {

			kaleoDefinitionVersions = ListUtil.subList(
				kaleoDefinitionVersions, searchContainer.getStart(),
				searchContainer.getEnd());
		}

		kaleoDefinitionVersions = ListUtil.sort(
			kaleoDefinitionVersions,
			getKaleoDefinitionVersionOrderByComparator(searchContainer));

		return kaleoDefinitionVersions;
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

	public boolean isPublishKaleoDefinitionVersionButtonVisible(
		PermissionChecker permissionChecker,
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		if (kaleoDefinitionVersion != null) {
			return KaleoDefinitionVersionPermission.contains(
				permissionChecker, kaleoDefinitionVersion, ActionKeys.UPDATE);
		}
		else {
			return KaleoDesignerPermission.contains(
				permissionChecker, _themeDisplay.getCompanyGroupId(),
				KaleoDesignerActionKeys.ADD_NEW_WORKFLOW);
		}
	}

	public boolean isSaveKaleoDefinitionVersionButtonVisible(
		PermissionChecker permissionChecker,
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		if (kaleoDefinitionVersion != null) {
			KaleoDefinition kaleoDefinition = getKaleoDefinition(
				kaleoDefinitionVersion);

			if ((kaleoDefinition != null) && !kaleoDefinition.isActive()) {
				return KaleoDefinitionVersionPermission.contains(
					permissionChecker, kaleoDefinitionVersion,
					ActionKeys.UPDATE);
			}
			else {
				return false;
			}
		}
		else {
			return KaleoDesignerPermission.contains(
				permissionChecker, _themeDisplay.getCompanyGroupId(),
				KaleoDesignerActionKeys.ADD_NEW_WORKFLOW);
		}
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

	protected String getLanguage(String key) {
		return LanguageUtil.get(getResourceBundle(), key);
	}

	protected String getLocalizedAssetName(String className) {
		return ResourceActionsUtil.getModelResource(
			_kaleoDesignerRequestHelper.getLocale(), className);
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

	private static final String _HTML =
		"<a class='alert-link' href='[$RENDER_URL$]'>[$MESSAGE$]</a>";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDesignerDisplayContext.class);

	private final KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private final KaleoDesignerRequestHelper _kaleoDesignerRequestHelper;
	private final ResourceBundleLoader _resourceBundleLoader;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;

}
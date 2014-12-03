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

package com.liferay.portlet.layoutsadmin.action;

import com.liferay.portal.ImageTypeException;
import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutFriendlyURLsException;
import com.liferay.portal.LayoutNameException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.SitemapChangeFrequencyException;
import com.liferay.portal.SitemapIncludeException;
import com.liferay.portal.SitemapPagePriorityException;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.ThemeSetting;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ThemeSettingImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.service.permission.LayoutSetPrototypePermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRActionServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil;
import com.liferay.portlet.sites.action.ActionUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class EditLayoutsAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			checkPermissions(actionRequest);
		}
		catch (PrincipalException pe) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");
		String closeRedirect = ParamUtil.getString(
			actionRequest, "closeRedirect");

		try {
			Layout layout = null;
			String oldFriendlyURL = StringPool.BLANK;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Object[] returnValue = updateLayout(
					actionRequest, actionResponse);

				layout = (Layout)returnValue[0];
				oldFriendlyURL = (String)returnValue[1];

				if (cmd.equals(Constants.ADD)) {
					SessionMessages.add(
						actionRequest,
						PortalUtil.getPortletId(actionRequest) + "pageAdded",
						layout);
				}
				else if (cmd.equals(Constants.UPDATE)) {
					redirect = updateRedirect(
						themeDisplay, redirect, null, layout, oldFriendlyURL);
					closeRedirect = updateCloseRedirect(
						themeDisplay, closeRedirect, null, layout,
						oldFriendlyURL);
				}
			}
			else if (cmd.equals(Constants.DELETE)) {
				long plid = ParamUtil.getLong(actionRequest, "plid");

				if (plid <= 0) {
					long groupId = ParamUtil.getLong(actionRequest, "groupId");
					boolean privateLayout = ParamUtil.getBoolean(
						actionRequest, "privateLayout");
					long layoutId = ParamUtil.getLong(
						actionRequest, "layoutId");

					layout = LayoutLocalServiceUtil.getLayout(
						groupId, privateLayout, layoutId);

					plid = layout.getPlid();
				}

				Object[] returnValue = SitesUtil.deleteLayout(
					actionRequest, actionResponse);

				Group group = (Group)returnValue[0];
				oldFriendlyURL = (String)returnValue[1];
				long newRefererPlid = (Long)returnValue[2];

				redirect = updateCloseRedirect(
					themeDisplay, redirect, group, null, oldFriendlyURL);

				long refererPlid = themeDisplay.getRefererPlid();

				if (plid == refererPlid) {
					redirect = HttpUtil.setParameter(
						redirect, "refererPlid", newRefererPlid);
					redirect = HttpUtil.setParameter(
						redirect, actionResponse.getNamespace() + "selPlid", 0);
				}

				closeRedirect = updateCloseRedirect(
					themeDisplay, closeRedirect, group, null, oldFriendlyURL);

				redirect = HttpUtil.addParameter(
					redirect, actionResponse.getNamespace() + "closeRedirect",
					closeRedirect);
			}
			else if (cmd.equals("display_order")) {
				updateDisplayOrder(actionRequest);
			}
			else if (cmd.equals("delete_layout_revision")) {
				deleteLayoutRevision(actionRequest);
			}
			else if (cmd.equals("enable")) {
				enableLayout(actionRequest);
			}
			else if (cmd.equals("reset_customized_view")) {
				LayoutTypePortlet layoutTypePortlet =
					themeDisplay.getLayoutTypePortlet();

				if ((layoutTypePortlet != null) &&
					layoutTypePortlet.isCustomizable() &&
					layoutTypePortlet.isCustomizedView()) {

					layoutTypePortlet.resetUserPreferences();
				}
			}
			else if (cmd.equals("reset_merge_fail_count_and_merge")) {
				resetMergeFailCountAndMerge(actionRequest);
			}
			else if (cmd.equals("reset_prototype")) {
				SitesUtil.resetPrototype(themeDisplay.getLayout());
			}
			else if (cmd.equals("select_layout_set_branch")) {
				selectLayoutSetBranch(actionRequest);
			}
			else if (cmd.equals("select_layout_branch")) {
				selectLayoutBranch(actionRequest);
			}
			else if (cmd.equals("update_layout_revision")) {
				updateLayoutRevision(actionRequest, themeDisplay);
			}

			MultiSessionMessages.add(
				actionRequest,
				PortalUtil.getPortletId(actionRequest) + "requestProcessed");

			sendRedirect(
				portletConfig, actionRequest, actionResponse, redirect,
				closeRedirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.layouts_admin.error");
			}
			else if (e instanceof ImageTypeException ||
					 e instanceof LayoutFriendlyURLException ||
					 e instanceof LayoutFriendlyURLsException ||
					 e instanceof LayoutNameException ||
					 e instanceof LayoutParentLayoutIdException ||
					 e instanceof LayoutSetVirtualHostException ||
					 e instanceof LayoutTypeException ||
					 e instanceof RequiredLayoutException ||
					 e instanceof SitemapChangeFrequencyException ||
					 e instanceof SitemapIncludeException ||
					 e instanceof SitemapPagePriorityException ||
					 e instanceof UploadException) {

				SessionErrors.add(actionRequest, e.getClass(), e);

				if (cmd.equals(Constants.ADD)) {
					SessionMessages.add(
						actionRequest,
						PortalUtil.getPortletId(actionRequest) + "addError", e);
				}
			}
			else if (e instanceof SystemException) {
				SessionErrors.add(actionRequest, e.getClass(), e);

				redirect = ParamUtil.getString(actionRequest, "pagesRedirect");

				sendRedirect(
					portletConfig, actionRequest, actionResponse, redirect,
					closeRedirect);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			checkPermissions(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return actionMapping.findForward("portlet.layouts_admin.error");
		}

		try {
			getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.edit_layouts"));
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, Group group, Layout layout,
			long selPlid)
		throws PortalException {

		if (selPlid > 0) {
			LayoutPermissionUtil.check(
				permissionChecker, layout, ActionKeys.VIEW);
		}
		else {
			GroupPermissionUtil.check(
				permissionChecker, group, ActionKeys.VIEW);
		}
	}

	protected void checkPermissions(PortletRequest portletRequest)
		throws Exception {

		Group group = getGroup(portletRequest);

		if (group == null) {
			throw new PrincipalException();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		Layout layout = themeDisplay.getLayout();

		String cmd = ParamUtil.getString(portletRequest, Constants.CMD);

		long selPlid = ParamUtil.getLong(portletRequest, "selPlid");

		if (selPlid > 0) {
			layout = LayoutLocalServiceUtil.getLayout(selPlid);
		}

		if (cmd.equals(Constants.ADD)) {
			long parentPlid = ParamUtil.getLong(portletRequest, "parentPlid");

			if (parentPlid == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
				if (!GroupPermissionUtil.contains(
						permissionChecker, group, ActionKeys.ADD_LAYOUT)) {

					throw new PrincipalException();
				}
			}
			else {
				layout = LayoutLocalServiceUtil.getLayout(parentPlid);

				if (!LayoutPermissionUtil.contains(
						permissionChecker, layout, ActionKeys.ADD_LAYOUT)) {

					throw new PrincipalException();
				}
			}
		}
		else if (cmd.equals(Constants.DELETE)) {
			if (!LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.DELETE)) {

				throw new PrincipalException();
			}
		}
		else if (cmd.equals(Constants.PUBLISH_TO_LIVE) ||
				 cmd.equals(Constants.PUBLISH_TO_REMOTE)) {

			boolean hasUpdateLayoutPermission = false;

			if (layout != null) {
				hasUpdateLayoutPermission = LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.UPDATE);
			}

			if (group.isCompany() || group.isSite()) {
				boolean publishToLive = GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.PUBLISH_STAGING);

				if (!hasUpdateLayoutPermission && !publishToLive) {
					throw new PrincipalException();
				}
			}
			else {
				checkPermission(permissionChecker, group, layout, selPlid);
			}
		}
		else if (cmd.equals(Constants.UPDATE)) {
			if (group.isCompany()) {
				if (!permissionChecker.isCompanyAdmin()) {
					throw new PrincipalException();
				}
			}
			else if (group.isLayoutPrototype()) {
				LayoutPrototypePermissionUtil.check(
					permissionChecker, group.getClassPK(), ActionKeys.UPDATE);
			}
			else if (group.isLayoutSetPrototype()) {
				LayoutSetPrototypePermissionUtil.check(
					permissionChecker, group.getClassPK(), ActionKeys.UPDATE);
			}
			else if (group.isUser()) {
				long groupUserId = group.getClassPK();

				User groupUser = UserLocalServiceUtil.getUserById(groupUserId);

				long[] organizationIds = groupUser.getOrganizationIds();

				UserPermissionUtil.check(
					permissionChecker, groupUserId, organizationIds,
					ActionKeys.UPDATE);
			}
			else {
				checkPermission(permissionChecker, group, layout, selPlid);
			}
		}
		else if (cmd.equals("reset_customized_view")) {
			if (!LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.CUSTOMIZE)) {

				throw new PrincipalException();
			}
		}
		else {
			checkPermission(permissionChecker, group, layout, selPlid);
		}
	}

	protected void deleteLayoutRevision(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		long layoutRevisionId = ParamUtil.getLong(
			actionRequest, "layoutRevisionId");

		LayoutRevision layoutRevision =
			LayoutRevisionLocalServiceUtil.getLayoutRevision(layoutRevisionId);

		LayoutRevisionLocalServiceUtil.deleteLayoutRevision(layoutRevision);

		boolean updateRecentLayoutRevisionId = ParamUtil.getBoolean(
			actionRequest, "updateRecentLayoutRevisionId");

		if (updateRecentLayoutRevisionId) {
			StagingUtil.setRecentLayoutRevisionId(
				request, layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid(),
				layoutRevision.getParentLayoutRevisionId());
		}
	}

	protected void deleteThemeSettingsProperties(
		UnicodeProperties typeSettingsProperties, String device) {

		String keyPrefix = ThemeSettingImpl.namespaceProperty(device);

		Set<String> keys = typeSettingsProperties.keySet();

		Iterator<String> itr = keys.iterator();

		while (itr.hasNext()) {
			String key = itr.next();

			if (key.startsWith(keyPrefix)) {
				itr.remove();
			}
		}
	}

	protected void enableLayout(ActionRequest actionRequest) throws Exception {
		long incompleteLayoutRevisionId = ParamUtil.getLong(
			actionRequest, "incompleteLayoutRevisionId");

		LayoutRevision incompleteLayoutRevision =
			LayoutRevisionLocalServiceUtil.getLayoutRevision(
				incompleteLayoutRevisionId);

		long layoutBranchId = ParamUtil.getLong(
			actionRequest, "layoutBranchId",
			incompleteLayoutRevision.getLayoutBranchId());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		LayoutRevisionLocalServiceUtil.updateLayoutRevision(
			serviceContext.getUserId(),
			incompleteLayoutRevision.getLayoutRevisionId(), layoutBranchId,
			incompleteLayoutRevision.getName(),
			incompleteLayoutRevision.getTitle(),
			incompleteLayoutRevision.getDescription(),
			incompleteLayoutRevision.getKeywords(),
			incompleteLayoutRevision.getRobots(),
			incompleteLayoutRevision.getTypeSettings(),
			incompleteLayoutRevision.getIconImage(),
			incompleteLayoutRevision.getIconImageId(),
			incompleteLayoutRevision.getThemeId(),
			incompleteLayoutRevision.getColorSchemeId(),
			incompleteLayoutRevision.getWapThemeId(),
			incompleteLayoutRevision.getWapColorSchemeId(),
			incompleteLayoutRevision.getCss(), serviceContext);
	}

	protected String getColorSchemeId(
			long companyId, String themeId, String colorSchemeId,
			boolean wapTheme)
		throws Exception {

		Theme theme = ThemeLocalServiceUtil.getTheme(
			companyId, themeId, wapTheme);

		if (!theme.hasColorSchemes()) {
			colorSchemeId = StringPool.BLANK;
		}

		if (Validator.isNull(colorSchemeId)) {
			ColorScheme colorScheme = ThemeLocalServiceUtil.getColorScheme(
				companyId, themeId, colorSchemeId, wapTheme);

			colorSchemeId = colorScheme.getColorSchemeId();
		}

		return colorSchemeId;
	}

	protected Group getGroup(PortletRequest portletRequest) throws Exception {
		return ActionUtil.getGroup(portletRequest);
	}

	protected byte[] getIconBytes(
		UploadPortletRequest uploadPortletRequest, String iconFileName) {

		InputStream inputStream = null;

		try {
			inputStream = uploadPortletRequest.getFileAsStream(iconFileName);

			if (inputStream != null) {
				return FileUtil.getBytes(inputStream);
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to retrieve icon", ioe);
			}
		}

		return new byte[0];
	}

	protected void inheritMobileRuleGroups(
			Layout layout, ServiceContext serviceContext)
		throws PortalException {

		List<MDRRuleGroupInstance> parentMDRRuleGroupInstances =
			MDRRuleGroupInstanceLocalServiceUtil.getRuleGroupInstances(
				Layout.class.getName(), layout.getParentPlid());

		for (MDRRuleGroupInstance parentMDRRuleGroupInstance :
				parentMDRRuleGroupInstances) {

			MDRRuleGroupInstance mdrRuleGroupInstance =
				MDRRuleGroupInstanceServiceUtil.addRuleGroupInstance(
					layout.getGroupId(), Layout.class.getName(),
					layout.getPlid(),
					parentMDRRuleGroupInstance.getRuleGroupId(),
					parentMDRRuleGroupInstance.getPriority(), serviceContext);

			List<MDRAction> parentMDRActions =
				MDRActionLocalServiceUtil.getActions(
					parentMDRRuleGroupInstance.getRuleGroupInstanceId());

			for (MDRAction mdrAction : parentMDRActions) {
				MDRActionServiceUtil.addAction(
					mdrRuleGroupInstance.getRuleGroupInstanceId(),
					mdrAction.getNameMap(), mdrAction.getDescriptionMap(),
					mdrAction.getType(), mdrAction.getTypeSettings(),
					serviceContext);
			}
		}
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	/**
	 * Resets the number of failed merge attempts for the page template, which
	 * is accessed from the action request's <code>layoutPrototypeId</code>
	 * param. Once the counter is reset, the modified page template is merged
	 * back into its linked page, which is accessed from the action request's
	 * <code>selPlid</code> param.
	 *
	 * <p>
	 * If the number of failed merge attempts is not equal to zero after the
	 * merge, an error key is submitted into the {@link SessionErrors}.
	 * </p>
	 *
	 * @param  actionRequest the action request
	 * @throws Exception if an exception occurred
	 */
	protected void resetMergeFailCountAndMerge(ActionRequest actionRequest)
		throws Exception {

		long layoutPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutPrototypeId");

		LayoutPrototype layoutPrototype =
			LayoutPrototypeLocalServiceUtil.getLayoutPrototype(
				layoutPrototypeId);

		SitesUtil.setMergeFailCount(layoutPrototype, 0);

		long selPlid = ParamUtil.getLong(actionRequest, "selPlid");

		Layout selLayout = LayoutLocalServiceUtil.getLayout(selPlid);

		SitesUtil.resetPrototype(selLayout);

		SitesUtil.mergeLayoutPrototypeLayout(selLayout.getGroup(), selLayout);

		layoutPrototype = LayoutPrototypeServiceUtil.getLayoutPrototype(
			layoutPrototypeId);

		int mergeFailCountAfterMerge = SitesUtil.getMergeFailCount(
			layoutPrototype);

		if (mergeFailCountAfterMerge > 0) {
			SessionErrors.add(actionRequest, "resetMergeFailCountAndMerge");
		}
	}

	protected void selectLayoutBranch(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		long layoutBranchId = ParamUtil.getLong(
			actionRequest, "layoutBranchId");

		StagingUtil.setRecentLayoutBranchId(
			request, layoutSetBranchId, themeDisplay.getPlid(), layoutBranchId);
	}

	protected void selectLayoutSetBranch(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		long layoutSetBranchId = ParamUtil.getLong(
			actionRequest, "layoutSetBranchId");

		// Ensure layout set branch exists

		LayoutSetBranch layoutSetBranch =
			LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(
				layoutSetBranchId);

		StagingUtil.setRecentLayoutSetBranchId(
			request, layoutSet.getLayoutSetId(),
			layoutSetBranch.getLayoutSetBranchId());
	}

	protected void setThemeSettingProperties(
			ActionRequest actionRequest,
			UnicodeProperties typeSettingsProperties,
			Map<String, ThemeSetting> themeSettings, String device,
			String deviceThemeId)
		throws PortalException {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

		Layout layout = LayoutLocalServiceUtil.getLayout(
			groupId, privateLayout, layoutId);

		for (String key : themeSettings.keySet()) {
			ThemeSetting themeSetting = themeSettings.get(key);

			String property =
				device + "ThemeSettingsProperties--" + key +
					StringPool.DOUBLE_DASH;

			String value = ParamUtil.getString(
				actionRequest, property, themeSetting.getValue());

			if (!Validator.equals(
					value, layout.getDefaultThemeSetting(key, device, false))) {

				typeSettingsProperties.setProperty(
					ThemeSettingImpl.namespaceProperty(device, key), value);
			}
		}
	}

	protected String updateCloseRedirect(
		ThemeDisplay themeDisplay, String closeRedirect, Group group,
		Layout layout, String oldLayoutFriendlyURL) {

		if (Validator.isNull(closeRedirect) ||
			Validator.isNull(oldLayoutFriendlyURL)) {

			return closeRedirect;
		}

		if (layout != null) {
			String oldPath = oldLayoutFriendlyURL;
			String newPath = layout.getFriendlyURL(themeDisplay.getLocale());

			return PortalUtil.updateRedirect(closeRedirect, oldPath, newPath);
		}
		else if (group != null) {
			String oldPath = group.getFriendlyURL() + oldLayoutFriendlyURL;
			String newPath = group.getFriendlyURL();

			return PortalUtil.updateRedirect(closeRedirect, oldPath, newPath);
		}

		return closeRedirect;
	}

	protected void updateDisplayOrder(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			actionRequest, "parentLayoutId");
		long[] layoutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "layoutIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		LayoutServiceUtil.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds, serviceContext);
	}

	protected Object[] updateLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(uploadPortletRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");
		long parentLayoutId = ParamUtil.getLong(
			uploadPortletRequest, "parentLayoutId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		Map<Locale, String> keywordsMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "keywords");
		Map<Locale, String> robotsMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "robots");
		String type = ParamUtil.getString(uploadPortletRequest, "type");
		boolean hidden = ParamUtil.getBoolean(uploadPortletRequest, "hidden");
		Map<Locale, String> friendlyURLMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "friendlyURL");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] iconBytes = null;

		long fileEntryId = ParamUtil.getLong(
			uploadPortletRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				fileEntryId);

			iconBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		long layoutPrototypeId = ParamUtil.getLong(
			uploadPortletRequest, "layoutPrototypeId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		Layout layout = null;
		UnicodeProperties layoutTypeSettingsProperties = null;
		String oldFriendlyURL = StringPool.BLANK;

		if (cmd.equals(Constants.ADD)) {

			// Add layout

			boolean inheritFromParentLayoutId = ParamUtil.getBoolean(
				uploadPortletRequest, "inheritFromParentLayoutId");

			UnicodeProperties typeSettingsProperties =
				PropertiesParamUtil.getProperties(
					actionRequest, "TypeSettingsProperties--");

			if (inheritFromParentLayoutId && (parentLayoutId > 0)) {
				Layout parentLayout = LayoutLocalServiceUtil.getLayout(
					groupId, privateLayout, parentLayoutId);

				layout = LayoutServiceUtil.addLayout(
					groupId, privateLayout, parentLayoutId, nameMap, titleMap,
					parentLayout.getDescriptionMap(),
					parentLayout.getKeywordsMap(), parentLayout.getRobotsMap(),
					parentLayout.getType(), parentLayout.getTypeSettings(),
					hidden, friendlyURLMap, serviceContext);

				inheritMobileRuleGroups(layout, serviceContext);

				if (parentLayout.isTypePortlet()) {
					ActionUtil.copyPreferences(
						actionRequest, layout, parentLayout);

					SitesUtil.copyLookAndFeel(layout, parentLayout);
				}
			}
			else if (layoutPrototypeId > 0) {
				LayoutPrototype layoutPrototype =
					LayoutPrototypeServiceUtil.getLayoutPrototype(
						layoutPrototypeId);

				boolean layoutPrototypeLinkEnabled = ParamUtil.getBoolean(
					uploadPortletRequest, "layoutPrototypeLinkEnabled");

				serviceContext.setAttribute(
					"layoutPrototypeLinkEnabled", layoutPrototypeLinkEnabled);

				serviceContext.setAttribute(
					"layoutPrototypeUuid", layoutPrototype.getUuid());

				layout = LayoutServiceUtil.addLayout(
					groupId, privateLayout, parentLayoutId, nameMap, titleMap,
					descriptionMap, keywordsMap, robotsMap,
					LayoutConstants.TYPE_PORTLET,
					typeSettingsProperties.toString(), hidden, friendlyURLMap,
					serviceContext);

				// Force propagation from page template to page. See LPS-48430.

				SitesUtil.mergeLayoutPrototypeLayout(layout.getGroup(), layout);
			}
			else {
				long copyLayoutId = ParamUtil.getLong(
					uploadPortletRequest, "copyLayoutId");

				Layout copyLayout = null;

				String layoutTemplateId = ParamUtil.getString(
					uploadPortletRequest, "layoutTemplateId",
					PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);

				if (copyLayoutId > 0) {
					try {
						copyLayout = LayoutLocalServiceUtil.getLayout(
							groupId, privateLayout, copyLayoutId);

						if (copyLayout.isTypePortlet()) {
							LayoutTypePortlet copyLayoutTypePortlet =
								(LayoutTypePortlet)copyLayout.getLayoutType();

							layoutTemplateId =
								copyLayoutTypePortlet.getLayoutTemplateId();

							typeSettingsProperties =
								copyLayout.getTypeSettingsProperties();
						}
					}
					catch (NoSuchLayoutException nsle) {
					}
				}

				layout = LayoutServiceUtil.addLayout(
					groupId, privateLayout, parentLayoutId, nameMap, titleMap,
					descriptionMap, keywordsMap, robotsMap, type,
					typeSettingsProperties.toString(), hidden, friendlyURLMap,
					serviceContext);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				layoutTypePortlet.setLayoutTemplateId(
					themeDisplay.getUserId(), layoutTemplateId);

				LayoutServiceUtil.updateLayout(
					groupId, privateLayout, layout.getLayoutId(),
					layout.getTypeSettings());

				if (copyLayout != null) {
					if (copyLayout.isTypePortlet()) {
						ActionUtil.copyPreferences(
							actionRequest, layout, copyLayout);

						SitesUtil.copyLookAndFeel(layout, copyLayout);
					}
				}
			}

			layoutTypeSettingsProperties = layout.getTypeSettingsProperties();
		}
		else {

			// Update layout

			layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);

			oldFriendlyURL = layout.getFriendlyURL(themeDisplay.getLocale());

			layout = LayoutServiceUtil.updateLayout(
				groupId, privateLayout, layoutId, layout.getParentLayoutId(),
				nameMap, titleMap, descriptionMap, keywordsMap, robotsMap, type,
				hidden, friendlyURLMap, !deleteLogo, iconBytes, serviceContext);

			layoutTypeSettingsProperties = layout.getTypeSettingsProperties();

			if (!layout.isTypeURL() && !layout.isTypeLinkToLayout() &&
				oldFriendlyURL.equals(
					layout.getFriendlyURL(themeDisplay.getLocale()))) {

				oldFriendlyURL = StringPool.BLANK;
			}

			UnicodeProperties formTypeSettingsProperties =
				PropertiesParamUtil.getProperties(
					actionRequest, "TypeSettingsProperties--");

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (type.equals(LayoutConstants.TYPE_PORTLET)) {
				String layoutTemplateId = ParamUtil.getString(
					uploadPortletRequest, "layoutTemplateId",
					PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);

				layoutTypePortlet.setLayoutTemplateId(
					themeDisplay.getUserId(), layoutTemplateId);

				long copyLayoutId = ParamUtil.getLong(
					uploadPortletRequest, "copyLayoutId");

				if ((copyLayoutId > 0) &&
					(copyLayoutId != layout.getLayoutId())) {

					try {
						Layout copyLayout = LayoutLocalServiceUtil.getLayout(
							groupId, privateLayout, copyLayoutId);

						if (copyLayout.isTypePortlet()) {
							layoutTypeSettingsProperties =
								copyLayout.getTypeSettingsProperties();

							ActionUtil.copyPreferences(
								actionRequest, layout, copyLayout);

							SitesUtil.copyLookAndFeel(layout, copyLayout);
						}
					}
					catch (NoSuchLayoutException nsle) {
					}
				}
				else {
					layoutTypeSettingsProperties.putAll(
						formTypeSettingsProperties);

					LayoutServiceUtil.updateLayout(
						groupId, privateLayout, layoutId,
						layout.getTypeSettings());
				}
			}
			else {
				layout.setTypeSettingsProperties(formTypeSettingsProperties);

				layoutTypeSettingsProperties.putAll(
					layout.getTypeSettingsProperties());

				LayoutServiceUtil.updateLayout(
					groupId, privateLayout, layoutId, layout.getTypeSettings());
			}

			String[] removeEmbeddedPortletIds = ParamUtil.getParameterValues(
				actionRequest, "removeEmbeddedPortletIds");

			if (removeEmbeddedPortletIds.length > 0) {
				PortletLocalServiceUtil.deletePortlets(
					themeDisplay.getCompanyId(), removeEmbeddedPortletIds,
					layout.getPlid());
			}

			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			EventsProcessorUtil.process(
				PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
				layoutTypePortlet.getConfigurationActionUpdate(),
				uploadPortletRequest, response);
		}

		updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, privateLayout, layout.getLayoutId(),
			layoutTypeSettingsProperties);

		return new Object[] {layout, oldFriendlyURL};
	}

	protected void updateLayoutRevision(
			ActionRequest actionRequest, ThemeDisplay themeDisplay)
		throws Exception {

		long layoutRevisionId = ParamUtil.getLong(
			actionRequest, "layoutRevisionId");

		LayoutRevision layoutRevision =
			LayoutRevisionLocalServiceUtil.getLayoutRevision(layoutRevisionId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		LayoutRevision enableLayoutRevision =
			LayoutRevisionLocalServiceUtil.updateLayoutRevision(
				serviceContext.getUserId(), layoutRevisionId,
				layoutRevision.getLayoutBranchId(), layoutRevision.getName(),
				layoutRevision.getTitle(), layoutRevision.getDescription(),
				layoutRevision.getKeywords(), layoutRevision.getRobots(),
				layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
				layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
				layoutRevision.getColorSchemeId(),
				layoutRevision.getWapThemeId(),
				layoutRevision.getWapColorSchemeId(), layoutRevision.getCss(),
				serviceContext);

		if (layoutRevision.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) {
			StagingUtil.setRecentLayoutRevisionId(
				themeDisplay.getUser(), layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getPlid(), layoutRevision.getLayoutRevisionId());

			return;
		}

		LayoutRevision lastLayoutRevision =
			LayoutRevisionLocalServiceUtil.fetchLastLayoutRevision(
				enableLayoutRevision.getPlid(), true);

		if (lastLayoutRevision != null) {
			LayoutRevision newLayoutRevision =
				LayoutRevisionLocalServiceUtil.addLayoutRevision(
					serviceContext.getUserId(),
					layoutRevision.getLayoutSetBranchId(),
					layoutRevision.getLayoutBranchId(),
					enableLayoutRevision.getLayoutRevisionId(), false,
					layoutRevision.getPlid(),
					lastLayoutRevision.getLayoutRevisionId(),
					lastLayoutRevision.isPrivateLayout(),
					lastLayoutRevision.getName(), lastLayoutRevision.getTitle(),
					lastLayoutRevision.getDescription(),
					lastLayoutRevision.getKeywords(),
					lastLayoutRevision.getRobots(),
					lastLayoutRevision.getTypeSettings(),
					lastLayoutRevision.isIconImage(),
					lastLayoutRevision.getIconImageId(),
					lastLayoutRevision.getThemeId(),
					lastLayoutRevision.getColorSchemeId(),
					lastLayoutRevision.getWapThemeId(),
					lastLayoutRevision.getWapColorSchemeId(),
					lastLayoutRevision.getCss(), serviceContext);

			StagingUtil.setRecentLayoutRevisionId(
				themeDisplay.getUser(),
				newLayoutRevision.getLayoutSetBranchId(),
				newLayoutRevision.getPlid(),
				newLayoutRevision.getLayoutRevisionId());
		}
	}

	protected void updateLookAndFeel(
			ActionRequest actionRequest, long companyId, long liveGroupId,
			long stagingGroupId, boolean privateLayout, long layoutId,
			UnicodeProperties typeSettingsProperties)
		throws Exception {

		String[] devices = StringUtil.split(
			ParamUtil.getString(actionRequest, "devices"));

		for (String device : devices) {
			String deviceThemeId = ParamUtil.getString(
				actionRequest, device + "ThemeId");
			String deviceColorSchemeId = ParamUtil.getString(
				actionRequest, device + "ColorSchemeId");
			String deviceCss = ParamUtil.getString(
				actionRequest, device + "Css");
			boolean deviceWapTheme = device.equals("wap");

			boolean deviceInheritLookAndFeel = ParamUtil.getBoolean(
				actionRequest, device + "InheritLookAndFeel");

			if (deviceInheritLookAndFeel) {
				deviceThemeId = ThemeFactoryUtil.getDefaultRegularThemeId(
					companyId);
				deviceColorSchemeId = StringPool.BLANK;

				deleteThemeSettingsProperties(typeSettingsProperties, device);
			}
			else if (Validator.isNotNull(deviceThemeId)) {
				deviceColorSchemeId = getColorSchemeId(
					companyId, deviceThemeId, deviceColorSchemeId,
					deviceWapTheme);

				updateThemeSettingsProperties(
					actionRequest, companyId, typeSettingsProperties, device,
					deviceThemeId, deviceWapTheme);
			}

			long groupId = liveGroupId;

			if (stagingGroupId > 0) {
				groupId = stagingGroupId;
			}

			LayoutServiceUtil.updateLayout(
				groupId, privateLayout, layoutId,
				typeSettingsProperties.toString());

			LayoutServiceUtil.updateLookAndFeel(
				groupId, privateLayout, layoutId, deviceThemeId,
				deviceColorSchemeId, deviceCss, deviceWapTheme);
		}
	}

	protected String updateRedirect(
		ThemeDisplay themeDisplay, String redirect, Group group, Layout layout,
		String oldLayoutFriendlyURL) {

		if (Validator.isNull(redirect) ||
			Validator.isNull(oldLayoutFriendlyURL)) {

			return redirect;
		}

		if (layout != null) {
			String oldPath = oldLayoutFriendlyURL;

			if (layout.isTypeLinkToLayout() || layout.isTypeURL()) {
				try {
					layout = LayoutLocalServiceUtil.fetchFirstLayout(
						layout.getGroupId(), layout.getPrivateLayout(),
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
				}
				catch (Exception e) {
				}

				if (layout == null) {
					if (_log.isDebugEnabled()) {
						_log.debug("Unable to fetch first layout");
					}
				}
			}

			String newPath = layout.getFriendlyURL(themeDisplay.getLocale());

			return PortalUtil.updateRedirect(redirect, oldPath, newPath);
		}
		else if (group != null) {
			String oldPath = group.getFriendlyURL() + oldLayoutFriendlyURL;
			String newPath = group.getFriendlyURL();

			return PortalUtil.updateRedirect(redirect, oldPath, newPath);
		}

		return redirect;
	}

	protected UnicodeProperties updateThemeSettingsProperties(
			ActionRequest actionRequest, long companyId,
			UnicodeProperties typeSettingsProperties, String device,
			String deviceThemeId, boolean wapTheme)
		throws Exception {

		Theme theme = ThemeLocalServiceUtil.getTheme(
			companyId, deviceThemeId, wapTheme);

		deleteThemeSettingsProperties(typeSettingsProperties, device);

		Map<String, ThemeSetting> themeSettings =
			theme.getConfigurableSettings();

		if (themeSettings.isEmpty()) {
			return typeSettingsProperties;
		}

		setThemeSettingProperties(
			actionRequest, typeSettingsProperties, themeSettings, device,
			deviceThemeId);

		return typeSettingsProperties;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static Log _log = LogFactoryUtil.getLog(EditLayoutsAction.class);

}
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

package com.liferay.layout.admin.web.portlet;

import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.MDRActionLocalService;
import com.liferay.mobile.device.rules.service.MDRActionService;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalService;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceService;
import com.liferay.portal.ImageTypeException;
import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutFriendlyURLsException;
import com.liferay.portal.LayoutNameException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.SitemapChangeFrequencyException;
import com.liferay.portal.SitemapIncludeException;
import com.liferay.portal.SitemapPagePriorityException;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.ThemeSetting;
import com.liferay.portal.model.impl.ThemeSettingImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutPrototypeLocalService;
import com.liferay.portal.service.LayoutPrototypeService;
import com.liferay.portal.service.LayoutRevisionLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.LayoutSetService;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.ThemeLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
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
import javax.portlet.Portlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-layouts-admin",
		"com.liferay.portlet.icon=/icons/default.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Layouts Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutAdminPortletKeys.LAYOUT_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class}
)
public class LayoutAdminPortlet extends MVCPortlet {

	public void addLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
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

		long layoutPrototypeId = ParamUtil.getLong(
			uploadPortletRequest, "layoutPrototypeId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		Layout layout = null;

		boolean inheritFromParentLayoutId = ParamUtil.getBoolean(
			uploadPortletRequest, "inheritFromParentLayoutId");

		UnicodeProperties typeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		if (inheritFromParentLayoutId && (parentLayoutId > 0)) {
			Layout parentLayout = layoutLocalService.getLayout(
				groupId, privateLayout, parentLayoutId);

			layout = layoutService.addLayout(
				groupId, privateLayout, parentLayoutId, nameMap, titleMap,
				parentLayout.getDescriptionMap(), parentLayout.getKeywordsMap(),
				parentLayout.getRobotsMap(), parentLayout.getType(),
				parentLayout.getTypeSettings(), hidden, friendlyURLMap,
				serviceContext);

			inheritMobileRuleGroups(layout, serviceContext);

			if (parentLayout.isTypePortlet()) {
				ActionUtil.copyPreferences(actionRequest, layout, parentLayout);

				SitesUtil.copyLookAndFeel(layout, parentLayout);
			}
		}
		else if (layoutPrototypeId > 0) {
			LayoutPrototype layoutPrototype =
				layoutPrototypeService.getLayoutPrototype(layoutPrototypeId);

			boolean layoutPrototypeLinkEnabled = ParamUtil.getBoolean(
				uploadPortletRequest, "layoutPrototypeLinkEnabled");

			serviceContext.setAttribute(
				"layoutPrototypeLinkEnabled", layoutPrototypeLinkEnabled);

			serviceContext.setAttribute(
				"layoutPrototypeUuid", layoutPrototype.getUuid());

			layout = layoutService.addLayout(
				groupId, privateLayout, parentLayoutId, nameMap, titleMap,
				descriptionMap, keywordsMap, robotsMap,
				LayoutConstants.TYPE_PORTLET, typeSettingsProperties.toString(),
				hidden, friendlyURLMap, serviceContext);

			// Force propagation from page template to page.
			// See LPS-48430.

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
				copyLayout = layoutLocalService.fetchLayout(
					groupId, privateLayout, copyLayoutId);

				if ((copyLayout != null) && copyLayout.isTypePortlet()) {
					LayoutTypePortlet copyLayoutTypePortlet =
						(LayoutTypePortlet)copyLayout.getLayoutType();

					layoutTemplateId =
						copyLayoutTypePortlet.getLayoutTemplateId();

					typeSettingsProperties =
						copyLayout.getTypeSettingsProperties();
				}
			}

			layout = layoutService.addLayout(
				groupId, privateLayout, parentLayoutId, nameMap, titleMap,
				descriptionMap, keywordsMap, robotsMap, type,
				typeSettingsProperties.toString(), hidden, friendlyURLMap,
				serviceContext);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlet.setLayoutTemplateId(
				themeDisplay.getUserId(), layoutTemplateId);

			layoutService.updateLayout(
				groupId, privateLayout, layout.getLayoutId(),
				layout.getTypeSettings());

			if ((copyLayout != null) && copyLayout.isTypePortlet()) {
				ActionUtil.copyPreferences(actionRequest, layout, copyLayout);

				SitesUtil.copyLookAndFeel(layout, copyLayout);
			}
		}

		updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, privateLayout, layout.getLayoutId(),
			layout.getTypeSettingsProperties());
	}

	public void copyApplications(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

		Layout layout = layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (!layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
			return;
		}

		String layoutTemplateId = ParamUtil.getString(
			actionRequest, "layoutTemplateId",
			PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);

		layoutTypePortlet.setLayoutTemplateId(
			themeDisplay.getUserId(), layoutTemplateId);

		long copyLayoutId = ParamUtil.getLong(actionRequest, "copyLayoutId");

		if ((copyLayoutId == 0) || (copyLayoutId == layout.getLayoutId())) {
			return;
		}

		Layout copyLayout = layoutLocalService.fetchLayout(
			groupId, privateLayout, copyLayoutId);

		if ((copyLayout == null) || !copyLayout.isTypePortlet()) {
			return;
		}

		ActionUtil.removePortletIds(actionRequest, layout);

		ActionUtil.copyPreferences(actionRequest, layout, copyLayout);

		SitesUtil.copyLookAndFeel(layout, copyLayout);
	}

	public void deleteLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		long plid = ParamUtil.getLong(actionRequest, "plid");

		if (plid <= 0) {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

			Layout layout = layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);

			plid = layout.getPlid();
		}

		Object[] returnValue = SitesUtil.deleteLayout(
			actionRequest, actionResponse);

		if (plid == themeDisplay.getRefererPlid()) {
			long newRefererPlid = (Long)returnValue[2];

			redirect = HttpUtil.setParameter(
				redirect, "refererPlid", newRefererPlid);
			redirect = HttpUtil.setParameter(
				redirect, actionResponse.getNamespace() + "selPlid", 0);
		}

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	public void editLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");
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
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			iconBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		Layout layout = layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		layout = layoutService.updateLayout(
			groupId, privateLayout, layoutId, layout.getParentLayoutId(),
			nameMap, titleMap, descriptionMap, keywordsMap, robotsMap, type,
			hidden, friendlyURLMap, !deleteLogo, iconBytes, serviceContext);

		UnicodeProperties layoutTypeSettingsProperties =
			layout.getTypeSettingsProperties();

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

			layoutTypeSettingsProperties.putAll(formTypeSettingsProperties);

			layoutService.updateLayout(
				groupId, privateLayout, layoutId, layout.getTypeSettings());
		}
		else {
			layout.setTypeSettingsProperties(formTypeSettingsProperties);

			layoutTypeSettingsProperties.putAll(
				layout.getTypeSettingsProperties());

			layoutService.updateLayout(
				groupId, privateLayout, layoutId, layout.getTypeSettings());
		}

		String[] removeEmbeddedPortletIds = ParamUtil.getParameterValues(
			actionRequest, "removeEmbeddedPortletIds");

		if (removeEmbeddedPortletIds.length > 0) {
			portletLocalService.deletePortlets(
				themeDisplay.getCompanyId(), removeEmbeddedPortletIds,
				layout.getPlid());
		}

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		EventsProcessorUtil.process(
			PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
			layoutTypePortlet.getConfigurationActionUpdate(),
			uploadPortletRequest, response);

		updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, privateLayout, layout.getLayoutId(),
			layoutTypeSettingsProperties);
	}

	public void editLayoutSet(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long layoutSetId = ParamUtil.getLong(actionRequest, "layoutSetId");

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");

		LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(layoutSetId);

		updateLogo(actionRequest, liveGroupId, stagingGroupId, privateLayout);

		updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, privateLayout, layoutSet.getSettingsProperties());

		updateMergePages(actionRequest, liveGroupId);

		updateRobots(actionRequest, liveGroupId, privateLayout);

		updateSettings(
			actionRequest, liveGroupId, stagingGroupId, privateLayout,
			layoutSet.getSettingsProperties());
	}

	public void enableLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long incompleteLayoutRevisionId = ParamUtil.getLong(
			actionRequest, "incompleteLayoutRevisionId");

		LayoutRevision incompleteLayoutRevision =
			layoutRevisionLocalService.getLayoutRevision(
				incompleteLayoutRevisionId);

		long layoutBranchId = ParamUtil.getLong(
			actionRequest, "layoutBranchId",
			incompleteLayoutRevision.getLayoutBranchId());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		layoutRevisionLocalService.updateLayoutRevision(
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

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		super.processAction(actionRequest, actionResponse);

		MultiSessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) + "requestProcessed");
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
	public void resetMergeFailCountAndMerge(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutPrototypeId");

		LayoutPrototype layoutPrototype =
			layoutPrototypeLocalService.getLayoutPrototype(layoutPrototypeId);

		SitesUtil.setMergeFailCount(layoutPrototype, 0);

		long selPlid = ParamUtil.getLong(actionRequest, "selPlid");

		Layout selLayout = layoutLocalService.getLayout(selPlid);

		SitesUtil.resetPrototype(selLayout);

		SitesUtil.mergeLayoutPrototypeLayout(selLayout.getGroup(), selLayout);

		layoutPrototype = layoutPrototypeService.getLayoutPrototype(
			layoutPrototypeId);

		int mergeFailCountAfterMerge = SitesUtil.getMergeFailCount(
			layoutPrototype);

		if (mergeFailCountAfterMerge > 0) {
			SessionErrors.add(actionRequest, "resetMergeFailCountAndMerge");
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (resourceID.equals("getMobileDeviceRules")) {
			PortletSession portletSession = resourceRequest.getPortletSession();

			PortletContext portletContext = portletSession.getPortletContext();

			PortletRequestDispatcher portletRequestDispatcher =
				portletContext.getRequestDispatcher(
					"/layout/mobile_device_rules_rule_group_instances.jsp");

			portletRequestDispatcher.include(resourceRequest, resourceResponse);
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
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

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());
			}
			else {
				throw new PortletException(e);
			}
		}

		if (SessionErrors.contains(
				renderRequest, NoSuchGroupException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			try {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(renderRequest);

				ServiceContextThreadLocal.pushServiceContext(serviceContext);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected String getColorSchemeId(
			long companyId, String themeId, String colorSchemeId,
			boolean wapTheme)
		throws Exception {

		Theme theme = themeLocalService.getTheme(companyId, themeId, wapTheme);

		if (!theme.hasColorSchemes()) {
			colorSchemeId = StringPool.BLANK;
		}

		if (Validator.isNull(colorSchemeId)) {
			ColorScheme colorScheme = themeLocalService.getColorScheme(
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
			mdrRuleGroupInstanceLocalService.getRuleGroupInstances(
				Layout.class.getName(), layout.getParentPlid());

		for (MDRRuleGroupInstance parentMDRRuleGroupInstance :
				parentMDRRuleGroupInstances) {

			MDRRuleGroupInstance mdrRuleGroupInstance =
				mdrRuleGroupInstanceService.addRuleGroupInstance(
					layout.getGroupId(), Layout.class.getName(),
					layout.getPlid(),
					parentMDRRuleGroupInstance.getRuleGroupId(),
					parentMDRRuleGroupInstance.getPriority(), serviceContext);

			List<MDRAction> parentMDRActions = mdrActionLocalService.getActions(
				parentMDRRuleGroupInstance.getRuleGroupInstanceId());

			for (MDRAction mdrAction : parentMDRActions) {
				mdrActionService.addAction(
					mdrRuleGroupInstance.getRuleGroupInstanceId(),
					mdrAction.getNameMap(), mdrAction.getDescriptionMap(),
					mdrAction.getType(), mdrAction.getTypeSettings(),
					serviceContext);
			}
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof ImageTypeException ||
			cause instanceof LayoutFriendlyURLException ||
			cause instanceof LayoutFriendlyURLsException ||
			cause instanceof LayoutNameException ||
			cause instanceof LayoutParentLayoutIdException ||
			cause instanceof LayoutSetVirtualHostException ||
			cause instanceof LayoutTypeException ||
			cause instanceof NoSuchGroupException ||
			cause instanceof RequiredLayoutException ||
			cause instanceof SitemapChangeFrequencyException ||
			cause instanceof SitemapIncludeException ||
			cause instanceof SitemapPagePriorityException ||
			cause instanceof PrincipalException ||
			cause instanceof UploadException) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		this.dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		this.layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		this.layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeService(
		LayoutPrototypeService layoutPrototypeService) {

		this.layoutPrototypeService = layoutPrototypeService;
	}

	@Reference(unbind = "-")
	protected void setLayoutRevisionLocalService(
		LayoutRevisionLocalService layoutRevisionLocalService) {

		this.layoutRevisionLocalService = layoutRevisionLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		this.layoutSetLocalService = layoutSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetService(LayoutSetService layoutSetService) {
		this.layoutSetService = layoutSetService;
	}

	@Reference(unbind = "-")
	protected void setMDRActionLocalService(
		MDRActionLocalService mdrActionLocalService) {

		this.mdrActionLocalService = mdrActionLocalService;
	}

	@Reference(unbind = "-")
	protected void setMDRActionService(MDRActionService mdrActionService) {
		this.mdrActionService = mdrActionService;
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupInstanceLocalService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {

		this.mdrRuleGroupInstanceLocalService =
			mdrRuleGroupInstanceLocalService;
	}

	@Reference(unbind = "-")
	protected void setMDRRuleGroupInstanceService(
		MDRRuleGroupInstanceService mdrRuleGroupInstanceService) {

		this.mdrRuleGroupInstanceService = mdrRuleGroupInstanceService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		this.portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setThemeLocalService(ThemeLocalService themeLocalService) {
		this.themeLocalService = themeLocalService;
	}

	protected void setThemeSettingProperties(
			ActionRequest actionRequest,
			UnicodeProperties typeSettingsProperties,
			Map<String, ThemeSetting> themeSettings, String device,
			boolean isLayout)
		throws PortalException {

		Layout layout = null;

		if (isLayout) {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

			layout = layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);
		}

		for (String key : themeSettings.keySet()) {
			ThemeSetting themeSetting = themeSettings.get(key);

			String property =
				device + "ThemeSettingsProperties--" + key +
					StringPool.DOUBLE_DASH;

			String value = ParamUtil.getString(
				actionRequest, property, themeSetting.getValue());

			if ((isLayout &&
				 !Validator.equals(
					 value,
					 layout.getDefaultThemeSetting(key, device, false))) ||
				(!isLayout && !value.equals(themeSetting.getValue()))) {

				typeSettingsProperties.setProperty(
					ThemeSettingImpl.namespaceProperty(device, key), value);
			}
		}
	}

	protected void updateLogo(
			ActionRequest actionRequest, long liveGroupId, long stagingGroupId,
			boolean privateLayout)
		throws Exception {

		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = dlAppLocalService.getFileEntry(fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		long groupId = liveGroupId;

		if (stagingGroupId > 0) {
			groupId = stagingGroupId;
		}

		layoutSetService.updateLogo(
			groupId, privateLayout, !deleteLogo, logoBytes);
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
					deviceThemeId, deviceWapTheme, true);
			}

			long groupId = liveGroupId;

			if (stagingGroupId > 0) {
				groupId = stagingGroupId;
			}

			layoutService.updateLayout(
				groupId, privateLayout, layoutId,
				typeSettingsProperties.toString());

			layoutService.updateLookAndFeel(
				groupId, privateLayout, layoutId, deviceThemeId,
				deviceColorSchemeId, deviceCss, deviceWapTheme);
		}
	}

	protected void updateLookAndFeel(
			ActionRequest actionRequest, long companyId, long liveGroupId,
			long stagingGroupId, boolean privateLayout,
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

			if (Validator.isNotNull(deviceThemeId)) {
				deviceColorSchemeId = getColorSchemeId(
					companyId, deviceThemeId, deviceColorSchemeId,
					deviceWapTheme);

				updateThemeSettingsProperties(
					actionRequest, companyId, typeSettingsProperties, device,
					deviceThemeId, deviceWapTheme, false);
			}

			long groupId = liveGroupId;

			if (stagingGroupId > 0) {
				groupId = stagingGroupId;
			}

			layoutSetService.updateLookAndFeel(
				groupId, privateLayout, deviceThemeId, deviceColorSchemeId,
				deviceCss, deviceWapTheme);
		}
	}

	protected void updateMergePages(
			ActionRequest actionRequest, long liveGroupId)
		throws Exception {

		boolean mergeGuestPublicPages = ParamUtil.getBoolean(
			actionRequest, "mergeGuestPublicPages");

		Group liveGroup = groupLocalService.getGroup(liveGroupId);

		UnicodeProperties typeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"mergeGuestPublicPages", String.valueOf(mergeGuestPublicPages));

		groupService.updateGroup(liveGroupId, liveGroup.getTypeSettings());
	}

	protected void updateRobots(
			ActionRequest actionRequest, long liveGroupId,
			boolean privateLayout)
		throws Exception {

		Group liveGroup = groupLocalService.getGroup(liveGroupId);

		UnicodeProperties typeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		String propertyName = "false-robots.txt";

		if (privateLayout) {
			propertyName = "true-robots.txt";
		}

		String robots = ParamUtil.getString(
			actionRequest, "robots",
			liveGroup.getTypeSettingsProperty(propertyName));

		typeSettingsProperties.setProperty(propertyName, robots);

		groupService.updateGroup(
			liveGroup.getGroupId(), typeSettingsProperties.toString());
	}

	protected void updateSettings(
			ActionRequest actionRequest, long liveGroupId, long stagingGroupId,
			boolean privateLayout, UnicodeProperties settingsProperties)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		settingsProperties.putAll(typeSettingsProperties);

		long groupId = liveGroupId;

		if (stagingGroupId > 0) {
			groupId = stagingGroupId;
		}

		layoutSetService.updateSettings(
			groupId, privateLayout, settingsProperties.toString());
	}

	protected UnicodeProperties updateThemeSettingsProperties(
			ActionRequest actionRequest, long companyId,
			UnicodeProperties typeSettingsProperties, String device,
			String deviceThemeId, boolean wapTheme, boolean isLayout)
		throws Exception {

		Theme theme = themeLocalService.getTheme(
			companyId, deviceThemeId, wapTheme);

		deleteThemeSettingsProperties(typeSettingsProperties, device);

		Map<String, ThemeSetting> themeSettings =
			theme.getConfigurableSettings();

		if (themeSettings.isEmpty()) {
			return typeSettingsProperties;
		}

		setThemeSettingProperties(
			actionRequest, typeSettingsProperties, themeSettings, device,
			isLayout);

		return typeSettingsProperties;
	}

	protected DLAppLocalService dlAppLocalService;
	protected GroupLocalService groupLocalService;
	protected GroupService groupService;
	protected LayoutLocalService layoutLocalService;
	protected LayoutPrototypeLocalService layoutPrototypeLocalService;
	protected LayoutPrototypeService layoutPrototypeService;
	protected LayoutRevisionLocalService layoutRevisionLocalService;
	protected LayoutService layoutService;
	protected LayoutSetLocalService layoutSetLocalService;
	protected LayoutSetService layoutSetService;
	protected MDRActionLocalService mdrActionLocalService;
	protected MDRActionService mdrActionService;
	protected MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService;
	protected MDRRuleGroupInstanceService mdrRuleGroupInstanceService;
	protected PortletLocalService portletLocalService;
	protected ThemeLocalService themeLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutAdminPortlet.class);

}
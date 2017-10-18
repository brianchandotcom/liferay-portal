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

package com.liferay.layout.admin.web.internal.portlet;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.admin.web.internal.portlet.action.ActionUtil;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionNameException;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.model.MDRRuleGroupInstance;
import com.liferay.mobile.device.rules.service.MDRActionLocalService;
import com.liferay.mobile.device.rules.service.MDRActionService;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalService;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceService;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.ImageTypeException;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLsException;
import com.liferay.portal.kernel.exception.LayoutNameException;
import com.liferay.portal.kernel.exception.LayoutParentLayoutIdException;
import com.liferay.portal.kernel.exception.LayoutSetVirtualHostException;
import com.liferay.portal.kernel.exception.LayoutTypeException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredLayoutException;
import com.liferay.portal.kernel.exception.SitemapChangeFrequencyException;
import com.liferay.portal.kernel.exception.SitemapIncludeException;
import com.liferay.portal.kernel.exception.SitemapPagePriorityException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
		"com.liferay.portlet.header-portlet-css=/css/main.css",
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
			portal.getUploadPortletRequest(actionRequest);

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

		String linkToLayoutUuid = ParamUtil.getString(
			actionRequest, "linkToLayoutUuid");

		if (Validator.isNotNull(linkToLayoutUuid)) {
			Layout linkToLayout = layoutLocalService.getLayoutByUuidAndGroupId(
				linkToLayoutUuid, groupId, privateLayout);

			typeSettingsProperties.put(
				"linkToLayoutId", String.valueOf(linkToLayout.getLayoutId()));
		}

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
				com.liferay.portlet.sites.action.ActionUtil.copyPreferences(
					actionRequest, layout, parentLayout);

				SitesUtil.copyLookAndFeel(layout, parentLayout);
			}
		}
		else if (layoutPrototypeId > 0) {
			LayoutPrototype layoutPrototype =
				layoutPrototypeService.getLayoutPrototype(layoutPrototypeId);

			boolean layoutPrototypeLinkEnabled = ParamUtil.getBoolean(
				uploadPortletRequest,
				"layoutPrototypeLinkEnabled" + layoutPrototype.getUuid());

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
				com.liferay.portlet.sites.action.ActionUtil.copyPreferences(
					actionRequest, layout, copyLayout);

				SitesUtil.copyLookAndFeel(layout, copyLayout);
			}
		}

		updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, privateLayout, layout.getLayoutId(),
			layout.getTypeSettingsProperties());

		String portletResource = ParamUtil.getString(
			uploadPortletRequest, "portletResource");

		MultiSessionMessages.add(
			actionRequest, portletResource + "layoutAdded", layout);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = portal.getLayoutFullURL(layout, themeDisplay);

			if (layout.isTypeURL()) {
				redirect = portal.getGroupFriendlyURL(
					layout.getLayoutSet(), themeDisplay);
			}
		}

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	public void deleteLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long selPlid = ParamUtil.getLong(actionRequest, "selPlid");

		if (selPlid <= 0) {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

			Layout layout = layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);

			selPlid = layout.getPlid();
		}

		Layout deleteLayout = layoutLocalService.getLayout(selPlid);

		String redirect = getRedirect(actionRequest, deleteLayout);

		SitesUtil.deleteLayout(actionRequest, actionResponse);

		MultiSessionMessages.add(actionRequest, "layoutDeleted", selPlid);

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	public void editLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			portal.getUploadPortletRequest(actionRequest);

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

		String currentType = layout.getType();

		layout = layoutService.updateLayout(
			groupId, privateLayout, layoutId, layout.getParentLayoutId(),
			nameMap, titleMap, descriptionMap, keywordsMap, robotsMap, type,
			hidden, friendlyURLMap, !deleteLogo, iconBytes, serviceContext);

		themeDisplay.clearLayoutFriendlyURL(layout);

		UnicodeProperties layoutTypeSettingsProperties =
			layout.getTypeSettingsProperties();

		UnicodeProperties formTypeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		String linkToLayoutUuid = ParamUtil.getString(
			actionRequest, "linkToLayoutUuid");

		if (Validator.isNotNull(linkToLayoutUuid)) {
			Layout linkToLayout = layoutLocalService.getLayoutByUuidAndGroupId(
				linkToLayoutUuid, groupId, privateLayout);

			formTypeSettingsProperties.put(
				"linkToLayoutId", String.valueOf(linkToLayout.getLayoutId()));
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (type.equals(LayoutConstants.TYPE_PORTLET)) {
			String layoutTemplateId = ParamUtil.getString(
				uploadPortletRequest, "layoutTemplateId",
				PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);

			layoutTypePortlet.setLayoutTemplateId(
				themeDisplay.getUserId(), layoutTemplateId);

			layoutTypeSettingsProperties.putAll(formTypeSettingsProperties);

			boolean layoutCustomizable = GetterUtil.getBoolean(
				layoutTypeSettingsProperties.get(
					LayoutConstants.CUSTOMIZABLE_LAYOUT));

			if (!layoutCustomizable) {
				layoutTypePortlet.removeCustomization(
					layoutTypeSettingsProperties);
			}

			layout = layoutService.updateLayout(
				groupId, privateLayout, layoutId,
				layoutTypeSettingsProperties.toString());

			if (!currentType.equals(LayoutConstants.TYPE_PORTLET)) {
				portletPreferencesLocalService.deletePortletPreferences(
					0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());
			}
		}
		else {
			layout.setTypeSettingsProperties(formTypeSettingsProperties);

			layoutTypeSettingsProperties.putAll(
				layout.getTypeSettingsProperties());

			layout = layoutService.updateLayout(
				groupId, privateLayout, layoutId, layout.getTypeSettings());
		}

		HttpServletResponse response = portal.getHttpServletResponse(
			actionResponse);

		EventsProcessorUtil.process(
			PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
			layoutTypePortlet.getConfigurationActionUpdate(),
			uploadPortletRequest, response);

		updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, privateLayout, layout.getLayoutId(),
			layout.getTypeSettingsProperties());

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = portal.getLayoutFullURL(layout, themeDisplay);
		}

		MultiSessionMessages.add(actionRequest, "layoutUpdated", layout);

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	public void resetCustomizationView(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				ActionKeys.CUSTOMIZE)) {

			throw new PrincipalException();
		}

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		if ((layoutTypePortlet != null) && layoutTypePortlet.isCustomizable() &&
			layoutTypePortlet.isCustomizedView()) {

			layoutTypePortlet.resetUserPreferences();
		}

		MultiSessionMessages.add(
			actionRequest,
			portal.getPortletId(actionRequest) + "requestProcessed");

		Layout layout = themeDisplay.getLayout();

		actionResponse.sendRedirect(
			layout.getRegularURL(portal.getHttpServletRequest(actionRequest)));
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

	public void resetPrototype(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SitesUtil.resetPrototype(themeDisplay.getLayout());

		MultiSessionMessages.add(
			actionRequest,
			portal.getPortletId(actionRequest) + "requestProcessed");
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			getGroup(renderRequest);
			getLayout(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof NoSuchLayoutException ||
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
		else if (SessionErrors.contains(
					renderRequest, NoSuchLayoutException.class.getName())) {

			PortletURL redirectURL = portal.getControlPanelPortletURL(
				renderRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE);

			redirectURL.setParameter("mvcPath", "/view.jsp");
			redirectURL.setParameter(
				"selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));

			renderRequest.setAttribute(
				WebKeys.REDIRECT, redirectURL.toString());

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

			renderRequest.setAttribute(
				ApplicationListWebKeys.GROUP_PROVIDER, groupProvider);

			renderRequest.setAttribute(
				LayoutAdminWebKeys.ITEM_SELECTOR, itemSelector);

			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected String getEmptyLayoutSetURL(
		PortletRequest portletRequest, long groupId, boolean privateLayout) {

		PortletURL emptyLayoutSetURL = portal.getControlPanelPortletURL(
			portletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		emptyLayoutSetURL.setParameter("mvcPath", "/empty_layout_set.jsp");
		emptyLayoutSetURL.setParameter(
			"selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));
		emptyLayoutSetURL.setParameter("groupId", String.valueOf(groupId));
		emptyLayoutSetURL.setParameter(
			"privateLayout", String.valueOf(privateLayout));

		return emptyLayoutSetURL.toString();
	}

	protected Group getGroup(PortletRequest portletRequest) throws Exception {
		return com.liferay.portlet.sites.action.ActionUtil.getGroup(
			portletRequest);
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

	protected Layout getLayout(PortletRequest portletRequest) throws Exception {
		long selPlid = ParamUtil.getLong(
			portletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		if (selPlid != LayoutConstants.DEFAULT_PLID) {
			return layoutLocalService.getLayout(selPlid);
		}

		return null;
	}

	protected long getNewPlid(Layout layout) {
		long newPlid = LayoutConstants.DEFAULT_PLID;

		if (layout.getParentLayoutId() !=
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			Layout parentLayout = layoutLocalService.fetchLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getParentLayoutId());

			if (parentLayout != null) {
				newPlid = parentLayout.getPlid();
			}
		}

		if (newPlid <= 0) {
			Layout firstLayout = layoutLocalService.fetchFirstLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if ((firstLayout != null) &&
				(firstLayout.getPlid() != layout.getPlid())) {

				newPlid = firstLayout.getPlid();
			}

			if (newPlid <= 0) {
				Layout otherLayoutSetFirstLayout =
					layoutLocalService.fetchFirstLayout(
						layout.getGroupId(), !layout.isPrivateLayout(),
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

				if ((otherLayoutSetFirstLayout != null) &&
					(otherLayoutSetFirstLayout.getPlid() != layout.getPlid())) {

					newPlid = otherLayoutSetFirstLayout.getPlid();
				}
			}
		}

		return newPlid;
	}

	protected String getRedirect(ActionRequest actionRequest, Layout layout)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		Layout refererLayout = layoutLocalService.fetchLayout(
			themeDisplay.getRefererPlid());

		if (refererLayout == null) {
			return redirect;
		}

		boolean ancestor = false;

		if (layout.getPlid() == themeDisplay.getRefererPlid()) {
			ancestor = true;
		}
		else {
			for (Layout parentLayout : refererLayout.getAncestors()) {
				if (parentLayout.getPlid() == layout.getPlid()) {
					ancestor = true;
				}
			}
		}

		if (!ancestor) {
			return redirect;
		}

		long newRefererPlid = getNewPlid(layout);

		Layout redirectLayout = layoutLocalService.fetchLayout(newRefererPlid);

		if (redirectLayout != null) {
			redirect = portal.getLayoutFullURL(redirectLayout, themeDisplay);
		}
		else {
			redirect = getEmptyLayoutSetURL(
				actionRequest, layout.getGroupId(), layout.isPrivateLayout());
		}

		return redirect;
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
	protected boolean isAlwaysSendRedirect() {
		return true;
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof AssetCategoryException ||
			cause instanceof DuplicateLayoutPageTemplateCollectionException ||
			cause instanceof ImageTypeException ||
			cause instanceof LayoutFriendlyURLException ||
			cause instanceof LayoutFriendlyURLsException ||
			cause instanceof LayoutNameException ||
			cause instanceof LayoutPageTemplateCollectionNameException ||
			cause instanceof LayoutParentLayoutIdException ||
			cause instanceof LayoutSetVirtualHostException ||
			cause instanceof LayoutTypeException ||
			cause instanceof NoSuchGroupException ||
			cause instanceof PrincipalException ||
			cause instanceof RequiredLayoutException ||
			cause instanceof SitemapChangeFrequencyException ||
			cause instanceof SitemapIncludeException ||
			cause instanceof SitemapPagePriorityException ||
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
	protected void setGroupProvider(GroupProvider groupProvider) {
		this.groupProvider = groupProvider;
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
	protected void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
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
	protected void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		this.portletPreferencesLocalService = portletPreferencesLocalService;
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

			boolean deviceInheritLookAndFeel = ParamUtil.getBoolean(
				actionRequest, device + "InheritLookAndFeel");

			if (deviceInheritLookAndFeel) {
				deviceThemeId = ThemeFactoryUtil.getDefaultRegularThemeId(
					companyId);
				deviceColorSchemeId = StringPool.BLANK;

				actionUtil.deleteThemeSettingsProperties(
					typeSettingsProperties, device);
			}
			else if (Validator.isNotNull(deviceThemeId)) {
				deviceColorSchemeId = actionUtil.getColorSchemeId(
					companyId, deviceThemeId, deviceColorSchemeId);

				actionUtil.updateThemeSettingsProperties(
					actionRequest, companyId, typeSettingsProperties, device,
					deviceThemeId, true);
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
				deviceColorSchemeId, deviceCss);
		}
	}

	@Reference
	protected ActionUtil actionUtil;

	protected DLAppLocalService dlAppLocalService;
	protected GroupProvider groupProvider;

	@Reference
	protected ItemSelector itemSelector;

	protected LayoutLocalService layoutLocalService;
	protected LayoutPrototypeLocalService layoutPrototypeLocalService;
	protected LayoutPrototypeService layoutPrototypeService;
	protected LayoutService layoutService;
	protected MDRActionLocalService mdrActionLocalService;
	protected MDRActionService mdrActionService;
	protected MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService;
	protected MDRRuleGroupInstanceService mdrRuleGroupInstanceService;

	@Reference
	protected Portal portal;

	protected PortletPreferencesLocalService portletPreferencesLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutAdminPortlet.class);

}
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

package com.liferay.portal.events;

import com.liferay.portal.exception.LayoutPermissionException;
import com.liferay.portal.exception.NoSuchGroupException;
import com.liferay.portal.exception.NoSuchLayoutException;
import com.liferay.portal.exception.NoSuchUserException;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.interval.IntervalActionProcessor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortalWebResourceConstants;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SessionParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.LayoutTypeAccessPolicy;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.LayoutClone;
import com.liferay.portal.util.LayoutCloneFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portlet.exportimport.service.ExportImportLocalServiceUtil;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Felix Ventero
 * @author Jorge Ferrer
 */
public class ServicePreAction extends Action {

	public ServicePreAction() {
		initImportLARFiles();
	}

	public ThemeDisplay initThemeDisplay(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		// Company

		Company company = PortalUtil.getCompany(request);

		long companyId = company.getCompanyId();

		// CDN host

		String cdnHost = PortalUtil.getCDNHost(request);

		String dynamicResourcesCDNHost = StringPool.BLANK;

		boolean cdnDynamicResourceEnabled =
			PortalUtil.isCDNDynamicResourcesEnabled(request);

		if (cdnDynamicResourceEnabled) {
			dynamicResourcesCDNHost = cdnHost;
		}

		// Portal URL

		String portalURL = PortalUtil.getPortalURL(request);

		// Paths

		String contextPath = PortalUtil.getPathContext();
		String friendlyURLPrivateGroupPath =
			PortalUtil.getPathFriendlyURLPrivateGroup();
		String friendlyURLPrivateUserPath =
			PortalUtil.getPathFriendlyURLPrivateUser();
		String friendlyURLPublicPath = PortalUtil.getPathFriendlyURLPublic();
		String imagePath = dynamicResourcesCDNHost.concat(
			PortalUtil.getPathImage());
		String mainPath = PortalUtil.getPathMain();

		String i18nPath = (String)request.getAttribute(WebKeys.I18N_PATH);

		if (Validator.isNotNull(i18nPath)) {
			if (Validator.isNotNull(contextPath)) {
				String i18nContextPath = contextPath.concat(i18nPath);

				friendlyURLPrivateGroupPath = StringUtil.replaceFirst(
					friendlyURLPrivateGroupPath, contextPath, i18nContextPath);
				friendlyURLPrivateUserPath = StringUtil.replaceFirst(
					friendlyURLPrivateUserPath, contextPath, i18nContextPath);
				friendlyURLPublicPath = StringUtil.replaceFirst(
					friendlyURLPublicPath, contextPath, i18nContextPath);
				mainPath = StringUtil.replaceFirst(
					mainPath, contextPath, i18nContextPath);
			}
			else {
				friendlyURLPrivateGroupPath = i18nPath.concat(
					friendlyURLPrivateGroupPath);
				friendlyURLPrivateUserPath = i18nPath.concat(
					friendlyURLPrivateUserPath);
				friendlyURLPublicPath = i18nPath.concat(friendlyURLPublicPath);
				mainPath = i18nPath.concat(mainPath);
			}
		}

		// Company logo

		StringBundler sb = new StringBundler(5);

		sb.append(imagePath);
		sb.append("/company_logo?img_id=");
		sb.append(company.getLogoId());
		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(company.getLogoId()));

		String companyLogo = sb.toString();

		int companyLogoHeight = 0;
		int companyLogoWidth = 0;

		Image companyLogoImage = null;

		if (company.getLogoId() > 0) {
			companyLogoImage = ImageLocalServiceUtil.getCompanyLogo(
				company.getLogoId());
		}
		else {
			companyLogoImage = ImageToolUtil.getDefaultCompanyLogo();
		}

		if (companyLogoImage != null) {
			companyLogoHeight = companyLogoImage.getHeight();
			companyLogoWidth = companyLogoImage.getWidth();
		}

		String realCompanyLogo = companyLogo;
		int realCompanyLogoHeight = companyLogoHeight;
		int realCompanyLogoWidth = companyLogoWidth;

		// User

		User user = null;

		try {
			user = PortalUtil.initUser(request);
		}
		catch (NoSuchUserException nsue) {
			return null;
		}

		boolean signedIn = !user.isDefaultUser();

		if (PropsValues.BROWSER_CACHE_DISABLED ||
			(PropsValues.BROWSER_CACHE_SIGNED_IN_DISABLED && signedIn)) {

			response.setDateHeader(HttpHeaders.EXPIRES, 0);
			response.setHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
			response.setHeader(
				HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_NO_CACHE_VALUE);
		}

		User realUser = user;

		Long realUserId = (Long)session.getAttribute(WebKeys.USER_ID);

		if (realUserId != null) {
			if (user.getUserId() != realUserId.longValue()) {
				realUser = UserLocalServiceUtil.getUserById(
					realUserId.longValue());
			}
		}

		String doAsUserId = ParamUtil.getString(request, "doAsUserId");
		String doAsUserLanguageId = ParamUtil.getString(
			request, "doAsUserLanguageId");
		long doAsGroupId = ParamUtil.getLong(request, "doAsGroupId");

		long refererGroupId = ParamUtil.getLong(request, "refererGroupId");

		long refererPlid = ParamUtil.getLong(request, "refererPlid");

		if (LayoutLocalServiceUtil.fetchLayout(refererPlid) == null) {
			refererPlid = 0;
		}

		// Permission checker

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		// Cookie support

		try {

			// LEP-4069

			CookieKeys.validateSupportCookie(request);
		}
		catch (Exception e) {
			CookieKeys.addSupportCookie(request, response);
		}

		// Time zone

		TimeZone timeZone = user.getTimeZone();

		if (timeZone == null) {
			timeZone = company.getTimeZone();
		}

		// Layouts

		if (signedIn) {
			updateUserLayouts(user);
		}

		Layout layout = null;
		List<Layout> layouts = null;

		long plid = ParamUtil.getLong(request, "p_l_id");

		boolean viewableSourceGroup = true;

		if (plid > 0) {
			layout = LayoutLocalServiceUtil.getLayout(plid);
		}
		else {
			long groupId = ParamUtil.getLong(request, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				request, "privateLayout");
			long layoutId = ParamUtil.getLong(request, "layoutId");

			if ((groupId > 0) && (layoutId > 0)) {
				layout = LayoutLocalServiceUtil.getLayout(
					groupId, privateLayout, layoutId);
			}
		}

		if (layout != null) {
			Group layoutGroup = layout.getGroup();

			if (layoutGroup.isUser() &&
				((layout.isPrivateLayout() &&
				  !PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED) ||
				 (layout.isPublicLayout() &&
				  !PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED))) {

				User layoutUser = UserLocalServiceUtil.getUserById(
					companyId, layoutGroup.getClassPK());

				updateUserLayouts(layoutUser);

				layout = LayoutLocalServiceUtil.fetchLayout(layout.getPlid());
			}
		}

		if (layout != null) {
			long sourceGroupId = ParamUtil.getLong(request, "p_v_l_s_g_id");

			if ((sourceGroupId > 0) && (sourceGroupId != layout.getGroupId())) {
				Group sourceGroup = GroupLocalServiceUtil.getGroup(
					sourceGroupId);

				if (layout.isTypeControlPanel() || layout.isPublicLayout() ||
					SitesUtil.isUserGroupLayoutSetViewable(
						permissionChecker, layout.getGroup())) {

					layout = new VirtualLayout(layout, sourceGroup);
				}
				else {
					viewableSourceGroup = false;
				}
			}
		}

		String ppid = ParamUtil.getString(request, "p_p_id");

		Boolean redirectToDefaultLayout = (Boolean)request.getAttribute(
			WebKeys.REDIRECT_TO_DEFAULT_LAYOUT);

		if (redirectToDefaultLayout == null) {
			redirectToDefaultLayout = Boolean.FALSE;
		}

		if (layout != null) {
			Group group = layout.getGroup();

			if (!signedIn && PropsValues.AUTH_FORWARD_BY_REDIRECT) {
				request.setAttribute(WebKeys.REQUESTED_LAYOUT, layout);
			}

			boolean viewableGroup = hasAccessPermission(
				permissionChecker, layout, doAsGroupId, true);
			boolean viewableStaging =
				!group.isControlPanel() &&
				GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.VIEW_STAGING);

			if (viewableStaging) {
				layouts = LayoutLocalServiceUtil.getLayouts(
					layout.getGroupId(), layout.isPrivateLayout(),
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
			}
			else if ((!viewableGroup || !viewableSourceGroup) &&
					 group.isStagingGroup()) {

				layout = null;
			}
			else if (!isLoginRequest(request) &&
					 (!viewableGroup || !viewableSourceGroup ||
					  (!redirectToDefaultLayout &&
					   !hasAccessPermission(
						   permissionChecker, layout, doAsGroupId, false)))) {

				if (user.isDefaultUser() &&
					PropsValues.AUTH_LOGIN_PROMPT_ENABLED) {

					throw new PrincipalException.MustBeAuthenticated(
						user.getUserId());
				}

				sb = new StringBundler(6);

				sb.append("User ");
				sb.append(user.getUserId());
				sb.append(" is not allowed to access the ");
				sb.append(layout.isPrivateLayout() ? "private": "public");
				sb.append(" pages of group ");
				sb.append(layout.getGroupId());

				if (_log.isWarnEnabled()) {
					_log.warn(sb.toString());
				}

				throw new NoSuchLayoutException(sb.toString());
			}
			else if (isLoginRequest(request) && !viewableGroup) {
				layout = null;
			}
			else if (group.isLayoutPrototype()) {
				layouts = new ArrayList<>();
			}
			else {
				layouts = LayoutLocalServiceUtil.getLayouts(
					layout.getGroupId(), layout.isPrivateLayout(),
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

				if (!group.isControlPanel()) {
					doAsGroupId = 0;
				}
			}
		}

		List<Layout> unfilteredLayouts = layouts;

		LayoutComposite viewableLayoutComposite = null;

		if (layout == null) {
			viewableLayoutComposite = getDefaultViewableLayoutComposite(
				request, user, permissionChecker, doAsGroupId, signedIn);

			request.setAttribute(WebKeys.LAYOUT_DEFAULT, Boolean.TRUE);
		}
		else {
			viewableLayoutComposite = getViewableLayoutComposite(
				request, user, permissionChecker, layout, layouts, doAsGroupId);
		}

		String layoutSetLogo = null;

		layout = viewableLayoutComposite.getLayout();
		layouts = viewableLayoutComposite.getLayouts();

		Group group = null;

		if (layout != null) {
			group = layout.getGroup();

			if (!group.isControlPanel()) {
				rememberVisitedGroupIds(request, group.getGroupId());
			}
		}

		LayoutTypePortlet layoutTypePortlet = null;

		layouts = mergeAdditionalLayouts(
			request, user, permissionChecker, layout, layouts, doAsGroupId);

		LayoutSet layoutSet = null;

		boolean hasAddLayoutLayoutPermission = false;
		boolean hasCustomizeLayoutPermission = false;
		boolean hasDeleteLayoutPermission = false;
		boolean hasUpdateLayoutPermission = false;

		boolean customizedView = SessionParamUtil.getBoolean(
			request, "customized_view", true);

		if (layout != null) {
			LayoutType layoutType = layout.getLayoutType();

			LayoutTypeAccessPolicy layoutTypeAccessPolicy =
				layoutType.getLayoutTypeAccessPolicy();

			hasAddLayoutLayoutPermission =
				layoutTypeAccessPolicy.isAddLayoutAllowed(
					permissionChecker, layout);
			hasCustomizeLayoutPermission =
				layoutTypeAccessPolicy.isCustomizeLayoutAllowed(
					permissionChecker, layout);
			hasDeleteLayoutPermission =
				layoutTypeAccessPolicy.isDeleteLayoutAllowed(
					permissionChecker, layout);
			hasUpdateLayoutPermission =
				layoutTypeAccessPolicy.isUpdateLayoutAllowed(
					permissionChecker, layout);

			layoutSet = layout.getLayoutSet();

			if (company.isSiteLogo()) {
				long logoId = 0;

				if (layoutSet.isLogo()) {
					logoId = layoutSet.getLogoId();

					if (logoId == 0) {
						logoId = layoutSet.getLiveLogoId();
					}
				}
				else {
					LayoutSet siblingLayoutSet =
						LayoutSetLocalServiceUtil.getLayoutSet(
							layout.getGroupId(), !layout.isPrivateLayout());

					if (siblingLayoutSet.isLogo()) {
						logoId = siblingLayoutSet.getLogoId();
					}
				}

				if (logoId > 0) {
					sb = new StringBundler(5);

					sb.append(imagePath);
					sb.append("/layout_set_logo?img_id=");
					sb.append(logoId);
					sb.append("&t=");
					sb.append(WebServerServletTokenUtil.getToken(logoId));

					layoutSetLogo = sb.toString();

					Image layoutSetLogoImage =
						ImageLocalServiceUtil.getCompanyLogo(logoId);

					companyLogo = layoutSetLogo;
					companyLogoHeight = layoutSetLogoImage.getHeight();
					companyLogoWidth = layoutSetLogoImage.getWidth();
				}
			}

			plid = layout.getPlid();

			// Updates to shared layouts are not reflected until the next time
			// the user logs in because group layouts are cached in the session

			layout = (Layout)layout.clone();

			layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

			boolean customizable = layoutTypePortlet.isCustomizable();

			if (!customizable ||
				group.isLayoutPrototype() || group.isLayoutSetPrototype() ||
				group.isStagingGroup()) {

				customizedView = false;
			}

			layoutTypePortlet.setCustomizedView(customizedView);
			layoutTypePortlet.setUpdatePermission(hasUpdateLayoutPermission);

			if (signedIn && customizable && customizedView &&
				hasCustomizeLayoutPermission) {

				PortalPreferences portalPreferences =
					PortletPreferencesFactoryUtil.getPortalPreferences(
						user.getUserId(), true);

				layoutTypePortlet.setPortalPreferences(portalPreferences);
			}

			LayoutClone layoutClone = LayoutCloneFactory.getInstance();

			if (layoutClone != null) {
				String typeSettings = layoutClone.get(request, plid);

				if (typeSettings != null) {
					UnicodeProperties typeSettingsProperties =
						new UnicodeProperties(true);

					typeSettingsProperties.load(typeSettings);

					String stateMax = typeSettingsProperties.getProperty(
						LayoutTypePortletConstants.STATE_MAX);
					String stateMin = typeSettingsProperties.getProperty(
						LayoutTypePortletConstants.STATE_MIN);
					String modeAbout = typeSettingsProperties.getProperty(
						LayoutTypePortletConstants.MODE_ABOUT);
					String modeConfig = typeSettingsProperties.getProperty(
						LayoutTypePortletConstants.MODE_CONFIG);
					String modeEdit = typeSettingsProperties.getProperty(
						LayoutTypePortletConstants.MODE_EDIT);
					String modeEditDefaults =
						typeSettingsProperties.getProperty(
							LayoutTypePortletConstants.MODE_EDIT_DEFAULTS);
					String modeEditGuest = typeSettingsProperties.getProperty(
						LayoutTypePortletConstants.MODE_EDIT_GUEST);
					String modeHelp = typeSettingsProperties.getProperty(
						LayoutTypePortletConstants.MODE_HELP);
					String modePreview = typeSettingsProperties.getProperty(
						LayoutTypePortletConstants.MODE_PREVIEW);
					String modePrint = typeSettingsProperties.getProperty(
						LayoutTypePortletConstants.MODE_PRINT);

					layoutTypePortlet.setStateMax(stateMax);
					layoutTypePortlet.setStateMin(stateMin);
					layoutTypePortlet.setModeAbout(modeAbout);
					layoutTypePortlet.setModeConfig(modeConfig);
					layoutTypePortlet.setModeEdit(modeEdit);
					layoutTypePortlet.setModeEditDefaults(modeEditDefaults);
					layoutTypePortlet.setModeEditGuest(modeEditGuest);
					layoutTypePortlet.setModeHelp(modeHelp);
					layoutTypePortlet.setModePreview(modePreview);
					layoutTypePortlet.setModePrint(modePrint);
				}
			}

			request.setAttribute(WebKeys.LAYOUT, layout);
			request.setAttribute(WebKeys.LAYOUTS, layouts);
		}

		// Locale

		String i18nLanguageId = (String)request.getAttribute(
			WebKeys.I18N_LANGUAGE_ID);

		Locale locale = PortalUtil.getLocale(request, response, true);

		// Scope

		long scopeGroupId = PortalUtil.getScopeGroupId(request);

		if (group.isInheritContent()) {
			scopeGroupId = group.getParentGroupId();
		}

		if ((scopeGroupId <= 0) && (doAsGroupId > 0)) {
			scopeGroupId = doAsGroupId;
		}

		long siteGroupId = 0;

		if (layout != null) {
			if (layout.isTypeControlPanel()) {
				siteGroupId = PortalUtil.getSiteGroupId(scopeGroupId);
			}
			else {
				siteGroupId = PortalUtil.getSiteGroupId(layout.getGroupId());
			}
		}

		// Theme and color scheme

		Theme theme = null;
		ColorScheme colorScheme = null;

		boolean wapTheme = BrowserSnifferUtil.isWap(request);

		if ((layout != null) &&
			(layout.isTypeControlPanel() || group.isControlPanel())) {

			String themeId = PrefsPropsUtil.getString(
				companyId, PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID);
			String colorSchemeId =
				ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId();

			theme = ThemeLocalServiceUtil.getTheme(
				companyId, themeId, wapTheme);
			colorScheme = ThemeLocalServiceUtil.getColorScheme(
				companyId, theme.getThemeId(), colorSchemeId, wapTheme);

			if (!wapTheme && theme.isWapTheme()) {
				theme = ThemeLocalServiceUtil.getTheme(
					companyId,
					PropsValues.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID, false);
				colorScheme = ThemeLocalServiceUtil.getColorScheme(
					companyId, theme.getThemeId(), colorSchemeId, false);
			}

			request.setAttribute(WebKeys.THEME, theme);
			request.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);
		}

		boolean themeCssFastLoad = PropsValues.THEME_CSS_FAST_LOAD;

		if (PropsValues.THEME_CSS_FAST_LOAD_CHECK_REQUEST_PARAMETER) {
			themeCssFastLoad = SessionParamUtil.getBoolean(
				request, "css_fast_load", PropsValues.THEME_CSS_FAST_LOAD);
		}

		boolean themeImagesFastLoad = PropsValues.THEME_IMAGES_FAST_LOAD;

		if (PropsValues.THEME_IMAGES_FAST_LOAD_CHECK_REQUEST_PARAMETER) {
			SessionParamUtil.getBoolean(
				request, "images_fast_load",
				PropsValues.THEME_IMAGES_FAST_LOAD);
		}

		boolean themeJsBarebone = PropsValues.JAVASCRIPT_BAREBONE_ENABLED;

		if (themeJsBarebone) {
			if (signedIn) {
				themeJsBarebone = false;
			}
		}

		boolean themeJsFastLoad = SessionParamUtil.getBoolean(
			request, "js_fast_load", PropsValues.JAVASCRIPT_FAST_LOAD);

		String lifecycle = ParamUtil.getString(request, "p_p_lifecycle", "0");

		lifecycle = ParamUtil.getString(request, "p_t_lifecycle", lifecycle);

		boolean isolated = ParamUtil.getBoolean(request, "p_p_isolated");

		String facebookCanvasPageURL = (String)request.getAttribute(
			WebKeys.FACEBOOK_CANVAS_PAGE_URL);

		boolean widget = false;

		Boolean widgetObj = (Boolean)request.getAttribute(WebKeys.WIDGET);

		if (widgetObj != null) {
			widget = widgetObj.booleanValue();
		}

		// Theme display

		ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

		themeDisplay.setRequest(request);
		themeDisplay.setResponse(response);

		// Set attributes first that other methods (getCDNBaseURL and
		// setLookAndFeel) depend on

		themeDisplay.setCDNHost(cdnHost);
		themeDisplay.setCDNDynamicResourcesHost(dynamicResourcesCDNHost);
		themeDisplay.setFacebookCanvasPageURL(facebookCanvasPageURL);
		themeDisplay.setPortalURL(portalURL);
		themeDisplay.setRefererPlid(refererPlid);
		themeDisplay.setSecure(request.isSecure());
		themeDisplay.setServerName(request.getServerName());
		themeDisplay.setServerPort(request.getServerPort());
		themeDisplay.setWidget(widget);

		themeDisplay.setCompany(company);
		themeDisplay.setCompanyLogo(companyLogo);
		themeDisplay.setCompanyLogoHeight(companyLogoHeight);
		themeDisplay.setCompanyLogoWidth(companyLogoWidth);
		themeDisplay.setDoAsGroupId(doAsGroupId);
		themeDisplay.setDoAsUserId(doAsUserId);
		themeDisplay.setDoAsUserLanguageId(doAsUserLanguageId);
		themeDisplay.setI18nLanguageId(i18nLanguageId);
		themeDisplay.setI18nPath(i18nPath);
		themeDisplay.setIsolated(isolated);
		themeDisplay.setLanguageId(LocaleUtil.toLanguageId(locale));
		themeDisplay.setLayout(layout);
		themeDisplay.setLayouts(layouts);
		themeDisplay.setLayoutSet(layoutSet);
		themeDisplay.setLayoutSetLogo(layoutSetLogo);
		themeDisplay.setLayoutTypePortlet(layoutTypePortlet);
		themeDisplay.setLifecycle(lifecycle);
		themeDisplay.setLifecycleAction(lifecycle.equals("1"));
		themeDisplay.setLifecycleEvent(lifecycle.equals("3"));
		themeDisplay.setLifecycleRender(lifecycle.equals("0"));
		themeDisplay.setLifecycleResource(lifecycle.equals("2"));
		themeDisplay.setLocale(locale);
		themeDisplay.setLookAndFeel(theme, colorScheme);
		themeDisplay.setPathApplet(contextPath.concat("/applets"));
		themeDisplay.setPathCms(contextPath.concat("/cms"));
		themeDisplay.setPathContext(contextPath);
		themeDisplay.setPathFlash(contextPath.concat("/flash"));
		themeDisplay.setPathFriendlyURLPrivateGroup(
			friendlyURLPrivateGroupPath);
		themeDisplay.setPathFriendlyURLPrivateUser(friendlyURLPrivateUserPath);
		themeDisplay.setPathFriendlyURLPublic(friendlyURLPublicPath);
		themeDisplay.setPathImage(imagePath);
		themeDisplay.setPathJavaScript(
			PortalWebResourcesUtil.getContextPath(
				PortalWebResourceConstants.RESOURCE_TYPE_JS));
		themeDisplay.setPathMain(mainPath);
		themeDisplay.setPathSound(contextPath.concat("/html/sound"));
		themeDisplay.setPermissionChecker(permissionChecker);
		themeDisplay.setPlid(plid);
		themeDisplay.setPpid(ppid);
		themeDisplay.setRealCompanyLogo(realCompanyLogo);
		themeDisplay.setRealCompanyLogoHeight(realCompanyLogoHeight);
		themeDisplay.setRealCompanyLogoWidth(realCompanyLogoWidth);
		themeDisplay.setRealUser(realUser);
		themeDisplay.setRefererGroupId(refererGroupId);
		themeDisplay.setScopeGroupId(scopeGroupId);
		themeDisplay.setSignedIn(signedIn);
		themeDisplay.setSiteDefaultLocale(
			PortalUtil.getSiteDefaultLocale(siteGroupId));
		themeDisplay.setSiteGroupId(siteGroupId);
		themeDisplay.setStateExclusive(LiferayWindowState.isExclusive(request));
		themeDisplay.setStateMaximized(LiferayWindowState.isMaximized(request));
		themeDisplay.setStatePopUp(LiferayWindowState.isPopUp(request));
		themeDisplay.setThemeCssFastLoad(themeCssFastLoad);
		themeDisplay.setThemeImagesFastLoad(themeImagesFastLoad);
		themeDisplay.setThemeJsBarebone(themeJsBarebone);
		themeDisplay.setThemeJsFastLoad(themeJsFastLoad);
		themeDisplay.setTimeZone(timeZone);
		themeDisplay.setUnfilteredLayouts(unfilteredLayouts);
		themeDisplay.setUser(user);

		// Icons

		boolean showControlPanelIcon = false;

		if (signedIn &&
			PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.VIEW_CONTROL_PANEL)) {

			showControlPanelIcon = true;
		}

		themeDisplay.setShowControlPanelIcon(showControlPanelIcon);

		themeDisplay.setShowHomeIcon(true);
		themeDisplay.setShowMyAccountIcon(signedIn);
		themeDisplay.setShowPageSettingsIcon(hasDeleteLayoutPermission);
		themeDisplay.setShowPortalIcon(true);
		themeDisplay.setShowSignInIcon(!signedIn);

		boolean showSignOutIcon = signedIn;

		if (themeDisplay.isImpersonated()) {
			showSignOutIcon = false;
		}

		themeDisplay.setShowSignOutIcon(showSignOutIcon);

		themeDisplay.setShowStagingIcon(false);

		boolean showSiteAdministrationIcon = false;

		if (signedIn &&
			GroupPermissionUtil.contains(
				permissionChecker, group,
				ActionKeys.VIEW_SITE_ADMINISTRATION)) {

			showSiteAdministrationIcon = true;
		}

		themeDisplay.setShowSiteAdministrationIcon(showSiteAdministrationIcon);

		// Session

		if (PropsValues.SESSION_ENABLE_URL_WITH_SESSION_ID &&
			!CookieKeys.hasSessionId(request)) {

			themeDisplay.setAddSessionIdToURL(true);
			themeDisplay.setSessionId(session.getId());
		}

		// URLs

		String urlControlPanel = friendlyURLPrivateGroupPath.concat(
			GroupConstants.CONTROL_PANEL_FRIENDLY_URL);

		if (Validator.isNotNull(doAsUserId)) {
			urlControlPanel = HttpUtil.addParameter(
				urlControlPanel, "doAsUserId", doAsUserId);
		}

		if (refererGroupId > 0) {
			urlControlPanel = HttpUtil.addParameter(
				urlControlPanel, "refererGroupId", refererGroupId);
		}
		else if (scopeGroupId > 0) {
			Layout refererLayout = LayoutLocalServiceUtil.fetchLayout(plid);

			if (refererLayout != null) {
				Group refererLayoutGroup = refererLayout.getGroup();

				if (refererLayoutGroup.isUserGroup()) {
					urlControlPanel = HttpUtil.addParameter(
						urlControlPanel, "refererGroupId", scopeGroupId);
				}
			}
		}

		if (refererPlid > 0) {
			urlControlPanel = HttpUtil.addParameter(
				urlControlPanel, "refererPlid", refererPlid);
		}
		else if (plid > 0) {
			urlControlPanel = HttpUtil.addParameter(
				urlControlPanel, "refererPlid", plid);
		}

		if (themeDisplay.isAddSessionIdToURL()) {
			urlControlPanel = PortalUtil.getURLWithSessionId(
				urlControlPanel, session.getId());
		}

		themeDisplay.setURLControlPanel(urlControlPanel);

		String currentURL = PortalUtil.getCurrentURL(request);

		themeDisplay.setURLCurrent(currentURL);

		String urlHome = PortalUtil.getHomeURL(request);

		themeDisplay.setURLHome(urlHome);

		if (layout != null) {
			if (layout.isTypePortlet()) {
				boolean freeformLayout =
					layoutTypePortlet.getLayoutTemplateId().equals("freeform");

				themeDisplay.setFreeformLayout(freeformLayout);

				if (hasUpdateLayoutPermission) {
					themeDisplay.setShowLayoutTemplatesIcon(true);

					if (!group.isUser()) {
						themeDisplay.setShowPageCustomizationIcon(true);
					}
				}
			}

			if (hasUpdateLayoutPermission) {
				themeDisplay.setShowPageSettingsIcon(true);

				boolean site = group.isSite();

				if (!site && group.isStagingGroup()) {
					Group liveGroup = group.getLiveGroup();

					site = liveGroup.isSite();
				}

				if (site &&
					GroupPermissionUtil.contains(
						permissionChecker, scopeGroupId,
						ActionKeys.ASSIGN_MEMBERS)) {

					themeDisplay.setShowManageSiteMembershipsIcon(true);
				}
				else {
					themeDisplay.setShowManageSiteMembershipsIcon(false);
				}
			}

			Group scopeGroup = GroupLocalServiceUtil.getGroup(scopeGroupId);

			boolean hasAddLayoutGroupPermission = GroupPermissionUtil.contains(
				permissionChecker, scopeGroup, ActionKeys.ADD_LAYOUT);
			boolean hasManageLayoutsGroupPermission =
				GroupPermissionUtil.contains(
					permissionChecker, scopeGroup, ActionKeys.MANAGE_LAYOUTS);
			boolean hasManageStagingPermission = GroupPermissionUtil.contains(
				permissionChecker, scopeGroup, ActionKeys.MANAGE_STAGING);
			boolean hasPublishStagingPermission = GroupPermissionUtil.contains(
				permissionChecker, scopeGroup, ActionKeys.PUBLISH_STAGING);
			boolean hasUpdateGroupPermission = GroupPermissionUtil.contains(
				permissionChecker, scopeGroup, ActionKeys.UPDATE);
			boolean hasViewStagingPermission = GroupPermissionUtil.contains(
				permissionChecker, scopeGroup, ActionKeys.VIEW_STAGING);

			if (!group.isControlPanel() && !group.isUser() &&
				!group.isUserGroup() && hasUpdateGroupPermission) {

				themeDisplay.setShowSiteSettingsIcon(true);
			}

			if (!group.isLayoutPrototype() &&
				(hasAddLayoutGroupPermission || hasAddLayoutLayoutPermission ||
				 hasManageLayoutsGroupPermission || hasUpdateGroupPermission)) {

				themeDisplay.setShowSiteMapSettingsIcon(true);
			}

			if (group.hasStagingGroup() && !group.isStagingGroup()) {
				themeDisplay.setShowLayoutTemplatesIcon(false);
				themeDisplay.setURLPublishToLive(null);
			}

			if (group.isControlPanel()) {
				themeDisplay.setShowPageSettingsIcon(false);
				themeDisplay.setURLPublishToLive(null);
			}

			// LEP-4987

			if (group.isStaged() || group.isStagingGroup()) {
				if (hasManageStagingPermission || hasPublishStagingPermission ||
					hasUpdateLayoutPermission || hasViewStagingPermission) {

					themeDisplay.setShowStagingIcon(true);
				}

				if (hasPublishStagingPermission) {
					PortletURL publishToLiveURL = new PortletURLImpl(
						request, PortletKeys.EXPORT_IMPORT, plid,
						PortletRequest.RENDER_PHASE);

					publishToLiveURL.setParameter(
						"mvcRenderCommandName", "publishLayouts");

					if (layout.isPrivateLayout()) {
						publishToLiveURL.setParameter("tabs1", "private-pages");
					}
					else {
						publishToLiveURL.setParameter("tabs1", "public-pages");
					}

					publishToLiveURL.setParameter(
						"groupId", String.valueOf(scopeGroupId));
					publishToLiveURL.setParameter(
						"selPlid", String.valueOf(plid));
					publishToLiveURL.setPortletMode(PortletMode.VIEW);
					publishToLiveURL.setWindowState(
						LiferayWindowState.EXCLUSIVE);

					themeDisplay.setURLPublishToLive(publishToLiveURL);
				}
			}
		}

		if (!user.isActive() ||
			(PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.TERMS_OF_USE_REQUIRED) &&
			 !user.isAgreedToTermsOfUse())) {

			themeDisplay.setShowMyAccountIcon(false);
			themeDisplay.setShowPageSettingsIcon(false);
		}

		if ((layout != null) && layout.isLayoutPrototypeLinkActive()) {
			themeDisplay.setShowPageCustomizationIcon(false);
		}

		if (group.isLayoutPrototype()) {
			themeDisplay.setShowHomeIcon(false);
			themeDisplay.setShowManageSiteMembershipsIcon(false);
			themeDisplay.setShowMyAccountIcon(false);
			themeDisplay.setShowPageCustomizationIcon(false);
			themeDisplay.setShowPageSettingsIcon(true);
			themeDisplay.setShowPortalIcon(false);
			themeDisplay.setShowSignInIcon(false);
			themeDisplay.setShowSignOutIcon(false);
			themeDisplay.setShowSiteAdministrationIcon(false);
			themeDisplay.setShowSiteSettingsIcon(false);
			themeDisplay.setShowStagingIcon(false);
		}

		if (group.isLayoutSetPrototype()) {
			themeDisplay.setShowPageCustomizationIcon(false);
			themeDisplay.setShowSiteSettingsIcon(false);
		}

		if (group.hasStagingGroup() && !group.isStagingGroup()) {
			themeDisplay.setShowLayoutTemplatesIcon(false);
			themeDisplay.setShowPageCustomizationIcon(false);
			themeDisplay.setShowSiteMapSettingsIcon(false);
			themeDisplay.setShowSiteSettingsIcon(false);
		}

		themeDisplay.setURLPortal(portalURL.concat(contextPath));

		boolean secure = false;

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS ||
			request.isSecure()) {

			secure = true;
		}

		String securePortalURL = PortalUtil.getPortalURL(request, secure);

		String urlSignIn = securePortalURL.concat(mainPath).concat(
			_PATH_PORTAL_LOGIN);

		if (layout != null) {
			urlSignIn = HttpUtil.addParameter(
				urlSignIn, "p_l_id", layout.getPlid());
		}

		themeDisplay.setURLSignIn(urlSignIn);

		themeDisplay.setURLSignOut(mainPath.concat(_PATH_PORTAL_LOGOUT));

		return themeDisplay;
	}

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			servicePre(request, response);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Running takes " + stopWatch.getTime() + " ms");
		}
	}

	protected void addDefaultLayoutsByLAR(
			long userId, long groupId, boolean privateLayout, File larFile)
		throws PortalException {

		User user = UserLocalServiceUtil.getUser(userId);

		Map<String, String[]> parameterMap = new HashMap<>();

		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()});

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					user, groupId, privateLayout, null, parameterMap);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		ExportImportLocalServiceUtil.importLayouts(
			exportImportConfiguration, larFile);
	}

	protected void addDefaultUserPrivateLayoutByProperties(
			long userId, long groupId)
		throws PortalException {

		String friendlyURL = getFriendlyURL(
			PropsValues.DEFAULT_USER_PRIVATE_LAYOUT_FRIENDLY_URL);

		ServiceContext serviceContext = new ServiceContext();

		Layout layout = LayoutLocalServiceUtil.addLayout(
			userId, groupId, true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.DEFAULT_USER_PRIVATE_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, friendlyURL,
			serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			0, PropsValues.DEFAULT_USER_PRIVATE_LAYOUT_TEMPLATE_ID, false);

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		for (String columnId : layoutTemplate.getColumns()) {
			String keyPrefix = PropsKeys.DEFAULT_USER_PRIVATE_LAYOUT_PREFIX;

			String portletIds = PropsUtil.get(keyPrefix.concat(columnId));

			layoutTypePortlet.addPortletIds(
				0, StringUtil.split(portletIds), columnId, false);
		}

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		boolean updateLayoutSet = false;

		LayoutSet layoutSet = layout.getLayoutSet();

		if (Validator.isNotNull(
				PropsValues.DEFAULT_USER_PRIVATE_LAYOUT_REGULAR_THEME_ID)) {

			layoutSet.setThemeId(
				PropsValues.DEFAULT_USER_PRIVATE_LAYOUT_REGULAR_THEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.
					DEFAULT_USER_PRIVATE_LAYOUT_REGULAR_COLOR_SCHEME_ID)) {

			layoutSet.setColorSchemeId(
				PropsValues.
					DEFAULT_USER_PRIVATE_LAYOUT_REGULAR_COLOR_SCHEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.DEFAULT_USER_PRIVATE_LAYOUT_WAP_THEME_ID)) {

			layoutSet.setWapThemeId(
				PropsValues.DEFAULT_USER_PRIVATE_LAYOUT_WAP_THEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.DEFAULT_USER_PRIVATE_LAYOUT_WAP_COLOR_SCHEME_ID)) {

			layoutSet.setWapColorSchemeId(
				PropsValues.DEFAULT_USER_PRIVATE_LAYOUT_WAP_COLOR_SCHEME_ID);

			updateLayoutSet = true;
		}

		if (updateLayoutSet) {
			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
		}
	}

	protected void addDefaultUserPrivateLayouts(User user)
		throws PortalException {

		Group group = user.getGroup();

		if (privateLARFile != null) {
			addDefaultLayoutsByLAR(
				user.getUserId(), group.getGroupId(), true, privateLARFile);
		}
		else {
			addDefaultUserPrivateLayoutByProperties(
				user.getUserId(), group.getGroupId());
		}
	}

	protected void addDefaultUserPublicLayoutByProperties(
			long userId, long groupId)
		throws PortalException {

		String friendlyURL = getFriendlyURL(
			PropsValues.DEFAULT_USER_PUBLIC_LAYOUT_FRIENDLY_URL);

		ServiceContext serviceContext = new ServiceContext();

		Layout layout = LayoutLocalServiceUtil.addLayout(
			userId, groupId, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.DEFAULT_USER_PUBLIC_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, friendlyURL,
			serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			0, PropsValues.DEFAULT_USER_PUBLIC_LAYOUT_TEMPLATE_ID, false);

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		for (String columnId : layoutTemplate.getColumns()) {
			String keyPrefix = PropsKeys.DEFAULT_USER_PUBLIC_LAYOUT_PREFIX;

			String portletIds = PropsUtil.get(keyPrefix.concat(columnId));

			layoutTypePortlet.addPortletIds(
				0, StringUtil.split(portletIds), columnId, false);
		}

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		boolean updateLayoutSet = false;

		LayoutSet layoutSet = layout.getLayoutSet();

		if (Validator.isNotNull(
				PropsValues.DEFAULT_USER_PUBLIC_LAYOUT_REGULAR_THEME_ID)) {

			layoutSet.setThemeId(
				PropsValues.DEFAULT_USER_PUBLIC_LAYOUT_REGULAR_THEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.
					DEFAULT_USER_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID)) {

			layoutSet.setColorSchemeId(
				PropsValues.DEFAULT_USER_PUBLIC_LAYOUT_REGULAR_COLOR_SCHEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.DEFAULT_USER_PUBLIC_LAYOUT_WAP_THEME_ID)) {

			layoutSet.setWapThemeId(
				PropsValues.DEFAULT_USER_PUBLIC_LAYOUT_WAP_THEME_ID);

			updateLayoutSet = true;
		}

		if (Validator.isNotNull(
				PropsValues.DEFAULT_USER_PUBLIC_LAYOUT_WAP_COLOR_SCHEME_ID)) {

			layoutSet.setWapColorSchemeId(
				PropsValues.DEFAULT_USER_PUBLIC_LAYOUT_WAP_COLOR_SCHEME_ID);

			updateLayoutSet = true;
		}

		if (updateLayoutSet) {
			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
		}
	}

	protected void addDefaultUserPublicLayouts(User user)
		throws PortalException {

		Group userGroup = user.getGroup();

		if (publicLARFile != null) {
			addDefaultLayoutsByLAR(
				user.getUserId(), userGroup.getGroupId(), false, publicLARFile);
		}
		else {
			addDefaultUserPublicLayoutByProperties(
				user.getUserId(), userGroup.getGroupId());
		}
	}

	protected void deleteDefaultUserPrivateLayouts(User user)
		throws PortalException {

		Group group = user.getGroup();

		ServiceContext serviceContext = new ServiceContext();

		LayoutLocalServiceUtil.deleteLayouts(
			group.getGroupId(), true, serviceContext);
	}

	protected void deleteDefaultUserPublicLayouts(User user)
		throws PortalException {

		Group userGroup = user.getGroup();

		ServiceContext serviceContext = new ServiceContext();

		LayoutLocalServiceUtil.deleteLayouts(
			userGroup.getGroupId(), false, serviceContext);
	}

	protected LayoutComposite getDefaultUserPersonalSiteLayoutComposite(
		User user) {

		Layout layout = null;

		Group group = user.getGroup();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			group.getGroupId(), true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (layouts.isEmpty()) {
			layouts = LayoutLocalServiceUtil.getLayouts(
				group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
		}

		if (!layouts.isEmpty()) {
			layout = layouts.get(0);
		}

		return new LayoutComposite(layout, layouts);
	}

	protected LayoutComposite getDefaultUserSitesLayoutComposite(
			final User user)
		throws PortalException {

		final LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("usersGroups", Long.valueOf(user.getUserId()));

		int count = GroupLocalServiceUtil.searchCount(
			user.getCompanyId(), null, null, groupParams);

		IntervalActionProcessor<LayoutComposite> intervalActionProcessor =
			new IntervalActionProcessor<>(count);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod
				<LayoutComposite>() {

				@Override
				public LayoutComposite performIntervalAction(
					int start, int end) {

					List<Group> groups = GroupLocalServiceUtil.search(
						user.getCompanyId(), null, null, groupParams, start,
						end);

					for (Group group : groups) {
						List<Layout> layouts =
							LayoutLocalServiceUtil.getLayouts(
								group.getGroupId(), true,
								LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

						if (layouts.isEmpty()) {
							layouts = LayoutLocalServiceUtil.getLayouts(
								group.getGroupId(), false,
								LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
						}

						if (!layouts.isEmpty()) {
							return new LayoutComposite(layouts.get(0), layouts);
						}
					}

					return null;
				}

			});

		LayoutComposite layoutComposite =
			intervalActionProcessor.performIntervalActions();

		if (layoutComposite == null) {
			return new LayoutComposite(null, new ArrayList<Layout>());
		}

		return layoutComposite;
	}

	protected LayoutComposite getDefaultViewableLayoutComposite(
			HttpServletRequest request, User user,
			PermissionChecker permissionChecker, long doAsGroupId,
			boolean signedIn)
		throws PortalException {

		LayoutComposite defaultLayoutComposite =
			getDefaultVirtualHostLayoutComposite(request);

		defaultLayoutComposite = getViewableLayoutComposite(
			request, user, permissionChecker, defaultLayoutComposite,
			doAsGroupId);

		if (defaultLayoutComposite.getLayouts() != null) {
			return defaultLayoutComposite;
		}

		if (signedIn) {
			defaultLayoutComposite = getDefaultUserPersonalSiteLayoutComposite(
				user);

			if (defaultLayoutComposite.getLayout() == null) {
				defaultLayoutComposite = getDefaultUserSitesLayoutComposite(
					user);
			}

			defaultLayoutComposite = getViewableLayoutComposite(
				request, user, permissionChecker, defaultLayoutComposite,
				doAsGroupId);

			if (defaultLayoutComposite.getLayouts() != null) {
				return defaultLayoutComposite;
			}
		}

		defaultLayoutComposite = getGuestSiteLayoutComposite(user);

		return getViewableLayoutComposite(
			request, user, permissionChecker, defaultLayoutComposite,
			doAsGroupId);
	}

	protected LayoutComposite getDefaultVirtualHostLayoutComposite(
			HttpServletRequest request)
		throws PortalException {

		Layout layout = null;
		List<Layout> layouts = null;

		LayoutSet layoutSet = (LayoutSet)request.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if (layoutSet != null) {
			layouts = LayoutLocalServiceUtil.getLayouts(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			Group group = null;

			if (!layouts.isEmpty()) {
				layout = layouts.get(0);

				group = layout.getGroup();
			}

			if ((layout != null) && layout.isPrivateLayout()) {
				layouts = LayoutLocalServiceUtil.getLayouts(
					group.getGroupId(), false,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

				if (!layouts.isEmpty()) {
					layout = layouts.get(0);
				}
				else {
					group = null;
					layout = null;
				}
			}

			if ((group != null) && group.isStagingGroup()) {
				Group liveGroup = group.getLiveGroup();

				layouts = LayoutLocalServiceUtil.getLayouts(
					liveGroup.getGroupId(), false,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

				if (!layouts.isEmpty()) {
					layout = layouts.get(0);
				}
				else {
					layout = null;
				}
			}
		}

		return new LayoutComposite(layout, layouts);
	}

	protected String getFriendlyURL(String friendlyURL) {
		friendlyURL = GetterUtil.getString(friendlyURL);

		return FriendlyURLNormalizerUtil.normalize(friendlyURL);
	}

	protected LayoutComposite getGuestSiteLayoutComposite(User user)
		throws PortalException {

		Layout layout = null;
		List<Layout> layouts = null;

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			user.getCompanyId(), GroupConstants.GUEST);

		layouts = LayoutLocalServiceUtil.getLayouts(
			guestGroup.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (!layouts.isEmpty()) {
			layout = layouts.get(0);
		}

		return new LayoutComposite(layout, layouts);
	}

	protected LayoutComposite getViewableLayoutComposite(
			HttpServletRequest request, User user,
			PermissionChecker permissionChecker, Layout layout,
			List<Layout> layouts, long doAsGroupId)
		throws PortalException {

		if ((layouts == null) || layouts.isEmpty()) {
			return new LayoutComposite(layout, layouts);
		}

		Group group = layout.getGroup();

		boolean hasViewLayoutPermission = false;
		boolean hasViewStagingPermission =
			(group.isStagingGroup() || group.isStagedRemotely()) &&
			 !group.isControlPanel() &&
			 GroupPermissionUtil.contains(
				 permissionChecker, group, ActionKeys.VIEW_STAGING);

		if (hasAccessPermission(
				permissionChecker, layout, doAsGroupId, false) ||
			hasViewStagingPermission) {

			hasViewLayoutPermission = true;
		}

		List<Layout> accessibleLayouts = new ArrayList<>();

		for (int i = 0; i < layouts.size(); i++) {
			Layout curLayout = layouts.get(i);

			if (!curLayout.isHidden() &&
				(hasAccessPermission(
					permissionChecker, curLayout, doAsGroupId, false) ||
				 hasViewStagingPermission)) {

				if (accessibleLayouts.isEmpty() && !hasViewLayoutPermission) {
					layout = curLayout;
				}

				accessibleLayouts.add(curLayout);
			}
		}

		if (accessibleLayouts.isEmpty()) {
			layouts = null;

			if (!isLoginRequest(request) && !hasViewLayoutPermission) {
				if (user.isDefaultUser() &&
					PropsValues.AUTH_LOGIN_PROMPT_ENABLED) {

					throw new PrincipalException.MustBeAuthenticated(
						String.valueOf(user.getUserId()));
				}

				SessionErrors.add(
					request, LayoutPermissionException.class.getName());
			}
		}
		else {
			layouts = accessibleLayouts;
		}

		return new LayoutComposite(layout, layouts);
	}

	protected LayoutComposite getViewableLayoutComposite(
			HttpServletRequest request, User user,
			PermissionChecker permissionChecker,
			LayoutComposite defaultLayoutComposite, long doAsGroupId)
		throws PortalException {

		Layout layout = defaultLayoutComposite.getLayout();
		List<Layout> layouts = defaultLayoutComposite.getLayouts();

		return getViewableLayoutComposite(
			request, user, permissionChecker, layout, layouts, doAsGroupId);
	}

	protected boolean hasAccessPermission(
			PermissionChecker permissionChecker, Layout layout,
			long doAsGroupId, boolean checkViewableGroup)
		throws PortalException {

		return LayoutPermissionUtil.contains(
			permissionChecker, layout, checkViewableGroup, ActionKeys.VIEW);
	}

	protected Boolean hasPowerUserRole(User user) throws Exception {
		return RoleLocalServiceUtil.hasUserRole(
			user.getUserId(), user.getCompanyId(), RoleConstants.POWER_USER,
			true);
	}

	protected void initImportLARFiles() {
		String privateLARFileName =
			PropsValues.DEFAULT_USER_PRIVATE_LAYOUTS_LAR;

		if (_log.isDebugEnabled()) {
			_log.debug("Reading private LAR file " + privateLARFileName);
		}

		if (Validator.isNotNull(privateLARFileName)) {
			privateLARFile = new File(privateLARFileName);

			if (!privateLARFile.exists()) {
				_log.error(
					"Private LAR file " + privateLARFile + " does not exist");

				privateLARFile = null;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Using private LAR file " + privateLARFileName);
				}
			}
		}

		String publicLARFileName = PropsValues.DEFAULT_USER_PUBLIC_LAYOUTS_LAR;

		if (_log.isDebugEnabled()) {
			_log.debug("Reading public LAR file " + publicLARFileName);
		}

		if (Validator.isNotNull(publicLARFileName)) {
			publicLARFile = new File(publicLARFileName);

			if (!publicLARFile.exists()) {
				_log.error(
					"Public LAR file " + publicLARFile + " does not exist");

				publicLARFile = null;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Using public LAR file " + publicLARFileName);
				}
			}
		}
	}

	/**
	 * Returns <code>true</code> if the request URI's path starts with the
	 * portal's default login path <code>c/portal/login</code>.
	 *
	 * @param  request the servlet request for the page, which can be a result
	 *         of a redirect
	 * @return <code>true</code> if the request is a login request;
	 *         <code>false</code> otherwise
	 */
	protected boolean isLoginRequest(HttpServletRequest request) {
		String requestURI = request.getRequestURI();

		String mainPath = PortalUtil.getPathMain();

		String pathProxy = PortalUtil.getPathProxy();

		if (!Validator.isBlank(pathProxy)) {
			if (!requestURI.startsWith(pathProxy)) {
				requestURI = pathProxy + requestURI;
			}

			if (!mainPath.startsWith(pathProxy)) {
				mainPath = pathProxy + mainPath;
			}
		}

		if (requestURI.startsWith(mainPath.concat(_PATH_PORTAL_LOGIN))) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	protected boolean isViewableCommunity(
			User user, long groupId, boolean privateLayout,
			PermissionChecker permissionChecker)
		throws PortalException {

		return LayoutPermissionUtil.contains(
			permissionChecker, groupId, privateLayout, 0, ActionKeys.VIEW);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	protected boolean isViewableGroup(
			User user, long groupId, boolean privateLayout, long layoutId,
			String controlPanelCategory, PermissionChecker permissionChecker)
		throws PortalException {

		return LayoutPermissionUtil.contains(
			permissionChecker, groupId, privateLayout, layoutId,
			ActionKeys.VIEW);
	}

	protected List<Layout> mergeAdditionalLayouts(
			HttpServletRequest request, User user,
			PermissionChecker permissionChecker, Layout layout,
			List<Layout> layouts, long doAsGroupId)
		throws PortalException {

		if ((layout == null) || layout.isPrivateLayout()) {
			return layouts;
		}

		long layoutGroupId = layout.getGroupId();

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			user.getCompanyId(), GroupConstants.GUEST);

		if (layoutGroupId != guestGroup.getGroupId()) {
			Group layoutGroup = GroupLocalServiceUtil.getGroup(layoutGroupId);

			UnicodeProperties typeSettingsProperties =
				layoutGroup.getTypeSettingsProperties();

			boolean mergeGuestPublicPages = GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("mergeGuestPublicPages"));

			if (!mergeGuestPublicPages) {
				return layouts;
			}

			List<Layout> guestLayouts = LayoutLocalServiceUtil.getLayouts(
				guestGroup.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			LayoutComposite viewableLayoutComposite =
				getViewableLayoutComposite(
					request, user, permissionChecker, layout, guestLayouts,
					doAsGroupId);

			guestLayouts = viewableLayoutComposite.getLayouts();

			if (layouts == null) {
				return guestLayouts;
			}

			layouts.addAll(0, guestLayouts);
		}
		else {
			HttpSession session = request.getSession();

			Long previousGroupId = (Long)session.getAttribute(
				WebKeys.VISITED_GROUP_ID_PREVIOUS);

			if ((previousGroupId != null) &&
				(previousGroupId.longValue() != layoutGroupId)) {

				Group previousGroup = null;

				try {
					previousGroup = GroupLocalServiceUtil.getGroup(
						previousGroupId.longValue());
				}
				catch (NoSuchGroupException nsge) {
					if (_log.isWarnEnabled()) {
						_log.warn(nsge);
					}

					return layouts;
				}

				UnicodeProperties typeSettingsProperties =
					previousGroup.getTypeSettingsProperties();

				boolean mergeGuestPublicPages = GetterUtil.getBoolean(
					typeSettingsProperties.getProperty(
						"mergeGuestPublicPages"));

				if (!mergeGuestPublicPages) {
					return layouts;
				}

				List<Layout> previousLayouts =
					LayoutLocalServiceUtil.getLayouts(
						previousGroupId.longValue(), false,
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

				LayoutComposite viewableLayoutComposite =
					getViewableLayoutComposite(
						request, user, permissionChecker, layout,
						previousLayouts, doAsGroupId);

				previousLayouts = viewableLayoutComposite.getLayouts();

				if (previousLayouts != null) {
					layouts.addAll(previousLayouts);
				}
			}
		}

		return layouts;
	}

	protected void rememberVisitedGroupIds(
		HttpServletRequest request, long currentGroupId) {

		String requestURI = GetterUtil.getString(request.getRequestURI());

		if (!requestURI.endsWith(_PATH_PORTAL_LAYOUT)) {
			return;
		}

		HttpSession session = request.getSession();

		Long recentGroupId = (Long)session.getAttribute(
			WebKeys.VISITED_GROUP_ID_RECENT);

		Long previousGroupId = (Long)session.getAttribute(
			WebKeys.VISITED_GROUP_ID_PREVIOUS);

		if (recentGroupId == null) {
			recentGroupId = Long.valueOf(currentGroupId);

			session.setAttribute(
				WebKeys.VISITED_GROUP_ID_RECENT, recentGroupId);
		}
		else if (recentGroupId.longValue() != currentGroupId) {
			previousGroupId = Long.valueOf(recentGroupId.longValue());

			recentGroupId = Long.valueOf(currentGroupId);

			session.setAttribute(
				WebKeys.VISITED_GROUP_ID_RECENT, recentGroupId);

			session.setAttribute(
				WebKeys.VISITED_GROUP_ID_PREVIOUS, previousGroupId);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Current group id " + currentGroupId);
			_log.debug("Recent group id " + recentGroupId);
			_log.debug("Previous group id " + previousGroupId);
		}
	}

	protected void servicePre(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = initThemeDisplay(request, response);

		if (themeDisplay == null) {
			return;
		}

		request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		// Service context

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			request);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		// Ajaxable render

		if (PropsValues.LAYOUT_AJAX_RENDER_ENABLE) {
			boolean portletAjaxRender = ParamUtil.getBoolean(
				request, "p_p_ajax", true);

			request.setAttribute(
				WebKeys.PORTLET_AJAX_RENDER, portletAjaxRender);
		}

		// Parallel render

		if (PropsValues.LAYOUT_PARALLEL_RENDER_ENABLE &&
			ServerDetector.isTomcat()) {

			boolean portletParallelRender = ParamUtil.getBoolean(
				request, "p_p_parallel", true);

			request.setAttribute(
				WebKeys.PORTLET_PARALLEL_RENDER, portletParallelRender);
		}
	}

	protected void updateUserLayouts(User user) throws Exception {
		Boolean hasPowerUserRole = null;

		// Private layouts

		boolean addDefaultUserPrivateLayouts = false;

		if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED &&
			PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE) {

			addDefaultUserPrivateLayouts = true;

			if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED) {
				if (hasPowerUserRole == null) {
					hasPowerUserRole = hasPowerUserRole(user);
				}

				if (!hasPowerUserRole.booleanValue()) {
					addDefaultUserPrivateLayouts = false;
				}
			}
		}

		Boolean hasPrivateLayouts = null;

		if (addDefaultUserPrivateLayouts) {
			hasPrivateLayouts = LayoutLocalServiceUtil.hasLayouts(
				user, true, false);

			if (!hasPrivateLayouts) {
				addDefaultUserPrivateLayouts(user);
			}
		}

		boolean deleteDefaultUserPrivateLayouts = false;

		if (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED) {
			deleteDefaultUserPrivateLayouts = true;
		}
		else if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED) {
			if (hasPowerUserRole == null) {
				hasPowerUserRole = hasPowerUserRole(user);
			}

			if (!hasPowerUserRole.booleanValue()) {
				deleteDefaultUserPrivateLayouts = true;
			}
		}

		if (deleteDefaultUserPrivateLayouts) {
			if (hasPrivateLayouts == null) {
				hasPrivateLayouts = LayoutLocalServiceUtil.hasLayouts(
					user, true, false);
			}

			if (hasPrivateLayouts) {
				deleteDefaultUserPrivateLayouts(user);
			}
		}

		// Public pages

		boolean addDefaultUserPublicLayouts = false;

		if (PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED &&
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE) {

			addDefaultUserPublicLayouts = true;

			if (PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED) {
				if (hasPowerUserRole == null) {
					hasPowerUserRole = hasPowerUserRole(user);
				}

				if (!hasPowerUserRole.booleanValue()) {
					addDefaultUserPublicLayouts = false;
				}
			}
		}

		Boolean hasPublicLayouts = null;

		if (addDefaultUserPublicLayouts) {
			hasPublicLayouts = LayoutLocalServiceUtil.hasLayouts(
				user, false, false);

			if (!hasPublicLayouts) {
				addDefaultUserPublicLayouts(user);
			}
		}

		boolean deleteDefaultUserPublicLayouts = false;

		if (!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {
			deleteDefaultUserPublicLayouts = true;
		}
		else if (PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED) {
			if (hasPowerUserRole == null) {
				hasPowerUserRole = hasPowerUserRole(user);
			}

			if (!hasPowerUserRole.booleanValue()) {
				deleteDefaultUserPublicLayouts = true;
			}
		}

		if (deleteDefaultUserPublicLayouts) {
			if (hasPublicLayouts == null) {
				hasPublicLayouts = LayoutLocalServiceUtil.hasLayouts(
					user, false, false);
			}

			if (hasPublicLayouts) {
				deleteDefaultUserPublicLayouts(user);
			}
		}
	}

	protected File privateLARFile;
	protected File publicLARFile;

	protected class LayoutComposite {

		protected LayoutComposite(Layout layout, List<Layout> layouts) {
			_layout = layout;
			_layouts = layouts;
		}

		protected Layout getLayout() {
			return _layout;
		}

		protected List<Layout> getLayouts() {
			return _layouts;
		}

		private final Layout _layout;
		private final List<Layout> _layouts;

	}

	private static final String _PATH_PORTAL_LAYOUT = "/portal/layout";

	private static final String _PATH_PORTAL_LOGIN = "/portal/login";

	private static final String _PATH_PORTAL_LOGOUT = "/portal/logout";

	private static final Log _log = LogFactoryUtil.getLog(
		ServicePreAction.class);

}
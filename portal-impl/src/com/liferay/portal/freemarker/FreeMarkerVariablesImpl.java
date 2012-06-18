/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerVariables;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ArrayUtil_IW;
import com.liferay.portal.kernel.util.DateUtil_IW;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GetterUtil_IW;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil_IW;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Randomizer_IW;
import com.liferay.portal.kernel.util.StaticFieldGetter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.util.TimeZoneUtil_IW;
import com.liferay.portal.kernel.util.UnicodeFormatter_IW;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.permission.AccountPermissionUtil;
import com.liferay.portal.service.permission.CommonPermissionUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PasswordPolicyPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.theme.NavItem;
import com.liferay.portal.theme.RequestVars;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SessionClicks_IW;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.velocity.ServiceLocator;
import com.liferay.portal.velocity.UtilLocator;
import com.liferay.portal.velocity.VelocityPortletPreferences;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoRowLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import freemarker.ext.beans.BeansWrapper;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
public class FreeMarkerVariablesImpl implements FreeMarkerVariables {

	public void insertHelperUtilities(
		FreeMarkerContext freeMarkerContext, String[] restrictedVariables) {

		// Array util

		freeMarkerContext.put("arrayUtil", ArrayUtil_IW.getInstance());

		// Browser sniffer

		try {
			freeMarkerContext.put(
				"browserSniffer", BrowserSnifferUtil.getBrowserSniffer());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Date format

		try {
			freeMarkerContext.put(
				"dateFormatFactory",
				FastDateFormatFactoryUtil.getFastDateFormatFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Date util

		freeMarkerContext.put("dateUtil", DateUtil_IW.getInstance());

		// Enum util

		freeMarkerContext.put(
			"enumUtil", BeansWrapper.getDefaultInstance().getEnumModels());

		try {

			// Service locator

			ServiceLocator serviceLocator = ServiceLocator.getInstance();

			insertHelperUtility(
				freeMarkerContext, restrictedVariables, "serviceLocator",
				serviceLocator);

			// Expando column service

			try {
				freeMarkerContext.put(
					"expandoColumnLocalService",
					serviceLocator.findService(
						ExpandoColumnLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}

			// Expando row service

			try {
				freeMarkerContext.put(
					"expandoRowLocalService",
					serviceLocator.findService(
						ExpandoRowLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}

			// Expando table service

			try {
				freeMarkerContext.put(
					"expandoTableLocalService",
					serviceLocator.findService(
						ExpandoTableLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}

			// Expando value service

			try {
				freeMarkerContext.put(
					"expandoValueLocalService",
					serviceLocator.findService(
						ExpandoValueLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Getter util

		freeMarkerContext.put("getterUtil", GetterUtil_IW.getInstance());

		// Html util

		try {
			freeMarkerContext.put("htmlUtil", HtmlUtil.getHtml());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Http util

		try {
			freeMarkerContext.put("httpUtil", HttpUtil.getHttp());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Journal content util

		try {
			freeMarkerContext.put(
				"journalContentUtil", JournalContentUtil.getJournalContent());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// JSON factory util

		try {
			freeMarkerContext.put(
				"jsonFactoryUtil", JSONFactoryUtil.getJSONFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Language util

		try {
			freeMarkerContext.put("languageUtil", LanguageUtil.getLanguage());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			freeMarkerContext.put(
				"unicodeLanguageUtil",
				UnicodeLanguageUtil.getUnicodeLanguage());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Locale util

		try {
			freeMarkerContext.put("localeUtil", LocaleUtil.getInstance());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Object util

		freeMarkerContext.put("objectUtil", new LiferayObjectConstructor());

		// Param util

		freeMarkerContext.put("paramUtil", ParamUtil_IW.getInstance());

		// Portal util

		try {
			insertHelperUtility(
				freeMarkerContext, restrictedVariables, "portalUtil",
				PortalUtil.getPortal());
			insertHelperUtility(
				freeMarkerContext, restrictedVariables, "portal",
				PortalUtil.getPortal());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Prefs props util

		try {
			insertHelperUtility(
				freeMarkerContext, restrictedVariables, "prefsPropsUtil",
				PrefsPropsUtil.getPrefsProps());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Props util

		try {
			insertHelperUtility(
				freeMarkerContext, restrictedVariables, "propsUtil",
				PropsUtil.getProps());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Portlet URL factory

		try {
			freeMarkerContext.put(
				"portletURLFactory",
				PortletURLFactoryUtil.getPortletURLFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Portlet preferences

		insertHelperUtility(
			freeMarkerContext, restrictedVariables,
			"freeMarkerPortletPreferences", new VelocityPortletPreferences());

		// Randomizer

		freeMarkerContext.put(
			"randomizer", Randomizer_IW.getInstance().getWrappedInstance());

		try {
			// Util locator

			UtilLocator utilLocator = UtilLocator.getInstance();

			insertHelperUtility(
				freeMarkerContext, restrictedVariables, "utilLocator",
				utilLocator);

			// SAX reader util

			try {
				freeMarkerContext.put(
					"saxReaderUtil",
					utilLocator.findUtil(SAXReader.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Session clicks

		try {
			insertHelperUtility(
				freeMarkerContext, restrictedVariables, "sessionClicks",
				SessionClicks_IW.getInstance());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Static field getter

		freeMarkerContext.put(
			"staticFieldGetter", StaticFieldGetter.getInstance());

		// Static class util

		freeMarkerContext.put(
			"staticUtil", BeansWrapper.getDefaultInstance().getStaticModels());

		// String util

		freeMarkerContext.put("stringUtil", StringUtil_IW.getInstance());

		// Time zone util

		freeMarkerContext.put("timeZoneUtil", TimeZoneUtil_IW.getInstance());

		// Unicode formatter

		freeMarkerContext.put(
			"unicodeFormatter", UnicodeFormatter_IW.getInstance());

		// Validator

		freeMarkerContext.put("validator", Validator_IW.getInstance());

		// Web server servlet token

		try {
			freeMarkerContext.put(
				"webServerToken",
				WebServerServletTokenUtil.getWebServerServletToken());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Permissions

		try {
			freeMarkerContext.put(
				"accountPermission",
				AccountPermissionUtil.getAccountPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			freeMarkerContext.put(
				"commonPermission", CommonPermissionUtil.getCommonPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			freeMarkerContext.put(
				"groupPermission", GroupPermissionUtil.getGroupPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			freeMarkerContext.put(
				"layoutPermission", LayoutPermissionUtil.getLayoutPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}
		try {
			freeMarkerContext.put(
				"organizationPermission",
				OrganizationPermissionUtil.getOrganizationPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			freeMarkerContext.put(
				"passwordPolicyPermission",
				PasswordPolicyPermissionUtil.getPasswordPolicyPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}
		try {
			freeMarkerContext.put(
				"portalPermission", PortalPermissionUtil.getPortalPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}
		try {
			freeMarkerContext.put(
				"portletPermission",
				PortletPermissionUtil.getPortletPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}
		try {
			freeMarkerContext.put(
				"rolePermission", RolePermissionUtil.getRolePermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			freeMarkerContext.put(
				"userGroupPermission",
				UserGroupPermissionUtil.getUserGroupPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			freeMarkerContext.put(
				"userPermission", UserPermissionUtil.getUserPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Deprecated

		try {
			freeMarkerContext.put(
				"imageToken",
				WebServerServletTokenUtil.getWebServerServletToken());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}
	}

	public void insertVariables(
			FreeMarkerContext freeMarkerContext, HttpServletRequest request)
		throws Exception {

		// Request

		freeMarkerContext.put("request", request);

		// Portlet config

		PortletConfigImpl portletConfigImpl =
			(PortletConfigImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfigImpl != null) {
			freeMarkerContext.put("portletConfig", portletConfigImpl);
		}

		// Render request

		final PortletRequest portletRequest =
			(PortletRequest)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			if (portletRequest instanceof RenderRequest) {
				freeMarkerContext.put("renderRequest", portletRequest);
			}
		}

		// Render response

		final PortletResponse portletResponse =
			(PortletResponse)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse != null) {
			if (portletResponse instanceof RenderResponse) {
				freeMarkerContext.put("renderResponse", portletResponse);
			}
		}

		// XML request

		if ((portletRequest != null) && (portletResponse != null)) {
			freeMarkerContext.put(
				"xmlRequest",
				new Object() {

					@Override
					public String toString() {
						return PortletRequestUtil.toXML(
							portletRequest, portletResponse);
					}

				}
			);
		}

		// Theme display

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			Theme theme = themeDisplay.getTheme();

			Layout layout = themeDisplay.getLayout();
			List<Layout> layouts = themeDisplay.getLayouts();

			freeMarkerContext.put("themeDisplay", themeDisplay);
			freeMarkerContext.put("company", themeDisplay.getCompany());
			freeMarkerContext.put("user", themeDisplay.getUser());
			freeMarkerContext.put("realUser", themeDisplay.getRealUser());
			freeMarkerContext.put("layout", layout);
			freeMarkerContext.put("layouts", layouts);
			freeMarkerContext.put(
				"plid", String.valueOf(themeDisplay.getPlid()));
			freeMarkerContext.put(
				"layoutTypePortlet", themeDisplay.getLayoutTypePortlet());
			freeMarkerContext.put(
				"scopeGroupId", new Long(themeDisplay.getScopeGroupId()));
			freeMarkerContext.put(
				"permissionChecker", themeDisplay.getPermissionChecker());
			freeMarkerContext.put("locale", themeDisplay.getLocale());
			freeMarkerContext.put("timeZone", themeDisplay.getTimeZone());
			freeMarkerContext.put("theme", theme);
			freeMarkerContext.put("colorScheme", themeDisplay.getColorScheme());
			freeMarkerContext.put(
				"portletDisplay", themeDisplay.getPortletDisplay());

			// Navigation items

			if (layout != null) {
				RequestVars requestVars = new RequestVars(
					request, themeDisplay, layout.getAncestorPlid(),
					layout.getAncestorLayoutId());

				List<NavItem> navItems = NavItem.fromLayouts(
					requestVars, layouts);

				freeMarkerContext.put("navItems", navItems);
			}

			// Full css and templates path

			String servletContextName = GetterUtil.getString(
				theme.getServletContextName());

			freeMarkerContext.put(
				"fullCssPath",
				StringPool.SLASH + servletContextName +
					theme.getFreeMarkerTemplateLoader() + theme.getCssPath());

			freeMarkerContext.put(
				"fullTemplatesPath",
				StringPool.SLASH + servletContextName +
					theme.getFreeMarkerTemplateLoader() +
						theme.getTemplatesPath());

			// Init

			freeMarkerContext.put(
				"init",
				StringPool.SLASH + themeDisplay.getPathContext() +
					FreeMarkerTemplateLoader.SERVLET_SEPARATOR +
						"/html/themes/_unstyled/templates/init.ftl");

			// Deprecated

			freeMarkerContext.put(
				"portletGroupId", new Long(themeDisplay.getScopeGroupId()));
		}

		// Tiles attributes

		insertTilesVariables(freeMarkerContext, request);

		// Page title and subtitle

		if (request.getAttribute(WebKeys.PAGE_TITLE) != null) {
			freeMarkerContext.put(
				"pageTitle", request.getAttribute(WebKeys.PAGE_TITLE));
		}

		if (request.getAttribute(WebKeys.PAGE_SUBTITLE) != null) {
			freeMarkerContext.put(
				"pageSubtitle", request.getAttribute(WebKeys.PAGE_SUBTITLE));
		}

		// Insert custom ftl variables

		Map<String, Object> ftlVariables =
			(Map<String, Object>)request.getAttribute(WebKeys.FTL_VARIABLES);

		if (ftlVariables != null) {
			for (Map.Entry<String, Object> entry : ftlVariables.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				if (Validator.isNotNull(key)) {
					freeMarkerContext.put(key, value);
				}
			}
		}
	}

	protected void insertHelperUtility(
		FreeMarkerContext freeMarkerContext, String[] restrictedVariables,
		String key, Object value) {

		if (!ArrayUtil.contains(restrictedVariables, key)) {
			freeMarkerContext.put(key, value);
		}
	}

	protected void insertTilesVariables(
		FreeMarkerContext freeMarkerContext, HttpServletRequest request) {

		ComponentContext componentContext =
			(ComponentContext)request.getAttribute(
				ComponentConstants.COMPONENT_CONTEXT);

		if (componentContext == null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String tilesTitle = (String)componentContext.getAttribute("title");

		themeDisplay.setTilesTitle(tilesTitle);

		if (tilesTitle != null) {
			freeMarkerContext.put("tilesTitle", tilesTitle);
		}

		String tilesContent = (String)componentContext.getAttribute("content");

		themeDisplay.setTilesContent(tilesContent);

		if (tilesContent != null) {
			freeMarkerContext.put("tilesContent", tilesContent);
		}

		boolean tilesSelectable = GetterUtil.getBoolean(
			(String)componentContext.getAttribute("selectable"));

		themeDisplay.setTilesSelectable(tilesSelectable);

		freeMarkerContext.put("tilesSelectable", tilesSelectable);
	}

	private static Log _log = LogFactoryUtil.getLog(
		FreeMarkerVariablesImpl.class);

}
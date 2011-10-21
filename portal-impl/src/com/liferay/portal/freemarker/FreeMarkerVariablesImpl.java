/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
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
import com.liferay.portal.util.PrefsPropsUtil_IW;
import com.liferay.portal.util.PropsUtil_IW;
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

import freemarker.template.utility.ObjectConstructor;

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

		freeMarkerContext.put(
			"browserSniffer", BrowserSnifferUtil.getBrowserSniffer());

		// Date format

		freeMarkerContext.put(
			"dateFormatFactory",
			FastDateFormatFactoryUtil.getFastDateFormatFactory());

		// Date util

		freeMarkerContext.put("dateUtil", DateUtil_IW.getInstance());

		// Enum util

		freeMarkerContext.put(
			"enumUtil", BeansWrapper.getDefaultInstance().getEnumModels());

		// Expando column service

		ServiceLocator serviceLocator = ServiceLocator.getInstance();

		freeMarkerContext.put(
			"expandoColumnLocalService",
			serviceLocator.findService(
				ExpandoColumnLocalService.class.getName()));

		// Expando row service

		freeMarkerContext.put(
			"expandoRowLocalService",
			serviceLocator.findService(ExpandoRowLocalService.class.getName()));

		// Expando table service

		freeMarkerContext.put(
			"expandoTableLocalService",
			serviceLocator.findService(
				ExpandoTableLocalService.class.getName()));

		// Expando value service

		freeMarkerContext.put(
			"expandoValueLocalService",
			serviceLocator.findService(
				ExpandoValueLocalService.class.getName()));

		// Getter util

		freeMarkerContext.put("getterUtil", GetterUtil_IW.getInstance());

		// Html util

		freeMarkerContext.put("htmlUtil", HtmlUtil.getHtml());

		// Http util

		freeMarkerContext.put("httpUtil", HttpUtil.getHttp());

		// Journal content util

		freeMarkerContext.put(
			"journalContentUtil", JournalContentUtil.getJournalContent());

		// Language util

		freeMarkerContext.put("languageUtil", LanguageUtil.getLanguage());
		freeMarkerContext.put(
			"unicodeLanguageUtil", UnicodeLanguageUtil.getUnicodeLanguage());

		// Locale util

		freeMarkerContext.put("localeUtil", LocaleUtil.getInstance());

		// Object util

		freeMarkerContext.put("objectUtil", new ObjectConstructor());

		// Param util

		freeMarkerContext.put("paramUtil", ParamUtil_IW.getInstance());

		// Portal util

		insertHelperUtility(
			freeMarkerContext, restrictedVariables, "portalUtil",
			PortalUtil.getPortal());
		insertHelperUtility(
			freeMarkerContext, restrictedVariables, "portal",
			PortalUtil.getPortal());

		// Prefs props util

		insertHelperUtility(
			freeMarkerContext, restrictedVariables, "prefsPropsUtil",
			PrefsPropsUtil_IW.getInstance());

		// Props util

		insertHelperUtility(
			freeMarkerContext, restrictedVariables, "propsUtil",
			PropsUtil_IW.getInstance());

		// Portlet URL factory

		freeMarkerContext.put(
			"portletURLFactory", PortletURLFactoryUtil.getPortletURLFactory());

		// Portlet preferences

		insertHelperUtility(
			freeMarkerContext, restrictedVariables,
			"freeMarkerPortletPreferences", new VelocityPortletPreferences());

		// Randomizer

		freeMarkerContext.put(
			"randomizer", Randomizer_IW.getInstance().getWrappedInstance());

		// SAX reader util

		UtilLocator utilLocator = UtilLocator.getInstance();

		freeMarkerContext.put(
			"saxReaderUtil",
			utilLocator.findUtil(SAXReader.class.getName()));

		// Service locator

		insertHelperUtility(
			freeMarkerContext, restrictedVariables, "serviceLocator",
			serviceLocator);

		// Session clicks

		insertHelperUtility(
			freeMarkerContext, restrictedVariables, "sessionClicks",
			SessionClicks_IW.getInstance());

		// Static field getter

		freeMarkerContext.put(
			"staticFieldGetter", StaticFieldGetter.getInstance());

		// Static class util

		freeMarkerContext.put(
			"staticUtil",
			BeansWrapper.getDefaultInstance().getStaticModels());

		// String util

		freeMarkerContext.put("stringUtil", StringUtil_IW.getInstance());

		// Time zone util

		freeMarkerContext.put("timeZoneUtil", TimeZoneUtil_IW.getInstance());

		// Util locator

		insertHelperUtility(
			freeMarkerContext, restrictedVariables, "utilLocator", utilLocator);

		// Unicode formatter

		freeMarkerContext.put(
			"unicodeFormatter", UnicodeFormatter_IW.getInstance());

		// Validator

		freeMarkerContext.put("validator", Validator_IW.getInstance());

		// Web server servlet token

		freeMarkerContext.put(
			"webServerToken",
			WebServerServletTokenUtil.getWebServerServletToken());

		// Permissions

		freeMarkerContext.put(
			"accountPermission", AccountPermissionUtil.getAccountPermission());
		freeMarkerContext.put(
			"commonPermission", CommonPermissionUtil.getCommonPermission());
		freeMarkerContext.put(
			"groupPermission", GroupPermissionUtil.getGroupPermission());
		freeMarkerContext.put(
			"layoutPermission", LayoutPermissionUtil.getLayoutPermission());
		freeMarkerContext.put(
			"organizationPermission",
			OrganizationPermissionUtil.getOrganizationPermission());
		freeMarkerContext.put(
			"passwordPolicyPermission",
			PasswordPolicyPermissionUtil.getPasswordPolicyPermission());
		freeMarkerContext.put(
			"portalPermission", PortalPermissionUtil.getPortalPermission());
		freeMarkerContext.put(
			"portletPermission", PortletPermissionUtil.getPortletPermission());
		freeMarkerContext.put(
			"rolePermission", RolePermissionUtil.getRolePermission());
		freeMarkerContext.put(
			"userGroupPermission",
			UserGroupPermissionUtil.getUserGroupPermission());
		freeMarkerContext.put(
			"userPermission", UserPermissionUtil.getUserPermission());

		// Deprecated

		freeMarkerContext.put(
			"imageToken", WebServerServletTokenUtil.getWebServerServletToken());
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

}
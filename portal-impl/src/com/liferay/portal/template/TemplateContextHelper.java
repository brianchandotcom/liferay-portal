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

package com.liferay.portal.template;

import com.liferay.portal.kernel.audit.AuditMessageFactoryUtil;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.templateparser.TemplateContext;
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
import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.util.TimeZoneUtil_IW;
import com.liferay.portal.kernel.util.UnicodeFormatter_IW;
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
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoRowLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;

/**
 * @author Tina Tian
 */
public class TemplateContextHelper {

	public Map<String, Object> getHelperUtilities() {
		Map<String, Object> variables = new HashMap<String, Object>();

		// Array util

		variables.put("arrayUtil", ArrayUtil_IW.getInstance());

		// Audit message factory

		variables.put(
			"auditMessageFactoryUtil",
			AuditMessageFactoryUtil.getAuditMessageFactory());

		// Audit router util

		variables.put("auditRouterUtil", AuditRouterUtil.getAuditRouter());

		// Browser sniffer

		variables.put("browserSniffer", BrowserSnifferUtil.getBrowserSniffer());

		// Date format

		variables.put(
			"dateFormatFactory",
			FastDateFormatFactoryUtil.getFastDateFormatFactory());

		// Date util

		variables.put("dateUtil", DateUtil_IW.getInstance());

		// Expando column service

		ServiceLocator serviceLocator = ServiceLocator.getInstance();

		variables.put(
			"expandoColumnLocalService",
			serviceLocator.findService(
				ExpandoColumnLocalService.class.getName()));

		// Expando row service

		variables.put(
			"expandoRowLocalService",
			serviceLocator.findService(ExpandoRowLocalService.class.getName()));

		// Expando table service

		variables.put(
			"expandoTableLocalService",
			serviceLocator.findService(
				ExpandoTableLocalService.class.getName()));

		// Expando value service

		variables.put(
			"expandoValueLocalService",
			serviceLocator.findService(
				ExpandoValueLocalService.class.getName()));

		// Getter util

		variables.put("getterUtil", GetterUtil_IW.getInstance());

		// Html util

		variables.put("htmlUtil", HtmlUtil.getHtml());

		// Http util

		variables.put("httpUtil", HttpUtil.getHttp());

		// Journal content util

		variables.put(
			"journalContentUtil", JournalContentUtil.getJournalContent());

		// JSON factory util

		variables.put("jsonFactoryUtil", JSONFactoryUtil.getJSONFactory());

		// Language util

		variables.put("languageUtil", LanguageUtil.getLanguage());

		variables.put(
			"unicodeLanguageUtil", UnicodeLanguageUtil.getUnicodeLanguage());

		// Locale util

		variables.put("localeUtil", LocaleUtil.getInstance());

		// Param util

		variables.put("paramUtil", ParamUtil_IW.getInstance());

		// Portal util

		variables.put("portalUtil", PortalUtil.getPortal());

		variables.put("portal", PortalUtil.getPortal());

		// Prefs props util

		variables.put("prefsPropsUtil", PrefsPropsUtil_IW.getInstance());

		// Props util

		variables.put("propsUtil", PropsUtil_IW.getInstance());

		// Portlet URL factory

		variables.put(
			"portletURLFactory", PortletURLFactoryUtil.getPortletURLFactory());

		// Randomizer

		variables.put(
			"randomizer", Randomizer_IW.getInstance().getWrappedInstance());

		// SAX reader util

		UtilLocator utilLocator = UtilLocator.getInstance();

		variables.put(
			"saxReaderUtil", utilLocator.findUtil(SAXReader.class.getName()));

		// Service locator

		variables.put("serviceLocator", serviceLocator);

		// Session clicks

		variables.put("sessionClicks", SessionClicks_IW.getInstance());

		// Static field getter

		variables.put("staticFieldGetter", StaticFieldGetter.getInstance());

		// String util

		variables.put("stringUtil", StringUtil_IW.getInstance());

		// Time zone util

		variables.put("timeZoneUtil", TimeZoneUtil_IW.getInstance());

		// Util locator

		variables.put("utilLocator", utilLocator);

		// Unicode formatter

		variables.put("unicodeFormatter", UnicodeFormatter_IW.getInstance());

		// Validator

		variables.put("validator", Validator_IW.getInstance());

		// Web server servlet token

		variables.put(
			"webServerToken",
			WebServerServletTokenUtil.getWebServerServletToken());

		// Permissions

		variables.put(
			"accountPermission", AccountPermissionUtil.getAccountPermission());
		variables.put(
			"commonPermission", CommonPermissionUtil.getCommonPermission());
		variables.put(
			"groupPermission", GroupPermissionUtil.getGroupPermission());
		variables.put(
			"layoutPermission", LayoutPermissionUtil.getLayoutPermission());
		variables.put(
			"organizationPermission",
			OrganizationPermissionUtil.getOrganizationPermission());
		variables.put(
			"passwordPolicyPermission",
			PasswordPolicyPermissionUtil.getPasswordPolicyPermission());
		variables.put(
			"portalPermission", PortalPermissionUtil.getPortalPermission());
		variables.put(
			"portletPermission", PortletPermissionUtil.getPortletPermission());
		variables.put("rolePermission", RolePermissionUtil.getRolePermission());
		variables.put(
			"userGroupPermission",
			UserGroupPermissionUtil.getUserGroupPermission());
		variables.put("userPermission", UserPermissionUtil.getUserPermission());

		// Deprecated

		variables.put(
			"dateFormats",
			FastDateFormatFactoryUtil.getFastDateFormatFactory());
		variables.put(
			"imageToken", WebServerServletTokenUtil.getWebServerServletToken());
		variables.put(
			"locationPermission",
			OrganizationPermissionUtil.getOrganizationPermission());

		return variables;
	}

	public Map<String, Object> getRestrictedHelperUtilities() {
		Map<String, Object> helperUtilities = getHelperUtilities();

		Set<String> restrictedVariables = getRestrictedVariables();

		for (String restrictedVariable : restrictedVariables) {
			helperUtilities.remove(restrictedVariable);
		}

		return helperUtilities;
	}

	public Set<String> getRestrictedVariables() {
		return Collections.emptySet();
	}

	public void prepare(
			TemplateContext templateContext, HttpServletRequest request)
		throws TemplateException {

		// Request

		templateContext.put("request", request);

		// Portlet config

		PortletConfigImpl portletConfigImpl =
			(PortletConfigImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfigImpl != null) {
			templateContext.put("portletConfig", portletConfigImpl);
		}

		// Render request

		final PortletRequest portletRequest =
			(PortletRequest)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			if (portletRequest instanceof RenderRequest) {
				templateContext.put("renderRequest", portletRequest);
			}
		}

		// Render response

		final PortletResponse portletResponse =
			(PortletResponse)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse != null) {
			if (portletResponse instanceof RenderResponse) {
				templateContext.put("renderResponse", portletResponse);
			}
		}

		// XML request

		if ((portletRequest != null) && (portletResponse != null)) {
			templateContext.put(
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
			Layout layout = themeDisplay.getLayout();
			List<Layout> layouts = themeDisplay.getLayouts();

			templateContext.put("themeDisplay", themeDisplay);
			templateContext.put("company", themeDisplay.getCompany());
			templateContext.put("user", themeDisplay.getUser());
			templateContext.put("realUser", themeDisplay.getRealUser());
			templateContext.put("layout", layout);
			templateContext.put("layouts", layouts);
			templateContext.put("plid", String.valueOf(themeDisplay.getPlid()));
			templateContext.put(
				"layoutTypePortlet", themeDisplay.getLayoutTypePortlet());
			templateContext.put(
				"scopeGroupId", new Long(themeDisplay.getScopeGroupId()));
			templateContext.put(
				"permissionChecker", themeDisplay.getPermissionChecker());
			templateContext.put("locale", themeDisplay.getLocale());
			templateContext.put("timeZone", themeDisplay.getTimeZone());
			templateContext.put("colorScheme", themeDisplay.getColorScheme());
			templateContext.put(
				"portletDisplay", themeDisplay.getPortletDisplay());

			// Navigation items

			if (layout != null) {
				RequestVars requestVars = null;

				try {
					requestVars = new RequestVars(
						request, themeDisplay, layout.getAncestorPlid(),
						layout.getAncestorLayoutId());
				}
				catch(Exception e) {
					throw new TemplateException(e);
				}

				List<NavItem> navItems = NavItem.fromLayouts(
					requestVars, layouts);

				templateContext.put("navItems", navItems);
			}

			// Deprecated

			templateContext.put(
				"portletGroupId", new Long(themeDisplay.getScopeGroupId()));
		}

		// Theme

		Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

		if ((theme == null) && (themeDisplay != null)) {
			theme = themeDisplay.getTheme();
		}

		if (theme != null) {
			templateContext.put("theme", theme);
		}

		// Tiles attributes

		prepareTiles(templateContext, request);

		// Page title and subtitle

		templateContext.put(
			"pageTitle", request.getAttribute(WebKeys.PAGE_TITLE));
		templateContext.put(
			"pageSubtitle", request.getAttribute(WebKeys.PAGE_SUBTITLE));
	}

	protected void prepareTiles(
		TemplateContext templateContext, HttpServletRequest request) {

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

		templateContext.put("tilesTitle", tilesTitle);

		String tilesContent = (String)componentContext.getAttribute("content");

		themeDisplay.setTilesContent(tilesContent);

		templateContext.put("tilesContent", tilesContent);

		boolean tilesSelectable = GetterUtil.getBoolean(
			(String)componentContext.getAttribute("selectable"));

		themeDisplay.setTilesSelectable(tilesSelectable);

		templateContext.put("tilesSelectable", tilesSelectable);
	}

}
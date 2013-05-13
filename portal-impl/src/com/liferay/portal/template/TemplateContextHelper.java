/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletModeFactory_IW;
import com.liferay.portal.kernel.portlet.WindowStateFactory_IW;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.ArrayUtil_IW;
import com.liferay.portal.kernel.util.DateUtil_IW;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GetterUtil_IW;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListMergeable;
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
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.OrganizationLocalService;
import com.liferay.portal.service.OrganizationService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
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
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SessionClicks_IW;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoRowLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.taglib.util.VelocityTaglibImpl;
import com.liferay.util.portlet.PortletRequestUtil;

import java.lang.reflect.Method;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;

/**
 * @author Tina Tian
 * @author Jorge Ferrer
 */
public class TemplateContextHelper {

	public static Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classNameId, long classPK, String language, Locale locale)
		throws Exception {

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

		if (templateHandler == null) {
			return Collections.emptyMap();
		}

		Map<String, TemplateVariableGroup> templateVariableGroups =
			templateHandler.getTemplateVariableGroups(
				classPK, language, locale);

		TemplateVariableGroup portalServicesTemplateVariableGroup =
			new TemplateVariableGroup("portal-services");

		portalServicesTemplateVariableGroup.setAutocompleteEnabled(false);

		portalServicesTemplateVariableGroup.addServiceLocatorVariables(
			GroupLocalService.class, GroupService.class,
			LayoutLocalService.class, LayoutService.class,
			OrganizationLocalService.class, OrganizationService.class,
			UserLocalService.class, UserService.class);

		templateVariableGroups.put(
			portalServicesTemplateVariableGroup.getLabel(),
			portalServicesTemplateVariableGroup);

		return templateVariableGroups;
	}

	public Map<String, Object> getHelperUtilities(
		TemplateContextType templateContextType) {

		TemplateControlContext templateControlContext =
			getTemplateControlContext();

		AccessControlContext accessControlContext =
			templateControlContext.getAccessControlContext();
		ClassLoader classLoader = templateControlContext.getClassLoader();

		if (accessControlContext == null) {
			return doGetHelperUtilities(classLoader, templateContextType);
		}

		return AccessController.doPrivileged(
			new DoGetHelperUtilitiesPrivilegedAction(
				classLoader, templateContextType),
			accessControlContext);
	}

	public Set<String> getRestrictedVariables() {
		return Collections.emptySet();
	}

	public TemplateControlContext getTemplateControlContext() {
		return _pacl.getTemplateControlContext();
	}

	public void prepare(Template template, HttpServletRequest request) {

		// Request

		template.put("request", request);

		// Portlet config

		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfig != null) {
			template.put("portletConfig", portletConfig);
		}

		// Render request

		final PortletRequest portletRequest =
			(PortletRequest)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			if (portletRequest instanceof RenderRequest) {
				template.put("renderRequest", portletRequest);
			}
		}

		// Render response

		final PortletResponse portletResponse =
			(PortletResponse)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse != null) {
			if (portletResponse instanceof RenderResponse) {
				template.put("renderResponse", portletResponse);
			}
		}

		// XML request

		if ((portletRequest != null) && (portletResponse != null)) {
			template.put(
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

			template.put("themeDisplay", themeDisplay);
			template.put("company", themeDisplay.getCompany());
			template.put("user", themeDisplay.getUser());
			template.put("realUser", themeDisplay.getRealUser());
			template.put("layout", layout);
			template.put("layouts", layouts);
			template.put("plid", String.valueOf(themeDisplay.getPlid()));
			template.put(
				"layoutTypePortlet", themeDisplay.getLayoutTypePortlet());
			template.put(
				"scopeGroupId", new Long(themeDisplay.getScopeGroupId()));
			template.put(
				"permissionChecker", themeDisplay.getPermissionChecker());
			template.put("locale", themeDisplay.getLocale());
			template.put("timeZone", themeDisplay.getTimeZone());
			template.put("colorScheme", themeDisplay.getColorScheme());
			template.put("portletDisplay", themeDisplay.getPortletDisplay());

			// Navigation items

			if (layout != null) {
				List<NavItem> navItems = NavItem.fromLayouts(
					request, layouts, template);

				template.put("navItems", navItems);
			}

			// Deprecated

			template.put(
				"portletGroupId", new Long(themeDisplay.getScopeGroupId()));
		}

		// Theme

		Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

		if ((theme == null) && (themeDisplay != null)) {
			theme = themeDisplay.getTheme();
		}

		if (theme != null) {
			template.put("theme", theme);
		}

		// Tiles attributes

		prepareTiles(template, request);

		// Page title and subtitle

		ListMergeable<String> pageTitleListMergeable =
			(ListMergeable<String>)request.getAttribute(WebKeys.PAGE_TITLE);

		if (pageTitleListMergeable != null) {
			String pageTitle = pageTitleListMergeable.mergeToString(
				StringPool.SPACE);

			template.put("pageTitle", pageTitle);
		}

		ListMergeable<String> pageSubtitleListMergeable =
			(ListMergeable<String>)request.getAttribute(WebKeys.PAGE_SUBTITLE);

		if (pageSubtitleListMergeable != null) {
			String pageSubtitle = pageSubtitleListMergeable.mergeToString(
				StringPool.SPACE);

			template.put("pageSubtitle", pageSubtitle);
		}
	}

	public void removeAllHelperUtilities() {
		_helperUtilitiesMaps.clear();
	}

	public void removeHelperUtilities(ClassLoader classLoader) {
		_helperUtilitiesMaps.remove(classLoader);
	}

	protected Map<String, Object> doGetHelperUtilities(
		ClassLoader classLoader, TemplateContextType templateContextType) {

		HelperUtilitiesMap helperUtilitiesMap = _helperUtilitiesMaps.get(
			classLoader);

		if (helperUtilitiesMap == null) {
			helperUtilitiesMap = new HelperUtilitiesMap(
				TemplateContextType.class);

			_helperUtilitiesMaps.put(classLoader, helperUtilitiesMap);
		}

		Map<String, Object> helperUtilities = helperUtilitiesMap.get(
			templateContextType);

		if (helperUtilities != null) {
			return helperUtilities;
		}

		helperUtilities = new HashMap<String, Object>();

		populateCommonHelperUtilities(helperUtilities);
		populateExtraHelperUtilities(helperUtilities);

		if (templateContextType.equals(TemplateContextType.RESTRICTED)) {
			Set<String> restrictedVariables = getRestrictedVariables();

			for (String restrictedVariable : restrictedVariables) {
				helperUtilities.remove(restrictedVariable);
			}
		}

		helperUtilities = Collections.unmodifiableMap(helperUtilities);

		helperUtilitiesMap.put(templateContextType, helperUtilities);

		return helperUtilities;
	}

	protected void populateCommonHelperUtilities(
		Map<String, Object> variables) {

		// Array util

		variables.put("arrayUtil", ArrayUtil_IW.getInstance());

		// Audit message factory

		try {
			variables.put(
				"auditMessageFactoryUtil",
				AuditMessageFactoryUtil.getAuditMessageFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Audit router util

		try {
			variables.put("auditRouterUtil", AuditRouterUtil.getAuditRouter());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Browser sniffer

		try {
			variables.put(
				"browserSniffer", BrowserSnifferUtil.getBrowserSniffer());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Date format

		try {
			variables.put(
				"dateFormatFactory",
				FastDateFormatFactoryUtil.getFastDateFormatFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Date util

		variables.put("dateUtil", DateUtil_IW.getInstance());

		// Document library util

		try {
			variables.put("dlUtil", DLUtil.getDL());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Expando column service

		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();

			// Service locator

			variables.put("serviceLocator", serviceLocator);

			try {
				variables.put(
					"expandoColumnLocalService",
					serviceLocator.findService(
						ExpandoColumnLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}

			// Expando row service

			try {
				variables.put(
					"expandoRowLocalService",
					serviceLocator.findService(
						ExpandoRowLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}

			// Expando table service

			try {
				variables.put(
					"expandoTableLocalService",
					serviceLocator.findService(
						ExpandoTableLocalService.class.getName()));
			}
			catch (SecurityException se) {
				_log.error(se, se);
			}

			// Expando value service

			try {
				variables.put(
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

		variables.put("getterUtil", GetterUtil_IW.getInstance());

		// Html util

		try {
			variables.put("htmlUtil", HtmlUtil.getHtml());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Http util

		try {
			variables.put("httpUtil", HttpUtil.getHttp());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Journal content util

		try {
			variables.put(
				"journalContentUtil", JournalContentUtil.getJournalContent());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// JSON factory util

		try {
			variables.put("jsonFactoryUtil", JSONFactoryUtil.getJSONFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Language util

		try {
			variables.put("languageUtil", LanguageUtil.getLanguage());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"unicodeLanguageUtil",
				UnicodeLanguageUtil.getUnicodeLanguage());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Locale util

		try {
			variables.put("localeUtil", LocaleUtil.getInstance());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Param util

		variables.put("paramUtil", ParamUtil_IW.getInstance());

		// Portal util

		try {
			variables.put("portalUtil", PortalUtil.getPortal());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put("portal", PortalUtil.getPortal());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Prefs props util

		try {
			variables.put("prefsPropsUtil", PrefsPropsUtil.getPrefsProps());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Props util

		try {
			variables.put("propsUtil", PropsUtil.getProps());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Portlet mode factory

		variables.put(
			"portletModeFactory", PortletModeFactory_IW.getInstance());

		// Portlet URL factory

		try {
			variables.put(
				"portletURLFactory",
				PortletURLFactoryUtil.getPortletURLFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Randomizer

		try {
			variables.put(
				"randomizer", Randomizer_IW.getInstance().getWrappedInstance());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			UtilLocator utilLocator = UtilLocator.getInstance();

			// Util locator

			variables.put("utilLocator", utilLocator);

			// SAX reader util

			try {
				variables.put(
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

		variables.put("sessionClicks", SessionClicks_IW.getInstance());

		// Static field getter

		variables.put("staticFieldGetter", StaticFieldGetter.getInstance());

		// String util

		variables.put("stringUtil", StringUtil_IW.getInstance());

		// Time zone util

		variables.put("timeZoneUtil", TimeZoneUtil_IW.getInstance());

		// Unicode formatter

		variables.put("unicodeFormatter", UnicodeFormatter_IW.getInstance());

		// Validator

		variables.put("validator", Validator_IW.getInstance());

		// VelocityTaglib methods

		try {
			Class<?> clazz = VelocityTaglibImpl.class;

			Method method = clazz.getMethod(
				"layoutIcon", new Class[] {Layout.class});

			variables.put("velocityTaglib_layoutIcon", method);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Web server servlet token

		try {
			variables.put(
				"webServerToken",
				WebServerServletTokenUtil.getWebServerServletToken());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Window state factory

		variables.put(
			"windowStateFactory", WindowStateFactory_IW.getInstance());

		// Permissions

		try {
			variables.put(
				"accountPermission",
				AccountPermissionUtil.getAccountPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"commonPermission", CommonPermissionUtil.getCommonPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"groupPermission", GroupPermissionUtil.getGroupPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"layoutPermission", LayoutPermissionUtil.getLayoutPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"organizationPermission",
				OrganizationPermissionUtil.getOrganizationPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"passwordPolicyPermission",
				PasswordPolicyPermissionUtil.getPasswordPolicyPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"portalPermission", PortalPermissionUtil.getPortalPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"portletPermission",
				PortletPermissionUtil.getPortletPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"rolePermission", RolePermissionUtil.getRolePermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"userGroupPermission",
				UserGroupPermissionUtil.getUserGroupPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"userPermission", UserPermissionUtil.getUserPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		// Deprecated

		try {
			variables.put(
				"dateFormats",
				FastDateFormatFactoryUtil.getFastDateFormatFactory());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"imageToken",
				WebServerServletTokenUtil.getWebServerServletToken());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}

		try {
			variables.put(
				"locationPermission",
				OrganizationPermissionUtil.getOrganizationPermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}
	}

	protected void populateExtraHelperUtilities(Map<String, Object> variables) {
	}

	protected void prepareTiles(Template template, HttpServletRequest request) {
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

		template.put("tilesTitle", tilesTitle);

		String tilesContent = (String)componentContext.getAttribute("content");

		themeDisplay.setTilesContent(tilesContent);

		template.put("tilesContent", tilesContent);

		boolean tilesSelectable = GetterUtil.getBoolean(
			(String)componentContext.getAttribute("selectable"));

		themeDisplay.setTilesSelectable(tilesSelectable);

		template.put("tilesSelectable", tilesSelectable);
	}

	private static Log _log = LogFactoryUtil.getLog(
		TemplateContextHelper.class);

	private static PACL _pacl = new NoPACL();

	private Map<ClassLoader, HelperUtilitiesMap>
		_helperUtilitiesMaps = new ConcurrentHashMap
			<ClassLoader, HelperUtilitiesMap>();

	private static class NoPACL implements PACL {

		public TemplateControlContext getTemplateControlContext() {
			ClassLoader contextClassLoader =
				ClassLoaderUtil.getContextClassLoader();

			return new TemplateControlContext(null, contextClassLoader);
		}

	}

	public static interface PACL {

		public TemplateControlContext getTemplateControlContext();

	}

	private class DoGetHelperUtilitiesPrivilegedAction
		implements PrivilegedAction<Map<String, Object>> {

		public DoGetHelperUtilitiesPrivilegedAction(
			ClassLoader classLoader, TemplateContextType templateContextType) {

			_classLoader = classLoader;
			_templateContextType = templateContextType;
		}

		public Map<String, Object> run() {
			return doGetHelperUtilities(_classLoader, _templateContextType);
		}

		private ClassLoader _classLoader;
		private TemplateContextType _templateContextType;

	}

	private class HelperUtilitiesMap
		extends EnumMap<TemplateContextType, Map<String, Object>> {

		public HelperUtilitiesMap(Class<TemplateContextType> keyClazz) {
			super(keyClazz);
		}

	}

}
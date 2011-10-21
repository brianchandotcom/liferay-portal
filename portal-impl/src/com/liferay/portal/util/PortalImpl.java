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

package com.liferay.portal.util;

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperThreadLocal;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.FileTimestampUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.DeterminateKeyGenerator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.TicketConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.LayoutTypePortletImpl;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.TicketLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.service.permission.LayoutSetPrototypePermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.servlet.filters.i18n.I18nFilter;
import com.liferay.portal.servlet.filters.secure.NonceUtil;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.comparator.PortletControlPanelWeightComparator;
import com.liferay.portal.webserver.WebServerServlet;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.DefaultControlPanelEntryFactory;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletContextImpl;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesThreadLocal;
import com.liferay.portlet.PortletPreferencesWrapper;
import com.liferay.portlet.PortletQNameUtil;
import com.liferay.portlet.PortletRequestImpl;
import com.liferay.portlet.PortletResponseImpl;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.portlet.StateAwareResponseImpl;
import com.liferay.portlet.UserAttributes;
import com.liferay.portlet.admin.util.OmniadminUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.expando.ValueDataException;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.journal.asset.JournalArticleAssetRendererFactory;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.login.util.LoginUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.util.FacebookUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.util.Encryptor;
import com.liferay.util.JS;
import com.liferay.util.PwdGenerator;
import com.liferay.util.UniqueList;
import com.liferay.util.servlet.DynamicServletRequest;

import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Jorge Ferrer
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Wesley Gong
 * @author Hugo Huijser
 * @author Juan Fernández
 */
public class PortalImpl implements Portal {

	public PortalImpl() {

		// Computer name

		_computerName = System.getProperty("env.COMPUTERNAME");

		if (Validator.isNull(_computerName)) {
			_computerName = System.getProperty("env.HOST");
		}

		if (Validator.isNull(_computerName)) {
			_computerName = System.getProperty("env.HOSTNAME");
		}

		if (Validator.isNull(_computerName)) {
			try {
				_computerName = InetAddress.getLocalHost().getHostName();
			}
			catch (UnknownHostException uhe) {
			}
		}

		try {
			_computerAddress = InetAddress.getByName(
				_computerName).getHostAddress();
		}
		catch (UnknownHostException uhe) {
		}

		if (Validator.isNull(_computerAddress)) {
			try {
				_computerAddress = InetAddress.getLocalHost().getHostAddress();
			}
			catch (UnknownHostException uhe) {
			}
		}

		// Paths

		_pathProxy = PropsUtil.get(PropsKeys.PORTAL_PROXY_PATH);

		_pathContext = ContextPathUtil.getContextPath(
			PropsUtil.get(PropsKeys.PORTAL_CTX));
		_pathContext = _pathProxy.concat(_pathContext);

		_pathFriendlyURLPrivateGroup =
			_pathContext + _PRIVATE_GROUP_SERVLET_MAPPING;
		_pathFriendlyURLPrivateUser =
			_pathContext + _PRIVATE_USER_SERVLET_MAPPING;
		_pathFriendlyURLPublic = _pathContext + _PUBLIC_GROUP_SERVLET_MAPPING;
		_pathImage = _pathContext + PATH_IMAGE;
		_pathMain = _pathContext + PATH_MAIN;

		// Groups

		String[] customSystemGroups = PropsUtil.getArray(
			PropsKeys.SYSTEM_GROUPS);

		if ((customSystemGroups == null) || (customSystemGroups.length == 0)) {
			_allSystemGroups = GroupConstants.SYSTEM_GROUPS;
		}
		else {
			_allSystemGroups = ArrayUtil.append(
				GroupConstants.SYSTEM_GROUPS, customSystemGroups);
		}

		_sortedSystemGroups = new String[_allSystemGroups.length];

		System.arraycopy(
			_allSystemGroups, 0, _sortedSystemGroups, 0,
			_allSystemGroups.length);

		Arrays.sort(_sortedSystemGroups, new StringComparator());

		// Regular roles

		String[] customSystemRoles = PropsUtil.getArray(PropsKeys.SYSTEM_ROLES);

		if ((customSystemRoles == null) || (customSystemRoles.length == 0)) {
			_allSystemRoles = RoleConstants.SYSTEM_ROLES;
		}
		else {
			_allSystemRoles = ArrayUtil.append(
				RoleConstants.SYSTEM_ROLES, customSystemRoles);
		}

		_sortedSystemRoles = new String[_allSystemRoles.length];

		System.arraycopy(
			_allSystemRoles, 0, _sortedSystemRoles, 0, _allSystemRoles.length);

		Arrays.sort(_sortedSystemRoles, new StringComparator());

		// Organization roles

		String[] customSystemOrganizationRoles = PropsUtil.getArray(
			PropsKeys.SYSTEM_ORGANIZATION_ROLES);

		if ((customSystemOrganizationRoles == null) ||
			(customSystemOrganizationRoles.length == 0)) {

			_allSystemOrganizationRoles =
				RoleConstants.SYSTEM_ORGANIZATION_ROLES;
		}
		else {
			_allSystemOrganizationRoles = ArrayUtil.append(
				RoleConstants.SYSTEM_ORGANIZATION_ROLES,
				customSystemOrganizationRoles);
		}

		_sortedSystemOrganizationRoles =
			new String[_allSystemOrganizationRoles.length];

		System.arraycopy(
			_allSystemOrganizationRoles, 0, _sortedSystemOrganizationRoles, 0,
			_allSystemOrganizationRoles.length);

		Arrays.sort(_sortedSystemOrganizationRoles, new StringComparator());

		// Site roles

		String[] customSystemSiteRoles = PropsUtil.getArray(
			PropsKeys.SYSTEM_SITE_ROLES);

		if ((customSystemSiteRoles == null) ||
			(customSystemSiteRoles.length == 0)) {

			_allSystemSiteRoles = RoleConstants.SYSTEM_SITE_ROLES;
		}
		else {
			_allSystemSiteRoles = ArrayUtil.append(
				RoleConstants.SYSTEM_SITE_ROLES, customSystemSiteRoles);
		}

		_sortedSystemSiteRoles =
			new String[_allSystemSiteRoles.length];

		System.arraycopy(
			_allSystemSiteRoles, 0, _sortedSystemSiteRoles, 0,
			_allSystemSiteRoles.length);

		Arrays.sort(_sortedSystemSiteRoles, new StringComparator());

		// Authentication token ignore actions and tokens

		_authTokenIgnoreActions = SetUtil.fromArray(
			PropsValues.AUTH_TOKEN_IGNORE_ACTIONS);
		_authTokenIgnorePortlets = SetUtil.fromArray(
			PropsValues.AUTH_TOKEN_IGNORE_PORTLETS);

		// Portlet add default resource check white list

		resetPortletAddDefaultResourceCheckWhitelist();
		resetPortletAddDefaultResourceCheckWhitelistActions();

		// Reserved parameter names

		_reservedParams = new HashSet<String>();

		_reservedParams.add("p_auth");
		_reservedParams.add("p_auth_secret");
		_reservedParams.add("p_l_id");
		_reservedParams.add("p_l_reset");
		_reservedParams.add("p_p_auth");
		_reservedParams.add("p_p_id");
		_reservedParams.add("p_p_i_id");
		_reservedParams.add("p_p_lifecycle");
		_reservedParams.add("p_p_url_type");
		_reservedParams.add("p_p_state");
		_reservedParams.add("p_p_state_rcv");
		_reservedParams.add("p_p_mode");
		_reservedParams.add("p_p_resource_id");
		_reservedParams.add("p_p_cacheability");
		_reservedParams.add("p_p_width");
		_reservedParams.add("p_p_col_id");
		_reservedParams.add("p_p_col_pos");
		_reservedParams.add("p_p_col_count");
		_reservedParams.add("p_p_static");
		_reservedParams.add("p_p_isolated");
		_reservedParams.add("p_t_lifecycle");
		_reservedParams.add("p_o_p_id");
		_reservedParams.add("p_f_id");
		_reservedParams.add("p_j_a_id");
		_reservedParams.add("saveLastPath");
		_reservedParams.add("scroll");
	}

	public void addPageDescription(
		String description, HttpServletRequest request) {

		String requestDescription = (String)request.getAttribute(
			WebKeys.PAGE_DESCRIPTION);

		if (requestDescription != null) {
			description = requestDescription + StringPool.SPACE + description;
		}

		request.setAttribute(WebKeys.PAGE_DESCRIPTION, description);
	}

	public void addPageKeywords(String keywords, HttpServletRequest request) {
		List<String> requestKeywords = (List<String>)request.getAttribute(
			WebKeys.PAGE_KEYWORDS);

		if (requestKeywords == null) {
			requestKeywords = new UniqueList<String>();
		}

		String[] keywordsArray = StringUtil.split(keywords);

		for (String keyword : keywordsArray) {
			if (!requestKeywords.contains(keyword.toLowerCase())) {
				requestKeywords.add(keyword.toLowerCase());
			}
		}

		request.setAttribute(WebKeys.PAGE_KEYWORDS, requestKeywords);
	}

	public void addPageSubtitle(String subtitle, HttpServletRequest request) {
		String requestSubtitle = (String)request.getAttribute(
			WebKeys.PAGE_SUBTITLE);

		if (requestSubtitle != null) {
			subtitle = requestSubtitle + StringPool.SPACE + subtitle;
		}

		request.setAttribute(WebKeys.PAGE_SUBTITLE, subtitle);
	}

	public void addPageTitle(String title, HttpServletRequest request) {
		String requestTitle = (String)request.getAttribute(WebKeys.PAGE_TITLE);

		if (requestTitle != null) {
			title = requestTitle + StringPool.SPACE + title;
		}

		request.setAttribute(WebKeys.PAGE_TITLE, title);
	}

	public void addPortalPortEventListener(
		PortalPortEventListener portalPortEventListener) {

		if (!_portalPortEventListeners.contains(portalPortEventListener)) {
			_portalPortEventListeners.add(portalPortEventListener);
		}
	}

	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url) {

		addPortletBreadcrumbEntry(request, title, url, null);
	}

	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url,
		Map<String, Object> data) {

		List<BreadcrumbEntry> breadcrumbEntries =
			(List<BreadcrumbEntry>)request.getAttribute(
				WebKeys.PORTLET_BREADCRUMBS);

		if (breadcrumbEntries == null) {
			breadcrumbEntries = new ArrayList<BreadcrumbEntry>();

			request.setAttribute(
				WebKeys.PORTLET_BREADCRUMBS, breadcrumbEntries);
		}

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setData(data);
		breadcrumbEntry.setTitle(title);
		breadcrumbEntry.setURL(url);

		breadcrumbEntries.add(breadcrumbEntry);
	}

	public void addPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		addDefaultResource(themeDisplay, layout, portlet, true);
		addDefaultResource(themeDisplay, layout, portlet, false);
	}

	public String addPreservedParameters(
		ThemeDisplay themeDisplay, Layout layout, String url,
		boolean doAsUser) {

		if (doAsUser) {
			if (Validator.isNotNull(themeDisplay.getDoAsUserId())) {
				url = HttpUtil.addParameter(
					url, "doAsUserId", themeDisplay.getDoAsUserId());
			}

			if (Validator.isNotNull(themeDisplay.getDoAsUserLanguageId())) {
				url = HttpUtil.addParameter(
					url, "doAsUserLanguageId",
					themeDisplay.getDoAsUserLanguageId());
			}
		}

		if (layout.isTypeControlPanel()) {
			if (themeDisplay.getDoAsGroupId() > 0) {
				url = HttpUtil.addParameter(
					url, "doAsGroupId", themeDisplay.getDoAsGroupId());
			}

			if (themeDisplay.getRefererPlid() != LayoutConstants.DEFAULT_PLID) {
				url = HttpUtil.addParameter(
					url, "refererPlid", themeDisplay.getRefererPlid());
			}

			if (Validator.isNotNull(themeDisplay.getControlPanelCategory())) {
				url = HttpUtil.addParameter(
					url, "controlPanelCategory",
					themeDisplay.getControlPanelCategory());
			}
		}

		return url;
	}

	public String addPreservedParameters(
		ThemeDisplay themeDisplay, String url) {

		return addPreservedParameters(
			themeDisplay, themeDisplay.getLayout(), url, true);
	}

	public void clearRequestParameters(RenderRequest renderRequest) {
		RenderRequestImpl renderRequestImpl = (RenderRequestImpl)renderRequest;

		if (renderRequestImpl.isTriggeredByActionURL()) {
			Map<String, String[]> renderParameters =
				renderRequestImpl.getRenderParameters();

			renderParameters.clear();
		}
	}

	public void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		if (actionResponse instanceof StateAwareResponseImpl) {
			StateAwareResponseImpl stateAwareResponseImpl =
				(StateAwareResponseImpl)actionResponse;

			if (stateAwareResponseImpl.getRedirectLocation() != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Cannot copy parameters on a redirected " +
							"StateAwareResponseImpl");
				}

				return;
			}
		}

		ActionResponseImpl actionResponseImpl =
			(ActionResponseImpl)actionResponse;

		Map<String, String[]> renderParameters =
			actionResponseImpl.getRenderParameterMap();

		actionResponse.setRenderParameter("p_p_lifecycle", "1");

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();
			String[] values = actionRequest.getParameterValues(param);

			if (renderParameters.get(
					actionResponseImpl.getNamespace() + param) == null) {

				actionResponse.setRenderParameter(param, values);
			}
		}
	}

	public String escapeRedirect(String url) {
		if (Validator.isNull(url) || !HttpUtil.hasDomain(url)) {
			return url;
		}

		String domain = HttpUtil.getDomain(url);

		int pos = -1;

		if ((pos = domain.indexOf(CharPool.COLON)) != -1) {
			domain = domain.substring(0, pos);
		}

		try {
			Company company = CompanyLocalServiceUtil.fetchCompanyByVirtualHost(
				domain);

			if (company != null) {
				return url;
			}
		}
		catch (Exception e) {
		}

		try {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
				domain);

			if (layoutSet != null) {
				return url;
			}
		}
		catch (Exception e) {
		}

		try {
			String securityMode = PropsValues.REDIRECT_URL_SECURITY_MODE;

			if (securityMode.equals("domain")) {
				String[] allowedDomains =
					PropsValues.REDIRECT_URL_DOMAINS_ALLOWED;

				if ((allowedDomains.length > 0) &&
					!ArrayUtil.contains(allowedDomains, domain)) {

					if (_log.isDebugEnabled()) {
						_log.debug("Redirect URL " + url + " is not allowed");
					}

					url = null;
				}
			}
			else if (securityMode.equals("ip")) {
				String[] allowedIps = PropsValues.REDIRECT_URL_IPS_ALLOWED;

				InetAddress inetAddress = InetAddress.getByName(domain);

				if ((allowedIps.length > 0) &&
					!ArrayUtil.contains(
						allowedIps, inetAddress.getHostAddress())) {

					String serverIp = getComputerAddress();

					if (!serverIp.equals(inetAddress.getHostAddress()) ||
						!ArrayUtil.contains(allowedIps, "SERVER_IP")) {

						if (_log.isDebugEnabled()) {
							_log.debug(
								"Redirect URL " + url + " is not allowed");
						}

						url = null;
					}
				}
			}
		}
		catch (UnknownHostException uhe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to determine IP for redirect URL " + url);
			}

			url = null;
		}

		return url;
	}

	public String generateRandomKey(HttpServletRequest request, String input) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isLifecycleResource() ||
			themeDisplay.isStateExclusive()) {

			return PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
		}
		else {
			return DeterminateKeyGenerator.generate(input);
		}
	}

	public String getActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException, SystemException {

		String actualURL = null;

		if ((friendlyURL != null) &&
			friendlyURL.startsWith(
				JournalArticleConstants.CANONICAL_URL_SEPARATOR)) {

			try {
				actualURL = getJournalArticleActualURL(
					groupId, mainPath, friendlyURL, params, requestContext);
			}
			catch (Exception e) {
				friendlyURL = null;
			}
		}

		if (actualURL == null) {
			actualURL = getLayoutActualURL(
				groupId, privateLayout, mainPath, friendlyURL, params,
				requestContext);
		}

		return actualURL;
	}

	public Set<String> getAuthTokenIgnoreActions() {
		return _authTokenIgnoreActions;
	}

	public Set<String> getAuthTokenIgnorePortlets() {
		return _authTokenIgnorePortlets;
	}

	public BaseModel<?> getBaseModel(Resource resource)
		throws PortalException, SystemException {

		ResourceCode resourceCode =
			ResourceCodeLocalServiceUtil.getResourceCode(resource.getCodeId());

		String modelName = resourceCode.getName();
		String primKey = resource.getPrimKey();

		return getBaseModel(modelName, primKey);
	}

	public BaseModel<?> getBaseModel(ResourcePermission resourcePermission)
		throws PortalException, SystemException {

		String modelName = resourcePermission.getName();
		String primKey = resourcePermission.getPrimKey();

		return getBaseModel(modelName, primKey);
	}

	public BaseModel<?> getBaseModel(String modelName, String primKey)
		throws PortalException, SystemException {

		if (!modelName.contains(".model.")) {
			return null;
		}

		String[] parts = StringUtil.split(modelName, CharPool.PERIOD);

		if ((parts.length <= 2) || !parts[parts.length - 2].equals("model")) {
			return null;
		}

		parts[parts.length - 2] = "service";

		String serviceName =
			StringUtil.merge(parts, StringPool.PERIOD) + "LocalServiceUtil";
		String methodName = "get" + parts[parts.length - 1];

		Method method = null;

		try {
			Class<?> serviceUtil = Class.forName(serviceName);

			if (Validator.isNumber(primKey)) {
				method = serviceUtil.getMethod(
					methodName, new Class[] {Long.TYPE});

				return (BaseModel<?>)method.invoke(null, new Long(primKey));
			}
			else {
				method = serviceUtil.getMethod(
					methodName, new Class[] {String.class});

				return (BaseModel<?>)method.invoke(null, primKey);
			}
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof PortalException) {
				throw (PortalException)cause;
			}
			else if (cause instanceof SystemException) {
				throw (SystemException)cause;
			}
			else {
				throw new SystemException(cause);
			}
		}
	}

	public long getBasicAuthUserId(HttpServletRequest request)
		throws PortalException, SystemException {

		long companyId = PortalInstances.getCompanyId(request);

		return getBasicAuthUserId(request, companyId);
	}

	public long getBasicAuthUserId(HttpServletRequest request, long companyId)
		throws PortalException, SystemException {

		long userId = 0;

		String authorizationHeader = request.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isNull(authorizationHeader)) {
			return userId;
		}

		String[] authorizationArray = authorizationHeader.split("\\s+");

		String authorization = authorizationArray[0];
		String credentials = new String(Base64.decode(authorizationArray[1]));

		if (!authorization.equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
			return userId;
		}

		String[] loginAndPassword = StringUtil.split(
			credentials, CharPool.COLON);

		String login = loginAndPassword[0].trim();

		String password = null;

		if (loginAndPassword.length > 1) {
			password = loginAndPassword[1].trim();
		}

		// Strip @uid and @sn for backwards compatibility

		if (login.endsWith("@uid")) {
			int pos = login.indexOf("@uid");

			login = login.substring(0, pos);
		}
		else if (login.endsWith("@sn")) {
			int pos = login.indexOf("@sn");

			login = login.substring(0, pos);
		}

		try {
			userId = LoginUtil.getAuthenticatedUserId(
				request, login, password, null);
		}
		catch (AuthException ae) {
		}

		return userId;
	}

	public String getCanonicalAlternateURL(
			HttpServletRequest request, String url, Locale locale)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String layoutFriendlyURL = getLayoutFriendlyURL(
			layout, themeDisplay, locale);

		int pos = url.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

		if (pos == -1) {
			pos = url.indexOf(StringPool.QUESTION);
		}

		if (pos != -1) {
			url = url.substring(pos);
		}
		else{
			url = StringPool.BLANK;
		}

		String host = StringPool.BLANK;

		Group group = layout.getGroup();

		String groupFriendlyURL = group.getFriendlyURL();

		pos = layoutFriendlyURL.indexOf(groupFriendlyURL);

		if (pos != -1) {
			host = getPortalURL(request);
		}

		if (layout.isFirstParent() && Validator.isNull(url)) {
			String friendlyURL = layoutFriendlyURL.substring(
				0, layoutFriendlyURL.indexOf(layout.getFriendlyURL()));

			url = host.concat(friendlyURL);
		}
		else{
			url = host.concat(layoutFriendlyURL).concat(url);
		}

		return url;
	}

	public String getCanonicalURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String canonicalURL = getCurrentCompleteURL(request);

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.matches("_.*_redirect$")) {
				canonicalURL = HttpUtil.removeParameter(canonicalURL, name);
			}
		}

		return getCanonicalAlternateURL(
			request, canonicalURL, LocaleUtil.getDefault());
	}

	/**
	 * @deprecated {@link #getCDNHost(boolean)}
	 */
	public String getCDNHost() {
		long companyId = CompanyThreadLocal.getCompanyId();

		return getCDNHostHttp(companyId);
	}

	public String getCDNHost(boolean secure) {
		long companyId = CompanyThreadLocal.getCompanyId();

		if (secure) {
			return getCDNHostHttps(companyId);
		}
		else {
			return getCDNHostHttp(companyId);
		}
	}

	public String getCDNHostHttp(long companyId) {
		String cdnHostHttp = _cdnHostHttpMap.get(companyId);

		if (cdnHostHttp != null) {
			return cdnHostHttp;
		}

		try {
			cdnHostHttp = PrefsPropsUtil.getString(
				companyId, PropsKeys.CDN_HOST_HTTP, PropsValues.CDN_HOST_HTTP);
		}
		catch (Exception e) {
		}

		if ((cdnHostHttp == null) || cdnHostHttp.startsWith("${")) {
			cdnHostHttp = StringPool.BLANK;
		}

		_cdnHostHttpMap.put(companyId, cdnHostHttp);

		return cdnHostHttp;
	}

	public String getCDNHostHttps(long companyId) {
		String cdnHostHttps = _cdnHostHttpsMap.get(companyId);

		if (cdnHostHttps != null) {
			return cdnHostHttps;
		}

		try {
			cdnHostHttps = PrefsPropsUtil.getString(
				companyId, PropsKeys.CDN_HOST_HTTPS,
				PropsValues.CDN_HOST_HTTPS);
		}
		catch (SystemException e) {
		}

		if ((cdnHostHttps == null) || cdnHostHttps.startsWith("${")) {
			cdnHostHttps = StringPool.BLANK;
		}

		_cdnHostHttpsMap.put(companyId, cdnHostHttps);

		return cdnHostHttps;
	}

	public String getClassName(long classNameId) {
		try {
			ClassName className = ClassNameLocalServiceUtil.getClassName(
				classNameId);

			return className.getValue();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to get class name from id " + classNameId);
		}
	}

	public long getClassNameId(Class<?> clazz) {
		return ClassNameLocalServiceUtil.getClassNameId(clazz);
	}

	public long getClassNameId(String value) {
		return ClassNameLocalServiceUtil.getClassNameId(value);
	}

	public String getClassNamePortletId(String className) {
		String portletId = StringPool.BLANK;

		if (className.startsWith("com.liferay.portlet.blogs")) {
			portletId = PortletKeys.BLOGS;
		}
		else if (className.startsWith("com.liferay.portlet.bookmarks")) {
			portletId = PortletKeys.BOOKMARKS;
		}
		else if (className.startsWith("com.liferay.portlet.calendar")) {
			portletId = PortletKeys.CALENDAR;
		}
		else if (className.startsWith("com.liferay.portlet.documentlibrary")) {
			portletId = PortletKeys.DOCUMENT_LIBRARY;
		}
		else if (className.startsWith("com.liferay.portlet.imagegallery")) {
			portletId = PortletKeys.MEDIA_GALLERY_DISPLAY;
		}
		else if (className.startsWith("com.liferay.portlet.journal")) {
			portletId = PortletKeys.JOURNAL;
		}
		else if (className.startsWith("com.liferay.portlet.messageboards")) {
			portletId = PortletKeys.MESSAGE_BOARDS;
		}
		else if (className.startsWith("com.liferay.portlet.wiki")) {
			portletId = PortletKeys.WIKI;
		}

		return portletId;
	}

	public Company getCompany(HttpServletRequest request)
		throws PortalException, SystemException {

		long companyId = getCompanyId(request);

		if (companyId <= 0) {
			return null;
		}

		Company company = (Company)request.getAttribute(WebKeys.COMPANY);

		if (company == null) {

			// LEP-5994

			try {
				company = CompanyLocalServiceUtil.getCompanyById(companyId);
			}
			catch (NoSuchCompanyException nsce) {
				company = CompanyLocalServiceUtil.getCompanyById(
					PortalInstances.getDefaultCompanyId());
			}

			request.setAttribute(WebKeys.COMPANY, company);
		}

		return company;
	}

	public Company getCompany(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getCompany(getHttpServletRequest(portletRequest));
	}

	public long getCompanyId(HttpServletRequest request) {
		return PortalInstances.getCompanyId(request);
	}

	public long getCompanyId(PortletRequest portletRequest) {
		return getCompanyId(getHttpServletRequest(portletRequest));
	}

	public long[] getCompanyIds() {
		return PortalInstances.getCompanyIds();
	}

	public String getComputerAddress() {
		return _computerAddress;
	}

	public String getComputerName() {
		return _computerName;
	}

	public String getControlPanelCategory(
			String portletId, ThemeDisplay themeDisplay)
		throws SystemException {

		for (String category : PortletCategoryKeys.ALL) {
			List<Portlet> portlets = getControlPanelPortlets(
				category, themeDisplay);

			for (Portlet portlet : portlets) {
				if (portlet.getPortletId().equals(portletId)) {
					return category;
				}
			}
		}

		return StringPool.BLANK;
	}

	public String getControlPanelFullURL(
			long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException, SystemException {

		StringBundler sb = new StringBundler(6);

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		Company company = CompanyLocalServiceUtil.getCompany(
			group.getCompanyId());

		sb.append(
			getPortalURL(
				company.getVirtualHostname(), getPortalPort(false), false));
		sb.append(getPathFriendlyURLPrivateGroup());
		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		if (params != null) {
			params = new HashMap<String, String[]>(params);
		}
		else {
			params = new HashMap<String, String[]>();
		}

		params.put("p_p_id", new String[] {ppid});
		params.put("p_p_lifecycle", new String[] {"0"});
		params.put(
			"p_p_state", new String[] {WindowState.MAXIMIZED.toString()});
		params.put("p_p_mode", new String[] {PortletMode.VIEW.toString()});

		sb.append(HttpUtil.parameterMapToString(params, true));

		return sb.toString();
	}

	public Set<Portlet> getControlPanelPortlets(long companyId, String category)
		throws SystemException {

		Set<Portlet> portletsSet = new TreeSet<Portlet>(
			new PortletControlPanelWeightComparator());

		List<Portlet> portletsList = PortletLocalServiceUtil.getPortlets(
			companyId);

		for (Portlet portlet : portletsList) {
			if (category.equals(portlet.getControlPanelEntryCategory())) {
				portletsSet.add(portlet);
			}
		}

		return portletsSet;
	}

	public List<Portlet> getControlPanelPortlets(
			String category, ThemeDisplay themeDisplay)
		throws SystemException {

		Set<Portlet> portlets = getControlPanelPortlets(
			themeDisplay.getCompanyId(), category);

		return filterControlPanelPortlets(portlets, category, themeDisplay);
	}

	public String getCreateAccountURL(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		if (Validator.isNull(PropsValues.COMPANY_SECURITY_STRANGERS_URL)) {
			PortletURL createAccountURL = PortletURLFactoryUtil.create(
				request, PortletKeys.LOGIN, themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE);

			createAccountURL.setWindowState(WindowState.MAXIMIZED);
			createAccountURL.setPortletMode(PortletMode.VIEW);

			createAccountURL.setParameter("saveLastPath", "0");
			createAccountURL.setParameter(
				"struts_action", "/login/create_account");

			return createAccountURL.toString();
		}

		try {
			Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				themeDisplay.getScopeGroupId(), false,
				PropsValues.COMPANY_SECURITY_STRANGERS_URL);

			return PortalUtil.getLayoutURL(layout, themeDisplay);
		}
		catch (NoSuchLayoutException nsle) {
		}

		return StringPool.BLANK;
	}

	public String getCurrentCompleteURL(HttpServletRequest request) {
		String currentCompleteURL = (String)request.getAttribute(
			WebKeys.CURRENT_COMPLETE_URL);

		if (currentCompleteURL == null) {
			currentCompleteURL = HttpUtil.getCompleteURL(request);

			request.setAttribute(
				WebKeys.CURRENT_COMPLETE_URL, currentCompleteURL);
		}

		return currentCompleteURL;
	}

	public String getCurrentURL(HttpServletRequest request) {
		String currentURL = (String)request.getAttribute(WebKeys.CURRENT_URL);

		if (currentURL != null) {
			return currentURL;
		}

		currentURL = ParamUtil.getString(request, "currentURL");

		if (Validator.isNull(currentURL)) {
			currentURL = HttpUtil.getCompleteURL(request);

			if ((Validator.isNotNull(currentURL)) &&
				(currentURL.indexOf(_J_SECURITY_CHECK) == -1)) {

				currentURL = currentURL.substring(
					currentURL.indexOf(Http.PROTOCOL_DELIMITER) +
						Http.PROTOCOL_DELIMITER.length());

				currentURL = currentURL.substring(
					currentURL.indexOf(CharPool.SLASH));
			}

			if (Validator.isNotNull(currentURL) &&
				FacebookUtil.isFacebook(currentURL)) {

				String[] facebookData = FacebookUtil.getFacebookData(request);

				currentURL =
					FacebookUtil.FACEBOOK_APPS_URL + facebookData[0] +
						facebookData[2];
			}
		}

		if (Validator.isNull(currentURL)) {
			currentURL = getPathMain();
		}

		request.setAttribute(WebKeys.CURRENT_URL, currentURL);

		return currentURL;
	}

	public String getCurrentURL(PortletRequest portletRequest) {
		return (String)portletRequest.getAttribute(WebKeys.CURRENT_URL);
	}

	public String getCustomSQLFunctionIsNotNull() {
		return PropsValues.CUSTOM_SQL_FUNCTION_ISNOTNULL;
	}

	public String getCustomSQLFunctionIsNull() {
		return PropsValues.CUSTOM_SQL_FUNCTION_ISNULL;
	}

	public Date getDate(int month, int day, int year) {
		try {
			return getDate(month, day, year, null);
		}
		catch (PortalException pe) {
			throw new RuntimeException();
		}
	}

	public Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, hour, min, null, pe);
	}

	public Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException {

		if (!Validator.isGregorianDate(month, day, year)) {
			if (pe != null) {
				throw pe;
			}
			else {
				return null;
			}
		}
		else {
			Calendar cal = null;

			if (timeZone == null) {
				cal = CalendarFactoryUtil.getCalendar();
			}
			else {
				cal = CalendarFactoryUtil.getCalendar(timeZone);
			}

			if ((hour == -1) || (min == -1)) {
				cal.set(year, month, day, 0, 0, 0);
			}
			else {
				cal.set(year, month, day, hour, min, 0);
			}

			cal.set(Calendar.MILLISECOND, 0);

			Date date = cal.getTime();

			/*if (timeZone != null &&
				cal.before(CalendarFactoryUtil.getCalendar(timeZone))) {

				throw pe;
			}*/

			return date;
		}
	}

	public Date getDate(int month, int day, int year, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, null, pe);
	}

	public Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, -1, -1, timeZone, pe);
	}

	public long getDefaultCompanyId() {
		return PortalInstances.getDefaultCompanyId();
	}

	public long getDigestAuthUserId(HttpServletRequest request)
		throws PortalException, SystemException {

		long userId = 0;

		String authorizationHeader = request.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isNull(authorizationHeader) ||
			!authorizationHeader.startsWith("Digest ")) {

			return userId;
		}

		authorizationHeader = authorizationHeader.substring("Digest ".length());
		authorizationHeader = StringUtil.replace(
			authorizationHeader, CharPool.COMMA, CharPool.NEW_LINE);

		UnicodeProperties authorizationProperties = new UnicodeProperties();

		authorizationProperties.fastLoad(authorizationHeader);

		String username = StringUtil.unquote(
			authorizationProperties.getProperty("username"));
		String realm = StringUtil.unquote(
			authorizationProperties.getProperty("realm"));
		String nonce = StringUtil.unquote(
			authorizationProperties.getProperty("nonce"));
		String uri = StringUtil.unquote(
			authorizationProperties.getProperty("uri"));
		String response = StringUtil.unquote(
			authorizationProperties.getProperty("response"));

		if (Validator.isNull(username) || Validator.isNull(realm) ||
			Validator.isNull(nonce) || Validator.isNull(uri) ||
			Validator.isNull(response)) {

			return userId;
		}

		if (!realm.equals(PORTAL_REALM) ||
			!uri.equals(request.getRequestURI())) {

			return userId;
		}

		if (!NonceUtil.verify(nonce)) {
			return userId;
		}

		long companyId = PortalInstances.getCompanyId(request);

		userId = UserLocalServiceUtil.authenticateForDigest(
			companyId, username, realm, nonce, request.getMethod(), uri,
			response);

		return userId;
	}

	public String getEmailFromAddress(
			PortletPreferences preferences, long companyId, String defaultValue)
		throws SystemException {

		if (Validator.isNull(defaultValue)) {
			defaultValue = PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		}

		return preferences.getValue("emailFromAddress", defaultValue);
	}

	public String getEmailFromName(
			PortletPreferences preferences, long companyId, String defaultValue)
		throws SystemException {

		if (Validator.isNull(defaultValue)) {
			defaultValue = PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
		}

		return preferences.getValue("emailFromName", defaultValue);
	}

	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, PortletRequest portletRequest)
		throws PortalException, SystemException {

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		List<String> names = new ArrayList<String>();

		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			if (param.indexOf("ExpandoAttributeName--") != -1) {
				String name = ParamUtil.getString(portletRequest, param);

				names.add(name);
			}
		}

		for (String name : names) {
			int type = expandoBridge.getAttributeType(name);

			UnicodeProperties properties = expandoBridge.getAttributeProperties(
				name);

			String displayType = GetterUtil.getString(
				properties.getProperty(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE),
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX);

			Serializable value = getExpandoValue(
				portletRequest, "ExpandoAttribute--" + name + "--", type,
				displayType);

			attributes.put(name, value);
		}

		return attributes;
	}

	public Serializable getExpandoValue(
			PortletRequest portletRequest, String name, int type,
			String displayType)
		throws PortalException, SystemException {

		Serializable value = null;

		if (type == ExpandoColumnConstants.BOOLEAN) {
			value = ParamUtil.getBoolean(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
		}
		else if (type == ExpandoColumnConstants.DATE) {
			User user = getUser(portletRequest);

			int valueDateMonth = ParamUtil.getInteger(
				portletRequest, name + "Month");
			int valueDateDay = ParamUtil.getInteger(
				portletRequest, name + "Day");
			int valueDateYear = ParamUtil.getInteger(
				portletRequest, name + "Year");
			int valueDateHour = ParamUtil.getInteger(
				portletRequest, name + "Hour");
			int valueDateMinute = ParamUtil.getInteger(
				portletRequest, name + "Minute");
			int valueDateAmPm = ParamUtil.getInteger(
				portletRequest, name + "AmPm");

			if (valueDateAmPm == Calendar.PM) {
				valueDateHour += 12;
			}

			value = getDate(
				valueDateMonth, valueDateDay, valueDateYear, valueDateHour,
				valueDateMinute, user.getTimeZone(), new ValueDataException());
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			value = ParamUtil.getDouble(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			String[] values = portletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getDoubleValues(values);
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			value = ParamUtil.getFloat(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			String[] values = portletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getFloatValues(values);
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			value = ParamUtil.getInteger(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			String[] values = portletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getIntegerValues(values);
		}
		else if (type == ExpandoColumnConstants.LONG) {
			value = ParamUtil.getLong(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			String[] values = portletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getLongValues(values);
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			value = ParamUtil.getShort(portletRequest, name);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			String[] values = portletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getShortValues(values);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			value = portletRequest.getParameterValues(name);
		}
		else {
			value = ParamUtil.getString(portletRequest, name);
		}

		return value;
	}

	public String getFacebookURL(
			Portlet portlet, String facebookCanvasPageURL,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String facebookURL = _getServletURL(
			portlet, FacebookUtil.FACEBOOK_SERVLET_PATH + facebookCanvasPageURL,
			themeDisplay);

		if (!facebookURL.endsWith(StringPool.SLASH)) {
			facebookURL += StringPool.SLASH;
		}

		return facebookURL;
	}

	public String getFirstPageLayoutTypes(PageContext pageContext) {
		StringBundler sb = new StringBundler();

		for (String type : PropsValues.LAYOUT_TYPES) {
			if (isLayoutFirstPageable(type)) {
				sb.append(
					LanguageUtil.get(pageContext, "layout.types." + type));
				sb.append(StringPool.COMMA);
				sb.append(StringPool.SPACE);
			}
		}

		if (sb.index() >= 2) {
			sb.setIndex(sb.index() - 2);
		}

		return sb.toString();
	}

	public String getGlobalLibDir() {
		return PropsValues.LIFERAY_LIB_GLOBAL_DIR;
	}

	public String getGoogleGadgetURL(
			Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _getServletURL(
			portlet, PropsValues.GOOGLE_GADGET_SERVLET_MAPPING, themeDisplay);
	}

	public String getGroupFriendlyURL(
			Group group, boolean privateLayoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			group.getGroupId(), privateLayoutSet);

		String portalURL = StringPool.BLANK;

		if (!themeDisplay.getServerName().equals(_LOCALHOST)) {
			String virtualHostname = layoutSet.getVirtualHostname();

			if (Validator.isNull(virtualHostname) &&
				Validator.isNotNull(
					PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME) &&
				!layoutSet.isPrivateLayout()) {

				try {
					Group defaultGroup = GroupLocalServiceUtil.getGroup(
						themeDisplay.getCompanyId(),
						PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

					if (layoutSet.getGroupId() == defaultGroup.getGroupId()) {
						Company company = themeDisplay.getCompany();

						virtualHostname = company.getVirtualHostname();
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			if (Validator.isNotNull(virtualHostname) &&
				!virtualHostname.equalsIgnoreCase(_LOCALHOST)) {

				virtualHostname = getPortalURL(
					virtualHostname, themeDisplay.getServerPort(),
					themeDisplay.isSecure());

				String portalDomain = HttpUtil.getDomain(
					themeDisplay.getPortalURL());

				if (virtualHostname.contains(portalDomain)) {
					String path = StringPool.BLANK;

					if (themeDisplay.isWidget()) {
						path = PropsValues.WIDGET_SERVLET_MAPPING;
					}

					if (themeDisplay.isI18n()) {
						path = themeDisplay.getI18nPath();
					}

					return virtualHostname.concat(_pathContext).concat(path);
				}
			}
			else {
				Layout curLayout = themeDisplay.getLayout();

				LayoutSet curLayoutSet = curLayout.getLayoutSet();

				if ((layoutSet.getLayoutSetId() !=
						curLayoutSet.getLayoutSetId()) &&
					(group.getClassPK() != themeDisplay.getUserId())) {

					Company company = themeDisplay.getCompany();

					virtualHostname = company.getVirtualHostname();

					if (!virtualHostname.equalsIgnoreCase(_LOCALHOST)) {
						portalURL = getPortalURL(
							virtualHostname, themeDisplay.getServerPort(),
							themeDisplay.isSecure());
					}
				}
			}
		}

		String friendlyURL = null;

		if (privateLayoutSet) {
			if (group.isUser()) {
				friendlyURL = _PRIVATE_USER_SERVLET_MAPPING;
			}
			else {
				friendlyURL = _PRIVATE_GROUP_SERVLET_MAPPING;
			}
		}
		else {
			friendlyURL = _PUBLIC_GROUP_SERVLET_MAPPING;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(portalURL);
		sb.append(_pathContext);

		if (themeDisplay.isI18n()) {
			sb.append(themeDisplay.getI18nPath());
		}

		if (themeDisplay.isWidget()) {
			sb.append(PropsValues.WIDGET_SERVLET_MAPPING);
		}

		sb.append(friendlyURL);
		sb.append(group.getFriendlyURL());

		return sb.toString();
	}

	public String[] getGroupPermissions(HttpServletRequest request) {
		return request.getParameterValues("groupPermissions");
	}

	public String[] getGroupPermissions(PortletRequest portletRequest) {
		return portletRequest.getParameterValues("groupPermissions");
	}

	public String[] getGuestPermissions(HttpServletRequest request) {
		return request.getParameterValues("guestPermissions");
	}

	public String[] getGuestPermissions(PortletRequest portletRequest) {
		return portletRequest.getParameterValues("guestPermissions");
	}

	public String getHomeURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String portalURL = getPortalURL(request);

		return portalURL + _pathContext + getRelativeHomeURL(request);
	}

	public String getHost(HttpServletRequest request) {
		request = getOriginalServletRequest(request);

		String host = request.getHeader("Host");

		if (host != null) {
			host = host.trim().toLowerCase();

			int pos = host.indexOf(':');

			if (pos >= 0) {
				host = host.substring(0, pos);
			}
		}
		else {
			host = null;
		}

		return host;
	}

	public String getHost(PortletRequest portletRequest) {
		return getHost(getHttpServletRequest(portletRequest));
	}

	public HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest) {

		PortletRequestImpl portletRequestImpl =
			PortletRequestImpl.getPortletRequestImpl(portletRequest);

		return portletRequestImpl.getHttpServletRequest();
	}

	public HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse) {

		PortletResponseImpl portletResponseImpl =
			PortletResponseImpl.getPortletResponseImpl(portletResponse);

		return portletResponseImpl.getHttpServletResponse();
	}

	public String getJournalArticleActualURL(
			long groupId, String mainPath, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException, SystemException {

		String articleUrlTitle = friendlyURL.substring(
			JournalArticleConstants.CANONICAL_URL_SEPARATOR.length());

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				groupId, articleUrlTitle);

		Layout layout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			journalArticle.getLayoutUuid(), groupId);

		String layoutActualURL = getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams =
			new InheritableMap<String, String[]>();

		if (params != null) {
			actualParams.setParentMap(params);
		}

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		String defaultAssetPublisherPortletId = typeSettingsProperties.get(
			LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

		String currentDefaultAssetPublisherPortletId =
			defaultAssetPublisherPortletId;

		if (Validator.isNull(defaultAssetPublisherPortletId)) {
			defaultAssetPublisherPortletId =
				PortletKeys.ASSET_PUBLISHER +
					LayoutTypePortletImpl.getFullInstanceSeparator();
		}

		if (Validator.isNull(
				currentDefaultAssetPublisherPortletId)) {

			HttpServletRequest request =
				(HttpServletRequest)requestContext.get("request");

			String actualPortletAuthenticationToken = AuthTokenUtil.getToken(
				request, layout.getPlid(), defaultAssetPublisherPortletId);

			actualParams.put(
				"p_p_auth", new String[] {actualPortletAuthenticationToken});
		}

		actualParams.put(
			"p_p_id", new String[] {defaultAssetPublisherPortletId});
		actualParams.put("p_p_lifecycle", new String[] {"0"});

		if (Validator.isNull(
				currentDefaultAssetPublisherPortletId)) {

			actualParams.put(
				"p_p_state", new String[] {WindowState.MAXIMIZED.toString()});
		}

		actualParams.put("p_p_mode", new String[] {"view"});
		actualParams.put(
			"p_j_a_id", new String[] {String.valueOf(journalArticle.getId())});

		String namespace = getPortletNamespace(defaultAssetPublisherPortletId);

		actualParams.put(
			namespace + "struts_action",
			new String[] {"/asset_publisher/view_content"});
		actualParams.put(
			namespace + "type",
			new String[] {JournalArticleAssetRendererFactory.TYPE});
		actualParams.put(
			namespace + "urlTitle",
			new String[] {journalArticle.getUrlTitle()});

		String queryString = HttpUtil.parameterMapToString(
			actualParams, false);

		if (layoutActualURL.contains(StringPool.QUESTION)) {
			layoutActualURL =
				layoutActualURL + StringPool.AMPERSAND + queryString;
		}
		else {
			layoutActualURL =
				layoutActualURL + StringPool.QUESTION + queryString;
		}

		return layoutActualURL;
	}

	public String getJsSafePortletId(String portletId) {
		return JS.getSafeName(portletId);
	}

	public String getLayoutActualURL(Layout layout) {
		return getLayoutActualURL(layout, getPathMain());
	}

	public String getLayoutActualURL(Layout layout, String mainPath) {
		Map<String, String> variables = new HashMap<String, String>();

		variables.put("liferay:groupId", String.valueOf(layout.getGroupId()));
		variables.put("liferay:mainPath", mainPath);
		variables.put("liferay:plid", String.valueOf(layout.getPlid()));

		LayoutType layoutType = layout.getLayoutType();

		UnicodeProperties typeSettingsProperties =
			layoutType.getTypeSettingsProperties();

		variables.putAll(typeSettingsProperties);

		LayoutSettings layoutSettings = LayoutSettings.getInstance(layout);

		return layoutSettings.getURL(variables);
	}

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException, SystemException {

		return getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, null, null);
	}

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException, SystemException {

		Layout layout = null;
		String queryString = StringPool.BLANK;

		if (Validator.isNull(friendlyURL)) {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (!layouts.isEmpty()) {
				layout = layouts.get(0);
			}
			else {
				throw new NoSuchLayoutException(
					"{groupId=" + groupId + ",privateLayout=" + privateLayout +
						"} does not have any layouts");
			}
		}
		else {
			Object[] friendlyURLMapper = getPortletFriendlyURLMapper(
				groupId, privateLayout, friendlyURL, params, requestContext);

			layout = (Layout)friendlyURLMapper[0];
			queryString = (String)friendlyURLMapper[1];
		}

		String layoutActualURL = getLayoutActualURL(layout, mainPath);

		if (Validator.isNotNull(queryString)) {
			layoutActualURL = layoutActualURL.concat(queryString);
		}
		else if (params.isEmpty()) {
			LayoutType layoutType = layout.getLayoutType();

			UnicodeProperties typeSettingsProperties =
				layoutType.getTypeSettingsProperties();

			queryString = typeSettingsProperties.getProperty("query-string");

			if (Validator.isNotNull(queryString) &&
				layoutActualURL.contains(StringPool.QUESTION)) {

				layoutActualURL =
					layoutActualURL.concat(StringPool.AMPERSAND).concat(
						queryString);
			}
		}

		return layoutActualURL;
	}

	public String getLayoutEditPage(Layout layout) {
		LayoutSettings layoutSettings = LayoutSettings.getInstance(
			layout.getType());

		return layoutSettings.getEditPage();
	}

	public String getLayoutEditPage(String type) {
		LayoutSettings layoutSettings = LayoutSettings.getInstance(type);

		return layoutSettings.getEditPage();
	}

	public String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		if (!isLayoutFriendliable(layout)) {
			return null;
		}

		String groupFriendlyURL = getGroupFriendlyURL(
			layout.getGroup(), layout.isPrivateLayout(), themeDisplay);

		return groupFriendlyURL.concat(layout.getFriendlyURL());
	}

	public String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException, SystemException {

		String i18nLanguageId = themeDisplay.getI18nLanguageId();
		String i18nPath = themeDisplay.getI18nPath();

		try {
			String tempI18nLanguageId = null;

			if ((I18nFilter.getLanguageIds().contains(locale.toString())) &&
				((PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 1) &&
				 (!locale.equals(LocaleUtil.getDefault()))) ||
				(PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2)) {

				tempI18nLanguageId = locale.toString();
			}

			String tempI18nPath = null;

			if (Validator.isNotNull(tempI18nLanguageId)) {
				tempI18nPath = StringPool.SLASH + tempI18nLanguageId;

				if (!LanguageUtil.isDuplicateLanguageCode(
						locale.getLanguage())) {

					tempI18nPath = StringPool.SLASH + locale.getLanguage();
				}
				else {
					Locale priorityLocale = LanguageUtil.getLocale(
						locale.getLanguage());

					if (locale.equals(priorityLocale)) {
						tempI18nPath = StringPool.SLASH + locale.getLanguage();
					}
				}
			}

			themeDisplay.setI18nLanguageId(tempI18nLanguageId);
			themeDisplay.setI18nPath(tempI18nPath);

			return getLayoutFriendlyURL(layout, themeDisplay);
		}
		finally {
			themeDisplay.setI18nLanguageId(i18nLanguageId);
			themeDisplay.setI18nPath(i18nPath);
		}
	}

	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getLayoutFullURL(layout, themeDisplay, true);
	}

	public String getLayoutFullURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException {

		String layoutURL = getLayoutURL(layout, themeDisplay, doAsUser);
		String portalURL = getPortalURL(layout, themeDisplay);

		if (StringUtil.startsWith(layoutURL, portalURL)) {
			return layoutURL;
		}
		else {
			return portalURL + layoutURL;
		}
	}

	public String getLayoutFullURL(long groupId, String portletId)
		throws PortalException, SystemException {

		return getLayoutFullURL(groupId, portletId, false);
	}

	public String getLayoutFullURL(
			long groupId, String portletId, boolean secure)
		throws PortalException, SystemException {

		long plid = getPlidFromPortletId(groupId, portletId);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			return null;
		}

		StringBundler sb = new StringBundler(4);

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isLayout()) {
			long parentGroupId = group.getParentGroupId();

			if (parentGroupId > 0) {
				group = GroupLocalServiceUtil.getGroup(parentGroupId);
			}
		}

		String virtualHostname = null;

		LayoutSet layoutSet = layout.getLayoutSet();

		if (Validator.isNotNull(layoutSet.getVirtualHostname())) {
			virtualHostname = layoutSet.getVirtualHostname();
		}
		else {
			Company company = CompanyLocalServiceUtil.getCompany(
				layout.getCompanyId());

			virtualHostname = company.getVirtualHostname();
		}

		String portalURL = getPortalURL(
			virtualHostname, getPortalPort(secure), secure);

		sb.append(portalURL);

		if (layout.isPrivateLayout()) {
			if (group.isUser()) {
				sb.append(getPathFriendlyURLPrivateUser());
			}
			else {
				sb.append(getPathFriendlyURLPrivateGroup());
			}
		}
		else {
			sb.append(getPathFriendlyURLPublic());
		}

		sb.append(group.getFriendlyURL());
		sb.append(layout.getFriendlyURL());

		return sb.toString();
	}

	public String getLayoutFullURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getLayoutFullURL(themeDisplay.getLayout(), themeDisplay);
	}

	public String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String virtualHostname = layoutSet.getVirtualHostname();

		if (Validator.isNull(virtualHostname) &&
			Validator.isNotNull(PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME) &&
			!layoutSet.isPrivateLayout()) {

			try {
				Group group = GroupLocalServiceUtil.getGroup(
					themeDisplay.getCompanyId(),
					PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

				if (layoutSet.getGroupId() == group.getGroupId()) {
					Company company = themeDisplay.getCompany();

					virtualHostname = company.getVirtualHostname();
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (Validator.isNotNull(virtualHostname)) {
			String portalURL = getPortalURL(
				virtualHostname, themeDisplay.getServerPort(),
				themeDisplay.isSecure());

			// Use the layout set's virtual host setting only if the layout set
			// is already used for the current request

			long curLayoutSetId =
				themeDisplay.getLayout().getLayoutSet().getLayoutSetId();

			if ((layoutSet.getLayoutSetId() != curLayoutSetId) ||
				(portalURL.startsWith(themeDisplay.getURLPortal()))) {

				String layoutSetFriendlyURL = StringPool.BLANK;

				if (themeDisplay.isI18n()) {
					layoutSetFriendlyURL = themeDisplay.getI18nPath();
				}

				return portalURL + _pathContext + layoutSetFriendlyURL;
			}
		}

		Group group = GroupLocalServiceUtil.getGroup(layoutSet.getGroupId());

		String friendlyURL = null;

		if (layoutSet.isPrivateLayout()) {
			if (group.isUser()) {
				friendlyURL = _PRIVATE_USER_SERVLET_MAPPING;
			}
			else {
				friendlyURL = _PRIVATE_GROUP_SERVLET_MAPPING;
			}
		}
		else {
			friendlyURL = _PUBLIC_GROUP_SERVLET_MAPPING;
		}

		if (themeDisplay.isI18n()) {
			friendlyURL = themeDisplay.getI18nPath() + friendlyURL;
		}

		return _pathContext + friendlyURL + group.getFriendlyURL();
	}

	public String getLayoutTarget(Layout layout) {
		UnicodeProperties typeSettingsProps =
			layout.getTypeSettingsProperties();

		String target = typeSettingsProps.getProperty("target");

		if (Validator.isNull(target)) {
			target = StringPool.BLANK;
		}
		else {
			target = "target=\"" + HtmlUtil.escapeAttribute(target) + "\"";
		}

		return target;
	}

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getLayoutURL(layout, themeDisplay, true);
	}

	public String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException, SystemException {

		if (layout == null) {
			return themeDisplay.getPathMain() + PATH_PORTAL_LAYOUT;
		}

		if (!layout.isTypeURL()) {
			String layoutFriendlyURL = getLayoutFriendlyURL(
				layout, themeDisplay);

			if (Validator.isNotNull(layoutFriendlyURL)) {
				layoutFriendlyURL = addPreservedParameters(
					themeDisplay, layout, layoutFriendlyURL, doAsUser);

				return layoutFriendlyURL;
			}
		}

		String layoutURL = getLayoutActualURL(layout);

		layoutURL = addPreservedParameters(
			themeDisplay, layout, layoutURL, doAsUser);

		return layoutURL;
	}

	public String getLayoutURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getLayoutURL(themeDisplay.getLayout(), themeDisplay);
	}

	public String getLayoutViewPage(Layout layout) {
		LayoutSettings layoutSettings = LayoutSettings.getInstance(
			layout.getType());

		return layoutSettings.getViewPage();
	}

	public String getLayoutViewPage(String type) {
		LayoutSettings layoutSettings = LayoutSettings.getInstance(type);

		return layoutSettings.getViewPage();
	}

	public LiferayPortletRequest getLiferayPortletRequest(
		PortletRequest portletRequest) {

		PortletRequestImpl portletRequestImpl =
			PortletRequestImpl.getPortletRequestImpl(portletRequest);

		return portletRequestImpl;
	}

	public LiferayPortletResponse getLiferayPortletResponse(
		PortletResponse portletResponse) {

		PortletResponseImpl portletResponseImpl =
			PortletResponseImpl.getPortletResponseImpl(portletResponse);

		return portletResponseImpl;
	}

	public Locale getLocale(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			return themeDisplay.getLocale();
		}
		else {
			HttpSession session = request.getSession();

			return (Locale)session.getAttribute(Globals.LOCALE_KEY);
		}
	}

	public Locale getLocale(RenderRequest renderRequest) {
		return getLocale(getHttpServletRequest(renderRequest));
	}

	public String getMailId(String mx, String popPortletPrefix, Object... ids) {
		StringBundler sb = new StringBundler(ids.length * 2 + 7);

		sb.append(StringPool.LESS_THAN);
		sb.append(popPortletPrefix);

		if (!popPortletPrefix.endsWith(StringPool.PERIOD)) {
			sb.append(StringPool.PERIOD);
		}

		for (int i = 0; i < ids.length; i++) {
			Object id = ids[i];

			if (i != 0) {
				sb.append(StringPool.PERIOD);
			}

			sb.append(id);
		}

		sb.append(StringPool.AT);

		if (Validator.isNotNull(PropsValues.POP_SERVER_SUBDOMAIN)) {
			sb.append(PropsValues.POP_SERVER_SUBDOMAIN);
			sb.append(StringPool.PERIOD);
		}

		sb.append(mx);
		sb.append(StringPool.GREATER_THAN);

		return sb.toString();
	}

	public String getNetvibesURL(
			Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _getServletURL(
			portlet, PropsValues.NETVIBES_SERVLET_MAPPING, themeDisplay);
	}

	public String getNewPortletTitle(
		String portletTitle, String oldScopeName, String newScopeName) {

		if (portletTitle.endsWith(" (" + oldScopeName + ")")) {
			int pos = portletTitle.lastIndexOf(" (" + oldScopeName + ")");

			portletTitle = portletTitle.substring(0, pos);
		}

		if (Validator.isNull(newScopeName)) {
			return portletTitle;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(portletTitle);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(newScopeName);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	public HttpServletRequest getOriginalServletRequest(
		HttpServletRequest request) {

		HttpServletRequest originalRequest = request;

		while (originalRequest.getClass().getName().startsWith(
					"com.liferay.")) {

			// Get original request so that portlets inside portlets render
			// properly

			originalRequest = (HttpServletRequest)
				((HttpServletRequestWrapper)originalRequest).getRequest();
		}

		return originalRequest;
	}

	public String getOuterPortletId(HttpServletRequest request) {
		String outerPortletId = (String)request.getAttribute(
			WebKeys.OUTER_PORTLET_ID);

		if (outerPortletId == null) {
			outerPortletId = request.getParameter("p_o_p_id");
		}

		return outerPortletId;
	}

	public long getParentGroupId(long groupId)
		throws PortalException, SystemException {

		if (groupId <= 0) {
			return 0;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		long parentGroupId = groupId;

		if (group.isLayout()) {
			parentGroupId = group.getParentGroupId();
		}

		return parentGroupId;
	}

	public String getPathContext() {
		return _pathContext;
	}

	public String getPathFriendlyURLPrivateGroup() {
		return _pathFriendlyURLPrivateGroup;
	}

	public String getPathFriendlyURLPrivateUser() {
		return _pathFriendlyURLPrivateUser;
	}

	public String getPathFriendlyURLPublic() {
		return _pathFriendlyURLPublic;
	}

	public String getPathImage() {
		return _pathImage;
	}

	public String getPathMain() {
		return _pathMain;
	}

	public String getPathProxy() {
		return _pathProxy;
	}

	public long getPlidFromFriendlyURL(long companyId, String friendlyURL) {
		if (Validator.isNull(friendlyURL)) {
			return LayoutConstants.DEFAULT_PLID;
		}

		String[] urlParts = friendlyURL.split("\\/", 4);

		if ((friendlyURL.charAt(0) != CharPool.SLASH) &&
			(urlParts.length != 4)) {

			return LayoutConstants.DEFAULT_PLID;
		}

		boolean privateLayout = true;

		String urlPrefix = StringPool.SLASH + urlParts[1];

		if (_PUBLIC_GROUP_SERVLET_MAPPING.equals(urlPrefix)) {
			privateLayout = false;
		}
		else if (_PRIVATE_GROUP_SERVLET_MAPPING.equals(urlPrefix) ||
				 _PRIVATE_USER_SERVLET_MAPPING.equals(urlPrefix)) {

			privateLayout = true;
		}
		else {
			return LayoutConstants.DEFAULT_PLID;
		}

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, StringPool.SLASH + urlParts[2]);
		}
		catch (Exception e) {
		}

		if (group != null) {
			Layout layout = null;

			try {
				String layoutFriendlyURL = null;

				if (urlParts.length == 4) {
					layoutFriendlyURL = StringPool.SLASH + urlParts[3];

					layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
						group.getGroupId(), privateLayout, layoutFriendlyURL);
				}
				else {
					List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
						group.getGroupId(), privateLayout,
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, true, 0, 1);

					if (!layouts.isEmpty()) {
						layout = layouts.get(0);
					}
					else {
						return LayoutConstants.DEFAULT_PLID;
					}
				}

				return layout.getPlid();
			}
			catch (Exception e) {
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public long getPlidFromPortletId(
			long groupId, boolean privateLayout, String portletId)
		throws PortalException, SystemException {

		long plid = LayoutConstants.DEFAULT_PLID;

		StringBundler sb = new StringBundler(5);

		sb.append(groupId);
		sb.append(StringPool.SPACE);
		sb.append(privateLayout);
		sb.append(StringPool.SPACE);
		sb.append(portletId);

		String key = sb.toString();

		Long plidObj = _plidToPortletIdMap.get(key);

		if (plidObj == null) {
			plid = _getPlidFromPortletId(groupId, privateLayout, portletId);

			if (plid != LayoutConstants.DEFAULT_PLID) {
				_plidToPortletIdMap.put(key, plid);
			}
		}
		else {
			plid = plidObj.longValue();

			boolean validPlid = false;

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasDefaultScopePortletId(
						groupId, portletId)) {

					validPlid = true;
				}
			}
			catch (Exception e) {
			}

			if (!validPlid) {
				_plidToPortletIdMap.remove(key);

				plid = _getPlidFromPortletId(groupId, privateLayout, portletId);

				if (plid != LayoutConstants.DEFAULT_PLID) {
					_plidToPortletIdMap.put(key, plid);
				}
			}
		}

		return plid;
	}

	public long getPlidFromPortletId(long groupId, String portletId)
		throws PortalException, SystemException {

		long plid = getPlidFromPortletId(groupId, false, portletId);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			plid = getPlidFromPortletId(groupId, true, portletId);
		}

		if (plid == LayoutConstants.DEFAULT_PLID) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Portlet " + portletId +
						" does not exist on a page in group " + groupId);
			}
		}

		return plid;
	}

	public String getPortalLibDir() {
		return PropsValues.LIFERAY_LIB_PORTAL_DIR;
	}

	/**
	 * @deprecated {@link #getPortalPort(boolean)}
	 */
	public int getPortalPort() {
		return _portalPort.get();
	}

	public int getPortalPort(boolean secure) {
		if (secure) {
			return _securePortalPort.get();
		}
		else {
			return _portalPort.get();
		}
	}

	public Properties getPortalProperties() {
		return PropsUtil.getProperties();
	}

	public String getPortalURL(HttpServletRequest request) {
		return getPortalURL(request, isSecure(request));
	}

	public String getPortalURL(HttpServletRequest request, boolean secure) {
		return getPortalURL(
			request.getServerName(), request.getServerPort(), secure);
	}

	public String getPortalURL(PortletRequest portletRequest) {
		return getPortalURL(portletRequest, portletRequest.isSecure());
	}

	public String getPortalURL(PortletRequest portletRequest, boolean secure) {
		return getPortalURL(
			portletRequest.getServerName(), portletRequest.getServerPort(),
			secure);
	}

	public String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		StringBundler sb = new StringBundler();

		if (secure || Http.HTTPS.equals(PropsValues.WEB_SERVER_PROTOCOL)) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		if (Validator.isNull(PropsValues.WEB_SERVER_HOST)) {
			sb.append(serverName);
		}
		else {
			sb.append(PropsValues.WEB_SERVER_HOST);
		}

		if (!secure) {
			if (PropsValues.WEB_SERVER_HTTP_PORT == -1) {
				if ((serverPort != Http.HTTP_PORT) &&
					(serverPort != Http.HTTPS_PORT)) {

					sb.append(StringPool.COLON);
					sb.append(serverPort);
				}
			}
			else {
				if (PropsValues.WEB_SERVER_HTTP_PORT != Http.HTTP_PORT) {
					sb.append(StringPool.COLON);
					sb.append(PropsValues.WEB_SERVER_HTTP_PORT);
				}
			}
		}

		if (secure) {
			if (PropsValues.WEB_SERVER_HTTPS_PORT == -1) {
				if ((serverPort != Http.HTTP_PORT) &&
					(serverPort != Http.HTTPS_PORT)) {

					sb.append(StringPool.COLON);
					sb.append(serverPort);
				}
			}
			else {
				if (PropsValues.WEB_SERVER_HTTPS_PORT != Http.HTTPS_PORT) {
					sb.append(StringPool.COLON);
					sb.append(PropsValues.WEB_SERVER_HTTPS_PORT);
				}
			}
		}

		return sb.toString();
	}

	public String getPortalURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String serverName = themeDisplay.getServerName();

		if (layout == null) {
			layout = themeDisplay.getLayout();
		}

		if (layout != null) {
			LayoutSet layoutSet = layout.getLayoutSet();

			String virtualHostname = layoutSet.getVirtualHostname();

			if (Validator.isNotNull(virtualHostname)) {
				serverName = virtualHostname;
			}
		}

		return getPortalURL(
			serverName, themeDisplay.getServerPort(), themeDisplay.isSecure());
	}

	public String getPortalURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortalURL(null, themeDisplay);
	}

	public String getPortalWebDir() {
		return PropsValues.LIFERAY_WEB_PORTAL_DIR;
	}

	public Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return _portletAddDefaultResourceCheckWhitelist;
	}

	public Set<String> getPortletAddDefaultResourceCheckWhitelistActions() {
		return _portletAddDefaultResourceCheckWhitelistActions;
	}

	/**
	 * @deprecated {@link #getPortletBreadcrumbs(HttpServletRequest)}
	 */
	public List<BreadcrumbEntry> getPortletBreadcrumbList(
		HttpServletRequest request) {

		return getPortletBreadcrumbs(request);
	}

	public List<BreadcrumbEntry> getPortletBreadcrumbs(
		HttpServletRequest request) {

		return (List<BreadcrumbEntry>)request.getAttribute(
			WebKeys.PORTLET_BREADCRUMBS);
	}

	public String getPortletDescription(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		return resourceBundle.getString(
			JavaConstants.JAVAX_PORTLET_DESCRIPTION);
	}

	public String getPortletDescription(Portlet portlet, User user) {
		return getPortletDescription(portlet.getPortletId(), user);
	}

	public String getPortletDescription(String portletId, Locale locale) {
		return LanguageUtil.get(
			locale,
			JavaConstants.JAVAX_PORTLET_DESCRIPTION.concat(
				StringPool.PERIOD).concat(portletId));
	}

	public String getPortletDescription(String portletId, String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return getPortletDescription(portletId, locale);
	}

	public String getPortletDescription(String portletId, User user) {
		return LanguageUtil.get(
			user.getLocale(),
			JavaConstants.JAVAX_PORTLET_DESCRIPTION.concat(
				StringPool.PERIOD).concat(portletId));
	}

	public Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException, SystemException {

		boolean foundFriendlyURLMapper = false;

		String friendlyURL = url;
		String queryString = StringPool.BLANK;

		List<Portlet> portlets =
			PortletLocalServiceUtil.getFriendlyURLMapperPortlets();

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			FriendlyURLMapper friendlyURLMapper =
				portlet.getFriendlyURLMapperInstance();

			if (url.endsWith(
					StringPool.SLASH + friendlyURLMapper.getMapping())) {

				url += StringPool.SLASH;
			}

			int pos = -1;

			if (friendlyURLMapper.isCheckMappingWithPrefix()) {
				pos = url.indexOf(
					FRIENDLY_URL_SEPARATOR + friendlyURLMapper.getMapping() +
						StringPool.SLASH);
			}
			else {
				pos = url.indexOf(
					StringPool.SLASH + friendlyURLMapper.getMapping() +
						StringPool.SLASH);
			}

			if (pos != -1) {
				foundFriendlyURLMapper = true;

				friendlyURL = url.substring(0, pos);

				InheritableMap<String, String[]> actualParams =
					new InheritableMap<String, String[]>();

				if (params != null) {
					actualParams.setParentMap(params);
				}

				Map<String, String> prpIdentifiers =
					new HashMap<String, String>();

				Set<PublicRenderParameter> publicRenderParameters =
					portlet.getPublicRenderParameters();

				for (PublicRenderParameter publicRenderParameter :
						publicRenderParameters) {

					QName qName = publicRenderParameter.getQName();

					String publicRenderParameterIdentifier =
						qName.getLocalPart();
					String publicRenderParameterName =
						PortletQNameUtil.getPublicRenderParameterName(qName);

					prpIdentifiers.put(
						publicRenderParameterIdentifier,
						publicRenderParameterName);
				}

				FriendlyURLMapperThreadLocal.setPRPIdentifiers(prpIdentifiers);

				if (friendlyURLMapper.isCheckMappingWithPrefix()) {
					friendlyURLMapper.populateParams(
						url.substring(pos + 2), actualParams,
						requestContext);
				}
				else {
					friendlyURLMapper.populateParams(
						url.substring(pos), actualParams, requestContext);
				}

				queryString =
					StringPool.AMPERSAND +
						HttpUtil.parameterMapToString(actualParams, false);

				break;
			}
		}

		if (!foundFriendlyURLMapper) {
			int x = url.indexOf(FRIENDLY_URL_SEPARATOR);

			if (x != -1) {
				int y = url.indexOf(CharPool.SLASH, x + 3);

				if (y == -1) {
					y = url.length();
				}

				String ppid = url.substring(x + 3, y);

				if (Validator.isNotNull(ppid)) {
					friendlyURL = url.substring(0, x);

					Map<String, String[]> actualParams = null;

					if (params != null) {
						actualParams = new HashMap<String, String[]>(params);
					}
					else {
						actualParams = new HashMap<String, String[]>();
					}

					actualParams.put("p_p_id", new String[] {ppid});
					actualParams.put("p_p_lifecycle", new String[] {"0"});
					actualParams.put(
						"p_p_state",
						new String[] {WindowState.MAXIMIZED.toString()});
					actualParams.put(
						"p_p_mode", new String[] {PortletMode.VIEW.toString()});

					queryString =
						StringPool.AMPERSAND +
							HttpUtil.parameterMapToString(actualParams, false);
				}
			}
		}

		friendlyURL = StringUtil.replace(
			friendlyURL, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		if (friendlyURL.endsWith(StringPool.SLASH)) {
			friendlyURL = friendlyURL.substring(0, friendlyURL.length() - 1);
		}

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			groupId, privateLayout, friendlyURL);

		return new Object[] {layout, queryString};
	}

	public String getPortletId(HttpServletRequest request) {
		PortletConfigImpl portletConfigImpl =
			(PortletConfigImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfigImpl != null) {
			return portletConfigImpl.getPortletId();
		}
		else {
			return null;
		}
	}

	public String getPortletId(PortletRequest portletRequest) {
		PortletConfigImpl portletConfigImpl =
			(PortletConfigImpl)portletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfigImpl != null) {
			return portletConfigImpl.getPortletId();
		}
		else {
			return null;
		}
	}

	public String getPortletNamespace(String portletId) {
		return StringPool.UNDERLINE.concat(portletId).concat(
			StringPool.UNDERLINE);
	}

	public String getPortletTitle(Portlet portlet, Locale locale) {
		return getPortletTitle(portlet.getPortletId(), locale);
	}

	public String getPortletTitle(Portlet portlet, String languageId) {
		return getPortletTitle(portlet.getPortletId(), languageId);
	}

	public String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		return resourceBundle.getString(JavaConstants.JAVAX_PORTLET_TITLE);
	}

	public String getPortletTitle(Portlet portlet, User user) {
		return getPortletTitle(portlet.getPortletId(), user);
	}

	public String getPortletTitle(RenderResponse renderResponse) {
		PortletResponseImpl portletResponseImpl =
			PortletResponseImpl.getPortletResponseImpl(renderResponse);

		return ((RenderResponseImpl)portletResponseImpl).getTitle();
	}

	public String getPortletTitle(String portletId, Locale locale) {
		return LanguageUtil.get(
			locale,
			JavaConstants.JAVAX_PORTLET_TITLE.concat(StringPool.PERIOD).concat(
				portletId));
	}

	public String getPortletTitle(String portletId, String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return getPortletTitle(portletId, locale);
	}

	public String getPortletTitle(String portletId, User user) {
		return LanguageUtil.get(
			user.getLocale(),
			JavaConstants.JAVAX_PORTLET_TITLE.concat(StringPool.PERIOD).concat(
				portletId));
	}

	public String getPortletXmlFileName() throws SystemException {
		if (PrefsPropsUtil.getBoolean(
				PropsKeys.AUTO_DEPLOY_CUSTOM_PORTLET_XML,
				PropsValues.AUTO_DEPLOY_CUSTOM_PORTLET_XML)) {

			return PORTLET_XML_FILE_NAME_CUSTOM;
		}
		else {
			return PORTLET_XML_FILE_NAME_STANDARD;
		}
	}

	public PortletPreferences getPreferences(HttpServletRequest request) {
		RenderRequest renderRequest = (RenderRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletPreferences portletPreferences = null;

		if (renderRequest != null) {
			PortletPreferencesWrapper portletPreferencesWrapper =
				(PortletPreferencesWrapper)renderRequest.getPreferences();

			portletPreferences =
				portletPreferencesWrapper.getPortletPreferencesImpl();
		}

		return portletPreferences;
	}

	public PreferencesValidator getPreferencesValidator(Portlet portlet) {
		PortletBag portletBag = PortletBagPool.get(portlet.getRootPortletId());

		return portletBag.getPreferencesValidatorInstance();
	}

	public String getRelativeHomeURL(HttpServletRequest request)
		throws PortalException, SystemException {

		Company company = getCompany(request);

		String homeURL = company.getHomeURL();

		if (Validator.isNull(homeURL)) {
			homeURL = PropsValues.COMPANY_DEFAULT_HOME_URL;
		}

		return homeURL;
	}

	public long getScopeGroupId(HttpServletRequest request)
		throws PortalException, SystemException {

		String portletId = getPortletId(request);

		return getScopeGroupId(request, portletId);
	}

	public long getScopeGroupId(HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		long scopeGroupId = 0;

		if (layout != null) {
			Group group = layout.getGroup();

			if (group.isControlPanel()) {
				long doAsGroupId = ParamUtil.getLong(request, "doAsGroupId");

				Group doAsGroup = GroupLocalServiceUtil.fetchGroup(doAsGroupId);

				if ((doAsGroupId <= 0) || (doAsGroup == null)) {
					doAsGroupId = getDefaultScopeGroupId(group.getCompanyId());
				}

				if (doAsGroupId > 0) {
					scopeGroupId = doAsGroupId;
				}

				group = GroupLocalServiceUtil.fetchGroup(scopeGroupId);

				if ((group != null) && group.hasStagingGroup()) {
					try {
						Group stagingGroup = group.getStagingGroup();

						scopeGroupId = stagingGroup.getGroupId();
					}
					catch (Exception e) {
					}
				}
			}

			if ((portletId != null) &&
				(group.isStaged() || group.isStagingGroup())) {

				Group liveGroup = group;

				if (group.isStagingGroup()) {
					liveGroup = group.getLiveGroup();
				}

				if (liveGroup.isStaged() &&
					!liveGroup.isStagedPortlet(portletId)) {

					Layout liveGroupLayout =
						LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
							layout.getUuid(), liveGroup.getGroupId());

					if ((liveGroupLayout != null) &&
						liveGroupLayout.hasScopeGroup()) {

						scopeGroupId = getScopeGroupId(
							liveGroupLayout, portletId);
					}
					else {
						scopeGroupId = liveGroup.getGroupId();
					}
				}
			}
		}

		if (scopeGroupId <= 0) {
			scopeGroupId = getScopeGroupId(layout, portletId);
		}

		return scopeGroupId;
	}

	public long getScopeGroupId(Layout layout) {
		if (layout == null) {
			return 0;
		}
		else {
			return layout.getGroupId();
		}
	}

	public long getScopeGroupId(Layout layout, String portletId) {
		if (layout == null) {
			return 0;
		}

		if (Validator.isNull(portletId)) {
			return layout.getGroupId();
		}

		boolean strict = PortletPreferencesThreadLocal.isStrict();

		PortletPreferencesThreadLocal.setStrict(true);

		try {
			PortletPreferences portletSetup =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletId);

			String scopeType = GetterUtil.getString(
				portletSetup.getValue("lfrScopeType", null));

			if (Validator.isNull(scopeType)) {
				return layout.getGroupId();
			}

			if (scopeType.equals("company")) {
				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					layout.getCompanyId());

				return companyGroup.getGroupId();
			}
			else {
				String scopeLayoutUuid = GetterUtil.getString(
					portletSetup.getValue("lfrScopeLayoutUuid", null));

				Layout scopeLayout =
					LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
						scopeLayoutUuid, layout.getGroupId());

				Group scopeGroup = scopeLayout.getScopeGroup();

				return scopeGroup.getGroupId();
			}
		}
		catch (Exception e) {
			return layout.getGroupId();
		}
		finally {
			PortletPreferencesThreadLocal.setStrict(strict);
		}
	}

	public long getScopeGroupId(long plid) {
		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getLayout(plid);
		}
		catch (Exception e) {
		}

		return getScopeGroupId(layout);
	}

	public long getScopeGroupId(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getScopeGroupId(getHttpServletRequest(portletRequest));
	}

	public User getSelectedUser(HttpServletRequest request)
		throws PortalException, SystemException {

		return getSelectedUser(request, true);
	}

	public User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException, SystemException {

		long userId = ParamUtil.getLong(request, "p_u_i_d");

		User user = null;

		try {
			if (checkPermission) {
				user = UserServiceUtil.getUserById(userId);
			}
			else {
				user = UserLocalServiceUtil.getUserById(userId);
			}
		}
		catch (NoSuchUserException nsue) {
		}

		return user;
	}

	public User getSelectedUser(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getSelectedUser(portletRequest, true);
	}

	public User getSelectedUser(
			PortletRequest portletRequest, boolean checkPermission)
		throws PortalException, SystemException {

		return getSelectedUser(
			getHttpServletRequest(portletRequest), checkPermission);
	}

	public ServletContext getServletContext(
		Portlet portlet, ServletContext servletContext) {

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		PortletContextImpl portletContextImpl =
			(PortletContextImpl)portletConfig.getPortletContext();

		return portletContextImpl.getServletContext();
	}

	public String getSiteLoginURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		if (Validator.isNull(PropsValues.AUTH_LOGIN_SITE_URL)) {
			return null;
		}

		List<Layout> layouts = themeDisplay.getUnfilteredLayouts();

		if (layouts == null) {
			return null;
		}

		for (Layout layout : layouts) {
			String friendlyURL = layout.getFriendlyURL();

			if (friendlyURL.equals(PropsValues.AUTH_LOGIN_SITE_URL)) {
				if (themeDisplay.getLayout() != null) {
					String layoutSetFriendlyURL = getLayoutSetFriendlyURL(
						themeDisplay.getLayout().getLayoutSet(), themeDisplay);

					return layoutSetFriendlyURL +
						PropsValues.AUTH_LOGIN_SITE_URL;
				}

				break;
			}
		}

		return null;
	}

	public String getStaticResourceURL(
		HttpServletRequest request, String uri) {

		return getStaticResourceURL(request, uri, null, 0);
	}

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, long timestamp) {

		return getStaticResourceURL(request, uri, null, timestamp);
	}

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString) {

		return getStaticResourceURL(request, uri, queryString, 0);
	}

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString,
		long timestamp) {

		if (uri.indexOf(CharPool.QUESTION) != -1) {
			return uri;
		}

		if (uri.startsWith(StringPool.DOUBLE_SLASH)) {
			uri = uri.substring(1);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Theme theme = themeDisplay.getTheme();
		ColorScheme colorScheme = themeDisplay.getColorScheme();

		Map<String, String[]> parameterMap = null;

		if (Validator.isNotNull(queryString)) {
			parameterMap = HttpUtil.getParameterMap(queryString);
		}

		StringBundler sb = new StringBundler();

		// URI

		sb.append(uri);
		sb.append(StringPool.QUESTION);

		// Browser id

		if ((parameterMap == null) ||
			(!parameterMap.containsKey("browserId"))) {

			sb.append("&browserId=");
			sb.append(BrowserSnifferUtil.getBrowserId(request));
		}

		// Theme and color scheme

		if ((uri.endsWith(".css") || uri.endsWith(".jsp")) &&
			((parameterMap == null) || !parameterMap.containsKey("themeId"))) {

			sb.append("&themeId=");
			sb.append(theme.getThemeId());
		}

		if (uri.endsWith(".jsp") &&
			((parameterMap == null) ||
			 !parameterMap.containsKey("colorSchemeId"))) {

			sb.append("&colorSchemeId=");
			sb.append(colorScheme.getColorSchemeId());
		}

		// Minifier

		if ((parameterMap == null) ||
			(!parameterMap.containsKey("minifierType"))) {

			String minifierType = StringPool.BLANK;

			if (uri.endsWith(".css") || uri.endsWith("css.jsp") ||
				uri.endsWith("css/main.jsp")) {

				if (themeDisplay.isThemeCssFastLoad()) {
					minifierType = "css";
				}
			}
			else if (themeDisplay.isThemeJsFastLoad()) {
				minifierType = "js";
			}

			if (Validator.isNotNull(minifierType)) {
				sb.append("&minifierType=");
				sb.append(minifierType);
			}
		}

		// Query string

		if (Validator.isNotNull(queryString)) {
			if (!queryString.startsWith(StringPool.AMPERSAND)) {
				sb.append(StringPool.AMPERSAND);
			}

			sb.append(queryString);
		}

		// Language id

		sb.append("&languageId=");
		sb.append(themeDisplay.getLanguageId());

		// Build number

		sb.append("&b=");
		sb.append(ReleaseInfo.getBuildNumber());

		// Timestamp

		if ((parameterMap == null) || !parameterMap.containsKey("t")) {
			if ((timestamp == 0) && uri.startsWith(StrutsUtil.TEXT_HTML_DIR)) {
				ServletContext servletContext =
					(ServletContext)request.getAttribute(WebKeys.CTX);

				timestamp = FileTimestampUtil.getTimestamp(servletContext, uri);
			}

			if (timestamp == 0) {
				timestamp = theme.getTimestamp();
			}

			sb.append("&t=");
			sb.append(timestamp);
		}

		String url = sb.toString();

		url = StringUtil.replace(url, "?&", StringPool.QUESTION);

		return url;
	}

	public String getStrutsAction(HttpServletRequest request) {
		String strutsAction = ParamUtil.getString(request, "struts_action");

		if (Validator.isNotNull(strutsAction)) {

			// This method should only return a Struts action if you're dealing
			// with a regular HTTP servlet request, not a portlet HTTP servlet
			// request.

			return StringPool.BLANK;
		}

		return _getPortletParam(request, "struts_action");
	}

	public String[] getSystemGroups() {
		return _allSystemGroups;
	}

	public String[] getSystemOrganizationRoles() {
		return _allSystemOrganizationRoles;
	}

	public String[] getSystemRoles() {
		return _allSystemRoles;
	}

	public String[] getSystemSiteRoles() {
		return _allSystemSiteRoles;
	}

	public UploadServletRequest getUploadServletRequest(
		HttpServletRequest request) {

		HttpServletRequestWrapper requestWrapper = null;

		if (request instanceof HttpServletRequestWrapper) {
			requestWrapper = (HttpServletRequestWrapper)request;
		}

		UploadServletRequest uploadServletRequest = null;

		while (uploadServletRequest == null) {

			// Find the underlying UploadServletRequest wrapper. For example,
			// WebSphere wraps all requests with ProtectedServletRequest.

			if (requestWrapper instanceof UploadServletRequest) {
				uploadServletRequest = (UploadServletRequest)requestWrapper;
			}
			else {
				HttpServletRequest parentRequest =
					(HttpServletRequest)requestWrapper.getRequest();

				if (!(parentRequest instanceof HttpServletRequestWrapper)) {

					// This block should never be reached unless this method is
					// called from a hot deployable portlet. See LayoutAction.

					uploadServletRequest = new UploadServletRequestImpl(
						parentRequest);

					break;
				}
				else {
					requestWrapper = (HttpServletRequestWrapper)parentRequest;
				}
			}
		}

		return uploadServletRequest;
	}

	public UploadPortletRequest getUploadPortletRequest(
		PortletRequest portletRequest) {

		PortletRequestImpl portletRequestImpl =
			(PortletRequestImpl)portletRequest;

		DynamicServletRequest dynamicRequest =
			(DynamicServletRequest)portletRequestImpl.getHttpServletRequest();

		HttpServletRequestWrapper requestWrapper =
			(HttpServletRequestWrapper)dynamicRequest.getRequest();

		UploadServletRequest uploadServletRequest = getUploadServletRequest(
			requestWrapper);

		return new UploadPortletRequestImpl(
			uploadServletRequest,
			getPortletNamespace(portletRequestImpl.getPortletName()));
	}

	public Date getUptime() {
		return _UP_TIME;
	}

	public String getURLWithSessionId(String url, String sessionId) {
		if (!PropsValues.SESSION_ENABLE_URL_WITH_SESSION_ID) {
			return url;
		}

		if (Validator.isNull(url)) {
			return url;
		}

		// LEP-4787

		int x = url.indexOf(CharPool.SEMICOLON);

		if (x != -1) {
			return url;
		}

		x = url.indexOf(CharPool.QUESTION);

		if (x != -1) {
			StringBundler sb = new StringBundler(4);

			sb.append(url.substring(0, x));
			sb.append(_JSESSIONID);
			sb.append(sessionId);
			sb.append(url.substring(x));

			return sb.toString();
		}

		// In IE6, http://www.abc.com;jsessionid=XYZ does not work, but
		// http://www.abc.com/;jsessionid=XYZ does work.

		x = url.indexOf(StringPool.DOUBLE_SLASH);

		StringBundler sb = new StringBundler(4);

		sb.append(url);

		if (x != -1) {
			int y = url.lastIndexOf(CharPool.SLASH);

			if (x + 1 == y) {
				sb.append(StringPool.SLASH);
			}
		}

		sb.append(_JSESSIONID);
		sb.append(sessionId);

		return sb.toString();
	}

	public User getUser(HttpServletRequest request)
		throws PortalException, SystemException {

		User user = (User)request.getAttribute(WebKeys.USER);

		if (user != null) {
			return user;
		}

		long userId = getUserId(request);

		if (userId <= 0) {

			// Portlet WARs may have the correct remote user and not have the
			// correct user id because the user id is saved in the session
			// and may not be accessible by the portlet WAR's session. This
			// behavior is inconsistent across different application servers.

			String remoteUser = request.getRemoteUser();

			if (remoteUser == null) {
				return null;
			}

			userId = GetterUtil.getLong(remoteUser);
		}

		user = UserLocalServiceUtil.getUserById(userId);

		request.setAttribute(WebKeys.USER, user);

		return user;
	}

	public User getUser(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getUser(getHttpServletRequest(portletRequest));
	}

	public String getUserEmailAddress(long userId) throws SystemException {
		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			return user.getEmailAddress();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	public long getUserId(HttpServletRequest request) {
		Long userIdObj = (Long)request.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			return userIdObj.longValue();
		}

		String path = GetterUtil.getString(request.getPathInfo());
		String strutsAction = getStrutsAction(request);
		String actionName = _getPortletParam(request, "actionName");

		boolean alwaysAllowDoAsUser = false;

		if (path.equals("/portal/session_click") ||
			strutsAction.equals("/document_library/edit_file_entry") ||
			strutsAction.equals("/document_library_display/edit_file_entry") ||
			strutsAction.equals("/image_gallery_display/edit_file_entry") ||
			strutsAction.equals("/image_gallery_display/edit_image") ||
			strutsAction.equals("/wiki/edit_page_attachment") ||
			strutsAction.equals("/wiki_admin/edit_page_attachment") ||
			actionName.equals("addFile")) {

			try {
				alwaysAllowDoAsUser = isAlwaysAllowDoAsUser(request);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if ((!PropsValues.PORTAL_JAAS_ENABLE &&
			  PropsValues.PORTAL_IMPERSONATION_ENABLE) ||
			(alwaysAllowDoAsUser)) {

			String doAsUserIdString = ParamUtil.getString(
				request, "doAsUserId");

			try {
				long doAsUserId = getDoAsUserId(
					request, doAsUserIdString, alwaysAllowDoAsUser);

				if (doAsUserId > 0) {
					if (_log.isDebugEnabled()) {
						_log.debug("Impersonating user " + doAsUserId);
					}

					return doAsUserId;
				}
			}
			catch (Exception e) {
				_log.error("Unable to impersonate user " + doAsUserIdString, e);
			}
		}

		HttpSession session = request.getSession();

		String jRemoteUser = null;

		if (PropsValues.PORTAL_JAAS_ENABLE) {
			jRemoteUser = (String)session.getAttribute("j_remoteuser");
		}

		if (Validator.isNotNull(jRemoteUser)) {
			userIdObj = GetterUtil.getLong(jRemoteUser);
		}
		else {
			userIdObj = (Long)session.getAttribute(WebKeys.USER_ID);
		}

		if (userIdObj != null) {
			request.setAttribute(WebKeys.USER_ID, userIdObj);

			return userIdObj.longValue();
		}
		else {
			return 0;
		}
	}

	public long getUserId(PortletRequest portletRequest) {
		return getUserId(getHttpServletRequest(portletRequest));
	}

	public String getUserName(long userId, String defaultUserName) {
		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_FULL);
	}

	public String getUserName(
		long userId, String defaultUserName, HttpServletRequest request) {

		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_FULL, request);
	}

	public String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getUserName(userId, defaultUserName, userAttribute, null);
	}

	public String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest request) {

		String userName = defaultUserName;

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			if (userAttribute.equals(UserAttributes.USER_NAME_FULL)) {
				userName = user.getFullName();
			}
			else {
				userName = user.getScreenName();
			}

			if (request != null) {
				Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

				PortletURL portletURL = new PortletURLImpl(
					request, PortletKeys.DIRECTORY, layout.getPlid(),
					PortletRequest.RENDER_PHASE);

				portletURL.setWindowState(WindowState.MAXIMIZED);
				portletURL.setPortletMode(PortletMode.VIEW);

				portletURL.setParameter(
					"struts_action", "/directory/view_user");
				portletURL.setParameter(
					"p_u_i_d", String.valueOf(user.getUserId()));

				userName =
					"<a href=\"" + portletURL.toString() + "\">" +
						HtmlUtil.escape(userName) + "</a>";
			}
		}
		catch (Exception e) {
		}

		return userName;
	}

	public String getUserPassword(HttpServletRequest request) {
		HttpSession session = request.getSession();

		return getUserPassword(session);
	}

	public String getUserPassword(HttpSession session) {
		return (String)session.getAttribute(WebKeys.USER_PASSWORD);
	}

	public String getUserPassword(PortletRequest portletRequest) {
		return getUserPassword(getHttpServletRequest(portletRequest));
	}

	public String getUserValue(long userId, String param, String defaultValue)
		throws SystemException {

		if (Validator.isNotNull(defaultValue)) {
			return defaultValue;
		}
		else {
			try {
				User user = UserLocalServiceUtil.getUserById(userId);

				return BeanPropertiesUtil.getString(user, param, defaultValue);
			}
			catch (PortalException pe) {
				return StringPool.BLANK;
			}
		}
	}

	public long getValidUserId(long companyId, long userId)
		throws PortalException, SystemException {

		try {
			User user = UserLocalServiceUtil.getUser(userId);

			if (user.getCompanyId() == companyId) {
				return user.getUserId();
			}
			else {
				return userId;
			}
		}
		catch (NoSuchUserException nsue) {
			return UserLocalServiceUtil.getDefaultUserId(companyId);
		}
	}

	public String getWidgetURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return _getServletURL(
			portlet, PropsValues.WIDGET_SERVLET_MAPPING, themeDisplay);
	}

	public boolean isAllowAddPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();
		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		String portletId = portlet.getPortletId();

		Boolean renderPortletResource = (Boolean)request.getAttribute(
			WebKeys.RENDER_PORTLET_RESOURCE);

		if (renderPortletResource != null) {
			boolean runtimePortlet = renderPortletResource.booleanValue();

			if (runtimePortlet) {
				return true;
			}
		}

		if (layout.isTypePanel()) {
			return true;
		}

		if (layout.isTypeControlPanel() &&
			isControlPanelPortlet(portletId, themeDisplay)) {

			return true;
		}

		if (layout.isTypePortlet()) {
			String checkPortletId = portletId;

			String outerPortletId = getOuterPortletId(request);

			if (outerPortletId != null) {
				checkPortletId = outerPortletId;
			}

			if (layoutTypePortlet.hasPortletId(checkPortletId)) {
				return true;
			}
		}

		if (themeDisplay.isSignedIn() &&
			(portletId.equals(PortletKeys.LAYOUT_CONFIGURATION) ||
			 portletId.equals(PortletKeys.LAYOUTS_ADMIN))) {

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			Group group = layout.getGroup();

			if (group.isSite()) {
				if (LayoutPermissionUtil.contains(
						permissionChecker, layout, ActionKeys.CUSTOMIZE) ||
					LayoutPermissionUtil.contains(
						permissionChecker, layout, ActionKeys.UPDATE)) {

					return true;
				}
			}

			if (group.isCompany()) {
				if (permissionChecker.isCompanyAdmin()) {
					return true;
				}
			}
			else if (group.isLayoutPrototype()) {
				long layoutPrototypeId = group.getClassPK();

				if (LayoutPrototypePermissionUtil.contains(
						permissionChecker, layoutPrototypeId,
						ActionKeys.UPDATE)) {

					return true;
				}
			}
			else if (group.isLayoutSetPrototype()) {
				long layoutSetPrototypeId = group.getClassPK();

				if (LayoutSetPrototypePermissionUtil.contains(
						permissionChecker, layoutSetPrototypeId,
						ActionKeys.UPDATE)) {

					return true;
				}
			}
			else if (group.isOrganization()) {
				long organizationId = group.getOrganizationId();

				if (OrganizationPermissionUtil.contains(
						permissionChecker, organizationId, ActionKeys.UPDATE)) {

					return true;
				}
			}
			else if (group.isUserGroup()) {
				long scopeGroupId = themeDisplay.getScopeGroupId();

				if (GroupPermissionUtil.contains(
						permissionChecker, scopeGroupId, ActionKeys.UPDATE)) {

					return true;
				}
			}
			else if (group.isUser()) {
				return true;
			}
		}

		if (!portlet.isAddDefaultResource()) {
			return false;
		}

		if (!PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED) {
			return true;
		}

		if (_portletAddDefaultResourceCheckWhitelist.contains(portletId)) {
			return true;
		}

		String strutsAction = ParamUtil.getString(request, "struts_action");

		if (_portletAddDefaultResourceCheckWhitelistActions.contains(
				strutsAction)) {

			return true;
		}

		String requestPortletAuthenticationToken = ParamUtil.getString(
			request, "p_p_auth");

		if (Validator.isNull(requestPortletAuthenticationToken)) {
			HttpServletRequest originalRequest = getOriginalServletRequest(
				request);

			requestPortletAuthenticationToken = ParamUtil.getString(
				originalRequest, "p_p_auth");
		}

		if (Validator.isNotNull(requestPortletAuthenticationToken)) {
			String actualPortletAuthenticationToken = AuthTokenUtil.getToken(
				request, layout.getPlid(), portletId);

			if (requestPortletAuthenticationToken.equals(
					actualPortletAuthenticationToken)) {

				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1, renamed to {@link #isGroupAdmin(User, long)}
	 */
	public boolean isCommunityAdmin(User user, long groupId) throws Exception {
		return isGroupAdmin(user, groupId);
	}

	/**
	 * @deprecated As of 6.1, renamed to {@link #isGroupOwner(User, long)}
	 */
	public boolean isCommunityOwner(User user, long groupId) throws Exception {
		return isGroupOwner(user, groupId);
	}

	public boolean isCompanyAdmin(User user) throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user, true);

		return permissionChecker.isCompanyAdmin();
	}

	public boolean isCompanyControlPanelPortlet(
			String portletId, String category, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return true;
		}

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			themeDisplay.getCompanyId());

		themeDisplay.setScopeGroupId(companyGroup.getGroupId());

		return isControlPanelPortlet(portletId, category, themeDisplay);
	}

	public boolean isCompanyControlPanelPortlet(
			String portletId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return true;
		}

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			themeDisplay.getCompanyId());

		themeDisplay.setScopeGroupId(companyGroup.getGroupId());

		return isControlPanelPortlet(portletId, themeDisplay);
	}

	public boolean isCompanyControlPanelVisible(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return true;
		}

		long scopeGroupId = themeDisplay.getScopeGroupId();

		try {
			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				themeDisplay.getCompanyId());

			themeDisplay.setScopeGroupId(companyGroup.getGroupId());

			List<Portlet> controlPanelPortlets = getControlPanelPortlets(
				PortletCategoryKeys.CONTENT, themeDisplay);

			if (!controlPanelPortlets.isEmpty()) {
				return true;
			}
			else {
				return false;
			}
		}
		finally {
			themeDisplay.setScopeGroupId(scopeGroupId);
		}
	}

	public boolean isControlPanelPortlet(
			String portletId, String category, ThemeDisplay themeDisplay)
		throws SystemException {

		List<Portlet> portlets = getControlPanelPortlets(
			category, themeDisplay);

		for (Portlet portlet : portlets) {
			if (portlet.getPortletId().equals(portletId)) {
				return true;
			}
		}

		return false;
	}

	public boolean isControlPanelPortlet(
			String portletId, ThemeDisplay themeDisplay)
		throws SystemException {

		for (String category : PortletCategoryKeys.ALL) {
			if (isControlPanelPortlet(portletId, category, themeDisplay)) {
				return true;
			}
		}

		return false;
	}

	public boolean isSecure(HttpServletRequest request) {
		HttpSession session = request.getSession();

		Boolean httpsInitial = (Boolean)session.getAttribute(
			WebKeys.HTTPS_INITIAL);

		boolean secure = false;

		if ((PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS) &&
			(!PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) &&
			(httpsInitial != null) && (!httpsInitial.booleanValue())) {

			secure = false;
		}
		else {
			secure = request.isSecure();
		}

		return secure;
	}

	public boolean isGroupAdmin(User user, long groupId) throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user, true);

		return permissionChecker.isGroupAdmin(groupId);
	}

	public boolean isGroupOwner(User user, long groupId) throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user, true);

		return permissionChecker.isGroupOwner(groupId);
	}

	public boolean isLayoutDescendant(Layout layout, long layoutId)
		throws PortalException, SystemException {

		if (layout.getLayoutId() == layoutId) {
			return true;
		}
		else {
			for (Layout childLayout : layout.getChildren()) {
				if (isLayoutDescendant(childLayout, layoutId)) {
					return true;
				}
			}

			return false;
		}
	}

	public boolean isLayoutFirstPageable(Layout layout) {
		LayoutSettings layoutSettings = LayoutSettings.getInstance(layout);

		return layoutSettings.isFirstPageable();
	}

	public boolean isLayoutFirstPageable(String type) {
		LayoutSettings layoutSettings = LayoutSettings.getInstance(type);

		return layoutSettings.isFirstPageable();
	}

	public boolean isLayoutFriendliable(Layout layout) {
		LayoutSettings layoutSettings = LayoutSettings.getInstance(layout);

		return layoutSettings.isURLFriendliable();
	}

	public boolean isLayoutFriendliable(String type) {
		LayoutSettings layoutSettings = LayoutSettings.getInstance(type);

		return layoutSettings.isURLFriendliable();
	}

	public boolean isLayoutParentable(Layout layout) {
		return isLayoutParentable(layout.getType());
	}

	public boolean isLayoutParentable(String type) {
		LayoutSettings layoutSettings = LayoutSettings.getInstance(type);

		return layoutSettings.isParentable();
	}

	public boolean isLayoutSitemapable(Layout layout) {
		if (layout.isPrivateLayout()) {
			return false;
		}

		LayoutSettings layoutSettings = LayoutSettings.getInstance(layout);

		return layoutSettings.isSitemapable();
	}

	public boolean isMethodGet(PortletRequest portletRequest) {
		HttpServletRequest request = getHttpServletRequest(portletRequest);

		String method = GetterUtil.getString(request.getMethod());

		if (method.equalsIgnoreCase(HttpMethods.GET)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isMethodPost(PortletRequest portletRequest) {
		HttpServletRequest request = getHttpServletRequest(portletRequest);

		String method = GetterUtil.getString(request.getMethod());

		if (method.equalsIgnoreCase(HttpMethods.POST)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isMultipartRequest(HttpServletRequest request) {
		String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);

		if ((contentType != null) &&
			contentType.startsWith(ContentTypes.MULTIPART_FORM_DATA)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isOmniadmin(long userId) {
		return OmniadminUtil.isOmniadmin(userId);
	}

	public boolean isReservedParameter(String name) {
		return _reservedParams.contains(name);
	}

	public boolean isSystemGroup(String groupName) {
		if (groupName == null) {
			return false;
		}

		groupName = groupName.trim();

		int pos = Arrays.binarySearch(
			_sortedSystemGroups, groupName, new StringComparator());

		if (pos >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSystemRole(String roleName) {
		if (roleName == null) {
			return false;
		}

		roleName = roleName.trim();

		int pos = Arrays.binarySearch(
			_sortedSystemRoles, roleName, new StringComparator());

		if (pos >= 0) {
			return true;
		}
		else {
			pos = Arrays.binarySearch(
				_sortedSystemSiteRoles, roleName, new StringComparator());

			if (pos >= 0) {
				return true;
			}
			else {
				pos = Arrays.binarySearch(
					_sortedSystemOrganizationRoles, roleName,
					new StringComparator());

				if (pos >= 0) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isUpdateAvailable() throws SystemException {
		return PluginPackageUtil.isUpdateAvailable();
	}

	public boolean isValidResourceId(String resourceId) {
		if (Validator.isNull(resourceId)) {
			return true;
		}

		Matcher matcher = _bannedResourceIdPattern.matcher(resourceId);

		if (matcher.matches()) {
			return false;
		}

		return true;
	}

	public void removePortalPortEventListener(
		PortalPortEventListener portalPortEventListener) {

		_portalPortEventListeners.remove(portalPortEventListener);
	}

	public String renderPage(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, String path)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		StringServletResponse stringResponse = new StringServletResponse(
			response);

		requestDispatcher.include(request, stringResponse);

		return stringResponse.getString();
	}

	public String renderPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			boolean writeOutput)
		throws IOException, ServletException {

		return renderPortlet(
			servletContext, request, response, portlet, queryString, null, null,
			null, writeOutput);
	}

	public String renderPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			boolean writeOutput)
		throws IOException, ServletException {

		return renderPortlet(
			servletContext, request, response, portlet, queryString, columnId,
			columnPos, columnCount, null, writeOutput);
	}

	public String renderPortlet(
			ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response, Portlet portlet, String queryString,
			String columnId, Integer columnPos, Integer columnCount,
			String path, boolean writeOutput)
		throws IOException, ServletException {

		queryString = GetterUtil.getString(queryString);
		columnId = GetterUtil.getString(columnId);

		if (columnPos == null) {
			columnPos = Integer.valueOf(0);
		}

		if (columnCount == null) {
			columnCount = Integer.valueOf(0);
		}

		request.setAttribute(WebKeys.RENDER_PORTLET, portlet);
		request.setAttribute(WebKeys.RENDER_PORTLET_QUERY_STRING, queryString);
		request.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_ID, columnId);
		request.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_POS, columnPos);
		request.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_COUNT, columnCount);

		if (path == null) {
			path = "/html/portal/render_portlet.jsp";
		}

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		requestDispatcher.include(request, pipingServletResponse);

		boolean showPortlet = true;

		Boolean portletConfiguratorVisibility = (Boolean)request.getAttribute(
			WebKeys.PORTLET_CONFIGURATOR_VISIBILITY);

		if (portletConfiguratorVisibility != null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			try {
				Layout layout = themeDisplay.getLayout();

				if (!layout.isTypeControlPanel() &&
					!PortletPermissionUtil.contains(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getPlid(), portlet.getPortletId(),
						ActionKeys.CONFIGURATION)) {

					showPortlet = false;
				}
			}
			catch (Exception e) {
				throw new ServletException(e);
			}

			request.removeAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY);
		}

		if (showPortlet) {
			if (writeOutput) {
				response.setContentType(ContentTypes.TEXT_HTML_UTF8);

				StringBundler sb = unsyncStringWriter.getStringBundler();

				sb.writeTo(response.getWriter());

				return StringPool.BLANK;
			}
			else {
				return unsyncStringWriter.toString();
			}
		}
		else {
			return StringPool.BLANK;
		}
	}

	public void resetCDNHosts() {
		_cdnHostHttpMap.clear();
		_cdnHostHttpsMap.clear();

		if (!ClusterInvokeThreadLocal.isEnabled()) {
			return;
		}

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			_resetCDNHostsMethodHandler, true);

		try {
			ClusterExecutorUtil.execute(clusterRequest);
		}
		catch (Exception e) {
			_log.error("Unable to clear cluster wide CDN hosts", e);
		}
	}

	public Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		_portletAddDefaultResourceCheckWhitelist = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST);

		_portletAddDefaultResourceCheckWhitelist = Collections.unmodifiableSet(
			_portletAddDefaultResourceCheckWhitelist);

		return _portletAddDefaultResourceCheckWhitelist;
	}

	public Set<String> resetPortletAddDefaultResourceCheckWhitelistActions() {
		_portletAddDefaultResourceCheckWhitelistActions = SetUtil.fromArray(
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS);

		_portletAddDefaultResourceCheckWhitelistActions =
			Collections.unmodifiableSet(
				_portletAddDefaultResourceCheckWhitelistActions);

		return _portletAddDefaultResourceCheckWhitelistActions;
	}

	public void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		sendError(0, e, actionRequest, actionResponse);
	}

	public void sendError(
			Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		sendError(0, e, request, response);
	}

	public void sendError(
			int status, Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		StringBundler sb = new StringBundler(7);

		sb.append(_pathMain);
		sb.append("/portal/status?status=");
		sb.append(status);
		sb.append("&exception=");
		sb.append(e.getClass().getName());
		sb.append("&previousURL=");
		sb.append(HttpUtil.encodeURL(getCurrentURL(actionRequest)));

		actionResponse.sendRedirect(sb.toString());
	}

	public void sendError(
			int status, Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		if (_log.isInfoEnabled()) {
			String currentURL = (String)request.getAttribute(
				WebKeys.CURRENT_URL);

			_log.info(
				"Current URL " + currentURL + " generates exception: " +
					e.getMessage());
		}

		if (e instanceof NoSuchImageException) {
			if (_logWebServerServlet.isWarnEnabled()) {
				_logWebServerServlet.warn(e, e);
			}
		}
		else if ((e instanceof PortalException) && _log.isInfoEnabled()) {
			if ((e instanceof NoSuchLayoutException) ||
				(e instanceof PrincipalException)) {

				String msg = e.getMessage();

				if (Validator.isNotNull(msg)) {
					_log.info(msg);
				}
			}
			else {
				_log.info(e, e);
			}
		}
		else if ((e instanceof SystemException) && _log.isWarnEnabled()) {
			_log.warn(e, e);
		}

		if (response.isCommitted()) {
			return;
		}

		if (status == 0) {
			if (e instanceof PrincipalException) {
				status = HttpServletResponse.SC_FORBIDDEN;
			}
			else {
				String name = e.getClass().getName();

				name = name.substring(name.lastIndexOf(CharPool.PERIOD) + 1);

				if (name.startsWith("NoSuch") && name.endsWith("Exception")) {
					status = HttpServletResponse.SC_NOT_FOUND;
				}
			}

			if (status == 0) {

				// LPS-5352

				if (PropsValues.TCK_URL) {
					status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
				}
				else {
					status = HttpServletResponse.SC_BAD_REQUEST;
				}
			}
		}

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		String redirect = PATH_MAIN + "/portal/status";

		if (e instanceof NoSuchLayoutException &&
			Validator.isNotNull(
				PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND)) {

			response.setStatus(status);

			redirect = PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND;

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else if (PropsValues.LAYOUT_SHOW_HTTP_STATUS) {
			response.setStatus(status);

			SessionErrors.add(request, e.getClass().getName(), e);

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else {
			if (e != null) {
				response.sendError(status, e.getMessage());
			}
			else {
				response.sendError(status);
			}
		}
	}

	public void setPageDescription(
		String description, HttpServletRequest request) {

		request.setAttribute(WebKeys.PAGE_DESCRIPTION, description);
	}

	public void setPageKeywords(String keywords, HttpServletRequest request) {
		request.removeAttribute(WebKeys.PAGE_KEYWORDS);

		addPageKeywords(keywords, request);
	}

	public void setPageSubtitle(String subtitle, HttpServletRequest request) {
		request.setAttribute(WebKeys.PAGE_SUBTITLE, subtitle);
	}

	public void setPageTitle(String title, HttpServletRequest request) {
		request.setAttribute(WebKeys.PAGE_TITLE, title);
	}

	public void setPortalPort(HttpServletRequest request) {
		if (request.isSecure()) {
			if (_securePortalPort.get() == -1) {
				int securePortalPort = request.getServerPort();

				_securePortalPort.compareAndSet(-1, securePortalPort);
			}
		}
		else {
			if (_portalPort.get() == -1) {
				int portalPort = request.getServerPort();

				if (_portalPort.compareAndSet(-1, portalPort)) {
					notifyPortalPortEventListeners(portalPort);
				}
			}
		}
	}

	public void storePreferences(PortletPreferences portletPreferences)
		throws IOException, ValidatorException {

		PortletPreferencesWrapper portletPreferencesWrapper =
			(PortletPreferencesWrapper)portletPreferences;

		PortletPreferencesImpl portletPreferencesImpl =
			portletPreferencesWrapper.getPortletPreferencesImpl();

		portletPreferencesImpl.store();
	}

	public String[] stripURLAnchor(String url, String separator) {
		String anchor = StringPool.BLANK;

		int pos = url.indexOf(separator);

		if (pos != -1) {
			anchor = url.substring(pos);
			url = url.substring(0, pos);
		}

		return new String[] {url, anchor};
	}

	public String transformCustomSQL(String sql) {
		if ((_customSqlKeys == null) || (_customSqlValues == null)) {
			_initCustomSQL();
		}

		return StringUtil.replace(sql, _customSqlKeys, _customSqlValues);
	}

	public PortletMode updatePortletMode(
		String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request) {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if (portletMode == null || Validator.isNull(portletMode.toString())) {
			if (layoutType.hasModeAboutPortletId(portletId)) {
				return LiferayPortletMode.ABOUT;
			}
			else if (layoutType.hasModeConfigPortletId(portletId)) {
				return LiferayPortletMode.CONFIG;
			}
			else if (layoutType.hasModeEditPortletId(portletId)) {
				return PortletMode.EDIT;
			}
			else if (layoutType.hasModeEditDefaultsPortletId(portletId)) {
				return LiferayPortletMode.EDIT_DEFAULTS;
			}
			else if (layoutType.hasModeEditGuestPortletId(portletId)) {
				return LiferayPortletMode.EDIT_GUEST;
			}
			else if (layoutType.hasModeHelpPortletId(portletId)) {
				return PortletMode.HELP;
			}
			else if (layoutType.hasModePreviewPortletId(portletId)) {
				return LiferayPortletMode.PREVIEW;
			}
			else if (layoutType.hasModePrintPortletId(portletId)) {
				return LiferayPortletMode.PRINT;
			}
			else {
				return PortletMode.VIEW;
			}
		}
		else {
			boolean updateLayout = false;

			if (portletMode.equals(LiferayPortletMode.ABOUT) &&
				!layoutType.hasModeAboutPortletId(portletId)) {

				layoutType.addModeAboutPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.CONFIG) &&
					 !layoutType.hasModeConfigPortletId(portletId)) {

				layoutType.addModeConfigPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.EDIT) &&
					 !layoutType.hasModeEditPortletId(portletId)) {

				layoutType.addModeEditPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.EDIT_DEFAULTS) &&
					 !layoutType.hasModeEditDefaultsPortletId(portletId)) {

				layoutType.addModeEditDefaultsPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.EDIT_GUEST) &&
					 !layoutType.hasModeEditGuestPortletId(portletId)) {

				layoutType.addModeEditGuestPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.HELP) &&
					 !layoutType.hasModeHelpPortletId(portletId)) {

				layoutType.addModeHelpPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.PREVIEW) &&
					 !layoutType.hasModePreviewPortletId(portletId)) {

				layoutType.addModePreviewPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.PRINT) &&
					 !layoutType.hasModePrintPortletId(portletId)) {

				layoutType.addModePrintPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.VIEW) &&
					 !layoutType.hasModeViewPortletId(portletId)) {

				layoutType.removeModesPortletId(portletId);

				updateLayout = true;
			}

			if (updateLayout) {
				LayoutClone layoutClone = LayoutCloneFactory.getInstance();

				if (layoutClone != null) {
					layoutClone.update(
						request, layout.getPlid(), layout.getTypeSettings());
				}
			}

			return portletMode;
		}
	}

	public String updateRedirect(
		String redirect, String oldPath, String newPath) {

		if (Validator.isNotNull(redirect) && (oldPath != null) &&
			!oldPath.equals(newPath)) {

			redirect = StringUtil.replace(redirect, oldPath, newPath);
			redirect = StringUtil.replace(
				redirect, HttpUtil.encodeURL(oldPath),
				HttpUtil.encodeURL(newPath));
		}

		return redirect;
	}

	public WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request) {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if ((windowState == null) ||
			(Validator.isNull(windowState.toString()))) {

			if (layoutType.hasStateMaxPortletId(portletId)) {
				windowState = WindowState.MAXIMIZED;
			}
			else if (layoutType.hasStateMinPortletId(portletId)) {
				windowState = WindowState.MINIMIZED;
			}
			else {
				windowState = WindowState.NORMAL;
			}
		}
		else {
			boolean updateLayout = false;

			if (windowState.equals(WindowState.MAXIMIZED) &&
				!layoutType.hasStateMaxPortletId(portletId)) {

				layoutType.addStateMaxPortletId(portletId);

				if (PropsValues.LAYOUT_REMEMBER_MAXIMIZED_WINDOW_STATE) {
					updateLayout = true;
				}
			}
			else if (windowState.equals(WindowState.MINIMIZED) &&
					 !layoutType.hasStateMinPortletId(portletId)) {

				layoutType.addStateMinPortletId(portletId);

				updateLayout = true;
			}
			else if (windowState.equals(WindowState.NORMAL) &&
					 !layoutType.hasStateNormalPortletId(portletId)) {

				layoutType.removeStatesPortletId(portletId);

				updateLayout = true;
			}

			if (portletId.equals(PortletKeys.LAYOUTS_ADMIN) ||
				portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {

				updateLayout = false;
			}

			if (updateLayout) {
				LayoutClone layoutClone = LayoutCloneFactory.getInstance();

				if (layoutClone != null) {
					layoutClone.update(
						request, layout.getPlid(), layout.getTypeSettings());
				}
			}
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		themeDisplay.setStateExclusive(
			windowState.equals(LiferayWindowState.EXCLUSIVE));
		themeDisplay.setStateMaximized(
			windowState.equals(WindowState.MAXIMIZED));
		themeDisplay.setStatePopUp(
			windowState.equals(LiferayWindowState.POP_UP));

		if (themeDisplay.isStateMaximized() &&
			themeDisplay.isShowAddContentIcon()) {

			themeDisplay.setShowAddContentIcon(false);
		}
		else if (!themeDisplay.isStateMaximized() &&
				 !themeDisplay.isShowAddContentIcon() &&
				 themeDisplay.isShowAddContentIconPermission()) {

			themeDisplay.setShowAddContentIcon(true);
		}

		request.setAttribute(WebKeys.WINDOW_STATE, windowState);

		return windowState;
	}

	protected void addDefaultResource(
			ThemeDisplay themeDisplay, Layout layout, Portlet portlet,
			boolean portletActions)
		throws PortalException, SystemException {

		String rootPortletId = portlet.getRootPortletId();

		String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(
			layout.getPlid(), portlet.getPortletId());

		String name = null;
		String primaryKey = null;

		if (portletActions) {
			name = rootPortletId;
			primaryKey = portletPrimaryKey;
		}
		else {
			name = ResourceActionsUtil.getPortletBaseResource(rootPortletId);
			primaryKey = String.valueOf(
				getScopeGroupId(layout, portlet.getPortletId()));
		}

		if (Validator.isNull(name)) {
			return;
		}

		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				int count =
					ResourcePermissionLocalServiceUtil.
						getResourcePermissionsCount(
							themeDisplay.getCompanyId(), name,
							ResourceConstants.SCOPE_INDIVIDUAL,
							primaryKey);

				if (count == 0) {
					throw new NoSuchResourceException();
				}
			}
			else if (!portlet.isUndeployedPortlet()) {
				ResourceLocalServiceUtil.getResource(
					themeDisplay.getCompanyId(), name,
					ResourceConstants.SCOPE_INDIVIDUAL, primaryKey);
			}
		}
		catch (NoSuchResourceException nsre) {
			ResourceLocalServiceUtil.addResources(
				themeDisplay.getCompanyId(), layout.getGroupId(), 0, name,
				primaryKey, portletActions, true, true);
		}
	}

	protected List<Portlet> filterControlPanelPortlets(
		Set<Portlet> portlets, String category, ThemeDisplay themeDisplay) {

		Group group = themeDisplay.getScopeGroup();

		List<Portlet> filteredPortlets = new ArrayList<Portlet>();

		if (category.equals(PortletCategoryKeys.CONTENT) && group.isLayout()) {
			for (Portlet portlet : portlets) {
				if (portlet.isScopeable()) {
					filteredPortlets.add(portlet);
				}
			}
		}
		else {
			filteredPortlets.addAll(portlets);
		}

		Iterator<Portlet> itr = filteredPortlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			try {
				ControlPanelEntry controlPanelEntry =
					portlet.getControlPanelEntryInstance();

				if (controlPanelEntry == null) {
					controlPanelEntry =
						DefaultControlPanelEntryFactory.getInstance();
				}

				if (!controlPanelEntry.isVisible(
						portlet, category, themeDisplay)) {

					itr.remove();
				}
			}
			catch (Exception e) {
				_log.error(e, e);

				itr.remove();
			}
		}

		return filteredPortlets;
	}

	protected long getDefaultScopeGroupId(long companyId)
		throws PortalException , SystemException {

		long doAsGroupId = 0;

		Collection<Portlet> portlets = getControlPanelPortlets(
			companyId, PortletCategoryKeys.CONTENT);

		List<Group> groups = GroupServiceUtil.getManageableSites(portlets, 1);

		if (!groups.isEmpty()) {
			Group group = groups.get(0);

			doAsGroupId = group.getGroupId();
		}
		else {
			Group guestGroup = GroupLocalServiceUtil.fetchGroup(
				companyId, GroupConstants.GUEST);

			if (guestGroup != null) {
				doAsGroupId = guestGroup.getGroupId();
			}
		}

		return doAsGroupId;
	}

	protected long getDoAsUserId(
			HttpServletRequest request, String doAsUserIdString,
			boolean alwaysAllowDoAsUser)
		throws Exception {

		if (Validator.isNull(doAsUserIdString)) {
			return 0;
		}

		long doAsUserId = 0;

		try {
			Company company = getCompany(request);

			doAsUserId = GetterUtil.getLong(
				Encryptor.decrypt(company.getKeyObj(), doAsUserIdString));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to impersonate " + doAsUserIdString +
						" because the string cannot be decrypted",
					e);
			}

			return 0;
		}

		if (_log.isDebugEnabled()) {
			if (alwaysAllowDoAsUser) {
				_log.debug(
					"doAsUserId path or Struts action is always allowed");
			}
			else {
				_log.debug(
					"doAsUserId path is Struts action not always allowed");
			}
		}

		if (alwaysAllowDoAsUser) {
			request.setAttribute(WebKeys.USER_ID, new Long(doAsUserId));

			return doAsUserId;
		}

		HttpSession session = request.getSession();

		Long realUserIdObj = (Long)session.getAttribute(WebKeys.USER_ID);

		if (realUserIdObj == null) {
			return 0;
		}

		User doAsUser = UserLocalServiceUtil.getUserById(doAsUserId);

		long[] organizationIds = doAsUser.getOrganizationIds();

		User realUser = UserLocalServiceUtil.getUserById(
			realUserIdObj.longValue());
		boolean checkGuest = true;

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(realUser, checkGuest);

		if (doAsUser.isDefaultUser() ||
			UserPermissionUtil.contains(
				permissionChecker, doAsUserId, organizationIds,
				ActionKeys.IMPERSONATE)) {

			request.setAttribute(WebKeys.USER_ID, new Long(doAsUserId));

			return doAsUserId;
		}
		else {
			_log.error(
				"User " + realUserIdObj + " does not have the permission " +
					"to impersonate " + doAsUserId);

			return 0;
		}
	}

	protected boolean isAlwaysAllowDoAsUser(HttpServletRequest request)
		throws Exception {

		String ticketKey = ParamUtil.getString(request, "ticketKey");

		if (Validator.isNull(ticketKey)) {
			return false;
		}

		Ticket ticket = TicketLocalServiceUtil.fetchTicket(ticketKey);

		if (ticket == null) {
			return false;
		}

		String className = ticket.getClassName();

		if (!className.equals(User.class.getName())) {
			return false;
		}

		long doAsUserId = 0;

		try {
			Company company = getCompany(request);

			String doAsUserIdString = ParamUtil.getString(
				request, "doAsUserId");

			if (Validator.isNotNull(doAsUserIdString)) {
				doAsUserId = GetterUtil.getLong(
					Encryptor.decrypt(company.getKeyObj(), doAsUserIdString));
			}
		}
		catch (Exception e) {
			return false;
		}

		if ((ticket.getClassPK() != doAsUserId) ||
			(ticket.getType() != TicketConstants.TYPE_IMPERSONATE)) {

			return false;
		}

		if (ticket.isExpired()) {
			TicketLocalServiceUtil.deleteTicket(ticket);

			return false;
		}

		Date expirationDate = new Date(
			System.currentTimeMillis() +
				PropsValues.SESSION_TIMEOUT * Time.MINUTE);

		ticket.setExpirationDate(expirationDate);

		TicketLocalServiceUtil.updateTicket(ticket, false);

		return true;
	}

	protected void notifyPortalPortEventListeners(int portalPort) {
		for (PortalPortEventListener portalPortEventListener :
				_portalPortEventListeners) {

			portalPortEventListener.portalPortConfigured(portalPort);
		}
	}

	private long _getPlidFromPortletId(
			long groupId, boolean privateLayout, String portletId)
		throws PortalException, SystemException {

		long scopeGroupId = groupId;

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isLayout()) {
				Layout scopeLayout = LayoutLocalServiceUtil.getLayout(
					group.getClassPK());

				groupId = scopeLayout.getGroupId();
			}
		}
		catch (Exception e) {
		}

		long plid = LayoutConstants.DEFAULT_PLID;

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, LayoutConstants.TYPE_PORTLET);

		for (Layout layout : layouts) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (layoutTypePortlet.hasPortletId(portletId)) {
				if (getScopeGroupId(layout, portletId) == scopeGroupId) {
					plid = layout.getPlid();

					break;
				}
			}
		}

		return plid;
	}

	private String _getPortletParam(HttpServletRequest request, String name) {
		String portletId = ParamUtil.getString(request, "p_p_id");

		if (Validator.isNull(portletId)) {
			return StringPool.BLANK;
		}

		String value = null;

		int valueCount = 0;

		String keyName = StringPool.UNDERLINE.concat(name);

		Map<String, String[]> parameterMap = request.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();

			int pos = parameterName.indexOf(keyName);

			if (pos == -1) {
				continue;
			}

			valueCount++;

			// There should never be more than one value

			if (valueCount > 1) {
				return StringPool.BLANK;
			}

			String[] parameterValues = entry.getValue();

			if ((parameterValues == null) || (parameterValues.length == 0) ||
				Validator.isNull(parameterValues[0])) {

				continue;
			}

			// The Struts action must be for the correct portlet

			String portletId1 = parameterName.substring(1, pos);

			if (portletId.equals(portletId1)) {
				value = parameterValues[0];
			}
		}

		if (value == null) {
			value = StringPool.BLANK;
		}

		return value;
	}

	private String _getServletURL(
			Portlet portlet, String servletPath, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Layout layout = themeDisplay.getLayout();

		StringBundler sb = new StringBundler();

		sb.append(themeDisplay.getPortalURL());

		if (Validator.isNotNull(_pathContext)) {
			sb.append(_pathContext);
		}

		if (themeDisplay.isI18n()) {
			sb.append(themeDisplay.getI18nPath());
		}

		sb.append(servletPath);

		Group group = layout.getGroup();

		if (layout.isPrivateLayout()) {
			if (group.isUser()) {
				sb.append(_PRIVATE_USER_SERVLET_MAPPING);
			}
			else {
				sb.append(_PRIVATE_GROUP_SERVLET_MAPPING);
			}
		}
		else {
			sb.append(_PUBLIC_GROUP_SERVLET_MAPPING);
		}

		sb.append(group.getFriendlyURL());
		sb.append(layout.getFriendlyURL());

		sb.append(FRIENDLY_URL_SEPARATOR);

		FriendlyURLMapper friendlyURLMapper =
			portlet.getFriendlyURLMapperInstance();

		if ((friendlyURLMapper != null) && !portlet.isInstanceable()) {
			sb.append(friendlyURLMapper.getMapping());
		}
		else {
			sb.append(portlet.getPortletId());
		}

		return sb.toString();
	}

	private void _initCustomSQL() {
		_customSqlKeys = new String[] {
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.GROUP$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.LAYOUT$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.ORGANIZATION$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.ROLE$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.USER$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.USERGROUP$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.BLOGS.MODEL.BLOGSENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.BOOKMARKS.MODEL." +
				"BOOKMARKSENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.CALENDAR.MODEL.CALEVENT$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.DOCUMENTLIBRARY.MODEL." +
				"DLFILEENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.MESSAGEBOARDS.MODEL." +
				"MBMESSAGE$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.MESSAGEBOARDS.MODEL." +
				"MBTHREAD$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.WIKI.MODEL.WIKIPAGE$]",
			"[$RESOURCE_SCOPE_COMPANY$]",
			"[$RESOURCE_SCOPE_GROUP$]",
			"[$RESOURCE_SCOPE_GROUP_TEMPLATE$]",
			"[$RESOURCE_SCOPE_INDIVIDUAL$]",
			"[$SOCIAL_RELATION_TYPE_BI_COWORKER$]",
			"[$SOCIAL_RELATION_TYPE_BI_FRIEND$]",
			"[$SOCIAL_RELATION_TYPE_BI_ROMANTIC_PARTNER$]",
			"[$SOCIAL_RELATION_TYPE_BI_SIBLING$]",
			"[$SOCIAL_RELATION_TYPE_BI_SPOUSE$]",
			"[$SOCIAL_RELATION_TYPE_UNI_CHILD$]",
			"[$SOCIAL_RELATION_TYPE_UNI_ENEMY$]",
			"[$SOCIAL_RELATION_TYPE_UNI_FOLLOWER$]",
			"[$SOCIAL_RELATION_TYPE_UNI_PARENT$]",
			"[$SOCIAL_RELATION_TYPE_UNI_SUBORDINATE$]",
			"[$SOCIAL_RELATION_TYPE_UNI_SUPERVISOR$]",
			"[$FALSE$]",
			"[$TRUE$]"
		};

		DB db = DBFactoryUtil.getDB();

		Object[] customSqlValues = new Object[] {
			getClassNameId(Group.class),
			getClassNameId(Layout.class),
			getClassNameId(Organization.class),
			getClassNameId(Role.class),
			getClassNameId(User.class),
			getClassNameId(UserGroup.class),
			getClassNameId(BlogsEntry.class),
			getClassNameId(BookmarksEntry.class),
			getClassNameId(CalEvent.class),
			getClassNameId(DLFileEntry.class),
			getClassNameId(MBMessage.class),
			getClassNameId(MBThread.class),
			getClassNameId(WikiPage.class),
			ResourceConstants.SCOPE_COMPANY,
			ResourceConstants.SCOPE_GROUP,
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			ResourceConstants.SCOPE_INDIVIDUAL,
			SocialRelationConstants.TYPE_BI_COWORKER,
			SocialRelationConstants.TYPE_BI_FRIEND,
			SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER,
			SocialRelationConstants.TYPE_BI_SIBLING,
			SocialRelationConstants.TYPE_BI_SPOUSE,
			SocialRelationConstants.TYPE_UNI_CHILD,
			SocialRelationConstants.TYPE_UNI_ENEMY,
			SocialRelationConstants.TYPE_UNI_FOLLOWER,
			SocialRelationConstants.TYPE_UNI_PARENT,
			SocialRelationConstants.TYPE_UNI_SUBORDINATE,
			SocialRelationConstants.TYPE_UNI_SUPERVISOR,
			db.getTemplateFalse(),
			db.getTemplateTrue()
		};

		_customSqlValues = ArrayUtil.toStringArray(customSqlValues);
	}

	private static final String _J_SECURITY_CHECK = "j_security_check";

	private static final String _JSESSIONID = ";jsessionid=";

	private static final String _LOCALHOST = "localhost";

	private static final String  _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static final Date _UP_TIME = new Date();

	private static Log _log = LogFactoryUtil.getLog(PortalImpl.class);

	private static Log _logWebServerServlet = LogFactoryUtil.getLog(
		WebServerServlet.class);

	private static MethodHandler _resetCDNHostsMethodHandler =
		new MethodHandler(
			new MethodKey(PortalUtil.class.getName(), "resetCDNHosts"));

	private String[] _allSystemGroups;
	private String[] _allSystemOrganizationRoles;
	private String[] _allSystemRoles;
	private String[] _allSystemSiteRoles;
	private Set<String> _authTokenIgnoreActions;
	private Set<String> _authTokenIgnorePortlets;
	private Pattern _bannedResourceIdPattern = Pattern.compile(
		PropsValues.PORTLET_RESOURCE_ID_BANNED_PATHS_REGEXP,
		Pattern.CASE_INSENSITIVE);
	private static Map<Long, String> _cdnHostHttpMap =
		new ConcurrentHashMap<Long, String>();
	private static Map<Long, String> _cdnHostHttpsMap =
		new ConcurrentHashMap<Long, String>();
	private String _computerAddress;
	private String _computerName;
	private String[] _customSqlKeys;
	private String[] _customSqlValues;
	private String _pathContext;
	private String _pathFriendlyURLPrivateGroup;
	private String _pathFriendlyURLPrivateUser;
	private String _pathFriendlyURLPublic;
	private String _pathImage;
	private String _pathMain;
	private String _pathProxy;
	private Map<String, Long> _plidToPortletIdMap =
		new ConcurrentHashMap<String, Long>();
	private final AtomicInteger _portalPort = new AtomicInteger(-1);
	private List<PortalPortEventListener> _portalPortEventListeners =
		new ArrayList<PortalPortEventListener>();
	private Set<String> _portletAddDefaultResourceCheckWhitelist;
	private Set<String> _portletAddDefaultResourceCheckWhitelistActions;
	private Set<String> _reservedParams;
	private final AtomicInteger _securePortalPort = new AtomicInteger(-1);
	private String[] _sortedSystemGroups;
	private String[] _sortedSystemOrganizationRoles;
	private String[] _sortedSystemRoles;
	private String[] _sortedSystemSiteRoles;

}
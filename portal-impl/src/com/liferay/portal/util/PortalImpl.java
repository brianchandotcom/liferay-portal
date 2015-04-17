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

package com.liferay.portal.util;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.ImageTypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperThreadLocal;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.security.auth.AlwaysAllowDoAsUser;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.NonSerializableObjectRequestWrapper;
import com.liferay.portal.kernel.servlet.PersistentHttpServletRequestWrapper;
import com.liferay.portal.kernel.servlet.PortalMessages;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.DeterminateKeyGenerator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListMergeable;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutFriendlyURLComposite;
import com.liferay.portal.model.LayoutQueryStringComposite;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.LayoutTypeController;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.TicketConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.model.VirtualLayoutConstants;
import com.liferay.portal.model.impl.CookieRemotePreference;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.auth.AuthTokenWhitelistUtil;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.security.sso.SSOUtil;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.TicketLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.service.permission.LayoutSetPrototypePermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.servlet.filters.i18n.I18nFilter;
import com.liferay.portal.servlet.filters.secure.NonceUtil;
import com.liferay.portal.spring.context.PortalContextLoaderListener;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.comparator.PortletControlPanelWeightComparator;
import com.liferay.portal.webserver.WebServerServlet;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;
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
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.documentlibrary.ImageSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.expando.ValueDataException;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.journal.NoSuchFeedException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.login.util.LoginUtil;
import com.liferay.portlet.messageboards.action.EditDiscussionAction;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.sites.util.Sites;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.util.FacebookUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.util.Encryptor;
import com.liferay.util.JS;

import java.awt.image.RenderedImage;

import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
@DoPrivileged
public class PortalImpl implements Portal {

	public PortalImpl() {

		// Computer name

		String computerName = System.getProperty("env.COMPUTERNAME");

		if (Validator.isNull(computerName)) {
			computerName = System.getProperty("env.HOST");
		}

		if (Validator.isNull(computerName)) {
			computerName = System.getProperty("env.HOSTNAME");
		}

		if (Validator.isNull(computerName)) {
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();

				computerName = inetAddress.getHostName();
			}
			catch (UnknownHostException uhe) {
			}
		}

		_computerName = computerName;

		String computerAddress = null;

		try {
			InetAddress inetAddress = InetAddress.getByName(_computerName);

			computerAddress = inetAddress.getHostAddress();
		}
		catch (UnknownHostException uhe) {
		}

		if (Validator.isNull(computerAddress)) {
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();

				computerAddress = inetAddress.getHostAddress();
			}
			catch (UnknownHostException uhe) {
			}
		}

		_computerAddress = computerAddress;

		// Paths

		_pathProxy = PropsValues.PORTAL_PROXY_PATH;

		_pathContext = _pathProxy.concat(
			ContextPathUtil.getContextPath(
				PortalContextLoaderListener.getPortalServletContextPath()));

		_pathFriendlyURLPrivateGroup =
			_pathContext + _PRIVATE_GROUP_SERVLET_MAPPING;
		_pathFriendlyURLPrivateUser =
			_pathContext + _PRIVATE_USER_SERVLET_MAPPING;
		_pathFriendlyURLPublic = _pathContext + _PUBLIC_GROUP_SERVLET_MAPPING;
		_pathImage = _pathContext + PATH_IMAGE;
		_pathMain = _pathContext + PATH_MAIN;
		_pathModule = _pathContext + PATH_MODULE;

		// Groups

		String[] customSystemGroups = PropsUtil.getArray(
			PropsKeys.SYSTEM_GROUPS);

		if (ArrayUtil.isEmpty(customSystemGroups)) {
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

		if (ArrayUtil.isEmpty(customSystemRoles)) {
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

		if (ArrayUtil.isEmpty(customSystemOrganizationRoles)) {
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

		if (ArrayUtil.isEmpty(customSystemSiteRoles)) {
			_allSystemSiteRoles = RoleConstants.SYSTEM_SITE_ROLES;
		}
		else {
			_allSystemSiteRoles = ArrayUtil.append(
				RoleConstants.SYSTEM_SITE_ROLES, customSystemSiteRoles);
		}

		_sortedSystemSiteRoles = new String[_allSystemSiteRoles.length];

		System.arraycopy(
			_allSystemSiteRoles, 0, _sortedSystemSiteRoles, 0,
			_allSystemSiteRoles.length);

		Arrays.sort(_sortedSystemSiteRoles, new StringComparator());

		// Reserved parameter names

		_reservedParams = new HashSet<>();

		// Portal authentication

		_reservedParams.add("p_auth");
		_reservedParams.add("p_auth_secret");

		// Portal layout

		_reservedParams.add("p_l_id");
		_reservedParams.add("p_l_reset");

		// Portal portlet

		_reservedParams.add("p_p_auth");
		_reservedParams.add("p_p_id");
		_reservedParams.add("p_p_i_id");
		_reservedParams.add("p_p_lifecycle");
		_reservedParams.add("p_p_url_type");
		_reservedParams.add("p_p_state");
		_reservedParams.add("p_p_state_rcv"); // LPS-14144
		_reservedParams.add("p_p_mode");
		_reservedParams.add("p_p_resource_id");
		_reservedParams.add("p_p_cacheability");
		_reservedParams.add("p_p_width");
		_reservedParams.add("p_p_col_id");
		_reservedParams.add("p_p_col_pos");
		_reservedParams.add("p_p_col_count");
		_reservedParams.add("p_p_boundary");
		_reservedParams.add("p_p_decorate");
		_reservedParams.add("p_p_static");
		_reservedParams.add("p_p_isolated");

		// Portal theme

		_reservedParams.add("p_t_lifecycle"); // LPS-14383

		// Portal virtual layout

		_reservedParams.add("p_v_l_s_g_id"); // LPS-23010

		// Portal fragment

		_reservedParams.add("p_f_id");

		// Portal journal article

		_reservedParams.add("p_j_a_id"); // LPS-16418

		// Miscellaneous

		_reservedParams.add("saveLastPath");
		_reservedParams.add("scroll");
		_reservedParams.add("switchGroup");

		_servletContextName =
			PortalContextLoaderListener.getPortalServletContextName();

		if (ArrayUtil.isEmpty(PropsValues.VIRTUAL_HOSTS_VALID_HOSTS) ||
			ArrayUtil.contains(
				PropsValues.VIRTUAL_HOSTS_VALID_HOSTS, StringPool.STAR)) {

			_validPortalDomainCheckDisabled = true;
		}
		else {
			_validPortalDomainCheckDisabled = false;
		}

		// Always allow do as user service tracker

		try {
			Registry registry = RegistryUtil.getRegistry();

			ServiceTracker<AlwaysAllowDoAsUser, AlwaysAllowDoAsUser>
				alwaysAllowDoAsUserServiceTracker = registry.trackServices(
					AlwaysAllowDoAsUser.class,
					new AlwaysAllowDoAsUserServiceTrackerCustomizer());

			alwaysAllowDoAsUserServiceTracker.open();

			ServiceTracker
				<PortalInetSocketAddressEventListener,
					PortalInetSocketAddressEventListener>
				portalInetSocketAddressEventListenerServiceTracker =
					registry.trackServices(
						PortalInetSocketAddressEventListener.class,
						new PortalInetSocketAddressEventListenerServiceTrackerCustomizer());

			portalInetSocketAddressEventListenerServiceTracker.open();
		}
		catch (NullPointerException npe) {
		}
	}

	@Override
	public void addPageDescription(
		String description, HttpServletRequest request) {

		ListMergeable<String> descriptionListMergeable =
			(ListMergeable<String>)request.getAttribute(
				WebKeys.PAGE_DESCRIPTION);

		if (descriptionListMergeable == null) {
			descriptionListMergeable = new ListMergeable<>();

			request.setAttribute(
				WebKeys.PAGE_DESCRIPTION, descriptionListMergeable);
		}

		descriptionListMergeable.add(description);
	}

	@Override
	public void addPageKeywords(String keywords, HttpServletRequest request) {
		ListMergeable<String> keywordsListMergeable =
			(ListMergeable<String>)request.getAttribute(WebKeys.PAGE_KEYWORDS);

		if (keywordsListMergeable == null) {
			keywordsListMergeable = new ListMergeable<>();

			request.setAttribute(WebKeys.PAGE_KEYWORDS, keywordsListMergeable);
		}

		String[] keywordsArray = StringUtil.split(keywords);

		for (String keyword : keywordsArray) {
			if (!keywordsListMergeable.contains(
					StringUtil.toLowerCase(keyword))) {

				keywordsListMergeable.add(StringUtil.toLowerCase(keyword));
			}
		}
	}

	@Override
	public void addPageSubtitle(String subtitle, HttpServletRequest request) {
		ListMergeable<String> subtitleListMergeable =
			(ListMergeable<String>)request.getAttribute(WebKeys.PAGE_SUBTITLE);

		if (subtitleListMergeable == null) {
			subtitleListMergeable = new ListMergeable<>();

			request.setAttribute(WebKeys.PAGE_SUBTITLE, subtitleListMergeable);
		}

		subtitleListMergeable.add(subtitle);
	}

	@Override
	public void addPageTitle(String title, HttpServletRequest request) {
		ListMergeable<String> titleListMergeable =
			(ListMergeable<String>)request.getAttribute(WebKeys.PAGE_TITLE);

		if (titleListMergeable == null) {
			titleListMergeable = new ListMergeable<>();

			request.setAttribute(WebKeys.PAGE_TITLE, titleListMergeable);
		}

		titleListMergeable.add(title);
	}

	@Override
	public boolean addPortalInetSocketAddressEventListener(
		PortalInetSocketAddressEventListener
			portalInetSocketAddressEventListener) {

		return _portalInetSocketAddressEventListeners.add(
			portalInetSocketAddressEventListener);
	}

	/**
	 * Adds the portal port event listener to the portal. The listener will be
	 * notified whenever the portal port is set.
	 *
	 * @param      portalPortEventListener the portal port event listener to add
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #addPortalInetSocketAddressEventListener(
	 *             PortalInetSocketAddressEventListener)}
	 */
	@Deprecated
	@Override
	public void addPortalPortEventListener(
		PortalPortEventListener portalPortEventListener) {

		if (!_portalPortEventListeners.contains(portalPortEventListener)) {
			_portalPortEventListeners.add(portalPortEventListener);
		}
	}

	@Override
	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url) {

		addPortletBreadcrumbEntry(request, title, url, null);
	}

	@Override
	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url,
		Map<String, Object> data) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String name = WebKeys.PORTLET_BREADCRUMBS;

		if (Validator.isNotNull(portletDisplay.getId()) &&
			!portletDisplay.isFocused()) {

			name += StringPool.UNDERLINE + portletDisplay.getId();
		}

		List<BreadcrumbEntry> breadcrumbEntries =
			(List<BreadcrumbEntry>)request.getAttribute(name);

		if (breadcrumbEntries == null) {
			breadcrumbEntries = new ArrayList<>();

			request.setAttribute(name, breadcrumbEntries);
		}

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setData(data);
		breadcrumbEntry.setTitle(title);
		breadcrumbEntry.setURL(url);

		breadcrumbEntries.add(breadcrumbEntry);
	}

	@Override
	public void addPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long groupId = 0;

		if (layout.isTypeControlPanel()) {
			groupId = themeDisplay.getScopeGroupId();
		}
		else {
			groupId = getScopeGroupId(layout, portlet.getPortletId());
		}

		addDefaultResource(
			themeDisplay.getCompanyId(), groupId, layout, portlet, true);
		addDefaultResource(
			themeDisplay.getCompanyId(), groupId, layout, portlet, false);
	}

	@Override
	public void addPortletDefaultResource(
			long companyId, Layout layout, Portlet portlet)
		throws PortalException {

		addDefaultResource(companyId, layout, portlet, true);
		addDefaultResource(companyId, layout, portlet, false);
	}

	@Override
	public String addPreservedParameters(
		ThemeDisplay themeDisplay, Layout layout, String url,
		boolean doAsUser) {

		if (doAsUser) {
			if (Validator.isNotNull(themeDisplay.getDoAsUserId())) {
				url = HttpUtil.setParameter(
					url, "doAsUserId", themeDisplay.getDoAsUserId());
			}

			if (Validator.isNotNull(themeDisplay.getDoAsUserLanguageId())) {
				url = HttpUtil.setParameter(
					url, "doAsUserLanguageId",
					themeDisplay.getDoAsUserLanguageId());
			}
		}

		if (layout.isTypeControlPanel()) {
			if (themeDisplay.getDoAsGroupId() > 0) {
				url = HttpUtil.setParameter(
					url, "doAsGroupId", themeDisplay.getDoAsGroupId());
			}

			if (themeDisplay.getRefererPlid() != LayoutConstants.DEFAULT_PLID) {
				url = HttpUtil.setParameter(
					url, "refererPlid", themeDisplay.getRefererPlid());
			}

			if (Validator.isNotNull(themeDisplay.getControlPanelCategory())) {
				url = HttpUtil.setParameter(
					url, "controlPanelCategory",
					themeDisplay.getControlPanelCategory());
			}
		}

		return url;
	}

	@Override
	public String addPreservedParameters(
		ThemeDisplay themeDisplay, String url) {

		return addPreservedParameters(
			themeDisplay, themeDisplay.getLayout(), url, true);
	}

	@Override
	public void addUserLocaleOptionsMessage(HttpServletRequest request) {
		boolean ignoreUserLocaleOptions = GetterUtil.getBoolean(
			SessionClicks.get(
				request.getSession(), "ignoreUserLocaleOptions",
				Boolean.FALSE.toString()));

		if (ignoreUserLocaleOptions) {
			return;
		}

		boolean showUserLocaleOptionsMessage = ParamUtil.getBoolean(
			request, "showUserLocaleOptionsMessage", true);

		if (!showUserLocaleOptionsMessage) {
			return;
		}

		PortalMessages.add(request, PortalMessages.KEY_ANIMATION, false);
		PortalMessages.add(
			request, PortalMessages.KEY_JSP_PATH,
			"/html/common/themes/user_locale_options.jsp");
		PortalMessages.add(request, PortalMessages.KEY_TIMEOUT, -1);
	}

	@Override
	public void clearRequestParameters(RenderRequest renderRequest) {
		RenderRequestImpl renderRequestImpl = (RenderRequestImpl)renderRequest;

		if (renderRequestImpl.isTriggeredByActionURL()) {
			Map<String, String[]> renderParameters =
				renderRequestImpl.getRenderParameters();

			renderParameters.clear();
		}
	}

	@Override
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

		LiferayPortletResponse liferayPortletResponse =
			getLiferayPortletResponse(actionResponse);

		StateAwareResponse stateAwareResponse =
			(StateAwareResponse)liferayPortletResponse;

		Map<String, String[]> renderParameters =
			stateAwareResponse.getRenderParameterMap();

		actionResponse.setRenderParameter("p_p_lifecycle", "1");

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();
			String[] values = actionRequest.getParameterValues(param);

			if (renderParameters.get(
					actionResponse.getNamespace() + param) == null) {

				actionResponse.setRenderParameter(param, values);
			}
		}
	}

	@Override
	public String escapeRedirect(String url) {
		if (Validator.isNull(url) || !HttpUtil.hasDomain(url)) {
			return url;
		}

		String domain = HttpUtil.getDomain(url);

		int pos = domain.indexOf(CharPool.COLON);

		if (pos != -1) {
			domain = domain.substring(0, pos);
		}

		if (!_validPortalDomainCheckDisabled && isValidPortalDomain(domain)) {
			return url;
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

				if (allowedIps.length == 0) {
					return url;
				}

				InetAddress inetAddress = InetAddress.getByName(domain);

				String hostAddress = inetAddress.getHostAddress();

				String serverIp = getComputerAddress();

				boolean serverIpIsHostAddress = serverIp.equals(hostAddress);

				for (String ip : allowedIps) {
					if ((serverIpIsHostAddress && ip.equals("SERVER_IP")) ||
						ip.equals(hostAddress)) {

						return url;
					}
				}

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect URL " + url + " is not allowed");
				}

				url = null;
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

	@Override
	public String generateRandomKey(HttpServletRequest request, String input) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isAjax() || themeDisplay.isIsolated() ||
			themeDisplay.isLifecycleResource() ||
			themeDisplay.isStateExclusive()) {

			return StringUtil.randomId();
		}
		else {
			StringBundler sb = new StringBundler(5);

			sb.append(DeterminateKeyGenerator.generate(input));
			sb.append(StringPool.UNDERLINE);
			sb.append(request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_ID));
			sb.append(StringPool.UNDERLINE);
			sb.append(request.getAttribute(WebKeys.RENDER_PORTLET_COLUMN_POS));

			return JS.getSafeName(sb.toString());
		}
	}

	@Override
	public String getAbsoluteURL(HttpServletRequest request, String url) {
		String portalURL = getPortalURL(request);

		if (url.charAt(0) == CharPool.SLASH) {
			if (Validator.isNotNull(portalURL)) {
				url = portalURL.concat(url);
			}
		}

		if (!CookieKeys.hasSessionId(request) && url.startsWith(portalURL)) {
			url = getURLWithSessionId(url, request.getSession().getId());
		}

		return url;
	}

	@Override
	public LayoutQueryStringComposite getActualLayoutQueryStringComposite(
			long groupId, boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException {

		Layout layout = null;
		String layoutQueryStringCompositeFriendlyURL = friendlyURL;
		String queryString = StringPool.BLANK;

		if (Validator.isNull(friendlyURL)) {

			// We need to ensure that virtual layouts are merged

			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (!layouts.isEmpty()) {
				layout = layouts.get(0);
			}
			else {
				throw new NoSuchLayoutException(
					"{groupId=" + groupId + ", privateLayout=" + privateLayout +
						"}");
			}
		}
		else {
			LayoutQueryStringComposite layoutQueryStringComposite =
				getPortletFriendlyURLMapperLayoutQueryStringComposite(
					groupId, privateLayout, friendlyURL, params,
					requestContext);

			layout = layoutQueryStringComposite.getLayout();
			layoutQueryStringCompositeFriendlyURL =
				layoutQueryStringComposite.getFriendlyURL();
			queryString = layoutQueryStringComposite.getQueryString();
		}

		return new LayoutQueryStringComposite(
			layout, layoutQueryStringCompositeFriendlyURL, queryString);
	}

	@Override
	public String getActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		String actualURL = null;

		if (friendlyURL != null) {
			if (friendlyURL.startsWith(
					JournalArticleConstants.CANONICAL_URL_SEPARATOR)) {

				try {
					actualURL = getJournalArticleActualURL(
						groupId, privateLayout, mainPath, friendlyURL, params,
						requestContext);
				}
				catch (Exception e) {
					throw new NoSuchLayoutException(e);
				}
			}
			else if (friendlyURL.startsWith(
						VirtualLayoutConstants.CANONICAL_URL_SEPARATOR)) {

				try {
					actualURL = getVirtualLayoutActualURL(
						groupId, privateLayout, mainPath, friendlyURL, params,
						requestContext);
				}
				catch (Exception e) {
					throw new NoSuchLayoutException(e);
				}
			}
		}

		if (actualURL == null) {
			actualURL = getLayoutActualURL(
				groupId, privateLayout, mainPath, friendlyURL, params,
				requestContext);
		}

		return actualURL;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             LanguageUtil#getAvailableLocales}
	 */
	@Deprecated
	@Override
	public Locale[] getAlternateLocales(HttpServletRequest request) {
		return LanguageUtil.getAvailableLocales();
	}

	@Override
	public String getAlternateURL(
			String canonicalURL, ThemeDisplay themeDisplay, Locale locale,
			Layout layout)
		throws PortalException {

		String virtualHostname = getVirtualHostname(
			themeDisplay.getLayoutSet());

		if (Validator.isNull(virtualHostname)) {
			Company company = themeDisplay.getCompany();

			virtualHostname = company.getVirtualHostname();
		}

		String portalURL = themeDisplay.getPortalURL();

		String portalDomain = HttpUtil.getDomain(portalURL);

		if (!Validator.isBlank(portalDomain) &&
			!StringUtil.equalsIgnoreCase(portalDomain, _LOCALHOST) &&
			StringUtil.equalsIgnoreCase(virtualHostname, _LOCALHOST)) {

			virtualHostname = portalDomain;
		}

		String i18nPath = buildI18NPath(locale);

		if (Validator.isNull(virtualHostname)) {
			return canonicalURL.replaceFirst(
				_PUBLIC_GROUP_SERVLET_MAPPING,
				i18nPath.concat(_PUBLIC_GROUP_SERVLET_MAPPING));
		}

		// www.liferay.com:8080/ctx/page to www.liferay.com:8080/ctx/es/page

		int pos = canonicalURL.indexOf(virtualHostname);

		if (pos > 0) {
			pos = canonicalURL.indexOf(
				CharPool.SLASH, pos + virtualHostname.length());

			if (Validator.isNotNull(_pathContext)) {
				pos = canonicalURL.indexOf(
					CharPool.SLASH, pos + _pathContext.length());
			}

			if ((pos > 0) && (pos < canonicalURL.length())) {
				String friendlyURL = canonicalURL.substring(pos);

				int[] friendlyURLIndex = getGroupFriendlyURLIndex(friendlyURL);

				if (friendlyURLIndex != null) {
					int y = friendlyURLIndex[1];

					friendlyURL = friendlyURL.substring(y);

					if (friendlyURL.equals(StringPool.SLASH)) {
						friendlyURL = StringPool.BLANK;
					}
				}

				if (Validator.isNotNull(friendlyURL)) {
					String canonicalURLPrefix = canonicalURL.substring(0, pos);

					String canonicalURLSuffix = canonicalURL.substring(pos);

					canonicalURLSuffix = StringUtil.replaceFirst(
						canonicalURLSuffix, friendlyURL,
						layout.getFriendlyURL(locale));

					canonicalURL = canonicalURLPrefix.concat(
						canonicalURLSuffix);
				}

				Locale siteDefaultLocale = getSiteDefaultLocale(
					layout.getGroupId());

				if (siteDefaultLocale.equals(locale)) {
					return canonicalURL;
				}

				return canonicalURL.substring(0, pos).concat(
					i18nPath).concat(canonicalURL.substring(pos));
			}
		}

		return canonicalURL.concat(i18nPath);
	}

	@Override
	public long[] getAncestorSiteGroupIds(long groupId) throws PortalException {
		int i = 0;

		Set<Group> groups = doGetAncestorSiteGroups(groupId, false);

		long[] groupIds = new long[groups.size()];

		for (Group group : groups) {
			groupIds[i++] = group.getGroupId();
		}

		return groupIds;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             AuthTokenWhitelistUtil#getPortletCSRFWhitelistActions}
	 */
	@Deprecated
	@Override
	public Set<String> getAuthTokenIgnoreActions() {
		return AuthTokenWhitelistUtil.getPortletCSRFWhitelistActions();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             AuthTokenWhitelistUtil#getPortletCSRFWhitelist}
	 */
	@Deprecated
	@Override
	public Set<String> getAuthTokenIgnorePortlets() {
		return AuthTokenWhitelistUtil.getPortletCSRFWhitelist();
	}

	@Override
	public BaseModel<?> getBaseModel(ResourcePermission resourcePermission)
		throws PortalException {

		String modelName = resourcePermission.getName();
		String primKey = resourcePermission.getPrimKey();

		return getBaseModel(modelName, primKey);
	}

	@Override
	public BaseModel<?> getBaseModel(String modelName, String primKey)
		throws PortalException {

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

			method = serviceUtil.getMethod(
				methodName, new Class[] {String.class});

			return (BaseModel<?>)method.invoke(null, primKey);
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

	@Override
	public long getBasicAuthUserId(HttpServletRequest request)
		throws PortalException {

		long companyId = PortalInstances.getCompanyId(request);

		return getBasicAuthUserId(request, companyId);
	}

	@Override
	public long getBasicAuthUserId(HttpServletRequest request, long companyId)
		throws PortalException {

		long userId = 0;

		String authorizationHeader = request.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isNull(authorizationHeader)) {
			return userId;
		}

		String[] authorizationArray = authorizationHeader.split("\\s+");

		String authorization = authorizationArray[0];
		String credentials = new String(Base64.decode(authorizationArray[1]));

		if (!StringUtil.equalsIgnoreCase(
				authorization, HttpServletRequest.BASIC_AUTH)) {

			return userId;
		}

		String[] loginAndPassword = StringUtil.split(
			credentials, CharPool.COLON);

		String login = HttpUtil.decodeURL(loginAndPassword[0].trim());

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

	@Override
	public List<Group> getBrowsableScopeGroups(
			long userId, long companyId, long groupId, String portletId)
		throws PortalException {

		List<Group> groups = new ArrayList<>();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersGroups", new Long(userId));

		groups.addAll(
			0,
			GroupLocalServiceUtil.search(
				companyId, null, null, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS));

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(userId);

		for (Organization organization : organizations) {
			groups.add(0, organization.getGroup());
		}

		if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED ||
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

			groups.add(
				0, GroupLocalServiceUtil.getUserGroup(companyId, userId));
		}

		groups.addAll(0, getCurrentAndAncestorSiteGroups(groupId));

		List<Group> filteredGroups = new ArrayList<>();

		for (Group group : ListUtil.unique(groups)) {
			if (group.hasStagingGroup()) {
				Group stagingGroup = group.getStagingGroup();

				if ((stagingGroup.getGroupId() == groupId) &&
					group.isStagedPortlet(portletId) &&
					!group.isStagedRemotely() &&
					group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY)) {

					filteredGroups.add(stagingGroup);
				}
			}
			else {
				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	@Override
	public String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout)
		throws PortalException {

		return getCanonicalURL(completeURL, themeDisplay, layout, false);
	}

	@Override
	public String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout,
			boolean forceLayoutFriendlyURL)
		throws PortalException {

		String groupFriendlyURL = StringPool.BLANK;
		String parametersURL = StringPool.BLANK;

		if (Validator.isNotNull(completeURL)) {
			completeURL = removeRedirectParameter(completeURL);

			int pos = completeURL.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

			if (pos == -1) {
				pos = completeURL.indexOf(StringPool.QUESTION);
			}

			groupFriendlyURL = completeURL;

			if (pos != -1) {
				groupFriendlyURL = completeURL.substring(0, pos);
				parametersURL = completeURL.substring(pos);
			}
		}

		if (layout == null) {
			layout = themeDisplay.getLayout();
		}

		String canonicalLayoutFriendlyURL = StringPool.BLANK;

		String layoutFriendlyURL = layout.getFriendlyURL(
			themeDisplay.getLocale());

		String defaultLayoutFriendlyURL = layout.getFriendlyURL(
			getSiteDefaultLocale(layout.getGroupId()));

		if ((groupFriendlyURL.contains(layoutFriendlyURL) ||
			 groupFriendlyURL.contains(
				 StringPool.SLASH + layout.getLayoutId())) &&
			(!layout.isFirstParent() || Validator.isNotNull(parametersURL))) {

			canonicalLayoutFriendlyURL = defaultLayoutFriendlyURL;
		}
		else if (forceLayoutFriendlyURL) {
			canonicalLayoutFriendlyURL = defaultLayoutFriendlyURL;
		}

		groupFriendlyURL = getGroupFriendlyURL(
			layout.getLayoutSet(), themeDisplay, true);

		return groupFriendlyURL.concat(canonicalLayoutFriendlyURL).concat(
			parametersURL);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getCDNHost(boolean)}
	 */
	@Deprecated
	@Override
	public String getCDNHost() {
		long companyId = CompanyThreadLocal.getCompanyId();

		return getCDNHostHttp(companyId);
	}

	@Override
	public String getCDNHost(boolean secure) {
		long companyId = CompanyThreadLocal.getCompanyId();

		if (secure) {
			return getCDNHostHttps(companyId);
		}
		else {
			return getCDNHostHttp(companyId);
		}
	}

	@Override
	public String getCDNHost(HttpServletRequest request)
		throws PortalException {

		boolean cdnEnabled = ParamUtil.getBoolean(request, "cdn_enabled", true);

		if (!cdnEnabled) {
			return StringPool.BLANK;
		}

		String cdnHost = null;

		Company company = getCompany(request);

		if (request.isSecure()) {
			cdnHost = getCDNHostHttps(company.getCompanyId());
		}
		else {
			cdnHost = getCDNHostHttp(company.getCompanyId());
		}

		if (Validator.isUrl(cdnHost)) {
			return cdnHost;
		}

		return StringPool.BLANK;
	}

	@Override
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

		if ((cdnHostHttp == null) || cdnHostHttp.startsWith("${") ||
			!Validator.isUrl(cdnHostHttp)) {

			cdnHostHttp = StringPool.BLANK;
		}

		_cdnHostHttpMap.put(companyId, cdnHostHttp);

		return cdnHostHttp;
	}

	@Override
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
		catch (SystemException se) {
		}

		if ((cdnHostHttps == null) || cdnHostHttps.startsWith("${") ||
			!Validator.isUrl(cdnHostHttps)) {

			cdnHostHttps = StringPool.BLANK;
		}

		_cdnHostHttpsMap.put(companyId, cdnHostHttps);

		return cdnHostHttps;
	}

	@Override
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

	@Override
	public long getClassNameId(Class<?> clazz) {
		return getClassNameId(clazz.getName());
	}

	@Override
	public long getClassNameId(String value) {
		if (!StartupHelperUtil.isUpgrading()) {
			return ClassNameLocalServiceUtil.getClassNameId(value);
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select classNameId from ClassName_ where value = ?");

			ps.setString(1, value);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("classNameId");
			}
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to get class name ID from value " + value, e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	@Override
	public Company getCompany(HttpServletRequest request)
		throws PortalException {

		long companyId = getCompanyId(request);

		if (companyId <= 0) {
			return null;
		}

		Company company = (Company)request.getAttribute(WebKeys.COMPANY);

		if (company == null) {

			// LEP-5994

			company = CompanyLocalServiceUtil.fetchCompanyById(companyId);

			if (company == null) {
				company = CompanyLocalServiceUtil.getCompanyById(
					PortalInstances.getDefaultCompanyId());
			}

			request.setAttribute(WebKeys.COMPANY, company);
		}

		return company;
	}

	@Override
	public Company getCompany(PortletRequest portletRequest)
		throws PortalException {

		return getCompany(getHttpServletRequest(portletRequest));
	}

	@Override
	public long getCompanyId(HttpServletRequest request) {
		return PortalInstances.getCompanyId(request);
	}

	@Override
	public long getCompanyId(PortletRequest portletRequest) {
		return getCompanyId(getHttpServletRequest(portletRequest));
	}

	@Override
	public long[] getCompanyIds() {
		return PortalInstances.getCompanyIds();
	}

	@Override
	public String getComputerAddress() {
		return _computerAddress;
	}

	@Override
	public String getComputerName() {
		return _computerName;
	}

	@Override
	public Map<String, List<Portlet>> getControlPanelCategoriesMap(
		HttpServletRequest request) {

		return getCategoriesMap(
			request, WebKeys.CONTROL_PANEL_CATEGORIES_MAP,
			PortletCategoryKeys.ALL);
	}

	@Override
	public String getControlPanelCategory(
		String portletId, ThemeDisplay themeDisplay) {

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

	@Override
	public String getControlPanelFullURL(
			long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException {

		StringBundler sb = new StringBundler(6);

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		Company company = CompanyLocalServiceUtil.getCompany(
			group.getCompanyId());

		sb.append(
			getPortalURL(
				company.getVirtualHostname(), getPortalServerPort(false),
				false));
		sb.append(getPathFriendlyURLPrivateGroup());
		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		if (params != null) {
			params = new HashMap<>(params);
		}
		else {
			params = new HashMap<>();
		}

		params.put("p_p_id", new String[] {ppid});
		params.put("p_p_lifecycle", new String[] {"0"});
		params.put(
			"p_p_state", new String[] {WindowState.MAXIMIZED.toString()});
		params.put("p_p_mode", new String[] {PortletMode.VIEW.toString()});
		params.put("doAsGroupId", new String[] {String.valueOf(scopeGroupId)});
		params.put(
			"controlPanelCategory",
			new String[] {PortletCategoryKeys.CURRENT_SITE});

		sb.append(HttpUtil.parameterMapToString(params, true));

		return sb.toString();
	}

	@Override
	public long getControlPanelPlid(long companyId) throws PortalException {
		Group controlPanelGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.CONTROL_PANEL);

		return LayoutLocalServiceUtil.getDefaultPlid(
			controlPanelGroup.getGroupId(), true);
	}

	@Override
	public long getControlPanelPlid(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getControlPanelPlid(themeDisplay.getCompanyId());
	}

	@Override
	public Set<Portlet> getControlPanelPortlets(
		long companyId, String category) {

		Set<Portlet> portletsSet = new TreeSet<>(
			new PortletControlPanelWeightComparator());

		if (Validator.isNull(category)) {
			return portletsSet;
		}

		List<Portlet> portletsList = PortletLocalServiceUtil.getPortlets(
			companyId);

		for (Portlet portlet : portletsList) {
			String portletCategory = portlet.getControlPanelEntryCategory();

			if (category.equals(portletCategory) ||
				(category.endsWith(StringPool.PERIOD) &&
				 StringUtil.startsWith(portletCategory, category))) {

				portletsSet.add(portlet);
			}
		}

		return portletsSet;
	}

	@Override
	public List<Portlet> getControlPanelPortlets(
		String category, ThemeDisplay themeDisplay) {

		Set<Portlet> portlets = getControlPanelPortlets(
			themeDisplay.getCompanyId(), category);

		return filterControlPanelPortlets(portlets, themeDisplay);
	}

	@Override
	public PortletURL getControlPanelPortletURL(
		HttpServletRequest request, String portletId, long referrerPlid,
		String lifecycle) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long plid = 0;

		try {
			plid = getControlPanelPlid(themeDisplay.getCompanyId());
		}
		catch (Exception e) {
			_log.error("Unable to determine control panel layout id", e);
		}

		LiferayPortletURL liferayPortletURL = new PortletURLImpl(
			request, portletId, plid, lifecycle);

		liferayPortletURL.setDoAsGroupId(themeDisplay.getScopeGroupId());
		liferayPortletURL.setRefererPlid(themeDisplay.getPlid());

		return liferayPortletURL;
	}

	@Override
	public PortletURL getControlPanelPortletURL(
		PortletRequest portletRequest, String portletId, long referrerPlid,
		String lifecycle) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long plid = 0;

		try {
			plid = getControlPanelPlid(themeDisplay.getCompanyId());
		}
		catch (Exception e) {
			_log.error("Unable to determine control panel layout id", e);
		}

		LiferayPortletURL liferayPortletURL = new PortletURLImpl(
			portletRequest, portletId, plid, lifecycle);

		liferayPortletURL.setDoAsGroupId(themeDisplay.getScopeGroupId());
		liferayPortletURL.setRefererPlid(themeDisplay.getPlid());

		return liferayPortletURL;
	}

	@Override
	public String getCreateAccountURL(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		if (Validator.isNull(PropsValues.COMPANY_SECURITY_STRANGERS_URL)) {
			PortletURL createAccountURL = PortletURLFactoryUtil.create(
				request, PortletKeys.LOGIN, themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE);

			createAccountURL.setParameter(
				"saveLastPath", Boolean.FALSE.toString());
			createAccountURL.setParameter(
				"struts_action", "/login/create_account");
			createAccountURL.setPortletMode(PortletMode.VIEW);
			createAccountURL.setWindowState(WindowState.MAXIMIZED);

			if (!PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS) {
				return createAccountURL.toString();
			}

			String portalURL = getPortalURL(request);
			String portalURLSecure = getPortalURL(request, true);

			return StringUtil.replaceFirst(
				createAccountURL.toString(), portalURL, portalURLSecure);
		}

		try {
			Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				themeDisplay.getScopeGroupId(), false,
				PropsValues.COMPANY_SECURITY_STRANGERS_URL);

			return getLayoutURL(layout, themeDisplay);
		}
		catch (NoSuchLayoutException nsle) {
		}

		return StringPool.BLANK;
	}

	@Override
	public long[] getCurrentAndAncestorSiteGroupIds(long groupId)
		throws PortalException {

		return getCurrentAndAncestorSiteGroupIds(groupId, false);
	}

	@Override
	public long[] getCurrentAndAncestorSiteGroupIds(
			long groupId, boolean checkContentSharingWithChildrenEnabled)
		throws PortalException {

		List<Group> groups = getCurrentAndAncestorSiteGroups(
			groupId, checkContentSharingWithChildrenEnabled);

		long[] groupIds = new long[groups.size()];

		for (int i = 0; i < groups.size(); i++) {
			Group group = groups.get(i);

			groupIds[i] = group.getGroupId();
		}

		return groupIds;
	}

	@Override
	public long[] getCurrentAndAncestorSiteGroupIds(long[] groupIds)
		throws PortalException {

		return getCurrentAndAncestorSiteGroupIds(groupIds, false);
	}

	@Override
	public long[] getCurrentAndAncestorSiteGroupIds(
			long[] groupIds, boolean checkContentSharingWithChildrenEnabled)
		throws PortalException {

		List<Group> groups = getCurrentAndAncestorSiteGroups(
			groupIds, checkContentSharingWithChildrenEnabled);

		long[] currentAndAncestorSiteGroupIds = new long[groups.size()];

		for (int i = 0; i < groups.size(); i++) {
			Group group = groups.get(i);

			currentAndAncestorSiteGroupIds[i] = group.getGroupId();
		}

		return currentAndAncestorSiteGroupIds;
	}

	@Override
	public List<Group> getCurrentAndAncestorSiteGroups(long groupId)
		throws PortalException {

		return getCurrentAndAncestorSiteGroups(groupId, false);
	}

	@Override
	public List<Group> getCurrentAndAncestorSiteGroups(
			long groupId, boolean checkContentSharingWithChildrenEnabled)
		throws PortalException {

		Set<Group> groups = new LinkedHashSet<>();

		Group siteGroup = doGetCurrentSiteGroup(groupId);

		if (siteGroup != null) {
			groups.add(siteGroup);
		}

		groups.addAll(
			doGetAncestorSiteGroups(
				groupId, checkContentSharingWithChildrenEnabled));

		return new ArrayList<>(groups);
	}

	@Override
	public List<Group> getCurrentAndAncestorSiteGroups(long[] groupIds)
		throws PortalException {

		return getCurrentAndAncestorSiteGroups(groupIds, false);
	}

	@Override
	public List<Group> getCurrentAndAncestorSiteGroups(
			long[] groupIds, boolean checkContentSharingWithChildrenEnabled)
		throws PortalException {

		Set<Group> groups = new LinkedHashSet<>();

		for (int i = 0; i < groupIds.length; i++) {
			groups.addAll(
				getCurrentAndAncestorSiteGroups(
					groupIds[i], checkContentSharingWithChildrenEnabled));
		}

		return new ArrayList<>(groups);
	}

	@Override
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

	@Override
	public String getCurrentURL(HttpServletRequest request) {
		String currentURL = (String)request.getAttribute(WebKeys.CURRENT_URL);

		if (currentURL != null) {
			return currentURL;
		}

		currentURL = ParamUtil.getString(request, "currentURL");

		if (Validator.isNull(currentURL)) {
			currentURL = HttpUtil.getCompleteURL(request);

			if (Validator.isNotNull(currentURL) &&
				!currentURL.contains(_J_SECURITY_CHECK)) {

				currentURL = currentURL.substring(
					currentURL.indexOf(Http.PROTOCOL_DELIMITER) +
						Http.PROTOCOL_DELIMITER.length());

				currentURL = currentURL.substring(
					currentURL.indexOf(CharPool.SLASH));
			}

			if (Validator.isNotNull(currentURL) &&
				FacebookUtil.isFacebook(currentURL)) {

				String[] facebookData = FacebookUtil.getFacebookData(request);

				if (facebookData != null) {
					currentURL =
						FacebookUtil.FACEBOOK_APPS_URL + facebookData[0] +
							facebookData[2];
				}
			}
		}

		if (Validator.isNull(currentURL)) {
			currentURL = getPathMain();
		}

		request.setAttribute(WebKeys.CURRENT_URL, currentURL);

		return currentURL;
	}

	@Override
	public String getCurrentURL(PortletRequest portletRequest) {
		return (String)portletRequest.getAttribute(WebKeys.CURRENT_URL);
	}

	@Override
	public String getCustomSQLFunctionIsNotNull() {
		return PropsValues.CUSTOM_SQL_FUNCTION_ISNOTNULL;
	}

	@Override
	public String getCustomSQLFunctionIsNull() {
		return PropsValues.CUSTOM_SQL_FUNCTION_ISNULL;
	}

	@Override
	public Date getDate(int month, int day, int year) {
		try {
			return getDate(month, day, year, null);
		}
		catch (PortalException pe) {
			throw new RuntimeException();
		}
	}

	@Override
	public Date getDate(
			int month, int day, int year,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return getDate(month, day, year, null, clazz);
	}

	@Override
	public Date getDate(
			int month, int day, int year, int hour, int min,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return getDate(month, day, year, hour, min, null, clazz);
	}

	@Override
	public Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			Class<? extends PortalException> clazz)
		throws PortalException {

		if (!Validator.isGregorianDate(month, day, year)) {
			if (clazz != null) {
				try {
					throw clazz.newInstance();
				}
				catch (Exception e) {
					throw new PortalException(e);
				}
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

			/*if ((timeZone != null) &&
				cal.before(CalendarFactoryUtil.getCalendar(timeZone))) {

				throw pe;
			}*/

			return date;
		}
	}

	@Override
	public Date getDate(
			int month, int day, int year, TimeZone timeZone,
			Class<? extends PortalException> clazz)
		throws PortalException {

		return getDate(month, day, year, -1, -1, timeZone, clazz);
	}

	@Override
	public long getDefaultCompanyId() {
		return PortalInstances.getDefaultCompanyId();
	}

	@Override
	public long getDigestAuthUserId(HttpServletRequest request)
		throws PortalException {

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

	@Override
	public String getEmailFromAddress(
		PortletPreferences preferences, long companyId, String defaultValue) {

		if (Validator.isNull(defaultValue)) {
			defaultValue = PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		}

		return preferences.getValue("emailFromAddress", defaultValue);
	}

	@Override
	public String getEmailFromName(
		PortletPreferences preferences, long companyId, String defaultValue) {

		if (Validator.isNull(defaultValue)) {
			defaultValue = PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
		}

		return preferences.getValue("emailFromName", defaultValue);
	}

	@Override
	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, HttpServletRequest request)
		throws PortalException {

		Map<String, Serializable> attributes = new HashMap<>();

		List<String> names = new ArrayList<>();

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			if (param.contains("ExpandoAttributeName--")) {
				String name = ParamUtil.getString(request, param);

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
				request, "ExpandoAttribute--" + name + "--", type, displayType);

			attributes.put(name, value);
		}

		return attributes;
	}

	@Override
	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, PortletRequest portletRequest)
		throws PortalException {

		return getExpandoBridgeAttributes(
			expandoBridge, getHttpServletRequest(portletRequest));
	}

	@Override
	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge,
			UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		return getExpandoBridgeAttributes(
			expandoBridge, (HttpServletRequest)uploadPortletRequest);
	}

	@Override
	public Serializable getExpandoValue(
			HttpServletRequest request, String name, int type,
			String displayType)
		throws PortalException {

		Serializable value = null;

		if (type == ExpandoColumnConstants.BOOLEAN) {
			value = ParamUtil.getBoolean(request, name);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
		}
		else if (type == ExpandoColumnConstants.DATE) {
			int valueDateMonth = ParamUtil.getInteger(request, name + "Month");
			int valueDateDay = ParamUtil.getInteger(request, name + "Day");
			int valueDateYear = ParamUtil.getInteger(request, name + "Year");
			int valueDateHour = ParamUtil.getInteger(request, name + "Hour");
			int valueDateMinute = ParamUtil.getInteger(
				request, name + "Minute");
			int valueDateAmPm = ParamUtil.getInteger(request, name + "AmPm");

			if (valueDateAmPm == Calendar.PM) {
				valueDateHour += 12;
			}

			TimeZone timeZone = null;

			User user = getUser(request);

			if (user != null) {
				timeZone = user.getTimeZone();
			}

			value = getDate(
				valueDateMonth, valueDateDay, valueDateYear, valueDateHour,
				valueDateMinute, timeZone, ValueDataException.class);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			value = ParamUtil.getDouble(request, name);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			String[] values = request.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getDoubleValues(values);
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			value = ParamUtil.getFloat(request, name);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			String[] values = request.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getFloatValues(values);
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			value = ParamUtil.getInteger(request, name);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			String[] values = request.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getIntegerValues(values);
		}
		else if (type == ExpandoColumnConstants.LONG) {
			value = ParamUtil.getLong(request, name);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			String[] values = request.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getLongValues(values);
		}
		else if (type == ExpandoColumnConstants.NUMBER) {
			value = ParamUtil.getNumber(request, name);
		}
		else if (type == ExpandoColumnConstants.NUMBER_ARRAY) {
			String[] values = request.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getNumberValues(values);
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			value = ParamUtil.getShort(request, name);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			String[] values = request.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getShortValues(values);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			String[] values = request.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = values;
		}
		else if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
			value = (Serializable)LocalizationUtil.getLocalizationMap(
				request, name);
		}
		else {
			value = ParamUtil.getString(request, name);
		}

		return value;
	}

	@Override
	public Serializable getExpandoValue(
			PortletRequest portletRequest, String name, int type,
			String displayType)
		throws PortalException {

		return getExpandoValue(
			getHttpServletRequest(portletRequest), name, type, displayType);
	}

	@Override
	public Serializable getExpandoValue(
			UploadPortletRequest uploadPortletRequest, String name, int type,
			String displayType)
		throws PortalException {

		return getExpandoValue(
			(HttpServletRequest)uploadPortletRequest, name, type, displayType);
	}

	@Override
	public String getFacebookURL(
			Portlet portlet, String facebookCanvasPageURL,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String facebookURL = getServletURL(
			portlet, FacebookUtil.FACEBOOK_SERVLET_PATH + facebookCanvasPageURL,
			themeDisplay);

		if (!facebookURL.endsWith(StringPool.SLASH)) {
			facebookURL += StringPool.SLASH;
		}

		return facebookURL;
	}

	@Override
	public Portlet getFirstMyAccountPortlet(ThemeDisplay themeDisplay) {
		List<Portlet> portlets = getControlPanelPortlets(
			PortletCategoryKeys.MY, themeDisplay);

		if (portlets.isEmpty()) {
			return null;
		}

		return portlets.get(0);
	}

	@Override
	public String getFirstPageLayoutTypes(HttpServletRequest request) {
		StringBundler sb = new StringBundler();

		for (String type : LayoutTypeControllerTracker.getTypes()) {
			LayoutTypeController layoutTypeController =
				LayoutTypeControllerTracker.getLayoutTypeController(type);

			if (layoutTypeController.isFirstPageable()) {
				sb.append(LanguageUtil.get(request, "layout.types." + type));
				sb.append(StringPool.COMMA);
				sb.append(StringPool.SPACE);
			}
		}

		if (sb.index() >= 2) {
			sb.setIndex(sb.index() - 2);
		}

		return sb.toString();
	}

	@Override
	public Portlet getFirstSiteAdministrationPortlet(
		ThemeDisplay themeDisplay) {

		Portlet siteAdministrationPortlet = null;

		for (String category : PortletCategoryKeys.SITE_ADMINISTRATION_ALL) {
			List<Portlet> portlets = getControlPanelPortlets(
				category, themeDisplay);

			if (portlets.isEmpty()) {
				continue;
			}

			return portlets.get(0);
		}

		return siteAdministrationPortlet;
	}

	@Override
	public String getFullName(
		String firstName, String middleName, String lastName) {

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		return fullNameGenerator.getFullName(firstName, middleName, lastName);
	}

	@Override
	public String getGlobalLibDir() {
		return PropsValues.LIFERAY_LIB_GLOBAL_DIR;
	}

	@Override
	public String getGoogleGadgetURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException {

		return getServletURL(
			portlet, PropsValues.GOOGLE_GADGET_SERVLET_MAPPING, themeDisplay);
	}

	@Override
	public String getGroupFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {

		return getGroupFriendlyURL(layoutSet, themeDisplay, false);
	}

	@Override
	public String getGroupFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException {

		String i18nLanguageId = themeDisplay.getI18nLanguageId();
		String i18nPath = themeDisplay.getI18nPath();
		Locale originalLocale = themeDisplay.getLocale();

		try {
			setThemeDisplayI18n(themeDisplay, locale);

			return getGroupFriendlyURL(layoutSet, themeDisplay);
		}
		finally {
			resetThemeDisplayI18n(
				themeDisplay, i18nLanguageId, i18nPath, originalLocale);
		}
	}

	@Override
	public int[] getGroupFriendlyURLIndex(String requestURI) {
		if (requestURI.startsWith(
				_PRIVATE_GROUP_SERVLET_MAPPING + StringPool.SLASH) ||
			requestURI.startsWith(
				_PRIVATE_USER_SERVLET_MAPPING + StringPool.SLASH) ||
			requestURI.startsWith(
				_PUBLIC_GROUP_SERVLET_MAPPING + StringPool.SLASH)) {

			int x = requestURI.indexOf(StringPool.SLASH, 1);

			int y = requestURI.indexOf(CharPool.SLASH, x + 1);

			if (y == -1) {

				// /web/alpha

				requestURI += StringPool.SLASH;

				y = requestURI.indexOf(CharPool.SLASH, x + 1);
			}

			return new int[] {x, y};
		}

		return null;
	}

	@Override
	public String[] getGroupPermissions(HttpServletRequest request) {
		return request.getParameterValues("groupPermissions");
	}

	@Override
	public String[] getGroupPermissions(
		HttpServletRequest request, String className) {

		String[] groupPermissions = request.getParameterValues(
			"groupPermissions_" + className);

		String inputPermissionsShowOptions = request.getParameter(
			"inputPermissionsShowOptions");

		return getGroupPermissions(
			groupPermissions, className, inputPermissionsShowOptions);
	}

	@Override
	public String[] getGroupPermissions(PortletRequest portletRequest) {
		return portletRequest.getParameterValues("groupPermissions");
	}

	@Override
	public String[] getGroupPermissions(
		PortletRequest portletRequest, String className) {

		String[] groupPermissions = portletRequest.getParameterValues(
			"groupPermissions_" + className);

		String inputPermissionsShowOptions = portletRequest.getParameter(
			"inputPermissionsShowOptions");

		return getGroupPermissions(
			groupPermissions, className, inputPermissionsShowOptions);
	}

	@Override
	public String[] getGuestPermissions(HttpServletRequest request) {
		return request.getParameterValues("guestPermissions");
	}

	@Override
	public String[] getGuestPermissions(
		HttpServletRequest request, String className) {

		String[] guestPermissions = request.getParameterValues(
			"guestPermissions_" + className);

		String inputPermissionsShowOptions = request.getParameter(
			"inputPermissionsShowOptions");

		return getGuestPermissions(
			guestPermissions, className, inputPermissionsShowOptions);
	}

	@Override
	public String[] getGuestPermissions(PortletRequest portletRequest) {
		return portletRequest.getParameterValues("guestPermissions");
	}

	@Override
	public String[] getGuestPermissions(
		PortletRequest portletRequest, String className) {

		String[] guestPermissions = portletRequest.getParameterValues(
			"guestPermissions_" + className);

		String inputPermissionsShowOptions = portletRequest.getParameter(
			"inputPermissionsShowOptions");

		return getGuestPermissions(
			guestPermissions, className, inputPermissionsShowOptions);
	}

	@Override
	public String getHomeURL(HttpServletRequest request)
		throws PortalException {

		String portalURL = getPortalURL(request);

		return portalURL + _pathContext + getRelativeHomeURL(request);
	}

	@Override
	public String getHost(HttpServletRequest request) {
		request = getOriginalServletRequest(request);

		String host = request.getHeader("Host");

		if (host != null) {
			host = StringUtil.toLowerCase(host.trim());

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

	@Override
	public String getHost(PortletRequest portletRequest) {
		return getHost(getHttpServletRequest(portletRequest));
	}

	@Override
	public HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest) {

		PortletRequestImpl portletRequestImpl =
			PortletRequestImpl.getPortletRequestImpl(portletRequest);

		return portletRequestImpl.getHttpServletRequest();
	}

	@Override
	public HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse) {

		PortletResponseImpl portletResponseImpl =
			PortletResponseImpl.getPortletResponseImpl(portletResponse);

		return portletResponseImpl.getHttpServletResponse();
	}

	@Override
	public String getI18nPathLanguageId(
		Locale locale, String defaultI18nPathLanguageId) {

		String i18nPathLanguageId = defaultI18nPathLanguageId;

		if (!LanguageUtil.isDuplicateLanguageCode(locale.getLanguage())) {
			i18nPathLanguageId = locale.getLanguage();
		}
		else {
			Locale priorityLocale = LanguageUtil.getLocale(
				locale.getLanguage());

			if (locale.equals(priorityLocale)) {
				i18nPathLanguageId = locale.getLanguage();
			}
		}

		return i18nPathLanguageId;
	}

	@Override
	public String getJournalArticleActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		String urlTitle = friendlyURL.substring(
			JournalArticleConstants.CANONICAL_URL_SEPARATOR.length());

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				groupId, urlTitle);

		Layout layout = getJournalArticleLayout(
			groupId, privateLayout, friendlyURL);

		String layoutActualURL = getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams = new InheritableMap<>();

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
			String instanceId = PortletConstants.generateInstanceId();

			defaultAssetPublisherPortletId = PortletConstants.assemblePortletId(
				"com_liferay_asset_publisher_web_AssetPublisherPortlet",
				instanceId);
		}

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		if (Validator.isNull(currentDefaultAssetPublisherPortletId)) {
			String actualPortletAuthenticationToken = AuthTokenUtil.getToken(
				request, layout.getPlid(), defaultAssetPublisherPortletId);

			actualParams.put(
				"p_p_auth", new String[] {actualPortletAuthenticationToken});
		}

		actualParams.put(
			"p_p_id", new String[] {defaultAssetPublisherPortletId});
		actualParams.put("p_p_lifecycle", new String[] {"0"});

		if (Validator.isNull(currentDefaultAssetPublisherPortletId)) {
			actualParams.put(
				"p_p_state", new String[] {WindowState.MAXIMIZED.toString()});
		}

		actualParams.put("p_p_mode", new String[] {"view"});
		actualParams.put(
			"p_j_a_id", new String[] {String.valueOf(journalArticle.getId())});

		String namespace = getPortletNamespace(defaultAssetPublisherPortletId);

		actualParams.put(
			namespace + "mvcPath", new String[] {"/view_content.jsp"});

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				JournalArticle.class.getName());

		actualParams.put(
			namespace + "type", new String[] {assetRendererFactory.getType()});

		actualParams.put(
			namespace + "urlTitle",
			new String[] {journalArticle.getUrlTitle()});

		String queryString = HttpUtil.parameterMapToString(actualParams, false);

		if (layoutActualURL.contains(StringPool.QUESTION)) {
			layoutActualURL =
				layoutActualURL + StringPool.AMPERSAND + queryString;
		}
		else {
			layoutActualURL =
				layoutActualURL + StringPool.QUESTION + queryString;
		}

		Locale locale = getLocale(request);

		addPageSubtitle(journalArticle.getTitle(locale), request);
		addPageDescription(journalArticle.getDescription(locale), request);

		List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(
			JournalArticle.class.getName(), journalArticle.getPrimaryKey());

		if (!assetTags.isEmpty()) {
			addPageKeywords(
				ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);
		}

		return layoutActualURL;
	}

	@Override
	public Layout getJournalArticleLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws PortalException {

		String urlTitle = friendlyURL.substring(
			JournalArticleConstants.CANONICAL_URL_SEPARATOR.length());

		JournalArticle journalArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				groupId, urlTitle);

		return LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			journalArticle.getLayoutUuid(), groupId, privateLayout);
	}

	@Override
	public String getJsSafePortletId(String portletId) {
		return JS.getSafeName(portletId);
	}

	@Override
	public String getLayoutActualURL(Layout layout) {
		return getLayoutActualURL(layout, getPathMain());
	}

	@Override
	public String getLayoutActualURL(Layout layout, String mainPath) {
		Map<String, String> variables = new HashMap<>();

		variables.put("liferay:groupId", String.valueOf(layout.getGroupId()));
		variables.put("liferay:mainPath", mainPath);
		variables.put("liferay:plid", String.valueOf(layout.getPlid()));

		if (layout instanceof VirtualLayout) {
			variables.put(
				"liferay:pvlsgid", String.valueOf(layout.getGroupId()));
		}
		else {
			variables.put("liferay:pvlsgid", "0");
		}

		LayoutType layoutType = layout.getLayoutType();

		UnicodeProperties typeSettingsProperties =
			layoutType.getTypeSettingsProperties();

		variables.putAll(typeSettingsProperties);

		return layoutType.getURL(variables);
	}

	@Override
	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException {

		return getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, null, null);
	}

	@Override
	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		LayoutQueryStringComposite actualLayoutQueryStringComposite =
			getActualLayoutQueryStringComposite(
				groupId, privateLayout, friendlyURL, params, requestContext);

		Layout layout = actualLayoutQueryStringComposite.getLayout();
		String queryString = actualLayoutQueryStringComposite.getQueryString();

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

				layoutActualURL = layoutActualURL.concat(
					StringPool.AMPERSAND).concat(queryString);
			}
		}

		return layoutActualURL;
	}

	@Override
	public String getLayoutFriendlyURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		LayoutType layoutType = layout.getLayoutType();

		if (!layoutType.isURLFriendliable()) {
			return null;
		}

		String groupFriendlyURL = getGroupFriendlyURL(
			layout.getLayoutSet(), themeDisplay, false);

		return groupFriendlyURL.concat(
			layout.getFriendlyURL(themeDisplay.getLocale()));
	}

	@Override
	public String getLayoutFriendlyURL(
			Layout layout, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException {

		String i18nLanguageId = themeDisplay.getI18nLanguageId();
		String i18nPath = themeDisplay.getI18nPath();
		Locale originalLocale = themeDisplay.getLocale();

		try {
			setThemeDisplayI18n(themeDisplay, locale);

			return getLayoutFriendlyURL(layout, themeDisplay);
		}
		finally {
			resetThemeDisplayI18n(
				themeDisplay, i18nLanguageId, i18nPath, originalLocale);
		}
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long groupId, boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException {

		Layout layout = null;
		String layoutFriendlyURL = friendlyURL;

		if (friendlyURL != null) {
			if (friendlyURL.startsWith(
					JournalArticleConstants.CANONICAL_URL_SEPARATOR)) {

				try {
					layout = getJournalArticleLayout(
						groupId, privateLayout, friendlyURL);
				}
				catch (Exception e) {
					throw new NoSuchLayoutException(e);
				}
			}
			else if (friendlyURL.startsWith(
						VirtualLayoutConstants.CANONICAL_URL_SEPARATOR)) {

				try {
					LayoutFriendlyURLComposite layoutFriendlyURLComposite =
						getVirtualLayoutFriendlyURLComposite(
							privateLayout, friendlyURL, params, requestContext);

					layout = layoutFriendlyURLComposite.getLayout();
					layoutFriendlyURL =
						layoutFriendlyURLComposite.getFriendlyURL();
				}
				catch (Exception e) {
					throw new NoSuchLayoutException(e);
				}
			}
		}

		if (layout == null) {
			LayoutQueryStringComposite layoutQueryStringComposite =
				getActualLayoutQueryStringComposite(
					groupId, privateLayout, friendlyURL, params,
					requestContext);

			layout = layoutQueryStringComposite.getLayout();
			layoutFriendlyURL = layoutQueryStringComposite.getFriendlyURL();
		}

		return new LayoutFriendlyURLComposite(layout, layoutFriendlyURL);
	}

	@Override
	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getLayoutFullURL(layout, themeDisplay, true);
	}

	@Override
	public String getLayoutFullURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException {

		String layoutURL = getLayoutURL(layout, themeDisplay, doAsUser);

		if (!HttpUtil.hasProtocol(layoutURL)) {
			layoutURL = getPortalURL(layout, themeDisplay) + (layoutURL);
		}

		return layoutURL;
	}

	@Override
	public String getLayoutFullURL(long groupId, String portletId)
		throws PortalException {

		return getLayoutFullURL(groupId, portletId, false);
	}

	@Override
	public String getLayoutFullURL(
			long groupId, String portletId, boolean secure)
		throws PortalException {

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
			virtualHostname, getPortalServerPort(secure), secure);

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

	@Override
	public String getLayoutFullURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return getLayoutFullURL(themeDisplay.getLayout(), themeDisplay);
	}

	@Override
	public String getLayoutRelativeURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getLayoutRelativeURL(layout, themeDisplay, true);
	}

	@Override
	public String getLayoutRelativeURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException {

		String layoutFullURL = getLayoutFullURL(layout, themeDisplay, doAsUser);

		return HttpUtil.removeDomain(layoutFullURL);
	}

	@Override
	public String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {

		String virtualHostname = getVirtualHostname(layoutSet);

		if (Validator.isNotNull(virtualHostname) &&
			!StringUtil.equalsIgnoreCase(virtualHostname, _LOCALHOST)) {

			String portalURL = getPortalURL(
				virtualHostname, themeDisplay.getServerPort(),
				themeDisplay.isSecure());

			// Use the layout set's virtual host setting only if the layout set
			// is already used for the current request

			long curLayoutSetId =
				themeDisplay.getLayout().getLayoutSet().getLayoutSetId();

			if ((layoutSet.getLayoutSetId() != curLayoutSetId) ||
				portalURL.startsWith(themeDisplay.getURLPortal())) {

				String layoutSetFriendlyURL = portalURL + _pathContext;

				if (themeDisplay.isI18n()) {
					layoutSetFriendlyURL += themeDisplay.getI18nPath();
				}

				return addPreservedParameters(
					themeDisplay, layoutSetFriendlyURL);
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

		String layoutSetFriendlyURL =
			_pathContext + friendlyURL + group.getFriendlyURL();

		return addPreservedParameters(themeDisplay, layoutSetFriendlyURL);
	}

	@Override
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

	@Override
	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getLayoutURL(layout, themeDisplay, true);
	}

	@Override
	public String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, boolean doAsUser)
		throws PortalException {

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

	@Override
	public String getLayoutURL(
			Layout layout, ThemeDisplay themeDisplay, Locale locale)
		throws PortalException {

		String i18nLanguageId = themeDisplay.getI18nLanguageId();
		String i18nPath = themeDisplay.getI18nPath();
		Locale originalLocale = themeDisplay.getLocale();

		try {
			setThemeDisplayI18n(themeDisplay, locale);

			return getLayoutURL(layout, themeDisplay, true);
		}
		finally {
			resetThemeDisplayI18n(
				themeDisplay, i18nLanguageId, i18nPath, originalLocale);
		}
	}

	@Override
	public String getLayoutURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return getLayoutURL(themeDisplay.getLayout(), themeDisplay);
	}

	@Override
	public LiferayPortletRequest getLiferayPortletRequest(
		PortletRequest portletRequest) {

		PortletRequestImpl portletRequestImpl =
			PortletRequestImpl.getPortletRequestImpl(portletRequest);

		return DoPrivilegedUtil.wrapWhenActive(portletRequestImpl);
	}

	@Override
	public LiferayPortletResponse getLiferayPortletResponse(
		PortletResponse portletResponse) {

		PortletResponseImpl portletResponseImpl =
			PortletResponseImpl.getPortletResponseImpl(portletResponse);

		return DoPrivilegedUtil.wrapWhenActive(portletResponseImpl);
	}

	@Override
	public Locale getLocale(HttpServletRequest request) {
		return getLocale(request, null, false);
	}

	@Override
	public Locale getLocale(
		HttpServletRequest request, HttpServletResponse response,
		boolean initialize) {

		User user = null;

		if (initialize) {
			try {
				user = initUser(request);
			}
			catch (NoSuchUserException nsue) {
				return null;
			}
			catch (Exception e) {
			}
		}

		Locale locale = null;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			locale = themeDisplay.getLocale();

			if (LanguageUtil.isAvailableLocale(
					themeDisplay.getSiteGroupId(), locale)) {

				return locale;
			}
		}

		long groupId = 0;

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		if ((layout != null) && !layout.isTypeControlPanel()) {
			try {
				long scopeGroupId = getScopeGroupId(request);

				groupId = getSiteGroupId(scopeGroupId);
			}
			catch (Exception e) {
			}
		}

		String i18nLanguageId = (String)request.getAttribute(
			WebKeys.I18N_LANGUAGE_ID);

		if (Validator.isNotNull(i18nLanguageId)) {
			locale = LocaleUtil.fromLanguageId(i18nLanguageId);

			if (LanguageUtil.isAvailableLocale(groupId, locale)) {
				return locale;
			}
			else if (groupId > 0) {
				boolean inheritLocales = true;

				try {
					inheritLocales = LanguageUtil.isInheritLocales(groupId);
				}
				catch (PortalException pe) {
					_log.error(pe);
				}

				if (!inheritLocales) {
					String i18nLanguageCode = (String)request.getAttribute(
						WebKeys.I18N_LANGUAGE_CODE);

					locale = LanguageUtil.getLocale(groupId, i18nLanguageCode);

					if (LanguageUtil.isAvailableLocale(groupId, locale)) {
						return locale;
					}
				}
			}
		}

		String doAsUserLanguageId = ParamUtil.getString(
			request, "doAsUserLanguageId");

		if (Validator.isNotNull(doAsUserLanguageId)) {
			locale = LocaleUtil.fromLanguageId(doAsUserLanguageId);

			if (LanguageUtil.isAvailableLocale(groupId, locale)) {
				return locale;
			}
		}

		HttpSession session = request.getSession(false);

		if ((session != null) &&
			session.getAttribute(Globals.LOCALE_KEY) != null) {

			locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);

			if (LanguageUtil.isAvailableLocale(groupId, locale)) {
				return locale;
			}
		}

		// Get locale from the user

		if (user == null) {
			try {
				user = getUser(request);
			}
			catch (Exception e) {
			}
		}

		if ((user != null) && !user.isDefaultUser()) {
			Locale userLocale = getAvailableLocale(groupId, user.getLocale());

			if (userLocale != null) {
				if (LanguageUtil.isAvailableLocale(groupId, userLocale)) {
					if (initialize) {
						setLocale(request, response, userLocale);
					}

					return userLocale;
				}
			}
		}

		// Get locale from the cookie

		String languageId = CookieKeys.getCookie(
			request, CookieKeys.GUEST_LANGUAGE_ID, false);

		if (Validator.isNotNull(languageId)) {
			Locale cookieLocale = getAvailableLocale(
				groupId, LocaleUtil.fromLanguageId(languageId));

			if (cookieLocale != null) {
				if (LanguageUtil.isAvailableLocale(groupId, cookieLocale)) {
					if (initialize) {
						setLocale(request, response, cookieLocale);
					}

					return cookieLocale;
				}
			}
		}

		// Get locale from the request

		if (PropsValues.LOCALE_DEFAULT_REQUEST) {
			Enumeration<Locale> locales = request.getLocales();

			while (locales.hasMoreElements()) {
				Locale requestLocale = getAvailableLocale(
					groupId, locales.nextElement());

				if (requestLocale != null) {
					if (LanguageUtil.isAvailableLocale(
							groupId, requestLocale)) {

						if (initialize) {
							setLocale(request, response, requestLocale);
						}

						return requestLocale;
					}
				}
			}
		}

		// Get locale from the default user

		Company company = null;

		try {
			company = getCompany(request);
		}
		catch (Exception e) {
		}

		if (company == null) {
			return null;
		}

		User defaultUser = null;

		try {
			defaultUser = company.getDefaultUser();
		}
		catch (Exception e) {
		}

		if (defaultUser == null) {
			return null;
		}

		Locale defaultUserLocale = getAvailableLocale(
			groupId, defaultUser.getLocale());

		if (defaultUserLocale != null) {
			if (LanguageUtil.isAvailableLocale(groupId, defaultUserLocale)) {
				if (initialize) {
					setLocale(request, response, defaultUserLocale);
				}

				return defaultUserLocale;
			}
		}

		try {
			if (themeDisplay != null) {
				return themeDisplay.getSiteDefaultLocale();
			}
		}
		catch (Exception e) {
		}

		try {
			return getSiteDefaultLocale(groupId);
		}
		catch (Exception e) {
			return LocaleUtil.getDefault();
		}
	}

	@Override
	public Locale getLocale(PortletRequest portletRequest) {
		return getLocale(getHttpServletRequest(portletRequest));
	}

	@Override
	public String getLocalizedFriendlyURL(
			HttpServletRequest request, Layout layout, Locale locale,
			Locale originalLocale)
		throws Exception {

		String contextPath = getPathContext();

		String requestURI = request.getRequestURI();

		if (Validator.isNotNull(contextPath) &&
			requestURI.contains(contextPath)) {

			requestURI = requestURI.substring(contextPath.length());
		}

		requestURI = StringUtil.replace(
			requestURI, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		String path = request.getPathInfo();

		int x = path.indexOf(CharPool.SLASH, 1);

		String layoutFriendlyURL = null;

		if (originalLocale == null) {
			if ((x != -1) && ((x + 1) != path.length())) {
				layoutFriendlyURL = path.substring(x);
			}

			int y = layoutFriendlyURL.indexOf(
				VirtualLayoutConstants.CANONICAL_URL_SEPARATOR);

			if (y != -1) {
				y = layoutFriendlyURL.indexOf(CharPool.SLASH, 3);

				if ((y != -1) && ((y + 1) != layoutFriendlyURL.length())) {
					layoutFriendlyURL = layoutFriendlyURL.substring(y);
				}
			}

			y = layoutFriendlyURL.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

			if (y != -1) {
				layoutFriendlyURL = layoutFriendlyURL.substring(0, y);
			}
		}
		else {
			layoutFriendlyURL = layout.getFriendlyURL(originalLocale);
		}

		if (requestURI.contains(layoutFriendlyURL)) {
			requestURI = StringUtil.replaceFirst(
				requestURI, layoutFriendlyURL, layout.getFriendlyURL(locale));
		}

		String i18nPath = getI18nPathLanguageId(
			locale, LocaleUtil.toLanguageId(locale));

		boolean appendI18nPath = true;

		if ((PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 0) ||
			((PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 1) &&
			 locale.equals(LocaleUtil.getDefault()))) {

			appendI18nPath = false;
		}

		String localizedFriendlyURL = contextPath;

		if (appendI18nPath) {
			localizedFriendlyURL += StringPool.SLASH + i18nPath;
		}

		localizedFriendlyURL += requestURI;

		String queryString = request.getQueryString();

		if (Validator.isNotNull(queryString)) {
			localizedFriendlyURL +=
				StringPool.QUESTION + request.getQueryString();
		}

		return localizedFriendlyURL;
	}

	@Override
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

	@Override
	public String getNetvibesURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException {

		return getServletURL(
			portlet, PropsValues.NETVIBES_SERVLET_MAPPING, themeDisplay);
	}

	@Override
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

	@Override
	public HttpServletRequest getOriginalServletRequest(
		HttpServletRequest request) {

		List<PersistentHttpServletRequestWrapper>
			persistentHttpServletRequestWrappers = new ArrayList<>();

		HttpServletRequest originalRequest = request;

		while (originalRequest.getClass().getName().startsWith(
					"com.liferay.")) {

			if (originalRequest instanceof
					PersistentHttpServletRequestWrapper) {

				PersistentHttpServletRequestWrapper
					persistentHttpServletRequestWrapper =
						(PersistentHttpServletRequestWrapper)originalRequest;

				persistentHttpServletRequestWrappers.add(
					persistentHttpServletRequestWrapper.clone());
			}

			// Get original request so that portlets inside portlets render
			// properly

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)originalRequest;

			originalRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		for (int i = persistentHttpServletRequestWrappers.size() - 1; i >= 0;
				i--) {

			HttpServletRequestWrapper httpServletRequestWrapper =
				persistentHttpServletRequestWrappers.get(i);

			httpServletRequestWrapper.setRequest(originalRequest);

			originalRequest = httpServletRequestWrapper;
		}

		return originalRequest;
	}

	/**
	 * @deprecated As of 6.2.0 renamed to #getSiteGroupId(groupId)
	 */
	@Deprecated
	@Override
	public long getParentGroupId(long groupId) {
		return getSiteGroupId(groupId);
	}

	@Override
	public String getPathContext() {
		return _pathContext;
	}

	@Override
	public String getPathContext(HttpServletRequest request) {
		return getPathContext(request.getContextPath());
	}

	@Override
	public String getPathContext(PortletRequest portletRequest) {
		return getPathContext(portletRequest.getContextPath());
	}

	@Override
	public String getPathContext(String contextPath) {
		return _pathProxy.concat(ContextPathUtil.getContextPath(contextPath));
	}

	@Override
	public String getPathFriendlyURLPrivateGroup() {
		return _pathFriendlyURLPrivateGroup;
	}

	@Override
	public String getPathFriendlyURLPrivateUser() {
		return _pathFriendlyURLPrivateUser;
	}

	@Override
	public String getPathFriendlyURLPublic() {
		return _pathFriendlyURLPublic;
	}

	@Override
	public String getPathImage() {
		return _pathImage;
	}

	@Override
	public String getPathMain() {
		return _pathMain;
	}

	@Override
	public String getPathModule() {
		return _pathModule;
	}

	@Override
	public String getPathProxy() {
		return _pathProxy;
	}

	@Override
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

		if (group == null) {
			return LayoutConstants.DEFAULT_PLID;
		}

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

		return LayoutConstants.DEFAULT_PLID;
	}

	@Override
	public long getPlidFromPortletId(
			long groupId, boolean privateLayout, String portletId)
		throws PortalException {

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
			plid = doGetPlidFromPortletId(groupId, privateLayout, portletId);

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

				plid = doGetPlidFromPortletId(
					groupId, privateLayout, portletId);

				if (plid != LayoutConstants.DEFAULT_PLID) {
					_plidToPortletIdMap.put(key, plid);
				}
			}
		}

		return plid;
	}

	@Override
	public long getPlidFromPortletId(long groupId, String portletId)
		throws PortalException {

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

	@Override
	public PortalInetSocketAddressEventListener[]
		getPortalInetSocketAddressEventListeners() {

		return _portalInetSocketAddressEventListeners.toArray(
			new PortalInetSocketAddressEventListener[
				_portalInetSocketAddressEventListeners.size()]);
	}

	@Override
	public String getPortalLibDir() {
		return PropsValues.LIFERAY_LIB_PORTAL_DIR;
	}

	@Override
	public InetAddress getPortalLocalInetAddress(boolean secure) {
		InetSocketAddress inetSocketAddress = null;

		if (secure) {
			inetSocketAddress = _securePortalLocalInetSocketAddress.get();
		}
		else {
			inetSocketAddress = _portalLocalInetSocketAddress.get();
		}

		if (inetSocketAddress == null) {
			return null;
		}

		return inetSocketAddress.getAddress();
	}

	@Override
	public int getPortalLocalPort(boolean secure) {
		InetSocketAddress inetSocketAddress = null;

		if (secure) {
			inetSocketAddress = _securePortalLocalInetSocketAddress.get();
		}
		else {
			inetSocketAddress = _portalLocalInetSocketAddress.get();
		}

		if (inetSocketAddress == null) {
			return -1;
		}

		return inetSocketAddress.getPort();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getPortalPort(boolean)}
	 */
	@Deprecated
	@Override
	public int getPortalPort() {
		return getPortalServerPort(false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getPortalServerPort(boolean)}
	 */
	@Deprecated
	@Override
	public int getPortalPort(boolean secure) {
		return getPortalServerPort(secure);
	}

	@Override
	public Properties getPortalProperties() {
		return PropsUtil.getProperties();
	}

	@Override
	public InetAddress getPortalServerInetAddress(boolean secure) {
		InetSocketAddress inetSocketAddress = null;

		if (secure) {
			inetSocketAddress = _securePortalServerInetSocketAddress.get();
		}
		else {
			inetSocketAddress = _portalServerInetSocketAddress.get();
		}

		if (inetSocketAddress == null) {
			return null;
		}

		return inetSocketAddress.getAddress();
	}

	@Override
	public int getPortalServerPort(boolean secure) {
		InetSocketAddress inetSocketAddress = null;

		if (secure) {
			inetSocketAddress = _securePortalServerInetSocketAddress.get();
		}
		else {
			inetSocketAddress = _portalServerInetSocketAddress.get();
		}

		if (inetSocketAddress == null) {
			return -1;
		}

		return inetSocketAddress.getPort();
	}

	@Override
	public String getPortalURL(HttpServletRequest request) {
		return getPortalURL(request, isSecure(request));
	}

	@Override
	public String getPortalURL(HttpServletRequest request, boolean secure) {
		return getPortalURL(
			request.getServerName(), request.getServerPort(), secure);
	}

	@Override
	public String getPortalURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		String serverName = themeDisplay.getServerName();

		if (layout == null) {
			layout = themeDisplay.getLayout();
		}

		if (layout != null) {
			Layout virtualHostLayout = layout;

			long refererPlid = themeDisplay.getRefererPlid();

			if (refererPlid > 0) {
				virtualHostLayout = LayoutLocalServiceUtil.getLayout(
					refererPlid);
			}

			LayoutSet virtualHostLayoutSet = virtualHostLayout.getLayoutSet();

			String virtualHostname = virtualHostLayoutSet.getVirtualHostname();

			String domain = HttpUtil.getDomain(themeDisplay.getURLPortal());

			if (Validator.isNotNull(virtualHostname) &&
				domain.startsWith(virtualHostname)) {

				serverName = virtualHostname;
			}
		}

		return getPortalURL(
			serverName, themeDisplay.getServerPort(), themeDisplay.isSecure());
	}

	@Override
	public String getPortalURL(PortletRequest portletRequest) {
		return getPortalURL(portletRequest, portletRequest.isSecure());
	}

	@Override
	public String getPortalURL(PortletRequest portletRequest, boolean secure) {
		return getPortalURL(
			portletRequest.getServerName(), portletRequest.getServerPort(),
			secure);
	}

	@Override
	public String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		StringBundler sb = new StringBundler(4);

		boolean https =
			(secure ||
			 StringUtil.equalsIgnoreCase(
				Http.HTTPS, PropsValues.WEB_SERVER_PROTOCOL));

		if (https) {
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

		if (!https) {
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
		else {
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

	@Override
	public String getPortalURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortalURL(null, themeDisplay);
	}

	@Override
	public String getPortalWebDir() {
		return PropsValues.LIFERAY_WEB_PORTAL_DIR;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             AuthTokenWhitelistUtil#getPortletInvocationWhitelist}
	 */
	@Deprecated
	@Override
	public Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return AuthTokenWhitelistUtil.getPortletInvocationWhitelist();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             AuthTokenWhitelistUtil#getPortletInvocationWhitelistActions}
	 */
	@Deprecated
	@Override
	public Set<String> getPortletAddDefaultResourceCheckWhitelistActions() {
		return AuthTokenWhitelistUtil.getPortletInvocationWhitelistActions();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link
	 *             #getPortletBreadcrumbs(HttpServletRequest)}
	 */
	@Deprecated
	@Override
	public List<BreadcrumbEntry> getPortletBreadcrumbList(
		HttpServletRequest request) {

		return getPortletBreadcrumbs(request);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             BreadcrumbUtil#getPortletBreadcrumbEntries(
	 *             HttpServletRequest)}
	 */
	@Deprecated
	@Override
	public List<BreadcrumbEntry> getPortletBreadcrumbs(
		HttpServletRequest request) {

		return BreadcrumbUtil.getPortletBreadcrumbEntries(request);
	}

	@Override
	public PortletConfig getPortletConfig(
			long companyId, String portletId, ServletContext servletContext)
		throws PortletException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		return invokerPortlet.getPortletConfig();
	}

	@Override
	public String getPortletDescription(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		String portletDescription = ResourceBundleUtil.getString(
			resourceBundle,
			JavaConstants.JAVAX_PORTLET_DESCRIPTION.concat(
				StringPool.PERIOD).concat(portlet.getRootPortletId()));

		if (Validator.isNull(portletDescription)) {
			portletDescription = ResourceBundleUtil.getString(
				resourceBundle, JavaConstants.JAVAX_PORTLET_DESCRIPTION);
		}

		return portletDescription;
	}

	@Override
	public String getPortletDescription(Portlet portlet, User user) {
		return getPortletDescription(portlet.getPortletId(), user);
	}

	@Override
	public String getPortletDescription(String portletId, Locale locale) {
		return LanguageUtil.get(
			locale,
			JavaConstants.JAVAX_PORTLET_DESCRIPTION.concat(
				StringPool.PERIOD).concat(portletId));
	}

	@Override
	public String getPortletDescription(String portletId, String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return getPortletDescription(portletId, locale);
	}

	@Override
	public String getPortletDescription(String portletId, User user) {
		return LanguageUtil.get(
			user.getLocale(),
			JavaConstants.JAVAX_PORTLET_DESCRIPTION.concat(
				StringPool.PERIOD).concat(portletId));
	}

	public LayoutQueryStringComposite
		getPortletFriendlyURLMapperLayoutQueryStringComposite(
			long groupId, boolean privateLayout, String url,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException {

		boolean foundFriendlyURLMapper = false;

		String friendlyURL = url;
		String queryString = StringPool.BLANK;

		List<Portlet> portlets =
			PortletLocalServiceUtil.getFriendlyURLMapperPortlets();

		for (Portlet portlet : portlets) {
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
					new InheritableMap<>();

				if (params != null) {
					actualParams.setParentMap(params);
				}

				Map<String, String> prpIdentifiers = new HashMap<>();

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
						url.substring(pos + 2), actualParams, requestContext);
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
						actualParams = new HashMap<>(params);
					}
					else {
						actualParams = new HashMap<>();
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

		return new LayoutQueryStringComposite(layout, friendlyURL, queryString);
	}

	@Override
	public String getPortletId(HttpServletRequest request) {
		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (liferayPortletConfig != null) {
			return liferayPortletConfig.getPortletId();
		}
		else {
			return null;
		}
	}

	@Override
	public String getPortletId(PortletRequest portletRequest) {
		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)portletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (liferayPortletConfig != null) {
			return liferayPortletConfig.getPortletId();
		}
		else {
			return null;
		}
	}

	@Override
	public String getPortletLongTitle(Portlet portlet, Locale locale) {
		return getPortletLongTitle(portlet.getPortletId(), locale);
	}

	@Override
	public String getPortletLongTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		try {
			String portletLongTitle = resourceBundle.getString(
				JavaConstants.JAVAX_PORTLET_LONG_TITLE);

			if (portletLongTitle.startsWith(
					JavaConstants.JAVAX_PORTLET_LONG_TITLE)) {

				portletLongTitle = getPortletTitle(
					portlet, servletContext, locale);
			}

			return portletLongTitle;
		}
		catch (Exception e) {
			return getPortletTitle(portlet, servletContext, locale);
		}
	}

	@Override
	public String getPortletLongTitle(Portlet portlet, String languageId) {
		return getPortletLongTitle(portlet.getPortletId(), languageId);
	}

	@Override
	public String getPortletLongTitle(Portlet portlet, User user) {
		return getPortletLongTitle(portlet.getPortletId(), user);
	}

	@Override
	public String getPortletLongTitle(String portletId, Locale locale) {
		String portletLongTitle = LanguageUtil.get(
			locale,
			JavaConstants.JAVAX_PORTLET_LONG_TITLE.concat(
				StringPool.PERIOD).concat(portletId),
			StringPool.BLANK);

		if (Validator.isNull(portletLongTitle)) {
			portletLongTitle = getPortletTitle(portletId, locale);
		}

		return portletLongTitle;
	}

	@Override
	public String getPortletLongTitle(String portletId, String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return getPortletLongTitle(portletId, locale);
	}

	@Override
	public String getPortletLongTitle(String portletId, User user) {
		return getPortletLongTitle(portletId, user.getLocale());
	}

	@Override
	public String getPortletNamespace(String portletId) {
		return StringPool.UNDERLINE.concat(portletId).concat(
			StringPool.UNDERLINE);
	}

	@Override
	public String getPortletTitle(Portlet portlet, Locale locale) {
		return getPortletTitle(portlet.getPortletId(), locale);
	}

	@Override
	public String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		String portletTitle = ResourceBundleUtil.getString(
			resourceBundle,
			JavaConstants.JAVAX_PORTLET_TITLE.concat(StringPool.PERIOD).concat(
				portlet.getRootPortletId()));

		if (Validator.isNull(portletTitle)) {
			portletTitle = ResourceBundleUtil.getString(
				resourceBundle, JavaConstants.JAVAX_PORTLET_TITLE);
		}

		return portletTitle;
	}

	@Override
	public String getPortletTitle(Portlet portlet, String languageId) {
		return getPortletTitle(portlet.getPortletId(), languageId);
	}

	@Override
	public String getPortletTitle(Portlet portlet, User user) {
		return getPortletTitle(portlet.getPortletId(), user);
	}

	@Override
	public String getPortletTitle(PortletRequest portletRequest) {
		String portletId = (String)portletRequest.getAttribute(
			WebKeys.PORTLET_ID);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		HttpServletRequest request = getHttpServletRequest(portletRequest);

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		Locale locale = portletRequest.getLocale();

		return getPortletTitle(portlet, servletContext, locale);
	}

	@Override
	public String getPortletTitle(PortletResponse portletResponse) {
		PortletResponseImpl portletResponseImpl =
			PortletResponseImpl.getPortletResponseImpl(portletResponse);

		return ((RenderResponseImpl)portletResponseImpl).getTitle();
	}

	@Override
	public String getPortletTitle(String portletId, Locale locale) {
		return LanguageUtil.get(
			locale,
			JavaConstants.JAVAX_PORTLET_TITLE.concat(StringPool.PERIOD).concat(
				portletId));
	}

	@Override
	public String getPortletTitle(
		String portletId, ResourceBundle resourceBundle) {

		return LanguageUtil.get(
			resourceBundle,
			JavaConstants.JAVAX_PORTLET_TITLE.concat(StringPool.PERIOD).concat(
				portletId));
	}

	@Override
	public String getPortletTitle(String portletId, String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return getPortletTitle(portletId, locale);
	}

	@Override
	public String getPortletTitle(String portletId, User user) {
		return LanguageUtil.get(
			user.getLocale(),
			JavaConstants.JAVAX_PORTLET_TITLE.concat(StringPool.PERIOD).concat(
				portletId));
	}

	@Override
	public String getPortletXmlFileName() {
		if (PrefsPropsUtil.getBoolean(
				PropsKeys.AUTO_DEPLOY_CUSTOM_PORTLET_XML,
				PropsValues.AUTO_DEPLOY_CUSTOM_PORTLET_XML)) {

			return PORTLET_XML_FILE_NAME_CUSTOM;
		}
		else {
			return PORTLET_XML_FILE_NAME_STANDARD;
		}
	}

	@Override
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

	@Override
	public PreferencesValidator getPreferencesValidator(Portlet portlet) {
		PortletBag portletBag = PortletBagPool.get(portlet.getRootPortletId());

		if (portletBag == null) {
			return null;
		}

		List<PreferencesValidator> preferencesValidatorInstances =
			portletBag.getPreferencesValidatorInstances();

		if (preferencesValidatorInstances.isEmpty()) {
			return null;
		}

		return preferencesValidatorInstances.get(0);
	}

	@Override
	public String getRelativeHomeURL(HttpServletRequest request)
		throws PortalException {

		Company company = getCompany(request);

		String homeURL = company.getHomeURL();

		if (Validator.isNull(homeURL)) {
			homeURL = PropsValues.COMPANY_DEFAULT_HOME_URL;
		}

		return homeURL;
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public long getScopeGroupId(HttpServletRequest request)
		throws PortalException {

		String portletId = getPortletId(request);

		return getScopeGroupId(request, portletId);
	}

	@Override
	public long getScopeGroupId(HttpServletRequest request, String portletId)
		throws PortalException {

		return getScopeGroupId(request, portletId, false);
	}

	@Override
	public long getScopeGroupId(
			HttpServletRequest request, String portletId,
			boolean checkStagingGroup)
		throws PortalException {

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		long scopeGroupId = 0;

		if (layout != null) {
			Group group = layout.getGroup();

			long doAsGroupId = ParamUtil.getLong(request, "doAsGroupId");

			if (doAsGroupId <= 0) {
				HttpServletRequest originalRequest = getOriginalServletRequest(
					request);

				doAsGroupId = ParamUtil.getLong(originalRequest, "doAsGroupId");
			}

			Group doAsGroup = null;

			if (doAsGroupId > 0) {
				doAsGroup = GroupLocalServiceUtil.fetchGroup(doAsGroupId);
			}

			if (group.isControlPanel()) {
				if (doAsGroup == null) {
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
			else if (doAsGroup != null) {
				scopeGroupId = doAsGroupId;
			}

			if ((group != null) && group.isInheritContent()) {
				Group layoutGroup = layout.getGroup();

				if (!layoutGroup.isControlPanel()) {
					scopeGroupId = group.getParentGroupId();
				}
			}

			if ((portletId != null) && (group != null) &&
				(group.isStaged() || group.isStagingGroup())) {

				Group liveGroup = group;

				if (group.isStagingGroup()) {
					liveGroup = group.getLiveGroup();
				}

				if (liveGroup.isStaged() &&
					!liveGroup.isStagedPortlet(portletId)) {

					Layout liveGroupLayout =
						LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
								layout.getUuid(), liveGroup.getGroupId(),
								layout.isPrivateLayout());

					if ((liveGroupLayout != null) &&
						liveGroupLayout.hasScopeGroup()) {

						scopeGroupId = getScopeGroupId(
							liveGroupLayout, portletId);
					}
					else if (checkStagingGroup &&
							 !liveGroup.isStagedRemotely()) {

						Group stagingGroup = liveGroup.getStagingGroup();

						scopeGroupId = stagingGroup.getGroupId();
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

	@Override
	public long getScopeGroupId(Layout layout) {
		if (layout == null) {
			return 0;
		}
		else {
			return layout.getGroupId();
		}
	}

	@Override
	public long getScopeGroupId(Layout layout, String portletId) {
		if (layout == null) {
			return 0;
		}

		if (Validator.isNull(portletId)) {
			return layout.getGroupId();
		}

		try {
			PortletPreferences portletSetup =
				PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
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

			String scopeLayoutUuid = GetterUtil.getString(
				portletSetup.getValue("lfrScopeLayoutUuid", null));

			Layout scopeLayout =
				LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
					scopeLayoutUuid, layout.getGroupId(),
					layout.isPrivateLayout());

			Group scopeGroup = scopeLayout.getScopeGroup();

			return scopeGroup.getGroupId();
		}
		catch (Exception e) {
			return layout.getGroupId();
		}
	}

	@Override
	public long getScopeGroupId(long plid) {
		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getLayout(plid);
		}
		catch (Exception e) {
		}

		return getScopeGroupId(layout);
	}

	@Override
	public long getScopeGroupId(PortletRequest portletRequest)
		throws PortalException {

		return getScopeGroupId(getHttpServletRequest(portletRequest));
	}

	@Override
	public User getSelectedUser(HttpServletRequest request)
		throws PortalException {

		return getSelectedUser(request, true);
	}

	@Override
	public User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException {

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

	@Override
	public User getSelectedUser(PortletRequest portletRequest)
		throws PortalException {

		return getSelectedUser(portletRequest, true);
	}

	@Override
	public User getSelectedUser(
			PortletRequest portletRequest, boolean checkPermission)
		throws PortalException {

		return getSelectedUser(
			getHttpServletRequest(portletRequest), checkPermission);
	}

	@Override
	public String getServletContextName() {
		return _servletContextName;
	}

	@Override
	public long[] getSharedContentSiteGroupIds(
			long companyId, long groupId, long userId)
		throws PortalException {

		Set<Group> groups = new LinkedHashSet<>();

		Group siteGroup = doGetCurrentSiteGroup(groupId);

		if (siteGroup != null) {

			// Current site

			groups.add(siteGroup);

			// Descendant sites

			groups.addAll(siteGroup.getDescendants(true));

			// Layout scopes

			groups.addAll(
				GroupLocalServiceUtil.getGroups(
					siteGroup.getCompanyId(), Layout.class.getName(),
					siteGroup.getGroupId()));
		}

		// Administered sites

		if (PrefsPropsUtil.getBoolean(
				companyId,
				PropsKeys.
					SITES_CONTENT_SHARING_THROUGH_ADMINISTRATORS_ENABLED)) {

			LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

			groupParams.put("site", Boolean.TRUE);
			groupParams.put("usersGroups", userId);

			groups.addAll(
				GroupLocalServiceUtil.search(
					companyId, null, null, groupParams, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null));
		}

		// Ancestor sites and global site

		int sitesContentSharingWithChildrenEnabled = PrefsPropsUtil.getInteger(
			companyId, PropsKeys.SITES_CONTENT_SHARING_WITH_CHILDREN_ENABLED);

		if (sitesContentSharingWithChildrenEnabled !=
				Sites.CONTENT_SHARING_WITH_CHILDREN_DISABLED) {

			groups.addAll(doGetAncestorSiteGroups(groupId, true));
		}

		long[] groupIds = new long[groups.size()];

		int i = 0;

		for (Group group : groups) {
			groupIds[i++] = group.getGroupId();
		}

		return groupIds;
	}

	@Override
	public Map<String, List<Portlet>> getSiteAdministrationCategoriesMap(
		HttpServletRequest request) {

		return getCategoriesMap(
			request, WebKeys.SITE_ADMINISTRATION_CATEGORIES_MAP,
			PortletCategoryKeys.SITE_ADMINISTRATION_ALL);
	}

	@Override
	public PortletURL getSiteAdministrationURL(
		HttpServletRequest request, ThemeDisplay themeDisplay) {

		Portlet portlet = getFirstSiteAdministrationPortlet(themeDisplay);

		if (portlet == null) {
			return null;
		}

		return getSiteAdministrationURL(
			request, themeDisplay, portlet.getPortletId());
	}

	@Override
	public PortletURL getSiteAdministrationURL(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		String portletId) {

		LiferayPortletURL siteAdministrationURL = PortletURLFactoryUtil.create(
			request, portletId, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		siteAdministrationURL.setControlPanelCategory(
			PortletCategoryKeys.SITES);
		siteAdministrationURL.setDoAsGroupId(themeDisplay.getScopeGroupId());
		siteAdministrationURL.setParameter(
			"redirect", themeDisplay.getURLCurrent());

		return siteAdministrationURL;
	}

	@Override
	public PortletURL getSiteAdministrationURL(
		PortletResponse portletResponse, ThemeDisplay themeDisplay) {

		Portlet portlet = getFirstSiteAdministrationPortlet(themeDisplay);

		if (portlet == null) {
			return null;
		}

		return getSiteAdministrationURL(
			portletResponse, themeDisplay, portlet.getPortletId());
	}

	@Override
	public PortletURL getSiteAdministrationURL(
		PortletResponse portletResponse, ThemeDisplay themeDisplay,
		String portletName) {

		LiferayPortletResponse liferayPortletResponse =
			(LiferayPortletResponse)portletResponse;

		LiferayPortletURL siteAdministrationURL =
			liferayPortletResponse.createRenderURL(portletName);

		siteAdministrationURL.setControlPanelCategory(
			PortletCategoryKeys.SITES);
		siteAdministrationURL.setDoAsGroupId(themeDisplay.getScopeGroupId());
		siteAdministrationURL.setParameter(
			"redirect", themeDisplay.getURLCurrent());

		return siteAdministrationURL;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getCurrentAndAncestorSiteGroupIds(long)}
	 */
	@Deprecated
	@Override
	public long[] getSiteAndCompanyGroupIds(long groupId)
		throws PortalException {

		Group scopeGroup = GroupLocalServiceUtil.getGroup(groupId);

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			scopeGroup.getCompanyId());

		if (scopeGroup.isLayout()) {
			return new long[] {
				groupId, scopeGroup.getParentGroupId(),
				companyGroup.getGroupId()
			};
		}
		else if (scopeGroup.isLayoutSetPrototype() ||
				 scopeGroup.isOrganization() || scopeGroup.isRegularSite() ||
				 scopeGroup.isUser()) {

			return new long[] {groupId, companyGroup.getGroupId()};
		}
		else {
			return new long[] {companyGroup.getGroupId()};
		}
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getCurrentAndAncestorSiteGroupIds(long)}
	 */
	@Deprecated
	@Override
	public long[] getSiteAndCompanyGroupIds(ThemeDisplay themeDisplay)
		throws PortalException {

		return getSiteAndCompanyGroupIds(themeDisplay.getScopeGroupId());
	}

	@Override
	public Locale getSiteDefaultLocale(long groupId) throws PortalException {
		if (groupId <= 0) {
			return LocaleUtil.getDefault();
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Group liveGroup = group;

		if (group.isStagingGroup()) {
			liveGroup = group.getLiveGroup();
		}

		if (LanguageUtil.isInheritLocales(liveGroup.getGroupId())) {
			return LocaleUtil.getDefault();
		}

		UnicodeProperties typeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		User defaultUser = UserLocalServiceUtil.getDefaultUser(
			group.getCompanyId());

		String languageId = GetterUtil.getString(
			typeSettingsProperties.getProperty("languageId"),
			defaultUser.getLanguageId());

		return LocaleUtil.fromLanguageId(languageId);
	}

	@Override
	public long getSiteGroupId(long groupId) {
		if (groupId <= 0) {
			return 0;
		}

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		if (group == null) {
			return 0;
		}

		long siteGroupId = groupId;

		if (group.isLayout()) {
			siteGroupId = group.getParentGroupId();
		}

		return siteGroupId;
	}

	@Override
	public String getSiteLoginURL(ThemeDisplay themeDisplay)
		throws PortalException {

		if (Validator.isNull(PropsValues.AUTH_LOGIN_SITE_URL)) {
			return null;
		}

		List<Layout> layouts = themeDisplay.getUnfilteredLayouts();

		if (layouts == null) {
			return null;
		}

		for (Layout layout : layouts) {
			String friendlyURL = layout.getFriendlyURL(
				themeDisplay.getLocale());

			if (friendlyURL.equals(PropsValues.AUTH_LOGIN_SITE_URL)) {
				if (themeDisplay.getLayout() == null) {
					break;
				}

				String layoutSetFriendlyURL = getLayoutSetFriendlyURL(
					layout.getLayoutSet(), themeDisplay);

				return layoutSetFriendlyURL + PropsValues.AUTH_LOGIN_SITE_URL;
			}
		}

		return null;
	}

	@Override
	public String getStaticResourceURL(HttpServletRequest request, String uri) {
		return getStaticResourceURL(request, uri, null, 0);
	}

	@Override
	public String getStaticResourceURL(
		HttpServletRequest request, String uri, long timestamp) {

		return getStaticResourceURL(request, uri, null, timestamp);
	}

	@Override
	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString) {

		return getStaticResourceURL(request, uri, queryString, 0);
	}

	@Override
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

		StringBundler sb = new StringBundler(18);

		// URI

		sb.append(uri);
		sb.append(StringPool.QUESTION);

		// Browser id

		if ((parameterMap == null) || !parameterMap.containsKey("browserId")) {
			sb.append("&browserId=");
			sb.append(BrowserSnifferUtil.getBrowserId(request));
		}

		// Theme and color scheme

		if ((uri.endsWith(".css") || uri.endsWith(".jsp")) &&
			((parameterMap == null) || !parameterMap.containsKey("themeId"))) {

			sb.append("&themeId=");
			sb.append(HttpUtil.encodeURL(theme.getThemeId()));
		}

		if (uri.endsWith(".jsp") &&
			((parameterMap == null) ||
			 !parameterMap.containsKey("colorSchemeId"))) {

			sb.append("&colorSchemeId=");
			sb.append(HttpUtil.encodeURL(colorScheme.getColorSchemeId()));
		}

		// Minifier

		if ((parameterMap == null) ||
			!parameterMap.containsKey("minifierType")) {

			String minifierType = StringPool.BLANK;

			if (uri.endsWith(".css") || uri.endsWith("css.jsp") ||
				(uri.endsWith(".jsp") && uri.contains("/css/"))) {

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

		if (((parameterMap == null) || !parameterMap.containsKey("t")) &&
			 !(timestamp < 0)) {

			if ((timestamp == 0) && uri.startsWith(StrutsUtil.TEXT_HTML_DIR)) {
				ServletContext servletContext =
					(ServletContext)request.getAttribute(WebKeys.CTX);

				timestamp = ServletContextUtil.getLastModified(
					servletContext, uri, true);
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

	@Override
	public String getStrutsAction(HttpServletRequest request) {
		String strutsAction = ParamUtil.getString(request, "struts_action");

		if (Validator.isNotNull(strutsAction)) {

			// This method should only return a Struts action if you're dealing
			// with a regular HTTP servlet request, not a portlet HTTP servlet
			// request.

			return StringPool.BLANK;
		}

		return getPortletParam(request, "struts_action");
	}

	@Override
	public String[] getSystemGroups() {
		return _allSystemGroups;
	}

	@Override
	public String[] getSystemOrganizationRoles() {
		return _allSystemOrganizationRoles;
	}

	@Override
	public String[] getSystemRoles() {
		return _allSystemRoles;
	}

	@Override
	public String[] getSystemSiteRoles() {
		return _allSystemSiteRoles;
	}

	@Override
	public String getUniqueElementId(
		HttpServletRequest request, String namespace, String elementId) {

		String uniqueElementId = elementId;

		Set<String> uniqueElementIds = (Set<String>)request.getAttribute(
			WebKeys.UNIQUE_ELEMENT_IDS);

		if (uniqueElementIds == null) {
			uniqueElementIds = new ConcurrentHashSet<>();

			request.setAttribute(WebKeys.UNIQUE_ELEMENT_IDS, uniqueElementIds);
		}
		else {
			int i = 1;

			while (uniqueElementIds.contains(
						namespace.concat(uniqueElementId))) {

				if (Validator.isNull(elementId) ||
					elementId.endsWith(StringPool.UNDERLINE)) {

					uniqueElementId = elementId.concat(String.valueOf(i));
				}
				else {
					uniqueElementId =
						elementId.concat(StringPool.UNDERLINE).concat(
							String.valueOf(i));
				}

				i++;
			}
		}

		uniqueElementIds.add(namespace.concat(uniqueElementId));

		return uniqueElementId;
	}

	@Override
	public String getUniqueElementId(
		PortletRequest request, String namespace, String elementId) {

		return getUniqueElementId(
			getHttpServletRequest(request), namespace, elementId);
	}

	@Override
	public UploadPortletRequest getUploadPortletRequest(
		PortletRequest portletRequest) {

		PortletRequestImpl portletRequestImpl =
			PortletRequestImpl.getPortletRequestImpl(portletRequest);

		DynamicServletRequest dynamicRequest =
			(DynamicServletRequest)portletRequestImpl.getHttpServletRequest();

		HttpServletRequestWrapper requestWrapper =
			(HttpServletRequestWrapper)dynamicRequest.getRequest();

		UploadServletRequest uploadServletRequest = getUploadServletRequest(
			requestWrapper);

		return new UploadPortletRequestImpl(
			uploadServletRequest, portletRequestImpl,
			getPortletNamespace(portletRequestImpl.getPortletName()));
	}

	@Override
	public UploadServletRequest getUploadServletRequest(
		HttpServletRequest request) {

		List<PersistentHttpServletRequestWrapper>
			persistentHttpServletRequestWrappers = new ArrayList<>();

		HttpServletRequest currentRequest = request;

		while (currentRequest instanceof HttpServletRequestWrapper) {
			if (currentRequest instanceof UploadServletRequest) {
				return (UploadServletRequest)currentRequest;
			}

			if (currentRequest instanceof
					PersistentHttpServletRequestWrapper) {

				PersistentHttpServletRequestWrapper
					persistentHttpServletRequestWrapper =
						(PersistentHttpServletRequestWrapper)currentRequest;

				persistentHttpServletRequestWrappers.add(
					persistentHttpServletRequestWrapper.clone());
			}

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)currentRequest;

			currentRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		if (ServerDetector.isWebLogic()) {
			currentRequest = new NonSerializableObjectRequestWrapper(
				currentRequest);
		}

		for (int i = persistentHttpServletRequestWrappers.size() - 1; i >= 0;
				i--) {

			HttpServletRequestWrapper httpServletRequestWrapper =
				persistentHttpServletRequestWrappers.get(i);

			httpServletRequestWrapper.setRequest(currentRequest);

			currentRequest = httpServletRequestWrapper;
		}

		return new UploadServletRequestImpl(currentRequest);
	}

	@Override
	public Date getUptime() {
		return _upTime;
	}

	@Override
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
			sb.append(JSESSIONID);
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

			if ((x + 1) == y) {
				sb.append(StringPool.SLASH);
			}
		}

		sb.append(JSESSIONID);
		sb.append(sessionId);

		return sb.toString();
	}

	@Override
	public User getUser(HttpServletRequest request) throws PortalException {
		User user = (User)request.getAttribute(WebKeys.USER);

		if (user != null) {
			return user;
		}

		long userId = getUserId(request);

		if (userId <= 0) {

			// Portlet WARs may have the correct remote user and not have the
			// correct user id because the user id is saved in the session and
			// may not be accessible by the portlet WAR's session. This behavior
			// is inconsistent across different application servers.

			String remoteUser = request.getRemoteUser();

			if (remoteUser == null) {
				return null;
			}

			userId = GetterUtil.getLong(remoteUser);
		}

		if (userId > 0) {
			user = UserLocalServiceUtil.getUserById(userId);

			request.setAttribute(WebKeys.USER, user);
		}

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();

				if (cookieName.startsWith(
						CookieKeys.REMOTE_PREFERENCE_PREFIX)) {

					user.addRemotePreference(
						new CookieRemotePreference(cookie));
				}
			}
		}

		return user;
	}

	@Override
	public User getUser(PortletRequest portletRequest) throws PortalException {
		return getUser(getHttpServletRequest(portletRequest));
	}

	@Override
	public String getUserEmailAddress(long userId) {
		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			return user.getEmailAddress();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public long getUserId(HttpServletRequest request) {
		Long userIdObj = (Long)request.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			return userIdObj.longValue();
		}

		String path = GetterUtil.getString(request.getPathInfo());
		String strutsAction = getStrutsAction(request);
		String actionName = getPortletParam(request, "actionName");

		boolean alwaysAllowDoAsUser = false;

		if (path.equals("/portal/session_click") ||
			strutsAction.equals("/document_library/edit_file_entry") ||
			strutsAction.equals("/document_library_display/edit_file_entry") ||
			strutsAction.equals("/image_gallery_display/edit_file_entry") ||
			strutsAction.equals("/image_gallery_display/edit_image") ||
			actionName.equals("addFile") ||
			isAlwaysAllowDoAsUser(path, strutsAction, actionName)) {

			try {
				alwaysAllowDoAsUser = isAlwaysAllowDoAsUser(request);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if ((!PropsValues.PORTAL_JAAS_ENABLE &&
			 PropsValues.PORTAL_IMPERSONATION_ENABLE) ||
			alwaysAllowDoAsUser) {

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

		userIdObj = (Long)session.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			request.setAttribute(WebKeys.USER_ID, userIdObj);

			return userIdObj.longValue();
		}
		else {
			return 0;
		}
	}

	@Override
	public long getUserId(PortletRequest portletRequest) {
		return getUserId(getHttpServletRequest(portletRequest));
	}

	@Override
	public String getUserName(BaseModel<?> baseModel) {
		long userId = 0;
		String userName = StringPool.BLANK;

		if (baseModel instanceof AuditedModel) {
			AuditedModel auditedModel = (AuditedModel)baseModel;

			userId = auditedModel.getUserId();
			userName = auditedModel.getUserName();
		}
		else {
			userId = BeanPropertiesUtil.getLongSilent(baseModel, "userId");
			userName = BeanPropertiesUtil.getStringSilent(
				baseModel, "userName");
		}

		if (userId == 0) {
			return StringPool.BLANK;
		}

		if (baseModel.isEscapedModel()) {
			userName = HtmlUtil.unescape(userName);
		}

		userName = getUserName(userId, userName);

		if (baseModel.isEscapedModel()) {
			userName = HtmlUtil.escape(userName);
		}

		return userName;
	}

	@Override
	public String getUserName(long userId, String defaultUserName) {
		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_FULL);
	}

	@Override
	public String getUserName(
		long userId, String defaultUserName, HttpServletRequest request) {

		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_FULL, request);
	}

	@Override
	public String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getUserName(userId, defaultUserName, userAttribute, null);
	}

	@Override
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

				portletURL.setParameter(
					"struts_action", "/directory/view_user");
				portletURL.setParameter(
					"p_u_i_d", String.valueOf(user.getUserId()));
				portletURL.setPortletMode(PortletMode.VIEW);
				portletURL.setWindowState(WindowState.MAXIMIZED);

				userName =
					"<a href=\"" + portletURL.toString() + "\">" +
						HtmlUtil.escape(userName) + "</a>";
			}
		}
		catch (Exception e) {
		}

		return userName;
	}

	@Override
	public String getUserPassword(HttpServletRequest request) {
		HttpSession session = request.getSession();

		return getUserPassword(session);
	}

	@Override
	public String getUserPassword(HttpSession session) {
		return (String)session.getAttribute(WebKeys.USER_PASSWORD);
	}

	@Override
	public String getUserPassword(PortletRequest portletRequest) {
		return getUserPassword(getHttpServletRequest(portletRequest));
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String getUserValue(long userId, String param, String defaultValue) {
		if (Validator.isNotNull(defaultValue)) {
			return defaultValue;
		}

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			return BeanPropertiesUtil.getString(user, param, defaultValue);
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public String getValidPortalDomain(long companyId, String domain) {
		if (_validPortalDomainCheckDisabled) {
			return domain;
		}

		for (String virtualHost : PropsValues.VIRTUAL_HOSTS_VALID_HOSTS) {
			if (StringUtil.equalsIgnoreCase(domain, virtualHost) ||
				StringUtil.wildcardMatches(
					domain, virtualHost, CharPool.QUESTION, CharPool.STAR,
					CharPool.PERCENT, false)) {

				return domain;
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Set the property \"" + PropsKeys.VIRTUAL_HOSTS_VALID_HOSTS +
					"\" in portal.properties to allow \"" + domain +
						"\" as a domain");
		}

		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				getDefaultCompanyId());

			return company.getVirtualHostname();
		}
		catch (Exception e) {
			_log.error("Unable to load default portal instance", e);
		}

		return _LOCALHOST;
	}

	@Override
	public long getValidUserId(long companyId, long userId)
		throws PortalException {

		User user = UserLocalServiceUtil.fetchUser(userId);

		if (user == null) {
			return UserLocalServiceUtil.getDefaultUserId(companyId);
		}

		if (user.getCompanyId() == companyId) {
			return user.getUserId();
		}

		return userId;
	}

	@Override
	public String getVirtualHostname(LayoutSet layoutSet) {
		String virtualHostname = layoutSet.getVirtualHostname();

		if (Validator.isNull(virtualHostname)) {
			virtualHostname = layoutSet.getCompanyFallbackVirtualHostname();
		}

		return virtualHostname;
	}

	@Override
	public String getVirtualLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		// Group friendly URL

		String groupFriendlyURL = null;

		int pos = friendlyURL.indexOf(CharPool.SLASH, 3);

		if (pos != -1) {
			groupFriendlyURL = friendlyURL.substring(2, pos);
		}

		if (Validator.isNull(groupFriendlyURL)) {
			return mainPath;
		}

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		long companyId = PortalInstances.getCompanyId(request);

		Group group = GroupLocalServiceUtil.fetchFriendlyURLGroup(
			companyId, groupFriendlyURL);

		if (group == null) {
			return mainPath;
		}

		// Layout friendly URL

		String layoutFriendlyURL = null;

		if ((pos != -1) && ((pos + 1) != friendlyURL.length())) {
			layoutFriendlyURL = friendlyURL.substring(pos);
		}

		if (Validator.isNull(layoutFriendlyURL)) {
			return mainPath;
		}

		String actualURL = getActualURL(
			group.getGroupId(), privateLayout, mainPath, layoutFriendlyURL,
			params, requestContext);

		return HttpUtil.addParameter(
			HttpUtil.removeParameter(actualURL, "p_v_l_s_g_id"), "p_v_l_s_g_id",
			groupId);
	}

	@Override
	public LayoutFriendlyURLComposite getVirtualLayoutFriendlyURLComposite(
			boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException {

		// Group friendly URL

		String groupFriendlyURL = null;

		int pos = friendlyURL.indexOf(CharPool.SLASH, 3);

		if (pos != -1) {
			groupFriendlyURL = friendlyURL.substring(2, pos);
		}

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		long companyId = PortalInstances.getCompanyId(request);

		Group group = GroupLocalServiceUtil.fetchFriendlyURLGroup(
			companyId, groupFriendlyURL);

		// Layout friendly URL

		String layoutFriendlyURL = null;

		if ((pos != -1) && ((pos + 1) != friendlyURL.length())) {
			layoutFriendlyURL = friendlyURL.substring(pos);
		}

		LayoutQueryStringComposite layoutQueryStringComposite =
			getActualLayoutQueryStringComposite(
				group.getGroupId(), privateLayout, layoutFriendlyURL, params,
				requestContext);

		return new LayoutFriendlyURLComposite(
			layoutQueryStringComposite.getLayout(), layoutFriendlyURL);
	}

	@Override
	public String getWidgetURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException {

		return getServletURL(
			portlet, PropsValues.WIDGET_SERVLET_MAPPING, themeDisplay);
	}

	@Override
	public void initCustomSQL() {
		_customSqlKeys = new String[] {
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.GROUP$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.LAYOUT$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.ORGANIZATION$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.ROLE$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.USER$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.USERGROUP$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.BLOGS.MODEL.BLOGSENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.CALENDAR.MODEL.CALEVENT$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.DOCUMENTLIBRARY.MODEL." +
				"DLFILEENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.DOCUMENTLIBRARY.MODEL." +
				"DLFOLDER$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.JOURNAL.MODEL." +
				"JOURNALFOLDER$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.MESSAGEBOARDS.MODEL." +
				"MBMESSAGE$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.MESSAGEBOARDS.MODEL." +
				"MBTHREAD$]",
			"[$RESOURCE_SCOPE_COMPANY$]", "[$RESOURCE_SCOPE_GROUP$]",
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
			"[$SOCIAL_RELATION_TYPE_UNI_SUPERVISOR$]", "[$FALSE$]", "[$TRUE$]"
		};

		DB db = DBFactoryUtil.getDB();

		Object[] customSqlValues = new Object[] {
			getClassNameId(Group.class), getClassNameId(Layout.class),
			getClassNameId(Organization.class), getClassNameId(Role.class),
			getClassNameId(User.class), getClassNameId(UserGroup.class),
			getClassNameId(BlogsEntry.class), getClassNameId(CalEvent.class),
			getClassNameId(DLFileEntry.class), getClassNameId(DLFolder.class),
			getClassNameId(JournalFolder.class),
			getClassNameId(MBMessage.class), getClassNameId(MBThread.class),
			ResourceConstants.SCOPE_COMPANY, ResourceConstants.SCOPE_GROUP,
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
			SocialRelationConstants.TYPE_UNI_SUPERVISOR, db.getTemplateFalse(),
			db.getTemplateTrue()
		};

		_customSqlValues = ArrayUtil.toStringArray(customSqlValues);
	}

	@Override
	public User initUser(HttpServletRequest request) throws Exception {
		User user = null;

		try {
			user = getUser(request);
		}
		catch (NoSuchUserException nsue) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsue.getMessage());
			}

			long userId = getUserId(request);

			if (userId > 0) {
				HttpSession session = request.getSession();

				session.invalidate();
			}

			throw nsue;
		}

		if (user != null) {
			return user;
		}

		Company company = getCompany(request);

		return company.getDefaultUser();
	}

	@Override
	public void invokeTaglibDiscussion(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		_editDiscussionAction.processAction(
			null, null, portletConfig, actionRequest, actionResponse);
	}

	@Override
	public void invokeTaglibDiscussionPagination(
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws IOException, PortletException {

		_editDiscussionAction.serveResource(
			null, null, portletConfig, resourceRequest, resourceResponse);
	}

	/**
	 * @deprecated As of 6.2.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isAllowAddPortletDefaultResource(
			HttpServletRequest request, Portlet portlet)
		throws PortalException {

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

		if (layout.isTypePanel() &&
			isPanelSelectedPortlet(themeDisplay, portletId)) {

			return true;
		}

		if (layout.isTypeControlPanel() &&
			isControlPanelPortlet(portletId, themeDisplay)) {

			return true;
		}

		if ((layoutTypePortlet != null) &&
			layoutTypePortlet.hasPortletId(portletId)) {

			return true;
		}

		if (themeDisplay.isSignedIn() &&
			portletId.equals(PortletKeys.LAYOUTS_ADMIN)) {

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

		Set<String> whiteList =
			AuthTokenWhitelistUtil.getPortletInvocationWhitelist();

		if (whiteList.contains(portletId)) {
			return true;
		}

		String namespace = getPortletNamespace(portletId);

		String strutsAction = ParamUtil.getString(
			request, namespace + "struts_action");

		if (Validator.isNull(strutsAction)) {
			strutsAction = ParamUtil.getString(request, "struts_action");
		}

		Set<String> whitelistActions =
			AuthTokenWhitelistUtil.getPortletInvocationWhitelistActions();

		if (whitelistActions.contains(strutsAction)) {
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

	@Override
	public boolean isCDNDynamicResourcesEnabled(HttpServletRequest request)
		throws PortalException {

		Company company = getCompany(request);

		return isCDNDynamicResourcesEnabled(company.getCompanyId());
	}

	@Override
	public boolean isCDNDynamicResourcesEnabled(long companyId) {
		try {
			return PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.CDN_DYNAMIC_RESOURCES_ENABLED,
				PropsValues.CDN_DYNAMIC_RESOURCES_ENABLED);
		}
		catch (SystemException se) {
		}

		return PropsValues.CDN_DYNAMIC_RESOURCES_ENABLED;
	}

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupAdmin(User, long)}
	 */
	@Deprecated
	@Override
	public boolean isCommunityAdmin(User user, long groupId) throws Exception {
		return isGroupAdmin(user, groupId);
	}

	/**
	 * @deprecated As of 6.1.0, renamed to {@link #isGroupOwner(User, long)}
	 */
	@Deprecated
	@Override
	public boolean isCommunityOwner(User user, long groupId) throws Exception {
		return isGroupOwner(user, groupId);
	}

	@Override
	public boolean isCompanyAdmin(User user) throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		return permissionChecker.isCompanyAdmin();
	}

	@Override
	public boolean isCompanyControlPanelPortlet(
			String portletId, String category, ThemeDisplay themeDisplay)
		throws PortalException {

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

	@Override
	public boolean isCompanyControlPanelPortlet(
			String portletId, ThemeDisplay themeDisplay)
		throws PortalException {

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

	@Override
	public boolean isCompanyControlPanelVisible(ThemeDisplay themeDisplay)
		throws PortalException {

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
				PortletCategoryKeys.SITE_ADMINISTRATION, themeDisplay);

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

	@Override
	public boolean isControlPanelPortlet(
		String portletId, String category, ThemeDisplay themeDisplay) {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletId);

		String controlPanelEntryCategory =
			portlet.getControlPanelEntryCategory();

		if (controlPanelEntryCategory.equals(category) ||
			(category.endsWith(StringPool.PERIOD) &&
			 StringUtil.startsWith(controlPanelEntryCategory, category))) {

			return isControlPanelPortlet(portletId, themeDisplay);
		}

		return false;
	}

	@Override
	public boolean isControlPanelPortlet(
		String portletId, ThemeDisplay themeDisplay) {

		try {
			return PortletPermissionUtil.hasControlPanelAccessPermission(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), portletId);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check control panel access permission", pe);
			}
		}

		return false;
	}

	@Override
	public boolean isGroupAdmin(User user, long groupId) throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		return permissionChecker.isGroupAdmin(groupId);
	}

	@Override
	public boolean isGroupFriendlyURL(
		String fullURL, String groupFriendlyURL, String layoutFriendlyURL) {

		if (fullURL.endsWith(groupFriendlyURL) &&
			!fullURL.endsWith(groupFriendlyURL.concat(layoutFriendlyURL))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isGroupOwner(User user, long groupId) throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		return permissionChecker.isGroupOwner(groupId);
	}

	@Override
	public boolean isLayoutDescendant(Layout layout, long layoutId)
		throws PortalException {

		if (layout.getLayoutId() == layoutId) {
			return true;
		}

		for (Layout childLayout : layout.getChildren()) {
			if (isLayoutDescendant(childLayout, layoutId)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isLayoutSitemapable(Layout layout) {
		if (layout.isPrivateLayout()) {
			return false;
		}

		LayoutType layoutType = layout.getLayoutType();

		return layoutType.isSitemapable();
	}

	@Override
	public boolean isLoginRedirectRequired(HttpServletRequest request) {
		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
			!request.isSecure()) {

			return true;
		}

		long companyId = PortalUtil.getCompanyId(request);

		if (SSOUtil.isLoginRedirectRequired(companyId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMethodGet(PortletRequest portletRequest) {
		HttpServletRequest request = getHttpServletRequest(portletRequest);

		String method = GetterUtil.getString(request.getMethod());

		if (StringUtil.equalsIgnoreCase(method, HttpMethods.GET)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isMethodPost(PortletRequest portletRequest) {
		HttpServletRequest request = getHttpServletRequest(portletRequest);

		String method = GetterUtil.getString(request.getMethod());

		if (StringUtil.equalsIgnoreCase(method, HttpMethods.POST)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
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

	@Override
	public boolean isOmniadmin(long userId) {
		return OmniadminUtil.isOmniadmin(userId);
	}

	@Override
	public boolean isOmniadmin(User user) {
		return OmniadminUtil.isOmniadmin(user);
	}

	@Override
	public boolean isReservedParameter(String name) {
		return _reservedParams.contains(name);
	}

	@Override
	public boolean isRightToLeft(HttpServletRequest request) {
		String languageId = LanguageUtil.getLanguageId(request);

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		String langDir = LanguageUtil.get(locale, "lang.dir");

		return langDir.equals("rtl");
	}

	@Override
	public boolean isRSSFeedsEnabled() {
		return PropsValues.RSS_FEEDS_ENABLED;
	}

	@Override
	public boolean isSecure(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session == null) {
			return request.isSecure();
		}

		Boolean httpsInitial = (Boolean)session.getAttribute(
			WebKeys.HTTPS_INITIAL);

		boolean secure = false;

		if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
			!PropsValues.SESSION_ENABLE_PHISHING_PROTECTION &&
			(httpsInitial != null) && !httpsInitial.booleanValue()) {

			secure = false;
		}
		else {
			secure = request.isSecure();
		}

		return secure;
	}

	@Override
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

	@Override
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

		pos = Arrays.binarySearch(
			_sortedSystemSiteRoles, roleName, new StringComparator());

		if (pos >= 0) {
			return true;
		}

		pos = Arrays.binarySearch(
			_sortedSystemOrganizationRoles, roleName, new StringComparator());

		if (pos >= 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isUpdateAvailable() {
		return PluginPackageUtil.isUpdateAvailable();
	}

	@Override
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

	@Override
	public boolean removePortalInetSocketAddressEventListener(
		PortalInetSocketAddressEventListener
			portalInetSocketAddressEventListener) {

		return _portalInetSocketAddressEventListeners.remove(
			portalInetSocketAddressEventListener);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #removePortalInetSocketAddressEventListener(
	 *             PortalInetSocketAddressEventListener)}
	 */
	@Deprecated
	@Override
	public void removePortalPortEventListener(
		PortalPortEventListener portalPortEventListener) {

		_portalPortEventListeners.remove(portalPortEventListener);
	}

	@Override
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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             AuthTokenWhitelistUtil#resetPortletInvocationWhitelist}
	 */
	@Deprecated
	@Override
	public Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		return AuthTokenWhitelistUtil.resetPortletInvocationWhitelist();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             AuthTokenWhitelistUtil#resetPortletInvocationWhitelistActions}
	 */
	@Deprecated
	@Override
	public Set<String> resetPortletAddDefaultResourceCheckWhitelistActions() {
		return AuthTokenWhitelistUtil.resetPortletInvocationWhitelistActions();
	}

	@Override
	public String resetPortletParameters(String url, String portletId) {
		if (Validator.isNull(url) || Validator.isNull(portletId)) {
			return url;
		}

		String portletNamespace = getPortletNamespace(portletId);

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(url);

		for (String name : parameterMap.keySet()) {
			if (name.startsWith(portletNamespace)) {
				url = HttpUtil.removeParameter(url, name);
			}
		}

		return url;
	}

	@Override
	public void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		sendError(0, e, actionRequest, actionResponse);
	}

	@Override
	public void sendError(
			Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		sendError(0, e, request, response);
	}

	@Override
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

	@Override
	public void sendError(
			int status, Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			String currentURL = (String)request.getAttribute(
				WebKeys.CURRENT_URL);

			_log.debug(
				"Current URL " + currentURL + " generates exception: " +
					e.getMessage());
		}

		if (e instanceof NoSuchImageException) {
			if (_logWebServerServlet.isWarnEnabled()) {
				_logWebServerServlet.warn(e, e);
			}
		}
		else if ((e instanceof PortalException) && _log.isDebugEnabled()) {
			if ((e instanceof NoSuchLayoutException) ||
				(e instanceof PrincipalException)) {

				String msg = e.getMessage();

				if (Validator.isNotNull(msg)) {
					_log.debug(msg);
				}
			}
			else {
				_log.debug(e, e);
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
				status = HttpServletResponse.SC_BAD_REQUEST;
			}
		}

		String redirect = null;

		if ((e instanceof NoSuchGroupException) &&
			Validator.isNotNull(
				PropsValues.SITES_FRIENDLY_URL_PAGE_NOT_FOUND)) {

			redirect = PropsValues.SITES_FRIENDLY_URL_PAGE_NOT_FOUND;
		}
		else if ((e instanceof NoSuchLayoutException) &&
				 Validator.isNotNull(
					 PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND)) {

			redirect = PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND;
		}
		else if (PropsValues.LAYOUT_SHOW_HTTP_STATUS) {
			redirect = PATH_MAIN + "/portal/status";
		}

		if (Validator.equals(redirect, request.getRequestURI())) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to redirect to missing URI: " + redirect);
			}

			redirect = null;
		}

		if (Validator.isNotNull(redirect)) {
			HttpSession session = PortalSessionThreadLocal.getHttpSession();

			if (session == null) {
				session = request.getSession();
			}

			response.setStatus(status);

			SessionErrors.add(session, e.getClass(), e);

			ServletContext servletContext = session.getServletContext();

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else if (e != null) {
			response.sendError(status, e.getMessage());
		}
		else {
			String currentURL = (String)request.getAttribute(
				WebKeys.CURRENT_URL);

			response.sendError(status, "Current URL " + currentURL);
		}
	}

	@Override
	public void sendRSSFeedsDisabledError(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		sendError(
			HttpServletResponse.SC_NOT_FOUND, new NoSuchFeedException(),
			request, response);
	}

	@Override
	public void sendRSSFeedsDisabledError(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException, ServletException {

		HttpServletRequest request = getHttpServletRequest(portletRequest);
		HttpServletResponse response = getHttpServletResponse(portletResponse);

		sendRSSFeedsDisabledError(request, response);
	}

	@Override
	public void setPageDescription(
		String description, HttpServletRequest request) {

		ListMergeable<String> descriptionListMergeable = new ListMergeable<>();

		descriptionListMergeable.add(description);

		request.setAttribute(
			WebKeys.PAGE_DESCRIPTION, descriptionListMergeable);
	}

	@Override
	public void setPageKeywords(String keywords, HttpServletRequest request) {
		request.removeAttribute(WebKeys.PAGE_KEYWORDS);

		addPageKeywords(keywords, request);
	}

	@Override
	public void setPageSubtitle(String subtitle, HttpServletRequest request) {
		ListMergeable<String> subtitleListMergeable = new ListMergeable<>();

		subtitleListMergeable.add(subtitle);

		request.setAttribute(WebKeys.PAGE_SUBTITLE, subtitleListMergeable);
	}

	@Override
	public void setPageTitle(String title, HttpServletRequest request) {
		ListMergeable<String> titleListMergeable = new ListMergeable<>();

		titleListMergeable.add(title);

		request.setAttribute(WebKeys.PAGE_TITLE, titleListMergeable);
	}

	@Override
	public void setPortalInetSocketAddresses(HttpServletRequest request) {
		boolean secure = request.isSecure();

		if ((secure && (_securePortalLocalInetSocketAddress.get() != null) &&
			 (_securePortalServerInetSocketAddress.get() != null)) ||
			(!secure && (_portalLocalInetSocketAddress.get() != null) &&
			 (_portalServerInetSocketAddress.get() != null))) {

			return;
		}

		InetAddress localInetAddress = null;
		InetAddress serverInetAddress = null;

		try {
			localInetAddress = InetAddress.getByName(request.getLocalAddr());
			serverInetAddress = InetAddress.getByName(request.getServerName());
		}
		catch (UnknownHostException uhe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to resolve portal host", uhe);
			}

			return;
		}

		InetSocketAddress localInetSocketAddress = new InetSocketAddress(
			localInetAddress, request.getLocalPort());
		InetSocketAddress serverInetSocketAddress = new InetSocketAddress(
			serverInetAddress, request.getServerPort());

		if (secure) {
			if (_securePortalLocalInetSocketAddress.compareAndSet(
					null, localInetSocketAddress)) {

				notifyPortalInetSocketAddressEventListeners(
					localInetSocketAddress, true, true);
			}

			if (_securePortalServerInetSocketAddress.compareAndSet(
					null, serverInetSocketAddress)) {

				notifyPortalInetSocketAddressEventListeners(
					serverInetSocketAddress, false, true);
			}
		}
		else {
			if (_portalLocalInetSocketAddress.compareAndSet(
					null, localInetSocketAddress)) {

				notifyPortalInetSocketAddressEventListeners(
					localInetSocketAddress, true, false);
			}

			if (_portalServerInetSocketAddress.compareAndSet(
					null, serverInetSocketAddress)) {

				notifyPortalInetSocketAddressEventListeners(
					serverInetSocketAddress, false, false);
			}
		}
	}

	/**
	 * Sets the port obtained on the first request to the portal.
	 *
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #setPortalInetSocketAddresses(HttpServletRequest)}
	 */
	@Deprecated
	@Override
	public void setPortalPort(HttpServletRequest request) {
		if (request.isSecure()) {
			if (_securePortalPort.get() == -1) {
				int securePortalPort = request.getServerPort();

				if (_securePortalPort.compareAndSet(-1, securePortalPort) &&
					StringUtil.equalsIgnoreCase(
						Http.HTTPS, PropsValues.WEB_SERVER_PROTOCOL)) {

					notifyPortalPortEventListeners(securePortalPort);
				}
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

	@Override
	public void storePreferences(PortletPreferences portletPreferences)
		throws IOException, ValidatorException {

		PortletPreferencesWrapper portletPreferencesWrapper =
			(PortletPreferencesWrapper)portletPreferences;

		PortletPreferencesImpl portletPreferencesImpl =
			portletPreferencesWrapper.getPortletPreferencesImpl();

		portletPreferencesImpl.store();
	}

	@Override
	public String[] stripURLAnchor(String url, String separator) {
		String anchor = StringPool.BLANK;

		int pos = url.indexOf(separator);

		if (pos != -1) {
			anchor = url.substring(pos);
			url = url.substring(0, pos);
		}

		return new String[] {url, anchor};
	}

	@Override
	public String transformCustomSQL(String sql) {
		if ((_customSqlKeys == null) || (_customSqlValues == null)) {
			initCustomSQL();
		}

		return StringUtil.replace(sql, _customSqlKeys, _customSqlValues);
	}

	@Override
	public String transformSQL(String sql) {
		return SQLTransformer.transform(sql);
	}

	@Override
	public void updateImageId(
			BaseModel<?> baseModel, boolean image, byte[] bytes,
			String fieldName, long maxSize, int maxHeight, int maxWidth)
		throws PortalException {

		long imageId = BeanPropertiesUtil.getLong(baseModel, fieldName);

		if (!image) {
			if (imageId > 0) {
				ImageLocalServiceUtil.deleteImage(imageId);

				BeanPropertiesUtil.setProperty(baseModel, fieldName, 0);
			}

			return;
		}

		if (ArrayUtil.isEmpty(bytes)) {
			return;
		}

		if ((maxSize > 0) && ((bytes == null) || (bytes.length > maxSize))) {
			throw new ImageSizeException();
		}

		if (imageId <= 0) {
			imageId = CounterLocalServiceUtil.increment();

			BeanPropertiesUtil.setProperty(baseModel, fieldName, imageId);
		}

		if ((maxHeight > 0) || (maxWidth > 0)) {
			try {
				ImageBag imageBag = ImageToolUtil.read(bytes);

				RenderedImage renderedImage = imageBag.getRenderedImage();

				if (renderedImage == null) {
					throw new ImageTypeException();
				}

				renderedImage = ImageToolUtil.scale(
					renderedImage, maxHeight, maxWidth);

				bytes = ImageToolUtil.getBytes(
					renderedImage, imageBag.getType());
			}
			catch (IOException ioe) {
				throw new ImageSizeException(ioe);
			}
		}

		ImageLocalServiceUtil.updateImage(imageId, bytes);
	}

	@Override
	public PortletMode updatePortletMode(
		String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request) {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if ((portletMode == null) || Validator.isNull(portletMode.toString())) {
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

	@Override
	public String updateRedirect(
		String redirect, String oldPath, String newPath) {

		if (Validator.isNull(redirect) || (oldPath == null) ||
			oldPath.equals(newPath)) {

			return redirect;
		}

		String queryString = HttpUtil.getQueryString(redirect);

		String redirectParam = HttpUtil.getParameter(
			redirect, "redirect", false);

		if (Validator.isNotNull(redirectParam)) {
			String newRedirectParam = StringUtil.replace(
				redirectParam, HttpUtil.encodeURL(oldPath),
				HttpUtil.encodeURL(newPath));

			queryString = StringUtil.replace(
				queryString, redirectParam, newRedirectParam);
		}

		String redirectPath = HttpUtil.getPath(redirect);

		int pos = redirect.indexOf(redirectPath);

		String prefix = redirect.substring(0, pos);

		pos = redirectPath.lastIndexOf(oldPath);

		if (pos != -1) {
			prefix += redirectPath.substring(0, pos);

			String suffix = redirectPath.substring(pos + oldPath.length());

			redirect = prefix + newPath + suffix;
		}
		else {
			redirect = prefix + redirectPath;
		}

		if (Validator.isNotNull(queryString)) {
			redirect += StringPool.QUESTION + queryString;
		}

		return redirect;
	}

	@Override
	public WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request) {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if ((windowState == null) || Validator.isNull(windowState.toString())) {
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
			long companyId, Layout layout, Portlet portlet,
			boolean portletActions)
		throws PortalException {

		long groupId = getScopeGroupId(layout, portlet.getPortletId());

		addDefaultResource(companyId, groupId, layout, portlet, portletActions);
	}

	protected void addDefaultResource(
			long companyId, long groupId, Layout layout, Portlet portlet,
			boolean portletActions)
		throws PortalException {

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
			Group group = GroupLocalServiceUtil.fetchGroup(groupId);

			if ((group != null) && group.isStagingGroup()) {
				groupId = group.getLiveGroupId();
			}

			name = ResourceActionsUtil.getPortletBaseResource(rootPortletId);
			primaryKey = String.valueOf(groupId);
		}

		if (Validator.isNull(name)) {
			return;
		}

		int count =
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				companyId, name, ResourceConstants.SCOPE_INDIVIDUAL,
				primaryKey);

		if (count > 0) {
			return;
		}

		boolean addGuestPermissions = true;

		if (portletActions) {
			Group layoutGroup = layout.getGroup();

			if (layout.isPrivateLayout() && !layoutGroup.isLayoutPrototype() &&
				!layoutGroup.isLayoutSetPrototype()) {

				addGuestPermissions = false;
			}
		}

		ResourceLocalServiceUtil.addResources(
			companyId, groupId, 0, name, primaryKey, portletActions, true,
			addGuestPermissions);
	}

	protected String buildI18NPath(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		if (Validator.isNull(languageId)) {
			return null;
		}

		if (LanguageUtil.isDuplicateLanguageCode(locale.getLanguage())) {
			Locale priorityLocale = LanguageUtil.getLocale(
				locale.getLanguage());

			if (locale.equals(priorityLocale)) {
				languageId = locale.getLanguage();
			}
		}
		else {
			languageId = locale.getLanguage();
		}

		return StringPool.SLASH.concat(languageId);
	}

	protected Set<Group> doGetAncestorSiteGroups(
			long groupId, boolean checkContentSharingWithChildrenEnabled)
		throws PortalException {

		Set<Group> groups = new LinkedHashSet<>();

		long siteGroupId = getSiteGroupId(groupId);

		Group siteGroup = GroupLocalServiceUtil.fetchGroup(siteGroupId);

		if (siteGroup == null) {
			return groups;
		}

		for (Group group : siteGroup.getAncestors()) {
			if (checkContentSharingWithChildrenEnabled &&
				!SitesUtil.isContentSharingWithChildrenEnabled(group)) {

				continue;
			}

			groups.add(group);
		}

		if (!siteGroup.isCompany()) {
			groups.add(
				GroupLocalServiceUtil.getCompanyGroup(
					siteGroup.getCompanyId()));
		}

		return groups;
	}

	protected Group doGetCurrentSiteGroup(long groupId) throws PortalException {
		long siteGroupId = getSiteGroupId(groupId);

		Group siteGroup = GroupLocalServiceUtil.getGroup(siteGroupId);

		if (!siteGroup.isLayoutPrototype()) {
			return siteGroup;
		}

		return null;
	}

	protected long doGetPlidFromPortletId(
			long groupId, boolean privateLayout, String portletId)
		throws PortalException {

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

			if (layoutTypePortlet.hasPortletId(portletId, true)) {
				if (getScopeGroupId(layout, portletId) == scopeGroupId) {
					plid = layout.getPlid();

					break;
				}
			}
		}

		return plid;
	}

	protected List<Portlet> filterControlPanelPortlets(
		Set<Portlet> portlets, ThemeDisplay themeDisplay) {

		List<Portlet> filteredPortlets = new ArrayList<>(portlets);

		Iterator<Portlet> itr = filteredPortlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			try {
				if (!portlet.isActive() || portlet.isInstanceable() ||
					!PortletPermissionUtil.hasControlPanelAccessPermission(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getScopeGroupId(), portlet)) {

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

	protected Locale getAvailableLocale(long groupId, Locale locale) {
		if (Validator.isNull(locale.getCountry())) {

			// Locales must contain a country code

			locale = LanguageUtil.getLocale(locale.getLanguage());
		}

		if (!LanguageUtil.isAvailableLocale(groupId, locale)) {
			return null;
		}

		return locale;
	}

	protected String getCanonicalDomain(
		String virtualHostname, String portalDomain) {

		if (Validator.isBlank(portalDomain) ||
			StringUtil.equalsIgnoreCase(portalDomain, _LOCALHOST) ||
			!StringUtil.equalsIgnoreCase(virtualHostname, _LOCALHOST)) {

			return virtualHostname;
		}

		int pos = portalDomain.indexOf(CharPool.COLON);

		if (pos == -1) {
			return portalDomain;
		}

		return portalDomain.substring(0, pos);
	}

	protected Map<String, List<Portlet>> getCategoriesMap(
		HttpServletRequest request, String attributeName, String[] categories) {

		Map<String, List<Portlet>> categoriesMap =
			(Map<String, List<Portlet>>)request.getAttribute(attributeName);

		if (categoriesMap != null) {
			return categoriesMap;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		categoriesMap = new LinkedHashMap<>();

		for (String category : categories) {
			Group group = themeDisplay.getSiteGroup();

			if (group.isInheritContent() &&
				category.equals(
					PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT)) {

				continue;
			}

			List<Portlet> portlets = getControlPanelPortlets(
				category, themeDisplay);

			if (!portlets.isEmpty()) {
				categoriesMap.put(category, portlets);
			}
		}

		request.setAttribute(attributeName, categoriesMap);

		return categoriesMap;
	}

	protected long getDefaultScopeGroupId(long companyId)
		throws PortalException {

		long doAsGroupId = 0;

		Collection<Portlet> portlets = getControlPanelPortlets(
			companyId, PortletCategoryKeys.SITE_ADMINISTRATION);

		List<Group> groups = GroupServiceUtil.getManageableSiteGroups(
			portlets, 1);

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
						" because the string cannot be decrypted");
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

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(realUser);

		if (doAsUser.isDefaultUser() ||
			UserPermissionUtil.contains(
				permissionChecker, doAsUserId, organizationIds,
				ActionKeys.IMPERSONATE)) {

			request.setAttribute(WebKeys.USER_ID, new Long(doAsUserId));

			return doAsUserId;
		}

		_log.error(
			"User " + realUserIdObj + " does not have the permission to " +
				"impersonate " + doAsUserId);

		return 0;
	}

	protected String getGroupFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay,
			boolean canonicalURL)
		throws PortalException {

		Group group = layoutSet.getGroup();

		boolean privateLayoutSet = layoutSet.getPrivateLayout();

		String portalURL = themeDisplay.getPortalURL();

		boolean useGroupVirtualHostName = false;

		if (canonicalURL ||
			!StringUtil.equalsIgnoreCase(
				themeDisplay.getServerName(), _LOCALHOST)) {

			useGroupVirtualHostName = true;
		}

		long refererPlid = themeDisplay.getRefererPlid();

		if (refererPlid > 0) {
			Layout refererLayout = LayoutLocalServiceUtil.fetchLayout(
				refererPlid);

			if ((refererLayout != null) &&
				((refererLayout.getGroupId() != group.getGroupId()) ||
				 (refererLayout.isPrivateLayout() != privateLayoutSet))) {

				useGroupVirtualHostName = false;
			}
		}

		if (useGroupVirtualHostName) {
			String virtualHostname = getVirtualHostname(layoutSet);

			String portalDomain = HttpUtil.getDomain(portalURL);

			if (Validator.isNotNull(virtualHostname) &&
				(canonicalURL ||
				 !StringUtil.equalsIgnoreCase(virtualHostname, _LOCALHOST))) {

				virtualHostname = getCanonicalDomain(
					virtualHostname, portalDomain);

				virtualHostname = getPortalURL(
					virtualHostname, themeDisplay.getServerPort(),
					themeDisplay.isSecure());

				if (canonicalURL || virtualHostname.contains(portalDomain)) {
					String path = StringPool.BLANK;

					if (themeDisplay.isWidget()) {
						path = PropsValues.WIDGET_SERVLET_MAPPING;
					}

					if (themeDisplay.isI18n() && !canonicalURL) {
						path = themeDisplay.getI18nPath();
					}

					return virtualHostname.concat(_pathContext).concat(path);
				}
			}
			else {
				LayoutSet curLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					themeDisplay.getSiteGroupId(), privateLayoutSet);

				if (canonicalURL ||
					((layoutSet.getLayoutSetId() !=
						curLayoutSet.getLayoutSetId()) &&
					 (group.getClassPK() != themeDisplay.getUserId()))) {

					if (group.isControlPanel() || group.isUserPersonalPanel()) {
						virtualHostname = themeDisplay.getServerName();

						if (Validator.isNull(virtualHostname) ||
							StringUtil.equalsIgnoreCase(
								virtualHostname, _LOCALHOST)) {

							virtualHostname = curLayoutSet.getVirtualHostname();
						}
					}

					if (Validator.isNull(virtualHostname) ||
						StringUtil.equalsIgnoreCase(
							virtualHostname, _LOCALHOST)) {

						Company company = themeDisplay.getCompany();

						virtualHostname = company.getVirtualHostname();
					}

					if (canonicalURL ||
						!StringUtil.equalsIgnoreCase(
							virtualHostname, _LOCALHOST)) {

						virtualHostname = getCanonicalDomain(
							virtualHostname, portalDomain);

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

		if (themeDisplay.isI18n() && !canonicalURL) {
			sb.append(themeDisplay.getI18nPath());
		}

		if (themeDisplay.isWidget()) {
			sb.append(PropsValues.WIDGET_SERVLET_MAPPING);
		}

		sb.append(friendlyURL);
		sb.append(group.getFriendlyURL());

		return sb.toString();
	}

	protected String[] getGroupPermissions(
		String[] groupPermissions, String className,
		String inputPermissionsShowOptions) {

		if ((groupPermissions != null) ||
			(inputPermissionsShowOptions != null)) {

			return groupPermissions;
		}

		List<String> groupDefaultActions =
			ResourceActionsUtil.getModelResourceGroupDefaultActions(className);

		return groupDefaultActions.toArray(
			new String[groupDefaultActions.size()]);
	}

	protected String[] getGuestPermissions(
		String[] guestPermissions, String className,
		String inputPermissionsShowOptions) {

		if ((guestPermissions != null) ||
			(inputPermissionsShowOptions != null)) {

			return guestPermissions;
		}

		List<String> guestDefaultActions =
			ResourceActionsUtil.getModelResourceGuestDefaultActions(className);

		return guestDefaultActions.toArray(
			new String[guestDefaultActions.size()]);
	}

	protected String getPortletParam(HttpServletRequest request, String name) {
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

			if (ArrayUtil.isEmpty(parameterValues) ||
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

	protected String getServletURL(
			Portlet portlet, String servletPath, ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = themeDisplay.getLayout();

		StringBundler sb = new StringBundler(9);

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
		sb.append(layout.getFriendlyURL(themeDisplay.getLocale()));

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

	protected boolean isAlwaysAllowDoAsUser(HttpServletRequest request)
		throws Exception {

		String ticketKey = ParamUtil.getString(request, "ticketKey");

		if (Validator.isNull(ticketKey)) {
			return false;
		}

		Ticket ticket = TicketLocalServiceUtil.fetchTicket(ticketKey);

		if ((ticket == null) ||
			(ticket.getType() != TicketConstants.TYPE_IMPERSONATE)) {

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

		if (ticket.getClassPK() != doAsUserId) {
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

		TicketLocalServiceUtil.updateTicket(ticket);

		return true;
	}

	protected boolean isAlwaysAllowDoAsUser(
		String path, String strutsAction, String actionName) {

		for (AlwaysAllowDoAsUser alwaysAllowDoAsUser : _alwaysAllowDoAsUsers) {
			Collection<String> paths = alwaysAllowDoAsUser.getPaths();

			if (paths.contains(path)) {
				return true;
			}

			Collection<String> strutsActions =
				alwaysAllowDoAsUser.getStrutsActions();

			if (strutsActions.contains(strutsAction)) {
				return true;
			}

			Collection<String> actionNames =
				alwaysAllowDoAsUser.getActionNames();

			if (actionNames.contains(actionName)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated As of 6.2.0, with no direct replacement
	 */
	@Deprecated
	protected boolean isPanelSelectedPortlet(
		ThemeDisplay themeDisplay, String portletId) {

		Layout layout = themeDisplay.getLayout();

		String panelSelectedPortlets = layout.getTypeSettingsProperty(
			"panelSelectedPortlets");

		if (Validator.isNotNull(panelSelectedPortlets)) {
			String[] panelSelectedPortletsArray = StringUtil.split(
				panelSelectedPortlets);

			return ArrayUtil.contains(panelSelectedPortletsArray, portletId);
		}

		return false;
	}

	protected boolean isValidPortalDomain(long companyId, String domain) {
		if (_validPortalDomainCheckDisabled) {
			return true;
		}

		if (!Validator.isHostName(domain)) {
			return false;
		}

		for (String virtualHost : PropsValues.VIRTUAL_HOSTS_VALID_HOSTS) {
			if (StringUtil.equalsIgnoreCase(domain, virtualHost) ||
				StringUtil.wildcardMatches(
					domain, virtualHost, CharPool.QUESTION, CharPool.STAR,
					CharPool.PERCENT, false)) {

				return true;
			}
		}

		if (StringUtil.equalsIgnoreCase(domain, PropsValues.WEB_SERVER_HOST)) {
			return true;
		}

		if (isValidVirtualHostname(domain)) {
			return true;
		}

		if (StringUtil.equalsIgnoreCase(domain, getCDNHostHttp(companyId))) {
			return true;
		}

		if (StringUtil.equalsIgnoreCase(domain, getCDNHostHttps(companyId))) {
			return true;
		}

		return false;
	}

	protected boolean isValidPortalDomain(String domain) {
		long companyId = CompanyThreadLocal.getCompanyId();

		return isValidPortalDomain(companyId, domain);
	}

	protected boolean isValidVirtualHostname(String virtualHostname) {
		try {
			virtualHostname = StringUtil.toLowerCase(virtualHostname.trim());

			VirtualHost virtualHost =
				VirtualHostLocalServiceUtil.fetchVirtualHost(virtualHostname);

			if (virtualHost != null) {
				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	protected void notifyPortalInetSocketAddressEventListeners(
		InetSocketAddress inetSocketAddress, boolean local, boolean secure) {

		for (PortalInetSocketAddressEventListener
				portalInetSocketAddressEventListener :
					_portalInetSocketAddressEventListeners) {

			if (local) {
				portalInetSocketAddressEventListener.
					portalLocalInetSocketAddressConfigured(
						inetSocketAddress, secure);
			}
			else {
				portalInetSocketAddressEventListener.
					portalServerInetSocketAddressConfigured(
						inetSocketAddress, secure);
			}
		}
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #notifyPortalInetSocketAddressEventListeners(
	 *             InetSocketAddress, boolean, boolean)}
	 */
	@Deprecated
	protected void notifyPortalPortEventListeners(int portalPort) {
		for (PortalPortEventListener portalPortEventListener :
				_portalPortEventListeners) {

			portalPortEventListener.portalPortConfigured(portalPort);
		}
	}

	protected String removeRedirectParameter(String url) {
		String queryString = HttpUtil.getQueryString(url);

		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			queryString);

		for (String parameter : parameterMap.keySet()) {
			if (parameter.endsWith("redirect")) {
				url = HttpUtil.removeParameter(url, parameter);
			}
		}

		return url;
	}

	protected void resetThemeDisplayI18n(
		ThemeDisplay themeDisplay, String languageId, String path,
		Locale locale) {

		themeDisplay.setI18nLanguageId(languageId);
		themeDisplay.setI18nPath(path);
		themeDisplay.setLocale(locale);
	}

	protected void setLocale(
		HttpServletRequest request, HttpServletResponse response,
		Locale locale) {

		HttpSession session = request.getSession();

		session.setAttribute(Globals.LOCALE_KEY, locale);

		LanguageUtil.updateCookie(request, response, locale);
	}

	protected void setThemeDisplayI18n(
		ThemeDisplay themeDisplay, Locale locale) {

		String i18nLanguageId = null;
		String i18nPath = null;

		if ((I18nFilter.getLanguageIds().contains(locale.toString()) &&
			 (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 1) &&
			 !locale.equals(LocaleUtil.getDefault())) ||
			(PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2)) {

			i18nLanguageId = locale.toString();
			i18nPath = buildI18NPath(locale);
		}

		themeDisplay.setI18nLanguageId(i18nLanguageId);
		themeDisplay.setI18nPath(i18nPath);
		themeDisplay.setLocale(locale);
	}

	private static final Log _logWebServerServlet = LogFactoryUtil.getLog(
		WebServerServlet.class);

	private static final String _J_SECURITY_CHECK = "j_security_check";

	private static final String _LOCALHOST = "localhost";

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static final Log _log = LogFactoryUtil.getLog(PortalImpl.class);

	private static final Map<Long, String> _cdnHostHttpMap =
		new ConcurrentHashMap<>();
	private static final Map<Long, String> _cdnHostHttpsMap =
		new ConcurrentHashMap<>();
	private static final MethodHandler _resetCDNHostsMethodHandler =
		new MethodHandler(new MethodKey(PortalUtil.class, "resetCDNHosts"));
	private static final Date _upTime = new Date();

	private final String[] _allSystemGroups;
	private final String[] _allSystemOrganizationRoles;
	private final String[] _allSystemRoles;
	private final String[] _allSystemSiteRoles;
	private final List<AlwaysAllowDoAsUser> _alwaysAllowDoAsUsers =
		new ArrayList<>();
	private final Pattern _bannedResourceIdPattern = Pattern.compile(
		PropsValues.PORTLET_RESOURCE_ID_BANNED_PATHS_REGEXP,
		Pattern.CASE_INSENSITIVE);
	private final String _computerAddress;
	private final String _computerName;
	private String[] _customSqlKeys;
	private String[] _customSqlValues;
	private final EditDiscussionAction _editDiscussionAction =
		new EditDiscussionAction();
	private final String _pathContext;
	private final String _pathFriendlyURLPrivateGroup;
	private final String _pathFriendlyURLPrivateUser;
	private final String _pathFriendlyURLPublic;
	private final String _pathImage;
	private final String _pathMain;
	private final String _pathModule;
	private final String _pathProxy;
	private final Map<String, Long> _plidToPortletIdMap =
		new ConcurrentHashMap<>();
	private final Set<PortalInetSocketAddressEventListener>
		_portalInetSocketAddressEventListeners = new CopyOnWriteArraySet<>();
	private final AtomicReference<InetSocketAddress>
		_portalLocalInetSocketAddress = new AtomicReference<>();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #_portalServerInetSocketAddress}
	 */
	@Deprecated
	private final AtomicInteger _portalPort = new AtomicInteger(-1);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #_portalInetSocketAddressEventListeners}
	 */
	@Deprecated
	private final List<PortalPortEventListener> _portalPortEventListeners =
		new ArrayList<>();

	private final AtomicReference<InetSocketAddress>
		_portalServerInetSocketAddress = new AtomicReference<>();
	private final Set<String> _reservedParams;
	private final AtomicReference<InetSocketAddress>
		_securePortalLocalInetSocketAddress = new AtomicReference<>();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #_securePortalServerInetSocketAddress}
	 */
	@Deprecated
	private final AtomicInteger _securePortalPort = new AtomicInteger(-1);

	private final AtomicReference<InetSocketAddress>
		_securePortalServerInetSocketAddress = new AtomicReference<>();
	private final String _servletContextName;
	private final String[] _sortedSystemGroups;
	private final String[] _sortedSystemOrganizationRoles;
	private final String[] _sortedSystemRoles;
	private final String[] _sortedSystemSiteRoles;
	private final boolean _validPortalDomainCheckDisabled;

	private class AlwaysAllowDoAsUserServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<AlwaysAllowDoAsUser, AlwaysAllowDoAsUser> {

		@Override
		public AlwaysAllowDoAsUser addingService(
			ServiceReference<AlwaysAllowDoAsUser> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			AlwaysAllowDoAsUser alwaysAllowDoAsUser = registry.getService(
				serviceReference);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Add alway sallow do as user " +
						ClassUtil.getClassName(alwaysAllowDoAsUser));
			}

			_alwaysAllowDoAsUsers.add(alwaysAllowDoAsUser);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"There are " + _alwaysAllowDoAsUsers.size() +
						" alway sallow do as user instances");
			}

			return alwaysAllowDoAsUser;
		}

		@Override
		public void modifiedService(
			ServiceReference<AlwaysAllowDoAsUser> serviceReference,
			AlwaysAllowDoAsUser alwaysAllowDoAsUser) {
		}

		@Override
		public void removedService(
			ServiceReference<AlwaysAllowDoAsUser> serviceReference,
			AlwaysAllowDoAsUser alwaysAllowDoAsUser) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Delete alway sallow do as user " +
						ClassUtil.getClassName(alwaysAllowDoAsUser));
			}

			_alwaysAllowDoAsUsers.remove(alwaysAllowDoAsUser);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"There are " + _alwaysAllowDoAsUsers.size() +
						" alway sallow do as user instances");
			}
		}

	}

	private class PortalInetSocketAddressEventListenerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PortalInetSocketAddressEventListener,
				PortalInetSocketAddressEventListener> {

		@Override
		public PortalInetSocketAddressEventListener addingService(
			ServiceReference<PortalInetSocketAddressEventListener>
				serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			PortalInetSocketAddressEventListener
				portalInetSocketAddressEventListener = registry.getService(
					serviceReference);

			addPortalInetSocketAddressEventListener(
				portalInetSocketAddressEventListener);

			return portalInetSocketAddressEventListener;
		}

		@Override
		public void modifiedService(
			ServiceReference<PortalInetSocketAddressEventListener>
				serviceReference,
			PortalInetSocketAddressEventListener
				portalInetSocketAddressEventListener) {
		}

		@Override
		public void removedService(
			ServiceReference<PortalInetSocketAddressEventListener>
				serviceReference,
			PortalInetSocketAddressEventListener
				portalInetSocketAddressEventListener) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			removePortalInetSocketAddressEventListener(
				portalInetSocketAddressEventListener);
		}

	}

}
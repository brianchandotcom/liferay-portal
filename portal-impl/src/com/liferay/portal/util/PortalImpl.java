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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.exception.ImageSizeException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.expando.kernel.exception.ValueDataException;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.petra.encryptor.Encryptor;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ImageTypeException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchImageException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RSSFeedException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.constants.LanguageConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.LayoutQueryStringComposite;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.model.VirtualLayoutConstants;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperThreadLocal;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LayoutFriendlyURLSeparatorComposite;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayRenderRequest;
import com.liferay.portal.kernel.portlet.LiferayRenderResponse;
import com.liferay.portal.kernel.portlet.LiferayStateAwareResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletQNameUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.UserAttributes;
import com.liferay.portal.kernel.redirect.RedirectURLSettingsUtil;
import com.liferay.portal.kernel.security.auth.AlwaysAllowDoAsUser;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.TicketLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.HttpSessionWrapper;
import com.liferay.portal.kernel.servlet.NonSerializableObjectRequestWrapper;
import com.liferay.portal.kernel.servlet.PersistentHttpServletRequestWrapper;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdSplitterUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.DeterminateKeyGenerator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListMergeable;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalInetSocketAddressEventListener;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletCategoryKeys;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.model.impl.CookieRemotePreference;
import com.liferay.portal.model.impl.LayoutTypeImpl;
import com.liferay.portal.security.jaas.JAASHelper;
import com.liferay.portal.security.sso.SSOUtil;
import com.liferay.portal.servlet.filters.i18n.I18nFilter;
import com.liferay.portal.spring.context.PortalContextLoaderListener;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.webserver.WebServerServlet;
import com.liferay.portlet.LiferayPortletUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesWrapper;
import com.liferay.portlet.admin.util.OmniadminUtil;
import com.liferay.sites.kernel.util.Sites;
import com.liferay.sites.kernel.util.SitesUtil;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.util.JS;

import java.awt.image.RenderedImage;

import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Method;

import java.net.IDN;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.net.UnknownHostException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Jorge Ferrer
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Wesley Gong
 * @author Hugo Huijser
 * @author Juan Fernández
 * @author Marco Leo
 * @author Neil Griffin
 */
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
			catch (UnknownHostException unknownHostException) {
				if (_log.isDebugEnabled()) {
					_log.debug(unknownHostException);
				}
			}
		}

		_computerName = computerName;

		try {
			List<NetworkInterface> networkInterfaces = Collections.list(
				NetworkInterface.getNetworkInterfaces());

			for (NetworkInterface networkInterface : networkInterfaces) {
				List<InetAddress> inetAddresses = Collections.list(
					networkInterface.getInetAddresses());

				for (InetAddress inetAddress : inetAddresses) {
					if (inetAddress instanceof Inet4Address) {
						_computerAddresses.add(inetAddress.getHostAddress());
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error("Unable to determine server's IP addresses");

			_log.error(exception);
		}

		// Paths

		_pathProxy = PropsValues.PORTAL_PROXY_PATH;

		_pathContext = _pathProxy.concat(
			getContextPath(
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

		// Portal authentication

		_reservedParams.add("p_auth");
		_reservedParams.add("p_auth_secret");

		// Portal layout

		_reservedParams.add("p_l_back_url");
		_reservedParams.add("p_l_id");
		_reservedParams.add("p_l_mode");
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
		_reservedParams.add("p_p_async");
		_reservedParams.add("p_p_hub");
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
			ServiceTracker<AlwaysAllowDoAsUser, AlwaysAllowDoAsUser>
				alwaysAllowDoAsUserServiceTracker = new ServiceTracker<>(
					_bundleContext, AlwaysAllowDoAsUser.class,
					new AlwaysAllowDoAsUserServiceTrackerCustomizer());

			alwaysAllowDoAsUserServiceTracker.open();

			ServiceTracker
				<PortalInetSocketAddressEventListener,
				 PortalInetSocketAddressEventListener>
					portalInetSocketAddressEventListenerServiceTracker =
						new ServiceTracker<>(
							_bundleContext,
							PortalInetSocketAddressEventListener.class,
							new PortalInetSocketAddressEventListenerServiceTrackerCustomizer());

			portalInetSocketAddressEventListenerServiceTracker.open();

			ServiceTracker<StrutsAction, StrutsAction>
				commentsStrutsActionServiceTracker = new ServiceTracker<>(
					_bundleContext, StrutsAction.class,
					new CommentsStrutsActionServiceTrackerCustomizer());

			commentsStrutsActionServiceTracker.open();
		}
		catch (NullPointerException nullPointerException) {
			if (_log.isDebugEnabled()) {
				_log.debug(nullPointerException);
			}
		}
	}

	@Override
	public void addPageDescription(
		String description, HttpServletRequest httpServletRequest) {

		ListMergeable<String> descriptionListMergeable =
			(ListMergeable<String>)httpServletRequest.getAttribute(
				WebKeys.PAGE_DESCRIPTION);

		if (descriptionListMergeable == null) {
			descriptionListMergeable = new ListMergeable<>();

			httpServletRequest.setAttribute(
				WebKeys.PAGE_DESCRIPTION, descriptionListMergeable);
		}

		descriptionListMergeable.add(description);
	}

	@Override
	public void addPageKeywords(
		String keywords, HttpServletRequest httpServletRequest) {

		ListMergeable<String> keywordsListMergeable =
			(ListMergeable<String>)httpServletRequest.getAttribute(
				WebKeys.PAGE_KEYWORDS);

		if (keywordsListMergeable == null) {
			keywordsListMergeable = new ListMergeable<>();

			httpServletRequest.setAttribute(
				WebKeys.PAGE_KEYWORDS, keywordsListMergeable);
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
	public void addPageSubtitle(
		String subtitle, HttpServletRequest httpServletRequest) {

		ListMergeable<String> subtitleListMergeable =
			(ListMergeable<String>)httpServletRequest.getAttribute(
				WebKeys.PAGE_SUBTITLE);

		if (subtitleListMergeable == null) {
			subtitleListMergeable = new ListMergeable<>();

			httpServletRequest.setAttribute(
				WebKeys.PAGE_SUBTITLE, subtitleListMergeable);
		}

		subtitleListMergeable.add(subtitle);
	}

	@Override
	public void addPageTitle(
		String title, HttpServletRequest httpServletRequest) {

		ListMergeable<String> titleListMergeable =
			(ListMergeable<String>)httpServletRequest.getAttribute(
				WebKeys.PAGE_TITLE);

		if (titleListMergeable == null) {
			titleListMergeable = new ListMergeable<>();

			httpServletRequest.setAttribute(
				WebKeys.PAGE_TITLE, titleListMergeable);
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

	@Override
	public void addPortletBreadcrumbEntry(
		HttpServletRequest httpServletRequest, String title, String url) {

		addPortletBreadcrumbEntry(httpServletRequest, title, url, null);
	}

	@Override
	public void addPortletBreadcrumbEntry(
		HttpServletRequest httpServletRequest, String title, String url,
		Map<String, Object> data) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		boolean portletBreadcrumbEntry = false;

		if (Validator.isNotNull(portletDisplay.getId()) &&
			!portletDisplay.isFocused()) {

			portletBreadcrumbEntry = true;
		}

		addPortletBreadcrumbEntry(
			httpServletRequest, title, url, null, portletBreadcrumbEntry);
	}

	@Override
	public void addPortletBreadcrumbEntry(
		HttpServletRequest httpServletRequest, String title, String url,
		Map<String, Object> data, boolean portletBreadcrumbEntry) {

		String name = WebKeys.PORTLET_BREADCRUMBS;

		if (portletBreadcrumbEntry) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			name += StringPool.UNDERLINE + portletDisplay.getId();
		}

		List<BreadcrumbEntry> breadcrumbEntries =
			(List<BreadcrumbEntry>)httpServletRequest.getAttribute(name);

		if (breadcrumbEntries == null) {
			breadcrumbEntries = new ArrayList<>();

			httpServletRequest.setAttribute(name, breadcrumbEntries);
		}

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setData(data);
		breadcrumbEntry.setTitle(title);
		breadcrumbEntry.setURL(url);

		breadcrumbEntries.add(breadcrumbEntry);
	}

	@Override
	public void addPortletDefaultResource(
			HttpServletRequest httpServletRequest, Portlet portlet)
		throws PortalException {

		String name = _getPortletBaseResource(portlet);

		if (Validator.isNull(name)) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long groupId = 0;

		if (layout.isTypeControlPanel()) {
			groupId = themeDisplay.getScopeGroupId();
		}
		else {
			groupId = getScopeGroupId(layout, portlet.getPortletId());
		}

		addRootModelResource(themeDisplay.getCompanyId(), groupId, name);
	}

	@Override
	public void addPortletDefaultResource(
			long companyId, Layout layout, Portlet portlet)
		throws PortalException {

		String name = _getPortletBaseResource(portlet);

		if (Validator.isNull(name)) {
			return;
		}

		long groupId = getScopeGroupId(layout, portlet.getPortletId());

		addRootModelResource(companyId, groupId, name);
	}

	@Override
	public String addPreservedParameters(
		ThemeDisplay themeDisplay, Layout layout, String url,
		boolean doAsUser) {

		return addPreservedParameters(
			themeDisplay, url, layout.isTypeControlPanel(), doAsUser);
	}

	@Override
	public String addPreservedParameters(
		ThemeDisplay themeDisplay, String url) {

		return addPreservedParameters(
			themeDisplay, themeDisplay.getLayout(), url, true);
	}

	@Override
	public String addPreservedParameters(
		ThemeDisplay themeDisplay, String url, boolean typeControlPanel,
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

		if (typeControlPanel) {
			if (Validator.isNotNull(themeDisplay.getPpid())) {
				url = HttpUtil.setParameter(
					url, "p_p_id", themeDisplay.getPpid());
			}

			if (themeDisplay.getDoAsGroupId() > 0) {
				url = HttpUtil.setParameter(
					url, "doAsGroupId", themeDisplay.getDoAsGroupId());
			}

			if (themeDisplay.getRefererGroupId() !=
					GroupConstants.DEFAULT_PARENT_GROUP_ID) {

				url = HttpUtil.setParameter(
					url, "refererGroupId", themeDisplay.getRefererGroupId());
			}

			if (themeDisplay.getRefererPlid() != LayoutConstants.DEFAULT_PLID) {
				url = HttpUtil.setParameter(
					url, "refererPlid", themeDisplay.getRefererPlid());
			}
		}

		return url;
	}

	@Override
	public void clearRequestParameters(RenderRequest renderRequest) {
		LiferayRenderRequest liferayRenderRequest =
			(LiferayRenderRequest)renderRequest;

		if (liferayRenderRequest.isTriggeredByActionURL()) {
			liferayRenderRequest.clearRenderParameters();
		}
	}

	@Override
	public void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		if (actionResponse instanceof LiferayStateAwareResponse) {
			LiferayStateAwareResponse liferayStateAwareResponse =
				(LiferayStateAwareResponse)actionResponse;

			if (liferayStateAwareResponse.getRedirectLocation() != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Cannot copy parameters on a redirected " +
							"LiferayStateAwareResponse");
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

		Enumeration<String> enumeration = actionRequest.getParameterNames();

		while (enumeration.hasMoreElements()) {
			String param = enumeration.nextElement();

			if (renderParameters.get(actionResponse.getNamespace() + param) ==
					null) {

				String[] values = actionRequest.getParameterValues(param);

				if (values == null) {
					values = new String[0];
				}
				else {
					values = ArrayUtil.filter(
						values,
						s -> {
							if (s == null) {
								return false;
							}

							return true;
						});
				}

				actionResponse.setRenderParameter(param, values);
			}
		}
	}

	@Override
	public String escapeRedirect(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		URI uri = HttpUtil.getURI(url);

		if (uri == null) {
			return null;
		}

		if (!uri.isAbsolute()) {

			// https://datatracker.ietf.org/doc/html/rfc3986#section-4.2

			if (url.startsWith(StringPool.DOUBLE_SLASH)) {

				// "//" authority path-abempty

				return null;
			}

			return url;
		}

		String domain = uri.getHost();

		if (Validator.isNull(domain)) {

			// Absolute URL must have a domain

			return null;
		}

		if (!_validPortalDomainCheckDisabled && isValidPortalDomain(domain)) {
			return url;
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		String securityMode = RedirectURLSettingsUtil.getSecurityMode(
			companyId);

		if (securityMode.equals("domain")) {
			String[] allowedDomains = RedirectURLSettingsUtil.getAllowedDomains(
				companyId);

			if (allowedDomains.length == 0) {
				return url;
			}

			for (String allowedDomain : allowedDomains) {
				if (allowedDomain.startsWith("*.") &&
					(allowedDomain.regionMatches(
						1, domain,
						domain.length() - (allowedDomain.length() - 1),
						allowedDomain.length() - 1) ||
					 allowedDomain.regionMatches(
						 2, domain, 0, domain.length()))) {

					return url;
				}
				else if (allowedDomain.equals(domain)) {
					return url;
				}
			}

			if (_log.isWarnEnabled()) {
				_log.warn("Redirect URL " + url + " is not allowed");
			}

			url = null;
		}
		else {
			String[] allowedIPs = RedirectURLSettingsUtil.getAllowedIPs(
				companyId);

			if (allowedIPs.length == 0) {
				return url;
			}

			try {
				InetAddress inetAddress = InetAddressUtil.getInetAddressByName(
					domain);

				String hostAddress = inetAddress.getHostAddress();

				boolean serverIpIsHostAddress = _computerAddresses.contains(
					hostAddress);

				for (String ip : allowedIPs) {
					if ((serverIpIsHostAddress && ip.equals("SERVER_IP")) ||
						ip.equals(hostAddress)) {

						return url;
					}
				}

				if (_log.isWarnEnabled()) {
					_log.warn("Redirect URL " + url + " is not allowed");
				}
			}
			catch (UnknownHostException unknownHostException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to determine IP for redirect URL " + url,
						unknownHostException);
				}
			}

			url = null;
		}

		return url;
	}

	@Override
	public String generateRandomKey(
		HttpServletRequest httpServletRequest, String input) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isAjax() || themeDisplay.isIsolated() ||
			themeDisplay.isLifecycleResource() ||
			themeDisplay.isStateExclusive()) {

			return StringUtil.randomId();
		}

		StringBundler sb = new StringBundler(5);

		sb.append(DeterminateKeyGenerator.generate(input));
		sb.append(StringPool.UNDERLINE);

		Object columnId = httpServletRequest.getAttribute(
			WebKeys.RENDER_PORTLET_COLUMN_ID);

		if (columnId != null) {
			sb.append(HtmlUtil.getAUICompatibleId(columnId.toString()));
		}

		sb.append(StringPool.UNDERLINE);

		Object columnPos = httpServletRequest.getAttribute(
			WebKeys.RENDER_PORTLET_COLUMN_POS);

		if (columnPos != null) {
			sb.append(HtmlUtil.getAUICompatibleId(columnPos.toString()));
		}

		return sb.toString();
	}

	@Override
	public String getAbsoluteURL(
		HttpServletRequest httpServletRequest, String url) {

		String portalURL = getPortalURL(httpServletRequest);

		if ((url.charAt(0) == CharPool.SLASH) &&
			Validator.isNotNull(portalURL)) {

			url = portalURL.concat(url);
		}

		if (!CookieKeys.hasSessionId(httpServletRequest) &&
			url.startsWith(portalURL)) {

			HttpSession httpSession = httpServletRequest.getSession();

			url = getURLWithSessionId(url, httpSession.getId());
		}

		return url;
	}

	@Override
	public LayoutQueryStringComposite getActualLayoutQueryStringComposite(
			long groupId, boolean privateLayout, String friendlyURL,
			Map<String, String[]> params, Map<String, Object> requestContext)
		throws PortalException {

		Layout layout = null;

		if (Validator.isNull(friendlyURL)) {

			// We need to ensure that virtual layouts are merged

			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, true, 0, 1);

			if (!layouts.isEmpty()) {
				layout = layouts.get(0);
			}
			else {
				throw new NoSuchLayoutException(
					StringBundler.concat(
						"{groupId=", groupId, ", privateLayout=", privateLayout,
						"}"));
			}
		}
		else {
			return getPortletFriendlyURLMapperLayoutQueryStringComposite(
				groupId, privateLayout, friendlyURL, params, requestContext);
		}

		return new LayoutQueryStringComposite(
			layout, friendlyURL, StringPool.BLANK);
	}

	@Override
	public String getActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		String actualURL = null;

		if (friendlyURL != null) {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)requestContext.get("request");

			long companyId = PortalInstances.getCompanyId(httpServletRequest);

			for (String urlSeparator :
					FriendlyURLResolverRegistryUtil.getURLSeparators()) {

				if (!friendlyURL.startsWith(urlSeparator)) {
					continue;
				}

				try {
					FriendlyURLResolver friendlyURLResolver =
						FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(
							urlSeparator);

					actualURL = friendlyURLResolver.getActualURL(
						companyId, groupId, privateLayout, mainPath,
						friendlyURL, params, requestContext);

					break;
				}
				catch (Exception exception) {
					throw new NoSuchLayoutException(exception);
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

	@Override
	public String getAlternateURL(
			String canonicalURL, ThemeDisplay themeDisplay, Locale locale,
			Layout layout)
		throws PortalException {

		Map<Locale, String> alternateURLs = _getAlternateURLs(
			canonicalURL, themeDisplay, layout, Collections.singleton(locale));

		return alternateURLs.get(locale);
	}

	@Override
	public Map<Locale, String> getAlternateURLs(
			String canonicalURL, ThemeDisplay themeDisplay, Layout layout)
		throws PortalException {

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
			layout.getGroupId());

		return _getAlternateURLs(
			canonicalURL, themeDisplay, layout, availableLocales);
	}

	@Override
	public long[] getAncestorSiteGroupIds(long groupId) {
		int i = 0;

		Set<Group> groups = getAncestorSiteGroups(groupId, false);

		long[] groupIds = new long[groups.size()];

		for (Group group : groups) {
			groupIds[i++] = group.getGroupId();
		}

		return groupIds;
	}

	@Override
	public BaseModel<?> getBaseModel(ResourcePermission resourcePermission)
		throws PortalException {

		String modelName = resourcePermission.getName();

		return getBaseModel(modelName, resourcePermission.getPrimKey());
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
					methodName, new Class<?>[] {Long.TYPE});

				return (BaseModel<?>)method.invoke(null, Long.valueOf(primKey));
			}

			method = serviceUtil.getMethod(
				methodName, new Class<?>[] {String.class});

			return (BaseModel<?>)method.invoke(null, primKey);
		}
		catch (Exception exception) {
			Throwable throwable = exception.getCause();

			if (throwable instanceof PortalException) {
				throw (PortalException)throwable;
			}
			else if (throwable instanceof SystemException) {
				throw (SystemException)throwable;
			}
			else {
				throw new SystemException(throwable);
			}
		}
	}

	@Override
	public List<Group> getBrowsableScopeGroups(
			long userId, long companyId, long groupId, String portletId)
		throws PortalException {

		List<Group> groups = new ArrayList<>();

		groups.addAll(
			0,
			GroupLocalServiceUtil.search(
				companyId, null, null,
				LinkedHashMapBuilder.<String, Object>put(
					"usersGroups", Long.valueOf(userId)
				).build(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));

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

		return getCanonicalURL(completeURL, themeDisplay, layout, false, true);
	}

	@Override
	public String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout,
			boolean forceLayoutFriendlyURL)
		throws PortalException {

		return getCanonicalURL(
			completeURL, themeDisplay, layout, forceLayoutFriendlyURL, true);
	}

	@Override
	public String getCanonicalURL(
			String completeURL, ThemeDisplay themeDisplay, Layout layout,
			boolean forceLayoutFriendlyURL, boolean includeQueryString)
		throws PortalException {

		String groupFriendlyURL = StringPool.BLANK;
		boolean includeParametersURL = false;
		String parametersURL = StringPool.BLANK;

		if (Validator.isNotNull(completeURL)) {
			completeURL = removeRedirectParameter(completeURL);

			int pos = -1;

			for (String urlSeparator :
					FriendlyURLResolverRegistryUtil.getURLSeparators()) {

				pos = completeURL.indexOf(urlSeparator);

				if (pos != -1) {
					includeParametersURL = true;

					break;
				}
			}

			if (pos == -1) {
				pos = completeURL.indexOf(CharPool.QUESTION);
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

		String defaultLayoutFriendlyURL = null;

		Locale siteDefaultLocale = getSiteDefaultLocale(layout.getGroupId());

		if (siteDefaultLocale.equals(themeDisplay.getLocale())) {
			defaultLayoutFriendlyURL = themeDisplay.getLayoutFriendlyURL(
				layout);
		}
		else {
			defaultLayoutFriendlyURL = layout.getFriendlyURL(
				getSiteDefaultLocale(layout.getGroupId()));
		}

		Group layoutGroup = layout.getGroup();

		if (forceLayoutFriendlyURL ||
			((!layout.isFirstParent() || Validator.isNotNull(parametersURL)) &&
			 _requiresLayoutFriendlyURL(
				 layoutGroup.getFriendlyURL(),
				 themeDisplay.getLayoutFriendlyURL(layout),
				 StringUtil.toLowerCase(groupFriendlyURL))) ||
			groupFriendlyURL.endsWith(
				StringPool.SLASH + layout.getLayoutId())) {

			canonicalLayoutFriendlyURL = defaultLayoutFriendlyURL;
		}

		groupFriendlyURL = getGroupFriendlyURL(
			layout.getLayoutSet(), themeDisplay, true,
			layout.isTypeControlPanel());

		if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2) {
			String groupFriendlyURLDomain = HttpUtil.getDomain(
				groupFriendlyURL);

			int pos = groupFriendlyURL.indexOf(groupFriendlyURLDomain);

			if (pos > 0) {
				pos = groupFriendlyURL.indexOf(
					CharPool.SLASH, pos + groupFriendlyURLDomain.length());

				if (Validator.isNotNull(_pathContext)) {
					pos = groupFriendlyURL.indexOf(
						CharPool.SLASH, pos + _pathContext.length());
				}
			}

			StringBundler sb = new StringBundler(3);

			if ((pos <= 0) || (pos >= groupFriendlyURL.length())) {
				sb.append(groupFriendlyURL);
				sb.append(_buildI18NPath(siteDefaultLocale, layout.getGroup()));

				if (!canonicalLayoutFriendlyURL.startsWith(StringPool.SLASH)) {
					sb.append(StringPool.SLASH);
				}
			}
			else {
				String groupFriendlyURLPrefix = groupFriendlyURL.substring(
					0, pos);

				String groupFriendlyURLSuffix = groupFriendlyURL.substring(pos);

				sb.append(groupFriendlyURLPrefix);
				sb.append(_buildI18NPath(siteDefaultLocale, layout.getGroup()));
				sb.append(groupFriendlyURLSuffix);
			}

			groupFriendlyURL = sb.toString();
		}

		groupFriendlyURL = groupFriendlyURL.concat(canonicalLayoutFriendlyURL);

		if (includeQueryString) {
			if (groupFriendlyURL.endsWith(StringPool.SLASH) &&
				parametersURL.startsWith(StringPool.SLASH)) {

				parametersURL = parametersURL.substring(1);
			}

			groupFriendlyURL = groupFriendlyURL.concat(parametersURL);
		}
		else if (includeParametersURL) {
			int x = 0;

			if (groupFriendlyURL.endsWith(StringPool.SLASH) &&
				parametersURL.startsWith(StringPool.SLASH)) {

				x = 1;
			}

			int y = parametersURL.indexOf(CharPool.QUESTION);

			if (y == -1) {
				y = parametersURL.length();
			}

			groupFriendlyURL = groupFriendlyURL.concat(
				parametersURL.substring(x, y));
		}

		return groupFriendlyURL;
	}

	@Override
	public String getCDNHost(boolean secure) {
		long companyId = CompanyThreadLocal.getCompanyId();

		if (secure) {
			return getCDNHostHttps(companyId);
		}

		return getCDNHostHttp(companyId);
	}

	@Override
	public String getCDNHost(HttpServletRequest httpServletRequest)
		throws PortalException {

		boolean cdnEnabled = ParamUtil.getBoolean(
			httpServletRequest, "cdn_enabled", true);
		String portletId = ParamUtil.getString(httpServletRequest, "p_p_id");

		if (!cdnEnabled || portletId.equals(PortletKeys.PORTAL_SETTINGS)) {
			return StringPool.BLANK;
		}

		String cdnHost = null;

		Company company = getCompany(httpServletRequest);

		if (isSecure(httpServletRequest)) {
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
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
		catch (SystemException systemException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(systemException);
			}
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
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to get class name from id " + classNameId, exception);
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

		try (Connection connection = DataAccess.getConnection()) {
			if (PortalUpgradeProcess.isInLatestSchemaVersion(connection)) {
				return ClassNameLocalServiceUtil.getClassNameId(value);
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select classNameId from ClassName_ where value = ?")) {

				preparedStatement.setString(1, value);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						return resultSet.getLong("classNameId");
					}
				}
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Unable to get class name ID from value " + value, exception);
		}

		return 0;
	}

	@Override
	public Company getCompany(HttpServletRequest httpServletRequest)
		throws PortalException {

		long companyId = getCompanyId(httpServletRequest);

		if (companyId <= 0) {
			return null;
		}

		Company company = (Company)httpServletRequest.getAttribute(
			WebKeys.COMPANY);

		if (company == null) {

			// LEP-5994

			company = CompanyLocalServiceUtil.fetchCompanyById(companyId);

			if (company == null) {
				company = CompanyLocalServiceUtil.getCompanyById(
					PortalInstances.getDefaultCompanyId());
			}

			httpServletRequest.setAttribute(WebKeys.COMPANY, company);
		}

		return company;
	}

	@Override
	public Company getCompany(PortletRequest portletRequest)
		throws PortalException {

		return getCompany(getHttpServletRequest(portletRequest));
	}

	@Override
	public long getCompanyId(HttpServletRequest httpServletRequest) {
		return PortalInstances.getCompanyId(httpServletRequest);
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
	public Set<String> getComputerAddresses() {
		return _computerAddresses;
	}

	@Override
	public String getComputerName() {
		return _computerName;
	}

	@Override
	public String getControlPanelFullURL(
			long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		Group controlPanelDisplayGroup = getControlPanelDisplayGroup(
			group.getCompanyId(), scopeGroupId, 0, ppid);

		Company company = CompanyLocalServiceUtil.getCompany(
			controlPanelDisplayGroup.getCompanyId());

		return getSiteAdminURL(
			getPortalURL(
				company.getVirtualHostname(), getPortalServerPort(false),
				false),
			controlPanelDisplayGroup, ppid, params);
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
	public PortletURL getControlPanelPortletURL(
		HttpServletRequest httpServletRequest, Group group, String portletId,
		long refererGroupId, long refererPlid, String lifecycle) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		if (group == null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = getControlPanelDisplayGroup(
				themeDisplay.getControlPanelGroup(),
				themeDisplay.getScopeGroup(), themeDisplay.getDoAsGroupId(),
				portletId);
		}

		return requestBackedPortletURLFactory.createControlPanelPortletURL(
			portletId, group, refererGroupId, refererPlid, lifecycle);
	}

	@Override
	public PortletURL getControlPanelPortletURL(
		HttpServletRequest httpServletRequest, String portletId,
		String lifecycle) {

		return getControlPanelPortletURL(
			httpServletRequest, null, portletId, 0, 0, lifecycle);
	}

	@Override
	public PortletURL getControlPanelPortletURL(
		PortletRequest portletRequest, Group group, String portletId,
		long refererGroupId, long refererPlid, String lifecycle) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(portletRequest);

		if (group == null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = getControlPanelDisplayGroup(
				themeDisplay.getControlPanelGroup(),
				themeDisplay.getScopeGroup(), themeDisplay.getDoAsGroupId(),
				portletId);
		}

		return requestBackedPortletURLFactory.createControlPanelPortletURL(
			portletId, group, refererGroupId, refererPlid, lifecycle);
	}

	@Override
	public PortletURL getControlPanelPortletURL(
		PortletRequest portletRequest, String portletId, String lifecycle) {

		return getControlPanelPortletURL(
			portletRequest, null, portletId, 0, 0, lifecycle);
	}

	@Override
	public String getCreateAccountURL(
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws Exception {

		if (Validator.isNull(PropsValues.COMPANY_SECURITY_STRANGERS_URL)) {
			long plid = themeDisplay.getPlid();

			Layout layout = themeDisplay.getLayout();

			if (layout.isPrivateLayout()) {
				plid = LayoutLocalServiceUtil.getDefaultPlid(
					layout.getGroupId(), false);
			}

			PortletURL createAccountURL = PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					httpServletRequest, PortletKeys.LOGIN, plid,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/login/create_account"
			).setParameter(
				"saveLastPath", false
			).setPortletMode(
				PortletMode.VIEW
			).setWindowState(
				WindowState.MAXIMIZED
			).buildPortletURL();

			if (!PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS) {
				return createAccountURL.toString();
			}

			return StringUtil.replaceFirst(
				createAccountURL.toString(), getPortalURL(httpServletRequest),
				getPortalURL(httpServletRequest, true));
		}

		try {
			Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				themeDisplay.getScopeGroupId(), false,
				PropsValues.COMPANY_SECURITY_STRANGERS_URL);

			return getLayoutURL(layout, themeDisplay);
		}
		catch (NoSuchLayoutException noSuchLayoutException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchLayoutException);
			}
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

		Group siteGroup = getCurrentSiteGroup(groupId);

		if (siteGroup != null) {
			groups.add(siteGroup);
		}

		groups.addAll(
			getAncestorSiteGroups(
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

		for (long groupId : groupIds) {
			groups.addAll(
				getCurrentAndAncestorSiteGroups(
					groupId, checkContentSharingWithChildrenEnabled));
		}

		return new ArrayList<>(groups);
	}

	@Override
	public String getCurrentCompleteURL(HttpServletRequest httpServletRequest) {
		String currentCompleteURL = (String)httpServletRequest.getAttribute(
			WebKeys.CURRENT_COMPLETE_URL);

		if (currentCompleteURL == null) {
			currentCompleteURL = HttpUtil.getCompleteURL(httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.CURRENT_COMPLETE_URL, currentCompleteURL);
		}

		return currentCompleteURL;
	}

	@Override
	public String getCurrentURL(HttpServletRequest httpServletRequest) {
		String currentURL = (String)httpServletRequest.getAttribute(
			WebKeys.CURRENT_URL);

		if (currentURL != null) {
			return currentURL;
		}

		currentURL = ParamUtil.getString(httpServletRequest, "currentURL");

		if (Validator.isNull(currentURL)) {
			currentURL = HttpUtil.getCompleteURL(httpServletRequest);

			if (Validator.isNotNull(currentURL) &&
				!currentURL.contains(_J_SECURITY_CHECK)) {

				currentURL = currentURL.substring(
					currentURL.indexOf(Http.PROTOCOL_DELIMITER) +
						Http.PROTOCOL_DELIMITER.length());

				currentURL = currentURL.substring(
					currentURL.indexOf(CharPool.SLASH));
			}
		}

		if (Validator.isNull(currentURL)) {
			currentURL = getPathMain();
		}

		httpServletRequest.setAttribute(WebKeys.CURRENT_URL, currentURL);

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
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
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
				catch (PortalException portalException) {
					throw portalException;
				}
				catch (Exception exception) {
					throw new PortalException(exception);
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
			ExpandoBridge expandoBridge, HttpServletRequest httpServletRequest)
		throws PortalException {

		Map<String, Serializable> attributes = new HashMap<>();

		List<String> names = new ArrayList<>();

		Enumeration<String> enumeration =
			httpServletRequest.getParameterNames();

		while (enumeration.hasMoreElements()) {
			String param = enumeration.nextElement();

			if (param.contains("ExpandoAttributeName--")) {
				String name = ParamUtil.getString(httpServletRequest, param);

				names.add(name);
			}
		}

		for (String name : names) {
			int type = expandoBridge.getAttributeType(name);

			UnicodeProperties unicodeProperties =
				expandoBridge.getAttributeProperties(name);

			String displayType = GetterUtil.getString(
				unicodeProperties.getProperty(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE),
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX);

			Serializable value = getExpandoValue(
				httpServletRequest, "ExpandoAttribute--" + name + "--", type,
				displayType);

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
			HttpServletRequest httpServletRequest, String name, int type,
			String displayType)
		throws PortalException {

		Serializable value = null;

		if (type == ExpandoColumnConstants.BOOLEAN) {
			value = ParamUtil.getBoolean(httpServletRequest, name);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
		}
		else if (type == ExpandoColumnConstants.DATE) {
			int valueDateMonth = ParamUtil.getInteger(
				httpServletRequest, name + "Month");
			int valueDateDay = ParamUtil.getInteger(
				httpServletRequest, name + "Day");
			int valueDateYear = ParamUtil.getInteger(
				httpServletRequest, name + "Year");
			int valueDateHour = ParamUtil.getInteger(
				httpServletRequest, name + "Hour");
			int valueDateMinute = ParamUtil.getInteger(
				httpServletRequest, name + "Minute");
			int valueDateAmPm = ParamUtil.getInteger(
				httpServletRequest, name + "AmPm");

			if (valueDateAmPm == Calendar.PM) {
				valueDateHour += 12;
			}

			TimeZone timeZone = null;

			User user = getUser(httpServletRequest);

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
			value = ParamUtil.getDouble(httpServletRequest, name);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			String[] values = httpServletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getDoubleValues(values);
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			value = ParamUtil.getFloat(httpServletRequest, name);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			String[] values = httpServletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getFloatValues(values);
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			value = ParamUtil.getInteger(httpServletRequest, name);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			String[] values = httpServletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getIntegerValues(values);
		}
		else if (type == ExpandoColumnConstants.LONG) {
			value = ParamUtil.getLong(httpServletRequest, name);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			String[] values = httpServletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getLongValues(values);
		}
		else if (type == ExpandoColumnConstants.NUMBER) {
			value = ParamUtil.getNumber(httpServletRequest, name);
		}
		else if (type == ExpandoColumnConstants.NUMBER_ARRAY) {
			String[] values = httpServletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getNumberValues(values);
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			value = ParamUtil.getShort(httpServletRequest, name);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			String[] values = httpServletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = GetterUtil.getShortValues(values);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			String[] values = httpServletRequest.getParameterValues(name);

			if (displayType.equals(
					ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_TEXT_BOX) &&
				!ArrayUtil.isEmpty(values)) {

				values = StringUtil.splitLines(values[0]);
			}

			value = values;
		}
		else if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
			value = (Serializable)LocalizationUtil.getLocalizationMap(
				httpServletRequest, name);
		}
		else {
			value = ParamUtil.getString(httpServletRequest, name);
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
	public String getFirstPageLayoutTypes(
		HttpServletRequest httpServletRequest) {

		StringBundler sb = new StringBundler();

		for (String type : LayoutTypeControllerTracker.getTypes()) {
			LayoutTypeController layoutTypeController =
				LayoutTypeControllerTracker.getLayoutTypeController(type);

			if (layoutTypeController.isFirstPageable()) {
				sb.append(
					LanguageUtil.get(
						httpServletRequest, "layout.types." + type));
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
	public String getForwardedHost(HttpServletRequest httpServletRequest) {
		String serverName = httpServletRequest.getServerName();

		if (!PropsValues.WEB_SERVER_FORWARDED_HOST_ENABLED) {
			return serverName;
		}

		String forwardedHost = httpServletRequest.getHeader(
			PropsValues.WEB_SERVER_FORWARDED_HOST_HEADER);

		if (Validator.isBlank(forwardedHost) ||
			forwardedHost.equals(serverName)) {

			return serverName;
		}

		if (_validPortalDomainCheckDisabled) {
			if (!Validator.isHostName(forwardedHost)) {
				if (_log.isWarnEnabled()) {
					_log.warn("Invalid forwarded host: " + forwardedHost);
				}

				return serverName;
			}
		}
		else if (!isValidPortalDomain(forwardedHost)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Invalid forwarded host: " + forwardedHost);
			}

			return serverName;
		}

		return forwardedHost;
	}

	@Override
	public int getForwardedPort(HttpServletRequest httpServletRequest) {
		if (!PropsValues.WEB_SERVER_FORWARDED_PORT_ENABLED) {
			return httpServletRequest.getServerPort();
		}

		return GetterUtil.getInteger(
			httpServletRequest.getHeader(
				PropsValues.WEB_SERVER_FORWARDED_PORT_HEADER),
			httpServletRequest.getServerPort());
	}

	@Override
	public String getFullName(
		String firstName, String middleName, String lastName) {

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		return fullNameGenerator.getFullName(firstName, middleName, lastName);
	}

	@Override
	public String getGoogleGadgetURL(Portlet portlet, ThemeDisplay themeDisplay)
		throws PortalException {

		return getServletURL(
			portlet, PropsValues.GOOGLE_GADGET_SERVLET_MAPPING, themeDisplay);
	}

	@Override
	public String getGroupFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay,
			boolean canonicalURL, boolean controlPanel)
		throws PortalException {

		Group group = themeDisplay.getSiteGroup();

		if (group.getGroupId() != layoutSet.getGroupId()) {
			group = layoutSet.getGroup();
		}

		return _getGroupFriendlyURL(
			group, layoutSet, themeDisplay, canonicalURL, controlPanel);
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

			return getGroupFriendlyURL(layoutSet, themeDisplay, false, false);
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
	public String getHomeURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		String portalURL = getPortalURL(httpServletRequest);

		return portalURL + _pathContext +
			getRelativeHomeURL(httpServletRequest);
	}

	@Override
	public String getHost(HttpServletRequest httpServletRequest) {
		httpServletRequest = getOriginalServletRequest(httpServletRequest);

		String host = httpServletRequest.getHeader("Host");

		if (host != null) {
			host = StringUtil.toLowerCase(host.trim());

			// See RFC-3986 (section 3.2.2).

			int pos = host.indexOf(']');

			if ((pos > 0) && host.startsWith("[")) {
				return host.substring(1, pos);
			}

			pos = host.indexOf(':');

			if (pos >= 0) {
				host = host.substring(0, pos);
			}
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

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)portletRequest.getAttribute(
				PortletServlet.PORTLET_SERVLET_REQUEST);

		if (httpServletRequest != null) {
			return httpServletRequest;
		}

		if (portletRequest instanceof LiferayPortletRequest) {
			LiferayPortletRequest liferayPortletRequest =
				(LiferayPortletRequest)portletRequest;

			return liferayPortletRequest.getHttpServletRequest();
		}

		LiferayPortletRequest liferayPortletRequest =
			LiferayPortletUtil.getLiferayPortletRequest(portletRequest);

		return liferayPortletRequest.getHttpServletRequest();
	}

	@Override
	public HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse) {

		if (portletResponse instanceof LiferayPortletResponse) {
			LiferayPortletResponse liferayPortletResponse =
				(LiferayPortletResponse)portletResponse;

			return liferayPortletResponse.getHttpServletResponse();
		}

		LiferayPortletResponse liferayPortletResponse =
			LiferayPortletUtil.getLiferayPortletResponse(portletResponse);

		return liferayPortletResponse.getHttpServletResponse();
	}

	@Override
	public String getI18nPathLanguageId(
		Locale locale, String defaultI18nPathLanguageId) {

		String i18nPathLanguageId = StringUtil.replaceFirst(
			defaultI18nPathLanguageId, CharPool.UNDERLINE, CharPool.DASH);

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
	public String getJsSafePortletId(String portletId) {
		return JS.getSafeName(portletId);
	}

	@Override
	public String getLayoutActualURL(Layout layout) {
		return getLayoutActualURL(layout, getPathMain());
	}

	@Override
	public String getLayoutActualURL(Layout layout, String mainPath) {
		Layout browsableLayout = getBrowsableLayout(layout);

		String groupIdString = String.valueOf(browsableLayout.getGroupId());

		Map<String, String> variables = HashMapBuilder.put(
			"liferay:groupId", groupIdString
		).put(
			"liferay:layoutId", String.valueOf(browsableLayout.getLayoutId())
		).put(
			"liferay:mainPath", mainPath
		).put(
			"liferay:plid", String.valueOf(browsableLayout.getPlid())
		).put(
			"liferay:privateLayout",
			String.valueOf(browsableLayout.isPrivateLayout())
		).build();

		String pvlsgid = "0";

		if (browsableLayout instanceof VirtualLayout) {
			pvlsgid = groupIdString;
		}

		variables.put("liferay:pvlsgid", pvlsgid);

		variables.putAll(layout.getTypeSettingsProperties());

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				layout.getType());

		return LayoutTypeImpl.getURL(layoutTypeController.getURL(), variables);
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
		else if (MapUtil.isEmpty(params)) {
			UnicodeProperties typeSettingsUnicodeProperties =
				layout.getTypeSettingsProperties();

			queryString = typeSettingsUnicodeProperties.getProperty(
				"query-string");

			if (Validator.isNotNull(queryString) &&
				layoutActualURL.contains(StringPool.QUESTION)) {

				layoutActualURL = StringBundler.concat(
					layoutActualURL, StringPool.AMPERSAND, queryString);
			}
		}

		return layoutActualURL;
	}

	@Override
	public String getLayoutFriendlyURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		if (themeDisplay.getLayout() == layout) {
			return getLayoutFriendlyURL(themeDisplay);
		}

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				layout.getType());

		if (!layoutTypeController.isURLFriendliable()) {
			return null;
		}

		String friendlyURL = layoutTypeController.getFriendlyURL(
			themeDisplay.getRequest(), layout);

		if (friendlyURL != null) {
			return friendlyURL;
		}

		LayoutSet layoutSet = themeDisplay.getLayoutSet();

		if ((layoutSet == null) ||
			(layoutSet.getGroupId() != layout.getGroupId()) ||
			(layoutSet.isPrivateLayout() != layout.isPrivateLayout())) {

			layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
				layout.getGroupId(), layout.isPrivateLayout());
		}

		if (layoutSet == null) {
			return null;
		}

		String groupFriendlyURL = getGroupFriendlyURL(
			layoutSet, themeDisplay, false, layout.isTypeControlPanel());

		return groupFriendlyURL.concat(
			themeDisplay.getLayoutFriendlyURL(layout));
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
	public String getLayoutFriendlyURL(ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = themeDisplay.getLayout();

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				layout.getType());

		if (!layoutTypeController.isURLFriendliable()) {
			return null;
		}

		String friendlyURL = layoutTypeController.getFriendlyURL(
			themeDisplay.getRequest(), layout);

		if (friendlyURL != null) {
			return friendlyURL;
		}

		Group group = themeDisplay.getSiteGroup();

		LayoutSet layoutSet = themeDisplay.getLayoutSet();

		if (group.getGroupId() != layoutSet.getGroupId()) {
			group = layoutSet.getGroup();
		}

		String groupFriendlyURL = _getGroupFriendlyURL(
			group, themeDisplay.getLayoutSet(), themeDisplay, false,
			layout.isTypeControlPanel());

		return groupFriendlyURL.concat(
			themeDisplay.getLayoutFriendlyURL(layout));
	}

	@Override
	public LayoutFriendlyURLSeparatorComposite
			getLayoutFriendlyURLSeparatorComposite(
				long groupId, boolean privateLayout, String friendlyURL,
				Map<String, String[]> params,
				Map<String, Object> requestContext)
		throws PortalException {

		LayoutFriendlyURLSeparatorComposite
			layoutFriendlyURLSeparatorComposite = null;

		if (friendlyURL != null) {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)requestContext.get("request");

			long companyId = PortalInstances.getCompanyId(httpServletRequest);

			for (String urlSeparator :
					FriendlyURLResolverRegistryUtil.getURLSeparators()) {

				if (!friendlyURL.startsWith(urlSeparator)) {
					continue;
				}

				try {
					FriendlyURLResolver friendlyURLResolver =
						FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(
							urlSeparator);

					layoutFriendlyURLSeparatorComposite =
						friendlyURLResolver.
							getLayoutFriendlyURLSeparatorComposite(
								companyId, groupId, privateLayout, friendlyURL,
								params, requestContext);

					break;
				}
				catch (Exception exception) {
					throw new NoSuchLayoutException(exception);
				}
			}
		}

		if (layoutFriendlyURLSeparatorComposite != null) {
			return layoutFriendlyURLSeparatorComposite;
		}

		LayoutQueryStringComposite layoutQueryStringComposite =
			getActualLayoutQueryStringComposite(
				groupId, privateLayout, friendlyURL, params, requestContext);

		return new LayoutFriendlyURLSeparatorComposite(
			layoutQueryStringComposite.getLayout(),
			layoutQueryStringComposite.getFriendlyURL(),
			FRIENDLY_URL_SEPARATOR);
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
			layoutURL = getPortalURL(layout, themeDisplay) + layoutURL;
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

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (!virtualHostnames.isEmpty()) {
			virtualHostname = virtualHostnames.firstKey();
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

		return HttpUtil.removeDomain(
			getLayoutFullURL(layout, themeDisplay, doAsUser));
	}

	@Override
	public String getLayoutSetDisplayURL(
			LayoutSet layoutSet, boolean secureConnection)
		throws PortalException {

		Company company = CompanyLocalServiceUtil.getCompany(
			layoutSet.getCompanyId());

		String defaultVirtualHostName = _getDefaultVirtualHostName(company);

		int portalPort = getPortalServerPort(secureConnection);

		String portalURL = getPortalURL(
			company.getVirtualHostname(), portalPort, secureConnection);

		TreeMap<String, String> virtualHostnames = getVirtualHostnames(
			layoutSet);

		if (!virtualHostnames.isEmpty() &&
			!virtualHostnames.containsKey(defaultVirtualHostName)) {

			int index = portalURL.indexOf("://");

			String portalDomain = portalURL.substring(index + 3);

			String virtualHostname = getCanonicalDomain(
				virtualHostnames, portalDomain, defaultVirtualHostName);

			virtualHostname = getPortalURL(
				virtualHostname, portalPort, secureConnection);

			if (virtualHostname.contains(portalDomain)) {
				return virtualHostname.concat(getPathContext());
			}
		}

		StringBundler sb = new StringBundler(4);

		sb.append(portalURL);
		sb.append(getPathContext());

		Group group = layoutSet.getGroup();

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

		sb.append(friendlyURL);

		sb.append(group.getFriendlyURL());

		return sb.toString();
	}

	@Override
	public String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {

		String defaultVirtualHostName = _getDefaultVirtualHostName(
			themeDisplay.getCompany());

		String virtualHostname = null;

		TreeMap<String, String> virtualHostnames = getVirtualHostnames(
			layoutSet);

		if (!virtualHostnames.isEmpty()) {
			virtualHostname = virtualHostnames.firstKey();
		}

		if (Validator.isNotNull(virtualHostname) &&
			!StringUtil.equalsIgnoreCase(
				virtualHostname, defaultVirtualHostName)) {

			String portalURL = getPortalURL(
				virtualHostname, themeDisplay.getServerPort(),
				themeDisplay.isSecure());

			// Use the layout set's virtual host setting only if the layout set
			// is already used for the current request

			Layout curLayout = themeDisplay.getLayout();

			LayoutSet curLayoutSet = curLayout.getLayoutSet();

			long curLayoutSetId = curLayoutSet.getLayoutSetId();

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
		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		String target = typeSettingsUnicodeProperties.getProperty("target");

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

		if (layout.isTypeURL()) {
			String url = layout.getTypeSettingsProperty(
				LayoutTypePortletConstants.URL);

			if (Validator.isNotNull(url) && !url.startsWith(StringPool.SLASH) &&
				!url.startsWith(getPortalURL(layout, themeDisplay))) {

				return url;
			}

			return addPreservedParameters(
				themeDisplay, layout, getLayoutActualURL(layout), doAsUser);
		}

		String layoutFriendlyURL = getLayoutFriendlyURL(layout, themeDisplay);

		if (Validator.isNotNull(layoutFriendlyURL)) {
			return addPreservedParameters(
				themeDisplay, layout, layoutFriendlyURL, doAsUser);
		}

		String layoutURL = getLayoutActualURL(layout);

		return addPreservedParameters(
			themeDisplay, layout, layoutURL, doAsUser);
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

		if (portletRequest instanceof LiferayPortletRequest) {
			return (LiferayPortletRequest)portletRequest;
		}

		return LiferayPortletUtil.getLiferayPortletRequest(portletRequest);
	}

	@Override
	public LiferayPortletResponse getLiferayPortletResponse(
		PortletResponse portletResponse) {

		if (portletResponse instanceof LiferayPortletResponse) {
			return (LiferayPortletResponse)portletResponse;
		}

		return LiferayPortletUtil.getLiferayPortletResponse(portletResponse);
	}

	@Override
	public Locale getLocale(HttpServletRequest httpServletRequest) {
		Locale locale = (Locale)httpServletRequest.getAttribute(WebKeys.LOCALE);

		if (locale == _NULL_LOCALE) {
			return null;
		}

		if (locale == null) {
			locale = getLocale(httpServletRequest, null, false);

			if (locale == null) {
				httpServletRequest.setAttribute(WebKeys.LOCALE, _NULL_LOCALE);
			}
			else {
				httpServletRequest.setAttribute(WebKeys.LOCALE, locale);
			}
		}

		return locale;
	}

	@Override
	public Locale getLocale(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean initialize) {

		User user = null;

		if (initialize) {
			try {
				user = initUser(httpServletRequest);
			}
			catch (NoSuchUserException noSuchUserException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(noSuchUserException);
				}

				return null;
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		Locale locale = null;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			locale = themeDisplay.getLocale();

			if (LanguageUtil.isAvailableLocale(
					themeDisplay.getSiteGroupId(), locale)) {

				return locale;
			}
		}

		long groupId = 0;

		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);

		if ((layout != null) && !layout.isTypeControlPanel()) {
			try {
				groupId = getSiteGroupId(getScopeGroupId(httpServletRequest));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		String i18nLanguageId = (String)httpServletRequest.getAttribute(
			WebKeys.I18N_LANGUAGE_ID);

		if (Validator.isNotNull(i18nLanguageId)) {
			locale = LocaleUtil.fromLanguageId(i18nLanguageId, true, false);

			if (LanguageUtil.isAvailableLocale(groupId, locale)) {
				return locale;
			}
			else if (groupId > 0) {
				boolean inheritLocales = true;

				try {
					inheritLocales = LanguageUtil.isInheritLocales(groupId);
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to check if group " + groupId +
							" inherits locales",
						portalException);
				}

				if (!inheritLocales) {
					String i18nLanguageCode =
						(String)httpServletRequest.getAttribute(
							WebKeys.I18N_LANGUAGE_CODE);

					locale = LanguageUtil.getLocale(groupId, i18nLanguageCode);

					if (LanguageUtil.isAvailableLocale(groupId, locale)) {
						return locale;
					}
				}
			}
		}

		String doAsUserLanguageId = ParamUtil.getString(
			httpServletRequest, "doAsUserLanguageId");

		if (Validator.isNotNull(doAsUserLanguageId)) {
			locale = LocaleUtil.fromLanguageId(doAsUserLanguageId);

			if (LanguageUtil.isAvailableLocale(groupId, locale)) {
				return locale;
			}
		}

		HttpSession httpSession = httpServletRequest.getSession(false);

		if (httpSession != null) {
			locale = (Locale)httpSession.getAttribute(WebKeys.LOCALE);

			if (LanguageUtil.isAvailableLocale(groupId, locale)) {
				return locale;
			}
		}

		// Get locale from the user

		if (user == null) {
			try {
				user = getUser(httpServletRequest);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		if ((user != null) && !user.isDefaultUser()) {
			Locale userLocale = getAvailableLocale(groupId, user.getLocale());

			if (LanguageUtil.isAvailableLocale(groupId, userLocale)) {
				if (initialize) {
					setLocale(
						httpServletRequest, httpServletResponse, userLocale);
				}

				return userLocale;
			}
		}

		// Get locale from the cookie

		String languageId = CookieKeys.getCookie(
			httpServletRequest, CookieKeys.GUEST_LANGUAGE_ID, false);

		if (Validator.isNotNull(languageId)) {
			Locale cookieLocale = getAvailableLocale(
				groupId, LocaleUtil.fromLanguageId(languageId));

			if (LanguageUtil.isAvailableLocale(groupId, cookieLocale)) {
				if (initialize) {
					setLocale(
						httpServletRequest, httpServletResponse, cookieLocale);
				}

				return cookieLocale;
			}
		}

		// Get locale from the request

		if (PropsValues.LOCALE_DEFAULT_REQUEST) {
			Enumeration<Locale> enumeration = httpServletRequest.getLocales();

			while (enumeration.hasMoreElements()) {
				Locale requestLocale = getAvailableLocale(
					groupId, enumeration.nextElement());

				if (LanguageUtil.isAvailableLocale(groupId, requestLocale)) {
					if (initialize) {
						setLocale(
							httpServletRequest, httpServletResponse,
							requestLocale);
					}

					return requestLocale;
				}
			}
		}

		// Get locale from the group

		if (groupId > 0) {
			try {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				UnicodeProperties typeSettingsUnicodeProperties =
					group.getTypeSettingsProperties();

				String defaultLanguageId =
					typeSettingsUnicodeProperties.getProperty("languageId");

				if (Validator.isNotNull(defaultLanguageId)) {
					locale = LocaleUtil.fromLanguageId(defaultLanguageId);

					if (LanguageUtil.isAvailableLocale(groupId, locale)) {
						if (initialize) {
							setLocale(
								httpServletRequest, httpServletResponse,
								locale);
						}

						return locale;
					}
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		// Get locale from the default user

		Company company = null;

		try {
			company = getCompany(httpServletRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		if (company == null) {
			return null;
		}

		User defaultUser = null;

		try {
			defaultUser = company.getDefaultUser();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		if (defaultUser == null) {
			return null;
		}

		Locale defaultUserLocale = getAvailableLocale(
			groupId, defaultUser.getLocale());

		if (LanguageUtil.isAvailableLocale(groupId, defaultUserLocale)) {
			if (initialize) {
				setLocale(
					httpServletRequest, httpServletResponse, defaultUserLocale);
			}

			return defaultUserLocale;
		}

		try {
			if (themeDisplay != null) {
				return themeDisplay.getSiteDefaultLocale();
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		try {
			return getSiteDefaultLocale(groupId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return LocaleUtil.getDefault();
		}
	}

	@Override
	public Locale getLocale(PortletRequest portletRequest) {
		return getLocale(getHttpServletRequest(portletRequest));
	}

	@Override
	public String getLocalizedFriendlyURL(
		HttpServletRequest httpServletRequest, Layout layout, Locale locale,
		Locale originalLocale) {

		String requestURI = httpServletRequest.getRequestURI();

		HttpServletRequest originalHttpServletRequest =
			getOriginalServletRequest(httpServletRequest);

		StringBuffer originRequestURL =
			originalHttpServletRequest.getRequestURL();

		if (originRequestURL.indexOf(_PUBLIC_GROUP_SERVLET_MAPPING) < 0) {
			requestURI = originalHttpServletRequest.getRequestURI();
		}

		String contextPath = getPathContext();

		if (Validator.isNotNull(contextPath) &&
			requestURI.startsWith(contextPath)) {

			requestURI = requestURI.substring(contextPath.length());
		}

		requestURI = StringUtil.replace(
			requestURI, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		String layoutFriendlyURL = null;

		if (originalLocale == null) {
			String path = httpServletRequest.getPathInfo();

			int x = path.indexOf(CharPool.SLASH, 1);

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

		boolean appendI18nPath = true;

		if ((PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 0) ||
			((PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 1) &&
			 locale.equals(LocaleUtil.getDefault()))) {

			appendI18nPath = false;
		}

		String localizedFriendlyURL = contextPath;

		if (appendI18nPath) {
			String i18nPathLanguageId = getI18nPathLanguageId(
				locale, LocaleUtil.toLanguageId(locale));

			String i18nPath = StringPool.SLASH + i18nPathLanguageId;

			if (!requestURI.contains(i18nPath)) {
				localizedFriendlyURL += i18nPath;
			}
		}

		localizedFriendlyURL += requestURI;

		String queryString = httpServletRequest.getQueryString();

		if (Validator.isNull(queryString)) {
			queryString = (String)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING);
		}

		if (Validator.isNotNull(queryString)) {
			localizedFriendlyURL += StringPool.QUESTION + queryString;
		}

		return localizedFriendlyURL;
	}

	@Override
	public String getMailId(String mx, String popPortletPrefix, Object... ids) {
		StringBundler sb = new StringBundler((ids.length * 2) + 7);

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

		return StringUtil.appendParentheticalSuffix(portletTitle, newScopeName);
	}

	@Override
	public HttpServletRequest getOriginalServletRequest(
		HttpServletRequest httpServletRequest) {

		HttpServletRequest currentHttpServletRequest = httpServletRequest;
		HttpServletRequestWrapper currentRequestWrapper = null;
		HttpServletRequest originalHttpServletRequest = null;

		while (currentHttpServletRequest instanceof HttpServletRequestWrapper) {
			if (currentHttpServletRequest instanceof
					PersistentHttpServletRequestWrapper) {

				PersistentHttpServletRequestWrapper
					persistentHttpServletRequestWrapper =
						(PersistentHttpServletRequestWrapper)
							currentHttpServletRequest;

				persistentHttpServletRequestWrapper =
					persistentHttpServletRequestWrapper.clone();

				if (originalHttpServletRequest == null) {
					originalHttpServletRequest =
						persistentHttpServletRequestWrapper;
				}

				if (currentRequestWrapper != null) {
					currentRequestWrapper.setRequest(
						persistentHttpServletRequestWrapper);
				}

				currentRequestWrapper = persistentHttpServletRequestWrapper;
			}

			// Get original request so that portlets inside portlets render
			// properly

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)currentHttpServletRequest;

			currentHttpServletRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		if (currentRequestWrapper != null) {
			currentRequestWrapper.setRequest(currentHttpServletRequest);
		}

		if (originalHttpServletRequest != null) {
			return originalHttpServletRequest;
		}

		return currentHttpServletRequest;
	}

	@Override
	public String getPathContext() {
		return _pathContext;
	}

	@Override
	public String getPathContext(HttpServletRequest httpServletRequest) {
		return getPathContext(httpServletRequest.getContextPath());
	}

	@Override
	public String getPathContext(PortletRequest portletRequest) {
		return getPathContext(portletRequest.getContextPath());
	}

	@Override
	public String getPathContext(String contextPath) {
		return _pathProxy.concat(getContextPath(contextPath));
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
		if (Validator.isNull(friendlyURL) ||
			friendlyURL.equals(StringPool.SLASH)) {

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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		if (group == null) {
			return LayoutConstants.DEFAULT_PLID;
		}

		Layout layout = null;

		try {
			if (urlParts.length == 4) {
				String layoutFriendlyURL = StringPool.SLASH + urlParts[3];

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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	@Override
	public long getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		long plid = LayoutConstants.DEFAULT_PLID;

		String key = StringBundler.concat(
			groupId, StringPool.SPACE, privateLayout, StringPool.SPACE,
			portletId);

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
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
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
	public long getPlidFromPortletId(long groupId, String portletId) {
		long plid = getPlidFromPortletId(groupId, false, portletId);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			plid = getPlidFromPortletId(groupId, true, portletId);
		}

		if (plid == LayoutConstants.DEFAULT_PLID) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Portlet ", portletId,
						" does not exist on a page in group ", groupId));
			}
		}

		return plid;
	}

	@Override
	public PortalInetSocketAddressEventListener[]
		getPortalInetSocketAddressEventListeners() {

		return _portalInetSocketAddressEventListeners.toArray(
			new PortalInetSocketAddressEventListener[0]);
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
			if (secure) {
				return PropsValues.WEB_SERVER_HTTPS_PORT;
			}

			return PropsValues.WEB_SERVER_HTTP_PORT;
		}

		return inetSocketAddress.getPort();
	}

	@Override
	public String getPortalURL(HttpServletRequest httpServletRequest) {
		return getPortalURL(httpServletRequest, isSecure(httpServletRequest));
	}

	@Override
	public String getPortalURL(
		HttpServletRequest httpServletRequest, boolean secure) {

		int serverPort = getForwardedPort(httpServletRequest);

		if (Validator.isNull(PropsValues.WEB_SERVER_HOST)) {
			return _getPortalURL(
				getForwardedHost(httpServletRequest), serverPort, secure);
		}

		return _getPortalURL(PropsValues.WEB_SERVER_HOST, serverPort, secure);
	}

	@Override
	public String getPortalURL(Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

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

			return getPortalURL(virtualHostLayout.getLayoutSet(), themeDisplay);
		}

		return getPortalURL(
			themeDisplay.getServerName(), themeDisplay.getServerPort(),
			themeDisplay.isSecure());
	}

	@Override
	public String getPortalURL(LayoutSet layoutSet, ThemeDisplay themeDisplay) {
		String serverName = themeDisplay.getServerName();

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (!virtualHostnames.isEmpty()) {
			String domain = themeDisplay.getPortalDomain();

			if (_containsHostname(virtualHostnames, domain)) {
				serverName = domain;
			}
		}

		return getPortalURL(
			serverName, themeDisplay.getServerPort(), themeDisplay.isSecure());
	}

	@Override
	public String getPortalURL(PortletRequest portletRequest) {
		return getPortalURL(getHttpServletRequest(portletRequest));
	}

	@Override
	public String getPortalURL(PortletRequest portletRequest, boolean secure) {
		return getPortalURL(getHttpServletRequest(portletRequest), secure);
	}

	@Override
	public String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		if (Validator.isNull(PropsValues.WEB_SERVER_HOST)) {
			return _getPortalURL(serverName, serverPort, secure);
		}

		return _getPortalURL(PropsValues.WEB_SERVER_HOST, serverPort, secure);
	}

	@Override
	public String getPortalURL(ThemeDisplay themeDisplay)
		throws PortalException {

		return getPortalURL((Layout)null, themeDisplay);
	}

	@Override
	public String getPortalWebDir() {
		return PropsValues.LIFERAY_WEB_PORTAL_DIR;
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

		String portletDescription = LanguageUtil.get(
			resourceBundle,
			StringBundler.concat(
				JavaConstants.JAVAX_PORTLET_DESCRIPTION, StringPool.PERIOD,
				portlet.getRootPortletId()),
			null);

		if (Validator.isNull(portletDescription)) {
			portletDescription = LanguageUtil.get(
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
			StringBundler.concat(
				JavaConstants.JAVAX_PORTLET_DESCRIPTION, StringPool.PERIOD,
				portletId));
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
			StringBundler.concat(
				JavaConstants.JAVAX_PORTLET_DESCRIPTION, StringPool.PERIOD,
				portletId));
	}

	public LayoutQueryStringComposite
			getPortletFriendlyURLMapperLayoutQueryStringComposite(
				long groupId, boolean privateLayout, String url,
				Map<String, String[]> params,
				Map<String, Object> requestContext)
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

			if (pos == -1) {
				continue;
			}

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

				String publicRenderParameterIdentifier = qName.getLocalPart();

				prpIdentifiers.put(
					publicRenderParameterIdentifier,
					PortletQNameUtil.getPublicRenderParameterName(qName));
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
						"p_p_mode", new String[] {PortletMode.VIEW.toString()});
					actualParams.put(
						"p_p_state",
						new String[] {WindowState.MAXIMIZED.toString()});

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

		Layout layout = null;

		if (Validator.isNotNull(friendlyURL)) {
			layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				groupId, privateLayout, friendlyURL);

			if (Validator.isNotNull(layout.getSourcePrototypeLayoutUuid())) {
				layout = LayoutLocalServiceUtil.getLayout(layout.getPlid());
			}
		}
		else {
			long defaultPlid = LayoutLocalServiceUtil.getDefaultPlid(
				groupId, privateLayout);

			layout = LayoutLocalServiceUtil.getLayout(defaultPlid);
		}

		return new LayoutQueryStringComposite(layout, friendlyURL, queryString);
	}

	@Override
	public String getPortletId(HttpServletRequest httpServletRequest) {
		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (liferayPortletConfig != null) {
			return liferayPortletConfig.getPortletId();
		}

		return null;
	}

	@Override
	public String getPortletId(PortletRequest portletRequest) {
		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)portletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (liferayPortletConfig != null) {
			return liferayPortletConfig.getPortletId();
		}

		return null;
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
			String portletLongTitle = ResourceBundleUtil.getString(
				resourceBundle, JavaConstants.JAVAX_PORTLET_LONG_TITLE);

			if (portletLongTitle.startsWith(
					JavaConstants.JAVAX_PORTLET_LONG_TITLE)) {

				portletLongTitle = getPortletTitle(
					portlet, servletContext, locale);
			}

			return portletLongTitle;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

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
			StringBundler.concat(
				JavaConstants.JAVAX_PORTLET_LONG_TITLE, StringPool.PERIOD,
				portletId),
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
		return StringBundler.concat(
			StringPool.UNDERLINE, portletId, StringPool.UNDERLINE);
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

		return _getPortletTitle(
			portlet.getRootPortletId(), portletConfig, locale);
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

		PortletConfig portletConfig = PortletConfigFactoryUtil.get(portletId);

		Locale locale = portletRequest.getLocale();

		String portletTitle = null;

		if (portletConfig == null) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getCompanyId(portletRequest), portletId);

			HttpServletRequest httpServletRequest = getHttpServletRequest(
				portletRequest);

			ServletContext servletContext =
				(ServletContext)httpServletRequest.getAttribute(WebKeys.CTX);

			portletTitle = getPortletTitle(portlet, servletContext, locale);
		}
		else {
			portletTitle = _getPortletTitle(
				PortletIdCodec.decodePortletName(portletId), portletConfig,
				locale);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long portletScopeGroupId = _getScopeGroupId(
			themeDisplay, layout, portletId);

		if (portletScopeGroupId != layout.getGroupId()) {
			Group portletScopeGroup = GroupLocalServiceUtil.fetchGroup(
				portletScopeGroupId);

			String portletScopeName = portletScopeGroup.getName(locale);

			try {
				portletScopeName = portletScopeGroup.getDescriptiveName(locale);
			}
			catch (PortalException portalException) {
				_log.error("Unable to get descriptive name", portalException);
			}

			return getNewPortletTitle(
				portletTitle, StringPool.BLANK, portletScopeName);
		}

		return portletTitle;
	}

	@Override
	public String getPortletTitle(PortletResponse portletResponse) {
		LiferayPortletResponse liferayPortletResponse =
			LiferayPortletUtil.getLiferayPortletResponse(portletResponse);

		if (liferayPortletResponse instanceof LiferayRenderResponse) {
			LiferayRenderResponse liferayRenderResponse =
				(LiferayRenderResponse)liferayPortletResponse;

			return liferayRenderResponse.getTitle();
		}

		return null;
	}

	@Override
	public String getPortletTitle(String portletId, Locale locale) {
		PortletConfig portletConfig = PortletConfigFactoryUtil.get(portletId);

		if (portletConfig == null) {
			return PortletIdCodec.decodePortletName(portletId);
		}

		return getPortletTitle(
			portletId, portletConfig.getResourceBundle(locale));
	}

	@Override
	public String getPortletTitle(
		String portletId, ResourceBundle resourceBundle) {

		portletId = PortletIdCodec.decodePortletName(portletId);

		String portletTitle = LanguageUtil.get(
			resourceBundle,
			StringBundler.concat(
				JavaConstants.JAVAX_PORTLET_TITLE, StringPool.PERIOD,
				portletId),
			null);

		if (Validator.isNull(portletTitle)) {
			portletTitle = ResourceBundleUtil.getString(
				resourceBundle, JavaConstants.JAVAX_PORTLET_TITLE);
		}

		return portletTitle;
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
			StringBundler.concat(
				JavaConstants.JAVAX_PORTLET_TITLE, StringPool.PERIOD,
				portletId));
	}

	@Override
	public String getPortletXmlFileName() {
		if (PropsValues.AUTO_DEPLOY_CUSTOM_PORTLET_XML) {
			return PORTLET_XML_FILE_NAME_CUSTOM;
		}

		return PORTLET_XML_FILE_NAME_STANDARD;
	}

	@Override
	public PortletPreferences getPreferences(
		HttpServletRequest httpServletRequest) {

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
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
	public String getRelativeHomeURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		Company company = getCompany(httpServletRequest);

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
	public long getScopeGroupId(HttpServletRequest httpServletRequest)
		throws PortalException {

		return getScopeGroupId(
			httpServletRequest, getPortletId(httpServletRequest));
	}

	@Override
	public long getScopeGroupId(
			HttpServletRequest httpServletRequest, String portletId)
		throws PortalException {

		return getScopeGroupId(httpServletRequest, portletId, false);
	}

	@Override
	public long getScopeGroupId(
			HttpServletRequest httpServletRequest, String portletId,
			boolean checkStagingGroup)
		throws PortalException {

		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long scopeGroupId = 0;

		if (layout != null) {
			Group group = layout.getGroup();

			long doAsGroupId = ParamUtil.getLong(
				httpServletRequest, "doAsGroupId");

			if (doAsGroupId <= 0) {
				HttpServletRequest originalHttpServletRequest =
					getOriginalServletRequest(httpServletRequest);

				doAsGroupId = ParamUtil.getLong(
					originalHttpServletRequest, "doAsGroupId");
			}

			Group doAsGroup = null;

			if (doAsGroupId > 0) {
				doAsGroup = GroupLocalServiceUtil.fetchGroup(doAsGroupId);
			}

			if (group.isControlPanel()) {
				if (doAsGroupId > 0) {
					scopeGroupId = doAsGroupId;
				}

				group = GroupLocalServiceUtil.fetchGroup(scopeGroupId);

				if ((group != null) && group.hasStagingGroup()) {
					try {
						Group stagingGroup =
							StagingUtil.getPermissionStagingGroup(group);

						scopeGroupId = stagingGroup.getGroupId();
					}
					catch (Exception exception) {
						if (_log.isDebugEnabled()) {
							_log.debug(exception);
						}
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

						scopeGroupId = _getScopeGroupId(
							themeDisplay, liveGroupLayout, portletId);
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
			scopeGroupId = _getScopeGroupId(themeDisplay, layout, portletId);
		}

		return scopeGroupId;
	}

	@Override
	public long getScopeGroupId(Layout layout) {
		if (layout == null) {
			return 0;
		}

		return layout.getGroupId();
	}

	@Override
	public long getScopeGroupId(Layout layout, String portletId) {
		return _getScopeGroupId(null, layout, portletId);
	}

	@Override
	public long getScopeGroupId(long plid) {
		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getLayout(plid);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return getScopeGroupId(layout);
	}

	@Override
	public long getScopeGroupId(PortletRequest portletRequest)
		throws PortalException {

		return getScopeGroupId(getHttpServletRequest(portletRequest));
	}

	@Override
	public User getSelectedUser(HttpServletRequest httpServletRequest)
		throws PortalException {

		return getSelectedUser(httpServletRequest, true);
	}

	@Override
	public User getSelectedUser(
			HttpServletRequest httpServletRequest, boolean checkPermission)
		throws PortalException {

		long userId = ParamUtil.getLong(httpServletRequest, "p_u_i_d");

		User user = null;

		try {
			if (checkPermission) {
				user = UserServiceUtil.getUserById(userId);
			}
			else {
				user = UserLocalServiceUtil.getUserById(userId);
			}
		}
		catch (NoSuchUserException noSuchUserException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchUserException);
			}
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

		Group siteGroup = getCurrentSiteGroup(groupId);

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

			groups.addAll(GroupLocalServiceUtil.getUserSitesGroups(userId));
		}

		// Ancestor sites and global site

		int sitesContentSharingWithChildrenEnabled = PrefsPropsUtil.getInteger(
			companyId, PropsKeys.SITES_CONTENT_SHARING_WITH_CHILDREN_ENABLED);

		if (sitesContentSharingWithChildrenEnabled !=
				Sites.CONTENT_SHARING_WITH_CHILDREN_DISABLED) {

			groups.addAll(getAncestorSiteGroups(groupId, true));
		}

		Iterator<Group> iterator = groups.iterator();

		while (iterator.hasNext()) {
			Group group = iterator.next();

			if (!StagingUtil.isGroupAccessible(group, siteGroup)) {
				iterator.remove();
			}
		}

		long[] groupIds = new long[groups.size()];

		int i = 0;

		for (Group group : groups) {
			groupIds[i++] = group.getGroupId();
		}

		return groupIds;
	}

	@Override
	public String getSiteAdminURL(
			String portalURL, Group group, String ppid,
			Map<String, String[]> params)
		throws PortalException {

		StringBundler sb = new StringBundler(7);

		sb.append(portalURL);
		sb.append(getPathFriendlyURLPrivateGroup());

		if ((group != null) && !group.isControlPanel()) {
			sb.append(group.getFriendlyURL());
			sb.append(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR);
		}

		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		if (params != null) {
			params = new LinkedHashMap<>(params);
		}
		else {
			params = new LinkedHashMap<>();
		}

		if (Validator.isNotNull(ppid)) {
			params.put("p_p_id", new String[] {ppid});
		}

		params.put("p_p_lifecycle", new String[] {"0"});
		params.put(
			"p_p_state", new String[] {WindowState.MAXIMIZED.toString()});
		params.put("p_p_mode", new String[] {PortletMode.VIEW.toString()});

		sb.append(HttpUtil.parameterMapToString(params, true));

		return sb.toString();
	}

	@Override
	public String getSiteAdminURL(
			ThemeDisplay themeDisplay, String ppid,
			Map<String, String[]> params)
		throws PortalException {

		return getSiteAdminURL(
			themeDisplay.getPortalURL(), themeDisplay.getScopeGroup(), ppid,
			params);
	}

	@Override
	public Locale getSiteDefaultLocale(Group group) throws PortalException {
		if (group == null) {
			return LocaleUtil.getDefault();
		}

		Group liveGroup = group;

		if (group.isStagingGroup()) {
			liveGroup = group.getLiveGroup();
		}

		if (LanguageUtil.isInheritLocales(liveGroup.getGroupId())) {
			Company company = CompanyLocalServiceUtil.getCompany(
				liveGroup.getCompanyId());

			return company.getLocale();
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			liveGroup.getTypeSettingsProperties();

		User defaultUser = UserLocalServiceUtil.getDefaultUser(
			group.getCompanyId());

		String languageId = GetterUtil.getString(
			typeSettingsUnicodeProperties.getProperty("languageId"),
			defaultUser.getLanguageId());

		return LocaleUtil.fromLanguageId(languageId);
	}

	@Override
	public Locale getSiteDefaultLocale(long groupId) throws PortalException {
		if (groupId <= 0) {
			return LocaleUtil.getDefault();
		}

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		return getSiteDefaultLocale(group);
	}

	@Override
	public long getSiteGroupId(long groupId) {
		Group group = _getSiteGroup(groupId);

		if (group == null) {
			return 0;
		}

		return group.getGroupId();
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
			String friendlyURL = themeDisplay.getLayoutFriendlyURL(layout);

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
	public String getStaticResourceURL(
		HttpServletRequest httpServletRequest, String uri) {

		return getStaticResourceURL(httpServletRequest, uri, null, 0);
	}

	@Override
	public String getStaticResourceURL(
		HttpServletRequest httpServletRequest, String uri, long timestamp) {

		return getStaticResourceURL(httpServletRequest, uri, null, timestamp);
	}

	@Override
	public String getStaticResourceURL(
		HttpServletRequest httpServletRequest, String uri, String queryString) {

		return getStaticResourceURL(httpServletRequest, uri, queryString, 0);
	}

	@Override
	public String getStaticResourceURL(
		HttpServletRequest httpServletRequest, String uri, String queryString,
		long timestamp) {

		if (uri.indexOf(CharPool.QUESTION) != -1) {
			return uri;
		}

		if (uri.startsWith(StringPool.DOUBLE_SLASH)) {
			uri = uri.substring(1);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Theme theme = themeDisplay.getTheme();

		Map<String, String[]> parameterMap = null;

		if (Validator.isNotNull(queryString)) {
			parameterMap = HttpUtil.getParameterMap(queryString);
		}

		StringBundler sb = new StringBundler(15);

		// URI

		sb.append(uri);

		boolean firstParam = true;

		// Browser id

		if ((parameterMap == null) || !parameterMap.containsKey("browserId")) {
			sb.append("?browserId=");
			sb.append(BrowserSnifferUtil.getBrowserId(httpServletRequest));

			firstParam = false;
		}

		// Theme and color scheme

		if ((uri.endsWith(".css") || uri.endsWith(".jsp")) &&
			((parameterMap == null) || !parameterMap.containsKey("themeId"))) {

			if (firstParam) {
				sb.append("?themeId=");

				firstParam = false;
			}
			else {
				sb.append("&themeId=");
			}

			sb.append(URLCodec.encodeURL(theme.getThemeId()));
		}

		if (uri.endsWith(".jsp") &&
			((parameterMap == null) ||
			 !parameterMap.containsKey("colorSchemeId"))) {

			if (firstParam) {
				sb.append("?colorSchemeId=");

				firstParam = false;
			}
			else {
				sb.append("&colorSchemeId=");
			}

			ColorScheme colorScheme = themeDisplay.getColorScheme();

			sb.append(URLCodec.encodeURL(colorScheme.getColorSchemeId()));
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
				if (firstParam) {
					sb.append("?minifierType=");

					firstParam = false;
				}
				else {
					sb.append("&minifierType=");
				}

				sb.append(minifierType);
			}
		}

		// Query string

		if (Validator.isNotNull(queryString)) {
			if (queryString.charAt(0) == CharPool.AMPERSAND) {
				if (firstParam) {
					sb.append(StringPool.QUESTION);
					sb.append(queryString.substring(1));
				}
				else {
					sb.append(queryString);
				}
			}
			else if (firstParam) {
				sb.append(StringPool.QUESTION);
				sb.append(queryString);
			}
			else {
				sb.append(StringPool.AMPERSAND);
				sb.append(queryString);
			}

			firstParam = false;
		}

		// Language id

		if (firstParam) {
			sb.append("?languageId=");
		}
		else {
			sb.append("&languageId=");
		}

		sb.append(themeDisplay.getLanguageId());

		// Timestamp

		if (((parameterMap == null) || !parameterMap.containsKey("t")) &&
			!(timestamp < 0)) {

			if (timestamp == 0) {
				String portalURL = getPortalURL(httpServletRequest);

				String path = uri;

				if (uri.startsWith(portalURL)) {
					path = uri.substring(portalURL.length());
				}

				if (path.startsWith(StrutsUtil.TEXT_HTML_DIR)) {
					ServletContext servletContext =
						(ServletContext)httpServletRequest.getAttribute(
							WebKeys.CTX);

					timestamp = ServletContextUtil.getLastModified(
						servletContext, path, true);
				}
				else {
					timestamp = PortalWebResourcesUtil.getPathLastModified(
						path, theme.getTimestamp());
				}
			}

			sb.append("&t=");
			sb.append(timestamp);
		}

		return sb.toString();
	}

	@Override
	public String getStrutsAction(HttpServletRequest httpServletRequest) {
		String strutsAction = ParamUtil.getString(
			httpServletRequest, "struts_action");

		if (Validator.isNotNull(strutsAction)) {

			// This method should only return a Struts action if you're dealing
			// with a regular HTTP servlet request, not a portlet HTTP servlet
			// request.

			return StringPool.BLANK;
		}

		return getPortletParam(httpServletRequest, "struts_action");
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
		HttpServletRequest httpServletRequest, String namespace,
		String elementId) {

		String uniqueElementId = elementId;

		Set<String> uniqueElementIds =
			(Set<String>)httpServletRequest.getAttribute(
				WebKeys.UNIQUE_ELEMENT_IDS);

		if (uniqueElementIds == null) {
			uniqueElementIds = Collections.newSetFromMap(
				new ConcurrentHashMap<>());

			httpServletRequest.setAttribute(
				WebKeys.UNIQUE_ELEMENT_IDS, uniqueElementIds);
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
					uniqueElementId = StringBundler.concat(
						elementId, StringPool.UNDERLINE, i);
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

		LiferayPortletRequest liferayPortletRequest =
			LiferayPortletUtil.getLiferayPortletRequest(portletRequest);

		DynamicServletRequest dynamicRequest =
			(DynamicServletRequest)
				liferayPortletRequest.getHttpServletRequest();

		HttpServletRequestWrapper requestWrapper =
			(HttpServletRequestWrapper)dynamicRequest.getRequest();

		return new UploadPortletRequestImpl(
			getUploadServletRequest(requestWrapper), liferayPortletRequest,
			getPortletNamespace(liferayPortletRequest.getPortletName()));
	}

	@Override
	public UploadServletRequest getUploadServletRequest(
		HttpServletRequest httpServletRequest) {

		return getUploadServletRequest(httpServletRequest, 0, null, 0, 0);
	}

	@Override
	public UploadServletRequest getUploadServletRequest(
		HttpServletRequest httpServletRequest, int fileSizeThreshold,
		String location, long maxRequestSize, long maxFileSize) {

		List<PersistentHttpServletRequestWrapper>
			persistentHttpServletRequestWrappers = new ArrayList<>();

		HttpServletRequest currentHttpServletRequest = httpServletRequest;

		while (currentHttpServletRequest instanceof HttpServletRequestWrapper) {
			if (currentHttpServletRequest instanceof UploadServletRequest) {
				return (UploadServletRequest)currentHttpServletRequest;
			}

			Class<?> currentRequestClass = currentHttpServletRequest.getClass();

			String currentRequestClassName = currentRequestClass.getName();

			if (!isUnwrapRequest(currentRequestClassName)) {
				break;
			}

			if (currentHttpServletRequest instanceof
					PersistentHttpServletRequestWrapper) {

				PersistentHttpServletRequestWrapper
					persistentHttpServletRequestWrapper =
						(PersistentHttpServletRequestWrapper)
							currentHttpServletRequest;

				persistentHttpServletRequestWrappers.add(
					persistentHttpServletRequestWrapper.clone());
			}

			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)currentHttpServletRequest;

			currentHttpServletRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		if (ServerDetector.isWebLogic()) {
			currentHttpServletRequest = new NonSerializableObjectRequestWrapper(
				currentHttpServletRequest);
		}

		for (int i = persistentHttpServletRequestWrappers.size() - 1; i >= 0;
			 i--) {

			HttpServletRequestWrapper httpServletRequestWrapper =
				persistentHttpServletRequestWrappers.get(i);

			httpServletRequestWrapper.setRequest(currentHttpServletRequest);

			currentHttpServletRequest = httpServletRequestWrapper;
		}

		return new UploadServletRequestImpl(
			currentHttpServletRequest, fileSizeThreshold, location,
			maxRequestSize, maxFileSize);
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

		// LPS-73785

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			HttpSession httpSession = PortalSessionContext.get(sessionId);

			if (httpSession != null) {
				while (httpSession instanceof HttpSessionWrapper) {
					HttpSessionWrapper httpSessionWrapper =
						(HttpSessionWrapper)httpSession;

					httpSession = httpSessionWrapper.getWrappedSession();
				}

				sessionId = httpSession.getId();
			}
		}

		x = url.indexOf(CharPool.QUESTION);

		if (x != -1) {
			return StringBundler.concat(
				url.substring(0, x), JSESSIONID, sessionId, url.substring(x));
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
	public User getUser(HttpServletRequest httpServletRequest)
		throws PortalException {

		User user = (User)httpServletRequest.getAttribute(WebKeys.USER);

		if (user != null) {
			return user;
		}

		long userId = getUserId(httpServletRequest);

		if (userId <= 0) {

			// Portlet WARs may have the correct remote user and not have the
			// correct user id because the user id is saved in the session and
			// may not be accessible by the portlet WAR's session. This behavior
			// is inconsistent across different application servers.

			String remoteUser = httpServletRequest.getRemoteUser();

			if ((remoteUser == null) && !PropsValues.PORTAL_JAAS_ENABLE) {
				HttpSession httpSession = httpServletRequest.getSession();

				remoteUser = (String)httpSession.getAttribute("j_remoteuser");
			}

			if (remoteUser == null) {
				return null;
			}

			if (PropsValues.PORTAL_JAAS_ENABLE) {
				long companyId = getCompanyId(httpServletRequest);

				try {
					userId = JAASHelper.getJaasUserId(companyId, remoteUser);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}
				}
			}
			else {
				userId = GetterUtil.getLong(remoteUser);
			}
		}

		if (userId > 0) {
			user = UserLocalServiceUtil.getUserById(userId);

			httpServletRequest.setAttribute(WebKeys.USER, user);
		}

		Cookie[] cookies = httpServletRequest.getCookies();

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
		catch (PortalException portalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return StringPool.BLANK;
		}
	}

	@Override
	public long getUserId(HttpServletRequest httpServletRequest) {
		Long userIdObj = (Long)httpServletRequest.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			return userIdObj.longValue();
		}

		String doAsUserIdString = ParamUtil.getString(
			httpServletRequest, "doAsUserId", null);

		if (doAsUserIdString != null) {
			String actionName = getPortletParam(
				httpServletRequest, "actionName");
			String mvcRenderCommandName = ParamUtil.getString(
				httpServletRequest, "mvcRenderCommandName");
			String path = GetterUtil.getString(
				httpServletRequest.getPathInfo());

			boolean alwaysAllowDoAsUser = false;

			if (actionName.equals("addFile") ||
				mvcRenderCommandName.equals(
					"/document_library/edit_file_entry") ||
				path.equals("/portal/session_click") ||
				isAlwaysAllowDoAsUser(
					actionName, mvcRenderCommandName, path,
					getStrutsAction(httpServletRequest))) {

				try {
					alwaysAllowDoAsUser = isAlwaysAllowDoAsUser(
						httpServletRequest);
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}

			if ((!PropsValues.PORTAL_JAAS_ENABLE &&
				 PropsValues.PORTAL_IMPERSONATION_ENABLE) ||
				alwaysAllowDoAsUser) {

				try {
					long doAsUserId = getDoAsUserId(
						httpServletRequest, doAsUserIdString,
						alwaysAllowDoAsUser);

					if (doAsUserId > 0) {
						if (_log.isDebugEnabled()) {
							_log.debug("Impersonating user " + doAsUserId);
						}

						return doAsUserId;
					}
				}
				catch (Exception exception) {
					_log.error(
						"Unable to impersonate user " + doAsUserIdString,
						exception);
				}
			}
		}

		HttpSession httpSession = httpServletRequest.getSession();

		userIdObj = (Long)httpSession.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			httpServletRequest.setAttribute(WebKeys.USER_ID, userIdObj);

			return userIdObj.longValue();
		}

		return 0;
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
		long userId, String defaultUserName,
		HttpServletRequest httpServletRequest) {

		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_FULL,
			httpServletRequest);
	}

	@Override
	public String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getUserName(userId, defaultUserName, userAttribute, null);
	}

	@Override
	public String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest httpServletRequest) {

		String userName = defaultUserName;

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			if (userAttribute.equals(UserAttributes.USER_NAME_FULL)) {
				userName = user.getFullName();
			}
			else {
				userName = user.getScreenName();
			}

			if (httpServletRequest != null) {
				Layout layout = (Layout)httpServletRequest.getAttribute(
					WebKeys.LAYOUT);

				userName = StringBundler.concat(
					"<a href=\"",
					PortletURLBuilder.create(
						PortletURLFactoryUtil.create(
							httpServletRequest, PortletKeys.DIRECTORY, layout,
							PortletRequest.RENDER_PHASE)
					).setParameter(
						"struts_action", "/directory/view_user"
					).setParameter(
						"p_u_i_d", user.getUserId()
					).setPortletMode(
						PortletMode.VIEW
					).setWindowState(
						WindowState.MAXIMIZED
					).buildString(),
					"\">", HtmlUtil.escape(userName), "</a>");
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return userName;
	}

	@Override
	public String getUserPassword(HttpServletRequest httpServletRequest) {
		httpServletRequest = getOriginalServletRequest(httpServletRequest);

		return getUserPassword(httpServletRequest.getSession());
	}

	@Override
	public String getUserPassword(HttpSession httpSession) {
		return (String)httpSession.getAttribute(WebKeys.USER_PASSWORD);
	}

	@Override
	public String getUserPassword(PortletRequest portletRequest) {
		return getUserPassword(getHttpServletRequest(portletRequest));
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
				StringBundler.concat(
					"Set the property \"", PropsKeys.VIRTUAL_HOSTS_VALID_HOSTS,
					"\" in portal.properties to allow \"", domain,
					"\" as a domain"));
		}

		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				getDefaultCompanyId());

			return company.getVirtualHostname();
		}
		catch (Exception exception) {
			_log.error("Unable to load default portal instance", exception);
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
	public TreeMap<String, String> getVirtualHostnames(LayoutSet layoutSet) {
		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (!virtualHostnames.isEmpty()) {
			return virtualHostnames;
		}

		String companyFallbackVirtualHostname =
			layoutSet.getCompanyFallbackVirtualHostname();

		if (Validator.isNull(companyFallbackVirtualHostname)) {
			return virtualHostnames;
		}

		return TreeMapBuilder.put(
			companyFallbackVirtualHostname, StringPool.BLANK
		).build();
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
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.TEAM$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.USER$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.USERGROUP$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.DOCUMENTLIBRARY.MODEL." +
				"DLFILEENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.DOCUMENTLIBRARY.MODEL." +
				"DLFOLDER$]",
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

		DB db = DBManagerUtil.getDB();

		_customSqlValues = ArrayUtil.toStringArray(
			new Object[] {
				getClassNameId(Group.class), getClassNameId(Layout.class),
				getClassNameId(Organization.class), getClassNameId(Role.class),
				getClassNameId(Team.class), getClassNameId(User.class),
				getClassNameId(UserGroup.class),
				getClassNameId(DLFileEntry.class),
				getClassNameId(DLFolder.class), ResourceConstants.SCOPE_COMPANY,
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
				db.getTemplateFalse(), db.getTemplateTrue()
			});
	}

	@Override
	public User initUser(HttpServletRequest httpServletRequest)
		throws Exception {

		User user = null;

		try {
			user = getUser(httpServletRequest);
		}
		catch (NoSuchUserException noSuchUserException) {
			if (_log.isWarnEnabled()) {
				_log.warn(noSuchUserException);
			}

			long userId = getUserId(httpServletRequest);

			if (userId > 0) {
				HttpSession httpSession = httpServletRequest.getSession();

				httpSession.invalidate();
			}

			throw noSuchUserException;
		}

		if (user != null) {
			return user;
		}

		Company company = getCompany(httpServletRequest);

		return company.getDefaultUser();
	}

	@Override
	public boolean isCDNDynamicResourcesEnabled(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		Company company = getCompany(httpServletRequest);

		return isCDNDynamicResourcesEnabled(company.getCompanyId());
	}

	@Override
	public boolean isCDNDynamicResourcesEnabled(long companyId) {
		try {
			return PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.CDN_DYNAMIC_RESOURCES_ENABLED,
				PropsValues.CDN_DYNAMIC_RESOURCES_ENABLED);
		}
		catch (SystemException systemException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(systemException);
			}
		}

		return PropsValues.CDN_DYNAMIC_RESOURCES_ENABLED;
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
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check control panel access permission",
					portalException);
			}
		}

		return false;
	}

	@Override
	public boolean isCustomPortletMode(PortletMode portletMode) {
		if (LiferayPortletMode.ABOUT.equals(portletMode) ||
			LiferayPortletMode.CONFIG.equals(portletMode) ||
			LiferayPortletMode.EDIT.equals(portletMode) ||
			LiferayPortletMode.EDIT_DEFAULTS.equals(portletMode) ||
			LiferayPortletMode.EDIT_GUEST.equals(portletMode) ||
			LiferayPortletMode.HELP.equals(portletMode) ||
			LiferayPortletMode.PREVIEW.equals(portletMode) ||
			LiferayPortletMode.PRINT.equals(portletMode) ||
			LiferayPortletMode.VIEW.equals(portletMode)) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isForwardedSecure(HttpServletRequest httpServletRequest) {
		if (PropsValues.WEB_SERVER_FORWARDED_PROTOCOL_ENABLED) {
			String forwardedProtocol = httpServletRequest.getHeader(
				PropsValues.WEB_SERVER_FORWARDED_PROTOCOL_HEADER);

			if (Validator.isNotNull(forwardedProtocol) &&
				Objects.equals(Http.HTTPS, forwardedProtocol)) {

				return true;
			}
		}

		return httpServletRequest.isSecure();
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
	public boolean isLoginRedirectRequired(
		HttpServletRequest httpServletRequest) {

		if ((PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
			 !httpServletRequest.isSecure()) ||
			SSOUtil.isLoginRedirectRequired(getCompanyId(httpServletRequest))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMethodGet(PortletRequest portletRequest) {
		HttpServletRequest httpServletRequest = getHttpServletRequest(
			portletRequest);

		String method = GetterUtil.getString(httpServletRequest.getMethod());

		if (StringUtil.equalsIgnoreCase(method, HttpMethods.GET)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMethodPost(PortletRequest portletRequest) {
		HttpServletRequest httpServletRequest = getHttpServletRequest(
			portletRequest);

		String method = GetterUtil.getString(httpServletRequest.getMethod());

		if (StringUtil.equalsIgnoreCase(method, HttpMethods.POST)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMultipartRequest(HttpServletRequest httpServletRequest) {
		String contentType = httpServletRequest.getHeader(
			HttpHeaders.CONTENT_TYPE);

		if ((contentType != null) &&
			contentType.startsWith(ContentTypes.MULTIPART_FORM_DATA)) {

			return true;
		}

		return false;
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
	public boolean isRightToLeft(HttpServletRequest httpServletRequest) {
		Locale locale = LocaleUtil.fromLanguageId(
			LanguageUtil.getLanguageId(httpServletRequest));

		String langDir = LanguageUtil.get(locale, LanguageConstants.KEY_DIR);

		return langDir.equals("rtl");
	}

	@Override
	public boolean isRSSFeedsEnabled() {
		return PropsValues.RSS_FEEDS_ENABLED;
	}

	@Override
	public boolean isSecure(HttpServletRequest httpServletRequest) {
		if (PropsValues.WEB_SERVER_FORWARDED_PROTOCOL_ENABLED) {
			return isForwardedSecure(httpServletRequest);
		}

		if (!PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS ||
			PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {

			return httpServletRequest.isSecure();
		}

		HttpSession httpSession = httpServletRequest.getSession();

		if (httpSession == null) {
			return httpServletRequest.isSecure();
		}

		Boolean httpsInitial = (Boolean)httpSession.getAttribute(
			WebKeys.HTTPS_INITIAL);

		if ((httpsInitial == null) || httpsInitial) {
			return httpServletRequest.isSecure();
		}

		return false;
	}

	@Override
	public boolean isSkipPortletContentRendering(
		Group group, LayoutTypePortlet layoutTypePortlet,
		PortletDisplay portletDisplay, String portletName) {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			group.getCompanyId(), portletDisplay.getId());

		if (portlet.isSystem()) {
			return false;
		}

		if (group.isLayoutPrototype() &&
			layoutTypePortlet.hasPortletId(portletDisplay.getId()) &&
			portletDisplay.isModeView() && !portletDisplay.isStatePopUp() &&
			!portletName.equals(PortletKeys.NESTED_PORTLETS)) {

			return true;
		}

		return false;
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

		return false;
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
	public boolean isValidResourceId(String resourceId) {
		if (Validator.isNull(resourceId)) {
			return true;
		}

		Matcher matcher = _bannedResourceIdPattern.matcher(resourceId);

		if (matcher.matches()) {
			return false;
		}

		int count = _PORTLET_RESOURCE_ID_URL_DECODE_COUNT;

		while ((count > 0) && resourceId.contains("%")) {
			resourceId = HttpUtil.decodePath(resourceId);

			if (Validator.isNull(resourceId)) {
				return false;
			}

			matcher = _bannedResourceIdPattern.matcher(resourceId);

			if (matcher.matches()) {
				return false;
			}

			count--;

			if (count == 0) {
				return false;
			}
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
		catch (Exception exception) {
			_log.error("Unable to clear cluster wide CDN hosts", exception);
		}
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
			Exception exception, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		sendError(0, exception, actionRequest, actionResponse);
	}

	@Override
	public void sendError(
			Exception exception, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		sendError(0, exception, httpServletRequest, httpServletResponse);
	}

	@Override
	public void sendError(
			int status, Exception exception, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		StringBundler sb = new StringBundler(7);

		sb.append(_pathMain);
		sb.append("/portal/status?status=");
		sb.append(status);
		sb.append("&exception=");

		Class<?> clazz = exception.getClass();

		sb.append(clazz.getName());

		sb.append("&previousURL=");
		sb.append(URLCodec.encodeURL(getCurrentURL(actionRequest)));

		actionResponse.sendRedirect(sb.toString());
	}

	@Override
	public void sendError(
			int status, Exception exception,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			String currentURL = (String)httpServletRequest.getAttribute(
				WebKeys.CURRENT_URL);

			_log.debug(
				StringBundler.concat(
					"Current URL ", currentURL, " generates exception: ",
					exception.getMessage()));
		}

		if (exception instanceof NoSuchImageException) {
			if (_webServerServletLog.isWarnEnabled()) {
				_webServerServletLog.warn(exception, exception);
			}
		}
		else if (exception instanceof PortalException) {
			if (_log.isDebugEnabled()) {
				if (exception instanceof NoSuchLayoutException ||
					exception instanceof PrincipalException) {

					String msg = exception.getMessage();

					if (Validator.isNotNull(msg)) {
						_log.debug(msg);
					}
				}
				else {
					_log.debug(exception);
				}
			}
		}
		else if (_log.isWarnEnabled()) {
			_log.warn(exception);
		}

		if (httpServletResponse.isCommitted()) {
			return;
		}

		if (status == 0) {
			if (exception instanceof PrincipalException) {
				status = HttpServletResponse.SC_FORBIDDEN;
			}
			else {
				Class<?> clazz = exception.getClass();

				String name = clazz.getName();

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

		if ((exception instanceof NoSuchGroupException) &&
			Validator.isNotNull(
				PropsValues.SITES_FRIENDLY_URL_PAGE_NOT_FOUND)) {

			redirect = _get18nErrorRedirect(
				httpServletRequest,
				PropsValues.SITES_FRIENDLY_URL_PAGE_NOT_FOUND);
		}
		else if ((exception instanceof NoSuchLayoutException) &&
				 Validator.isNotNull(
					 PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND)) {

			redirect = _get18nErrorRedirect(
				httpServletRequest,
				PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND);

			httpServletRequest.setAttribute(
				NoSuchLayoutException.class.getName(), Boolean.TRUE);
		}
		else if (PropsValues.LAYOUT_SHOW_HTTP_STATUS) {
			DynamicServletRequest dynamicRequest = new DynamicServletRequest(
				httpServletRequest);

			// Reset layout params or there will be an infinite loop

			dynamicRequest.setParameter("p_l_id", StringPool.BLANK);

			dynamicRequest.setParameter("groupId", StringPool.BLANK);
			dynamicRequest.setParameter("layoutId", StringPool.BLANK);
			dynamicRequest.setParameter("privateLayout", StringPool.BLANK);

			httpServletRequest = dynamicRequest;

			redirect = PATH_MAIN + "/portal/status";
		}

		if (Objects.equals(redirect, httpServletRequest.getRequestURI())) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to redirect to missing URI: " + redirect);
			}

			redirect = null;
		}

		if (Validator.isNotNull(redirect)) {
			HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

			if (httpSession == null) {
				httpSession = httpServletRequest.getSession();
			}

			httpServletResponse.setStatus(status);

			SessionErrors.add(httpSession, exception.getClass(), exception);

			ServletContext servletContext = httpSession.getServletContext();

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(
					httpServletRequest, httpServletResponse);
			}
		}
		else if (exception != null) {
			httpServletResponse.sendError(
				status,
				"A " + exception.getClass() +
					" error occurred while processing your request");
		}
		else {
			String currentURL = (String)httpServletRequest.getAttribute(
				WebKeys.CURRENT_URL);

			httpServletResponse.sendError(status, "Current URL " + currentURL);
		}
	}

	@Override
	public void sendRSSFeedsDisabledError(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		sendError(
			HttpServletResponse.SC_NOT_FOUND, new RSSFeedException(),
			httpServletRequest, httpServletResponse);
	}

	@Override
	public void sendRSSFeedsDisabledError(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException, ServletException {

		sendRSSFeedsDisabledError(
			getHttpServletRequest(portletRequest),
			getHttpServletResponse(portletResponse));
	}

	@Override
	public void setPageDescription(
		String description, HttpServletRequest httpServletRequest) {

		ListMergeable<String> descriptionListMergeable = new ListMergeable<>();

		descriptionListMergeable.add(description);

		httpServletRequest.setAttribute(
			WebKeys.PAGE_DESCRIPTION, descriptionListMergeable);
	}

	@Override
	public void setPageKeywords(
		String keywords, HttpServletRequest httpServletRequest) {

		httpServletRequest.removeAttribute(WebKeys.PAGE_KEYWORDS);

		addPageKeywords(keywords, httpServletRequest);
	}

	@Override
	public void setPageSubtitle(
		String subtitle, HttpServletRequest httpServletRequest) {

		ListMergeable<String> subtitleListMergeable = new ListMergeable<>();

		subtitleListMergeable.add(subtitle);

		httpServletRequest.setAttribute(
			WebKeys.PAGE_SUBTITLE, subtitleListMergeable);
	}

	@Override
	public void setPageTitle(
		String title, HttpServletRequest httpServletRequest) {

		ListMergeable<String> titleListMergeable = new ListMergeable<>();

		titleListMergeable.add(title);

		httpServletRequest.setAttribute(WebKeys.PAGE_TITLE, titleListMergeable);
	}

	@Override
	public void setPortalInetSocketAddresses(
		HttpServletRequest httpServletRequest) {

		boolean secure = httpServletRequest.isSecure();

		if ((secure && (_securePortalLocalInetSocketAddress.get() != null) &&
			 (_securePortalServerInetSocketAddress.get() != null)) ||
			(!secure && (_portalLocalInetSocketAddress.get() != null) &&
			 (_portalServerInetSocketAddress.get() != null))) {

			return;
		}

		InetAddress localInetAddress = null;
		InetAddress serverInetAddress = null;

		try {
			localInetAddress = InetAddress.getByName(
				httpServletRequest.getLocalAddr());
			serverInetAddress = InetAddress.getByName(
				httpServletRequest.getServerName());
		}
		catch (UnknownHostException unknownHostException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to resolve portal host", unknownHostException);
			}

			return;
		}

		InetSocketAddress localInetSocketAddress = new InetSocketAddress(
			localInetAddress, httpServletRequest.getLocalPort());
		InetSocketAddress serverInetSocketAddress = new InetSocketAddress(
			serverInetAddress, httpServletRequest.getServerPort());

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
			BaseModel<?> baseModel, boolean hasImage, byte[] bytes,
			String fieldName, long maxSize, int maxHeight, int maxWidth)
		throws PortalException {

		long imageId = BeanPropertiesUtil.getLong(baseModel, fieldName);

		if (!hasImage) {
			if (imageId > 0) {
				ImageLocalServiceUtil.deleteImage(imageId);

				BeanPropertiesUtil.setProperty(baseModel, fieldName, 0);
			}

			return;
		}

		if (ArrayUtil.isEmpty(bytes)) {
			return;
		}

		if ((maxSize > 0) && (bytes.length > maxSize)) {
			throw new ImageSizeException();
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
			catch (IOException ioException) {
				throw new ImageSizeException(ioException);
			}
		}

		Image image = null;

		if (imageId > 0) {
			image = ImageLocalServiceUtil.moveImage(imageId, bytes);
		}
		else {
			image = ImageLocalServiceUtil.updateImage(
				BeanPropertiesUtil.getLong(baseModel, "companyId"),
				CounterLocalServiceUtil.increment(), bytes);
		}

		BeanPropertiesUtil.setProperty(
			baseModel, fieldName, image.getImageId());
	}

	@Override
	public PortletMode updatePortletMode(
			String portletId, User user, Layout layout, PortletMode portletMode,
			HttpServletRequest httpServletRequest)
		throws PortalException {

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

			return PortletMode.VIEW;
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			getCompanyId(httpServletRequest), portletId);

		Set<String> allPortletModes = portlet.getAllPortletModes();

		if (!allPortletModes.contains(portletMode.toString())) {
			return PortletModeFactory.getPortletMode(null, 3);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		boolean updateLayout = false;

		if (portletMode.equals(LiferayPortletMode.ABOUT) &&
			!layoutType.hasModeAboutPortletId(portletId)) {

			layoutType.addModeAboutPortletId(portletId);

			updateLayout = true;
		}
		else if (portletMode.equals(LiferayPortletMode.CONFIG) &&
				 !layoutType.hasModeConfigPortletId(portletId) &&
				 PortletPermissionUtil.contains(
					 permissionChecker, getScopeGroupId(httpServletRequest),
					 layout, portlet, ActionKeys.CONFIGURATION)) {

			layoutType.addModeConfigPortletId(portletId);

			updateLayout = true;
		}
		else if (portletMode.equals(PortletMode.EDIT) &&
				 !layoutType.hasModeEditPortletId(portletId) &&
				 PortletPermissionUtil.contains(
					 permissionChecker, getScopeGroupId(httpServletRequest),
					 layout, portlet, ActionKeys.PREFERENCES)) {

			layoutType.addModeEditPortletId(portletId);

			updateLayout = true;
		}
		else if (portletMode.equals(LiferayPortletMode.EDIT_DEFAULTS) &&
				 !layoutType.hasModeEditDefaultsPortletId(portletId) &&
				 PortletPermissionUtil.contains(
					 permissionChecker, getScopeGroupId(httpServletRequest),
					 layout, portlet, ActionKeys.PREFERENCES)) {

			layoutType.addModeEditDefaultsPortletId(portletId);

			updateLayout = true;
		}
		else if (portletMode.equals(LiferayPortletMode.EDIT_GUEST) &&
				 !layoutType.hasModeEditGuestPortletId(portletId) &&
				 PortletPermissionUtil.contains(
					 permissionChecker, getScopeGroupId(httpServletRequest),
					 layout, portlet, ActionKeys.GUEST_PREFERENCES)) {

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
		else if (isCustomPortletMode(portletMode) &&
				 !layoutType.hasModeCustomPortletId(
					 portletId, portletMode.toString())) {

			layoutType.addModeCustomPortletId(
				portletId, portletMode.toString());

			updateLayout = true;
		}

		if (updateLayout &&
			PortletPermissionUtil.contains(
				permissionChecker, getScopeGroupId(httpServletRequest), layout,
				portlet, ActionKeys.VIEW)) {

			LayoutClone layoutClone = LayoutCloneFactory.getInstance();

			if (layoutClone != null) {
				layoutClone.update(
					httpServletRequest, layout.getPlid(),
					layout.getTypeSettings());
			}
		}

		return portletMode;
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
				redirectParam, URLCodec.encodeURL(oldPath),
				URLCodec.encodeURL(newPath));

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
		HttpServletRequest httpServletRequest) {

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

			if (updateLayout) {
				LayoutClone layoutClone = LayoutCloneFactory.getInstance();

				if (layoutClone != null) {
					layoutClone.update(
						httpServletRequest, layout.getPlid(),
						layout.getTypeSettings());
				}
			}
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setStateExclusive(
			windowState.equals(LiferayWindowState.EXCLUSIVE));
		themeDisplay.setStateMaximized(
			windowState.equals(WindowState.MAXIMIZED));
		themeDisplay.setStatePopUp(
			windowState.equals(LiferayWindowState.POP_UP));

		httpServletRequest.setAttribute(WebKeys.WINDOW_STATE, windowState);

		return windowState;
	}

	protected void addRootModelResource(
			long companyId, long groupId, String name)
		throws PortalException {

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		if ((group != null) && group.isStagingGroup()) {
			groupId = group.getLiveGroupId();
		}

		String primaryKey = String.valueOf(groupId);

		int count =
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				companyId, name, ResourceConstants.SCOPE_INDIVIDUAL,
				primaryKey);

		if (count > 0) {
			return;
		}

		ResourceLocalServiceUtil.addResources(
			companyId, groupId, 0, name, primaryKey, false, true, true);
	}

	protected long doGetPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		long scopeGroupId = groupId;

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isLayout()) {
				Layout scopeLayout = LayoutLocalServiceUtil.getLayout(
					group.getClassPK());

				groupId = scopeLayout.getGroupId();
			}

			String[] validLayoutTypes = {
				LayoutConstants.TYPE_CONTENT,
				LayoutConstants.TYPE_FULL_PAGE_APPLICATION,
				LayoutConstants.TYPE_PANEL, LayoutConstants.TYPE_PORTLET
			};

			for (String layoutType : validLayoutTypes) {
				List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
					groupId, privateLayout, layoutType);

				long plid = getPlidFromPortletId(
					layouts, portletId, scopeGroupId);

				if (plid != LayoutConstants.DEFAULT_PLID) {
					return plid;
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	protected List<Portlet> filterControlPanelPortlets(
		Set<Portlet> portlets, ThemeDisplay themeDisplay) {

		List<Portlet> filteredPortlets = new ArrayList<>(portlets);

		Iterator<Portlet> iterator = filteredPortlets.iterator();

		while (iterator.hasNext()) {
			Portlet portlet = iterator.next();

			try {
				if (!portlet.isActive() || portlet.isInstanceable() ||
					!PortletPermissionUtil.hasControlPanelAccessPermission(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getScopeGroupId(), portlet)) {

					iterator.remove();
				}
			}
			catch (Exception exception) {
				_log.error(exception);

				iterator.remove();
			}
		}

		return filteredPortlets;
	}

	protected Set<Group> getAncestorSiteGroups(
		long groupId, boolean checkContentSharingWithChildrenEnabled) {

		Group siteGroup = _getSiteGroup(groupId);

		if (siteGroup == null) {
			return Collections.emptySet();
		}

		Set<Group> groups = null;

		for (Group group : siteGroup.getAncestors()) {
			if (checkContentSharingWithChildrenEnabled &&
				!SitesUtil.isContentSharingWithChildrenEnabled(group)) {

				continue;
			}

			if (groups == null) {
				groups = new LinkedHashSet<>();
			}

			groups.add(group);
		}

		if (!siteGroup.isCompany()) {
			ThreadLocalCache<Group> threadLocalCache =
				ThreadLocalCacheManager.getThreadLocalCache(
					Lifecycle.REQUEST, Company.class.getName());

			String cacheKey = StringUtil.toHexString(siteGroup.getCompanyId());

			Group companyGroup = threadLocalCache.get(cacheKey);

			if (companyGroup == null) {
				companyGroup = GroupLocalServiceUtil.fetchCompanyGroup(
					siteGroup.getCompanyId());

				threadLocalCache.put(cacheKey, companyGroup);
			}

			if (companyGroup != null) {
				if (groups == null) {
					return Collections.singleton(companyGroup);
				}

				groups.add(companyGroup);

				return groups;
			}
		}

		if (groups == null) {
			return Collections.emptySet();
		}

		return groups;
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

	protected Layout getBrowsableLayout(Layout layout) {
		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				layout.getType());

		if (layoutTypeController.isBrowsable()) {
			return layout;
		}

		Layout browsableChildLayout = null;

		List<Layout> childLayouts = layout.getAllChildren();

		for (Layout childLayout : childLayouts) {
			LayoutTypeController childLayoutTypeController =
				LayoutTypeControllerTracker.getLayoutTypeController(
					childLayout.getType());

			if (childLayoutTypeController.isBrowsable()) {
				browsableChildLayout = childLayout;

				break;
			}
		}

		if (browsableChildLayout != null) {
			return browsableChildLayout;
		}

		long defaultPlid = LayoutLocalServiceUtil.getDefaultPlid(
			layout.getGroupId(), layout.isPrivateLayout());

		return LayoutLocalServiceUtil.fetchLayout(defaultPlid);
	}

	protected String getCanonicalDomain(
		TreeMap<String, String> virtualHostnames, String portalDomain,
		String defaultVirtualHostName) {

		if (Validator.isBlank(portalDomain) ||
			StringUtil.equalsIgnoreCase(portalDomain, defaultVirtualHostName) ||
			!virtualHostnames.containsKey(defaultVirtualHostName)) {

			return virtualHostnames.firstKey();
		}

		int pos = portalDomain.indexOf(CharPool.COLON);

		if (pos == -1) {
			return portalDomain;
		}

		return portalDomain.substring(0, pos);
	}

	protected String getContextPath(String contextPath) {
		contextPath = GetterUtil.getString(contextPath);

		if ((contextPath.length() == 0) ||
			contextPath.equals(StringPool.SLASH)) {

			contextPath = StringPool.BLANK;
		}
		else if (!contextPath.startsWith(StringPool.SLASH)) {
			contextPath = StringPool.SLASH.concat(contextPath);
		}

		return contextPath;
	}

	protected Group getControlPanelDisplayGroup(
		Group controlPanelGroup, Group scopeGroup, long doAsGroupId,
		String portletId) {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			controlPanelGroup.getCompanyId(), portletId);

		String portletCategory = portlet.getControlPanelEntryCategory();

		if (portletCategory.equals(PortletCategoryKeys.CONTROL_PANEL_APPS) ||
			portletCategory.equals(
				PortletCategoryKeys.CONTROL_PANEL_CONFIGURATION) ||
			portletCategory.equals(PortletCategoryKeys.CONTROL_PANEL_SITES) ||
			portletCategory.equals(PortletCategoryKeys.CONTROL_PANEL_SYSTEM) ||
			portletCategory.equals(PortletCategoryKeys.CONTROL_PANEL_USERS) ||
			portletCategory.equals(
				PortletCategoryKeys.CONTROL_PANEL_WORKFLOW) ||
			portletCategory.equals(PortletCategoryKeys.USER_MY_ACCOUNT)) {

			return controlPanelGroup;
		}

		if (doAsGroupId > 0) {
			Group doAsGroup = GroupLocalServiceUtil.fetchGroup(doAsGroupId);

			if (doAsGroup != null) {
				return doAsGroup;
			}
		}

		return scopeGroup;
	}

	protected Group getControlPanelDisplayGroup(
		long companyId, long scopeGroupId, long doAsGroupId, String portletId) {

		return getControlPanelDisplayGroup(
			GroupLocalServiceUtil.fetchGroup(
				companyId, GroupConstants.CONTROL_PANEL),
			GroupLocalServiceUtil.fetchGroup(scopeGroupId), doAsGroupId,
			portletId);
	}

	protected Group getCurrentSiteGroup(long groupId) throws PortalException {
		Group siteGroup = _getSiteGroup(groupId);

		if ((siteGroup != null) && !siteGroup.isLayoutPrototype()) {
			return siteGroup;
		}

		return null;
	}

	protected long getDoAsUserId(
			HttpServletRequest httpServletRequest, String doAsUserIdString,
			boolean alwaysAllowDoAsUser)
		throws Exception {

		if (Validator.isNull(doAsUserIdString)) {
			return 0;
		}

		HttpSession httpSession = httpServletRequest.getSession();

		Long realUserIdObj = (Long)httpSession.getAttribute(WebKeys.USER_ID);

		if (!alwaysAllowDoAsUser && (realUserIdObj == null)) {
			return 0;
		}

		long doAsUserId = GetterUtil.getLong(doAsUserIdString);

		if (doAsUserId == 0) {
			try {
				Company company = getCompany(httpServletRequest);

				doAsUserId = GetterUtil.getLong(
					Encryptor.decrypt(company.getKeyObj(), doAsUserIdString));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to impersonate " + doAsUserIdString +
							" because the string cannot be decrypted",
						exception);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to impersonate " + doAsUserIdString +
							" because the string cannot be decrypted");
				}

				return 0;
			}
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
			httpServletRequest.setAttribute(
				WebKeys.USER_ID, Long.valueOf(doAsUserId));

			return doAsUserId;
		}

		User doAsUser = UserLocalServiceUtil.getUserById(doAsUserId);

		User realUser = UserLocalServiceUtil.getUserById(
			realUserIdObj.longValue());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(realUser);

		if (doAsUser.isDefaultUser() ||
			UserPermissionUtil.contains(
				permissionChecker, doAsUserId, doAsUser.getOrganizationIds(),
				ActionKeys.IMPERSONATE)) {

			httpServletRequest.setAttribute(
				WebKeys.USER_ID, Long.valueOf(doAsUserId));

			return doAsUserId;
		}

		_log.error(
			StringBundler.concat(
				"User ", realUserIdObj,
				" does not have the permission to impersonate ", doAsUserId));

		return 0;
	}

	protected String getGroupFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay,
			boolean canonicalURL)
		throws PortalException {

		return getGroupFriendlyURL(
			layoutSet, themeDisplay, canonicalURL, false);
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

		return groupDefaultActions.toArray(new String[0]);
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

		return guestDefaultActions.toArray(new String[0]);
	}

	protected long getPlidFromPortletId(
		List<Layout> layouts, String portletId, long scopeGroupId) {

		for (Layout layout : layouts) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (getScopeGroupId(layout, portletId) != scopeGroupId) {
				continue;
			}

			for (Portlet portlet : layoutTypePortlet.getAllPortlets()) {
				if ((portletId.equals(portlet.getPortletId()) ||
					 portletId.equals(portlet.getRootPortletId())) &&
					!layout.isPortletEmbedded(portletId, layout.getGroupId()) &&
					!layoutTypePortlet.isPortletCustomizable(portletId)) {

					return layout.getPlid();
				}
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	protected String getPortletParam(
		HttpServletRequest httpServletRequest, String name) {

		String portletId1 = ParamUtil.getString(httpServletRequest, "p_p_id");

		if (Validator.isNull(portletId1)) {
			return StringPool.BLANK;
		}

		String value = null;

		int valueCount = 0;

		String keyName = StringPool.UNDERLINE.concat(name);

		Map<String, String[]> parameterMap =
			httpServletRequest.getParameterMap();

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

			String portletId2 = parameterName.substring(1, pos);

			if (portletId1.equals(portletId2)) {
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
		sb.append(themeDisplay.getLayoutFriendlyURL(layout));

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

	protected boolean isAlwaysAllowDoAsUser(
			HttpServletRequest httpServletRequest)
		throws Exception {

		String ticketKey = ParamUtil.getString(httpServletRequest, "ticketKey");

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
			String doAsUserIdString = ParamUtil.getString(
				httpServletRequest, "doAsUserId");

			if (Validator.isNotNull(doAsUserIdString)) {
				Company company = getCompany(httpServletRequest);

				doAsUserId = GetterUtil.getLong(
					Encryptor.decrypt(company.getKeyObj(), doAsUserIdString));
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

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
				(PropsValues.SESSION_TIMEOUT * Time.MINUTE));

		ticket.setExpirationDate(expirationDate);

		TicketLocalServiceUtil.updateTicket(ticket);

		return true;
	}

	protected boolean isAlwaysAllowDoAsUser(
		String actionName, String mvcRenderCommandName, String path,
		String strutsAction) {

		for (AlwaysAllowDoAsUser alwaysAllowDoAsUser : _alwaysAllowDoAsUsers) {
			Collection<String> actionNames =
				alwaysAllowDoAsUser.getActionNames();

			if (actionNames.contains(actionName)) {
				return true;
			}

			Collection<String> mvcRenderCommandNames =
				alwaysAllowDoAsUser.getMVCRenderCommandNames();

			if (mvcRenderCommandNames.contains(mvcRenderCommandName)) {
				return true;
			}

			Collection<String> paths = alwaysAllowDoAsUser.getPaths();

			if (paths.contains(path)) {
				return true;
			}

			Collection<String> strutsActions =
				alwaysAllowDoAsUser.getStrutsActions();

			if (strutsActions.contains(strutsAction)) {
				return true;
			}
		}

		return false;
	}

	protected boolean isUnwrapRequest(String currentRequestClassName) {
		for (String packageName : PropsValues.REQUEST_UNWRAP_PACKAGES) {
			if (currentRequestClassName.startsWith(packageName)) {
				return true;
			}
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

		if (StringUtil.equalsIgnoreCase(domain, PropsValues.WEB_SERVER_HOST) ||
			isValidVirtualHostname(domain) ||
			StringUtil.equalsIgnoreCase(domain, getCDNHostHttp(companyId)) ||
			StringUtil.equalsIgnoreCase(domain, getCDNHostHttps(companyId))) {

			return true;
		}

		return false;
	}

	protected boolean isValidPortalDomain(String domain) {
		return isValidPortalDomain(CompanyThreadLocal.getCompanyId(), domain);
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
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

	protected String removeRedirectParameter(String url) {
		Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
			HttpUtil.getQueryString(url));

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
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Locale locale) {

		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.setAttribute(WebKeys.LOCALE, locale);

		LanguageUtil.updateCookie(
			httpServletRequest, httpServletResponse, locale);
	}

	protected void setThemeDisplayI18n(
		ThemeDisplay themeDisplay, Locale locale) {

		String i18nPath = null;

		Set<String> languageIds = I18nFilter.getLanguageIds();

		if ((languageIds.contains(locale.toString()) &&
			 (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 1) &&
			 !locale.equals(LocaleUtil.getDefault())) ||
			(PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2)) {

			i18nPath = _buildI18NPath(locale, themeDisplay.getSiteGroup());
		}

		themeDisplay.setI18nLanguageId(locale.toString());
		themeDisplay.setI18nPath(i18nPath);
		themeDisplay.setLocale(locale);
	}

	private String _buildI18NPath(Locale locale, Group group) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return _buildI18NPath(languageId, locale, group);
	}

	private String _buildI18NPath(String languageId, Locale locale) {
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

	private String _buildI18NPath(
		String languageId, Locale locale, Group group) {

		if (Validator.isNull(languageId)) {
			return null;
		}

		Locale siteDefaultLocale = null;

		try {
			siteDefaultLocale = getSiteDefaultLocale(group);

			if (!LanguageUtil.isSameLanguage(locale, siteDefaultLocale)) {
				siteDefaultLocale = LanguageUtil.getLocale(
					group.getGroupId(), locale.getLanguage());
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get default locale from group: " +
						group.getGroupId() + ".  Using portal defaults.",
					exception);
			}

			siteDefaultLocale = LocaleUtil.getDefault();

			if (!LanguageUtil.isSameLanguage(locale, siteDefaultLocale)) {
				siteDefaultLocale = LanguageUtil.getLocale(
					locale.getLanguage());
			}
		}

		String siteDefaultLanguageId = LanguageUtil.getLanguageId(
			siteDefaultLocale);

		if (siteDefaultLanguageId.startsWith(languageId)) {
			languageId = siteDefaultLocale.getLanguage();
		}

		return StringPool.SLASH.concat(LocaleUtil.toW3cLanguageId(languageId));
	}

	private boolean _containsHostname(
		TreeMap<String, String> virtualHostnames, String portalDomain) {

		int pos = portalDomain.indexOf(CharPool.COLON);

		if (pos > 0) {
			portalDomain = portalDomain.substring(0, pos);
		}

		if (virtualHostnames.containsKey(portalDomain) ||
			(portalDomain.contains("xn--") &&
			 virtualHostnames.containsKey(IDN.toUnicode(portalDomain)))) {

			return true;
		}

		return false;
	}

	private String _get18nErrorRedirect(
		HttpServletRequest httpServletRequest, String redirect) {

		String i18nErrorPath = GetterUtil.getString(
			httpServletRequest.getAttribute(WebKeys.I18N_ERROR_PATH));

		if (Validator.isNull(i18nErrorPath)) {
			return redirect;
		}

		return i18nErrorPath.concat(redirect);
	}

	private Map<Locale, String> _getAlternateURLs(
			String canonicalURL, ThemeDisplay themeDisplay, Layout layout,
			Set<Locale> availableLocales)
		throws PortalException {

		String defaultVirtualHostName = _getDefaultVirtualHostName(
			themeDisplay.getCompany());
		String portalDomain = themeDisplay.getPortalDomain();

		TreeMap<String, String> virtualHostnames = getVirtualHostnames(
			themeDisplay.getLayoutSet());

		String virtualHostname = _getVirtualHostname(
			virtualHostnames, themeDisplay);

		if ((!Validator.isBlank(portalDomain) &&
			 !StringUtil.equalsIgnoreCase(
				 portalDomain, defaultVirtualHostName) &&
			 StringUtil.equalsIgnoreCase(
				 virtualHostname, defaultVirtualHostName)) ||
			virtualHostnames.containsKey(portalDomain)) {

			virtualHostname = portalDomain;
		}

		Map<Locale, String> alternateURLs = new HashMap<>();

		if (Validator.isNull(virtualHostname)) {
			for (Locale locale : availableLocales) {
				String i18nPath = _buildI18NPath(
					locale, themeDisplay.getSiteGroup());

				alternateURLs.put(
					locale,
					canonicalURL.replaceFirst(
						_PUBLIC_GROUP_SERVLET_MAPPING,
						i18nPath.concat(_PUBLIC_GROUP_SERVLET_MAPPING)));
			}

			return alternateURLs;
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
		}

		Locale siteDefaultLocale = getSiteDefaultLocale(layout.getGroupId());

		if ((pos <= 0) || (pos >= canonicalURL.length())) {
			for (Locale locale : availableLocales) {
				if (siteDefaultLocale.equals(locale) &&
					(PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE != 2)) {

					alternateURLs.put(locale, canonicalURL);
				}
				else {
					alternateURLs.put(
						locale,
						StringBundler.concat(
							canonicalURL,
							_buildI18NPath(locale, themeDisplay.getSiteGroup()),
							StringPool.SLASH));
				}
			}

			return alternateURLs;
		}

		boolean replaceFriendlyURL = true;

		String currentURL = canonicalURL.substring(pos);

		int[] friendlyURLIndex = getGroupFriendlyURLIndex(currentURL);

		if (friendlyURLIndex != null) {
			int y = friendlyURLIndex[1];

			currentURL = currentURL.substring(y);

			if (currentURL.equals(StringPool.SLASH)) {
				replaceFriendlyURL = false;
			}
		}

		List<LayoutFriendlyURL> layoutFriendlyURLs = null;

		String groupFriendlyURLPrefix = null;

		if (replaceFriendlyURL) {
			layoutFriendlyURLs =
				LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
					layout.getPlid());

			if (layout instanceof VirtualLayout) {
				VirtualLayout virtualLayout = (VirtualLayout)layout;

				layout = virtualLayout.getSourceLayout();

				Group group = layout.getGroup();

				groupFriendlyURLPrefix =
					VirtualLayoutConstants.CANONICAL_URL_SEPARATOR.concat(
						group.getFriendlyURL());
			}
		}

		String canonicalURLPrefix = canonicalURL.substring(0, pos);

		String canonicalURLSuffix = canonicalURL.substring(pos);

		if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2) {
			String i18nPath = _buildI18NPath(
				siteDefaultLocale, layout.getGroup());

			if (canonicalURLSuffix.startsWith(i18nPath)) {
				canonicalURLSuffix = canonicalURLSuffix.substring(
					i18nPath.length());
			}
		}

		for (Locale locale : availableLocales) {
			String alternateURL = canonicalURL;
			String alternateURLSuffix = canonicalURLSuffix;
			String languageId = LocaleUtil.toLanguageId(locale);

			if (replaceFriendlyURL) {
				String friendlyURL = null;

				for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
					if (!languageId.equals(layoutFriendlyURL.getLanguageId())) {
						continue;
					}

					friendlyURL = layoutFriendlyURL.getFriendlyURL();

					if (groupFriendlyURLPrefix != null) {
						friendlyURL = groupFriendlyURLPrefix.concat(
							friendlyURL);
					}

					break;
				}

				if (friendlyURL != null) {
					alternateURLSuffix = StringUtil.replaceFirst(
						alternateURLSuffix, layout.getFriendlyURL(),
						friendlyURL);
				}

				alternateURL = canonicalURLPrefix.concat(alternateURLSuffix);
			}

			if (siteDefaultLocale.equals(locale) &&
				(PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE != 2)) {

				alternateURLs.put(locale, alternateURL);
			}
			else {
				alternateURLs.put(
					locale,
					StringBundler.concat(
						canonicalURLPrefix,
						_buildI18NPath(
							languageId, locale, themeDisplay.getSiteGroup()),
						alternateURLSuffix));
			}
		}

		return alternateURLs;
	}

	private String _getDefaultVirtualHostName(Company company) {
		if ((company != null) &&
			Validator.isNotNull(company.getVirtualHostname())) {

			return company.getVirtualHostname();
		}

		return _LOCALHOST;
	}

	private String _getGroupFriendlyURL(
			Group group, LayoutSet layoutSet, ThemeDisplay themeDisplay,
			boolean canonicalURL, boolean controlPanel)
		throws PortalException {

		boolean privateLayoutSet = layoutSet.isPrivateLayout();

		String portalURL = themeDisplay.getPortalURL();

		boolean useGroupVirtualHostname = false;

		String defaultVirtualHostName = _LOCALHOST;

		Company company = themeDisplay.getCompany();

		if ((company != null) &&
			Validator.isNotNull(company.getVirtualHostname())) {

			defaultVirtualHostName = company.getVirtualHostname();
		}

		if (canonicalURL ||
			!StringUtil.equalsIgnoreCase(
				themeDisplay.getServerName(), defaultVirtualHostName)) {

			useGroupVirtualHostname = true;
		}

		long refererPlid = themeDisplay.getRefererPlid();

		if (refererPlid > 0) {
			Layout refererLayout = LayoutLocalServiceUtil.fetchLayout(
				refererPlid);

			if ((refererLayout != null) &&
				((refererLayout.getGroupId() != group.getGroupId()) ||
				 (refererLayout.isPrivateLayout() != privateLayoutSet))) {

				useGroupVirtualHostname = false;
			}
		}

		String portalDomain = themeDisplay.getPortalDomain();

		TreeMap<String, String> virtualHostnames = getVirtualHostnames(
			layoutSet);

		if (useGroupVirtualHostname) {
			if (!virtualHostnames.isEmpty() &&
				(canonicalURL ||
				 !virtualHostnames.containsKey(defaultVirtualHostName))) {

				if (!controlPanel || !privateLayoutSet) {
					if (canonicalURL) {
						String path = StringPool.BLANK;

						if (themeDisplay.isWidget()) {
							path = PropsValues.WIDGET_SERVLET_MAPPING;
						}

						if (!virtualHostnames.containsKey(
								defaultVirtualHostName) &&
							!_containsHostname(
								virtualHostnames, portalDomain)) {

							portalURL = getPortalURL(
								virtualHostnames.firstKey(),
								themeDisplay.getServerPort(),
								themeDisplay.isSecure());
						}

						return StringBundler.concat(
							portalURL, _pathContext, path);
					}

					if (_containsHostname(virtualHostnames, portalDomain)) {
						String path = StringPool.BLANK;

						if (themeDisplay.isWidget()) {
							path = PropsValues.WIDGET_SERVLET_MAPPING;
						}

						if (themeDisplay.isI18n()) {
							path = themeDisplay.getI18nPath();
						}

						return StringBundler.concat(
							portalURL, _pathContext, path);
					}
				}
			}
			else {
				if (canonicalURL ||
					((layoutSet.getGroupId() !=
						themeDisplay.getSiteGroupId()) &&
					 (group.getClassPK() != themeDisplay.getUserId()))) {

					if (group.isControlPanel() || controlPanel) {
						virtualHostnames = new TreeMap<>();

						String serverName = themeDisplay.getServerName();

						if (Validator.isNotNull(serverName) &&
							!serverName.equals(defaultVirtualHostName)) {

							virtualHostnames.put(serverName, StringPool.BLANK);
						}
						else {
							LayoutSet curLayoutSet =
								LayoutSetLocalServiceUtil.getLayoutSet(
									themeDisplay.getSiteGroupId(),
									privateLayoutSet);

							virtualHostnames =
								curLayoutSet.getVirtualHostnames();
						}
					}

					if (virtualHostnames.isEmpty() ||
						virtualHostnames.containsKey(defaultVirtualHostName)) {

						virtualHostnames = TreeMapBuilder.put(
							company::getVirtualHostname, StringPool.BLANK
						).build();
					}

					if (canonicalURL ||
						!virtualHostnames.containsKey(defaultVirtualHostName)) {

						String virtualHostname = getCanonicalDomain(
							virtualHostnames, portalDomain,
							defaultVirtualHostName);

						portalURL = getPortalURL(
							virtualHostname, themeDisplay.getServerPort(),
							themeDisplay.isSecure());
					}
				}
			}
		}

		StringBundler sb = new StringBundler(6);

		sb.append(portalURL);
		sb.append(_pathContext);

		if (themeDisplay.isI18n() && !canonicalURL &&
			LanguageUtil.isAvailableLocale(
				group.getGroupId(), themeDisplay.getI18nLanguageId())) {

			sb.append(themeDisplay.getI18nPath());
		}

		if (themeDisplay.isWidget()) {
			sb.append(PropsValues.WIDGET_SERVLET_MAPPING);
		}

		if (privateLayoutSet) {
			if (group.isUser()) {
				sb.append(_PRIVATE_USER_SERVLET_MAPPING);
			}
			else {
				sb.append(_PRIVATE_GROUP_SERVLET_MAPPING);
			}

			sb.append(group.getFriendlyURL());
		}
		else if (!StringUtil.equals(
					group.getGroupKey(),
					PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME) ||
				 (!StringUtil.equalsIgnoreCase(
					 portalDomain, defaultVirtualHostName) &&
				  !_containsHostname(virtualHostnames, portalDomain))) {

			sb.append(_PUBLIC_GROUP_SERVLET_MAPPING);
			sb.append(group.getFriendlyURL());
		}

		return sb.toString();
	}

	private String _getPortalURL(
		String serverName, int serverPort, boolean secure) {

		StringBundler sb = new StringBundler(4);

		boolean https = false;

		if (secure ||
			StringUtil.equalsIgnoreCase(
				Http.HTTPS, PropsValues.WEB_SERVER_PROTOCOL)) {

			https = true;
		}

		if (https) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		sb.append(serverName);

		if (!https) {
			if (PropsValues.WEB_SERVER_HTTP_PORT == -1) {
				if ((serverPort != -1) && (serverPort != Http.HTTP_PORT) &&
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
				if ((serverPort != -1) && (serverPort != Http.HTTP_PORT) &&
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

	private String _getPortletBaseResource(Portlet portlet) {
		for (String modelName :
				ResourceActionsUtil.getPortletModelResources(
					portlet.getRootPortletId())) {

			if (!modelName.contains(".model.")) {
				return modelName;
			}
		}

		return null;
	}

	private String _getPortletTitle(
		String rootPortletId, PortletConfig portletConfig, Locale locale) {

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		String portletTitle = LanguageUtil.get(
			resourceBundle,
			StringBundler.concat(
				JavaConstants.JAVAX_PORTLET_TITLE, StringPool.PERIOD,
				rootPortletId),
			null);

		if (Validator.isNull(portletTitle)) {
			portletTitle = LanguageUtil.get(
				resourceBundle, JavaConstants.JAVAX_PORTLET_TITLE);
		}

		return portletTitle;
	}

	private long _getScopeGroupId(
		ThemeDisplay themeDisplay, Layout layout, String portletId) {

		if (layout == null) {
			return 0;
		}

		if (Validator.isNull(portletId)) {
			return layout.getGroupId();
		}

		try {
			PortletPreferences portletSetup = null;

			if (themeDisplay == null) {
				portletSetup =
					PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
						layout, portletId);
			}
			else {
				portletSetup = themeDisplay.getStrictLayoutPortletSetup(
					layout, portletId);
			}

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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return layout.getGroupId();
		}
	}

	private Group _getSiteGroup(long groupId) {
		if (groupId <= 0) {
			return null;
		}

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		if (group.isLayout()) {
			return group.getParentGroup();
		}

		return group;
	}

	private String _getVirtualHostname(
		TreeMap<String, String> virtualHostnames, ThemeDisplay themeDisplay) {

		if (virtualHostnames.isEmpty()) {
			Company company = themeDisplay.getCompany();

			return company.getVirtualHostname();
		}

		return virtualHostnames.firstKey();
	}

	private boolean _requiresLayoutFriendlyURL(
		String siteGroupFriendlyURL, String layoutFriendlyURL,
		String groupFriendlyURL) {

		if (groupFriendlyURL.contains(
				_PUBLIC_GROUP_SERVLET_MAPPING + StringPool.SLASH)) {

			if (groupFriendlyURL.contains(
					StringBundler.concat(
						_PUBLIC_GROUP_SERVLET_MAPPING, siteGroupFriendlyURL,
						layoutFriendlyURL))) {

				return true;
			}
		}
		else if (groupFriendlyURL.contains(layoutFriendlyURL)) {
			return true;
		}

		return false;
	}

	private static final String _J_SECURITY_CHECK = "j_security_check";

	private static final String _LOCALHOST = "localhost";

	private static final Locale _NULL_LOCALE;

	private static final int _PORTLET_RESOURCE_ID_URL_DECODE_COUNT =
		GetterUtil.getInteger(
			PropsUtil.get("portlet.resource.id.url.decode.count"), 10);

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static final Log _log = LogFactoryUtil.getLog(PortalImpl.class);

	private static final Pattern _bannedResourceIdPattern = Pattern.compile(
		PropsValues.PORTLET_RESOURCE_ID_BANNED_PATHS_REGEXP,
		Pattern.CASE_INSENSITIVE);
	private static final Map<Long, String> _cdnHostHttpMap =
		new ConcurrentHashMap<>();
	private static final Map<Long, String> _cdnHostHttpsMap =
		new ConcurrentHashMap<>();
	private static final MethodHandler _resetCDNHostsMethodHandler =
		new MethodHandler(new MethodKey(PortalUtil.class, "resetCDNHosts"));
	private static final Date _upTime = new Date();
	private static final Log _webServerServletLog = LogFactoryUtil.getLog(
		WebServerServlet.class);

	static {
		Locale locale = LocaleUtil.getDefault();

		_NULL_LOCALE = (Locale)locale.clone();
	}

	private final String[] _allSystemGroups;
	private final String[] _allSystemOrganizationRoles;
	private final String[] _allSystemRoles;
	private final String[] _allSystemSiteRoles;
	private final List<AlwaysAllowDoAsUser> _alwaysAllowDoAsUsers =
		new ArrayList<>();
	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private final Set<String> _computerAddresses = new HashSet<>();
	private final String _computerName;
	private String[] _customSqlKeys;
	private String[] _customSqlValues;
	private volatile StrutsAction _editDiscussionStrutsAction;
	private volatile StrutsAction _getCommentsStrutsAction;
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
	private final AtomicReference<InetSocketAddress>
		_portalServerInetSocketAddress = new AtomicReference<>();
	private final Set<String> _reservedParams = new HashSet<>();
	private final AtomicReference<InetSocketAddress>
		_securePortalLocalInetSocketAddress = new AtomicReference<>();
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

			AlwaysAllowDoAsUser alwaysAllowDoAsUser = _bundleContext.getService(
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

			_bundleContext.ungetService(serviceReference);

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

	private class CommentsStrutsActionServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<StrutsAction, StrutsAction> {

		@Override
		public StrutsAction addingService(
			ServiceReference<StrutsAction> serviceReference) {

			String path = GetterUtil.getString(
				serviceReference.getProperty("path"));

			StrutsAction strutsAction = _bundleContext.getService(
				serviceReference);

			if (Objects.equals(path, "/portal/comment/discussion/edit")) {
				_editDiscussionStrutsAction = strutsAction;
			}
			else if (Objects.equals(
						path, "/portal/comment/discussion/get_comments")) {

				_getCommentsStrutsAction = strutsAction;
			}

			return strutsAction;
		}

		@Override
		public void modifiedService(
			ServiceReference<StrutsAction> serviceReference,
			StrutsAction strutsAction) {

			removedService(serviceReference, strutsAction);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<StrutsAction> serviceReference,
			StrutsAction strutsAction) {

			String path = GetterUtil.getString(
				serviceReference.getProperty("path"));

			if (Objects.equals(path, "/portal/comment/discussion/edit")) {
				_editDiscussionStrutsAction = null;
			}
			else if (Objects.equals(
						path, "/portal/comment/discussion/get_comments")) {

				_getCommentsStrutsAction = null;
			}

			_bundleContext.ungetService(serviceReference);
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

			PortalInetSocketAddressEventListener
				portalInetSocketAddressEventListener =
					_bundleContext.getService(serviceReference);

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

			_bundleContext.ungetService(serviceReference);

			removePortalInetSocketAddressEventListener(
				portalInetSocketAddressEventListener);
		}

	}

}
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

package com.liferay.portal.kernel.theme;

import aQute.bnd.annotation.ProviderType;

import com.liferay.admin.kernel.util.PortalMyAccountApplicationType;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.mobile.device.rules.kernel.MDRRuleGroupInstance;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.ThemeSetting;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneThreadLocal;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides general configuration methods for the portal, providing access to
 * the portal's pages, sites, themes, locales, URLs, and more. This class is an
 * information context object that holds data commonly referred to for various
 * kinds of front-end information.
 *
 * <p>
 * Liferay makes the <code>ThemeDisplay</code> available as a request attribute
 * and in various scripting and templating scopes. A typical way to obtain
 * <code>ThemeDisplay</code> is from a request:
 * </p>
 *
 * <p>
 * <pre>
 * <code>
 * themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
 * </code>
 * </pre>
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
@JSON
@ProviderType
public class ThemeDisplay
	implements Cloneable, Mergeable<ThemeDisplay>, Serializable {

	public ThemeDisplay() {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating new instance " + hashCode());
		}

		_portletDisplay.setThemeDisplay(this);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		ThemeDisplay themeDisplay = (ThemeDisplay)super.clone();

		PortletDisplay portletDisplay = new PortletDisplay();

		_portletDisplay.copyTo(portletDisplay);

		themeDisplay._portletDisplay = portletDisplay;

		portletDisplay.setThemeDisplay(themeDisplay);

		return themeDisplay;
	}

	public Account getAccount() {
		return _account;
	}

	/**
	 * Returns the content delivery network (CDN) base URL, or the current
	 * portal URL if the CDN base URL is <code>null</code>. The CDN base URL can
	 * be configured by setting the <code>cdn.host.http</code> or
	 * <code>cdn.host.https</code> property in a
	 * <code>portal-ext.properties</code> file.
	 *
	 * @return the CDN base URL, or the current portal URL if the CDN base URL
	 *         is <code>null</code>
	 */
	public String getCDNBaseURL() {
		if (_cdnBaseURL != null) {
			return _cdnBaseURL;
		}

		String host = getCDNHost();

		if (Validator.isNull(host)) {
			String portalURL = getPortalURL();

			try {
				portalURL = PortalUtil.getPortalURL(getLayout(), this);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			host = portalURL;
		}

		_cdnBaseURL = host;

		return _cdnBaseURL;
	}

	/**
	 * Returns the content delivery network (CDN) dynamic resources host, or the
	 * current portal URL if the CDN dynamic resources host is
	 * <code>null</code>. By setting the
	 * <code>cdn.dynamic.resources.enabled</code> property to <code>true</code>
	 * in a <code>portal-ext.properties</code> file, the CDN can be used for
	 * dynamic resources, like minified CSS and JS files.
	 *
	 * @return the CDN dynamic resources host, or the current portal URL if the
	 *         CDN dynamic resources host is <code>null</code>
	 */
	public String getCDNDynamicResourcesHost() {
		return _cdnDynamicResourcesHost;
	}

	public String getCDNHost() {
		return _cdnHost;
	}

	public ColorScheme getColorScheme() {
		return _colorScheme;
	}

	/**
	 * Returns the color scheme ID as defined in the theme's
	 * <code>liferay-look-and-feel.xml</code>.
	 *
	 * @return the color scheme ID as defined in the theme's
	 *         <code>liferay-look-and-feel.xml</code>
	 */
	public String getColorSchemeId() {
		return _colorScheme.getColorSchemeId();
	}

	/**
	 * Returns the portal instance bean.
	 *
	 * <p>
	 * Company is Liferay's technical name for a portal instance.
	 * <p>
	 *
	 * @return the portal instance bean
	 */
	public Company getCompany() {
		return _company;
	}

	public long getCompanyGroupId() {
		return _companyGroupId;
	}

	/**
	 * Returns the portal instance ID.
	 *
	 * <p>
	 * Company is Liferay's technical name for a portal instance.
	 * <p>
	 *
	 * @return the portal instance ID
	 */
	public long getCompanyId() {
		return _company.getCompanyId();
	}

	/**
	 * Returns the server's relative path to the portal instance's logo.
	 *
	 * <p>
	 * Company is Liferay's technical name for a portal instance.
	 * <p>
	 *
	 * @return the server's relative path to the portal instance's logo
	 */
	public String getCompanyLogo() {
		return _companyLogo;
	}

	/**
	 * Returns the height of the portal instance's logo in pixels.
	 *
	 * <p>
	 * Company is Liferay's technical name for a portal instance.
	 * <p>
	 *
	 * @return the height of the portal instance's logo in pixels
	 */
	public int getCompanyLogoHeight() {
		return _companyLogoHeight;
	}

	/**
	 * Returns the width of the portal instance's logo in pixels.
	 *
	 * <p>
	 * Company is Liferay's technical name for a portal instance.
	 * <p>
	 *
	 * @return the width of the portal instance's logo in pixels
	 */
	public int getCompanyLogoWidth() {
		return _companyLogoWidth;
	}

	public Contact getContact() {
		return _contact;
	}

	/**
	 * Returns the portal instance's default user.
	 *
	 * @return the portal instance's default user
	 */
	public User getDefaultUser() throws PortalException {
		if (_defaultUser == null) {
			_defaultUser = _company.getDefaultUser();
		}

		return _defaultUser;
	}

	/**
	 * Returns the ID of the portal instance's default user.
	 *
	 * @return the ID of the portal instance's default user
	 */
	public long getDefaultUserId() throws PortalException {
		return getDefaultUser().getUserId();
	}

	/**
	 * Returns the information about the detected device, such as the device's
	 * brand, browser, operating system, screen resolution, etc.
	 *
	 * @return the information about the detected device
	 */
	public Device getDevice() {
		return _device;
	}

	public long getDoAsGroupId() {
		return _doAsGroupId;
	}

	/**
	 * Returns the encrypted ID of the "do as" user, which can be used by an
	 * administrative user to impersonate another user, on that user's behalf.
	 *
	 * @return the encrypted ID of the "do as" user, which can be used by an
	 *         administrative user to impersonate another user, on that user's
	 *         behalf
	 */
	public String getDoAsUserId() {
		return _doAsUserId;
	}

	public String getDoAsUserLanguageId() {
		return _doAsUserLanguageId;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public String getFacebookCanvasPageURL() {
		return _facebookCanvasPageURL;
	}

	/**
	 * Returns the current internationalization language's code.
	 *
	 * <p>
	 * For example:
	 * </p>
	 *
	 * <p>
	 * English (U.K.) returns <code>en_GB</code>
	 * </p>
	 *
	 * @return the current internationalization language's code
	 */
	public String getI18nLanguageId() {
		return _i18nLanguageId;
	}

	/**
	 * Returns the path element for the current internationalization language.
	 *
	 * <p>
	 * For example, the German localization returns <code>/de</code>. Liferay's
	 * UI language can be changed by adding the language code into the URL path.
	 * The following URL uses the German localization:
	 * <code>http://localhost:8080/de/web/guest/home</code>.
	 * </p>
	 *
	 * @return the path element for the current internationalization language
	 */
	public String getI18nPath() {
		return _i18nPath;
	}

	/**
	 * Returns the current language's code.
	 *
	 * <p>
	 * For example:
	 * </p>
	 *
	 * <p>
	 * English (U.K.) returns <code>en_GB</code>
	 * </p>
	 *
	 * @return the current language's code
	 */
	public String getLanguageId() {
		return _languageId;
	}

	/**
	 * Returns the site's page.
	 *
	 * <p>
	 * Layout is Liferay's technical name for a page.
	 * </p>
	 *
	 * @return the site's page
	 */
	public Layout getLayout() {
		return _layout;
	}

	/**
	 * Returns the site's top-level pages.
	 *
	 * <p>
	 * Layout is Liferay's technical name for a page.
	 * </p>
	 *
	 * @return the site's top-level pages
	 */
	public List<Layout> getLayouts() {
		return _layouts;
	}

	/**
	 * Returns the current layout set, being either a public layout set or a
	 * private layout set.
	 *
	 * <p>
	 * A site can have public and private pages (layouts), which are contained
	 * in a public layout set and a private page set, respectively.
	 * </p>
	 *
	 * @return the current layout set, being either a public layout set or a
	 *         private layout set
	 */
	public LayoutSet getLayoutSet() {
		return _layoutSet;
	}

	/**
	 * Returns the path to the site's configured logo, or <code>null</code> if
	 * there is no configured logo.
	 *
	 * @return the path to the site's configured logo, or <code>null</code> if
	 *         there is no configured logo
	 */
	public String getLayoutSetLogo() {
		return _layoutSetLogo;
	}

	public LayoutTypePortlet getLayoutTypePortlet() {
		return _layoutTypePortlet;
	}

	/**
	 * Returns the numeric portlet lifecycle indicator.
	 *
	 * <p>
	 * For example:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * returns "0" for RENDER phase
	 * returns "1" for ACTION phase
	 * returns "2" for RESOURCE phase
	 * returns "3" for EVENT phase
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @return the numeric portlet lifecycle indicator
	 */
	public String getLifecycle() {
		return _lifecycle;
	}

	/**
	 * Returns the locale used for displaying content.
	 *
	 * @return the locale used for displaying content
	 */
	public Locale getLocale() {
		return _locale;
	}

	public MDRRuleGroupInstance getMDRRuleGroupInstance() {
		return _mdrRuleGroupInstance;
	}

	public String getPathApplet() {
		return _pathApplet;
	}

	public String getPathCms() {
		return _pathCms;
	}

	/**
	 * Returns the base URL for the color scheme's images, which can be
	 * configured in the theme's <code>liferay-look-and-feel.xml</code>.
	 *
	 * @return the base URL for the color scheme's images
	 */
	public String getPathColorSchemeImages() {
		return _pathColorSchemeImages;
	}

	public String getPathContext() {
		return _pathContext;
	}

	public String getPathFlash() {
		return _pathFlash;
	}

	/**
	 * Returns the URL for the site's private layout set. This method typically
	 * returns <code>/group</code>.
	 *
	 * @return the URL for the site's private layout set
	 */
	public String getPathFriendlyURLPrivateGroup() {
		return _pathFriendlyURLPrivateGroup;
	}

	/**
	 * Returns the URL for the user's private page set. This method typically
	 * returns <code>/user</code>.
	 *
	 * @return the URL for the user's private page set
	 */
	public String getPathFriendlyURLPrivateUser() {
		return _pathFriendlyURLPrivateUser;
	}

	/**
	 * Returns the URL for the site's public page set. This method typically
	 * returns <code>/web</code>.
	 *
	 * @return the URL for the site's public page set
	 */
	public String getPathFriendlyURLPublic() {
		return _pathFriendlyURLPublic;
	}

	/**
	 * Returns the URL for the portal instance's images. This method typically
	 * returns <code>/image</code>.
	 *
	 * @return the URL for the portal instance's images
	 */
	public String getPathImage() {
		return _pathImage;
	}

	/**
	 * Returns the URL for the portal instance's JavaScript resources.
	 *
	 * @return the URL for the portal instance's JavaScript resources
	 */
	public String getPathJavaScript() {
		return _pathJavaScript;
	}

	/**
	 * Returns the URL for the portal instance's main servlet. This method
	 * typically returns <code>/c</code>.
	 *
	 * @return the URL for the portal instance's main servlet
	 */
	public String getPathMain() {
		return _pathMain;
	}

	public String getPathSound() {
		return _pathSound;
	}

	/**
	 * Returns the URL for the theme's CSS directory.
	 *
	 * @return the URL for the theme's CSS directory
	 */
	public String getPathThemeCss() {
		return _pathThemeCss;
	}

	/**
	 * Returns the URL for the theme's images.
	 *
	 * @return the URL for the theme's images
	 */
	public String getPathThemeImages() {
		return _pathThemeImages;
	}

	/**
	 * Returns the URL for the theme's JavaScript directory.
	 *
	 * @return the URL for the theme's JavaScript directory
	 */
	public String getPathThemeJavaScript() {
		return _pathThemeJavaScript;
	}

	/**
	 * Returns the base URL for the theme.
	 *
	 * @return the base URL for the theme
	 */
	public String getPathThemeRoot() {
		return _pathThemeRoot;
	}

	/**
	 * Returns the URL for the theme's templates.
	 *
	 * @return the URL for the theme's templates
	 */
	public String getPathThemeTemplates() {
		return _pathThemeTemplates;
	}

	/**
	 * Returns the permission checker, which is used to ensure users making
	 * resource requests have the necessary access permissions.
	 *
	 * @return the permission checker
	 */
	@JSON(include = false)
	public PermissionChecker getPermissionChecker() {
		return _permissionChecker;
	}

	/**
	 * Returns the primary key of the page.
	 *
	 * <p>
	 * Historically, "plid" was short for "portlet layout ID", which is the
	 * primary key (ID) of the current layout (page).
	 * </p>
	 *
	 * @return the primary key of the page
	 */
	public long getPlid() {
		return _plid;
	}

	public String getPortalDomain() {
		return _portalDomain;
	}

	/**
	 * Returns the portal instance's base URL, which can be configured by
	 * setting the <code>web.server.host</code> property in a
	 * <code>portal-ext.properties</code> file.
	 *
	 * @return the portal instance's base URL
	 */
	public String getPortalURL() {
		return _portalURL;
	}

	@JSON(include = false)
	public PortletDisplay getPortletDisplay() {
		return _portletDisplay;
	}

	public String getPpid() {
		return _ppid;
	}

	public String getProtocol() {
		return HttpUtil.getProtocol(_secure);
	}

	public String getRealCompanyLogo() {
		return _realCompanyLogo;
	}

	public int getRealCompanyLogoHeight() {
		return _realCompanyLogoHeight;
	}

	public int getRealCompanyLogoWidth() {
		return _realCompanyLogoWidth;
	}

	/**
	 * Returns the logged in user. Since administrative users are able to
	 * impersonate other users, this method reveals the identity of the user who
	 * actually logged in.
	 *
	 * @return the logged in user
	 * @see    #getUser()
	 */
	public User getRealUser() {
		return _realUser;
	}

	/**
	 * Returns the ID of the logged in user.
	 *
	 * @return the ID of the logged in user
	 * @see    #getRealUser()
	 */
	public long getRealUserId() {
		return _realUser.getUserId();
	}

	public Group getRefererGroup() {
		return _refererGroup;
	}

	public long getRefererGroupId() {
		return _refererGroupId;
	}

	public long getRefererPlid() {
		return _refererPlid;
	}

	/**
	 * Returns the currently served HTTP servlet request.
	 *
	 * @return the currently served HTTP servlet request
	 */
	@JSON(include = false)
	public HttpServletRequest getRequest() {
		return _request;
	}

	/**
	 * Returns the currently served HTTP servlet response.
	 *
	 * @return the currently served HTTP servlet response
	 */
	@JSON(include = false)
	public HttpServletResponse getResponse() {
		return _response;
	}

	/**
	 * Returns the scoped or sub-scoped active group (e.g. site).
	 *
	 * @return the scoped or sub-scoped active group
	 */
	public Group getScopeGroup() {
		return _scopeGroup;
	}

	/**
	 * Returns the ID of the scoped or sub-scoped active group (e.g. site).
	 *
	 * @return the ID of the scoped or sub-scoped active group
	 */
	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	/**
	 * Returns the name of the scoped or sub-scoped active group (e.g. site).
	 *
	 * @return the name of the scoped or sub-scoped active group
	 */
	public String getScopeGroupName() throws PortalException {
		if (_scopeGroup == null) {
			return StringPool.BLANK;
		}

		return _scopeGroup.getDescriptiveName();
	}

	public Layout getScopeLayout() throws PortalException {
		if (_layout.hasScopeGroup()) {
			return _layout;
		}
		else if (_scopeGroup.isLayout()) {
			return LayoutLocalServiceUtil.getLayout(_scopeGroup.getClassPK());
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the portal instance's server name, which can be configured by
	 * setting the <code>web.server.host</code> property in a
	 * <code>portal-ext.properties</code> file.
	 *
	 * @return the server name, which can be configured by setting the
	 *         <code>web.server.host</code> property in a
	 *         <code>portal-ext.properties</code> file
	 */
	public String getServerName() {
		return _serverName;
	}

	/**
	 * Returns the server port, which can be configured by setting the
	 * <code>web.server.http.port</code> or <code>web.server.https.port</code>
	 * property in a <code>portal-ext.properties</code> file.
	 *
	 * @return the server port, which can be configured by setting the
	 *         <code>web.server.http.port</code> or
	 *         <code>web.server.https.port</code> property in a
	 *         <code>portal-ext.properties</code> file
	 */
	public int getServerPort() {
		return _serverPort;
	}

	/**
	 * Returns the session ID, or a blank string if the session ID is not
	 * available to the application.
	 *
	 * @return the session ID, or returns a blank string if the session ID is
	 *         not available to the application
	 */
	public String getSessionId() {
		return _sessionId;
	}

	public Locale getSiteDefaultLocale() {
		return _siteDefaultLocale;
	}

	public Group getSiteGroup() {
		return _siteGroup;
	}

	public long getSiteGroupId() {
		return _siteGroupId;
	}

	public long getSiteGroupIdOrLiveGroupId() {
		return StagingUtil.getLiveGroupId(_siteGroupId);
	}

	public String getSiteGroupName() throws PortalException {
		if (_siteGroup == null) {
			return StringPool.BLANK;
		}

		return _siteGroup.getDescriptiveName();
	}

	public Theme getTheme() {
		return _theme;
	}

	public String getThemeId() {
		return _theme.getThemeId();
	}

	/**
	 * Returns the theme's configurable settings, which are declared in
	 * <code>liferay-look-and-feel.xml</code> and are configurable in the user
	 * interface.
	 *
	 * @param  key the theme's key
	 * @return the theme's configurable settings
	 */
	public String getThemeSetting(String key) {
		Theme theme = getTheme();

		String device = theme.getDevice();

		Layout layout = getLayout();

		return layout.getThemeSetting(key, device);
	}

	/**
	 * Returns the theme's configurable settings, which are declared in
	 * <code>liferay-look-and-feel.xml</code> and are configurable in the user
	 * interface.
	 *
	 * @return a list of the theme's configurable settings
	 */
	public Properties getThemeSettings() {
		Theme theme = getTheme();

		Properties properties = new Properties();

		Map<String, ThemeSetting> themeSettings = theme.getSettings();

		for (Map.Entry<String, ThemeSetting> entry : themeSettings.entrySet()) {
			String key = entry.getKey();
			ThemeSetting themeSetting = entry.getValue();

			String value = null;

			if (themeSetting.isConfigurable()) {
				value = getThemeSetting(key);
			}
			else {
				value = themeSetting.getValue();
			}

			if (value != null) {
				properties.put(key, value);
			}
		}

		return properties;
	}

	public String getTilesContent() {
		return _tilesContent;
	}

	public String getTilesTitle() {
		return _tilesTitle;
	}

	public TimeZone getTimeZone() {
		return _timeZone;
	}

	public List<Layout> getUnfilteredLayouts() {
		return _unfilteredLayouts;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public String getURLAddContent() {
		return StringPool.BLANK;
	}

	public String getURLControlPanel() {
		return _urlControlPanel;
	}

	public String getURLCurrent() {
		return _urlCurrent;
	}

	public String getURLHome() {
		return _urlHome;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public String getURLLayoutTemplates() {
		if (Validator.isNull(_urlLayoutTemplates)) {
			return getURLPageSettings() + "#layout";
		}

		return _urlLayoutTemplates;
	}

	@JSON(include = false)
	public PortletURL getURLMyAccount() {
		if (_urlMyAccount == null) {
			String portletId = PortletProviderUtil.getPortletId(
				PortalMyAccountApplicationType.MyAccount.CLASS_NAME,
				PortletProvider.Action.VIEW);

			_urlMyAccount = PortalUtil.getControlPanelPortletURL(
				getRequest(), portletId, PortletRequest.RENDER_PHASE);
		}

		return _urlMyAccount;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@JSON(include = false)
	public PortletURL getURLPageSettings() {
		if (_urlPageSettings == null) {
			String portletId = PortletProviderUtil.getPortletId(
				Layout.class.getName(), PortletProvider.Action.EDIT);

			_urlPageSettings = PortalUtil.getControlPanelPortletURL(
				getRequest(), portletId, PortletRequest.RENDER_PHASE);
		}

		return _urlPageSettings;
	}

	public String getURLPortal() {
		return _urlPortal;
	}

	@JSON(include = false)
	public PortletURL getURLPublishToLive() {
		return _urlPublishToLive;
	}

	public String getURLSignIn() {
		return _urlSignIn;
	}

	public String getURLSignOut() {
		return _urlSignOut;
	}

	@JSON(include = false)
	public PortletURL getURLUpdateManager() {
		if (_urlUpdateManager == null) {
			_urlUpdateManager = PortalUtil.getControlPanelPortletURL(
				getRequest(), PortletKeys.MARKETPLACE_STORE,
				PortletRequest.RENDER_PHASE);
		}

		return _urlUpdateManager;
	}

	/**
	 * The user for which the current request is being handled. Note, that an
	 * administrative user can impersonate another user.
	 *
	 * @return the user for which the current request is being handled
	 * @see    #getRealUser()
	 */
	public User getUser() {
		return _user;
	}

	/**
	 * Returns the ID of the user for which the current request is being
	 * handled. Note that an administrative user can impersonate another user.
	 *
	 * @return the ID of the user for which the current request is being handled
	 */
	public long getUserId() {
		return _user.getUserId();
	}

	public boolean isAddSessionIdToURL() {
		return _addSessionIdToURL;
	}

	public boolean isAjax() {
		return _ajax;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public boolean isFacebook() {
		return false;
	}

	public boolean isFreeformLayout() {
		return _freeformLayout;
	}

	public boolean isI18n() {
		return _i18n;
	}

	/**
	 * Returns <code>true</code> if the user is being impersonated by an
	 * administrative user.
	 *
	 * @return <code>true</code> if the user is being impersonated by an
	 *         administrative user; <code>false</code> otherwise
	 * @see    #getRealUser()
	 * @see    #getUser()
	 */
	public boolean isImpersonated() {
		if (getUserId() == getRealUserId()) {
			return false;
		}

		return true;
	}

	public boolean isIncludedJs(String js) {
		String path = getPathJavaScript();

		if (isIncludePortletCssJs() &&
			js.startsWith(path + "/liferay/portlet_css.js")) {

			return true;
		}

		return false;
	}

	public boolean isIncludePortletCssJs() {
		return _includePortletCssJs;
	}

	public boolean isIsolated() {
		return _isolated;
	}

	public boolean isLifecycleAction() {
		return _lifecycleAction;
	}

	public boolean isLifecycleEvent() {
		return _lifecycleEvent;
	}

	public boolean isLifecycleRender() {
		return _lifecycleRender;
	}

	public boolean isLifecycleResource() {
		return _lifecycleResource;
	}

	public boolean isSecure() {
		return _secure;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public boolean isShowAddContentIcon() {
		return false;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public boolean isShowAddContentIconPermission() {
		return false;
	}

	public boolean isShowControlPanelIcon() {
		return _showControlPanelIcon;
	}

	public boolean isShowHomeIcon() {
		return _showHomeIcon;
	}

	public boolean isShowLayoutTemplatesIcon() {
		return _showLayoutTemplatesIcon;
	}

	public boolean isShowMyAccountIcon() {
		return _showMyAccountIcon;
	}

	public boolean isShowPageCustomizationIcon() {
		return _showPageCustomizationIcon;
	}

	public boolean isShowPageSettingsIcon() {
		return _showPageSettingsIcon;
	}

	public boolean isShowPortalIcon() {
		return _showPortalIcon;
	}

	public boolean isShowSignInIcon() {
		return _showSignInIcon;
	}

	public boolean isShowSignOutIcon() {
		return _showSignOutIcon;
	}

	public boolean isShowSiteAdministrationIcon() {
		return _showSiteAdministrationIcon;
	}

	public boolean isShowStagingIcon() {
		return _showStagingIcon;
	}

	public boolean isSignedIn() {
		return _signedIn;
	}

	public boolean isStateExclusive() {
		return _stateExclusive;
	}

	public boolean isStateMaximized() {
		return _stateMaximized;
	}

	public boolean isStatePopUp() {
		return _statePopUp;
	}

	public boolean isThemeCssFastLoad() {
		return _themeCssFastLoad;
	}

	public boolean isThemeImagesFastLoad() {
		return _themeImagesFastLoad;
	}

	public boolean isThemeJsBarebone() {
		return _themeJsBarebone;
	}

	public boolean isThemeJsFastLoad() {
		return _themeJsFastLoad;
	}

	public boolean isTilesSelectable() {
		return _tilesSelectable;
	}

	public boolean isWidget() {
		return _widget;
	}

	@Override
	public ThemeDisplay merge(ThemeDisplay themeDisplay) {
		if ((themeDisplay == null) || (themeDisplay == this)) {
			return this;
		}

		_includePortletCssJs = themeDisplay._includePortletCssJs;

		return this;
	}

	public void setAccount(Account account) {
		_account = account;
	}

	public void setAddSessionIdToURL(boolean addSessionIdToURL) {
		_addSessionIdToURL = addSessionIdToURL;
	}

	public void setAjax(boolean ajax) {
		_ajax = ajax;
	}

	public void setCDNBaseURL(String cdnBase) {
		_cdnBaseURL = cdnBase;
	}

	public void setCDNDynamicResourcesHost(String cdnDynamicResourcesHost) {
		_cdnDynamicResourcesHost = cdnDynamicResourcesHost;
	}

	public void setCDNHost(String cdnHost) {
		_cdnHost = cdnHost;
	}

	public void setCompany(Company company) throws PortalException {
		_company = company;
		_companyGroupId = company.getGroupId();

		setAccount(company.getAccount());
	}

	public void setCompanyLogo(String companyLogo) {
		_companyLogo = companyLogo;
	}

	public void setCompanyLogoHeight(int companyLogoHeight) {
		_companyLogoHeight = companyLogoHeight;
	}

	public void setCompanyLogoWidth(int companyLogoWidth) {
		_companyLogoWidth = companyLogoWidth;
	}

	public void setContact(Contact contact) {
		_contact = contact;
	}

	public void setDevice(Device device) {
		_device = device;
	}

	public void setDoAsGroupId(long doAsGroupId) {
		_doAsGroupId = doAsGroupId;
	}

	public void setDoAsUserId(String doAsUserId) {
		_doAsUserId = doAsUserId;
	}

	public void setDoAsUserLanguageId(String doAsUserLanguageId) {
		_doAsUserLanguageId = doAsUserLanguageId;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setFacebookCanvasPageURL(String facebookCanvasPageURL) {
		_facebookCanvasPageURL = facebookCanvasPageURL;
	}

	public void setFreeformLayout(boolean freeformLayout) {
		_freeformLayout = freeformLayout;
	}

	public void setI18nLanguageId(String i18nLanguageId) {
		_i18nLanguageId = i18nLanguageId;

		if (Validator.isNotNull(i18nLanguageId)) {
			_i18n = true;
		}
		else {
			_i18n = false;
		}
	}

	public void setI18nPath(String i18nPath) {
		_i18nPath = i18nPath;

		if (Validator.isNotNull(i18nPath)) {
			_i18n = true;
		}
		else {
			_i18n = false;
		}
	}

	public void setIncludePortletCssJs(boolean includePortletCssJs) {
		_includePortletCssJs = includePortletCssJs;
	}

	public void setIsolated(boolean isolated) {
		_isolated = isolated;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setLayout(Layout layout) {
		_layout = layout;
	}

	public void setLayouts(List<Layout> layouts) {
		_layouts = layouts;
	}

	public void setLayoutSet(LayoutSet layoutSet) {
		_layoutSet = layoutSet;
	}

	public void setLayoutSetLogo(String layoutSetLogo) {
		_layoutSetLogo = layoutSetLogo;
	}

	public void setLayoutTypePortlet(LayoutTypePortlet layoutTypePortlet) {
		_layoutTypePortlet = layoutTypePortlet;
	}

	public void setLifecycle(String lifecycle) {
		_lifecycle = lifecycle;
	}

	public void setLifecycleAction(boolean lifecycleAction) {
		_lifecycleAction = lifecycleAction;
	}

	public void setLifecycleEvent(boolean lifecycleEvent) {
		_lifecycleEvent = lifecycleEvent;
	}

	public void setLifecycleRender(boolean lifecycleRender) {
		_lifecycleRender = lifecycleRender;
	}

	public void setLifecycleResource(boolean lifecycleResource) {
		_lifecycleResource = lifecycleResource;
	}

	public void setLocale(Locale locale) {
		_locale = locale;

		LocaleThreadLocal.setThemeDisplayLocale(locale);
	}

	public void setLookAndFeel(Theme theme, ColorScheme colorScheme) {
		_theme = theme;
		_colorScheme = colorScheme;

		if ((theme == null) || (colorScheme == null)) {
			return;
		}

		String themeStaticResourcePath = theme.getStaticResourcePath();

		String cdnBaseURL = getCDNBaseURL();

		setPathColorSchemeImages(
			cdnBaseURL + themeStaticResourcePath +
				colorScheme.getColorSchemeImagesPath());

		String dynamicResourcesHost = getCDNDynamicResourcesHost();

		if (Validator.isNull(dynamicResourcesHost)) {
			String portalURL = getPortalURL();

			try {
				portalURL = PortalUtil.getPortalURL(getLayout(), this);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			dynamicResourcesHost = portalURL;
		}

		setPathThemeCss(
			dynamicResourcesHost + themeStaticResourcePath +
				theme.getCssPath());

		setPathThemeImages(
			cdnBaseURL + themeStaticResourcePath + theme.getImagesPath());
		setPathThemeJavaScript(
			cdnBaseURL + themeStaticResourcePath + theme.getJavaScriptPath());

		String rootPath = theme.getRootPath();

		if (rootPath.equals(StringPool.SLASH)) {
			setPathThemeRoot(themeStaticResourcePath);
		}
		else {
			setPathThemeRoot(themeStaticResourcePath + rootPath);
		}

		setPathThemeTemplates(
			cdnBaseURL + themeStaticResourcePath + theme.getTemplatesPath());
	}

	public void setMDRRuleGroupInstance(
		MDRRuleGroupInstance mdrRuleGroupInstance) {

		_mdrRuleGroupInstance = mdrRuleGroupInstance;
	}

	public void setPathApplet(String pathApplet) {
		_pathApplet = pathApplet;
	}

	public void setPathCms(String pathCms) {
		_pathCms = pathCms;
	}

	public void setPathColorSchemeImages(String pathColorSchemeImages) {
		_pathColorSchemeImages = pathColorSchemeImages;
	}

	public void setPathContext(String pathContext) {
		_pathContext = pathContext;
	}

	public void setPathFlash(String pathFlash) {
		_pathFlash = pathFlash;
	}

	public void setPathFriendlyURLPrivateGroup(
		String pathFriendlyURLPrivateGroup) {

		_pathFriendlyURLPrivateGroup = pathFriendlyURLPrivateGroup;
	}

	public void setPathFriendlyURLPrivateUser(
		String pathFriendlyURLPrivateUser) {

		_pathFriendlyURLPrivateUser = pathFriendlyURLPrivateUser;
	}

	public void setPathFriendlyURLPublic(String pathFriendlyURLPublic) {
		_pathFriendlyURLPublic = pathFriendlyURLPublic;
	}

	public void setPathImage(String pathImage) {
		_pathImage = pathImage;
	}

	public void setPathJavaScript(String pathJavaScript) {
		_pathJavaScript = pathJavaScript;
	}

	public void setPathMain(String pathMain) {
		_pathMain = pathMain;
	}

	public void setPathSound(String pathSound) {
		_pathSound = pathSound;
	}

	public void setPathThemeCss(String pathThemeCss) {
		_pathThemeCss = pathThemeCss;
	}

	public void setPathThemeImages(String pathThemeImages) {
		_pathThemeImages = pathThemeImages;
	}

	public void setPathThemeJavaScript(String pathThemeJavaScript) {
		_pathThemeJavaScript = pathThemeJavaScript;
	}

	public void setPathThemeRoot(String pathThemeRoot) {
		_pathThemeRoot = pathThemeRoot;
	}

	public void setPathThemeTemplates(String pathThemeTemplates) {
		_pathThemeTemplates = pathThemeTemplates;
	}

	public void setPermissionChecker(PermissionChecker permissionChecker) {
		_permissionChecker = permissionChecker;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public void setPortalDomain(String portalDomain) {
		_portalDomain = portalDomain;
	}

	public void setPortalURL(String portalURL) {
		_portalURL = portalURL;
	}

	public void setPpid(String ppid) {
		_ppid = ppid;
	}

	public void setRealCompanyLogo(String realCompanyLogo) {
		_realCompanyLogo = realCompanyLogo;
	}

	public void setRealCompanyLogoHeight(int realCompanyLogoHeight) {
		_realCompanyLogoHeight = realCompanyLogoHeight;
	}

	public void setRealCompanyLogoWidth(int realCompanyLogoWidth) {
		_realCompanyLogoWidth = realCompanyLogoWidth;
	}

	public void setRealUser(User realUser) {
		_realUser = realUser;
	}

	public void setRefererGroupId(long refererGroupId) {
		_refererGroupId = refererGroupId;

		if (_refererGroupId > 0) {
			try {
				_refererGroup = GroupLocalServiceUtil.getGroup(_refererGroupId);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void setRefererPlid(long refererPlid) {
		_refererPlid = refererPlid;
	}

	public void setRequest(HttpServletRequest request) {
		_request = request;
	}

	public void setResponse(HttpServletResponse response) {
		_response = response;
	}

	public void setScopeGroupId(long scopeGroupId) {
		_scopeGroupId = scopeGroupId;

		if (_scopeGroupId > 0) {
			try {
				_scopeGroup = GroupLocalServiceUtil.getGroup(_scopeGroupId);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void setSecure(boolean secure) {
		_secure = secure;
	}

	public void setServerName(String serverName) {
		_serverName = serverName;
	}

	public void setServerPort(int serverPort) {
		_serverPort = serverPort;
	}

	public void setSessionId(String sessionId) {
		_sessionId = sessionId;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setShowAddContentIcon(boolean showAddContentIcon) {
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setShowAddContentIconPermission(
		boolean showAddContentIconPermission) {
	}

	public void setShowControlPanelIcon(boolean showControlPanelIcon) {
		_showControlPanelIcon = showControlPanelIcon;
	}

	public void setShowHomeIcon(boolean showHomeIcon) {
		_showHomeIcon = showHomeIcon;
	}

	public void setShowLayoutTemplatesIcon(boolean showLayoutTemplatesIcon) {
		_showLayoutTemplatesIcon = showLayoutTemplatesIcon;
	}

	public void setShowMyAccountIcon(boolean showMyAccountIcon) {
		_showMyAccountIcon = showMyAccountIcon;
	}

	public void setShowPageCustomizationIcon(
		boolean showPageCustomizationIcon) {

		_showPageCustomizationIcon = showPageCustomizationIcon;
	}

	public void setShowPageSettingsIcon(boolean showPageSettingsIcon) {
		_showPageSettingsIcon = showPageSettingsIcon;
	}

	public void setShowPortalIcon(boolean showPortalIcon) {
		_showPortalIcon = showPortalIcon;
	}

	public void setShowSignInIcon(boolean showSignInIcon) {
		_showSignInIcon = showSignInIcon;
	}

	public void setShowSignOutIcon(boolean showSignOutIcon) {
		_showSignOutIcon = showSignOutIcon;
	}

	public void setShowSiteAdministrationIcon(
		boolean showSiteAdministrationIcon) {

		_showSiteAdministrationIcon = showSiteAdministrationIcon;
	}

	public void setShowStagingIcon(boolean showStagingIcon) {
		_showStagingIcon = showStagingIcon;
	}

	public void setSignedIn(boolean signedIn) {
		_signedIn = signedIn;
	}

	public void setSiteDefaultLocale(Locale siteDefaultLocale) {
		_siteDefaultLocale = siteDefaultLocale;

		LocaleThreadLocal.setSiteDefaultLocale(siteDefaultLocale);
	}

	public void setSiteGroupId(long siteGroupId) {
		_siteGroupId = siteGroupId;

		if (_siteGroupId > 0) {
			try {
				_siteGroup = GroupLocalServiceUtil.getGroup(_siteGroupId);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void setStateExclusive(boolean stateExclusive) {
		_stateExclusive = stateExclusive;
	}

	public void setStateMaximized(boolean stateMaximized) {
		_stateMaximized = stateMaximized;
	}

	public void setStatePopUp(boolean statePopUp) {
		_statePopUp = statePopUp;
	}

	public void setThemeCssFastLoad(boolean themeCssFastLoad) {
		_themeCssFastLoad = themeCssFastLoad;
	}

	public void setThemeImagesFastLoad(boolean themeImagesFastLoad) {
		_themeImagesFastLoad = themeImagesFastLoad;
	}

	public void setThemeJsBarebone(boolean themeJsBarebone) {
		_themeJsBarebone = themeJsBarebone;
	}

	public void setThemeJsFastLoad(boolean themeJsFastLoad) {
		_themeJsFastLoad = themeJsFastLoad;
	}

	public void setTilesContent(String tilesContent) {
		_tilesContent = tilesContent;
	}

	public void setTilesSelectable(boolean tilesSelectable) {
		_tilesSelectable = tilesSelectable;
	}

	public void setTilesTitle(String tilesTitle) {
		_tilesTitle = tilesTitle;
	}

	public void setTimeZone(TimeZone timeZone) {
		_timeZone = timeZone;

		TimeZoneThreadLocal.setThemeDisplayTimeZone(timeZone);
	}

	public void setUnfilteredLayouts(List<Layout> unfilteredLayouts) {
		_unfilteredLayouts = unfilteredLayouts;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setURLAddContent(String urlAddContent) {
	}

	public void setURLControlPanel(String urlControlPanel) {
		_urlControlPanel = urlControlPanel;
	}

	public void setURLCurrent(String urlCurrent) {
		_urlCurrent = urlCurrent;
	}

	public void setURLHome(String urlHome) {
		_urlHome = urlHome;
	}

	public void setURLLayoutTemplates(String urlLayoutTemplates) {
		_urlLayoutTemplates = urlLayoutTemplates;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setURLMyAccount(PortletURL urlMyAccount) {
		_urlMyAccount = urlMyAccount;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setURLPageSettings(PortletURL urlPageSettings) {
		_urlPageSettings = urlPageSettings;
	}

	public void setURLPortal(String urlPortal) {
		_urlPortal = urlPortal;
	}

	public void setURLPublishToLive(PortletURL urlPublishToLive) {
		_urlPublishToLive = urlPublishToLive;
	}

	public void setURLSignIn(String urlSignIn) {
		_urlSignIn = urlSignIn;
	}

	public void setURLSignOut(String urlSignOut) {
		_urlSignOut = urlSignOut;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setURLUpdateManager(PortletURL urlUpdateManager) {
		_urlUpdateManager = urlUpdateManager;
	}

	public void setUser(User user) throws PortalException {
		_user = user;

		setContact(user.getContact());
	}

	public void setWidget(boolean widget) {
		_widget = widget;
	}

	@Override
	public ThemeDisplay split() {
		try {
			return (ThemeDisplay)clone();
		}
		catch (CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
	}

	public String translate(String key) {
		return LanguageUtil.get(getLocale(), key);
	}

	public String translate(String pattern, Object... arguments) {
		return LanguageUtil.format(getLocale(), pattern, arguments);
	}

	private static final Log _log = LogFactoryUtil.getLog(ThemeDisplay.class);

	private Account _account;
	private boolean _addSessionIdToURL;
	private boolean _ajax;
	private String _cdnBaseURL;
	private String _cdnDynamicResourcesHost = StringPool.BLANK;
	private String _cdnHost = StringPool.BLANK;
	private ColorScheme _colorScheme;
	private Company _company;
	private long _companyGroupId;
	private String _companyLogo = StringPool.BLANK;
	private int _companyLogoHeight;
	private int _companyLogoWidth;
	private Contact _contact;
	private User _defaultUser;
	private Device _device;
	private long _doAsGroupId;
	private String _doAsUserId = StringPool.BLANK;
	private String _doAsUserLanguageId = StringPool.BLANK;

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	private String _facebookCanvasPageURL;

	private boolean _freeformLayout;
	private boolean _i18n;
	private String _i18nLanguageId;
	private String _i18nPath;
	private boolean _includePortletCssJs;
	private boolean _isolated;
	private String _languageId;
	private Layout _layout;
	private List<Layout> _layouts;
	private LayoutSet _layoutSet;
	private String _layoutSetLogo = StringPool.BLANK;
	private LayoutTypePortlet _layoutTypePortlet;
	private String _lifecycle;
	private boolean _lifecycleAction;
	private boolean _lifecycleEvent;
	private boolean _lifecycleRender;
	private boolean _lifecycleResource;
	private Locale _locale;
	private MDRRuleGroupInstance _mdrRuleGroupInstance;
	private String _pathApplet = StringPool.BLANK;
	private String _pathCms = StringPool.BLANK;
	private String _pathColorSchemeImages = StringPool.BLANK;
	private String _pathContext = StringPool.BLANK;
	private String _pathFlash = StringPool.BLANK;
	private String _pathFriendlyURLPrivateGroup = StringPool.BLANK;
	private String _pathFriendlyURLPrivateUser = StringPool.BLANK;
	private String _pathFriendlyURLPublic = StringPool.BLANK;
	private String _pathImage = StringPool.BLANK;
	private String _pathJavaScript = StringPool.BLANK;
	private String _pathMain = StringPool.BLANK;
	private String _pathSound = StringPool.BLANK;
	private String _pathThemeCss = StringPool.BLANK;
	private String _pathThemeImages = StringPool.BLANK;
	private String _pathThemeJavaScript = StringPool.BLANK;
	private String _pathThemeRoot = StringPool.BLANK;
	private String _pathThemeTemplates = StringPool.BLANK;
	private transient PermissionChecker _permissionChecker;
	private long _plid;
	private String _portalDomain = StringPool.BLANK;
	private String _portalURL = StringPool.BLANK;
	private PortletDisplay _portletDisplay = new PortletDisplay();
	private String _ppid = StringPool.BLANK;
	private String _realCompanyLogo = StringPool.BLANK;
	private int _realCompanyLogoHeight;
	private int _realCompanyLogoWidth;
	private User _realUser;
	private Group _refererGroup;
	private long _refererGroupId;
	private long _refererPlid;
	private transient HttpServletRequest _request;
	private transient HttpServletResponse _response;
	private Group _scopeGroup;
	private long _scopeGroupId;
	private boolean _secure;
	private String _serverName;
	private int _serverPort;
	private String _sessionId = StringPool.BLANK;
	private boolean _showControlPanelIcon;
	private boolean _showHomeIcon;
	private boolean _showLayoutTemplatesIcon;
	private boolean _showMyAccountIcon;
	private boolean _showPageCustomizationIcon;
	private boolean _showPageSettingsIcon;
	private boolean _showPortalIcon;
	private boolean _showSignInIcon;
	private boolean _showSignOutIcon;
	private boolean _showSiteAdministrationIcon;
	private boolean _showStagingIcon;
	private boolean _signedIn;
	private Locale _siteDefaultLocale;
	private Group _siteGroup;
	private long _siteGroupId;
	private boolean _stateExclusive;
	private boolean _stateMaximized;
	private boolean _statePopUp;
	private Theme _theme;
	private boolean _themeCssFastLoad;
	private boolean _themeImagesFastLoad;
	private boolean _themeJsBarebone;
	private boolean _themeJsFastLoad;
	private String _tilesContent = StringPool.BLANK;
	private boolean _tilesSelectable;
	private String _tilesTitle = StringPool.BLANK;
	private TimeZone _timeZone;
	private List<Layout> _unfilteredLayouts;
	private String _urlControlPanel = StringPool.BLANK;
	private String _urlCurrent = StringPool.BLANK;
	private String _urlHome = StringPool.BLANK;
	private String _urlLayoutTemplates = StringPool.BLANK;
	private transient PortletURL _urlMyAccount;
	private transient PortletURL _urlPageSettings;
	private String _urlPortal = StringPool.BLANK;
	private transient PortletURL _urlPublishToLive;
	private String _urlSignIn = StringPool.BLANK;
	private String _urlSignOut = StringPool.BLANK;
	private transient PortletURL _urlUpdateManager;
	private User _user;
	private boolean _widget;

}
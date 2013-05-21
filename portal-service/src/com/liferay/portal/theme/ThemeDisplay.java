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

package com.liferay.portal.theme;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.ThemeSetting;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
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

	public String getCDNBaseURL() {
		if (_cdnBaseURL != null) {
			return _cdnBaseURL;
		}

		String host = getCDNHost();

		String portalURL = getPortalURL();

		if (getServerName() != null) {
			try {
				portalURL = PortalUtil.getPortalURL(getLayout(), this);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (Validator.isNull(host)) {
			host = portalURL;
		}

		_cdnBaseURL = host;

		return _cdnBaseURL;
	}

	public String getCDNDynamicResourcesHost() {
		return _cdnDynamicResourcesHost;
	}

	public String getCDNHost() {
		return _cdnHost;
	}

	public ColorScheme getColorScheme() {
		return _colorScheme;
	}

	public String getColorSchemeId() {
		return _colorScheme.getColorSchemeId();
	}

	public Company getCompany() {
		return _company;
	}

	public long getCompanyGroupId() {
		return _companyGroupId;
	}

	public long getCompanyId() {
		return _company.getCompanyId();
	}

	public String getCompanyLogo() {
		return _companyLogo;
	}

	public int getCompanyLogoHeight() {
		return _companyLogoHeight;
	}

	public int getCompanyLogoWidth() {
		return _companyLogoWidth;
	}

	public Contact getContact() {
		return _contact;
	}

	public String getControlPanelCategory() {
		return _controlPanelCategory;
	}

	public User getDefaultUser() throws PortalException, SystemException {
		if (_defaultUser == null) {
			_defaultUser = _company.getDefaultUser();
		}

		return _defaultUser;
	}

	public long getDefaultUserId() throws PortalException, SystemException {
		return getDefaultUser().getUserId();
	}

	public Device getDevice() {
		return _device;
	}

	public long getDoAsGroupId() {
		return _doAsGroupId;
	}

	public String getDoAsUserId() {
		return _doAsUserId;
	}

	public String getDoAsUserLanguageId() {
		return _doAsUserLanguageId;
	}

	public String getFacebookCanvasPageURL() {
		return _facebookCanvasPageURL;
	}

	public String getI18nLanguageId() {
		return _i18nLanguageId;
	}

	public String getI18nPath() {
		return _i18nPath;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public Layout getLayout() {
		return _layout;
	}

	public List<Layout> getLayouts() {
		return _layouts;
	}

	public LayoutSet getLayoutSet() {
		return _layoutSet;
	}

	public String getLayoutSetLogo() {
		return _layoutSetLogo;
	}

	public LayoutTypePortlet getLayoutTypePortlet() {
		return _layoutTypePortlet;
	}

	public String getLifecycle() {
		return _lifecycle;
	}

	public Locale getLocale() {
		return _locale;
	}

	public MDRRuleGroupInstance getMDRRuleGroupInstance() {
		return _mdrRuleGroupInstance;
	}

	/**
	 * @deprecated As of 6.2 renamed to {@link #getSiteGroup}
	 */
	public Group getParentGroup() {
		return getSiteGroup();
	}

	/**
	 * @deprecated As of 6.2 renamed to {@link #getSiteGroupId}
	 */
	public long getParentGroupId() {
		return getSiteGroupId();
	}

	/**
	 * @deprecated As of 6.2 renamed to {@link #getSiteGroupName}
	 */
	public String getParentGroupName() throws PortalException, SystemException {
		return getSiteGroupName();
	}

	public String getPathApplet() {
		return _pathApplet;
	}

	public String getPathCms() {
		return _pathCms;
	}

	public String getPathColorSchemeImages() {
		return _pathColorSchemeImages;
	}

	public String getPathContext() {
		return _pathContext;
	}

	public String getPathFlash() {
		return _pathFlash;
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

	public String getPathJavaScript() {
		return _pathJavaScript;
	}

	public String getPathMain() {
		return _pathMain;
	}

	public String getPathSound() {
		return _pathSound;
	}

	public String getPathThemeCss() {
		return _pathThemeCss;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getPathThemeImages}
	 */
	public String getPathThemeImage() {
		return getPathThemeImages();
	}

	public String getPathThemeImages() {
		return _pathThemeImages;
	}

	public String getPathThemeJavaScript() {
		return _pathThemeJavaScript;
	}

	public String getPathThemeRoot() {
		return _pathThemeRoot;
	}

	public String getPathThemeTemplates() {
		return _pathThemeTemplates;
	}

	public PermissionChecker getPermissionChecker() {
		return _permissionChecker;
	}

	public long getPlid() {
		return _plid;
	}

	public String getPortalURL() {
		return _portalURL;
	}

	public PortletDisplay getPortletDisplay() {
		return _portletDisplay;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getScopeGroupId}
	 */
	public long getPortletGroupId() {
		return getScopeGroupId();
	}

	public String getPpid() {
		return _ppid;
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

	public User getRealUser() {
		return _realUser;
	}

	public long getRealUserId() {
		return _realUser.getUserId();
	}

	public long getRefererGroupId() {
		return _refererGroupId;
	}

	public long getRefererPlid() {
		return _refererPlid;
	}

	public HttpServletRequest getRequest() {
		return _request;
	}

	public Group getScopeGroup() {
		return _scopeGroup;
	}

	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	/**
	 * @deprecated As of 6.2 renamed to {@link #getSiteGroupIdOrLiveGroupId}
	 */
	public long getScopeGroupIdOrLiveGroupId()
		throws PortalException, SystemException {

		return getSiteGroupIdOrLiveGroupId();
	}

	public String getScopeGroupName() throws PortalException, SystemException {
		if (_scopeGroup == null) {
			return StringPool.BLANK;
		}
		else {
			return _scopeGroup.getDescriptiveName();
		}
	}

	public Layout getScopeLayout() throws PortalException, SystemException {
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

	public String getServerName() {
		return _serverName;
	}

	public int getServerPort() {
		return _serverPort;
	}

	public String getSessionId() {
		return _sessionId;
	}

	public Group getSiteGroup() {
		return _siteGroup;
	}

	public long getSiteGroupId() {
		return _siteGroupId;
	}

	public long getSiteGroupIdOrLiveGroupId()
		throws PortalException, SystemException {

		return StagingUtil.getLiveGroupId(_siteGroupId);
	}

	public String getSiteGroupName() throws PortalException, SystemException {
		if (_siteGroup == null) {
			return StringPool.BLANK;
		}
		else {
			return _siteGroup.getDescriptiveName();
		}
	}

	public Theme getTheme() {
		return _theme;
	}

	public String getThemeId() {
		return _theme.getThemeId();
	}

	public String getThemeSetting(String key) {
		Theme theme = getTheme();

		String device = theme.getDevice();

		Layout layout = getLayout();

		return layout.getThemeSetting(key, device);
	}

	public Properties getThemeSettings() {
		Theme theme = getTheme();

		Properties properties = new Properties();

		Map<String, ThemeSetting> themeSettings = theme.getSettings();

		for (String key : themeSettings.keySet()) {
			ThemeSetting themeSetting = themeSettings.get(key);

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

	public String getURLAddContent() {
		return _urlAddContent;
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

	public String getURLLayoutTemplates() {
		if (Validator.isNull(_urlLayoutTemplates)) {
			return _urlPageSettings + "#layout";
		}

		return _urlLayoutTemplates;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getURLSiteAdministration()}
	 */
	public PortletURL getURLManageSiteMemberships() {
		return _urlManageSiteMemberships;
	}

	public PortletURL getURLMyAccount() {
		return _urlMyAccount;
	}

	public PortletURL getURLPageSettings() {
		return _urlPageSettings;
	}

	public String getURLPortal() {
		return _urlPortal;
	}

	public PortletURL getURLPublishToLive() {
		return _urlPublishToLive;
	}

	public String getURLSignIn() {
		return _urlSignIn;
	}

	public String getURLSignOut() {
		return _urlSignOut;
	}

	public String getURLSiteAdministration() {
		return _urlSiteAdministration;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getURLSiteAdministration()}
	 */
	public String getURLSiteContent() {
		return getURLSiteAdministration();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #isShowSiteAdministrationIcon()}
	 */
	public PortletURL getURLSiteMapSettings() {
		return _urlSiteMapSettings;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getURLSiteAdministration()}
	 */
	public PortletURL getURLSiteSettings() {
		return _urlSiteSettings;
	}

	public PortletURL getURLUpdateManager() {
		return _urlUpdateManager;
	}

	public User getUser() {
		return _user;
	}

	public long getUserId() {
		return _user.getUserId();
	}

	public boolean isAddSessionIdToURL() {
		return _addSessionIdToURL;
	}

	public boolean isAjax() {
		return _ajax;
	}

	public boolean isFacebook() {
		return _facebook;
	}

	public boolean isFreeformLayout() {
		return _freeformLayout;
	}

	public boolean isI18n() {
		return _i18n;
	}

	public boolean isImpersonated() {
		if (getUserId() == getRealUserId()) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isIncludedJs(String js) {
		String path = getPathJavaScript();

		if (isIncludePortletCssJs() &&
			js.startsWith(path + "/liferay/portlet_css.js")) {

			return true;
		}
		else {
			return false;
		}
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

	public boolean isShowAddContentIcon() {
		return _showAddContentIcon;
	}

	public boolean isShowAddContentIconPermission() {
		return _showAddContentIconPermission;
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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #isShowSiteAdministrationIcon()}
	 */
	public boolean isShowManageSiteMembershipsIcon() {
		return _showManageSiteMembershipsIcon;
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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #isShowSiteAdministrationIcon()}
	 */
	public boolean isShowSiteContentIcon() {
		return isShowSiteAdministrationIcon();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #isShowSiteAdministrationIcon()}
	 */
	public boolean isShowSiteMapSettingsIcon() {
		return _showSiteMapSettingsIcon;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #isShowSiteAdministrationIcon()}
	 */
	public boolean isShowSiteSettingsIcon() {
		return _showSiteSettingsIcon;
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

	public boolean isWapTheme() {
		return _theme.isWapTheme();
	}

	public boolean isWidget() {
		return _widget;
	}

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

	public void setCompany(Company company)
		throws PortalException, SystemException {

		_company = company;
		_companyGroupId = company.getGroup().getGroupId();

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

	public void setControlPanelCategory(String controlPanelCategory) {
		_controlPanelCategory = controlPanelCategory;
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

	public void setFacebookCanvasPageURL(String facebookCanvasPageURL) {
		_facebookCanvasPageURL = facebookCanvasPageURL;

		if (Validator.isNotNull(facebookCanvasPageURL)) {
			_facebook = true;
		}
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

			if (getServerName() != null) {
				try {
					portalURL = PortalUtil.getPortalURL(getLayout(), this);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			dynamicResourcesHost = portalURL;
		}

		setPathThemeCss(
			dynamicResourcesHost + themeStaticResourcePath +
				theme.getCssPath());

		setPathThemeImages(
			cdnBaseURL + themeStaticResourcePath + theme.getImagesPath());
		setPathThemeJavaScript(
			cdnBaseURL + themeStaticResourcePath +
				theme.getJavaScriptPath());

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

	/**
	 * @deprecated As of 6.2 renamed to {@link #setSiteGroupId(long)}
	 */
	public void setParentGroupId(long parentGroupId) {
		setSiteGroupId(parentGroupId);
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
		if (isFacebook() &&
			!pathImage.startsWith(Http.HTTP_WITH_SLASH) &&
			!pathImage.startsWith(Http.HTTPS_WITH_SLASH)) {

			pathImage = getPortalURL() + pathImage;
		}

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
	}

	public void setRefererPlid(long refererPlid) {
		_refererPlid = refererPlid;
	}

	public void setRequest(HttpServletRequest request) {
		_request = request;
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

	public void setShowAddContentIcon(boolean showAddContentIcon) {
		_showAddContentIcon = showAddContentIcon;
	}

	public void setShowAddContentIconPermission(
		boolean showAddContentIconPermission) {

		_showAddContentIconPermission = showAddContentIconPermission;
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

	public void setShowManageSiteMembershipsIcon(
		boolean showManageSiteMembershipsIcon) {

		_showManageSiteMembershipsIcon = showManageSiteMembershipsIcon;
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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #setShowSiteAdministrationIcon(boolean)}
	 */
	public void setShowSiteContentIcon(boolean showSiteContentIcon) {
		setShowSiteAdministrationIcon(showSiteContentIcon);
	}

	public void setShowSiteMapSettingsIcon(boolean showSiteMapSettingsIcon) {
		_showSiteMapSettingsIcon = showSiteMapSettingsIcon;
	}

	public void setShowSiteSettingsIcon(boolean showSiteSettingsIcon) {
		_showSiteSettingsIcon = showSiteSettingsIcon;
	}

	public void setShowStagingIcon(boolean showStagingIcon) {
		_showStagingIcon = showStagingIcon;
	}

	public void setSignedIn(boolean signedIn) {
		_signedIn = signedIn;
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

	public void setURLAddContent(String urlAddContent) {
		_urlAddContent = urlAddContent;
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

	public void setURLManageSiteMemberships(
		PortletURL urlManageSiteMemberships) {

		_urlManageSiteMemberships = urlManageSiteMemberships;
	}

	public void setURLMyAccount(PortletURL urlMyAccount) {
		_urlMyAccount = urlMyAccount;
	}

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

	public void setURLSiteAdministration(String urlSiteAdministration) {
		_urlSiteAdministration = urlSiteAdministration;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #setURLSiteAdministration(String)}
	 */
	public void setURLSiteContent(String urlSiteContent) {
		setURLSiteAdministration(urlSiteContent);
	}

	public void setURLSiteMapSettings(PortletURL urlSiteMapSettings) {
		_urlSiteMapSettings = urlSiteMapSettings;
	}

	public void setURLSiteSettings(PortletURL urlSiteSettings) {
		_urlSiteSettings = urlSiteSettings;
	}

	public void setURLUpdateManager(PortletURL urlUpdateManager) {
		_urlUpdateManager = urlUpdateManager;
	}

	public void setUser(User user) throws PortalException, SystemException {
		_user = user;

		setContact(user.getContact());
	}

	public void setWidget(boolean widget) {
		_widget = widget;
	}

	public String translate(String key) {
		return LanguageUtil.get(getLocale(), key);
	}

	public String translate(String pattern, Object argument) {
		return LanguageUtil.format(getLocale(), pattern, argument);
	}

	public String translate(String pattern, Object[] arguments) {
		return LanguageUtil.format(getLocale(), pattern, arguments);
	}

	private static Log _log = LogFactoryUtil.getLog(ThemeDisplay.class);

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
	private String _controlPanelCategory = StringPool.BLANK;
	private User _defaultUser;
	private Device _device;
	private long _doAsGroupId = 0;
	private String _doAsUserId = StringPool.BLANK;
	private String _doAsUserLanguageId = StringPool.BLANK;
	private boolean _facebook;
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
	private String _portalURL = StringPool.BLANK;
	private PortletDisplay _portletDisplay = new PortletDisplay();
	private String _ppid = StringPool.BLANK;
	private String _realCompanyLogo = StringPool.BLANK;
	private int _realCompanyLogoHeight;
	private int _realCompanyLogoWidth;
	private User _realUser;
	private long _refererGroupId;
	private long _refererPlid;
	private transient HttpServletRequest _request;
	private Group _scopeGroup;
	private long _scopeGroupId;
	private boolean _secure;
	private String _serverName;
	private int _serverPort;
	private String _sessionId = StringPool.BLANK;
	private boolean _showAddContentIcon;
	private boolean _showAddContentIconPermission;
	private boolean _showControlPanelIcon;
	private boolean _showHomeIcon;
	private boolean _showLayoutTemplatesIcon;
	private boolean _showManageSiteMembershipsIcon;
	private boolean _showMyAccountIcon;
	private boolean _showPageCustomizationIcon;
	private boolean _showPageSettingsIcon;
	private boolean _showPortalIcon;
	private boolean _showSignInIcon;
	private boolean _showSignOutIcon;
	private boolean _showSiteAdministrationIcon;
	private boolean _showSiteMapSettingsIcon;
	private boolean _showSiteSettingsIcon;
	private boolean _showStagingIcon;
	private boolean _signedIn;
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
	private String _urlAddContent = StringPool.BLANK;
	private String _urlControlPanel = StringPool.BLANK;
	private String _urlCurrent = StringPool.BLANK;
	private String _urlHome = StringPool.BLANK;
	private String _urlLayoutTemplates = StringPool.BLANK;
	private transient PortletURL _urlManageSiteMemberships = null;
	private transient PortletURL _urlMyAccount = null;
	private transient PortletURL _urlPageSettings = null;
	private String _urlPortal = StringPool.BLANK;
	private transient PortletURL _urlPublishToLive = null;
	private String _urlSignIn = StringPool.BLANK;
	private String _urlSignOut = StringPool.BLANK;
	private String _urlSiteAdministration = StringPool.BLANK;
	private transient PortletURL _urlSiteMapSettings = null;
	private transient PortletURL _urlSiteSettings = null;
	private transient PortletURL _urlUpdateManager = null;
	private User _user;
	private boolean _widget;

}
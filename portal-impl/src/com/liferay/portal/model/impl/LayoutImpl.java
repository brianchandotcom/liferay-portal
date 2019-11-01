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

package com.liferay.portal.model.impl;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.PortletWrapper;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LayoutTypePortletFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.LayoutClone;
import com.liferay.portal.util.LayoutCloneFactory;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Represents a portal layout, providing access to the layout's URLs, parent
 * layouts, child layouts, theme settings, type settings, and more.
 *
 * <p>
 * The UI name for a layout is "page." Thus, a layout represents a page in the
 * portal. A single page is either part of the public or private layout set of a
 * group (site). Layouts can be organized hierarchically and are summarized in a
 * {@link LayoutSet}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class LayoutImpl extends LayoutBaseImpl {

	public static boolean hasFriendlyURLKeyword(String friendlyURL) {
		String keyword = _getFriendlyURLKeyword(friendlyURL);

		if (Validator.isNotNull(keyword)) {
			return true;
		}

		return false;
	}

	public static int validateFriendlyURL(String friendlyURL) {
		return validateFriendlyURL(friendlyURL, true);
	}

	/**
	 * Checks whether the URL is a valid friendly URL. It checks for minimal
	 * length and that syntactic restrictions are met, and can check that the
	 * URL's length does not exceed the maximum length.
	 *
	 * @param  friendlyURL the URL to be checked
	 * @param  checkMaxLength whether to check that the URL's length does not
	 *         exceed the maximum length
	 * @return <code>-1</code> if the URL is a valid friendly URL; a {@link
	 *         LayoutFriendlyURLException} constant otherwise
	 */
	public static int validateFriendlyURL(
		String friendlyURL, boolean checkMaxLength) {

		if (friendlyURL.length() < 2) {
			return LayoutFriendlyURLException.TOO_SHORT;
		}

		if (checkMaxLength &&
			(friendlyURL.length() > LayoutConstants.FRIENDLY_URL_MAX_LENGTH)) {

			return LayoutFriendlyURLException.TOO_LONG;
		}

		if (!friendlyURL.startsWith(StringPool.SLASH)) {
			return LayoutFriendlyURLException.DOES_NOT_START_WITH_SLASH;
		}

		if (friendlyURL.endsWith(StringPool.SLASH)) {
			return LayoutFriendlyURLException.ENDS_WITH_SLASH;
		}

		if (friendlyURL.contains(StringPool.DOUBLE_SLASH)) {
			return LayoutFriendlyURLException.ADJACENT_SLASHES;
		}

		for (char c : friendlyURL.toCharArray()) {
			if (!Validator.isChar(c) && !Validator.isDigit(c) &&
				(c != CharPool.DASH) && (c != CharPool.PERCENT) &&
				(c != CharPool.PERIOD) && (c != CharPool.PLUS) &&
				(c != CharPool.SLASH) && (c != CharPool.STAR) &&
				(c != CharPool.UNDERLINE)) {

				return LayoutFriendlyURLException.INVALID_CHARACTERS;
			}
		}

		return -1;
	}

	public static void validateFriendlyURLKeyword(String friendlyURL)
		throws LayoutFriendlyURLException {

		String keyword = _getFriendlyURLKeyword(friendlyURL);

		if (Validator.isNotNull(keyword)) {
			LayoutFriendlyURLException lfurle = new LayoutFriendlyURLException(
				LayoutFriendlyURLException.KEYWORD_CONFLICT);

			lfurle.setKeywordConflict(keyword);

			throw lfurle;
		}
	}

	/**
	 * Returns all layouts that are direct or indirect children of the current
	 * layout.
	 *
	 * @return the layouts that are direct or indirect children of the current
	 *         layout
	 */
	@Override
	public List<Layout> getAllChildren() {
		List<Layout> layouts = new ArrayList<>();

		for (Layout layout : getChildren()) {
			layouts.add(layout);
			layouts.addAll(layout.getAllChildren());
		}

		return layouts;
	}

	/**
	 * Returns the ID of the topmost parent layout (e.g. n-th parent layout) of
	 * the current layout.
	 *
	 * @return the ID of the topmost parent layout of the current layout
	 */
	@Override
	public long getAncestorLayoutId() throws PortalException {
		long layoutId = 0;

		Layout layout = this;

		while (true) {
			if (!layout.isRootLayout()) {
				layout = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getParentLayoutId());
			}
			else {
				layoutId = layout.getLayoutId();

				break;
			}
		}

		return layoutId;
	}

	/**
	 * Returns the plid of the topmost parent layout (e.g. n-th parent layout)
	 * of the current layout.
	 *
	 * @return the plid of the topmost parent layout of the current layout
	 */
	@Override
	public long getAncestorPlid() throws PortalException {
		long plid = 0;

		Layout layout = this;

		while (true) {
			if (!layout.isRootLayout()) {
				layout = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getParentLayoutId());
			}
			else {
				plid = layout.getPlid();

				break;
			}
		}

		return plid;
	}

	/**
	 * Returns all parent layouts of the current layout. The list is retrieved
	 * recursively with the direct parent layout listed first, and most distant
	 * parent listed last.
	 *
	 * @return the current layout's list of parent layouts
	 */
	@Override
	public List<Layout> getAncestors() throws PortalException {
		List<Layout> layouts = Collections.emptyList();

		Layout layout = this;

		while (!layout.isRootLayout()) {
			layout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getParentLayoutId());

			if (layouts.isEmpty()) {
				layouts = new ArrayList<>();
			}

			layouts.add(layout);
		}

		return layouts;
	}

	/**
	 * Returns all child layouts of the current layout, independent of user
	 * access permissions.
	 *
	 * @return the list of all child layouts
	 */
	@Override
	public List<Layout> getChildren() {
		return LayoutLocalServiceUtil.getLayouts(
			getGroupId(), isPrivateLayout(), getLayoutId());
	}

	/**
	 * Returns all child layouts of the current layout that the user has
	 * permission to access.
	 *
	 * @param  permissionChecker the user-specific context to check permissions
	 * @return the list of all child layouts that the user has permission to
	 *         access
	 */
	@Override
	public List<Layout> getChildren(PermissionChecker permissionChecker)
		throws PortalException {

		List<Layout> layouts = ListUtil.copy(getChildren());

		Iterator<Layout> itr = layouts.iterator();

		while (itr.hasNext()) {
			Layout layout = itr.next();

			if (layout.isHidden() ||
				!LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return layouts;
	}

	/**
	 * Returns the color scheme that is configured for the current layout, or
	 * the color scheme of the layout set that contains the current layout if no
	 * color scheme is configured.
	 *
	 * @return the color scheme that is configured for the current layout, or
	 *         the color scheme  of the layout set that contains the current
	 *         layout if no color scheme is configured
	 */
	@Override
	public ColorScheme getColorScheme() throws PortalException {
		if (isInheritLookAndFeel()) {
			LayoutSet layoutSet = getLayoutSet();

			return layoutSet.getColorScheme();
		}

		Theme theme = getTheme();

		return ThemeLocalServiceUtil.getColorScheme(
			getCompanyId(), theme.getThemeId(), getColorSchemeId());
	}

	/**
	 * Returns the CSS text for the current layout, or for the layout set if no
	 * CSS text is configured in the current layout.
	 *
	 * <p>
	 * Layouts and layout sets can configure CSS that is applied in addition to
	 * the theme's CSS.
	 * </p>
	 *
	 * @return the CSS text for the current layout, or for the layout set if no
	 *         CSS text is configured in the current layout
	 */
	@Override
	public String getCssText() throws PortalException {
		if (isInheritLookAndFeel()) {
			LayoutSet layoutSet = getLayoutSet();

			return layoutSet.getCss();
		}

		return getCss();
	}

	@Override
	public String getDefaultThemeSetting(
		String key, String device, boolean inheritLookAndFeel) {

		if (!inheritLookAndFeel) {
			try {
				Theme theme = getTheme();

				return theme.getSetting(key);
			}
			catch (Exception e) {
			}
		}

		try {
			LayoutSet layoutSet = getLayoutSet();

			return layoutSet.getThemeSetting(key, device);
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public List<Portlet> getEmbeddedPortlets() {
		return getEmbeddedPortlets(getGroupId());
	}

	@Override
	public List<Portlet> getEmbeddedPortlets(long groupId) {
		List<PortletPreferences> portletPreferences = _getPortletPreferences(
			groupId);

		if (portletPreferences.isEmpty()) {
			return Collections.emptyList();
		}

		List<Portlet> portlets = new ArrayList<>();

		Set<String> layoutPortletIds = _getLayoutPortletIds();

		for (PortletPreferences portletPreference : portletPreferences) {
			String portletId = portletPreference.getPortletId();

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				getCompanyId(), portletId);

			if ((portlet == null) || !portlet.isReady() ||
				portlet.isUndeployedPortlet() || !portlet.isActive() ||
				!layoutPortletIds.contains(portletId)) {

				continue;
			}

			Portlet embeddedPortlet = portlet;

			if (portlet.isInstanceable()) {

				// Instanceable portlets do not need to be cloned because they
				// are already cloned. See the method getPortletById in the
				// class PortletLocalServiceImpl and how it references the
				// method getClonedInstance in the class PortletImpl.

			}
			else {
				embeddedPortlet = new PortletWrapper(portlet) {

					@Override
					public boolean getStatic() {
						return _staticPortlet;
					}

					@Override
					public boolean isStatic() {
						return _staticPortlet;
					}

					@Override
					public void setStatic(boolean staticPortlet) {
						_staticPortlet = staticPortlet;
					}

					private boolean _staticPortlet;

				};
			}

			// We set embedded portlets as static on order to avoid adding the
			// close and/or move icons.

			embeddedPortlet.setStatic(true);

			portlets.add(embeddedPortlet);
		}

		return portlets;
	}

	/**
	 * Returns the layout's friendly URL for the given locale.
	 *
	 * @param  locale the locale that the friendly URL should be retrieved for
	 * @return the layout's friendly URL for the given locale
	 */
	@Override
	public String getFriendlyURL(Locale locale) {
		String friendlyURL = getFriendlyURL();

		try {
			Group group = getGroup();

			UnicodeProperties typeSettingsProperties =
				group.getTypeSettingsProperties();

			if (!GetterUtil.getBoolean(
					typeSettingsProperties.getProperty(
						GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES),
					true)) {

				String[] locales = StringUtil.split(
					typeSettingsProperties.getProperty(PropsKeys.LOCALES));

				if (!ArrayUtil.contains(
						locales, LanguageUtil.getLanguageId(locale))) {

					return friendlyURL;
				}
			}

			LayoutFriendlyURL layoutFriendlyURL =
				LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURL(
					getPlid(), LocaleUtil.toLanguageId(locale));

			friendlyURL = layoutFriendlyURL.getFriendlyURL();
		}
		catch (Exception e) {
		}

		return friendlyURL;
	}

	/**
	 * Returns the friendly URLs for all configured locales.
	 *
	 * @return the friendly URLs for all configured locales
	 */
	@Override
	public Map<Locale, String> getFriendlyURLMap() {
		Map<Locale, String> friendlyURLMap = new HashMap<>();

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(getPlid());

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			friendlyURLMap.put(
				LocaleUtil.fromLanguageId(layoutFriendlyURL.getLanguageId()),
				layoutFriendlyURL.getFriendlyURL());
		}

		// If the site/portal default language changes, there may not exist a
		// value for the new default language. In this situation, we will use
		// the value from the previous default language.

		Locale defaultSiteLocale = LocaleUtil.getSiteDefault();

		if (Validator.isNull(friendlyURLMap.get(defaultSiteLocale))) {
			Locale defaultLocale = LocaleUtil.fromLanguageId(
				getDefaultLanguageId());

			String defaultFriendlyURL = friendlyURLMap.get(defaultLocale);

			friendlyURLMap.put(defaultSiteLocale, defaultFriendlyURL);
		}

		return friendlyURLMap;
	}

	@Override
	public String getFriendlyURLsXML() {
		return LocalizationUtil.updateLocalization(
			getFriendlyURLMap(), StringPool.BLANK, "FriendlyURL",
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
	}

	/**
	 * Returns the current layout's group.
	 *
	 * <p>
	 * Group is Liferay's technical name for a site.
	 * </p>
	 *
	 * @return the current layout's group
	 */
	@Override
	public Group getGroup() {
		try {
			return GroupLocalServiceUtil.getGroup(getGroupId());
		}
		catch (PortalException pe) {
			return ReflectionUtil.throwException(pe);
		}
	}

	/**
	 * Returns the current layout's HTML title for the given locale, or the
	 * current layout's name for the given locale if no HTML title is
	 * configured.
	 *
	 * @param  locale the locale that the HTML title should be retrieved for
	 * @return the current layout's HTML title for the given locale, or the
	 *         current layout's name for the given locale if no HTML title is
	 *         configured
	 */
	@Override
	public String getHTMLTitle(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getHTMLTitle(localeLanguageId);
	}

	/**
	 * Returns the current layout's HTML title for the given locale language ID,
	 * or the current layout's name if no HTML title is configured.
	 *
	 * @param  localeLanguageId the locale that the HTML title should be
	 *         retrieved for
	 * @return the current layout's HTML title for the given locale language ID,
	 *         or the current layout's name if no HTML title is configured
	 */
	@Override
	public String getHTMLTitle(String localeLanguageId) {
		String htmlTitle = getTitle(localeLanguageId);

		if (Validator.isNull(htmlTitle)) {
			htmlTitle = getName(localeLanguageId);
		}

		return htmlTitle;
	}

	/**
	 * Returns <code>true</code> if the current layout has a configured icon.
	 *
	 * @return <code>true</code> if the current layout has a configured icon;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean getIconImage() {
		if (getIconImageId() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Returns the current layout's {@link LayoutSet}.
	 *
	 * @return the current layout's layout set
	 */
	@Override
	public LayoutSet getLayoutSet() {
		if (_layoutSet == null) {
			try {
				_layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					getGroupId(), isPrivateLayout());
			}
			catch (PortalException pe) {
				ReflectionUtil.throwException(pe);
			}
		}

		return _layoutSet;
	}

	/**
	 * Returns the current layout's {@link LayoutType}.
	 *
	 * @return the current layout's layout type
	 */
	@Override
	public LayoutType getLayoutType() {
		if (_layoutType == null) {
			_layoutType = LayoutTypePortletFactoryUtil.create(this);
		}

		return _layoutType;
	}

	/**
	 * Returns the current layout's linked layout.
	 *
	 * @return the current layout's linked layout, or <code>null</code> if no
	 *         linked layout could be found
	 */
	@Override
	public Layout getLinkedToLayout() {
		long linkToLayoutId = GetterUtil.getLong(
			getTypeSettingsProperty("linkToLayoutId"));

		if (linkToLayoutId <= 0) {
			return null;
		}

		return LayoutLocalServiceUtil.fetchLayout(
			getGroupId(), isPrivateLayout(), linkToLayoutId);
	}

	@Override
	public String getRegularURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		String url = _getURL(httpServletRequest, false, false);

		if (!Validator.isUrl(url, true)) {
			return StringPool.SLASH + url;
		}

		return url;
	}

	@Override
	public String getResetLayoutURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		return _getURL(httpServletRequest, true, true);
	}

	@Override
	public String getResetMaxStateURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		return _getURL(httpServletRequest, true, false);
	}

	@Override
	public Group getScopeGroup() throws PortalException {
		Group group = null;

		try {
			group = GroupLocalServiceUtil.getLayoutGroup(
				getCompanyId(), getPlid());
		}
		catch (NoSuchGroupException nsge) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsge, nsge);
			}
		}

		return group;
	}

	@Override
	public String getTarget() {
		return PortalUtil.getLayoutTarget(this);
	}

	/**
	 * Returns the current layout's theme, or the layout set's theme if no
	 * layout theme is configured.
	 *
	 * @return the current layout's theme, or the layout set's theme if no
	 *         layout theme is configured
	 */
	@Override
	public Theme getTheme() throws PortalException {
		if (isInheritLookAndFeel()) {
			LayoutSet layoutSet = getLayoutSet();

			return layoutSet.getTheme();
		}

		return ThemeLocalServiceUtil.getTheme(getCompanyId(), getThemeId());
	}

	@Override
	public String getThemeSetting(String key, String device) {
		return getThemeSetting(key, device, isInheritLookAndFeel());
	}

	@Override
	public String getThemeSetting(
		String key, String device, boolean inheritLookAndFeel) {

		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		String value = typeSettingsProperties.getProperty(
			ThemeSettingImpl.namespaceProperty(device, key));

		if (value != null) {
			return value;
		}

		return getDefaultThemeSetting(key, device, inheritLookAndFeel);
	}

	@Override
	public String getTypeSettings() {
		if (_typeSettingsProperties == null) {
			return super.getTypeSettings();
		}

		return _typeSettingsProperties.toString();
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			_typeSettingsProperties.fastLoad(super.getTypeSettings());
		}

		return _typeSettingsProperties;
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key);
	}

	@Override
	public String getTypeSettingsProperty(String key, String defaultValue) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key, defaultValue);
	}

	/**
	 * Returns <code>true</code> if the given layout ID matches one of the
	 * current layout's hierarchical parents.
	 *
	 * @param  layoutId the layout ID to search for in the current layout's
	 *         parent list
	 * @return <code>true</code> if the given layout ID matches one of the
	 *         current layout's hierarchical parents; <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean hasAncestor(long layoutId) throws PortalException {
		long parentLayoutId = getParentLayoutId();

		while (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			if (parentLayoutId == layoutId) {
				return true;
			}

			Layout parentLayout = LayoutLocalServiceUtil.getLayout(
				getGroupId(), isPrivateLayout(), parentLayoutId);

			parentLayoutId = parentLayout.getParentLayoutId();
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the current layout has child layouts.
	 *
	 * @return <code>true</code> if the current layout has child layouts,
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean hasChildren() {
		return LayoutLocalServiceUtil.hasLayouts(
			getGroupId(), isPrivateLayout(), getLayoutId());
	}

	@Override
	public boolean hasScopeGroup() throws PortalException {
		Group group = getScopeGroup();

		if (group != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasSetModifiedDate() {
		return true;
	}

	@Override
	public boolean includeLayoutContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(getType());

		return layoutTypeController.includeLayoutContent(
			httpServletRequest, httpServletResponse, this);
	}

	@Override
	public boolean isChildSelected(boolean selectable, Layout layout)
		throws PortalException {

		if (selectable) {
			long plid = getPlid();

			List<Layout> ancestors = layout.getAncestors();

			for (Layout curLayout : ancestors) {
				if (plid == curLayout.getPlid()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the current layout can be used as a content
	 * display page.
	 *
	 * <p>
	 * A content display page must have an Asset Publisher portlet that is
	 * configured as the default Asset Publisher for the layout.
	 * </p>
	 *
	 * @return <code>true</code> if the current layout can be used as a content
	 *         display page; <code>false</code> otherwise
	 */
	@Override
	public boolean isContentDisplayPage() {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		String defaultAssetPublisherPortletId =
			typeSettingsProperties.getProperty(
				LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

		if (Validator.isNotNull(defaultAssetPublisherPortletId)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isCustomizable() {
		if (!isTypePortlet()) {
			return false;
		}

		if (GetterUtil.getBoolean(
				getTypeSettingsProperty(LayoutConstants.CUSTOMIZABLE_LAYOUT))) {

			return true;
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)getLayoutType();

		if (layoutTypePortlet.isCustomizable()) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the current layout is the first layout in
	 * its parent's hierarchical list of children layouts.
	 *
	 * @return <code>true</code> if the current layout is the first layout in
	 *         its parent's hierarchical list of children layouts;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean isFirstChild() {
		if (getPriority() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the current layout is the topmost parent
	 * layout.
	 *
	 * @return <code>true</code> if the current layout is the topmost parent
	 *         layout; <code>false</code> otherwise
	 */
	@Override
	public boolean isFirstParent() {
		if (isFirstChild() && isRootLayout()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isIconImage() {
		return getIconImage();
	}

	/**
	 * Returns <code>true</code> if the current layout utilizes its {@link
	 * LayoutSet}'s look and feel options (e.g. theme and color scheme).
	 *
	 * @return <code>true</code> if the current layout utilizes its layout set's
	 *         look and feel options; <code>false</code> otherwise
	 */
	@Override
	public boolean isInheritLookAndFeel() {
		if (Validator.isNull(getThemeId()) ||
			Validator.isNull(getColorSchemeId())) {

			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the current layout is built from a layout
	 * template and still maintains an active connection to it.
	 *
	 * @return <code>true</code> if the current layout is built from a layout
	 *         template and still maintains an active connection to it;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean isLayoutPrototypeLinkActive() {
		if (isLayoutPrototypeLinkEnabled() &&
			Validator.isNotNull(getLayoutPrototypeUuid())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPortletEmbedded(String portletId, long groupId) {
		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, getPlid(), portletId);

		if (portletPreferences == null) {
			return false;
		}

		portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				groupId, PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				PortletKeys.PREFS_PLID_SHARED, portletId);

		if ((portletPreferences == null) && isTypePortlet()) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)getLayoutType();

			PortalPreferences portalPreferences =
				layoutTypePortlet.getPortalPreferences();

			if ((portalPreferences != null) &&
				layoutTypePortlet.isCustomizable()) {

				portletPreferences =
					PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
						portalPreferences.getUserId(),
						PortletKeys.PREFS_OWNER_TYPE_USER, getPlid(),
						portletId);
			}
		}

		if (portletPreferences == null) {
			return false;
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			getCompanyId(), portletId);

		if ((portlet == null) || !portlet.isReady() ||
			portlet.isUndeployedPortlet() || !portlet.isActive()) {

			return false;
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the current layout is part of the public
	 * {@link LayoutSet}.
	 *
	 * <p>
	 * Note, the returned value reflects the layout's default access options,
	 * not its access permissions.
	 * </p>
	 *
	 * @return <code>true</code> if the current layout is part of the public
	 *         layout set; <code>false</code> otherwise
	 */
	@Override
	public boolean isPublicLayout() {
		return !isPrivateLayout();
	}

	/**
	 * Returns <code>true</code> if the current layout is the root layout.
	 *
	 * @return <code>true</code> if the current layout is the root layout;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean isRootLayout() {
		if (getParentLayoutId() == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSelected(
		boolean selectable, Layout layout, long ancestorPlid) {

		if (selectable) {
			long plid = getPlid();

			if ((plid == layout.getPlid()) || (plid == ancestorPlid)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the current layout can hold embedded
	 * portlets.
	 *
	 * @return <code>true</code> if the current layout can hold embedded
	 *         portlets; <code>false</code> otherwise
	 */
	@Override
	public boolean isSupportsEmbeddedPortlets() {
		if (isTypeEmbedded() || isTypePanel() || isTypePortlet()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTypeControlPanel() {
		if (Objects.equals(getType(), LayoutConstants.TYPE_CONTROL_PANEL) ||
			Objects.equals(
				_getLayoutTypeControllerType(),
				LayoutConstants.TYPE_CONTROL_PANEL)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isTypeEmbedded() {
		if (Objects.equals(getType(), LayoutConstants.TYPE_EMBEDDED) ||
			Objects.equals(
				_getLayoutTypeControllerType(),
				LayoutConstants.TYPE_EMBEDDED)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isTypeLinkToLayout() {
		if (Objects.equals(getType(), LayoutConstants.TYPE_LINK_TO_LAYOUT) ||
			Objects.equals(
				_getLayoutTypeControllerType(),
				LayoutConstants.TYPE_LINK_TO_LAYOUT)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isTypePanel() {
		if (Objects.equals(getType(), LayoutConstants.TYPE_PANEL) ||
			Objects.equals(
				_getLayoutTypeControllerType(), LayoutConstants.TYPE_PANEL)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isTypePortlet() {
		if (Objects.equals(getType(), LayoutConstants.TYPE_PORTLET) ||
			Objects.equals(
				_getLayoutTypeControllerType(), LayoutConstants.TYPE_PORTLET)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isTypeURL() {
		if (Objects.equals(getType(), LayoutConstants.TYPE_URL)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean matches(
		HttpServletRequest httpServletRequest, String friendlyURL) {

		LayoutType layoutType = getLayoutType();

		LayoutTypeController layoutTypeController =
			layoutType.getLayoutTypeController();

		return layoutTypeController.matches(
			httpServletRequest, friendlyURL, this);
	}

	@Override
	public void setGroupId(long groupId) {
		super.setGroupId(groupId);

		_layoutSet = null;
	}

	@Override
	public void setLayoutSet(LayoutSet layoutSet) {
		_layoutSet = layoutSet;
	}

	@Override
	public void setPrivateLayout(boolean privateLayout) {
		super.setPrivateLayout(privateLayout);

		_layoutSet = null;
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		_typeSettingsProperties = null;

		super.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;

		super.setTypeSettings(_typeSettingsProperties.toString());
	}

	private static String _getFriendlyURLKeyword(String friendlyURL) {
		friendlyURL = StringUtil.toLowerCase(friendlyURL);

		for (String keyword : _friendlyURLKeywords) {
			if (friendlyURL.startsWith(keyword)) {
				return keyword;
			}

			if (keyword.equals(friendlyURL + StringPool.SLASH)) {
				return friendlyURL;
			}
		}

		return null;
	}

	private static void _initFriendlyURLKeywords() {
		_friendlyURLKeywords =
			new String[PropsValues.LAYOUT_FRIENDLY_URL_KEYWORDS.length];

		for (int i = 0; i < PropsValues.LAYOUT_FRIENDLY_URL_KEYWORDS.length;
			 i++) {

			String keyword = PropsValues.LAYOUT_FRIENDLY_URL_KEYWORDS[i];

			keyword = StringPool.SLASH + keyword;

			if (!keyword.contains(StringPool.PERIOD)) {
				if (keyword.endsWith(StringPool.STAR)) {
					keyword = keyword.substring(0, keyword.length() - 1);
				}
				else {
					keyword = keyword + StringPool.SLASH;
				}
			}

			_friendlyURLKeywords[i] = StringUtil.toLowerCase(keyword);
		}
	}

	private Set<String> _getLayoutPortletIds() {
		Set<String> layoutPortletIds = new HashSet<>();

		List<PortletPreferences> portletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, getPlid());

		for (PortletPreferences portletPreference : portletPreferences) {
			layoutPortletIds.add(portletPreference.getPortletId());
		}

		return layoutPortletIds;
	}

	private String _getLayoutTypeControllerType() {
		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(getType());

		return layoutTypeController.getType();
	}

	private LayoutTypePortlet _getLayoutTypePortletClone(
			HttpServletRequest httpServletRequest)
		throws IOException {

		LayoutTypePortlet layoutTypePortlet = null;

		LayoutClone layoutClone = LayoutCloneFactory.getInstance();

		if (layoutClone != null) {
			String typeSettings = layoutClone.get(
				httpServletRequest, getPlid());

			if (typeSettings != null) {
				UnicodeProperties typeSettingsProperties =
					new UnicodeProperties(true);

				typeSettingsProperties.load(typeSettings);

				String stateMax = typeSettingsProperties.getProperty(
					LayoutTypePortletConstants.STATE_MAX);
				String stateMin = typeSettingsProperties.getProperty(
					LayoutTypePortletConstants.STATE_MIN);

				Layout layout = (Layout)clone();

				layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

				layoutTypePortlet.setStateMax(stateMax);
				layoutTypePortlet.setStateMin(stateMin);
			}
		}

		if (layoutTypePortlet == null) {
			layoutTypePortlet = (LayoutTypePortlet)getLayoutType();
		}

		return layoutTypePortlet;
	}

	private List<PortletPreferences> _getPortletPreferences(long groupId) {
		List<PortletPreferences> portletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				groupId, PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				PortletKeys.PREFS_PLID_SHARED);

		if (isTypePortlet()) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)getLayoutType();

			PortalPreferences portalPreferences =
				layoutTypePortlet.getPortalPreferences();

			if ((portalPreferences != null) &&
				layoutTypePortlet.isCustomizable()) {

				portletPreferences = ListUtil.copy(portletPreferences);

				portletPreferences.addAll(
					PortletPreferencesLocalServiceUtil.getPortletPreferences(
						portalPreferences.getUserId(),
						PortletKeys.PREFS_OWNER_TYPE_USER, getPlid()));
			}
		}

		return portletPreferences;
	}

	private String _getURL(
			HttpServletRequest httpServletRequest, boolean resetMaxState,
			boolean resetRenderParameters)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (resetMaxState) {
			Layout layout = themeDisplay.getLayout();

			LayoutTypePortlet layoutTypePortlet = null;

			if (layout.equals(this)) {
				layoutTypePortlet = themeDisplay.getLayoutTypePortlet();
			}
			else {
				try {
					layoutTypePortlet = _getLayoutTypePortletClone(
						httpServletRequest);
				}
				catch (IOException ioe) {
					_log.error("Unable to clone layout settings", ioe);

					layoutTypePortlet = (LayoutTypePortlet)getLayoutType();
				}
			}

			if (layoutTypePortlet.hasStateMax()) {
				String portletId =
					StringUtil.split(layoutTypePortlet.getStateMax())[0];

				LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
					httpServletRequest, portletId, this,
					PortletRequest.ACTION_PHASE);

				try {
					portletURL.setWindowState(WindowState.NORMAL);
					portletURL.setPortletMode(PortletMode.VIEW);
				}
				catch (PortletException pe) {
					throw new SystemException(pe);
				}

				portletURL.setAnchor(false);

				if (PropsValues.LAYOUT_DEFAULT_P_L_RESET &&
					!resetRenderParameters) {

					portletURL.setParameter("p_l_reset", "0");
				}
				else if (!PropsValues.LAYOUT_DEFAULT_P_L_RESET &&
						 resetRenderParameters) {

					portletURL.setParameter("p_l_reset", "1");
				}

				return portletURL.toString();
			}
		}

		String url = PortalUtil.getLayoutURL(this, themeDisplay);

		if (!CookieKeys.hasSessionId(httpServletRequest) &&
			(url.startsWith(PortalUtil.getPortalURL(httpServletRequest)) ||
			 url.startsWith(StringPool.SLASH))) {

			HttpSession session = httpServletRequest.getSession();

			url = PortalUtil.getURLWithSessionId(url, session.getId());
		}

		if (!resetMaxState) {
			return url;
		}

		if (PropsValues.LAYOUT_DEFAULT_P_L_RESET && !resetRenderParameters) {
			url = HttpUtil.addParameter(url, "p_l_reset", 0);
		}
		else if (!PropsValues.LAYOUT_DEFAULT_P_L_RESET &&
				 resetRenderParameters) {

			url = HttpUtil.addParameter(url, "p_l_reset", 1);
		}

		return url;
	}

	private static final Log _log = LogFactoryUtil.getLog(LayoutImpl.class);

	private static String[] _friendlyURLKeywords;

	static {
		_initFriendlyURLKeywords();
	}

	private LayoutSet _layoutSet;
	private transient LayoutType _layoutType;
	private UnicodeProperties _typeSettingsProperties;

}
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

package com.liferay.portal.model.impl;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutClone;
import com.liferay.portal.util.LayoutCloneFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
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

	public LayoutImpl() {
	}

	public List<Layout> getAllChildren() throws SystemException {
		List<Layout> layouts = new ArrayList<Layout>();

		for (Layout layout : getChildren()) {
			layouts.add(layout);
			layouts.addAll(layout.getAllChildren());
		}

		return layouts;
	}

	public long getAncestorLayoutId() throws PortalException, SystemException {
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

	public long getAncestorPlid() throws PortalException, SystemException {
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

	public List<Layout> getAncestors() throws PortalException, SystemException {
		List<Layout> layouts = new ArrayList<Layout>();

		Layout layout = this;

		while (!layout.isRootLayout()) {
			layout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getParentLayoutId());

			layouts.add(layout);
		}

		return layouts;
	}

	public List<Layout> getChildren() throws SystemException {
		return LayoutLocalServiceUtil.getLayouts(
			getGroupId(), isPrivateLayout(), getLayoutId());
	}

	public List<Layout> getChildren(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

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

	public ColorScheme getColorScheme()
		throws PortalException, SystemException {

		if (isInheritLookAndFeel()) {
			return getLayoutSet().getColorScheme();
		}
		else {
			return ThemeLocalServiceUtil.getColorScheme(
				getCompanyId(), getTheme().getThemeId(), getColorSchemeId(),
				false);
		}
	}

	public String getCssText() throws PortalException, SystemException {
		if (isInheritLookAndFeel()) {
			return getLayoutSet().getCss();
		}
		else {
			return getCss();
		}
	}

	public Map<Locale, String> getFriendlyURLMap() throws SystemException {
		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(getPlid());

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			friendlyURLMap.put(
				LocaleUtil.fromLanguageId(layoutFriendlyURL.getLanguageId()),
				layoutFriendlyURL.getFriendlyURL());
		}

		return friendlyURLMap;
	}

	public String getFriendlyURLsXML() throws SystemException {
		Map<Locale, String> friendlyURLMap = getFriendlyURLMap();

		return LocalizationUtil.updateLocalization(
			friendlyURLMap, StringPool.BLANK, "FriendlyURL",
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
	}

	public Group getGroup() throws PortalException, SystemException {
		return GroupLocalServiceUtil.getGroup(getGroupId());
	}

	public String getHTMLTitle(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getHTMLTitle(localeLanguageId);
	}

	public String getHTMLTitle(String localeLanguageId) {
		String htmlTitle = getTitle(localeLanguageId);

		if (Validator.isNull(htmlTitle)) {
			htmlTitle = getName(localeLanguageId);
		}

		return htmlTitle;
	}

	public LayoutSet getLayoutSet() throws PortalException, SystemException {
		if (_layoutSet == null) {
			_layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				getGroupId(), isPrivateLayout());
		}

		return _layoutSet;
	}

	public LayoutType getLayoutType() {
		if (_layoutType == null) {
			_layoutType = new LayoutTypePortletImpl(this);
		}

		return _layoutType;
	}

	public long getParentPlid() throws PortalException, SystemException {
		if (getParentLayoutId() == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			return 0;
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(
			getGroupId(), isPrivateLayout(), getParentLayoutId());

		return layout.getPlid();
	}

	public String getRegularURL(HttpServletRequest request)
		throws PortalException, SystemException {

		return _getURL(request, false, false);
	}

	public String getResetLayoutURL(HttpServletRequest request)
		throws PortalException, SystemException {

		return _getURL(request, true, true);
	}

	public String getResetMaxStateURL(HttpServletRequest request)
		throws PortalException, SystemException {

		return _getURL(request, true, false);
	}

	public Group getScopeGroup() throws PortalException, SystemException {
		Group group = null;

		try {
			group = GroupLocalServiceUtil.getLayoutGroup(
				getCompanyId(), getPlid());
		}
		catch (NoSuchGroupException nsge) {
		}

		return group;
	}

	public String getTarget() {
		return PortalUtil.getLayoutTarget(this);
	}

	public Theme getTheme() throws PortalException, SystemException {
		if (isInheritLookAndFeel()) {
			return getLayoutSet().getTheme();
		}
		else {
			return ThemeLocalServiceUtil.getTheme(
				getCompanyId(), getThemeId(), false);
		}
	}

	public String getThemeSetting(String key, String device) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		String value = typeSettingsProperties.getProperty(
			ThemeSettingImpl.namespaceProperty(device, key));

		if (value != null) {
			return value;
		}

		if (!isInheritLookAndFeel()) {
			try {
				Theme theme = _getTheme(device);

				return theme.getSetting(key);
			}
			catch (Exception e) {
			}
		}

		try {
			LayoutSet layoutSet = getLayoutSet();

			value = layoutSet.getThemeSetting(key, device);
		}
		catch (Exception e) {
		}

		return value;
	}

	@Override
	public String getTypeSettings() {
		if (_typeSettingsProperties == null) {
			return super.getTypeSettings();
		}
		else {
			return _typeSettingsProperties.toString();
		}
	}

	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			_typeSettingsProperties.fastLoad(super.getTypeSettings());
		}

		return _typeSettingsProperties;
	}

	public String getTypeSettingsProperty(String key) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key);
	}

	public String getTypeSettingsProperty(String key, String defaultValue) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key, defaultValue);
	}

	public ColorScheme getWapColorScheme()
		throws PortalException, SystemException {

		if (isInheritLookAndFeel()) {
			return getLayoutSet().getWapColorScheme();
		}
		else {
			return ThemeLocalServiceUtil.getColorScheme(
				getCompanyId(), getWapTheme().getThemeId(),
				getWapColorSchemeId(), true);
		}
	}

	public Theme getWapTheme() throws PortalException, SystemException {
		if (isInheritWapLookAndFeel()) {
			return getLayoutSet().getWapTheme();
		}
		else {
			return ThemeLocalServiceUtil.getTheme(
				getCompanyId(), getWapThemeId(), true);
		}
	}

	public boolean hasAncestor(long layoutId)
		throws PortalException, SystemException {

		long parentLayoutId = getParentLayoutId();

		while (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			if (parentLayoutId == layoutId) {
				return true;
			}
			else {
				Layout parentLayout = LayoutLocalServiceUtil.getLayout(
					getGroupId(), isPrivateLayout(), parentLayoutId);

				parentLayoutId = parentLayout.getParentLayoutId();
			}
		}

		return false;
	}

	public boolean hasChildren() throws SystemException {
		return LayoutLocalServiceUtil.hasLayouts(
			getGroupId(), isPrivateLayout(), getLayoutId());
	}

	public boolean hasScopeGroup() throws PortalException, SystemException {
		Group group = getScopeGroup();

		if (group != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isChildSelected(boolean selectable, Layout layout)
		throws PortalException, SystemException {

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

	public boolean isFirstChild() {
		if (getPriority() == 0) {
			return true;
		}

		return false;
	}

	public boolean isFirstParent() {
		if (isFirstChild() && isRootLayout()) {
			return true;
		}

		return false;
	}

	public boolean isInheritLookAndFeel() {
		if (Validator.isNull(getThemeId()) ||
			Validator.isNull(getColorSchemeId())) {

			return true;
		}

		return false;
	}

	public boolean isInheritWapLookAndFeel() {
		if (Validator.isNull(getWapThemeId()) ||
			Validator.isNull(getWapColorSchemeId())) {

			return true;
		}

		return false;
	}

	public boolean isLayoutPrototypeLinkActive() {
		if (isLayoutPrototypeLinkEnabled() &&
			Validator.isNotNull(getLayoutPrototypeUuid())) {

			return true;
		}

		return false;
	}

	public boolean isPublicLayout() {
		return !isPrivateLayout();
	}

	public boolean isRootLayout() {
		if (getParentLayoutId() == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			return true;
		}

		return false;
	}

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

	public boolean isSupportsEmbeddedPortlets() {
		if (isTypeArticle() || isTypeEmbedded() || isTypePanel() ||
			isTypePortlet()) {

			return true;
		}

		return false;
	}

	public boolean isTypeArticle() {
		if (getType().equals(LayoutConstants.TYPE_ARTICLE)) {
			return true;
		}

		return false;
	}

	public boolean isTypeControlPanel() {
		if (getType().equals(LayoutConstants.TYPE_CONTROL_PANEL)) {
			return true;
		}

		return false;
	}

	public boolean isTypeEmbedded() {
		if (getType().equals(LayoutConstants.TYPE_EMBEDDED)) {
			return true;
		}

		return false;
	}

	public boolean isTypeLinkToLayout() {
		if (getType().equals(LayoutConstants.TYPE_LINK_TO_LAYOUT)) {
			return true;
		}

		return false;
	}

	public boolean isTypePanel() {
		if (getType().equals(LayoutConstants.TYPE_PANEL)) {
			return true;
		}

		return false;
	}

	public boolean isTypePortlet() {
		if (getType().equals(LayoutConstants.TYPE_PORTLET)) {
			return true;
		}

		return false;
	}

	public boolean isTypeURL() {
		if (getType().equals(LayoutConstants.TYPE_URL)) {
			return true;
		}

		return false;
	}

	@Override
	public void setGroupId(long groupId) {
		super.setGroupId(groupId);

		_layoutSet = null;
	}

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

	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;

		super.setTypeSettings(_typeSettingsProperties.toString());
	}

	private static String _getFriendlyURLKeyword(String friendlyURL) {
		friendlyURL = friendlyURL.toLowerCase();

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

			_friendlyURLKeywords[i] = keyword.toLowerCase();
		}
	}

	private LayoutTypePortlet _getLayoutTypePortletClone(
			HttpServletRequest request)
		throws IOException {

		LayoutTypePortlet layoutTypePortlet = null;

		LayoutClone layoutClone = LayoutCloneFactory.getInstance();

		if (layoutClone != null) {
			String typeSettings = layoutClone.get(request, getPlid());

			if (typeSettings != null) {
				UnicodeProperties typeSettingsProperties =
					new UnicodeProperties(true);

				typeSettingsProperties.load(typeSettings);

				String stateMax = typeSettingsProperties.getProperty(
					LayoutTypePortletConstants.STATE_MAX);
				String stateMin = typeSettingsProperties.getProperty(
					LayoutTypePortletConstants.STATE_MIN);

				Layout layout = (Layout)this.clone();

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

	private Theme _getTheme(String device)
		throws PortalException, SystemException {

		if (device.equals("regular")) {
			return getTheme();
		}
		else {
			return getWapTheme();
		}
	}

	private String _getURL(
			HttpServletRequest request, boolean resetMaxState,
			boolean resetRenderParameters)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (resetMaxState) {
			Layout layout = themeDisplay.getLayout();

			LayoutTypePortlet layoutTypePortlet = null;

			if (layout.equals(this)) {
				layoutTypePortlet = themeDisplay.getLayoutTypePortlet();
			}
			else {
				try {
					layoutTypePortlet = _getLayoutTypePortletClone(request);
				}
				catch (IOException ioe) {
					_log.error("Unable to clone layout settings", ioe);

					layoutTypePortlet = (LayoutTypePortlet)getLayoutType();
				}
			}

			if (layoutTypePortlet.hasStateMax()) {
				String portletId = StringUtil.split(
					layoutTypePortlet.getStateMax())[0];

				PortletURLImpl portletURLImpl = new PortletURLImpl(
					request, portletId, getPlid(), PortletRequest.ACTION_PHASE);

				try {
					portletURLImpl.setWindowState(WindowState.NORMAL);
					portletURLImpl.setPortletMode(PortletMode.VIEW);
				}
				catch (PortletException pe) {
					throw new SystemException(pe);
				}

				portletURLImpl.setAnchor(false);

				if (PropsValues.LAYOUT_DEFAULT_P_L_RESET &&
					!resetRenderParameters) {

					portletURLImpl.setParameter("p_l_reset", "0");
				}
				else if (!PropsValues.LAYOUT_DEFAULT_P_L_RESET &&
						 resetRenderParameters) {

					portletURLImpl.setParameter("p_l_reset", "1");
				}

				return portletURLImpl.toString();
			}
		}

		String portalURL = PortalUtil.getPortalURL(request);

		String url = PortalUtil.getLayoutURL(this, themeDisplay);

		if (!CookieKeys.hasSessionId(request) &&
			(url.startsWith(portalURL) || url.startsWith(StringPool.SLASH))) {

			url = PortalUtil.getURLWithSessionId(
				url, request.getSession().getId());
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

	private static Log _log = LogFactoryUtil.getLog(LayoutImpl.class);

	private static String[] _friendlyURLKeywords;

	private LayoutSet _layoutSet;
	private LayoutType _layoutType;
	private UnicodeProperties _typeSettingsProperties;

	static {
		_initFriendlyURLKeywords();
	}

}
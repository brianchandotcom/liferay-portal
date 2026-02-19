/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ScopeUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutRevisionImpl extends LayoutRevisionBaseImpl {

	@Override
	public String getBreadcrumb(Locale locale) throws PortalException {
		List<Layout> layouts = _getAncestors();

		StringBundler sb = new StringBundler((4 * layouts.size()) + 5);

		Group group = getGroup();

		sb.append(group.getLayoutRootNodeName(isPrivateLayout(), locale));

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(layouts);

		for (Layout layout : layouts) {
			sb.append(HtmlUtil.escape(_getLayoutName(layout, locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(getName(locale)));

		return sb.toString();
	}

	@Override
	public List<LayoutRevision> getChildren() {
		return LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(
			getLayoutSetBranchId(), getLayoutRevisionId(), getPlid());
	}

	@Override
	public ColorScheme getColorScheme() throws PortalException {
		if (isInheritLookAndFeel()) {
			return getLayoutSet().getColorScheme();
		}

		return ThemeLocalServiceUtil.getColorScheme(
			getCompanyId(), getTheme().getThemeId(), getColorSchemeId());
	}

	@Override
	public String getCssText() throws PortalException {
		if (isInheritLookAndFeel()) {
			return getLayoutSet().getCss();
		}

		return getCss();
	}

	public String getFaviconFileEntryERC() {
		return getTypeSettingsProperty("faviconFileEntryERC");
	}

	public long getFaviconFileEntryGroupId() {
		if (_faviconFileEntryGroupId != null) {
			return _faviconFileEntryGroupId;
		}

		_faviconFileEntryGroupId = ScopeUtil.getItemGroupId(
			getCompanyId(), getFaviconFileEntryScopeERC(), getGroupId());

		if (_faviconFileEntryGroupId == null) {
			return 0;
		}

		return _faviconFileEntryGroupId;
	}

	public String getFaviconFileEntryScopeERC() {
		return getTypeSettingsProperty("faviconFileEntryScopeERC");
	}

	public String getFaviconURL() {
		if (_faviconURL != null) {
			return _faviconURL;
		}

		if (Validator.isNull(getFaviconFileEntryERC())) {
			return null;
		}

		try {
			FileEntry fileEntry =
				DLAppServiceUtil.getFileEntryByExternalReferenceCode(
					getFaviconFileEntryERC(), getFaviconFileEntryGroupId());

			_faviconURL = HtmlUtil.escape(
				StringBundler.concat(
					PortalUtil.getPathContext(), "/documents/",
					fileEntry.getRepositoryId(), StringPool.SLASH,
					fileEntry.getFolderId(), StringPool.SLASH,
					URLCodec.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())),
					StringPool.SLASH, URLCodec.encodeURL(fileEntry.getUuid())));

			return _faviconURL;
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to resolve favicon file entry with external ",
						"reference code ", getFaviconFileEntryERC(),
						" and group ID ", getFaviconFileEntryGroupId()));
			}

			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public Group getGroup() {
		try {
			return GroupLocalServiceUtil.getGroup(getGroupId());
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public String getHTMLTitle(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getHTMLTitle(localeLanguageId);
	}

	@Override
	public String getHTMLTitle(String localeLanguageId) {
		String htmlTitle = getTitle(localeLanguageId);

		if (Validator.isNull(htmlTitle)) {
			htmlTitle = getName(localeLanguageId);
		}

		return htmlTitle;
	}

	@Override
	public Image getIconImage() {
		if (_iconImage != null) {
			return _iconImage;
		}

		if (hasIconImage()) {
			_iconImage = ImageLocalServiceUtil.fetchImage(getIconImageId());

			if ((_iconImage == null) && _log.isWarnEnabled()) {
				_log.warn("Unable to get image with ID " + getIconImageId());
			}
		}

		String iconImageERC = getTypeSettingsProperty("iconImageERC");

		if ((_iconImage == null) && Validator.isNotNull(iconImageERC)) {
			_iconImage =
				ImageLocalServiceUtil.fetchImageByExternalReferenceCode(
					iconImageERC, getCompanyId());

			if (_iconImage != null) {
				super.setIconImageId(_iconImage.getImageId());
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to fetch image with external reference code ",
						iconImageERC, " and company ", getCompanyId()));
			}
		}

		return _iconImage;
	}

	public String getIconImageERC() {
		return getTypeSettingsProperty("iconImageERC");
	}

	@Override
	public LayoutBranch getLayoutBranch() throws PortalException {
		return LayoutBranchLocalServiceUtil.getLayoutBranch(
			getLayoutBranchId());
	}

	@Override
	public LayoutSet getLayoutSet() throws PortalException {
		return LayoutSetLocalServiceUtil.getLayoutSet(
			getGroupId(), isPrivateLayout());
	}

	@Override
	public String getRegularURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String url = PortalUtil.getLayoutURL(
			LayoutLocalServiceUtil.getLayout(getPlid()), themeDisplay);

		if (!CookiesManagerUtil.hasSessionId(httpServletRequest) &&
			(url.startsWith(PortalUtil.getPortalURL(httpServletRequest)) ||
			 url.startsWith(StringPool.SLASH))) {

			HttpSession httpSession = httpServletRequest.getSession();

			url = PortalUtil.getURLWithSessionId(url, httpSession.getId());
		}

		return url;
	}

	@Override
	public String getTarget() {
		String target = getTypeSettingsProperty("target");

		if (Validator.isNull(target)) {
			return StringPool.BLANK;
		}

		return "target=\"" + HtmlUtil.escapeAttribute(target) + "\"";
	}

	@Override
	public Theme getTheme() throws PortalException {
		if (isInheritLookAndFeel()) {
			return getLayoutSet().getTheme();
		}

		return ThemeLocalServiceUtil.getTheme(getCompanyId(), getThemeId());
	}

	@Override
	public String getThemeSetting(String key, String device) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		String value = typeSettingsUnicodeProperties.getProperty(
			ThemeSettingImpl.namespaceProperty(device, key));

		if (value != null) {
			return value;
		}

		if (!isInheritLookAndFeel()) {
			try {
				Theme theme = getTheme();

				return theme.getSetting(key);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		try {
			LayoutSet layoutSet = getLayoutSet();

			value = layoutSet.getThemeSetting(key, device);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return value;
	}

	@Override
	public String getTypeSettings() {
		if (_typeSettingsUnicodeProperties == null) {
			return super.getTypeSettings();
		}

		return _typeSettingsUnicodeProperties.toString();
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				super.getTypeSettings()
			).build();
		}

		return _typeSettingsUnicodeProperties;
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return typeSettingsUnicodeProperties.getProperty(key);
	}

	@Override
	public String getTypeSettingsProperty(String key, String defaultValue) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return typeSettingsUnicodeProperties.getProperty(key, defaultValue);
	}

	@Override
	public boolean hasChildren() {
		return !getChildren().isEmpty();
	}

	@Override
	public boolean hasIconImage() {
		String iconImageERC = getTypeSettingsProperty("iconImageERC");

		if ((getIconImageId() > 0) || Validator.isNotNull(iconImageERC)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isContentDisplayPage() {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		String defaultAssetPublisherPortletId =
			typeSettingsUnicodeProperties.getProperty(
				LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

		return Validator.isNotNull(defaultAssetPublisherPortletId);
	}

	@Override
	public boolean isCustomizable() throws PortalException {
		Layout layout = LayoutLocalServiceUtil.getLayout(getPlid());

		if (!layout.isTypePortlet()) {
			return false;
		}

		if (GetterUtil.getBoolean(
				getTypeSettingsProperty(LayoutConstants.CUSTOMIZABLE_LAYOUT))) {

			return true;
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.isCustomizable();
	}

	@Override
	public boolean isInheritLookAndFeel() {
		if (Validator.isNull(getThemeId()) ||
			Validator.isNull(getColorSchemeId())) {

			return true;
		}

		return false;
	}

	public void setFaviconFileEntryERC(String faviconFileEntryERC) {
		UnicodeProperties unicodeProperties = getTypeSettingsProperties();

		unicodeProperties.setProperty(
			"faviconFileEntryERC", faviconFileEntryERC);

		setTypeSettingsProperties(unicodeProperties);

		_faviconURL = null;
	}

	public void setFaviconFileEntryScopeERC(String faviconFileEntryScopeERC) {
		UnicodeProperties unicodeProperties = getTypeSettingsProperties();

		unicodeProperties.setProperty(
			"faviconFileEntryScopeERC", faviconFileEntryScopeERC);

		setTypeSettingsProperties(unicodeProperties);

		_faviconURL = null;
		_faviconFileEntryGroupId = null;
	}

	public void setIconImageERC(String iconImageERC) {
		if ((_iconImage != null) &&
			Objects.equals(
				_iconImage.getExternalReferenceCode(), iconImageERC)) {

			return;
		}

		UnicodeProperties unicodeProperties = getTypeSettingsProperties();

		if (Validator.isNotNull(iconImageERC)) {
			_iconImage =
				ImageLocalServiceUtil.fetchImageByExternalReferenceCode(
					iconImageERC, getCompanyId());

			if (_iconImage != null) {
				super.setIconImageId(_iconImage.getImageId());
				unicodeProperties.setProperty("iconImageERC", iconImageERC);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to resolve icon image with external ",
							"reference code ", iconImageERC, " and company ID ",
							getCompanyId()));
				}

				super.setIconImageId(0);
				unicodeProperties.setProperty("iconImageERC", iconImageERC);
			}
		}
		else {
			super.setIconImageId(0);
			_iconImage = null;
			unicodeProperties.setProperty("iconImageERC", null);
		}

		setTypeSettingsProperties(unicodeProperties);
	}

	@Override
	public void setIconImageId(long iconImageId) {
		if (getIconImageId() == iconImageId) {
			return;
		}

		super.setIconImageId(iconImageId);

		_iconImage = getIconImage();

		if (_iconImage == null) {
			setIconImageERC(null);
		}
		else {
			setIconImageERC(_iconImage.getExternalReferenceCode());
		}
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		_typeSettingsUnicodeProperties = null;

		super.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsUnicodeProperties) {

		_typeSettingsUnicodeProperties = typeSettingsUnicodeProperties;

		super.setTypeSettings(_typeSettingsUnicodeProperties.toString());
	}

	private List<Layout> _getAncestors() throws PortalException {
		Layout layout = LayoutLocalServiceUtil.getLayout(getPlid());

		return layout.getAncestors();
	}

	private String _getLayoutName(Layout layout, Locale locale) {
		LayoutRevision layoutRevision =
			LayoutRevisionLocalServiceUtil.fetchLatestLayoutRevision(
				getLayoutSetBranchId(), layout.getPlid());

		if (layoutRevision == null) {
			return layout.getName(locale);
		}

		return layoutRevision.getName(locale);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutRevisionImpl.class);

	private Long _faviconFileEntryGroupId;
	private String _faviconURL;
	private Image _iconImage;
	private UnicodeProperties _typeSettingsUnicodeProperties;

}
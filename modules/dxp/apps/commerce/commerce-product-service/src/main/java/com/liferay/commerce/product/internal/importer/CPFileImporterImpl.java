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

package com.liferay.commerce.product.internal.importer;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.ThemeSetting;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.impl.ThemeSettingImpl;

import java.io.InputStream;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CPFileImporterImpl implements CPFileImporter {

	public static final String IMG_TAG =
		"<img alt='' src='%s' data-fileentryid='%s' />";

	@Override
	public void cleanLayouts(ServiceContext serviceContext)
		throws PortalException {

		_layoutLocalService.deleteLayouts(
			serviceContext.getScopeGroupId(), true, serviceContext);

		_layoutLocalService.deleteLayouts(
			serviceContext.getScopeGroupId(), false, serviceContext);
	}

	@Override
	public void createJournalArticles(
			JSONArray journalArticleJSONArray, ClassLoader classLoader,
			String dependenciesFilePath, ServiceContext serviceContext,
			ThemeDisplay themeDisplay)
		throws Exception {

		for (int i = 0; i < journalArticleJSONArray.length(); i++) {
			JSONObject journalArticleJSONObject =
				journalArticleJSONArray.getJSONObject(i);

			createJournalArticle(
				journalArticleJSONObject, classLoader, dependenciesFilePath,
				serviceContext, themeDisplay);
		}
	}

	@Override
	public void createLayouts(
			JSONArray jsonArray, ClassLoader classLoader,
			String dependenciesFilePath, ServiceContext serviceContext)
		throws Exception {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			createLayout(jsonObject, serviceContext);
		}
	}

	@Override
	public void updateLookAndFeel(String themeId, ServiceContext serviceContext)
		throws PortalException {

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			serviceContext.getScopeGroupId(), false);

		UnicodeProperties typeSettingProperties =
			layoutSet.getSettingsProperties();

		Theme theme = _themeLocalService.fetchTheme(
			serviceContext.getCompanyId(), themeId);

		if (theme == null) {
			if (_log.isInfoEnabled()) {
				_log.info("No Theme registered with themeId: " + themeId);
			}

			return;
		}

		setThemeSettingProperties(theme, typeSettingProperties);

		_layoutSetLocalService.updateLookAndFeel(
			serviceContext.getScopeGroupId(), false, themeId, StringPool.BLANK,
			StringPool.BLANK);
	}

	protected void addLayoutPortlets(
			JSONArray jsonArray, Layout layout, String layoutTemplateId,
			ServiceContext serviceContext)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			UserConstants.USER_ID_DEFAULT, layoutTemplateId, false);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject portletJSONObject = jsonArray.getJSONObject(i);

			String portletId = addPortletId(
				portletJSONObject, layoutTypePortlet, serviceContext);

			JSONObject portletPreferencesJSONObject =
				portletJSONObject.getJSONObject("portletPreferences");

			setPortletPreferences(
				portletPreferencesJSONObject, layout, portletId,
				serviceContext);
		}
	}

	protected String addPortletId(
			JSONObject jsonObject, LayoutTypePortlet layoutTypePortlet,
			ServiceContext serviceContext)
		throws Exception {

		String layoutColumnId = jsonObject.getString("layoutColumnId");
		int layoutColumnPos = jsonObject.getInt("layoutColumnPos");
		String portletName = jsonObject.getString("portletName");

		return layoutTypePortlet.addPortletId(
			serviceContext.getUserId(), portletName, layoutColumnId,
			layoutColumnPos, false);
	}

	protected JournalArticle createJournalArticle(
			JSONObject jsonObject, ClassLoader classLoader,
			String dependenciesFilePath, ServiceContext serviceContext,
			ThemeDisplay themeDisplay)
		throws Exception {

		String articleId = jsonObject.getString("articleId");
		String content = jsonObject.getString("content");
		String ddmStructureKey = jsonObject.getString("ddmStructureKey");
		String ddmTemplateKey = jsonObject.getString("ddmTemplateKey");
		String description = jsonObject.getString("description");
		String title = jsonObject.getString("title");

		articleId = StringUtil.toUpperCase(StringUtil.trim(articleId));

		JournalArticle journalArticle =
			_journalArticleLocalService.fetchArticle(
				serviceContext.getScopeGroupId(), articleId);

		if (journalArticle != null) {
			return journalArticle;
		}

		String ddmStructureFileName =
			dependenciesFilePath + ddmStructureKey + ".xml";

		fetchOrAddDDMStructure(
			ddmStructureKey, classLoader, ddmStructureFileName, serviceContext);

		Map<Locale, String> titleMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		titleMap.put(Locale.US, title);
		descriptionMap.put(Locale.US, description);

		content = getNormalizedContent(
			content, classLoader, dependenciesFilePath, serviceContext,
			themeDisplay);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		int displayDateMonth = displayCalendar.get(Calendar.MONTH);
		int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = displayCalendar.get(Calendar.YEAR);
		int displayDateHour = displayCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
		int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		return _journalArticleLocalService.addArticle(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0L,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0L, articleId, false,
			1, titleMap, descriptionMap, content, ddmStructureKey,
			ddmTemplateKey, StringPool.BLANK, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute, 0, 0, 0, 0, 0,
			true, 0, 0, 0, 0, 0, true, true, false, StringPool.BLANK, null,
			null, StringPool.BLANK, serviceContext);
	}

	protected void createLayout(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws Exception {

		String layoutTemplateId = jsonObject.getString("layoutTemplateId");
		String name = jsonObject.getString("name");

		String friendlyURL = StringUtil.toLowerCase(name);

		friendlyURL = friendlyURL.trim();

		friendlyURL = FriendlyURLNormalizerUtil.normalize(friendlyURL);

		friendlyURL = CharPool.SLASH + friendlyURL;

		Layout layout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, name,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, true, friendlyURL,
			serviceContext);

		JSONArray portletsJSONArray = jsonObject.getJSONArray("portlets");

		addLayoutPortlets(
			portletsJSONArray, layout, layoutTemplateId, serviceContext);

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	protected void deleteThemeSettingsProperties(
		UnicodeProperties typeSettingsProperties, String device) {

		String keyPrefix = ThemeSettingImpl.namespaceProperty(device);

		Set<String> keys = typeSettingsProperties.keySet();

		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (key.startsWith(keyPrefix)) {
				iterator.remove();
			}
		}
	}

	protected DDMStructure fetchOrAddDDMStructure(
			String ddmStructureKey, ClassLoader classLoader,
			String ddmStructureFileName, ServiceContext serviceContext)
		throws Exception {

		long classNameId = _portal.getClassNameId(JournalArticle.class);

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			serviceContext.getScopeGroupId(), classNameId, ddmStructureKey,
			true);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		_defaultDDMStructureHelper.addDDMStructures(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			classNameId, classLoader, ddmStructureFileName, serviceContext);

		return _ddmStructureLocalService.fetchStructure(
			serviceContext.getScopeGroupId(), classNameId, ddmStructureKey,
			true);
	}

	protected FileEntry fetchOrAddFileEntry(
			ClassLoader classLoader, String dependenciesFilePath,
			String fileName, ServiceContext serviceContext)
		throws Exception {

		FileEntry fileEntry = null;

		try {
			fileEntry = _dlAppLocalService.getFileEntry(
				serviceContext.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		if (fileEntry != null) {
			return fileEntry;
		}

		String filePath = dependenciesFilePath + fileName;

		InputStream is = classLoader.getResourceAsStream(filePath);

		String mimeType = MimeTypesUtil.getContentType(fileName);

		byte[] byteArray = FileUtil.getBytes(is);

		return _dlAppLocalService.addFileEntry(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName, mimeType,
			fileName, StringPool.BLANK, StringPool.BLANK, byteArray,
			serviceContext);
	}

	protected long getAssetEntryId(
			String articleId, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNull(articleId)) {
			return 0;
		}

		JournalArticle journalArticle =
			_journalArticleLocalService.fetchArticle(
				serviceContext.getScopeGroupId(), articleId);

		if (journalArticle == null) {
			return 0;
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		if (assetEntry == null) {
			return 0;
		}

		return assetEntry.getEntryId();
	}

	protected String getNormalizedContent(
			String content, ClassLoader classLoader,
			String dependenciesFilePath, ServiceContext serviceContext,
			ThemeDisplay themeDisplay)
		throws Exception {

		Set<String> placeHolders = new HashSet<>();

		Matcher matcher = _placeholderPattern.matcher(content);

		while (matcher.find()) {
			placeHolders.add(matcher.group());
		}

		for (String placeHolder : placeHolders) {
			String fileName = placeHolder.substring(
				2, placeHolder.length() - 2);

			FileEntry fileEntry = fetchOrAddFileEntry(
				classLoader, dependenciesFilePath, fileName, serviceContext);

			String previewURL = DLUtil.getDownloadURL(
				fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
				StringPool.BLANK, false, false);

			String imgHtmlTag = String.format(
				IMG_TAG, previewURL,
				String.valueOf(fileEntry.getFileEntryId()));

			content = content.replace(placeHolder, imgHtmlTag);
		}

		return content;
	}

	protected void setPortletPreferences(
			JSONObject jsonObject, Layout layout, String portletId,
			ServiceContext serviceContext)
		throws Exception {

		if (jsonObject == null) {
			return;
		}

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				layout.getGroupId(), 0, layout, portletId, false);

		PortletPreferences portletSetup =
			_portletPreferencesLocalService.getPreferences(
				portletPreferencesIds);

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			String value = jsonObject.getString(key);

			if (key.equals("groupId")) {
				value = String.valueOf(serviceContext.getScopeGroupId());
			}
			else if (key.equals("assetEntryId")) {
				String articleId = jsonObject.getString("articleId");

				long assetEntryId = getAssetEntryId(articleId, serviceContext);

				value = String.valueOf(assetEntryId);
			}

			portletSetup.setValue(key, value);
		}

		portletSetup.store();
	}

	protected void setThemeSettingProperties(
		Theme theme, UnicodeProperties typeSettingProperties) {

		String device = "regular";

		deleteThemeSettingsProperties(typeSettingProperties, device);

		Map<String, ThemeSetting> themeSettings =
			theme.getConfigurableSettings();

		for (Map.Entry<String, ThemeSetting> entry : themeSettings.entrySet()) {
			String key = entry.getKey();
			ThemeSetting themeSetting = entry.getValue();

			String value = themeSetting.getValue();

			if (!value.equals(themeSetting.getValue())) {
				typeSettingProperties.setProperty(
					ThemeSettingImpl.namespaceProperty(device, key), value);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPFileImporterImpl.class);

	private static final Pattern _placeholderPattern = Pattern.compile(
		"\\[%[^\\[%]+%\\]", Pattern.CASE_INSENSITIVE);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private ThemeLocalService _themeLocalService;

}
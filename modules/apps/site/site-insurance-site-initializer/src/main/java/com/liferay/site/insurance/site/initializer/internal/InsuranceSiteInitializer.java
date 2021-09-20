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

package com.liferay.site.insurance.site.initializer.internal;

import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.insurance.site.initializer.internal.util.ImagesImporterUtil;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import java.io.File;

import java.net.URL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = "site.initializer.key=" + InsuranceSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class InsuranceSiteInitializer implements SiteInitializer {

	public static final String KEY = "site-insurance-site-initializer";

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return "Raylife";
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			_createServiceContext(groupId);

			_addDLFileEntries();

			_addDDMStructures();

			_addDDMTemplates();

			_addJournalArticles(_addJournalFolders());

			_addAssetListEntries();
			_addFragmentEntries();
			_addSiteNavigationMenus();

			_addStyleBookEntries();
			_setDefaultStyleBookEntry();

			_addLayoutPageTemplateEntries();

			_addLayouts();

			_updateLayoutSetLookAndFeel("private");
			_updateLayoutSetLookAndFeel("public");
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();
	}

	private void _addAssetListEntries() throws Exception {
		_assetListEntryLocalService.addDynamicAssetListEntry(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			"Policies", _getDynamicCollectionTypeSettings("POLICY", null),
			_serviceContext);

		_assetListEntryLocalService.addDynamicAssetListEntry(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			"Closed Claims",
			_getDynamicCollectionTypeSettings("CLAIM", new String[] {"closed"}),
			_serviceContext);

		_assetListEntryLocalService.addDynamicAssetListEntry(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			"Open Claims",
			_getDynamicCollectionTypeSettings("CLAIM", new String[] {"open"}),
			_serviceContext);
	}

	private Layout _addContentLayout(
			JSONObject pageJSONObject, JSONObject pageDefinitionJSONObject,
			Map<String, String> resourcesMap)
		throws Exception {

		String type = StringUtil.toLowerCase(pageJSONObject.getString("type"));

		Layout layout = _layoutLocalService.addLayout(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			pageJSONObject.getBoolean("private"),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), pageJSONObject.getString("name")
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			type, null, false, false, new HashMap<>(), _serviceContext);

		Layout draftLayout = layout.fetchDraftLayout();

		_importPageDefinition(draftLayout, pageDefinitionJSONObject);

		if (Objects.equals(LayoutConstants.TYPE_COLLECTION, type)) {
			UnicodeProperties typeSettingsUnicodeProperties =
				draftLayout.getTypeSettingsProperties();

			typeSettingsUnicodeProperties.setProperty(
				"collectionPK",
				resourcesMap.get(pageJSONObject.getString("collectionKey")));
			typeSettingsUnicodeProperties.setProperty(
				"collectionType",
				"com.liferay.item.selector.criteria." +
					"InfoListItemSelectorReturnType");

			draftLayout = _layoutLocalService.updateLayout(
				_serviceContext.getScopeGroupId(),
				draftLayout.isPrivateLayout(), draftLayout.getLayoutId(),
				typeSettingsUnicodeProperties.toString());
		}

		JSONObject settingsJSONObject = pageDefinitionJSONObject.getJSONObject(
			"settings");

		if (settingsJSONObject != null) {
			draftLayout = _updateLayoutTypeSettings(
				draftLayout, settingsJSONObject);
		}

		layout = _layoutCopyHelper.copyLayout(draftLayout, layout);

		_layoutLocalService.updateStatus(
			layout.getUserId(), layout.getPlid(),
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		_layoutLocalService.updateStatus(
			layout.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		return layout;
	}

	private void _addDDMStructures() throws Exception {
		Enumeration<URL> enumeration = _bundle.findEntries(
			_PATH + "/ddm-structures", StringPool.STAR, false);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			Class<?> clazz = getClass();

			_defaultDDMStructureHelper.addDDMStructures(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class),
				clazz.getClassLoader(), url.getPath(), _serviceContext);
		}
	}

	private void _addDDMTemplates() throws Exception {
		long resourceClassNameId = _portal.getClassNameId(JournalArticle.class);

		Enumeration<URL> enumeration = _bundle.findEntries(
			_PATH + "/ddm-templates", "ddm_template.json", true);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			JSONObject ddmTemplateJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(url.openStream()));

			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchStructure(
					_serviceContext.getScopeGroupId(), resourceClassNameId,
					ddmTemplateJSONObject.getString("ddmStructureKey"));

			_ddmTemplateLocalService.addTemplate(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				_portal.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(), resourceClassNameId,
				ddmTemplateJSONObject.getString("ddmTemplateKey"),
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(),
					ddmTemplateJSONObject.getString("name")
				).build(),
				null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
				TemplateConstants.LANG_TYPE_FTL, _read("ddm_template.ftl", url),
				false, false, null, null, _serviceContext);
		}
	}

	private void _addDLFileEntries() throws Exception {
		URL url = _bundle.getEntry("/images.zip");

		File file = FileUtil.createTempFile(url.openStream());

		_fileEntries = ImagesImporterUtil.importFile(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			file);
	}

	private void _addFragmentEntries() throws Exception {
		URL url = _bundle.getEntry("/fragments.zip");

		File file = FileUtil.createTempFile(url.openStream());

		_fragmentsImporter.importFragmentEntries(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(), 0,
			file, false);
	}

	private void _addJournalArticles(List<JournalFolder> journalFolders)
		throws Exception {

		Map<String, String> fileEntriesMap = _getFileEntriesMap();

		Map<String, Long> journalFolderMap = new HashMap<>();

		for (JournalFolder journalFolder : journalFolders) {
			journalFolderMap.put(
				journalFolder.getName(), journalFolder.getFolderId());
		}

		Enumeration<URL> enumeration = _bundle.findEntries(
			_PATH + "/journal-articles", "journal_article.json", true);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String content = StringUtil.read(url.openStream());

			JSONObject journalArticleJSONObject =
				JSONFactoryUtil.createJSONObject(content);

			Map<Locale, String> titleMap = Collections.singletonMap(
				LocaleUtil.getSiteDefault(),
				journalArticleJSONObject.getString("name"));

			Calendar calendar = CalendarFactoryUtil.getCalendar(
				_serviceContext.getTimeZone());

			int displayDateMonth = calendar.get(Calendar.MONTH);
			int displayDateDay = calendar.get(Calendar.DAY_OF_MONTH);
			int displayDateYear = calendar.get(Calendar.YEAR);
			int displayDateHour = calendar.get(Calendar.HOUR_OF_DAY);
			int displayDateMinute = calendar.get(Calendar.MINUTE);

			List<String> assetTagNames = new ArrayList<>();

			JSONArray assetTagNamesJSONArray =
				journalArticleJSONObject.getJSONArray("tags");

			if (assetTagNamesJSONArray != null) {
				for (int i = 0; i < assetTagNamesJSONArray.length(); i++) {
					assetTagNames.add(assetTagNamesJSONArray.getString(i));
				}
			}

			_serviceContext.setAssetTagNames(
				assetTagNames.toArray(new String[0]));

			_journalArticleLocalService.addArticle(
				null, _serviceContext.getUserId(),
				_serviceContext.getScopeGroupId(),
				journalFolderMap.getOrDefault(
					journalArticleJSONObject.getString("folder"),
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID),
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0,
				journalArticleJSONObject.getString("articleId"), false, 1,
				titleMap, null, titleMap,
				StringUtil.replace(
					_read("journal_article.xml", url), "[$", "$]",
					fileEntriesMap),
				journalArticleJSONObject.getString("ddmStructureKey"),
				journalArticleJSONObject.getString("ddmTemplateKey"), null,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, 0, 0, 0, 0, 0, true, 0, 0,
				0, 0, 0, true, true, false, null, null, null, null,
				_serviceContext);

			_serviceContext.setAssetTagNames(null);
		}
	}

	private List<JournalFolder> _addJournalFolders() throws Exception {
		List<JournalFolder> journalFolders = new ArrayList<>();

		JSONArray journalFoldersJSONArray = JSONFactoryUtil.createJSONArray(
			_read("/journal-folders/journal_folders.json"));

		for (int i = 0; i < journalFoldersJSONArray.length(); i++) {
			JSONObject jsonObject = journalFoldersJSONArray.getJSONObject(i);

			JournalFolder journalFolder = _journalFolderLocalService.addFolder(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				jsonObject.getString("name"), null, _serviceContext);

			journalFolders.add(journalFolder);
		}

		return journalFolders;
	}

	private void _addLayoutPageTemplateEntries() throws Exception {
		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		keyValuePairs.add(
			new KeyValuePair(
				"display-page-templates",
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_DISPLAY_PAGE_TEMPLATE));
		keyValuePairs.add(
			new KeyValuePair(
				"master-pages",
				LayoutPageTemplateExportImportConstants.FILE_NAME_MASTER_PAGE));

		Map<String, String> numberValuesMap = new HashMap<>();

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getStructures(
				_serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class.getName()));

		for (DDMStructure ddmStructure : ddmStructures) {
			numberValuesMap.put(
				StringUtil.toUpperCase(ddmStructure.getStructureKey()),
				String.valueOf(ddmStructure.getStructureId()));
		}

		File file = _generateZipFile(
			keyValuePairs, numberValuesMap,
			HashMapBuilder.put(
				"CUSTOMER_PORTAL_SITE_NAVIGATION_MENU_ID",
				String.valueOf(
					_siteNavigationMenuMap.get("Customer Portal Menu"))
			).put(
				"LAYOUT_URL_CLAIMS", _getPrivateFriendlyURL("claims")
			).put(
				"LAYOUT_URL_POLICIES", _getPrivateFriendlyURL("policies")
			).put(
				"PUBLIC_SITE_NAVIGATION_MENU_ID",
				String.valueOf(_siteNavigationMenuMap.get("Public Menu"))
			).put(
				"SCOPE_GROUP_ID",
				String.valueOf(_serviceContext.getScopeGroupId())
			).build());

		_layoutPageTemplatesImporter.importFile(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			file, false);
	}

	private void _addLayouts() throws Exception {
		Map<String, String> resourcesMap = _getResourcesMap();

		JSONArray layoutsJSONArray = JSONFactoryUtil.createJSONArray(
			_read("/layouts/layouts.json"));

		for (int i = 0; i < layoutsJSONArray.length(); i++) {
			JSONObject jsonObject = layoutsJSONArray.getJSONObject(i);

			String path = jsonObject.getString("path");

			JSONObject pageJSONObject = JSONFactoryUtil.createJSONObject(
				_read(StringBundler.concat("/layouts/", path, "/page.json")));

			String type = StringUtil.toLowerCase(
				pageJSONObject.getString("type"));

			Layout layout = null;

			if (Objects.equals(LayoutConstants.TYPE_CONTENT, type) ||
				Objects.equals(LayoutConstants.TYPE_COLLECTION, type)) {

				String pageDefinitionJSON = StringUtil.replace(
					_read(
						StringBundler.concat(
							"/layouts/", path, "/page-definition.json")),
					"\"[$", "$]\"", resourcesMap);

				layout = _addContentLayout(
					pageJSONObject,
					JSONFactoryUtil.createJSONObject(pageDefinitionJSON),
					resourcesMap);
			}
			else {
				layout = _addWidgetLayout(pageJSONObject);
			}

			_addNavigationMenuItems(layout);
		}
	}

	private void _addNavigationMenuItems(Layout layout) throws Exception {
		if (layout == null) {
			return;
		}

		List<SiteNavigationMenu> siteNavigationMenus =
			_layoutsSiteNavigationMenuMap.get(
				StringUtil.toLowerCase(
					layout.getName(LocaleUtil.getSiteDefault())));

		if (ListUtil.isEmpty(siteNavigationMenus)) {
			return;
		}

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				SiteNavigationMenuItemTypeConstants.LAYOUT);

		for (SiteNavigationMenu siteNavigationMenu : siteNavigationMenus) {
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT,
				siteNavigationMenuItemType.getTypeSettingsFromLayout(layout),
				_serviceContext);
		}
	}

	private void _addSiteNavigationMenus() throws Exception {
		_layoutsSiteNavigationMenuMap = new HashMap<>();
		_siteNavigationMenuMap = new HashMap<>();

		JSONArray siteNavigationMenuJSONArray = JSONFactoryUtil.createJSONArray(
			_read("/site-navigation-menus/site-navigation-menus.json"));

		for (int i = 0; i < siteNavigationMenuJSONArray.length(); i++) {
			JSONObject jsonObject = siteNavigationMenuJSONArray.getJSONObject(
				i);

			String name = jsonObject.getString("name");

			SiteNavigationMenu siteNavigationMenu =
				_siteNavigationMenuLocalService.addSiteNavigationMenu(
					_serviceContext.getUserId(),
					_serviceContext.getScopeGroupId(), name, _serviceContext);

			_siteNavigationMenuMap.put(
				name, siteNavigationMenu.getSiteNavigationMenuId());

			JSONArray pagesJSONArray = jsonObject.getJSONArray("pages");

			for (int j = 0; j < pagesJSONArray.length(); j++) {
				List<SiteNavigationMenu> siteNavigationMenus =
					_layoutsSiteNavigationMenuMap.computeIfAbsent(
						pagesJSONArray.getString(j), key -> new ArrayList<>());

				siteNavigationMenus.add(siteNavigationMenu);
			}
		}
	}

	private void _addStyleBookEntries() throws Exception {
		URL url = _bundle.getEntry("/style-books.zip");

		File file = FileUtil.createTempFile(url.openStream());

		_styleBookEntryZipProcessor.importStyleBookEntries(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			file, false);
	}

	private Layout _addWidgetLayout(JSONObject jsonObject) throws Exception {
		String name = jsonObject.getString("name");

		return _layoutLocalService.addLayout(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), name
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_PORTLET, null, false, false, new HashMap<>(),
			_serviceContext);
	}

	private void _addZipWriterEntry(
			ZipWriter zipWriter, URL url, Map<String, String> numberValuesMap,
			Map<String, String> stringValuesMap)
		throws Exception {

		if (_isReplaceableTokenFileExtension(url)) {
			String content = StringUtil.read(url.openStream());

			content = StringUtil.replace(
				content, "\"[£", "£]\"", numberValuesMap);

			content = StringUtil.replace(content, "[$", "$]", stringValuesMap);

			zipWriter.addEntry(
				StringUtil.removeSubstring(url.getPath(), _PATH), content);
		}
		else {
			zipWriter.addEntry(
				StringUtil.removeSubstring(url.getPath(), _PATH),
				url.openStream());
		}
	}

	private void _createServiceContext(long groupId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Locale locale = LocaleUtil.getSiteDefault();

		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));

		serviceContext.setScopeGroupId(groupId);

		User user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());

		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		_serviceContext = serviceContext;
	}

	private File _generateZipFile(
			List<KeyValuePair> pathKeyValuePairs,
			Map<String, String> numberValuesMap,
			Map<String, String> stringValuesMap)
		throws Exception {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		for (KeyValuePair pathKeyValuePair : pathKeyValuePairs) {
			Enumeration<URL> enumeration = _bundle.findEntries(
				StringBundler.concat(
					_PATH, StringPool.FORWARD_SLASH, pathKeyValuePair.getKey(),
					StringPool.FORWARD_SLASH),
				pathKeyValuePair.getValue(), true);

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				_populateZipWriter(
					zipWriter, url, numberValuesMap, stringValuesMap);
			}
		}

		return zipWriter.getFile();
	}

	private String _getDynamicCollectionTypeSettings(
			String ddmStructureKey, String[] assetTagNames)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_serviceContext.getScopeGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			ddmStructureKey);

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.create(
			true
		).put(
			"anyAssetType",
			String.valueOf(_portal.getClassNameId(JournalArticle.class))
		).put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			String.valueOf(ddmStructure.getStructureId())
		).put(
			"classNameIds", JournalArticle.class.getName()
		).put(
			"classTypeIdsJournalArticleAssetRendererFactory",
			String.valueOf(ddmStructure.getStructureId())
		).put(
			"groupIds", String.valueOf(_serviceContext.getScopeGroupId())
		).put(
			"orderByColumn1", "modifiedDate"
		).put(
			"orderByColumn2", "title"
		).put(
			"orderByType1", "ASC"
		).put(
			"orderByType2", "ASC"
		).build();

		if (ArrayUtil.isNotEmpty(assetTagNames)) {
			for (int i = 0; i < assetTagNames.length; i++) {
				unicodeProperties.put("queryAndOperator" + i, "true");
				unicodeProperties.put("queryContains" + i, "true");
				unicodeProperties.put("queryName" + i, "assetTags");
				unicodeProperties.put("queryValues" + i, assetTagNames[i]);
			}
		}

		return unicodeProperties.toString();
	}

	private Map<String, String> _getFileEntriesMap() throws Exception {
		Map<String, String> fileEntriesMap = new HashMap<>();

		for (FileEntry fileEntry : _fileEntries) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(fileEntry));

			jsonObject.put("alt", StringPool.BLANK);

			fileEntriesMap.put(
				"JSON_" + fileEntry.getFileName(), jsonObject.toString());

			fileEntriesMap.put(
				"URL_" + fileEntry.getFileName(),
				_dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false));
		}

		return fileEntriesMap;
	}

	private String _getPrivateFriendlyURL(String layoutName) throws Exception {
		Group scopeGroup = _serviceContext.getScopeGroup();

		return StringBundler.concat(
			_portal.getPathFriendlyURLPrivateGroup(),
			scopeGroup.getFriendlyURL(), StringPool.FORWARD_SLASH, layoutName);
	}

	private Map<String, String> _getResourcesMap() {
		Map<String, String> resourcesMap = new HashMap<>();

		List<JournalArticle> articles = _journalArticleLocalService.getArticles(
			_serviceContext.getScopeGroupId());

		for (JournalArticle article : articles) {
			resourcesMap.put(
				article.getArticleId(),
				String.valueOf(article.getResourcePrimKey()));
		}

		List<AssetListEntry> assetListEntries =
			_assetListEntryLocalService.getAssetListEntries(
				_serviceContext.getScopeGroupId());

		for (AssetListEntry assetListEntry : assetListEntries) {
			resourcesMap.put(
				StringUtil.toUpperCase(assetListEntry.getAssetListEntryKey()),
				String.valueOf(assetListEntry.getAssetListEntryId()));
		}

		return resourcesMap;
	}

	private String _getThemeId(long companyId, String themeName) {
		List<Theme> themes = ListUtil.filter(
			_themeLocalService.getThemes(companyId),
			theme -> Objects.equals(theme.getName(), themeName));

		if (ListUtil.isNotEmpty(themes)) {
			Theme theme = themes.get(0);

			return theme.getThemeId();
		}

		return null;
	}

	private void _importPageDefinition(
			Layout draftLayout, JSONObject pageDefinitionJSONObject)
		throws Exception {

		if (!pageDefinitionJSONObject.has("pageElement")) {
			return;
		}

		JSONObject jsonObject = pageDefinitionJSONObject.getJSONObject(
			"pageElement");

		String type = jsonObject.getString("type");

		if (Validator.isNull(type) || !Objects.equals(type, "Root")) {
			return;
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					draftLayout.getGroupId(), draftLayout.getPlid(), true);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));

		JSONArray pageElementsJSONArray = jsonObject.getJSONArray(
			"pageElements");

		for (int j = 0; j < pageElementsJSONArray.length(); j++) {
			_layoutPageTemplatesImporter.importPageElement(
				draftLayout, layoutStructure, layoutStructure.getMainItemId(),
				pageElementsJSONArray.getString(j), j);
		}
	}

	private boolean _isReplaceableTokenFileExtension(URL url) {
		String filePath = url.getPath();

		int index = filePath.lastIndexOf(".");

		String extension = filePath.substring(index);

		return ArrayUtil.contains(_REPLACEABLE_TOKEN_FILE_EXTENSION, extension);
	}

	private void _populateZipWriter(
			ZipWriter zipWriter, URL url, Map<String, String> numberValuesMap,
			Map<String, String> stringValuesMap)
		throws Exception {

		zipWriter.addEntry(
			StringUtil.removeSubstring(url.getFile(), _PATH), url.openStream());

		Enumeration<URL> enumeration = _bundle.findEntries(
			FileUtil.getPath(url.getPath()), StringPool.STAR, true);

		while (enumeration.hasMoreElements()) {
			_addZipWriterEntry(
				zipWriter, enumeration.nextElement(), numberValuesMap,
				stringValuesMap);
		}
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(clazz.getClassLoader(), _PATH + fileName);
	}

	private String _read(String fileName, URL url) throws Exception {
		String path = url.getPath();

		URL entryURL = _bundle.getEntry(
			path.substring(0, path.lastIndexOf("/") + 1) + fileName);

		return StringUtil.read(entryURL.openStream());
	}

	private void _setDefaultStyleBookEntry() throws PortalException {
		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.fetchStyleBookEntry(
				_serviceContext.getScopeGroupId(), "raylife");

		_styleBookEntryLocalService.updateDefaultStyleBookEntry(
			styleBookEntry.getStyleBookEntryId(), true);
	}

	private void _updateLayoutSetLookAndFeel(String type) throws Exception {
		boolean privateLayoutSet = false;

		if (Objects.equals(type, "private")) {
			privateLayoutSet = true;
		}

		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			_serviceContext.getScopeGroupId(), privateLayoutSet);

		UnicodeProperties settingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		settingsUnicodeProperties.putAll(
			UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				_read("/layout-set/" + type + "/theme.properties")
			).build());

		_layoutSetLocalService.updateSettings(
			_serviceContext.getScopeGroupId(), privateLayoutSet,
			settingsUnicodeProperties.toString());

		_layoutSetLocalService.updateLookAndFeel(
			_serviceContext.getScopeGroupId(), privateLayoutSet,
			layoutSet.getThemeId(), layoutSet.getColorSchemeId(),
			_read("/layout-set/" + type + "/css.css"));

		URL logoURL = _bundle.getEntry(
			StringBundler.concat(_PATH, "/layout-set/", type, "/logo.png"));

		if (logoURL != null) {
			File file = FileUtil.createTempFile(logoURL.openStream());

			_layoutSetLocalService.updateLogo(
				_serviceContext.getScopeGroupId(), privateLayoutSet, true,
				file);
		}
	}

	private Layout _updateLayoutTypeSettings(
			Layout layout, JSONObject settingsJSONObject)
		throws Exception {

		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		JSONObject themeSettingsJSONObject = settingsJSONObject.getJSONObject(
			"themeSettings");

		Set<Map.Entry<String, String>> entrySet = unicodeProperties.entrySet();

		entrySet.removeIf(
			entry -> {
				String key = entry.getKey();

				return key.startsWith("lfr-theme:");
			});

		if (themeSettingsJSONObject != null) {
			for (String key : themeSettingsJSONObject.keySet()) {
				unicodeProperties.put(
					key, themeSettingsJSONObject.getString(key));
			}

			layout = _layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), unicodeProperties.toString());

			layout.setTypeSettingsProperties(unicodeProperties);
		}

		String themeId = layout.getThemeId();

		String themeName = settingsJSONObject.getString("themeName");

		if (Validator.isNotNull(themeName)) {
			themeId = _getThemeId(layout.getCompanyId(), themeName);
		}

		String colorSchemeName = settingsJSONObject.getString(
			"colorSchemeName", layout.getColorSchemeId());

		String css = settingsJSONObject.getString("css", layout.getCss());

		layout = _layoutLocalService.updateLookAndFeel(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			themeId, colorSchemeName, css);

		JSONObject masterPageJSONObject = settingsJSONObject.getJSONObject(
			"masterPage");

		if (masterPageJSONObject != null) {
			LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(
						layout.getGroupId(),
						masterPageJSONObject.getString("key"));

			if (masterLayoutPageTemplateEntry != null) {
				layout = _layoutLocalService.updateMasterLayoutPlid(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(),
					masterLayoutPageTemplateEntry.getPlid());
			}
		}

		return layout;
	}

	private static final String _PATH =
		"com/liferay/site/insurance/site/initializer/internal/dependencies";

	private static final String[] _REPLACEABLE_TOKEN_FILE_EXTENSION = {
		".ftl", ".json", ".xml"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		InsuranceSiteInitializer.class);

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	private Bundle _bundle;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private DLURLHelper _dlURLHelper;

	private List<FileEntry> _fileEntries;

	@Reference
	private FragmentsImporter _fragmentsImporter;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalFolderLocalService _journalFolderLocalService;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService _layoutPageTemplateEntryService;

	@Reference
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	private Map<String, List<SiteNavigationMenu>> _layoutsSiteNavigationMenuMap;

	@Reference
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.insurance.site.initializer)"
	)
	private ServletContext _servletContext;

	@Reference
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Reference
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	private Map<String, Long> _siteNavigationMenuMap;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Reference
	private StyleBookEntryZipProcessor _styleBookEntryZipProcessor;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
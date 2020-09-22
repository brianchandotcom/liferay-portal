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

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
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
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
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

import java.io.File;
import java.io.IOException;

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
		return _NAME;
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			_createServiceContext(groupId);

			_addDDMStructures();
			_addDDMTemplates();

			_addImages();

			_addJournalFolders();
			_addJournalArticles();

			_addFragments();

			_addCollections();

			_addSiteNavigationMenus();

			_addMasterPages();
			_addDisplayPageTemplates();
			_addLayouts();

			_updateLookAndFeel();
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

	private void _addCollections() throws Exception {
		_assetListEntryLocalService.addDynamicAssetListEntry(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			"Policies", _getDynamicCollectionTypeSettings("POLICY"),
			_serviceContext);

		_assetListEntryLocalService.addDynamicAssetListEntry(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			"Closed Claims", _getDynamicCollectionTypeSettings("CLAIM"),
			_serviceContext);

		_assetListEntryLocalService.addDynamicAssetListEntry(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			"Open Claims", _getDynamicCollectionTypeSettings("CLAIM"),
			_serviceContext);
	}

	private Layout _addContentLayout(
			JSONObject pageJSONObject, JSONObject pageDefinitionJSONObject,
			String type, Map<String, String> resourcesMap)
		throws Exception {

		String name = pageJSONObject.getString("name");

		Layout layout = _layoutLocalService.addLayout(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			pageJSONObject.getBoolean("private"),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), name
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

		draftLayout = _updateLayoutSettings(
			draftLayout, pageDefinitionJSONObject.getJSONObject("settings"));

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
		JSONArray ddmTemplatesJSONArray = JSONFactoryUtil.createJSONArray(
			_readFile("/ddm-templates/ddm_templates.json"));

		for (int i = 0; i < ddmTemplatesJSONArray.length(); i++) {
			JSONObject jsonObject = ddmTemplatesJSONArray.getJSONObject(i);

			String ddmStructureKey = jsonObject.getString("ddmStructureKey");

			long resourceClassNameId = _portal.getClassNameId(
				JournalArticle.class);

			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchStructure(
					_serviceContext.getScopeGroupId(), resourceClassNameId,
					ddmStructureKey);

			_ddmTemplateLocalService.addTemplate(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				_portal.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(), resourceClassNameId,
				jsonObject.getString("ddmTemplateKey"),
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(), jsonObject.getString("name")
				).build(),
				null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
				TemplateConstants.LANG_TYPE_FTL,
				_readFile(jsonObject.getString("contentPath")), false, false,
				null, null, _serviceContext);
		}
	}

	private void _addDisplayPageTemplates() throws Exception {
		Map<String, String> valuesMap = new HashMap<>();

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getStructures(
				_serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class.getName()));

		for (DDMStructure ddmStructure : ddmStructures) {
			valuesMap.put(
				StringUtil.toUpperCase(ddmStructure.getStructureKey()),
				String.valueOf(ddmStructure.getStructureId()));
		}

		File file = _generateZipFile(
			"display-page-templates",
			LayoutPageTemplateExportImportConstants.
				FILE_NAME_DISPLAY_PAGE_TEMPLATE,
			valuesMap);

		_layoutPageTemplatesImporter.importFile(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			file, false);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_serviceContext.getScopeGroupId(), "policy",
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), true);

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				_serviceContext.getScopeGroupId(), "claim",
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), true);
	}

	private void _addFragments() throws Exception {
		URL url = _bundle.getEntry("/fragments.zip");

		File file = FileUtil.createTempFile(url.openStream());

		_fragmentsImporter.importFile(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(), 0,
			file, false);
	}

	private void _addImages() throws Exception {
		URL url = _bundle.getEntry("/images.zip");

		File file = FileUtil.createTempFile(url.openStream());

		_fileEntries = ImagesImporterUtil.importFile(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			file);
	}

	private void _addJournalArticles() throws Exception {
		JSONArray journalArticlesJSONArray = JSONFactoryUtil.createJSONArray(
			_readFile("/journal-articles/journal_articles.json"));

		Map<String, Long> journalFolderMap = new HashMap<>();

		for (JournalFolder journalFolder : _journalFolders) {
			journalFolderMap.put(
				journalFolder.getName(), journalFolder.getFolderId());
		}

		Map<String, String> fileEntriesMap = _getFileEntriesMap();

		for (int i = 0; i < journalArticlesJSONArray.length(); i++) {
			JSONObject jsonObject = journalArticlesJSONArray.getJSONObject(i);

			String content = StringUtil.replace(
				_readFile(jsonObject.getString("contentPath")),
				StringPool.DOLLAR, StringPool.DOLLAR, fileEntriesMap);

			Calendar calendar = CalendarFactoryUtil.getCalendar(
				_serviceContext.getTimeZone());

			int displayDateMonth = calendar.get(Calendar.MONTH);
			int displayDateDay = calendar.get(Calendar.DAY_OF_MONTH);
			int displayDateYear = calendar.get(Calendar.YEAR);
			int displayDateHour = calendar.get(Calendar.HOUR_OF_DAY);
			int displayDateMinute = calendar.get(Calendar.MINUTE);

			long folderId = journalFolderMap.getOrDefault(
				jsonObject.getString("folder"),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			JournalArticle article = _journalArticleLocalService.addArticle(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				folderId, JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0,
				jsonObject.getString("articleId"), false, 1,
				Collections.singletonMap(
					LocaleUtil.getSiteDefault(), jsonObject.getString("name")),
				null, content, jsonObject.getString("ddmStructureKey"),
				jsonObject.getString("ddmTemplateKey"), null, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true,
				true, false, null, null, null, null, _serviceContext);

			long resourceClassNameId = _portal.getClassNameId(
				JournalArticle.class);

			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchStructure(
					_serviceContext.getScopeGroupId(), resourceClassNameId,
					jsonObject.getString("ddmStructureKey"));

			long defaultLayoutPageTemplateEntryId =
				_getDefaultLayoutPageTemplateEntryId(
					resourceClassNameId, ddmStructure.getStructureId());

			_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				resourceClassNameId, article.getResourcePrimKey(),
				defaultLayoutPageTemplateEntryId,
				AssetDisplayPageConstants.TYPE_DEFAULT, _serviceContext);
		}
	}

	private void _addJournalFolders() throws Exception {
		_journalFolders = new ArrayList<>();

		JSONArray journalFoldersJSONArray = JSONFactoryUtil.createJSONArray(
			_readFile("/journal-folders/journal_folders.json"));

		for (int i = 0; i < journalFoldersJSONArray.length(); i++) {
			JSONObject jsonObject = journalFoldersJSONArray.getJSONObject(i);

			JournalFolder journalFolder = _journalFolderLocalService.addFolder(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				jsonObject.getString("name"), null, _serviceContext);

			_journalFolders.add(journalFolder);
		}
	}

	private void _addLayouts() throws Exception {
		JSONArray layoutsJSONArray = JSONFactoryUtil.createJSONArray(
			_readFile("/layouts/layouts.json"));

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

		for (int i = 0; i < layoutsJSONArray.length(); i++) {
			JSONObject jsonObject = layoutsJSONArray.getJSONObject(i);

			String path = jsonObject.getString("contentPath");

			JSONObject pageJSONObject = JSONFactoryUtil.createJSONObject(
				_readFile(path + StringPool.SLASH + "page.json"));

			String type = StringUtil.toLowerCase(
				pageJSONObject.getString("type"));

			Layout layout = null;

			if (Objects.equals(LayoutConstants.TYPE_CONTENT, type) ||
				Objects.equals(LayoutConstants.TYPE_COLLECTION, type)) {

				String pageDefinition = StringUtil.replace(
					_readFile(path + StringPool.SLASH + "page-definition.json"),
					StringPool.DOLLAR, StringPool.DOLLAR, resourcesMap);

				layout = _addContentLayout(
					pageJSONObject,
					JSONFactoryUtil.createJSONObject(pageDefinition), type,
					resourcesMap);
			}
			else {
				layout = _addWidgetLayout(pageJSONObject);
			}

			_addNavigationMenuItems(layout);
		}
	}

	private void _addMasterPages() throws Exception {
		File file = _generateZipFile(
			"master-pages",
			LayoutPageTemplateExportImportConstants.FILE_NAME_MASTER_PAGE,
			HashMapBuilder.put(
				"CUSTOMER_PORTAL_SITE_NAVIGATION_MENU_ID",
				String.valueOf(
					_siteNavigationMenuMap.get("Customer Portal Menu"))
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
			_readFile("/site-navigation-menus/site-navigation-menus.json"));

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
			ZipWriter zipWriter, URL url, Map<String, String> valuesMap)
		throws IOException {

		String entryPath = url.getPath();

		String zipPath = StringUtil.removeSubstring(entryPath, _PATH);

		String content = StringUtil.read(url.openStream());

		zipWriter.addEntry(
			zipPath,
			StringUtil.replace(
				content, StringPool.DOLLAR, StringPool.DOLLAR, valuesMap));
	}

	private void _createServiceContext(long groupId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		User user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());

		Locale locale = LocaleUtil.getSiteDefault();

		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		_serviceContext = serviceContext;
	}

	private File _generateZipFile(
			String path, String type, Map<String, String> valuesMap)
		throws Exception {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		StringBuilder sb = new StringBuilder(3);

		sb.append(_PATH + StringPool.FORWARD_SLASH + path);
		sb.append(StringPool.FORWARD_SLASH);

		Enumeration<URL> enumeration = _bundle.findEntries(
			sb.toString(), type, true);

		try {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				_populateZipWriter(zipWriter, url, valuesMap);
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new Exception(exception);
		}
	}

	private long _getDefaultLayoutPageTemplateEntryId(
		long classNameId, long classTypeId) {

		LayoutPageTemplateEntry defaultAssetDisplayPage =
			_layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				_serviceContext.getScopeGroupId(), classNameId, classTypeId);

		if (defaultAssetDisplayPage == null) {
			return 0;
		}

		return defaultAssetDisplayPage.getLayoutPageTemplateEntryId();
	}

	private String _getDynamicCollectionTypeSettings(String ddmStructureKey)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_serviceContext.getScopeGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			ddmStructureKey);

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		unicodeProperties.put(
			"anyAssetType",
			String.valueOf(_portal.getClassNameId(JournalArticle.class)));
		unicodeProperties.put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			String.valueOf(ddmStructure.getStructureId()));
		unicodeProperties.put(
			"classTypeIdsJournalArticleAssetRendererFactory",
			String.valueOf(ddmStructure.getStructureId()));
		unicodeProperties.put("classNameIds", JournalArticle.class.getName());
		unicodeProperties.put(
			"groupIds", String.valueOf(_serviceContext.getScopeGroupId()));
		unicodeProperties.put("orderByColumn1", "modifiedDate");
		unicodeProperties.put("orderByColumn2", "title");
		unicodeProperties.put("orderByType1", "ASC");
		unicodeProperties.put("orderByType2", "ASC");

		return unicodeProperties.toString();
	}

	private Map<String, String> _getFileEntriesMap() throws Exception {
		Map<String, String> fileEntriesMap = new HashMap<>();

		for (FileEntry fileEntry : _fileEntries) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(fileEntry));

			jsonObject.put("alt", StringPool.BLANK);

			fileEntriesMap.put(fileEntry.getFileName(), jsonObject.toString());

			fileEntriesMap.put(
				"IMG_" + fileEntry.getFileName(),
				_dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false));
		}

		return fileEntriesMap;
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

	private void _populateZipWriter(
			ZipWriter zipWriter, URL url, Map<String, String> valuesMap)
		throws IOException {

		String zipPath = StringUtil.removeSubstring(url.getFile(), _PATH);

		zipWriter.addEntry(zipPath, url.openStream());

		Enumeration<URL> enumeration = _bundle.findEntries(
			FileUtil.getPath(url.getPath()), StringPool.STAR, true);

		while (enumeration.hasMoreElements()) {
			URL elementUrl = enumeration.nextElement();

			_addZipWriterEntry(zipWriter, elementUrl, valuesMap);
		}
	}

	private String _readFile(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(clazz.getClassLoader(), _PATH + fileName);
	}

	private Layout _updateLayoutSettings(
		Layout layout, JSONObject settingsJSONObject) {

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

			layout.setTypeSettingsProperties(unicodeProperties);
		}

		String themeName = settingsJSONObject.getString("themeName");

		if (Validator.isNotNull(themeName)) {
			String themeId = _getThemeId(layout.getCompanyId(), themeName);

			layout.setThemeId(themeId);
		}

		String colorSchemeName = settingsJSONObject.getString(
			"colorSchemeName");

		if (Validator.isNotNull(colorSchemeName)) {
			layout.setColorSchemeId(colorSchemeName);
		}

		String css = settingsJSONObject.getString("css");

		if (Validator.isNotNull(css)) {
			layout.setCss(css);
		}

		JSONObject masterPageJSONObject = settingsJSONObject.getJSONObject(
			"masterPage");

		if (masterPageJSONObject != null) {
			LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(
						layout.getGroupId(),
						masterPageJSONObject.getString("key"));

			if (masterLayoutPageTemplateEntry != null) {
				layout.setMasterLayoutPlid(
					masterLayoutPageTemplateEntry.getPlid());
			}
		}

		return _layoutLocalService.updateLayout(layout);
	}

	private void _updateLookAndFeel() throws Exception {
		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			_serviceContext.getScopeGroupId(), false);

		UnicodeProperties settingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		settingsUnicodeProperties.setProperty(
			"lfr-theme:regular:show-footer", Boolean.FALSE.toString());
		settingsUnicodeProperties.setProperty(
			"lfr-theme:regular:show-header", Boolean.FALSE.toString());
		settingsUnicodeProperties.setProperty(
			"lfr-theme:regular:show-header-search", Boolean.FALSE.toString());
		settingsUnicodeProperties.setProperty(
			"lfr-theme:regular:show-maximize-minimize-application-links",
			Boolean.FALSE.toString());
		settingsUnicodeProperties.setProperty(
			"lfr-theme:regular:wrap-widget-page-content",
			Boolean.FALSE.toString());

		_layoutSetLocalService.updateSettings(
			_serviceContext.getScopeGroupId(), false,
			settingsUnicodeProperties.toString());

		_layoutSetLocalService.updateLookAndFeel(
			_serviceContext.getScopeGroupId(), false, layoutSet.getThemeId(),
			layoutSet.getColorSchemeId(), _readFile("/layout-set/custom.css"));
	}

	private static final String _NAME = "Insurance Demo Site";

	private static final String _PATH =
		"com/liferay/site/insurance/site/initializer/internal/dependencies";

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

	private List<JournalFolder> _journalFolders;

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
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
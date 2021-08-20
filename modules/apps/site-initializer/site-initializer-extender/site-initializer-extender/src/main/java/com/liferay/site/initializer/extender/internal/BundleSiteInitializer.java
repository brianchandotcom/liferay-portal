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

package com.liferay.site.initializer.extender.internal;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Brian Wing Shun Chan
 */
public class BundleSiteInitializer implements SiteInitializer {

	public BundleSiteInitializer(
		AssetListEntryLocalService assetListEntryLocalService, Bundle bundle,
		DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLocalService ddmTemplateLocalService,
		DefaultDDMStructureHelper defaultDDMStructureHelper,
		DocumentResource.Factory documentResourceFactory,
		FragmentsImporter fragmentsImporter,
		JournalArticleLocalService journalArticleLocalService,
		JSONFactory jsonFactory, LayoutCopyHelper layoutCopyHelper,
		LayoutLocalService layoutLocalService,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		LayoutPageTemplatesImporter layoutPageTemplatesImporter,
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		Portal portal, ServletContext servletContext,
		SiteNavigationMenuItemLocalService siteNavigationMenuItemLocalService,
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry,
		SiteNavigationMenuLocalService siteNavigationMenuLocalService,
		StyleBookEntryZipProcessor styleBookEntryZipProcessor,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		ThemeLocalService themeLocalService,
		UserLocalService userLocalService) {

		_assetListEntryLocalService = assetListEntryLocalService;
		_bundle = bundle;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmTemplateLocalService = ddmTemplateLocalService;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_documentResourceFactory = documentResourceFactory;
		_fragmentsImporter = fragmentsImporter;
		_journalArticleLocalService = journalArticleLocalService;
		_jsonFactory = jsonFactory;
		_layoutCopyHelper = layoutCopyHelper;
		_layoutLocalService = layoutLocalService;
		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
		_layoutPageTemplatesImporter = layoutPageTemplatesImporter;
		_layoutPageTemplateStructureLocalService =
			layoutPageTemplateStructureLocalService;
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;
		_portal = portal;
		_servletContext = servletContext;
		_siteNavigationMenuItemLocalService =
			siteNavigationMenuItemLocalService;
		_siteNavigationMenuItemTypeRegistry =
			siteNavigationMenuItemTypeRegistry;
		_siteNavigationMenuLocalService = siteNavigationMenuLocalService;
		_styleBookEntryZipProcessor = styleBookEntryZipProcessor;
		_taxonomyVocabularyResourceFactory = taxonomyVocabularyResourceFactory;
		_themeLocalService = themeLocalService;
		_userLocalService = userLocalService;

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		_classLoader = bundleWiring.getClassLoader();
	}

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return _bundle.getSymbolicName();
	}

	@Override
	public String getName(Locale locale) {
		Dictionary<String, String> headers = _bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getString(headers.get("Bundle-Name"));
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			User user = _userLocalService.getUser(
				PrincipalThreadLocal.getUserId());

			ServiceContext serviceContext = new ServiceContext() {
				{
					setAddGroupPermissions(true);
					setAddGuestPermissions(true);
					setScopeGroupId(groupId);
					setTimeZone(user.getTimeZone());
					setUserId(user.getUserId());
				}
			};

			_addDDMStructures(serviceContext);
			_addDDMTemplates(serviceContext);
			_addDocuments(serviceContext);
			_addFragmentEntries(serviceContext);
			_addSiteNavigationMenus(serviceContext);
			_addObjectDefinitions(serviceContext);
			_addStyleBookEntries(serviceContext);
			_addTaxonomyVocabularies(serviceContext);
			_addLayouts(serviceContext);
		}
		catch (Exception exception) {
			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	private Layout _addContentLayout(
			JSONObject pageJSONObject, JSONObject pageDefinitionJSONObject,
			Map<String, String> resourcesMap, ServiceContext serviceContext)
		throws Exception {

		String type = StringUtil.toLowerCase(pageJSONObject.getString("type"));

		Layout layout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			pageJSONObject.getBoolean("private"),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), pageJSONObject.getString("name")
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			type, null, false, false, new HashMap<>(), serviceContext);

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
				serviceContext.getScopeGroupId(), draftLayout.isPrivateLayout(),
				draftLayout.getLayoutId(),
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
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		_layoutLocalService.updateStatus(
			layout.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		return layout;
	}

	private void _addDDMStructures(ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/ddm-structures");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			_defaultDDMStructureHelper.addDDMStructures(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class), _classLoader,
				resourcePath, serviceContext);
		}
	}

	private void _addDDMTemplates(ServiceContext serviceContext)
		throws Exception {

		long resourceClassNameId = _portal.getClassNameId(JournalArticle.class);

		Enumeration<URL> enumeration = _bundle.findEntries(
			"/site-initializer/ddm-templates", "ddm-template.json", true);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			JSONObject ddmTemplateJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(url.openStream()));

			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchStructure(
					serviceContext.getScopeGroupId(), resourceClassNameId,
					ddmTemplateJSONObject.getString("ddmStructureKey"));

			_ddmTemplateLocalService.addTemplate(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(), resourceClassNameId,
				ddmTemplateJSONObject.getString("ddmTemplateKey"),
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(),
					ddmTemplateJSONObject.getString("name")
				).build(),
				null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
				TemplateConstants.LANG_TYPE_FTL, _read("ddm-template.ftl", url),
				false, false, null, null, serviceContext);
		}
	}

	private void _addDocuments(ServiceContext serviceContext) throws Exception {
		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/documents");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		DocumentResource.Builder documentResourceBuilder =
			_documentResourceFactory.create();

		DocumentResource documentResource = documentResourceBuilder.user(
			serviceContext.fetchUser()
		).build();

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith("/")) {

				// TODO Recurse

				continue;
			}

			String fileName = FileUtil.getShortFileName(resourcePath);

			URL url = _servletContext.getResource(resourcePath);

			URLConnection urlConnection = url.openConnection();

			Map<String, String> values = Collections.emptyMap();

			String json = _read(resourcePath + "._si.json");

			if (json != null) {
				JSONObject jsonObject = _jsonFactory.createJSONObject(json);

				for (String key : jsonObject.keySet()) {
					values.put(key, jsonObject.getString(key));
				}
			}

			documentResource.postSiteDocument(
				serviceContext.getScopeGroupId(),
				MultipartBody.of(
					Collections.singletonMap(
						"file",
						new BinaryFile(
							MimeTypesUtil.getContentType(fileName), fileName,
							urlConnection.getInputStream(),
							urlConnection.getContentLength())),
					null, values));
		}
	}

	private void _addFragmentEntries(ServiceContext serviceContext)
		throws Exception {

		URL url = _bundle.getEntry("/fragments.zip");

		if (url == null) {
			return;
		}

		_fragmentsImporter.importFragmentEntries(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
			FileUtil.createTempFile(url.openStream()), false);
	}

	private void _addLayouts(ServiceContext serviceContext) throws Exception {
		Map<String, String> resourcesMap = _getResourcesMap(serviceContext);

		JSONArray layoutsJSONArray = JSONFactoryUtil.createJSONArray(
			_read("/site-initializer/layouts/layouts.json"));

		for (int i = 0; i < layoutsJSONArray.length(); i++) {
			JSONObject jsonObject = layoutsJSONArray.getJSONObject(i);

			String path = jsonObject.getString("path");

			JSONObject pageJSONObject = JSONFactoryUtil.createJSONObject(
				_read(
					StringBundler.concat(
						"/site-initializer/layouts/", path, "/page.json")));

			String type = StringUtil.toLowerCase(
				pageJSONObject.getString("type"));

			Layout layout = null;

			if (Objects.equals(LayoutConstants.TYPE_CONTENT, type) ||
				Objects.equals(LayoutConstants.TYPE_COLLECTION, type)) {

				String pageDefinitionJSON = StringUtil.replace(
					_read(
						StringBundler.concat(
							"/site-initializer/layouts/", path,
							"/page-definition.json")),
					"\"[$", "$]\"", resourcesMap);

				layout = _addContentLayout(
					pageJSONObject,
					JSONFactoryUtil.createJSONObject(pageDefinitionJSON),
					resourcesMap, serviceContext);
			}
			else {
				layout = _addWidgetLayout(pageJSONObject, serviceContext);
			}

			_addNavigationMenuItems(layout, serviceContext);
		}
	}

	private void _addNavigationMenuItems(
			Layout layout, ServiceContext serviceContext)
		throws Exception {

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
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT,
				siteNavigationMenuItemType.getTypeSettingsFromLayout(layout),
				serviceContext);
		}
	}

	private void _addObjectDefinitions(ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/object-definitions");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		ObjectDefinitionResource.Builder objectDefinitionResourceBuilder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource =
			objectDefinitionResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		for (String resourcePath : resourcePaths) {
			String json = _read(resourcePath);

			ObjectDefinition objectDefinition = ObjectDefinition.toDTO(json);

			if (objectDefinition == null) {
				_log.error(
					"Unable to transform object definition from JSON: " + json);

				continue;
			}

			try {
				objectDefinition =
					objectDefinitionResource.postObjectDefinition(
						objectDefinition);

				objectDefinitionResource.postObjectDefinitionPublish(
					objectDefinition.getId());
			}
			catch (Exception exception) {

				// TODO PUT

			}
		}
	}

	private void _addSiteNavigationMenus(ServiceContext serviceContext)
		throws Exception {

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
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), name, serviceContext);

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

	private void _addStyleBookEntries(ServiceContext serviceContext)
		throws Exception {

		URL url = _bundle.getEntry("/style-books.zip");

		if (url == null) {
			return;
		}

		_styleBookEntryZipProcessor.importStyleBookEntries(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			FileUtil.createTempFile(url.openStream()), false);
	}

	private void _addTaxonomyVocabularies(ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/taxonomy-vocabularies");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		TaxonomyVocabularyResource.Builder taxonomyVocabularyResourceBuilder =
			_taxonomyVocabularyResourceFactory.create();

		TaxonomyVocabularyResource taxonomyVocabularyResource =
			taxonomyVocabularyResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		for (String resourcePath : resourcePaths) {
			String json = _read(resourcePath);

			TaxonomyVocabulary taxonomyVocabulary = TaxonomyVocabulary.toDTO(
				json);

			if (taxonomyVocabulary == null) {
				_log.error(
					"Unable to transform taxonomy vocabulary from JSON: " +
						json);

				continue;
			}

			// TODO

			if (false) {
				taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
					serviceContext.getScopeGroupId(), taxonomyVocabulary);
			}
		}
	}

	private Layout _addWidgetLayout(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws Exception {

		String name = jsonObject.getString("name");

		return _layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), name
			).build(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_PORTLET, null, false, false, new HashMap<>(),
			serviceContext);
	}

	private Map<String, String> _getResourcesMap(
		ServiceContext serviceContext) {

		Map<String, String> resourcesMap = new HashMap<>();

		List<JournalArticle> articles = _journalArticleLocalService.getArticles(
			serviceContext.getScopeGroupId());

		for (JournalArticle article : articles) {
			resourcesMap.put(
				article.getArticleId(),
				String.valueOf(article.getResourcePrimKey()));
		}

		List<AssetListEntry> assetListEntries =
			_assetListEntryLocalService.getAssetListEntries(
				serviceContext.getScopeGroupId());

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

	private String _read(String resourcePath) throws Exception {
		InputStream inputStream = _servletContext.getResourceAsStream(
			resourcePath);

		if (inputStream == null) {
			return null;
		}

		return StringUtil.read(inputStream);
	}

	private String _read(String fileName, URL url) throws Exception {
		String path = url.getPath();

		URL entryURL = _bundle.getEntry(
			path.substring(0, path.lastIndexOf("/") + 1) + fileName);

		return StringUtil.read(entryURL.openStream());
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

	private static final Log _log = LogFactoryUtil.getLog(
		BundleSiteInitializer.class);

	private final AssetListEntryLocalService _assetListEntryLocalService;
	private final Bundle _bundle;
	private final ClassLoader _classLoader;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMTemplateLocalService _ddmTemplateLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final DocumentResource.Factory _documentResourceFactory;
	private final FragmentsImporter _fragmentsImporter;
	private final JournalArticleLocalService _journalArticleLocalService;
	private final JSONFactory _jsonFactory;
	private final LayoutCopyHelper _layoutCopyHelper;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;
	private final LayoutPageTemplatesImporter _layoutPageTemplatesImporter;
	private final LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;
	private Map<String, List<SiteNavigationMenu>> _layoutsSiteNavigationMenuMap;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final Portal _portal;
	private final ServletContext _servletContext;
	private final SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;
	private final SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;
	private final SiteNavigationMenuLocalService
		_siteNavigationMenuLocalService;
	private Map<String, Long> _siteNavigationMenuMap;
	private final StyleBookEntryZipProcessor _styleBookEntryZipProcessor;
	private final TaxonomyVocabularyResource.Factory
		_taxonomyVocabularyResourceFactory;
	private final ThemeLocalService _themeLocalService;
	private final UserLocalService _userLocalService;

}
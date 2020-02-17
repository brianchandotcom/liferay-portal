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

package com.liferay.frontend.theme.porygon.site.initializer.internal;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryModel;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(
	immediate = true,
	property = "site.initializer.key=" + PorygonSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class PorygonSiteInitializer implements SiteInitializer {

	public static final String KEY = "porygon-site-initializer";

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
		return _THEME_NAME;
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			ServiceContext serviceContext = _createServiceContext(groupId);

			_updateLogo(serviceContext);
			_updateLookAndFeel(serviceContext);

			List<FileEntry> fileEntries = _addFileEntries(serviceContext);

			List<FragmentEntry> fragmentEntries = _addFragmentEntries(
				fileEntries, serviceContext);

			Map<String, FragmentEntry> fragmentEntriesMap =
				_getFragmentEntriesMap(fragmentEntries);

			List<FragmentEntry> entryFragmentEntries = new ArrayList<>();

			entryFragmentEntries.add(fragmentEntriesMap.get("entry"));

			List<DDMTemplate> ddmTemplates = _addApplicationDisplayTemplates(
				serviceContext);

			DDMStructure ddmStructure = _addJournalArticleDDMStructure(
				serviceContext);

			_addJournalArticleDDMTemplates(ddmStructure, serviceContext);

			_addDisplayPageEntry(
				"Porygon Entry", entryFragmentEntries,
				_PATH + "/page_templates", "porygon_entry.jpg", ddmStructure,
				fileEntries, serviceContext);

			_addJournalArticles(fileEntries, serviceContext);

			_addLayouts(ddmStructure, ddmTemplates, serviceContext);
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

	private List<DDMTemplate> _addApplicationDisplayTemplates(
			ServiceContext serviceContext)
		throws Exception {

		List<DDMTemplate> ddmTemplates = new ArrayList<>();

		Enumeration<URL> enumeration = _bundle.findEntries(
			_PATH + "/adt", "*.ftl", false);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String script = StringUtil.read(url.openStream());

			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.getSiteDefault(),
				FileUtil.stripExtension(
					FileUtil.getShortFileName(url.getPath()))
			).build();

			DDMTemplate ddmTemplate = _ddmTemplateLocalService.addTemplate(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(AssetEntry.class.getName()), 0,
				_portal.getClassNameId(_PORTLET_DISPLAY_TEMPLATE_CLASS_NAME),
				nameMap, new HashMap<>(),
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
				DDMTemplateConstants.TEMPLATE_MODE_EDIT,
				TemplateConstants.LANG_TYPE_FTL, script, serviceContext);

			ddmTemplates.add(ddmTemplate);
		}

		return ddmTemplates;
	}

	private void _addAssetPublisherPortlet(
			DDMStructure ddmStructure, String ddmTemplateKey, String delta,
			String portletDecoratorId, Layout layout, String column,
			ServiceContext serviceContext)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String portletId = layoutTypePortlet.addPortletId(
			serviceContext.getUserId(),
			"com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet",
			column, -1);

		PortletPreferences jxPortletPreferences =
			_getAssetPublisherPortletPreferences(ddmStructure, serviceContext);

		jxPortletPreferences.setValue("delta", delta);
		jxPortletPreferences.setValue("displayStyle", ddmTemplateKey);
		jxPortletPreferences.setValue(
			"portletSetupPortletDecoratorId", portletDecoratorId);

		_portletPreferencesLocalService.addPortletPreferences(
			serviceContext.getCompanyId(), 0,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(), portletId,
			null, PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));
	}

	private LayoutPageTemplateEntry _addDisplayPageEntry(
			String name, List<FragmentEntry> fragmentEntries,
			String thumbnailPath, String thumbnailFileName,
			DDMStructure ddmStructure, List<FileEntry> fileEntries,
			ServiceContext serviceContext)
		throws Exception {

		long previewFileEntryId = _getPreviewFileEntryId(
			thumbnailPath, thumbnailFileName, serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
				ddmStructure.getClassNameId(), ddmStructure.getStructureId(),
				name, LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
				previewFileEntryId, true, 0, 0, 0,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		long[] fragmentEntryIds = ListUtil.toLongArray(
			fragmentEntries, FragmentEntryModel::getFragmentEntryId);

		Map<String, String> fileEntriesMap = _getFileEntriesMap(fileEntries);

		URL url = _bundle.getEntry(_PATH + "/fragments/editable_values.json");

		String editableValues = StringUtil.replace(
			StringUtil.read(url.openStream()), StringPool.DOLLAR,
			StringPool.DOLLAR, fileEntriesMap);

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), name,
				fragmentEntryIds, editableValues, serviceContext);
	}

	private List<FileEntry> _addFileEntries(ServiceContext serviceContext)
		throws Exception {

		List<FileEntry> fileEntries = new ArrayList<>();

		Folder folder = _dlAppLocalService.addFolder(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _THEME_NAME,
			StringPool.BLANK, serviceContext);

		Enumeration<URL> enumeration = _bundle.findEntries(
			_PATH + "/images", StringPool.STAR, false);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			byte[] bytes = null;

			try (InputStream is = url.openStream()) {
				bytes = FileUtil.getBytes(is);
			}

			String fileName = FileUtil.getShortFileName(url.getPath());

			FileEntry fileEntry = _dlAppLocalService.addFileEntry(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				folder.getFolderId(), fileName, null, fileName,
				StringPool.BLANK, StringPool.BLANK, bytes, serviceContext);

			fileEntries.add(fileEntry);
		}

		return fileEntries;
	}

	private List<FragmentEntry> _addFragmentEntries(
			List<FileEntry> fileEntries, ServiceContext serviceContext)
		throws Exception {

		List<FragmentEntry> fragmentEntries = new ArrayList<>();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_THEME_NAME, null, serviceContext);

		Map<String, String> fileEntriesMap = _getFileEntriesMap(fileEntries);

		Enumeration<URL> enumeration = _bundle.findEntries(
			_PATH + "/fragments", "*.html", false);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String fileName = FileUtil.getShortFileName(url.getPath());
			String filePath = FileUtil.getPath(url.getPath());

			url = _bundle.getEntry(filePath + "/" + fileName);

			String html = StringUtil.replace(
				StringUtil.read(url.openStream()), StringPool.DOLLAR,
				StringPool.DOLLAR, fileEntriesMap);

			String shortFileName = FileUtil.getShortFileName(url.getPath());

			String fragmentEntryId = FileUtil.stripExtension(shortFileName);

			StringBundler sb = new StringBundler(4);

			sb.append(filePath);
			sb.append(StringPool.SLASH);
			sb.append(fragmentEntryId);
			sb.append(".css");

			URL cssURL = _bundle.getEntry(sb.toString());

			String css = StringUtil.replace(
				StringUtil.read(cssURL.openStream()), StringPool.DOLLAR,
				StringPool.DOLLAR, fileEntriesMap);

			String fragmentEntryName = StringUtil.upperCaseFirstLetter(
				fragmentEntryId);

			FragmentEntry fragmentEntry =
				_fragmentEntryLocalService.addFragmentEntry(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					fragmentCollection.getFragmentCollectionId(), null,
					fragmentEntryName, css, html, StringPool.BLANK,
					StringPool.BLANK,
					_getPreviewFileEntryId(
						filePath, fragmentEntryId + ".jpg", serviceContext),
					FragmentConstants.TYPE_SECTION,
					WorkflowConstants.STATUS_APPROVED, serviceContext);

			fragmentEntries.add(fragmentEntry);
		}

		return fragmentEntries;
	}

	private Layout _addHomeLayout(
			DDMStructure ddmStructure, List<DDMTemplate> adtDDMTemplates,
			ServiceContext serviceContext)
		throws Exception {

		Layout layout = _addLayout("Home", serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey(
				"adt_entry_card_first_item_bigger_left", adtDDMTemplates),
			"3", _PORTLET_DECORATOR_BAREBONE, layout, _COLUMN_1,
			serviceContext);

		String portletId = _addNestedPortletsPortlet(
			_LAYOUT_70_30, layout, serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey("adt_entry_list_3_items", adtDDMTemplates), "6",
			_PORTLET_DECORATOR_BAREBONE, layout,
			_getNestedColumn(portletId, _COLUMN_1), serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure, "title-list", "8", _PORTLET_DECORATOR_TRENDING,
			layout, _getNestedColumn(portletId, _COLUMN_2), serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey("adt_entry_carousel", adtDDMTemplates), "3",
			_PORTLET_DECORATOR_BAREBONE, layout, _COLUMN_1, serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey("adt_entry_list_4_items", adtDDMTemplates), "4",
			_PORTLET_DECORATOR_BAREBONE, layout, _COLUMN_1, serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey(
				"adt_entry_card_first_item_bigger_right", adtDDMTemplates),
			"5", _PORTLET_DECORATOR_BAREBONE, layout, _COLUMN_1,
			serviceContext);

		portletId = _addNestedPortletsPortlet(
			_LAYOUT_50_50, layout, serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure, "title-list", "5", _PORTLET_DECORATOR_TRENDING,
			layout, _getNestedColumn(portletId, _COLUMN_1), serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure, "title-list", "5", _PORTLET_DECORATOR_TRENDING,
			layout, _getNestedColumn(portletId, _COLUMN_2), serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey("adt_entry_list_4_items", adtDDMTemplates), "16",
			_PORTLET_DECORATOR_BAREBONE, layout, _COLUMN_1, serviceContext);

		_updateLayout(layout);

		return layout;
	}

	private DDMStructure _addJournalArticleDDMStructure(
			ServiceContext serviceContext)
		throws Exception {

		Locale siteDefaultLocale = LocaleUtil.getSiteDefault();

		Map<Locale, String> nameMap = HashMapBuilder.put(
			siteDefaultLocale, "Porygon Entry"
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			siteDefaultLocale, "Porygon Entry"
		).build();

		URL definitionURL = _bundle.getEntry(
			_PATH + "/journal/structures/porygon_entry/definition.json");

		DDMFormDeserializerDeserializeRequest.Builder
			ddmFormDeserializerDeserializeRequestBuilder =
				DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
					StringUtil.read(definitionURL.openStream()));

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(
					ddmFormDeserializerDeserializeRequestBuilder.build());

		DDMForm ddmForm = ddmFormDeserializerDeserializeResponse.getDDMForm();

		ddmForm = _ddm.updateDDMFormDefaultLocale(ddmForm, siteDefaultLocale);

		URL layoutURL = _bundle.getEntry(
			_PATH + "/journal/structures/porygon_entry/layout.json");

		DDMFormLayoutDeserializerDeserializeRequest.Builder
			ddmFormLayoutDeserializerDeserializeRequestBuilder =
				DDMFormLayoutDeserializerDeserializeRequest.Builder.newBuilder(
					StringUtil.read(layoutURL.openStream()));

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				_jsonDDMFormLayoutDeserializer.deserialize(
					ddmFormLayoutDeserializerDeserializeRequestBuilder.build());

		return _ddmStructureLocalService.addStructure(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			_portal.getClassNameId(JournalArticle.class), "PORYGON_ENTRY",
			nameMap, descriptionMap, ddmForm,
			ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout(),
			StorageType.JSON.toString(), DDMStructureConstants.TYPE_DEFAULT,
			serviceContext);
	}

	private void _addJournalArticleDDMTemplates(
			DDMStructure ddmStructure, ServiceContext serviceContext)
		throws Exception {

		Enumeration<URL> enumeration = _bundle.findEntries(
			_PATH + "/journal/structures/porygon_entry/templates", "*", false);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String script = StringUtil.read(url.openStream());

			String ddmTemplateKey = FileUtil.stripExtension(
				FileUtil.getShortFileName(url.getPath()));

			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.getSiteDefault(),
				StringUtil.upperCaseFirstLetter(
					StringUtil.replace(ddmTemplateKey, '_', ' '))
			).build();

			_ddmTemplateLocalService.addTemplate(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(), ddmStructure.getClassNameId(),
				ddmTemplateKey, nameMap, null,
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
				DDMTemplateConstants.TEMPLATE_MODE_CREATE,
				TemplateConstants.LANG_TYPE_FTL, script, true, false,
				StringPool.BLANK, null, serviceContext);
		}
	}

	private List<JournalArticle> _addJournalArticles(
			List<FileEntry> fileEntries, ServiceContext serviceContext)
		throws Exception {

		Map<String, String> fileEntriesMap = new HashMap<>();

		for (FileEntry fileEntry : fileEntries) {
			fileEntriesMap.put(
				fileEntry.getFileName(),
				JSONFactoryUtil.looseSerialize(fileEntry));
		}

		List<JournalArticle> journalArticles = new ArrayList<>();

		Enumeration<URL> enumeration = _bundle.findEntries(
			_PATH + "/journal/structures/porygon_entry/content", "*", false);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String content = StringUtil.replace(
				StringUtil.read(url.openStream()), StringPool.DOLLAR,
				StringPool.DOLLAR, fileEntriesMap);

			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.getSiteDefault(),
				StringUtil.upperCaseFirstLetter(
					CamelCaseUtil.toCamelCase(
						StringUtil.replace(
							FileUtil.stripExtension(
								FileUtil.getShortFileName(url.getPath())),
							CharPool.UNDERLINE, CharPool.SPACE)))
			).build();

			JournalArticle article = _journalArticleLocalService.addArticle(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
				nameMap, null, content, "PORYGON_ENTRY", "PORYGON_ENTRY",
				serviceContext);

			_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class),
				article.getResourcePrimKey(), 0,
				AssetDisplayPageConstants.TYPE_DEFAULT, serviceContext);

			journalArticles.add(article);
		}

		return journalArticles;
	}

	private Layout _addLayout(String name, ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), name
		).build();

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("layout-template-id", "1_column");

		return _layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, nameMap, new HashMap<>(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), "portlet",
			typeSettingsProperties.toString(), false, new HashMap<>(),
			serviceContext);
	}

	private void _addLayouts(
			DDMStructure ddmStructure, List<DDMTemplate> adtDDMTemplates,
			ServiceContext serviceContext)
		throws Exception {

		_addHomeLayout(ddmStructure, adtDDMTemplates, serviceContext);

		_addPhotographyLayout(ddmStructure, adtDDMTemplates, serviceContext);

		_addScienceLayout(ddmStructure, adtDDMTemplates, serviceContext);

		_addReviewsLayout(ddmStructure, adtDDMTemplates, serviceContext);
	}

	private String _addNestedPortletsPortlet(
			String layoutTemplateId, Layout layout,
			ServiceContext serviceContext)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String portletId = layoutTypePortlet.addPortletId(
			serviceContext.getUserId(),
			"com_liferay_nested_portlets_web_portlet_NestedPortletsPortlet",
			_COLUMN_1, -1);

		PortletPreferences jxPortletPreferences = new PortletPreferencesImpl();

		jxPortletPreferences.setValue("layoutTemplateId", layoutTemplateId);
		jxPortletPreferences.setValue(
			"portletSetupPortletDecoratorId", _PORTLET_DECORATOR_BAREBONE);

		_portletPreferencesLocalService.addPortletPreferences(
			serviceContext.getCompanyId(), 0,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(), portletId,
			null, PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		return portletId;
	}

	private Layout _addPhotographyLayout(
			DDMStructure ddmStructure, List<DDMTemplate> adtDDMTemplates,
			ServiceContext serviceContext)
		throws Exception {

		Layout layout = _addLayout("Photography", serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey(
				"adt_entry_card_first_item_bigger_right", adtDDMTemplates),
			"3", _PORTLET_DECORATOR_BAREBONE, layout, _COLUMN_1,
			serviceContext);

		String portletId = _addNestedPortletsPortlet(
			_LAYOUT_70_30, layout, serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey("adt_entry_list_3_items", adtDDMTemplates), "12",
			_PORTLET_DECORATOR_BAREBONE, layout,
			_getNestedColumn(portletId, _COLUMN_1), serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure, "title-list", "12", _PORTLET_DECORATOR_TRENDING,
			layout, _getNestedColumn(portletId, _COLUMN_2), serviceContext);

		_updateLayout(layout);

		return layout;
	}

	private Layout _addReviewsLayout(
			DDMStructure ddmStructure, List<DDMTemplate> adtDDMTemplates,
			ServiceContext serviceContext)
		throws Exception {

		Layout layout = _addLayout("Reviews", serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey("adt_entry_list_3_items", adtDDMTemplates), "6",
			_PORTLET_DECORATOR_BAREBONE, layout, _COLUMN_1, serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey("adt_entry_list_4_items", adtDDMTemplates), "12",
			_PORTLET_DECORATOR_TRENDING, layout, _COLUMN_1, serviceContext);

		_updateLayout(layout);

		return layout;
	}

	private Layout _addScienceLayout(
			DDMStructure ddmStructure, List<DDMTemplate> adtDDMTemplates,
			ServiceContext serviceContext)
		throws Exception {

		Layout layout = _addLayout("Science", serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey(
				"adt_entry_card_first_item_bigger_right", adtDDMTemplates),
			"5", _PORTLET_DECORATOR_BAREBONE, layout, _COLUMN_1,
			serviceContext);

		_addAssetPublisherPortlet(
			ddmStructure,
			_getDDMTemplateKey("adt_entry_list_3_items", adtDDMTemplates), "9",
			_PORTLET_DECORATOR_BAREBONE, layout, _COLUMN_1, serviceContext);

		_updateLayout(layout);

		return layout;
	}

	private ServiceContext _createServiceContext(long groupId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Group group = _groupLocalService.getGroup(groupId);

		serviceContext.setCompanyId(group.getCompanyId());

		User user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());

		Locale locale = LocaleUtil.getSiteDefault();

		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private PortletPreferences _getAssetPublisherPortletPreferences(
			DDMStructure ddmStructure, ServiceContext serviceContext)
		throws Exception {

		PortletPreferences jxPortletPreferences = new PortletPreferencesImpl();

		String journalArticleClassNameId = String.valueOf(
			_portal.getClassNameId(JournalArticle.class));

		jxPortletPreferences.setValue(
			"anyAssetType", journalArticleClassNameId);

		String structureId = String.valueOf(ddmStructure.getStructureId());

		jxPortletPreferences.setValue(
			"anyClassTypeJournalArticleAssetRendererFactory", structureId);

		jxPortletPreferences.setValue("assetLinkBehavior", "viewInPortlet");
		jxPortletPreferences.setValue(
			"classNameIds", journalArticleClassNameId);
		jxPortletPreferences.setValue("classTypeIds", structureId);
		jxPortletPreferences.setValue(
			"classTypeIdsJournalArticleAssetRendererFactory", structureId);
		jxPortletPreferences.setValue(
			"groupId", String.valueOf(serviceContext.getScopeGroupId()));
		jxPortletPreferences.setValue("emailAssetEntryAddedEnabled", "false");

		return jxPortletPreferences;
	}

	private String _getDDMTemplateKey(
		String name, List<DDMTemplate> ddmTemplates) {

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			String ddmTemplateName = ddmTemplate.getName(
				LocaleUtil.getSiteDefault());

			if (ddmTemplateName.equals(name)) {
				return "ddmTemplate_" + ddmTemplate.getTemplateKey();
			}
		}

		return name;
	}

	private Map<String, String> _getFileEntriesMap(List<FileEntry> fileEntries)
		throws PortalException {

		Map<String, String> fileEntriesMap = new HashMap<>();

		for (FileEntry fileEntry : fileEntries) {
			String fileEntryURL = _dlURLHelper.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, false);

			fileEntriesMap.put(fileEntry.getFileName(), fileEntryURL);
		}

		return fileEntriesMap;
	}

	private Map<String, FragmentEntry> _getFragmentEntriesMap(
		List<FragmentEntry> fragmentEntries) {

		Map<String, FragmentEntry> fragmentEntriesMap = new HashMap<>();

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			fragmentEntriesMap.put(
				StringUtil.toLowerCase(fragmentEntry.getName()), fragmentEntry);
		}

		return fragmentEntriesMap;
	}

	private String _getNestedColumn(String portletId, String column) {
		StringBundler sb = new StringBundler(4);

		sb.append(StringPool.UNDERLINE);
		sb.append(portletId);
		sb.append(StringPool.DOUBLE_UNDERLINE);
		sb.append(column);

		return sb.toString();
	}

	private long _getPreviewFileEntryId(
			String filePath, String fileName, ServiceContext serviceContext)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append(filePath);
		sb.append(StringPool.SLASH);
		sb.append(fileName);

		URL url = _bundle.getEntry(sb.toString());

		if (url == null) {
			return 0;
		}

		Folder folder = _dlAppLocalService.getFolder(
			serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _THEME_NAME);
		String imageFileName = FileUtil.getShortFileName(url.getPath());

		byte[] bytes = null;

		try (InputStream is = url.openStream()) {
			bytes = FileUtil.getBytes(is);
		}

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			folder.getFolderId(), imageFileName, null, imageFileName,
			StringPool.BLANK, StringPool.BLANK, bytes, serviceContext);

		return fileEntry.getFileEntryId();
	}

	private void _updateLayout(Layout layout) throws Exception {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.resetModes();
		layoutTypePortlet.resetStates();

		_layoutService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	private void _updateLogo(ServiceContext serviceContext) throws Exception {
		URL url = _bundle.getEntry(_PATH + "/images/logo.png");

		byte[] bytes = null;

		try (InputStream is = url.openStream()) {
			bytes = FileUtil.getBytes(is);
		}

		_layoutSetLocalService.updateLogo(
			serviceContext.getScopeGroupId(), false, true, bytes);
	}

	private void _updateLookAndFeel(ServiceContext serviceContext)
		throws PortalException {

		Theme theme = _themeLocalService.fetchTheme(
			serviceContext.getCompanyId(), _THEME_ID);

		if (theme == null) {
			if (_log.isInfoEnabled()) {
				_log.info("No theme found for " + _THEME_ID);
			}

			return;
		}

		_layoutSetLocalService.updateLookAndFeel(
			serviceContext.getScopeGroupId(), _THEME_ID, StringPool.BLANK,
			StringPool.BLANK);
	}

	private static final String _COLUMN_1 = "column-1";

	private static final String _COLUMN_2 = "column-2";

	private static final String _LAYOUT_50_50 = "2_columns_i";

	private static final String _LAYOUT_70_30 = "porygon_70_30_width_limited";

	private static final String _PATH =
		"com/liferay/frontend/theme/porygon/site/initializer/internal" +
			"/dependencies";

	private static final String _PORTLET_DECORATOR_BAREBONE = "barebone";

	private static final String _PORTLET_DECORATOR_TRENDING = "trending";

	private static final String _PORTLET_DISPLAY_TEMPLATE_CLASS_NAME =
		"com.liferay.portlet.display.template.PortletDisplayTemplate";

	private static final String _THEME_ID = "porygon_WAR_porygontheme";

	private static final String _THEME_NAME = "Porygon";

	private static final Log _log = LogFactoryUtil.getLog(
		PorygonSiteInitializer.class);

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	private Bundle _bundle;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _jsonDDMFormDeserializer;

	@Reference(target = "(ddm.form.layout.deserializer.type=json)")
	private DDMFormLayoutDeserializer _jsonDDMFormLayoutDeserializer;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.theme.porygon.site.initializer)"
	)
	private ServletContext _servletContext;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
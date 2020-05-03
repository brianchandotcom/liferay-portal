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

package com.liferay.layout.page.template.admin.web.internal.importer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.headless.delivery.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.delivery.dto.v1_0.MasterPage;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageTemplate;
import com.liferay.headless.delivery.dto.v1_0.PageTemplateCollection;
import com.liferay.headless.delivery.dto.v1_0.Settings;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer.LayoutStructureItemImporter;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer.LayoutStructureItemImporterTracker;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.exception.DisplayPageTemplateValidatorException;
import com.liferay.layout.page.template.exception.MasterPageValidatorException;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporterResultEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.validator.DisplayPageTemplateValidator;
import com.liferay.layout.page.template.validator.MasterPageValidator;
import com.liferay.layout.page.template.validator.PageDefinitionValidator;
import com.liferay.layout.page.template.validator.PageTemplateValidator;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = LayoutPageTemplatesImporter.class)
public class LayoutPageTemplatesImporterImpl
	implements LayoutPageTemplatesImporter {

	@Override
	public void importFile(
			long userId, long groupId, File file, boolean overwrite)
		throws Exception {

		importFile(userId, groupId, 0, file, overwrite);
	}

	@Override
	public List<LayoutPageTemplatesImporterResultEntry> importFile(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			File file, boolean overwrite)
		throws Exception {

		_layoutPageTemplatesImporterResultEntries = new ArrayList<>();

		try (ZipFile zipFile = new ZipFile(file)) {
			_processMasterLayoutPageTemplateEntries(
				groupId, overwrite, zipFile);

			_processDisplayPageTemplatePageTemplateEntries(
				groupId, overwrite, zipFile);

			_processBasicLayoutPageTemplateEntries(
				groupId, layoutPageTemplateCollectionId, overwrite, zipFile);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);

				throw portalException;
			}
		}

		return _layoutPageTemplatesImporterResultEntries;
	}

	@Override
	public List<FragmentEntryLink> importPageElement(
			Layout layout, LayoutStructure layoutStructure, String parentItemId,
			String pageElementJSON, int position)
		throws Exception {

		PageElement pageElement = _objectMapper.readValue(
			pageElementJSON, PageElement.class);

		_processPageElement(
			layout, layoutStructure, pageElement, parentItemId, position);

		List<FragmentEntryLink> fragmentEntryLinks = new ArrayList<>();

		LayoutStructureItem parentLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(parentItemId);

		fragmentEntryLinks.addAll(
			_getFragmentEntryLinks(
				layoutStructure,
				parentLayoutStructureItem.getChildrenItemIds()));

		_updateLayoutPageTemplateStructure(layout, layoutStructure);

		return fragmentEntryLinks;
	}

	private long _getClassTypeId(
		DisplayPageTemplate displayPageTemplate, long groupId) {

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				displayPageTemplate.getContentTypeClassName());

		if (infoDisplayContributor == null) {
			return 0;
		}

		List<ClassType> classTypes = new ArrayList<>();

		try {
			classTypes = infoDisplayContributor.getClassTypes(
				groupId, LocaleUtil.getSiteDefault());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Class types for group %s and locale %s could not be " +
							"obtained"),
					portalException);
			}

			return 0;
		}

		String contentSubtypeName = displayPageTemplate.getContentSubtypeName();

		for (ClassType classType : classTypes) {
			if (contentSubtypeName.equals(classType.getName())) {
				return classType.getClassTypeId();
			}
		}

		return 0;
	}

	private PageTemplateCollectionEntry
		_getDefaultPageTemplateCollectionEntry() {

		PageTemplateCollection pageTemplateCollection =
			new PageTemplateCollection() {
				{
					name = _PAGE_TEMPLATE_COLLECTION_KEY_DEFAULT;
				}
			};

		return new PageTemplateCollectionEntry(
			_PAGE_TEMPLATE_COLLECTION_KEY_DEFAULT, pageTemplateCollection);
	}

	private List<DisplayPageTemplateEntry> _getDisplayPageTemplateEntries(
			long groupId, ZipFile zipFile)
		throws IOException, PortalException {

		List<DisplayPageTemplateEntry> displayPageTemplateEntries =
			new ArrayList<>();

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if ((zipEntry == null) ||
				!_isDisplayPageTemplateFile(zipEntry.getName())) {

				continue;
			}

			String content = StringUtil.read(zipFile.getInputStream(zipEntry));

			DisplayPageTemplate displayPageTemplate = null;

			try {
				DisplayPageTemplateValidator.validateDisplayPageTemplate(
					content);

				displayPageTemplate = _objectMapper.readValue(
					content, DisplayPageTemplate.class);
			}
			catch (DisplayPageTemplateValidatorException
						displayPageTemplateValidatorException) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid display page template for: " +
							zipEntry.getName());
				}

				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						zipEntry.getName(),
						LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
						LayoutPageTemplatesImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-its-display-" +
								"page-template-is-invalid",
							new String[] {zipEntry.getName()})));

				continue;
			}

			try {
				String pageDefinitionJSON = _getPageDefinitionJSON(
					zipEntry.getName(), zipFile);

				PageDefinitionValidator.validatePageDefinition(
					pageDefinitionJSON);

				displayPageTemplateEntries.add(
					new DisplayPageTemplateEntry(
						displayPageTemplate,
						_getKey(
							_DISPLAY_PAGE_TEMPLATE_ENTRY_KEY_DEFAULT,
							displayPageTemplate.getName(), zipEntry),
						_objectMapper.readValue(
							pageDefinitionJSON, PageDefinition.class),
						_getThumbnailZipEntry(zipEntry.getName(), zipFile),
						zipEntry.getName()));
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid page definition for: " +
							displayPageTemplate.getName());
				}

				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						displayPageTemplate.getName(),
						LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
						LayoutPageTemplatesImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-its-page-" +
								"definition-is-invalid",
							new String[] {zipEntry.getName()})));
			}
		}

		return displayPageTemplateEntries;
	}

	private String _getErrorMessage(
			long groupId, String languageKey, String[] arguments)
		throws PortalException {

		Locale locale = null;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			locale = serviceContext.getLocale();
		}
		else {
			locale = _portal.getSiteDefaultLocale(groupId);
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return _language.format(resourceBundle, languageKey, arguments);
	}

	private List<FragmentEntryLink> _getFragmentEntryLinks(
			LayoutStructure layoutStructure, List<String> childrenItemIds)
		throws PortalException {

		List<FragmentEntryLink> fragmentEntryLinks = new ArrayList<>();

		for (String childItemId : childrenItemIds) {
			LayoutStructureItem layoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			if (layoutStructureItem instanceof FragmentLayoutStructureItem) {
				FragmentLayoutStructureItem fragmentLayoutStructureItem =
					(FragmentLayoutStructureItem)layoutStructureItem;

				fragmentEntryLinks.add(
					_fragmentEntryLinkLocalService.getFragmentEntryLink(
						fragmentLayoutStructureItem.getFragmentEntryLinkId()));
			}

			List<String> currentChildrenItemIds =
				layoutStructureItem.getChildrenItemIds();

			fragmentEntryLinks.addAll(
				_getFragmentEntryLinks(
					layoutStructure, currentChildrenItemIds));
		}

		return fragmentEntryLinks;
	}

	private String _getKey(String defaultKey, String name, ZipEntry zipEntry) {
		String[] pathParts = StringUtil.split(
			zipEntry.getName(), CharPool.SLASH);

		String key = defaultKey;

		if (Validator.isNotNull(name)) {
			key = name;
		}

		if (pathParts.length > 1) {
			key = pathParts[pathParts.length - 2];
		}

		key = StringUtil.replace(key, CharPool.SPACE, CharPool.DASH);
		key = StringUtil.toLowerCase(key);

		return key;
	}

	private LayoutPageTemplateCollection _getLayoutPageTemplateCollection(
			long groupId, long layoutPageTemplateCollectionId,
			PageTemplateCollectionEntry pageTemplateCollectionEntry,
			boolean overwrite)
		throws PortalException {

		LayoutPageTemplateCollection layoutPageTemplateCollection = null;

		if (layoutPageTemplateCollectionId > 0) {
			layoutPageTemplateCollection =
				_layoutPageTemplateCollectionService.
					fetchLayoutPageTemplateCollection(
						layoutPageTemplateCollectionId);

			if (layoutPageTemplateCollection == null) {
				throw new PortalException(
					"Invalid layout page template collection ID: " +
						layoutPageTemplateCollectionId);
			}

			return layoutPageTemplateCollection;
		}

		String layoutPageTemplateCollectionKey =
			pageTemplateCollectionEntry.getKey();

		PageTemplateCollection pageTemplateCollection =
			pageTemplateCollectionEntry.getPageTemplateCollection();

		layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollection(
					groupId, layoutPageTemplateCollectionKey);

		if (layoutPageTemplateCollection == null) {
			return _layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					groupId, pageTemplateCollection.getName(),
					pageTemplateCollection.getDescription(),
					ServiceContextThreadLocal.getServiceContext());
		}

		if (overwrite) {
			return _layoutPageTemplateCollectionService.
				updateLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					pageTemplateCollection.getName(),
					pageTemplateCollection.getDescription());
		}

		return layoutPageTemplateCollection;
	}

	private List<MasterPageEntry> _getMasterPageEntries(
			long groupId, ZipFile zipFile)
		throws IOException, PortalException {

		List<MasterPageEntry> masterPageEntries = new ArrayList<>();

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if ((zipEntry == null) || !_isMasterPageFile(zipEntry.getName())) {
				continue;
			}

			String content = StringUtil.read(zipFile.getInputStream(zipEntry));

			MasterPage masterPage = null;

			try {
				MasterPageValidator.validateMasterPage(content);

				masterPage = _objectMapper.readValue(content, MasterPage.class);
			}
			catch (MasterPageValidatorException masterPageValidatorException) {
				if (_log.isWarnEnabled()) {
					_log.warn("Invalid master page for: " + zipEntry.getName());
				}

				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						zipEntry.getName(),
						LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
						LayoutPageTemplatesImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-its-master-page-" +
								"is-invalid",
							new String[] {zipEntry.getName()})));

				continue;
			}

			try {
				String pageDefinitionJSON = _getPageDefinitionJSON(
					zipEntry.getName(), zipFile);

				PageDefinitionValidator.validatePageDefinition(
					pageDefinitionJSON);

				masterPageEntries.add(
					new MasterPageEntry(
						_getKey(
							_MASTER_PAGE_ENTRY_KEY_DEFAULT,
							masterPage.getName(), zipEntry),
						masterPage,
						_objectMapper.readValue(
							pageDefinitionJSON, PageDefinition.class),
						_getThumbnailZipEntry(zipEntry.getName(), zipFile),
						zipEntry.getName()));
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid page definition for: " + masterPage.getName());
				}

				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						masterPage.getName(),
						LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
						LayoutPageTemplatesImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-its-page-" +
								"definition-is-invalid",
							new String[] {zipEntry.getName()})));
			}
		}

		return masterPageEntries;
	}

	private String _getPageDefinitionJSON(String fileName, ZipFile zipFile)
		throws IOException {

		String path = fileName.substring(
			0, fileName.lastIndexOf(StringPool.FORWARD_SLASH) + 1);

		ZipEntry zipEntry = zipFile.getEntry(
			path +
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_PAGE_DEFINITION);

		if (zipEntry == null) {
			return null;
		}

		return StringUtil.read(zipFile.getInputStream(zipEntry));
	}

	private Map<String, PageTemplateCollectionEntry>
			_getPageTemplateCollectionEntryMap(long groupId, ZipFile zipFile)
		throws IOException, PortalException {

		Map<String, PageTemplateCollectionEntry> pageTemplateCollectionMap =
			new HashMap<>();

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if ((zipEntry == null) ||
				!_isPageTemplateCollectionFile(zipEntry.getName())) {

				continue;
			}

			String[] pathParts = StringUtil.split(
				zipEntry.getName(), CharPool.SLASH);

			String pageTemplateCollectionKey = "imported";

			if (pathParts.length > 1) {
				pageTemplateCollectionKey = pathParts[pathParts.length - 2];
			}

			String content = StringUtil.read(zipFile.getInputStream(zipEntry));

			PageTemplateCollection pageTemplateCollection =
				_objectMapper.readValue(content, PageTemplateCollection.class);

			pageTemplateCollectionMap.put(
				pageTemplateCollectionKey,
				new PageTemplateCollectionEntry(
					pageTemplateCollectionKey, pageTemplateCollection));
		}

		enumeration = zipFile.entries();

		if (MapUtil.isEmpty(pageTemplateCollectionMap)) {
			pageTemplateCollectionMap.put(
				_PAGE_TEMPLATE_COLLECTION_KEY_DEFAULT,
				_getDefaultPageTemplateCollectionEntry());
		}

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if ((zipEntry == null) ||
				!_isPageTemplateFile(zipEntry.getName())) {

				continue;
			}

			String content = StringUtil.read(zipFile.getInputStream(zipEntry));

			PageTemplate pageTemplate = null;

			try {
				PageTemplateValidator.validatePageTemplate(content);

				pageTemplate = _objectMapper.readValue(
					content, PageTemplate.class);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid page template for: " + zipEntry.getName());
				}

				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						zipEntry.getName(),
						LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
						LayoutPageTemplatesImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-its-page-" +
								"template-is-invalid",
							new String[] {zipEntry.getName()})));

				continue;
			}

			String pageTemplateCollectionKey = _getPageTemplateCollectionKey(
				zipEntry.getName(), zipFile);

			PageTemplateCollectionEntry pageTemplateCollectionEntry =
				pageTemplateCollectionMap.get(pageTemplateCollectionKey);

			try {
				String pageDefinitionJSON = _getPageDefinitionJSON(
					zipEntry.getName(), zipFile);

				PageDefinitionValidator.validatePageDefinition(
					pageDefinitionJSON);

				PageDefinition pageDefinition = _objectMapper.readValue(
					pageDefinitionJSON, PageDefinition.class);

				pageTemplateCollectionEntry.addPageTemplateEntry(
					_getKey(
						_PAGE_TEMPLATE_ENTRY_KEY_DEFAULT,
						pageTemplate.getName(), zipEntry),
					new PageTemplateEntry(
						pageTemplate, pageDefinition,
						_getThumbnailZipEntry(zipEntry.getName(), zipFile),
						zipEntry.getName()));
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid page definition for: " +
							pageTemplate.getName());
				}

				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						pageTemplate.getName(),
						LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
						LayoutPageTemplatesImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-its-page-" +
								"definition-is-invalid",
							new String[] {zipEntry.getName()})));
			}
		}

		return pageTemplateCollectionMap;
	}

	private String _getPageTemplateCollectionKey(
		String fileName, ZipFile zipFile) {

		if (fileName.lastIndexOf(CharPool.SLASH) == -1) {
			return "imported";
		}

		String path = fileName.substring(
			0, fileName.lastIndexOf(StringPool.FORWARD_SLASH));

		ZipEntry zipEntry = zipFile.getEntry(
			path + CharPool.SLASH +
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_PAGE_TEMPLATE_COLLECTION);

		if (zipEntry == null) {
			return _getPageTemplateCollectionKey(path, zipFile);
		}

		int pos = path.lastIndexOf(CharPool.SLASH);

		String layoutPageTemplateCollectionKey = path.substring(pos + 1);

		if (Validator.isNotNull(layoutPageTemplateCollectionKey)) {
			return layoutPageTemplateCollectionKey;
		}

		return _PAGE_TEMPLATE_COLLECTION_KEY_DEFAULT;
	}

	private long _getPreviewFileEntryId(
			long groupId, long layoutPageTemplateEntryId, ZipEntry zipEntry,
			ZipFile zipFile)
		throws Exception {

		if (zipEntry == null) {
			return 0;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Repository repository = _portletFileRepository.fetchPortletRepository(
			groupId, LayoutAdminPortletKeys.GROUP_PAGES);

		if (repository == null) {
			repository = _portletFileRepository.addPortletRepository(
				groupId, LayoutAdminPortletKeys.GROUP_PAGES, serviceContext);
		}

		String imageFileName =
			layoutPageTemplateEntryId + "_preview." +
				FileUtil.getExtension(zipEntry.getName());

		byte[] bytes = null;

		try (InputStream is = zipFile.getInputStream(zipEntry)) {
			bytes = FileUtil.getBytes(is);
		}

		FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
			groupId, repository.getDlFolderId(), imageFileName);

		if (fileEntry != null) {
			_portletFileRepository.deletePortletFileEntry(
				fileEntry.getFileEntryId());
		}

		fileEntry = _portletFileRepository.addPortletFileEntry(
			groupId, serviceContext.getUserId(),
			LayoutPageTemplateEntry.class.getName(), layoutPageTemplateEntryId,
			LayoutAdminPortletKeys.GROUP_PAGES, repository.getDlFolderId(),
			bytes, imageFileName, MimeTypesUtil.getContentType(imageFileName),
			false);

		return fileEntry.getFileEntryId();
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

	private ZipEntry _getThumbnailZipEntry(String fileName, ZipFile zipFile) {
		String path = fileName.substring(
			0, fileName.lastIndexOf(StringPool.FORWARD_SLASH) + 1);

		for (String thumbnailExtension : _THUMBNAIL_VALID_EXTENSIONS) {
			ZipEntry zipEntry = zipFile.getEntry(
				path + _THUMBNAIL_FILE_NAME + thumbnailExtension);

			if (zipEntry != null) {
				return zipEntry;
			}
		}

		return null;
	}

	private boolean _isDisplayPageTemplateFile(String fileName) {
		if (fileName.endsWith(
				CharPool.SLASH +
					LayoutPageTemplateExportImportConstants.
						FILE_NAME_DISPLAY_PAGE_TEMPLATE)) {

			return true;
		}

		return false;
	}

	private boolean _isMasterPageFile(String fileName) {
		if (fileName.endsWith(
				CharPool.SLASH +
					LayoutPageTemplateExportImportConstants.
						FILE_NAME_MASTER_PAGE)) {

			return true;
		}

		return false;
	}

	private boolean _isPageTemplateCollectionFile(String fileName) {
		if (fileName.endsWith(
				CharPool.SLASH +
					LayoutPageTemplateExportImportConstants.
						FILE_NAME_PAGE_TEMPLATE_COLLECTION)) {

			return true;
		}

		return false;
	}

	private boolean _isPageTemplateFile(String fileName) {
		if (fileName.endsWith(
				CharPool.SLASH +
					LayoutPageTemplateExportImportConstants.
						FILE_NAME_PAGE_TEMPLATE)) {

			return true;
		}

		return false;
	}

	private void _processBasicLayoutPageTemplateEntries(
			long groupId, long layoutPageTemplateCollectionId,
			boolean overwrite, ZipFile zipFile)
		throws Exception {

		Map<String, PageTemplateCollectionEntry>
			pageTemplateCollectionEntryMap = _getPageTemplateCollectionEntryMap(
				groupId, zipFile);

		for (Map.Entry<String, PageTemplateCollectionEntry> entry :
				pageTemplateCollectionEntryMap.entrySet()) {

			PageTemplateCollectionEntry pageTemplateCollectionEntry =
				entry.getValue();

			LayoutPageTemplateCollection layoutPageTemplateCollection =
				_getLayoutPageTemplateCollection(
					groupId, layoutPageTemplateCollectionId,
					pageTemplateCollectionEntry, overwrite);

			_processPageTemplateEntries(
				groupId, layoutPageTemplateCollection,
				pageTemplateCollectionEntry.getPageTemplatesEntries(),
				overwrite, zipFile);
		}
	}

	private void _processDisplayPageTemplatePageTemplateEntries(
			long groupId, boolean overwrite, ZipFile zipFile)
		throws Exception {

		List<DisplayPageTemplateEntry> displayPageTemplateEntries =
			_getDisplayPageTemplateEntries(groupId, zipFile);

		for (DisplayPageTemplateEntry displayPageTemplateEntry :
				displayPageTemplateEntries) {

			Callable<Void> callable = new DisplayPagesImporterCallable(
				groupId, displayPageTemplateEntry, overwrite, zipFile);

			try {
				TransactionInvokerUtil.invoke(_transactionConfig, callable);
			}
			catch (Throwable t) {
				if (_log.isWarnEnabled()) {
					_log.warn(t, t);
				}

				DisplayPageTemplate displayPageTemplate =
					displayPageTemplateEntry.getDisplayPageTemplate();

				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						displayPageTemplate.getName(),
						LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
						LayoutPageTemplatesImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-of-invalid-" +
								"values-in-its-page-definition",
							new String[] {displayPageTemplate.getName()})));
			}
		}
	}

	private void _processLayoutPageTemplateEntry(
			long classNameId, long classTypeId, long groupId,
			long layoutPageTemplateCollectionId,
			LayoutPageTemplateEntry layoutPageTemplateEntry, String name,
			PageDefinition pageDefinition, int layoutPageTemplateEntryType,
			boolean overwrite, ZipEntry thumbnailZipEntry, String zipPath,
			ZipFile zipFile)
		throws Exception {

		try {
			boolean added = false;

			if (layoutPageTemplateEntry == null) {
				if (classNameId == 0) {
					layoutPageTemplateEntry =
						_layoutPageTemplateEntryService.
							addLayoutPageTemplateEntry(
								groupId, layoutPageTemplateCollectionId, name,
								layoutPageTemplateEntryType, 0,
								WorkflowConstants.STATUS_APPROVED,
								ServiceContextThreadLocal.getServiceContext());
				}
				else {
					layoutPageTemplateEntry =
						_layoutPageTemplateEntryService.
							addLayoutPageTemplateEntry(
								groupId, layoutPageTemplateCollectionId,
								classNameId, classTypeId, name, 0,
								WorkflowConstants.STATUS_APPROVED,
								ServiceContextThreadLocal.getServiceContext());
				}

				added = true;
			}
			else if (overwrite) {
				layoutPageTemplateEntry =
					_layoutPageTemplateEntryService.
						updateLayoutPageTemplateEntry(
							layoutPageTemplateEntry.
								getLayoutPageTemplateEntryId(),
							name);

				added = true;
			}

			if (added) {
				_processPageDefinition(layoutPageTemplateEntry, pageDefinition);

				long previewFileEntryId = _getPreviewFileEntryId(
					groupId,
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
					thumbnailZipEntry, zipFile);

				_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
					previewFileEntryId);

				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						name, layoutPageTemplateEntryType,
						LayoutPageTemplatesImporterResultEntry.Status.
							IMPORTED));
			}
			else {
				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						name, layoutPageTemplateEntryType,
						LayoutPageTemplatesImporterResultEntry.Status.IGNORED,
						_getErrorMessage(
							groupId, _MESSAGE_KEY_IGNORED,
							new String[] {
								zipPath,
								_toTypeName(layoutPageTemplateEntryType)
							})));
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}

			_layoutPageTemplatesImporterResultEntries.add(
				new LayoutPageTemplatesImporterResultEntry(
					name, layoutPageTemplateEntryType,
					LayoutPageTemplatesImporterResultEntry.Status.INVALID,
					_getErrorMessage(
						groupId, _MESSAGE_KEY_INVALID,
						new String[] {
							zipPath, _toTypeName(layoutPageTemplateEntryType)
						})));
		}
	}

	private void _processMasterLayoutPageTemplateEntries(
			long groupId, boolean overwrite, ZipFile zipFile)
		throws Exception {

		List<MasterPageEntry> masterPageEntries = _getMasterPageEntries(
			groupId, zipFile);

		for (MasterPageEntry masterPageEntry : masterPageEntries) {
			Callable<Void> callable = new MasterLayoutTemplatesImporterCallable(
				groupId, masterPageEntry, overwrite, zipFile);

			try {
				TransactionInvokerUtil.invoke(_transactionConfig, callable);
			}
			catch (Throwable t) {
				if (_log.isWarnEnabled()) {
					_log.warn(t, t);
				}

				MasterPage masterPage = masterPageEntry.getMasterPage();

				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						masterPage.getName(),
						LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
						LayoutPageTemplatesImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-of-invalid-" +
								"values-in-its-page-definition",
							new String[] {masterPage.getName()})));
			}
		}
	}

	private void _processPageDefinition(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			PageDefinition pageDefinition)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		if (pageDefinition != null) {
			PageElement pageElement = pageDefinition.getPageElement();

			if ((pageElement.getType() == PageElement.Type.ROOT) &&
				(pageElement.getPageElements() != null)) {

				int position = 0;

				for (PageElement childPageElement :
						pageElement.getPageElements()) {

					_processPageElement(
						layout, layoutStructure, childPageElement,
						rootLayoutStructureItem.getItemId(), position);

					position++;
				}
			}

			Settings settings = pageDefinition.getSettings();

			if (settings != null) {
				_updateLayoutSettings(layout, settings);
			}
		}

		_updateLayoutPageTemplateStructure(layout, layoutStructure);

		_updateLayouts(layoutPageTemplateEntry);
	}

	private void _processPageElement(
			Layout layout, LayoutStructure layoutStructure,
			PageElement pageElement, String parentItemId, int position)
		throws Exception {

		LayoutStructureItemImporter layoutStructureItemImporter =
			_layoutStructureItemImporterTracker.getLayoutStructureItemImporter(
				pageElement.getType());

		LayoutStructureItem layoutStructureItem = null;

		if (layoutStructureItemImporter != null) {
			layoutStructureItem =
				layoutStructureItemImporter.addLayoutStructureItem(
					layout, layoutStructure, pageElement, parentItemId,
					position);
		}
		else if (pageElement.getType() == PageElement.Type.ROOT) {
			layoutStructureItem = layoutStructure.getMainLayoutStructureItem();
		}
		else {
			return;
		}

		if ((layoutStructureItem == null) ||
			(pageElement.getPageElements() == null)) {

			return;
		}

		int childPosition = 0;

		for (PageElement childPageElement : pageElement.getPageElements()) {
			_processPageElement(
				layout, layoutStructure, childPageElement,
				layoutStructureItem.getItemId(), childPosition);

			childPosition++;
		}
	}

	private void _processPageTemplateEntries(
			long groupId,
			LayoutPageTemplateCollection layoutPageTemplateCollection,
			Map<String, PageTemplateEntry> pageTemplateEntryMap,
			boolean overwrite, ZipFile zipFile)
		throws PortalException {

		for (Map.Entry<String, PageTemplateEntry> entry :
				pageTemplateEntryMap.entrySet()) {

			PageTemplateEntry pageTemplateEntry = entry.getValue();

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(groupId, entry.getKey());

			Callable<Void> callable =
				new BasicLayoutPageTemplatesImporterCallable(
					groupId,
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					layoutPageTemplateEntry, overwrite, pageTemplateEntry,
					zipFile);

			try {
				TransactionInvokerUtil.invoke(_transactionConfig, callable);
			}
			catch (Throwable t) {
				if (_log.isWarnEnabled()) {
					_log.warn(t, t);
				}

				PageTemplate pageTemplate = pageTemplateEntry.getPageTemplate();

				_layoutPageTemplatesImporterResultEntries.add(
					new LayoutPageTemplatesImporterResultEntry(
						pageTemplate.getName(),
						LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
						LayoutPageTemplatesImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-of-invalid-" +
								"values-in-its-page-definition",
							new String[] {pageTemplate.getName()})));
			}
		}
	}

	private String _toTypeName(int layoutPageTemplateEntryType) {
		if (layoutPageTemplateEntryType ==
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) {

			return "display page template";
		}

		if (layoutPageTemplateEntryType ==
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT) {

			return "master page";
		}

		if (layoutPageTemplateEntryType ==
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) {

			return "page template";
		}

		return null;
	}

	private void _updateLayoutPageTemplateStructure(
			Layout layout, LayoutStructure layoutStructure)
		throws PortalException {

		long classNameId = _portal.getClassNameId(Layout.class.getName());

		JSONObject jsonObject = layoutStructure.toJSONObject();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), classNameId, layout.getPlid());

		if (layoutPageTemplateStructure != null) {
			_layoutPageTemplateStructureLocalService.
				deleteLayoutPageTemplateStructure(layoutPageTemplateStructure);
		}

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			layout.getUserId(), layout.getGroupId(), classNameId,
			layout.getPlid(), jsonObject.toString(),
			ServiceContextThreadLocal.getServiceContext());
	}

	private void _updateLayouts(LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class.getName()), layout.getPlid());

		draftLayout = _layoutCopyHelper.copyLayout(layout, draftLayout);

		_layoutLocalService.updateStatus(
			layoutPageTemplateEntry.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextThreadLocal.getServiceContext());
	}

	private void _updateLayoutSettings(Layout layout, Settings settings) {
		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		Map<String, String> themeSettings =
			(Map<String, String>)settings.getThemeSettings();

		if (themeSettings != null) {
			for (Map.Entry<String, String> entry : themeSettings.entrySet()) {
				unicodeProperties.put(entry.getKey(), entry.getValue());
			}

			layout.setTypeSettingsProperties(unicodeProperties);
		}

		if (Validator.isNotNull(settings.getThemeName())) {
			String themeId = _getThemeId(
				layout.getCompanyId(), settings.getThemeName());

			layout.setThemeId(themeId);
		}

		if (Validator.isNotNull(settings.getColorSchemeName())) {
			layout.setColorSchemeId(settings.getColorSchemeName());
		}

		if (Validator.isNotNull(settings.getCss())) {
			layout.setCss(settings.getCss());
		}

		MasterPage masterPage = settings.getMasterPage();

		if (masterPage != null) {
			LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(
						layout.getGroupId(), masterPage.getKey());

			if (masterLayoutPageTemplateEntry != null) {
				layout.setMasterLayoutPlid(
					masterLayoutPageTemplateEntry.getPlid());
			}
		}

		_layoutLocalService.updateLayout(layout);
	}

	private static final String _DISPLAY_PAGE_TEMPLATE_ENTRY_KEY_DEFAULT =
		"imported-display-page-template";

	private static final String _MASTER_PAGE_ENTRY_KEY_DEFAULT =
		"imported-master-page";

	private static final String _MESSAGE_KEY_IGNORED =
		"x-was-ignored-because-a-x-with-the-same-key-already-exists";

	private static final String _MESSAGE_KEY_INVALID =
		"x-could-not-be-imported-because-a-x-with-the-same-name-already-exists";

	private static final String _PAGE_TEMPLATE_COLLECTION_KEY_DEFAULT =
		"imported";

	private static final String _PAGE_TEMPLATE_ENTRY_KEY_DEFAULT = "imported";

	private static final String _THUMBNAIL_FILE_NAME = "thumbnail";

	private static final String[] _THUMBNAIL_VALID_EXTENSIONS = {
		".bmp", ".gif", ".jpeg", ".jpg", ".png", ".svg", ".tiff"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplatesImporterImpl.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();
	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private Language _language;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	private List<LayoutPageTemplatesImporterResultEntry>
		_layoutPageTemplatesImporterResultEntries;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutStructureItemImporterTracker
		_layoutStructureItemImporterTracker;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private ThemeLocalService _themeLocalService;

	private static class DisplayPageTemplateEntry {

		public DisplayPageTemplateEntry(
			DisplayPageTemplate displayPageTemplate, String key,
			PageDefinition pageDefinition, ZipEntry thumbnailZipEntry,
			String zipPath) {

			_displayPageTemplate = displayPageTemplate;
			_key = key;
			_pageDefinition = pageDefinition;
			_thumbnailZipEntry = thumbnailZipEntry;
			_zipPath = zipPath;
		}

		public DisplayPageTemplate getDisplayPageTemplate() {
			return _displayPageTemplate;
		}

		public String getKey() {
			return _key;
		}

		public PageDefinition getPageDefinition() {
			return _pageDefinition;
		}

		public ZipEntry getThumbnailZipEntry() {
			return _thumbnailZipEntry;
		}

		public String getZipPath() {
			return _zipPath;
		}

		private final DisplayPageTemplate _displayPageTemplate;
		private final String _key;
		private final PageDefinition _pageDefinition;
		private final ZipEntry _thumbnailZipEntry;
		private final String _zipPath;

	}

	private static class MasterPageEntry {

		public MasterPageEntry(
			String key, MasterPage masterPage, PageDefinition pageDefinition,
			ZipEntry thumbnailZipEntry, String zipPath) {

			_key = key;
			_masterPage = masterPage;
			_pageDefinition = pageDefinition;
			_thumbnailZipEntry = thumbnailZipEntry;
			_zipPath = zipPath;
		}

		public String getKey() {
			return _key;
		}

		public MasterPage getMasterPage() {
			return _masterPage;
		}

		public PageDefinition getPageDefinition() {
			return _pageDefinition;
		}

		public ZipEntry getThumbnailZipEntry() {
			return _thumbnailZipEntry;
		}

		public String getZipPath() {
			return _zipPath;
		}

		private final String _key;
		private final MasterPage _masterPage;
		private final PageDefinition _pageDefinition;
		private final ZipEntry _thumbnailZipEntry;
		private final String _zipPath;

	}

	private class BasicLayoutPageTemplatesImporterCallable
		implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			PageTemplate pageTemplate = _pageTemplateEntry.getPageTemplate();

			_processLayoutPageTemplateEntry(
				0, 0, _groupId, _layoutPageTemplateCollectionId,
				_layoutPageTemplateEntry, pageTemplate.getName(),
				_pageTemplateEntry.getPageDefinition(),
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, _overwrite,
				_pageTemplateEntry.getThumbnailZipEntry(),
				_pageTemplateEntry.getZipPath(), _zipFile);

			return null;
		}

		private BasicLayoutPageTemplatesImporterCallable(
			long groupId, long layoutPageTemplateCollectionId,
			LayoutPageTemplateEntry layoutPageTemplateEntry, boolean overwrite,
			PageTemplateEntry pageTemplateEntry, ZipFile zipFile) {

			_groupId = groupId;
			_layoutPageTemplateCollectionId = layoutPageTemplateCollectionId;
			_layoutPageTemplateEntry = layoutPageTemplateEntry;
			_overwrite = overwrite;
			_pageTemplateEntry = pageTemplateEntry;
			_zipFile = zipFile;
		}

		private final long _groupId;
		private final long _layoutPageTemplateCollectionId;
		private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
		private final boolean _overwrite;
		private final PageTemplateEntry _pageTemplateEntry;
		private final ZipFile _zipFile;

	}

	private class DisplayPagesImporterCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			DisplayPageTemplate displayPageTemplate =
				_displayPageTemplateEntry.getDisplayPageTemplate();

			long classNameId = _portal.getClassNameId(
				displayPageTemplate.getContentTypeClassName());
			long classTypeId = _getClassTypeId(displayPageTemplate, _groupId);

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(
						_groupId, _displayPageTemplateEntry.getKey());

			_processLayoutPageTemplateEntry(
				classNameId, classTypeId, _groupId, 0, layoutPageTemplateEntry,
				displayPageTemplate.getName(),
				_displayPageTemplateEntry.getPageDefinition(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
				_overwrite, _displayPageTemplateEntry.getThumbnailZipEntry(),
				_displayPageTemplateEntry.getZipPath(), _zipFile);

			return null;
		}

		private DisplayPagesImporterCallable(
			long groupId, DisplayPageTemplateEntry displayPageTemplateEntry,
			boolean overwrite, ZipFile zipFile) {

			_groupId = groupId;
			_displayPageTemplateEntry = displayPageTemplateEntry;
			_overwrite = overwrite;
			_zipFile = zipFile;
		}

		private final DisplayPageTemplateEntry _displayPageTemplateEntry;
		private final long _groupId;
		private final boolean _overwrite;
		private final ZipFile _zipFile;

	}

	private class MasterLayoutTemplatesImporterCallable
		implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			MasterPage masterPage = _masterPageEntry.getMasterPage();

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(
						_groupId, _masterPageEntry.getKey());

			_processLayoutPageTemplateEntry(
				0, 0, _groupId, 0, layoutPageTemplateEntry,
				masterPage.getName(), _masterPageEntry.getPageDefinition(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
				_overwrite, _masterPageEntry.getThumbnailZipEntry(),
				_masterPageEntry.getZipPath(), _zipFile);

			return null;
		}

		private MasterLayoutTemplatesImporterCallable(
			long groupId, MasterPageEntry masterPageEntry, boolean overwrite,
			ZipFile zipFile) {

			_groupId = groupId;
			_masterPageEntry = masterPageEntry;
			_overwrite = overwrite;
			_zipFile = zipFile;
		}

		private final long _groupId;
		private final MasterPageEntry _masterPageEntry;
		private final boolean _overwrite;
		private final ZipFile _zipFile;

	}

	private class PageTemplateCollectionEntry {

		public PageTemplateCollectionEntry(
			String key, PageTemplateCollection pageTemplateCollection) {

			_key = key;
			_pageTemplateCollection = pageTemplateCollection;

			_pageTemplateEntries = new HashMap<>();
		}

		public void addPageTemplateEntry(
			String key, PageTemplateEntry pageTemplateEntry) {

			_pageTemplateEntries.put(key, pageTemplateEntry);
		}

		public String getKey() {
			return _key;
		}

		public PageTemplateCollection getPageTemplateCollection() {
			return _pageTemplateCollection;
		}

		public Map<String, PageTemplateEntry> getPageTemplatesEntries() {
			return _pageTemplateEntries;
		}

		private final String _key;
		private final PageTemplateCollection _pageTemplateCollection;
		private final Map<String, PageTemplateEntry> _pageTemplateEntries;

	}

	private class PageTemplateEntry {

		public PageTemplateEntry(
			PageTemplate pageTemplate, PageDefinition pageDefinition,
			ZipEntry thumbnailZipEntry, String zipPath) {

			_pageTemplate = pageTemplate;
			_pageDefinition = pageDefinition;
			_thumbnailZipEntry = thumbnailZipEntry;
			_zipPath = zipPath;
		}

		public PageDefinition getPageDefinition() {
			return _pageDefinition;
		}

		public PageTemplate getPageTemplate() {
			return _pageTemplate;
		}

		public ZipEntry getThumbnailZipEntry() {
			return _thumbnailZipEntry;
		}

		public String getZipPath() {
			return _zipPath;
		}

		private final PageDefinition _pageDefinition;
		private final PageTemplate _pageTemplate;
		private final ZipEntry _thumbnailZipEntry;
		private final String _zipPath;

	}

}
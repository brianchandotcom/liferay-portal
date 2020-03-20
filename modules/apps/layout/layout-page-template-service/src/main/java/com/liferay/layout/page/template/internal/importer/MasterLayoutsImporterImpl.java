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

package com.liferay.layout.page.template.internal.importer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.headless.delivery.dto.v1_0.MasterPage;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.Settings;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.exception.PageDefinitionValidatorException;
import com.liferay.layout.page.template.importer.MasterLayoutsImporter;
import com.liferay.layout.page.template.importer.MasterLayoutsImporterResultEntry;
import com.liferay.layout.page.template.internal.importer.helper.LayoutStructureItemHelper;
import com.liferay.layout.page.template.internal.importer.helper.LayoutStructureItemHelperFactory;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.validator.PageDefinitionValidator;
import com.liferay.layout.util.LayoutCopyHelper;
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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = MasterLayoutsImporter.class)
public class MasterLayoutsImporterImpl implements MasterLayoutsImporter {

	@Override
	public List<MasterLayoutsImporterResultEntry> importFile(
			long userId, long groupId, File file, boolean overwrite)
		throws Exception {

		_masterLayoutsImporterResultEntries = new ArrayList<>();

		try (ZipFile zipFile = new ZipFile(file)) {
			_processMasterPageEntries(
				groupId, _getMasterPageEntries(groupId, zipFile), overwrite,
				zipFile);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);

				throw portalException;
			}
		}

		return _masterLayoutsImporterResultEntries;
	}

	private String _getErrorMessage(
			long groupId, String languageKey, String resourceName)
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

		return _language.format(resourceBundle, languageKey, resourceName);
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

			MasterPage masterPage = _objectMapper.readValue(
				content, MasterPage.class);

			try {
				String pageDefinitionJSON = _getPageDefinitionJSON(
					zipEntry.getName(), zipFile);

				PageDefinitionValidator.validatePageDefinition(
					pageDefinitionJSON);

				PageDefinition pageDefinition = _objectMapper.readValue(
					pageDefinitionJSON, PageDefinition.class);

				ZipEntry thumbnailZipEntry = _getThumbnailZipEntry(
					zipEntry.getName(), zipFile);

				masterPageEntries.add(
					new MasterPageEntry(
						masterPage, pageDefinition, thumbnailZipEntry,
						zipEntry.getName()));
			}
			catch (PageDefinitionValidatorException
						pageDefinitionValidatorException) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Invalid page definition for: " + masterPage.getName());
				}

				_masterLayoutsImporterResultEntries.add(
					new MasterLayoutsImporterResultEntry(
						masterPage.getName(),
						MasterLayoutsImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-its-page-" +
								"definition-is-invalid",
							zipEntry.getName())));
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

		FileEntry fileEntry = _portletFileRepository.addPortletFileEntry(
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

		return zipFile.getEntry(path + _FILE_NAME_THUMBNAIL);
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

	private void _processMasterPageEntries(
			long groupId, List<MasterPageEntry> masterPageEntries,
			boolean overwrite, ZipFile zipFile)
		throws Exception {

		for (MasterPageEntry masterPageEntry : masterPageEntries) {
			MasterPage masterPage = masterPageEntry.getMasterPage();

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(groupId, masterPage.getKey());

			try {
				boolean added = false;

				if (layoutPageTemplateEntry == null) {
					layoutPageTemplateEntry =
						_layoutPageTemplateEntryService.
							addLayoutPageTemplateEntry(
								groupId, 0, masterPage.getName(),
								LayoutPageTemplateEntryTypeConstants.
									TYPE_MASTER_LAYOUT,
								0, WorkflowConstants.STATUS_APPROVED,
								ServiceContextThreadLocal.getServiceContext());

					added = true;
				}
				else if (overwrite) {
					layoutPageTemplateEntry =
						_layoutPageTemplateEntryService.
							updateLayoutPageTemplateEntry(
								layoutPageTemplateEntry.
									getLayoutPageTemplateEntryId(),
								masterPage.getName());

					added = true;
				}

				if (added) {
					_processPageDefinition(
						layoutPageTemplateEntry,
						masterPageEntry.getPageDefinition());

					long previewFileEntryId = _getPreviewFileEntryId(
						groupId,
						layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
						masterPageEntry.getThumbnailZipEntry(), zipFile);

					_layoutPageTemplateEntryService.
						updateLayoutPageTemplateEntry(
							layoutPageTemplateEntry.
								getLayoutPageTemplateEntryId(),
							previewFileEntryId);

					_masterLayoutsImporterResultEntries.add(
						new MasterLayoutsImporterResultEntry(
							masterPage.getName(),
							MasterLayoutsImporterResultEntry.Status.IMPORTED));
				}
				else {
					_masterLayoutsImporterResultEntries.add(
						new MasterLayoutsImporterResultEntry(
							masterPage.getName(),
							MasterLayoutsImporterResultEntry.Status.IGNORED,
							_getErrorMessage(
								groupId,
								"x-was-ignored-because-a-master-page-with-" +
									"the-same-key-already-exists",
								masterPageEntry.getZipPath())));
				}
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException, portalException);
				}

				_masterLayoutsImporterResultEntries.add(
					new MasterLayoutsImporterResultEntry(
						masterPage.getName(),
						MasterLayoutsImporterResultEntry.Status.INVALID,
						_getErrorMessage(
							groupId,
							"x-could-not-be-imported-because-a-master-page-" +
								"with-the-same-name-already-exists",
							masterPageEntry.getZipPath())));
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

		LayoutStructureItemHelperFactory layoutStructureItemHelperFactory =
			LayoutStructureItemHelperFactory.getInstance();

		LayoutStructureItemHelper layoutStructureItemHelper =
			layoutStructureItemHelperFactory.getLayoutStructureItemHelper(
				pageElement.getType());

		if (layoutStructureItemHelper == null) {
			return;
		}

		LayoutStructureItem layoutStructureItem =
			layoutStructureItemHelper.addLayoutStructureItem(
				_fragmentCollectionContributorTracker,
				_fragmentEntryProcessorRegistry, _fragmentEntryValidator,
				layout, layoutStructure, pageElement, parentItemId, position);

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
				deleteLayoutPageTemplateStructure(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId());
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

		_layoutCopyHelper.copyLayout(layout, draftLayout);
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

		_layoutLocalService.updateLayout(layout);
	}

	private static final String _FILE_NAME_THUMBNAIL = "thumbnail.jpg";

	private static final Log _log = LogFactoryUtil.getLog(
		MasterLayoutsImporterImpl.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private FragmentEntryValidator _fragmentEntryValidator;

	@Reference
	private Language _language;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	private List<MasterLayoutsImporterResultEntry>
		_masterLayoutsImporterResultEntries;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private ThemeLocalService _themeLocalService;

	private static class MasterPageEntry {

		public MasterPageEntry(
			MasterPage masterPage, PageDefinition pageDefinition,
			ZipEntry thumbnailZipEntry, String zipPath) {

			_masterPage = masterPage;
			_pageDefinition = pageDefinition;
			_thumbnailZipEntry = thumbnailZipEntry;
			_zipPath = zipPath;
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

		private final MasterPage _masterPage;
		private final PageDefinition _pageDefinition;
		private final ZipEntry _thumbnailZipEntry;
		private final String _zipPath;

	}

}
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

package com.liferay.layout.page.template.admin.web.internal.portlet.helper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.DisplayPageTemplateConverterUtil;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.MasterPageConverterUtil;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.PageDefinitionConverterUtil;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.PageTemplateCollectionConverterUtil;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.PageTemplateConverterUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = ExportHelper.class)
public class ExportHelper {

	public File exportDisplayPages(
			List<LayoutPageTemplateEntry> layoutPageTemplateEntries)
		throws PortletException {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		try {
			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					layoutPageTemplateEntries) {

				_populateDisplayPagesZipWriter(
					layoutPageTemplateEntry, zipWriter);
			}

			zipWriter.finish();

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	public File exportMasterLayouts(
			List<LayoutPageTemplateEntry> layoutPageTemplateEntries)
		throws PortletException {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		try {
			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					layoutPageTemplateEntries) {

				_populateMasterLayoutsZipWriter(
					layoutPageTemplateEntry, zipWriter);
			}

			zipWriter.finish();

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	public File exportPageTemplates(
			List<LayoutPageTemplateEntry> layoutPageTemplateEntries)
		throws PortletException {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		Map<Long, LayoutPageTemplateCollection>
			layoutPageTemplateCollectionKeyMap = new HashMap<>();

		try {
			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					layoutPageTemplateEntries) {

				_populateLayoutPageTemplateCollectionKeyMap(
					layoutPageTemplateCollectionKeyMap,
					layoutPageTemplateEntry);

				_populatePageTemplatesZipWriter(
					layoutPageTemplateEntry, layoutPageTemplateCollectionKeyMap,
					zipWriter);
			}

			zipWriter.finish();

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	private FileEntry _getPreviewFileEntry(long previewFileEntryId) {
		if (previewFileEntryId <= 0) {
			return null;
		}

		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(
				previewFileEntryId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get file entry preview", portalException);
			}
		}

		return null;
	}

	private void _populateDisplayPagesZipWriter(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			ZipWriter zipWriter)
		throws Exception {

		String displayPagePath =
			"display-page-templates" + StringPool.SLASH +
				layoutPageTemplateEntry.getLayoutPageTemplateEntryKey();

		SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();

		FilterProvider filterProvider = simpleFilterProvider.addFilter(
			"Liferay.Vulcan", SimpleBeanPropertyFilter.serializeAll());

		ObjectWriter objectWriter = _objectMapper.writer(filterProvider);

		zipWriter.addEntry(
			displayPagePath + StringPool.SLASH +
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_DISPLAY_PAGE_TEMPLATE,
			objectWriter.writeValueAsString(
				DisplayPageTemplateConverterUtil.toDisplayPageTemplate(
					layoutPageTemplateEntry)));

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		if (layout != null) {
			PageDefinition pageDefinition =
				PageDefinitionConverterUtil.toPageDefinition(
					_fragmentCollectionContributorTracker,
					_fragmentEntryConfigurationParser, _fragmentRendererTracker,
					_infoDisplayContributorTracker, layout);

			zipWriter.addEntry(
				displayPagePath + "/page-definition.json",
				objectWriter.writeValueAsString(pageDefinition));
		}

		FileEntry previewFileEntry = _getPreviewFileEntry(
			layoutPageTemplateEntry.getPreviewFileEntryId());

		if (previewFileEntry != null) {
			zipWriter.addEntry(
				displayPagePath + "/thumbnail." +
					previewFileEntry.getExtension(),
				previewFileEntry.getContentStream());
		}
	}

	private void _populateLayoutPageTemplateCollectionKeyMap(
			Map<Long, LayoutPageTemplateCollection>
				layoutPageTemplateCollectionKeyMap,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		long layoutPageTemplateCollectionId =
			layoutPageTemplateEntry.getLayoutPageTemplateCollectionId();

		if (layoutPageTemplateCollectionKeyMap.containsKey(
				layoutPageTemplateCollectionId)) {

			return;
		}

		layoutPageTemplateCollectionKeyMap.put(
			layoutPageTemplateCollectionId,
			_layoutPageTemplateCollectionLocalService.
				getLayoutPageTemplateCollection(
					layoutPageTemplateCollectionId));
	}

	private void _populateMasterLayoutsZipWriter(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			ZipWriter zipWriter)
		throws Exception {

		String masterLayoutPath =
			"master-pages" + StringPool.SLASH +
				layoutPageTemplateEntry.getLayoutPageTemplateEntryKey();

		SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();

		FilterProvider filterProvider = simpleFilterProvider.addFilter(
			"Liferay.Vulcan", SimpleBeanPropertyFilter.serializeAll());

		ObjectWriter objectWriter = _objectMapper.writer(filterProvider);

		zipWriter.addEntry(
			masterLayoutPath + StringPool.SLASH +
				LayoutPageTemplateExportImportConstants.FILE_NAME_MASTER_PAGE,
			objectWriter.writeValueAsString(
				MasterPageConverterUtil.toMasterPage(layoutPageTemplateEntry)));

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		if (layout != null) {
			PageDefinition pageDefinition =
				PageDefinitionConverterUtil.toPageDefinition(
					_fragmentCollectionContributorTracker,
					_fragmentEntryConfigurationParser, _fragmentRendererTracker,
					_infoDisplayContributorTracker, layout);

			zipWriter.addEntry(
				masterLayoutPath + "/page-definition.json",
				objectWriter.writeValueAsString(pageDefinition));
		}

		FileEntry previewFileEntry = _getPreviewFileEntry(
			layoutPageTemplateEntry.getPreviewFileEntryId());

		if (previewFileEntry != null) {
			zipWriter.addEntry(
				masterLayoutPath + "/thumbnail." +
					previewFileEntry.getExtension(),
				previewFileEntry.getContentStream());
		}
	}

	private void _populatePageTemplatesZipWriter(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			Map<Long, LayoutPageTemplateCollection>
				layoutPageTemplateCollectionKeyMap,
			ZipWriter zipWriter)
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			layoutPageTemplateCollectionKeyMap.get(
				layoutPageTemplateEntry.getLayoutPageTemplateCollectionId());

		String layoutPageTemplateCollectionKey =
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionKey();

		String layoutPageTemplateCollectionPath =
			"page-templates" + StringPool.SLASH +
				layoutPageTemplateCollectionKey;

		SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();

		FilterProvider filterProvider = simpleFilterProvider.addFilter(
			"Liferay.Vulcan", SimpleBeanPropertyFilter.serializeAll());

		ObjectWriter objectWriter = _objectMapper.writer(filterProvider);

		zipWriter.addEntry(
			layoutPageTemplateCollectionPath + StringPool.SLASH +
				LayoutPageTemplateExportImportConstants.
					FILE_NAME_PAGE_TEMPLATE_COLLECTION,
			objectWriter.writeValueAsString(
				PageTemplateCollectionConverterUtil.toPageTemplateCollection(
					layoutPageTemplateCollection)));

		String layoutPageTemplateEntryPath =
			layoutPageTemplateCollectionPath + StringPool.SLASH +
				layoutPageTemplateEntry.getLayoutPageTemplateEntryKey();

		zipWriter.addEntry(
			layoutPageTemplateEntryPath + StringPool.SLASH +
				LayoutPageTemplateExportImportConstants.FILE_NAME_PAGE_TEMPLATE,
			objectWriter.writeValueAsString(
				PageTemplateConverterUtil.toPageTemplate(
					layoutPageTemplateEntry)));

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		if (layout != null) {
			PageDefinition pageDefinition =
				PageDefinitionConverterUtil.toPageDefinition(
					_fragmentCollectionContributorTracker,
					_fragmentEntryConfigurationParser, _fragmentRendererTracker,
					_infoDisplayContributorTracker, layout);

			zipWriter.addEntry(
				layoutPageTemplateEntryPath + "/page-definition.json",
				objectWriter.writeValueAsString(pageDefinition));
		}

		FileEntry previewFileEntry = _getPreviewFileEntry(
			layoutPageTemplateEntry.getPreviewFileEntryId());

		if (previewFileEntry != null) {
			zipWriter.addEntry(
				layoutPageTemplateEntryPath + "/thumbnail." +
					previewFileEntry.getExtension(),
				previewFileEntry.getContentStream());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ExportHelper.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
			enable(SerializationFeature.INDENT_OUTPUT);
			setDateFormat(new ISO8601DateFormat());
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
			setVisibility(
				PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
			setVisibility(
				PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
		}
	};

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

}
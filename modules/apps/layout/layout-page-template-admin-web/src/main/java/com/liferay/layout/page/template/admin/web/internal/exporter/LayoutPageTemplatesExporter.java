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

package com.liferay.layout.page.template.admin.web.internal.exporter;

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

import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter.DisplayPageTemplateDTOConverter;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter.MasterPageDTOConverter;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter.PageTemplateCollectionDTOConverter;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter.PageTemplateDTOConverter;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
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
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

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
@Component(immediate = true, service = LayoutPageTemplatesExporter.class)
public class LayoutPageTemplatesExporter {

	public File exportDisplayPages(
			List<LayoutPageTemplateEntry> layoutPageTemplateEntries)
		throws PortletException {

		DTOConverter<LayoutStructure, PageDefinition>
			pageDefinitionDTOConverter = _getPageDefinitionDTOConverter();
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		try {
			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					layoutPageTemplateEntries) {

				if (layoutPageTemplateEntry.isDraft() ||
					(layoutPageTemplateEntry.getType() !=
						LayoutPageTemplateEntryTypeConstants.
							TYPE_DISPLAY_PAGE)) {

					continue;
				}

				_populateDisplayPagesZipWriter(
					layoutPageTemplateEntry, pageDefinitionDTOConverter,
					zipWriter);
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	public File exportGroupLayoutPageTemplates(long groupId)
		throws PortletException {

		Map<Long, LayoutPageTemplateCollection>
			layoutPageTemplateCollectionKeyMap = new HashMap<>();
		DTOConverter<LayoutStructure, PageDefinition>
			pageDefinitionDTOConverter = _getPageDefinitionDTOConverter();
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntries(
				groupId);

		try {
			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					layoutPageTemplateEntries) {

				if (layoutPageTemplateEntry.isDraft()) {
					continue;
				}

				if (layoutPageTemplateEntry.getType() ==
						LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) {

					_populateLayoutPageTemplateCollectionKeyMap(
						layoutPageTemplateCollectionKeyMap,
						layoutPageTemplateEntry);

					_populatePageTemplatesZipWriter(
						layoutPageTemplateEntry,
						layoutPageTemplateCollectionKeyMap,
						pageDefinitionDTOConverter, zipWriter);
				}
				else if (layoutPageTemplateEntry.getType() ==
							LayoutPageTemplateEntryTypeConstants.
								TYPE_DISPLAY_PAGE) {

					_populateDisplayPagesZipWriter(
						layoutPageTemplateEntry, pageDefinitionDTOConverter,
						zipWriter);
				}
				else if (layoutPageTemplateEntry.getType() ==
							LayoutPageTemplateEntryTypeConstants.
								TYPE_MASTER_LAYOUT) {

					_populateMasterLayoutsZipWriter(
						layoutPageTemplateEntry, pageDefinitionDTOConverter,
						zipWriter);
				}
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	public File exportMasterLayouts(
			List<LayoutPageTemplateEntry> layoutPageTemplateEntries)
		throws PortletException {

		DTOConverter<LayoutStructure, PageDefinition>
			pageDefinitionDTOConverter = _getPageDefinitionDTOConverter();
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		try {
			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					layoutPageTemplateEntries) {

				if (layoutPageTemplateEntry.isDraft() ||
					(layoutPageTemplateEntry.getType() !=
						LayoutPageTemplateEntryTypeConstants.
							TYPE_MASTER_LAYOUT)) {

					continue;
				}

				_populateMasterLayoutsZipWriter(
					layoutPageTemplateEntry, pageDefinitionDTOConverter,
					zipWriter);
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	public File exportPageTemplates(
			List<LayoutPageTemplateEntry> layoutPageTemplateEntries)
		throws PortletException {

		Map<Long, LayoutPageTemplateCollection>
			layoutPageTemplateCollectionKeyMap = new HashMap<>();
		DTOConverter<LayoutStructure, PageDefinition>
			pageDefinitionDTOConverter = _getPageDefinitionDTOConverter();
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		try {
			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					layoutPageTemplateEntries) {

				if (layoutPageTemplateEntry.isDraft() ||
					(layoutPageTemplateEntry.getType() !=
						LayoutPageTemplateEntryTypeConstants.TYPE_BASIC)) {

					continue;
				}

				_populateLayoutPageTemplateCollectionKeyMap(
					layoutPageTemplateCollectionKeyMap,
					layoutPageTemplateEntry);

				_populatePageTemplatesZipWriter(
					layoutPageTemplateEntry, layoutPageTemplateCollectionKeyMap,
					pageDefinitionDTOConverter, zipWriter);
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	private DTOConverterContext _getDTOConverterContext(
		Layout layout, LayoutStructure layoutStructure) {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				_dtoConverterRegistry, layoutStructure.getMainItemId(), null,
				null, null);

		dtoConverterContext.setAttribute("layout", layout);

		return dtoConverterContext;
	}

	private LayoutStructure _getLayoutStructure(Layout layout) {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		return LayoutStructure.of(layoutPageTemplateStructure.getData(0L));
	}

	private DTOConverter<LayoutStructure, PageDefinition>
		_getPageDefinitionDTOConverter() {

		return (DTOConverter<LayoutStructure, PageDefinition>)
			_dtoConverterRegistry.getDTOConverter(
				LayoutStructure.class.getName());
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
			DTOConverter<LayoutStructure, PageDefinition>
				pageDefinitionDTOConverter,
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
				DisplayPageTemplateDTOConverter.toDTO(
					layoutPageTemplateEntry)));

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		if (layout != null) {
			LayoutStructure layoutStructure = _getLayoutStructure(layout);

			PageDefinition pageDefinition = pageDefinitionDTOConverter.toDTO(
				_getDTOConverterContext(layout, layoutStructure),
				layoutStructure);

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
			DTOConverter<LayoutStructure, PageDefinition>
				pageDefinitionDTOConverter,
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
				MasterPageDTOConverter.toDTO(layoutPageTemplateEntry)));

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		if (layout != null) {
			LayoutStructure layoutStructure = _getLayoutStructure(layout);

			PageDefinition pageDefinition = pageDefinitionDTOConverter.toDTO(
				_getDTOConverterContext(layout, layoutStructure),
				layoutStructure);

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
			DTOConverter<LayoutStructure, PageDefinition>
				pageDefinitionDTOConverter,
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
				PageTemplateCollectionDTOConverter.toDTO(
					layoutPageTemplateCollection)));

		String layoutPageTemplateEntryPath =
			layoutPageTemplateCollectionPath + StringPool.SLASH +
				layoutPageTemplateEntry.getLayoutPageTemplateEntryKey();

		zipWriter.addEntry(
			layoutPageTemplateEntryPath + StringPool.SLASH +
				LayoutPageTemplateExportImportConstants.FILE_NAME_PAGE_TEMPLATE,
			objectWriter.writeValueAsString(
				PageTemplateDTOConverter.toDTO(layoutPageTemplateEntry)));

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		if (layout != null) {
			LayoutStructure layoutStructure = _getLayoutStructure(layout);

			PageDefinition pageDefinition = pageDefinitionDTOConverter.toDTO(
				_getDTOConverterContext(layout, layoutStructure),
				layoutStructure);

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

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplatesExporter.class);

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
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

}
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

package com.liferay.layout.admin.web.internal.exporter;

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
import com.liferay.layout.admin.web.internal.headless.delivery.dto.v1_0.UtilityPageTemplateDTOConverter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.File;

import java.util.List;

import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutUtilityPageEntryExporter.class)
public class LayoutUtilityPageEntryExporter {

	public File exportUtilityPages(
			List<LayoutUtilityPageEntry> layoutUtilityPageEntries)
		throws PortletException {

		DTOConverter<LayoutStructure, PageDefinition>
			pageDefinitionDTOConverter = _getPageDefinitionDTOConverter();
		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		try {
			for (LayoutUtilityPageEntry layoutUtilityPageEntry :
					layoutUtilityPageEntries) {

				_populateLayoutUtilityPageEntriesZipWriter(
					layoutUtilityPageEntry, pageDefinitionDTOConverter,
					zipWriter);
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

		return LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());
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

	private void _populateLayoutUtilityPageEntriesZipWriter(
			LayoutUtilityPageEntry layoutUtilityPageEntry,
			DTOConverter<LayoutStructure, PageDefinition>
				pageDefinitionDTOConverter,
			ZipWriter zipWriter)
		throws Exception {

		String layoutUtilityPageEntryPath =
			"layout-utility-page-template/" +
				layoutUtilityPageEntry.getExternalReferenceCode();

		SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();

		FilterProvider filterProvider = simpleFilterProvider.addFilter(
			"Liferay.Vulcan", SimpleBeanPropertyFilter.serializeAll());

		ObjectWriter objectWriter = _objectMapper.writer(filterProvider);

		zipWriter.addEntry(
			layoutUtilityPageEntryPath + StringPool.SLASH +
				_FILE_NAME_UTILITY_PAGE,
			objectWriter.writeValueAsString(
				UtilityPageTemplateDTOConverter.toDTO(layoutUtilityPageEntry)));

		Layout layout = _layoutLocalService.fetchLayout(
			layoutUtilityPageEntry.getPlid());

		if (layout != null) {
			LayoutStructure layoutStructure = _getLayoutStructure(layout);

			PageDefinition pageDefinition = pageDefinitionDTOConverter.toDTO(
				_getDTOConverterContext(layout, layoutStructure),
				layoutStructure);

			zipWriter.addEntry(
				layoutUtilityPageEntryPath + "/page-definition.json",
				objectWriter.writeValueAsString(pageDefinition));
		}

		FileEntry previewFileEntry = _getPreviewFileEntry(
			layoutUtilityPageEntry.getPreviewFileEntryId());

		if (previewFileEntry != null) {
			zipWriter.addEntry(
				layoutUtilityPageEntryPath + "/thumbnail." +
					previewFileEntry.getExtension(),
				previewFileEntry.getContentStream());
		}
	}

	private static final String _FILE_NAME_UTILITY_PAGE = "utility-page.json";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutUtilityPageEntryExporter.class);

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
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private ZipWriterFactory _zipWriterFactory;

}
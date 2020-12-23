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

package com.liferay.layout.page.template.admin.web.internal.serializer;

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

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.serializer.LayoutStructureItemJSONSerializer;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemJSONSerializer.class)
public class LayoutStructureItemJSONSerializerImpl
	implements LayoutStructureItemJSONSerializer {

	@Override
	public String toJSONString(
			Layout layout, String layoutStructureItemId,
			boolean saveInlineContent, boolean saveMappingConfiguration,
			long segmentsExperienceId)
		throws PortalException {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				_dtoConverterRegistry, layoutStructureItemId, null, null, null);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		dtoConverterContext.setAttribute("groupId", layout.getGroupId());
		dtoConverterContext.setAttribute("layoutStructure", layoutStructure);
		dtoConverterContext.setAttribute(
			"saveInlineContent", saveInlineContent);
		dtoConverterContext.setAttribute(
			"saveMappingConfiguration", saveMappingConfiguration);

		DTOConverter<LayoutStructureItem, PageElement> pageElementDTOConverter =
			(DTOConverter<LayoutStructureItem, PageElement>)
				_dtoConverterRegistry.getDTOConverter(
					LayoutStructureItem.class.getName());

		try {
			PageElement pageElement = pageElementDTOConverter.toDTO(
				dtoConverterContext,
				layoutStructure.getMainLayoutStructureItem());

			SimpleFilterProvider simpleFilterProvider =
				new SimpleFilterProvider();

			FilterProvider filterProvider = simpleFilterProvider.addFilter(
				"Liferay.Vulcan", SimpleBeanPropertyFilter.serializeAll());

			ObjectWriter objectWriter = _objectMapper.writer(filterProvider);

			return objectWriter.writeValueAsString(pageElement);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
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
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

}
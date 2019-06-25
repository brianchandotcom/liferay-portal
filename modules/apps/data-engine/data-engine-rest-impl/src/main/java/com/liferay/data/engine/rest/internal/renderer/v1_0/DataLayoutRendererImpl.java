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

package com.liferay.data.engine.rest.internal.renderer.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutRow;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataDefinitionFieldUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataLayoutUtil;
import com.liferay.data.engine.spi.field.type.FieldType;
import com.liferay.data.engine.spi.field.type.FieldTypeTracker;
import com.liferay.data.engine.spi.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.spi.renderer.DataLayoutRenderer;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.renderer.ComponentDescriptor;
import com.liferay.portal.template.soy.renderer.SoyComponentRenderer;

import java.io.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(immediate = true, service = DataLayoutRenderer.class)
public class DataLayoutRendererImpl implements DataLayoutRenderer {

	@Override
	public String render(
			Long dataLayoutId, Map<String, Object> dataRecordValues,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		Writer writer = new UnsyncStringWriter();

		ComponentDescriptor componentDescriptor = new ComponentDescriptor(
			_TEMPLATE_NAMESPACE, _npmResolver.resolveModuleName(_MODULE_NAME));

		Map<String, Object> context = new HashMap<>();

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DataLayout dataLayout = DataLayoutUtil.toDataLayout(
			ddmStructureLayout.getDefinition());

		context.put(
			"pages",
			_createDataLayoutPageContexts(
				_getDataDefinitionFieldsMap(
					DataDefinitionUtil.toDataDefinition(
						ddmStructureVersion.getStructure()),
					dataRecordValues),
				dataLayout.getDataLayoutPages(), _fieldTypeTracker,
				httpServletRequest, httpServletResponse));

		context.put("paginationMode", dataLayout.getPaginationMode());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String pathThemeImages = themeDisplay.getPathThemeImages();

		context.put("spritemap", pathThemeImages.concat("/clay/icons.svg"));

		_soyComponentRenderer.renderSoyComponent(
			httpServletRequest, writer, componentDescriptor, context);

		return writer.toString();
	}

	private List<Object> _createDataLayoutColumnContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		DataLayoutColumn[] dataLayoutColumns, FieldTypeTracker fieldTypeTracker,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		List<Object> dataLayoutColumnContexts = new ArrayList<>();

		for (DataLayoutColumn dataLayoutColumn : dataLayoutColumns) {
			Map<String, Object> dataLayoutColumnsContext = new HashMap<>();

			dataLayoutColumnsContext.put(
				"fields",
				_createFieldTypeContexts(
					dataDefinitionFields, dataLayoutColumn.getFieldNames(),
					fieldTypeTracker, httpServletRequest, httpServletResponse));
			dataLayoutColumnsContext.put(
				"size", dataLayoutColumn.getColumnSize());

			dataLayoutColumnContexts.add(dataLayoutColumnsContext);
		}

		return dataLayoutColumnContexts;
	}

	private List<Object> _createDataLayoutPageContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		DataLayoutPage[] dataLayoutPages, FieldTypeTracker fieldTypeTracker,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		List<Object> dataLayoutPageContexts = new ArrayList<>();

		for (DataLayoutPage dataLayoutPage : dataLayoutPages) {
			Map<String, Object> dataLayoutPageContext = new HashMap<>();

			dataLayoutPageContext.put(
				"description",
				GetterUtil.getString(
					LocalizedValueUtil.getLocalizedValue(
						httpServletRequest.getLocale(),
						dataLayoutPage.getDescription())));

			dataLayoutPageContext.put(
				"rows",
				_createDataLayoutRowContexts(
					dataDefinitionFields, dataLayoutPage.getDataLayoutRows(),
					fieldTypeTracker, httpServletRequest, httpServletResponse));

			dataLayoutPageContext.put(
				"title",
				GetterUtil.getString(
					LocalizedValueUtil.getLocalizedValue(
						httpServletRequest.getLocale(),
						dataLayoutPage.getTitle())));

			dataLayoutPageContexts.add(dataLayoutPageContext);
		}

		return dataLayoutPageContexts;
	}

	private List<Object> _createDataLayoutRowContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		DataLayoutRow[] dataLayoutRows, FieldTypeTracker fieldTypeTracker,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		List<Object> dataLayoutRowContexts = new ArrayList<>();

		for (DataLayoutRow dataLayoutRow : dataLayoutRows) {
			Map<String, Object> dataLayoutRowContext = new HashMap<>();

			dataLayoutRowContext.put(
				"columns",
				_createDataLayoutColumnContexts(
					dataDefinitionFields, dataLayoutRow.getDataLayoutColums(),
					fieldTypeTracker, httpServletRequest, httpServletResponse));

			dataLayoutRowContexts.add(dataLayoutRowContext);
		}

		return dataLayoutRowContexts;
	}

	private List<Object> _createFieldTypeContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		String[] fieldNames, FieldTypeTracker fieldTypeTracker,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		List<Object> fieldTypeContexts = new ArrayList<>();

		for (String fieldName : fieldNames) {
			DataDefinitionField dataDefinitionField = dataDefinitionFields.get(
				fieldName);

			FieldType fieldType = fieldTypeTracker.getFieldType(
				dataDefinitionField.getFieldType());

			if (fieldType != null) {
				fieldTypeContexts.add(
					fieldType.includeContext(
						httpServletRequest, httpServletResponse,
						DataDefinitionFieldUtil.toSPIDataDefinitionField(
							dataDefinitionField)));
			}
		}

		return fieldTypeContexts;
	}

	private Map<String, DataDefinitionField> _getDataDefinitionFieldsMap(
		DataDefinition dataDefinition, Map<String, Object> dataRecordValues) {

		List<DataDefinitionField> dataDefinitionFields = Arrays.asList(
			dataDefinition.getDataDefinitionFields());

		Stream<DataDefinitionField> stream = dataDefinitionFields.stream();

		return stream.collect(
			Collectors.toMap(
				dataDefinitionField -> dataDefinitionField.getName(),
				definitionField -> {
					if (MapUtil.isEmpty(dataRecordValues)) {
						return definitionField;
					}

					Map<String, Object> customProperties =
						definitionField.getCustomProperties();

					customProperties.put(
						"value",
						dataRecordValues.get(definitionField.getName()));

					return definitionField;
				}));
	}

	private static final String _MODULE_NAME =
		"dynamic-data-mapping-form-renderer/js/metal/containers/Form/Form.es";

	private static final String _TEMPLATE_NAMESPACE = "FormRenderer.render";

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private FieldTypeTracker _fieldTypeTracker;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private SoyComponentRenderer _soyComponentRenderer;

}
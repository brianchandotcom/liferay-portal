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
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataLayoutUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.FieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.portal.template.soy.renderer.ComponentDescriptor;
import com.liferay.portal.template.soy.renderer.SoyComponentRenderer;

import java.io.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcela Cunha
 */
public class DataLayoutRenderer {

	public String render(
			Long dataLayoutId,
			DDMStructureLayoutLocalService ddmStructureLayoutLocalService,
			DDMStructureLocalService ddmStructureLocalService,
			DDMStructureVersionLocalService ddmStructureVersionLocalService,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, NPMResolver npmResolver,
			boolean readOnly, SoyComponentRenderer soyComponentRenderer,
			SoyDataFactory soyDataFactory)
		throws Exception {

		Writer writer = new UnsyncStringWriter();

		ComponentDescriptor componentDescriptor = new ComponentDescriptor(
			_TEMPLATE_NAMESPACE, npmResolver.resolveModuleName(_MODULE_NAME));

		Map<String, Object> context = new HashMap<>();

		DDMStructureLayout ddmStructureLayout =
			ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		DDMStructureVersion ddmStructureVersion =
			ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DataLayout dataLayout = DataLayoutUtil.toDataLayout(
			ddmStructureLayout.getDefinition());

		context.put(
			"pages",
			_createDataLayoutPagesContexts(
				_getDataDefinitionFieldsMap(
					DataDefinitionUtil.toDataDefinition(
						ddmStructureVersion.getStructure())),
				dataLayout.getDataLayoutPages(), httpServletRequest,
				httpServletResponse, readOnly, soyDataFactory));

		context.put("paginationMode", dataLayout.getPaginationMode());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String pathThemeImages = themeDisplay.getPathThemeImages();

		context.put("spritemap", pathThemeImages.concat("/clay/icons.svg"));

		soyComponentRenderer.renderSoyComponent(
			httpServletRequest, writer, componentDescriptor, context);

		return writer.toString();
	}

	private List<Object> _createDataLayoutColumnsContext(
		Map<String, DataDefinitionField> dataDefinitionFields,
		DataLayoutColumn[] dataLayoutColumns,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean readOnly,
		SoyDataFactory soyDataFactory) {

		List<Object> dataLayoutColumnsContexts = new ArrayList<>();

		for (DataLayoutColumn dataLayoutColumn : dataLayoutColumns) {
			Map<String, Object> dataLayoutColumnsContext = new HashMap<>();

			dataLayoutColumnsContext.put(
				"fields",
				_createFieldTypesContexts(
					dataDefinitionFields, dataLayoutColumn.getFieldNames(),
					httpServletRequest, httpServletResponse, readOnly,
					soyDataFactory));
			dataLayoutColumnsContext.put(
				"size", dataLayoutColumn.getColumnSize());

			dataLayoutColumnsContexts.add(dataLayoutColumnsContext);
		}

		return dataLayoutColumnsContexts;
	}

	private List<Object> _createDataLayoutPagesContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		DataLayoutPage[] dataLayoutPages, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean readOnly,
		SoyDataFactory soyDataFactory) {

		List<Object> dataLayoutPagesContexts = new ArrayList<>();

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
				_createDataLayoutRowsContexts(
					dataDefinitionFields, dataLayoutPage.getDataLayoutRows(),
					httpServletRequest, httpServletResponse, readOnly,
					soyDataFactory));

			dataLayoutPageContext.put(
				"title",
				GetterUtil.getString(
					LocalizedValueUtil.getLocalizedValue(
						httpServletRequest.getLocale(),
						dataLayoutPage.getTitle())));

			dataLayoutPagesContexts.add(dataLayoutPageContext);
		}

		return dataLayoutPagesContexts;
	}

	private List<Object> _createDataLayoutRowsContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		DataLayoutRow[] dataLayoutRows, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean readOnly,
		SoyDataFactory soyDataFactory) {

		List<Object> dataLayoutRowsContexts = new ArrayList<>();

		for (DataLayoutRow dataLayoutRow : dataLayoutRows) {
			Map<String, Object> dataLayoutRowContext = new HashMap<>();

			dataLayoutRowContext.put(
				"columns",
				_createDataLayoutColumnsContext(
					dataDefinitionFields, dataLayoutRow.getDataLayoutColums(),
					httpServletRequest, httpServletResponse, readOnly,
					soyDataFactory));

			dataLayoutRowsContexts.add(dataLayoutRowContext);
		}

		return dataLayoutRowsContexts;
	}

	private List<Object> _createFieldTypesContexts(
		Map<String, DataDefinitionField> dataDefinitionFields,
		String[] fieldNames, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean readOnly,
		SoyDataFactory soyDataFactory) {

		List<Object> fieldTypesContexts = new ArrayList<>();

		for (String fieldName : fieldNames) {
			DataDefinitionField dataDefinitionField = dataDefinitionFields.get(
				fieldName);

			Map<String, Object> fieldTypeContext = new HashMap<>();

			FieldTypeUtil.includeFieldTypeContext(
				dataDefinitionField, dataDefinitionField.getFieldType(),
				fieldTypeContext, httpServletRequest, httpServletResponse,
				readOnly, soyDataFactory);

			fieldTypesContexts.add(fieldTypeContext);
		}

		return fieldTypesContexts;
	}

	private Map<String, DataDefinitionField> _getDataDefinitionFieldsMap(
		DataDefinition dataDefinition) {

		List<DataDefinitionField> dataDefinitionFields = Arrays.asList(
			dataDefinition.getDataDefinitionFields());

		Stream<DataDefinitionField> stream = dataDefinitionFields.stream();

		return stream.collect(
			Collectors.toMap(field -> field.getName(), Function.identity()));
	}

	private static final String _MODULE_NAME =
		"dynamic-data-mapping-form-builder/metal/js/components/Form" +
			"/FormRenderer.es";

	private static final String _TEMPLATE_NAMESPACE = "FormRenderer.render";

}
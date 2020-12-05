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

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.google.gson.Gson;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.dto.v2_0.DataRule;
import com.liferay.dynamic.data.mapping.form.builder.rule.DDMFormRuleDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.spi.converter.SPIDDMFormRuleConverter;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRule;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class DataLayoutUtil {

	public static String serialize(
			DataLayout dataLayout, DDMForm ddmForm,
			DDMFormLayoutSerializer ddmFormLayoutSerializer,
			DDMFormRuleDeserializer ddmFormRuleDeserializer)
		throws Exception {

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				toDDMFormLayout(dataLayout, ddmForm, ddmFormRuleDeserializer));

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				ddmFormLayoutSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	public static DataLayout toDataLayout(
			DDMFormLayout ddmFormLayout,
			SPIDDMFormRuleConverter spiDDMFormRuleConverter)
		throws Exception {

		return new DataLayout() {
			{
				dataLayoutFields = _toDataLayoutFields(
					ddmFormLayout.getDDMFormFields());
				dataLayoutPages = _toDataLayoutPages(
					ddmFormLayout.getDDMFormLayoutPages());
				dataRules = _toDataRules(
					ddmFormLayout.getDDMFormRules(), spiDDMFormRuleConverter);
				paginationMode = ddmFormLayout.getPaginationMode();
			}
		};
	}

	public static DataLayout toDataLayout(
			DDMStructureLayout ddmStructureLayout,
			SPIDDMFormRuleConverter spiDDMFormRuleConverter)
		throws Exception {

		if (ddmStructureLayout == null) {
			return null;
		}

		DataLayout dataLayout = toDataLayout(
			ddmStructureLayout.getDDMFormLayout(), spiDDMFormRuleConverter);

		dataLayout.setDateCreated(ddmStructureLayout.getCreateDate());
		dataLayout.setDataDefinitionId(ddmStructureLayout.getDDMStructureId());
		dataLayout.setDataLayoutKey(ddmStructureLayout.getStructureLayoutKey());
		dataLayout.setDateModified(ddmStructureLayout.getModifiedDate());
		dataLayout.setDescription(
			LocalizedValueUtil.toStringObjectMap(
				ddmStructureLayout.getDescriptionMap()));
		dataLayout.setId(ddmStructureLayout.getStructureLayoutId());
		dataLayout.setName(
			LocalizedValueUtil.toStringObjectMap(
				ddmStructureLayout.getNameMap()));
		dataLayout.setSiteId(ddmStructureLayout.getGroupId());
		dataLayout.setUserId(ddmStructureLayout.getUserId());

		return dataLayout;
	}

	public static DDMFormLayout toDDMFormLayout(
			DataLayout dataLayout, DDMForm ddmForm,
			DDMFormRuleDeserializer ddmFormRuleDeserializer)
		throws Exception {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDDMFormFields(
			_toDDMFormFields(dataLayout.getDataLayoutFields()));
		ddmFormLayout.setDDMFormLayoutPages(
			_toDDMFormLayoutPages(
				dataLayout.getDataLayoutPages(), ddmForm.getDefaultLocale()));
		ddmFormLayout.setDefaultLocale(ddmForm.getDefaultLocale());
		ddmFormLayout.setPaginationMode(dataLayout.getPaginationMode());

		ddmFormLayout.setDDMFormRules(
			ddmFormRuleDeserializer.deserialize(
				ddmForm,
				JSONUtil.toJSONArray(
					dataLayout.getDataRules(), rule -> _serializeRule(rule))));

		return ddmFormLayout;
	}

	private static JSONObject _serializeRule(DataRule dataRule)
		throws JSONException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Gson gson = new Gson();

		return jsonObject.put(
			"actions", dataRule.getActions()
		).put(
			"conditions", dataRule.getConditions()
		).put(
			"logical-operator", dataRule.getLogicalOperator()
		).put(
			"name",
			JSONFactoryUtil.createJSONObject(gson.toJson(dataRule.getName()))
		);
	}

	private static DataLayoutColumn _toDataLayoutColumn(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		return new DataLayoutColumn() {
			{
				columnSize = ddmFormLayoutColumn.getSize();
				fieldNames = ArrayUtil.toStringArray(
					ddmFormLayoutColumn.getDDMFormFieldNames());
			}
		};
	}

	private static DataLayoutColumn[] _toDataLayoutColumns(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		if (ListUtil.isEmpty(ddmFormLayoutColumns)) {
			return new DataLayoutColumn[0];
		}

		Stream<DDMFormLayoutColumn> stream = ddmFormLayoutColumns.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutColumn
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutColumn[0]
		);
	}

	private static Map<String, Object> _toDataLayoutFields(
		List<DDMFormField> ddmFormFields) {

		Map<String, Object> dataLayoutFields = new HashMap<>();

		ddmFormFields.forEach(
			ddmFormField -> dataLayoutFields.put(
				ddmFormField.getName(),
				HashMapBuilder.<String, Object>put(
					"required", ddmFormField.isRequired()
				).build()));

		return dataLayoutFields;
	}

	private static DataLayoutPage _toDataLayoutPage(
		DDMFormLayoutPage ddmFormLayoutPage) {

		return new DataLayoutPage() {
			{
				dataLayoutRows = _toDataLayoutRows(
					ddmFormLayoutPage.getDDMFormLayoutRows());
				description = LocalizedValueUtil.toLocalizedValuesMap(
					ddmFormLayoutPage.getDescription());
				title = LocalizedValueUtil.toLocalizedValuesMap(
					ddmFormLayoutPage.getTitle());
			}
		};
	}

	private static DataLayoutPage[] _toDataLayoutPages(
		List<DDMFormLayoutPage> ddmFormLayoutPages) {

		if (ListUtil.isEmpty(ddmFormLayoutPages)) {
			return new DataLayoutPage[0];
		}

		Stream<DDMFormLayoutPage> stream = ddmFormLayoutPages.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutPage
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutPage[0]
		);
	}

	private static DataLayoutRow _toDataLayoutRow(
		DDMFormLayoutRow ddmFormLayoutRow) {

		return new DataLayoutRow() {
			{
				dataLayoutColumns = _toDataLayoutColumns(
					ddmFormLayoutRow.getDDMFormLayoutColumns());
			}
		};
	}

	private static DataLayoutRow[] _toDataLayoutRows(
		List<DDMFormLayoutRow> ddmFormLayoutRows) {

		Stream<DDMFormLayoutRow> stream = ddmFormLayoutRows.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutRow
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutRow[0]
		);
	}

	private static DataRule[] _toDataRules(
		List<DDMFormRule> ddmFormRules,
		SPIDDMFormRuleConverter spiDDMFormRuleConverter) {

		DataRule[] dataRules = new DataRule[0];

		for (SPIDDMFormRule spiDDMFormRule :
				spiDDMFormRuleConverter.convert(ddmFormRules)) {

			DataRule dataRule = new DataRule();

			Gson gson = new Gson();

			dataRule.setActions(
				Stream.of(
					spiDDMFormRule.getSPIDDMFormRuleActions()
				).flatMap(
					Collection::stream
				).map(
					spiDDMFormRuleAction -> gson.fromJson(
						JSONFactoryUtil.looseSerializeDeep(
							spiDDMFormRuleAction),
						Map.class)
				).toArray(
					Map[]::new
				));
			dataRule.setConditions(
				Stream.of(
					spiDDMFormRule.getSPIDDMFormRuleConditions()
				).flatMap(
					Collection::stream
				).map(
					spiDDMFormRuleCondition -> gson.fromJson(
						JSONFactoryUtil.looseSerializeDeep(
							spiDDMFormRuleCondition),
						Map.class)
				).toArray(
					Map[]::new
				));

			dataRule.setLogicalOperator(spiDDMFormRule.getLogicalOperator());
			dataRule.setName(
				LocalizedValueUtil.toLocalizedValuesMap(
					spiDDMFormRule.getName()));

			dataRules = ArrayUtil.append(dataRules, dataRule);
		}

		return dataRules;
	}

	private static List<DDMFormField> _toDDMFormFields(
		Map<String, Object> dataLayoutFields) {

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		if (MapUtil.isEmpty(dataLayoutFields)) {
			return ddmFormFields;
		}

		dataLayoutFields.forEach(
			(key, value) -> {
				DDMFormField ddmFormField = new DDMFormField();

				ddmFormField.setName(key);

				Map<String, Object> properties = (Map<String, Object>)value;

				ddmFormField.setRequired(
					GetterUtil.getBoolean(properties.get("required")));

				ddmFormFields.add(ddmFormField);
			});

		return ddmFormFields;
	}

	private static DDMFormLayoutColumn _toDDMFormLayoutColumn(
		DataLayoutColumn dataLayoutColumn) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn();

		ddmFormLayoutColumn.setDDMFormFieldNames(
			Arrays.asList(dataLayoutColumn.getFieldNames()));
		ddmFormLayoutColumn.setSize(dataLayoutColumn.getColumnSize());

		return ddmFormLayoutColumn;
	}

	private static List<DDMFormLayoutColumn> _toDDMFormLayoutColumns(
		DataLayoutColumn[] dataLayoutColumns) {

		if (ArrayUtil.isEmpty(dataLayoutColumns)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutColumns
		).map(
			DataLayoutUtil::_toDDMFormLayoutColumn
		).collect(
			Collectors.toList()
		);
	}

	private static DDMFormLayoutPage _toDDMFormLayoutPage(
		DataLayoutPage dataLayoutPage, Locale locale) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		ddmFormLayoutPage.setDDMFormLayoutRows(
			_toDDMFormLayoutRows(dataLayoutPage.getDataLayoutRows()));
		ddmFormLayoutPage.setDescription(
			LocalizedValueUtil.toLocalizedValue(
				dataLayoutPage.getDescription(), locale));
		ddmFormLayoutPage.setTitle(
			LocalizedValueUtil.toLocalizedValue(
				dataLayoutPage.getTitle(), locale));

		return ddmFormLayoutPage;
	}

	private static List<DDMFormLayoutPage> _toDDMFormLayoutPages(
		DataLayoutPage[] dataLayoutPages, Locale locale) {

		if (ArrayUtil.isEmpty(dataLayoutPages)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutPages
		).map(
			dataLayoutPage -> _toDDMFormLayoutPage(dataLayoutPage, locale)
		).collect(
			Collectors.toList()
		);
	}

	private static DDMFormLayoutRow _toDDMFormLayoutRow(
		DataLayoutRow dataLayoutRow) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			_toDDMFormLayoutColumns(dataLayoutRow.getDataLayoutColumns()));

		return ddmFormLayoutRow;
	}

	private static List<DDMFormLayoutRow> _toDDMFormLayoutRows(
		DataLayoutRow[] dataLayoutRows) {

		if (ArrayUtil.isEmpty(dataLayoutRows)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutRows
		).map(
			DataLayoutUtil::_toDDMFormLayoutRow
		).collect(
			Collectors.toList()
		);
	}

}
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

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class DataLayoutUtil {

	public static String serialize(
		DataLayout dataLayout,
		DDMFormLayoutSerializer ddmFormLayoutSerializer) {

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				toDDMFormLayout(dataLayout));

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				ddmFormLayoutSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	public static DataLayout toDataLayout(DDMFormLayout ddmFormLayout) {
		return new DataLayout() {
			{
				dataLayoutPages = _toDataLayoutPages(
					ddmFormLayout.getDDMFormLayoutPages());
				paginationMode = ddmFormLayout.getPaginationMode();
			}
		};
	}

	public static DataLayout toDataLayout(
		DDMStructureLayout ddmStructureLayout) {

		if (ddmStructureLayout == null) {
			return null;
		}

		DataLayout dataLayout = toDataLayout(
			ddmStructureLayout.getDDMFormLayout());

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

	public static DDMFormLayout toDDMFormLayout(DataLayout dataLayout) {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDDMFormLayoutPages(
			_toDDMFormLayoutPages(dataLayout.getDataLayoutPages()));
		ddmFormLayout.setPaginationMode(dataLayout.getPaginationMode());

		return ddmFormLayout;
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
		DataLayoutPage dataLayoutPage) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		ddmFormLayoutPage.setDDMFormLayoutRows(
			_toDDMFormLayoutRows(dataLayoutPage.getDataLayoutRows()));
		ddmFormLayoutPage.setDescription(
			LocalizedValueUtil.toLocalizedValue(
				dataLayoutPage.getDescription()));
		ddmFormLayoutPage.setTitle(
			LocalizedValueUtil.toLocalizedValue(dataLayoutPage.getTitle()));

		return ddmFormLayoutPage;
	}

	private static List<DDMFormLayoutPage> _toDDMFormLayoutPages(
		DataLayoutPage[] dataLayoutPages) {

		if (ArrayUtil.isEmpty(dataLayoutPages)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutPages
		).map(
			DataLayoutUtil::_toDDMFormLayoutPage
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
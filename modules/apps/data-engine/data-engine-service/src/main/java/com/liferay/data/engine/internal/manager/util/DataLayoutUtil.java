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

package com.liferay.data.engine.internal.manager.util;

import com.liferay.data.engine.DataLayout;
import com.liferay.data.engine.DataLayoutColumn;
import com.liferay.data.engine.DataLayoutPage;
import com.liferay.data.engine.DataLayoutRow;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class DataLayoutUtil {

	public static String serialize(
		DDMFormLayoutSerializer ddmFormLayoutSerializer,
		DataLayout dataLayout) {

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				_toDDMFormLayout(dataLayout));

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				ddmFormLayoutSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	public static DataLayout toDataLayout(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		if (ddmStructureLayout == null) {
			return null;
		}

		DDMFormLayout ddmFormLayout = ddmStructureLayout.getDDMFormLayout();

		return new DataLayout() {
			{
				setDataDefinitionId(ddmStructureLayout.getDDMStructureId());
				setDataLayoutKey(ddmStructureLayout.getStructureLayoutKey());
				setDataLayoutPages(
					_toDataLayoutPages(ddmFormLayout.getDDMFormLayoutPages()));
				setDateCreated(ddmStructureLayout.getCreateDate());
				setDateModified(ddmStructureLayout.getModifiedDate());
				setDescription(
					LocalizedValueUtil.toStringObjectMap(
						ddmStructureLayout.getDescriptionMap()));
				setGroupId(ddmStructureLayout.getGroupId());
				setId(ddmStructureLayout.getStructureLayoutId());
				setName(
					LocalizedValueUtil.toStringObjectMap(
						ddmStructureLayout.getNameMap()));
				setPaginationMode(ddmFormLayout.getPaginationMode());
				setUserId(ddmStructureLayout.getUserId());
			}
		};
	}

	public static List<DataLayout> toDataLayouts(
			List<DDMStructureLayout> ddmStructureLayouts)
		throws Exception {

		List<DataLayout> dataLayouts = new ArrayList<>();

		for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
			dataLayouts.add(toDataLayout(ddmStructureLayout));
		}

		return dataLayouts;
	}

	private static DataLayoutColumn _toDataLayoutColumn(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		return new DataLayoutColumn() {
			{
				setColumnSize(ddmFormLayoutColumn.getSize());
				setFieldNames(ddmFormLayoutColumn.getDDMFormFieldNames());
			}
		};
	}

	private static List<DataLayoutColumn> _toDataLayoutColumns(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		if (ListUtil.isEmpty(ddmFormLayoutColumns)) {
			Collections.emptyList();
		}

		Stream<DDMFormLayoutColumn> stream = ddmFormLayoutColumns.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutColumn
		).collect(
			Collectors.toList()
		);
	}

	private static DataLayoutPage _toDataLayoutPage(
		DDMFormLayoutPage ddmFormLayoutPage) {

		return new DataLayoutPage() {
			{
				setDataLayoutRows(
					_toDataLayoutRows(
						ddmFormLayoutPage.getDDMFormLayoutRows()));
				setDescription(
					LocalizedValueUtil.toLocalizedValuesMap(
						ddmFormLayoutPage.getDescription()));
				setTitle(
					LocalizedValueUtil.toLocalizedValuesMap(
						ddmFormLayoutPage.getTitle()));
			}
		};
	}

	private static List<DataLayoutPage> _toDataLayoutPages(
		List<DDMFormLayoutPage> ddmFormLayoutPages) {

		if (ListUtil.isEmpty(ddmFormLayoutPages)) {
			Collections.emptyList();
		}

		Stream<DDMFormLayoutPage> stream = ddmFormLayoutPages.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutPage
		).collect(
			Collectors.toList()
		);
	}

	private static DataLayoutRow _toDataLayoutRow(
		DDMFormLayoutRow ddmFormLayoutRow) {

		return new DataLayoutRow() {
			{
				setDataLayoutColumns(
					_toDataLayoutColumns(
						ddmFormLayoutRow.getDDMFormLayoutColumns()));
			}
		};
	}

	private static List<DataLayoutRow> _toDataLayoutRows(
		List<DDMFormLayoutRow> ddmFormLayoutRows) {

		if (ListUtil.isEmpty(ddmFormLayoutRows)) {
			return Collections.emptyList();
		}

		Stream<DDMFormLayoutRow> stream = ddmFormLayoutRows.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutRow
		).collect(
			Collectors.toList()
		);
	}

	private static DDMFormLayout _toDDMFormLayout(DataLayout dataLayout) {
		return new DDMFormLayout() {
			{
				setDDMFormLayoutPages(
					_toDDMFormLayoutPages(dataLayout.getDataLayoutPages()));
				setPaginationMode(dataLayout.getPaginationMode());
			}
		};
	}

	private static DDMFormLayoutColumn _toDDMFormLayoutColumn(
		DataLayoutColumn dataLayoutColumn) {

		return new DDMFormLayoutColumn() {
			{
				setDDMFormFieldNames(dataLayoutColumn.getFieldNames());
				setSize(dataLayoutColumn.getColumnSize());
			}
		};
	}

	private static List<DDMFormLayoutColumn> _toDDMFormLayoutColumns(
		List<DataLayoutColumn> dataLayoutColumns) {

		if (ListUtil.isEmpty(dataLayoutColumns)) {
			return Collections.emptyList();
		}

		Stream<DataLayoutColumn> stream = dataLayoutColumns.stream();

		return stream.map(
			DataLayoutUtil::_toDDMFormLayoutColumn
		).collect(
			Collectors.toList()
		);
	}

	private static DDMFormLayoutPage _toDDMFormLayoutPage(
		DataLayoutPage dataLayoutPage) {

		return new DDMFormLayoutPage() {
			{
				setDDMFormLayoutRows(
					_toDDMFormLayoutRows(dataLayoutPage.getDataLayoutRows()));
				setDescription(
					LocalizedValueUtil.toLocalizedValue(
						dataLayoutPage.getDescription()));
				setTitle(
					LocalizedValueUtil.toLocalizedValue(
						dataLayoutPage.getTitle()));
			}
		};
	}

	private static List<DDMFormLayoutPage> _toDDMFormLayoutPages(
		List<DataLayoutPage> dataLayoutPages) {

		if (ListUtil.isEmpty(dataLayoutPages)) {
			return Collections.emptyList();
		}

		Stream<DataLayoutPage> stream = dataLayoutPages.stream();

		return stream.map(
			DataLayoutUtil::_toDDMFormLayoutPage
		).collect(
			Collectors.toList()
		);
	}

	private static DDMFormLayoutRow _toDDMFormLayoutRow(
		DataLayoutRow dataLayoutRow) {

		return new DDMFormLayoutRow() {
			{
				setDDMFormLayoutColumns(
					_toDDMFormLayoutColumns(
						dataLayoutRow.getDataLayoutColumns()));
			}
		};
	}

	private static List<DDMFormLayoutRow> _toDDMFormLayoutRows(
		List<DataLayoutRow> dataLayoutRows) {

		if (ListUtil.isEmpty(dataLayoutRows)) {
			return Collections.emptyList();
		}

		Stream<DataLayoutRow> stream = dataLayoutRows.stream();

		return stream.map(
			DataLayoutUtil::_toDDMFormLayoutRow
		).collect(
			Collectors.toList()
		);
	}

}
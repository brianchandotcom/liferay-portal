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

package com.liferay.data.engine.service.impl.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.portal.kernel.util.ListUtil;

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
		DEDataLayout deDataLayout) {

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				_toDDMFormLayout(deDataLayout));

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				ddmFormLayoutSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	public static DEDataLayout toDEDataLayout(
			DDMStructureLayout ddmStructureLayout)
		throws Exception {

		if (ddmStructureLayout == null) {
			return null;
		}

		DEDataLayout deDataLayout = _toDEDataLayout(
			ddmStructureLayout.getDDMFormLayout());

		deDataLayout.setCreateDate(ddmStructureLayout.getCreateDate());
		deDataLayout.setDataDefinitionId(
			ddmStructureLayout.getDDMStructureId());
		deDataLayout.setDataLayoutKey(
			ddmStructureLayout.getStructureLayoutKey());
		deDataLayout.setDescription(ddmStructureLayout.getDescriptionMap());
		deDataLayout.setGroupId(ddmStructureLayout.getGroupId());
		deDataLayout.setId(ddmStructureLayout.getStructureLayoutId());
		deDataLayout.setModifiedDate(ddmStructureLayout.getModifiedDate());
		deDataLayout.setName(ddmStructureLayout.getNameMap());
		deDataLayout.setUserId(ddmStructureLayout.getUserId());

		return deDataLayout;
	}

	private static DDMFormLayout _toDDMFormLayout(DEDataLayout deDataLayout) {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDDMFormLayoutPages(
			_toDDMFormLayoutPages(deDataLayout.getDEDataLayoutPages()));
		ddmFormLayout.setPaginationMode(deDataLayout.getPaginationMode());

		return ddmFormLayout;
	}

	private static DDMFormLayoutColumn _toDDMFormLayoutColumn(
		DEDataLayoutColumn deDataLayoutColumn) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn();

		ddmFormLayoutColumn.setDDMFormFieldNames(
			deDataLayoutColumn.getFieldNames());
		ddmFormLayoutColumn.setSize(deDataLayoutColumn.getColumnSize());

		return ddmFormLayoutColumn;
	}

	private static List<DDMFormLayoutColumn> _toDDMFormLayoutColumns(
		List<DEDataLayoutColumn> deDataLayoutColumns) {

		if (ListUtil.isEmpty(deDataLayoutColumns)) {
			return Collections.emptyList();
		}

		Stream<DEDataLayoutColumn> stream = deDataLayoutColumns.stream();

		return stream.map(
			DataLayoutUtil::_toDDMFormLayoutColumn
		).collect(
			Collectors.toList()
		);
	}

	private static DDMFormLayoutPage _toDDMFormLayoutPage(
		DEDataLayoutPage deDataLayoutPage) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		ddmFormLayoutPage.setDDMFormLayoutRows(
			_toDDMFormLayoutRows(deDataLayoutPage.getDEDataLayoutRows()));
		ddmFormLayoutPage.setDescription(
			LocalizedValueUtil.toLocalizedValue(
				LocalizedValueUtil.toStringObjectMap(
					deDataLayoutPage.getDescription())));
		ddmFormLayoutPage.setTitle(
			LocalizedValueUtil.toLocalizedValue(
				LocalizedValueUtil.toStringObjectMap(
					deDataLayoutPage.getTitle())));

		return ddmFormLayoutPage;
	}

	private static List<DDMFormLayoutPage> _toDDMFormLayoutPages(
		List<DEDataLayoutPage> deDataLayoutPages) {

		if (ListUtil.isEmpty(deDataLayoutPages)) {
			return Collections.emptyList();
		}

		Stream<DEDataLayoutPage> stream = deDataLayoutPages.stream();

		return stream.map(
			DataLayoutUtil::_toDDMFormLayoutPage
		).collect(
			Collectors.toList()
		);
	}

	private static DDMFormLayoutRow _toDDMFormLayoutRow(
		DEDataLayoutRow deDataLayoutRow) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(
			_toDDMFormLayoutColumns(deDataLayoutRow.getDEDataLayoutColumns()));

		return ddmFormLayoutRow;
	}

	private static List<DDMFormLayoutRow> _toDDMFormLayoutRows(
		List<DEDataLayoutRow> deDataLayoutRows) {

		if (ListUtil.isEmpty(deDataLayoutRows)) {
			return Collections.emptyList();
		}

		Stream<DEDataLayoutRow> stream = deDataLayoutRows.stream();

		return stream.map(
			DataLayoutUtil::_toDDMFormLayoutRow
		).collect(
			Collectors.toList()
		);
	}

	private static DEDataLayout _toDEDataLayout(DDMFormLayout ddmFormLayout) {
		return new DEDataLayout() {
			{
				setPaginationMode(ddmFormLayout.getPaginationMode());
				setDEDataLayoutPages(
					_toDEDataLayoutPages(
						ddmFormLayout.getDDMFormLayoutPages()));
			}
		};
	}

	private static DEDataLayoutColumn _toDEDataLayoutColumn(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		return new DEDataLayoutColumn() {
			{
				setColumnSize(ddmFormLayoutColumn.getSize());
				setFieldNames(ddmFormLayoutColumn.getDDMFormFieldNames());
			}
		};
	}

	private static List<DEDataLayoutColumn> _toDEDataLayoutColumns(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		if (ListUtil.isEmpty(ddmFormLayoutColumns)) {
			Collections.emptyList();
		}

		Stream<DDMFormLayoutColumn> stream = ddmFormLayoutColumns.stream();

		return stream.map(
			DataLayoutUtil::_toDEDataLayoutColumn
		).collect(
			Collectors.toList()
		);
	}

	private static DEDataLayoutPage _toDEDataLayoutPage(
		DDMFormLayoutPage ddmFormLayoutPage) {

		return new DEDataLayoutPage() {
			{
				setDescription(
					LocalizedValueUtil.toLocaleStringMap(
						LocalizedValueUtil.toLocalizedValuesMap(
							ddmFormLayoutPage.getDescription())));
				setDEDataLayoutRows(
					_toDEDataLayoutRows(
						ddmFormLayoutPage.getDDMFormLayoutRows()));
				setTitle(
					LocalizedValueUtil.toLocaleStringMap(
						LocalizedValueUtil.toLocalizedValuesMap(
							ddmFormLayoutPage.getTitle())));
			}
		};
	}

	private static List<DEDataLayoutPage> _toDEDataLayoutPages(
		List<DDMFormLayoutPage> ddmFormLayoutPages) {

		if (ListUtil.isEmpty(ddmFormLayoutPages)) {
			Collections.emptyList();
		}

		Stream<DDMFormLayoutPage> stream = ddmFormLayoutPages.stream();

		return stream.map(
			DataLayoutUtil::_toDEDataLayoutPage
		).collect(
			Collectors.toList()
		);
	}

	private static DEDataLayoutRow _toDEDataLayoutRow(
		DDMFormLayoutRow ddmFormLayoutRow) {

		return new DEDataLayoutRow() {
			{
				setDEDataLayoutColumns(
					_toDEDataLayoutColumns(
						ddmFormLayoutRow.getDDMFormLayoutColumns()));
			}
		};
	}

	private static List<DEDataLayoutRow> _toDEDataLayoutRows(
		List<DDMFormLayoutRow> ddmFormLayoutRows) {

		if (ListUtil.isEmpty(ddmFormLayoutRows)) {
			return Collections.emptyList();
		}

		Stream<DDMFormLayoutRow> stream = ddmFormLayoutRows.stream();

		return stream.map(
			DataLayoutUtil::_toDEDataLayoutRow
		).collect(
			Collectors.toList()
		);
	}

}
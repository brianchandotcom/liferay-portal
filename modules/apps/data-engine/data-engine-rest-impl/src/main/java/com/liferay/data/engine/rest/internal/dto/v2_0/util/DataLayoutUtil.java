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
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutColumn;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.data.engine.model.DEDataLayoutRow;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class DataLayoutUtil {

	public static DataLayout toDataLayout(DDMFormLayout ddmFormLayout) {
		return new DataLayout() {
			{
				dataLayoutPages = _toDataLayoutPages(
					Optional.ofNullable(
						ddmFormLayout.getDDMFormLayoutPages()
					).map(
						ddmFormLayoutPages -> ddmFormLayoutPages.toArray(
							new DDMFormLayoutPage[0])
					).orElse(
						new DDMFormLayoutPage[0]
					));
				paginationMode = ddmFormLayout.getPaginationMode();
			}
		};
	}

	public static DataLayout toDataLayout(DDMStructureLayout ddmStructureLayout)
		throws Exception {

		if (ddmStructureLayout == null) {
			return null;
		}

		DataLayout dataLayout = toDataLayout(
			ddmStructureLayout.getDDMFormLayout());

		dataLayout.setDataDefinitionId(ddmStructureLayout.getDDMStructureId());
		dataLayout.setDataLayoutKey(ddmStructureLayout.getStructureLayoutKey());
		dataLayout.setDateCreated(ddmStructureLayout.getCreateDate());
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

	public static DataLayout toDataLayout(DEDataLayout deDataLayout) {
		return new DataLayout() {
			{
				dataDefinitionId = deDataLayout.getDataDefinitionId();
				dataLayoutKey = deDataLayout.getDataLayoutKey();
				dataLayoutPages = _toDataLayoutPages(
					deDataLayout.getDEDataLayoutPages());
				dateCreated = deDataLayout.getCreateDate();
				dateModified = deDataLayout.getModifiedDate();
				description = LocalizedValueUtil.toStringObjectMap(
					deDataLayout.getDescription());
				id = deDataLayout.getId();
				name = LocalizedValueUtil.toStringObjectMap(
					deDataLayout.getName());
				paginationMode = deDataLayout.getPaginationMode();
				siteId = deDataLayout.getGroupId();
				userId = deDataLayout.getUserId();
			}
		};
	}

	public static DDMFormLayout toDDMFormLayout(DataLayout dataLayout) {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDDMFormLayoutPages(
			_toDDMFormLayoutPages(dataLayout.getDataLayoutPages()));
		ddmFormLayout.setPaginationMode(dataLayout.getPaginationMode());

		return ddmFormLayout;
	}

	public static DEDataLayout toDEDataLayout(DataLayout dataLayout) {
		return new DEDataLayout() {
			{
				setDataDefinitionId(dataLayout.getDataDefinitionId());
				setDataLayoutKey(dataLayout.getDataLayoutKey());
				setCreateDate(dataLayout.getDateCreated());
				setDEDataLayoutPages(
					_toDEDataLayoutPages(dataLayout.getDataLayoutPages()));
				setDescription(
					LocalizedValueUtil.toLocaleStringMap(
						dataLayout.getDescription()));
				setGroupId(dataLayout.getSiteId());
				setId(dataLayout.getId());
				setModifiedDate(dataLayout.getDateModified());
				setName(
					LocalizedValueUtil.toLocaleStringMap(dataLayout.getName()));
				setPaginationMode(dataLayout.getPaginationMode());
				setUserId(dataLayout.getUserId());
			}
		};
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

	private static DataLayoutColumn _toDataLayoutColumn(
		DEDataLayoutColumn deDataLayoutColumn) {

		return new DataLayoutColumn() {
			{
				setColumnSize(deDataLayoutColumn.getColumnSize());
				setFieldNames(
					ArrayUtil.toStringArray(
						deDataLayoutColumn.getFieldNames()));
			}
		};
	}

	private static DataLayoutColumn[] _toDataLayoutColumns(
		DDMFormLayoutColumn[] ddmFormLayoutColumns) {

		if (ArrayUtil.isEmpty(ddmFormLayoutColumns)) {
			return new DataLayoutColumn[0];
		}

		return Stream.of(
			ddmFormLayoutColumns
		).map(
			DataLayoutUtil::_toDataLayoutColumn
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutColumn[0]
		);
	}

	private static DataLayoutColumn[] _toDataLayoutColumns(
		List<DEDataLayoutColumn> deDataLayoutColumns) {

		if (ListUtil.isEmpty(deDataLayoutColumns)) {
			return new DataLayoutColumn[0];
		}

		Stream<DEDataLayoutColumn> stream = deDataLayoutColumns.stream();

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
					Optional.ofNullable(
						ddmFormLayoutPage.getDDMFormLayoutRows()
					).map(
						ddmFormLayoutRows -> ddmFormLayoutRows.toArray(
							new DDMFormLayoutRow[0])
					).orElse(
						new DDMFormLayoutRow[0]
					));
				description = LocalizedValueUtil.toLocalizedValuesMap(
					ddmFormLayoutPage.getDescription());
				title = LocalizedValueUtil.toLocalizedValuesMap(
					ddmFormLayoutPage.getTitle());
			}
		};
	}

	private static DataLayoutPage _toDataLayoutPage(
		DEDataLayoutPage deDataLayoutPage) {

		return new DataLayoutPage() {
			{
				dataLayoutRows = _toDataLayoutRows(
					deDataLayoutPage.getDEDataLayoutRows());
				description = LocalizedValueUtil.toStringObjectMap(
					deDataLayoutPage.getDescription());
				title = LocalizedValueUtil.toStringObjectMap(
					deDataLayoutPage.getTitle());
			}
		};
	}

	private static DataLayoutPage[] _toDataLayoutPages(
		DDMFormLayoutPage[] ddmFormLayoutPages) {

		if (ArrayUtil.isEmpty(ddmFormLayoutPages)) {
			return new DataLayoutPage[0];
		}

		return Stream.of(
			ddmFormLayoutPages
		).map(
			DataLayoutUtil::_toDataLayoutPage
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutPage[0]
		);
	}

	private static DataLayoutPage[] _toDataLayoutPages(
		List<DEDataLayoutPage> deDataLayoutPages) {

		if (ListUtil.isEmpty(deDataLayoutPages)) {
			return new DataLayoutPage[0];
		}

		Stream<DEDataLayoutPage> stream = deDataLayoutPages.stream();

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
					Optional.ofNullable(
						ddmFormLayoutRow.getDDMFormLayoutColumns()
					).map(
						ddmFormLayoutColumns -> ddmFormLayoutColumns.toArray(
							new DDMFormLayoutColumn[0])
					).orElse(
						new DDMFormLayoutColumn[0]
					));
			}
		};
	}

	private static DataLayoutRow _toDataLayoutRow(
		DEDataLayoutRow deDataLayoutRow) {

		return new DataLayoutRow() {
			{
				dataLayoutColumns = _toDataLayoutColumns(
					deDataLayoutRow.getDEDataLayoutColumns());
			}
		};
	}

	private static DataLayoutRow[] _toDataLayoutRows(
		DDMFormLayoutRow[] ddmFormLayoutRows) {

		if (ArrayUtil.isEmpty(ddmFormLayoutRows)) {
			return new DataLayoutRow[0];
		}

		return Stream.of(
			ddmFormLayoutRows
		).map(
			DataLayoutUtil::_toDataLayoutRow
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutRow[0]
		);
	}

	private static DataLayoutRow[] _toDataLayoutRows(
		List<DEDataLayoutRow> deDataLayoutRows) {

		Stream<DEDataLayoutRow> stream = deDataLayoutRows.stream();

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

	private static DEDataLayoutColumn _toDEDataLayoutColumn(
		DataLayoutColumn dataLayoutColumn) {

		return new DEDataLayoutColumn() {
			{
				setColumnSize(dataLayoutColumn.getColumnSize());
				setFieldNames(
					ListUtil.fromArray(dataLayoutColumn.getFieldNames()));
			}
		};
	}

	private static List<DEDataLayoutColumn> _toDEDataLayoutColumns(
		DataLayoutColumn[] dataLayoutColumns) {

		if (ArrayUtil.isEmpty(dataLayoutColumns)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutColumns
		).map(
			DataLayoutUtil::_toDEDataLayoutColumn
		).collect(
			Collectors.toList()
		);
	}

	private static DEDataLayoutPage _toDEDataLayoutPage(
		DataLayoutPage dataLayoutPage) {

		return new DEDataLayoutPage() {
			{
				setDEDataLayoutRows(
					_toDEDataLayoutRows(dataLayoutPage.getDataLayoutRows()));
				setDescription(
					LocalizedValueUtil.toLocaleStringMap(
						dataLayoutPage.getDescription()));
				setTitle(
					LocalizedValueUtil.toLocaleStringMap(
						dataLayoutPage.getTitle()));
			}
		};
	}

	private static List<DEDataLayoutPage> _toDEDataLayoutPages(
		DataLayoutPage[] dataLayoutPages) {

		if (ArrayUtil.isEmpty(dataLayoutPages)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutPages
		).map(
			DataLayoutUtil::_toDEDataLayoutPage
		).collect(
			Collectors.toList()
		);
	}

	private static DEDataLayoutRow _toDEDataLayoutRow(
		DataLayoutRow dataLayoutRow) {

		return new DEDataLayoutRow() {
			{
				setDEDataLayoutColumns(
					_toDEDataLayoutColumns(
						dataLayoutRow.getDataLayoutColumns()));
			}
		};
	}

	private static List<DEDataLayoutRow> _toDEDataLayoutRows(
		DataLayoutRow[] dataLayoutRows) {

		if (ArrayUtil.isEmpty(dataLayoutRows)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutRows
		).map(
			DataLayoutUtil::_toDEDataLayoutRow
		).collect(
			Collectors.toList()
		);
	}

}
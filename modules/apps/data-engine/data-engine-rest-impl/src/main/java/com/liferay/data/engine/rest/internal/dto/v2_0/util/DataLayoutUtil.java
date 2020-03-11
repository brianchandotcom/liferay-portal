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

	public static DataLayout toDataLayout(
		com.liferay.data.engine.DataLayout dataLayout) {

		return new DataLayout() {
			{
				dataDefinitionId = dataLayout.getDataDefinitionId();
				dataLayoutKey = dataLayout.getDataLayoutKey();
				dataLayoutPages = _toDataLayoutPages(
					dataLayout.getDataLayoutPages());
				dateCreated = dataLayout.getDateCreated();
				dateModified = dataLayout.getDateModified();
				description = dataLayout.getDescription();
				id = dataLayout.getId();
				name = dataLayout.getName();
				paginationMode = dataLayout.getPaginationMode();
				siteId = dataLayout.getGroupId();
				userId = dataLayout.getUserId();
			}
		};
	}

	public static com.liferay.data.engine.DataLayout toDataLayout(
		DataLayout dataLayout) {

		return new com.liferay.data.engine.DataLayout() {
			{
				setDataDefinitionId(dataLayout.getDataDefinitionId());
				setDataLayoutKey(dataLayout.getDataLayoutKey());
				setDataLayoutPages(
					_toDataLayoutPages(dataLayout.getDataLayoutPages()));
				setDateCreated(dataLayout.getDateCreated());
				setDateModified(dataLayout.getDateModified());
				setDescription(dataLayout.getDescription());
				setGroupId(dataLayout.getSiteId());
				setId(dataLayout.getId());
				setName(dataLayout.getName());
				setPaginationMode(dataLayout.getPaginationMode());
				setUserId(dataLayout.getUserId());
			}
		};
	}

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

		DDMFormLayout ddmFormLayout = ddmStructureLayout.getDDMFormLayout();

		return new DataLayout() {
			{
				dataDefinitionId = ddmStructureLayout.getDDMStructureId();
				dataLayoutKey = ddmStructureLayout.getStructureLayoutKey();
				dataLayoutPages = _toDataLayoutPages(
					Optional.ofNullable(
						ddmFormLayout.getDDMFormLayoutPages()
					).map(
						ddmFormLayoutPages -> ddmFormLayoutPages.toArray(
							new DDMFormLayoutPage[0])
					).orElse(
						new DDMFormLayoutPage[0]
					));
				dateCreated = ddmStructureLayout.getCreateDate();
				dateModified = ddmStructureLayout.getModifiedDate();
				description = LocalizedValueUtil.toStringObjectMap(
					ddmStructureLayout.getDescriptionMap());
				id = ddmStructureLayout.getStructureLayoutId();
				name = LocalizedValueUtil.toStringObjectMap(
					ddmStructureLayout.getNameMap());
				paginationMode = ddmFormLayout.getPaginationMode();
				siteId = ddmStructureLayout.getGroupId();
				userId = ddmStructureLayout.getUserId();
			}
		};
	}

	public static DDMFormLayout toDDMFormLayout(DataLayout dataLayout) {
		return new DDMFormLayout() {
			{
				setDDMFormLayoutPages(
					_toDDMFormLayoutPages(dataLayout.getDataLayoutPages()));
				setPaginationMode(dataLayout.getPaginationMode());
			}
		};
	}

	private static DataLayoutColumn _toDataLayoutColumn(
		com.liferay.data.engine.DataLayoutColumn dataLayoutColumn) {

		return new DataLayoutColumn() {
			{
				setColumnSize(dataLayoutColumn.getColumnSize());
				setFieldNames(
					ArrayUtil.toStringArray(dataLayoutColumn.getFieldNames()));
			}
		};
	}

	private static com.liferay.data.engine.DataLayoutColumn _toDataLayoutColumn(
		DataLayoutColumn dataLayoutColumn) {

		return new com.liferay.data.engine.DataLayoutColumn() {
			{
				setColumnSize(dataLayoutColumn.getColumnSize());
				setFieldNames(
					ListUtil.fromArray(dataLayoutColumn.getFieldNames()));
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

	private static List<com.liferay.data.engine.DataLayoutColumn>
		_toDataLayoutColumns(DataLayoutColumn[] dataLayoutColumns) {

		if (ArrayUtil.isEmpty(dataLayoutColumns)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutColumns
		).map(
			DataLayoutUtil::_toDataLayoutColumn
		).collect(
			Collectors.toList()
		);
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
		List<com.liferay.data.engine.DataLayoutColumn> dataLayoutColumns) {

		if (ListUtil.isEmpty(dataLayoutColumns)) {
			return new DataLayoutColumn[0];
		}

		Stream<com.liferay.data.engine.DataLayoutColumn> stream =
			dataLayoutColumns.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutColumn
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutColumn[0]
		);
	}

	private static DataLayoutPage _toDataLayoutPage(
		com.liferay.data.engine.DataLayoutPage dataLayoutPage) {

		return new DataLayoutPage() {
			{
				dataLayoutRows = _toDataLayoutRows(
					dataLayoutPage.getDataLayoutRows());
				description = dataLayoutPage.getDescription();
				title = dataLayoutPage.getTitle();
			}
		};
	}

	private static com.liferay.data.engine.DataLayoutPage _toDataLayoutPage(
		DataLayoutPage dataLayoutPage) {

		return new com.liferay.data.engine.DataLayoutPage() {
			{
				setDataLayoutRows(
					_toDataLayoutRows(dataLayoutPage.getDataLayoutRows()));
				setDescription(dataLayoutPage.getDescription());
				setTitle(dataLayoutPage.getTitle());
			}
		};
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

	private static List<com.liferay.data.engine.DataLayoutPage>
		_toDataLayoutPages(DataLayoutPage[] dataLayoutPages) {

		if (ArrayUtil.isEmpty(dataLayoutPages)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutPages
		).map(
			DataLayoutUtil::_toDataLayoutPage
		).collect(
			Collectors.toList()
		);
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
		List<com.liferay.data.engine.DataLayoutPage> dataLayoutPages) {

		if (ListUtil.isEmpty(dataLayoutPages)) {
			return new DataLayoutPage[0];
		}

		Stream<com.liferay.data.engine.DataLayoutPage> stream =
			dataLayoutPages.stream();

		return stream.map(
			DataLayoutUtil::_toDataLayoutPage
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutPage[0]
		);
	}

	private static DataLayoutRow _toDataLayoutRow(
		com.liferay.data.engine.DataLayoutRow dataLayoutRow) {

		return new DataLayoutRow() {
			{
				dataLayoutColumns = _toDataLayoutColumns(
					dataLayoutRow.getDataLayoutColumns());
			}
		};
	}

	private static com.liferay.data.engine.DataLayoutRow _toDataLayoutRow(
		DataLayoutRow dataLayoutRow) {

		return new com.liferay.data.engine.DataLayoutRow() {
			{
				setDataLayoutColumns(
					_toDataLayoutColumns(dataLayoutRow.getDataLayoutColumns()));
			}
		};
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

	private static List<com.liferay.data.engine.DataLayoutRow>
		_toDataLayoutRows(DataLayoutRow[] dataLayoutRows) {

		if (ArrayUtil.isEmpty(dataLayoutRows)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataLayoutRows
		).map(
			DataLayoutUtil::_toDataLayoutRow
		).collect(
			Collectors.toList()
		);
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
		List<com.liferay.data.engine.DataLayoutRow> dataLayoutRows) {

		Stream<com.liferay.data.engine.DataLayoutRow> stream =
			dataLayoutRows.stream();

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

		return new DDMFormLayoutColumn() {
			{
				setDDMFormFieldNames(
					Arrays.asList(dataLayoutColumn.getFieldNames()));
				setSize(dataLayoutColumn.getColumnSize());
			}
		};
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

		return new DDMFormLayoutRow() {
			{
				setDDMFormLayoutColumns(
					_toDDMFormLayoutColumns(
						dataLayoutRow.getDataLayoutColumns()));
			}
		};
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
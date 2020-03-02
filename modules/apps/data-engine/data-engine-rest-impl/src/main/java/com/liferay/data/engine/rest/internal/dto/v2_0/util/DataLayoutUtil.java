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
import com.liferay.data.engine.spi.model.SPIDataLayout;
import com.liferay.data.engine.spi.model.SPIDataLayoutColumn;
import com.liferay.data.engine.spi.model.SPIDataLayoutPage;
import com.liferay.data.engine.spi.model.SPIDataLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
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
					ddmFormLayout.getDDMFormLayoutPages());
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

	public static DataLayout toDataLayout(SPIDataLayout spiDataLayout) {
		if (spiDataLayout == null) {
			return null;
		}

		return new DataLayout() {
			{
				dataDefinitionId = spiDataLayout.getDataDefinitionId();
				description = spiDataLayout.getDescription();
				dataLayoutKey = spiDataLayout.getDataLayoutKey();
				dataLayoutPages = _toDataLayoutPages(
					spiDataLayout.getSPIDataLayoutPages());
				dateCreated = spiDataLayout.getDateCreated();
				dateModified = spiDataLayout.getDateModified();
				id = spiDataLayout.getId();
				name = spiDataLayout.getName();
				paginationMode = spiDataLayout.getPaginationMode();
				siteId = spiDataLayout.getSiteId();
				userId = spiDataLayout.getUserId();
			}
		};
	}

	public static SPIDataLayout toSPIDataLayout(DataLayout dataLayout) {
		SPIDataLayout spiDataLayout = new SPIDataLayout();

		spiDataLayout.setDateCreated(dataLayout.getDateCreated());
		spiDataLayout.setDataDefinitionId(dataLayout.getDataDefinitionId());
		spiDataLayout.setDataLayoutKey(dataLayout.getDataLayoutKey());
		spiDataLayout.setDateModified(dataLayout.getDateModified());
		spiDataLayout.setDescription(dataLayout.getDescription());
		spiDataLayout.setId(dataLayout.getId());
		spiDataLayout.setName(dataLayout.getName());
		spiDataLayout.setPaginationMode(dataLayout.getPaginationMode());
		spiDataLayout.setSiteId(dataLayout.getSiteId());
		spiDataLayout.setSPIDataLayoutPages(
			_toSPIDataLayoutPages(dataLayout.getDataLayoutPages()));
		spiDataLayout.setUserId(dataLayout.getUserId());

		return spiDataLayout;
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
		SPIDataLayoutColumn spiDataLayoutColumn) {

		DataLayoutColumn dataLayoutColumn = new DataLayoutColumn();

		dataLayoutColumn.setColumnSize(spiDataLayoutColumn.getColumnSize());
		dataLayoutColumn.setFieldNames(spiDataLayoutColumn.getFieldNames());

		return dataLayoutColumn;
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

	private static DataLayoutColumn[] _toDataLayoutColumns(
		SPIDataLayoutColumn[] spiDataLayoutColumns) {

		if (ArrayUtil.isEmpty(spiDataLayoutColumns)) {
			return new DataLayoutColumn[0];
		}

		return Stream.of(
			spiDataLayoutColumns
		).map(
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

	private static DataLayoutPage _toDataLayoutPage(
		SPIDataLayoutPage spiDataLayoutPage) {

		DataLayoutPage dataLayoutPage = new DataLayoutPage();

		dataLayoutPage.setDescription(spiDataLayoutPage.getDescription());
		dataLayoutPage.setDataLayoutRows(
			_toDataLayoutRows(spiDataLayoutPage.getSPIDataLayoutRows()));
		dataLayoutPage.setTitle(spiDataLayoutPage.getTitle());

		return dataLayoutPage;
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

	private static DataLayoutPage[] _toDataLayoutPages(
		SPIDataLayoutPage[] spiDataLayoutPages) {

		if (ArrayUtil.isEmpty(spiDataLayoutPages)) {
			return new DataLayoutPage[0];
		}

		return Stream.of(
			spiDataLayoutPages
		).map(
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

	private static DataLayoutRow _toDataLayoutRow(
		SPIDataLayoutRow spiDataLayoutRow) {

		DataLayoutRow dataLayoutRow = new DataLayoutRow();

		dataLayoutRow.setDataLayoutColumns(
			_toDataLayoutColumns(spiDataLayoutRow.getSPIDataLayoutColumns()));

		return dataLayoutRow;
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

	private static DataLayoutRow[] _toDataLayoutRows(
		SPIDataLayoutRow[] spiDataLayoutRows) {

		if (ArrayUtil.isEmpty(spiDataLayoutRows)) {
			return new DataLayoutRow[0];
		}

		return Stream.of(
			spiDataLayoutRows
		).map(
			DataLayoutUtil::_toDataLayoutRow
		).collect(
			Collectors.toList()
		).toArray(
			new DataLayoutRow[0]
		);
	}

	private static SPIDataLayoutColumn _toSPIDataLayoutColumn(
		DataLayoutColumn dataLayoutColumn) {

		SPIDataLayoutColumn spiDataLayoutColumn = new SPIDataLayoutColumn();

		spiDataLayoutColumn.setColumnSize(dataLayoutColumn.getColumnSize());
		spiDataLayoutColumn.setFieldNames(dataLayoutColumn.getFieldNames());

		return spiDataLayoutColumn;
	}

	private static SPIDataLayoutColumn[] _toSPIDataLayoutColumns(
		DataLayoutColumn[] dataLayoutColumns) {

		if (ArrayUtil.isEmpty(dataLayoutColumns)) {
			return new SPIDataLayoutColumn[0];
		}

		return Stream.of(
			dataLayoutColumns
		).map(
			DataLayoutUtil::_toSPIDataLayoutColumn
		).collect(
			Collectors.toList()
		).toArray(
			new SPIDataLayoutColumn[0]
		);
	}

	private static SPIDataLayoutPage _toSPIDataLayoutPage(
		DataLayoutPage dataLayoutPage) {

		SPIDataLayoutPage spiDataLayoutPage = new SPIDataLayoutPage();

		spiDataLayoutPage.setDescription(dataLayoutPage.getDescription());
		spiDataLayoutPage.setSPIDataLayoutRows(
			_toSPIDataLayoutRows(dataLayoutPage.getDataLayoutRows()));
		spiDataLayoutPage.setTitle(dataLayoutPage.getTitle());

		return spiDataLayoutPage;
	}

	private static SPIDataLayoutPage[] _toSPIDataLayoutPages(
		DataLayoutPage[] dataLayoutPages) {

		if (ArrayUtil.isEmpty(dataLayoutPages)) {
			return new SPIDataLayoutPage[0];
		}

		return Stream.of(
			dataLayoutPages
		).map(
			DataLayoutUtil::_toSPIDataLayoutPage
		).collect(
			Collectors.toList()
		).toArray(
			new SPIDataLayoutPage[0]
		);
	}

	private static SPIDataLayoutRow _toSPIDataLayoutRow(
		DataLayoutRow dataLayoutRow) {

		SPIDataLayoutRow spiDataLayoutRow = new SPIDataLayoutRow();

		spiDataLayoutRow.setSPIDataLayoutColumns(
			_toSPIDataLayoutColumns(dataLayoutRow.getDataLayoutColumns()));

		return spiDataLayoutRow;
	}

	private static SPIDataLayoutRow[] _toSPIDataLayoutRows(
		DataLayoutRow[] dataLayoutRows) {

		if (ArrayUtil.isEmpty(dataLayoutRows)) {
			return new SPIDataLayoutRow[0];
		}

		return Stream.of(
			dataLayoutRows
		).map(
			DataLayoutUtil::_toSPIDataLayoutRow
		).collect(
			Collectors.toList()
		).toArray(
			new SPIDataLayoutRow[0]
		);
	}

}
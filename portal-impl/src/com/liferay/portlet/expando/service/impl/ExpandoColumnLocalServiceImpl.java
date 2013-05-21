/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.ColumnNameException;
import com.liferay.portlet.expando.ColumnTypeException;
import com.liferay.portlet.expando.DuplicateColumnNameException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.portlet.expando.service.base.ExpandoColumnLocalServiceBaseImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class ExpandoColumnLocalServiceImpl
	extends ExpandoColumnLocalServiceBaseImpl {

	public ExpandoColumn addColumn(long tableId, String name, int type)
		throws PortalException, SystemException {

		return addColumn(tableId, name, type, null);
	}

	public ExpandoColumn addColumn(
			long tableId, String name, int type, Object defaultData)
		throws PortalException, SystemException {

		// Column

		ExpandoTable table = expandoTablePersistence.findByPrimaryKey(tableId);

		ExpandoValue value = validate(0, tableId, name, type, defaultData);

		long columnId = counterLocalService.increment();

		ExpandoColumn column = expandoColumnPersistence.create(columnId);

		column.setCompanyId(table.getCompanyId());
		column.setTableId(tableId);
		column.setName(name);
		column.setType(type);
		column.setDefaultData(value.getData());

		expandoColumnPersistence.update(column);

		// Resources

		resourceLocalService.addResources(
			table.getCompanyId(), 0, 0, ExpandoColumn.class.getName(),
			column.getColumnId(), false, false, false);

		return column;
	}

	public void deleteColumn(ExpandoColumn column) throws SystemException {

		// Column

		expandoColumnPersistence.remove(column);

		// Values

		expandoValueLocalService.deleteColumnValues(column.getColumnId());
	}

	public void deleteColumn(long columnId)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnPersistence.findByPrimaryKey(
			columnId);

		deleteColumn(column);
	}

	public void deleteColumn(
			long companyId, long classNameId, String tableName, String name)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, classNameId, tableName);

		deleteColumn(table.getTableId(), name);
	}

	public void deleteColumn(long tableId, String name) throws SystemException {
		ExpandoColumn column = expandoColumnPersistence.fetchByT_N(
			tableId, name);

		if (column != null) {
			expandoColumnPersistence.remove(column);
		}
	}

	public void deleteColumn(
			long companyId, String className, String tableName, String name)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteColumn(companyId, classNameId, tableName, name);
	}

	public void deleteColumns(long tableId) throws SystemException {
		List<ExpandoColumn> columns = expandoColumnPersistence.findByTableId(
			tableId);

		for (ExpandoColumn column : columns) {
			deleteColumn(column);
		}
	}

	public void deleteColumns(
			long companyId, long classNameId, String tableName)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, classNameId, tableName);

		deleteColumns(table.getTableId());
	}

	public void deleteColumns(
			long companyId, String className, String tableName)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteColumns(companyId, classNameId, tableName);
	}

	public ExpandoColumn getColumn(long columnId)
		throws PortalException, SystemException {

		return expandoColumnPersistence.findByPrimaryKey(columnId);
	}

	public ExpandoColumn getColumn(
			long companyId, long classNameId, String tableName, String name)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return null;
		}

		return expandoColumnPersistence.fetchByT_N(table.getTableId(), name);
	}

	public ExpandoColumn getColumn(long tableId, String name)
		throws SystemException {

		return expandoColumnPersistence.fetchByT_N(tableId, name);
	}

	public ExpandoColumn getColumn(
			long companyId, String className, String tableName, String name)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumn(companyId, classNameId, tableName, name);
	}

	public List<ExpandoColumn> getColumns(long tableId) throws SystemException {
		return expandoColumnPersistence.findByTableId(tableId);
	}

	public List<ExpandoColumn> getColumns(
			long tableId, Collection<String> names)
		throws SystemException {

		return expandoColumnPersistence.findByT_N(
			tableId, names.toArray(new String[names.size()]));
	}

	public List<ExpandoColumn> getColumns(
			long companyId, long classNameId, String tableName)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return Collections.emptyList();
		}

		return expandoColumnPersistence.findByTableId(table.getTableId());
	}

	public List<ExpandoColumn> getColumns(
			long companyId, long classNameId, String tableName,
			Collection<String> names)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return Collections.emptyList();
		}

		return expandoColumnPersistence.findByT_N(
			table.getTableId(), names.toArray(new String[names.size()]));
	}

	public List<ExpandoColumn> getColumns(
			long companyId, String className, String tableName)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumns(companyId, classNameId, tableName);
	}

	public List<ExpandoColumn> getColumns(
			long companyId, String className, String tableName,
			Collection<String> columnNames)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumns(companyId, classNameId, tableName, columnNames);
	}

	public int getColumnsCount(long tableId) throws SystemException {
		return expandoColumnPersistence.countByTableId(tableId);
	}

	public int getColumnsCount(
			long companyId, long classNameId, String tableName)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return 0;
		}

		return expandoColumnPersistence.countByTableId(table.getTableId());
	}

	public int getColumnsCount(
			long companyId, String className, String tableName)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumnsCount(companyId, classNameId, tableName);
	}

	public ExpandoColumn getDefaultTableColumn(
			long companyId, long classNameId, String name)
		throws SystemException {

		return getColumn(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME,
			name);
	}

	public ExpandoColumn getDefaultTableColumn(
			long companyId, String className, String name)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumn(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME,
			name);
	}

	public List<ExpandoColumn> getDefaultTableColumns(
			long companyId, long classNameId)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);

		if (table == null) {
			return Collections.emptyList();
		}

		return expandoColumnPersistence.findByTableId(table.getTableId());
	}

	public List<ExpandoColumn> getDefaultTableColumns(
			long companyId, String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumns(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	public int getDefaultTableColumnsCount(long companyId, long classNameId)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);

		if (table == null) {
			return 0;
		}

		return expandoColumnPersistence.countByTableId(table.getTableId());
	}

	public int getDefaultTableColumnsCount(long companyId, String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumnsCount(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	public ExpandoColumn updateColumn(long columnId, String name, int type)
		throws PortalException, SystemException {

		return expandoColumnLocalService.updateColumn(
			columnId, name, type, null);
	}

	public ExpandoColumn updateColumn(
			long columnId, String name, int type, Object defaultData)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnPersistence.findByPrimaryKey(
			columnId);

		ExpandoValue value = validate(
			columnId, column.getTableId(), name, type, defaultData);

		column.setName(name);
		column.setType(type);
		column.setDefaultData(value.getData());

		expandoColumnPersistence.update(column);

		return column;
	}

	public ExpandoColumn updateTypeSettings(long columnId, String typeSettings)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnPersistence.findByPrimaryKey(
			columnId);

		column.setTypeSettings(typeSettings);

		expandoColumnPersistence.update(column);

		return column;
	}

	protected ExpandoValue validate(
			long columnId, long tableId, String name, int type,
			Object defaultData)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new ColumnNameException();
		}

		ExpandoColumn column = expandoColumnPersistence.fetchByT_N(
			tableId, name);

		if (column != null) {
			if (column.getColumnId() != columnId) {
				throw new DuplicateColumnNameException();
			}
		}

		if ((type != ExpandoColumnConstants.BOOLEAN) &&
			(type != ExpandoColumnConstants.BOOLEAN_ARRAY) &&
			(type != ExpandoColumnConstants.DATE) &&
			(type != ExpandoColumnConstants.DATE_ARRAY) &&
			(type != ExpandoColumnConstants.DOUBLE) &&
			(type != ExpandoColumnConstants.DOUBLE_ARRAY) &&
			(type != ExpandoColumnConstants.FLOAT) &&
			(type != ExpandoColumnConstants.FLOAT_ARRAY) &&
			(type != ExpandoColumnConstants.INTEGER) &&
			(type != ExpandoColumnConstants.INTEGER_ARRAY) &&
			(type != ExpandoColumnConstants.LONG) &&
			(type != ExpandoColumnConstants.LONG_ARRAY) &&
			(type != ExpandoColumnConstants.NUMBER) &&
			(type != ExpandoColumnConstants.NUMBER_ARRAY) &&
			(type != ExpandoColumnConstants.SHORT) &&
			(type != ExpandoColumnConstants.SHORT_ARRAY) &&
			(type != ExpandoColumnConstants.STRING) &&
			(type != ExpandoColumnConstants.STRING_ARRAY) &&
			(type != ExpandoColumnConstants.STRING_ARRAY_LOCALIZED) &&
			(type != ExpandoColumnConstants.STRING_LOCALIZED)) {

			throw new ColumnTypeException();
		}

		ExpandoValue value = new ExpandoValueImpl();

		if (defaultData == null) {
			return value;
		}

		value.setColumnId(columnId);

		if (type == ExpandoColumnConstants.BOOLEAN) {
			value.setBoolean((Boolean)defaultData);
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			value.setBooleanArray((boolean[])defaultData);
		}
		else if (type == ExpandoColumnConstants.DATE) {
			value.setDate((Date)defaultData);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			value.setDateArray((Date[])defaultData);
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			value.setDouble((Double)defaultData);
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			value.setDoubleArray((double[])defaultData);
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			value.setFloat((Float)defaultData);
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			value.setFloatArray((float[])defaultData);
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			value.setInteger((Integer)defaultData);
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			value.setIntegerArray((int[])defaultData);
		}
		else if (type == ExpandoColumnConstants.LONG) {
			value.setLong((Long)defaultData);
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			value.setLongArray((long[])defaultData);
		}
		else if (type == ExpandoColumnConstants.NUMBER) {
			value.setNumber((Number)defaultData);
		}
		else if (type == ExpandoColumnConstants.NUMBER_ARRAY) {
			value.setNumberArray((Number[])defaultData);
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			value.setShort((Short)defaultData);
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			value.setShortArray((short[])defaultData);
		}
		else if (type == ExpandoColumnConstants.STRING) {
			value.setString((String)defaultData);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			value.setStringArray((String[])defaultData);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY_LOCALIZED) {
			value.setStringArrayMap(
				(Map<Locale, String[]>)defaultData, LocaleUtil.getDefault());
		}
		else if (type == ExpandoColumnConstants.STRING_LOCALIZED) {
			value.setStringMap(
				(Map<Locale, String>)defaultData, LocaleUtil.getDefault());
		}

		return value;
	}

}
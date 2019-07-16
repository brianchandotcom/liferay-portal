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

package com.liferay.portlet.expando.service.impl;

import com.liferay.expando.kernel.exception.DuplicateTableNameException;
import com.liferay.expando.kernel.exception.TableNameException;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.expando.service.base.ExpandoTableLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class ExpandoTableLocalServiceImpl
	extends ExpandoTableLocalServiceBaseImpl {

	@Override
	public ExpandoTable addDefaultTable(long companyId, long classNameId)
		throws PortalException {

		return addTable(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	@Override
	public ExpandoTable addDefaultTable(long companyId, String className)
		throws PortalException {

		return addTable(
			companyId, className, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	@Override
	public ExpandoTable addTable(long companyId, long classNameId, String name)
		throws PortalException {

		ExpandoTable expandoTable = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, name);

		if (expandoTable != null) {
			return expandoTable;
		}

		validate(companyId, 0, classNameId, name);

		long tableId = counterLocalService.increment();

		ExpandoTable table = expandoTablePersistence.create(tableId);

		table.setCompanyId(companyId);
		table.setClassNameId(classNameId);
		table.setName(name);

		expandoTablePersistence.update(table);

		return table;
	}

	@Override
	public ExpandoTable addTable(long companyId, String className, String name)
		throws PortalException {

		return addTable(
			companyId, classNameLocalService.getClassNameId(className), name);
	}

	@Override
	public void deleteTable(ExpandoTable table) {

		// Table

		expandoTablePersistence.remove(table);

		// Columns

		runSQL(
			"delete from ExpandoColumn where tableId = " + table.getTableId());

		expandoColumnPersistence.clearCache();

		// Rows

		runSQL("delete from ExpandoRow where tableId = " + table.getTableId());

		expandoRowPersistence.clearCache();

		// Values

		runSQL(
			"delete from ExpandoValue where tableId = " + table.getTableId());

		expandoValuePersistence.clearCache();
	}

	@Override
	public void deleteTable(long tableId) throws PortalException {
		ExpandoTable table = expandoTablePersistence.findByPrimaryKey(tableId);

		deleteTable(table);
	}

	@Override
	public void deleteTable(long companyId, long classNameId, String name)
		throws PortalException {

		ExpandoTable table = expandoTablePersistence.findByC_C_N(
			companyId, classNameId, name);

		deleteTable(table);
	}

	@Override
	public void deleteTable(long companyId, String className, String name)
		throws PortalException {

		deleteTable(
			companyId, classNameLocalService.getClassNameId(className), name);
	}

	@Override
	public void deleteTables(long companyId, long classNameId) {
		List<ExpandoTable> tables = expandoTablePersistence.findByC_C(
			companyId, classNameId);

		for (ExpandoTable table : tables) {
			deleteTable(table);
		}
	}

	@Override
	public void deleteTables(long companyId, String className) {
		deleteTables(
			companyId, classNameLocalService.getClassNameId(className));
	}

	@Override
	public ExpandoTable fetchDefaultTable(long companyId, long classNameId) {
		return fetchTable(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	@Override
	public ExpandoTable fetchDefaultTable(long companyId, String className) {
		return fetchTable(
			companyId, classNameLocalService.getClassNameId(className),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	@Override
	public ExpandoTable fetchTable(
		long companyId, long classNameId, String name) {

		return expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, name);
	}

	@Override
	public ExpandoTable getDefaultTable(long companyId, long classNameId)
		throws PortalException {

		return getTable(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	@Override
	public ExpandoTable getDefaultTable(long companyId, String className)
		throws PortalException {

		return getTable(
			companyId, classNameLocalService.getClassNameId(className),
			ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	@Override
	public ExpandoTable getTable(long tableId) throws PortalException {
		return expandoTablePersistence.findByPrimaryKey(tableId);
	}

	@Override
	public ExpandoTable getTable(long companyId, long classNameId, String name)
		throws PortalException {

		return expandoTablePersistence.findByC_C_N(
			companyId, classNameId, name);
	}

	@Override
	public ExpandoTable getTable(long companyId, String className, String name)
		throws PortalException {

		return getTable(
			companyId, classNameLocalService.getClassNameId(className), name);
	}

	@Override
	public List<ExpandoTable> getTables(long companyId, long classNameId) {
		return expandoTablePersistence.findByC_C(companyId, classNameId);
	}

	@Override
	public List<ExpandoTable> getTables(long companyId, String className) {
		return getTables(
			companyId, classNameLocalService.getClassNameId(className));
	}

	@Override
	public ExpandoTable updateTable(long tableId, String name)
		throws PortalException {

		ExpandoTable table = expandoTablePersistence.findByPrimaryKey(tableId);

		String tableName = table.getName();

		if (tableName.equals(ExpandoTableConstants.DEFAULT_TABLE_NAME)) {
			throw new TableNameException(
				"Cannot rename " + ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}

		validate(table.getCompanyId(), tableId, table.getClassNameId(), name);

		table.setName(name);

		return expandoTablePersistence.update(table);
	}

	protected void validate(
			long companyId, long tableId, long classNameId, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new TableNameException("Name is null");
		}

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, name);

		if ((table != null) && (table.getTableId() != tableId)) {
			throw new DuplicateTableNameException("{tableId=" + tableId + "}");
		}
	}

}
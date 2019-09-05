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

package com.liferay.expando.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ExpandoTable}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTable
 * @generated
 */
public class ExpandoTableWrapper
	extends BaseModelWrapper<ExpandoTable>
	implements ExpandoTable, ModelWrapper<ExpandoTable> {

	public ExpandoTableWrapper(ExpandoTable expandoTable) {
		super(expandoTable);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("tableId", getTableId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long tableId = (Long)attributes.get("tableId");

		if (tableId != null) {
			setTableId(tableId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	/**
	 * Returns the fully qualified class name of this expando table.
	 *
	 * @return the fully qualified class name of this expando table
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this expando table.
	 *
	 * @return the class name ID of this expando table
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the company ID of this expando table.
	 *
	 * @return the company ID of this expando table
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the name of this expando table.
	 *
	 * @return the name of this expando table
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this expando table.
	 *
	 * @return the primary key of this expando table
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the table ID of this expando table.
	 *
	 * @return the table ID of this expando table
	 */
	@Override
	public long getTableId() {
		return model.getTableId();
	}

	@Override
	public boolean isDefaultTable() {
		return model.isDefaultTable();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this expando table.
	 *
	 * @param classNameId the class name ID of this expando table
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the company ID of this expando table.
	 *
	 * @param companyId the company ID of this expando table
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the name of this expando table.
	 *
	 * @param name the name of this expando table
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this expando table.
	 *
	 * @param primaryKey the primary key of this expando table
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the table ID of this expando table.
	 *
	 * @param tableId the table ID of this expando table
	 */
	@Override
	public void setTableId(long tableId) {
		model.setTableId(tableId);
	}

	@Override
	protected ExpandoTableWrapper wrap(ExpandoTable expandoTable) {
		return new ExpandoTableWrapper(expandoTable);
	}

}
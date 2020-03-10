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

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ExpandoColumn}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumn
 * @generated
 */
public class ExpandoColumnWrapper
	extends BaseModelWrapper<ExpandoColumn>
	implements ExpandoColumn, ModelWrapper<ExpandoColumn> {

	public ExpandoColumnWrapper(ExpandoColumn expandoColumn) {
		super(expandoColumn);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("columnId", getColumnId());
		attributes.put("companyId", getCompanyId());
		attributes.put("tableId", getTableId());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("defaultData", getDefaultData());
		attributes.put("typeSettings", getTypeSettings());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long columnId = (Long)attributes.get("columnId");

		if (columnId != null) {
			setColumnId(columnId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long tableId = (Long)attributes.get("tableId");

		if (tableId != null) {
			setTableId(tableId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String defaultData = (String)attributes.get("defaultData");

		if (defaultData != null) {
			setDefaultData(defaultData);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}
	}

	/**
	 * Returns the column ID of this expando column.
	 *
	 * @return the column ID of this expando column
	 */
	@Override
	public long getColumnId() {
		return model.getColumnId();
	}

	/**
	 * Returns the company ID of this expando column.
	 *
	 * @return the company ID of this expando column
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the default data of this expando column.
	 *
	 * @return the default data of this expando column
	 */
	@Override
	public String getDefaultData() {
		return model.getDefaultData();
	}

	@Override
	public Serializable getDefaultValue() {
		return model.getDefaultValue();
	}

	@Override
	public String getDisplayName(java.util.Locale locale) {
		return model.getDisplayName(locale);
	}

	/**
	 * Returns the name of this expando column.
	 *
	 * @return the name of this expando column
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this expando column.
	 *
	 * @return the primary key of this expando column
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the table ID of this expando column.
	 *
	 * @return the table ID of this expando column
	 */
	@Override
	public long getTableId() {
		return model.getTableId();
	}

	/**
	 * Returns the type of this expando column.
	 *
	 * @return the type of this expando column
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the type settings of this expando column.
	 *
	 * @return the type settings of this expando column
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsProperties() {

		return model.getTypeSettingsProperties();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the column ID of this expando column.
	 *
	 * @param columnId the column ID of this expando column
	 */
	@Override
	public void setColumnId(long columnId) {
		model.setColumnId(columnId);
	}

	/**
	 * Sets the company ID of this expando column.
	 *
	 * @param companyId the company ID of this expando column
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the default data of this expando column.
	 *
	 * @param defaultData the default data of this expando column
	 */
	@Override
	public void setDefaultData(String defaultData) {
		model.setDefaultData(defaultData);
	}

	/**
	 * Sets the name of this expando column.
	 *
	 * @param name the name of this expando column
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this expando column.
	 *
	 * @param primaryKey the primary key of this expando column
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the table ID of this expando column.
	 *
	 * @param tableId the table ID of this expando column
	 */
	@Override
	public void setTableId(long tableId) {
		model.setTableId(tableId);
	}

	/**
	 * Sets the type of this expando column.
	 *
	 * @param type the type of this expando column
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the type settings of this expando column.
	 *
	 * @param typeSettings the type settings of this expando column
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsUnicodeProperties) {

		model.setTypeSettingsProperties(typeSettingsUnicodeProperties);
	}

	@Override
	protected ExpandoColumnWrapper wrap(ExpandoColumn expandoColumn) {
		return new ExpandoColumnWrapper(expandoColumn);
	}

}
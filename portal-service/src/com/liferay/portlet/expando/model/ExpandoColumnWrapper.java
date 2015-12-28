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

package com.liferay.portlet.expando.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

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
@ProviderType
public class ExpandoColumnWrapper implements ExpandoColumn,
	ModelWrapper<ExpandoColumn> {
	public ExpandoColumnWrapper(ExpandoColumn expandoColumn) {
		_expandoColumn = expandoColumn;
	}

	@Override
	public Class<?> getModelClass() {
		return ExpandoColumn.class;
	}

	@Override
	public String getModelClassName() {
		return ExpandoColumn.class.getName();
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

	@Override
	public java.lang.Object clone() {
		return new ExpandoColumnWrapper((ExpandoColumn)_expandoColumn.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn) {
		return _expandoColumn.compareTo(expandoColumn);
	}

	/**
	* Returns the column ID of this expando column.
	*
	* @return the column ID of this expando column
	*/
	@Override
	public long getColumnId() {
		return _expandoColumn.getColumnId();
	}

	/**
	* Returns the company ID of this expando column.
	*
	* @return the company ID of this expando column
	*/
	@Override
	public long getCompanyId() {
		return _expandoColumn.getCompanyId();
	}

	/**
	* Returns the default data of this expando column.
	*
	* @return the default data of this expando column
	*/
	@Override
	public java.lang.String getDefaultData() {
		return _expandoColumn.getDefaultData();
	}

	@Override
	public java.io.Serializable getDefaultValue() {
		return _expandoColumn.getDefaultValue();
	}

	@Override
	public java.lang.String getDisplayName(java.util.Locale locale) {
		return _expandoColumn.getDisplayName(locale);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _expandoColumn.getExpandoBridge();
	}

	/**
	* Returns the name of this expando column.
	*
	* @return the name of this expando column
	*/
	@Override
	public java.lang.String getName() {
		return _expandoColumn.getName();
	}

	/**
	* Returns the primary key of this expando column.
	*
	* @return the primary key of this expando column
	*/
	@Override
	public long getPrimaryKey() {
		return _expandoColumn.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoColumn.getPrimaryKeyObj();
	}

	/**
	* Returns the table ID of this expando column.
	*
	* @return the table ID of this expando column
	*/
	@Override
	public long getTableId() {
		return _expandoColumn.getTableId();
	}

	/**
	* Returns the type of this expando column.
	*
	* @return the type of this expando column
	*/
	@Override
	public int getType() {
		return _expandoColumn.getType();
	}

	/**
	* Returns the type settings of this expando column.
	*
	* @return the type settings of this expando column
	*/
	@Override
	public java.lang.String getTypeSettings() {
		return _expandoColumn.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _expandoColumn.getTypeSettingsProperties();
	}

	@Override
	public int hashCode() {
		return _expandoColumn.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _expandoColumn.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _expandoColumn.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _expandoColumn.isNew();
	}

	@Override
	public void persist() {
		_expandoColumn.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_expandoColumn.setCachedModel(cachedModel);
	}

	/**
	* Sets the column ID of this expando column.
	*
	* @param columnId the column ID of this expando column
	*/
	@Override
	public void setColumnId(long columnId) {
		_expandoColumn.setColumnId(columnId);
	}

	/**
	* Sets the company ID of this expando column.
	*
	* @param companyId the company ID of this expando column
	*/
	@Override
	public void setCompanyId(long companyId) {
		_expandoColumn.setCompanyId(companyId);
	}

	/**
	* Sets the default data of this expando column.
	*
	* @param defaultData the default data of this expando column
	*/
	@Override
	public void setDefaultData(java.lang.String defaultData) {
		_expandoColumn.setDefaultData(defaultData);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_expandoColumn.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_expandoColumn.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoColumn.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the name of this expando column.
	*
	* @param name the name of this expando column
	*/
	@Override
	public void setName(java.lang.String name) {
		_expandoColumn.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_expandoColumn.setNew(n);
	}

	/**
	* Sets the primary key of this expando column.
	*
	* @param primaryKey the primary key of this expando column
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_expandoColumn.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_expandoColumn.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the table ID of this expando column.
	*
	* @param tableId the table ID of this expando column
	*/
	@Override
	public void setTableId(long tableId) {
		_expandoColumn.setTableId(tableId);
	}

	/**
	* Sets the type of this expando column.
	*
	* @param type the type of this expando column
	*/
	@Override
	public void setType(int type) {
		_expandoColumn.setType(type);
	}

	/**
	* Sets the type settings of this expando column.
	*
	* @param typeSettings the type settings of this expando column
	*/
	@Override
	public void setTypeSettings(java.lang.String typeSettings) {
		_expandoColumn.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_expandoColumn.setTypeSettingsProperties(typeSettingsProperties);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.expando.model.ExpandoColumn> toCacheModel() {
		return _expandoColumn.toCacheModel();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn toEscapedModel() {
		return new ExpandoColumnWrapper(_expandoColumn.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _expandoColumn.toString();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn toUnescapedModel() {
		return new ExpandoColumnWrapper(_expandoColumn.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _expandoColumn.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExpandoColumnWrapper)) {
			return false;
		}

		ExpandoColumnWrapper expandoColumnWrapper = (ExpandoColumnWrapper)obj;

		if (Validator.equals(_expandoColumn, expandoColumnWrapper._expandoColumn)) {
			return true;
		}

		return false;
	}

	@Override
	public ExpandoColumn getWrappedModel() {
		return _expandoColumn;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _expandoColumn.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _expandoColumn.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_expandoColumn.resetOriginalValues();
	}

	private final ExpandoColumn _expandoColumn;
}
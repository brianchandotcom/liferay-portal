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

package com.liferay.portlet.expando.model;

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

	/**
	* Returns the primary key of this expando column.
	*
	* @return the primary key of this expando column
	*/
	@Override
	public long getPrimaryKey() {
		return _expandoColumn.getPrimaryKey();
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
	* Sets the column ID of this expando column.
	*
	* @param columnId the column ID of this expando column
	*/
	@Override
	public void setColumnId(long columnId) {
		_expandoColumn.setColumnId(columnId);
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
	* Sets the company ID of this expando column.
	*
	* @param companyId the company ID of this expando column
	*/
	@Override
	public void setCompanyId(long companyId) {
		_expandoColumn.setCompanyId(companyId);
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
	* Sets the table ID of this expando column.
	*
	* @param tableId the table ID of this expando column
	*/
	@Override
	public void setTableId(long tableId) {
		_expandoColumn.setTableId(tableId);
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
	* Sets the name of this expando column.
	*
	* @param name the name of this expando column
	*/
	@Override
	public void setName(java.lang.String name) {
		_expandoColumn.setName(name);
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
	* Sets the type of this expando column.
	*
	* @param type the type of this expando column
	*/
	@Override
	public void setType(int type) {
		_expandoColumn.setType(type);
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

	/**
	* Sets the default data of this expando column.
	*
	* @param defaultData the default data of this expando column
	*/
	@Override
	public void setDefaultData(java.lang.String defaultData) {
		_expandoColumn.setDefaultData(defaultData);
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
	public boolean isNew() {
		return _expandoColumn.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_expandoColumn.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _expandoColumn.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_expandoColumn.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _expandoColumn.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoColumn.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_expandoColumn.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _expandoColumn.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_expandoColumn.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_expandoColumn.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoColumn.setExpandoBridgeAttributes(serviceContext);
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

	@Override
	public int hashCode() {
		return _expandoColumn.hashCode();
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
	public com.liferay.portlet.expando.model.ExpandoColumn toUnescapedModel() {
		return new ExpandoColumnWrapper(_expandoColumn.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _expandoColumn.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _expandoColumn.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoColumn.persist();
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
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _expandoColumn.getTypeSettingsProperties();
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_expandoColumn.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ExpandoColumn getWrappedExpandoColumn() {
		return _expandoColumn;
	}

	@Override
	public ExpandoColumn getWrappedModel() {
		return _expandoColumn;
	}

	@Override
	public void resetOriginalValues() {
		_expandoColumn.resetOriginalValues();
	}

	private ExpandoColumn _expandoColumn;
}
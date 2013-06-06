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
 * This class is a wrapper for {@link ExpandoTable}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTable
 * @generated
 */
public class ExpandoTableWrapper implements ExpandoTable,
	ModelWrapper<ExpandoTable> {
	public ExpandoTableWrapper(ExpandoTable expandoTable) {
		_expandoTable = expandoTable;
	}

	@Override
	public Class<?> getModelClass() {
		return ExpandoTable.class;
	}

	@Override
	public String getModelClassName() {
		return ExpandoTable.class.getName();
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
	* Returns the primary key of this expando table.
	*
	* @return the primary key of this expando table
	*/
	@Override
	public long getPrimaryKey() {
		return _expandoTable.getPrimaryKey();
	}

	/**
	* Sets the primary key of this expando table.
	*
	* @param primaryKey the primary key of this expando table
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_expandoTable.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the table ID of this expando table.
	*
	* @return the table ID of this expando table
	*/
	@Override
	public long getTableId() {
		return _expandoTable.getTableId();
	}

	/**
	* Sets the table ID of this expando table.
	*
	* @param tableId the table ID of this expando table
	*/
	@Override
	public void setTableId(long tableId) {
		_expandoTable.setTableId(tableId);
	}

	/**
	* Returns the company ID of this expando table.
	*
	* @return the company ID of this expando table
	*/
	@Override
	public long getCompanyId() {
		return _expandoTable.getCompanyId();
	}

	/**
	* Sets the company ID of this expando table.
	*
	* @param companyId the company ID of this expando table
	*/
	@Override
	public void setCompanyId(long companyId) {
		_expandoTable.setCompanyId(companyId);
	}

	/**
	* Returns the fully qualified class name of this expando table.
	*
	* @return the fully qualified class name of this expando table
	*/
	@Override
	public java.lang.String getClassName() {
		return _expandoTable.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_expandoTable.setClassName(className);
	}

	/**
	* Returns the class name ID of this expando table.
	*
	* @return the class name ID of this expando table
	*/
	@Override
	public long getClassNameId() {
		return _expandoTable.getClassNameId();
	}

	/**
	* Sets the class name ID of this expando table.
	*
	* @param classNameId the class name ID of this expando table
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_expandoTable.setClassNameId(classNameId);
	}

	/**
	* Returns the name of this expando table.
	*
	* @return the name of this expando table
	*/
	@Override
	public java.lang.String getName() {
		return _expandoTable.getName();
	}

	/**
	* Sets the name of this expando table.
	*
	* @param name the name of this expando table
	*/
	@Override
	public void setName(java.lang.String name) {
		_expandoTable.setName(name);
	}

	@Override
	public boolean isNew() {
		return _expandoTable.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_expandoTable.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _expandoTable.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_expandoTable.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _expandoTable.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoTable.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_expandoTable.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _expandoTable.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_expandoTable.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_expandoTable.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoTable.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ExpandoTableWrapper((ExpandoTable)_expandoTable.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable) {
		return _expandoTable.compareTo(expandoTable);
	}

	@Override
	public int hashCode() {
		return _expandoTable.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.expando.model.ExpandoTable> toCacheModel() {
		return _expandoTable.toCacheModel();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable toEscapedModel() {
		return new ExpandoTableWrapper(_expandoTable.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoTable toUnescapedModel() {
		return new ExpandoTableWrapper(_expandoTable.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _expandoTable.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _expandoTable.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoTable.persist();
	}

	@Override
	public boolean isDefaultTable() {
		return _expandoTable.isDefaultTable();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ExpandoTable getWrappedExpandoTable() {
		return _expandoTable;
	}

	@Override
	public ExpandoTable getWrappedModel() {
		return _expandoTable;
	}

	@Override
	public void resetOriginalValues() {
		_expandoTable.resetOriginalValues();
	}

	private ExpandoTable _expandoTable;
}
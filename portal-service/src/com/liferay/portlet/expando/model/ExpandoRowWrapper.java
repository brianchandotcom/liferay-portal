/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link ExpandoRow}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoRow
 * @generated
 */
public class ExpandoRowWrapper implements ExpandoRow, ModelWrapper<ExpandoRow> {
	public ExpandoRowWrapper(ExpandoRow expandoRow) {
		_expandoRow = expandoRow;
	}

	public Class<?> getModelClass() {
		return ExpandoRow.class;
	}

	public String getModelClassName() {
		return ExpandoRow.class.getName();
	}

	/**
	* Returns the primary key of this expando row.
	*
	* @return the primary key of this expando row
	*/
	public long getPrimaryKey() {
		return _expandoRow.getPrimaryKey();
	}

	/**
	* Sets the primary key of this expando row.
	*
	* @param primaryKey the primary key of this expando row
	*/
	public void setPrimaryKey(long primaryKey) {
		_expandoRow.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the row ID of this expando row.
	*
	* @return the row ID of this expando row
	*/
	public long getRowId() {
		return _expandoRow.getRowId();
	}

	/**
	* Sets the row ID of this expando row.
	*
	* @param rowId the row ID of this expando row
	*/
	public void setRowId(long rowId) {
		_expandoRow.setRowId(rowId);
	}

	/**
	* Returns the company ID of this expando row.
	*
	* @return the company ID of this expando row
	*/
	public long getCompanyId() {
		return _expandoRow.getCompanyId();
	}

	/**
	* Sets the company ID of this expando row.
	*
	* @param companyId the company ID of this expando row
	*/
	public void setCompanyId(long companyId) {
		_expandoRow.setCompanyId(companyId);
	}

	/**
	* Returns the table ID of this expando row.
	*
	* @return the table ID of this expando row
	*/
	public long getTableId() {
		return _expandoRow.getTableId();
	}

	/**
	* Sets the table ID of this expando row.
	*
	* @param tableId the table ID of this expando row
	*/
	public void setTableId(long tableId) {
		_expandoRow.setTableId(tableId);
	}

	/**
	* Returns the class p k of this expando row.
	*
	* @return the class p k of this expando row
	*/
	public long getClassPK() {
		return _expandoRow.getClassPK();
	}

	/**
	* Sets the class p k of this expando row.
	*
	* @param classPK the class p k of this expando row
	*/
	public void setClassPK(long classPK) {
		_expandoRow.setClassPK(classPK);
	}

	public boolean isNew() {
		return _expandoRow.isNew();
	}

	public void setNew(boolean n) {
		_expandoRow.setNew(n);
	}

	public boolean isCachedModel() {
		return _expandoRow.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_expandoRow.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _expandoRow.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoRow.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_expandoRow.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _expandoRow.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoRow.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ExpandoRowWrapper((ExpandoRow)_expandoRow.clone());
	}

	public int compareTo(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow) {
		return _expandoRow.compareTo(expandoRow);
	}

	@Override
	public int hashCode() {
		return _expandoRow.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.expando.model.ExpandoRow> toCacheModel() {
		return _expandoRow.toCacheModel();
	}

	public com.liferay.portlet.expando.model.ExpandoRow toEscapedModel() {
		return new ExpandoRowWrapper(_expandoRow.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _expandoRow.toString();
	}

	public java.lang.String toXmlString() {
		return _expandoRow.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoRow.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public ExpandoRow getWrappedExpandoRow() {
		return _expandoRow;
	}

	public ExpandoRow getWrappedModel() {
		return _expandoRow;
	}

	public void resetOriginalValues() {
		_expandoRow.resetOriginalValues();
	}

	private ExpandoRow _expandoRow;
}
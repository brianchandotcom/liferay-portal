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
 * This class is a wrapper for {@link ExpandoValue}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValue
 * @generated
 */
public class ExpandoValueWrapper implements ExpandoValue,
	ModelWrapper<ExpandoValue> {
	public ExpandoValueWrapper(ExpandoValue expandoValue) {
		_expandoValue = expandoValue;
	}

	@Override
	public Class<?> getModelClass() {
		return ExpandoValue.class;
	}

	@Override
	public String getModelClassName() {
		return ExpandoValue.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("valueId", getValueId());
		attributes.put("companyId", getCompanyId());
		attributes.put("tableId", getTableId());
		attributes.put("columnId", getColumnId());
		attributes.put("rowId", getRowId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("data", getData());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long valueId = (Long)attributes.get("valueId");

		if (valueId != null) {
			setValueId(valueId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long tableId = (Long)attributes.get("tableId");

		if (tableId != null) {
			setTableId(tableId);
		}

		Long columnId = (Long)attributes.get("columnId");

		if (columnId != null) {
			setColumnId(columnId);
		}

		Long rowId = (Long)attributes.get("rowId");

		if (rowId != null) {
			setRowId(rowId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}
	}

	/**
	* Returns the primary key of this expando value.
	*
	* @return the primary key of this expando value
	*/
	@Override
	public long getPrimaryKey() {
		return _expandoValue.getPrimaryKey();
	}

	/**
	* Sets the primary key of this expando value.
	*
	* @param primaryKey the primary key of this expando value
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_expandoValue.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the value ID of this expando value.
	*
	* @return the value ID of this expando value
	*/
	@Override
	public long getValueId() {
		return _expandoValue.getValueId();
	}

	/**
	* Sets the value ID of this expando value.
	*
	* @param valueId the value ID of this expando value
	*/
	@Override
	public void setValueId(long valueId) {
		_expandoValue.setValueId(valueId);
	}

	/**
	* Returns the company ID of this expando value.
	*
	* @return the company ID of this expando value
	*/
	@Override
	public long getCompanyId() {
		return _expandoValue.getCompanyId();
	}

	/**
	* Sets the company ID of this expando value.
	*
	* @param companyId the company ID of this expando value
	*/
	@Override
	public void setCompanyId(long companyId) {
		_expandoValue.setCompanyId(companyId);
	}

	/**
	* Returns the table ID of this expando value.
	*
	* @return the table ID of this expando value
	*/
	@Override
	public long getTableId() {
		return _expandoValue.getTableId();
	}

	/**
	* Sets the table ID of this expando value.
	*
	* @param tableId the table ID of this expando value
	*/
	@Override
	public void setTableId(long tableId) {
		_expandoValue.setTableId(tableId);
	}

	/**
	* Returns the column ID of this expando value.
	*
	* @return the column ID of this expando value
	*/
	@Override
	public long getColumnId() {
		return _expandoValue.getColumnId();
	}

	/**
	* Sets the column ID of this expando value.
	*
	* @param columnId the column ID of this expando value
	*/
	@Override
	public void setColumnId(long columnId) {
		_expandoValue.setColumnId(columnId);
	}

	/**
	* Returns the row ID of this expando value.
	*
	* @return the row ID of this expando value
	*/
	@Override
	public long getRowId() {
		return _expandoValue.getRowId();
	}

	/**
	* Sets the row ID of this expando value.
	*
	* @param rowId the row ID of this expando value
	*/
	@Override
	public void setRowId(long rowId) {
		_expandoValue.setRowId(rowId);
	}

	/**
	* Returns the fully qualified class name of this expando value.
	*
	* @return the fully qualified class name of this expando value
	*/
	@Override
	public java.lang.String getClassName() {
		return _expandoValue.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_expandoValue.setClassName(className);
	}

	/**
	* Returns the class name ID of this expando value.
	*
	* @return the class name ID of this expando value
	*/
	@Override
	public long getClassNameId() {
		return _expandoValue.getClassNameId();
	}

	/**
	* Sets the class name ID of this expando value.
	*
	* @param classNameId the class name ID of this expando value
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_expandoValue.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this expando value.
	*
	* @return the class p k of this expando value
	*/
	@Override
	public long getClassPK() {
		return _expandoValue.getClassPK();
	}

	/**
	* Sets the class p k of this expando value.
	*
	* @param classPK the class p k of this expando value
	*/
	@Override
	public void setClassPK(long classPK) {
		_expandoValue.setClassPK(classPK);
	}

	/**
	* Returns the data of this expando value.
	*
	* @return the data of this expando value
	*/
	@Override
	public java.lang.String getData() {
		return _expandoValue.getData();
	}

	/**
	* Sets the data of this expando value.
	*
	* @param data the data of this expando value
	*/
	@Override
	public void setData(java.lang.String data) {
		_expandoValue.setData(data);
	}

	@Override
	public boolean isNew() {
		return _expandoValue.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_expandoValue.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _expandoValue.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_expandoValue.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _expandoValue.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoValue.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_expandoValue.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _expandoValue.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_expandoValue.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_expandoValue.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoValue.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ExpandoValueWrapper((ExpandoValue)_expandoValue.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue) {
		return _expandoValue.compareTo(expandoValue);
	}

	@Override
	public int hashCode() {
		return _expandoValue.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.expando.model.ExpandoValue> toCacheModel() {
		return _expandoValue.toCacheModel();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue toEscapedModel() {
		return new ExpandoValueWrapper(_expandoValue.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue toUnescapedModel() {
		return new ExpandoValueWrapper(_expandoValue.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _expandoValue.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _expandoValue.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.persist();
	}

	@Override
	public java.util.List<java.util.Locale> getAvailableLocales()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getAvailableLocales();
	}

	@Override
	public boolean getBoolean()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getBoolean();
	}

	@Override
	public boolean[] getBooleanArray()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getBooleanArray();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn getColumn()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getColumn();
	}

	@Override
	public java.util.Date getDate()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getDate();
	}

	@Override
	public java.util.Date[] getDateArray()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getDateArray();
	}

	@Override
	public java.util.Locale getDefaultLocale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getDefaultLocale();
	}

	@Override
	public double getDouble()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getDouble();
	}

	@Override
	public double[] getDoubleArray()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getDoubleArray();
	}

	@Override
	public float getFloat()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getFloat();
	}

	@Override
	public float[] getFloatArray()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getFloatArray();
	}

	@Override
	public int getInteger()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getInteger();
	}

	@Override
	public int[] getIntegerArray()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getIntegerArray();
	}

	@Override
	public long getLong()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getLong();
	}

	@Override
	public long[] getLongArray()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getLongArray();
	}

	@Override
	public java.lang.Number getNumber()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getNumber();
	}

	@Override
	public java.lang.Number[] getNumberArray()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getNumberArray();
	}

	@Override
	public java.io.Serializable getSerializable()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getSerializable();
	}

	@Override
	public short getShort()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getShort();
	}

	@Override
	public short[] getShortArray()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getShortArray();
	}

	@Override
	public java.lang.String getString()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getString();
	}

	@Override
	public java.lang.String getString(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getString(locale);
	}

	@Override
	public java.lang.String[] getStringArray()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getStringArray();
	}

	@Override
	public java.lang.String[] getStringArray(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getStringArray(locale);
	}

	@Override
	public java.util.Map<java.util.Locale, java.lang.String[]> getStringArrayMap()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getStringArrayMap();
	}

	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getStringMap()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _expandoValue.getStringMap();
	}

	@Override
	public void setBoolean(boolean data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setBoolean(data);
	}

	@Override
	public void setBooleanArray(boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setBooleanArray(data);
	}

	@Override
	public void setColumn(
		com.liferay.portlet.expando.model.ExpandoColumn column) {
		_expandoValue.setColumn(column);
	}

	@Override
	public void setDate(java.util.Date data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setDate(data);
	}

	@Override
	public void setDateArray(java.util.Date[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setDateArray(data);
	}

	@Override
	public void setDouble(double data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setDouble(data);
	}

	@Override
	public void setDoubleArray(double[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setDoubleArray(data);
	}

	@Override
	public void setFloat(float data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setFloat(data);
	}

	@Override
	public void setFloatArray(float[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setFloatArray(data);
	}

	@Override
	public void setInteger(int data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setInteger(data);
	}

	@Override
	public void setIntegerArray(int[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setIntegerArray(data);
	}

	@Override
	public void setLong(long data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setLong(data);
	}

	@Override
	public void setLongArray(long[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setLongArray(data);
	}

	@Override
	public void setNumber(java.lang.Number data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setNumber(data);
	}

	@Override
	public void setNumberArray(java.lang.Number[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setNumberArray(data);
	}

	@Override
	public void setShort(short data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setShort(data);
	}

	@Override
	public void setShortArray(short[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setShortArray(data);
	}

	@Override
	public void setString(java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setString(data);
	}

	@Override
	public void setString(java.lang.String data, java.util.Locale locale,
		java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setString(data, locale, defaultLocale);
	}

	@Override
	public void setStringArray(java.lang.String[] data)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setStringArray(data);
	}

	@Override
	public void setStringArray(java.lang.String[] data,
		java.util.Locale locale, java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setStringArray(data, locale, defaultLocale);
	}

	@Override
	public void setStringArrayMap(
		java.util.Map<java.util.Locale, java.lang.String[]> dataMap,
		java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setStringArrayMap(dataMap, defaultLocale);
	}

	@Override
	public void setStringMap(
		java.util.Map<java.util.Locale, java.lang.String> dataMap,
		java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_expandoValue.setStringMap(dataMap, defaultLocale);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ExpandoValue getWrappedExpandoValue() {
		return _expandoValue;
	}

	@Override
	public ExpandoValue getWrappedModel() {
		return _expandoValue;
	}

	@Override
	public void resetOriginalValues() {
		_expandoValue.resetOriginalValues();
	}

	private ExpandoValue _expandoValue;
}
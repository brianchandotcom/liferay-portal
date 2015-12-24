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
 * This class is a wrapper for {@link ExpandoValue}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValue
 * @generated
 */
@ProviderType
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
	public java.util.List<java.util.Locale> getAvailableLocales()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getAvailableLocales();
	}

	@Override
	public boolean getBoolean()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getBoolean();
	}

	@Override
	public boolean[] getBooleanArray()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getBooleanArray();
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
	* Returns the class p k of this expando value.
	*
	* @return the class p k of this expando value
	*/
	@Override
	public long getClassPK() {
		return _expandoValue.getClassPK();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoColumn getColumn()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getColumn();
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
	* Returns the company ID of this expando value.
	*
	* @return the company ID of this expando value
	*/
	@Override
	public long getCompanyId() {
		return _expandoValue.getCompanyId();
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

	@Override
	public java.util.Date getDate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getDate();
	}

	@Override
	public java.util.Date[] getDateArray()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getDateArray();
	}

	@Override
	public java.util.Locale getDefaultLocale()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getDefaultLocale();
	}

	@Override
	public double getDouble()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getDouble();
	}

	@Override
	public double[] getDoubleArray()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getDoubleArray();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _expandoValue.getExpandoBridge();
	}

	@Override
	public float getFloat()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getFloat();
	}

	@Override
	public float[] getFloatArray()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getFloatArray();
	}

	@Override
	public int getInteger()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getInteger();
	}

	@Override
	public int[] getIntegerArray()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getIntegerArray();
	}

	@Override
	public long getLong()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getLong();
	}

	@Override
	public long[] getLongArray()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getLongArray();
	}

	@Override
	public java.lang.Number getNumber()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getNumber();
	}

	@Override
	public java.lang.Number[] getNumberArray()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getNumberArray();
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

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _expandoValue.getPrimaryKeyObj();
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

	@Override
	public java.io.Serializable getSerializable()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getSerializable();
	}

	@Override
	public short getShort()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getShort();
	}

	@Override
	public short[] getShortArray()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getShortArray();
	}

	@Override
	public java.lang.String getString()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getString();
	}

	@Override
	public java.lang.String getString(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getString(locale);
	}

	@Override
	public java.lang.String[] getStringArray()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getStringArray();
	}

	@Override
	public java.lang.String[] getStringArray(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getStringArray(locale);
	}

	@Override
	public Map<java.util.Locale, java.lang.String[]> getStringArrayMap()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getStringArrayMap();
	}

	@Override
	public Map<java.util.Locale, java.lang.String> getStringMap()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _expandoValue.getStringMap();
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
	* Returns the value ID of this expando value.
	*
	* @return the value ID of this expando value
	*/
	@Override
	public long getValueId() {
		return _expandoValue.getValueId();
	}

	@Override
	public int hashCode() {
		return _expandoValue.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _expandoValue.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _expandoValue.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _expandoValue.isNew();
	}

	@Override
	public void persist() {
		_expandoValue.persist();
	}

	@Override
	public void setBoolean(boolean data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setBoolean(data);
	}

	@Override
	public void setBooleanArray(boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setBooleanArray(data);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_expandoValue.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_expandoValue.setClassName(className);
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
	* Sets the class p k of this expando value.
	*
	* @param classPK the class p k of this expando value
	*/
	@Override
	public void setClassPK(long classPK) {
		_expandoValue.setClassPK(classPK);
	}

	@Override
	public void setColumn(
		com.liferay.portlet.expando.model.ExpandoColumn column) {
		_expandoValue.setColumn(column);
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
	* Sets the company ID of this expando value.
	*
	* @param companyId the company ID of this expando value
	*/
	@Override
	public void setCompanyId(long companyId) {
		_expandoValue.setCompanyId(companyId);
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
	public void setDate(java.util.Date data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setDate(data);
	}

	@Override
	public void setDateArray(java.util.Date[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setDateArray(data);
	}

	@Override
	public void setDouble(double data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setDouble(data);
	}

	@Override
	public void setDoubleArray(double[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setDoubleArray(data);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_expandoValue.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_expandoValue.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_expandoValue.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setFloat(float data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setFloat(data);
	}

	@Override
	public void setFloatArray(float[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setFloatArray(data);
	}

	@Override
	public void setInteger(int data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setInteger(data);
	}

	@Override
	public void setIntegerArray(int[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setIntegerArray(data);
	}

	@Override
	public void setLong(long data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setLong(data);
	}

	@Override
	public void setLongArray(long[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setLongArray(data);
	}

	@Override
	public void setNew(boolean n) {
		_expandoValue.setNew(n);
	}

	@Override
	public void setNumber(java.lang.Number data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setNumber(data);
	}

	@Override
	public void setNumberArray(java.lang.Number[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setNumberArray(data);
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

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_expandoValue.setPrimaryKeyObj(primaryKeyObj);
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

	@Override
	public void setShort(short data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setShort(data);
	}

	@Override
	public void setShortArray(short[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setShortArray(data);
	}

	@Override
	public void setString(java.lang.String data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setString(data);
	}

	@Override
	public void setString(java.lang.String data, java.util.Locale locale,
		java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setString(data, locale, defaultLocale);
	}

	@Override
	public void setStringArray(java.lang.String[] data)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setStringArray(data);
	}

	@Override
	public void setStringArray(java.lang.String[] data,
		java.util.Locale locale, java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setStringArray(data, locale, defaultLocale);
	}

	@Override
	public void setStringArrayMap(
		Map<java.util.Locale, java.lang.String[]> dataMap,
		java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setStringArrayMap(dataMap, defaultLocale);
	}

	@Override
	public void setStringMap(Map<java.util.Locale, java.lang.String> dataMap,
		java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException {
		_expandoValue.setStringMap(dataMap, defaultLocale);
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
	* Sets the value ID of this expando value.
	*
	* @param valueId the value ID of this expando value
	*/
	@Override
	public void setValueId(long valueId) {
		_expandoValue.setValueId(valueId);
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
	public java.lang.String toString() {
		return _expandoValue.toString();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoValue toUnescapedModel() {
		return new ExpandoValueWrapper(_expandoValue.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _expandoValue.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExpandoValueWrapper)) {
			return false;
		}

		ExpandoValueWrapper expandoValueWrapper = (ExpandoValueWrapper)obj;

		if (Validator.equals(_expandoValue, expandoValueWrapper._expandoValue)) {
			return true;
		}

		return false;
	}

	@Override
	public ExpandoValue getWrappedModel() {
		return _expandoValue;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _expandoValue.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _expandoValue.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_expandoValue.resetOriginalValues();
	}

	private final ExpandoValue _expandoValue;
}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link ExpandoValue}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValue
 * @generated
 */
public class ExpandoValueWrapper
	extends BaseModelWrapper<ExpandoValue>
	implements ExpandoValue, ModelWrapper<ExpandoValue> {

	public ExpandoValueWrapper(ExpandoValue expandoValue) {
		super(expandoValue);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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
	public ExpandoValue cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public java.util.List<java.util.Locale> getAvailableLocales()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAvailableLocales();
	}

	@Override
	public boolean getBoolean()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getBoolean();
	}

	@Override
	public boolean[] getBooleanArray()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getBooleanArray();
	}

	/**
	 * Returns the fully qualified class name of this expando value.
	 *
	 * @return the fully qualified class name of this expando value
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this expando value.
	 *
	 * @return the class name ID of this expando value
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this expando value.
	 *
	 * @return the class pk of this expando value
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	@Override
	public ExpandoColumn getColumn()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getColumn();
	}

	/**
	 * Returns the column ID of this expando value.
	 *
	 * @return the column ID of this expando value
	 */
	@Override
	public long getColumnId() {
		return model.getColumnId();
	}

	/**
	 * Returns the company ID of this expando value.
	 *
	 * @return the company ID of this expando value
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this expando value.
	 *
	 * @return the ct collection ID of this expando value
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the data of this expando value.
	 *
	 * @return the data of this expando value
	 */
	@Override
	public String getData() {
		return model.getData();
	}

	@Override
	public Date getDate()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDate();
	}

	@Override
	public Date[] getDateArray()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDateArray();
	}

	@Override
	public java.util.Locale getDefaultLocale()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDefaultLocale();
	}

	@Override
	public double getDouble()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDouble();
	}

	@Override
	public double[] getDoubleArray()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDoubleArray();
	}

	@Override
	public float getFloat()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFloat();
	}

	@Override
	public float[] getFloatArray()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFloatArray();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getGeolocationJSONObject()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getGeolocationJSONObject();
	}

	@Override
	public int getInteger()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getInteger();
	}

	@Override
	public int[] getIntegerArray()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getIntegerArray();
	}

	@Override
	public long getLong()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getLong();
	}

	@Override
	public long[] getLongArray()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getLongArray();
	}

	/**
	 * Returns the mvcc version of this expando value.
	 *
	 * @return the mvcc version of this expando value
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	@Override
	public Number getNumber()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getNumber();
	}

	@Override
	public Number[] getNumberArray()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getNumberArray();
	}

	/**
	 * Returns the primary key of this expando value.
	 *
	 * @return the primary key of this expando value
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the row ID of this expando value.
	 *
	 * @return the row ID of this expando value
	 */
	@Override
	public long getRowId() {
		return model.getRowId();
	}

	@Override
	public Serializable getSerializable()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getSerializable();
	}

	@Override
	public short getShort()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getShort();
	}

	@Override
	public short[] getShortArray()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getShortArray();
	}

	@Override
	public String getString()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getString();
	}

	@Override
	public String getString(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getString(locale);
	}

	@Override
	public String[] getStringArray()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getStringArray();
	}

	@Override
	public String[] getStringArray(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getStringArray(locale);
	}

	@Override
	public Map<java.util.Locale, String[]> getStringArrayMap()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getStringArrayMap();
	}

	@Override
	public Map<java.util.Locale, String> getStringMap()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getStringMap();
	}

	/**
	 * Returns the table ID of this expando value.
	 *
	 * @return the table ID of this expando value
	 */
	@Override
	public long getTableId() {
		return model.getTableId();
	}

	/**
	 * Returns the value ID of this expando value.
	 *
	 * @return the value ID of this expando value
	 */
	@Override
	public long getValueId() {
		return model.getValueId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setBoolean(boolean data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setBoolean(data);
	}

	@Override
	public void setBooleanArray(boolean[] data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setBooleanArray(data);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this expando value.
	 *
	 * @param classNameId the class name ID of this expando value
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this expando value.
	 *
	 * @param classPK the class pk of this expando value
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	@Override
	public void setColumn(ExpandoColumn column) {
		model.setColumn(column);
	}

	/**
	 * Sets the column ID of this expando value.
	 *
	 * @param columnId the column ID of this expando value
	 */
	@Override
	public void setColumnId(long columnId) {
		model.setColumnId(columnId);
	}

	/**
	 * Sets the company ID of this expando value.
	 *
	 * @param companyId the company ID of this expando value
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this expando value.
	 *
	 * @param ctCollectionId the ct collection ID of this expando value
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the data of this expando value.
	 *
	 * @param data the data of this expando value
	 */
	@Override
	public void setData(String data) {
		model.setData(data);
	}

	@Override
	public void setDate(Date data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setDate(data);
	}

	@Override
	public void setDateArray(Date[] data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setDateArray(data);
	}

	@Override
	public void setDouble(double data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setDouble(data);
	}

	@Override
	public void setDoubleArray(double[] data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setDoubleArray(data);
	}

	@Override
	public void setFloat(float data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setFloat(data);
	}

	@Override
	public void setFloatArray(float[] data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setFloatArray(data);
	}

	@Override
	public void setGeolocationJSONObject(
			com.liferay.portal.kernel.json.JSONObject dataJSONObject)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setGeolocationJSONObject(dataJSONObject);
	}

	@Override
	public void setInteger(int data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setInteger(data);
	}

	@Override
	public void setIntegerArray(int[] data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setIntegerArray(data);
	}

	@Override
	public void setLong(long data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setLong(data);
	}

	@Override
	public void setLongArray(long[] data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setLongArray(data);
	}

	/**
	 * Sets the mvcc version of this expando value.
	 *
	 * @param mvccVersion the mvcc version of this expando value
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNumber(Number data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setNumber(data);
	}

	@Override
	public void setNumberArray(Number[] data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setNumberArray(data);
	}

	/**
	 * Sets the primary key of this expando value.
	 *
	 * @param primaryKey the primary key of this expando value
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the row ID of this expando value.
	 *
	 * @param rowId the row ID of this expando value
	 */
	@Override
	public void setRowId(long rowId) {
		model.setRowId(rowId);
	}

	@Override
	public void setShort(short data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setShort(data);
	}

	@Override
	public void setShortArray(short[] data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setShortArray(data);
	}

	@Override
	public void setString(String data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setString(data);
	}

	@Override
	public void setString(
			String data, java.util.Locale locale,
			java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setString(data, locale, defaultLocale);
	}

	@Override
	public void setStringArray(String[] data)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setStringArray(data);
	}

	@Override
	public void setStringArray(
			String[] data, java.util.Locale locale,
			java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setStringArray(data, locale, defaultLocale);
	}

	@Override
	public void setStringArrayMap(
			Map<java.util.Locale, String[]> dataMap,
			java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setStringArrayMap(dataMap, defaultLocale);
	}

	@Override
	public void setStringMap(
			Map<java.util.Locale, String> dataMap,
			java.util.Locale defaultLocale)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setStringMap(dataMap, defaultLocale);
	}

	/**
	 * Sets the table ID of this expando value.
	 *
	 * @param tableId the table ID of this expando value
	 */
	@Override
	public void setTableId(long tableId) {
		model.setTableId(tableId);
	}

	/**
	 * Sets the value ID of this expando value.
	 *
	 * @param valueId the value ID of this expando value
	 */
	@Override
	public void setValueId(long valueId) {
		model.setValueId(valueId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<ExpandoValue, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<ExpandoValue, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected ExpandoValueWrapper wrap(ExpandoValue expandoValue) {
		return new ExpandoValueWrapper(expandoValue);
	}

}
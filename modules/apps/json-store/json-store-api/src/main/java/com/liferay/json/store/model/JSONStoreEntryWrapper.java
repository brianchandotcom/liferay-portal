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

package com.liferay.json.store.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link JSONStoreEntry}.
 * </p>
 *
 * @author Preston Crary
 * @see JSONStoreEntry
 * @generated
 */
public class JSONStoreEntryWrapper
	extends BaseModelWrapper<JSONStoreEntry>
	implements JSONStoreEntry, ModelWrapper<JSONStoreEntry> {

	public JSONStoreEntryWrapper(JSONStoreEntry jsonStoreEntry) {
		super(jsonStoreEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("jsonStoreEntryId", getJsonStoreEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("parentJSONStoreEntryId", getParentJSONStoreEntryId());
		attributes.put("index", getIndex());
		attributes.put("key", getKey());
		attributes.put("type", getType());
		attributes.put("valueLong", getValueLong());
		attributes.put("valueString", getValueString());

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

		Long jsonStoreEntryId = (Long)attributes.get("jsonStoreEntryId");

		if (jsonStoreEntryId != null) {
			setJsonStoreEntryId(jsonStoreEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long parentJSONStoreEntryId = (Long)attributes.get(
			"parentJSONStoreEntryId");

		if (parentJSONStoreEntryId != null) {
			setParentJSONStoreEntryId(parentJSONStoreEntryId);
		}

		Integer index = (Integer)attributes.get("index");

		if (index != null) {
			setIndex(index);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Long valueLong = (Long)attributes.get("valueLong");

		if (valueLong != null) {
			setValueLong(valueLong);
		}

		String valueString = (String)attributes.get("valueString");

		if (valueString != null) {
			setValueString(valueString);
		}
	}

	/**
	 * Returns the fully qualified class name of this json store entry.
	 *
	 * @return the fully qualified class name of this json store entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this json store entry.
	 *
	 * @return the class name ID of this json store entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this json store entry.
	 *
	 * @return the class pk of this json store entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this json store entry.
	 *
	 * @return the company ID of this json store entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this json store entry.
	 *
	 * @return the ct collection ID of this json store entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the index of this json store entry.
	 *
	 * @return the index of this json store entry
	 */
	@Override
	public int getIndex() {
		return model.getIndex();
	}

	/**
	 * Returns the json store entry ID of this json store entry.
	 *
	 * @return the json store entry ID of this json store entry
	 */
	@Override
	public long getJsonStoreEntryId() {
		return model.getJsonStoreEntryId();
	}

	/**
	 * Returns the key of this json store entry.
	 *
	 * @return the key of this json store entry
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the mvcc version of this json store entry.
	 *
	 * @return the mvcc version of this json store entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent json store entry ID of this json store entry.
	 *
	 * @return the parent json store entry ID of this json store entry
	 */
	@Override
	public long getParentJSONStoreEntryId() {
		return model.getParentJSONStoreEntryId();
	}

	/**
	 * Returns the primary key of this json store entry.
	 *
	 * @return the primary key of this json store entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this json store entry.
	 *
	 * @return the type of this json store entry
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	@Override
	public Object getValue() {
		return model.getValue();
	}

	/**
	 * Returns the value long of this json store entry.
	 *
	 * @return the value long of this json store entry
	 */
	@Override
	public long getValueLong() {
		return model.getValueLong();
	}

	/**
	 * Returns the value string of this json store entry.
	 *
	 * @return the value string of this json store entry
	 */
	@Override
	public String getValueString() {
		return model.getValueString();
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
	 * Sets the class name ID of this json store entry.
	 *
	 * @param classNameId the class name ID of this json store entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this json store entry.
	 *
	 * @param classPK the class pk of this json store entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this json store entry.
	 *
	 * @param companyId the company ID of this json store entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this json store entry.
	 *
	 * @param ctCollectionId the ct collection ID of this json store entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the index of this json store entry.
	 *
	 * @param index the index of this json store entry
	 */
	@Override
	public void setIndex(int index) {
		model.setIndex(index);
	}

	/**
	 * Sets the json store entry ID of this json store entry.
	 *
	 * @param jsonStoreEntryId the json store entry ID of this json store entry
	 */
	@Override
	public void setJsonStoreEntryId(long jsonStoreEntryId) {
		model.setJsonStoreEntryId(jsonStoreEntryId);
	}

	/**
	 * Sets the key of this json store entry.
	 *
	 * @param key the key of this json store entry
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the mvcc version of this json store entry.
	 *
	 * @param mvccVersion the mvcc version of this json store entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent json store entry ID of this json store entry.
	 *
	 * @param parentJSONStoreEntryId the parent json store entry ID of this json store entry
	 */
	@Override
	public void setParentJSONStoreEntryId(long parentJSONStoreEntryId) {
		model.setParentJSONStoreEntryId(parentJSONStoreEntryId);
	}

	/**
	 * Sets the primary key of this json store entry.
	 *
	 * @param primaryKey the primary key of this json store entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this json store entry.
	 *
	 * @param type the type of this json store entry
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	@Override
	public void setValue(Object value) {
		model.setValue(value);
	}

	/**
	 * Sets the value long of this json store entry.
	 *
	 * @param valueLong the value long of this json store entry
	 */
	@Override
	public void setValueLong(long valueLong) {
		model.setValueLong(valueLong);
	}

	/**
	 * Sets the value string of this json store entry.
	 *
	 * @param valueString the value string of this json store entry
	 */
	@Override
	public void setValueString(String valueString) {
		model.setValueString(valueString);
	}

	@Override
	public Map<String, Function<JSONStoreEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<JSONStoreEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected JSONStoreEntryWrapper wrap(JSONStoreEntry jsonStoreEntry) {
		return new JSONStoreEntryWrapper(jsonStoreEntry);
	}

}
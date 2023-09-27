/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.json.storage.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link JSONStorageEntry}.
 * </p>
 *
 * @author Preston Crary
 * @see JSONStorageEntry
 * @generated
 */
public class JSONStorageEntryWrapper
	extends BaseModelWrapper<JSONStorageEntry>
	implements JSONStorageEntry, ModelWrapper<JSONStorageEntry> {

	public JSONStorageEntryWrapper(JSONStorageEntry jsonStorageEntry) {
		super(jsonStorageEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("jsonStorageEntryId", getJsonStorageEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put(
			"parentJSONStorageEntryId", getParentJSONStorageEntryId());
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

		Long jsonStorageEntryId = (Long)attributes.get("jsonStorageEntryId");

		if (jsonStorageEntryId != null) {
			setJsonStorageEntryId(jsonStorageEntryId);
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

		Long parentJSONStorageEntryId = (Long)attributes.get(
			"parentJSONStorageEntryId");

		if (parentJSONStorageEntryId != null) {
			setParentJSONStorageEntryId(parentJSONStorageEntryId);
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

	@Override
	public JSONStorageEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this json storage entry.
	 *
	 * @return the fully qualified class name of this json storage entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this json storage entry.
	 *
	 * @return the class name ID of this json storage entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this json storage entry.
	 *
	 * @return the class pk of this json storage entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this json storage entry.
	 *
	 * @return the company ID of this json storage entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this json storage entry.
	 *
	 * @return the ct collection ID of this json storage entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the index of this json storage entry.
	 *
	 * @return the index of this json storage entry
	 */
	@Override
	public int getIndex() {
		return model.getIndex();
	}

	/**
	 * Returns the json storage entry ID of this json storage entry.
	 *
	 * @return the json storage entry ID of this json storage entry
	 */
	@Override
	public long getJsonStorageEntryId() {
		return model.getJsonStorageEntryId();
	}

	/**
	 * Returns the key of this json storage entry.
	 *
	 * @return the key of this json storage entry
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the mvcc version of this json storage entry.
	 *
	 * @return the mvcc version of this json storage entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent json storage entry ID of this json storage entry.
	 *
	 * @return the parent json storage entry ID of this json storage entry
	 */
	@Override
	public long getParentJSONStorageEntryId() {
		return model.getParentJSONStorageEntryId();
	}

	/**
	 * Returns the primary key of this json storage entry.
	 *
	 * @return the primary key of this json storage entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this json storage entry.
	 *
	 * @return the type of this json storage entry
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
	 * Returns the value long of this json storage entry.
	 *
	 * @return the value long of this json storage entry
	 */
	@Override
	public long getValueLong() {
		return model.getValueLong();
	}

	/**
	 * Returns the value string of this json storage entry.
	 *
	 * @return the value string of this json storage entry
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
	 * Sets the class name ID of this json storage entry.
	 *
	 * @param classNameId the class name ID of this json storage entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this json storage entry.
	 *
	 * @param classPK the class pk of this json storage entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this json storage entry.
	 *
	 * @param companyId the company ID of this json storage entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this json storage entry.
	 *
	 * @param ctCollectionId the ct collection ID of this json storage entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the index of this json storage entry.
	 *
	 * @param index the index of this json storage entry
	 */
	@Override
	public void setIndex(int index) {
		model.setIndex(index);
	}

	/**
	 * Sets the json storage entry ID of this json storage entry.
	 *
	 * @param jsonStorageEntryId the json storage entry ID of this json storage entry
	 */
	@Override
	public void setJsonStorageEntryId(long jsonStorageEntryId) {
		model.setJsonStorageEntryId(jsonStorageEntryId);
	}

	/**
	 * Sets the key of this json storage entry.
	 *
	 * @param key the key of this json storage entry
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the mvcc version of this json storage entry.
	 *
	 * @param mvccVersion the mvcc version of this json storage entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent json storage entry ID of this json storage entry.
	 *
	 * @param parentJSONStorageEntryId the parent json storage entry ID of this json storage entry
	 */
	@Override
	public void setParentJSONStorageEntryId(long parentJSONStorageEntryId) {
		model.setParentJSONStorageEntryId(parentJSONStorageEntryId);
	}

	/**
	 * Sets the primary key of this json storage entry.
	 *
	 * @param primaryKey the primary key of this json storage entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this json storage entry.
	 *
	 * @param type the type of this json storage entry
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
	 * Sets the value long of this json storage entry.
	 *
	 * @param valueLong the value long of this json storage entry
	 */
	@Override
	public void setValueLong(long valueLong) {
		model.setValueLong(valueLong);
	}

	/**
	 * Sets the value string of this json storage entry.
	 *
	 * @param valueString the value string of this json storage entry
	 */
	@Override
	public void setValueString(String valueString) {
		model.setValueString(valueString);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<JSONStorageEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<JSONStorageEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected JSONStorageEntryWrapper wrap(JSONStorageEntry jsonStorageEntry) {
		return new JSONStorageEntryWrapper(jsonStorageEntry);
	}

}
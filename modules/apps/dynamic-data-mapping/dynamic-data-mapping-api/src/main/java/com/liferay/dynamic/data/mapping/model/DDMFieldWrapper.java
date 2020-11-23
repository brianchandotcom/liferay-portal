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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DDMField}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMField
 * @generated
 */
public class DDMFieldWrapper
	extends BaseModelWrapper<DDMField>
	implements DDMField, ModelWrapper<DDMField> {

	public DDMFieldWrapper(DDMField ddmField) {
		super(ddmField);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("fieldId", getFieldId());
		attributes.put("companyId", getCompanyId());
		attributes.put("structureVersionId", getStructureVersionId());
		attributes.put("parentFieldId", getParentFieldId());
		attributes.put("storageId", getStorageId());
		attributes.put("fieldName", getFieldName());
		attributes.put("fieldType", getFieldType());
		attributes.put("priority", getPriority());
		attributes.put("instanceId", getInstanceId());
		attributes.put("localizable", isLocalizable());

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

		Long fieldId = (Long)attributes.get("fieldId");

		if (fieldId != null) {
			setFieldId(fieldId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long structureVersionId = (Long)attributes.get("structureVersionId");

		if (structureVersionId != null) {
			setStructureVersionId(structureVersionId);
		}

		Long parentFieldId = (Long)attributes.get("parentFieldId");

		if (parentFieldId != null) {
			setParentFieldId(parentFieldId);
		}

		Long storageId = (Long)attributes.get("storageId");

		if (storageId != null) {
			setStorageId(storageId);
		}

		String fieldName = (String)attributes.get("fieldName");

		if (fieldName != null) {
			setFieldName(fieldName);
		}

		String fieldType = (String)attributes.get("fieldType");

		if (fieldType != null) {
			setFieldType(fieldType);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String instanceId = (String)attributes.get("instanceId");

		if (instanceId != null) {
			setInstanceId(instanceId);
		}

		Boolean localizable = (Boolean)attributes.get("localizable");

		if (localizable != null) {
			setLocalizable(localizable);
		}
	}

	/**
	 * Returns the company ID of this ddm field.
	 *
	 * @return the company ID of this ddm field
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this ddm field.
	 *
	 * @return the ct collection ID of this ddm field
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the field ID of this ddm field.
	 *
	 * @return the field ID of this ddm field
	 */
	@Override
	public long getFieldId() {
		return model.getFieldId();
	}

	/**
	 * Returns the field name of this ddm field.
	 *
	 * @return the field name of this ddm field
	 */
	@Override
	public String getFieldName() {
		return model.getFieldName();
	}

	/**
	 * Returns the field type of this ddm field.
	 *
	 * @return the field type of this ddm field
	 */
	@Override
	public String getFieldType() {
		return model.getFieldType();
	}

	/**
	 * Returns the instance ID of this ddm field.
	 *
	 * @return the instance ID of this ddm field
	 */
	@Override
	public String getInstanceId() {
		return model.getInstanceId();
	}

	/**
	 * Returns the localizable of this ddm field.
	 *
	 * @return the localizable of this ddm field
	 */
	@Override
	public boolean getLocalizable() {
		return model.getLocalizable();
	}

	/**
	 * Returns the mvcc version of this ddm field.
	 *
	 * @return the mvcc version of this ddm field
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent field ID of this ddm field.
	 *
	 * @return the parent field ID of this ddm field
	 */
	@Override
	public long getParentFieldId() {
		return model.getParentFieldId();
	}

	/**
	 * Returns the primary key of this ddm field.
	 *
	 * @return the primary key of this ddm field
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this ddm field.
	 *
	 * @return the priority of this ddm field
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the storage ID of this ddm field.
	 *
	 * @return the storage ID of this ddm field
	 */
	@Override
	public long getStorageId() {
		return model.getStorageId();
	}

	/**
	 * Returns the structure version ID of this ddm field.
	 *
	 * @return the structure version ID of this ddm field
	 */
	@Override
	public long getStructureVersionId() {
		return model.getStructureVersionId();
	}

	/**
	 * Returns <code>true</code> if this ddm field is localizable.
	 *
	 * @return <code>true</code> if this ddm field is localizable; <code>false</code> otherwise
	 */
	@Override
	public boolean isLocalizable() {
		return model.isLocalizable();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ddm field.
	 *
	 * @param companyId the company ID of this ddm field
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this ddm field.
	 *
	 * @param ctCollectionId the ct collection ID of this ddm field
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the field ID of this ddm field.
	 *
	 * @param fieldId the field ID of this ddm field
	 */
	@Override
	public void setFieldId(long fieldId) {
		model.setFieldId(fieldId);
	}

	/**
	 * Sets the field name of this ddm field.
	 *
	 * @param fieldName the field name of this ddm field
	 */
	@Override
	public void setFieldName(String fieldName) {
		model.setFieldName(fieldName);
	}

	/**
	 * Sets the field type of this ddm field.
	 *
	 * @param fieldType the field type of this ddm field
	 */
	@Override
	public void setFieldType(String fieldType) {
		model.setFieldType(fieldType);
	}

	/**
	 * Sets the instance ID of this ddm field.
	 *
	 * @param instanceId the instance ID of this ddm field
	 */
	@Override
	public void setInstanceId(String instanceId) {
		model.setInstanceId(instanceId);
	}

	/**
	 * Sets whether this ddm field is localizable.
	 *
	 * @param localizable the localizable of this ddm field
	 */
	@Override
	public void setLocalizable(boolean localizable) {
		model.setLocalizable(localizable);
	}

	/**
	 * Sets the mvcc version of this ddm field.
	 *
	 * @param mvccVersion the mvcc version of this ddm field
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent field ID of this ddm field.
	 *
	 * @param parentFieldId the parent field ID of this ddm field
	 */
	@Override
	public void setParentFieldId(long parentFieldId) {
		model.setParentFieldId(parentFieldId);
	}

	/**
	 * Sets the primary key of this ddm field.
	 *
	 * @param primaryKey the primary key of this ddm field
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this ddm field.
	 *
	 * @param priority the priority of this ddm field
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the storage ID of this ddm field.
	 *
	 * @param storageId the storage ID of this ddm field
	 */
	@Override
	public void setStorageId(long storageId) {
		model.setStorageId(storageId);
	}

	/**
	 * Sets the structure version ID of this ddm field.
	 *
	 * @param structureVersionId the structure version ID of this ddm field
	 */
	@Override
	public void setStructureVersionId(long structureVersionId) {
		model.setStructureVersionId(structureVersionId);
	}

	@Override
	public Map<String, Function<DDMField, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DDMField, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected DDMFieldWrapper wrap(DDMField ddmField) {
		return new DDMFieldWrapper(ddmField);
	}

}
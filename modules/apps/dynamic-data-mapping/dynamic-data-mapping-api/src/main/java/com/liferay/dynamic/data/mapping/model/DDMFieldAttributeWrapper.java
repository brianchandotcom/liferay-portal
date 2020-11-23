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
 * This class is a wrapper for {@link DDMFieldAttribute}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFieldAttribute
 * @generated
 */
public class DDMFieldAttributeWrapper
	extends BaseModelWrapper<DDMFieldAttribute>
	implements DDMFieldAttribute, ModelWrapper<DDMFieldAttribute> {

	public DDMFieldAttributeWrapper(DDMFieldAttribute ddmFieldAttribute) {
		super(ddmFieldAttribute);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("fieldAttributeId", getFieldAttributeId());
		attributes.put("companyId", getCompanyId());
		attributes.put("fieldId", getFieldId());
		attributes.put("storageId", getStorageId());
		attributes.put("languageId", getLanguageId());
		attributes.put("attributeName", getAttributeName());
		attributes.put("smallAttributeValue", getSmallAttributeValue());
		attributes.put("largeAttributeValue", getLargeAttributeValue());

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

		Long fieldAttributeId = (Long)attributes.get("fieldAttributeId");

		if (fieldAttributeId != null) {
			setFieldAttributeId(fieldAttributeId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long fieldId = (Long)attributes.get("fieldId");

		if (fieldId != null) {
			setFieldId(fieldId);
		}

		Long storageId = (Long)attributes.get("storageId");

		if (storageId != null) {
			setStorageId(storageId);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String attributeName = (String)attributes.get("attributeName");

		if (attributeName != null) {
			setAttributeName(attributeName);
		}

		String smallAttributeValue = (String)attributes.get(
			"smallAttributeValue");

		if (smallAttributeValue != null) {
			setSmallAttributeValue(smallAttributeValue);
		}

		String largeAttributeValue = (String)attributes.get(
			"largeAttributeValue");

		if (largeAttributeValue != null) {
			setLargeAttributeValue(largeAttributeValue);
		}
	}

	/**
	 * Returns the attribute name of this ddm field attribute.
	 *
	 * @return the attribute name of this ddm field attribute
	 */
	@Override
	public String getAttributeName() {
		return model.getAttributeName();
	}

	@Override
	public String getAttributeValue() {
		return model.getAttributeValue();
	}

	/**
	 * Returns the company ID of this ddm field attribute.
	 *
	 * @return the company ID of this ddm field attribute
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this ddm field attribute.
	 *
	 * @return the ct collection ID of this ddm field attribute
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the field attribute ID of this ddm field attribute.
	 *
	 * @return the field attribute ID of this ddm field attribute
	 */
	@Override
	public long getFieldAttributeId() {
		return model.getFieldAttributeId();
	}

	/**
	 * Returns the field ID of this ddm field attribute.
	 *
	 * @return the field ID of this ddm field attribute
	 */
	@Override
	public long getFieldId() {
		return model.getFieldId();
	}

	/**
	 * Returns the language ID of this ddm field attribute.
	 *
	 * @return the language ID of this ddm field attribute
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the large attribute value of this ddm field attribute.
	 *
	 * @return the large attribute value of this ddm field attribute
	 */
	@Override
	public String getLargeAttributeValue() {
		return model.getLargeAttributeValue();
	}

	/**
	 * Returns the mvcc version of this ddm field attribute.
	 *
	 * @return the mvcc version of this ddm field attribute
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ddm field attribute.
	 *
	 * @return the primary key of this ddm field attribute
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the small attribute value of this ddm field attribute.
	 *
	 * @return the small attribute value of this ddm field attribute
	 */
	@Override
	public String getSmallAttributeValue() {
		return model.getSmallAttributeValue();
	}

	/**
	 * Returns the storage ID of this ddm field attribute.
	 *
	 * @return the storage ID of this ddm field attribute
	 */
	@Override
	public long getStorageId() {
		return model.getStorageId();
	}

	/**
	 * Sets the attribute name of this ddm field attribute.
	 *
	 * @param attributeName the attribute name of this ddm field attribute
	 */
	@Override
	public void setAttributeName(String attributeName) {
		model.setAttributeName(attributeName);
	}

	@Override
	public void setAttributeValue(String value) {
		model.setAttributeValue(value);
	}

	/**
	 * Sets the company ID of this ddm field attribute.
	 *
	 * @param companyId the company ID of this ddm field attribute
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this ddm field attribute.
	 *
	 * @param ctCollectionId the ct collection ID of this ddm field attribute
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the field attribute ID of this ddm field attribute.
	 *
	 * @param fieldAttributeId the field attribute ID of this ddm field attribute
	 */
	@Override
	public void setFieldAttributeId(long fieldAttributeId) {
		model.setFieldAttributeId(fieldAttributeId);
	}

	/**
	 * Sets the field ID of this ddm field attribute.
	 *
	 * @param fieldId the field ID of this ddm field attribute
	 */
	@Override
	public void setFieldId(long fieldId) {
		model.setFieldId(fieldId);
	}

	/**
	 * Sets the language ID of this ddm field attribute.
	 *
	 * @param languageId the language ID of this ddm field attribute
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the large attribute value of this ddm field attribute.
	 *
	 * @param largeAttributeValue the large attribute value of this ddm field attribute
	 */
	@Override
	public void setLargeAttributeValue(String largeAttributeValue) {
		model.setLargeAttributeValue(largeAttributeValue);
	}

	/**
	 * Sets the mvcc version of this ddm field attribute.
	 *
	 * @param mvccVersion the mvcc version of this ddm field attribute
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ddm field attribute.
	 *
	 * @param primaryKey the primary key of this ddm field attribute
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the small attribute value of this ddm field attribute.
	 *
	 * @param smallAttributeValue the small attribute value of this ddm field attribute
	 */
	@Override
	public void setSmallAttributeValue(String smallAttributeValue) {
		model.setSmallAttributeValue(smallAttributeValue);
	}

	/**
	 * Sets the storage ID of this ddm field attribute.
	 *
	 * @param storageId the storage ID of this ddm field attribute
	 */
	@Override
	public void setStorageId(long storageId) {
		model.setStorageId(storageId);
	}

	@Override
	public Map<String, Function<DDMFieldAttribute, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DDMFieldAttribute, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected DDMFieldAttributeWrapper wrap(
		DDMFieldAttribute ddmFieldAttribute) {

		return new DDMFieldAttributeWrapper(ddmFieldAttribute);
	}

}
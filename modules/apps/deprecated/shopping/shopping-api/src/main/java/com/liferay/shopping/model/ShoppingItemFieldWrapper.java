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

package com.liferay.shopping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link ShoppingItemField}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemField
 * @generated
 */
@ProviderType
public class ShoppingItemFieldWrapper implements ShoppingItemField,
	ModelWrapper<ShoppingItemField> {
	public ShoppingItemFieldWrapper(ShoppingItemField shoppingItemField) {
		_shoppingItemField = shoppingItemField;
	}

	@Override
	public Class<?> getModelClass() {
		return ShoppingItemField.class;
	}

	@Override
	public String getModelClassName() {
		return ShoppingItemField.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("itemFieldId", getItemFieldId());
		attributes.put("companyId", getCompanyId());
		attributes.put("itemId", getItemId());
		attributes.put("name", getName());
		attributes.put("values", getValues());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long itemFieldId = (Long)attributes.get("itemFieldId");

		if (itemFieldId != null) {
			setItemFieldId(itemFieldId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long itemId = (Long)attributes.get("itemId");

		if (itemId != null) {
			setItemId(itemId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String values = (String)attributes.get("values");

		if (values != null) {
			setValues(values);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public ShoppingItemField toEscapedModel() {
		return new ShoppingItemFieldWrapper(_shoppingItemField.toEscapedModel());
	}

	@Override
	public ShoppingItemField toUnescapedModel() {
		return new ShoppingItemFieldWrapper(_shoppingItemField.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _shoppingItemField.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _shoppingItemField.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _shoppingItemField.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _shoppingItemField.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ShoppingItemField> toCacheModel() {
		return _shoppingItemField.toCacheModel();
	}

	@Override
	public int compareTo(ShoppingItemField shoppingItemField) {
		return _shoppingItemField.compareTo(shoppingItemField);
	}

	@Override
	public int hashCode() {
		return _shoppingItemField.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _shoppingItemField.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new ShoppingItemFieldWrapper((ShoppingItemField)_shoppingItemField.clone());
	}

	/**
	* Returns the description of this shopping item field.
	*
	* @return the description of this shopping item field
	*/
	@Override
	public java.lang.String getDescription() {
		return _shoppingItemField.getDescription();
	}

	/**
	* Returns the name of this shopping item field.
	*
	* @return the name of this shopping item field
	*/
	@Override
	public java.lang.String getName() {
		return _shoppingItemField.getName();
	}

	/**
	* Returns the values of this shopping item field.
	*
	* @return the values of this shopping item field
	*/
	@Override
	public java.lang.String getValues() {
		return _shoppingItemField.getValues();
	}

	@Override
	public java.lang.String toString() {
		return _shoppingItemField.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _shoppingItemField.toXmlString();
	}

	@Override
	public java.lang.String[] getValuesArray() {
		return _shoppingItemField.getValuesArray();
	}

	/**
	* Returns the company ID of this shopping item field.
	*
	* @return the company ID of this shopping item field
	*/
	@Override
	public long getCompanyId() {
		return _shoppingItemField.getCompanyId();
	}

	/**
	* Returns the item field ID of this shopping item field.
	*
	* @return the item field ID of this shopping item field
	*/
	@Override
	public long getItemFieldId() {
		return _shoppingItemField.getItemFieldId();
	}

	/**
	* Returns the item ID of this shopping item field.
	*
	* @return the item ID of this shopping item field
	*/
	@Override
	public long getItemId() {
		return _shoppingItemField.getItemId();
	}

	/**
	* Returns the primary key of this shopping item field.
	*
	* @return the primary key of this shopping item field
	*/
	@Override
	public long getPrimaryKey() {
		return _shoppingItemField.getPrimaryKey();
	}

	@Override
	public void persist() {
		_shoppingItemField.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shoppingItemField.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this shopping item field.
	*
	* @param companyId the company ID of this shopping item field
	*/
	@Override
	public void setCompanyId(long companyId) {
		_shoppingItemField.setCompanyId(companyId);
	}

	/**
	* Sets the description of this shopping item field.
	*
	* @param description the description of this shopping item field
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_shoppingItemField.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_shoppingItemField.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_shoppingItemField.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_shoppingItemField.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the item field ID of this shopping item field.
	*
	* @param itemFieldId the item field ID of this shopping item field
	*/
	@Override
	public void setItemFieldId(long itemFieldId) {
		_shoppingItemField.setItemFieldId(itemFieldId);
	}

	/**
	* Sets the item ID of this shopping item field.
	*
	* @param itemId the item ID of this shopping item field
	*/
	@Override
	public void setItemId(long itemId) {
		_shoppingItemField.setItemId(itemId);
	}

	/**
	* Sets the name of this shopping item field.
	*
	* @param name the name of this shopping item field
	*/
	@Override
	public void setName(java.lang.String name) {
		_shoppingItemField.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_shoppingItemField.setNew(n);
	}

	/**
	* Sets the primary key of this shopping item field.
	*
	* @param primaryKey the primary key of this shopping item field
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_shoppingItemField.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_shoppingItemField.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the values of this shopping item field.
	*
	* @param values the values of this shopping item field
	*/
	@Override
	public void setValues(java.lang.String values) {
		_shoppingItemField.setValues(values);
	}

	@Override
	public void setValuesArray(java.lang.String[] valuesArray) {
		_shoppingItemField.setValuesArray(valuesArray);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShoppingItemFieldWrapper)) {
			return false;
		}

		ShoppingItemFieldWrapper shoppingItemFieldWrapper = (ShoppingItemFieldWrapper)obj;

		if (Objects.equals(_shoppingItemField,
					shoppingItemFieldWrapper._shoppingItemField)) {
			return true;
		}

		return false;
	}

	@Override
	public ShoppingItemField getWrappedModel() {
		return _shoppingItemField;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _shoppingItemField.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _shoppingItemField.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_shoppingItemField.resetOriginalValues();
	}

	private final ShoppingItemField _shoppingItemField;
}
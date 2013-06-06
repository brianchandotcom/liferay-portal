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

package com.liferay.portlet.shopping.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ShoppingItemField}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemField
 * @generated
 */
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

	/**
	* Returns the primary key of this shopping item field.
	*
	* @return the primary key of this shopping item field
	*/
	@Override
	public long getPrimaryKey() {
		return _shoppingItemField.getPrimaryKey();
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
	* Sets the item field ID of this shopping item field.
	*
	* @param itemFieldId the item field ID of this shopping item field
	*/
	@Override
	public void setItemFieldId(long itemFieldId) {
		_shoppingItemField.setItemFieldId(itemFieldId);
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
	* Sets the item ID of this shopping item field.
	*
	* @param itemId the item ID of this shopping item field
	*/
	@Override
	public void setItemId(long itemId) {
		_shoppingItemField.setItemId(itemId);
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
	* Sets the name of this shopping item field.
	*
	* @param name the name of this shopping item field
	*/
	@Override
	public void setName(java.lang.String name) {
		_shoppingItemField.setName(name);
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

	/**
	* Sets the values of this shopping item field.
	*
	* @param values the values of this shopping item field
	*/
	@Override
	public void setValues(java.lang.String values) {
		_shoppingItemField.setValues(values);
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
	* Sets the description of this shopping item field.
	*
	* @param description the description of this shopping item field
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_shoppingItemField.setDescription(description);
	}

	@Override
	public boolean isNew() {
		return _shoppingItemField.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_shoppingItemField.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _shoppingItemField.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shoppingItemField.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _shoppingItemField.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingItemField.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_shoppingItemField.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingItemField.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_shoppingItemField.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_shoppingItemField.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingItemField.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ShoppingItemFieldWrapper((ShoppingItemField)_shoppingItemField.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField) {
		return _shoppingItemField.compareTo(shoppingItemField);
	}

	@Override
	public int hashCode() {
		return _shoppingItemField.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.shopping.model.ShoppingItemField> toCacheModel() {
		return _shoppingItemField.toCacheModel();
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingItemField toEscapedModel() {
		return new ShoppingItemFieldWrapper(_shoppingItemField.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.shopping.model.ShoppingItemField toUnescapedModel() {
		return new ShoppingItemFieldWrapper(_shoppingItemField.toUnescapedModel());
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
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingItemField.persist();
	}

	@Override
	public java.lang.String[] getValuesArray() {
		return _shoppingItemField.getValuesArray();
	}

	@Override
	public void setValuesArray(java.lang.String[] valuesArray) {
		_shoppingItemField.setValuesArray(valuesArray);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ShoppingItemField getWrappedShoppingItemField() {
		return _shoppingItemField;
	}

	@Override
	public ShoppingItemField getWrappedModel() {
		return _shoppingItemField;
	}

	@Override
	public void resetOriginalValues() {
		_shoppingItemField.resetOriginalValues();
	}

	private ShoppingItemField _shoppingItemField;
}
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

package com.liferay.portal.model;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ListType}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ListType
 * @generated
 */
public class ListTypeWrapper implements ListType, ModelWrapper<ListType> {
	public ListTypeWrapper(ListType listType) {
		_listType = listType;
	}

	@Override
	public Class<?> getModelClass() {
		return ListType.class;
	}

	@Override
	public String getModelClassName() {
		return ListType.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("listTypeId", getListTypeId());
		attributes.put("name", getName());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Integer listTypeId = (Integer)attributes.get("listTypeId");

		if (listTypeId != null) {
			setListTypeId(listTypeId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	/**
	* Returns the primary key of this list type.
	*
	* @return the primary key of this list type
	*/
	@Override
	public int getPrimaryKey() {
		return _listType.getPrimaryKey();
	}

	/**
	* Sets the primary key of this list type.
	*
	* @param primaryKey the primary key of this list type
	*/
	@Override
	public void setPrimaryKey(int primaryKey) {
		_listType.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the list type ID of this list type.
	*
	* @return the list type ID of this list type
	*/
	@Override
	public int getListTypeId() {
		return _listType.getListTypeId();
	}

	/**
	* Sets the list type ID of this list type.
	*
	* @param listTypeId the list type ID of this list type
	*/
	@Override
	public void setListTypeId(int listTypeId) {
		_listType.setListTypeId(listTypeId);
	}

	/**
	* Returns the name of this list type.
	*
	* @return the name of this list type
	*/
	@Override
	public java.lang.String getName() {
		return _listType.getName();
	}

	/**
	* Sets the name of this list type.
	*
	* @param name the name of this list type
	*/
	@Override
	public void setName(java.lang.String name) {
		_listType.setName(name);
	}

	/**
	* Returns the type of this list type.
	*
	* @return the type of this list type
	*/
	@Override
	public java.lang.String getType() {
		return _listType.getType();
	}

	/**
	* Sets the type of this list type.
	*
	* @param type the type of this list type
	*/
	@Override
	public void setType(java.lang.String type) {
		_listType.setType(type);
	}

	@Override
	public boolean isNew() {
		return _listType.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_listType.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _listType.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_listType.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _listType.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _listType.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_listType.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _listType.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_listType.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_listType.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_listType.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ListTypeWrapper((ListType)_listType.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.ListType listType) {
		return _listType.compareTo(listType);
	}

	@Override
	public int hashCode() {
		return _listType.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.ListType> toCacheModel() {
		return _listType.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.ListType toEscapedModel() {
		return new ListTypeWrapper(_listType.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.ListType toUnescapedModel() {
		return new ListTypeWrapper(_listType.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _listType.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _listType.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ListTypeWrapper)) {
			return false;
		}

		ListTypeWrapper listTypeWrapper = (ListTypeWrapper)obj;

		if (Validator.equals(_listType, listTypeWrapper._listType)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ListType getWrappedListType() {
		return _listType;
	}

	@Override
	public ListType getWrappedModel() {
		return _listType;
	}

	@Override
	public void resetOriginalValues() {
		_listType.resetOriginalValues();
	}

	private ListType _listType;
}
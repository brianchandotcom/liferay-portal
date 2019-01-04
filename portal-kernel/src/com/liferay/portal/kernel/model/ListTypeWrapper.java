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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
@ProviderType
public class ListTypeWrapper extends BaseModelWrapper<ListType>
	implements ListType, ModelWrapper<ListType> {
	public ListTypeWrapper(ListType listType) {
		super(listType);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("listTypeId", getListTypeId());
		attributes.put("name", getName());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long listTypeId = (Long)attributes.get("listTypeId");

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
	* Returns the list type ID of this list type.
	*
	* @return the list type ID of this list type
	*/
	@Override
	public long getListTypeId() {
		return model.getListTypeId();
	}

	/**
	* Returns the mvcc version of this list type.
	*
	* @return the mvcc version of this list type
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the name of this list type.
	*
	* @return the name of this list type
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the primary key of this list type.
	*
	* @return the primary key of this list type
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the type of this list type.
	*
	* @return the type of this list type
	*/
	@Override
	public String getType() {
		return model.getType();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the list type ID of this list type.
	*
	* @param listTypeId the list type ID of this list type
	*/
	@Override
	public void setListTypeId(long listTypeId) {
		model.setListTypeId(listTypeId);
	}

	/**
	* Sets the mvcc version of this list type.
	*
	* @param mvccVersion the mvcc version of this list type
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this list type.
	*
	* @param name the name of this list type
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the primary key of this list type.
	*
	* @param primaryKey the primary key of this list type
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the type of this list type.
	*
	* @param type the type of this list type
	*/
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	@Override
	protected ListTypeWrapper wrap(ListType listType) {
		return new ListTypeWrapper(listType);
	}
}
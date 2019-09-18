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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.sql.Blob;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link EagerBlobEntity}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntity
 * @generated
 */
public class EagerBlobEntityWrapper
	implements EagerBlobEntity, ModelWrapper<EagerBlobEntity> {

	public EagerBlobEntityWrapper(EagerBlobEntity eagerBlobEntity) {
		_eagerBlobEntity = eagerBlobEntity;
	}

	@Override
	public Class<?> getModelClass() {
		return EagerBlobEntity.class;
	}

	@Override
	public String getModelClassName() {
		return EagerBlobEntity.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("eagerBlobEntityId", getEagerBlobEntityId());
		attributes.put("groupId", getGroupId());
		attributes.put("fieldBlob", getFieldBlob());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long eagerBlobEntityId = (Long)attributes.get("eagerBlobEntityId");

		if (eagerBlobEntityId != null) {
			setEagerBlobEntityId(eagerBlobEntityId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Blob fieldBlob = (Blob)attributes.get("fieldBlob");

		if (fieldBlob != null) {
			setFieldBlob(fieldBlob);
		}
	}

	@Override
	public Object clone() {
		return new EagerBlobEntityWrapper(
			(EagerBlobEntity)_eagerBlobEntity.clone());
	}

	@Override
	public int compareTo(EagerBlobEntity eagerBlobEntity) {
		return _eagerBlobEntity.compareTo(eagerBlobEntity);
	}

	/**
	 * Returns the eager blob entity ID of this eager blob entity.
	 *
	 * @return the eager blob entity ID of this eager blob entity
	 */
	@Override
	public long getEagerBlobEntityId() {
		return _eagerBlobEntity.getEagerBlobEntityId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _eagerBlobEntity.getExpandoBridge();
	}

	/**
	 * Returns the field blob of this eager blob entity.
	 *
	 * @return the field blob of this eager blob entity
	 */
	@Override
	public Blob getFieldBlob() {
		return _eagerBlobEntity.getFieldBlob();
	}

	/**
	 * Returns the group ID of this eager blob entity.
	 *
	 * @return the group ID of this eager blob entity
	 */
	@Override
	public long getGroupId() {
		return _eagerBlobEntity.getGroupId();
	}

	/**
	 * Returns the primary key of this eager blob entity.
	 *
	 * @return the primary key of this eager blob entity
	 */
	@Override
	public long getPrimaryKey() {
		return _eagerBlobEntity.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _eagerBlobEntity.getPrimaryKeyObj();
	}

	/**
	 * Returns the uuid of this eager blob entity.
	 *
	 * @return the uuid of this eager blob entity
	 */
	@Override
	public String getUuid() {
		return _eagerBlobEntity.getUuid();
	}

	@Override
	public int hashCode() {
		return _eagerBlobEntity.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _eagerBlobEntity.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _eagerBlobEntity.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _eagerBlobEntity.isNew();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a eager blob entity model instance should use the <code>EagerBlobEntity</code> interface instead.
	 */
	@Override
	public void persist() {
		_eagerBlobEntity.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_eagerBlobEntity.setCachedModel(cachedModel);
	}

	/**
	 * Sets the eager blob entity ID of this eager blob entity.
	 *
	 * @param eagerBlobEntityId the eager blob entity ID of this eager blob entity
	 */
	@Override
	public void setEagerBlobEntityId(long eagerBlobEntityId) {
		_eagerBlobEntity.setEagerBlobEntityId(eagerBlobEntityId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_eagerBlobEntity.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_eagerBlobEntity.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_eagerBlobEntity.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the field blob of this eager blob entity.
	 *
	 * @param fieldBlob the field blob of this eager blob entity
	 */
	@Override
	public void setFieldBlob(Blob fieldBlob) {
		_eagerBlobEntity.setFieldBlob(fieldBlob);
	}

	/**
	 * Sets the group ID of this eager blob entity.
	 *
	 * @param groupId the group ID of this eager blob entity
	 */
	@Override
	public void setGroupId(long groupId) {
		_eagerBlobEntity.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_eagerBlobEntity.setNew(n);
	}

	/**
	 * Sets the primary key of this eager blob entity.
	 *
	 * @param primaryKey the primary key of this eager blob entity
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_eagerBlobEntity.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_eagerBlobEntity.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the uuid of this eager blob entity.
	 *
	 * @param uuid the uuid of this eager blob entity
	 */
	@Override
	public void setUuid(String uuid) {
		_eagerBlobEntity.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<EagerBlobEntity>
		toCacheModel() {

		return _eagerBlobEntity.toCacheModel();
	}

	@Override
	public EagerBlobEntity toEscapedModel() {
		return new EagerBlobEntityWrapper(_eagerBlobEntity.toEscapedModel());
	}

	@Override
	public String toString() {
		return _eagerBlobEntity.toString();
	}

	@Override
	public EagerBlobEntity toUnescapedModel() {
		return new EagerBlobEntityWrapper(_eagerBlobEntity.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _eagerBlobEntity.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EagerBlobEntityWrapper)) {
			return false;
		}

		EagerBlobEntityWrapper eagerBlobEntityWrapper =
			(EagerBlobEntityWrapper)obj;

		if (Objects.equals(
				_eagerBlobEntity, eagerBlobEntityWrapper._eagerBlobEntity)) {

			return true;
		}

		return false;
	}

	@Override
	public EagerBlobEntity getWrappedModel() {
		return _eagerBlobEntity;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _eagerBlobEntity.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _eagerBlobEntity.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_eagerBlobEntity.resetOriginalValues();
	}

	private final EagerBlobEntity _eagerBlobEntity;

}
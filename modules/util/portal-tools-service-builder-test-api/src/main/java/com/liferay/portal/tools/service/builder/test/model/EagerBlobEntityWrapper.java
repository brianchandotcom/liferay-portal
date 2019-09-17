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
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.sql.Blob;

import java.util.Date;
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
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("field1", getField1());
		attributes.put("field2", isField2());
		attributes.put("field3", getField3());
		attributes.put("field4", getField4());
		attributes.put("field5", getField5());
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

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String field1 = (String)attributes.get("field1");

		if (field1 != null) {
			setField1(field1);
		}

		Boolean field2 = (Boolean)attributes.get("field2");

		if (field2 != null) {
			setField2(field2);
		}

		Integer field3 = (Integer)attributes.get("field3");

		if (field3 != null) {
			setField3(field3);
		}

		Date field4 = (Date)attributes.get("field4");

		if (field4 != null) {
			setField4(field4);
		}

		String field5 = (String)attributes.get("field5");

		if (field5 != null) {
			setField5(field5);
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
	 * Returns the company ID of this eager blob entity.
	 *
	 * @return the company ID of this eager blob entity
	 */
	@Override
	public long getCompanyId() {
		return _eagerBlobEntity.getCompanyId();
	}

	/**
	 * Returns the create date of this eager blob entity.
	 *
	 * @return the create date of this eager blob entity
	 */
	@Override
	public Date getCreateDate() {
		return _eagerBlobEntity.getCreateDate();
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
	 * Returns the field1 of this eager blob entity.
	 *
	 * @return the field1 of this eager blob entity
	 */
	@Override
	public String getField1() {
		return _eagerBlobEntity.getField1();
	}

	/**
	 * Returns the field2 of this eager blob entity.
	 *
	 * @return the field2 of this eager blob entity
	 */
	@Override
	public boolean getField2() {
		return _eagerBlobEntity.getField2();
	}

	/**
	 * Returns the field3 of this eager blob entity.
	 *
	 * @return the field3 of this eager blob entity
	 */
	@Override
	public int getField3() {
		return _eagerBlobEntity.getField3();
	}

	/**
	 * Returns the field4 of this eager blob entity.
	 *
	 * @return the field4 of this eager blob entity
	 */
	@Override
	public Date getField4() {
		return _eagerBlobEntity.getField4();
	}

	/**
	 * Returns the field5 of this eager blob entity.
	 *
	 * @return the field5 of this eager blob entity
	 */
	@Override
	public String getField5() {
		return _eagerBlobEntity.getField5();
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
	 * Returns the modified date of this eager blob entity.
	 *
	 * @return the modified date of this eager blob entity
	 */
	@Override
	public Date getModifiedDate() {
		return _eagerBlobEntity.getModifiedDate();
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
	 * Returns the user ID of this eager blob entity.
	 *
	 * @return the user ID of this eager blob entity
	 */
	@Override
	public long getUserId() {
		return _eagerBlobEntity.getUserId();
	}

	/**
	 * Returns the user name of this eager blob entity.
	 *
	 * @return the user name of this eager blob entity
	 */
	@Override
	public String getUserName() {
		return _eagerBlobEntity.getUserName();
	}

	/**
	 * Returns the user uuid of this eager blob entity.
	 *
	 * @return the user uuid of this eager blob entity
	 */
	@Override
	public String getUserUuid() {
		return _eagerBlobEntity.getUserUuid();
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

	/**
	 * Returns <code>true</code> if this eager blob entity is field2.
	 *
	 * @return <code>true</code> if this eager blob entity is field2; <code>false</code> otherwise
	 */
	@Override
	public boolean isField2() {
		return _eagerBlobEntity.isField2();
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
	 * Sets the company ID of this eager blob entity.
	 *
	 * @param companyId the company ID of this eager blob entity
	 */
	@Override
	public void setCompanyId(long companyId) {
		_eagerBlobEntity.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this eager blob entity.
	 *
	 * @param createDate the create date of this eager blob entity
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_eagerBlobEntity.setCreateDate(createDate);
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
	 * Sets the field1 of this eager blob entity.
	 *
	 * @param field1 the field1 of this eager blob entity
	 */
	@Override
	public void setField1(String field1) {
		_eagerBlobEntity.setField1(field1);
	}

	/**
	 * Sets whether this eager blob entity is field2.
	 *
	 * @param field2 the field2 of this eager blob entity
	 */
	@Override
	public void setField2(boolean field2) {
		_eagerBlobEntity.setField2(field2);
	}

	/**
	 * Sets the field3 of this eager blob entity.
	 *
	 * @param field3 the field3 of this eager blob entity
	 */
	@Override
	public void setField3(int field3) {
		_eagerBlobEntity.setField3(field3);
	}

	/**
	 * Sets the field4 of this eager blob entity.
	 *
	 * @param field4 the field4 of this eager blob entity
	 */
	@Override
	public void setField4(Date field4) {
		_eagerBlobEntity.setField4(field4);
	}

	/**
	 * Sets the field5 of this eager blob entity.
	 *
	 * @param field5 the field5 of this eager blob entity
	 */
	@Override
	public void setField5(String field5) {
		_eagerBlobEntity.setField5(field5);
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

	/**
	 * Sets the modified date of this eager blob entity.
	 *
	 * @param modifiedDate the modified date of this eager blob entity
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_eagerBlobEntity.setModifiedDate(modifiedDate);
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
	 * Sets the user ID of this eager blob entity.
	 *
	 * @param userId the user ID of this eager blob entity
	 */
	@Override
	public void setUserId(long userId) {
		_eagerBlobEntity.setUserId(userId);
	}

	/**
	 * Sets the user name of this eager blob entity.
	 *
	 * @param userName the user name of this eager blob entity
	 */
	@Override
	public void setUserName(String userName) {
		_eagerBlobEntity.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this eager blob entity.
	 *
	 * @param userUuid the user uuid of this eager blob entity
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_eagerBlobEntity.setUserUuid(userUuid);
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
	public StagedModelType getStagedModelType() {
		return _eagerBlobEntity.getStagedModelType();
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
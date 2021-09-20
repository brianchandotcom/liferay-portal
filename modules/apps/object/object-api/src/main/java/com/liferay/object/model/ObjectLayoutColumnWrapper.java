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

package com.liferay.object.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ObjectLayoutColumn}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutColumn
 * @generated
 */
public class ObjectLayoutColumnWrapper
	extends BaseModelWrapper<ObjectLayoutColumn>
	implements ModelWrapper<ObjectLayoutColumn>, ObjectLayoutColumn {

	public ObjectLayoutColumnWrapper(ObjectLayoutColumn objectLayoutColumn) {
		super(objectLayoutColumn);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectLayoutColumnId", getObjectLayoutColumnId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectFieldId", getObjectFieldId());
		attributes.put("objectLayoutRowId", getObjectLayoutRowId());
		attributes.put("priority", getPriority());
		attributes.put("size", getSize());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long objectLayoutColumnId = (Long)attributes.get(
			"objectLayoutColumnId");

		if (objectLayoutColumnId != null) {
			setObjectLayoutColumnId(objectLayoutColumnId);
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

		Long objectFieldId = (Long)attributes.get("objectFieldId");

		if (objectFieldId != null) {
			setObjectFieldId(objectFieldId);
		}

		Long objectLayoutRowId = (Long)attributes.get("objectLayoutRowId");

		if (objectLayoutRowId != null) {
			setObjectLayoutRowId(objectLayoutRowId);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Integer size = (Integer)attributes.get("size");

		if (size != null) {
			setSize(size);
		}
	}

	@Override
	public ObjectLayoutColumn cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object layout column.
	 *
	 * @return the company ID of this object layout column
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object layout column.
	 *
	 * @return the create date of this object layout column
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this object layout column.
	 *
	 * @return the modified date of this object layout column
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object layout column.
	 *
	 * @return the mvcc version of this object layout column
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object field ID of this object layout column.
	 *
	 * @return the object field ID of this object layout column
	 */
	@Override
	public long getObjectFieldId() {
		return model.getObjectFieldId();
	}

	/**
	 * Returns the object layout column ID of this object layout column.
	 *
	 * @return the object layout column ID of this object layout column
	 */
	@Override
	public long getObjectLayoutColumnId() {
		return model.getObjectLayoutColumnId();
	}

	/**
	 * Returns the object layout row ID of this object layout column.
	 *
	 * @return the object layout row ID of this object layout column
	 */
	@Override
	public long getObjectLayoutRowId() {
		return model.getObjectLayoutRowId();
	}

	/**
	 * Returns the primary key of this object layout column.
	 *
	 * @return the primary key of this object layout column
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this object layout column.
	 *
	 * @return the priority of this object layout column
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the size of this object layout column.
	 *
	 * @return the size of this object layout column
	 */
	@Override
	public int getSize() {
		return model.getSize();
	}

	/**
	 * Returns the user ID of this object layout column.
	 *
	 * @return the user ID of this object layout column
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object layout column.
	 *
	 * @return the user name of this object layout column
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object layout column.
	 *
	 * @return the user uuid of this object layout column
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object layout column.
	 *
	 * @return the uuid of this object layout column
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Sets the company ID of this object layout column.
	 *
	 * @param companyId the company ID of this object layout column
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object layout column.
	 *
	 * @param createDate the create date of this object layout column
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this object layout column.
	 *
	 * @param modifiedDate the modified date of this object layout column
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object layout column.
	 *
	 * @param mvccVersion the mvcc version of this object layout column
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object field ID of this object layout column.
	 *
	 * @param objectFieldId the object field ID of this object layout column
	 */
	@Override
	public void setObjectFieldId(long objectFieldId) {
		model.setObjectFieldId(objectFieldId);
	}

	/**
	 * Sets the object layout column ID of this object layout column.
	 *
	 * @param objectLayoutColumnId the object layout column ID of this object layout column
	 */
	@Override
	public void setObjectLayoutColumnId(long objectLayoutColumnId) {
		model.setObjectLayoutColumnId(objectLayoutColumnId);
	}

	/**
	 * Sets the object layout row ID of this object layout column.
	 *
	 * @param objectLayoutRowId the object layout row ID of this object layout column
	 */
	@Override
	public void setObjectLayoutRowId(long objectLayoutRowId) {
		model.setObjectLayoutRowId(objectLayoutRowId);
	}

	/**
	 * Sets the primary key of this object layout column.
	 *
	 * @param primaryKey the primary key of this object layout column
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this object layout column.
	 *
	 * @param priority the priority of this object layout column
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the size of this object layout column.
	 *
	 * @param size the size of this object layout column
	 */
	@Override
	public void setSize(int size) {
		model.setSize(size);
	}

	/**
	 * Sets the user ID of this object layout column.
	 *
	 * @param userId the user ID of this object layout column
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object layout column.
	 *
	 * @param userName the user name of this object layout column
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object layout column.
	 *
	 * @param userUuid the user uuid of this object layout column
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object layout column.
	 *
	 * @param uuid the uuid of this object layout column
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ObjectLayoutColumnWrapper wrap(
		ObjectLayoutColumn objectLayoutColumn) {

		return new ObjectLayoutColumnWrapper(objectLayoutColumn);
	}

}
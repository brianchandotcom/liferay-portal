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
 * This class is a wrapper for {@link ObjectFieldSetting}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFieldSetting
 * @generated
 */
public class ObjectFieldSettingWrapper
	extends BaseModelWrapper<ObjectFieldSetting>
	implements ModelWrapper<ObjectFieldSetting>, ObjectFieldSetting {

	public ObjectFieldSettingWrapper(ObjectFieldSetting objectFieldSetting) {
		super(objectFieldSetting);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectFieldSettingId", getObjectFieldSettingId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectFieldId", getObjectFieldId());
		attributes.put("name", getName());
		attributes.put("value", getValue());

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

		Long objectFieldSettingId = (Long)attributes.get(
			"objectFieldSettingId");

		if (objectFieldSettingId != null) {
			setObjectFieldSettingId(objectFieldSettingId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public ObjectFieldSetting cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object field setting.
	 *
	 * @return the company ID of this object field setting
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object field setting.
	 *
	 * @return the create date of this object field setting
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this object field setting.
	 *
	 * @return the modified date of this object field setting
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object field setting.
	 *
	 * @return the mvcc version of this object field setting
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this object field setting.
	 *
	 * @return the name of this object field setting
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the object field ID of this object field setting.
	 *
	 * @return the object field ID of this object field setting
	 */
	@Override
	public long getObjectFieldId() {
		return model.getObjectFieldId();
	}

	/**
	 * Returns the object field setting ID of this object field setting.
	 *
	 * @return the object field setting ID of this object field setting
	 */
	@Override
	public long getObjectFieldSettingId() {
		return model.getObjectFieldSettingId();
	}

	/**
	 * Returns the primary key of this object field setting.
	 *
	 * @return the primary key of this object field setting
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this object field setting.
	 *
	 * @return the user ID of this object field setting
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object field setting.
	 *
	 * @return the user name of this object field setting
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object field setting.
	 *
	 * @return the user uuid of this object field setting
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object field setting.
	 *
	 * @return the uuid of this object field setting
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the value of this object field setting.
	 *
	 * @return the value of this object field setting
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this object field setting.
	 *
	 * @param companyId the company ID of this object field setting
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object field setting.
	 *
	 * @param createDate the create date of this object field setting
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this object field setting.
	 *
	 * @param modifiedDate the modified date of this object field setting
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object field setting.
	 *
	 * @param mvccVersion the mvcc version of this object field setting
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this object field setting.
	 *
	 * @param name the name of this object field setting
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the object field ID of this object field setting.
	 *
	 * @param objectFieldId the object field ID of this object field setting
	 */
	@Override
	public void setObjectFieldId(long objectFieldId) {
		model.setObjectFieldId(objectFieldId);
	}

	/**
	 * Sets the object field setting ID of this object field setting.
	 *
	 * @param objectFieldSettingId the object field setting ID of this object field setting
	 */
	@Override
	public void setObjectFieldSettingId(long objectFieldSettingId) {
		model.setObjectFieldSettingId(objectFieldSettingId);
	}

	/**
	 * Sets the primary key of this object field setting.
	 *
	 * @param primaryKey the primary key of this object field setting
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this object field setting.
	 *
	 * @param userId the user ID of this object field setting
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object field setting.
	 *
	 * @param userName the user name of this object field setting
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object field setting.
	 *
	 * @param userUuid the user uuid of this object field setting
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object field setting.
	 *
	 * @param uuid the uuid of this object field setting
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the value of this object field setting.
	 *
	 * @param value the value of this object field setting
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ObjectFieldSettingWrapper wrap(
		ObjectFieldSetting objectFieldSetting) {

		return new ObjectFieldSettingWrapper(objectFieldSetting);
	}

}
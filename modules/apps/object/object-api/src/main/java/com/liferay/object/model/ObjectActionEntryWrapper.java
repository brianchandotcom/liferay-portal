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
 * This class is a wrapper for {@link ObjectActionEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectActionEntry
 * @generated
 */
public class ObjectActionEntryWrapper
	extends BaseModelWrapper<ObjectActionEntry>
	implements ModelWrapper<ObjectActionEntry>, ObjectActionEntry {

	public ObjectActionEntryWrapper(ObjectActionEntry objectActionEntry) {
		super(objectActionEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectActionEntryId", getObjectActionEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectDefinitionId", getObjectDefinitionId());
		attributes.put("active", isActive());
		attributes.put("triggerName", getTriggerName());
		attributes.put("name", getName());
		attributes.put("settings", getSettings());

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

		Long objectActionEntryId = (Long)attributes.get("objectActionEntryId");

		if (objectActionEntryId != null) {
			setObjectActionEntryId(objectActionEntryId);
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

		Long objectDefinitionId = (Long)attributes.get("objectDefinitionId");

		if (objectDefinitionId != null) {
			setObjectDefinitionId(objectDefinitionId);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String triggerName = (String)attributes.get("triggerName");

		if (triggerName != null) {
			setTriggerName(triggerName);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}
	}

	@Override
	public ObjectActionEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this object action entry.
	 *
	 * @return the active of this object action entry
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the company ID of this object action entry.
	 *
	 * @return the company ID of this object action entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object action entry.
	 *
	 * @return the create date of this object action entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this object action entry.
	 *
	 * @return the modified date of this object action entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object action entry.
	 *
	 * @return the mvcc version of this object action entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this object action entry.
	 *
	 * @return the name of this object action entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the object action entry ID of this object action entry.
	 *
	 * @return the object action entry ID of this object action entry
	 */
	@Override
	public long getObjectActionEntryId() {
		return model.getObjectActionEntryId();
	}

	/**
	 * Returns the object definition ID of this object action entry.
	 *
	 * @return the object definition ID of this object action entry
	 */
	@Override
	public long getObjectDefinitionId() {
		return model.getObjectDefinitionId();
	}

	/**
	 * Returns the primary key of this object action entry.
	 *
	 * @return the primary key of this object action entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the settings of this object action entry.
	 *
	 * @return the settings of this object action entry
	 */
	@Override
	public String getSettings() {
		return model.getSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getSettingsUnicodeProperties() {

		return model.getSettingsUnicodeProperties();
	}

	/**
	 * Returns the trigger name of this object action entry.
	 *
	 * @return the trigger name of this object action entry
	 */
	@Override
	public String getTriggerName() {
		return model.getTriggerName();
	}

	/**
	 * Returns the user ID of this object action entry.
	 *
	 * @return the user ID of this object action entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object action entry.
	 *
	 * @return the user name of this object action entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object action entry.
	 *
	 * @return the user uuid of this object action entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object action entry.
	 *
	 * @return the uuid of this object action entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this object action entry is active.
	 *
	 * @return <code>true</code> if this object action entry is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this object action entry is active.
	 *
	 * @param active the active of this object action entry
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this object action entry.
	 *
	 * @param companyId the company ID of this object action entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object action entry.
	 *
	 * @param createDate the create date of this object action entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this object action entry.
	 *
	 * @param modifiedDate the modified date of this object action entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object action entry.
	 *
	 * @param mvccVersion the mvcc version of this object action entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this object action entry.
	 *
	 * @param name the name of this object action entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the object action entry ID of this object action entry.
	 *
	 * @param objectActionEntryId the object action entry ID of this object action entry
	 */
	@Override
	public void setObjectActionEntryId(long objectActionEntryId) {
		model.setObjectActionEntryId(objectActionEntryId);
	}

	/**
	 * Sets the object definition ID of this object action entry.
	 *
	 * @param objectDefinitionId the object definition ID of this object action entry
	 */
	@Override
	public void setObjectDefinitionId(long objectDefinitionId) {
		model.setObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Sets the primary key of this object action entry.
	 *
	 * @param primaryKey the primary key of this object action entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the settings of this object action entry.
	 *
	 * @param settings the settings of this object action entry
	 */
	@Override
	public void setSettings(String settings) {
		model.setSettings(settings);
	}

	/**
	 * Sets the trigger name of this object action entry.
	 *
	 * @param triggerName the trigger name of this object action entry
	 */
	@Override
	public void setTriggerName(String triggerName) {
		model.setTriggerName(triggerName);
	}

	/**
	 * Sets the user ID of this object action entry.
	 *
	 * @param userId the user ID of this object action entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object action entry.
	 *
	 * @param userName the user name of this object action entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object action entry.
	 *
	 * @param userUuid the user uuid of this object action entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object action entry.
	 *
	 * @param uuid the uuid of this object action entry
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
	protected ObjectActionEntryWrapper wrap(
		ObjectActionEntry objectActionEntry) {

		return new ObjectActionEntryWrapper(objectActionEntry);
	}

}
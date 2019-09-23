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

package com.liferay.trash.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link TrashEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntry
 * @generated
 */
public class TrashEntryWrapper
	extends BaseModelWrapper<TrashEntry>
	implements ModelWrapper<TrashEntry>, TrashEntry {

	public TrashEntryWrapper(TrashEntry trashEntry) {
		super(trashEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("entryId", getEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("systemEventSetKey", getSystemEventSetKey());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long systemEventSetKey = (Long)attributes.get("systemEventSetKey");

		if (systemEventSetKey != null) {
			setSystemEventSetKey(systemEventSetKey);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	 * Returns the fully qualified class name of this trash entry.
	 *
	 * @return the fully qualified class name of this trash entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this trash entry.
	 *
	 * @return the class name ID of this trash entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this trash entry.
	 *
	 * @return the class pk of this trash entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this trash entry.
	 *
	 * @return the company ID of this trash entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this trash entry.
	 *
	 * @return the create date of this trash entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the entry ID of this trash entry.
	 *
	 * @return the entry ID of this trash entry
	 */
	@Override
	public long getEntryId() {
		return model.getEntryId();
	}

	/**
	 * Returns the group ID of this trash entry.
	 *
	 * @return the group ID of this trash entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this trash entry.
	 *
	 * @return the mvcc version of this trash entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this trash entry.
	 *
	 * @return the primary key of this trash entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public TrashEntry getRootEntry() {
		return model.getRootEntry();
	}

	/**
	 * Returns the status of this trash entry.
	 *
	 * @return the status of this trash entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the system event set key of this trash entry.
	 *
	 * @return the system event set key of this trash entry
	 */
	@Override
	public long getSystemEventSetKey() {
		return model.getSystemEventSetKey();
	}

	/**
	 * Returns the type settings of this trash entry.
	 *
	 * @return the type settings of this trash entry
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsProperties() {

		return model.getTypeSettingsProperties();
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		return model.getTypeSettingsProperty(key);
	}

	@Override
	public String getTypeSettingsProperty(String key, String defaultValue) {
		return model.getTypeSettingsProperty(key, defaultValue);
	}

	/**
	 * Returns the user ID of this trash entry.
	 *
	 * @return the user ID of this trash entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this trash entry.
	 *
	 * @return the user name of this trash entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this trash entry.
	 *
	 * @return the user uuid of this trash entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public boolean isTrashEntry(Class<?> clazz, long classPK) {
		return model.isTrashEntry(clazz, classPK);
	}

	@Override
	public boolean isTrashEntry(String className, long classPK) {
		return model.isTrashEntry(className, classPK);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a trash entry model instance should use the <code>TrashEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this trash entry.
	 *
	 * @param classNameId the class name ID of this trash entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this trash entry.
	 *
	 * @param classPK the class pk of this trash entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this trash entry.
	 *
	 * @param companyId the company ID of this trash entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this trash entry.
	 *
	 * @param createDate the create date of this trash entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the entry ID of this trash entry.
	 *
	 * @param entryId the entry ID of this trash entry
	 */
	@Override
	public void setEntryId(long entryId) {
		model.setEntryId(entryId);
	}

	/**
	 * Sets the group ID of this trash entry.
	 *
	 * @param groupId the group ID of this trash entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this trash entry.
	 *
	 * @param mvccVersion the mvcc version of this trash entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this trash entry.
	 *
	 * @param primaryKey the primary key of this trash entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public void setRootEntry(TrashEntry rootEntry) {
		model.setRootEntry(rootEntry);
	}

	/**
	 * Sets the status of this trash entry.
	 *
	 * @param status the status of this trash entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the system event set key of this trash entry.
	 *
	 * @param systemEventSetKey the system event set key of this trash entry
	 */
	@Override
	public void setSystemEventSetKey(long systemEventSetKey) {
		model.setSystemEventSetKey(systemEventSetKey);
	}

	/**
	 * Sets the type settings of this trash entry.
	 *
	 * @param typeSettings the type settings of this trash entry
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsProperties) {

		model.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	 * Sets the user ID of this trash entry.
	 *
	 * @param userId the user ID of this trash entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this trash entry.
	 *
	 * @param userName the user name of this trash entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this trash entry.
	 *
	 * @param userUuid the user uuid of this trash entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected TrashEntryWrapper wrap(TrashEntry trashEntry) {
		return new TrashEntryWrapper(trashEntry);
	}

}
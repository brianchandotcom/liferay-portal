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

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SystemEvent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SystemEvent
 * @generated
 */
public class SystemEventWrapper
	extends BaseModelWrapper<SystemEvent>
	implements ModelWrapper<SystemEvent>, SystemEvent {

	public SystemEventWrapper(SystemEvent systemEvent) {
		super(systemEvent);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("systemEventId", getSystemEventId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("classUuid", getClassUuid());
		attributes.put("referrerClassNameId", getReferrerClassNameId());
		attributes.put("parentSystemEventId", getParentSystemEventId());
		attributes.put("systemEventSetKey", getSystemEventSetKey());
		attributes.put("type", getType());
		attributes.put("extraData", getExtraData());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long systemEventId = (Long)attributes.get("systemEventId");

		if (systemEventId != null) {
			setSystemEventId(systemEventId);
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

		String classUuid = (String)attributes.get("classUuid");

		if (classUuid != null) {
			setClassUuid(classUuid);
		}

		Long referrerClassNameId = (Long)attributes.get("referrerClassNameId");

		if (referrerClassNameId != null) {
			setReferrerClassNameId(referrerClassNameId);
		}

		Long parentSystemEventId = (Long)attributes.get("parentSystemEventId");

		if (parentSystemEventId != null) {
			setParentSystemEventId(parentSystemEventId);
		}

		Long systemEventSetKey = (Long)attributes.get("systemEventSetKey");

		if (systemEventSetKey != null) {
			setSystemEventSetKey(systemEventSetKey);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String extraData = (String)attributes.get("extraData");

		if (extraData != null) {
			setExtraData(extraData);
		}
	}

	/**
	 * Returns the fully qualified class name of this system event.
	 *
	 * @return the fully qualified class name of this system event
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this system event.
	 *
	 * @return the class name ID of this system event
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this system event.
	 *
	 * @return the class pk of this system event
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the class uuid of this system event.
	 *
	 * @return the class uuid of this system event
	 */
	@Override
	public String getClassUuid() {
		return model.getClassUuid();
	}

	/**
	 * Returns the company ID of this system event.
	 *
	 * @return the company ID of this system event
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this system event.
	 *
	 * @return the create date of this system event
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the extra data of this system event.
	 *
	 * @return the extra data of this system event
	 */
	@Override
	public String getExtraData() {
		return model.getExtraData();
	}

	/**
	 * Returns the group ID of this system event.
	 *
	 * @return the group ID of this system event
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this system event.
	 *
	 * @return the mvcc version of this system event
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent system event ID of this system event.
	 *
	 * @return the parent system event ID of this system event
	 */
	@Override
	public long getParentSystemEventId() {
		return model.getParentSystemEventId();
	}

	/**
	 * Returns the primary key of this system event.
	 *
	 * @return the primary key of this system event
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public String getReferrerClassName() {
		return model.getReferrerClassName();
	}

	/**
	 * Returns the referrer class name ID of this system event.
	 *
	 * @return the referrer class name ID of this system event
	 */
	@Override
	public long getReferrerClassNameId() {
		return model.getReferrerClassNameId();
	}

	/**
	 * Returns the system event ID of this system event.
	 *
	 * @return the system event ID of this system event
	 */
	@Override
	public long getSystemEventId() {
		return model.getSystemEventId();
	}

	/**
	 * Returns the system event set key of this system event.
	 *
	 * @return the system event set key of this system event
	 */
	@Override
	public long getSystemEventSetKey() {
		return model.getSystemEventSetKey();
	}

	/**
	 * Returns the type of this system event.
	 *
	 * @return the type of this system event
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this system event.
	 *
	 * @return the user ID of this system event
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this system event.
	 *
	 * @return the user name of this system event
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this system event.
	 *
	 * @return the user uuid of this system event
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a system event model instance should use the <code>SystemEvent</code> interface instead.
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
	 * Sets the class name ID of this system event.
	 *
	 * @param classNameId the class name ID of this system event
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this system event.
	 *
	 * @param classPK the class pk of this system event
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the class uuid of this system event.
	 *
	 * @param classUuid the class uuid of this system event
	 */
	@Override
	public void setClassUuid(String classUuid) {
		model.setClassUuid(classUuid);
	}

	/**
	 * Sets the company ID of this system event.
	 *
	 * @param companyId the company ID of this system event
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this system event.
	 *
	 * @param createDate the create date of this system event
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the extra data of this system event.
	 *
	 * @param extraData the extra data of this system event
	 */
	@Override
	public void setExtraData(String extraData) {
		model.setExtraData(extraData);
	}

	/**
	 * Sets the group ID of this system event.
	 *
	 * @param groupId the group ID of this system event
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this system event.
	 *
	 * @param mvccVersion the mvcc version of this system event
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent system event ID of this system event.
	 *
	 * @param parentSystemEventId the parent system event ID of this system event
	 */
	@Override
	public void setParentSystemEventId(long parentSystemEventId) {
		model.setParentSystemEventId(parentSystemEventId);
	}

	/**
	 * Sets the primary key of this system event.
	 *
	 * @param primaryKey the primary key of this system event
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public void setReferrerClassName(String referrerClassName) {
		model.setReferrerClassName(referrerClassName);
	}

	/**
	 * Sets the referrer class name ID of this system event.
	 *
	 * @param referrerClassNameId the referrer class name ID of this system event
	 */
	@Override
	public void setReferrerClassNameId(long referrerClassNameId) {
		model.setReferrerClassNameId(referrerClassNameId);
	}

	/**
	 * Sets the system event ID of this system event.
	 *
	 * @param systemEventId the system event ID of this system event
	 */
	@Override
	public void setSystemEventId(long systemEventId) {
		model.setSystemEventId(systemEventId);
	}

	/**
	 * Sets the system event set key of this system event.
	 *
	 * @param systemEventSetKey the system event set key of this system event
	 */
	@Override
	public void setSystemEventSetKey(long systemEventSetKey) {
		model.setSystemEventSetKey(systemEventSetKey);
	}

	/**
	 * Sets the type of this system event.
	 *
	 * @param type the type of this system event
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this system event.
	 *
	 * @param userId the user ID of this system event
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this system event.
	 *
	 * @param userName the user name of this system event
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this system event.
	 *
	 * @param userUuid the user uuid of this system event
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SystemEventWrapper wrap(SystemEvent systemEvent) {
		return new SystemEventWrapper(systemEvent);
	}

}
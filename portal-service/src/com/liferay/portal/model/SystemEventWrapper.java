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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

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
@ProviderType
public class SystemEventWrapper implements SystemEvent,
	ModelWrapper<SystemEvent> {
	public SystemEventWrapper(SystemEvent systemEvent) {
		_systemEvent = systemEvent;
	}

	@Override
	public Class<?> getModelClass() {
		return SystemEvent.class;
	}

	@Override
	public String getModelClassName() {
		return SystemEvent.class.getName();
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

	@Override
	public java.lang.Object clone() {
		return new SystemEventWrapper((SystemEvent)_systemEvent.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.SystemEvent systemEvent) {
		return _systemEvent.compareTo(systemEvent);
	}

	/**
	* Returns the fully qualified class name of this system event.
	*
	* @return the fully qualified class name of this system event
	*/
	@Override
	public java.lang.String getClassName() {
		return _systemEvent.getClassName();
	}

	/**
	* Returns the class name ID of this system event.
	*
	* @return the class name ID of this system event
	*/
	@Override
	public long getClassNameId() {
		return _systemEvent.getClassNameId();
	}

	/**
	* Returns the class p k of this system event.
	*
	* @return the class p k of this system event
	*/
	@Override
	public long getClassPK() {
		return _systemEvent.getClassPK();
	}

	/**
	* Returns the class uuid of this system event.
	*
	* @return the class uuid of this system event
	*/
	@Override
	public java.lang.String getClassUuid() {
		return _systemEvent.getClassUuid();
	}

	/**
	* Returns the company ID of this system event.
	*
	* @return the company ID of this system event
	*/
	@Override
	public long getCompanyId() {
		return _systemEvent.getCompanyId();
	}

	/**
	* Returns the create date of this system event.
	*
	* @return the create date of this system event
	*/
	@Override
	public Date getCreateDate() {
		return _systemEvent.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _systemEvent.getExpandoBridge();
	}

	/**
	* Returns the extra data of this system event.
	*
	* @return the extra data of this system event
	*/
	@Override
	public java.lang.String getExtraData() {
		return _systemEvent.getExtraData();
	}

	/**
	* Returns the group ID of this system event.
	*
	* @return the group ID of this system event
	*/
	@Override
	public long getGroupId() {
		return _systemEvent.getGroupId();
	}

	/**
	* Returns the mvcc version of this system event.
	*
	* @return the mvcc version of this system event
	*/
	@Override
	public long getMvccVersion() {
		return _systemEvent.getMvccVersion();
	}

	/**
	* Returns the parent system event ID of this system event.
	*
	* @return the parent system event ID of this system event
	*/
	@Override
	public long getParentSystemEventId() {
		return _systemEvent.getParentSystemEventId();
	}

	/**
	* Returns the primary key of this system event.
	*
	* @return the primary key of this system event
	*/
	@Override
	public long getPrimaryKey() {
		return _systemEvent.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _systemEvent.getPrimaryKeyObj();
	}

	@Override
	public java.lang.String getReferrerClassName() {
		return _systemEvent.getReferrerClassName();
	}

	/**
	* Returns the referrer class name ID of this system event.
	*
	* @return the referrer class name ID of this system event
	*/
	@Override
	public long getReferrerClassNameId() {
		return _systemEvent.getReferrerClassNameId();
	}

	/**
	* Returns the system event ID of this system event.
	*
	* @return the system event ID of this system event
	*/
	@Override
	public long getSystemEventId() {
		return _systemEvent.getSystemEventId();
	}

	/**
	* Returns the system event set key of this system event.
	*
	* @return the system event set key of this system event
	*/
	@Override
	public long getSystemEventSetKey() {
		return _systemEvent.getSystemEventSetKey();
	}

	/**
	* Returns the type of this system event.
	*
	* @return the type of this system event
	*/
	@Override
	public int getType() {
		return _systemEvent.getType();
	}

	/**
	* Returns the user ID of this system event.
	*
	* @return the user ID of this system event
	*/
	@Override
	public long getUserId() {
		return _systemEvent.getUserId();
	}

	/**
	* Returns the user name of this system event.
	*
	* @return the user name of this system event
	*/
	@Override
	public java.lang.String getUserName() {
		return _systemEvent.getUserName();
	}

	/**
	* Returns the user uuid of this system event.
	*
	* @return the user uuid of this system event
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _systemEvent.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _systemEvent.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _systemEvent.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _systemEvent.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _systemEvent.isNew();
	}

	@Override
	public void persist() {
		_systemEvent.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_systemEvent.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_systemEvent.setClassName(className);
	}

	/**
	* Sets the class name ID of this system event.
	*
	* @param classNameId the class name ID of this system event
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_systemEvent.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this system event.
	*
	* @param classPK the class p k of this system event
	*/
	@Override
	public void setClassPK(long classPK) {
		_systemEvent.setClassPK(classPK);
	}

	/**
	* Sets the class uuid of this system event.
	*
	* @param classUuid the class uuid of this system event
	*/
	@Override
	public void setClassUuid(java.lang.String classUuid) {
		_systemEvent.setClassUuid(classUuid);
	}

	/**
	* Sets the company ID of this system event.
	*
	* @param companyId the company ID of this system event
	*/
	@Override
	public void setCompanyId(long companyId) {
		_systemEvent.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this system event.
	*
	* @param createDate the create date of this system event
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_systemEvent.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_systemEvent.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_systemEvent.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_systemEvent.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the extra data of this system event.
	*
	* @param extraData the extra data of this system event
	*/
	@Override
	public void setExtraData(java.lang.String extraData) {
		_systemEvent.setExtraData(extraData);
	}

	/**
	* Sets the group ID of this system event.
	*
	* @param groupId the group ID of this system event
	*/
	@Override
	public void setGroupId(long groupId) {
		_systemEvent.setGroupId(groupId);
	}

	/**
	* Sets the mvcc version of this system event.
	*
	* @param mvccVersion the mvcc version of this system event
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_systemEvent.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_systemEvent.setNew(n);
	}

	/**
	* Sets the parent system event ID of this system event.
	*
	* @param parentSystemEventId the parent system event ID of this system event
	*/
	@Override
	public void setParentSystemEventId(long parentSystemEventId) {
		_systemEvent.setParentSystemEventId(parentSystemEventId);
	}

	/**
	* Sets the primary key of this system event.
	*
	* @param primaryKey the primary key of this system event
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_systemEvent.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_systemEvent.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setReferrerClassName(java.lang.String referrerClassName) {
		_systemEvent.setReferrerClassName(referrerClassName);
	}

	/**
	* Sets the referrer class name ID of this system event.
	*
	* @param referrerClassNameId the referrer class name ID of this system event
	*/
	@Override
	public void setReferrerClassNameId(long referrerClassNameId) {
		_systemEvent.setReferrerClassNameId(referrerClassNameId);
	}

	/**
	* Sets the system event ID of this system event.
	*
	* @param systemEventId the system event ID of this system event
	*/
	@Override
	public void setSystemEventId(long systemEventId) {
		_systemEvent.setSystemEventId(systemEventId);
	}

	/**
	* Sets the system event set key of this system event.
	*
	* @param systemEventSetKey the system event set key of this system event
	*/
	@Override
	public void setSystemEventSetKey(long systemEventSetKey) {
		_systemEvent.setSystemEventSetKey(systemEventSetKey);
	}

	/**
	* Sets the type of this system event.
	*
	* @param type the type of this system event
	*/
	@Override
	public void setType(int type) {
		_systemEvent.setType(type);
	}

	/**
	* Sets the user ID of this system event.
	*
	* @param userId the user ID of this system event
	*/
	@Override
	public void setUserId(long userId) {
		_systemEvent.setUserId(userId);
	}

	/**
	* Sets the user name of this system event.
	*
	* @param userName the user name of this system event
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_systemEvent.setUserName(userName);
	}

	/**
	* Sets the user uuid of this system event.
	*
	* @param userUuid the user uuid of this system event
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_systemEvent.setUserUuid(userUuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.SystemEvent> toCacheModel() {
		return _systemEvent.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.SystemEvent toEscapedModel() {
		return new SystemEventWrapper(_systemEvent.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _systemEvent.toString();
	}

	@Override
	public com.liferay.portal.model.SystemEvent toUnescapedModel() {
		return new SystemEventWrapper(_systemEvent.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _systemEvent.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SystemEventWrapper)) {
			return false;
		}

		SystemEventWrapper systemEventWrapper = (SystemEventWrapper)obj;

		if (Validator.equals(_systemEvent, systemEventWrapper._systemEvent)) {
			return true;
		}

		return false;
	}

	@Override
	public SystemEvent getWrappedModel() {
		return _systemEvent;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _systemEvent.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _systemEvent.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_systemEvent.resetOriginalValues();
	}

	private final SystemEvent _systemEvent;
}
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
		attributes.put("type", getType());
		attributes.put("extraData", getExtraData());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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
	* Returns the primary key of this system event.
	*
	* @return the primary key of this system event
	*/
	@Override
	public long getPrimaryKey() {
		return _systemEvent.getPrimaryKey();
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
	* Sets the system event ID of this system event.
	*
	* @param systemEventId the system event ID of this system event
	*/
	@Override
	public void setSystemEventId(long systemEventId) {
		_systemEvent.setSystemEventId(systemEventId);
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
	* Sets the group ID of this system event.
	*
	* @param groupId the group ID of this system event
	*/
	@Override
	public void setGroupId(long groupId) {
		_systemEvent.setGroupId(groupId);
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
	* Sets the company ID of this system event.
	*
	* @param companyId the company ID of this system event
	*/
	@Override
	public void setCompanyId(long companyId) {
		_systemEvent.setCompanyId(companyId);
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
	* Sets the user ID of this system event.
	*
	* @param userId the user ID of this system event
	*/
	@Override
	public void setUserId(long userId) {
		_systemEvent.setUserId(userId);
	}

	/**
	* Returns the user uuid of this system event.
	*
	* @return the user uuid of this system event
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _systemEvent.getUserUuid();
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
	* Sets the user name of this system event.
	*
	* @param userName the user name of this system event
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_systemEvent.setUserName(userName);
	}

	/**
	* Returns the create date of this system event.
	*
	* @return the create date of this system event
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _systemEvent.getCreateDate();
	}

	/**
	* Sets the create date of this system event.
	*
	* @param createDate the create date of this system event
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_systemEvent.setCreateDate(createDate);
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

	@Override
	public void setClassName(java.lang.String className) {
		_systemEvent.setClassName(className);
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
	* Sets the class name ID of this system event.
	*
	* @param classNameId the class name ID of this system event
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_systemEvent.setClassNameId(classNameId);
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
	* Sets the class p k of this system event.
	*
	* @param classPK the class p k of this system event
	*/
	@Override
	public void setClassPK(long classPK) {
		_systemEvent.setClassPK(classPK);
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
	* Sets the class uuid of this system event.
	*
	* @param classUuid the class uuid of this system event
	*/
	@Override
	public void setClassUuid(java.lang.String classUuid) {
		_systemEvent.setClassUuid(classUuid);
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
	* Sets the referrer class name ID of this system event.
	*
	* @param referrerClassNameId the referrer class name ID of this system event
	*/
	@Override
	public void setReferrerClassNameId(long referrerClassNameId) {
		_systemEvent.setReferrerClassNameId(referrerClassNameId);
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
	* Sets the type of this system event.
	*
	* @param type the type of this system event
	*/
	@Override
	public void setType(int type) {
		_systemEvent.setType(type);
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
	* Sets the extra data of this system event.
	*
	* @param extraData the extra data of this system event
	*/
	@Override
	public void setExtraData(java.lang.String extraData) {
		_systemEvent.setExtraData(extraData);
	}

	@Override
	public boolean isNew() {
		return _systemEvent.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_systemEvent.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _systemEvent.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_systemEvent.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _systemEvent.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _systemEvent.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_systemEvent.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _systemEvent.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
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

	@Override
	public java.lang.Object clone() {
		return new SystemEventWrapper((SystemEvent)_systemEvent.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.SystemEvent systemEvent) {
		return _systemEvent.compareTo(systemEvent);
	}

	@Override
	public int hashCode() {
		return _systemEvent.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.SystemEvent> toCacheModel() {
		return _systemEvent.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.SystemEvent toEscapedModel() {
		return new SystemEventWrapper(_systemEvent.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.SystemEvent toUnescapedModel() {
		return new SystemEventWrapper(_systemEvent.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _systemEvent.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _systemEvent.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_systemEvent.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public SystemEvent getWrappedSystemEvent() {
		return _systemEvent;
	}

	@Override
	public SystemEvent getWrappedModel() {
		return _systemEvent;
	}

	@Override
	public void resetOriginalValues() {
		_systemEvent.resetOriginalValues();
	}

	private SystemEvent _systemEvent;
}
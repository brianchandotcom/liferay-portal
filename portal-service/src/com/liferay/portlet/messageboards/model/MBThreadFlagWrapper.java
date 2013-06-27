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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MBThreadFlag}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadFlag
 * @generated
 */
public class MBThreadFlagWrapper implements MBThreadFlag,
	ModelWrapper<MBThreadFlag> {
	public MBThreadFlagWrapper(MBThreadFlag mbThreadFlag) {
		_mbThreadFlag = mbThreadFlag;
	}

	@Override
	public Class<?> getModelClass() {
		return MBThreadFlag.class;
	}

	@Override
	public String getModelClassName() {
		return MBThreadFlag.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("threadFlagId", getThreadFlagId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("threadId", getThreadId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long threadFlagId = (Long)attributes.get("threadFlagId");

		if (threadFlagId != null) {
			setThreadFlagId(threadFlagId);
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

		Long threadId = (Long)attributes.get("threadId");

		if (threadId != null) {
			setThreadId(threadId);
		}
	}

	/**
	* Returns the primary key of this message boards thread flag.
	*
	* @return the primary key of this message boards thread flag
	*/
	@Override
	public long getPrimaryKey() {
		return _mbThreadFlag.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards thread flag.
	*
	* @param primaryKey the primary key of this message boards thread flag
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mbThreadFlag.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this message boards thread flag.
	*
	* @return the uuid of this message boards thread flag
	*/
	@Override
	public java.lang.String getUuid() {
		return _mbThreadFlag.getUuid();
	}

	/**
	* Sets the uuid of this message boards thread flag.
	*
	* @param uuid the uuid of this message boards thread flag
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_mbThreadFlag.setUuid(uuid);
	}

	/**
	* Returns the thread flag ID of this message boards thread flag.
	*
	* @return the thread flag ID of this message boards thread flag
	*/
	@Override
	public long getThreadFlagId() {
		return _mbThreadFlag.getThreadFlagId();
	}

	/**
	* Sets the thread flag ID of this message boards thread flag.
	*
	* @param threadFlagId the thread flag ID of this message boards thread flag
	*/
	@Override
	public void setThreadFlagId(long threadFlagId) {
		_mbThreadFlag.setThreadFlagId(threadFlagId);
	}

	/**
	* Returns the group ID of this message boards thread flag.
	*
	* @return the group ID of this message boards thread flag
	*/
	@Override
	public long getGroupId() {
		return _mbThreadFlag.getGroupId();
	}

	/**
	* Sets the group ID of this message boards thread flag.
	*
	* @param groupId the group ID of this message boards thread flag
	*/
	@Override
	public void setGroupId(long groupId) {
		_mbThreadFlag.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this message boards thread flag.
	*
	* @return the company ID of this message boards thread flag
	*/
	@Override
	public long getCompanyId() {
		return _mbThreadFlag.getCompanyId();
	}

	/**
	* Sets the company ID of this message boards thread flag.
	*
	* @param companyId the company ID of this message boards thread flag
	*/
	@Override
	public void setCompanyId(long companyId) {
		_mbThreadFlag.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this message boards thread flag.
	*
	* @return the user ID of this message boards thread flag
	*/
	@Override
	public long getUserId() {
		return _mbThreadFlag.getUserId();
	}

	/**
	* Sets the user ID of this message boards thread flag.
	*
	* @param userId the user ID of this message boards thread flag
	*/
	@Override
	public void setUserId(long userId) {
		_mbThreadFlag.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message boards thread flag.
	*
	* @return the user uuid of this message boards thread flag
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadFlag.getUserUuid();
	}

	/**
	* Sets the user uuid of this message boards thread flag.
	*
	* @param userUuid the user uuid of this message boards thread flag
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_mbThreadFlag.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this message boards thread flag.
	*
	* @return the user name of this message boards thread flag
	*/
	@Override
	public java.lang.String getUserName() {
		return _mbThreadFlag.getUserName();
	}

	/**
	* Sets the user name of this message boards thread flag.
	*
	* @param userName the user name of this message boards thread flag
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_mbThreadFlag.setUserName(userName);
	}

	/**
	* Returns the create date of this message boards thread flag.
	*
	* @return the create date of this message boards thread flag
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _mbThreadFlag.getCreateDate();
	}

	/**
	* Sets the create date of this message boards thread flag.
	*
	* @param createDate the create date of this message boards thread flag
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_mbThreadFlag.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this message boards thread flag.
	*
	* @return the modified date of this message boards thread flag
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _mbThreadFlag.getModifiedDate();
	}

	/**
	* Sets the modified date of this message boards thread flag.
	*
	* @param modifiedDate the modified date of this message boards thread flag
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbThreadFlag.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the thread ID of this message boards thread flag.
	*
	* @return the thread ID of this message boards thread flag
	*/
	@Override
	public long getThreadId() {
		return _mbThreadFlag.getThreadId();
	}

	/**
	* Sets the thread ID of this message boards thread flag.
	*
	* @param threadId the thread ID of this message boards thread flag
	*/
	@Override
	public void setThreadId(long threadId) {
		_mbThreadFlag.setThreadId(threadId);
	}

	@Override
	public boolean isNew() {
		return _mbThreadFlag.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_mbThreadFlag.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _mbThreadFlag.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mbThreadFlag.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _mbThreadFlag.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mbThreadFlag.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbThreadFlag.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbThreadFlag.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_mbThreadFlag.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_mbThreadFlag.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbThreadFlag.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MBThreadFlagWrapper((MBThreadFlag)_mbThreadFlag.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.messageboards.model.MBThreadFlag mbThreadFlag) {
		return _mbThreadFlag.compareTo(mbThreadFlag);
	}

	@Override
	public int hashCode() {
		return _mbThreadFlag.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBThreadFlag> toCacheModel() {
		return _mbThreadFlag.toCacheModel();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBThreadFlag toEscapedModel() {
		return new MBThreadFlagWrapper(_mbThreadFlag.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBThreadFlag toUnescapedModel() {
		return new MBThreadFlagWrapper(_mbThreadFlag.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbThreadFlag.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _mbThreadFlag.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbThreadFlag.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBThreadFlagWrapper)) {
			return false;
		}

		MBThreadFlagWrapper mbThreadFlagWrapper = (MBThreadFlagWrapper)obj;

		if (Validator.equals(_mbThreadFlag, mbThreadFlagWrapper._mbThreadFlag)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public MBThreadFlag getWrappedMBThreadFlag() {
		return _mbThreadFlag;
	}

	@Override
	public MBThreadFlag getWrappedModel() {
		return _mbThreadFlag;
	}

	@Override
	public void resetOriginalValues() {
		_mbThreadFlag.resetOriginalValues();
	}

	private MBThreadFlag _mbThreadFlag;
}
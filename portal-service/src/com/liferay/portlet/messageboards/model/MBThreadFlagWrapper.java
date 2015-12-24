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

package com.liferay.portlet.messageboards.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

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
@ProviderType
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
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
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
	* Returns the create date of this message boards thread flag.
	*
	* @return the create date of this message boards thread flag
	*/
	@Override
	public Date getCreateDate() {
		return _mbThreadFlag.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbThreadFlag.getExpandoBridge();
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
	* Returns the last publish date of this message boards thread flag.
	*
	* @return the last publish date of this message boards thread flag
	*/
	@Override
	public Date getLastPublishDate() {
		return _mbThreadFlag.getLastPublishDate();
	}

	/**
	* Returns the modified date of this message boards thread flag.
	*
	* @return the modified date of this message boards thread flag
	*/
	@Override
	public Date getModifiedDate() {
		return _mbThreadFlag.getModifiedDate();
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

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mbThreadFlag.getPrimaryKeyObj();
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
	* Returns the thread ID of this message boards thread flag.
	*
	* @return the thread ID of this message boards thread flag
	*/
	@Override
	public long getThreadId() {
		return _mbThreadFlag.getThreadId();
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
	* Returns the user name of this message boards thread flag.
	*
	* @return the user name of this message boards thread flag
	*/
	@Override
	public java.lang.String getUserName() {
		return _mbThreadFlag.getUserName();
	}

	/**
	* Returns the user uuid of this message boards thread flag.
	*
	* @return the user uuid of this message boards thread flag
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _mbThreadFlag.getUserUuid();
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

	@Override
	public int hashCode() {
		return _mbThreadFlag.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _mbThreadFlag.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _mbThreadFlag.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _mbThreadFlag.isNew();
	}

	@Override
	public void persist() {
		_mbThreadFlag.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mbThreadFlag.setCachedModel(cachedModel);
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
	* Sets the create date of this message boards thread flag.
	*
	* @param createDate the create date of this message boards thread flag
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_mbThreadFlag.setCreateDate(createDate);
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
	* Sets the last publish date of this message boards thread flag.
	*
	* @param lastPublishDate the last publish date of this message boards thread flag
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_mbThreadFlag.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this message boards thread flag.
	*
	* @param modifiedDate the modified date of this message boards thread flag
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_mbThreadFlag.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_mbThreadFlag.setNew(n);
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

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbThreadFlag.setPrimaryKeyObj(primaryKeyObj);
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
	* Sets the thread ID of this message boards thread flag.
	*
	* @param threadId the thread ID of this message boards thread flag
	*/
	@Override
	public void setThreadId(long threadId) {
		_mbThreadFlag.setThreadId(threadId);
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
	* Sets the user name of this message boards thread flag.
	*
	* @param userName the user name of this message boards thread flag
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_mbThreadFlag.setUserName(userName);
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
	* Sets the uuid of this message boards thread flag.
	*
	* @param uuid the uuid of this message boards thread flag
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_mbThreadFlag.setUuid(uuid);
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
	public java.lang.String toString() {
		return _mbThreadFlag.toString();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBThreadFlag toUnescapedModel() {
		return new MBThreadFlagWrapper(_mbThreadFlag.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _mbThreadFlag.toXmlString();
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

	@Override
	public StagedModelType getStagedModelType() {
		return _mbThreadFlag.getStagedModelType();
	}

	@Override
	public MBThreadFlag getWrappedModel() {
		return _mbThreadFlag;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _mbThreadFlag.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _mbThreadFlag.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_mbThreadFlag.resetOriginalValues();
	}

	private final MBThreadFlag _mbThreadFlag;
}
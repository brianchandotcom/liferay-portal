/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MBThreadFlag}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThreadFlag
 * @generated
 */
public class MBThreadFlagWrapper implements MBThreadFlag,
	ModelWrapper<MBThreadFlag> {
	public MBThreadFlagWrapper(MBThreadFlag mbThreadFlag) {
		_mbThreadFlag = mbThreadFlag;
	}

	public Class<?> getModelClass() {
		return MBThreadFlag.class;
	}

	public String getModelClassName() {
		return MBThreadFlag.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("threadFlagId", getThreadFlagId());
		attributes.put("userId", getUserId());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("threadId", getThreadId());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long threadFlagId = (Long)attributes.get("threadFlagId");

		if (threadFlagId != null) {
			setThreadFlagId(threadFlagId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
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
	public long getPrimaryKey() {
		return _mbThreadFlag.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards thread flag.
	*
	* @param primaryKey the primary key of this message boards thread flag
	*/
	public void setPrimaryKey(long primaryKey) {
		_mbThreadFlag.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the thread flag ID of this message boards thread flag.
	*
	* @return the thread flag ID of this message boards thread flag
	*/
	public long getThreadFlagId() {
		return _mbThreadFlag.getThreadFlagId();
	}

	/**
	* Sets the thread flag ID of this message boards thread flag.
	*
	* @param threadFlagId the thread flag ID of this message boards thread flag
	*/
	public void setThreadFlagId(long threadFlagId) {
		_mbThreadFlag.setThreadFlagId(threadFlagId);
	}

	/**
	* Returns the user ID of this message boards thread flag.
	*
	* @return the user ID of this message boards thread flag
	*/
	public long getUserId() {
		return _mbThreadFlag.getUserId();
	}

	/**
	* Sets the user ID of this message boards thread flag.
	*
	* @param userId the user ID of this message boards thread flag
	*/
	public void setUserId(long userId) {
		_mbThreadFlag.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message boards thread flag.
	*
	* @return the user uuid of this message boards thread flag
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadFlag.getUserUuid();
	}

	/**
	* Sets the user uuid of this message boards thread flag.
	*
	* @param userUuid the user uuid of this message boards thread flag
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_mbThreadFlag.setUserUuid(userUuid);
	}

	/**
	* Returns the modified date of this message boards thread flag.
	*
	* @return the modified date of this message boards thread flag
	*/
	public java.util.Date getModifiedDate() {
		return _mbThreadFlag.getModifiedDate();
	}

	/**
	* Sets the modified date of this message boards thread flag.
	*
	* @param modifiedDate the modified date of this message boards thread flag
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbThreadFlag.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the thread ID of this message boards thread flag.
	*
	* @return the thread ID of this message boards thread flag
	*/
	public long getThreadId() {
		return _mbThreadFlag.getThreadId();
	}

	/**
	* Sets the thread ID of this message boards thread flag.
	*
	* @param threadId the thread ID of this message boards thread flag
	*/
	public void setThreadId(long threadId) {
		_mbThreadFlag.setThreadId(threadId);
	}

	public boolean isNew() {
		return _mbThreadFlag.isNew();
	}

	public void setNew(boolean n) {
		_mbThreadFlag.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbThreadFlag.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbThreadFlag.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbThreadFlag.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbThreadFlag.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbThreadFlag.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbThreadFlag.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbThreadFlag.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MBThreadFlagWrapper((MBThreadFlag)_mbThreadFlag.clone());
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBThreadFlag mbThreadFlag) {
		return _mbThreadFlag.compareTo(mbThreadFlag);
	}

	@Override
	public int hashCode() {
		return _mbThreadFlag.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBThreadFlag> toCacheModel() {
		return _mbThreadFlag.toCacheModel();
	}

	public com.liferay.portlet.messageboards.model.MBThreadFlag toEscapedModel() {
		return new MBThreadFlagWrapper(_mbThreadFlag.toEscapedModel());
	}

	public com.liferay.portlet.messageboards.model.MBThreadFlag toUnescapedModel() {
		return new MBThreadFlagWrapper(_mbThreadFlag.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbThreadFlag.toString();
	}

	public java.lang.String toXmlString() {
		return _mbThreadFlag.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbThreadFlag.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public MBThreadFlag getWrappedMBThreadFlag() {
		return _mbThreadFlag;
	}

	public MBThreadFlag getWrappedModel() {
		return _mbThreadFlag;
	}

	public void resetOriginalValues() {
		_mbThreadFlag.resetOriginalValues();
	}

	private MBThreadFlag _mbThreadFlag;
}
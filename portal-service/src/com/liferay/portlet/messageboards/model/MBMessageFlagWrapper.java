/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link MBMessageFlag}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageFlag
 * @generated
 */
public class MBMessageFlagWrapper implements MBMessageFlag {
	public MBMessageFlagWrapper(MBMessageFlag mbMessageFlag) {
		_mbMessageFlag = mbMessageFlag;
	}

	public Class<?> getModelClass() {
		return MBMessageFlag.class;
	}

	public String getModelClassName() {
		return MBMessageFlag.class.getName();
	}

	/**
	* Returns the primary key of this message boards message flag.
	*
	* @return the primary key of this message boards message flag
	*/
	public long getPrimaryKey() {
		return _mbMessageFlag.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards message flag.
	*
	* @param primaryKey the primary key of this message boards message flag
	*/
	public void setPrimaryKey(long primaryKey) {
		_mbMessageFlag.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the message flag ID of this message boards message flag.
	*
	* @return the message flag ID of this message boards message flag
	*/
	public long getMessageFlagId() {
		return _mbMessageFlag.getMessageFlagId();
	}

	/**
	* Sets the message flag ID of this message boards message flag.
	*
	* @param messageFlagId the message flag ID of this message boards message flag
	*/
	public void setMessageFlagId(long messageFlagId) {
		_mbMessageFlag.setMessageFlagId(messageFlagId);
	}

	/**
	* Returns the user ID of this message boards message flag.
	*
	* @return the user ID of this message boards message flag
	*/
	public long getUserId() {
		return _mbMessageFlag.getUserId();
	}

	/**
	* Sets the user ID of this message boards message flag.
	*
	* @param userId the user ID of this message boards message flag
	*/
	public void setUserId(long userId) {
		_mbMessageFlag.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message boards message flag.
	*
	* @return the user uuid of this message boards message flag
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlag.getUserUuid();
	}

	/**
	* Sets the user uuid of this message boards message flag.
	*
	* @param userUuid the user uuid of this message boards message flag
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_mbMessageFlag.setUserUuid(userUuid);
	}

	/**
	* Returns the modified date of this message boards message flag.
	*
	* @return the modified date of this message boards message flag
	*/
	public java.util.Date getModifiedDate() {
		return _mbMessageFlag.getModifiedDate();
	}

	/**
	* Sets the modified date of this message boards message flag.
	*
	* @param modifiedDate the modified date of this message boards message flag
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbMessageFlag.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the thread ID of this message boards message flag.
	*
	* @return the thread ID of this message boards message flag
	*/
	public long getThreadId() {
		return _mbMessageFlag.getThreadId();
	}

	/**
	* Sets the thread ID of this message boards message flag.
	*
	* @param threadId the thread ID of this message boards message flag
	*/
	public void setThreadId(long threadId) {
		_mbMessageFlag.setThreadId(threadId);
	}

	/**
	* Returns the message ID of this message boards message flag.
	*
	* @return the message ID of this message boards message flag
	*/
	public long getMessageId() {
		return _mbMessageFlag.getMessageId();
	}

	/**
	* Sets the message ID of this message boards message flag.
	*
	* @param messageId the message ID of this message boards message flag
	*/
	public void setMessageId(long messageId) {
		_mbMessageFlag.setMessageId(messageId);
	}

	/**
	* Returns the flag of this message boards message flag.
	*
	* @return the flag of this message boards message flag
	*/
	public int getFlag() {
		return _mbMessageFlag.getFlag();
	}

	/**
	* Sets the flag of this message boards message flag.
	*
	* @param flag the flag of this message boards message flag
	*/
	public void setFlag(int flag) {
		_mbMessageFlag.setFlag(flag);
	}

	public boolean isNew() {
		return _mbMessageFlag.isNew();
	}

	public void setNew(boolean n) {
		_mbMessageFlag.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbMessageFlag.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbMessageFlag.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbMessageFlag.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbMessageFlag.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbMessageFlag.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbMessageFlag.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbMessageFlag.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbMessageFlag.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MBMessageFlagWrapper((MBMessageFlag)_mbMessageFlag.clone());
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag) {
		return _mbMessageFlag.compareTo(mbMessageFlag);
	}

	@Override
	public int hashCode() {
		return _mbMessageFlag.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBMessageFlag> toCacheModel() {
		return _mbMessageFlag.toCacheModel();
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag toEscapedModel() {
		return new MBMessageFlagWrapper(_mbMessageFlag.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbMessageFlag.toString();
	}

	public java.lang.String toXmlString() {
		return _mbMessageFlag.toXmlString();
	}

	public void save()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlag.save();
	}

	public MBMessageFlag getWrappedMBMessageFlag() {
		return _mbMessageFlag;
	}

	public void resetOriginalValues() {
		_mbMessageFlag.resetOriginalValues();
	}

	private MBMessageFlag _mbMessageFlag;
}
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
 * This class is a wrapper for {@link MBStatsUser}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUser
 * @generated
 */
public class MBStatsUserWrapper implements MBStatsUser,
	ModelWrapper<MBStatsUser> {
	public MBStatsUserWrapper(MBStatsUser mbStatsUser) {
		_mbStatsUser = mbStatsUser;
	}

	@Override
	public Class<?> getModelClass() {
		return MBStatsUser.class;
	}

	@Override
	public String getModelClassName() {
		return MBStatsUser.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("statsUserId", getStatsUserId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("messageCount", getMessageCount());
		attributes.put("lastPostDate", getLastPostDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long statsUserId = (Long)attributes.get("statsUserId");

		if (statsUserId != null) {
			setStatsUserId(statsUserId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Integer messageCount = (Integer)attributes.get("messageCount");

		if (messageCount != null) {
			setMessageCount(messageCount);
		}

		Date lastPostDate = (Date)attributes.get("lastPostDate");

		if (lastPostDate != null) {
			setLastPostDate(lastPostDate);
		}
	}

	/**
	* Returns the primary key of this message boards stats user.
	*
	* @return the primary key of this message boards stats user
	*/
	@Override
	public long getPrimaryKey() {
		return _mbStatsUser.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards stats user.
	*
	* @param primaryKey the primary key of this message boards stats user
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mbStatsUser.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the stats user ID of this message boards stats user.
	*
	* @return the stats user ID of this message boards stats user
	*/
	@Override
	public long getStatsUserId() {
		return _mbStatsUser.getStatsUserId();
	}

	/**
	* Sets the stats user ID of this message boards stats user.
	*
	* @param statsUserId the stats user ID of this message boards stats user
	*/
	@Override
	public void setStatsUserId(long statsUserId) {
		_mbStatsUser.setStatsUserId(statsUserId);
	}

	/**
	* Returns the stats user uuid of this message boards stats user.
	*
	* @return the stats user uuid of this message boards stats user
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getStatsUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbStatsUser.getStatsUserUuid();
	}

	/**
	* Sets the stats user uuid of this message boards stats user.
	*
	* @param statsUserUuid the stats user uuid of this message boards stats user
	*/
	@Override
	public void setStatsUserUuid(java.lang.String statsUserUuid) {
		_mbStatsUser.setStatsUserUuid(statsUserUuid);
	}

	/**
	* Returns the group ID of this message boards stats user.
	*
	* @return the group ID of this message boards stats user
	*/
	@Override
	public long getGroupId() {
		return _mbStatsUser.getGroupId();
	}

	/**
	* Sets the group ID of this message boards stats user.
	*
	* @param groupId the group ID of this message boards stats user
	*/
	@Override
	public void setGroupId(long groupId) {
		_mbStatsUser.setGroupId(groupId);
	}

	/**
	* Returns the user ID of this message boards stats user.
	*
	* @return the user ID of this message boards stats user
	*/
	@Override
	public long getUserId() {
		return _mbStatsUser.getUserId();
	}

	/**
	* Sets the user ID of this message boards stats user.
	*
	* @param userId the user ID of this message boards stats user
	*/
	@Override
	public void setUserId(long userId) {
		_mbStatsUser.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message boards stats user.
	*
	* @return the user uuid of this message boards stats user
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbStatsUser.getUserUuid();
	}

	/**
	* Sets the user uuid of this message boards stats user.
	*
	* @param userUuid the user uuid of this message boards stats user
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_mbStatsUser.setUserUuid(userUuid);
	}

	/**
	* Returns the message count of this message boards stats user.
	*
	* @return the message count of this message boards stats user
	*/
	@Override
	public int getMessageCount() {
		return _mbStatsUser.getMessageCount();
	}

	/**
	* Sets the message count of this message boards stats user.
	*
	* @param messageCount the message count of this message boards stats user
	*/
	@Override
	public void setMessageCount(int messageCount) {
		_mbStatsUser.setMessageCount(messageCount);
	}

	/**
	* Returns the last post date of this message boards stats user.
	*
	* @return the last post date of this message boards stats user
	*/
	@Override
	public java.util.Date getLastPostDate() {
		return _mbStatsUser.getLastPostDate();
	}

	/**
	* Sets the last post date of this message boards stats user.
	*
	* @param lastPostDate the last post date of this message boards stats user
	*/
	@Override
	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbStatsUser.setLastPostDate(lastPostDate);
	}

	@Override
	public boolean isNew() {
		return _mbStatsUser.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_mbStatsUser.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _mbStatsUser.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mbStatsUser.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _mbStatsUser.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mbStatsUser.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbStatsUser.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbStatsUser.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_mbStatsUser.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_mbStatsUser.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbStatsUser.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MBStatsUserWrapper((MBStatsUser)_mbStatsUser.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return _mbStatsUser.compareTo(mbStatsUser);
	}

	@Override
	public int hashCode() {
		return _mbStatsUser.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBStatsUser> toCacheModel() {
		return _mbStatsUser.toCacheModel();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser toEscapedModel() {
		return new MBStatsUserWrapper(_mbStatsUser.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser toUnescapedModel() {
		return new MBStatsUserWrapper(_mbStatsUser.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbStatsUser.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _mbStatsUser.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbStatsUser.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBStatsUserWrapper)) {
			return false;
		}

		MBStatsUserWrapper mbStatsUserWrapper = (MBStatsUserWrapper)obj;

		if (Validator.equals(_mbStatsUser, mbStatsUserWrapper._mbStatsUser)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public MBStatsUser getWrappedMBStatsUser() {
		return _mbStatsUser;
	}

	@Override
	public MBStatsUser getWrappedModel() {
		return _mbStatsUser;
	}

	@Override
	public void resetOriginalValues() {
		_mbStatsUser.resetOriginalValues();
	}

	private MBStatsUser _mbStatsUser;
}
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
@ProviderType
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
		attributes.put("companyId", getCompanyId());
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

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
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

	@Override
	public java.lang.Object clone() {
		return new MBStatsUserWrapper((MBStatsUser)_mbStatsUser.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return _mbStatsUser.compareTo(mbStatsUser);
	}

	/**
	* Returns the company ID of this message boards stats user.
	*
	* @return the company ID of this message boards stats user
	*/
	@Override
	public long getCompanyId() {
		return _mbStatsUser.getCompanyId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbStatsUser.getExpandoBridge();
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
	* Returns the last post date of this message boards stats user.
	*
	* @return the last post date of this message boards stats user
	*/
	@Override
	public Date getLastPostDate() {
		return _mbStatsUser.getLastPostDate();
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
	* Returns the primary key of this message boards stats user.
	*
	* @return the primary key of this message boards stats user
	*/
	@Override
	public long getPrimaryKey() {
		return _mbStatsUser.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mbStatsUser.getPrimaryKeyObj();
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
	* Returns the stats user uuid of this message boards stats user.
	*
	* @return the stats user uuid of this message boards stats user
	*/
	@Override
	public java.lang.String getStatsUserUuid() {
		return _mbStatsUser.getStatsUserUuid();
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
	* Returns the user uuid of this message boards stats user.
	*
	* @return the user uuid of this message boards stats user
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _mbStatsUser.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _mbStatsUser.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _mbStatsUser.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _mbStatsUser.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _mbStatsUser.isNew();
	}

	@Override
	public void persist() {
		_mbStatsUser.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mbStatsUser.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this message boards stats user.
	*
	* @param companyId the company ID of this message boards stats user
	*/
	@Override
	public void setCompanyId(long companyId) {
		_mbStatsUser.setCompanyId(companyId);
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
	* Sets the last post date of this message boards stats user.
	*
	* @param lastPostDate the last post date of this message boards stats user
	*/
	@Override
	public void setLastPostDate(Date lastPostDate) {
		_mbStatsUser.setLastPostDate(lastPostDate);
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

	@Override
	public void setNew(boolean n) {
		_mbStatsUser.setNew(n);
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

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbStatsUser.setPrimaryKeyObj(primaryKeyObj);
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
	* Sets the stats user uuid of this message boards stats user.
	*
	* @param statsUserUuid the stats user uuid of this message boards stats user
	*/
	@Override
	public void setStatsUserUuid(java.lang.String statsUserUuid) {
		_mbStatsUser.setStatsUserUuid(statsUserUuid);
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
	* Sets the user uuid of this message boards stats user.
	*
	* @param userUuid the user uuid of this message boards stats user
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_mbStatsUser.setUserUuid(userUuid);
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
	public java.lang.String toString() {
		return _mbStatsUser.toString();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBStatsUser toUnescapedModel() {
		return new MBStatsUserWrapper(_mbStatsUser.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _mbStatsUser.toXmlString();
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

	@Override
	public MBStatsUser getWrappedModel() {
		return _mbStatsUser;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _mbStatsUser.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _mbStatsUser.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_mbStatsUser.resetOriginalValues();
	}

	private final MBStatsUser _mbStatsUser;
}
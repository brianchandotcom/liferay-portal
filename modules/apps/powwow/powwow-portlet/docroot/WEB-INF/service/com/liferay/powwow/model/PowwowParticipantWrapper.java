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

package com.liferay.powwow.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link PowwowParticipant}.
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowParticipant
 * @generated
 */
@ProviderType
public class PowwowParticipantWrapper implements PowwowParticipant,
	ModelWrapper<PowwowParticipant> {
	public PowwowParticipantWrapper(PowwowParticipant powwowParticipant) {
		_powwowParticipant = powwowParticipant;
	}

	@Override
	public Class<?> getModelClass() {
		return PowwowParticipant.class;
	}

	@Override
	public String getModelClassName() {
		return PowwowParticipant.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("powwowParticipantId", getPowwowParticipantId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("powwowMeetingId", getPowwowMeetingId());
		attributes.put("name", getName());
		attributes.put("participantUserId", getParticipantUserId());
		attributes.put("emailAddress", getEmailAddress());
		attributes.put("type", getType());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long powwowParticipantId = (Long)attributes.get("powwowParticipantId");

		if (powwowParticipantId != null) {
			setPowwowParticipantId(powwowParticipantId);
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

		Long powwowMeetingId = (Long)attributes.get("powwowMeetingId");

		if (powwowMeetingId != null) {
			setPowwowMeetingId(powwowMeetingId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long participantUserId = (Long)attributes.get("participantUserId");

		if (participantUserId != null) {
			setParticipantUserId(participantUserId);
		}

		String emailAddress = (String)attributes.get("emailAddress");

		if (emailAddress != null) {
			setEmailAddress(emailAddress);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public PowwowParticipant toEscapedModel() {
		return new PowwowParticipantWrapper(_powwowParticipant.toEscapedModel());
	}

	@Override
	public PowwowParticipant toUnescapedModel() {
		return new PowwowParticipantWrapper(_powwowParticipant.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _powwowParticipant.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _powwowParticipant.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _powwowParticipant.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _powwowParticipant.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<PowwowParticipant> toCacheModel() {
		return _powwowParticipant.toCacheModel();
	}

	@Override
	public int compareTo(PowwowParticipant powwowParticipant) {
		return _powwowParticipant.compareTo(powwowParticipant);
	}

	/**
	* Returns the status of this powwow participant.
	*
	* @return the status of this powwow participant
	*/
	@Override
	public int getStatus() {
		return _powwowParticipant.getStatus();
	}

	/**
	* Returns the type of this powwow participant.
	*
	* @return the type of this powwow participant
	*/
	@Override
	public int getType() {
		return _powwowParticipant.getType();
	}

	@Override
	public int hashCode() {
		return _powwowParticipant.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _powwowParticipant.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new PowwowParticipantWrapper((PowwowParticipant)_powwowParticipant.clone());
	}

	/**
	* Returns the email address of this powwow participant.
	*
	* @return the email address of this powwow participant
	*/
	@Override
	public java.lang.String getEmailAddress() {
		return _powwowParticipant.getEmailAddress();
	}

	/**
	* Returns the name of this powwow participant.
	*
	* @return the name of this powwow participant
	*/
	@Override
	public java.lang.String getName() {
		return _powwowParticipant.getName();
	}

	/**
	* Returns the participant user uuid of this powwow participant.
	*
	* @return the participant user uuid of this powwow participant
	*/
	@Override
	public java.lang.String getParticipantUserUuid() {
		return _powwowParticipant.getParticipantUserUuid();
	}

	/**
	* Returns the user name of this powwow participant.
	*
	* @return the user name of this powwow participant
	*/
	@Override
	public java.lang.String getUserName() {
		return _powwowParticipant.getUserName();
	}

	/**
	* Returns the user uuid of this powwow participant.
	*
	* @return the user uuid of this powwow participant
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _powwowParticipant.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _powwowParticipant.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _powwowParticipant.toXmlString();
	}

	/**
	* Returns the create date of this powwow participant.
	*
	* @return the create date of this powwow participant
	*/
	@Override
	public Date getCreateDate() {
		return _powwowParticipant.getCreateDate();
	}

	/**
	* Returns the modified date of this powwow participant.
	*
	* @return the modified date of this powwow participant
	*/
	@Override
	public Date getModifiedDate() {
		return _powwowParticipant.getModifiedDate();
	}

	/**
	* Returns the company ID of this powwow participant.
	*
	* @return the company ID of this powwow participant
	*/
	@Override
	public long getCompanyId() {
		return _powwowParticipant.getCompanyId();
	}

	/**
	* Returns the group ID of this powwow participant.
	*
	* @return the group ID of this powwow participant
	*/
	@Override
	public long getGroupId() {
		return _powwowParticipant.getGroupId();
	}

	/**
	* Returns the participant user ID of this powwow participant.
	*
	* @return the participant user ID of this powwow participant
	*/
	@Override
	public long getParticipantUserId() {
		return _powwowParticipant.getParticipantUserId();
	}

	/**
	* Returns the powwow meeting ID of this powwow participant.
	*
	* @return the powwow meeting ID of this powwow participant
	*/
	@Override
	public long getPowwowMeetingId() {
		return _powwowParticipant.getPowwowMeetingId();
	}

	/**
	* Returns the powwow participant ID of this powwow participant.
	*
	* @return the powwow participant ID of this powwow participant
	*/
	@Override
	public long getPowwowParticipantId() {
		return _powwowParticipant.getPowwowParticipantId();
	}

	/**
	* Returns the primary key of this powwow participant.
	*
	* @return the primary key of this powwow participant
	*/
	@Override
	public long getPrimaryKey() {
		return _powwowParticipant.getPrimaryKey();
	}

	/**
	* Returns the user ID of this powwow participant.
	*
	* @return the user ID of this powwow participant
	*/
	@Override
	public long getUserId() {
		return _powwowParticipant.getUserId();
	}

	@Override
	public void persist() {
		_powwowParticipant.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_powwowParticipant.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this powwow participant.
	*
	* @param companyId the company ID of this powwow participant
	*/
	@Override
	public void setCompanyId(long companyId) {
		_powwowParticipant.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this powwow participant.
	*
	* @param createDate the create date of this powwow participant
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_powwowParticipant.setCreateDate(createDate);
	}

	/**
	* Sets the email address of this powwow participant.
	*
	* @param emailAddress the email address of this powwow participant
	*/
	@Override
	public void setEmailAddress(java.lang.String emailAddress) {
		_powwowParticipant.setEmailAddress(emailAddress);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_powwowParticipant.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_powwowParticipant.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_powwowParticipant.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this powwow participant.
	*
	* @param groupId the group ID of this powwow participant
	*/
	@Override
	public void setGroupId(long groupId) {
		_powwowParticipant.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this powwow participant.
	*
	* @param modifiedDate the modified date of this powwow participant
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_powwowParticipant.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this powwow participant.
	*
	* @param name the name of this powwow participant
	*/
	@Override
	public void setName(java.lang.String name) {
		_powwowParticipant.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_powwowParticipant.setNew(n);
	}

	/**
	* Sets the participant user ID of this powwow participant.
	*
	* @param participantUserId the participant user ID of this powwow participant
	*/
	@Override
	public void setParticipantUserId(long participantUserId) {
		_powwowParticipant.setParticipantUserId(participantUserId);
	}

	/**
	* Sets the participant user uuid of this powwow participant.
	*
	* @param participantUserUuid the participant user uuid of this powwow participant
	*/
	@Override
	public void setParticipantUserUuid(java.lang.String participantUserUuid) {
		_powwowParticipant.setParticipantUserUuid(participantUserUuid);
	}

	/**
	* Sets the powwow meeting ID of this powwow participant.
	*
	* @param powwowMeetingId the powwow meeting ID of this powwow participant
	*/
	@Override
	public void setPowwowMeetingId(long powwowMeetingId) {
		_powwowParticipant.setPowwowMeetingId(powwowMeetingId);
	}

	/**
	* Sets the powwow participant ID of this powwow participant.
	*
	* @param powwowParticipantId the powwow participant ID of this powwow participant
	*/
	@Override
	public void setPowwowParticipantId(long powwowParticipantId) {
		_powwowParticipant.setPowwowParticipantId(powwowParticipantId);
	}

	/**
	* Sets the primary key of this powwow participant.
	*
	* @param primaryKey the primary key of this powwow participant
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_powwowParticipant.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_powwowParticipant.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the status of this powwow participant.
	*
	* @param status the status of this powwow participant
	*/
	@Override
	public void setStatus(int status) {
		_powwowParticipant.setStatus(status);
	}

	/**
	* Sets the type of this powwow participant.
	*
	* @param type the type of this powwow participant
	*/
	@Override
	public void setType(int type) {
		_powwowParticipant.setType(type);
	}

	/**
	* Sets the user ID of this powwow participant.
	*
	* @param userId the user ID of this powwow participant
	*/
	@Override
	public void setUserId(long userId) {
		_powwowParticipant.setUserId(userId);
	}

	/**
	* Sets the user name of this powwow participant.
	*
	* @param userName the user name of this powwow participant
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_powwowParticipant.setUserName(userName);
	}

	/**
	* Sets the user uuid of this powwow participant.
	*
	* @param userUuid the user uuid of this powwow participant
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_powwowParticipant.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PowwowParticipantWrapper)) {
			return false;
		}

		PowwowParticipantWrapper powwowParticipantWrapper = (PowwowParticipantWrapper)obj;

		if (Objects.equals(_powwowParticipant,
					powwowParticipantWrapper._powwowParticipant)) {
			return true;
		}

		return false;
	}

	@Override
	public PowwowParticipant getWrappedModel() {
		return _powwowParticipant;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _powwowParticipant.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _powwowParticipant.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_powwowParticipant.resetOriginalValues();
	}

	private final PowwowParticipant _powwowParticipant;
}
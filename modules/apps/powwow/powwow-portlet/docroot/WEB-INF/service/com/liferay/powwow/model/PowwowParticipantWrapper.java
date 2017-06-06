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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PowwowParticipant}.
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowParticipant
 * @generated
 */
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
	* Sets the primary key of this powwow participant.
	*
	* @param primaryKey the primary key of this powwow participant
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_powwowParticipant.setPrimaryKey(primaryKey);
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
	* Sets the powwow participant ID of this powwow participant.
	*
	* @param powwowParticipantId the powwow participant ID of this powwow participant
	*/
	@Override
	public void setPowwowParticipantId(long powwowParticipantId) {
		_powwowParticipant.setPowwowParticipantId(powwowParticipantId);
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
	* Sets the group ID of this powwow participant.
	*
	* @param groupId the group ID of this powwow participant
	*/
	@Override
	public void setGroupId(long groupId) {
		_powwowParticipant.setGroupId(groupId);
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
	* Sets the company ID of this powwow participant.
	*
	* @param companyId the company ID of this powwow participant
	*/
	@Override
	public void setCompanyId(long companyId) {
		_powwowParticipant.setCompanyId(companyId);
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
	* Returns the user uuid of this powwow participant.
	*
	* @return the user uuid of this powwow participant
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowParticipant.getUserUuid();
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
	* Sets the user name of this powwow participant.
	*
	* @param userName the user name of this powwow participant
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_powwowParticipant.setUserName(userName);
	}

	/**
	* Returns the create date of this powwow participant.
	*
	* @return the create date of this powwow participant
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _powwowParticipant.getCreateDate();
	}

	/**
	* Sets the create date of this powwow participant.
	*
	* @param createDate the create date of this powwow participant
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_powwowParticipant.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this powwow participant.
	*
	* @return the modified date of this powwow participant
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _powwowParticipant.getModifiedDate();
	}

	/**
	* Sets the modified date of this powwow participant.
	*
	* @param modifiedDate the modified date of this powwow participant
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_powwowParticipant.setModifiedDate(modifiedDate);
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
	* Sets the powwow meeting ID of this powwow participant.
	*
	* @param powwowMeetingId the powwow meeting ID of this powwow participant
	*/
	@Override
	public void setPowwowMeetingId(long powwowMeetingId) {
		_powwowParticipant.setPowwowMeetingId(powwowMeetingId);
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
	* Sets the name of this powwow participant.
	*
	* @param name the name of this powwow participant
	*/
	@Override
	public void setName(java.lang.String name) {
		_powwowParticipant.setName(name);
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
	* Sets the participant user ID of this powwow participant.
	*
	* @param participantUserId the participant user ID of this powwow participant
	*/
	@Override
	public void setParticipantUserId(long participantUserId) {
		_powwowParticipant.setParticipantUserId(participantUserId);
	}

	/**
	* Returns the participant user uuid of this powwow participant.
	*
	* @return the participant user uuid of this powwow participant
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getParticipantUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _powwowParticipant.getParticipantUserUuid();
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
	* Returns the email address of this powwow participant.
	*
	* @return the email address of this powwow participant
	*/
	@Override
	public java.lang.String getEmailAddress() {
		return _powwowParticipant.getEmailAddress();
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

	/**
	* Returns the type of this powwow participant.
	*
	* @return the type of this powwow participant
	*/
	@Override
	public int getType() {
		return _powwowParticipant.getType();
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
	* Returns the status of this powwow participant.
	*
	* @return the status of this powwow participant
	*/
	@Override
	public int getStatus() {
		return _powwowParticipant.getStatus();
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

	@Override
	public boolean isNew() {
		return _powwowParticipant.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_powwowParticipant.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _powwowParticipant.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_powwowParticipant.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _powwowParticipant.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _powwowParticipant.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_powwowParticipant.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _powwowParticipant.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_powwowParticipant.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_powwowParticipant.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_powwowParticipant.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PowwowParticipantWrapper((PowwowParticipant)_powwowParticipant.clone());
	}

	@Override
	public int compareTo(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant) {
		return _powwowParticipant.compareTo(powwowParticipant);
	}

	@Override
	public int hashCode() {
		return _powwowParticipant.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.powwow.model.PowwowParticipant> toCacheModel() {
		return _powwowParticipant.toCacheModel();
	}

	@Override
	public com.liferay.powwow.model.PowwowParticipant toEscapedModel() {
		return new PowwowParticipantWrapper(_powwowParticipant.toEscapedModel());
	}

	@Override
	public com.liferay.powwow.model.PowwowParticipant toUnescapedModel() {
		return new PowwowParticipantWrapper(_powwowParticipant.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _powwowParticipant.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _powwowParticipant.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_powwowParticipant.persist();
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

		if (Validator.equals(_powwowParticipant,
					powwowParticipantWrapper._powwowParticipant)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public PowwowParticipant getWrappedPowwowParticipant() {
		return _powwowParticipant;
	}

	@Override
	public PowwowParticipant getWrappedModel() {
		return _powwowParticipant;
	}

	@Override
	public void resetOriginalValues() {
		_powwowParticipant.resetOriginalValues();
	}

	private PowwowParticipant _powwowParticipant;
}
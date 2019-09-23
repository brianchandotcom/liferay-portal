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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class PowwowParticipantWrapper
	extends BaseModelWrapper<PowwowParticipant>
	implements ModelWrapper<PowwowParticipant>, PowwowParticipant {

	public PowwowParticipantWrapper(PowwowParticipant powwowParticipant) {
		super(powwowParticipant);
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
	 * Returns the company ID of this powwow participant.
	 *
	 * @return the company ID of this powwow participant
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this powwow participant.
	 *
	 * @return the create date of this powwow participant
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the email address of this powwow participant.
	 *
	 * @return the email address of this powwow participant
	 */
	@Override
	public String getEmailAddress() {
		return model.getEmailAddress();
	}

	/**
	 * Returns the group ID of this powwow participant.
	 *
	 * @return the group ID of this powwow participant
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this powwow participant.
	 *
	 * @return the modified date of this powwow participant
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this powwow participant.
	 *
	 * @return the name of this powwow participant
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the participant user ID of this powwow participant.
	 *
	 * @return the participant user ID of this powwow participant
	 */
	@Override
	public long getParticipantUserId() {
		return model.getParticipantUserId();
	}

	/**
	 * Returns the participant user uuid of this powwow participant.
	 *
	 * @return the participant user uuid of this powwow participant
	 */
	@Override
	public String getParticipantUserUuid() {
		return model.getParticipantUserUuid();
	}

	/**
	 * Returns the powwow meeting ID of this powwow participant.
	 *
	 * @return the powwow meeting ID of this powwow participant
	 */
	@Override
	public long getPowwowMeetingId() {
		return model.getPowwowMeetingId();
	}

	/**
	 * Returns the powwow participant ID of this powwow participant.
	 *
	 * @return the powwow participant ID of this powwow participant
	 */
	@Override
	public long getPowwowParticipantId() {
		return model.getPowwowParticipantId();
	}

	/**
	 * Returns the primary key of this powwow participant.
	 *
	 * @return the primary key of this powwow participant
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this powwow participant.
	 *
	 * @return the status of this powwow participant
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the type of this powwow participant.
	 *
	 * @return the type of this powwow participant
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this powwow participant.
	 *
	 * @return the user ID of this powwow participant
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this powwow participant.
	 *
	 * @return the user name of this powwow participant
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this powwow participant.
	 *
	 * @return the user uuid of this powwow participant
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a powwow participant model instance should use the <code>PowwowParticipant</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this powwow participant.
	 *
	 * @param companyId the company ID of this powwow participant
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this powwow participant.
	 *
	 * @param createDate the create date of this powwow participant
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the email address of this powwow participant.
	 *
	 * @param emailAddress the email address of this powwow participant
	 */
	@Override
	public void setEmailAddress(String emailAddress) {
		model.setEmailAddress(emailAddress);
	}

	/**
	 * Sets the group ID of this powwow participant.
	 *
	 * @param groupId the group ID of this powwow participant
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this powwow participant.
	 *
	 * @param modifiedDate the modified date of this powwow participant
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this powwow participant.
	 *
	 * @param name the name of this powwow participant
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the participant user ID of this powwow participant.
	 *
	 * @param participantUserId the participant user ID of this powwow participant
	 */
	@Override
	public void setParticipantUserId(long participantUserId) {
		model.setParticipantUserId(participantUserId);
	}

	/**
	 * Sets the participant user uuid of this powwow participant.
	 *
	 * @param participantUserUuid the participant user uuid of this powwow participant
	 */
	@Override
	public void setParticipantUserUuid(String participantUserUuid) {
		model.setParticipantUserUuid(participantUserUuid);
	}

	/**
	 * Sets the powwow meeting ID of this powwow participant.
	 *
	 * @param powwowMeetingId the powwow meeting ID of this powwow participant
	 */
	@Override
	public void setPowwowMeetingId(long powwowMeetingId) {
		model.setPowwowMeetingId(powwowMeetingId);
	}

	/**
	 * Sets the powwow participant ID of this powwow participant.
	 *
	 * @param powwowParticipantId the powwow participant ID of this powwow participant
	 */
	@Override
	public void setPowwowParticipantId(long powwowParticipantId) {
		model.setPowwowParticipantId(powwowParticipantId);
	}

	/**
	 * Sets the primary key of this powwow participant.
	 *
	 * @param primaryKey the primary key of this powwow participant
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this powwow participant.
	 *
	 * @param status the status of this powwow participant
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the type of this powwow participant.
	 *
	 * @param type the type of this powwow participant
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this powwow participant.
	 *
	 * @param userId the user ID of this powwow participant
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this powwow participant.
	 *
	 * @param userName the user name of this powwow participant
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this powwow participant.
	 *
	 * @param userUuid the user uuid of this powwow participant
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected PowwowParticipantWrapper wrap(
		PowwowParticipant powwowParticipant) {

		return new PowwowParticipantWrapper(powwowParticipant);
	}

}
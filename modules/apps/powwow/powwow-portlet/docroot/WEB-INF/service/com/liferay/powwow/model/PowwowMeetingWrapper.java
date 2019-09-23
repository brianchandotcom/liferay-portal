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

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PowwowMeeting}.
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowMeeting
 * @generated
 */
public class PowwowMeetingWrapper
	extends BaseModelWrapper<PowwowMeeting>
	implements ModelWrapper<PowwowMeeting>, PowwowMeeting {

	public PowwowMeetingWrapper(PowwowMeeting powwowMeeting) {
		super(powwowMeeting);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("powwowMeetingId", getPowwowMeetingId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("powwowServerId", getPowwowServerId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("providerType", getProviderType());
		attributes.put("providerTypeMetadata", getProviderTypeMetadata());
		attributes.put("languageId", getLanguageId());
		attributes.put("calendarBookingId", getCalendarBookingId());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long powwowMeetingId = (Long)attributes.get("powwowMeetingId");

		if (powwowMeetingId != null) {
			setPowwowMeetingId(powwowMeetingId);
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

		Long powwowServerId = (Long)attributes.get("powwowServerId");

		if (powwowServerId != null) {
			setPowwowServerId(powwowServerId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String providerType = (String)attributes.get("providerType");

		if (providerType != null) {
			setProviderType(providerType);
		}

		String providerTypeMetadata = (String)attributes.get(
			"providerTypeMetadata");

		if (providerTypeMetadata != null) {
			setProviderTypeMetadata(providerTypeMetadata);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		Long calendarBookingId = (Long)attributes.get("calendarBookingId");

		if (calendarBookingId != null) {
			setCalendarBookingId(calendarBookingId);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	 * Returns the calendar booking ID of this powwow meeting.
	 *
	 * @return the calendar booking ID of this powwow meeting
	 */
	@Override
	public long getCalendarBookingId() {
		return model.getCalendarBookingId();
	}

	/**
	 * Returns the company ID of this powwow meeting.
	 *
	 * @return the company ID of this powwow meeting
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this powwow meeting.
	 *
	 * @return the create date of this powwow meeting
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this powwow meeting.
	 *
	 * @return the description of this powwow meeting
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the group ID of this powwow meeting.
	 *
	 * @return the group ID of this powwow meeting
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the language ID of this powwow meeting.
	 *
	 * @return the language ID of this powwow meeting
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the modified date of this powwow meeting.
	 *
	 * @return the modified date of this powwow meeting
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this powwow meeting.
	 *
	 * @return the name of this powwow meeting
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the powwow meeting ID of this powwow meeting.
	 *
	 * @return the powwow meeting ID of this powwow meeting
	 */
	@Override
	public long getPowwowMeetingId() {
		return model.getPowwowMeetingId();
	}

	/**
	 * Returns the powwow server ID of this powwow meeting.
	 *
	 * @return the powwow server ID of this powwow meeting
	 */
	@Override
	public long getPowwowServerId() {
		return model.getPowwowServerId();
	}

	/**
	 * Returns the primary key of this powwow meeting.
	 *
	 * @return the primary key of this powwow meeting
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the provider type of this powwow meeting.
	 *
	 * @return the provider type of this powwow meeting
	 */
	@Override
	public String getProviderType() {
		return model.getProviderType();
	}

	/**
	 * Returns the provider type metadata of this powwow meeting.
	 *
	 * @return the provider type metadata of this powwow meeting
	 */
	@Override
	public String getProviderTypeMetadata() {
		return model.getProviderTypeMetadata();
	}

	@Override
	public Map<String, Serializable> getProviderTypeMetadataMap() {
		return model.getProviderTypeMetadataMap();
	}

	/**
	 * Returns the status of this powwow meeting.
	 *
	 * @return the status of this powwow meeting
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this powwow meeting.
	 *
	 * @return the user ID of this powwow meeting
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this powwow meeting.
	 *
	 * @return the user name of this powwow meeting
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this powwow meeting.
	 *
	 * @return the user uuid of this powwow meeting
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a powwow meeting model instance should use the <code>PowwowMeeting</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the calendar booking ID of this powwow meeting.
	 *
	 * @param calendarBookingId the calendar booking ID of this powwow meeting
	 */
	@Override
	public void setCalendarBookingId(long calendarBookingId) {
		model.setCalendarBookingId(calendarBookingId);
	}

	/**
	 * Sets the company ID of this powwow meeting.
	 *
	 * @param companyId the company ID of this powwow meeting
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this powwow meeting.
	 *
	 * @param createDate the create date of this powwow meeting
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this powwow meeting.
	 *
	 * @param description the description of this powwow meeting
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the group ID of this powwow meeting.
	 *
	 * @param groupId the group ID of this powwow meeting
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the language ID of this powwow meeting.
	 *
	 * @param languageId the language ID of this powwow meeting
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the modified date of this powwow meeting.
	 *
	 * @param modifiedDate the modified date of this powwow meeting
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this powwow meeting.
	 *
	 * @param name the name of this powwow meeting
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the powwow meeting ID of this powwow meeting.
	 *
	 * @param powwowMeetingId the powwow meeting ID of this powwow meeting
	 */
	@Override
	public void setPowwowMeetingId(long powwowMeetingId) {
		model.setPowwowMeetingId(powwowMeetingId);
	}

	/**
	 * Sets the powwow server ID of this powwow meeting.
	 *
	 * @param powwowServerId the powwow server ID of this powwow meeting
	 */
	@Override
	public void setPowwowServerId(long powwowServerId) {
		model.setPowwowServerId(powwowServerId);
	}

	/**
	 * Sets the primary key of this powwow meeting.
	 *
	 * @param primaryKey the primary key of this powwow meeting
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the provider type of this powwow meeting.
	 *
	 * @param providerType the provider type of this powwow meeting
	 */
	@Override
	public void setProviderType(String providerType) {
		model.setProviderType(providerType);
	}

	/**
	 * Sets the provider type metadata of this powwow meeting.
	 *
	 * @param providerTypeMetadata the provider type metadata of this powwow meeting
	 */
	@Override
	public void setProviderTypeMetadata(String providerTypeMetadata) {
		model.setProviderTypeMetadata(providerTypeMetadata);
	}

	/**
	 * Sets the status of this powwow meeting.
	 *
	 * @param status the status of this powwow meeting
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this powwow meeting.
	 *
	 * @param userId the user ID of this powwow meeting
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this powwow meeting.
	 *
	 * @param userName the user name of this powwow meeting
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this powwow meeting.
	 *
	 * @param userUuid the user uuid of this powwow meeting
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected PowwowMeetingWrapper wrap(PowwowMeeting powwowMeeting) {
		return new PowwowMeetingWrapper(powwowMeeting);
	}

}
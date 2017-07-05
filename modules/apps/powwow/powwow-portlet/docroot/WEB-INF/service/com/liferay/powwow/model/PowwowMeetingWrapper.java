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
 * This class is a wrapper for {@link PowwowMeeting}.
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowMeeting
 * @generated
 */
@ProviderType
public class PowwowMeetingWrapper implements PowwowMeeting,
	ModelWrapper<PowwowMeeting> {
	public PowwowMeetingWrapper(PowwowMeeting powwowMeeting) {
		_powwowMeeting = powwowMeeting;
	}

	@Override
	public Class<?> getModelClass() {
		return PowwowMeeting.class;
	}

	@Override
	public String getModelClassName() {
		return PowwowMeeting.class.getName();
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

	@Override
	public PowwowMeeting toEscapedModel() {
		return new PowwowMeetingWrapper(_powwowMeeting.toEscapedModel());
	}

	@Override
	public PowwowMeeting toUnescapedModel() {
		return new PowwowMeetingWrapper(_powwowMeeting.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _powwowMeeting.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _powwowMeeting.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _powwowMeeting.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _powwowMeeting.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<PowwowMeeting> toCacheModel() {
		return _powwowMeeting.toCacheModel();
	}

	@Override
	public int compareTo(PowwowMeeting powwowMeeting) {
		return _powwowMeeting.compareTo(powwowMeeting);
	}

	/**
	* Returns the status of this powwow meeting.
	*
	* @return the status of this powwow meeting
	*/
	@Override
	public int getStatus() {
		return _powwowMeeting.getStatus();
	}

	@Override
	public int hashCode() {
		return _powwowMeeting.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _powwowMeeting.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new PowwowMeetingWrapper((PowwowMeeting)_powwowMeeting.clone());
	}

	/**
	* Returns the description of this powwow meeting.
	*
	* @return the description of this powwow meeting
	*/
	@Override
	public java.lang.String getDescription() {
		return _powwowMeeting.getDescription();
	}

	/**
	* Returns the language ID of this powwow meeting.
	*
	* @return the language ID of this powwow meeting
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _powwowMeeting.getLanguageId();
	}

	/**
	* Returns the name of this powwow meeting.
	*
	* @return the name of this powwow meeting
	*/
	@Override
	public java.lang.String getName() {
		return _powwowMeeting.getName();
	}

	/**
	* Returns the provider type of this powwow meeting.
	*
	* @return the provider type of this powwow meeting
	*/
	@Override
	public java.lang.String getProviderType() {
		return _powwowMeeting.getProviderType();
	}

	/**
	* Returns the provider type metadata of this powwow meeting.
	*
	* @return the provider type metadata of this powwow meeting
	*/
	@Override
	public java.lang.String getProviderTypeMetadata() {
		return _powwowMeeting.getProviderTypeMetadata();
	}

	/**
	* Returns the user name of this powwow meeting.
	*
	* @return the user name of this powwow meeting
	*/
	@Override
	public java.lang.String getUserName() {
		return _powwowMeeting.getUserName();
	}

	/**
	* Returns the user uuid of this powwow meeting.
	*
	* @return the user uuid of this powwow meeting
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _powwowMeeting.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _powwowMeeting.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _powwowMeeting.toXmlString();
	}

	/**
	* Returns the create date of this powwow meeting.
	*
	* @return the create date of this powwow meeting
	*/
	@Override
	public Date getCreateDate() {
		return _powwowMeeting.getCreateDate();
	}

	/**
	* Returns the modified date of this powwow meeting.
	*
	* @return the modified date of this powwow meeting
	*/
	@Override
	public Date getModifiedDate() {
		return _powwowMeeting.getModifiedDate();
	}

	@Override
	public Map<java.lang.String, Serializable> getProviderTypeMetadataMap() {
		return _powwowMeeting.getProviderTypeMetadataMap();
	}

	/**
	* Returns the calendar booking ID of this powwow meeting.
	*
	* @return the calendar booking ID of this powwow meeting
	*/
	@Override
	public long getCalendarBookingId() {
		return _powwowMeeting.getCalendarBookingId();
	}

	/**
	* Returns the company ID of this powwow meeting.
	*
	* @return the company ID of this powwow meeting
	*/
	@Override
	public long getCompanyId() {
		return _powwowMeeting.getCompanyId();
	}

	/**
	* Returns the group ID of this powwow meeting.
	*
	* @return the group ID of this powwow meeting
	*/
	@Override
	public long getGroupId() {
		return _powwowMeeting.getGroupId();
	}

	/**
	* Returns the powwow meeting ID of this powwow meeting.
	*
	* @return the powwow meeting ID of this powwow meeting
	*/
	@Override
	public long getPowwowMeetingId() {
		return _powwowMeeting.getPowwowMeetingId();
	}

	/**
	* Returns the powwow server ID of this powwow meeting.
	*
	* @return the powwow server ID of this powwow meeting
	*/
	@Override
	public long getPowwowServerId() {
		return _powwowMeeting.getPowwowServerId();
	}

	/**
	* Returns the primary key of this powwow meeting.
	*
	* @return the primary key of this powwow meeting
	*/
	@Override
	public long getPrimaryKey() {
		return _powwowMeeting.getPrimaryKey();
	}

	/**
	* Returns the user ID of this powwow meeting.
	*
	* @return the user ID of this powwow meeting
	*/
	@Override
	public long getUserId() {
		return _powwowMeeting.getUserId();
	}

	@Override
	public void persist() {
		_powwowMeeting.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_powwowMeeting.setCachedModel(cachedModel);
	}

	/**
	* Sets the calendar booking ID of this powwow meeting.
	*
	* @param calendarBookingId the calendar booking ID of this powwow meeting
	*/
	@Override
	public void setCalendarBookingId(long calendarBookingId) {
		_powwowMeeting.setCalendarBookingId(calendarBookingId);
	}

	/**
	* Sets the company ID of this powwow meeting.
	*
	* @param companyId the company ID of this powwow meeting
	*/
	@Override
	public void setCompanyId(long companyId) {
		_powwowMeeting.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this powwow meeting.
	*
	* @param createDate the create date of this powwow meeting
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_powwowMeeting.setCreateDate(createDate);
	}

	/**
	* Sets the description of this powwow meeting.
	*
	* @param description the description of this powwow meeting
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_powwowMeeting.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_powwowMeeting.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_powwowMeeting.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_powwowMeeting.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this powwow meeting.
	*
	* @param groupId the group ID of this powwow meeting
	*/
	@Override
	public void setGroupId(long groupId) {
		_powwowMeeting.setGroupId(groupId);
	}

	/**
	* Sets the language ID of this powwow meeting.
	*
	* @param languageId the language ID of this powwow meeting
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_powwowMeeting.setLanguageId(languageId);
	}

	/**
	* Sets the modified date of this powwow meeting.
	*
	* @param modifiedDate the modified date of this powwow meeting
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_powwowMeeting.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this powwow meeting.
	*
	* @param name the name of this powwow meeting
	*/
	@Override
	public void setName(java.lang.String name) {
		_powwowMeeting.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_powwowMeeting.setNew(n);
	}

	/**
	* Sets the powwow meeting ID of this powwow meeting.
	*
	* @param powwowMeetingId the powwow meeting ID of this powwow meeting
	*/
	@Override
	public void setPowwowMeetingId(long powwowMeetingId) {
		_powwowMeeting.setPowwowMeetingId(powwowMeetingId);
	}

	/**
	* Sets the powwow server ID of this powwow meeting.
	*
	* @param powwowServerId the powwow server ID of this powwow meeting
	*/
	@Override
	public void setPowwowServerId(long powwowServerId) {
		_powwowMeeting.setPowwowServerId(powwowServerId);
	}

	/**
	* Sets the primary key of this powwow meeting.
	*
	* @param primaryKey the primary key of this powwow meeting
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_powwowMeeting.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_powwowMeeting.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the provider type of this powwow meeting.
	*
	* @param providerType the provider type of this powwow meeting
	*/
	@Override
	public void setProviderType(java.lang.String providerType) {
		_powwowMeeting.setProviderType(providerType);
	}

	/**
	* Sets the provider type metadata of this powwow meeting.
	*
	* @param providerTypeMetadata the provider type metadata of this powwow meeting
	*/
	@Override
	public void setProviderTypeMetadata(java.lang.String providerTypeMetadata) {
		_powwowMeeting.setProviderTypeMetadata(providerTypeMetadata);
	}

	/**
	* Sets the status of this powwow meeting.
	*
	* @param status the status of this powwow meeting
	*/
	@Override
	public void setStatus(int status) {
		_powwowMeeting.setStatus(status);
	}

	/**
	* Sets the user ID of this powwow meeting.
	*
	* @param userId the user ID of this powwow meeting
	*/
	@Override
	public void setUserId(long userId) {
		_powwowMeeting.setUserId(userId);
	}

	/**
	* Sets the user name of this powwow meeting.
	*
	* @param userName the user name of this powwow meeting
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_powwowMeeting.setUserName(userName);
	}

	/**
	* Sets the user uuid of this powwow meeting.
	*
	* @param userUuid the user uuid of this powwow meeting
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_powwowMeeting.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PowwowMeetingWrapper)) {
			return false;
		}

		PowwowMeetingWrapper powwowMeetingWrapper = (PowwowMeetingWrapper)obj;

		if (Objects.equals(_powwowMeeting, powwowMeetingWrapper._powwowMeeting)) {
			return true;
		}

		return false;
	}

	@Override
	public PowwowMeeting getWrappedModel() {
		return _powwowMeeting;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _powwowMeeting.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _powwowMeeting.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_powwowMeeting.resetOriginalValues();
	}

	private final PowwowMeeting _powwowMeeting;
}
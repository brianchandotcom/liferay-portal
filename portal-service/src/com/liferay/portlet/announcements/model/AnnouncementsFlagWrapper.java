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

package com.liferay.portlet.announcements.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AnnouncementsFlag}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsFlag
 * @generated
 */
@ProviderType
public class AnnouncementsFlagWrapper implements AnnouncementsFlag,
	ModelWrapper<AnnouncementsFlag> {
	public AnnouncementsFlagWrapper(AnnouncementsFlag announcementsFlag) {
		_announcementsFlag = announcementsFlag;
	}

	@Override
	public Class<?> getModelClass() {
		return AnnouncementsFlag.class;
	}

	@Override
	public String getModelClassName() {
		return AnnouncementsFlag.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("flagId", getFlagId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("entryId", getEntryId());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long flagId = (Long)attributes.get("flagId");

		if (flagId != null) {
			setFlagId(flagId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}

		Integer value = (Integer)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new AnnouncementsFlagWrapper((AnnouncementsFlag)_announcementsFlag.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag) {
		return _announcementsFlag.compareTo(announcementsFlag);
	}

	/**
	* Returns the company ID of this announcements flag.
	*
	* @return the company ID of this announcements flag
	*/
	@Override
	public long getCompanyId() {
		return _announcementsFlag.getCompanyId();
	}

	/**
	* Returns the create date of this announcements flag.
	*
	* @return the create date of this announcements flag
	*/
	@Override
	public Date getCreateDate() {
		return _announcementsFlag.getCreateDate();
	}

	/**
	* Returns the entry ID of this announcements flag.
	*
	* @return the entry ID of this announcements flag
	*/
	@Override
	public long getEntryId() {
		return _announcementsFlag.getEntryId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _announcementsFlag.getExpandoBridge();
	}

	/**
	* Returns the flag ID of this announcements flag.
	*
	* @return the flag ID of this announcements flag
	*/
	@Override
	public long getFlagId() {
		return _announcementsFlag.getFlagId();
	}

	/**
	* Returns the primary key of this announcements flag.
	*
	* @return the primary key of this announcements flag
	*/
	@Override
	public long getPrimaryKey() {
		return _announcementsFlag.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _announcementsFlag.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this announcements flag.
	*
	* @return the user ID of this announcements flag
	*/
	@Override
	public long getUserId() {
		return _announcementsFlag.getUserId();
	}

	/**
	* Returns the user uuid of this announcements flag.
	*
	* @return the user uuid of this announcements flag
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _announcementsFlag.getUserUuid();
	}

	/**
	* Returns the value of this announcements flag.
	*
	* @return the value of this announcements flag
	*/
	@Override
	public int getValue() {
		return _announcementsFlag.getValue();
	}

	@Override
	public int hashCode() {
		return _announcementsFlag.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _announcementsFlag.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _announcementsFlag.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _announcementsFlag.isNew();
	}

	@Override
	public void persist() {
		_announcementsFlag.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_announcementsFlag.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this announcements flag.
	*
	* @param companyId the company ID of this announcements flag
	*/
	@Override
	public void setCompanyId(long companyId) {
		_announcementsFlag.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this announcements flag.
	*
	* @param createDate the create date of this announcements flag
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_announcementsFlag.setCreateDate(createDate);
	}

	/**
	* Sets the entry ID of this announcements flag.
	*
	* @param entryId the entry ID of this announcements flag
	*/
	@Override
	public void setEntryId(long entryId) {
		_announcementsFlag.setEntryId(entryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_announcementsFlag.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_announcementsFlag.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_announcementsFlag.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the flag ID of this announcements flag.
	*
	* @param flagId the flag ID of this announcements flag
	*/
	@Override
	public void setFlagId(long flagId) {
		_announcementsFlag.setFlagId(flagId);
	}

	@Override
	public void setNew(boolean n) {
		_announcementsFlag.setNew(n);
	}

	/**
	* Sets the primary key of this announcements flag.
	*
	* @param primaryKey the primary key of this announcements flag
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_announcementsFlag.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_announcementsFlag.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this announcements flag.
	*
	* @param userId the user ID of this announcements flag
	*/
	@Override
	public void setUserId(long userId) {
		_announcementsFlag.setUserId(userId);
	}

	/**
	* Sets the user uuid of this announcements flag.
	*
	* @param userUuid the user uuid of this announcements flag
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_announcementsFlag.setUserUuid(userUuid);
	}

	/**
	* Sets the value of this announcements flag.
	*
	* @param value the value of this announcements flag
	*/
	@Override
	public void setValue(int value) {
		_announcementsFlag.setValue(value);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.announcements.model.AnnouncementsFlag> toCacheModel() {
		return _announcementsFlag.toCacheModel();
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsFlag toEscapedModel() {
		return new AnnouncementsFlagWrapper(_announcementsFlag.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _announcementsFlag.toString();
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsFlag toUnescapedModel() {
		return new AnnouncementsFlagWrapper(_announcementsFlag.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _announcementsFlag.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AnnouncementsFlagWrapper)) {
			return false;
		}

		AnnouncementsFlagWrapper announcementsFlagWrapper = (AnnouncementsFlagWrapper)obj;

		if (Validator.equals(_announcementsFlag,
					announcementsFlagWrapper._announcementsFlag)) {
			return true;
		}

		return false;
	}

	@Override
	public AnnouncementsFlag getWrappedModel() {
		return _announcementsFlag;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _announcementsFlag.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _announcementsFlag.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_announcementsFlag.resetOriginalValues();
	}

	private final AnnouncementsFlag _announcementsFlag;
}
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

package com.liferay.portlet.announcements.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AnnouncementsFlag}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlag
 * @generated
 */
public class AnnouncementsFlagWrapper implements AnnouncementsFlag,
	ModelWrapper<AnnouncementsFlag> {
	public AnnouncementsFlagWrapper(AnnouncementsFlag announcementsFlag) {
		_announcementsFlag = announcementsFlag;
	}

	public Class<?> getModelClass() {
		return AnnouncementsFlag.class;
	}

	public String getModelClassName() {
		return AnnouncementsFlag.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("flagId", getFlagId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("entryId", getEntryId());
		attributes.put("value", getValue());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long flagId = (Long)attributes.get("flagId");

		if (flagId != null) {
			setFlagId(flagId);
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

	/**
	* Returns the primary key of this announcements flag.
	*
	* @return the primary key of this announcements flag
	*/
	public long getPrimaryKey() {
		return _announcementsFlag.getPrimaryKey();
	}

	/**
	* Sets the primary key of this announcements flag.
	*
	* @param primaryKey the primary key of this announcements flag
	*/
	public void setPrimaryKey(long primaryKey) {
		_announcementsFlag.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the flag ID of this announcements flag.
	*
	* @return the flag ID of this announcements flag
	*/
	public long getFlagId() {
		return _announcementsFlag.getFlagId();
	}

	/**
	* Sets the flag ID of this announcements flag.
	*
	* @param flagId the flag ID of this announcements flag
	*/
	public void setFlagId(long flagId) {
		_announcementsFlag.setFlagId(flagId);
	}

	/**
	* Returns the user ID of this announcements flag.
	*
	* @return the user ID of this announcements flag
	*/
	public long getUserId() {
		return _announcementsFlag.getUserId();
	}

	/**
	* Sets the user ID of this announcements flag.
	*
	* @param userId the user ID of this announcements flag
	*/
	public void setUserId(long userId) {
		_announcementsFlag.setUserId(userId);
	}

	/**
	* Returns the user uuid of this announcements flag.
	*
	* @return the user uuid of this announcements flag
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlag.getUserUuid();
	}

	/**
	* Sets the user uuid of this announcements flag.
	*
	* @param userUuid the user uuid of this announcements flag
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_announcementsFlag.setUserUuid(userUuid);
	}

	/**
	* Returns the create date of this announcements flag.
	*
	* @return the create date of this announcements flag
	*/
	public java.util.Date getCreateDate() {
		return _announcementsFlag.getCreateDate();
	}

	/**
	* Sets the create date of this announcements flag.
	*
	* @param createDate the create date of this announcements flag
	*/
	public void setCreateDate(java.util.Date createDate) {
		_announcementsFlag.setCreateDate(createDate);
	}

	/**
	* Returns the entry ID of this announcements flag.
	*
	* @return the entry ID of this announcements flag
	*/
	public long getEntryId() {
		return _announcementsFlag.getEntryId();
	}

	/**
	* Sets the entry ID of this announcements flag.
	*
	* @param entryId the entry ID of this announcements flag
	*/
	public void setEntryId(long entryId) {
		_announcementsFlag.setEntryId(entryId);
	}

	/**
	* Returns the value of this announcements flag.
	*
	* @return the value of this announcements flag
	*/
	public int getValue() {
		return _announcementsFlag.getValue();
	}

	/**
	* Sets the value of this announcements flag.
	*
	* @param value the value of this announcements flag
	*/
	public void setValue(int value) {
		_announcementsFlag.setValue(value);
	}

	public boolean isNew() {
		return _announcementsFlag.isNew();
	}

	public void setNew(boolean n) {
		_announcementsFlag.setNew(n);
	}

	public boolean isCachedModel() {
		return _announcementsFlag.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_announcementsFlag.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _announcementsFlag.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _announcementsFlag.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_announcementsFlag.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _announcementsFlag.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_announcementsFlag.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_announcementsFlag.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_announcementsFlag.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AnnouncementsFlagWrapper((AnnouncementsFlag)_announcementsFlag.clone());
	}

	public int compareTo(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag) {
		return _announcementsFlag.compareTo(announcementsFlag);
	}

	@Override
	public int hashCode() {
		return _announcementsFlag.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.announcements.model.AnnouncementsFlag> toCacheModel() {
		return _announcementsFlag.toCacheModel();
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag toEscapedModel() {
		return new AnnouncementsFlagWrapper(_announcementsFlag.toEscapedModel());
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag toUnescapedModel() {
		return new AnnouncementsFlagWrapper(_announcementsFlag.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _announcementsFlag.toString();
	}

	public java.lang.String toXmlString() {
		return _announcementsFlag.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsFlag.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public AnnouncementsFlag getWrappedAnnouncementsFlag() {
		return _announcementsFlag;
	}

	public AnnouncementsFlag getWrappedModel() {
		return _announcementsFlag;
	}

	public void resetOriginalValues() {
		_announcementsFlag.resetOriginalValues();
	}

	private AnnouncementsFlag _announcementsFlag;
}
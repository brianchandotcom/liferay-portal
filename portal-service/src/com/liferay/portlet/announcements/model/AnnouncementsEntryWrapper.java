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

package com.liferay.portlet.announcements.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AnnouncementsEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntry
 * @generated
 */
public class AnnouncementsEntryWrapper implements AnnouncementsEntry,
	ModelWrapper<AnnouncementsEntry> {
	public AnnouncementsEntryWrapper(AnnouncementsEntry announcementsEntry) {
		_announcementsEntry = announcementsEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return AnnouncementsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AnnouncementsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("entryId", getEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("title", getTitle());
		attributes.put("content", getContent());
		attributes.put("url", getUrl());
		attributes.put("type", getType());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("priority", getPriority());
		attributes.put("alert", getAlert());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean alert = (Boolean)attributes.get("alert");

		if (alert != null) {
			setAlert(alert);
		}
	}

	/**
	* Returns the primary key of this announcements entry.
	*
	* @return the primary key of this announcements entry
	*/
	@Override
	public long getPrimaryKey() {
		return _announcementsEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this announcements entry.
	*
	* @param primaryKey the primary key of this announcements entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_announcementsEntry.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this announcements entry.
	*
	* @return the uuid of this announcements entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _announcementsEntry.getUuid();
	}

	/**
	* Sets the uuid of this announcements entry.
	*
	* @param uuid the uuid of this announcements entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_announcementsEntry.setUuid(uuid);
	}

	/**
	* Returns the entry ID of this announcements entry.
	*
	* @return the entry ID of this announcements entry
	*/
	@Override
	public long getEntryId() {
		return _announcementsEntry.getEntryId();
	}

	/**
	* Sets the entry ID of this announcements entry.
	*
	* @param entryId the entry ID of this announcements entry
	*/
	@Override
	public void setEntryId(long entryId) {
		_announcementsEntry.setEntryId(entryId);
	}

	/**
	* Returns the company ID of this announcements entry.
	*
	* @return the company ID of this announcements entry
	*/
	@Override
	public long getCompanyId() {
		return _announcementsEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this announcements entry.
	*
	* @param companyId the company ID of this announcements entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_announcementsEntry.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this announcements entry.
	*
	* @return the user ID of this announcements entry
	*/
	@Override
	public long getUserId() {
		return _announcementsEntry.getUserId();
	}

	/**
	* Sets the user ID of this announcements entry.
	*
	* @param userId the user ID of this announcements entry
	*/
	@Override
	public void setUserId(long userId) {
		_announcementsEntry.setUserId(userId);
	}

	/**
	* Returns the user uuid of this announcements entry.
	*
	* @return the user uuid of this announcements entry
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this announcements entry.
	*
	* @param userUuid the user uuid of this announcements entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_announcementsEntry.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this announcements entry.
	*
	* @return the user name of this announcements entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _announcementsEntry.getUserName();
	}

	/**
	* Sets the user name of this announcements entry.
	*
	* @param userName the user name of this announcements entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_announcementsEntry.setUserName(userName);
	}

	/**
	* Returns the create date of this announcements entry.
	*
	* @return the create date of this announcements entry
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _announcementsEntry.getCreateDate();
	}

	/**
	* Sets the create date of this announcements entry.
	*
	* @param createDate the create date of this announcements entry
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_announcementsEntry.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this announcements entry.
	*
	* @return the modified date of this announcements entry
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _announcementsEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this announcements entry.
	*
	* @param modifiedDate the modified date of this announcements entry
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_announcementsEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this announcements entry.
	*
	* @return the fully qualified class name of this announcements entry
	*/
	@Override
	public java.lang.String getClassName() {
		return _announcementsEntry.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_announcementsEntry.setClassName(className);
	}

	/**
	* Returns the class name ID of this announcements entry.
	*
	* @return the class name ID of this announcements entry
	*/
	@Override
	public long getClassNameId() {
		return _announcementsEntry.getClassNameId();
	}

	/**
	* Sets the class name ID of this announcements entry.
	*
	* @param classNameId the class name ID of this announcements entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_announcementsEntry.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this announcements entry.
	*
	* @return the class p k of this announcements entry
	*/
	@Override
	public long getClassPK() {
		return _announcementsEntry.getClassPK();
	}

	/**
	* Sets the class p k of this announcements entry.
	*
	* @param classPK the class p k of this announcements entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_announcementsEntry.setClassPK(classPK);
	}

	/**
	* Returns the title of this announcements entry.
	*
	* @return the title of this announcements entry
	*/
	@Override
	public java.lang.String getTitle() {
		return _announcementsEntry.getTitle();
	}

	/**
	* Sets the title of this announcements entry.
	*
	* @param title the title of this announcements entry
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_announcementsEntry.setTitle(title);
	}

	/**
	* Returns the content of this announcements entry.
	*
	* @return the content of this announcements entry
	*/
	@Override
	public java.lang.String getContent() {
		return _announcementsEntry.getContent();
	}

	/**
	* Sets the content of this announcements entry.
	*
	* @param content the content of this announcements entry
	*/
	@Override
	public void setContent(java.lang.String content) {
		_announcementsEntry.setContent(content);
	}

	/**
	* Returns the url of this announcements entry.
	*
	* @return the url of this announcements entry
	*/
	@Override
	public java.lang.String getUrl() {
		return _announcementsEntry.getUrl();
	}

	/**
	* Sets the url of this announcements entry.
	*
	* @param url the url of this announcements entry
	*/
	@Override
	public void setUrl(java.lang.String url) {
		_announcementsEntry.setUrl(url);
	}

	/**
	* Returns the type of this announcements entry.
	*
	* @return the type of this announcements entry
	*/
	@Override
	public java.lang.String getType() {
		return _announcementsEntry.getType();
	}

	/**
	* Sets the type of this announcements entry.
	*
	* @param type the type of this announcements entry
	*/
	@Override
	public void setType(java.lang.String type) {
		_announcementsEntry.setType(type);
	}

	/**
	* Returns the display date of this announcements entry.
	*
	* @return the display date of this announcements entry
	*/
	@Override
	public java.util.Date getDisplayDate() {
		return _announcementsEntry.getDisplayDate();
	}

	/**
	* Sets the display date of this announcements entry.
	*
	* @param displayDate the display date of this announcements entry
	*/
	@Override
	public void setDisplayDate(java.util.Date displayDate) {
		_announcementsEntry.setDisplayDate(displayDate);
	}

	/**
	* Returns the expiration date of this announcements entry.
	*
	* @return the expiration date of this announcements entry
	*/
	@Override
	public java.util.Date getExpirationDate() {
		return _announcementsEntry.getExpirationDate();
	}

	/**
	* Sets the expiration date of this announcements entry.
	*
	* @param expirationDate the expiration date of this announcements entry
	*/
	@Override
	public void setExpirationDate(java.util.Date expirationDate) {
		_announcementsEntry.setExpirationDate(expirationDate);
	}

	/**
	* Returns the priority of this announcements entry.
	*
	* @return the priority of this announcements entry
	*/
	@Override
	public int getPriority() {
		return _announcementsEntry.getPriority();
	}

	/**
	* Sets the priority of this announcements entry.
	*
	* @param priority the priority of this announcements entry
	*/
	@Override
	public void setPriority(int priority) {
		_announcementsEntry.setPriority(priority);
	}

	/**
	* Returns the alert of this announcements entry.
	*
	* @return the alert of this announcements entry
	*/
	@Override
	public boolean getAlert() {
		return _announcementsEntry.getAlert();
	}

	/**
	* Returns <code>true</code> if this announcements entry is alert.
	*
	* @return <code>true</code> if this announcements entry is alert; <code>false</code> otherwise
	*/
	@Override
	public boolean isAlert() {
		return _announcementsEntry.isAlert();
	}

	/**
	* Sets whether this announcements entry is alert.
	*
	* @param alert the alert of this announcements entry
	*/
	@Override
	public void setAlert(boolean alert) {
		_announcementsEntry.setAlert(alert);
	}

	@Override
	public boolean isNew() {
		return _announcementsEntry.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_announcementsEntry.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _announcementsEntry.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_announcementsEntry.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _announcementsEntry.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _announcementsEntry.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_announcementsEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _announcementsEntry.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_announcementsEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_announcementsEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_announcementsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AnnouncementsEntryWrapper((AnnouncementsEntry)_announcementsEntry.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry) {
		return _announcementsEntry.compareTo(announcementsEntry);
	}

	@Override
	public int hashCode() {
		return _announcementsEntry.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.announcements.model.AnnouncementsEntry> toCacheModel() {
		return _announcementsEntry.toCacheModel();
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry toEscapedModel() {
		return new AnnouncementsEntryWrapper(_announcementsEntry.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.announcements.model.AnnouncementsEntry toUnescapedModel() {
		return new AnnouncementsEntryWrapper(_announcementsEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _announcementsEntry.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _announcementsEntry.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsEntry.persist();
	}

	@Override
	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsEntry.getGroupId();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public AnnouncementsEntry getWrappedAnnouncementsEntry() {
		return _announcementsEntry;
	}

	@Override
	public AnnouncementsEntry getWrappedModel() {
		return _announcementsEntry;
	}

	@Override
	public void resetOriginalValues() {
		_announcementsEntry.resetOriginalValues();
	}

	private AnnouncementsEntry _announcementsEntry;
}
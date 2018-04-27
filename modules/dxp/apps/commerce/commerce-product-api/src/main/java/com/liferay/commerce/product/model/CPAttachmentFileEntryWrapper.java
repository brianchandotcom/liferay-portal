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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CPAttachmentFileEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntry
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntryWrapper implements CPAttachmentFileEntry,
	ModelWrapper<CPAttachmentFileEntry> {
	public CPAttachmentFileEntryWrapper(
		CPAttachmentFileEntry cpAttachmentFileEntry) {
		_cpAttachmentFileEntry = cpAttachmentFileEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CPAttachmentFileEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CPAttachmentFileEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPAttachmentFileEntryId", getCPAttachmentFileEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("title", getTitle());
		attributes.put("json", getJson());
		attributes.put("priority", getPriority());
		attributes.put("type", getType());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPAttachmentFileEntryId = (Long)attributes.get(
				"CPAttachmentFileEntryId");

		if (CPAttachmentFileEntryId != null) {
			setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJson(json);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public Object clone() {
		return new CPAttachmentFileEntryWrapper((CPAttachmentFileEntry)_cpAttachmentFileEntry.clone());
	}

	@Override
	public int compareTo(CPAttachmentFileEntry cpAttachmentFileEntry) {
		return _cpAttachmentFileEntry.compareTo(cpAttachmentFileEntry);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _cpAttachmentFileEntry.getAvailableLanguageIds();
	}

	/**
	* Returns the fully qualified class name of this cp attachment file entry.
	*
	* @return the fully qualified class name of this cp attachment file entry
	*/
	@Override
	public String getClassName() {
		return _cpAttachmentFileEntry.getClassName();
	}

	/**
	* Returns the class name ID of this cp attachment file entry.
	*
	* @return the class name ID of this cp attachment file entry
	*/
	@Override
	public long getClassNameId() {
		return _cpAttachmentFileEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this cp attachment file entry.
	*
	* @return the class pk of this cp attachment file entry
	*/
	@Override
	public long getClassPK() {
		return _cpAttachmentFileEntry.getClassPK();
	}

	/**
	* Returns the company ID of this cp attachment file entry.
	*
	* @return the company ID of this cp attachment file entry
	*/
	@Override
	public long getCompanyId() {
		return _cpAttachmentFileEntry.getCompanyId();
	}

	/**
	* Returns the cp attachment file entry ID of this cp attachment file entry.
	*
	* @return the cp attachment file entry ID of this cp attachment file entry
	*/
	@Override
	public long getCPAttachmentFileEntryId() {
		return _cpAttachmentFileEntry.getCPAttachmentFileEntryId();
	}

	/**
	* Returns the create date of this cp attachment file entry.
	*
	* @return the create date of this cp attachment file entry
	*/
	@Override
	public Date getCreateDate() {
		return _cpAttachmentFileEntry.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return _cpAttachmentFileEntry.getDefaultLanguageId();
	}

	/**
	* Returns the display date of this cp attachment file entry.
	*
	* @return the display date of this cp attachment file entry
	*/
	@Override
	public Date getDisplayDate() {
		return _cpAttachmentFileEntry.getDisplayDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpAttachmentFileEntry.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this cp attachment file entry.
	*
	* @return the expiration date of this cp attachment file entry
	*/
	@Override
	public Date getExpirationDate() {
		return _cpAttachmentFileEntry.getExpirationDate();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAttachmentFileEntry.getFileEntry();
	}

	/**
	* Returns the file entry ID of this cp attachment file entry.
	*
	* @return the file entry ID of this cp attachment file entry
	*/
	@Override
	public long getFileEntryId() {
		return _cpAttachmentFileEntry.getFileEntryId();
	}

	/**
	* Returns the group ID of this cp attachment file entry.
	*
	* @return the group ID of this cp attachment file entry
	*/
	@Override
	public long getGroupId() {
		return _cpAttachmentFileEntry.getGroupId();
	}

	/**
	* Returns the json of this cp attachment file entry.
	*
	* @return the json of this cp attachment file entry
	*/
	@Override
	public String getJson() {
		return _cpAttachmentFileEntry.getJson();
	}

	/**
	* Returns the last publish date of this cp attachment file entry.
	*
	* @return the last publish date of this cp attachment file entry
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpAttachmentFileEntry.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp attachment file entry.
	*
	* @return the modified date of this cp attachment file entry
	*/
	@Override
	public Date getModifiedDate() {
		return _cpAttachmentFileEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp attachment file entry.
	*
	* @return the primary key of this cp attachment file entry
	*/
	@Override
	public long getPrimaryKey() {
		return _cpAttachmentFileEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpAttachmentFileEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this cp attachment file entry.
	*
	* @return the priority of this cp attachment file entry
	*/
	@Override
	public double getPriority() {
		return _cpAttachmentFileEntry.getPriority();
	}

	/**
	* Returns the status of this cp attachment file entry.
	*
	* @return the status of this cp attachment file entry
	*/
	@Override
	public int getStatus() {
		return _cpAttachmentFileEntry.getStatus();
	}

	/**
	* Returns the status by user ID of this cp attachment file entry.
	*
	* @return the status by user ID of this cp attachment file entry
	*/
	@Override
	public long getStatusByUserId() {
		return _cpAttachmentFileEntry.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this cp attachment file entry.
	*
	* @return the status by user name of this cp attachment file entry
	*/
	@Override
	public String getStatusByUserName() {
		return _cpAttachmentFileEntry.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this cp attachment file entry.
	*
	* @return the status by user uuid of this cp attachment file entry
	*/
	@Override
	public String getStatusByUserUuid() {
		return _cpAttachmentFileEntry.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this cp attachment file entry.
	*
	* @return the status date of this cp attachment file entry
	*/
	@Override
	public Date getStatusDate() {
		return _cpAttachmentFileEntry.getStatusDate();
	}

	/**
	* Returns the title of this cp attachment file entry.
	*
	* @return the title of this cp attachment file entry
	*/
	@Override
	public String getTitle() {
		return _cpAttachmentFileEntry.getTitle();
	}

	/**
	* Returns the localized title of this cp attachment file entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this cp attachment file entry
	*/
	@Override
	public String getTitle(java.util.Locale locale) {
		return _cpAttachmentFileEntry.getTitle(locale);
	}

	/**
	* Returns the localized title of this cp attachment file entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp attachment file entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return _cpAttachmentFileEntry.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this cp attachment file entry in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this cp attachment file entry
	*/
	@Override
	public String getTitle(String languageId) {
		return _cpAttachmentFileEntry.getTitle(languageId);
	}

	/**
	* Returns the localized title of this cp attachment file entry in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp attachment file entry
	*/
	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return _cpAttachmentFileEntry.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return _cpAttachmentFileEntry.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleCurrentValue() {
		return _cpAttachmentFileEntry.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this cp attachment file entry.
	*
	* @return the locales and localized titles of this cp attachment file entry
	*/
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return _cpAttachmentFileEntry.getTitleMap();
	}

	/**
	* Returns the type of this cp attachment file entry.
	*
	* @return the type of this cp attachment file entry
	*/
	@Override
	public int getType() {
		return _cpAttachmentFileEntry.getType();
	}

	/**
	* Returns the user ID of this cp attachment file entry.
	*
	* @return the user ID of this cp attachment file entry
	*/
	@Override
	public long getUserId() {
		return _cpAttachmentFileEntry.getUserId();
	}

	/**
	* Returns the user name of this cp attachment file entry.
	*
	* @return the user name of this cp attachment file entry
	*/
	@Override
	public String getUserName() {
		return _cpAttachmentFileEntry.getUserName();
	}

	/**
	* Returns the user uuid of this cp attachment file entry.
	*
	* @return the user uuid of this cp attachment file entry
	*/
	@Override
	public String getUserUuid() {
		return _cpAttachmentFileEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this cp attachment file entry.
	*
	* @return the uuid of this cp attachment file entry
	*/
	@Override
	public String getUuid() {
		return _cpAttachmentFileEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpAttachmentFileEntry.hashCode();
	}

	/**
	* Returns <code>true</code> if this cp attachment file entry is approved.
	*
	* @return <code>true</code> if this cp attachment file entry is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _cpAttachmentFileEntry.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _cpAttachmentFileEntry.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this cp attachment file entry is denied.
	*
	* @return <code>true</code> if this cp attachment file entry is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _cpAttachmentFileEntry.isDenied();
	}

	/**
	* Returns <code>true</code> if this cp attachment file entry is a draft.
	*
	* @return <code>true</code> if this cp attachment file entry is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _cpAttachmentFileEntry.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpAttachmentFileEntry.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this cp attachment file entry is expired.
	*
	* @return <code>true</code> if this cp attachment file entry is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _cpAttachmentFileEntry.isExpired();
	}

	/**
	* Returns <code>true</code> if this cp attachment file entry is inactive.
	*
	* @return <code>true</code> if this cp attachment file entry is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _cpAttachmentFileEntry.isInactive();
	}

	/**
	* Returns <code>true</code> if this cp attachment file entry is incomplete.
	*
	* @return <code>true</code> if this cp attachment file entry is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _cpAttachmentFileEntry.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _cpAttachmentFileEntry.isNew();
	}

	/**
	* Returns <code>true</code> if this cp attachment file entry is pending.
	*
	* @return <code>true</code> if this cp attachment file entry is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _cpAttachmentFileEntry.isPending();
	}

	/**
	* Returns <code>true</code> if this cp attachment file entry is scheduled.
	*
	* @return <code>true</code> if this cp attachment file entry is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _cpAttachmentFileEntry.isScheduled();
	}

	@Override
	public void persist() {
		_cpAttachmentFileEntry.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpAttachmentFileEntry.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpAttachmentFileEntry.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpAttachmentFileEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_cpAttachmentFileEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this cp attachment file entry.
	*
	* @param classNameId the class name ID of this cp attachment file entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_cpAttachmentFileEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this cp attachment file entry.
	*
	* @param classPK the class pk of this cp attachment file entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_cpAttachmentFileEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this cp attachment file entry.
	*
	* @param companyId the company ID of this cp attachment file entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpAttachmentFileEntry.setCompanyId(companyId);
	}

	/**
	* Sets the cp attachment file entry ID of this cp attachment file entry.
	*
	* @param CPAttachmentFileEntryId the cp attachment file entry ID of this cp attachment file entry
	*/
	@Override
	public void setCPAttachmentFileEntryId(long CPAttachmentFileEntryId) {
		_cpAttachmentFileEntry.setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
	}

	/**
	* Sets the create date of this cp attachment file entry.
	*
	* @param createDate the create date of this cp attachment file entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpAttachmentFileEntry.setCreateDate(createDate);
	}

	/**
	* Sets the display date of this cp attachment file entry.
	*
	* @param displayDate the display date of this cp attachment file entry
	*/
	@Override
	public void setDisplayDate(Date displayDate) {
		_cpAttachmentFileEntry.setDisplayDate(displayDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpAttachmentFileEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpAttachmentFileEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpAttachmentFileEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this cp attachment file entry.
	*
	* @param expirationDate the expiration date of this cp attachment file entry
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_cpAttachmentFileEntry.setExpirationDate(expirationDate);
	}

	/**
	* Sets the file entry ID of this cp attachment file entry.
	*
	* @param fileEntryId the file entry ID of this cp attachment file entry
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_cpAttachmentFileEntry.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the group ID of this cp attachment file entry.
	*
	* @param groupId the group ID of this cp attachment file entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpAttachmentFileEntry.setGroupId(groupId);
	}

	/**
	* Sets the json of this cp attachment file entry.
	*
	* @param json the json of this cp attachment file entry
	*/
	@Override
	public void setJson(String json) {
		_cpAttachmentFileEntry.setJson(json);
	}

	/**
	* Sets the last publish date of this cp attachment file entry.
	*
	* @param lastPublishDate the last publish date of this cp attachment file entry
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpAttachmentFileEntry.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cp attachment file entry.
	*
	* @param modifiedDate the modified date of this cp attachment file entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpAttachmentFileEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpAttachmentFileEntry.setNew(n);
	}

	/**
	* Sets the primary key of this cp attachment file entry.
	*
	* @param primaryKey the primary key of this cp attachment file entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpAttachmentFileEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpAttachmentFileEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this cp attachment file entry.
	*
	* @param priority the priority of this cp attachment file entry
	*/
	@Override
	public void setPriority(double priority) {
		_cpAttachmentFileEntry.setPriority(priority);
	}

	/**
	* Sets the status of this cp attachment file entry.
	*
	* @param status the status of this cp attachment file entry
	*/
	@Override
	public void setStatus(int status) {
		_cpAttachmentFileEntry.setStatus(status);
	}

	/**
	* Sets the status by user ID of this cp attachment file entry.
	*
	* @param statusByUserId the status by user ID of this cp attachment file entry
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_cpAttachmentFileEntry.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this cp attachment file entry.
	*
	* @param statusByUserName the status by user name of this cp attachment file entry
	*/
	@Override
	public void setStatusByUserName(String statusByUserName) {
		_cpAttachmentFileEntry.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this cp attachment file entry.
	*
	* @param statusByUserUuid the status by user uuid of this cp attachment file entry
	*/
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_cpAttachmentFileEntry.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this cp attachment file entry.
	*
	* @param statusDate the status date of this cp attachment file entry
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_cpAttachmentFileEntry.setStatusDate(statusDate);
	}

	/**
	* Sets the title of this cp attachment file entry.
	*
	* @param title the title of this cp attachment file entry
	*/
	@Override
	public void setTitle(String title) {
		_cpAttachmentFileEntry.setTitle(title);
	}

	/**
	* Sets the localized title of this cp attachment file entry in the language.
	*
	* @param title the localized title of this cp attachment file entry
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		_cpAttachmentFileEntry.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this cp attachment file entry in the language, and sets the default locale.
	*
	* @param title the localized title of this cp attachment file entry
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpAttachmentFileEntry.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		_cpAttachmentFileEntry.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this cp attachment file entry from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this cp attachment file entry
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		_cpAttachmentFileEntry.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this cp attachment file entry from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this cp attachment file entry
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {
		_cpAttachmentFileEntry.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the type of this cp attachment file entry.
	*
	* @param type the type of this cp attachment file entry
	*/
	@Override
	public void setType(int type) {
		_cpAttachmentFileEntry.setType(type);
	}

	/**
	* Sets the user ID of this cp attachment file entry.
	*
	* @param userId the user ID of this cp attachment file entry
	*/
	@Override
	public void setUserId(long userId) {
		_cpAttachmentFileEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this cp attachment file entry.
	*
	* @param userName the user name of this cp attachment file entry
	*/
	@Override
	public void setUserName(String userName) {
		_cpAttachmentFileEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp attachment file entry.
	*
	* @param userUuid the user uuid of this cp attachment file entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpAttachmentFileEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp attachment file entry.
	*
	* @param uuid the uuid of this cp attachment file entry
	*/
	@Override
	public void setUuid(String uuid) {
		_cpAttachmentFileEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPAttachmentFileEntry> toCacheModel() {
		return _cpAttachmentFileEntry.toCacheModel();
	}

	@Override
	public CPAttachmentFileEntry toEscapedModel() {
		return new CPAttachmentFileEntryWrapper(_cpAttachmentFileEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpAttachmentFileEntry.toString();
	}

	@Override
	public CPAttachmentFileEntry toUnescapedModel() {
		return new CPAttachmentFileEntryWrapper(_cpAttachmentFileEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpAttachmentFileEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPAttachmentFileEntryWrapper)) {
			return false;
		}

		CPAttachmentFileEntryWrapper cpAttachmentFileEntryWrapper = (CPAttachmentFileEntryWrapper)obj;

		if (Objects.equals(_cpAttachmentFileEntry,
					cpAttachmentFileEntryWrapper._cpAttachmentFileEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpAttachmentFileEntry.getStagedModelType();
	}

	@Override
	public CPAttachmentFileEntry getWrappedModel() {
		return _cpAttachmentFileEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpAttachmentFileEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpAttachmentFileEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpAttachmentFileEntry.resetOriginalValues();
	}

	private final CPAttachmentFileEntry _cpAttachmentFileEntry;
}
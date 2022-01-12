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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CPAttachmentFileEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntry
 * @generated
 */
public class CPAttachmentFileEntryWrapper
	extends BaseModelWrapper<CPAttachmentFileEntry>
	implements CPAttachmentFileEntry, ModelWrapper<CPAttachmentFileEntry> {

	public CPAttachmentFileEntryWrapper(
		CPAttachmentFileEntry cpAttachmentFileEntry) {

		super(cpAttachmentFileEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
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
		attributes.put("cdnEnabled", isCDNEnabled());
		attributes.put("cdnURL", getCDNURL());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
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

		Boolean cdnEnabled = (Boolean)attributes.get("cdnEnabled");

		if (cdnEnabled != null) {
			setCDNEnabled(cdnEnabled);
		}

		String cdnURL = (String)attributes.get("cdnURL");

		if (cdnURL != null) {
			setCDNURL(cdnURL);
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
	public CPAttachmentFileEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry fetchFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.fetchFileEntry();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the cdn enabled of this cp attachment file entry.
	 *
	 * @return the cdn enabled of this cp attachment file entry
	 */
	@Override
	public boolean getCDNEnabled() {
		return model.getCDNEnabled();
	}

	/**
	 * Returns the cdn url of this cp attachment file entry.
	 *
	 * @return the cdn url of this cp attachment file entry
	 */
	@Override
	public String getCDNURL() {
		return model.getCDNURL();
	}

	/**
	 * Returns the fully qualified class name of this cp attachment file entry.
	 *
	 * @return the fully qualified class name of this cp attachment file entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this cp attachment file entry.
	 *
	 * @return the class name ID of this cp attachment file entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this cp attachment file entry.
	 *
	 * @return the class pk of this cp attachment file entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this cp attachment file entry.
	 *
	 * @return the company ID of this cp attachment file entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp attachment file entry ID of this cp attachment file entry.
	 *
	 * @return the cp attachment file entry ID of this cp attachment file entry
	 */
	@Override
	public long getCPAttachmentFileEntryId() {
		return model.getCPAttachmentFileEntryId();
	}

	/**
	 * Returns the create date of this cp attachment file entry.
	 *
	 * @return the create date of this cp attachment file entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp attachment file entry.
	 *
	 * @return the ct collection ID of this cp attachment file entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the display date of this cp attachment file entry.
	 *
	 * @return the display date of this cp attachment file entry
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the expiration date of this cp attachment file entry.
	 *
	 * @return the expiration date of this cp attachment file entry
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the external reference code of this cp attachment file entry.
	 *
	 * @return the external reference code of this cp attachment file entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the file entry ID of this cp attachment file entry.
	 *
	 * @return the file entry ID of this cp attachment file entry
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the group ID of this cp attachment file entry.
	 *
	 * @return the group ID of this cp attachment file entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the json of this cp attachment file entry.
	 *
	 * @return the json of this cp attachment file entry
	 */
	@Override
	public String getJson() {
		return model.getJson();
	}

	/**
	 * Returns the last publish date of this cp attachment file entry.
	 *
	 * @return the last publish date of this cp attachment file entry
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this cp attachment file entry.
	 *
	 * @return the modified date of this cp attachment file entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp attachment file entry.
	 *
	 * @return the mvcc version of this cp attachment file entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp attachment file entry.
	 *
	 * @return the primary key of this cp attachment file entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this cp attachment file entry.
	 *
	 * @return the priority of this cp attachment file entry
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the status of this cp attachment file entry.
	 *
	 * @return the status of this cp attachment file entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this cp attachment file entry.
	 *
	 * @return the status by user ID of this cp attachment file entry
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this cp attachment file entry.
	 *
	 * @return the status by user name of this cp attachment file entry
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this cp attachment file entry.
	 *
	 * @return the status by user uuid of this cp attachment file entry
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this cp attachment file entry.
	 *
	 * @return the status date of this cp attachment file entry
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the title of this cp attachment file entry.
	 *
	 * @return the title of this cp attachment file entry
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the localized title of this cp attachment file entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this cp attachment file entry
	 */
	@Override
	public String getTitle(java.util.Locale locale) {
		return model.getTitle(locale);
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
		return model.getTitle(locale, useDefault);
	}

	/**
	 * Returns the localized title of this cp attachment file entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this cp attachment file entry
	 */
	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
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
		return model.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return model.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleCurrentValue() {
		return model.getTitleCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized titles of this cp attachment file entry.
	 *
	 * @return the locales and localized titles of this cp attachment file entry
	 */
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return model.getTitleMap();
	}

	/**
	 * Returns the type of this cp attachment file entry.
	 *
	 * @return the type of this cp attachment file entry
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this cp attachment file entry.
	 *
	 * @return the user ID of this cp attachment file entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp attachment file entry.
	 *
	 * @return the user name of this cp attachment file entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp attachment file entry.
	 *
	 * @return the user uuid of this cp attachment file entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp attachment file entry.
	 *
	 * @return the uuid of this cp attachment file entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this cp attachment file entry is approved.
	 *
	 * @return <code>true</code> if this cp attachment file entry is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this cp attachment file entry is cdn enabled.
	 *
	 * @return <code>true</code> if this cp attachment file entry is cdn enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isCDNEnabled() {
		return model.isCDNEnabled();
	}

	/**
	 * Returns <code>true</code> if this cp attachment file entry is denied.
	 *
	 * @return <code>true</code> if this cp attachment file entry is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this cp attachment file entry is a draft.
	 *
	 * @return <code>true</code> if this cp attachment file entry is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this cp attachment file entry is expired.
	 *
	 * @return <code>true</code> if this cp attachment file entry is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this cp attachment file entry is inactive.
	 *
	 * @return <code>true</code> if this cp attachment file entry is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this cp attachment file entry is incomplete.
	 *
	 * @return <code>true</code> if this cp attachment file entry is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this cp attachment file entry is pending.
	 *
	 * @return <code>true</code> if this cp attachment file entry is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this cp attachment file entry is scheduled.
	 *
	 * @return <code>true</code> if this cp attachment file entry is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets whether this cp attachment file entry is cdn enabled.
	 *
	 * @param cdnEnabled the cdn enabled of this cp attachment file entry
	 */
	@Override
	public void setCDNEnabled(boolean cdnEnabled) {
		model.setCDNEnabled(cdnEnabled);
	}

	/**
	 * Sets the cdn url of this cp attachment file entry.
	 *
	 * @param cdnURL the cdn url of this cp attachment file entry
	 */
	@Override
	public void setCDNURL(String cdnURL) {
		model.setCDNURL(cdnURL);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this cp attachment file entry.
	 *
	 * @param classNameId the class name ID of this cp attachment file entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this cp attachment file entry.
	 *
	 * @param classPK the class pk of this cp attachment file entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this cp attachment file entry.
	 *
	 * @param companyId the company ID of this cp attachment file entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp attachment file entry ID of this cp attachment file entry.
	 *
	 * @param CPAttachmentFileEntryId the cp attachment file entry ID of this cp attachment file entry
	 */
	@Override
	public void setCPAttachmentFileEntryId(long CPAttachmentFileEntryId) {
		model.setCPAttachmentFileEntryId(CPAttachmentFileEntryId);
	}

	/**
	 * Sets the create date of this cp attachment file entry.
	 *
	 * @param createDate the create date of this cp attachment file entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp attachment file entry.
	 *
	 * @param ctCollectionId the ct collection ID of this cp attachment file entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the display date of this cp attachment file entry.
	 *
	 * @param displayDate the display date of this cp attachment file entry
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the expiration date of this cp attachment file entry.
	 *
	 * @param expirationDate the expiration date of this cp attachment file entry
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the external reference code of this cp attachment file entry.
	 *
	 * @param externalReferenceCode the external reference code of this cp attachment file entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the file entry ID of this cp attachment file entry.
	 *
	 * @param fileEntryId the file entry ID of this cp attachment file entry
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the group ID of this cp attachment file entry.
	 *
	 * @param groupId the group ID of this cp attachment file entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the json of this cp attachment file entry.
	 *
	 * @param json the json of this cp attachment file entry
	 */
	@Override
	public void setJson(String json) {
		model.setJson(json);
	}

	/**
	 * Sets the last publish date of this cp attachment file entry.
	 *
	 * @param lastPublishDate the last publish date of this cp attachment file entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this cp attachment file entry.
	 *
	 * @param modifiedDate the modified date of this cp attachment file entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp attachment file entry.
	 *
	 * @param mvccVersion the mvcc version of this cp attachment file entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp attachment file entry.
	 *
	 * @param primaryKey the primary key of this cp attachment file entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this cp attachment file entry.
	 *
	 * @param priority the priority of this cp attachment file entry
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the status of this cp attachment file entry.
	 *
	 * @param status the status of this cp attachment file entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this cp attachment file entry.
	 *
	 * @param statusByUserId the status by user ID of this cp attachment file entry
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this cp attachment file entry.
	 *
	 * @param statusByUserName the status by user name of this cp attachment file entry
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this cp attachment file entry.
	 *
	 * @param statusByUserUuid the status by user uuid of this cp attachment file entry
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this cp attachment file entry.
	 *
	 * @param statusDate the status date of this cp attachment file entry
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the title of this cp attachment file entry.
	 *
	 * @param title the title of this cp attachment file entry
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the localized title of this cp attachment file entry in the language.
	 *
	 * @param title the localized title of this cp attachment file entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		model.setTitle(title, locale);
	}

	/**
	 * Sets the localized title of this cp attachment file entry in the language, and sets the default locale.
	 *
	 * @param title the localized title of this cp attachment file entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitle(
		String title, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		model.setTitleCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized titles of this cp attachment file entry from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this cp attachment file entry
	 */
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		model.setTitleMap(titleMap);
	}

	/**
	 * Sets the localized titles of this cp attachment file entry from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this cp attachment file entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitleMap(
		Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {

		model.setTitleMap(titleMap, defaultLocale);
	}

	/**
	 * Sets the type of this cp attachment file entry.
	 *
	 * @param type the type of this cp attachment file entry
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this cp attachment file entry.
	 *
	 * @param userId the user ID of this cp attachment file entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp attachment file entry.
	 *
	 * @param userName the user name of this cp attachment file entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp attachment file entry.
	 *
	 * @param userUuid the user uuid of this cp attachment file entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp attachment file entry.
	 *
	 * @param uuid the uuid of this cp attachment file entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<CPAttachmentFileEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPAttachmentFileEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPAttachmentFileEntryWrapper wrap(
		CPAttachmentFileEntry cpAttachmentFileEntry) {

		return new CPAttachmentFileEntryWrapper(cpAttachmentFileEntry);
	}

}
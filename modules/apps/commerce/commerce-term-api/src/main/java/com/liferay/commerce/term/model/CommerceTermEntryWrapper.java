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

package com.liferay.commerce.term.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceTermEntry}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceTermEntry
 * @generated
 */
public class CommerceTermEntryWrapper
	extends BaseModelWrapper<CommerceTermEntry>
	implements CommerceTermEntry, ModelWrapper<CommerceTermEntry> {

	public CommerceTermEntryWrapper(CommerceTermEntry commerceTermEntry) {
		super(commerceTermEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceTermEntryId", getCommerceTermEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("active", isActive());
		attributes.put("description", getDescription());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("label", getLabel());
		attributes.put("name", getName());
		attributes.put("priority", getPriority());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
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

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceTermEntryId = (Long)attributes.get("commerceTermEntryId");

		if (commerceTermEntryId != null) {
			setCommerceTermEntryId(commerceTermEntryId);
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

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String label = (String)attributes.get("label");

		if (label != null) {
			setLabel(label);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
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
	public CommerceTermEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this commerce term entry.
	 *
	 * @return the active of this commerce term entry
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the commerce term entry ID of this commerce term entry.
	 *
	 * @return the commerce term entry ID of this commerce term entry
	 */
	@Override
	public long getCommerceTermEntryId() {
		return model.getCommerceTermEntryId();
	}

	/**
	 * Returns the company ID of this commerce term entry.
	 *
	 * @return the company ID of this commerce term entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce term entry.
	 *
	 * @return the create date of this commerce term entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this commerce term entry.
	 *
	 * @return the description of this commerce term entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the display date of this commerce term entry.
	 *
	 * @return the display date of this commerce term entry
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the expiration date of this commerce term entry.
	 *
	 * @return the expiration date of this commerce term entry
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the external reference code of this commerce term entry.
	 *
	 * @return the external reference code of this commerce term entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the label of this commerce term entry.
	 *
	 * @return the label of this commerce term entry
	 */
	@Override
	public String getLabel() {
		return model.getLabel();
	}

	/**
	 * Returns the localized label of this commerce term entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized label of this commerce term entry
	 */
	@Override
	public String getLabel(java.util.Locale locale) {
		return model.getLabel(locale);
	}

	/**
	 * Returns the localized label of this commerce term entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this commerce term entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getLabel(java.util.Locale locale, boolean useDefault) {
		return model.getLabel(locale, useDefault);
	}

	/**
	 * Returns the localized label of this commerce term entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized label of this commerce term entry
	 */
	@Override
	public String getLabel(String languageId) {
		return model.getLabel(languageId);
	}

	/**
	 * Returns the localized label of this commerce term entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this commerce term entry
	 */
	@Override
	public String getLabel(String languageId, boolean useDefault) {
		return model.getLabel(languageId, useDefault);
	}

	@Override
	public String getLabelCurrentLanguageId() {
		return model.getLabelCurrentLanguageId();
	}

	@Override
	public String getLabelCurrentValue() {
		return model.getLabelCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized labels of this commerce term entry.
	 *
	 * @return the locales and localized labels of this commerce term entry
	 */
	@Override
	public Map<java.util.Locale, String> getLabelMap() {
		return model.getLabelMap();
	}

	/**
	 * Returns the last publish date of this commerce term entry.
	 *
	 * @return the last publish date of this commerce term entry
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce term entry.
	 *
	 * @return the modified date of this commerce term entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce term entry.
	 *
	 * @return the mvcc version of this commerce term entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this commerce term entry.
	 *
	 * @return the name of this commerce term entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce term entry.
	 *
	 * @return the primary key of this commerce term entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce term entry.
	 *
	 * @return the priority of this commerce term entry
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the status of this commerce term entry.
	 *
	 * @return the status of this commerce term entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this commerce term entry.
	 *
	 * @return the status by user ID of this commerce term entry
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this commerce term entry.
	 *
	 * @return the status by user name of this commerce term entry
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this commerce term entry.
	 *
	 * @return the status by user uuid of this commerce term entry
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this commerce term entry.
	 *
	 * @return the status date of this commerce term entry
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the type of this commerce term entry.
	 *
	 * @return the type of this commerce term entry
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the type settings of this commerce term entry.
	 *
	 * @return the type settings of this commerce term entry
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	/**
	 * Returns the user ID of this commerce term entry.
	 *
	 * @return the user ID of this commerce term entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce term entry.
	 *
	 * @return the user name of this commerce term entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce term entry.
	 *
	 * @return the user uuid of this commerce term entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce term entry is active.
	 *
	 * @return <code>true</code> if this commerce term entry is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this commerce term entry is approved.
	 *
	 * @return <code>true</code> if this commerce term entry is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this commerce term entry is denied.
	 *
	 * @return <code>true</code> if this commerce term entry is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this commerce term entry is a draft.
	 *
	 * @return <code>true</code> if this commerce term entry is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this commerce term entry is expired.
	 *
	 * @return <code>true</code> if this commerce term entry is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this commerce term entry is inactive.
	 *
	 * @return <code>true</code> if this commerce term entry is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this commerce term entry is incomplete.
	 *
	 * @return <code>true</code> if this commerce term entry is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this commerce term entry is pending.
	 *
	 * @return <code>true</code> if this commerce term entry is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this commerce term entry is scheduled.
	 *
	 * @return <code>true</code> if this commerce term entry is scheduled; <code>false</code> otherwise
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
	 * Sets whether this commerce term entry is active.
	 *
	 * @param active the active of this commerce term entry
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the commerce term entry ID of this commerce term entry.
	 *
	 * @param commerceTermEntryId the commerce term entry ID of this commerce term entry
	 */
	@Override
	public void setCommerceTermEntryId(long commerceTermEntryId) {
		model.setCommerceTermEntryId(commerceTermEntryId);
	}

	/**
	 * Sets the company ID of this commerce term entry.
	 *
	 * @param companyId the company ID of this commerce term entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce term entry.
	 *
	 * @param createDate the create date of this commerce term entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this commerce term entry.
	 *
	 * @param description the description of this commerce term entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the display date of this commerce term entry.
	 *
	 * @param displayDate the display date of this commerce term entry
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the expiration date of this commerce term entry.
	 *
	 * @param expirationDate the expiration date of this commerce term entry
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the external reference code of this commerce term entry.
	 *
	 * @param externalReferenceCode the external reference code of this commerce term entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the label of this commerce term entry.
	 *
	 * @param label the label of this commerce term entry
	 */
	@Override
	public void setLabel(String label) {
		model.setLabel(label);
	}

	/**
	 * Sets the localized label of this commerce term entry in the language.
	 *
	 * @param label the localized label of this commerce term entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setLabel(String label, java.util.Locale locale) {
		model.setLabel(label, locale);
	}

	/**
	 * Sets the localized label of this commerce term entry in the language, and sets the default locale.
	 *
	 * @param label the localized label of this commerce term entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabel(
		String label, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setLabel(label, locale, defaultLocale);
	}

	@Override
	public void setLabelCurrentLanguageId(String languageId) {
		model.setLabelCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized labels of this commerce term entry from the map of locales and localized labels.
	 *
	 * @param labelMap the locales and localized labels of this commerce term entry
	 */
	@Override
	public void setLabelMap(Map<java.util.Locale, String> labelMap) {
		model.setLabelMap(labelMap);
	}

	/**
	 * Sets the localized labels of this commerce term entry from the map of locales and localized labels, and sets the default locale.
	 *
	 * @param labelMap the locales and localized labels of this commerce term entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabelMap(
		Map<java.util.Locale, String> labelMap,
		java.util.Locale defaultLocale) {

		model.setLabelMap(labelMap, defaultLocale);
	}

	/**
	 * Sets the last publish date of this commerce term entry.
	 *
	 * @param lastPublishDate the last publish date of this commerce term entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce term entry.
	 *
	 * @param modifiedDate the modified date of this commerce term entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce term entry.
	 *
	 * @param mvccVersion the mvcc version of this commerce term entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this commerce term entry.
	 *
	 * @param name the name of this commerce term entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce term entry.
	 *
	 * @param primaryKey the primary key of this commerce term entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce term entry.
	 *
	 * @param priority the priority of this commerce term entry
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the status of this commerce term entry.
	 *
	 * @param status the status of this commerce term entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this commerce term entry.
	 *
	 * @param statusByUserId the status by user ID of this commerce term entry
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this commerce term entry.
	 *
	 * @param statusByUserName the status by user name of this commerce term entry
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this commerce term entry.
	 *
	 * @param statusByUserUuid the status by user uuid of this commerce term entry
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this commerce term entry.
	 *
	 * @param statusDate the status date of this commerce term entry
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the type of this commerce term entry.
	 *
	 * @param type the type of this commerce term entry
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the type settings of this commerce term entry.
	 *
	 * @param typeSettings the type settings of this commerce term entry
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	/**
	 * Sets the user ID of this commerce term entry.
	 *
	 * @param userId the user ID of this commerce term entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce term entry.
	 *
	 * @param userName the user name of this commerce term entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce term entry.
	 *
	 * @param userUuid the user uuid of this commerce term entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceTermEntryWrapper wrap(
		CommerceTermEntry commerceTermEntry) {

		return new CommerceTermEntryWrapper(commerceTermEntry);
	}

}
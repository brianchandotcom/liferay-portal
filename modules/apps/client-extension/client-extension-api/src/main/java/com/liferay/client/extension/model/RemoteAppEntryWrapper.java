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

package com.liferay.remote.app.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RemoteAppEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntry
 * @generated
 */
public class RemoteAppEntryWrapper
	extends BaseModelWrapper<RemoteAppEntry>
	implements ModelWrapper<RemoteAppEntry>, RemoteAppEntry {

	public RemoteAppEntryWrapper(RemoteAppEntry remoteAppEntry) {
		super(remoteAppEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("remoteAppEntryId", getRemoteAppEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("customElementCSSURLs", getCustomElementCSSURLs());
		attributes.put(
			"customElementHTMLElementName", getCustomElementHTMLElementName());
		attributes.put("customElementURLs", getCustomElementURLs());
		attributes.put("customElementUseESM", isCustomElementUseESM());
		attributes.put("description", getDescription());
		attributes.put("friendlyURLMapping", getFriendlyURLMapping());
		attributes.put("iFrameURL", getIFrameURL());
		attributes.put("instanceable", isInstanceable());
		attributes.put("name", getName());
		attributes.put("portletCategoryName", getPortletCategoryName());
		attributes.put("properties", getProperties());
		attributes.put("sourceCodeURL", getSourceCodeURL());
		attributes.put("type", getType());
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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long remoteAppEntryId = (Long)attributes.get("remoteAppEntryId");

		if (remoteAppEntryId != null) {
			setRemoteAppEntryId(remoteAppEntryId);
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

		String customElementCSSURLs = (String)attributes.get(
			"customElementCSSURLs");

		if (customElementCSSURLs != null) {
			setCustomElementCSSURLs(customElementCSSURLs);
		}

		String customElementHTMLElementName = (String)attributes.get(
			"customElementHTMLElementName");

		if (customElementHTMLElementName != null) {
			setCustomElementHTMLElementName(customElementHTMLElementName);
		}

		String customElementURLs = (String)attributes.get("customElementURLs");

		if (customElementURLs != null) {
			setCustomElementURLs(customElementURLs);
		}

		Boolean customElementUseESM = (Boolean)attributes.get(
			"customElementUseESM");

		if (customElementUseESM != null) {
			setCustomElementUseESM(customElementUseESM);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String friendlyURLMapping = (String)attributes.get(
			"friendlyURLMapping");

		if (friendlyURLMapping != null) {
			setFriendlyURLMapping(friendlyURLMapping);
		}

		String iFrameURL = (String)attributes.get("iFrameURL");

		if (iFrameURL != null) {
			setIFrameURL(iFrameURL);
		}

		Boolean instanceable = (Boolean)attributes.get("instanceable");

		if (instanceable != null) {
			setInstanceable(instanceable);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String portletCategoryName = (String)attributes.get(
			"portletCategoryName");

		if (portletCategoryName != null) {
			setPortletCategoryName(portletCategoryName);
		}

		String properties = (String)attributes.get("properties");

		if (properties != null) {
			setProperties(properties);
		}

		String sourceCodeURL = (String)attributes.get("sourceCodeURL");

		if (sourceCodeURL != null) {
			setSourceCodeURL(sourceCodeURL);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
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
	public RemoteAppEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this remote app entry.
	 *
	 * @return the company ID of this remote app entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this remote app entry.
	 *
	 * @return the container model ID of this remote app entry
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this remote app entry.
	 *
	 * @return the container name of this remote app entry
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this remote app entry.
	 *
	 * @return the create date of this remote app entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the custom element cssur ls of this remote app entry.
	 *
	 * @return the custom element cssur ls of this remote app entry
	 */
	@Override
	public String getCustomElementCSSURLs() {
		return model.getCustomElementCSSURLs();
	}

	/**
	 * Returns the custom element html element name of this remote app entry.
	 *
	 * @return the custom element html element name of this remote app entry
	 */
	@Override
	public String getCustomElementHTMLElementName() {
		return model.getCustomElementHTMLElementName();
	}

	/**
	 * Returns the custom element ur ls of this remote app entry.
	 *
	 * @return the custom element ur ls of this remote app entry
	 */
	@Override
	public String getCustomElementURLs() {
		return model.getCustomElementURLs();
	}

	/**
	 * Returns the custom element use esm of this remote app entry.
	 *
	 * @return the custom element use esm of this remote app entry
	 */
	@Override
	public boolean getCustomElementUseESM() {
		return model.getCustomElementUseESM();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this remote app entry.
	 *
	 * @return the description of this remote app entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the external reference code of this remote app entry.
	 *
	 * @return the external reference code of this remote app entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the friendly url mapping of this remote app entry.
	 *
	 * @return the friendly url mapping of this remote app entry
	 */
	@Override
	public String getFriendlyURLMapping() {
		return model.getFriendlyURLMapping();
	}

	/**
	 * Returns the i frame url of this remote app entry.
	 *
	 * @return the i frame url of this remote app entry
	 */
	@Override
	public String getIFrameURL() {
		return model.getIFrameURL();
	}

	/**
	 * Returns the instanceable of this remote app entry.
	 *
	 * @return the instanceable of this remote app entry
	 */
	@Override
	public boolean getInstanceable() {
		return model.getInstanceable();
	}

	/**
	 * Returns the modified date of this remote app entry.
	 *
	 * @return the modified date of this remote app entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this remote app entry.
	 *
	 * @return the mvcc version of this remote app entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this remote app entry.
	 *
	 * @return the name of this remote app entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this remote app entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this remote app entry
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this remote app entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this remote app entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this remote app entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this remote app entry
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this remote app entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this remote app entry
	 */
	@Override
	public String getName(String languageId, boolean useDefault) {
		return model.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return model.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return model.getNameCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized names of this remote app entry.
	 *
	 * @return the locales and localized names of this remote app entry
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the parent container model ID of this remote app entry.
	 *
	 * @return the parent container model ID of this remote app entry
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	/**
	 * Returns the portlet category name of this remote app entry.
	 *
	 * @return the portlet category name of this remote app entry
	 */
	@Override
	public String getPortletCategoryName() {
		return model.getPortletCategoryName();
	}

	/**
	 * Returns the primary key of this remote app entry.
	 *
	 * @return the primary key of this remote app entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the properties of this remote app entry.
	 *
	 * @return the properties of this remote app entry
	 */
	@Override
	public String getProperties() {
		return model.getProperties();
	}

	/**
	 * Returns the remote app entry ID of this remote app entry.
	 *
	 * @return the remote app entry ID of this remote app entry
	 */
	@Override
	public long getRemoteAppEntryId() {
		return model.getRemoteAppEntryId();
	}

	/**
	 * Returns the source code url of this remote app entry.
	 *
	 * @return the source code url of this remote app entry
	 */
	@Override
	public String getSourceCodeURL() {
		return model.getSourceCodeURL();
	}

	/**
	 * Returns the status of this remote app entry.
	 *
	 * @return the status of this remote app entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this remote app entry.
	 *
	 * @return the status by user ID of this remote app entry
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this remote app entry.
	 *
	 * @return the status by user name of this remote app entry
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this remote app entry.
	 *
	 * @return the status by user uuid of this remote app entry
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this remote app entry.
	 *
	 * @return the status date of this remote app entry
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the type of this remote app entry.
	 *
	 * @return the type of this remote app entry
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this remote app entry.
	 *
	 * @return the user ID of this remote app entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this remote app entry.
	 *
	 * @return the user name of this remote app entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this remote app entry.
	 *
	 * @return the user uuid of this remote app entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this remote app entry.
	 *
	 * @return the uuid of this remote app entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this remote app entry is approved.
	 *
	 * @return <code>true</code> if this remote app entry is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this remote app entry is custom element use esm.
	 *
	 * @return <code>true</code> if this remote app entry is custom element use esm; <code>false</code> otherwise
	 */
	@Override
	public boolean isCustomElementUseESM() {
		return model.isCustomElementUseESM();
	}

	/**
	 * Returns <code>true</code> if this remote app entry is denied.
	 *
	 * @return <code>true</code> if this remote app entry is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this remote app entry is a draft.
	 *
	 * @return <code>true</code> if this remote app entry is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this remote app entry is expired.
	 *
	 * @return <code>true</code> if this remote app entry is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this remote app entry is inactive.
	 *
	 * @return <code>true</code> if this remote app entry is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this remote app entry is incomplete.
	 *
	 * @return <code>true</code> if this remote app entry is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this remote app entry is instanceable.
	 *
	 * @return <code>true</code> if this remote app entry is instanceable; <code>false</code> otherwise
	 */
	@Override
	public boolean isInstanceable() {
		return model.isInstanceable();
	}

	/**
	 * Returns <code>true</code> if this remote app entry is pending.
	 *
	 * @return <code>true</code> if this remote app entry is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this remote app entry is scheduled.
	 *
	 * @return <code>true</code> if this remote app entry is scheduled; <code>false</code> otherwise
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
	 * Sets the company ID of this remote app entry.
	 *
	 * @param companyId the company ID of this remote app entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this remote app entry.
	 *
	 * @param containerModelId the container model ID of this remote app entry
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this remote app entry.
	 *
	 * @param createDate the create date of this remote app entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the custom element cssur ls of this remote app entry.
	 *
	 * @param customElementCSSURLs the custom element cssur ls of this remote app entry
	 */
	@Override
	public void setCustomElementCSSURLs(String customElementCSSURLs) {
		model.setCustomElementCSSURLs(customElementCSSURLs);
	}

	/**
	 * Sets the custom element html element name of this remote app entry.
	 *
	 * @param customElementHTMLElementName the custom element html element name of this remote app entry
	 */
	@Override
	public void setCustomElementHTMLElementName(
		String customElementHTMLElementName) {

		model.setCustomElementHTMLElementName(customElementHTMLElementName);
	}

	/**
	 * Sets the custom element ur ls of this remote app entry.
	 *
	 * @param customElementURLs the custom element ur ls of this remote app entry
	 */
	@Override
	public void setCustomElementURLs(String customElementURLs) {
		model.setCustomElementURLs(customElementURLs);
	}

	/**
	 * Sets whether this remote app entry is custom element use esm.
	 *
	 * @param customElementUseESM the custom element use esm of this remote app entry
	 */
	@Override
	public void setCustomElementUseESM(boolean customElementUseESM) {
		model.setCustomElementUseESM(customElementUseESM);
	}

	/**
	 * Sets the description of this remote app entry.
	 *
	 * @param description the description of this remote app entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the external reference code of this remote app entry.
	 *
	 * @param externalReferenceCode the external reference code of this remote app entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the friendly url mapping of this remote app entry.
	 *
	 * @param friendlyURLMapping the friendly url mapping of this remote app entry
	 */
	@Override
	public void setFriendlyURLMapping(String friendlyURLMapping) {
		model.setFriendlyURLMapping(friendlyURLMapping);
	}

	/**
	 * Sets the i frame url of this remote app entry.
	 *
	 * @param iFrameURL the i frame url of this remote app entry
	 */
	@Override
	public void setIFrameURL(String iFrameURL) {
		model.setIFrameURL(iFrameURL);
	}

	/**
	 * Sets whether this remote app entry is instanceable.
	 *
	 * @param instanceable the instanceable of this remote app entry
	 */
	@Override
	public void setInstanceable(boolean instanceable) {
		model.setInstanceable(instanceable);
	}

	/**
	 * Sets the modified date of this remote app entry.
	 *
	 * @param modifiedDate the modified date of this remote app entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this remote app entry.
	 *
	 * @param mvccVersion the mvcc version of this remote app entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this remote app entry.
	 *
	 * @param name the name of this remote app entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this remote app entry in the language.
	 *
	 * @param name the localized name of this remote app entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this remote app entry in the language, and sets the default locale.
	 *
	 * @param name the localized name of this remote app entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setName(
		String name, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized names of this remote app entry from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this remote app entry
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this remote app entry from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this remote app entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the parent container model ID of this remote app entry.
	 *
	 * @param parentContainerModelId the parent container model ID of this remote app entry
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the portlet category name of this remote app entry.
	 *
	 * @param portletCategoryName the portlet category name of this remote app entry
	 */
	@Override
	public void setPortletCategoryName(String portletCategoryName) {
		model.setPortletCategoryName(portletCategoryName);
	}

	/**
	 * Sets the primary key of this remote app entry.
	 *
	 * @param primaryKey the primary key of this remote app entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the properties of this remote app entry.
	 *
	 * @param properties the properties of this remote app entry
	 */
	@Override
	public void setProperties(String properties) {
		model.setProperties(properties);
	}

	/**
	 * Sets the remote app entry ID of this remote app entry.
	 *
	 * @param remoteAppEntryId the remote app entry ID of this remote app entry
	 */
	@Override
	public void setRemoteAppEntryId(long remoteAppEntryId) {
		model.setRemoteAppEntryId(remoteAppEntryId);
	}

	/**
	 * Sets the source code url of this remote app entry.
	 *
	 * @param sourceCodeURL the source code url of this remote app entry
	 */
	@Override
	public void setSourceCodeURL(String sourceCodeURL) {
		model.setSourceCodeURL(sourceCodeURL);
	}

	/**
	 * Sets the status of this remote app entry.
	 *
	 * @param status the status of this remote app entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this remote app entry.
	 *
	 * @param statusByUserId the status by user ID of this remote app entry
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this remote app entry.
	 *
	 * @param statusByUserName the status by user name of this remote app entry
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this remote app entry.
	 *
	 * @param statusByUserUuid the status by user uuid of this remote app entry
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this remote app entry.
	 *
	 * @param statusDate the status date of this remote app entry
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the type of this remote app entry.
	 *
	 * @param type the type of this remote app entry
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this remote app entry.
	 *
	 * @param userId the user ID of this remote app entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this remote app entry.
	 *
	 * @param userName the user name of this remote app entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this remote app entry.
	 *
	 * @param userUuid the user uuid of this remote app entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this remote app entry.
	 *
	 * @param uuid the uuid of this remote app entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected RemoteAppEntryWrapper wrap(RemoteAppEntry remoteAppEntry) {
		return new RemoteAppEntryWrapper(remoteAppEntry);
	}

}
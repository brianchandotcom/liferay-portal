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
 * This class is a wrapper for {@link CPAvailabilityRange}.
 * </p>
 *
 * @author Marco Leo
 * @see CPAvailabilityRange
 * @generated
 */
@ProviderType
public class CPAvailabilityRangeWrapper implements CPAvailabilityRange,
	ModelWrapper<CPAvailabilityRange> {
	public CPAvailabilityRangeWrapper(CPAvailabilityRange cpAvailabilityRange) {
		_cpAvailabilityRange = cpAvailabilityRange;
	}

	@Override
	public Class<?> getModelClass() {
		return CPAvailabilityRange.class;
	}

	@Override
	public String getModelClassName() {
		return CPAvailabilityRange.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPAvailabilityRangeId", getCPAvailabilityRangeId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("title", getTitle());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPAvailabilityRangeId = (Long)attributes.get(
				"CPAvailabilityRangeId");

		if (CPAvailabilityRangeId != null) {
			setCPAvailabilityRangeId(CPAvailabilityRangeId);
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

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CPAvailabilityRangeWrapper((CPAvailabilityRange)_cpAvailabilityRange.clone());
	}

	@Override
	public int compareTo(CPAvailabilityRange cpAvailabilityRange) {
		return _cpAvailabilityRange.compareTo(cpAvailabilityRange);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _cpAvailabilityRange.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this cp availability range.
	*
	* @return the company ID of this cp availability range
	*/
	@Override
	public long getCompanyId() {
		return _cpAvailabilityRange.getCompanyId();
	}

	/**
	* Returns the cp availability range ID of this cp availability range.
	*
	* @return the cp availability range ID of this cp availability range
	*/
	@Override
	public long getCPAvailabilityRangeId() {
		return _cpAvailabilityRange.getCPAvailabilityRangeId();
	}

	/**
	* Returns the cp definition ID of this cp availability range.
	*
	* @return the cp definition ID of this cp availability range
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpAvailabilityRange.getCPDefinitionId();
	}

	/**
	* Returns the create date of this cp availability range.
	*
	* @return the create date of this cp availability range
	*/
	@Override
	public Date getCreateDate() {
		return _cpAvailabilityRange.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _cpAvailabilityRange.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpAvailabilityRange.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp availability range.
	*
	* @return the group ID of this cp availability range
	*/
	@Override
	public long getGroupId() {
		return _cpAvailabilityRange.getGroupId();
	}

	/**
	* Returns the last publish date of this cp availability range.
	*
	* @return the last publish date of this cp availability range
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpAvailabilityRange.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp availability range.
	*
	* @return the modified date of this cp availability range
	*/
	@Override
	public Date getModifiedDate() {
		return _cpAvailabilityRange.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp availability range.
	*
	* @return the primary key of this cp availability range
	*/
	@Override
	public long getPrimaryKey() {
		return _cpAvailabilityRange.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpAvailabilityRange.getPrimaryKeyObj();
	}

	/**
	* Returns the title of this cp availability range.
	*
	* @return the title of this cp availability range
	*/
	@Override
	public java.lang.String getTitle() {
		return _cpAvailabilityRange.getTitle();
	}

	/**
	* Returns the localized title of this cp availability range in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this cp availability range
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _cpAvailabilityRange.getTitle(locale);
	}

	/**
	* Returns the localized title of this cp availability range in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp availability range. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _cpAvailabilityRange.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this cp availability range in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this cp availability range
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _cpAvailabilityRange.getTitle(languageId);
	}

	/**
	* Returns the localized title of this cp availability range in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp availability range
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _cpAvailabilityRange.getTitle(languageId, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _cpAvailabilityRange.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _cpAvailabilityRange.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this cp availability range.
	*
	* @return the locales and localized titles of this cp availability range
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _cpAvailabilityRange.getTitleMap();
	}

	/**
	* Returns the user ID of this cp availability range.
	*
	* @return the user ID of this cp availability range
	*/
	@Override
	public long getUserId() {
		return _cpAvailabilityRange.getUserId();
	}

	/**
	* Returns the user name of this cp availability range.
	*
	* @return the user name of this cp availability range
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpAvailabilityRange.getUserName();
	}

	/**
	* Returns the user uuid of this cp availability range.
	*
	* @return the user uuid of this cp availability range
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpAvailabilityRange.getUserUuid();
	}

	/**
	* Returns the uuid of this cp availability range.
	*
	* @return the uuid of this cp availability range
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpAvailabilityRange.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpAvailabilityRange.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpAvailabilityRange.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpAvailabilityRange.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpAvailabilityRange.isNew();
	}

	@Override
	public void persist() {
		_cpAvailabilityRange.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpAvailabilityRange.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpAvailabilityRange.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpAvailabilityRange.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp availability range.
	*
	* @param companyId the company ID of this cp availability range
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpAvailabilityRange.setCompanyId(companyId);
	}

	/**
	* Sets the cp availability range ID of this cp availability range.
	*
	* @param CPAvailabilityRangeId the cp availability range ID of this cp availability range
	*/
	@Override
	public void setCPAvailabilityRangeId(long CPAvailabilityRangeId) {
		_cpAvailabilityRange.setCPAvailabilityRangeId(CPAvailabilityRangeId);
	}

	/**
	* Sets the cp definition ID of this cp availability range.
	*
	* @param CPDefinitionId the cp definition ID of this cp availability range
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpAvailabilityRange.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the create date of this cp availability range.
	*
	* @param createDate the create date of this cp availability range
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpAvailabilityRange.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpAvailabilityRange.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpAvailabilityRange.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpAvailabilityRange.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp availability range.
	*
	* @param groupId the group ID of this cp availability range
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpAvailabilityRange.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this cp availability range.
	*
	* @param lastPublishDate the last publish date of this cp availability range
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpAvailabilityRange.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cp availability range.
	*
	* @param modifiedDate the modified date of this cp availability range
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpAvailabilityRange.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpAvailabilityRange.setNew(n);
	}

	/**
	* Sets the primary key of this cp availability range.
	*
	* @param primaryKey the primary key of this cp availability range
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpAvailabilityRange.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpAvailabilityRange.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the title of this cp availability range.
	*
	* @param title the title of this cp availability range
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_cpAvailabilityRange.setTitle(title);
	}

	/**
	* Sets the localized title of this cp availability range in the language.
	*
	* @param title the localized title of this cp availability range
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_cpAvailabilityRange.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this cp availability range in the language, and sets the default locale.
	*
	* @param title the localized title of this cp availability range
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpAvailabilityRange.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_cpAvailabilityRange.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this cp availability range from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this cp availability range
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_cpAvailabilityRange.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this cp availability range from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this cp availability range
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_cpAvailabilityRange.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this cp availability range.
	*
	* @param userId the user ID of this cp availability range
	*/
	@Override
	public void setUserId(long userId) {
		_cpAvailabilityRange.setUserId(userId);
	}

	/**
	* Sets the user name of this cp availability range.
	*
	* @param userName the user name of this cp availability range
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpAvailabilityRange.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp availability range.
	*
	* @param userUuid the user uuid of this cp availability range
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpAvailabilityRange.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp availability range.
	*
	* @param uuid the uuid of this cp availability range
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpAvailabilityRange.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPAvailabilityRange> toCacheModel() {
		return _cpAvailabilityRange.toCacheModel();
	}

	@Override
	public CPAvailabilityRange toEscapedModel() {
		return new CPAvailabilityRangeWrapper(_cpAvailabilityRange.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _cpAvailabilityRange.toString();
	}

	@Override
	public CPAvailabilityRange toUnescapedModel() {
		return new CPAvailabilityRangeWrapper(_cpAvailabilityRange.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpAvailabilityRange.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPAvailabilityRangeWrapper)) {
			return false;
		}

		CPAvailabilityRangeWrapper cpAvailabilityRangeWrapper = (CPAvailabilityRangeWrapper)obj;

		if (Objects.equals(_cpAvailabilityRange,
					cpAvailabilityRangeWrapper._cpAvailabilityRange)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpAvailabilityRange.getStagedModelType();
	}

	@Override
	public CPAvailabilityRange getWrappedModel() {
		return _cpAvailabilityRange;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpAvailabilityRange.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpAvailabilityRange.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpAvailabilityRange.resetOriginalValues();
	}

	private final CPAvailabilityRange _cpAvailabilityRange;
}
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

package com.liferay.commerce.model;

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
 * This class is a wrapper for {@link CommerceAvailabilityRange}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityRange
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeWrapper
	implements CommerceAvailabilityRange,
		ModelWrapper<CommerceAvailabilityRange> {
	public CommerceAvailabilityRangeWrapper(
		CommerceAvailabilityRange commerceAvailabilityRange) {
		_commerceAvailabilityRange = commerceAvailabilityRange;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceAvailabilityRange.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceAvailabilityRange.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceAvailabilityRangeId",
			getCommerceAvailabilityRangeId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("priority", getPriority());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceAvailabilityRangeId = (Long)attributes.get(
				"commerceAvailabilityRangeId");

		if (commerceAvailabilityRangeId != null) {
			setCommerceAvailabilityRangeId(commerceAvailabilityRangeId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new CommerceAvailabilityRangeWrapper((CommerceAvailabilityRange)_commerceAvailabilityRange.clone());
	}

	@Override
	public int compareTo(CommerceAvailabilityRange commerceAvailabilityRange) {
		return _commerceAvailabilityRange.compareTo(commerceAvailabilityRange);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _commerceAvailabilityRange.getAvailableLanguageIds();
	}

	/**
	* Returns the commerce availability range ID of this commerce availability range.
	*
	* @return the commerce availability range ID of this commerce availability range
	*/
	@Override
	public long getCommerceAvailabilityRangeId() {
		return _commerceAvailabilityRange.getCommerceAvailabilityRangeId();
	}

	/**
	* Returns the company ID of this commerce availability range.
	*
	* @return the company ID of this commerce availability range
	*/
	@Override
	public long getCompanyId() {
		return _commerceAvailabilityRange.getCompanyId();
	}

	/**
	* Returns the create date of this commerce availability range.
	*
	* @return the create date of this commerce availability range
	*/
	@Override
	public Date getCreateDate() {
		return _commerceAvailabilityRange.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return _commerceAvailabilityRange.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceAvailabilityRange.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce availability range.
	*
	* @return the group ID of this commerce availability range
	*/
	@Override
	public long getGroupId() {
		return _commerceAvailabilityRange.getGroupId();
	}

	/**
	* Returns the last publish date of this commerce availability range.
	*
	* @return the last publish date of this commerce availability range
	*/
	@Override
	public Date getLastPublishDate() {
		return _commerceAvailabilityRange.getLastPublishDate();
	}

	/**
	* Returns the modified date of this commerce availability range.
	*
	* @return the modified date of this commerce availability range
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceAvailabilityRange.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce availability range.
	*
	* @return the primary key of this commerce availability range
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceAvailabilityRange.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceAvailabilityRange.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this commerce availability range.
	*
	* @return the priority of this commerce availability range
	*/
	@Override
	public double getPriority() {
		return _commerceAvailabilityRange.getPriority();
	}

	/**
	* Returns the title of this commerce availability range.
	*
	* @return the title of this commerce availability range
	*/
	@Override
	public String getTitle() {
		return _commerceAvailabilityRange.getTitle();
	}

	/**
	* Returns the localized title of this commerce availability range in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this commerce availability range
	*/
	@Override
	public String getTitle(java.util.Locale locale) {
		return _commerceAvailabilityRange.getTitle(locale);
	}

	/**
	* Returns the localized title of this commerce availability range in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this commerce availability range. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return _commerceAvailabilityRange.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this commerce availability range in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this commerce availability range
	*/
	@Override
	public String getTitle(String languageId) {
		return _commerceAvailabilityRange.getTitle(languageId);
	}

	/**
	* Returns the localized title of this commerce availability range in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this commerce availability range
	*/
	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return _commerceAvailabilityRange.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return _commerceAvailabilityRange.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleCurrentValue() {
		return _commerceAvailabilityRange.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this commerce availability range.
	*
	* @return the locales and localized titles of this commerce availability range
	*/
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return _commerceAvailabilityRange.getTitleMap();
	}

	/**
	* Returns the user ID of this commerce availability range.
	*
	* @return the user ID of this commerce availability range
	*/
	@Override
	public long getUserId() {
		return _commerceAvailabilityRange.getUserId();
	}

	/**
	* Returns the user name of this commerce availability range.
	*
	* @return the user name of this commerce availability range
	*/
	@Override
	public String getUserName() {
		return _commerceAvailabilityRange.getUserName();
	}

	/**
	* Returns the user uuid of this commerce availability range.
	*
	* @return the user uuid of this commerce availability range
	*/
	@Override
	public String getUserUuid() {
		return _commerceAvailabilityRange.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce availability range.
	*
	* @return the uuid of this commerce availability range
	*/
	@Override
	public String getUuid() {
		return _commerceAvailabilityRange.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceAvailabilityRange.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceAvailabilityRange.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceAvailabilityRange.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceAvailabilityRange.isNew();
	}

	@Override
	public void persist() {
		_commerceAvailabilityRange.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceAvailabilityRange.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceAvailabilityRange.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceAvailabilityRange.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce availability range ID of this commerce availability range.
	*
	* @param commerceAvailabilityRangeId the commerce availability range ID of this commerce availability range
	*/
	@Override
	public void setCommerceAvailabilityRangeId(long commerceAvailabilityRangeId) {
		_commerceAvailabilityRange.setCommerceAvailabilityRangeId(commerceAvailabilityRangeId);
	}

	/**
	* Sets the company ID of this commerce availability range.
	*
	* @param companyId the company ID of this commerce availability range
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceAvailabilityRange.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce availability range.
	*
	* @param createDate the create date of this commerce availability range
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceAvailabilityRange.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceAvailabilityRange.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceAvailabilityRange.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceAvailabilityRange.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce availability range.
	*
	* @param groupId the group ID of this commerce availability range
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceAvailabilityRange.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this commerce availability range.
	*
	* @param lastPublishDate the last publish date of this commerce availability range
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commerceAvailabilityRange.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this commerce availability range.
	*
	* @param modifiedDate the modified date of this commerce availability range
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceAvailabilityRange.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceAvailabilityRange.setNew(n);
	}

	/**
	* Sets the primary key of this commerce availability range.
	*
	* @param primaryKey the primary key of this commerce availability range
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceAvailabilityRange.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceAvailabilityRange.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce availability range.
	*
	* @param priority the priority of this commerce availability range
	*/
	@Override
	public void setPriority(double priority) {
		_commerceAvailabilityRange.setPriority(priority);
	}

	/**
	* Sets the title of this commerce availability range.
	*
	* @param title the title of this commerce availability range
	*/
	@Override
	public void setTitle(String title) {
		_commerceAvailabilityRange.setTitle(title);
	}

	/**
	* Sets the localized title of this commerce availability range in the language.
	*
	* @param title the localized title of this commerce availability range
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		_commerceAvailabilityRange.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this commerce availability range in the language, and sets the default locale.
	*
	* @param title the localized title of this commerce availability range
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceAvailabilityRange.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		_commerceAvailabilityRange.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this commerce availability range from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this commerce availability range
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		_commerceAvailabilityRange.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this commerce availability range from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this commerce availability range
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {
		_commerceAvailabilityRange.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this commerce availability range.
	*
	* @param userId the user ID of this commerce availability range
	*/
	@Override
	public void setUserId(long userId) {
		_commerceAvailabilityRange.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce availability range.
	*
	* @param userName the user name of this commerce availability range
	*/
	@Override
	public void setUserName(String userName) {
		_commerceAvailabilityRange.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce availability range.
	*
	* @param userUuid the user uuid of this commerce availability range
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceAvailabilityRange.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce availability range.
	*
	* @param uuid the uuid of this commerce availability range
	*/
	@Override
	public void setUuid(String uuid) {
		_commerceAvailabilityRange.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceAvailabilityRange> toCacheModel() {
		return _commerceAvailabilityRange.toCacheModel();
	}

	@Override
	public CommerceAvailabilityRange toEscapedModel() {
		return new CommerceAvailabilityRangeWrapper(_commerceAvailabilityRange.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceAvailabilityRange.toString();
	}

	@Override
	public CommerceAvailabilityRange toUnescapedModel() {
		return new CommerceAvailabilityRangeWrapper(_commerceAvailabilityRange.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceAvailabilityRange.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceAvailabilityRangeWrapper)) {
			return false;
		}

		CommerceAvailabilityRangeWrapper commerceAvailabilityRangeWrapper = (CommerceAvailabilityRangeWrapper)obj;

		if (Objects.equals(_commerceAvailabilityRange,
					commerceAvailabilityRangeWrapper._commerceAvailabilityRange)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceAvailabilityRange.getStagedModelType();
	}

	@Override
	public CommerceAvailabilityRange getWrappedModel() {
		return _commerceAvailabilityRange;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceAvailabilityRange.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceAvailabilityRange.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceAvailabilityRange.resetOriginalValues();
	}

	private final CommerceAvailabilityRange _commerceAvailabilityRange;
}
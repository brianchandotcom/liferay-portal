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
 * This class is a wrapper for {@link CommerceAvailabilityEstimate}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimate
 * @generated
 */
public class CommerceAvailabilityEstimateWrapper
	implements CommerceAvailabilityEstimate,
			   ModelWrapper<CommerceAvailabilityEstimate> {

	public CommerceAvailabilityEstimateWrapper(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {

		_commerceAvailabilityEstimate = commerceAvailabilityEstimate;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceAvailabilityEstimate.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceAvailabilityEstimate.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"commerceAvailabilityEstimateId",
			getCommerceAvailabilityEstimateId());
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

		Long commerceAvailabilityEstimateId = (Long)attributes.get(
			"commerceAvailabilityEstimateId");

		if (commerceAvailabilityEstimateId != null) {
			setCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId);
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
		return new CommerceAvailabilityEstimateWrapper(
			(CommerceAvailabilityEstimate)
				_commerceAvailabilityEstimate.clone());
	}

	@Override
	public int compareTo(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {

		return _commerceAvailabilityEstimate.compareTo(
			commerceAvailabilityEstimate);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _commerceAvailabilityEstimate.getAvailableLanguageIds();
	}

	/**
	 * Returns the commerce availability estimate ID of this commerce availability estimate.
	 *
	 * @return the commerce availability estimate ID of this commerce availability estimate
	 */
	@Override
	public long getCommerceAvailabilityEstimateId() {
		return _commerceAvailabilityEstimate.
			getCommerceAvailabilityEstimateId();
	}

	/**
	 * Returns the company ID of this commerce availability estimate.
	 *
	 * @return the company ID of this commerce availability estimate
	 */
	@Override
	public long getCompanyId() {
		return _commerceAvailabilityEstimate.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce availability estimate.
	 *
	 * @return the create date of this commerce availability estimate
	 */
	@Override
	public Date getCreateDate() {
		return _commerceAvailabilityEstimate.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return _commerceAvailabilityEstimate.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceAvailabilityEstimate.getExpandoBridge();
	}

	/**
	 * Returns the last publish date of this commerce availability estimate.
	 *
	 * @return the last publish date of this commerce availability estimate
	 */
	@Override
	public Date getLastPublishDate() {
		return _commerceAvailabilityEstimate.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce availability estimate.
	 *
	 * @return the modified date of this commerce availability estimate
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceAvailabilityEstimate.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce availability estimate.
	 *
	 * @return the primary key of this commerce availability estimate
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceAvailabilityEstimate.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceAvailabilityEstimate.getPrimaryKeyObj();
	}

	/**
	 * Returns the priority of this commerce availability estimate.
	 *
	 * @return the priority of this commerce availability estimate
	 */
	@Override
	public double getPriority() {
		return _commerceAvailabilityEstimate.getPriority();
	}

	/**
	 * Returns the title of this commerce availability estimate.
	 *
	 * @return the title of this commerce availability estimate
	 */
	@Override
	public String getTitle() {
		return _commerceAvailabilityEstimate.getTitle();
	}

	/**
	 * Returns the localized title of this commerce availability estimate in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this commerce availability estimate
	 */
	@Override
	public String getTitle(java.util.Locale locale) {
		return _commerceAvailabilityEstimate.getTitle(locale);
	}

	/**
	 * Returns the localized title of this commerce availability estimate in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this commerce availability estimate. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return _commerceAvailabilityEstimate.getTitle(locale, useDefault);
	}

	/**
	 * Returns the localized title of this commerce availability estimate in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this commerce availability estimate
	 */
	@Override
	public String getTitle(String languageId) {
		return _commerceAvailabilityEstimate.getTitle(languageId);
	}

	/**
	 * Returns the localized title of this commerce availability estimate in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this commerce availability estimate
	 */
	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return _commerceAvailabilityEstimate.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return _commerceAvailabilityEstimate.getTitleCurrentLanguageId();
	}

	@Override
	public String getTitleCurrentValue() {
		return _commerceAvailabilityEstimate.getTitleCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized titles of this commerce availability estimate.
	 *
	 * @return the locales and localized titles of this commerce availability estimate
	 */
	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return _commerceAvailabilityEstimate.getTitleMap();
	}

	/**
	 * Returns the user ID of this commerce availability estimate.
	 *
	 * @return the user ID of this commerce availability estimate
	 */
	@Override
	public long getUserId() {
		return _commerceAvailabilityEstimate.getUserId();
	}

	/**
	 * Returns the user name of this commerce availability estimate.
	 *
	 * @return the user name of this commerce availability estimate
	 */
	@Override
	public String getUserName() {
		return _commerceAvailabilityEstimate.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce availability estimate.
	 *
	 * @return the user uuid of this commerce availability estimate
	 */
	@Override
	public String getUserUuid() {
		return _commerceAvailabilityEstimate.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce availability estimate.
	 *
	 * @return the uuid of this commerce availability estimate
	 */
	@Override
	public String getUuid() {
		return _commerceAvailabilityEstimate.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceAvailabilityEstimate.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceAvailabilityEstimate.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceAvailabilityEstimate.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceAvailabilityEstimate.isNew();
	}

	@Override
	public void persist() {
		_commerceAvailabilityEstimate.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		_commerceAvailabilityEstimate.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		_commerceAvailabilityEstimate.prepareLocalizedFieldsForImport(
			defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceAvailabilityEstimate.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce availability estimate ID of this commerce availability estimate.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID of this commerce availability estimate
	 */
	@Override
	public void setCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {

		_commerceAvailabilityEstimate.setCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId);
	}

	/**
	 * Sets the company ID of this commerce availability estimate.
	 *
	 * @param companyId the company ID of this commerce availability estimate
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceAvailabilityEstimate.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce availability estimate.
	 *
	 * @param createDate the create date of this commerce availability estimate
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceAvailabilityEstimate.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceAvailabilityEstimate.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceAvailabilityEstimate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceAvailabilityEstimate.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the last publish date of this commerce availability estimate.
	 *
	 * @param lastPublishDate the last publish date of this commerce availability estimate
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commerceAvailabilityEstimate.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce availability estimate.
	 *
	 * @param modifiedDate the modified date of this commerce availability estimate
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceAvailabilityEstimate.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceAvailabilityEstimate.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce availability estimate.
	 *
	 * @param primaryKey the primary key of this commerce availability estimate
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceAvailabilityEstimate.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceAvailabilityEstimate.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the priority of this commerce availability estimate.
	 *
	 * @param priority the priority of this commerce availability estimate
	 */
	@Override
	public void setPriority(double priority) {
		_commerceAvailabilityEstimate.setPriority(priority);
	}

	/**
	 * Sets the title of this commerce availability estimate.
	 *
	 * @param title the title of this commerce availability estimate
	 */
	@Override
	public void setTitle(String title) {
		_commerceAvailabilityEstimate.setTitle(title);
	}

	/**
	 * Sets the localized title of this commerce availability estimate in the language.
	 *
	 * @param title the localized title of this commerce availability estimate
	 * @param locale the locale of the language
	 */
	@Override
	public void setTitle(String title, java.util.Locale locale) {
		_commerceAvailabilityEstimate.setTitle(title, locale);
	}

	/**
	 * Sets the localized title of this commerce availability estimate in the language, and sets the default locale.
	 *
	 * @param title the localized title of this commerce availability estimate
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitle(
		String title, java.util.Locale locale, java.util.Locale defaultLocale) {

		_commerceAvailabilityEstimate.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		_commerceAvailabilityEstimate.setTitleCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized titles of this commerce availability estimate from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this commerce availability estimate
	 */
	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		_commerceAvailabilityEstimate.setTitleMap(titleMap);
	}

	/**
	 * Sets the localized titles of this commerce availability estimate from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this commerce availability estimate
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setTitleMap(
		Map<java.util.Locale, String> titleMap,
		java.util.Locale defaultLocale) {

		_commerceAvailabilityEstimate.setTitleMap(titleMap, defaultLocale);
	}

	/**
	 * Sets the user ID of this commerce availability estimate.
	 *
	 * @param userId the user ID of this commerce availability estimate
	 */
	@Override
	public void setUserId(long userId) {
		_commerceAvailabilityEstimate.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce availability estimate.
	 *
	 * @param userName the user name of this commerce availability estimate
	 */
	@Override
	public void setUserName(String userName) {
		_commerceAvailabilityEstimate.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce availability estimate.
	 *
	 * @param userUuid the user uuid of this commerce availability estimate
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceAvailabilityEstimate.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce availability estimate.
	 *
	 * @param uuid the uuid of this commerce availability estimate
	 */
	@Override
	public void setUuid(String uuid) {
		_commerceAvailabilityEstimate.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceAvailabilityEstimate> toCacheModel() {

		return _commerceAvailabilityEstimate.toCacheModel();
	}

	@Override
	public CommerceAvailabilityEstimate toEscapedModel() {
		return new CommerceAvailabilityEstimateWrapper(
			_commerceAvailabilityEstimate.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceAvailabilityEstimate.toString();
	}

	@Override
	public CommerceAvailabilityEstimate toUnescapedModel() {
		return new CommerceAvailabilityEstimateWrapper(
			_commerceAvailabilityEstimate.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceAvailabilityEstimate.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAvailabilityEstimateWrapper)) {
			return false;
		}

		CommerceAvailabilityEstimateWrapper
			commerceAvailabilityEstimateWrapper =
				(CommerceAvailabilityEstimateWrapper)object;

		if (Objects.equals(
				_commerceAvailabilityEstimate,
				commerceAvailabilityEstimateWrapper.
					_commerceAvailabilityEstimate)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceAvailabilityEstimate.getStagedModelType();
	}

	@Override
	public CommerceAvailabilityEstimate getWrappedModel() {
		return _commerceAvailabilityEstimate;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceAvailabilityEstimate.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceAvailabilityEstimate.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceAvailabilityEstimate.resetOriginalValues();
	}

	private final CommerceAvailabilityEstimate _commerceAvailabilityEstimate;

}
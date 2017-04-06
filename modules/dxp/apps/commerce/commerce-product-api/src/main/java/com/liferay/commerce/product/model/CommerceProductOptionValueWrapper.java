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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceProductOptionValue}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductOptionValue
 * @generated
 */
@ProviderType
public class CommerceProductOptionValueWrapper
	implements CommerceProductOptionValue,
		ModelWrapper<CommerceProductOptionValue> {
	public CommerceProductOptionValueWrapper(
		CommerceProductOptionValue commerceProductOptionValue) {
		_commerceProductOptionValue = commerceProductOptionValue;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceProductOptionValue.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceProductOptionValue.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceProductOptionValueId",
			getCommerceProductOptionValueId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceProductOptionId", getCommerceProductOptionId());
		attributes.put("title", getTitle());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceProductOptionValueId = (Long)attributes.get(
				"commerceProductOptionValueId");

		if (commerceProductOptionValueId != null) {
			setCommerceProductOptionValueId(commerceProductOptionValueId);
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

		Long commerceProductOptionId = (Long)attributes.get(
				"commerceProductOptionId");

		if (commerceProductOptionId != null) {
			setCommerceProductOptionId(commerceProductOptionId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public CommerceProductOptionValue toEscapedModel() {
		return new CommerceProductOptionValueWrapper(_commerceProductOptionValue.toEscapedModel());
	}

	@Override
	public CommerceProductOptionValue toUnescapedModel() {
		return new CommerceProductOptionValueWrapper(_commerceProductOptionValue.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _commerceProductOptionValue.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceProductOptionValue.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceProductOptionValue.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceProductOptionValue.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceProductOptionValue> toCacheModel() {
		return _commerceProductOptionValue.toCacheModel();
	}

	@Override
	public int compareTo(CommerceProductOptionValue commerceProductOptionValue) {
		return _commerceProductOptionValue.compareTo(commerceProductOptionValue);
	}

	/**
	* Returns the priority of this commerce product option value.
	*
	* @return the priority of this commerce product option value
	*/
	@Override
	public int getPriority() {
		return _commerceProductOptionValue.getPriority();
	}

	@Override
	public int hashCode() {
		return _commerceProductOptionValue.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceProductOptionValue.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceProductOptionValueWrapper((CommerceProductOptionValue)_commerceProductOptionValue.clone());
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceProductOptionValue.getDefaultLanguageId();
	}

	/**
	* Returns the title of this commerce product option value.
	*
	* @return the title of this commerce product option value
	*/
	@Override
	public java.lang.String getTitle() {
		return _commerceProductOptionValue.getTitle();
	}

	/**
	* Returns the localized title of this commerce product option value in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this commerce product option value
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _commerceProductOptionValue.getTitle(languageId);
	}

	/**
	* Returns the localized title of this commerce product option value in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this commerce product option value
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _commerceProductOptionValue.getTitle(languageId, useDefault);
	}

	/**
	* Returns the localized title of this commerce product option value in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this commerce product option value
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _commerceProductOptionValue.getTitle(locale);
	}

	/**
	* Returns the localized title of this commerce product option value in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this commerce product option value. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _commerceProductOptionValue.getTitle(locale, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _commerceProductOptionValue.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _commerceProductOptionValue.getTitleCurrentValue();
	}

	/**
	* Returns the user name of this commerce product option value.
	*
	* @return the user name of this commerce product option value
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceProductOptionValue.getUserName();
	}

	/**
	* Returns the user uuid of this commerce product option value.
	*
	* @return the user uuid of this commerce product option value
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceProductOptionValue.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _commerceProductOptionValue.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceProductOptionValue.toXmlString();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceProductOptionValue.getAvailableLanguageIds();
	}

	/**
	* Returns the create date of this commerce product option value.
	*
	* @return the create date of this commerce product option value
	*/
	@Override
	public Date getCreateDate() {
		return _commerceProductOptionValue.getCreateDate();
	}

	/**
	* Returns the modified date of this commerce product option value.
	*
	* @return the modified date of this commerce product option value
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceProductOptionValue.getModifiedDate();
	}

	/**
	* Returns a map of the locales and localized titles of this commerce product option value.
	*
	* @return the locales and localized titles of this commerce product option value
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _commerceProductOptionValue.getTitleMap();
	}

	/**
	* Returns the commerce product option ID of this commerce product option value.
	*
	* @return the commerce product option ID of this commerce product option value
	*/
	@Override
	public long getCommerceProductOptionId() {
		return _commerceProductOptionValue.getCommerceProductOptionId();
	}

	/**
	* Returns the commerce product option value ID of this commerce product option value.
	*
	* @return the commerce product option value ID of this commerce product option value
	*/
	@Override
	public long getCommerceProductOptionValueId() {
		return _commerceProductOptionValue.getCommerceProductOptionValueId();
	}

	/**
	* Returns the company ID of this commerce product option value.
	*
	* @return the company ID of this commerce product option value
	*/
	@Override
	public long getCompanyId() {
		return _commerceProductOptionValue.getCompanyId();
	}

	/**
	* Returns the group ID of this commerce product option value.
	*
	* @return the group ID of this commerce product option value
	*/
	@Override
	public long getGroupId() {
		return _commerceProductOptionValue.getGroupId();
	}

	/**
	* Returns the primary key of this commerce product option value.
	*
	* @return the primary key of this commerce product option value
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceProductOptionValue.getPrimaryKey();
	}

	/**
	* Returns the user ID of this commerce product option value.
	*
	* @return the user ID of this commerce product option value
	*/
	@Override
	public long getUserId() {
		return _commerceProductOptionValue.getUserId();
	}

	@Override
	public void persist() {
		_commerceProductOptionValue.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductOptionValue.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductOptionValue.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceProductOptionValue.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce product option ID of this commerce product option value.
	*
	* @param commerceProductOptionId the commerce product option ID of this commerce product option value
	*/
	@Override
	public void setCommerceProductOptionId(long commerceProductOptionId) {
		_commerceProductOptionValue.setCommerceProductOptionId(commerceProductOptionId);
	}

	/**
	* Sets the commerce product option value ID of this commerce product option value.
	*
	* @param commerceProductOptionValueId the commerce product option value ID of this commerce product option value
	*/
	@Override
	public void setCommerceProductOptionValueId(
		long commerceProductOptionValueId) {
		_commerceProductOptionValue.setCommerceProductOptionValueId(commerceProductOptionValueId);
	}

	/**
	* Sets the company ID of this commerce product option value.
	*
	* @param companyId the company ID of this commerce product option value
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceProductOptionValue.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce product option value.
	*
	* @param createDate the create date of this commerce product option value
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceProductOptionValue.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceProductOptionValue.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceProductOptionValue.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceProductOptionValue.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce product option value.
	*
	* @param groupId the group ID of this commerce product option value
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceProductOptionValue.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce product option value.
	*
	* @param modifiedDate the modified date of this commerce product option value
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceProductOptionValue.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceProductOptionValue.setNew(n);
	}

	/**
	* Sets the primary key of this commerce product option value.
	*
	* @param primaryKey the primary key of this commerce product option value
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceProductOptionValue.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceProductOptionValue.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce product option value.
	*
	* @param priority the priority of this commerce product option value
	*/
	@Override
	public void setPriority(int priority) {
		_commerceProductOptionValue.setPriority(priority);
	}

	/**
	* Sets the title of this commerce product option value.
	*
	* @param title the title of this commerce product option value
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_commerceProductOptionValue.setTitle(title);
	}

	/**
	* Sets the localized title of this commerce product option value in the language.
	*
	* @param title the localized title of this commerce product option value
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_commerceProductOptionValue.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this commerce product option value in the language, and sets the default locale.
	*
	* @param title the localized title of this commerce product option value
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceProductOptionValue.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_commerceProductOptionValue.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this commerce product option value from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this commerce product option value
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_commerceProductOptionValue.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this commerce product option value from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this commerce product option value
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_commerceProductOptionValue.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this commerce product option value.
	*
	* @param userId the user ID of this commerce product option value
	*/
	@Override
	public void setUserId(long userId) {
		_commerceProductOptionValue.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce product option value.
	*
	* @param userName the user name of this commerce product option value
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceProductOptionValue.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce product option value.
	*
	* @param userUuid the user uuid of this commerce product option value
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceProductOptionValue.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductOptionValueWrapper)) {
			return false;
		}

		CommerceProductOptionValueWrapper commerceProductOptionValueWrapper = (CommerceProductOptionValueWrapper)obj;

		if (Objects.equals(_commerceProductOptionValue,
					commerceProductOptionValueWrapper._commerceProductOptionValue)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceProductOptionValue getWrappedModel() {
		return _commerceProductOptionValue;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceProductOptionValue.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceProductOptionValue.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceProductOptionValue.resetOriginalValues();
	}

	private final CommerceProductOptionValue _commerceProductOptionValue;
}
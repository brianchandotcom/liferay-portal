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
 * This class is a wrapper for {@link CommerceProductDefinitionOptionValueRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionValueRel
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelWrapper
	implements CommerceProductDefinitionOptionValueRel,
		ModelWrapper<CommerceProductDefinitionOptionValueRel> {
	public CommerceProductDefinitionOptionValueRelWrapper(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		_commerceProductDefinitionOptionValueRel = commerceProductDefinitionOptionValueRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceProductDefinitionOptionValueRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceProductDefinitionOptionValueRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceProductDefinitionOptionValueRelId",
			getCommerceProductDefinitionOptionValueRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceProductDefinitionOptionRelId",
			getCommerceProductDefinitionOptionRelId());
		attributes.put("title", getTitle());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceProductDefinitionOptionValueRelId = (Long)attributes.get(
				"commerceProductDefinitionOptionValueRelId");

		if (commerceProductDefinitionOptionValueRelId != null) {
			setCommerceProductDefinitionOptionValueRelId(commerceProductDefinitionOptionValueRelId);
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

		Long commerceProductDefinitionOptionRelId = (Long)attributes.get(
				"commerceProductDefinitionOptionRelId");

		if (commerceProductDefinitionOptionRelId != null) {
			setCommerceProductDefinitionOptionRelId(commerceProductDefinitionOptionRelId);
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
	public CommerceProductDefinitionOptionValueRel toEscapedModel() {
		return new CommerceProductDefinitionOptionValueRelWrapper(_commerceProductDefinitionOptionValueRel.toEscapedModel());
	}

	@Override
	public CommerceProductDefinitionOptionValueRel toUnescapedModel() {
		return new CommerceProductDefinitionOptionValueRelWrapper(_commerceProductDefinitionOptionValueRel.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _commerceProductDefinitionOptionValueRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceProductDefinitionOptionValueRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceProductDefinitionOptionValueRel.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceProductDefinitionOptionValueRel.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceProductDefinitionOptionValueRel> toCacheModel() {
		return _commerceProductDefinitionOptionValueRel.toCacheModel();
	}

	@Override
	public int compareTo(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		return _commerceProductDefinitionOptionValueRel.compareTo(commerceProductDefinitionOptionValueRel);
	}

	/**
	* Returns the priority of this commerce product definition option value rel.
	*
	* @return the priority of this commerce product definition option value rel
	*/
	@Override
	public int getPriority() {
		return _commerceProductDefinitionOptionValueRel.getPriority();
	}

	@Override
	public int hashCode() {
		return _commerceProductDefinitionOptionValueRel.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceProductDefinitionOptionValueRel.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceProductDefinitionOptionValueRelWrapper((CommerceProductDefinitionOptionValueRel)_commerceProductDefinitionOptionValueRel.clone());
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceProductDefinitionOptionValueRel.getDefaultLanguageId();
	}

	/**
	* Returns the title of this commerce product definition option value rel.
	*
	* @return the title of this commerce product definition option value rel
	*/
	@Override
	public java.lang.String getTitle() {
		return _commerceProductDefinitionOptionValueRel.getTitle();
	}

	/**
	* Returns the localized title of this commerce product definition option value rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this commerce product definition option value rel
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _commerceProductDefinitionOptionValueRel.getTitle(languageId);
	}

	/**
	* Returns the localized title of this commerce product definition option value rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this commerce product definition option value rel
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _commerceProductDefinitionOptionValueRel.getTitle(languageId,
			useDefault);
	}

	/**
	* Returns the localized title of this commerce product definition option value rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this commerce product definition option value rel
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _commerceProductDefinitionOptionValueRel.getTitle(locale);
	}

	/**
	* Returns the localized title of this commerce product definition option value rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this commerce product definition option value rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _commerceProductDefinitionOptionValueRel.getTitle(locale,
			useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _commerceProductDefinitionOptionValueRel.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _commerceProductDefinitionOptionValueRel.getTitleCurrentValue();
	}

	/**
	* Returns the user name of this commerce product definition option value rel.
	*
	* @return the user name of this commerce product definition option value rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceProductDefinitionOptionValueRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce product definition option value rel.
	*
	* @return the user uuid of this commerce product definition option value rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceProductDefinitionOptionValueRel.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _commerceProductDefinitionOptionValueRel.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceProductDefinitionOptionValueRel.toXmlString();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceProductDefinitionOptionValueRel.getAvailableLanguageIds();
	}

	/**
	* Returns the create date of this commerce product definition option value rel.
	*
	* @return the create date of this commerce product definition option value rel
	*/
	@Override
	public Date getCreateDate() {
		return _commerceProductDefinitionOptionValueRel.getCreateDate();
	}

	/**
	* Returns the modified date of this commerce product definition option value rel.
	*
	* @return the modified date of this commerce product definition option value rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceProductDefinitionOptionValueRel.getModifiedDate();
	}

	/**
	* Returns a map of the locales and localized titles of this commerce product definition option value rel.
	*
	* @return the locales and localized titles of this commerce product definition option value rel
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _commerceProductDefinitionOptionValueRel.getTitleMap();
	}

	/**
	* Returns the commerce product definition option rel ID of this commerce product definition option value rel.
	*
	* @return the commerce product definition option rel ID of this commerce product definition option value rel
	*/
	@Override
	public long getCommerceProductDefinitionOptionRelId() {
		return _commerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionRelId();
	}

	/**
	* Returns the commerce product definition option value rel ID of this commerce product definition option value rel.
	*
	* @return the commerce product definition option value rel ID of this commerce product definition option value rel
	*/
	@Override
	public long getCommerceProductDefinitionOptionValueRelId() {
		return _commerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionValueRelId();
	}

	/**
	* Returns the company ID of this commerce product definition option value rel.
	*
	* @return the company ID of this commerce product definition option value rel
	*/
	@Override
	public long getCompanyId() {
		return _commerceProductDefinitionOptionValueRel.getCompanyId();
	}

	/**
	* Returns the group ID of this commerce product definition option value rel.
	*
	* @return the group ID of this commerce product definition option value rel
	*/
	@Override
	public long getGroupId() {
		return _commerceProductDefinitionOptionValueRel.getGroupId();
	}

	/**
	* Returns the primary key of this commerce product definition option value rel.
	*
	* @return the primary key of this commerce product definition option value rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceProductDefinitionOptionValueRel.getPrimaryKey();
	}

	/**
	* Returns the user ID of this commerce product definition option value rel.
	*
	* @return the user ID of this commerce product definition option value rel
	*/
	@Override
	public long getUserId() {
		return _commerceProductDefinitionOptionValueRel.getUserId();
	}

	@Override
	public void persist() {
		_commerceProductDefinitionOptionValueRel.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductDefinitionOptionValueRel.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductDefinitionOptionValueRel.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceProductDefinitionOptionValueRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce product definition option rel ID of this commerce product definition option value rel.
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID of this commerce product definition option value rel
	*/
	@Override
	public void setCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId) {
		_commerceProductDefinitionOptionValueRel.setCommerceProductDefinitionOptionRelId(commerceProductDefinitionOptionRelId);
	}

	/**
	* Sets the commerce product definition option value rel ID of this commerce product definition option value rel.
	*
	* @param commerceProductDefinitionOptionValueRelId the commerce product definition option value rel ID of this commerce product definition option value rel
	*/
	@Override
	public void setCommerceProductDefinitionOptionValueRelId(
		long commerceProductDefinitionOptionValueRelId) {
		_commerceProductDefinitionOptionValueRel.setCommerceProductDefinitionOptionValueRelId(commerceProductDefinitionOptionValueRelId);
	}

	/**
	* Sets the company ID of this commerce product definition option value rel.
	*
	* @param companyId the company ID of this commerce product definition option value rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceProductDefinitionOptionValueRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce product definition option value rel.
	*
	* @param createDate the create date of this commerce product definition option value rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceProductDefinitionOptionValueRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceProductDefinitionOptionValueRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceProductDefinitionOptionValueRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceProductDefinitionOptionValueRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce product definition option value rel.
	*
	* @param groupId the group ID of this commerce product definition option value rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceProductDefinitionOptionValueRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce product definition option value rel.
	*
	* @param modifiedDate the modified date of this commerce product definition option value rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceProductDefinitionOptionValueRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceProductDefinitionOptionValueRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce product definition option value rel.
	*
	* @param primaryKey the primary key of this commerce product definition option value rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceProductDefinitionOptionValueRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceProductDefinitionOptionValueRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce product definition option value rel.
	*
	* @param priority the priority of this commerce product definition option value rel
	*/
	@Override
	public void setPriority(int priority) {
		_commerceProductDefinitionOptionValueRel.setPriority(priority);
	}

	/**
	* Sets the title of this commerce product definition option value rel.
	*
	* @param title the title of this commerce product definition option value rel
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_commerceProductDefinitionOptionValueRel.setTitle(title);
	}

	/**
	* Sets the localized title of this commerce product definition option value rel in the language.
	*
	* @param title the localized title of this commerce product definition option value rel
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_commerceProductDefinitionOptionValueRel.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this commerce product definition option value rel in the language, and sets the default locale.
	*
	* @param title the localized title of this commerce product definition option value rel
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceProductDefinitionOptionValueRel.setTitle(title, locale,
			defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_commerceProductDefinitionOptionValueRel.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this commerce product definition option value rel from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this commerce product definition option value rel
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_commerceProductDefinitionOptionValueRel.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this commerce product definition option value rel from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this commerce product definition option value rel
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_commerceProductDefinitionOptionValueRel.setTitleMap(titleMap,
			defaultLocale);
	}

	/**
	* Sets the user ID of this commerce product definition option value rel.
	*
	* @param userId the user ID of this commerce product definition option value rel
	*/
	@Override
	public void setUserId(long userId) {
		_commerceProductDefinitionOptionValueRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce product definition option value rel.
	*
	* @param userName the user name of this commerce product definition option value rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceProductDefinitionOptionValueRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce product definition option value rel.
	*
	* @param userUuid the user uuid of this commerce product definition option value rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceProductDefinitionOptionValueRel.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefinitionOptionValueRelWrapper)) {
			return false;
		}

		CommerceProductDefinitionOptionValueRelWrapper commerceProductDefinitionOptionValueRelWrapper =
			(CommerceProductDefinitionOptionValueRelWrapper)obj;

		if (Objects.equals(_commerceProductDefinitionOptionValueRel,
					commerceProductDefinitionOptionValueRelWrapper._commerceProductDefinitionOptionValueRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceProductDefinitionOptionValueRel getWrappedModel() {
		return _commerceProductDefinitionOptionValueRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceProductDefinitionOptionValueRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceProductDefinitionOptionValueRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceProductDefinitionOptionValueRel.resetOriginalValues();
	}

	private final CommerceProductDefinitionOptionValueRel _commerceProductDefinitionOptionValueRel;
}
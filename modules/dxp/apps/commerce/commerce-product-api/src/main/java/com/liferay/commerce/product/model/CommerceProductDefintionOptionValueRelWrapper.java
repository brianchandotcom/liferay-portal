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
 * This class is a wrapper for {@link CommerceProductDefintionOptionValueRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefintionOptionValueRel
 * @generated
 */
@ProviderType
public class CommerceProductDefintionOptionValueRelWrapper
	implements CommerceProductDefintionOptionValueRel,
		ModelWrapper<CommerceProductDefintionOptionValueRel> {
	public CommerceProductDefintionOptionValueRelWrapper(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		_commerceProductDefintionOptionValueRel = commerceProductDefintionOptionValueRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceProductDefintionOptionValueRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceProductDefintionOptionValueRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceProductDefintionOptionValueRelId",
			getCommerceProductDefintionOptionValueRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceProductDefintionOptionRelId",
			getCommerceProductDefintionOptionRelId());
		attributes.put("title", getTitle());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceProductDefintionOptionValueRelId = (Long)attributes.get(
				"commerceProductDefintionOptionValueRelId");

		if (commerceProductDefintionOptionValueRelId != null) {
			setCommerceProductDefintionOptionValueRelId(commerceProductDefintionOptionValueRelId);
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

		Long commerceProductDefintionOptionRelId = (Long)attributes.get(
				"commerceProductDefintionOptionRelId");

		if (commerceProductDefintionOptionRelId != null) {
			setCommerceProductDefintionOptionRelId(commerceProductDefintionOptionRelId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		Long priority = (Long)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public CommerceProductDefintionOptionValueRel toEscapedModel() {
		return new CommerceProductDefintionOptionValueRelWrapper(_commerceProductDefintionOptionValueRel.toEscapedModel());
	}

	@Override
	public CommerceProductDefintionOptionValueRel toUnescapedModel() {
		return new CommerceProductDefintionOptionValueRelWrapper(_commerceProductDefintionOptionValueRel.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _commerceProductDefintionOptionValueRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceProductDefintionOptionValueRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceProductDefintionOptionValueRel.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceProductDefintionOptionValueRel.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceProductDefintionOptionValueRel> toCacheModel() {
		return _commerceProductDefintionOptionValueRel.toCacheModel();
	}

	@Override
	public int compareTo(
		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
		return _commerceProductDefintionOptionValueRel.compareTo(commerceProductDefintionOptionValueRel);
	}

	@Override
	public int hashCode() {
		return _commerceProductDefintionOptionValueRel.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceProductDefintionOptionValueRel.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceProductDefintionOptionValueRelWrapper((CommerceProductDefintionOptionValueRel)_commerceProductDefintionOptionValueRel.clone());
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceProductDefintionOptionValueRel.getDefaultLanguageId();
	}

	/**
	* Returns the title of this commerce product defintion option value rel.
	*
	* @return the title of this commerce product defintion option value rel
	*/
	@Override
	public java.lang.String getTitle() {
		return _commerceProductDefintionOptionValueRel.getTitle();
	}

	/**
	* Returns the localized title of this commerce product defintion option value rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this commerce product defintion option value rel
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _commerceProductDefintionOptionValueRel.getTitle(languageId);
	}

	/**
	* Returns the localized title of this commerce product defintion option value rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this commerce product defintion option value rel
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _commerceProductDefintionOptionValueRel.getTitle(languageId,
			useDefault);
	}

	/**
	* Returns the localized title of this commerce product defintion option value rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this commerce product defintion option value rel
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _commerceProductDefintionOptionValueRel.getTitle(locale);
	}

	/**
	* Returns the localized title of this commerce product defintion option value rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this commerce product defintion option value rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _commerceProductDefintionOptionValueRel.getTitle(locale,
			useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _commerceProductDefintionOptionValueRel.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _commerceProductDefintionOptionValueRel.getTitleCurrentValue();
	}

	/**
	* Returns the user name of this commerce product defintion option value rel.
	*
	* @return the user name of this commerce product defintion option value rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceProductDefintionOptionValueRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce product defintion option value rel.
	*
	* @return the user uuid of this commerce product defintion option value rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceProductDefintionOptionValueRel.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _commerceProductDefintionOptionValueRel.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceProductDefintionOptionValueRel.toXmlString();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceProductDefintionOptionValueRel.getAvailableLanguageIds();
	}

	/**
	* Returns the create date of this commerce product defintion option value rel.
	*
	* @return the create date of this commerce product defintion option value rel
	*/
	@Override
	public Date getCreateDate() {
		return _commerceProductDefintionOptionValueRel.getCreateDate();
	}

	/**
	* Returns the modified date of this commerce product defintion option value rel.
	*
	* @return the modified date of this commerce product defintion option value rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceProductDefintionOptionValueRel.getModifiedDate();
	}

	/**
	* Returns a map of the locales and localized titles of this commerce product defintion option value rel.
	*
	* @return the locales and localized titles of this commerce product defintion option value rel
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _commerceProductDefintionOptionValueRel.getTitleMap();
	}

	/**
	* Returns the commerce product defintion option rel ID of this commerce product defintion option value rel.
	*
	* @return the commerce product defintion option rel ID of this commerce product defintion option value rel
	*/
	@Override
	public long getCommerceProductDefintionOptionRelId() {
		return _commerceProductDefintionOptionValueRel.getCommerceProductDefintionOptionRelId();
	}

	/**
	* Returns the commerce product defintion option value rel ID of this commerce product defintion option value rel.
	*
	* @return the commerce product defintion option value rel ID of this commerce product defintion option value rel
	*/
	@Override
	public long getCommerceProductDefintionOptionValueRelId() {
		return _commerceProductDefintionOptionValueRel.getCommerceProductDefintionOptionValueRelId();
	}

	/**
	* Returns the company ID of this commerce product defintion option value rel.
	*
	* @return the company ID of this commerce product defintion option value rel
	*/
	@Override
	public long getCompanyId() {
		return _commerceProductDefintionOptionValueRel.getCompanyId();
	}

	/**
	* Returns the group ID of this commerce product defintion option value rel.
	*
	* @return the group ID of this commerce product defintion option value rel
	*/
	@Override
	public long getGroupId() {
		return _commerceProductDefintionOptionValueRel.getGroupId();
	}

	/**
	* Returns the primary key of this commerce product defintion option value rel.
	*
	* @return the primary key of this commerce product defintion option value rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceProductDefintionOptionValueRel.getPrimaryKey();
	}

	/**
	* Returns the priority of this commerce product defintion option value rel.
	*
	* @return the priority of this commerce product defintion option value rel
	*/
	@Override
	public long getPriority() {
		return _commerceProductDefintionOptionValueRel.getPriority();
	}

	/**
	* Returns the user ID of this commerce product defintion option value rel.
	*
	* @return the user ID of this commerce product defintion option value rel
	*/
	@Override
	public long getUserId() {
		return _commerceProductDefintionOptionValueRel.getUserId();
	}

	@Override
	public void persist() {
		_commerceProductDefintionOptionValueRel.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductDefintionOptionValueRel.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductDefintionOptionValueRel.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceProductDefintionOptionValueRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce product defintion option rel ID of this commerce product defintion option value rel.
	*
	* @param commerceProductDefintionOptionRelId the commerce product defintion option rel ID of this commerce product defintion option value rel
	*/
	@Override
	public void setCommerceProductDefintionOptionRelId(
		long commerceProductDefintionOptionRelId) {
		_commerceProductDefintionOptionValueRel.setCommerceProductDefintionOptionRelId(commerceProductDefintionOptionRelId);
	}

	/**
	* Sets the commerce product defintion option value rel ID of this commerce product defintion option value rel.
	*
	* @param commerceProductDefintionOptionValueRelId the commerce product defintion option value rel ID of this commerce product defintion option value rel
	*/
	@Override
	public void setCommerceProductDefintionOptionValueRelId(
		long commerceProductDefintionOptionValueRelId) {
		_commerceProductDefintionOptionValueRel.setCommerceProductDefintionOptionValueRelId(commerceProductDefintionOptionValueRelId);
	}

	/**
	* Sets the company ID of this commerce product defintion option value rel.
	*
	* @param companyId the company ID of this commerce product defintion option value rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceProductDefintionOptionValueRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce product defintion option value rel.
	*
	* @param createDate the create date of this commerce product defintion option value rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceProductDefintionOptionValueRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceProductDefintionOptionValueRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceProductDefintionOptionValueRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceProductDefintionOptionValueRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce product defintion option value rel.
	*
	* @param groupId the group ID of this commerce product defintion option value rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceProductDefintionOptionValueRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce product defintion option value rel.
	*
	* @param modifiedDate the modified date of this commerce product defintion option value rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceProductDefintionOptionValueRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceProductDefintionOptionValueRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce product defintion option value rel.
	*
	* @param primaryKey the primary key of this commerce product defintion option value rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceProductDefintionOptionValueRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceProductDefintionOptionValueRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce product defintion option value rel.
	*
	* @param priority the priority of this commerce product defintion option value rel
	*/
	@Override
	public void setPriority(long priority) {
		_commerceProductDefintionOptionValueRel.setPriority(priority);
	}

	/**
	* Sets the title of this commerce product defintion option value rel.
	*
	* @param title the title of this commerce product defintion option value rel
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_commerceProductDefintionOptionValueRel.setTitle(title);
	}

	/**
	* Sets the localized title of this commerce product defintion option value rel in the language.
	*
	* @param title the localized title of this commerce product defintion option value rel
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_commerceProductDefintionOptionValueRel.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this commerce product defintion option value rel in the language, and sets the default locale.
	*
	* @param title the localized title of this commerce product defintion option value rel
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceProductDefintionOptionValueRel.setTitle(title, locale,
			defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_commerceProductDefintionOptionValueRel.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this commerce product defintion option value rel from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this commerce product defintion option value rel
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_commerceProductDefintionOptionValueRel.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this commerce product defintion option value rel from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this commerce product defintion option value rel
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_commerceProductDefintionOptionValueRel.setTitleMap(titleMap,
			defaultLocale);
	}

	/**
	* Sets the user ID of this commerce product defintion option value rel.
	*
	* @param userId the user ID of this commerce product defintion option value rel
	*/
	@Override
	public void setUserId(long userId) {
		_commerceProductDefintionOptionValueRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce product defintion option value rel.
	*
	* @param userName the user name of this commerce product defintion option value rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceProductDefintionOptionValueRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce product defintion option value rel.
	*
	* @param userUuid the user uuid of this commerce product defintion option value rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceProductDefintionOptionValueRel.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefintionOptionValueRelWrapper)) {
			return false;
		}

		CommerceProductDefintionOptionValueRelWrapper commerceProductDefintionOptionValueRelWrapper =
			(CommerceProductDefintionOptionValueRelWrapper)obj;

		if (Objects.equals(_commerceProductDefintionOptionValueRel,
					commerceProductDefintionOptionValueRelWrapper._commerceProductDefintionOptionValueRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceProductDefintionOptionValueRel getWrappedModel() {
		return _commerceProductDefintionOptionValueRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceProductDefintionOptionValueRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceProductDefintionOptionValueRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceProductDefintionOptionValueRel.resetOriginalValues();
	}

	private final CommerceProductDefintionOptionValueRel _commerceProductDefintionOptionValueRel;
}
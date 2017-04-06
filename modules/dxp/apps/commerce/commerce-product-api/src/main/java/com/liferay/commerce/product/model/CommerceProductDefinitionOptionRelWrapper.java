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
 * This class is a wrapper for {@link CommerceProductDefinitionOptionRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionRel
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionRelWrapper
	implements CommerceProductDefinitionOptionRel,
		ModelWrapper<CommerceProductDefinitionOptionRel> {
	public CommerceProductDefinitionOptionRelWrapper(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		_commerceProductDefinitionOptionRel = commerceProductDefinitionOptionRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceProductDefinitionOptionRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceProductDefinitionOptionRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceProductDefinitionOptionRelId",
			getCommerceProductDefinitionOptionRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceProductOptionId", getCommerceProductOptionId());
		attributes.put("commerceProductDefinitionId",
			getCommerceProductDefinitionId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("DDMFormFieldTypeName", getDDMFormFieldTypeName());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceProductDefinitionOptionRelId = (Long)attributes.get(
				"commerceProductDefinitionOptionRelId");

		if (commerceProductDefinitionOptionRelId != null) {
			setCommerceProductDefinitionOptionRelId(commerceProductDefinitionOptionRelId);
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

		Long commerceProductDefinitionId = (Long)attributes.get(
				"commerceProductDefinitionId");

		if (commerceProductDefinitionId != null) {
			setCommerceProductDefinitionId(commerceProductDefinitionId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String DDMFormFieldTypeName = (String)attributes.get(
				"DDMFormFieldTypeName");

		if (DDMFormFieldTypeName != null) {
			setDDMFormFieldTypeName(DDMFormFieldTypeName);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public CommerceProductDefinitionOptionRel toEscapedModel() {
		return new CommerceProductDefinitionOptionRelWrapper(_commerceProductDefinitionOptionRel.toEscapedModel());
	}

	@Override
	public CommerceProductDefinitionOptionRel toUnescapedModel() {
		return new CommerceProductDefinitionOptionRelWrapper(_commerceProductDefinitionOptionRel.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _commerceProductDefinitionOptionRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceProductDefinitionOptionRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceProductDefinitionOptionRel.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceProductDefinitionOptionRel.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceProductDefinitionOptionRel> toCacheModel() {
		return _commerceProductDefinitionOptionRel.toCacheModel();
	}

	@Override
	public int compareTo(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		return _commerceProductDefinitionOptionRel.compareTo(commerceProductDefinitionOptionRel);
	}

	/**
	* Returns the priority of this commerce product definition option rel.
	*
	* @return the priority of this commerce product definition option rel
	*/
	@Override
	public int getPriority() {
		return _commerceProductDefinitionOptionRel.getPriority();
	}

	@Override
	public int hashCode() {
		return _commerceProductDefinitionOptionRel.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceProductDefinitionOptionRel.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceProductDefinitionOptionRelWrapper((CommerceProductDefinitionOptionRel)_commerceProductDefinitionOptionRel.clone());
	}

	/**
	* Returns the ddm form field type name of this commerce product definition option rel.
	*
	* @return the ddm form field type name of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getDDMFormFieldTypeName() {
		return _commerceProductDefinitionOptionRel.getDDMFormFieldTypeName();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceProductDefinitionOptionRel.getDefaultLanguageId();
	}

	/**
	* Returns the description of this commerce product definition option rel.
	*
	* @return the description of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getDescription() {
		return _commerceProductDefinitionOptionRel.getDescription();
	}

	/**
	* Returns the localized description of this commerce product definition option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _commerceProductDefinitionOptionRel.getDescription(languageId);
	}

	/**
	* Returns the localized description of this commerce product definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _commerceProductDefinitionOptionRel.getDescription(languageId,
			useDefault);
	}

	/**
	* Returns the localized description of this commerce product definition option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _commerceProductDefinitionOptionRel.getDescription(locale);
	}

	/**
	* Returns the localized description of this commerce product definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce product definition option rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _commerceProductDefinitionOptionRel.getDescription(locale,
			useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _commerceProductDefinitionOptionRel.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _commerceProductDefinitionOptionRel.getDescriptionCurrentValue();
	}

	/**
	* Returns the name of this commerce product definition option rel.
	*
	* @return the name of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getName() {
		return _commerceProductDefinitionOptionRel.getName();
	}

	/**
	* Returns the localized name of this commerce product definition option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commerceProductDefinitionOptionRel.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce product definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commerceProductDefinitionOptionRel.getName(languageId,
			useDefault);
	}

	/**
	* Returns the localized name of this commerce product definition option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commerceProductDefinitionOptionRel.getName(locale);
	}

	/**
	* Returns the localized name of this commerce product definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce product definition option rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceProductDefinitionOptionRel.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commerceProductDefinitionOptionRel.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commerceProductDefinitionOptionRel.getNameCurrentValue();
	}

	/**
	* Returns the user name of this commerce product definition option rel.
	*
	* @return the user name of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceProductDefinitionOptionRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce product definition option rel.
	*
	* @return the user uuid of this commerce product definition option rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceProductDefinitionOptionRel.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _commerceProductDefinitionOptionRel.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceProductDefinitionOptionRel.toXmlString();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceProductDefinitionOptionRel.getAvailableLanguageIds();
	}

	/**
	* Returns the create date of this commerce product definition option rel.
	*
	* @return the create date of this commerce product definition option rel
	*/
	@Override
	public Date getCreateDate() {
		return _commerceProductDefinitionOptionRel.getCreateDate();
	}

	/**
	* Returns the modified date of this commerce product definition option rel.
	*
	* @return the modified date of this commerce product definition option rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceProductDefinitionOptionRel.getModifiedDate();
	}

	/**
	* Returns a map of the locales and localized descriptions of this commerce product definition option rel.
	*
	* @return the locales and localized descriptions of this commerce product definition option rel
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _commerceProductDefinitionOptionRel.getDescriptionMap();
	}

	/**
	* Returns a map of the locales and localized names of this commerce product definition option rel.
	*
	* @return the locales and localized names of this commerce product definition option rel
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commerceProductDefinitionOptionRel.getNameMap();
	}

	/**
	* Returns the commerce product definition ID of this commerce product definition option rel.
	*
	* @return the commerce product definition ID of this commerce product definition option rel
	*/
	@Override
	public long getCommerceProductDefinitionId() {
		return _commerceProductDefinitionOptionRel.getCommerceProductDefinitionId();
	}

	/**
	* Returns the commerce product definition option rel ID of this commerce product definition option rel.
	*
	* @return the commerce product definition option rel ID of this commerce product definition option rel
	*/
	@Override
	public long getCommerceProductDefinitionOptionRelId() {
		return _commerceProductDefinitionOptionRel.getCommerceProductDefinitionOptionRelId();
	}

	/**
	* Returns the commerce product option ID of this commerce product definition option rel.
	*
	* @return the commerce product option ID of this commerce product definition option rel
	*/
	@Override
	public long getCommerceProductOptionId() {
		return _commerceProductDefinitionOptionRel.getCommerceProductOptionId();
	}

	/**
	* Returns the company ID of this commerce product definition option rel.
	*
	* @return the company ID of this commerce product definition option rel
	*/
	@Override
	public long getCompanyId() {
		return _commerceProductDefinitionOptionRel.getCompanyId();
	}

	/**
	* Returns the group ID of this commerce product definition option rel.
	*
	* @return the group ID of this commerce product definition option rel
	*/
	@Override
	public long getGroupId() {
		return _commerceProductDefinitionOptionRel.getGroupId();
	}

	/**
	* Returns the primary key of this commerce product definition option rel.
	*
	* @return the primary key of this commerce product definition option rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceProductDefinitionOptionRel.getPrimaryKey();
	}

	/**
	* Returns the user ID of this commerce product definition option rel.
	*
	* @return the user ID of this commerce product definition option rel
	*/
	@Override
	public long getUserId() {
		return _commerceProductDefinitionOptionRel.getUserId();
	}

	@Override
	public void persist() {
		_commerceProductDefinitionOptionRel.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductDefinitionOptionRel.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductDefinitionOptionRel.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceProductDefinitionOptionRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce product definition ID of this commerce product definition option rel.
	*
	* @param commerceProductDefinitionId the commerce product definition ID of this commerce product definition option rel
	*/
	@Override
	public void setCommerceProductDefinitionId(long commerceProductDefinitionId) {
		_commerceProductDefinitionOptionRel.setCommerceProductDefinitionId(commerceProductDefinitionId);
	}

	/**
	* Sets the commerce product definition option rel ID of this commerce product definition option rel.
	*
	* @param commerceProductDefinitionOptionRelId the commerce product definition option rel ID of this commerce product definition option rel
	*/
	@Override
	public void setCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId) {
		_commerceProductDefinitionOptionRel.setCommerceProductDefinitionOptionRelId(commerceProductDefinitionOptionRelId);
	}

	/**
	* Sets the commerce product option ID of this commerce product definition option rel.
	*
	* @param commerceProductOptionId the commerce product option ID of this commerce product definition option rel
	*/
	@Override
	public void setCommerceProductOptionId(long commerceProductOptionId) {
		_commerceProductDefinitionOptionRel.setCommerceProductOptionId(commerceProductOptionId);
	}

	/**
	* Sets the company ID of this commerce product definition option rel.
	*
	* @param companyId the company ID of this commerce product definition option rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceProductDefinitionOptionRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce product definition option rel.
	*
	* @param createDate the create date of this commerce product definition option rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceProductDefinitionOptionRel.setCreateDate(createDate);
	}

	/**
	* Sets the ddm form field type name of this commerce product definition option rel.
	*
	* @param DDMFormFieldTypeName the ddm form field type name of this commerce product definition option rel
	*/
	@Override
	public void setDDMFormFieldTypeName(java.lang.String DDMFormFieldTypeName) {
		_commerceProductDefinitionOptionRel.setDDMFormFieldTypeName(DDMFormFieldTypeName);
	}

	/**
	* Sets the description of this commerce product definition option rel.
	*
	* @param description the description of this commerce product definition option rel
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_commerceProductDefinitionOptionRel.setDescription(description);
	}

	/**
	* Sets the localized description of this commerce product definition option rel in the language.
	*
	* @param description the localized description of this commerce product definition option rel
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_commerceProductDefinitionOptionRel.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this commerce product definition option rel in the language, and sets the default locale.
	*
	* @param description the localized description of this commerce product definition option rel
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_commerceProductDefinitionOptionRel.setDescription(description, locale,
			defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_commerceProductDefinitionOptionRel.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this commerce product definition option rel from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce product definition option rel
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_commerceProductDefinitionOptionRel.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this commerce product definition option rel from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce product definition option rel
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_commerceProductDefinitionOptionRel.setDescriptionMap(descriptionMap,
			defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceProductDefinitionOptionRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceProductDefinitionOptionRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceProductDefinitionOptionRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce product definition option rel.
	*
	* @param groupId the group ID of this commerce product definition option rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceProductDefinitionOptionRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce product definition option rel.
	*
	* @param modifiedDate the modified date of this commerce product definition option rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceProductDefinitionOptionRel.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce product definition option rel.
	*
	* @param name the name of this commerce product definition option rel
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceProductDefinitionOptionRel.setName(name);
	}

	/**
	* Sets the localized name of this commerce product definition option rel in the language.
	*
	* @param name the localized name of this commerce product definition option rel
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commerceProductDefinitionOptionRel.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce product definition option rel in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce product definition option rel
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceProductDefinitionOptionRel.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commerceProductDefinitionOptionRel.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce product definition option rel from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce product definition option rel
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commerceProductDefinitionOptionRel.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce product definition option rel from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce product definition option rel
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commerceProductDefinitionOptionRel.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceProductDefinitionOptionRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce product definition option rel.
	*
	* @param primaryKey the primary key of this commerce product definition option rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceProductDefinitionOptionRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceProductDefinitionOptionRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce product definition option rel.
	*
	* @param priority the priority of this commerce product definition option rel
	*/
	@Override
	public void setPriority(int priority) {
		_commerceProductDefinitionOptionRel.setPriority(priority);
	}

	/**
	* Sets the user ID of this commerce product definition option rel.
	*
	* @param userId the user ID of this commerce product definition option rel
	*/
	@Override
	public void setUserId(long userId) {
		_commerceProductDefinitionOptionRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce product definition option rel.
	*
	* @param userName the user name of this commerce product definition option rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceProductDefinitionOptionRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce product definition option rel.
	*
	* @param userUuid the user uuid of this commerce product definition option rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceProductDefinitionOptionRel.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefinitionOptionRelWrapper)) {
			return false;
		}

		CommerceProductDefinitionOptionRelWrapper commerceProductDefinitionOptionRelWrapper =
			(CommerceProductDefinitionOptionRelWrapper)obj;

		if (Objects.equals(_commerceProductDefinitionOptionRel,
					commerceProductDefinitionOptionRelWrapper._commerceProductDefinitionOptionRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceProductDefinitionOptionRel getWrappedModel() {
		return _commerceProductDefinitionOptionRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceProductDefinitionOptionRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceProductDefinitionOptionRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceProductDefinitionOptionRel.resetOriginalValues();
	}

	private final CommerceProductDefinitionOptionRel _commerceProductDefinitionOptionRel;
}
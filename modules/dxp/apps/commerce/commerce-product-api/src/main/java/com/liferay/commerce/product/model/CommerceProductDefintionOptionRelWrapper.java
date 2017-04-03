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
 * This class is a wrapper for {@link CommerceProductDefintionOptionRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefintionOptionRel
 * @generated
 */
@ProviderType
public class CommerceProductDefintionOptionRelWrapper
	implements CommerceProductDefintionOptionRel,
		ModelWrapper<CommerceProductDefintionOptionRel> {
	public CommerceProductDefintionOptionRelWrapper(
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
		_commerceProductDefintionOptionRel = commerceProductDefintionOptionRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceProductDefintionOptionRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceProductDefintionOptionRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceProductDefintionOptionRelId",
			getCommerceProductDefintionOptionRelId());
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
		Long commerceProductDefintionOptionRelId = (Long)attributes.get(
				"commerceProductDefintionOptionRelId");

		if (commerceProductDefintionOptionRelId != null) {
			setCommerceProductDefintionOptionRelId(commerceProductDefintionOptionRelId);
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

		String priority = (String)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@Override
	public CommerceProductDefintionOptionRel toEscapedModel() {
		return new CommerceProductDefintionOptionRelWrapper(_commerceProductDefintionOptionRel.toEscapedModel());
	}

	@Override
	public CommerceProductDefintionOptionRel toUnescapedModel() {
		return new CommerceProductDefintionOptionRelWrapper(_commerceProductDefintionOptionRel.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _commerceProductDefintionOptionRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceProductDefintionOptionRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceProductDefintionOptionRel.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceProductDefintionOptionRel.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceProductDefintionOptionRel> toCacheModel() {
		return _commerceProductDefintionOptionRel.toCacheModel();
	}

	@Override
	public int compareTo(
		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
		return _commerceProductDefintionOptionRel.compareTo(commerceProductDefintionOptionRel);
	}

	@Override
	public int hashCode() {
		return _commerceProductDefintionOptionRel.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceProductDefintionOptionRel.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceProductDefintionOptionRelWrapper((CommerceProductDefintionOptionRel)_commerceProductDefintionOptionRel.clone());
	}

	/**
	* Returns the ddm form field type name of this commerce product defintion option rel.
	*
	* @return the ddm form field type name of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getDDMFormFieldTypeName() {
		return _commerceProductDefintionOptionRel.getDDMFormFieldTypeName();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _commerceProductDefintionOptionRel.getDefaultLanguageId();
	}

	/**
	* Returns the description of this commerce product defintion option rel.
	*
	* @return the description of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getDescription() {
		return _commerceProductDefintionOptionRel.getDescription();
	}

	/**
	* Returns the localized description of this commerce product defintion option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _commerceProductDefintionOptionRel.getDescription(languageId);
	}

	/**
	* Returns the localized description of this commerce product defintion option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _commerceProductDefintionOptionRel.getDescription(languageId,
			useDefault);
	}

	/**
	* Returns the localized description of this commerce product defintion option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _commerceProductDefintionOptionRel.getDescription(locale);
	}

	/**
	* Returns the localized description of this commerce product defintion option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this commerce product defintion option rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _commerceProductDefintionOptionRel.getDescription(locale,
			useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _commerceProductDefintionOptionRel.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _commerceProductDefintionOptionRel.getDescriptionCurrentValue();
	}

	/**
	* Returns the name of this commerce product defintion option rel.
	*
	* @return the name of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getName() {
		return _commerceProductDefintionOptionRel.getName();
	}

	/**
	* Returns the localized name of this commerce product defintion option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _commerceProductDefintionOptionRel.getName(languageId);
	}

	/**
	* Returns the localized name of this commerce product defintion option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _commerceProductDefintionOptionRel.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this commerce product defintion option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _commerceProductDefintionOptionRel.getName(locale);
	}

	/**
	* Returns the localized name of this commerce product defintion option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this commerce product defintion option rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _commerceProductDefintionOptionRel.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _commerceProductDefintionOptionRel.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _commerceProductDefintionOptionRel.getNameCurrentValue();
	}

	/**
	* Returns the priority of this commerce product defintion option rel.
	*
	* @return the priority of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getPriority() {
		return _commerceProductDefintionOptionRel.getPriority();
	}

	/**
	* Returns the user name of this commerce product defintion option rel.
	*
	* @return the user name of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceProductDefintionOptionRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce product defintion option rel.
	*
	* @return the user uuid of this commerce product defintion option rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceProductDefintionOptionRel.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _commerceProductDefintionOptionRel.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceProductDefintionOptionRel.toXmlString();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _commerceProductDefintionOptionRel.getAvailableLanguageIds();
	}

	/**
	* Returns the create date of this commerce product defintion option rel.
	*
	* @return the create date of this commerce product defintion option rel
	*/
	@Override
	public Date getCreateDate() {
		return _commerceProductDefintionOptionRel.getCreateDate();
	}

	/**
	* Returns the modified date of this commerce product defintion option rel.
	*
	* @return the modified date of this commerce product defintion option rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceProductDefintionOptionRel.getModifiedDate();
	}

	/**
	* Returns a map of the locales and localized descriptions of this commerce product defintion option rel.
	*
	* @return the locales and localized descriptions of this commerce product defintion option rel
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _commerceProductDefintionOptionRel.getDescriptionMap();
	}

	/**
	* Returns a map of the locales and localized names of this commerce product defintion option rel.
	*
	* @return the locales and localized names of this commerce product defintion option rel
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _commerceProductDefintionOptionRel.getNameMap();
	}

	/**
	* Returns the commerce product definition ID of this commerce product defintion option rel.
	*
	* @return the commerce product definition ID of this commerce product defintion option rel
	*/
	@Override
	public long getCommerceProductDefinitionId() {
		return _commerceProductDefintionOptionRel.getCommerceProductDefinitionId();
	}

	/**
	* Returns the commerce product defintion option rel ID of this commerce product defintion option rel.
	*
	* @return the commerce product defintion option rel ID of this commerce product defintion option rel
	*/
	@Override
	public long getCommerceProductDefintionOptionRelId() {
		return _commerceProductDefintionOptionRel.getCommerceProductDefintionOptionRelId();
	}

	/**
	* Returns the commerce product option ID of this commerce product defintion option rel.
	*
	* @return the commerce product option ID of this commerce product defintion option rel
	*/
	@Override
	public long getCommerceProductOptionId() {
		return _commerceProductDefintionOptionRel.getCommerceProductOptionId();
	}

	/**
	* Returns the company ID of this commerce product defintion option rel.
	*
	* @return the company ID of this commerce product defintion option rel
	*/
	@Override
	public long getCompanyId() {
		return _commerceProductDefintionOptionRel.getCompanyId();
	}

	/**
	* Returns the group ID of this commerce product defintion option rel.
	*
	* @return the group ID of this commerce product defintion option rel
	*/
	@Override
	public long getGroupId() {
		return _commerceProductDefintionOptionRel.getGroupId();
	}

	/**
	* Returns the primary key of this commerce product defintion option rel.
	*
	* @return the primary key of this commerce product defintion option rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceProductDefintionOptionRel.getPrimaryKey();
	}

	/**
	* Returns the user ID of this commerce product defintion option rel.
	*
	* @return the user ID of this commerce product defintion option rel
	*/
	@Override
	public long getUserId() {
		return _commerceProductDefintionOptionRel.getUserId();
	}

	@Override
	public void persist() {
		_commerceProductDefintionOptionRel.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductDefintionOptionRel.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceProductDefintionOptionRel.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceProductDefintionOptionRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce product definition ID of this commerce product defintion option rel.
	*
	* @param commerceProductDefinitionId the commerce product definition ID of this commerce product defintion option rel
	*/
	@Override
	public void setCommerceProductDefinitionId(long commerceProductDefinitionId) {
		_commerceProductDefintionOptionRel.setCommerceProductDefinitionId(commerceProductDefinitionId);
	}

	/**
	* Sets the commerce product defintion option rel ID of this commerce product defintion option rel.
	*
	* @param commerceProductDefintionOptionRelId the commerce product defintion option rel ID of this commerce product defintion option rel
	*/
	@Override
	public void setCommerceProductDefintionOptionRelId(
		long commerceProductDefintionOptionRelId) {
		_commerceProductDefintionOptionRel.setCommerceProductDefintionOptionRelId(commerceProductDefintionOptionRelId);
	}

	/**
	* Sets the commerce product option ID of this commerce product defintion option rel.
	*
	* @param commerceProductOptionId the commerce product option ID of this commerce product defintion option rel
	*/
	@Override
	public void setCommerceProductOptionId(long commerceProductOptionId) {
		_commerceProductDefintionOptionRel.setCommerceProductOptionId(commerceProductOptionId);
	}

	/**
	* Sets the company ID of this commerce product defintion option rel.
	*
	* @param companyId the company ID of this commerce product defintion option rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceProductDefintionOptionRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce product defintion option rel.
	*
	* @param createDate the create date of this commerce product defintion option rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceProductDefintionOptionRel.setCreateDate(createDate);
	}

	/**
	* Sets the ddm form field type name of this commerce product defintion option rel.
	*
	* @param DDMFormFieldTypeName the ddm form field type name of this commerce product defintion option rel
	*/
	@Override
	public void setDDMFormFieldTypeName(java.lang.String DDMFormFieldTypeName) {
		_commerceProductDefintionOptionRel.setDDMFormFieldTypeName(DDMFormFieldTypeName);
	}

	/**
	* Sets the description of this commerce product defintion option rel.
	*
	* @param description the description of this commerce product defintion option rel
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_commerceProductDefintionOptionRel.setDescription(description);
	}

	/**
	* Sets the localized description of this commerce product defintion option rel in the language.
	*
	* @param description the localized description of this commerce product defintion option rel
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_commerceProductDefintionOptionRel.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this commerce product defintion option rel in the language, and sets the default locale.
	*
	* @param description the localized description of this commerce product defintion option rel
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_commerceProductDefintionOptionRel.setDescription(description, locale,
			defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_commerceProductDefintionOptionRel.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this commerce product defintion option rel from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce product defintion option rel
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_commerceProductDefintionOptionRel.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this commerce product defintion option rel from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this commerce product defintion option rel
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_commerceProductDefintionOptionRel.setDescriptionMap(descriptionMap,
			defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceProductDefintionOptionRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceProductDefintionOptionRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceProductDefintionOptionRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce product defintion option rel.
	*
	* @param groupId the group ID of this commerce product defintion option rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceProductDefintionOptionRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce product defintion option rel.
	*
	* @param modifiedDate the modified date of this commerce product defintion option rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceProductDefintionOptionRel.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce product defintion option rel.
	*
	* @param name the name of this commerce product defintion option rel
	*/
	@Override
	public void setName(java.lang.String name) {
		_commerceProductDefintionOptionRel.setName(name);
	}

	/**
	* Sets the localized name of this commerce product defintion option rel in the language.
	*
	* @param name the localized name of this commerce product defintion option rel
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_commerceProductDefintionOptionRel.setName(name, locale);
	}

	/**
	* Sets the localized name of this commerce product defintion option rel in the language, and sets the default locale.
	*
	* @param name the localized name of this commerce product defintion option rel
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceProductDefintionOptionRel.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_commerceProductDefintionOptionRel.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this commerce product defintion option rel from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this commerce product defintion option rel
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_commerceProductDefintionOptionRel.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this commerce product defintion option rel from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this commerce product defintion option rel
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_commerceProductDefintionOptionRel.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_commerceProductDefintionOptionRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce product defintion option rel.
	*
	* @param primaryKey the primary key of this commerce product defintion option rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceProductDefintionOptionRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceProductDefintionOptionRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce product defintion option rel.
	*
	* @param priority the priority of this commerce product defintion option rel
	*/
	@Override
	public void setPriority(java.lang.String priority) {
		_commerceProductDefintionOptionRel.setPriority(priority);
	}

	/**
	* Sets the user ID of this commerce product defintion option rel.
	*
	* @param userId the user ID of this commerce product defintion option rel
	*/
	@Override
	public void setUserId(long userId) {
		_commerceProductDefintionOptionRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce product defintion option rel.
	*
	* @param userName the user name of this commerce product defintion option rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceProductDefintionOptionRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce product defintion option rel.
	*
	* @param userUuid the user uuid of this commerce product defintion option rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceProductDefintionOptionRel.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefintionOptionRelWrapper)) {
			return false;
		}

		CommerceProductDefintionOptionRelWrapper commerceProductDefintionOptionRelWrapper =
			(CommerceProductDefintionOptionRelWrapper)obj;

		if (Objects.equals(_commerceProductDefintionOptionRel,
					commerceProductDefintionOptionRelWrapper._commerceProductDefintionOptionRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceProductDefintionOptionRel getWrappedModel() {
		return _commerceProductDefintionOptionRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceProductDefintionOptionRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceProductDefintionOptionRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceProductDefintionOptionRel.resetOriginalValues();
	}

	private final CommerceProductDefintionOptionRel _commerceProductDefintionOptionRel;
}
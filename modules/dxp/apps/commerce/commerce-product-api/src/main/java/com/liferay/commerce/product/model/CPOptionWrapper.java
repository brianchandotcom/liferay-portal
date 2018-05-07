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
 * This class is a wrapper for {@link CPOption}.
 * </p>
 *
 * @author Marco Leo
 * @see CPOption
 * @generated
 */
@ProviderType
public class CPOptionWrapper implements CPOption, ModelWrapper<CPOption> {
	public CPOptionWrapper(CPOption cpOption) {
		_cpOption = cpOption;
	}

	@Override
	public Class<?> getModelClass() {
		return CPOption.class;
	}

	@Override
	public String getModelClassName() {
		return CPOption.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPOptionId", getCPOptionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("DDMFormFieldTypeName", getDDMFormFieldTypeName());
		attributes.put("facetable", isFacetable());
		attributes.put("required", isRequired());
		attributes.put("skuContributor", isSkuContributor());
		attributes.put("key", getKey());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPOptionId = (Long)attributes.get("CPOptionId");

		if (CPOptionId != null) {
			setCPOptionId(CPOptionId);
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

		Boolean facetable = (Boolean)attributes.get("facetable");

		if (facetable != null) {
			setFacetable(facetable);
		}

		Boolean required = (Boolean)attributes.get("required");

		if (required != null) {
			setRequired(required);
		}

		Boolean skuContributor = (Boolean)attributes.get("skuContributor");

		if (skuContributor != null) {
			setSkuContributor(skuContributor);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new CPOptionWrapper((CPOption)_cpOption.clone());
	}

	@Override
	public int compareTo(CPOption cpOption) {
		return _cpOption.compareTo(cpOption);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _cpOption.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this cp option.
	*
	* @return the company ID of this cp option
	*/
	@Override
	public long getCompanyId() {
		return _cpOption.getCompanyId();
	}

	/**
	* Returns the cp option ID of this cp option.
	*
	* @return the cp option ID of this cp option
	*/
	@Override
	public long getCPOptionId() {
		return _cpOption.getCPOptionId();
	}

	@Override
	public java.util.List<CPOptionValue> getCPOptionValues() {
		return _cpOption.getCPOptionValues();
	}

	/**
	* Returns the create date of this cp option.
	*
	* @return the create date of this cp option
	*/
	@Override
	public Date getCreateDate() {
		return _cpOption.getCreateDate();
	}

	/**
	* Returns the ddm form field type name of this cp option.
	*
	* @return the ddm form field type name of this cp option
	*/
	@Override
	public String getDDMFormFieldTypeName() {
		return _cpOption.getDDMFormFieldTypeName();
	}

	@Override
	public String getDefaultLanguageId() {
		return _cpOption.getDefaultLanguageId();
	}

	/**
	* Returns the description of this cp option.
	*
	* @return the description of this cp option
	*/
	@Override
	public String getDescription() {
		return _cpOption.getDescription();
	}

	/**
	* Returns the localized description of this cp option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this cp option
	*/
	@Override
	public String getDescription(java.util.Locale locale) {
		return _cpOption.getDescription(locale);
	}

	/**
	* Returns the localized description of this cp option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp option. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return _cpOption.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this cp option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this cp option
	*/
	@Override
	public String getDescription(String languageId) {
		return _cpOption.getDescription(languageId);
	}

	/**
	* Returns the localized description of this cp option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp option
	*/
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _cpOption.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _cpOption.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _cpOption.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this cp option.
	*
	* @return the locales and localized descriptions of this cp option
	*/
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return _cpOption.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpOption.getExpandoBridge();
	}

	/**
	* Returns the facetable of this cp option.
	*
	* @return the facetable of this cp option
	*/
	@Override
	public boolean getFacetable() {
		return _cpOption.getFacetable();
	}

	/**
	* Returns the group ID of this cp option.
	*
	* @return the group ID of this cp option
	*/
	@Override
	public long getGroupId() {
		return _cpOption.getGroupId();
	}

	/**
	* Returns the key of this cp option.
	*
	* @return the key of this cp option
	*/
	@Override
	public String getKey() {
		return _cpOption.getKey();
	}

	/**
	* Returns the last publish date of this cp option.
	*
	* @return the last publish date of this cp option
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpOption.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp option.
	*
	* @return the modified date of this cp option
	*/
	@Override
	public Date getModifiedDate() {
		return _cpOption.getModifiedDate();
	}

	/**
	* Returns the name of this cp option.
	*
	* @return the name of this cp option
	*/
	@Override
	public String getName() {
		return _cpOption.getName();
	}

	/**
	* Returns the localized name of this cp option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this cp option
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return _cpOption.getName(locale);
	}

	/**
	* Returns the localized name of this cp option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this cp option. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return _cpOption.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this cp option in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this cp option
	*/
	@Override
	public String getName(String languageId) {
		return _cpOption.getName(languageId);
	}

	/**
	* Returns the localized name of this cp option in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this cp option
	*/
	@Override
	public String getName(String languageId, boolean useDefault) {
		return _cpOption.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _cpOption.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _cpOption.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this cp option.
	*
	* @return the locales and localized names of this cp option
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return _cpOption.getNameMap();
	}

	/**
	* Returns the primary key of this cp option.
	*
	* @return the primary key of this cp option
	*/
	@Override
	public long getPrimaryKey() {
		return _cpOption.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpOption.getPrimaryKeyObj();
	}

	/**
	* Returns the required of this cp option.
	*
	* @return the required of this cp option
	*/
	@Override
	public boolean getRequired() {
		return _cpOption.getRequired();
	}

	/**
	* Returns the sku contributor of this cp option.
	*
	* @return the sku contributor of this cp option
	*/
	@Override
	public boolean getSkuContributor() {
		return _cpOption.getSkuContributor();
	}

	/**
	* Returns the user ID of this cp option.
	*
	* @return the user ID of this cp option
	*/
	@Override
	public long getUserId() {
		return _cpOption.getUserId();
	}

	/**
	* Returns the user name of this cp option.
	*
	* @return the user name of this cp option
	*/
	@Override
	public String getUserName() {
		return _cpOption.getUserName();
	}

	/**
	* Returns the user uuid of this cp option.
	*
	* @return the user uuid of this cp option
	*/
	@Override
	public String getUserUuid() {
		return _cpOption.getUserUuid();
	}

	/**
	* Returns the uuid of this cp option.
	*
	* @return the uuid of this cp option
	*/
	@Override
	public String getUuid() {
		return _cpOption.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpOption.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpOption.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpOption.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this cp option is facetable.
	*
	* @return <code>true</code> if this cp option is facetable; <code>false</code> otherwise
	*/
	@Override
	public boolean isFacetable() {
		return _cpOption.isFacetable();
	}

	@Override
	public boolean isNew() {
		return _cpOption.isNew();
	}

	/**
	* Returns <code>true</code> if this cp option is required.
	*
	* @return <code>true</code> if this cp option is required; <code>false</code> otherwise
	*/
	@Override
	public boolean isRequired() {
		return _cpOption.isRequired();
	}

	/**
	* Returns <code>true</code> if this cp option is sku contributor.
	*
	* @return <code>true</code> if this cp option is sku contributor; <code>false</code> otherwise
	*/
	@Override
	public boolean isSkuContributor() {
		return _cpOption.isSkuContributor();
	}

	@Override
	public void persist() {
		_cpOption.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpOption.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpOption.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpOption.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp option.
	*
	* @param companyId the company ID of this cp option
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpOption.setCompanyId(companyId);
	}

	/**
	* Sets the cp option ID of this cp option.
	*
	* @param CPOptionId the cp option ID of this cp option
	*/
	@Override
	public void setCPOptionId(long CPOptionId) {
		_cpOption.setCPOptionId(CPOptionId);
	}

	/**
	* Sets the create date of this cp option.
	*
	* @param createDate the create date of this cp option
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpOption.setCreateDate(createDate);
	}

	/**
	* Sets the ddm form field type name of this cp option.
	*
	* @param DDMFormFieldTypeName the ddm form field type name of this cp option
	*/
	@Override
	public void setDDMFormFieldTypeName(String DDMFormFieldTypeName) {
		_cpOption.setDDMFormFieldTypeName(DDMFormFieldTypeName);
	}

	/**
	* Sets the description of this cp option.
	*
	* @param description the description of this cp option
	*/
	@Override
	public void setDescription(String description) {
		_cpOption.setDescription(description);
	}

	/**
	* Sets the localized description of this cp option in the language.
	*
	* @param description the localized description of this cp option
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		_cpOption.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this cp option in the language, and sets the default locale.
	*
	* @param description the localized description of this cp option
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpOption.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_cpOption.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this cp option from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this cp option
	*/
	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		_cpOption.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this cp option from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this cp option
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {
		_cpOption.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpOption.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpOption.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpOption.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets whether this cp option is facetable.
	*
	* @param facetable the facetable of this cp option
	*/
	@Override
	public void setFacetable(boolean facetable) {
		_cpOption.setFacetable(facetable);
	}

	/**
	* Sets the group ID of this cp option.
	*
	* @param groupId the group ID of this cp option
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpOption.setGroupId(groupId);
	}

	/**
	* Sets the key of this cp option.
	*
	* @param key the key of this cp option
	*/
	@Override
	public void setKey(String key) {
		_cpOption.setKey(key);
	}

	/**
	* Sets the last publish date of this cp option.
	*
	* @param lastPublishDate the last publish date of this cp option
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpOption.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cp option.
	*
	* @param modifiedDate the modified date of this cp option
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpOption.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this cp option.
	*
	* @param name the name of this cp option
	*/
	@Override
	public void setName(String name) {
		_cpOption.setName(name);
	}

	/**
	* Sets the localized name of this cp option in the language.
	*
	* @param name the localized name of this cp option
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		_cpOption.setName(name, locale);
	}

	/**
	* Sets the localized name of this cp option in the language, and sets the default locale.
	*
	* @param name the localized name of this cp option
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpOption.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_cpOption.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this cp option from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this cp option
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		_cpOption.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this cp option from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this cp option
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		_cpOption.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_cpOption.setNew(n);
	}

	/**
	* Sets the primary key of this cp option.
	*
	* @param primaryKey the primary key of this cp option
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpOption.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpOption.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets whether this cp option is required.
	*
	* @param required the required of this cp option
	*/
	@Override
	public void setRequired(boolean required) {
		_cpOption.setRequired(required);
	}

	/**
	* Sets whether this cp option is sku contributor.
	*
	* @param skuContributor the sku contributor of this cp option
	*/
	@Override
	public void setSkuContributor(boolean skuContributor) {
		_cpOption.setSkuContributor(skuContributor);
	}

	/**
	* Sets the user ID of this cp option.
	*
	* @param userId the user ID of this cp option
	*/
	@Override
	public void setUserId(long userId) {
		_cpOption.setUserId(userId);
	}

	/**
	* Sets the user name of this cp option.
	*
	* @param userName the user name of this cp option
	*/
	@Override
	public void setUserName(String userName) {
		_cpOption.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp option.
	*
	* @param userUuid the user uuid of this cp option
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpOption.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp option.
	*
	* @param uuid the uuid of this cp option
	*/
	@Override
	public void setUuid(String uuid) {
		_cpOption.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPOption> toCacheModel() {
		return _cpOption.toCacheModel();
	}

	@Override
	public CPOption toEscapedModel() {
		return new CPOptionWrapper(_cpOption.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpOption.toString();
	}

	@Override
	public CPOption toUnescapedModel() {
		return new CPOptionWrapper(_cpOption.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpOption.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPOptionWrapper)) {
			return false;
		}

		CPOptionWrapper cpOptionWrapper = (CPOptionWrapper)obj;

		if (Objects.equals(_cpOption, cpOptionWrapper._cpOption)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpOption.getStagedModelType();
	}

	@Override
	public CPOption getWrappedModel() {
		return _cpOption;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpOption.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpOption.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpOption.resetOriginalValues();
	}

	private final CPOption _cpOption;
}
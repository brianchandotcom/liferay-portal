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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CPDefinitionOptionRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRel
 * @generated
 */
public class CPDefinitionOptionRelWrapper
	extends BaseModelWrapper<CPDefinitionOptionRel>
	implements CPDefinitionOptionRel, ModelWrapper<CPDefinitionOptionRel> {

	public CPDefinitionOptionRelWrapper(
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		super(cpDefinitionOptionRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionOptionRelId", getCPDefinitionOptionRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CPOptionId", getCPOptionId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("DDMFormFieldTypeName", getDDMFormFieldTypeName());
		attributes.put("priority", getPriority());
		attributes.put("facetable", isFacetable());
		attributes.put("required", isRequired());
		attributes.put("skuContributor", isSkuContributor());
		attributes.put("key", getKey());
		attributes.put("priceType", getPriceType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionOptionRelId = (Long)attributes.get(
			"CPDefinitionOptionRelId");

		if (CPDefinitionOptionRelId != null) {
			setCPDefinitionOptionRelId(CPDefinitionOptionRelId);
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

		Long CPOptionId = (Long)attributes.get("CPOptionId");

		if (CPOptionId != null) {
			setCPOptionId(CPOptionId);
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

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
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

		String priceType = (String)attributes.get("priceType");

		if (priceType != null) {
			setPriceType(priceType);
		}
	}

	@Override
	public CPDefinitionOptionRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CPDefinitionOptionValueRel
		fetchPreselectedCPDefinitionOptionValueRel() {

		return model.fetchPreselectedCPDefinitionOptionValueRel();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this cp definition option rel.
	 *
	 * @return the company ID of this cp definition option rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPDefinition();
	}

	/**
	 * Returns the cp definition ID of this cp definition option rel.
	 *
	 * @return the cp definition ID of this cp definition option rel
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the cp definition option rel ID of this cp definition option rel.
	 *
	 * @return the cp definition option rel ID of this cp definition option rel
	 */
	@Override
	public long getCPDefinitionOptionRelId() {
		return model.getCPDefinitionOptionRelId();
	}

	@Override
	public java.util.List<CPDefinitionOptionValueRel>
		getCPDefinitionOptionValueRels() {

		return model.getCPDefinitionOptionValueRels();
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount() {
		return model.getCPDefinitionOptionValueRelsCount();
	}

	@Override
	public CPOption getCPOption()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPOption();
	}

	/**
	 * Returns the cp option ID of this cp definition option rel.
	 *
	 * @return the cp option ID of this cp definition option rel
	 */
	@Override
	public long getCPOptionId() {
		return model.getCPOptionId();
	}

	/**
	 * Returns the create date of this cp definition option rel.
	 *
	 * @return the create date of this cp definition option rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp definition option rel.
	 *
	 * @return the ct collection ID of this cp definition option rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the ddm form field type name of this cp definition option rel.
	 *
	 * @return the ddm form field type name of this cp definition option rel
	 */
	@Override
	public String getDDMFormFieldTypeName() {
		return model.getDDMFormFieldTypeName();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this cp definition option rel.
	 *
	 * @return the description of this cp definition option rel
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this cp definition option rel in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this cp definition option rel
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this cp definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this cp definition option rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this cp definition option rel in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this cp definition option rel
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this cp definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this cp definition option rel
	 */
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return model.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return model.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return model.getDescriptionCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized descriptions of this cp definition option rel.
	 *
	 * @return the locales and localized descriptions of this cp definition option rel
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the facetable of this cp definition option rel.
	 *
	 * @return the facetable of this cp definition option rel
	 */
	@Override
	public boolean getFacetable() {
		return model.getFacetable();
	}

	/**
	 * Returns the group ID of this cp definition option rel.
	 *
	 * @return the group ID of this cp definition option rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the key of this cp definition option rel.
	 *
	 * @return the key of this cp definition option rel
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the modified date of this cp definition option rel.
	 *
	 * @return the modified date of this cp definition option rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp definition option rel.
	 *
	 * @return the mvcc version of this cp definition option rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this cp definition option rel.
	 *
	 * @return the name of this cp definition option rel
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this cp definition option rel in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this cp definition option rel
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this cp definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this cp definition option rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this cp definition option rel in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this cp definition option rel
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this cp definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this cp definition option rel
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
	 * Returns a map of the locales and localized names of this cp definition option rel.
	 *
	 * @return the locales and localized names of this cp definition option rel
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the price type of this cp definition option rel.
	 *
	 * @return the price type of this cp definition option rel
	 */
	@Override
	public String getPriceType() {
		return model.getPriceType();
	}

	/**
	 * Returns the primary key of this cp definition option rel.
	 *
	 * @return the primary key of this cp definition option rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this cp definition option rel.
	 *
	 * @return the priority of this cp definition option rel
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the required of this cp definition option rel.
	 *
	 * @return the required of this cp definition option rel
	 */
	@Override
	public boolean getRequired() {
		return model.getRequired();
	}

	/**
	 * Returns the sku contributor of this cp definition option rel.
	 *
	 * @return the sku contributor of this cp definition option rel
	 */
	@Override
	public boolean getSkuContributor() {
		return model.getSkuContributor();
	}

	/**
	 * Returns the user ID of this cp definition option rel.
	 *
	 * @return the user ID of this cp definition option rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp definition option rel.
	 *
	 * @return the user name of this cp definition option rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp definition option rel.
	 *
	 * @return the user uuid of this cp definition option rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp definition option rel.
	 *
	 * @return the uuid of this cp definition option rel
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this cp definition option rel is facetable.
	 *
	 * @return <code>true</code> if this cp definition option rel is facetable; <code>false</code> otherwise
	 */
	@Override
	public boolean isFacetable() {
		return model.isFacetable();
	}

	@Override
	public boolean isPriceContributor() {
		return model.isPriceContributor();
	}

	@Override
	public boolean isPriceTypeDynamic() {
		return model.isPriceTypeDynamic();
	}

	@Override
	public boolean isPriceTypeStatic() {
		return model.isPriceTypeStatic();
	}

	/**
	 * Returns <code>true</code> if this cp definition option rel is required.
	 *
	 * @return <code>true</code> if this cp definition option rel is required; <code>false</code> otherwise
	 */
	@Override
	public boolean isRequired() {
		return model.isRequired();
	}

	/**
	 * Returns <code>true</code> if this cp definition option rel is sku contributor.
	 *
	 * @return <code>true</code> if this cp definition option rel is sku contributor; <code>false</code> otherwise
	 */
	@Override
	public boolean isSkuContributor() {
		return model.isSkuContributor();
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
	 * Sets the company ID of this cp definition option rel.
	 *
	 * @param companyId the company ID of this cp definition option rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition ID of this cp definition option rel.
	 *
	 * @param CPDefinitionId the cp definition ID of this cp definition option rel
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the cp definition option rel ID of this cp definition option rel.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID of this cp definition option rel
	 */
	@Override
	public void setCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {
		model.setCPDefinitionOptionRelId(CPDefinitionOptionRelId);
	}

	/**
	 * Sets the cp option ID of this cp definition option rel.
	 *
	 * @param CPOptionId the cp option ID of this cp definition option rel
	 */
	@Override
	public void setCPOptionId(long CPOptionId) {
		model.setCPOptionId(CPOptionId);
	}

	/**
	 * Sets the create date of this cp definition option rel.
	 *
	 * @param createDate the create date of this cp definition option rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp definition option rel.
	 *
	 * @param ctCollectionId the ct collection ID of this cp definition option rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ddm form field type name of this cp definition option rel.
	 *
	 * @param DDMFormFieldTypeName the ddm form field type name of this cp definition option rel
	 */
	@Override
	public void setDDMFormFieldTypeName(String DDMFormFieldTypeName) {
		model.setDDMFormFieldTypeName(DDMFormFieldTypeName);
	}

	/**
	 * Sets the description of this cp definition option rel.
	 *
	 * @param description the description of this cp definition option rel
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this cp definition option rel in the language.
	 *
	 * @param description the localized description of this cp definition option rel
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this cp definition option rel in the language, and sets the default locale.
	 *
	 * @param description the localized description of this cp definition option rel
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescription(
		String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		model.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized descriptions of this cp definition option rel from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this cp definition option rel
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this cp definition option rel from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this cp definition option rel
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets whether this cp definition option rel is facetable.
	 *
	 * @param facetable the facetable of this cp definition option rel
	 */
	@Override
	public void setFacetable(boolean facetable) {
		model.setFacetable(facetable);
	}

	/**
	 * Sets the group ID of this cp definition option rel.
	 *
	 * @param groupId the group ID of this cp definition option rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the key of this cp definition option rel.
	 *
	 * @param key the key of this cp definition option rel
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the modified date of this cp definition option rel.
	 *
	 * @param modifiedDate the modified date of this cp definition option rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp definition option rel.
	 *
	 * @param mvccVersion the mvcc version of this cp definition option rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this cp definition option rel.
	 *
	 * @param name the name of this cp definition option rel
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this cp definition option rel in the language.
	 *
	 * @param name the localized name of this cp definition option rel
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this cp definition option rel in the language, and sets the default locale.
	 *
	 * @param name the localized name of this cp definition option rel
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
	 * Sets the localized names of this cp definition option rel from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this cp definition option rel
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this cp definition option rel from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this cp definition option rel
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the price type of this cp definition option rel.
	 *
	 * @param priceType the price type of this cp definition option rel
	 */
	@Override
	public void setPriceType(String priceType) {
		model.setPriceType(priceType);
	}

	/**
	 * Sets the primary key of this cp definition option rel.
	 *
	 * @param primaryKey the primary key of this cp definition option rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this cp definition option rel.
	 *
	 * @param priority the priority of this cp definition option rel
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets whether this cp definition option rel is required.
	 *
	 * @param required the required of this cp definition option rel
	 */
	@Override
	public void setRequired(boolean required) {
		model.setRequired(required);
	}

	/**
	 * Sets whether this cp definition option rel is sku contributor.
	 *
	 * @param skuContributor the sku contributor of this cp definition option rel
	 */
	@Override
	public void setSkuContributor(boolean skuContributor) {
		model.setSkuContributor(skuContributor);
	}

	/**
	 * Sets the user ID of this cp definition option rel.
	 *
	 * @param userId the user ID of this cp definition option rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp definition option rel.
	 *
	 * @param userName the user name of this cp definition option rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp definition option rel.
	 *
	 * @param userUuid the user uuid of this cp definition option rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp definition option rel.
	 *
	 * @param uuid the uuid of this cp definition option rel
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<CPDefinitionOptionRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPDefinitionOptionRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPDefinitionOptionRelWrapper wrap(
		CPDefinitionOptionRel cpDefinitionOptionRel) {

		return new CPDefinitionOptionRelWrapper(cpDefinitionOptionRel);
	}

}
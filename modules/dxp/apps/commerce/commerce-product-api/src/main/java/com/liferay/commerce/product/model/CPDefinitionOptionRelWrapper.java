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
 * This class is a wrapper for {@link CPDefinitionOptionRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRel
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelWrapper implements CPDefinitionOptionRel,
	ModelWrapper<CPDefinitionOptionRel> {
	public CPDefinitionOptionRelWrapper(
		CPDefinitionOptionRel cpDefinitionOptionRel) {
		_cpDefinitionOptionRel = cpDefinitionOptionRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionOptionRel.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionOptionRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

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
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("DDMFormFieldTypeName", getDDMFormFieldTypeName());
		attributes.put("priority", getPriority());
		attributes.put("facetable", isFacetable());
		attributes.put("required", isRequired());
		attributes.put("skuContributor", isSkuContributor());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
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
	}

	@Override
	public java.lang.Object clone() {
		return new CPDefinitionOptionRelWrapper((CPDefinitionOptionRel)_cpDefinitionOptionRel.clone());
	}

	@Override
	public int compareTo(CPDefinitionOptionRel cpDefinitionOptionRel) {
		return _cpDefinitionOptionRel.compareTo(cpDefinitionOptionRel);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _cpDefinitionOptionRel.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this cp definition option rel.
	*
	* @return the company ID of this cp definition option rel
	*/
	@Override
	public long getCompanyId() {
		return _cpDefinitionOptionRel.getCompanyId();
	}

	@Override
	public CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRel.getCPDefinition();
	}

	/**
	* Returns the cp definition ID of this cp definition option rel.
	*
	* @return the cp definition ID of this cp definition option rel
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpDefinitionOptionRel.getCPDefinitionId();
	}

	/**
	* Returns the cp definition option rel ID of this cp definition option rel.
	*
	* @return the cp definition option rel ID of this cp definition option rel
	*/
	@Override
	public long getCPDefinitionOptionRelId() {
		return _cpDefinitionOptionRel.getCPDefinitionOptionRelId();
	}

	@Override
	public java.util.List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels() {
		return _cpDefinitionOptionRel.getCPDefinitionOptionValueRels();
	}

	@Override
	public CPOption getCPOption()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRel.getCPOption();
	}

	/**
	* Returns the cp option ID of this cp definition option rel.
	*
	* @return the cp option ID of this cp definition option rel
	*/
	@Override
	public long getCPOptionId() {
		return _cpDefinitionOptionRel.getCPOptionId();
	}

	/**
	* Returns the create date of this cp definition option rel.
	*
	* @return the create date of this cp definition option rel
	*/
	@Override
	public Date getCreateDate() {
		return _cpDefinitionOptionRel.getCreateDate();
	}

	/**
	* Returns the ddm form field type name of this cp definition option rel.
	*
	* @return the ddm form field type name of this cp definition option rel
	*/
	@Override
	public java.lang.String getDDMFormFieldTypeName() {
		return _cpDefinitionOptionRel.getDDMFormFieldTypeName();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _cpDefinitionOptionRel.getDefaultLanguageId();
	}

	/**
	* Returns the description of this cp definition option rel.
	*
	* @return the description of this cp definition option rel
	*/
	@Override
	public java.lang.String getDescription() {
		return _cpDefinitionOptionRel.getDescription();
	}

	/**
	* Returns the localized description of this cp definition option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this cp definition option rel
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _cpDefinitionOptionRel.getDescription(locale);
	}

	/**
	* Returns the localized description of this cp definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp definition option rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _cpDefinitionOptionRel.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this cp definition option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this cp definition option rel
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _cpDefinitionOptionRel.getDescription(languageId);
	}

	/**
	* Returns the localized description of this cp definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this cp definition option rel
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _cpDefinitionOptionRel.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _cpDefinitionOptionRel.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _cpDefinitionOptionRel.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this cp definition option rel.
	*
	* @return the locales and localized descriptions of this cp definition option rel
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _cpDefinitionOptionRel.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDefinitionOptionRel.getExpandoBridge();
	}

	/**
	* Returns the facetable of this cp definition option rel.
	*
	* @return the facetable of this cp definition option rel
	*/
	@Override
	public boolean getFacetable() {
		return _cpDefinitionOptionRel.getFacetable();
	}

	/**
	* Returns the group ID of this cp definition option rel.
	*
	* @return the group ID of this cp definition option rel
	*/
	@Override
	public long getGroupId() {
		return _cpDefinitionOptionRel.getGroupId();
	}

	/**
	* Returns the modified date of this cp definition option rel.
	*
	* @return the modified date of this cp definition option rel
	*/
	@Override
	public Date getModifiedDate() {
		return _cpDefinitionOptionRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp definition option rel.
	*
	* @return the primary key of this cp definition option rel
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDefinitionOptionRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDefinitionOptionRel.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this cp definition option rel.
	*
	* @return the priority of this cp definition option rel
	*/
	@Override
	public double getPriority() {
		return _cpDefinitionOptionRel.getPriority();
	}

	/**
	* Returns the required of this cp definition option rel.
	*
	* @return the required of this cp definition option rel
	*/
	@Override
	public boolean getRequired() {
		return _cpDefinitionOptionRel.getRequired();
	}

	/**
	* Returns the sku contributor of this cp definition option rel.
	*
	* @return the sku contributor of this cp definition option rel
	*/
	@Override
	public boolean getSkuContributor() {
		return _cpDefinitionOptionRel.getSkuContributor();
	}

	/**
	* Returns the title of this cp definition option rel.
	*
	* @return the title of this cp definition option rel
	*/
	@Override
	public java.lang.String getTitle() {
		return _cpDefinitionOptionRel.getTitle();
	}

	/**
	* Returns the localized title of this cp definition option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this cp definition option rel
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _cpDefinitionOptionRel.getTitle(locale);
	}

	/**
	* Returns the localized title of this cp definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp definition option rel. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _cpDefinitionOptionRel.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this cp definition option rel in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this cp definition option rel
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _cpDefinitionOptionRel.getTitle(languageId);
	}

	/**
	* Returns the localized title of this cp definition option rel in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp definition option rel
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _cpDefinitionOptionRel.getTitle(languageId, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _cpDefinitionOptionRel.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _cpDefinitionOptionRel.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this cp definition option rel.
	*
	* @return the locales and localized titles of this cp definition option rel
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _cpDefinitionOptionRel.getTitleMap();
	}

	/**
	* Returns the user ID of this cp definition option rel.
	*
	* @return the user ID of this cp definition option rel
	*/
	@Override
	public long getUserId() {
		return _cpDefinitionOptionRel.getUserId();
	}

	/**
	* Returns the user name of this cp definition option rel.
	*
	* @return the user name of this cp definition option rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpDefinitionOptionRel.getUserName();
	}

	/**
	* Returns the user uuid of this cp definition option rel.
	*
	* @return the user uuid of this cp definition option rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpDefinitionOptionRel.getUserUuid();
	}

	/**
	* Returns the uuid of this cp definition option rel.
	*
	* @return the uuid of this cp definition option rel
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpDefinitionOptionRel.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpDefinitionOptionRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpDefinitionOptionRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDefinitionOptionRel.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this cp definition option rel is facetable.
	*
	* @return <code>true</code> if this cp definition option rel is facetable; <code>false</code> otherwise
	*/
	@Override
	public boolean isFacetable() {
		return _cpDefinitionOptionRel.isFacetable();
	}

	@Override
	public boolean isNew() {
		return _cpDefinitionOptionRel.isNew();
	}

	/**
	* Returns <code>true</code> if this cp definition option rel is required.
	*
	* @return <code>true</code> if this cp definition option rel is required; <code>false</code> otherwise
	*/
	@Override
	public boolean isRequired() {
		return _cpDefinitionOptionRel.isRequired();
	}

	/**
	* Returns <code>true</code> if this cp definition option rel is sku contributor.
	*
	* @return <code>true</code> if this cp definition option rel is sku contributor; <code>false</code> otherwise
	*/
	@Override
	public boolean isSkuContributor() {
		return _cpDefinitionOptionRel.isSkuContributor();
	}

	@Override
	public void persist() {
		_cpDefinitionOptionRel.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpDefinitionOptionRel.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpDefinitionOptionRel.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDefinitionOptionRel.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp definition option rel.
	*
	* @param companyId the company ID of this cp definition option rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDefinitionOptionRel.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition ID of this cp definition option rel.
	*
	* @param CPDefinitionId the cp definition ID of this cp definition option rel
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpDefinitionOptionRel.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the cp definition option rel ID of this cp definition option rel.
	*
	* @param CPDefinitionOptionRelId the cp definition option rel ID of this cp definition option rel
	*/
	@Override
	public void setCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {
		_cpDefinitionOptionRel.setCPDefinitionOptionRelId(CPDefinitionOptionRelId);
	}

	/**
	* Sets the cp option ID of this cp definition option rel.
	*
	* @param CPOptionId the cp option ID of this cp definition option rel
	*/
	@Override
	public void setCPOptionId(long CPOptionId) {
		_cpDefinitionOptionRel.setCPOptionId(CPOptionId);
	}

	/**
	* Sets the create date of this cp definition option rel.
	*
	* @param createDate the create date of this cp definition option rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpDefinitionOptionRel.setCreateDate(createDate);
	}

	/**
	* Sets the ddm form field type name of this cp definition option rel.
	*
	* @param DDMFormFieldTypeName the ddm form field type name of this cp definition option rel
	*/
	@Override
	public void setDDMFormFieldTypeName(java.lang.String DDMFormFieldTypeName) {
		_cpDefinitionOptionRel.setDDMFormFieldTypeName(DDMFormFieldTypeName);
	}

	/**
	* Sets the description of this cp definition option rel.
	*
	* @param description the description of this cp definition option rel
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_cpDefinitionOptionRel.setDescription(description);
	}

	/**
	* Sets the localized description of this cp definition option rel in the language.
	*
	* @param description the localized description of this cp definition option rel
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_cpDefinitionOptionRel.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this cp definition option rel in the language, and sets the default locale.
	*
	* @param description the localized description of this cp definition option rel
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_cpDefinitionOptionRel.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_cpDefinitionOptionRel.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this cp definition option rel from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this cp definition option rel
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_cpDefinitionOptionRel.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this cp definition option rel from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this cp definition option rel
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_cpDefinitionOptionRel.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDefinitionOptionRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDefinitionOptionRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDefinitionOptionRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets whether this cp definition option rel is facetable.
	*
	* @param facetable the facetable of this cp definition option rel
	*/
	@Override
	public void setFacetable(boolean facetable) {
		_cpDefinitionOptionRel.setFacetable(facetable);
	}

	/**
	* Sets the group ID of this cp definition option rel.
	*
	* @param groupId the group ID of this cp definition option rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpDefinitionOptionRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this cp definition option rel.
	*
	* @param modifiedDate the modified date of this cp definition option rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpDefinitionOptionRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpDefinitionOptionRel.setNew(n);
	}

	/**
	* Sets the primary key of this cp definition option rel.
	*
	* @param primaryKey the primary key of this cp definition option rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDefinitionOptionRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDefinitionOptionRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this cp definition option rel.
	*
	* @param priority the priority of this cp definition option rel
	*/
	@Override
	public void setPriority(double priority) {
		_cpDefinitionOptionRel.setPriority(priority);
	}

	/**
	* Sets whether this cp definition option rel is required.
	*
	* @param required the required of this cp definition option rel
	*/
	@Override
	public void setRequired(boolean required) {
		_cpDefinitionOptionRel.setRequired(required);
	}

	/**
	* Sets whether this cp definition option rel is sku contributor.
	*
	* @param skuContributor the sku contributor of this cp definition option rel
	*/
	@Override
	public void setSkuContributor(boolean skuContributor) {
		_cpDefinitionOptionRel.setSkuContributor(skuContributor);
	}

	/**
	* Sets the title of this cp definition option rel.
	*
	* @param title the title of this cp definition option rel
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_cpDefinitionOptionRel.setTitle(title);
	}

	/**
	* Sets the localized title of this cp definition option rel in the language.
	*
	* @param title the localized title of this cp definition option rel
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_cpDefinitionOptionRel.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this cp definition option rel in the language, and sets the default locale.
	*
	* @param title the localized title of this cp definition option rel
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpDefinitionOptionRel.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_cpDefinitionOptionRel.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this cp definition option rel from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this cp definition option rel
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_cpDefinitionOptionRel.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this cp definition option rel from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this cp definition option rel
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_cpDefinitionOptionRel.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this cp definition option rel.
	*
	* @param userId the user ID of this cp definition option rel
	*/
	@Override
	public void setUserId(long userId) {
		_cpDefinitionOptionRel.setUserId(userId);
	}

	/**
	* Sets the user name of this cp definition option rel.
	*
	* @param userName the user name of this cp definition option rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpDefinitionOptionRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp definition option rel.
	*
	* @param userUuid the user uuid of this cp definition option rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpDefinitionOptionRel.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp definition option rel.
	*
	* @param uuid the uuid of this cp definition option rel
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpDefinitionOptionRel.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDefinitionOptionRel> toCacheModel() {
		return _cpDefinitionOptionRel.toCacheModel();
	}

	@Override
	public CPDefinitionOptionRel toEscapedModel() {
		return new CPDefinitionOptionRelWrapper(_cpDefinitionOptionRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _cpDefinitionOptionRel.toString();
	}

	@Override
	public CPDefinitionOptionRel toUnescapedModel() {
		return new CPDefinitionOptionRelWrapper(_cpDefinitionOptionRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpDefinitionOptionRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionOptionRelWrapper)) {
			return false;
		}

		CPDefinitionOptionRelWrapper cpDefinitionOptionRelWrapper = (CPDefinitionOptionRelWrapper)obj;

		if (Objects.equals(_cpDefinitionOptionRel,
					cpDefinitionOptionRelWrapper._cpDefinitionOptionRel)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpDefinitionOptionRel.getStagedModelType();
	}

	@Override
	public CPDefinitionOptionRel getWrappedModel() {
		return _cpDefinitionOptionRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDefinitionOptionRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDefinitionOptionRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDefinitionOptionRel.resetOriginalValues();
	}

	private final CPDefinitionOptionRel _cpDefinitionOptionRel;
}
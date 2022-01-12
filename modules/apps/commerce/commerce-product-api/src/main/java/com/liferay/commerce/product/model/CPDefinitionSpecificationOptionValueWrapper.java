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
 * This class is a wrapper for {@link CPDefinitionSpecificationOptionValue}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValue
 * @generated
 */
public class CPDefinitionSpecificationOptionValueWrapper
	extends BaseModelWrapper<CPDefinitionSpecificationOptionValue>
	implements CPDefinitionSpecificationOptionValue,
			   ModelWrapper<CPDefinitionSpecificationOptionValue> {

	public CPDefinitionSpecificationOptionValueWrapper(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		super(cpDefinitionSpecificationOptionValue);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put(
			"CPDefinitionSpecificationOptionValueId",
			getCPDefinitionSpecificationOptionValueId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CPSpecificationOptionId", getCPSpecificationOptionId());
		attributes.put("CPOptionCategoryId", getCPOptionCategoryId());
		attributes.put("value", getValue());
		attributes.put("priority", getPriority());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Long CPDefinitionSpecificationOptionValueId = (Long)attributes.get(
			"CPDefinitionSpecificationOptionValueId");

		if (CPDefinitionSpecificationOptionValueId != null) {
			setCPDefinitionSpecificationOptionValueId(
				CPDefinitionSpecificationOptionValueId);
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

		Long CPSpecificationOptionId = (Long)attributes.get(
			"CPSpecificationOptionId");

		if (CPSpecificationOptionId != null) {
			setCPSpecificationOptionId(CPSpecificationOptionId);
		}

		Long CPOptionCategoryId = (Long)attributes.get("CPOptionCategoryId");

		if (CPOptionCategoryId != null) {
			setCPOptionCategoryId(CPOptionCategoryId);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
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
	public CPDefinitionSpecificationOptionValue cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this cp definition specification option value.
	 *
	 * @return the company ID of this cp definition specification option value
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
	 * Returns the cp definition ID of this cp definition specification option value.
	 *
	 * @return the cp definition ID of this cp definition specification option value
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the cp definition specification option value ID of this cp definition specification option value.
	 *
	 * @return the cp definition specification option value ID of this cp definition specification option value
	 */
	@Override
	public long getCPDefinitionSpecificationOptionValueId() {
		return model.getCPDefinitionSpecificationOptionValueId();
	}

	@Override
	public CPOptionCategory getCPOptionCategory()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPOptionCategory();
	}

	/**
	 * Returns the cp option category ID of this cp definition specification option value.
	 *
	 * @return the cp option category ID of this cp definition specification option value
	 */
	@Override
	public long getCPOptionCategoryId() {
		return model.getCPOptionCategoryId();
	}

	@Override
	public CPSpecificationOption getCPSpecificationOption()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPSpecificationOption();
	}

	/**
	 * Returns the cp specification option ID of this cp definition specification option value.
	 *
	 * @return the cp specification option ID of this cp definition specification option value
	 */
	@Override
	public long getCPSpecificationOptionId() {
		return model.getCPSpecificationOptionId();
	}

	/**
	 * Returns the create date of this cp definition specification option value.
	 *
	 * @return the create date of this cp definition specification option value
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp definition specification option value.
	 *
	 * @return the ct collection ID of this cp definition specification option value
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the group ID of this cp definition specification option value.
	 *
	 * @return the group ID of this cp definition specification option value
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this cp definition specification option value.
	 *
	 * @return the last publish date of this cp definition specification option value
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this cp definition specification option value.
	 *
	 * @return the modified date of this cp definition specification option value
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp definition specification option value.
	 *
	 * @return the mvcc version of this cp definition specification option value
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp definition specification option value.
	 *
	 * @return the primary key of this cp definition specification option value
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this cp definition specification option value.
	 *
	 * @return the priority of this cp definition specification option value
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the user ID of this cp definition specification option value.
	 *
	 * @return the user ID of this cp definition specification option value
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp definition specification option value.
	 *
	 * @return the user name of this cp definition specification option value
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp definition specification option value.
	 *
	 * @return the user uuid of this cp definition specification option value
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp definition specification option value.
	 *
	 * @return the uuid of this cp definition specification option value
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the value of this cp definition specification option value.
	 *
	 * @return the value of this cp definition specification option value
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	/**
	 * Returns the localized value of this cp definition specification option value in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized value of this cp definition specification option value
	 */
	@Override
	public String getValue(java.util.Locale locale) {
		return model.getValue(locale);
	}

	/**
	 * Returns the localized value of this cp definition specification option value in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized value of this cp definition specification option value. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getValue(java.util.Locale locale, boolean useDefault) {
		return model.getValue(locale, useDefault);
	}

	/**
	 * Returns the localized value of this cp definition specification option value in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized value of this cp definition specification option value
	 */
	@Override
	public String getValue(String languageId) {
		return model.getValue(languageId);
	}

	/**
	 * Returns the localized value of this cp definition specification option value in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized value of this cp definition specification option value
	 */
	@Override
	public String getValue(String languageId, boolean useDefault) {
		return model.getValue(languageId, useDefault);
	}

	@Override
	public String getValueCurrentLanguageId() {
		return model.getValueCurrentLanguageId();
	}

	@Override
	public String getValueCurrentValue() {
		return model.getValueCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized values of this cp definition specification option value.
	 *
	 * @return the locales and localized values of this cp definition specification option value
	 */
	@Override
	public Map<java.util.Locale, String> getValueMap() {
		return model.getValueMap();
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
	 * Sets the company ID of this cp definition specification option value.
	 *
	 * @param companyId the company ID of this cp definition specification option value
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition ID of this cp definition specification option value.
	 *
	 * @param CPDefinitionId the cp definition ID of this cp definition specification option value
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the cp definition specification option value ID of this cp definition specification option value.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID of this cp definition specification option value
	 */
	@Override
	public void setCPDefinitionSpecificationOptionValueId(
		long CPDefinitionSpecificationOptionValueId) {

		model.setCPDefinitionSpecificationOptionValueId(
			CPDefinitionSpecificationOptionValueId);
	}

	/**
	 * Sets the cp option category ID of this cp definition specification option value.
	 *
	 * @param CPOptionCategoryId the cp option category ID of this cp definition specification option value
	 */
	@Override
	public void setCPOptionCategoryId(long CPOptionCategoryId) {
		model.setCPOptionCategoryId(CPOptionCategoryId);
	}

	/**
	 * Sets the cp specification option ID of this cp definition specification option value.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID of this cp definition specification option value
	 */
	@Override
	public void setCPSpecificationOptionId(long CPSpecificationOptionId) {
		model.setCPSpecificationOptionId(CPSpecificationOptionId);
	}

	/**
	 * Sets the create date of this cp definition specification option value.
	 *
	 * @param createDate the create date of this cp definition specification option value
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp definition specification option value.
	 *
	 * @param ctCollectionId the ct collection ID of this cp definition specification option value
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this cp definition specification option value.
	 *
	 * @param groupId the group ID of this cp definition specification option value
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this cp definition specification option value.
	 *
	 * @param lastPublishDate the last publish date of this cp definition specification option value
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this cp definition specification option value.
	 *
	 * @param modifiedDate the modified date of this cp definition specification option value
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp definition specification option value.
	 *
	 * @param mvccVersion the mvcc version of this cp definition specification option value
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp definition specification option value.
	 *
	 * @param primaryKey the primary key of this cp definition specification option value
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this cp definition specification option value.
	 *
	 * @param priority the priority of this cp definition specification option value
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the user ID of this cp definition specification option value.
	 *
	 * @param userId the user ID of this cp definition specification option value
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp definition specification option value.
	 *
	 * @param userName the user name of this cp definition specification option value
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp definition specification option value.
	 *
	 * @param userUuid the user uuid of this cp definition specification option value
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp definition specification option value.
	 *
	 * @param uuid the uuid of this cp definition specification option value
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the value of this cp definition specification option value.
	 *
	 * @param value the value of this cp definition specification option value
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	/**
	 * Sets the localized value of this cp definition specification option value in the language.
	 *
	 * @param value the localized value of this cp definition specification option value
	 * @param locale the locale of the language
	 */
	@Override
	public void setValue(String value, java.util.Locale locale) {
		model.setValue(value, locale);
	}

	/**
	 * Sets the localized value of this cp definition specification option value in the language, and sets the default locale.
	 *
	 * @param value the localized value of this cp definition specification option value
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setValue(
		String value, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setValue(value, locale, defaultLocale);
	}

	@Override
	public void setValueCurrentLanguageId(String languageId) {
		model.setValueCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized values of this cp definition specification option value from the map of locales and localized values.
	 *
	 * @param valueMap the locales and localized values of this cp definition specification option value
	 */
	@Override
	public void setValueMap(Map<java.util.Locale, String> valueMap) {
		model.setValueMap(valueMap);
	}

	/**
	 * Sets the localized values of this cp definition specification option value from the map of locales and localized values, and sets the default locale.
	 *
	 * @param valueMap the locales and localized values of this cp definition specification option value
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setValueMap(
		Map<java.util.Locale, String> valueMap,
		java.util.Locale defaultLocale) {

		model.setValueMap(valueMap, defaultLocale);
	}

	@Override
	public Map<String, Function<CPDefinitionSpecificationOptionValue, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPDefinitionSpecificationOptionValue, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPDefinitionSpecificationOptionValueWrapper wrap(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		return new CPDefinitionSpecificationOptionValueWrapper(
			cpDefinitionSpecificationOptionValue);
	}

}
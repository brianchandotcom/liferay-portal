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
 * This class is a wrapper for {@link CPDefinitionSpecificationOptionValue}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValue
 * @generated
 */
@ProviderType
public class CPDefinitionSpecificationOptionValueWrapper
	implements CPDefinitionSpecificationOptionValue,
		ModelWrapper<CPDefinitionSpecificationOptionValue> {
	public CPDefinitionSpecificationOptionValueWrapper(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue) {
		_cpDefinitionSpecificationOptionValue = cpDefinitionSpecificationOptionValue;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionSpecificationOptionValue.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionSpecificationOptionValue.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionSpecificationOptionValueId",
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
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionSpecificationOptionValueId = (Long)attributes.get(
				"CPDefinitionSpecificationOptionValueId");

		if (CPDefinitionSpecificationOptionValueId != null) {
			setCPDefinitionSpecificationOptionValueId(CPDefinitionSpecificationOptionValueId);
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
	public Object clone() {
		return new CPDefinitionSpecificationOptionValueWrapper((CPDefinitionSpecificationOptionValue)_cpDefinitionSpecificationOptionValue.clone());
	}

	@Override
	public int compareTo(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue) {
		return _cpDefinitionSpecificationOptionValue.compareTo(cpDefinitionSpecificationOptionValue);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _cpDefinitionSpecificationOptionValue.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this cp definition specification option value.
	*
	* @return the company ID of this cp definition specification option value
	*/
	@Override
	public long getCompanyId() {
		return _cpDefinitionSpecificationOptionValue.getCompanyId();
	}

	@Override
	public CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionSpecificationOptionValue.getCPDefinition();
	}

	/**
	* Returns the cp definition ID of this cp definition specification option value.
	*
	* @return the cp definition ID of this cp definition specification option value
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpDefinitionSpecificationOptionValue.getCPDefinitionId();
	}

	/**
	* Returns the cp definition specification option value ID of this cp definition specification option value.
	*
	* @return the cp definition specification option value ID of this cp definition specification option value
	*/
	@Override
	public long getCPDefinitionSpecificationOptionValueId() {
		return _cpDefinitionSpecificationOptionValue.getCPDefinitionSpecificationOptionValueId();
	}

	@Override
	public CPOptionCategory getCPOptionCategory()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionSpecificationOptionValue.getCPOptionCategory();
	}

	/**
	* Returns the cp option category ID of this cp definition specification option value.
	*
	* @return the cp option category ID of this cp definition specification option value
	*/
	@Override
	public long getCPOptionCategoryId() {
		return _cpDefinitionSpecificationOptionValue.getCPOptionCategoryId();
	}

	@Override
	public CPSpecificationOption getCPSpecificationOption()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionSpecificationOptionValue.getCPSpecificationOption();
	}

	/**
	* Returns the cp specification option ID of this cp definition specification option value.
	*
	* @return the cp specification option ID of this cp definition specification option value
	*/
	@Override
	public long getCPSpecificationOptionId() {
		return _cpDefinitionSpecificationOptionValue.getCPSpecificationOptionId();
	}

	/**
	* Returns the create date of this cp definition specification option value.
	*
	* @return the create date of this cp definition specification option value
	*/
	@Override
	public Date getCreateDate() {
		return _cpDefinitionSpecificationOptionValue.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return _cpDefinitionSpecificationOptionValue.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDefinitionSpecificationOptionValue.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp definition specification option value.
	*
	* @return the group ID of this cp definition specification option value
	*/
	@Override
	public long getGroupId() {
		return _cpDefinitionSpecificationOptionValue.getGroupId();
	}

	/**
	* Returns the last publish date of this cp definition specification option value.
	*
	* @return the last publish date of this cp definition specification option value
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpDefinitionSpecificationOptionValue.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp definition specification option value.
	*
	* @return the modified date of this cp definition specification option value
	*/
	@Override
	public Date getModifiedDate() {
		return _cpDefinitionSpecificationOptionValue.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp definition specification option value.
	*
	* @return the primary key of this cp definition specification option value
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDefinitionSpecificationOptionValue.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDefinitionSpecificationOptionValue.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this cp definition specification option value.
	*
	* @return the priority of this cp definition specification option value
	*/
	@Override
	public double getPriority() {
		return _cpDefinitionSpecificationOptionValue.getPriority();
	}

	/**
	* Returns the user ID of this cp definition specification option value.
	*
	* @return the user ID of this cp definition specification option value
	*/
	@Override
	public long getUserId() {
		return _cpDefinitionSpecificationOptionValue.getUserId();
	}

	/**
	* Returns the user name of this cp definition specification option value.
	*
	* @return the user name of this cp definition specification option value
	*/
	@Override
	public String getUserName() {
		return _cpDefinitionSpecificationOptionValue.getUserName();
	}

	/**
	* Returns the user uuid of this cp definition specification option value.
	*
	* @return the user uuid of this cp definition specification option value
	*/
	@Override
	public String getUserUuid() {
		return _cpDefinitionSpecificationOptionValue.getUserUuid();
	}

	/**
	* Returns the uuid of this cp definition specification option value.
	*
	* @return the uuid of this cp definition specification option value
	*/
	@Override
	public String getUuid() {
		return _cpDefinitionSpecificationOptionValue.getUuid();
	}

	/**
	* Returns the value of this cp definition specification option value.
	*
	* @return the value of this cp definition specification option value
	*/
	@Override
	public String getValue() {
		return _cpDefinitionSpecificationOptionValue.getValue();
	}

	/**
	* Returns the localized value of this cp definition specification option value in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized value of this cp definition specification option value
	*/
	@Override
	public String getValue(java.util.Locale locale) {
		return _cpDefinitionSpecificationOptionValue.getValue(locale);
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
		return _cpDefinitionSpecificationOptionValue.getValue(locale, useDefault);
	}

	/**
	* Returns the localized value of this cp definition specification option value in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized value of this cp definition specification option value
	*/
	@Override
	public String getValue(String languageId) {
		return _cpDefinitionSpecificationOptionValue.getValue(languageId);
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
		return _cpDefinitionSpecificationOptionValue.getValue(languageId,
			useDefault);
	}

	@Override
	public String getValueCurrentLanguageId() {
		return _cpDefinitionSpecificationOptionValue.getValueCurrentLanguageId();
	}

	@Override
	public String getValueCurrentValue() {
		return _cpDefinitionSpecificationOptionValue.getValueCurrentValue();
	}

	/**
	* Returns a map of the locales and localized values of this cp definition specification option value.
	*
	* @return the locales and localized values of this cp definition specification option value
	*/
	@Override
	public Map<java.util.Locale, String> getValueMap() {
		return _cpDefinitionSpecificationOptionValue.getValueMap();
	}

	@Override
	public int hashCode() {
		return _cpDefinitionSpecificationOptionValue.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpDefinitionSpecificationOptionValue.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDefinitionSpecificationOptionValue.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpDefinitionSpecificationOptionValue.isNew();
	}

	@Override
	public void persist() {
		_cpDefinitionSpecificationOptionValue.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpDefinitionSpecificationOptionValue.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpDefinitionSpecificationOptionValue.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDefinitionSpecificationOptionValue.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp definition specification option value.
	*
	* @param companyId the company ID of this cp definition specification option value
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDefinitionSpecificationOptionValue.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition ID of this cp definition specification option value.
	*
	* @param CPDefinitionId the cp definition ID of this cp definition specification option value
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpDefinitionSpecificationOptionValue.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the cp definition specification option value ID of this cp definition specification option value.
	*
	* @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID of this cp definition specification option value
	*/
	@Override
	public void setCPDefinitionSpecificationOptionValueId(
		long CPDefinitionSpecificationOptionValueId) {
		_cpDefinitionSpecificationOptionValue.setCPDefinitionSpecificationOptionValueId(CPDefinitionSpecificationOptionValueId);
	}

	/**
	* Sets the cp option category ID of this cp definition specification option value.
	*
	* @param CPOptionCategoryId the cp option category ID of this cp definition specification option value
	*/
	@Override
	public void setCPOptionCategoryId(long CPOptionCategoryId) {
		_cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(CPOptionCategoryId);
	}

	/**
	* Sets the cp specification option ID of this cp definition specification option value.
	*
	* @param CPSpecificationOptionId the cp specification option ID of this cp definition specification option value
	*/
	@Override
	public void setCPSpecificationOptionId(long CPSpecificationOptionId) {
		_cpDefinitionSpecificationOptionValue.setCPSpecificationOptionId(CPSpecificationOptionId);
	}

	/**
	* Sets the create date of this cp definition specification option value.
	*
	* @param createDate the create date of this cp definition specification option value
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpDefinitionSpecificationOptionValue.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDefinitionSpecificationOptionValue.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDefinitionSpecificationOptionValue.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDefinitionSpecificationOptionValue.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp definition specification option value.
	*
	* @param groupId the group ID of this cp definition specification option value
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpDefinitionSpecificationOptionValue.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this cp definition specification option value.
	*
	* @param lastPublishDate the last publish date of this cp definition specification option value
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpDefinitionSpecificationOptionValue.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cp definition specification option value.
	*
	* @param modifiedDate the modified date of this cp definition specification option value
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpDefinitionSpecificationOptionValue.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpDefinitionSpecificationOptionValue.setNew(n);
	}

	/**
	* Sets the primary key of this cp definition specification option value.
	*
	* @param primaryKey the primary key of this cp definition specification option value
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDefinitionSpecificationOptionValue.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDefinitionSpecificationOptionValue.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this cp definition specification option value.
	*
	* @param priority the priority of this cp definition specification option value
	*/
	@Override
	public void setPriority(double priority) {
		_cpDefinitionSpecificationOptionValue.setPriority(priority);
	}

	/**
	* Sets the user ID of this cp definition specification option value.
	*
	* @param userId the user ID of this cp definition specification option value
	*/
	@Override
	public void setUserId(long userId) {
		_cpDefinitionSpecificationOptionValue.setUserId(userId);
	}

	/**
	* Sets the user name of this cp definition specification option value.
	*
	* @param userName the user name of this cp definition specification option value
	*/
	@Override
	public void setUserName(String userName) {
		_cpDefinitionSpecificationOptionValue.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp definition specification option value.
	*
	* @param userUuid the user uuid of this cp definition specification option value
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpDefinitionSpecificationOptionValue.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp definition specification option value.
	*
	* @param uuid the uuid of this cp definition specification option value
	*/
	@Override
	public void setUuid(String uuid) {
		_cpDefinitionSpecificationOptionValue.setUuid(uuid);
	}

	/**
	* Sets the value of this cp definition specification option value.
	*
	* @param value the value of this cp definition specification option value
	*/
	@Override
	public void setValue(String value) {
		_cpDefinitionSpecificationOptionValue.setValue(value);
	}

	/**
	* Sets the localized value of this cp definition specification option value in the language.
	*
	* @param value the localized value of this cp definition specification option value
	* @param locale the locale of the language
	*/
	@Override
	public void setValue(String value, java.util.Locale locale) {
		_cpDefinitionSpecificationOptionValue.setValue(value, locale);
	}

	/**
	* Sets the localized value of this cp definition specification option value in the language, and sets the default locale.
	*
	* @param value the localized value of this cp definition specification option value
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setValue(String value, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpDefinitionSpecificationOptionValue.setValue(value, locale,
			defaultLocale);
	}

	@Override
	public void setValueCurrentLanguageId(String languageId) {
		_cpDefinitionSpecificationOptionValue.setValueCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized values of this cp definition specification option value from the map of locales and localized values.
	*
	* @param valueMap the locales and localized values of this cp definition specification option value
	*/
	@Override
	public void setValueMap(Map<java.util.Locale, String> valueMap) {
		_cpDefinitionSpecificationOptionValue.setValueMap(valueMap);
	}

	/**
	* Sets the localized values of this cp definition specification option value from the map of locales and localized values, and sets the default locale.
	*
	* @param valueMap the locales and localized values of this cp definition specification option value
	* @param defaultLocale the default locale
	*/
	@Override
	public void setValueMap(Map<java.util.Locale, String> valueMap,
		java.util.Locale defaultLocale) {
		_cpDefinitionSpecificationOptionValue.setValueMap(valueMap,
			defaultLocale);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDefinitionSpecificationOptionValue> toCacheModel() {
		return _cpDefinitionSpecificationOptionValue.toCacheModel();
	}

	@Override
	public CPDefinitionSpecificationOptionValue toEscapedModel() {
		return new CPDefinitionSpecificationOptionValueWrapper(_cpDefinitionSpecificationOptionValue.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpDefinitionSpecificationOptionValue.toString();
	}

	@Override
	public CPDefinitionSpecificationOptionValue toUnescapedModel() {
		return new CPDefinitionSpecificationOptionValueWrapper(_cpDefinitionSpecificationOptionValue.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpDefinitionSpecificationOptionValue.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionSpecificationOptionValueWrapper)) {
			return false;
		}

		CPDefinitionSpecificationOptionValueWrapper cpDefinitionSpecificationOptionValueWrapper =
			(CPDefinitionSpecificationOptionValueWrapper)obj;

		if (Objects.equals(_cpDefinitionSpecificationOptionValue,
					cpDefinitionSpecificationOptionValueWrapper._cpDefinitionSpecificationOptionValue)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpDefinitionSpecificationOptionValue.getStagedModelType();
	}

	@Override
	public CPDefinitionSpecificationOptionValue getWrappedModel() {
		return _cpDefinitionSpecificationOptionValue;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDefinitionSpecificationOptionValue.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDefinitionSpecificationOptionValue.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDefinitionSpecificationOptionValue.resetOriginalValues();
	}

	private final CPDefinitionSpecificationOptionValue _cpDefinitionSpecificationOptionValue;
}
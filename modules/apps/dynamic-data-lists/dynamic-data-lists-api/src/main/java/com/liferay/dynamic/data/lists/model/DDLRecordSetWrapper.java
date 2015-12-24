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

package com.liferay.dynamic.data.lists.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecordSet}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSet
 * @generated
 */
@ProviderType
public class DDLRecordSetWrapper implements DDLRecordSet,
	ModelWrapper<DDLRecordSet> {
	public DDLRecordSetWrapper(DDLRecordSet ddlRecordSet) {
		_ddlRecordSet = ddlRecordSet;
	}

	@Override
	public Class<?> getModelClass() {
		return DDLRecordSet.class;
	}

	@Override
	public String getModelClassName() {
		return DDLRecordSet.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("recordSetId", getRecordSetId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("DDMStructureId", getDDMStructureId());
		attributes.put("recordSetKey", getRecordSetKey());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("minDisplayRows", getMinDisplayRows());
		attributes.put("scope", getScope());
		attributes.put("settings", getSettings());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long recordSetId = (Long)attributes.get("recordSetId");

		if (recordSetId != null) {
			setRecordSetId(recordSetId);
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

		Long DDMStructureId = (Long)attributes.get("DDMStructureId");

		if (DDMStructureId != null) {
			setDDMStructureId(DDMStructureId);
		}

		String recordSetKey = (String)attributes.get("recordSetKey");

		if (recordSetKey != null) {
			setRecordSetKey(recordSetKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer minDisplayRows = (Integer)attributes.get("minDisplayRows");

		if (minDisplayRows != null) {
			setMinDisplayRows(minDisplayRows);
		}

		Integer scope = (Integer)attributes.get("scope");

		if (scope != null) {
			setScope(scope);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDLRecordSetWrapper((DDLRecordSet)_ddlRecordSet.clone());
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.lists.model.DDLRecordSet ddlRecordSet) {
		return _ddlRecordSet.compareTo(ddlRecordSet);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _ddlRecordSet.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this d d l record set.
	*
	* @return the company ID of this d d l record set
	*/
	@Override
	public long getCompanyId() {
		return _ddlRecordSet.getCompanyId();
	}

	/**
	* Returns the create date of this d d l record set.
	*
	* @return the create date of this d d l record set
	*/
	@Override
	public Date getCreateDate() {
		return _ddlRecordSet.getCreateDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructure getDDMStructure()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordSet.getDDMStructure();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructure getDDMStructure(
		long formDDMTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordSet.getDDMStructure(formDDMTemplateId);
	}

	/**
	* Returns the d d m structure ID of this d d l record set.
	*
	* @return the d d m structure ID of this d d l record set
	*/
	@Override
	public long getDDMStructureId() {
		return _ddlRecordSet.getDDMStructureId();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _ddlRecordSet.getDefaultLanguageId();
	}

	/**
	* Returns the description of this d d l record set.
	*
	* @return the description of this d d l record set
	*/
	@Override
	public java.lang.String getDescription() {
		return _ddlRecordSet.getDescription();
	}

	/**
	* Returns the localized description of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this d d l record set
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _ddlRecordSet.getDescription(languageId);
	}

	/**
	* Returns the localized description of this d d l record set in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d l record set
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddlRecordSet.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this d d l record set
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _ddlRecordSet.getDescription(locale);
	}

	/**
	* Returns the localized description of this d d l record set in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d l record set. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddlRecordSet.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddlRecordSet.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _ddlRecordSet.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this d d l record set.
	*
	* @return the locales and localized descriptions of this d d l record set
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddlRecordSet.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddlRecordSet.getExpandoBridge();
	}

	/**
	* Returns the group ID of this d d l record set.
	*
	* @return the group ID of this d d l record set
	*/
	@Override
	public long getGroupId() {
		return _ddlRecordSet.getGroupId();
	}

	/**
	* Returns the last publish date of this d d l record set.
	*
	* @return the last publish date of this d d l record set
	*/
	@Override
	public Date getLastPublishDate() {
		return _ddlRecordSet.getLastPublishDate();
	}

	/**
	* Returns the min display rows of this d d l record set.
	*
	* @return the min display rows of this d d l record set
	*/
	@Override
	public int getMinDisplayRows() {
		return _ddlRecordSet.getMinDisplayRows();
	}

	/**
	* Returns the modified date of this d d l record set.
	*
	* @return the modified date of this d d l record set
	*/
	@Override
	public Date getModifiedDate() {
		return _ddlRecordSet.getModifiedDate();
	}

	/**
	* Returns the name of this d d l record set.
	*
	* @return the name of this d d l record set
	*/
	@Override
	public java.lang.String getName() {
		return _ddlRecordSet.getName();
	}

	/**
	* Returns the localized name of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this d d l record set
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _ddlRecordSet.getName(languageId);
	}

	/**
	* Returns the localized name of this d d l record set in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d l record set
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddlRecordSet.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this d d l record set
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _ddlRecordSet.getName(locale);
	}

	/**
	* Returns the localized name of this d d l record set in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d l record set. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddlRecordSet.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _ddlRecordSet.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _ddlRecordSet.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this d d l record set.
	*
	* @return the locales and localized names of this d d l record set
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddlRecordSet.getNameMap();
	}

	/**
	* Returns the primary key of this d d l record set.
	*
	* @return the primary key of this d d l record set
	*/
	@Override
	public long getPrimaryKey() {
		return _ddlRecordSet.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddlRecordSet.getPrimaryKeyObj();
	}

	/**
	* Returns the record set ID of this d d l record set.
	*
	* @return the record set ID of this d d l record set
	*/
	@Override
	public long getRecordSetId() {
		return _ddlRecordSet.getRecordSetId();
	}

	/**
	* Returns the record set key of this d d l record set.
	*
	* @return the record set key of this d d l record set
	*/
	@Override
	public java.lang.String getRecordSetKey() {
		return _ddlRecordSet.getRecordSetKey();
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getRecords() {
		return _ddlRecordSet.getRecords();
	}

	/**
	* Returns the scope of this d d l record set.
	*
	* @return the scope of this d d l record set
	*/
	@Override
	public int getScope() {
		return _ddlRecordSet.getScope();
	}

	/**
	* Returns the settings of this d d l record set.
	*
	* @return the settings of this d d l record set
	*/
	@Override
	public java.lang.String getSettings() {
		return _ddlRecordSet.getSettings();
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues getSettingsDDMFormValues() {
		return _ddlRecordSet.getSettingsDDMFormValues();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordSetSettings getSettingsModel() {
		return _ddlRecordSet.getSettingsModel();
	}

	/**
	* Returns the user ID of this d d l record set.
	*
	* @return the user ID of this d d l record set
	*/
	@Override
	public long getUserId() {
		return _ddlRecordSet.getUserId();
	}

	/**
	* Returns the user name of this d d l record set.
	*
	* @return the user name of this d d l record set
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddlRecordSet.getUserName();
	}

	/**
	* Returns the user uuid of this d d l record set.
	*
	* @return the user uuid of this d d l record set
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddlRecordSet.getUserUuid();
	}

	/**
	* Returns the uuid of this d d l record set.
	*
	* @return the uuid of this d d l record set
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddlRecordSet.getUuid();
	}

	@Override
	public int hashCode() {
		return _ddlRecordSet.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddlRecordSet.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddlRecordSet.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddlRecordSet.isNew();
	}

	@Override
	public void persist() {
		_ddlRecordSet.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_ddlRecordSet.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddlRecordSet.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddlRecordSet.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this d d l record set.
	*
	* @param companyId the company ID of this d d l record set
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddlRecordSet.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this d d l record set.
	*
	* @param createDate the create date of this d d l record set
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddlRecordSet.setCreateDate(createDate);
	}

	/**
	* Sets the d d m structure ID of this d d l record set.
	*
	* @param DDMStructureId the d d m structure ID of this d d l record set
	*/
	@Override
	public void setDDMStructureId(long DDMStructureId) {
		_ddlRecordSet.setDDMStructureId(DDMStructureId);
	}

	/**
	* Sets the description of this d d l record set.
	*
	* @param description the description of this d d l record set
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_ddlRecordSet.setDescription(description);
	}

	/**
	* Sets the localized description of this d d l record set in the language.
	*
	* @param description the localized description of this d d l record set
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_ddlRecordSet.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this d d l record set in the language, and sets the default locale.
	*
	* @param description the localized description of this d d l record set
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddlRecordSet.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddlRecordSet.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this d d l record set from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this d d l record set
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddlRecordSet.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this d d l record set from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this d d l record set
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddlRecordSet.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddlRecordSet.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddlRecordSet.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddlRecordSet.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this d d l record set.
	*
	* @param groupId the group ID of this d d l record set
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddlRecordSet.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this d d l record set.
	*
	* @param lastPublishDate the last publish date of this d d l record set
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_ddlRecordSet.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the min display rows of this d d l record set.
	*
	* @param minDisplayRows the min display rows of this d d l record set
	*/
	@Override
	public void setMinDisplayRows(int minDisplayRows) {
		_ddlRecordSet.setMinDisplayRows(minDisplayRows);
	}

	/**
	* Sets the modified date of this d d l record set.
	*
	* @param modifiedDate the modified date of this d d l record set
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddlRecordSet.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this d d l record set.
	*
	* @param name the name of this d d l record set
	*/
	@Override
	public void setName(java.lang.String name) {
		_ddlRecordSet.setName(name);
	}

	/**
	* Sets the localized name of this d d l record set in the language.
	*
	* @param name the localized name of this d d l record set
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddlRecordSet.setName(name, locale);
	}

	/**
	* Sets the localized name of this d d l record set in the language, and sets the default locale.
	*
	* @param name the localized name of this d d l record set
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddlRecordSet.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddlRecordSet.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this d d l record set from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d l record set
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_ddlRecordSet.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d l record set from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d l record set
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddlRecordSet.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddlRecordSet.setNew(n);
	}

	/**
	* Sets the primary key of this d d l record set.
	*
	* @param primaryKey the primary key of this d d l record set
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddlRecordSet.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddlRecordSet.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the record set ID of this d d l record set.
	*
	* @param recordSetId the record set ID of this d d l record set
	*/
	@Override
	public void setRecordSetId(long recordSetId) {
		_ddlRecordSet.setRecordSetId(recordSetId);
	}

	/**
	* Sets the record set key of this d d l record set.
	*
	* @param recordSetKey the record set key of this d d l record set
	*/
	@Override
	public void setRecordSetKey(java.lang.String recordSetKey) {
		_ddlRecordSet.setRecordSetKey(recordSetKey);
	}

	/**
	* Sets the scope of this d d l record set.
	*
	* @param scope the scope of this d d l record set
	*/
	@Override
	public void setScope(int scope) {
		_ddlRecordSet.setScope(scope);
	}

	/**
	* Sets the settings of this d d l record set.
	*
	* @param settings the settings of this d d l record set
	*/
	@Override
	public void setSettings(java.lang.String settings) {
		_ddlRecordSet.setSettings(settings);
	}

	/**
	* Sets the user ID of this d d l record set.
	*
	* @param userId the user ID of this d d l record set
	*/
	@Override
	public void setUserId(long userId) {
		_ddlRecordSet.setUserId(userId);
	}

	/**
	* Sets the user name of this d d l record set.
	*
	* @param userName the user name of this d d l record set
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddlRecordSet.setUserName(userName);
	}

	/**
	* Sets the user uuid of this d d l record set.
	*
	* @param userUuid the user uuid of this d d l record set
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddlRecordSet.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this d d l record set.
	*
	* @param uuid the uuid of this d d l record set
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddlRecordSet.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.dynamic.data.lists.model.DDLRecordSet> toCacheModel() {
		return _ddlRecordSet.toCacheModel();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordSet toEscapedModel() {
		return new DDLRecordSetWrapper(_ddlRecordSet.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddlRecordSet.toString();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordSet toUnescapedModel() {
		return new DDLRecordSetWrapper(_ddlRecordSet.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddlRecordSet.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDLRecordSetWrapper)) {
			return false;
		}

		DDLRecordSetWrapper ddlRecordSetWrapper = (DDLRecordSetWrapper)obj;

		if (Validator.equals(_ddlRecordSet, ddlRecordSetWrapper._ddlRecordSet)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddlRecordSet.getStagedModelType();
	}

	@Override
	public DDLRecordSet getWrappedModel() {
		return _ddlRecordSet;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddlRecordSet.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddlRecordSet.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddlRecordSet.resetOriginalValues();
	}

	private final DDLRecordSet _ddlRecordSet;
}
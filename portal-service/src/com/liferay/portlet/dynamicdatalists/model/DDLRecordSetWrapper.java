/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecordSet}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLRecordSet
 * @generated
 */
public class DDLRecordSetWrapper implements DDLRecordSet,
	ModelWrapper<DDLRecordSet> {
	public DDLRecordSetWrapper(DDLRecordSet ddlRecordSet) {
		_ddlRecordSet = ddlRecordSet;
	}

	public Class<?> getModelClass() {
		return DDLRecordSet.class;
	}

	public String getModelClassName() {
		return DDLRecordSet.class.getName();
	}

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

		return attributes;
	}

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
	}

	/**
	* Returns the primary key of this d d l record set.
	*
	* @return the primary key of this d d l record set
	*/
	public long getPrimaryKey() {
		return _ddlRecordSet.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d l record set.
	*
	* @param primaryKey the primary key of this d d l record set
	*/
	public void setPrimaryKey(long primaryKey) {
		_ddlRecordSet.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this d d l record set.
	*
	* @return the uuid of this d d l record set
	*/
	public java.lang.String getUuid() {
		return _ddlRecordSet.getUuid();
	}

	/**
	* Sets the uuid of this d d l record set.
	*
	* @param uuid the uuid of this d d l record set
	*/
	public void setUuid(java.lang.String uuid) {
		_ddlRecordSet.setUuid(uuid);
	}

	/**
	* Returns the record set ID of this d d l record set.
	*
	* @return the record set ID of this d d l record set
	*/
	public long getRecordSetId() {
		return _ddlRecordSet.getRecordSetId();
	}

	/**
	* Sets the record set ID of this d d l record set.
	*
	* @param recordSetId the record set ID of this d d l record set
	*/
	public void setRecordSetId(long recordSetId) {
		_ddlRecordSet.setRecordSetId(recordSetId);
	}

	/**
	* Returns the group ID of this d d l record set.
	*
	* @return the group ID of this d d l record set
	*/
	public long getGroupId() {
		return _ddlRecordSet.getGroupId();
	}

	/**
	* Sets the group ID of this d d l record set.
	*
	* @param groupId the group ID of this d d l record set
	*/
	public void setGroupId(long groupId) {
		_ddlRecordSet.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this d d l record set.
	*
	* @return the company ID of this d d l record set
	*/
	public long getCompanyId() {
		return _ddlRecordSet.getCompanyId();
	}

	/**
	* Sets the company ID of this d d l record set.
	*
	* @param companyId the company ID of this d d l record set
	*/
	public void setCompanyId(long companyId) {
		_ddlRecordSet.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this d d l record set.
	*
	* @return the user ID of this d d l record set
	*/
	public long getUserId() {
		return _ddlRecordSet.getUserId();
	}

	/**
	* Sets the user ID of this d d l record set.
	*
	* @param userId the user ID of this d d l record set
	*/
	public void setUserId(long userId) {
		_ddlRecordSet.setUserId(userId);
	}

	/**
	* Returns the user uuid of this d d l record set.
	*
	* @return the user uuid of this d d l record set
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSet.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d l record set.
	*
	* @param userUuid the user uuid of this d d l record set
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddlRecordSet.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this d d l record set.
	*
	* @return the user name of this d d l record set
	*/
	public java.lang.String getUserName() {
		return _ddlRecordSet.getUserName();
	}

	/**
	* Sets the user name of this d d l record set.
	*
	* @param userName the user name of this d d l record set
	*/
	public void setUserName(java.lang.String userName) {
		_ddlRecordSet.setUserName(userName);
	}

	/**
	* Returns the create date of this d d l record set.
	*
	* @return the create date of this d d l record set
	*/
	public java.util.Date getCreateDate() {
		return _ddlRecordSet.getCreateDate();
	}

	/**
	* Sets the create date of this d d l record set.
	*
	* @param createDate the create date of this d d l record set
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddlRecordSet.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this d d l record set.
	*
	* @return the modified date of this d d l record set
	*/
	public java.util.Date getModifiedDate() {
		return _ddlRecordSet.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d l record set.
	*
	* @param modifiedDate the modified date of this d d l record set
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddlRecordSet.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the d d m structure ID of this d d l record set.
	*
	* @return the d d m structure ID of this d d l record set
	*/
	public long getDDMStructureId() {
		return _ddlRecordSet.getDDMStructureId();
	}

	/**
	* Sets the d d m structure ID of this d d l record set.
	*
	* @param DDMStructureId the d d m structure ID of this d d l record set
	*/
	public void setDDMStructureId(long DDMStructureId) {
		_ddlRecordSet.setDDMStructureId(DDMStructureId);
	}

	/**
	* Returns the record set key of this d d l record set.
	*
	* @return the record set key of this d d l record set
	*/
	public java.lang.String getRecordSetKey() {
		return _ddlRecordSet.getRecordSetKey();
	}

	/**
	* Sets the record set key of this d d l record set.
	*
	* @param recordSetKey the record set key of this d d l record set
	*/
	public void setRecordSetKey(java.lang.String recordSetKey) {
		_ddlRecordSet.setRecordSetKey(recordSetKey);
	}

	/**
	* Returns the name of this d d l record set.
	*
	* @return the name of this d d l record set
	*/
	public java.lang.String getName() {
		return _ddlRecordSet.getName();
	}

	/**
	* Returns the localized name of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this d d l record set
	*/
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
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddlRecordSet.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this d d l record set
	*/
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
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddlRecordSet.getName(languageId, useDefault);
	}

	public java.lang.String getNameCurrentLanguageId() {
		return _ddlRecordSet.getNameCurrentLanguageId();
	}

	public java.lang.String getNameCurrentValue() {
		return _ddlRecordSet.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this d d l record set.
	*
	* @return the locales and localized names of this d d l record set
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddlRecordSet.getNameMap();
	}

	/**
	* Sets the name of this d d l record set.
	*
	* @param name the name of this d d l record set
	*/
	public void setName(java.lang.String name) {
		_ddlRecordSet.setName(name);
	}

	/**
	* Sets the localized name of this d d l record set in the language.
	*
	* @param name the localized name of this d d l record set
	* @param locale the locale of the language
	*/
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
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddlRecordSet.setName(name, locale, defaultLocale);
	}

	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddlRecordSet.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this d d l record set from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d l record set
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_ddlRecordSet.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d l record set from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d l record set
	* @param defaultLocale the default locale
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddlRecordSet.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this d d l record set.
	*
	* @return the description of this d d l record set
	*/
	public java.lang.String getDescription() {
		return _ddlRecordSet.getDescription();
	}

	/**
	* Returns the localized description of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this d d l record set
	*/
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
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddlRecordSet.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this d d l record set in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this d d l record set
	*/
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
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddlRecordSet.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddlRecordSet.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _ddlRecordSet.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this d d l record set.
	*
	* @return the locales and localized descriptions of this d d l record set
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddlRecordSet.getDescriptionMap();
	}

	/**
	* Sets the description of this d d l record set.
	*
	* @param description the description of this d d l record set
	*/
	public void setDescription(java.lang.String description) {
		_ddlRecordSet.setDescription(description);
	}

	/**
	* Sets the localized description of this d d l record set in the language.
	*
	* @param description the localized description of this d d l record set
	* @param locale the locale of the language
	*/
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
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddlRecordSet.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddlRecordSet.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this d d l record set from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this d d l record set
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddlRecordSet.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this d d l record set from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this d d l record set
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddlRecordSet.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the min display rows of this d d l record set.
	*
	* @return the min display rows of this d d l record set
	*/
	public int getMinDisplayRows() {
		return _ddlRecordSet.getMinDisplayRows();
	}

	/**
	* Sets the min display rows of this d d l record set.
	*
	* @param minDisplayRows the min display rows of this d d l record set
	*/
	public void setMinDisplayRows(int minDisplayRows) {
		_ddlRecordSet.setMinDisplayRows(minDisplayRows);
	}

	/**
	* Returns the scope of this d d l record set.
	*
	* @return the scope of this d d l record set
	*/
	public int getScope() {
		return _ddlRecordSet.getScope();
	}

	/**
	* Sets the scope of this d d l record set.
	*
	* @param scope the scope of this d d l record set
	*/
	public void setScope(int scope) {
		_ddlRecordSet.setScope(scope);
	}

	public boolean isNew() {
		return _ddlRecordSet.isNew();
	}

	public void setNew(boolean n) {
		_ddlRecordSet.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddlRecordSet.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddlRecordSet.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddlRecordSet.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddlRecordSet.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddlRecordSet.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddlRecordSet.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddlRecordSet.setExpandoBridgeAttributes(serviceContext);
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddlRecordSet.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new DDLRecordSetWrapper((DDLRecordSet)_ddlRecordSet.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordSet ddlRecordSet) {
		return _ddlRecordSet.compareTo(ddlRecordSet);
	}

	@Override
	public int hashCode() {
		return _ddlRecordSet.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> toCacheModel() {
		return _ddlRecordSet.toCacheModel();
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet toEscapedModel() {
		return new DDLRecordSetWrapper(_ddlRecordSet.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddlRecordSet.toString();
	}

	public java.lang.String toXmlString() {
		return _ddlRecordSet.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordSet.persist();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructure()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSet.getDDMStructure();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructure(
		long formDDMTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSet.getDDMStructure(formDDMTemplateId);
	}

	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getRecords()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSet.getRecords();
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.storage.Fields> getRecordsFieldsList()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSet.getRecordsFieldsList();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public DDLRecordSet getWrappedDDLRecordSet() {
		return _ddlRecordSet;
	}

	public DDLRecordSet getWrappedModel() {
		return _ddlRecordSet;
	}

	public void resetOriginalValues() {
		_ddlRecordSet.resetOriginalValues();
	}

	private DDLRecordSet _ddlRecordSet;
}
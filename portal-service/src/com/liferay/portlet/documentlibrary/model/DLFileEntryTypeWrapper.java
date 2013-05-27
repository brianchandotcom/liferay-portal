/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLFileEntryType}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryType
 * @generated
 */
public class DLFileEntryTypeWrapper implements DLFileEntryType,
	ModelWrapper<DLFileEntryType> {
	public DLFileEntryTypeWrapper(DLFileEntryType dlFileEntryType) {
		_dlFileEntryType = dlFileEntryType;
	}

	@Override
	public Class<?> getModelClass() {
		return DLFileEntryType.class;
	}

	@Override
	public String getModelClassName() {
		return DLFileEntryType.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("fileEntryTypeId", getFileEntryTypeId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fileEntryTypeKey", getFileEntryTypeKey());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fileEntryTypeId = (Long)attributes.get("fileEntryTypeId");

		if (fileEntryTypeId != null) {
			setFileEntryTypeId(fileEntryTypeId);
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

		String fileEntryTypeKey = (String)attributes.get("fileEntryTypeKey");

		if (fileEntryTypeKey != null) {
			setFileEntryTypeKey(fileEntryTypeKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	/**
	* Returns the primary key of this document library file entry type.
	*
	* @return the primary key of this document library file entry type
	*/
	@Override
	public long getPrimaryKey() {
		return _dlFileEntryType.getPrimaryKey();
	}

	/**
	* Sets the primary key of this document library file entry type.
	*
	* @param primaryKey the primary key of this document library file entry type
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlFileEntryType.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this document library file entry type.
	*
	* @return the uuid of this document library file entry type
	*/
	@Override
	public java.lang.String getUuid() {
		return _dlFileEntryType.getUuid();
	}

	/**
	* Sets the uuid of this document library file entry type.
	*
	* @param uuid the uuid of this document library file entry type
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_dlFileEntryType.setUuid(uuid);
	}

	/**
	* Returns the file entry type ID of this document library file entry type.
	*
	* @return the file entry type ID of this document library file entry type
	*/
	@Override
	public long getFileEntryTypeId() {
		return _dlFileEntryType.getFileEntryTypeId();
	}

	/**
	* Sets the file entry type ID of this document library file entry type.
	*
	* @param fileEntryTypeId the file entry type ID of this document library file entry type
	*/
	@Override
	public void setFileEntryTypeId(long fileEntryTypeId) {
		_dlFileEntryType.setFileEntryTypeId(fileEntryTypeId);
	}

	/**
	* Returns the group ID of this document library file entry type.
	*
	* @return the group ID of this document library file entry type
	*/
	@Override
	public long getGroupId() {
		return _dlFileEntryType.getGroupId();
	}

	/**
	* Sets the group ID of this document library file entry type.
	*
	* @param groupId the group ID of this document library file entry type
	*/
	@Override
	public void setGroupId(long groupId) {
		_dlFileEntryType.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this document library file entry type.
	*
	* @return the company ID of this document library file entry type
	*/
	@Override
	public long getCompanyId() {
		return _dlFileEntryType.getCompanyId();
	}

	/**
	* Sets the company ID of this document library file entry type.
	*
	* @param companyId the company ID of this document library file entry type
	*/
	@Override
	public void setCompanyId(long companyId) {
		_dlFileEntryType.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this document library file entry type.
	*
	* @return the user ID of this document library file entry type
	*/
	@Override
	public long getUserId() {
		return _dlFileEntryType.getUserId();
	}

	/**
	* Sets the user ID of this document library file entry type.
	*
	* @param userId the user ID of this document library file entry type
	*/
	@Override
	public void setUserId(long userId) {
		_dlFileEntryType.setUserId(userId);
	}

	/**
	* Returns the user uuid of this document library file entry type.
	*
	* @return the user uuid of this document library file entry type
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryType.getUserUuid();
	}

	/**
	* Sets the user uuid of this document library file entry type.
	*
	* @param userUuid the user uuid of this document library file entry type
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileEntryType.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this document library file entry type.
	*
	* @return the user name of this document library file entry type
	*/
	@Override
	public java.lang.String getUserName() {
		return _dlFileEntryType.getUserName();
	}

	/**
	* Sets the user name of this document library file entry type.
	*
	* @param userName the user name of this document library file entry type
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_dlFileEntryType.setUserName(userName);
	}

	/**
	* Returns the create date of this document library file entry type.
	*
	* @return the create date of this document library file entry type
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _dlFileEntryType.getCreateDate();
	}

	/**
	* Sets the create date of this document library file entry type.
	*
	* @param createDate the create date of this document library file entry type
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_dlFileEntryType.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this document library file entry type.
	*
	* @return the modified date of this document library file entry type
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _dlFileEntryType.getModifiedDate();
	}

	/**
	* Sets the modified date of this document library file entry type.
	*
	* @param modifiedDate the modified date of this document library file entry type
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFileEntryType.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the file entry type key of this document library file entry type.
	*
	* @return the file entry type key of this document library file entry type
	*/
	@Override
	public java.lang.String getFileEntryTypeKey() {
		return _dlFileEntryType.getFileEntryTypeKey();
	}

	/**
	* Sets the file entry type key of this document library file entry type.
	*
	* @param fileEntryTypeKey the file entry type key of this document library file entry type
	*/
	@Override
	public void setFileEntryTypeKey(java.lang.String fileEntryTypeKey) {
		_dlFileEntryType.setFileEntryTypeKey(fileEntryTypeKey);
	}

	/**
	* Returns the name of this document library file entry type.
	*
	* @return the name of this document library file entry type
	*/
	@Override
	public java.lang.String getName() {
		return _dlFileEntryType.getName();
	}

	/**
	* Returns the localized name of this document library file entry type in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this document library file entry type
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _dlFileEntryType.getName(locale);
	}

	/**
	* Returns the localized name of this document library file entry type in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this document library file entry type. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _dlFileEntryType.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this document library file entry type in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this document library file entry type
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _dlFileEntryType.getName(languageId);
	}

	/**
	* Returns the localized name of this document library file entry type in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this document library file entry type
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _dlFileEntryType.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _dlFileEntryType.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _dlFileEntryType.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this document library file entry type.
	*
	* @return the locales and localized names of this document library file entry type
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _dlFileEntryType.getNameMap();
	}

	/**
	* Sets the name of this document library file entry type.
	*
	* @param name the name of this document library file entry type
	*/
	@Override
	public void setName(java.lang.String name) {
		_dlFileEntryType.setName(name);
	}

	/**
	* Sets the localized name of this document library file entry type in the language.
	*
	* @param name the localized name of this document library file entry type
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_dlFileEntryType.setName(name, locale);
	}

	/**
	* Sets the localized name of this document library file entry type in the language, and sets the default locale.
	*
	* @param name the localized name of this document library file entry type
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_dlFileEntryType.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_dlFileEntryType.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this document library file entry type from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this document library file entry type
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_dlFileEntryType.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this document library file entry type from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this document library file entry type
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_dlFileEntryType.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this document library file entry type.
	*
	* @return the description of this document library file entry type
	*/
	@Override
	public java.lang.String getDescription() {
		return _dlFileEntryType.getDescription();
	}

	/**
	* Returns the localized description of this document library file entry type in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this document library file entry type
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _dlFileEntryType.getDescription(locale);
	}

	/**
	* Returns the localized description of this document library file entry type in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this document library file entry type. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _dlFileEntryType.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this document library file entry type in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this document library file entry type
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _dlFileEntryType.getDescription(languageId);
	}

	/**
	* Returns the localized description of this document library file entry type in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this document library file entry type
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _dlFileEntryType.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _dlFileEntryType.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _dlFileEntryType.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this document library file entry type.
	*
	* @return the locales and localized descriptions of this document library file entry type
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _dlFileEntryType.getDescriptionMap();
	}

	/**
	* Sets the description of this document library file entry type.
	*
	* @param description the description of this document library file entry type
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_dlFileEntryType.setDescription(description);
	}

	/**
	* Sets the localized description of this document library file entry type in the language.
	*
	* @param description the localized description of this document library file entry type
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_dlFileEntryType.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this document library file entry type in the language, and sets the default locale.
	*
	* @param description the localized description of this document library file entry type
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_dlFileEntryType.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_dlFileEntryType.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this document library file entry type from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this document library file entry type
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_dlFileEntryType.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this document library file entry type from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this document library file entry type
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_dlFileEntryType.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public boolean isNew() {
		return _dlFileEntryType.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_dlFileEntryType.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _dlFileEntryType.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFileEntryType.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFileEntryType.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileEntryType.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFileEntryType.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileEntryType.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_dlFileEntryType.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_dlFileEntryType.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileEntryType.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_dlFileEntryType.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new DLFileEntryTypeWrapper((DLFileEntryType)_dlFileEntryType.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		return _dlFileEntryType.compareTo(dlFileEntryType);
	}

	@Override
	public int hashCode() {
		return _dlFileEntryType.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.documentlibrary.model.DLFileEntryType> toCacheModel() {
		return _dlFileEntryType.toCacheModel();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType toEscapedModel() {
		return new DLFileEntryTypeWrapper(_dlFileEntryType.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType toUnescapedModel() {
		return new DLFileEntryTypeWrapper(_dlFileEntryType.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _dlFileEntryType.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlFileEntryType.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryType.persist();
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryType.getDDMStructures();
	}

	@Override
	public boolean isExportable() {
		return _dlFileEntryType.isExportable();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public DLFileEntryType getWrappedDLFileEntryType() {
		return _dlFileEntryType;
	}

	@Override
	public DLFileEntryType getWrappedModel() {
		return _dlFileEntryType;
	}

	@Override
	public void resetOriginalValues() {
		_dlFileEntryType.resetOriginalValues();
	}

	private DLFileEntryType _dlFileEntryType;
}
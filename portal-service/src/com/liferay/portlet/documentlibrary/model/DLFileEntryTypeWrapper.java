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

package com.liferay.portlet.documentlibrary.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLFileEntryType}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryType
 * @generated
 */
@ProviderType
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
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
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
	public java.lang.String[] getAvailableLanguageIds() {
		return _dlFileEntryType.getAvailableLanguageIds();
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
	* Returns the create date of this document library file entry type.
	*
	* @return the create date of this document library file entry type
	*/
	@Override
	public Date getCreateDate() {
		return _dlFileEntryType.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.DDMStructure> getDDMStructures() {
		return _dlFileEntryType.getDDMStructures();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _dlFileEntryType.getDefaultLanguageId();
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
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _dlFileEntryType.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileEntryType.getExpandoBridge();
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
	* Returns the file entry type key of this document library file entry type.
	*
	* @return the file entry type key of this document library file entry type
	*/
	@Override
	public java.lang.String getFileEntryTypeKey() {
		return _dlFileEntryType.getFileEntryTypeKey();
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
	* Returns the last publish date of this document library file entry type.
	*
	* @return the last publish date of this document library file entry type
	*/
	@Override
	public Date getLastPublishDate() {
		return _dlFileEntryType.getLastPublishDate();
	}

	/**
	* Returns the modified date of this document library file entry type.
	*
	* @return the modified date of this document library file entry type
	*/
	@Override
	public Date getModifiedDate() {
		return _dlFileEntryType.getModifiedDate();
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
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _dlFileEntryType.getNameMap();
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

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileEntryType.getPrimaryKeyObj();
	}

	@Override
	public java.lang.String getUnambiguousName(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes,
		long groupId, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _dlFileEntryType.getUnambiguousName(dlFileEntryTypes, groupId,
			locale);
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
	* Returns the user name of this document library file entry type.
	*
	* @return the user name of this document library file entry type
	*/
	@Override
	public java.lang.String getUserName() {
		return _dlFileEntryType.getUserName();
	}

	/**
	* Returns the user uuid of this document library file entry type.
	*
	* @return the user uuid of this document library file entry type
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _dlFileEntryType.getUserUuid();
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

	@Override
	public int hashCode() {
		return _dlFileEntryType.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _dlFileEntryType.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlFileEntryType.isEscapedModel();
	}

	@Override
	public boolean isExportable() {
		return _dlFileEntryType.isExportable();
	}

	@Override
	public boolean isNew() {
		return _dlFileEntryType.isNew();
	}

	@Override
	public void persist() {
		_dlFileEntryType.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_dlFileEntryType.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_dlFileEntryType.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlFileEntryType.setCachedModel(cachedModel);
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
	* Sets the create date of this document library file entry type.
	*
	* @param createDate the create date of this document library file entry type
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_dlFileEntryType.setCreateDate(createDate);
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
		Map<java.util.Locale, java.lang.String> descriptionMap) {
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
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_dlFileEntryType.setDescriptionMap(descriptionMap, defaultLocale);
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
	* Sets the file entry type key of this document library file entry type.
	*
	* @param fileEntryTypeKey the file entry type key of this document library file entry type
	*/
	@Override
	public void setFileEntryTypeKey(java.lang.String fileEntryTypeKey) {
		_dlFileEntryType.setFileEntryTypeKey(fileEntryTypeKey);
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
	* Sets the last publish date of this document library file entry type.
	*
	* @param lastPublishDate the last publish date of this document library file entry type
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_dlFileEntryType.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this document library file entry type.
	*
	* @param modifiedDate the modified date of this document library file entry type
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_dlFileEntryType.setModifiedDate(modifiedDate);
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
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_dlFileEntryType.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this document library file entry type from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this document library file entry type
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_dlFileEntryType.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_dlFileEntryType.setNew(n);
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

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_dlFileEntryType.setPrimaryKeyObj(primaryKeyObj);
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
	* Sets the user name of this document library file entry type.
	*
	* @param userName the user name of this document library file entry type
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_dlFileEntryType.setUserName(userName);
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
	* Sets the uuid of this document library file entry type.
	*
	* @param uuid the uuid of this document library file entry type
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_dlFileEntryType.setUuid(uuid);
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
	public java.lang.String toString() {
		return _dlFileEntryType.toString();
	}

	@Override
	public com.liferay.portlet.documentlibrary.model.DLFileEntryType toUnescapedModel() {
		return new DLFileEntryTypeWrapper(_dlFileEntryType.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _dlFileEntryType.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileEntryTypeWrapper)) {
			return false;
		}

		DLFileEntryTypeWrapper dlFileEntryTypeWrapper = (DLFileEntryTypeWrapper)obj;

		if (Validator.equals(_dlFileEntryType,
					dlFileEntryTypeWrapper._dlFileEntryType)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _dlFileEntryType.getStagedModelType();
	}

	@Override
	public DLFileEntryType getWrappedModel() {
		return _dlFileEntryType;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlFileEntryType.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlFileEntryType.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlFileEntryType.resetOriginalValues();
	}

	private final DLFileEntryType _dlFileEntryType;
}
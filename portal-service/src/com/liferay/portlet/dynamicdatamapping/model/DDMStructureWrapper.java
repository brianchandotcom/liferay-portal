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

package com.liferay.portlet.dynamicdatamapping.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructure}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructure
 * @generated
 */
public class DDMStructureWrapper implements DDMStructure,
	ModelWrapper<DDMStructure> {
	public DDMStructureWrapper(DDMStructure ddmStructure) {
		_ddmStructure = ddmStructure;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMStructure.class;
	}

	@Override
	public String getModelClassName() {
		return DDMStructure.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("structureId", getStructureId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentStructureId", getParentStructureId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("structureKey", getStructureKey());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("xsd", getXsd());
		attributes.put("storageType", getStorageType());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long structureId = (Long)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
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

		Long parentStructureId = (Long)attributes.get("parentStructureId");

		if (parentStructureId != null) {
			setParentStructureId(parentStructureId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		String structureKey = (String)attributes.get("structureKey");

		if (structureKey != null) {
			setStructureKey(structureKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String xsd = (String)attributes.get("xsd");

		if (xsd != null) {
			setXsd(xsd);
		}

		String storageType = (String)attributes.get("storageType");

		if (storageType != null) {
			setStorageType(storageType);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	/**
	* Returns the primary key of this d d m structure.
	*
	* @return the primary key of this d d m structure
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmStructure.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m structure.
	*
	* @param primaryKey the primary key of this d d m structure
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmStructure.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this d d m structure.
	*
	* @return the uuid of this d d m structure
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddmStructure.getUuid();
	}

	/**
	* Sets the uuid of this d d m structure.
	*
	* @param uuid the uuid of this d d m structure
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddmStructure.setUuid(uuid);
	}

	/**
	* Returns the structure ID of this d d m structure.
	*
	* @return the structure ID of this d d m structure
	*/
	@Override
	public long getStructureId() {
		return _ddmStructure.getStructureId();
	}

	/**
	* Sets the structure ID of this d d m structure.
	*
	* @param structureId the structure ID of this d d m structure
	*/
	@Override
	public void setStructureId(long structureId) {
		_ddmStructure.setStructureId(structureId);
	}

	/**
	* Returns the group ID of this d d m structure.
	*
	* @return the group ID of this d d m structure
	*/
	@Override
	public long getGroupId() {
		return _ddmStructure.getGroupId();
	}

	/**
	* Sets the group ID of this d d m structure.
	*
	* @param groupId the group ID of this d d m structure
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmStructure.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this d d m structure.
	*
	* @return the company ID of this d d m structure
	*/
	@Override
	public long getCompanyId() {
		return _ddmStructure.getCompanyId();
	}

	/**
	* Sets the company ID of this d d m structure.
	*
	* @param companyId the company ID of this d d m structure
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmStructure.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this d d m structure.
	*
	* @return the user ID of this d d m structure
	*/
	@Override
	public long getUserId() {
		return _ddmStructure.getUserId();
	}

	/**
	* Sets the user ID of this d d m structure.
	*
	* @param userId the user ID of this d d m structure
	*/
	@Override
	public void setUserId(long userId) {
		_ddmStructure.setUserId(userId);
	}

	/**
	* Returns the user uuid of this d d m structure.
	*
	* @return the user uuid of this d d m structure
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d m structure.
	*
	* @param userUuid the user uuid of this d d m structure
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmStructure.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this d d m structure.
	*
	* @return the user name of this d d m structure
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmStructure.getUserName();
	}

	/**
	* Sets the user name of this d d m structure.
	*
	* @param userName the user name of this d d m structure
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmStructure.setUserName(userName);
	}

	/**
	* Returns the create date of this d d m structure.
	*
	* @return the create date of this d d m structure
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _ddmStructure.getCreateDate();
	}

	/**
	* Sets the create date of this d d m structure.
	*
	* @param createDate the create date of this d d m structure
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_ddmStructure.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this d d m structure.
	*
	* @return the modified date of this d d m structure
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _ddmStructure.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d m structure.
	*
	* @param modifiedDate the modified date of this d d m structure
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddmStructure.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the parent structure ID of this d d m structure.
	*
	* @return the parent structure ID of this d d m structure
	*/
	@Override
	public long getParentStructureId() {
		return _ddmStructure.getParentStructureId();
	}

	/**
	* Sets the parent structure ID of this d d m structure.
	*
	* @param parentStructureId the parent structure ID of this d d m structure
	*/
	@Override
	public void setParentStructureId(long parentStructureId) {
		_ddmStructure.setParentStructureId(parentStructureId);
	}

	/**
	* Returns the fully qualified class name of this d d m structure.
	*
	* @return the fully qualified class name of this d d m structure
	*/
	@Override
	public java.lang.String getClassName() {
		return _ddmStructure.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_ddmStructure.setClassName(className);
	}

	/**
	* Returns the class name ID of this d d m structure.
	*
	* @return the class name ID of this d d m structure
	*/
	@Override
	public long getClassNameId() {
		return _ddmStructure.getClassNameId();
	}

	/**
	* Sets the class name ID of this d d m structure.
	*
	* @param classNameId the class name ID of this d d m structure
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ddmStructure.setClassNameId(classNameId);
	}

	/**
	* Returns the structure key of this d d m structure.
	*
	* @return the structure key of this d d m structure
	*/
	@Override
	public java.lang.String getStructureKey() {
		return _ddmStructure.getStructureKey();
	}

	/**
	* Sets the structure key of this d d m structure.
	*
	* @param structureKey the structure key of this d d m structure
	*/
	@Override
	public void setStructureKey(java.lang.String structureKey) {
		_ddmStructure.setStructureKey(structureKey);
	}

	/**
	* Returns the name of this d d m structure.
	*
	* @return the name of this d d m structure
	*/
	@Override
	public java.lang.String getName() {
		return _ddmStructure.getName();
	}

	/**
	* Returns the localized name of this d d m structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this d d m structure
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmStructure.getName(locale);
	}

	/**
	* Returns the localized name of this d d m structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m structure. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmStructure.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this d d m structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this d d m structure
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmStructure.getName(languageId);
	}

	/**
	* Returns the localized name of this d d m structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m structure
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmStructure.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _ddmStructure.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _ddmStructure.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this d d m structure.
	*
	* @return the locales and localized names of this d d m structure
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmStructure.getNameMap();
	}

	/**
	* Sets the name of this d d m structure.
	*
	* @param name the name of this d d m structure
	*/
	@Override
	public void setName(java.lang.String name) {
		_ddmStructure.setName(name);
	}

	/**
	* Sets the localized name of this d d m structure in the language.
	*
	* @param name the localized name of this d d m structure
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmStructure.setName(name, locale);
	}

	/**
	* Sets the localized name of this d d m structure in the language, and sets the default locale.
	*
	* @param name the localized name of this d d m structure
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmStructure.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddmStructure.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this d d m structure from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d m structure
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmStructure.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d m structure from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d m structure
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmStructure.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this d d m structure.
	*
	* @return the description of this d d m structure
	*/
	@Override
	public java.lang.String getDescription() {
		return _ddmStructure.getDescription();
	}

	/**
	* Returns the localized description of this d d m structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this d d m structure
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _ddmStructure.getDescription(locale);
	}

	/**
	* Returns the localized description of this d d m structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m structure. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddmStructure.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this d d m structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this d d m structure
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _ddmStructure.getDescription(languageId);
	}

	/**
	* Returns the localized description of this d d m structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m structure
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddmStructure.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddmStructure.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _ddmStructure.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this d d m structure.
	*
	* @return the locales and localized descriptions of this d d m structure
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddmStructure.getDescriptionMap();
	}

	/**
	* Sets the description of this d d m structure.
	*
	* @param description the description of this d d m structure
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_ddmStructure.setDescription(description);
	}

	/**
	* Sets the localized description of this d d m structure in the language.
	*
	* @param description the localized description of this d d m structure
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_ddmStructure.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this d d m structure in the language, and sets the default locale.
	*
	* @param description the localized description of this d d m structure
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddmStructure.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddmStructure.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this d d m structure from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m structure
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddmStructure.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this d d m structure from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m structure
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmStructure.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the xsd of this d d m structure.
	*
	* @return the xsd of this d d m structure
	*/
	@Override
	public java.lang.String getXsd() {
		return _ddmStructure.getXsd();
	}

	/**
	* Sets the xsd of this d d m structure.
	*
	* @param xsd the xsd of this d d m structure
	*/
	@Override
	public void setXsd(java.lang.String xsd) {
		_ddmStructure.setXsd(xsd);
	}

	/**
	* Returns the storage type of this d d m structure.
	*
	* @return the storage type of this d d m structure
	*/
	@Override
	public java.lang.String getStorageType() {
		return _ddmStructure.getStorageType();
	}

	/**
	* Sets the storage type of this d d m structure.
	*
	* @param storageType the storage type of this d d m structure
	*/
	@Override
	public void setStorageType(java.lang.String storageType) {
		_ddmStructure.setStorageType(storageType);
	}

	/**
	* Returns the type of this d d m structure.
	*
	* @return the type of this d d m structure
	*/
	@Override
	public int getType() {
		return _ddmStructure.getType();
	}

	/**
	* Sets the type of this d d m structure.
	*
	* @param type the type of this d d m structure
	*/
	@Override
	public void setType(int type) {
		_ddmStructure.setType(type);
	}

	@Override
	public boolean isNew() {
		return _ddmStructure.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_ddmStructure.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _ddmStructure.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmStructure.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmStructure.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmStructure.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmStructure.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmStructure.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddmStructure.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddmStructure.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmStructure.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddmStructure.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new DDMStructureWrapper((DDMStructure)_ddmStructure.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure) {
		return _ddmStructure.compareTo(ddmStructure);
	}

	@Override
	public int hashCode() {
		return _ddmStructure.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> toCacheModel() {
		return _ddmStructure.toCacheModel();
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure toEscapedModel() {
		return new DDMStructureWrapper(_ddmStructure.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure toUnescapedModel() {
		return new DDMStructureWrapper(_ddmStructure.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmStructure.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmStructure.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmStructure.persist();
	}

	@Override
	public java.util.List<java.lang.String> getAvailableLanguageIds() {
		return _ddmStructure.getAvailableLanguageIds();
	}

	@Override
	public java.util.List<java.lang.String> getChildrenFieldNames(
		java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getChildrenFieldNames(fieldName);
	}

	@Override
	public java.lang.String getCompleteXsd()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getCompleteXsd();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _ddmStructure.getDefaultLanguageId();
	}

	@Override
	public com.liferay.portal.kernel.xml.Document getDocument() {
		return _ddmStructure.getDocument();
	}

	@Override
	public java.lang.String getFieldDataType(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldDataType(fieldName);
	}

	@Override
	public java.lang.String getFieldLabel(java.lang.String fieldName,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldLabel(fieldName, locale);
	}

	@Override
	public java.lang.String getFieldLabel(java.lang.String fieldName,
		java.lang.String locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldLabel(fieldName, locale);
	}

	@Override
	public java.util.Set<java.lang.String> getFieldNames()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldNames();
	}

	@Override
	public java.lang.String getFieldProperty(java.lang.String fieldName,
		java.lang.String property)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldProperty(fieldName, property);
	}

	@Override
	public java.lang.String getFieldProperty(java.lang.String fieldName,
		java.lang.String property, java.lang.String locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldProperty(fieldName, property, locale);
	}

	@Override
	public boolean getFieldRepeatable(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldRepeatable(fieldName);
	}

	@Override
	public boolean getFieldRequired(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldRequired(fieldName);
	}

	@Override
	public java.util.Map<java.lang.String, java.lang.String> getFields(
		java.lang.String fieldName, java.lang.String attributeName,
		java.lang.String attributeValue) {
		return _ddmStructure.getFields(fieldName, attributeName, attributeValue);
	}

	@Override
	public java.util.Map<java.lang.String, java.lang.String> getFields(
		java.lang.String fieldName, java.lang.String attributeName,
		java.lang.String attributeValue, java.lang.String locale) {
		return _ddmStructure.getFields(fieldName, attributeName,
			attributeValue, locale);
	}

	@Override
	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> getFieldsMap()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldsMap();
	}

	@Override
	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> getFieldsMap(
		java.lang.String locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldsMap(locale);
	}

	@Override
	public java.lang.String getFieldTip(java.lang.String fieldName,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldTip(fieldName, locale);
	}

	@Override
	public java.lang.String getFieldTip(java.lang.String fieldName,
		java.lang.String locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldTip(fieldName, locale);
	}

	@Override
	public java.lang.String getFieldType(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getFieldType(fieldName);
	}

	@Override
	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>> getLocalizedFieldsMap() {
		return _ddmStructure.getLocalizedFieldsMap();
	}

	@Override
	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>> getLocalizedTransientFieldsMap() {
		return _ddmStructure.getLocalizedTransientFieldsMap();
	}

	@Override
	public java.util.List<java.lang.String> getRootFieldNames()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getRootFieldNames();
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getTemplates();
	}

	@Override
	public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> getTransientFieldsMap(
		java.lang.String locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.getTransientFieldsMap(locale);
	}

	/**
	* Returns the WebDAV URL to access the structure.
	*
	* @param themeDisplay the theme display needed to build the URL. It can
	set HTTPS access, the server name, the server port, the path
	context, and the scope group.
	* @param webDAVToken the WebDAV token for the URL
	* @return the WebDAV URL
	*/
	@Override
	public java.lang.String getWebDavURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		java.lang.String webDAVToken) {
		return _ddmStructure.getWebDavURL(themeDisplay, webDAVToken);
	}

	@Override
	public boolean hasField(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.hasField(fieldName);
	}

	@Override
	public boolean isFieldPrivate(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.isFieldPrivate(fieldName);
	}

	@Override
	public boolean isFieldRepeatable(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructure.isFieldRepeatable(fieldName);
	}

	@Override
	public void setDocument(com.liferay.portal.kernel.xml.Document document) {
		_ddmStructure.setDocument(document);
	}

	@Override
	public void setLocalizedFieldsMap(
		java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>> localizedFieldsMap) {
		_ddmStructure.setLocalizedFieldsMap(localizedFieldsMap);
	}

	@Override
	public void setLocalizedTransientFieldsMap(
		java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>> localizedTransientFieldsMap) {
		_ddmStructure.setLocalizedTransientFieldsMap(localizedTransientFieldsMap);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStructureWrapper)) {
			return false;
		}

		DDMStructureWrapper ddmStructureWrapper = (DDMStructureWrapper)obj;

		if (Validator.equals(_ddmStructure, ddmStructureWrapper._ddmStructure)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public DDMStructure getWrappedDDMStructure() {
		return _ddmStructure;
	}

	@Override
	public DDMStructure getWrappedModel() {
		return _ddmStructure;
	}

	@Override
	public void resetOriginalValues() {
		_ddmStructure.resetOriginalValues();
	}

	private DDMStructure _ddmStructure;
}
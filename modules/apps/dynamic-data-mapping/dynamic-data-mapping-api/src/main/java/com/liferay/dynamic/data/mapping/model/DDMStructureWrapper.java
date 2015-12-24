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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

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
@ProviderType
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
		attributes.put("versionUserId", getVersionUserId());
		attributes.put("versionUserName", getVersionUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentStructureId", getParentStructureId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("structureKey", getStructureKey());
		attributes.put("version", getVersion());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("definition", getDefinition());
		attributes.put("storageType", getStorageType());
		attributes.put("type", getType());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Long versionUserId = (Long)attributes.get("versionUserId");

		if (versionUserId != null) {
			setVersionUserId(versionUserId);
		}

		String versionUserName = (String)attributes.get("versionUserName");

		if (versionUserName != null) {
			setVersionUserName(versionUserName);
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

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String definition = (String)attributes.get("definition");

		if (definition != null) {
			setDefinition(definition);
		}

		String storageType = (String)attributes.get("storageType");

		if (storageType != null) {
			setStorageType(storageType);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDMStructureWrapper((DDMStructure)_ddmStructure.clone());
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure) {
		return _ddmStructure.compareTo(ddmStructure);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMForm createFullHierarchyDDMForm()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.createFullHierarchyDDMForm();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _ddmStructure.getAvailableLanguageIds();
	}

	@Override
	public java.util.List<java.lang.String> getChildrenFieldNames(
		java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getChildrenFieldNames(fieldName);
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
	* Returns the company ID of this d d m structure.
	*
	* @return the company ID of this d d m structure
	*/
	@Override
	public long getCompanyId() {
		return _ddmStructure.getCompanyId();
	}

	/**
	* Returns the create date of this d d m structure.
	*
	* @return the create date of this d d m structure
	*/
	@Override
	public Date getCreateDate() {
		return _ddmStructure.getCreateDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMForm getDDMForm() {
		return _ddmStructure.getDDMForm();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormField getDDMFormField(
		java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getDDMFormField(fieldName);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormField> getDDMFormFields(
		boolean includeTransientFields) {
		return _ddmStructure.getDDMFormFields(includeTransientFields);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormLayout getDDMFormLayout()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getDDMFormLayout();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _ddmStructure.getDefaultLanguageId();
	}

	/**
	* Returns the definition of this d d m structure.
	*
	* @return the definition of this d d m structure
	*/
	@Override
	public java.lang.String getDefinition() {
		return _ddmStructure.getDefinition();
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
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddmStructure.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmStructure.getExpandoBridge();
	}

	@Override
	public java.lang.String getFieldDataType(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldDataType(fieldName);
	}

	@Override
	public java.lang.String getFieldLabel(java.lang.String fieldName,
		java.lang.String locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldLabel(fieldName, locale);
	}

	@Override
	public java.lang.String getFieldLabel(java.lang.String fieldName,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldLabel(fieldName, locale);
	}

	@Override
	public java.util.Set<java.lang.String> getFieldNames() {
		return _ddmStructure.getFieldNames();
	}

	@Override
	public java.lang.String getFieldProperty(java.lang.String fieldName,
		java.lang.String property)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldProperty(fieldName, property);
	}

	@Override
	public boolean getFieldRepeatable(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldRepeatable(fieldName);
	}

	@Override
	public boolean getFieldRequired(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldRequired(fieldName);
	}

	@Override
	public java.lang.String getFieldTip(java.lang.String fieldName,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldTip(fieldName, locale);
	}

	@Override
	public java.lang.String getFieldTip(java.lang.String fieldName,
		java.lang.String locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldTip(fieldName, locale);
	}

	@Override
	public java.lang.String getFieldType(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldType(fieldName);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMForm getFullHierarchyDDMForm() {
		return _ddmStructure.getFullHierarchyDDMForm();
	}

	@Override
	public Map<java.lang.String, com.liferay.dynamic.data.mapping.model.DDMFormField> getFullHierarchyDDMFormFieldsMap(
		boolean includeNestedDDMFormFields) {
		return _ddmStructure.getFullHierarchyDDMFormFieldsMap(includeNestedDDMFormFields);
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
	* Returns the last publish date of this d d m structure.
	*
	* @return the last publish date of this d d m structure
	*/
	@Override
	public Date getLastPublishDate() {
		return _ddmStructure.getLastPublishDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion getLatestStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getLatestStructureVersion();
	}

	/**
	* Returns the modified date of this d d m structure.
	*
	* @return the modified date of this d d m structure
	*/
	@Override
	public Date getModifiedDate() {
		return _ddmStructure.getModifiedDate();
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
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmStructure.getNameMap();
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
	* Returns the primary key of this d d m structure.
	*
	* @return the primary key of this d d m structure
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmStructure.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmStructure.getPrimaryKeyObj();
	}

	@Override
	public java.util.List<java.lang.String> getRootFieldNames() {
		return _ddmStructure.getRootFieldNames();
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
	* Returns the structure ID of this d d m structure.
	*
	* @return the structure ID of this d d m structure
	*/
	@Override
	public long getStructureId() {
		return _ddmStructure.getStructureId();
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

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion getStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getStructureVersion();
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates() {
		return _ddmStructure.getTemplates();
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

	@Override
	public java.lang.String getUnambiguousName(
		java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> structures,
		long groupId, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getUnambiguousName(structures, groupId, locale);
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
	* Returns the user name of this d d m structure.
	*
	* @return the user name of this d d m structure
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmStructure.getUserName();
	}

	/**
	* Returns the user uuid of this d d m structure.
	*
	* @return the user uuid of this d d m structure
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmStructure.getUserUuid();
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
	* Returns the version of this d d m structure.
	*
	* @return the version of this d d m structure
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddmStructure.getVersion();
	}

	/**
	* Returns the version user ID of this d d m structure.
	*
	* @return the version user ID of this d d m structure
	*/
	@Override
	public long getVersionUserId() {
		return _ddmStructure.getVersionUserId();
	}

	/**
	* Returns the version user name of this d d m structure.
	*
	* @return the version user name of this d d m structure
	*/
	@Override
	public java.lang.String getVersionUserName() {
		return _ddmStructure.getVersionUserName();
	}

	/**
	* Returns the version user uuid of this d d m structure.
	*
	* @return the version user uuid of this d d m structure
	*/
	@Override
	public java.lang.String getVersionUserUuid() {
		return _ddmStructure.getVersionUserUuid();
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
	public boolean hasField(java.lang.String fieldName) {
		return _ddmStructure.hasField(fieldName);
	}

	@Override
	public int hashCode() {
		return _ddmStructure.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmStructure.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmStructure.isEscapedModel();
	}

	@Override
	public boolean isFieldRepeatable(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.isFieldRepeatable(fieldName);
	}

	@Override
	public boolean isFieldTransient(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.isFieldTransient(fieldName);
	}

	@Override
	public boolean isNew() {
		return _ddmStructure.isNew();
	}

	@Override
	public void persist() {
		_ddmStructure.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_ddmStructure.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddmStructure.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmStructure.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_ddmStructure.setClassName(className);
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
	* Sets the company ID of this d d m structure.
	*
	* @param companyId the company ID of this d d m structure
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmStructure.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this d d m structure.
	*
	* @param createDate the create date of this d d m structure
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmStructure.setCreateDate(createDate);
	}

	@Override
	public void setDDMForm(
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm) {
		_ddmStructure.setDDMForm(ddmForm);
	}

	/**
	* Sets the definition of this d d m structure.
	*
	* @param definition the definition of this d d m structure
	*/
	@Override
	public void setDefinition(java.lang.String definition) {
		_ddmStructure.setDefinition(definition);
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
		Map<java.util.Locale, java.lang.String> descriptionMap) {
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
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmStructure.setDescriptionMap(descriptionMap, defaultLocale);
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
	* Sets the last publish date of this d d m structure.
	*
	* @param lastPublishDate the last publish date of this d d m structure
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_ddmStructure.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this d d m structure.
	*
	* @param modifiedDate the modified date of this d d m structure
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmStructure.setModifiedDate(modifiedDate);
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
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmStructure.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d m structure from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d m structure
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmStructure.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmStructure.setNew(n);
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
	* Sets the primary key of this d d m structure.
	*
	* @param primaryKey the primary key of this d d m structure
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmStructure.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmStructure.setPrimaryKeyObj(primaryKeyObj);
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
	* Sets the structure ID of this d d m structure.
	*
	* @param structureId the structure ID of this d d m structure
	*/
	@Override
	public void setStructureId(long structureId) {
		_ddmStructure.setStructureId(structureId);
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
	* Sets the type of this d d m structure.
	*
	* @param type the type of this d d m structure
	*/
	@Override
	public void setType(int type) {
		_ddmStructure.setType(type);
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
	* Sets the user name of this d d m structure.
	*
	* @param userName the user name of this d d m structure
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmStructure.setUserName(userName);
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
	* Sets the uuid of this d d m structure.
	*
	* @param uuid the uuid of this d d m structure
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddmStructure.setUuid(uuid);
	}

	/**
	* Sets the version of this d d m structure.
	*
	* @param version the version of this d d m structure
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddmStructure.setVersion(version);
	}

	/**
	* Sets the version user ID of this d d m structure.
	*
	* @param versionUserId the version user ID of this d d m structure
	*/
	@Override
	public void setVersionUserId(long versionUserId) {
		_ddmStructure.setVersionUserId(versionUserId);
	}

	/**
	* Sets the version user name of this d d m structure.
	*
	* @param versionUserName the version user name of this d d m structure
	*/
	@Override
	public void setVersionUserName(java.lang.String versionUserName) {
		_ddmStructure.setVersionUserName(versionUserName);
	}

	/**
	* Sets the version user uuid of this d d m structure.
	*
	* @param versionUserUuid the version user uuid of this d d m structure
	*/
	@Override
	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_ddmStructure.setVersionUserUuid(versionUserUuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.dynamic.data.mapping.model.DDMStructure> toCacheModel() {
		return _ddmStructure.toCacheModel();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructure toEscapedModel() {
		return new DDMStructureWrapper(_ddmStructure.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmStructure.toString();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructure toUnescapedModel() {
		return new DDMStructureWrapper(_ddmStructure.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmStructure.toXmlString();
	}

	@Override
	public void updateDDMForm(
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm) {
		_ddmStructure.updateDDMForm(ddmForm);
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

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmStructure.getStagedModelType();
	}

	@Override
	public DDMStructure getWrappedModel() {
		return _ddmStructure;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmStructure.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmStructure.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmStructure.resetOriginalValues();
	}

	private final DDMStructure _ddmStructure;
}
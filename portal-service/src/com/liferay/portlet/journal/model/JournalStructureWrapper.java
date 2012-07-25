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

package com.liferay.portlet.journal.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link JournalStructure}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalStructure
 * @generated
 */
public class JournalStructureWrapper implements JournalStructure,
	ModelWrapper<JournalStructure> {
	public JournalStructureWrapper(JournalStructure journalStructure) {
		_journalStructure = journalStructure;
	}

	public Class<?> getModelClass() {
		return JournalStructure.class;
	}

	public String getModelClassName() {
		return JournalStructure.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("id", getId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("structureId", getStructureId());
		attributes.put("parentStructureId", getParentStructureId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("xsd", getXsd());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
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

		String structureId = (String)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}

		String parentStructureId = (String)attributes.get("parentStructureId");

		if (parentStructureId != null) {
			setParentStructureId(parentStructureId);
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
	}

	/**
	* Returns the primary key of this journal structure.
	*
	* @return the primary key of this journal structure
	*/
	public long getPrimaryKey() {
		return _journalStructure.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal structure.
	*
	* @param primaryKey the primary key of this journal structure
	*/
	public void setPrimaryKey(long primaryKey) {
		_journalStructure.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this journal structure.
	*
	* @return the uuid of this journal structure
	*/
	public java.lang.String getUuid() {
		return _journalStructure.getUuid();
	}

	/**
	* Sets the uuid of this journal structure.
	*
	* @param uuid the uuid of this journal structure
	*/
	public void setUuid(java.lang.String uuid) {
		_journalStructure.setUuid(uuid);
	}

	/**
	* Returns the ID of this journal structure.
	*
	* @return the ID of this journal structure
	*/
	public long getId() {
		return _journalStructure.getId();
	}

	/**
	* Sets the ID of this journal structure.
	*
	* @param id the ID of this journal structure
	*/
	public void setId(long id) {
		_journalStructure.setId(id);
	}

	/**
	* Returns the group ID of this journal structure.
	*
	* @return the group ID of this journal structure
	*/
	public long getGroupId() {
		return _journalStructure.getGroupId();
	}

	/**
	* Sets the group ID of this journal structure.
	*
	* @param groupId the group ID of this journal structure
	*/
	public void setGroupId(long groupId) {
		_journalStructure.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this journal structure.
	*
	* @return the company ID of this journal structure
	*/
	public long getCompanyId() {
		return _journalStructure.getCompanyId();
	}

	/**
	* Sets the company ID of this journal structure.
	*
	* @param companyId the company ID of this journal structure
	*/
	public void setCompanyId(long companyId) {
		_journalStructure.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this journal structure.
	*
	* @return the user ID of this journal structure
	*/
	public long getUserId() {
		return _journalStructure.getUserId();
	}

	/**
	* Sets the user ID of this journal structure.
	*
	* @param userId the user ID of this journal structure
	*/
	public void setUserId(long userId) {
		_journalStructure.setUserId(userId);
	}

	/**
	* Returns the user uuid of this journal structure.
	*
	* @return the user uuid of this journal structure
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructure.getUserUuid();
	}

	/**
	* Sets the user uuid of this journal structure.
	*
	* @param userUuid the user uuid of this journal structure
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_journalStructure.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this journal structure.
	*
	* @return the user name of this journal structure
	*/
	public java.lang.String getUserName() {
		return _journalStructure.getUserName();
	}

	/**
	* Sets the user name of this journal structure.
	*
	* @param userName the user name of this journal structure
	*/
	public void setUserName(java.lang.String userName) {
		_journalStructure.setUserName(userName);
	}

	/**
	* Returns the create date of this journal structure.
	*
	* @return the create date of this journal structure
	*/
	public java.util.Date getCreateDate() {
		return _journalStructure.getCreateDate();
	}

	/**
	* Sets the create date of this journal structure.
	*
	* @param createDate the create date of this journal structure
	*/
	public void setCreateDate(java.util.Date createDate) {
		_journalStructure.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this journal structure.
	*
	* @return the modified date of this journal structure
	*/
	public java.util.Date getModifiedDate() {
		return _journalStructure.getModifiedDate();
	}

	/**
	* Sets the modified date of this journal structure.
	*
	* @param modifiedDate the modified date of this journal structure
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalStructure.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the structure ID of this journal structure.
	*
	* @return the structure ID of this journal structure
	*/
	public java.lang.String getStructureId() {
		return _journalStructure.getStructureId();
	}

	/**
	* Sets the structure ID of this journal structure.
	*
	* @param structureId the structure ID of this journal structure
	*/
	public void setStructureId(java.lang.String structureId) {
		_journalStructure.setStructureId(structureId);
	}

	/**
	* Returns the parent structure ID of this journal structure.
	*
	* @return the parent structure ID of this journal structure
	*/
	public java.lang.String getParentStructureId() {
		return _journalStructure.getParentStructureId();
	}

	/**
	* Sets the parent structure ID of this journal structure.
	*
	* @param parentStructureId the parent structure ID of this journal structure
	*/
	public void setParentStructureId(java.lang.String parentStructureId) {
		_journalStructure.setParentStructureId(parentStructureId);
	}

	/**
	* Returns the name of this journal structure.
	*
	* @return the name of this journal structure
	*/
	public java.lang.String getName() {
		return _journalStructure.getName();
	}

	/**
	* Returns the localized name of this journal structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this journal structure
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _journalStructure.getName(locale);
	}

	/**
	* Returns the localized name of this journal structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this journal structure. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _journalStructure.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this journal structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this journal structure
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _journalStructure.getName(languageId);
	}

	/**
	* Returns the localized name of this journal structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this journal structure
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _journalStructure.getName(languageId, useDefault);
	}

	public java.lang.String getNameCurrentLanguageId() {
		return _journalStructure.getNameCurrentLanguageId();
	}

	public java.lang.String getNameCurrentValue() {
		return _journalStructure.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this journal structure.
	*
	* @return the locales and localized names of this journal structure
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _journalStructure.getNameMap();
	}

	/**
	* Sets the name of this journal structure.
	*
	* @param name the name of this journal structure
	*/
	public void setName(java.lang.String name) {
		_journalStructure.setName(name);
	}

	/**
	* Sets the localized name of this journal structure in the language.
	*
	* @param name the localized name of this journal structure
	* @param locale the locale of the language
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_journalStructure.setName(name, locale);
	}

	/**
	* Sets the localized name of this journal structure in the language, and sets the default locale.
	*
	* @param name the localized name of this journal structure
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_journalStructure.setName(name, locale, defaultLocale);
	}

	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_journalStructure.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this journal structure from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this journal structure
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_journalStructure.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this journal structure from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this journal structure
	* @param defaultLocale the default locale
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_journalStructure.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this journal structure.
	*
	* @return the description of this journal structure
	*/
	public java.lang.String getDescription() {
		return _journalStructure.getDescription();
	}

	/**
	* Returns the localized description of this journal structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this journal structure
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _journalStructure.getDescription(locale);
	}

	/**
	* Returns the localized description of this journal structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this journal structure. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _journalStructure.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this journal structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this journal structure
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _journalStructure.getDescription(languageId);
	}

	/**
	* Returns the localized description of this journal structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this journal structure
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _journalStructure.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _journalStructure.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _journalStructure.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this journal structure.
	*
	* @return the locales and localized descriptions of this journal structure
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _journalStructure.getDescriptionMap();
	}

	/**
	* Sets the description of this journal structure.
	*
	* @param description the description of this journal structure
	*/
	public void setDescription(java.lang.String description) {
		_journalStructure.setDescription(description);
	}

	/**
	* Sets the localized description of this journal structure in the language.
	*
	* @param description the localized description of this journal structure
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_journalStructure.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this journal structure in the language, and sets the default locale.
	*
	* @param description the localized description of this journal structure
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_journalStructure.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_journalStructure.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this journal structure from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this journal structure
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_journalStructure.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this journal structure from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this journal structure
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_journalStructure.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the xsd of this journal structure.
	*
	* @return the xsd of this journal structure
	*/
	public java.lang.String getXsd() {
		return _journalStructure.getXsd();
	}

	/**
	* Sets the xsd of this journal structure.
	*
	* @param xsd the xsd of this journal structure
	*/
	public void setXsd(java.lang.String xsd) {
		_journalStructure.setXsd(xsd);
	}

	public boolean isNew() {
		return _journalStructure.isNew();
	}

	public void setNew(boolean n) {
		_journalStructure.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalStructure.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalStructure.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalStructure.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalStructure.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalStructure.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalStructure.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalStructure.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new JournalStructureWrapper((JournalStructure)_journalStructure.clone());
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalStructure journalStructure) {
		return _journalStructure.compareTo(journalStructure);
	}

	@Override
	public int hashCode() {
		return _journalStructure.hashCode();
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_journalStructure.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.journal.model.JournalStructure> toCacheModel() {
		return _journalStructure.toCacheModel();
	}

	public com.liferay.portlet.journal.model.JournalStructure toEscapedModel() {
		return new JournalStructureWrapper(_journalStructure.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalStructure.toString();
	}

	public java.lang.String toXmlString() {
		return _journalStructure.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalStructure.persist();
	}

	public java.lang.String getMergedXsd() {
		return _journalStructure.getMergedXsd();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public JournalStructure getWrappedJournalStructure() {
		return _journalStructure;
	}

	public JournalStructure getWrappedModel() {
		return _journalStructure;
	}

	public void resetOriginalValues() {
		_journalStructure.resetOriginalValues();
	}

	private JournalStructure _journalStructure;
}
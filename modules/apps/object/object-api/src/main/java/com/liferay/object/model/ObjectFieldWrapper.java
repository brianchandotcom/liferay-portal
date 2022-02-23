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

package com.liferay.object.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ObjectField}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectField
 * @generated
 */
public class ObjectFieldWrapper
	extends BaseModelWrapper<ObjectField>
	implements ModelWrapper<ObjectField>, ObjectField {

	public ObjectFieldWrapper(ObjectField objectField) {
		super(objectField);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectFieldId", getObjectFieldId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("listTypeDefinitionId", getListTypeDefinitionId());
		attributes.put("objectDefinitionId", getObjectDefinitionId());
		attributes.put("businessType", getBusinessType());
		attributes.put("dbColumnName", getDBColumnName());
		attributes.put("dbTableName", getDBTableName());
		attributes.put("dbType", getDBType());
		attributes.put("indexed", isIndexed());
		attributes.put("indexedAsKeyword", isIndexedAsKeyword());
		attributes.put("indexedLanguageId", getIndexedLanguageId());
		attributes.put("label", getLabel());
		attributes.put("name", getName());
		attributes.put("relationshipType", getRelationshipType());
		attributes.put("required", isRequired());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long objectFieldId = (Long)attributes.get("objectFieldId");

		if (objectFieldId != null) {
			setObjectFieldId(objectFieldId);
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

		Long listTypeDefinitionId = (Long)attributes.get(
			"listTypeDefinitionId");

		if (listTypeDefinitionId != null) {
			setListTypeDefinitionId(listTypeDefinitionId);
		}

		Long objectDefinitionId = (Long)attributes.get("objectDefinitionId");

		if (objectDefinitionId != null) {
			setObjectDefinitionId(objectDefinitionId);
		}

		String businessType = (String)attributes.get("businessType");

		if (businessType != null) {
			setBusinessType(businessType);
		}

		String dbColumnName = (String)attributes.get("dbColumnName");

		if (dbColumnName != null) {
			setDBColumnName(dbColumnName);
		}

		String dbTableName = (String)attributes.get("dbTableName");

		if (dbTableName != null) {
			setDBTableName(dbTableName);
		}

		String dbType = (String)attributes.get("dbType");

		if (dbType != null) {
			setDBType(dbType);
		}

		Boolean indexed = (Boolean)attributes.get("indexed");

		if (indexed != null) {
			setIndexed(indexed);
		}

		Boolean indexedAsKeyword = (Boolean)attributes.get("indexedAsKeyword");

		if (indexedAsKeyword != null) {
			setIndexedAsKeyword(indexedAsKeyword);
		}

		String indexedLanguageId = (String)attributes.get("indexedLanguageId");

		if (indexedLanguageId != null) {
			setIndexedLanguageId(indexedLanguageId);
		}

		String label = (String)attributes.get("label");

		if (label != null) {
			setLabel(label);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String relationshipType = (String)attributes.get("relationshipType");

		if (relationshipType != null) {
			setRelationshipType(relationshipType);
		}

		Boolean required = (Boolean)attributes.get("required");

		if (required != null) {
			setRequired(required);
		}
	}

	@Override
	public ObjectField cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the business type of this object field.
	 *
	 * @return the business type of this object field
	 */
	@Override
	public String getBusinessType() {
		return model.getBusinessType();
	}

	/**
	 * Returns the company ID of this object field.
	 *
	 * @return the company ID of this object field
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object field.
	 *
	 * @return the create date of this object field
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the db column name of this object field.
	 *
	 * @return the db column name of this object field
	 */
	@Override
	public String getDBColumnName() {
		return model.getDBColumnName();
	}

	/**
	 * Returns the db table name of this object field.
	 *
	 * @return the db table name of this object field
	 */
	@Override
	public String getDBTableName() {
		return model.getDBTableName();
	}

	/**
	 * Returns the db type of this object field.
	 *
	 * @return the db type of this object field
	 */
	@Override
	public String getDBType() {
		return model.getDBType();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the indexed of this object field.
	 *
	 * @return the indexed of this object field
	 */
	@Override
	public boolean getIndexed() {
		return model.getIndexed();
	}

	/**
	 * Returns the indexed as keyword of this object field.
	 *
	 * @return the indexed as keyword of this object field
	 */
	@Override
	public boolean getIndexedAsKeyword() {
		return model.getIndexedAsKeyword();
	}

	/**
	 * Returns the indexed language ID of this object field.
	 *
	 * @return the indexed language ID of this object field
	 */
	@Override
	public String getIndexedLanguageId() {
		return model.getIndexedLanguageId();
	}

	/**
	 * Returns the label of this object field.
	 *
	 * @return the label of this object field
	 */
	@Override
	public String getLabel() {
		return model.getLabel();
	}

	/**
	 * Returns the localized label of this object field in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized label of this object field
	 */
	@Override
	public String getLabel(java.util.Locale locale) {
		return model.getLabel(locale);
	}

	/**
	 * Returns the localized label of this object field in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this object field. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getLabel(java.util.Locale locale, boolean useDefault) {
		return model.getLabel(locale, useDefault);
	}

	/**
	 * Returns the localized label of this object field in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized label of this object field
	 */
	@Override
	public String getLabel(String languageId) {
		return model.getLabel(languageId);
	}

	/**
	 * Returns the localized label of this object field in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this object field
	 */
	@Override
	public String getLabel(String languageId, boolean useDefault) {
		return model.getLabel(languageId, useDefault);
	}

	@Override
	public String getLabelCurrentLanguageId() {
		return model.getLabelCurrentLanguageId();
	}

	@Override
	public String getLabelCurrentValue() {
		return model.getLabelCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized labels of this object field.
	 *
	 * @return the locales and localized labels of this object field
	 */
	@Override
	public Map<java.util.Locale, String> getLabelMap() {
		return model.getLabelMap();
	}

	/**
	 * Returns the list type definition ID of this object field.
	 *
	 * @return the list type definition ID of this object field
	 */
	@Override
	public long getListTypeDefinitionId() {
		return model.getListTypeDefinitionId();
	}

	/**
	 * Returns the modified date of this object field.
	 *
	 * @return the modified date of this object field
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object field.
	 *
	 * @return the mvcc version of this object field
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this object field.
	 *
	 * @return the name of this object field
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the object definition ID of this object field.
	 *
	 * @return the object definition ID of this object field
	 */
	@Override
	public long getObjectDefinitionId() {
		return model.getObjectDefinitionId();
	}

	/**
	 * Returns the object field ID of this object field.
	 *
	 * @return the object field ID of this object field
	 */
	@Override
	public long getObjectFieldId() {
		return model.getObjectFieldId();
	}

	@Override
	public java.util.List<ObjectFieldSetting> getObjectFieldSettings() {
		return model.getObjectFieldSettings();
	}

	/**
	 * Returns the primary key of this object field.
	 *
	 * @return the primary key of this object field
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the relationship type of this object field.
	 *
	 * @return the relationship type of this object field
	 */
	@Override
	public String getRelationshipType() {
		return model.getRelationshipType();
	}

	/**
	 * Returns the required of this object field.
	 *
	 * @return the required of this object field
	 */
	@Override
	public boolean getRequired() {
		return model.getRequired();
	}

	/**
	 * Returns the user ID of this object field.
	 *
	 * @return the user ID of this object field
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object field.
	 *
	 * @return the user name of this object field
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object field.
	 *
	 * @return the user uuid of this object field
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object field.
	 *
	 * @return the uuid of this object field
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this object field is indexed.
	 *
	 * @return <code>true</code> if this object field is indexed; <code>false</code> otherwise
	 */
	@Override
	public boolean isIndexed() {
		return model.isIndexed();
	}

	/**
	 * Returns <code>true</code> if this object field is indexed as keyword.
	 *
	 * @return <code>true</code> if this object field is indexed as keyword; <code>false</code> otherwise
	 */
	@Override
	public boolean isIndexedAsKeyword() {
		return model.isIndexedAsKeyword();
	}

	/**
	 * Returns <code>true</code> if this object field is required.
	 *
	 * @return <code>true</code> if this object field is required; <code>false</code> otherwise
	 */
	@Override
	public boolean isRequired() {
		return model.isRequired();
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
	 * Sets the business type of this object field.
	 *
	 * @param businessType the business type of this object field
	 */
	@Override
	public void setBusinessType(String businessType) {
		model.setBusinessType(businessType);
	}

	/**
	 * Sets the company ID of this object field.
	 *
	 * @param companyId the company ID of this object field
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object field.
	 *
	 * @param createDate the create date of this object field
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the db column name of this object field.
	 *
	 * @param dbColumnName the db column name of this object field
	 */
	@Override
	public void setDBColumnName(String dbColumnName) {
		model.setDBColumnName(dbColumnName);
	}

	/**
	 * Sets the db table name of this object field.
	 *
	 * @param dbTableName the db table name of this object field
	 */
	@Override
	public void setDBTableName(String dbTableName) {
		model.setDBTableName(dbTableName);
	}

	/**
	 * Sets the db type of this object field.
	 *
	 * @param dbType the db type of this object field
	 */
	@Override
	public void setDBType(String dbType) {
		model.setDBType(dbType);
	}

	/**
	 * Sets whether this object field is indexed.
	 *
	 * @param indexed the indexed of this object field
	 */
	@Override
	public void setIndexed(boolean indexed) {
		model.setIndexed(indexed);
	}

	/**
	 * Sets whether this object field is indexed as keyword.
	 *
	 * @param indexedAsKeyword the indexed as keyword of this object field
	 */
	@Override
	public void setIndexedAsKeyword(boolean indexedAsKeyword) {
		model.setIndexedAsKeyword(indexedAsKeyword);
	}

	/**
	 * Sets the indexed language ID of this object field.
	 *
	 * @param indexedLanguageId the indexed language ID of this object field
	 */
	@Override
	public void setIndexedLanguageId(String indexedLanguageId) {
		model.setIndexedLanguageId(indexedLanguageId);
	}

	/**
	 * Sets the label of this object field.
	 *
	 * @param label the label of this object field
	 */
	@Override
	public void setLabel(String label) {
		model.setLabel(label);
	}

	/**
	 * Sets the localized label of this object field in the language.
	 *
	 * @param label the localized label of this object field
	 * @param locale the locale of the language
	 */
	@Override
	public void setLabel(String label, java.util.Locale locale) {
		model.setLabel(label, locale);
	}

	/**
	 * Sets the localized label of this object field in the language, and sets the default locale.
	 *
	 * @param label the localized label of this object field
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabel(
		String label, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setLabel(label, locale, defaultLocale);
	}

	@Override
	public void setLabelCurrentLanguageId(String languageId) {
		model.setLabelCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized labels of this object field from the map of locales and localized labels.
	 *
	 * @param labelMap the locales and localized labels of this object field
	 */
	@Override
	public void setLabelMap(Map<java.util.Locale, String> labelMap) {
		model.setLabelMap(labelMap);
	}

	/**
	 * Sets the localized labels of this object field from the map of locales and localized labels, and sets the default locale.
	 *
	 * @param labelMap the locales and localized labels of this object field
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabelMap(
		Map<java.util.Locale, String> labelMap,
		java.util.Locale defaultLocale) {

		model.setLabelMap(labelMap, defaultLocale);
	}

	/**
	 * Sets the list type definition ID of this object field.
	 *
	 * @param listTypeDefinitionId the list type definition ID of this object field
	 */
	@Override
	public void setListTypeDefinitionId(long listTypeDefinitionId) {
		model.setListTypeDefinitionId(listTypeDefinitionId);
	}

	/**
	 * Sets the modified date of this object field.
	 *
	 * @param modifiedDate the modified date of this object field
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object field.
	 *
	 * @param mvccVersion the mvcc version of this object field
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this object field.
	 *
	 * @param name the name of this object field
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the object definition ID of this object field.
	 *
	 * @param objectDefinitionId the object definition ID of this object field
	 */
	@Override
	public void setObjectDefinitionId(long objectDefinitionId) {
		model.setObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Sets the object field ID of this object field.
	 *
	 * @param objectFieldId the object field ID of this object field
	 */
	@Override
	public void setObjectFieldId(long objectFieldId) {
		model.setObjectFieldId(objectFieldId);
	}

	@Override
	public void setObjectFieldSettings(
		java.util.List<ObjectFieldSetting> objectFieldSettings) {

		model.setObjectFieldSettings(objectFieldSettings);
	}

	/**
	 * Sets the primary key of this object field.
	 *
	 * @param primaryKey the primary key of this object field
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the relationship type of this object field.
	 *
	 * @param relationshipType the relationship type of this object field
	 */
	@Override
	public void setRelationshipType(String relationshipType) {
		model.setRelationshipType(relationshipType);
	}

	/**
	 * Sets whether this object field is required.
	 *
	 * @param required the required of this object field
	 */
	@Override
	public void setRequired(boolean required) {
		model.setRequired(required);
	}

	/**
	 * Sets the user ID of this object field.
	 *
	 * @param userId the user ID of this object field
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object field.
	 *
	 * @param userName the user name of this object field
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object field.
	 *
	 * @param userUuid the user uuid of this object field
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object field.
	 *
	 * @param uuid the uuid of this object field
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ObjectFieldWrapper wrap(ObjectField objectField) {
		return new ObjectFieldWrapper(objectField);
	}

}
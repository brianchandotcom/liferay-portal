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

package com.liferay.mobile.device.rules.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MDRAction}.
 * </p>
 *
 * @author Edward C. Han
 * @see MDRAction
 * @generated
 */
@ProviderType
public class MDRActionWrapper implements MDRAction, ModelWrapper<MDRAction> {
	public MDRActionWrapper(MDRAction mdrAction) {
		_mdrAction = mdrAction;
	}

	@Override
	public Class<?> getModelClass() {
		return MDRAction.class;
	}

	@Override
	public String getModelClassName() {
		return MDRAction.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("actionId", getActionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("ruleGroupInstanceId", getRuleGroupInstanceId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long actionId = (Long)attributes.get("actionId");

		if (actionId != null) {
			setActionId(actionId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long ruleGroupInstanceId = (Long)attributes.get("ruleGroupInstanceId");

		if (ruleGroupInstanceId != null) {
			setRuleGroupInstanceId(ruleGroupInstanceId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new MDRActionWrapper((MDRAction)_mdrAction.clone());
	}

	@Override
	public int compareTo(
		com.liferay.mobile.device.rules.model.MDRAction mdrAction) {
		return _mdrAction.compareTo(mdrAction);
	}

	/**
	* Returns the action ID of this m d r action.
	*
	* @return the action ID of this m d r action
	*/
	@Override
	public long getActionId() {
		return _mdrAction.getActionId();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _mdrAction.getAvailableLanguageIds();
	}

	/**
	* Returns the fully qualified class name of this m d r action.
	*
	* @return the fully qualified class name of this m d r action
	*/
	@Override
	public java.lang.String getClassName() {
		return _mdrAction.getClassName();
	}

	/**
	* Returns the class name ID of this m d r action.
	*
	* @return the class name ID of this m d r action
	*/
	@Override
	public long getClassNameId() {
		return _mdrAction.getClassNameId();
	}

	/**
	* Returns the class p k of this m d r action.
	*
	* @return the class p k of this m d r action
	*/
	@Override
	public long getClassPK() {
		return _mdrAction.getClassPK();
	}

	/**
	* Returns the company ID of this m d r action.
	*
	* @return the company ID of this m d r action
	*/
	@Override
	public long getCompanyId() {
		return _mdrAction.getCompanyId();
	}

	/**
	* Returns the create date of this m d r action.
	*
	* @return the create date of this m d r action
	*/
	@Override
	public Date getCreateDate() {
		return _mdrAction.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _mdrAction.getDefaultLanguageId();
	}

	/**
	* Returns the description of this m d r action.
	*
	* @return the description of this m d r action
	*/
	@Override
	public java.lang.String getDescription() {
		return _mdrAction.getDescription();
	}

	/**
	* Returns the localized description of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this m d r action
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _mdrAction.getDescription(languageId);
	}

	/**
	* Returns the localized description of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this m d r action
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _mdrAction.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this m d r action
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _mdrAction.getDescription(locale);
	}

	/**
	* Returns the localized description of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this m d r action. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _mdrAction.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _mdrAction.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _mdrAction.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this m d r action.
	*
	* @return the locales and localized descriptions of this m d r action
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _mdrAction.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mdrAction.getExpandoBridge();
	}

	/**
	* Returns the group ID of this m d r action.
	*
	* @return the group ID of this m d r action
	*/
	@Override
	public long getGroupId() {
		return _mdrAction.getGroupId();
	}

	/**
	* Returns the last publish date of this m d r action.
	*
	* @return the last publish date of this m d r action
	*/
	@Override
	public Date getLastPublishDate() {
		return _mdrAction.getLastPublishDate();
	}

	/**
	* Returns the modified date of this m d r action.
	*
	* @return the modified date of this m d r action
	*/
	@Override
	public Date getModifiedDate() {
		return _mdrAction.getModifiedDate();
	}

	/**
	* Returns the name of this m d r action.
	*
	* @return the name of this m d r action
	*/
	@Override
	public java.lang.String getName() {
		return _mdrAction.getName();
	}

	/**
	* Returns the localized name of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this m d r action
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _mdrAction.getName(languageId);
	}

	/**
	* Returns the localized name of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this m d r action
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _mdrAction.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this m d r action
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _mdrAction.getName(locale);
	}

	/**
	* Returns the localized name of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this m d r action. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _mdrAction.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _mdrAction.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _mdrAction.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this m d r action.
	*
	* @return the locales and localized names of this m d r action
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _mdrAction.getNameMap();
	}

	/**
	* Returns the primary key of this m d r action.
	*
	* @return the primary key of this m d r action
	*/
	@Override
	public long getPrimaryKey() {
		return _mdrAction.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mdrAction.getPrimaryKeyObj();
	}

	/**
	* Returns the rule group instance ID of this m d r action.
	*
	* @return the rule group instance ID of this m d r action
	*/
	@Override
	public long getRuleGroupInstanceId() {
		return _mdrAction.getRuleGroupInstanceId();
	}

	/**
	* Returns the type of this m d r action.
	*
	* @return the type of this m d r action
	*/
	@Override
	public java.lang.String getType() {
		return _mdrAction.getType();
	}

	/**
	* Returns the type settings of this m d r action.
	*
	* @return the type settings of this m d r action
	*/
	@Override
	public java.lang.String getTypeSettings() {
		return _mdrAction.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _mdrAction.getTypeSettingsProperties();
	}

	/**
	* Returns the user ID of this m d r action.
	*
	* @return the user ID of this m d r action
	*/
	@Override
	public long getUserId() {
		return _mdrAction.getUserId();
	}

	/**
	* Returns the user name of this m d r action.
	*
	* @return the user name of this m d r action
	*/
	@Override
	public java.lang.String getUserName() {
		return _mdrAction.getUserName();
	}

	/**
	* Returns the user uuid of this m d r action.
	*
	* @return the user uuid of this m d r action
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _mdrAction.getUserUuid();
	}

	/**
	* Returns the uuid of this m d r action.
	*
	* @return the uuid of this m d r action
	*/
	@Override
	public java.lang.String getUuid() {
		return _mdrAction.getUuid();
	}

	@Override
	public int hashCode() {
		return _mdrAction.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _mdrAction.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _mdrAction.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _mdrAction.isNew();
	}

	@Override
	public void persist() {
		_mdrAction.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_mdrAction.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_mdrAction.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the action ID of this m d r action.
	*
	* @param actionId the action ID of this m d r action
	*/
	@Override
	public void setActionId(long actionId) {
		_mdrAction.setActionId(actionId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mdrAction.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_mdrAction.setClassName(className);
	}

	/**
	* Sets the class name ID of this m d r action.
	*
	* @param classNameId the class name ID of this m d r action
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_mdrAction.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this m d r action.
	*
	* @param classPK the class p k of this m d r action
	*/
	@Override
	public void setClassPK(long classPK) {
		_mdrAction.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this m d r action.
	*
	* @param companyId the company ID of this m d r action
	*/
	@Override
	public void setCompanyId(long companyId) {
		_mdrAction.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this m d r action.
	*
	* @param createDate the create date of this m d r action
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_mdrAction.setCreateDate(createDate);
	}

	/**
	* Sets the description of this m d r action.
	*
	* @param description the description of this m d r action
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_mdrAction.setDescription(description);
	}

	/**
	* Sets the localized description of this m d r action in the language.
	*
	* @param description the localized description of this m d r action
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_mdrAction.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this m d r action in the language, and sets the default locale.
	*
	* @param description the localized description of this m d r action
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_mdrAction.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_mdrAction.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this m d r action from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this m d r action
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_mdrAction.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this m d r action from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this m d r action
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_mdrAction.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_mdrAction.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_mdrAction.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mdrAction.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this m d r action.
	*
	* @param groupId the group ID of this m d r action
	*/
	@Override
	public void setGroupId(long groupId) {
		_mdrAction.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this m d r action.
	*
	* @param lastPublishDate the last publish date of this m d r action
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_mdrAction.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this m d r action.
	*
	* @param modifiedDate the modified date of this m d r action
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_mdrAction.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this m d r action.
	*
	* @param name the name of this m d r action
	*/
	@Override
	public void setName(java.lang.String name) {
		_mdrAction.setName(name);
	}

	/**
	* Sets the localized name of this m d r action in the language.
	*
	* @param name the localized name of this m d r action
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_mdrAction.setName(name, locale);
	}

	/**
	* Sets the localized name of this m d r action in the language, and sets the default locale.
	*
	* @param name the localized name of this m d r action
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_mdrAction.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_mdrAction.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this m d r action from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this m d r action
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_mdrAction.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this m d r action from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this m d r action
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_mdrAction.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_mdrAction.setNew(n);
	}

	/**
	* Sets the primary key of this m d r action.
	*
	* @param primaryKey the primary key of this m d r action
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mdrAction.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mdrAction.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the rule group instance ID of this m d r action.
	*
	* @param ruleGroupInstanceId the rule group instance ID of this m d r action
	*/
	@Override
	public void setRuleGroupInstanceId(long ruleGroupInstanceId) {
		_mdrAction.setRuleGroupInstanceId(ruleGroupInstanceId);
	}

	/**
	* Sets the type of this m d r action.
	*
	* @param type the type of this m d r action
	*/
	@Override
	public void setType(java.lang.String type) {
		_mdrAction.setType(type);
	}

	/**
	* Sets the type settings of this m d r action.
	*
	* @param typeSettings the type settings of this m d r action
	*/
	@Override
	public void setTypeSettings(java.lang.String typeSettings) {
		_mdrAction.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_mdrAction.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	* Sets the user ID of this m d r action.
	*
	* @param userId the user ID of this m d r action
	*/
	@Override
	public void setUserId(long userId) {
		_mdrAction.setUserId(userId);
	}

	/**
	* Sets the user name of this m d r action.
	*
	* @param userName the user name of this m d r action
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_mdrAction.setUserName(userName);
	}

	/**
	* Sets the user uuid of this m d r action.
	*
	* @param userUuid the user uuid of this m d r action
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_mdrAction.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this m d r action.
	*
	* @param uuid the uuid of this m d r action
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_mdrAction.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.mobile.device.rules.model.MDRAction> toCacheModel() {
		return _mdrAction.toCacheModel();
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRAction toEscapedModel() {
		return new MDRActionWrapper(_mdrAction.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mdrAction.toString();
	}

	@Override
	public com.liferay.mobile.device.rules.model.MDRAction toUnescapedModel() {
		return new MDRActionWrapper(_mdrAction.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _mdrAction.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MDRActionWrapper)) {
			return false;
		}

		MDRActionWrapper mdrActionWrapper = (MDRActionWrapper)obj;

		if (Validator.equals(_mdrAction, mdrActionWrapper._mdrAction)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _mdrAction.getStagedModelType();
	}

	@Override
	public MDRAction getWrappedModel() {
		return _mdrAction;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _mdrAction.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _mdrAction.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_mdrAction.resetOriginalValues();
	}

	private final MDRAction _mdrAction;
}
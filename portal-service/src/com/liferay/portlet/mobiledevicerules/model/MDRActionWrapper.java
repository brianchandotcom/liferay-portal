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

package com.liferay.portlet.mobiledevicerules.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

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

	/**
	* Sets the primary key of this m d r action.
	*
	* @param primaryKey the primary key of this m d r action
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mdrAction.setPrimaryKey(primaryKey);
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

	/**
	* Sets the uuid of this m d r action.
	*
	* @param uuid the uuid of this m d r action
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_mdrAction.setUuid(uuid);
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

	/**
	* Sets the action ID of this m d r action.
	*
	* @param actionId the action ID of this m d r action
	*/
	@Override
	public void setActionId(long actionId) {
		_mdrAction.setActionId(actionId);
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
	* Sets the group ID of this m d r action.
	*
	* @param groupId the group ID of this m d r action
	*/
	@Override
	public void setGroupId(long groupId) {
		_mdrAction.setGroupId(groupId);
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
	* Sets the company ID of this m d r action.
	*
	* @param companyId the company ID of this m d r action
	*/
	@Override
	public void setCompanyId(long companyId) {
		_mdrAction.setCompanyId(companyId);
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
	* Sets the user ID of this m d r action.
	*
	* @param userId the user ID of this m d r action
	*/
	@Override
	public void setUserId(long userId) {
		_mdrAction.setUserId(userId);
	}

	/**
	* Returns the user uuid of this m d r action.
	*
	* @return the user uuid of this m d r action
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mdrAction.getUserUuid();
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
	* Returns the user name of this m d r action.
	*
	* @return the user name of this m d r action
	*/
	@Override
	public java.lang.String getUserName() {
		return _mdrAction.getUserName();
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
	* Returns the create date of this m d r action.
	*
	* @return the create date of this m d r action
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _mdrAction.getCreateDate();
	}

	/**
	* Sets the create date of this m d r action.
	*
	* @param createDate the create date of this m d r action
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_mdrAction.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this m d r action.
	*
	* @return the modified date of this m d r action
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _mdrAction.getModifiedDate();
	}

	/**
	* Sets the modified date of this m d r action.
	*
	* @param modifiedDate the modified date of this m d r action
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mdrAction.setModifiedDate(modifiedDate);
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

	@Override
	public void setClassName(java.lang.String className) {
		_mdrAction.setClassName(className);
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
	* Sets the class name ID of this m d r action.
	*
	* @param classNameId the class name ID of this m d r action
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_mdrAction.setClassNameId(classNameId);
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
	* Sets the class p k of this m d r action.
	*
	* @param classPK the class p k of this m d r action
	*/
	@Override
	public void setClassPK(long classPK) {
		_mdrAction.setClassPK(classPK);
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
	* Sets the rule group instance ID of this m d r action.
	*
	* @param ruleGroupInstanceId the rule group instance ID of this m d r action
	*/
	@Override
	public void setRuleGroupInstanceId(long ruleGroupInstanceId) {
		_mdrAction.setRuleGroupInstanceId(ruleGroupInstanceId);
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
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _mdrAction.getNameMap();
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
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_mdrAction.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this m d r action from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this m d r action
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_mdrAction.setNameMap(nameMap, defaultLocale);
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
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _mdrAction.getDescriptionMap();
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
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
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
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_mdrAction.setDescriptionMap(descriptionMap, defaultLocale);
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
	* Sets the type of this m d r action.
	*
	* @param type the type of this m d r action
	*/
	@Override
	public void setType(java.lang.String type) {
		_mdrAction.setType(type);
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
	public boolean isNew() {
		return _mdrAction.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_mdrAction.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _mdrAction.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mdrAction.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _mdrAction.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mdrAction.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mdrAction.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mdrAction.getExpandoBridge();
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

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_mdrAction.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new MDRActionWrapper((MDRAction)_mdrAction.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction) {
		return _mdrAction.compareTo(mdrAction);
	}

	@Override
	public int hashCode() {
		return _mdrAction.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.mobiledevicerules.model.MDRAction> toCacheModel() {
		return _mdrAction.toCacheModel();
	}

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction toEscapedModel() {
		return new MDRActionWrapper(_mdrAction.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRAction toUnescapedModel() {
		return new MDRActionWrapper(_mdrAction.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mdrAction.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _mdrAction.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mdrAction.persist();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _mdrAction.getTypeSettingsProperties();
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_mdrAction.setTypeSettingsProperties(typeSettingsProperties);
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

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public MDRAction getWrappedMDRAction() {
		return _mdrAction;
	}

	@Override
	public MDRAction getWrappedModel() {
		return _mdrAction;
	}

	@Override
	public void resetOriginalValues() {
		_mdrAction.resetOriginalValues();
	}

	private MDRAction _mdrAction;
}
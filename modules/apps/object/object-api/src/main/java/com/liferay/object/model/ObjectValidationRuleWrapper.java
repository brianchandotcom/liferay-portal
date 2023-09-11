/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * This class is a wrapper for {@link ObjectValidationRule}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectValidationRule
 * @generated
 */
public class ObjectValidationRuleWrapper
	extends BaseModelWrapper<ObjectValidationRule>
	implements ModelWrapper<ObjectValidationRule>, ObjectValidationRule {

	public ObjectValidationRuleWrapper(
		ObjectValidationRule objectValidationRule) {

		super(objectValidationRule);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectValidationRuleId", getObjectValidationRuleId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectDefinitionId", getObjectDefinitionId());
		attributes.put("active", isActive());
		attributes.put("engine", getEngine());
		attributes.put("errorLabel", getErrorLabel());
		attributes.put("name", getName());
		attributes.put("outputType", getOutputType());
		attributes.put("script", getScript());
		attributes.put("system", isSystem());

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

		Long objectValidationRuleId = (Long)attributes.get(
			"objectValidationRuleId");

		if (objectValidationRuleId != null) {
			setObjectValidationRuleId(objectValidationRuleId);
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

		Long objectDefinitionId = (Long)attributes.get("objectDefinitionId");

		if (objectDefinitionId != null) {
			setObjectDefinitionId(objectDefinitionId);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String engine = (String)attributes.get("engine");

		if (engine != null) {
			setEngine(engine);
		}

		String errorLabel = (String)attributes.get("errorLabel");

		if (errorLabel != null) {
			setErrorLabel(errorLabel);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String outputType = (String)attributes.get("outputType");

		if (outputType != null) {
			setOutputType(outputType);
		}

		String script = (String)attributes.get("script");

		if (script != null) {
			setScript(script);
		}

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}
	}

	@Override
	public ObjectValidationRule cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public boolean compareOutputType(String outputType) {
		return model.compareOutputType(outputType);
	}

	/**
	 * Returns the active of this object validation rule.
	 *
	 * @return the active of this object validation rule
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this object validation rule.
	 *
	 * @return the company ID of this object validation rule
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object validation rule.
	 *
	 * @return the create date of this object validation rule
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the engine of this object validation rule.
	 *
	 * @return the engine of this object validation rule
	 */
	@Override
	public String getEngine() {
		return model.getEngine();
	}

	/**
	 * Returns the error label of this object validation rule.
	 *
	 * @return the error label of this object validation rule
	 */
	@Override
	public String getErrorLabel() {
		return model.getErrorLabel();
	}

	/**
	 * Returns the localized error label of this object validation rule in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized error label of this object validation rule
	 */
	@Override
	public String getErrorLabel(java.util.Locale locale) {
		return model.getErrorLabel(locale);
	}

	/**
	 * Returns the localized error label of this object validation rule in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized error label of this object validation rule. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getErrorLabel(java.util.Locale locale, boolean useDefault) {
		return model.getErrorLabel(locale, useDefault);
	}

	/**
	 * Returns the localized error label of this object validation rule in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized error label of this object validation rule
	 */
	@Override
	public String getErrorLabel(String languageId) {
		return model.getErrorLabel(languageId);
	}

	/**
	 * Returns the localized error label of this object validation rule in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized error label of this object validation rule
	 */
	@Override
	public String getErrorLabel(String languageId, boolean useDefault) {
		return model.getErrorLabel(languageId, useDefault);
	}

	@Override
	public String getErrorLabelCurrentLanguageId() {
		return model.getErrorLabelCurrentLanguageId();
	}

	@Override
	public String getErrorLabelCurrentValue() {
		return model.getErrorLabelCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized error labels of this object validation rule.
	 *
	 * @return the locales and localized error labels of this object validation rule
	 */
	@Override
	public Map<java.util.Locale, String> getErrorLabelMap() {
		return model.getErrorLabelMap();
	}

	/**
	 * Returns the modified date of this object validation rule.
	 *
	 * @return the modified date of this object validation rule
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object validation rule.
	 *
	 * @return the mvcc version of this object validation rule
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this object validation rule.
	 *
	 * @return the name of this object validation rule
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this object validation rule in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this object validation rule
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this object validation rule in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this object validation rule. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this object validation rule in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this object validation rule
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this object validation rule in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this object validation rule
	 */
	@Override
	public String getName(String languageId, boolean useDefault) {
		return model.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return model.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return model.getNameCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized names of this object validation rule.
	 *
	 * @return the locales and localized names of this object validation rule
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the object definition ID of this object validation rule.
	 *
	 * @return the object definition ID of this object validation rule
	 */
	@Override
	public long getObjectDefinitionId() {
		return model.getObjectDefinitionId();
	}

	/**
	 * Returns the object validation rule ID of this object validation rule.
	 *
	 * @return the object validation rule ID of this object validation rule
	 */
	@Override
	public long getObjectValidationRuleId() {
		return model.getObjectValidationRuleId();
	}

	@Override
	public java.util.List<ObjectValidationRuleSetting>
		getObjectValidationRuleSettings() {

		return model.getObjectValidationRuleSettings();
	}

	/**
	 * Returns the output type of this object validation rule.
	 *
	 * @return the output type of this object validation rule
	 */
	@Override
	public String getOutputType() {
		return model.getOutputType();
	}

	/**
	 * Returns the primary key of this object validation rule.
	 *
	 * @return the primary key of this object validation rule
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the script of this object validation rule.
	 *
	 * @return the script of this object validation rule
	 */
	@Override
	public String getScript() {
		return model.getScript();
	}

	/**
	 * Returns the system of this object validation rule.
	 *
	 * @return the system of this object validation rule
	 */
	@Override
	public boolean getSystem() {
		return model.getSystem();
	}

	/**
	 * Returns the user ID of this object validation rule.
	 *
	 * @return the user ID of this object validation rule
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object validation rule.
	 *
	 * @return the user name of this object validation rule
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object validation rule.
	 *
	 * @return the user uuid of this object validation rule
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object validation rule.
	 *
	 * @return the uuid of this object validation rule
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this object validation rule is active.
	 *
	 * @return <code>true</code> if this object validation rule is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this object validation rule is system.
	 *
	 * @return <code>true</code> if this object validation rule is system; <code>false</code> otherwise
	 */
	@Override
	public boolean isSystem() {
		return model.isSystem();
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
	 * Sets whether this object validation rule is active.
	 *
	 * @param active the active of this object validation rule
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this object validation rule.
	 *
	 * @param companyId the company ID of this object validation rule
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object validation rule.
	 *
	 * @param createDate the create date of this object validation rule
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the engine of this object validation rule.
	 *
	 * @param engine the engine of this object validation rule
	 */
	@Override
	public void setEngine(String engine) {
		model.setEngine(engine);
	}

	/**
	 * Sets the error label of this object validation rule.
	 *
	 * @param errorLabel the error label of this object validation rule
	 */
	@Override
	public void setErrorLabel(String errorLabel) {
		model.setErrorLabel(errorLabel);
	}

	/**
	 * Sets the localized error label of this object validation rule in the language.
	 *
	 * @param errorLabel the localized error label of this object validation rule
	 * @param locale the locale of the language
	 */
	@Override
	public void setErrorLabel(String errorLabel, java.util.Locale locale) {
		model.setErrorLabel(errorLabel, locale);
	}

	/**
	 * Sets the localized error label of this object validation rule in the language, and sets the default locale.
	 *
	 * @param errorLabel the localized error label of this object validation rule
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setErrorLabel(
		String errorLabel, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setErrorLabel(errorLabel, locale, defaultLocale);
	}

	@Override
	public void setErrorLabelCurrentLanguageId(String languageId) {
		model.setErrorLabelCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized error labels of this object validation rule from the map of locales and localized error labels.
	 *
	 * @param errorLabelMap the locales and localized error labels of this object validation rule
	 */
	@Override
	public void setErrorLabelMap(Map<java.util.Locale, String> errorLabelMap) {
		model.setErrorLabelMap(errorLabelMap);
	}

	/**
	 * Sets the localized error labels of this object validation rule from the map of locales and localized error labels, and sets the default locale.
	 *
	 * @param errorLabelMap the locales and localized error labels of this object validation rule
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setErrorLabelMap(
		Map<java.util.Locale, String> errorLabelMap,
		java.util.Locale defaultLocale) {

		model.setErrorLabelMap(errorLabelMap, defaultLocale);
	}

	/**
	 * Sets the modified date of this object validation rule.
	 *
	 * @param modifiedDate the modified date of this object validation rule
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object validation rule.
	 *
	 * @param mvccVersion the mvcc version of this object validation rule
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this object validation rule.
	 *
	 * @param name the name of this object validation rule
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this object validation rule in the language.
	 *
	 * @param name the localized name of this object validation rule
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this object validation rule in the language, and sets the default locale.
	 *
	 * @param name the localized name of this object validation rule
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setName(
		String name, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		model.setNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized names of this object validation rule from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this object validation rule
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this object validation rule from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this object validation rule
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the object definition ID of this object validation rule.
	 *
	 * @param objectDefinitionId the object definition ID of this object validation rule
	 */
	@Override
	public void setObjectDefinitionId(long objectDefinitionId) {
		model.setObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Sets the object validation rule ID of this object validation rule.
	 *
	 * @param objectValidationRuleId the object validation rule ID of this object validation rule
	 */
	@Override
	public void setObjectValidationRuleId(long objectValidationRuleId) {
		model.setObjectValidationRuleId(objectValidationRuleId);
	}

	@Override
	public void setObjectValidationRuleSettings(
		java.util.List<ObjectValidationRuleSetting>
			objectValidationRuleSettings) {

		model.setObjectValidationRuleSettings(objectValidationRuleSettings);
	}

	/**
	 * Sets the output type of this object validation rule.
	 *
	 * @param outputType the output type of this object validation rule
	 */
	@Override
	public void setOutputType(String outputType) {
		model.setOutputType(outputType);
	}

	/**
	 * Sets the primary key of this object validation rule.
	 *
	 * @param primaryKey the primary key of this object validation rule
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the script of this object validation rule.
	 *
	 * @param script the script of this object validation rule
	 */
	@Override
	public void setScript(String script) {
		model.setScript(script);
	}

	/**
	 * Sets whether this object validation rule is system.
	 *
	 * @param system the system of this object validation rule
	 */
	@Override
	public void setSystem(boolean system) {
		model.setSystem(system);
	}

	/**
	 * Sets the user ID of this object validation rule.
	 *
	 * @param userId the user ID of this object validation rule
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object validation rule.
	 *
	 * @param userName the user name of this object validation rule
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object validation rule.
	 *
	 * @param userUuid the user uuid of this object validation rule
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object validation rule.
	 *
	 * @param uuid the uuid of this object validation rule
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ObjectValidationRuleWrapper wrap(
		ObjectValidationRule objectValidationRule) {

		return new ObjectValidationRuleWrapper(objectValidationRule);
	}

}
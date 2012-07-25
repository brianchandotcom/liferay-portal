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

package com.liferay.portlet.dynamicdatamapping.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMTemplate}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMTemplate
 * @generated
 */
public class DDMTemplateWrapper implements DDMTemplate,
	ModelWrapper<DDMTemplate> {
	public DDMTemplateWrapper(DDMTemplate ddmTemplate) {
		_ddmTemplate = ddmTemplate;
	}

	public Class<?> getModelClass() {
		return DDMTemplate.class;
	}

	public String getModelClassName() {
		return DDMTemplate.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("templateId", getTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("templateKey", getTemplateKey());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("mode", getMode());
		attributes.put("language", getLanguage());
		attributes.put("script", getScript());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long templateId = (Long)attributes.get("templateId");

		if (templateId != null) {
			setTemplateId(templateId);
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

		String templateKey = (String)attributes.get("templateKey");

		if (templateKey != null) {
			setTemplateKey(templateKey);
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

		String mode = (String)attributes.get("mode");

		if (mode != null) {
			setMode(mode);
		}

		String language = (String)attributes.get("language");

		if (language != null) {
			setLanguage(language);
		}

		String script = (String)attributes.get("script");

		if (script != null) {
			setScript(script);
		}
	}

	/**
	* Returns the primary key of this d d m template.
	*
	* @return the primary key of this d d m template
	*/
	public long getPrimaryKey() {
		return _ddmTemplate.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m template.
	*
	* @param primaryKey the primary key of this d d m template
	*/
	public void setPrimaryKey(long primaryKey) {
		_ddmTemplate.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this d d m template.
	*
	* @return the uuid of this d d m template
	*/
	public java.lang.String getUuid() {
		return _ddmTemplate.getUuid();
	}

	/**
	* Sets the uuid of this d d m template.
	*
	* @param uuid the uuid of this d d m template
	*/
	public void setUuid(java.lang.String uuid) {
		_ddmTemplate.setUuid(uuid);
	}

	/**
	* Returns the template ID of this d d m template.
	*
	* @return the template ID of this d d m template
	*/
	public long getTemplateId() {
		return _ddmTemplate.getTemplateId();
	}

	/**
	* Sets the template ID of this d d m template.
	*
	* @param templateId the template ID of this d d m template
	*/
	public void setTemplateId(long templateId) {
		_ddmTemplate.setTemplateId(templateId);
	}

	/**
	* Returns the group ID of this d d m template.
	*
	* @return the group ID of this d d m template
	*/
	public long getGroupId() {
		return _ddmTemplate.getGroupId();
	}

	/**
	* Sets the group ID of this d d m template.
	*
	* @param groupId the group ID of this d d m template
	*/
	public void setGroupId(long groupId) {
		_ddmTemplate.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this d d m template.
	*
	* @return the company ID of this d d m template
	*/
	public long getCompanyId() {
		return _ddmTemplate.getCompanyId();
	}

	/**
	* Sets the company ID of this d d m template.
	*
	* @param companyId the company ID of this d d m template
	*/
	public void setCompanyId(long companyId) {
		_ddmTemplate.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this d d m template.
	*
	* @return the user ID of this d d m template
	*/
	public long getUserId() {
		return _ddmTemplate.getUserId();
	}

	/**
	* Sets the user ID of this d d m template.
	*
	* @param userId the user ID of this d d m template
	*/
	public void setUserId(long userId) {
		_ddmTemplate.setUserId(userId);
	}

	/**
	* Returns the user uuid of this d d m template.
	*
	* @return the user uuid of this d d m template
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmTemplate.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d m template.
	*
	* @param userUuid the user uuid of this d d m template
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddmTemplate.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this d d m template.
	*
	* @return the user name of this d d m template
	*/
	public java.lang.String getUserName() {
		return _ddmTemplate.getUserName();
	}

	/**
	* Sets the user name of this d d m template.
	*
	* @param userName the user name of this d d m template
	*/
	public void setUserName(java.lang.String userName) {
		_ddmTemplate.setUserName(userName);
	}

	/**
	* Returns the create date of this d d m template.
	*
	* @return the create date of this d d m template
	*/
	public java.util.Date getCreateDate() {
		return _ddmTemplate.getCreateDate();
	}

	/**
	* Sets the create date of this d d m template.
	*
	* @param createDate the create date of this d d m template
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddmTemplate.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this d d m template.
	*
	* @return the modified date of this d d m template
	*/
	public java.util.Date getModifiedDate() {
		return _ddmTemplate.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d m template.
	*
	* @param modifiedDate the modified date of this d d m template
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddmTemplate.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this d d m template.
	*
	* @return the fully qualified class name of this d d m template
	*/
	public java.lang.String getClassName() {
		return _ddmTemplate.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_ddmTemplate.setClassName(className);
	}

	/**
	* Returns the class name ID of this d d m template.
	*
	* @return the class name ID of this d d m template
	*/
	public long getClassNameId() {
		return _ddmTemplate.getClassNameId();
	}

	/**
	* Sets the class name ID of this d d m template.
	*
	* @param classNameId the class name ID of this d d m template
	*/
	public void setClassNameId(long classNameId) {
		_ddmTemplate.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this d d m template.
	*
	* @return the class p k of this d d m template
	*/
	public long getClassPK() {
		return _ddmTemplate.getClassPK();
	}

	/**
	* Sets the class p k of this d d m template.
	*
	* @param classPK the class p k of this d d m template
	*/
	public void setClassPK(long classPK) {
		_ddmTemplate.setClassPK(classPK);
	}

	/**
	* Returns the template key of this d d m template.
	*
	* @return the template key of this d d m template
	*/
	public java.lang.String getTemplateKey() {
		return _ddmTemplate.getTemplateKey();
	}

	/**
	* Sets the template key of this d d m template.
	*
	* @param templateKey the template key of this d d m template
	*/
	public void setTemplateKey(java.lang.String templateKey) {
		_ddmTemplate.setTemplateKey(templateKey);
	}

	/**
	* Returns the name of this d d m template.
	*
	* @return the name of this d d m template
	*/
	public java.lang.String getName() {
		return _ddmTemplate.getName();
	}

	/**
	* Returns the localized name of this d d m template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this d d m template
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmTemplate.getName(locale);
	}

	/**
	* Returns the localized name of this d d m template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmTemplate.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this d d m template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this d d m template
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmTemplate.getName(languageId);
	}

	/**
	* Returns the localized name of this d d m template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m template
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmTemplate.getName(languageId, useDefault);
	}

	public java.lang.String getNameCurrentLanguageId() {
		return _ddmTemplate.getNameCurrentLanguageId();
	}

	public java.lang.String getNameCurrentValue() {
		return _ddmTemplate.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this d d m template.
	*
	* @return the locales and localized names of this d d m template
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmTemplate.getNameMap();
	}

	/**
	* Sets the name of this d d m template.
	*
	* @param name the name of this d d m template
	*/
	public void setName(java.lang.String name) {
		_ddmTemplate.setName(name);
	}

	/**
	* Sets the localized name of this d d m template in the language.
	*
	* @param name the localized name of this d d m template
	* @param locale the locale of the language
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmTemplate.setName(name, locale);
	}

	/**
	* Sets the localized name of this d d m template in the language, and sets the default locale.
	*
	* @param name the localized name of this d d m template
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmTemplate.setName(name, locale, defaultLocale);
	}

	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddmTemplate.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this d d m template from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d m template
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmTemplate.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d m template from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d m template
	* @param defaultLocale the default locale
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmTemplate.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this d d m template.
	*
	* @return the description of this d d m template
	*/
	public java.lang.String getDescription() {
		return _ddmTemplate.getDescription();
	}

	/**
	* Returns the localized description of this d d m template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this d d m template
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _ddmTemplate.getDescription(locale);
	}

	/**
	* Returns the localized description of this d d m template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddmTemplate.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this d d m template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this d d m template
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _ddmTemplate.getDescription(languageId);
	}

	/**
	* Returns the localized description of this d d m template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m template
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddmTemplate.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddmTemplate.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _ddmTemplate.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this d d m template.
	*
	* @return the locales and localized descriptions of this d d m template
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddmTemplate.getDescriptionMap();
	}

	/**
	* Sets the description of this d d m template.
	*
	* @param description the description of this d d m template
	*/
	public void setDescription(java.lang.String description) {
		_ddmTemplate.setDescription(description);
	}

	/**
	* Sets the localized description of this d d m template in the language.
	*
	* @param description the localized description of this d d m template
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_ddmTemplate.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this d d m template in the language, and sets the default locale.
	*
	* @param description the localized description of this d d m template
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddmTemplate.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddmTemplate.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this d d m template from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m template
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddmTemplate.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this d d m template from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m template
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmTemplate.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the type of this d d m template.
	*
	* @return the type of this d d m template
	*/
	public java.lang.String getType() {
		return _ddmTemplate.getType();
	}

	/**
	* Sets the type of this d d m template.
	*
	* @param type the type of this d d m template
	*/
	public void setType(java.lang.String type) {
		_ddmTemplate.setType(type);
	}

	/**
	* Returns the mode of this d d m template.
	*
	* @return the mode of this d d m template
	*/
	public java.lang.String getMode() {
		return _ddmTemplate.getMode();
	}

	/**
	* Sets the mode of this d d m template.
	*
	* @param mode the mode of this d d m template
	*/
	public void setMode(java.lang.String mode) {
		_ddmTemplate.setMode(mode);
	}

	/**
	* Returns the language of this d d m template.
	*
	* @return the language of this d d m template
	*/
	public java.lang.String getLanguage() {
		return _ddmTemplate.getLanguage();
	}

	/**
	* Sets the language of this d d m template.
	*
	* @param language the language of this d d m template
	*/
	public void setLanguage(java.lang.String language) {
		_ddmTemplate.setLanguage(language);
	}

	/**
	* Returns the script of this d d m template.
	*
	* @return the script of this d d m template
	*/
	public java.lang.String getScript() {
		return _ddmTemplate.getScript();
	}

	/**
	* Sets the script of this d d m template.
	*
	* @param script the script of this d d m template
	*/
	public void setScript(java.lang.String script) {
		_ddmTemplate.setScript(script);
	}

	public boolean isNew() {
		return _ddmTemplate.isNew();
	}

	public void setNew(boolean n) {
		_ddmTemplate.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmTemplate.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmTemplate.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmTemplate.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmTemplate.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmTemplate.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new DDMTemplateWrapper((DDMTemplate)_ddmTemplate.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate ddmTemplate) {
		return _ddmTemplate.compareTo(ddmTemplate);
	}

	@Override
	public int hashCode() {
		return _ddmTemplate.hashCode();
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddmTemplate.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> toCacheModel() {
		return _ddmTemplate.toCacheModel();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate toEscapedModel() {
		return new DDMTemplateWrapper(_ddmTemplate.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmTemplate.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmTemplate.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmTemplate.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public DDMTemplate getWrappedDDMTemplate() {
		return _ddmTemplate;
	}

	public DDMTemplate getWrappedModel() {
		return _ddmTemplate;
	}

	public void resetOriginalValues() {
		_ddmTemplate.resetOriginalValues();
	}

	private DDMTemplate _ddmTemplate;
}
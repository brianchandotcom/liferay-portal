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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMTemplateVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersion
 * @generated
 */
@ProviderType
public class DDMTemplateVersionWrapper implements DDMTemplateVersion,
	ModelWrapper<DDMTemplateVersion> {
	public DDMTemplateVersionWrapper(DDMTemplateVersion ddmTemplateVersion) {
		_ddmTemplateVersion = ddmTemplateVersion;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMTemplateVersion.class;
	}

	@Override
	public String getModelClassName() {
		return DDMTemplateVersion.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("templateVersionId", getTemplateVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("templateId", getTemplateId());
		attributes.put("version", getVersion());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("language", getLanguage());
		attributes.put("script", getScript());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long templateVersionId = (Long)attributes.get("templateVersionId");

		if (templateVersionId != null) {
			setTemplateVersionId(templateVersionId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long templateId = (Long)attributes.get("templateId");

		if (templateId != null) {
			setTemplateId(templateId);
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

		String language = (String)attributes.get("language");

		if (language != null) {
			setLanguage(language);
		}

		String script = (String)attributes.get("script");

		if (script != null) {
			setScript(script);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDMTemplateVersionWrapper((DDMTemplateVersion)_ddmTemplateVersion.clone());
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.mapping.model.DDMTemplateVersion ddmTemplateVersion) {
		return _ddmTemplateVersion.compareTo(ddmTemplateVersion);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _ddmTemplateVersion.getAvailableLanguageIds();
	}

	/**
	* Returns the fully qualified class name of this d d m template version.
	*
	* @return the fully qualified class name of this d d m template version
	*/
	@Override
	public java.lang.String getClassName() {
		return _ddmTemplateVersion.getClassName();
	}

	/**
	* Returns the class name ID of this d d m template version.
	*
	* @return the class name ID of this d d m template version
	*/
	@Override
	public long getClassNameId() {
		return _ddmTemplateVersion.getClassNameId();
	}

	/**
	* Returns the class p k of this d d m template version.
	*
	* @return the class p k of this d d m template version
	*/
	@Override
	public long getClassPK() {
		return _ddmTemplateVersion.getClassPK();
	}

	/**
	* Returns the company ID of this d d m template version.
	*
	* @return the company ID of this d d m template version
	*/
	@Override
	public long getCompanyId() {
		return _ddmTemplateVersion.getCompanyId();
	}

	/**
	* Returns the create date of this d d m template version.
	*
	* @return the create date of this d d m template version
	*/
	@Override
	public Date getCreateDate() {
		return _ddmTemplateVersion.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _ddmTemplateVersion.getDefaultLanguageId();
	}

	/**
	* Returns the description of this d d m template version.
	*
	* @return the description of this d d m template version
	*/
	@Override
	public java.lang.String getDescription() {
		return _ddmTemplateVersion.getDescription();
	}

	/**
	* Returns the localized description of this d d m template version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this d d m template version
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _ddmTemplateVersion.getDescription(languageId);
	}

	/**
	* Returns the localized description of this d d m template version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m template version
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddmTemplateVersion.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this d d m template version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this d d m template version
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _ddmTemplateVersion.getDescription(locale);
	}

	/**
	* Returns the localized description of this d d m template version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this d d m template version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddmTemplateVersion.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddmTemplateVersion.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _ddmTemplateVersion.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this d d m template version.
	*
	* @return the locales and localized descriptions of this d d m template version
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddmTemplateVersion.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmTemplateVersion.getExpandoBridge();
	}

	/**
	* Returns the group ID of this d d m template version.
	*
	* @return the group ID of this d d m template version
	*/
	@Override
	public long getGroupId() {
		return _ddmTemplateVersion.getGroupId();
	}

	/**
	* Returns the language of this d d m template version.
	*
	* @return the language of this d d m template version
	*/
	@Override
	public java.lang.String getLanguage() {
		return _ddmTemplateVersion.getLanguage();
	}

	/**
	* Returns the name of this d d m template version.
	*
	* @return the name of this d d m template version
	*/
	@Override
	public java.lang.String getName() {
		return _ddmTemplateVersion.getName();
	}

	/**
	* Returns the localized name of this d d m template version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this d d m template version
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmTemplateVersion.getName(languageId);
	}

	/**
	* Returns the localized name of this d d m template version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m template version
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmTemplateVersion.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this d d m template version in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this d d m template version
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmTemplateVersion.getName(locale);
	}

	/**
	* Returns the localized name of this d d m template version in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m template version. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmTemplateVersion.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _ddmTemplateVersion.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _ddmTemplateVersion.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this d d m template version.
	*
	* @return the locales and localized names of this d d m template version
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmTemplateVersion.getNameMap();
	}

	/**
	* Returns the primary key of this d d m template version.
	*
	* @return the primary key of this d d m template version
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmTemplateVersion.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmTemplateVersion.getPrimaryKeyObj();
	}

	/**
	* Returns the script of this d d m template version.
	*
	* @return the script of this d d m template version
	*/
	@Override
	public java.lang.String getScript() {
		return _ddmTemplateVersion.getScript();
	}

	/**
	* Returns the status of this d d m template version.
	*
	* @return the status of this d d m template version
	*/
	@Override
	public int getStatus() {
		return _ddmTemplateVersion.getStatus();
	}

	/**
	* Returns the status by user ID of this d d m template version.
	*
	* @return the status by user ID of this d d m template version
	*/
	@Override
	public long getStatusByUserId() {
		return _ddmTemplateVersion.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this d d m template version.
	*
	* @return the status by user name of this d d m template version
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _ddmTemplateVersion.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this d d m template version.
	*
	* @return the status by user uuid of this d d m template version
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _ddmTemplateVersion.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this d d m template version.
	*
	* @return the status date of this d d m template version
	*/
	@Override
	public Date getStatusDate() {
		return _ddmTemplateVersion.getStatusDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplate getTemplate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplateVersion.getTemplate();
	}

	/**
	* Returns the template ID of this d d m template version.
	*
	* @return the template ID of this d d m template version
	*/
	@Override
	public long getTemplateId() {
		return _ddmTemplateVersion.getTemplateId();
	}

	/**
	* Returns the template version ID of this d d m template version.
	*
	* @return the template version ID of this d d m template version
	*/
	@Override
	public long getTemplateVersionId() {
		return _ddmTemplateVersion.getTemplateVersionId();
	}

	/**
	* Returns the user ID of this d d m template version.
	*
	* @return the user ID of this d d m template version
	*/
	@Override
	public long getUserId() {
		return _ddmTemplateVersion.getUserId();
	}

	/**
	* Returns the user name of this d d m template version.
	*
	* @return the user name of this d d m template version
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmTemplateVersion.getUserName();
	}

	/**
	* Returns the user uuid of this d d m template version.
	*
	* @return the user uuid of this d d m template version
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmTemplateVersion.getUserUuid();
	}

	/**
	* Returns the version of this d d m template version.
	*
	* @return the version of this d d m template version
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddmTemplateVersion.getVersion();
	}

	@Override
	public int hashCode() {
		return _ddmTemplateVersion.hashCode();
	}

	/**
	* Returns <code>true</code> if this d d m template version is approved.
	*
	* @return <code>true</code> if this d d m template version is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _ddmTemplateVersion.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmTemplateVersion.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this d d m template version is denied.
	*
	* @return <code>true</code> if this d d m template version is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _ddmTemplateVersion.isDenied();
	}

	/**
	* Returns <code>true</code> if this d d m template version is a draft.
	*
	* @return <code>true</code> if this d d m template version is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _ddmTemplateVersion.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmTemplateVersion.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this d d m template version is expired.
	*
	* @return <code>true</code> if this d d m template version is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _ddmTemplateVersion.isExpired();
	}

	/**
	* Returns <code>true</code> if this d d m template version is inactive.
	*
	* @return <code>true</code> if this d d m template version is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _ddmTemplateVersion.isInactive();
	}

	/**
	* Returns <code>true</code> if this d d m template version is incomplete.
	*
	* @return <code>true</code> if this d d m template version is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _ddmTemplateVersion.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _ddmTemplateVersion.isNew();
	}

	/**
	* Returns <code>true</code> if this d d m template version is pending.
	*
	* @return <code>true</code> if this d d m template version is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _ddmTemplateVersion.isPending();
	}

	/**
	* Returns <code>true</code> if this d d m template version is scheduled.
	*
	* @return <code>true</code> if this d d m template version is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _ddmTemplateVersion.isScheduled();
	}

	@Override
	public void persist() {
		_ddmTemplateVersion.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_ddmTemplateVersion.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddmTemplateVersion.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmTemplateVersion.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_ddmTemplateVersion.setClassName(className);
	}

	/**
	* Sets the class name ID of this d d m template version.
	*
	* @param classNameId the class name ID of this d d m template version
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ddmTemplateVersion.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this d d m template version.
	*
	* @param classPK the class p k of this d d m template version
	*/
	@Override
	public void setClassPK(long classPK) {
		_ddmTemplateVersion.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this d d m template version.
	*
	* @param companyId the company ID of this d d m template version
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmTemplateVersion.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this d d m template version.
	*
	* @param createDate the create date of this d d m template version
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmTemplateVersion.setCreateDate(createDate);
	}

	/**
	* Sets the description of this d d m template version.
	*
	* @param description the description of this d d m template version
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_ddmTemplateVersion.setDescription(description);
	}

	/**
	* Sets the localized description of this d d m template version in the language.
	*
	* @param description the localized description of this d d m template version
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_ddmTemplateVersion.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this d d m template version in the language, and sets the default locale.
	*
	* @param description the localized description of this d d m template version
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddmTemplateVersion.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddmTemplateVersion.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this d d m template version from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m template version
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddmTemplateVersion.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this d d m template version from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m template version
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmTemplateVersion.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddmTemplateVersion.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddmTemplateVersion.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmTemplateVersion.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this d d m template version.
	*
	* @param groupId the group ID of this d d m template version
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmTemplateVersion.setGroupId(groupId);
	}

	/**
	* Sets the language of this d d m template version.
	*
	* @param language the language of this d d m template version
	*/
	@Override
	public void setLanguage(java.lang.String language) {
		_ddmTemplateVersion.setLanguage(language);
	}

	/**
	* Sets the name of this d d m template version.
	*
	* @param name the name of this d d m template version
	*/
	@Override
	public void setName(java.lang.String name) {
		_ddmTemplateVersion.setName(name);
	}

	/**
	* Sets the localized name of this d d m template version in the language.
	*
	* @param name the localized name of this d d m template version
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmTemplateVersion.setName(name, locale);
	}

	/**
	* Sets the localized name of this d d m template version in the language, and sets the default locale.
	*
	* @param name the localized name of this d d m template version
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmTemplateVersion.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddmTemplateVersion.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this d d m template version from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d m template version
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmTemplateVersion.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d m template version from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d m template version
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmTemplateVersion.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmTemplateVersion.setNew(n);
	}

	/**
	* Sets the primary key of this d d m template version.
	*
	* @param primaryKey the primary key of this d d m template version
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmTemplateVersion.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmTemplateVersion.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the script of this d d m template version.
	*
	* @param script the script of this d d m template version
	*/
	@Override
	public void setScript(java.lang.String script) {
		_ddmTemplateVersion.setScript(script);
	}

	/**
	* Sets the status of this d d m template version.
	*
	* @param status the status of this d d m template version
	*/
	@Override
	public void setStatus(int status) {
		_ddmTemplateVersion.setStatus(status);
	}

	/**
	* Sets the status by user ID of this d d m template version.
	*
	* @param statusByUserId the status by user ID of this d d m template version
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_ddmTemplateVersion.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this d d m template version.
	*
	* @param statusByUserName the status by user name of this d d m template version
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_ddmTemplateVersion.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this d d m template version.
	*
	* @param statusByUserUuid the status by user uuid of this d d m template version
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_ddmTemplateVersion.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this d d m template version.
	*
	* @param statusDate the status date of this d d m template version
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_ddmTemplateVersion.setStatusDate(statusDate);
	}

	/**
	* Sets the template ID of this d d m template version.
	*
	* @param templateId the template ID of this d d m template version
	*/
	@Override
	public void setTemplateId(long templateId) {
		_ddmTemplateVersion.setTemplateId(templateId);
	}

	/**
	* Sets the template version ID of this d d m template version.
	*
	* @param templateVersionId the template version ID of this d d m template version
	*/
	@Override
	public void setTemplateVersionId(long templateVersionId) {
		_ddmTemplateVersion.setTemplateVersionId(templateVersionId);
	}

	/**
	* Sets the user ID of this d d m template version.
	*
	* @param userId the user ID of this d d m template version
	*/
	@Override
	public void setUserId(long userId) {
		_ddmTemplateVersion.setUserId(userId);
	}

	/**
	* Sets the user name of this d d m template version.
	*
	* @param userName the user name of this d d m template version
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmTemplateVersion.setUserName(userName);
	}

	/**
	* Sets the user uuid of this d d m template version.
	*
	* @param userUuid the user uuid of this d d m template version
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmTemplateVersion.setUserUuid(userUuid);
	}

	/**
	* Sets the version of this d d m template version.
	*
	* @param version the version of this d d m template version
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddmTemplateVersion.setVersion(version);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.dynamic.data.mapping.model.DDMTemplateVersion> toCacheModel() {
		return _ddmTemplateVersion.toCacheModel();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion toEscapedModel() {
		return new DDMTemplateVersionWrapper(_ddmTemplateVersion.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmTemplateVersion.toString();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion toUnescapedModel() {
		return new DDMTemplateVersionWrapper(_ddmTemplateVersion.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmTemplateVersion.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMTemplateVersionWrapper)) {
			return false;
		}

		DDMTemplateVersionWrapper ddmTemplateVersionWrapper = (DDMTemplateVersionWrapper)obj;

		if (Validator.equals(_ddmTemplateVersion,
					ddmTemplateVersionWrapper._ddmTemplateVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public DDMTemplateVersion getWrappedModel() {
		return _ddmTemplateVersion;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmTemplateVersion.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmTemplateVersion.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmTemplateVersion.resetOriginalValues();
	}

	private final DDMTemplateVersion _ddmTemplateVersion;
}
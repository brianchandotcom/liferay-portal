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

package com.liferay.commerce.notification.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceNotificationTemplate}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplate
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateWrapper
	implements CommerceNotificationTemplate,
		ModelWrapper<CommerceNotificationTemplate> {
	public CommerceNotificationTemplateWrapper(
		CommerceNotificationTemplate commerceNotificationTemplate) {
		_commerceNotificationTemplate = commerceNotificationTemplate;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceNotificationTemplate.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceNotificationTemplate.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceNotificationTemplateId",
			getCommerceNotificationTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("cc", getCc());
		attributes.put("ccn", getCcn());
		attributes.put("type", getType());
		attributes.put("enabled", isEnabled());
		attributes.put("subject", getSubject());
		attributes.put("body", getBody());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceNotificationTemplateId = (Long)attributes.get(
				"commerceNotificationTemplateId");

		if (commerceNotificationTemplateId != null) {
			setCommerceNotificationTemplateId(commerceNotificationTemplateId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String cc = (String)attributes.get("cc");

		if (cc != null) {
			setCc(cc);
		}

		String ccn = (String)attributes.get("ccn");

		if (ccn != null) {
			setCcn(ccn);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean enabled = (Boolean)attributes.get("enabled");

		if (enabled != null) {
			setEnabled(enabled);
		}

		String subject = (String)attributes.get("subject");

		if (subject != null) {
			setSubject(subject);
		}

		String body = (String)attributes.get("body");

		if (body != null) {
			setBody(body);
		}
	}

	@Override
	public Object clone() {
		return new CommerceNotificationTemplateWrapper((CommerceNotificationTemplate)_commerceNotificationTemplate.clone());
	}

	@Override
	public int compareTo(
		CommerceNotificationTemplate commerceNotificationTemplate) {
		return _commerceNotificationTemplate.compareTo(commerceNotificationTemplate);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _commerceNotificationTemplate.getAvailableLanguageIds();
	}

	/**
	* Returns the body of this commerce notification template.
	*
	* @return the body of this commerce notification template
	*/
	@Override
	public String getBody() {
		return _commerceNotificationTemplate.getBody();
	}

	/**
	* Returns the localized body of this commerce notification template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized body of this commerce notification template
	*/
	@Override
	public String getBody(java.util.Locale locale) {
		return _commerceNotificationTemplate.getBody(locale);
	}

	/**
	* Returns the localized body of this commerce notification template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized body of this commerce notification template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getBody(java.util.Locale locale, boolean useDefault) {
		return _commerceNotificationTemplate.getBody(locale, useDefault);
	}

	/**
	* Returns the localized body of this commerce notification template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized body of this commerce notification template
	*/
	@Override
	public String getBody(String languageId) {
		return _commerceNotificationTemplate.getBody(languageId);
	}

	/**
	* Returns the localized body of this commerce notification template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized body of this commerce notification template
	*/
	@Override
	public String getBody(String languageId, boolean useDefault) {
		return _commerceNotificationTemplate.getBody(languageId, useDefault);
	}

	@Override
	public String getBodyCurrentLanguageId() {
		return _commerceNotificationTemplate.getBodyCurrentLanguageId();
	}

	@Override
	public String getBodyCurrentValue() {
		return _commerceNotificationTemplate.getBodyCurrentValue();
	}

	/**
	* Returns a map of the locales and localized bodies of this commerce notification template.
	*
	* @return the locales and localized bodies of this commerce notification template
	*/
	@Override
	public Map<java.util.Locale, String> getBodyMap() {
		return _commerceNotificationTemplate.getBodyMap();
	}

	/**
	* Returns the cc of this commerce notification template.
	*
	* @return the cc of this commerce notification template
	*/
	@Override
	public String getCc() {
		return _commerceNotificationTemplate.getCc();
	}

	/**
	* Returns the ccn of this commerce notification template.
	*
	* @return the ccn of this commerce notification template
	*/
	@Override
	public String getCcn() {
		return _commerceNotificationTemplate.getCcn();
	}

	/**
	* Returns the commerce notification template ID of this commerce notification template.
	*
	* @return the commerce notification template ID of this commerce notification template
	*/
	@Override
	public long getCommerceNotificationTemplateId() {
		return _commerceNotificationTemplate.getCommerceNotificationTemplateId();
	}

	/**
	* Returns the company ID of this commerce notification template.
	*
	* @return the company ID of this commerce notification template
	*/
	@Override
	public long getCompanyId() {
		return _commerceNotificationTemplate.getCompanyId();
	}

	/**
	* Returns the create date of this commerce notification template.
	*
	* @return the create date of this commerce notification template
	*/
	@Override
	public Date getCreateDate() {
		return _commerceNotificationTemplate.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return _commerceNotificationTemplate.getDefaultLanguageId();
	}

	/**
	* Returns the description of this commerce notification template.
	*
	* @return the description of this commerce notification template
	*/
	@Override
	public String getDescription() {
		return _commerceNotificationTemplate.getDescription();
	}

	/**
	* Returns the enabled of this commerce notification template.
	*
	* @return the enabled of this commerce notification template
	*/
	@Override
	public boolean getEnabled() {
		return _commerceNotificationTemplate.getEnabled();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceNotificationTemplate.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce notification template.
	*
	* @return the group ID of this commerce notification template
	*/
	@Override
	public long getGroupId() {
		return _commerceNotificationTemplate.getGroupId();
	}

	/**
	* Returns the modified date of this commerce notification template.
	*
	* @return the modified date of this commerce notification template
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceNotificationTemplate.getModifiedDate();
	}

	/**
	* Returns the name of this commerce notification template.
	*
	* @return the name of this commerce notification template
	*/
	@Override
	public String getName() {
		return _commerceNotificationTemplate.getName();
	}

	/**
	* Returns the primary key of this commerce notification template.
	*
	* @return the primary key of this commerce notification template
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceNotificationTemplate.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceNotificationTemplate.getPrimaryKeyObj();
	}

	/**
	* Returns the subject of this commerce notification template.
	*
	* @return the subject of this commerce notification template
	*/
	@Override
	public String getSubject() {
		return _commerceNotificationTemplate.getSubject();
	}

	/**
	* Returns the localized subject of this commerce notification template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized subject of this commerce notification template
	*/
	@Override
	public String getSubject(java.util.Locale locale) {
		return _commerceNotificationTemplate.getSubject(locale);
	}

	/**
	* Returns the localized subject of this commerce notification template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized subject of this commerce notification template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getSubject(java.util.Locale locale, boolean useDefault) {
		return _commerceNotificationTemplate.getSubject(locale, useDefault);
	}

	/**
	* Returns the localized subject of this commerce notification template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized subject of this commerce notification template
	*/
	@Override
	public String getSubject(String languageId) {
		return _commerceNotificationTemplate.getSubject(languageId);
	}

	/**
	* Returns the localized subject of this commerce notification template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized subject of this commerce notification template
	*/
	@Override
	public String getSubject(String languageId, boolean useDefault) {
		return _commerceNotificationTemplate.getSubject(languageId, useDefault);
	}

	@Override
	public String getSubjectCurrentLanguageId() {
		return _commerceNotificationTemplate.getSubjectCurrentLanguageId();
	}

	@Override
	public String getSubjectCurrentValue() {
		return _commerceNotificationTemplate.getSubjectCurrentValue();
	}

	/**
	* Returns a map of the locales and localized subjects of this commerce notification template.
	*
	* @return the locales and localized subjects of this commerce notification template
	*/
	@Override
	public Map<java.util.Locale, String> getSubjectMap() {
		return _commerceNotificationTemplate.getSubjectMap();
	}

	/**
	* Returns the type of this commerce notification template.
	*
	* @return the type of this commerce notification template
	*/
	@Override
	public String getType() {
		return _commerceNotificationTemplate.getType();
	}

	/**
	* Returns the user ID of this commerce notification template.
	*
	* @return the user ID of this commerce notification template
	*/
	@Override
	public long getUserId() {
		return _commerceNotificationTemplate.getUserId();
	}

	/**
	* Returns the user name of this commerce notification template.
	*
	* @return the user name of this commerce notification template
	*/
	@Override
	public String getUserName() {
		return _commerceNotificationTemplate.getUserName();
	}

	/**
	* Returns the user uuid of this commerce notification template.
	*
	* @return the user uuid of this commerce notification template
	*/
	@Override
	public String getUserUuid() {
		return _commerceNotificationTemplate.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce notification template.
	*
	* @return the uuid of this commerce notification template
	*/
	@Override
	public String getUuid() {
		return _commerceNotificationTemplate.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceNotificationTemplate.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceNotificationTemplate.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this commerce notification template is enabled.
	*
	* @return <code>true</code> if this commerce notification template is enabled; <code>false</code> otherwise
	*/
	@Override
	public boolean isEnabled() {
		return _commerceNotificationTemplate.isEnabled();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceNotificationTemplate.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceNotificationTemplate.isNew();
	}

	@Override
	public void persist() {
		_commerceNotificationTemplate.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceNotificationTemplate.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_commerceNotificationTemplate.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the body of this commerce notification template.
	*
	* @param body the body of this commerce notification template
	*/
	@Override
	public void setBody(String body) {
		_commerceNotificationTemplate.setBody(body);
	}

	/**
	* Sets the localized body of this commerce notification template in the language.
	*
	* @param body the localized body of this commerce notification template
	* @param locale the locale of the language
	*/
	@Override
	public void setBody(String body, java.util.Locale locale) {
		_commerceNotificationTemplate.setBody(body, locale);
	}

	/**
	* Sets the localized body of this commerce notification template in the language, and sets the default locale.
	*
	* @param body the localized body of this commerce notification template
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setBody(String body, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceNotificationTemplate.setBody(body, locale, defaultLocale);
	}

	@Override
	public void setBodyCurrentLanguageId(String languageId) {
		_commerceNotificationTemplate.setBodyCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized bodies of this commerce notification template from the map of locales and localized bodies.
	*
	* @param bodyMap the locales and localized bodies of this commerce notification template
	*/
	@Override
	public void setBodyMap(Map<java.util.Locale, String> bodyMap) {
		_commerceNotificationTemplate.setBodyMap(bodyMap);
	}

	/**
	* Sets the localized bodies of this commerce notification template from the map of locales and localized bodies, and sets the default locale.
	*
	* @param bodyMap the locales and localized bodies of this commerce notification template
	* @param defaultLocale the default locale
	*/
	@Override
	public void setBodyMap(Map<java.util.Locale, String> bodyMap,
		java.util.Locale defaultLocale) {
		_commerceNotificationTemplate.setBodyMap(bodyMap, defaultLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceNotificationTemplate.setCachedModel(cachedModel);
	}

	/**
	* Sets the cc of this commerce notification template.
	*
	* @param cc the cc of this commerce notification template
	*/
	@Override
	public void setCc(String cc) {
		_commerceNotificationTemplate.setCc(cc);
	}

	/**
	* Sets the ccn of this commerce notification template.
	*
	* @param ccn the ccn of this commerce notification template
	*/
	@Override
	public void setCcn(String ccn) {
		_commerceNotificationTemplate.setCcn(ccn);
	}

	/**
	* Sets the commerce notification template ID of this commerce notification template.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID of this commerce notification template
	*/
	@Override
	public void setCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		_commerceNotificationTemplate.setCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	* Sets the company ID of this commerce notification template.
	*
	* @param companyId the company ID of this commerce notification template
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceNotificationTemplate.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce notification template.
	*
	* @param createDate the create date of this commerce notification template
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceNotificationTemplate.setCreateDate(createDate);
	}

	/**
	* Sets the description of this commerce notification template.
	*
	* @param description the description of this commerce notification template
	*/
	@Override
	public void setDescription(String description) {
		_commerceNotificationTemplate.setDescription(description);
	}

	/**
	* Sets whether this commerce notification template is enabled.
	*
	* @param enabled the enabled of this commerce notification template
	*/
	@Override
	public void setEnabled(boolean enabled) {
		_commerceNotificationTemplate.setEnabled(enabled);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceNotificationTemplate.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceNotificationTemplate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceNotificationTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce notification template.
	*
	* @param groupId the group ID of this commerce notification template
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceNotificationTemplate.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce notification template.
	*
	* @param modifiedDate the modified date of this commerce notification template
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceNotificationTemplate.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce notification template.
	*
	* @param name the name of this commerce notification template
	*/
	@Override
	public void setName(String name) {
		_commerceNotificationTemplate.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commerceNotificationTemplate.setNew(n);
	}

	/**
	* Sets the primary key of this commerce notification template.
	*
	* @param primaryKey the primary key of this commerce notification template
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceNotificationTemplate.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceNotificationTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the subject of this commerce notification template.
	*
	* @param subject the subject of this commerce notification template
	*/
	@Override
	public void setSubject(String subject) {
		_commerceNotificationTemplate.setSubject(subject);
	}

	/**
	* Sets the localized subject of this commerce notification template in the language.
	*
	* @param subject the localized subject of this commerce notification template
	* @param locale the locale of the language
	*/
	@Override
	public void setSubject(String subject, java.util.Locale locale) {
		_commerceNotificationTemplate.setSubject(subject, locale);
	}

	/**
	* Sets the localized subject of this commerce notification template in the language, and sets the default locale.
	*
	* @param subject the localized subject of this commerce notification template
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setSubject(String subject, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_commerceNotificationTemplate.setSubject(subject, locale, defaultLocale);
	}

	@Override
	public void setSubjectCurrentLanguageId(String languageId) {
		_commerceNotificationTemplate.setSubjectCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized subjects of this commerce notification template from the map of locales and localized subjects.
	*
	* @param subjectMap the locales and localized subjects of this commerce notification template
	*/
	@Override
	public void setSubjectMap(Map<java.util.Locale, String> subjectMap) {
		_commerceNotificationTemplate.setSubjectMap(subjectMap);
	}

	/**
	* Sets the localized subjects of this commerce notification template from the map of locales and localized subjects, and sets the default locale.
	*
	* @param subjectMap the locales and localized subjects of this commerce notification template
	* @param defaultLocale the default locale
	*/
	@Override
	public void setSubjectMap(Map<java.util.Locale, String> subjectMap,
		java.util.Locale defaultLocale) {
		_commerceNotificationTemplate.setSubjectMap(subjectMap, defaultLocale);
	}

	/**
	* Sets the type of this commerce notification template.
	*
	* @param type the type of this commerce notification template
	*/
	@Override
	public void setType(String type) {
		_commerceNotificationTemplate.setType(type);
	}

	/**
	* Sets the user ID of this commerce notification template.
	*
	* @param userId the user ID of this commerce notification template
	*/
	@Override
	public void setUserId(long userId) {
		_commerceNotificationTemplate.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce notification template.
	*
	* @param userName the user name of this commerce notification template
	*/
	@Override
	public void setUserName(String userName) {
		_commerceNotificationTemplate.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce notification template.
	*
	* @param userUuid the user uuid of this commerce notification template
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceNotificationTemplate.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce notification template.
	*
	* @param uuid the uuid of this commerce notification template
	*/
	@Override
	public void setUuid(String uuid) {
		_commerceNotificationTemplate.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceNotificationTemplate> toCacheModel() {
		return _commerceNotificationTemplate.toCacheModel();
	}

	@Override
	public CommerceNotificationTemplate toEscapedModel() {
		return new CommerceNotificationTemplateWrapper(_commerceNotificationTemplate.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceNotificationTemplate.toString();
	}

	@Override
	public CommerceNotificationTemplate toUnescapedModel() {
		return new CommerceNotificationTemplateWrapper(_commerceNotificationTemplate.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceNotificationTemplate.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceNotificationTemplateWrapper)) {
			return false;
		}

		CommerceNotificationTemplateWrapper commerceNotificationTemplateWrapper = (CommerceNotificationTemplateWrapper)obj;

		if (Objects.equals(_commerceNotificationTemplate,
					commerceNotificationTemplateWrapper._commerceNotificationTemplate)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceNotificationTemplate.getStagedModelType();
	}

	@Override
	public CommerceNotificationTemplate getWrappedModel() {
		return _commerceNotificationTemplate;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceNotificationTemplate.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceNotificationTemplate.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceNotificationTemplate.resetOriginalValues();
	}

	private final CommerceNotificationTemplate _commerceNotificationTemplate;
}
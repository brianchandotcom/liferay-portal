/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceNotificationTemplate}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplate
 * @generated
 */
public class CommerceNotificationTemplateWrapper
	extends BaseModelWrapper<CommerceNotificationTemplate>
	implements CommerceNotificationTemplate,
			   ModelWrapper<CommerceNotificationTemplate> {

	public CommerceNotificationTemplateWrapper(
		CommerceNotificationTemplate commerceNotificationTemplate) {

		super(commerceNotificationTemplate);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"commerceNotificationTemplateId",
			getCommerceNotificationTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("from", getFrom());
		attributes.put("fromName", getFromName());
		attributes.put("to", getTo());
		attributes.put("cc", getCc());
		attributes.put("bcc", getBcc());
		attributes.put("type", getType());
		attributes.put("enabled", isEnabled());
		attributes.put("subject", getSubject());
		attributes.put("body", getBody());

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

		String from = (String)attributes.get("from");

		if (from != null) {
			setFrom(from);
		}

		String fromName = (String)attributes.get("fromName");

		if (fromName != null) {
			setFromName(fromName);
		}

		String to = (String)attributes.get("to");

		if (to != null) {
			setTo(to);
		}

		String cc = (String)attributes.get("cc");

		if (cc != null) {
			setCc(cc);
		}

		String bcc = (String)attributes.get("bcc");

		if (bcc != null) {
			setBcc(bcc);
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
	public CommerceNotificationTemplate cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the bcc of this commerce notification template.
	 *
	 * @return the bcc of this commerce notification template
	 */
	@Override
	public String getBcc() {
		return model.getBcc();
	}

	/**
	 * Returns the body of this commerce notification template.
	 *
	 * @return the body of this commerce notification template
	 */
	@Override
	public String getBody() {
		return model.getBody();
	}

	/**
	 * Returns the localized body of this commerce notification template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized body of this commerce notification template
	 */
	@Override
	public String getBody(java.util.Locale locale) {
		return model.getBody(locale);
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
		return model.getBody(locale, useDefault);
	}

	/**
	 * Returns the localized body of this commerce notification template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized body of this commerce notification template
	 */
	@Override
	public String getBody(String languageId) {
		return model.getBody(languageId);
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
		return model.getBody(languageId, useDefault);
	}

	@Override
	public String getBodyCurrentLanguageId() {
		return model.getBodyCurrentLanguageId();
	}

	@Override
	public String getBodyCurrentValue() {
		return model.getBodyCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized bodies of this commerce notification template.
	 *
	 * @return the locales and localized bodies of this commerce notification template
	 */
	@Override
	public Map<java.util.Locale, String> getBodyMap() {
		return model.getBodyMap();
	}

	/**
	 * Returns the cc of this commerce notification template.
	 *
	 * @return the cc of this commerce notification template
	 */
	@Override
	public String getCc() {
		return model.getCc();
	}

	/**
	 * Returns the commerce notification template ID of this commerce notification template.
	 *
	 * @return the commerce notification template ID of this commerce notification template
	 */
	@Override
	public long getCommerceNotificationTemplateId() {
		return model.getCommerceNotificationTemplateId();
	}

	/**
	 * Returns the company ID of this commerce notification template.
	 *
	 * @return the company ID of this commerce notification template
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce notification template.
	 *
	 * @return the create date of this commerce notification template
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
	 * Returns the description of this commerce notification template.
	 *
	 * @return the description of this commerce notification template
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the enabled of this commerce notification template.
	 *
	 * @return the enabled of this commerce notification template
	 */
	@Override
	public boolean getEnabled() {
		return model.getEnabled();
	}

	/**
	 * Returns the from of this commerce notification template.
	 *
	 * @return the from of this commerce notification template
	 */
	@Override
	public String getFrom() {
		return model.getFrom();
	}

	/**
	 * Returns the from name of this commerce notification template.
	 *
	 * @return the from name of this commerce notification template
	 */
	@Override
	public String getFromName() {
		return model.getFromName();
	}

	/**
	 * Returns the localized from name of this commerce notification template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized from name of this commerce notification template
	 */
	@Override
	public String getFromName(java.util.Locale locale) {
		return model.getFromName(locale);
	}

	/**
	 * Returns the localized from name of this commerce notification template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized from name of this commerce notification template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getFromName(java.util.Locale locale, boolean useDefault) {
		return model.getFromName(locale, useDefault);
	}

	/**
	 * Returns the localized from name of this commerce notification template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized from name of this commerce notification template
	 */
	@Override
	public String getFromName(String languageId) {
		return model.getFromName(languageId);
	}

	/**
	 * Returns the localized from name of this commerce notification template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized from name of this commerce notification template
	 */
	@Override
	public String getFromName(String languageId, boolean useDefault) {
		return model.getFromName(languageId, useDefault);
	}

	@Override
	public String getFromNameCurrentLanguageId() {
		return model.getFromNameCurrentLanguageId();
	}

	@Override
	public String getFromNameCurrentValue() {
		return model.getFromNameCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized from names of this commerce notification template.
	 *
	 * @return the locales and localized from names of this commerce notification template
	 */
	@Override
	public Map<java.util.Locale, String> getFromNameMap() {
		return model.getFromNameMap();
	}

	/**
	 * Returns the group ID of this commerce notification template.
	 *
	 * @return the group ID of this commerce notification template
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce notification template.
	 *
	 * @return the modified date of this commerce notification template
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce notification template.
	 *
	 * @return the mvcc version of this commerce notification template
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this commerce notification template.
	 *
	 * @return the name of this commerce notification template
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this commerce notification template.
	 *
	 * @return the primary key of this commerce notification template
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the subject of this commerce notification template.
	 *
	 * @return the subject of this commerce notification template
	 */
	@Override
	public String getSubject() {
		return model.getSubject();
	}

	/**
	 * Returns the localized subject of this commerce notification template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized subject of this commerce notification template
	 */
	@Override
	public String getSubject(java.util.Locale locale) {
		return model.getSubject(locale);
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
		return model.getSubject(locale, useDefault);
	}

	/**
	 * Returns the localized subject of this commerce notification template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized subject of this commerce notification template
	 */
	@Override
	public String getSubject(String languageId) {
		return model.getSubject(languageId);
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
		return model.getSubject(languageId, useDefault);
	}

	@Override
	public String getSubjectCurrentLanguageId() {
		return model.getSubjectCurrentLanguageId();
	}

	@Override
	public String getSubjectCurrentValue() {
		return model.getSubjectCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized subjects of this commerce notification template.
	 *
	 * @return the locales and localized subjects of this commerce notification template
	 */
	@Override
	public Map<java.util.Locale, String> getSubjectMap() {
		return model.getSubjectMap();
	}

	/**
	 * Returns the to of this commerce notification template.
	 *
	 * @return the to of this commerce notification template
	 */
	@Override
	public String getTo() {
		return model.getTo();
	}

	/**
	 * Returns the type of this commerce notification template.
	 *
	 * @return the type of this commerce notification template
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this commerce notification template.
	 *
	 * @return the user ID of this commerce notification template
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce notification template.
	 *
	 * @return the user name of this commerce notification template
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce notification template.
	 *
	 * @return the user uuid of this commerce notification template
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce notification template.
	 *
	 * @return the uuid of this commerce notification template
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce notification template is enabled.
	 *
	 * @return <code>true</code> if this commerce notification template is enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isEnabled() {
		return model.isEnabled();
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
	 * Sets the bcc of this commerce notification template.
	 *
	 * @param bcc the bcc of this commerce notification template
	 */
	@Override
	public void setBcc(String bcc) {
		model.setBcc(bcc);
	}

	/**
	 * Sets the body of this commerce notification template.
	 *
	 * @param body the body of this commerce notification template
	 */
	@Override
	public void setBody(String body) {
		model.setBody(body);
	}

	/**
	 * Sets the localized body of this commerce notification template in the language.
	 *
	 * @param body the localized body of this commerce notification template
	 * @param locale the locale of the language
	 */
	@Override
	public void setBody(String body, java.util.Locale locale) {
		model.setBody(body, locale);
	}

	/**
	 * Sets the localized body of this commerce notification template in the language, and sets the default locale.
	 *
	 * @param body the localized body of this commerce notification template
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setBody(
		String body, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setBody(body, locale, defaultLocale);
	}

	@Override
	public void setBodyCurrentLanguageId(String languageId) {
		model.setBodyCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized bodies of this commerce notification template from the map of locales and localized bodies.
	 *
	 * @param bodyMap the locales and localized bodies of this commerce notification template
	 */
	@Override
	public void setBodyMap(Map<java.util.Locale, String> bodyMap) {
		model.setBodyMap(bodyMap);
	}

	/**
	 * Sets the localized bodies of this commerce notification template from the map of locales and localized bodies, and sets the default locale.
	 *
	 * @param bodyMap the locales and localized bodies of this commerce notification template
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setBodyMap(
		Map<java.util.Locale, String> bodyMap, java.util.Locale defaultLocale) {

		model.setBodyMap(bodyMap, defaultLocale);
	}

	/**
	 * Sets the cc of this commerce notification template.
	 *
	 * @param cc the cc of this commerce notification template
	 */
	@Override
	public void setCc(String cc) {
		model.setCc(cc);
	}

	/**
	 * Sets the commerce notification template ID of this commerce notification template.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID of this commerce notification template
	 */
	@Override
	public void setCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		model.setCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	 * Sets the company ID of this commerce notification template.
	 *
	 * @param companyId the company ID of this commerce notification template
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce notification template.
	 *
	 * @param createDate the create date of this commerce notification template
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this commerce notification template.
	 *
	 * @param description the description of this commerce notification template
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets whether this commerce notification template is enabled.
	 *
	 * @param enabled the enabled of this commerce notification template
	 */
	@Override
	public void setEnabled(boolean enabled) {
		model.setEnabled(enabled);
	}

	/**
	 * Sets the from of this commerce notification template.
	 *
	 * @param from the from of this commerce notification template
	 */
	@Override
	public void setFrom(String from) {
		model.setFrom(from);
	}

	/**
	 * Sets the from name of this commerce notification template.
	 *
	 * @param fromName the from name of this commerce notification template
	 */
	@Override
	public void setFromName(String fromName) {
		model.setFromName(fromName);
	}

	/**
	 * Sets the localized from name of this commerce notification template in the language.
	 *
	 * @param fromName the localized from name of this commerce notification template
	 * @param locale the locale of the language
	 */
	@Override
	public void setFromName(String fromName, java.util.Locale locale) {
		model.setFromName(fromName, locale);
	}

	/**
	 * Sets the localized from name of this commerce notification template in the language, and sets the default locale.
	 *
	 * @param fromName the localized from name of this commerce notification template
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setFromName(
		String fromName, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setFromName(fromName, locale, defaultLocale);
	}

	@Override
	public void setFromNameCurrentLanguageId(String languageId) {
		model.setFromNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized from names of this commerce notification template from the map of locales and localized from names.
	 *
	 * @param fromNameMap the locales and localized from names of this commerce notification template
	 */
	@Override
	public void setFromNameMap(Map<java.util.Locale, String> fromNameMap) {
		model.setFromNameMap(fromNameMap);
	}

	/**
	 * Sets the localized from names of this commerce notification template from the map of locales and localized from names, and sets the default locale.
	 *
	 * @param fromNameMap the locales and localized from names of this commerce notification template
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setFromNameMap(
		Map<java.util.Locale, String> fromNameMap,
		java.util.Locale defaultLocale) {

		model.setFromNameMap(fromNameMap, defaultLocale);
	}

	/**
	 * Sets the group ID of this commerce notification template.
	 *
	 * @param groupId the group ID of this commerce notification template
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce notification template.
	 *
	 * @param modifiedDate the modified date of this commerce notification template
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce notification template.
	 *
	 * @param mvccVersion the mvcc version of this commerce notification template
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this commerce notification template.
	 *
	 * @param name the name of this commerce notification template
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this commerce notification template.
	 *
	 * @param primaryKey the primary key of this commerce notification template
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the subject of this commerce notification template.
	 *
	 * @param subject the subject of this commerce notification template
	 */
	@Override
	public void setSubject(String subject) {
		model.setSubject(subject);
	}

	/**
	 * Sets the localized subject of this commerce notification template in the language.
	 *
	 * @param subject the localized subject of this commerce notification template
	 * @param locale the locale of the language
	 */
	@Override
	public void setSubject(String subject, java.util.Locale locale) {
		model.setSubject(subject, locale);
	}

	/**
	 * Sets the localized subject of this commerce notification template in the language, and sets the default locale.
	 *
	 * @param subject the localized subject of this commerce notification template
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setSubject(
		String subject, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setSubject(subject, locale, defaultLocale);
	}

	@Override
	public void setSubjectCurrentLanguageId(String languageId) {
		model.setSubjectCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized subjects of this commerce notification template from the map of locales and localized subjects.
	 *
	 * @param subjectMap the locales and localized subjects of this commerce notification template
	 */
	@Override
	public void setSubjectMap(Map<java.util.Locale, String> subjectMap) {
		model.setSubjectMap(subjectMap);
	}

	/**
	 * Sets the localized subjects of this commerce notification template from the map of locales and localized subjects, and sets the default locale.
	 *
	 * @param subjectMap the locales and localized subjects of this commerce notification template
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setSubjectMap(
		Map<java.util.Locale, String> subjectMap,
		java.util.Locale defaultLocale) {

		model.setSubjectMap(subjectMap, defaultLocale);
	}

	/**
	 * Sets the to of this commerce notification template.
	 *
	 * @param to the to of this commerce notification template
	 */
	@Override
	public void setTo(String to) {
		model.setTo(to);
	}

	/**
	 * Sets the type of this commerce notification template.
	 *
	 * @param type the type of this commerce notification template
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this commerce notification template.
	 *
	 * @param userId the user ID of this commerce notification template
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce notification template.
	 *
	 * @param userName the user name of this commerce notification template
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce notification template.
	 *
	 * @param userUuid the user uuid of this commerce notification template
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce notification template.
	 *
	 * @param uuid the uuid of this commerce notification template
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
	protected CommerceNotificationTemplateWrapper wrap(
		CommerceNotificationTemplate commerceNotificationTemplate) {

		return new CommerceNotificationTemplateWrapper(
			commerceNotificationTemplate);
	}

}
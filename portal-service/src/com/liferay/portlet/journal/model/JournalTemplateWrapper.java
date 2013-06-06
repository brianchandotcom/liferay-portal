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

package com.liferay.portlet.journal.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link JournalTemplate}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalTemplate
 * @generated
 */
public class JournalTemplateWrapper implements JournalTemplate,
	ModelWrapper<JournalTemplate> {
	public JournalTemplateWrapper(JournalTemplate journalTemplate) {
		_journalTemplate = journalTemplate;
	}

	@Override
	public Class<?> getModelClass() {
		return JournalTemplate.class;
	}

	@Override
	public String getModelClassName() {
		return JournalTemplate.class.getName();
	}

	@Override
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
		attributes.put("templateId", getTemplateId());
		attributes.put("structureId", getStructureId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("xsl", getXsl());
		attributes.put("langType", getLangType());
		attributes.put("cacheable", getCacheable());
		attributes.put("smallImage", getSmallImage());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("smallImageURL", getSmallImageURL());

		return attributes;
	}

	@Override
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

		String templateId = (String)attributes.get("templateId");

		if (templateId != null) {
			setTemplateId(templateId);
		}

		String structureId = (String)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String xsl = (String)attributes.get("xsl");

		if (xsl != null) {
			setXsl(xsl);
		}

		String langType = (String)attributes.get("langType");

		if (langType != null) {
			setLangType(langType);
		}

		Boolean cacheable = (Boolean)attributes.get("cacheable");

		if (cacheable != null) {
			setCacheable(cacheable);
		}

		Boolean smallImage = (Boolean)attributes.get("smallImage");

		if (smallImage != null) {
			setSmallImage(smallImage);
		}

		Long smallImageId = (Long)attributes.get("smallImageId");

		if (smallImageId != null) {
			setSmallImageId(smallImageId);
		}

		String smallImageURL = (String)attributes.get("smallImageURL");

		if (smallImageURL != null) {
			setSmallImageURL(smallImageURL);
		}
	}

	/**
	* Returns the primary key of this journal template.
	*
	* @return the primary key of this journal template
	*/
	@Override
	public long getPrimaryKey() {
		return _journalTemplate.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal template.
	*
	* @param primaryKey the primary key of this journal template
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_journalTemplate.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this journal template.
	*
	* @return the uuid of this journal template
	*/
	@Override
	public java.lang.String getUuid() {
		return _journalTemplate.getUuid();
	}

	/**
	* Sets the uuid of this journal template.
	*
	* @param uuid the uuid of this journal template
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_journalTemplate.setUuid(uuid);
	}

	/**
	* Returns the ID of this journal template.
	*
	* @return the ID of this journal template
	*/
	@Override
	public long getId() {
		return _journalTemplate.getId();
	}

	/**
	* Sets the ID of this journal template.
	*
	* @param id the ID of this journal template
	*/
	@Override
	public void setId(long id) {
		_journalTemplate.setId(id);
	}

	/**
	* Returns the group ID of this journal template.
	*
	* @return the group ID of this journal template
	*/
	@Override
	public long getGroupId() {
		return _journalTemplate.getGroupId();
	}

	/**
	* Sets the group ID of this journal template.
	*
	* @param groupId the group ID of this journal template
	*/
	@Override
	public void setGroupId(long groupId) {
		_journalTemplate.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this journal template.
	*
	* @return the company ID of this journal template
	*/
	@Override
	public long getCompanyId() {
		return _journalTemplate.getCompanyId();
	}

	/**
	* Sets the company ID of this journal template.
	*
	* @param companyId the company ID of this journal template
	*/
	@Override
	public void setCompanyId(long companyId) {
		_journalTemplate.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this journal template.
	*
	* @return the user ID of this journal template
	*/
	@Override
	public long getUserId() {
		return _journalTemplate.getUserId();
	}

	/**
	* Sets the user ID of this journal template.
	*
	* @param userId the user ID of this journal template
	*/
	@Override
	public void setUserId(long userId) {
		_journalTemplate.setUserId(userId);
	}

	/**
	* Returns the user uuid of this journal template.
	*
	* @return the user uuid of this journal template
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplate.getUserUuid();
	}

	/**
	* Sets the user uuid of this journal template.
	*
	* @param userUuid the user uuid of this journal template
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_journalTemplate.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this journal template.
	*
	* @return the user name of this journal template
	*/
	@Override
	public java.lang.String getUserName() {
		return _journalTemplate.getUserName();
	}

	/**
	* Sets the user name of this journal template.
	*
	* @param userName the user name of this journal template
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_journalTemplate.setUserName(userName);
	}

	/**
	* Returns the create date of this journal template.
	*
	* @return the create date of this journal template
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _journalTemplate.getCreateDate();
	}

	/**
	* Sets the create date of this journal template.
	*
	* @param createDate the create date of this journal template
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_journalTemplate.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this journal template.
	*
	* @return the modified date of this journal template
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _journalTemplate.getModifiedDate();
	}

	/**
	* Sets the modified date of this journal template.
	*
	* @param modifiedDate the modified date of this journal template
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalTemplate.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the template ID of this journal template.
	*
	* @return the template ID of this journal template
	*/
	@Override
	public java.lang.String getTemplateId() {
		return _journalTemplate.getTemplateId();
	}

	/**
	* Sets the template ID of this journal template.
	*
	* @param templateId the template ID of this journal template
	*/
	@Override
	public void setTemplateId(java.lang.String templateId) {
		_journalTemplate.setTemplateId(templateId);
	}

	/**
	* Returns the structure ID of this journal template.
	*
	* @return the structure ID of this journal template
	*/
	@Override
	public java.lang.String getStructureId() {
		return _journalTemplate.getStructureId();
	}

	/**
	* Sets the structure ID of this journal template.
	*
	* @param structureId the structure ID of this journal template
	*/
	@Override
	public void setStructureId(java.lang.String structureId) {
		_journalTemplate.setStructureId(structureId);
	}

	/**
	* Returns the name of this journal template.
	*
	* @return the name of this journal template
	*/
	@Override
	public java.lang.String getName() {
		return _journalTemplate.getName();
	}

	/**
	* Returns the localized name of this journal template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this journal template
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale) {
		return _journalTemplate.getName(locale);
	}

	/**
	* Returns the localized name of this journal template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this journal template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _journalTemplate.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this journal template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this journal template
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId) {
		return _journalTemplate.getName(languageId);
	}

	/**
	* Returns the localized name of this journal template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this journal template
	*/
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _journalTemplate.getName(languageId, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _journalTemplate.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _journalTemplate.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this journal template.
	*
	* @return the locales and localized names of this journal template
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _journalTemplate.getNameMap();
	}

	/**
	* Sets the name of this journal template.
	*
	* @param name the name of this journal template
	*/
	@Override
	public void setName(java.lang.String name) {
		_journalTemplate.setName(name);
	}

	/**
	* Sets the localized name of this journal template in the language.
	*
	* @param name the localized name of this journal template
	* @param locale the locale of the language
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale) {
		_journalTemplate.setName(name, locale);
	}

	/**
	* Sets the localized name of this journal template in the language, and sets the default locale.
	*
	* @param name the localized name of this journal template
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_journalTemplate.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_journalTemplate.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this journal template from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this journal template
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_journalTemplate.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this journal template from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this journal template
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_journalTemplate.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Returns the description of this journal template.
	*
	* @return the description of this journal template
	*/
	@Override
	public java.lang.String getDescription() {
		return _journalTemplate.getDescription();
	}

	/**
	* Returns the localized description of this journal template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this journal template
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale) {
		return _journalTemplate.getDescription(locale);
	}

	/**
	* Returns the localized description of this journal template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this journal template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _journalTemplate.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this journal template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this journal template
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _journalTemplate.getDescription(languageId);
	}

	/**
	* Returns the localized description of this journal template in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this journal template
	*/
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _journalTemplate.getDescription(languageId, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _journalTemplate.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _journalTemplate.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this journal template.
	*
	* @return the locales and localized descriptions of this journal template
	*/
	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _journalTemplate.getDescriptionMap();
	}

	/**
	* Sets the description of this journal template.
	*
	* @param description the description of this journal template
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_journalTemplate.setDescription(description);
	}

	/**
	* Sets the localized description of this journal template in the language.
	*
	* @param description the localized description of this journal template
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_journalTemplate.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this journal template in the language, and sets the default locale.
	*
	* @param description the localized description of this journal template
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_journalTemplate.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_journalTemplate.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this journal template from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this journal template
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_journalTemplate.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this journal template from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this journal template
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_journalTemplate.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the xsl of this journal template.
	*
	* @return the xsl of this journal template
	*/
	@Override
	public java.lang.String getXsl() {
		return _journalTemplate.getXsl();
	}

	/**
	* Sets the xsl of this journal template.
	*
	* @param xsl the xsl of this journal template
	*/
	@Override
	public void setXsl(java.lang.String xsl) {
		_journalTemplate.setXsl(xsl);
	}

	/**
	* Returns the lang type of this journal template.
	*
	* @return the lang type of this journal template
	*/
	@Override
	public java.lang.String getLangType() {
		return _journalTemplate.getLangType();
	}

	/**
	* Sets the lang type of this journal template.
	*
	* @param langType the lang type of this journal template
	*/
	@Override
	public void setLangType(java.lang.String langType) {
		_journalTemplate.setLangType(langType);
	}

	/**
	* Returns the cacheable of this journal template.
	*
	* @return the cacheable of this journal template
	*/
	@Override
	public boolean getCacheable() {
		return _journalTemplate.getCacheable();
	}

	/**
	* Returns <code>true</code> if this journal template is cacheable.
	*
	* @return <code>true</code> if this journal template is cacheable; <code>false</code> otherwise
	*/
	@Override
	public boolean isCacheable() {
		return _journalTemplate.isCacheable();
	}

	/**
	* Sets whether this journal template is cacheable.
	*
	* @param cacheable the cacheable of this journal template
	*/
	@Override
	public void setCacheable(boolean cacheable) {
		_journalTemplate.setCacheable(cacheable);
	}

	/**
	* Returns the small image of this journal template.
	*
	* @return the small image of this journal template
	*/
	@Override
	public boolean getSmallImage() {
		return _journalTemplate.getSmallImage();
	}

	/**
	* Returns <code>true</code> if this journal template is small image.
	*
	* @return <code>true</code> if this journal template is small image; <code>false</code> otherwise
	*/
	@Override
	public boolean isSmallImage() {
		return _journalTemplate.isSmallImage();
	}

	/**
	* Sets whether this journal template is small image.
	*
	* @param smallImage the small image of this journal template
	*/
	@Override
	public void setSmallImage(boolean smallImage) {
		_journalTemplate.setSmallImage(smallImage);
	}

	/**
	* Returns the small image ID of this journal template.
	*
	* @return the small image ID of this journal template
	*/
	@Override
	public long getSmallImageId() {
		return _journalTemplate.getSmallImageId();
	}

	/**
	* Sets the small image ID of this journal template.
	*
	* @param smallImageId the small image ID of this journal template
	*/
	@Override
	public void setSmallImageId(long smallImageId) {
		_journalTemplate.setSmallImageId(smallImageId);
	}

	/**
	* Returns the small image u r l of this journal template.
	*
	* @return the small image u r l of this journal template
	*/
	@Override
	public java.lang.String getSmallImageURL() {
		return _journalTemplate.getSmallImageURL();
	}

	/**
	* Sets the small image u r l of this journal template.
	*
	* @param smallImageURL the small image u r l of this journal template
	*/
	@Override
	public void setSmallImageURL(java.lang.String smallImageURL) {
		_journalTemplate.setSmallImageURL(smallImageURL);
	}

	@Override
	public boolean isNew() {
		return _journalTemplate.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_journalTemplate.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _journalTemplate.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_journalTemplate.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _journalTemplate.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _journalTemplate.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalTemplate.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_journalTemplate.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_journalTemplate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_journalTemplate.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public java.lang.Object clone() {
		return new JournalTemplateWrapper((JournalTemplate)_journalTemplate.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate) {
		return _journalTemplate.compareTo(journalTemplate);
	}

	@Override
	public int hashCode() {
		return _journalTemplate.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.journal.model.JournalTemplate> toCacheModel() {
		return _journalTemplate.toCacheModel();
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate toEscapedModel() {
		return new JournalTemplateWrapper(_journalTemplate.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.journal.model.JournalTemplate toUnescapedModel() {
		return new JournalTemplateWrapper(_journalTemplate.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalTemplate.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _journalTemplate.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalTemplate.persist();
	}

	@Override
	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplate.getSmallImageType();
	}

	@Override
	public void setSmallImageType(java.lang.String smallImageType) {
		_journalTemplate.setSmallImageType(smallImageType);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public JournalTemplate getWrappedJournalTemplate() {
		return _journalTemplate;
	}

	@Override
	public JournalTemplate getWrappedModel() {
		return _journalTemplate;
	}

	@Override
	public void resetOriginalValues() {
		_journalTemplate.resetOriginalValues();
	}

	private JournalTemplate _journalTemplate;
}
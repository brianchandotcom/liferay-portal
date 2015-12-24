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

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMTemplate}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplate
 * @generated
 */
@ProviderType
public class DDMTemplateWrapper implements DDMTemplate,
	ModelWrapper<DDMTemplate> {
	public DDMTemplateWrapper(DDMTemplate ddmTemplate) {
		_ddmTemplate = ddmTemplate;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMTemplate.class;
	}

	@Override
	public String getModelClassName() {
		return DDMTemplate.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("templateId", getTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("versionUserId", getVersionUserId());
		attributes.put("versionUserName", getVersionUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("resourceClassNameId", getResourceClassNameId());
		attributes.put("templateKey", getTemplateKey());
		attributes.put("version", getVersion());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("mode", getMode());
		attributes.put("language", getLanguage());
		attributes.put("script", getScript());
		attributes.put("cacheable", getCacheable());
		attributes.put("smallImage", getSmallImage());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("smallImageURL", getSmallImageURL());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
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

		Long versionUserId = (Long)attributes.get("versionUserId");

		if (versionUserId != null) {
			setVersionUserId(versionUserId);
		}

		String versionUserName = (String)attributes.get("versionUserName");

		if (versionUserName != null) {
			setVersionUserName(versionUserName);
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

		Long resourceClassNameId = (Long)attributes.get("resourceClassNameId");

		if (resourceClassNameId != null) {
			setResourceClassNameId(resourceClassNameId);
		}

		String templateKey = (String)attributes.get("templateKey");

		if (templateKey != null) {
			setTemplateKey(templateKey);
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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDMTemplateWrapper((DDMTemplate)_ddmTemplate.clone());
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.mapping.model.DDMTemplate ddmTemplate) {
		return _ddmTemplate.compareTo(ddmTemplate);
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _ddmTemplate.getAvailableLanguageIds();
	}

	/**
	* Returns the cacheable of this d d m template.
	*
	* @return the cacheable of this d d m template
	*/
	@Override
	public boolean getCacheable() {
		return _ddmTemplate.getCacheable();
	}

	/**
	* Returns the fully qualified class name of this d d m template.
	*
	* @return the fully qualified class name of this d d m template
	*/
	@Override
	public java.lang.String getClassName() {
		return _ddmTemplate.getClassName();
	}

	/**
	* Returns the class name ID of this d d m template.
	*
	* @return the class name ID of this d d m template
	*/
	@Override
	public long getClassNameId() {
		return _ddmTemplate.getClassNameId();
	}

	/**
	* Returns the class p k of this d d m template.
	*
	* @return the class p k of this d d m template
	*/
	@Override
	public long getClassPK() {
		return _ddmTemplate.getClassPK();
	}

	/**
	* Returns the company ID of this d d m template.
	*
	* @return the company ID of this d d m template
	*/
	@Override
	public long getCompanyId() {
		return _ddmTemplate.getCompanyId();
	}

	/**
	* Returns the create date of this d d m template.
	*
	* @return the create date of this d d m template
	*/
	@Override
	public Date getCreateDate() {
		return _ddmTemplate.getCreateDate();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _ddmTemplate.getDefaultLanguageId();
	}

	/**
	* Returns the description of this d d m template.
	*
	* @return the description of this d d m template
	*/
	@Override
	public java.lang.String getDescription() {
		return _ddmTemplate.getDescription();
	}

	/**
	* Returns the localized description of this d d m template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this d d m template
	*/
	@Override
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
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _ddmTemplate.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this d d m template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this d d m template
	*/
	@Override
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
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _ddmTemplate.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _ddmTemplate.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _ddmTemplate.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this d d m template.
	*
	* @return the locales and localized descriptions of this d d m template
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _ddmTemplate.getDescriptionMap();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmTemplate.getExpandoBridge();
	}

	/**
	* Returns the group ID of this d d m template.
	*
	* @return the group ID of this d d m template
	*/
	@Override
	public long getGroupId() {
		return _ddmTemplate.getGroupId();
	}

	/**
	* Returns the language of this d d m template.
	*
	* @return the language of this d d m template
	*/
	@Override
	public java.lang.String getLanguage() {
		return _ddmTemplate.getLanguage();
	}

	/**
	* Returns the last publish date of this d d m template.
	*
	* @return the last publish date of this d d m template
	*/
	@Override
	public Date getLastPublishDate() {
		return _ddmTemplate.getLastPublishDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getLatestTemplateVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplate.getLatestTemplateVersion();
	}

	/**
	* Returns the mode of this d d m template.
	*
	* @return the mode of this d d m template
	*/
	@Override
	public java.lang.String getMode() {
		return _ddmTemplate.getMode();
	}

	/**
	* Returns the modified date of this d d m template.
	*
	* @return the modified date of this d d m template
	*/
	@Override
	public Date getModifiedDate() {
		return _ddmTemplate.getModifiedDate();
	}

	/**
	* Returns the name of this d d m template.
	*
	* @return the name of this d d m template
	*/
	@Override
	public java.lang.String getName() {
		return _ddmTemplate.getName();
	}

	/**
	* Returns the localized name of this d d m template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this d d m template
	*/
	@Override
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
	@Override
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmTemplate.getName(languageId, useDefault);
	}

	/**
	* Returns the localized name of this d d m template in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this d d m template
	*/
	@Override
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
	@Override
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmTemplate.getName(locale, useDefault);
	}

	@Override
	public java.lang.String getNameCurrentLanguageId() {
		return _ddmTemplate.getNameCurrentLanguageId();
	}

	@Override
	public java.lang.String getNameCurrentValue() {
		return _ddmTemplate.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this d d m template.
	*
	* @return the locales and localized names of this d d m template
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmTemplate.getNameMap();
	}

	/**
	* Returns the primary key of this d d m template.
	*
	* @return the primary key of this d d m template
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmTemplate.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmTemplate.getPrimaryKeyObj();
	}

	/**
	* Returns the resource class name ID of this d d m template.
	*
	* @return the resource class name ID of this d d m template
	*/
	@Override
	public long getResourceClassNameId() {
		return _ddmTemplate.getResourceClassNameId();
	}

	/**
	* Returns the script of this d d m template.
	*
	* @return the script of this d d m template
	*/
	@Override
	public java.lang.String getScript() {
		return _ddmTemplate.getScript();
	}

	/**
	* Returns the small image of this d d m template.
	*
	* @return the small image of this d d m template
	*/
	@Override
	public boolean getSmallImage() {
		return _ddmTemplate.getSmallImage();
	}

	/**
	* Returns the small image ID of this d d m template.
	*
	* @return the small image ID of this d d m template
	*/
	@Override
	public long getSmallImageId() {
		return _ddmTemplate.getSmallImageId();
	}

	@Override
	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplate.getSmallImageType();
	}

	/**
	* Returns the small image u r l of this d d m template.
	*
	* @return the small image u r l of this d d m template
	*/
	@Override
	public java.lang.String getSmallImageURL() {
		return _ddmTemplate.getSmallImageURL();
	}

	/**
	* Returns the template ID of this d d m template.
	*
	* @return the template ID of this d d m template
	*/
	@Override
	public long getTemplateId() {
		return _ddmTemplate.getTemplateId();
	}

	@Override
	public java.lang.String getTemplateImageURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _ddmTemplate.getTemplateImageURL(themeDisplay);
	}

	/**
	* Returns the template key of this d d m template.
	*
	* @return the template key of this d d m template
	*/
	@Override
	public java.lang.String getTemplateKey() {
		return _ddmTemplate.getTemplateKey();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getTemplateVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmTemplate.getTemplateVersion();
	}

	/**
	* Returns the type of this d d m template.
	*
	* @return the type of this d d m template
	*/
	@Override
	public java.lang.String getType() {
		return _ddmTemplate.getType();
	}

	/**
	* Returns the user ID of this d d m template.
	*
	* @return the user ID of this d d m template
	*/
	@Override
	public long getUserId() {
		return _ddmTemplate.getUserId();
	}

	/**
	* Returns the user name of this d d m template.
	*
	* @return the user name of this d d m template
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmTemplate.getUserName();
	}

	/**
	* Returns the user uuid of this d d m template.
	*
	* @return the user uuid of this d d m template
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmTemplate.getUserUuid();
	}

	/**
	* Returns the uuid of this d d m template.
	*
	* @return the uuid of this d d m template
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddmTemplate.getUuid();
	}

	/**
	* Returns the version of this d d m template.
	*
	* @return the version of this d d m template
	*/
	@Override
	public java.lang.String getVersion() {
		return _ddmTemplate.getVersion();
	}

	/**
	* Returns the version user ID of this d d m template.
	*
	* @return the version user ID of this d d m template
	*/
	@Override
	public long getVersionUserId() {
		return _ddmTemplate.getVersionUserId();
	}

	/**
	* Returns the version user name of this d d m template.
	*
	* @return the version user name of this d d m template
	*/
	@Override
	public java.lang.String getVersionUserName() {
		return _ddmTemplate.getVersionUserName();
	}

	/**
	* Returns the version user uuid of this d d m template.
	*
	* @return the version user uuid of this d d m template
	*/
	@Override
	public java.lang.String getVersionUserUuid() {
		return _ddmTemplate.getVersionUserUuid();
	}

	/**
	* Returns the WebDAV URL to access the template.
	*
	* @param themeDisplay the theme display needed to build the URL. It can
	set HTTPS access, the server name, the server port, the path
	context, and the scope group.
	* @param webDAVToken the WebDAV token for the URL
	* @return the WebDAV URL
	*/
	@Override
	public java.lang.String getWebDavURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		java.lang.String webDAVToken) {
		return _ddmTemplate.getWebDavURL(themeDisplay, webDAVToken);
	}

	@Override
	public int hashCode() {
		return _ddmTemplate.hashCode();
	}

	/**
	* Returns <code>true</code> if this d d m template is cacheable.
	*
	* @return <code>true</code> if this d d m template is cacheable; <code>false</code> otherwise
	*/
	@Override
	public boolean isCacheable() {
		return _ddmTemplate.isCacheable();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmTemplate.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmTemplate.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmTemplate.isNew();
	}

	/**
	* Returns <code>true</code> if this d d m template is small image.
	*
	* @return <code>true</code> if this d d m template is small image; <code>false</code> otherwise
	*/
	@Override
	public boolean isSmallImage() {
		return _ddmTemplate.isSmallImage();
	}

	@Override
	public void persist() {
		_ddmTemplate.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_ddmTemplate.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_ddmTemplate.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets whether this d d m template is cacheable.
	*
	* @param cacheable the cacheable of this d d m template
	*/
	@Override
	public void setCacheable(boolean cacheable) {
		_ddmTemplate.setCacheable(cacheable);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmTemplate.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_ddmTemplate.setClassName(className);
	}

	/**
	* Sets the class name ID of this d d m template.
	*
	* @param classNameId the class name ID of this d d m template
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ddmTemplate.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this d d m template.
	*
	* @param classPK the class p k of this d d m template
	*/
	@Override
	public void setClassPK(long classPK) {
		_ddmTemplate.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this d d m template.
	*
	* @param companyId the company ID of this d d m template
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmTemplate.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this d d m template.
	*
	* @param createDate the create date of this d d m template
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmTemplate.setCreateDate(createDate);
	}

	/**
	* Sets the description of this d d m template.
	*
	* @param description the description of this d d m template
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_ddmTemplate.setDescription(description);
	}

	/**
	* Sets the localized description of this d d m template in the language.
	*
	* @param description the localized description of this d d m template
	* @param locale the locale of the language
	*/
	@Override
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
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_ddmTemplate.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_ddmTemplate.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this d d m template from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m template
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_ddmTemplate.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this d d m template from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this d d m template
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmTemplate.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddmTemplate.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddmTemplate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this d d m template.
	*
	* @param groupId the group ID of this d d m template
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmTemplate.setGroupId(groupId);
	}

	/**
	* Sets the language of this d d m template.
	*
	* @param language the language of this d d m template
	*/
	@Override
	public void setLanguage(java.lang.String language) {
		_ddmTemplate.setLanguage(language);
	}

	/**
	* Sets the last publish date of this d d m template.
	*
	* @param lastPublishDate the last publish date of this d d m template
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_ddmTemplate.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the mode of this d d m template.
	*
	* @param mode the mode of this d d m template
	*/
	@Override
	public void setMode(java.lang.String mode) {
		_ddmTemplate.setMode(mode);
	}

	/**
	* Sets the modified date of this d d m template.
	*
	* @param modifiedDate the modified date of this d d m template
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmTemplate.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this d d m template.
	*
	* @param name the name of this d d m template
	*/
	@Override
	public void setName(java.lang.String name) {
		_ddmTemplate.setName(name);
	}

	/**
	* Sets the localized name of this d d m template in the language.
	*
	* @param name the localized name of this d d m template
	* @param locale the locale of the language
	*/
	@Override
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
	@Override
	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmTemplate.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(java.lang.String languageId) {
		_ddmTemplate.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this d d m template from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d m template
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmTemplate.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this d d m template from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this d d m template
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmTemplate.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmTemplate.setNew(n);
	}

	/**
	* Sets the primary key of this d d m template.
	*
	* @param primaryKey the primary key of this d d m template
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmTemplate.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the resource class name ID of this d d m template.
	*
	* @param resourceClassNameId the resource class name ID of this d d m template
	*/
	@Override
	public void setResourceClassNameId(long resourceClassNameId) {
		_ddmTemplate.setResourceClassNameId(resourceClassNameId);
	}

	/**
	* Sets the script of this d d m template.
	*
	* @param script the script of this d d m template
	*/
	@Override
	public void setScript(java.lang.String script) {
		_ddmTemplate.setScript(script);
	}

	/**
	* Sets whether this d d m template is small image.
	*
	* @param smallImage the small image of this d d m template
	*/
	@Override
	public void setSmallImage(boolean smallImage) {
		_ddmTemplate.setSmallImage(smallImage);
	}

	/**
	* Sets the small image ID of this d d m template.
	*
	* @param smallImageId the small image ID of this d d m template
	*/
	@Override
	public void setSmallImageId(long smallImageId) {
		_ddmTemplate.setSmallImageId(smallImageId);
	}

	@Override
	public void setSmallImageType(java.lang.String smallImageType) {
		_ddmTemplate.setSmallImageType(smallImageType);
	}

	/**
	* Sets the small image u r l of this d d m template.
	*
	* @param smallImageURL the small image u r l of this d d m template
	*/
	@Override
	public void setSmallImageURL(java.lang.String smallImageURL) {
		_ddmTemplate.setSmallImageURL(smallImageURL);
	}

	/**
	* Sets the template ID of this d d m template.
	*
	* @param templateId the template ID of this d d m template
	*/
	@Override
	public void setTemplateId(long templateId) {
		_ddmTemplate.setTemplateId(templateId);
	}

	/**
	* Sets the template key of this d d m template.
	*
	* @param templateKey the template key of this d d m template
	*/
	@Override
	public void setTemplateKey(java.lang.String templateKey) {
		_ddmTemplate.setTemplateKey(templateKey);
	}

	/**
	* Sets the type of this d d m template.
	*
	* @param type the type of this d d m template
	*/
	@Override
	public void setType(java.lang.String type) {
		_ddmTemplate.setType(type);
	}

	/**
	* Sets the user ID of this d d m template.
	*
	* @param userId the user ID of this d d m template
	*/
	@Override
	public void setUserId(long userId) {
		_ddmTemplate.setUserId(userId);
	}

	/**
	* Sets the user name of this d d m template.
	*
	* @param userName the user name of this d d m template
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmTemplate.setUserName(userName);
	}

	/**
	* Sets the user uuid of this d d m template.
	*
	* @param userUuid the user uuid of this d d m template
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmTemplate.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this d d m template.
	*
	* @param uuid the uuid of this d d m template
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddmTemplate.setUuid(uuid);
	}

	/**
	* Sets the version of this d d m template.
	*
	* @param version the version of this d d m template
	*/
	@Override
	public void setVersion(java.lang.String version) {
		_ddmTemplate.setVersion(version);
	}

	/**
	* Sets the version user ID of this d d m template.
	*
	* @param versionUserId the version user ID of this d d m template
	*/
	@Override
	public void setVersionUserId(long versionUserId) {
		_ddmTemplate.setVersionUserId(versionUserId);
	}

	/**
	* Sets the version user name of this d d m template.
	*
	* @param versionUserName the version user name of this d d m template
	*/
	@Override
	public void setVersionUserName(java.lang.String versionUserName) {
		_ddmTemplate.setVersionUserName(versionUserName);
	}

	/**
	* Sets the version user uuid of this d d m template.
	*
	* @param versionUserUuid the version user uuid of this d d m template
	*/
	@Override
	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_ddmTemplate.setVersionUserUuid(versionUserUuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.dynamic.data.mapping.model.DDMTemplate> toCacheModel() {
		return _ddmTemplate.toCacheModel();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplate toEscapedModel() {
		return new DDMTemplateWrapper(_ddmTemplate.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmTemplate.toString();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplate toUnescapedModel() {
		return new DDMTemplateWrapper(_ddmTemplate.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmTemplate.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMTemplateWrapper)) {
			return false;
		}

		DDMTemplateWrapper ddmTemplateWrapper = (DDMTemplateWrapper)obj;

		if (Validator.equals(_ddmTemplate, ddmTemplateWrapper._ddmTemplate)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmTemplate.getStagedModelType();
	}

	@Override
	public DDMTemplate getWrappedModel() {
		return _ddmTemplate;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmTemplate.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmTemplate.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmTemplate.resetOriginalValues();
	}

	private final DDMTemplate _ddmTemplate;
}
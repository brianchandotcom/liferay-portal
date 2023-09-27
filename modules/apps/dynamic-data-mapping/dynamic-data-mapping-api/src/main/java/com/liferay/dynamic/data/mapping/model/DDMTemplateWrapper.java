/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DDMTemplate}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplate
 * @generated
 */
public class DDMTemplateWrapper
	extends BaseModelWrapper<DDMTemplate>
	implements DDMTemplate, ModelWrapper<DDMTemplate> {

	public DDMTemplateWrapper(DDMTemplate ddmTemplate) {
		super(ddmTemplate);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
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
		attributes.put("cacheable", isCacheable());
		attributes.put("smallImage", isSmallImage());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("smallImageURL", getSmallImageURL());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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
	public DDMTemplate cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the cacheable of this ddm template.
	 *
	 * @return the cacheable of this ddm template
	 */
	@Override
	public boolean getCacheable() {
		return model.getCacheable();
	}

	/**
	 * Returns the fully qualified class name of this ddm template.
	 *
	 * @return the fully qualified class name of this ddm template
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this ddm template.
	 *
	 * @return the class name ID of this ddm template
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this ddm template.
	 *
	 * @return the class pk of this ddm template
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this ddm template.
	 *
	 * @return the company ID of this ddm template
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ddm template.
	 *
	 * @return the create date of this ddm template
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this ddm template.
	 *
	 * @return the ct collection ID of this ddm template
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this ddm template.
	 *
	 * @return the description of this ddm template
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the localized description of this ddm template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this ddm template
	 */
	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	/**
	 * Returns the localized description of this ddm template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this ddm template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	/**
	 * Returns the localized description of this ddm template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this ddm template
	 */
	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	/**
	 * Returns the localized description of this ddm template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this ddm template
	 */
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return model.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return model.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return model.getDescriptionCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized descriptions of this ddm template.
	 *
	 * @return the locales and localized descriptions of this ddm template
	 */
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	/**
	 * Returns the group ID of this ddm template.
	 *
	 * @return the group ID of this ddm template
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the language of this ddm template.
	 *
	 * @return the language of this ddm template
	 */
	@Override
	public String getLanguage() {
		return model.getLanguage();
	}

	/**
	 * Returns the last publish date of this ddm template.
	 *
	 * @return the last publish date of this ddm template
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	@Override
	public DDMTemplateVersion getLatestTemplateVersion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getLatestTemplateVersion();
	}

	/**
	 * Returns the mode of this ddm template.
	 *
	 * @return the mode of this ddm template
	 */
	@Override
	public String getMode() {
		return model.getMode();
	}

	/**
	 * Returns the modified date of this ddm template.
	 *
	 * @return the modified date of this ddm template
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this ddm template.
	 *
	 * @return the mvcc version of this ddm template
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this ddm template.
	 *
	 * @return the name of this ddm template
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the localized name of this ddm template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this ddm template
	 */
	@Override
	public String getName(java.util.Locale locale) {
		return model.getName(locale);
	}

	/**
	 * Returns the localized name of this ddm template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this ddm template. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return model.getName(locale, useDefault);
	}

	/**
	 * Returns the localized name of this ddm template in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this ddm template
	 */
	@Override
	public String getName(String languageId) {
		return model.getName(languageId);
	}

	/**
	 * Returns the localized name of this ddm template in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this ddm template
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
	 * Returns a map of the locales and localized names of this ddm template.
	 *
	 * @return the locales and localized names of this ddm template
	 */
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return model.getNameMap();
	}

	/**
	 * Returns the primary key of this ddm template.
	 *
	 * @return the primary key of this ddm template
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public String getResourceClassName() {
		return model.getResourceClassName();
	}

	/**
	 * Returns the resource class name ID of this ddm template.
	 *
	 * @return the resource class name ID of this ddm template
	 */
	@Override
	public long getResourceClassNameId() {
		return model.getResourceClassNameId();
	}

	/**
	 * Returns the script of this ddm template.
	 *
	 * @return the script of this ddm template
	 */
	@Override
	public String getScript() {
		return model.getScript();
	}

	/**
	 * Returns the small image of this ddm template.
	 *
	 * @return the small image of this ddm template
	 */
	@Override
	public boolean getSmallImage() {
		return model.getSmallImage();
	}

	/**
	 * Returns the small image ID of this ddm template.
	 *
	 * @return the small image ID of this ddm template
	 */
	@Override
	public long getSmallImageId() {
		return model.getSmallImageId();
	}

	@Override
	public String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getSmallImageType();
	}

	/**
	 * Returns the small image url of this ddm template.
	 *
	 * @return the small image url of this ddm template
	 */
	@Override
	public String getSmallImageURL() {
		return model.getSmallImageURL();
	}

	/**
	 * Returns the template ID of this ddm template.
	 *
	 * @return the template ID of this ddm template
	 */
	@Override
	public long getTemplateId() {
		return model.getTemplateId();
	}

	@Override
	public String getTemplateImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {

		return model.getTemplateImageURL(themeDisplay);
	}

	/**
	 * Returns the template key of this ddm template.
	 *
	 * @return the template key of this ddm template
	 */
	@Override
	public String getTemplateKey() {
		return model.getTemplateKey();
	}

	@Override
	public DDMTemplateVersion getTemplateVersion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTemplateVersion();
	}

	/**
	 * Returns the type of this ddm template.
	 *
	 * @return the type of this ddm template
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this ddm template.
	 *
	 * @return the user ID of this ddm template
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this ddm template.
	 *
	 * @return the user name of this ddm template
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this ddm template.
	 *
	 * @return the user uuid of this ddm template
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this ddm template.
	 *
	 * @return the uuid of this ddm template
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this ddm template.
	 *
	 * @return the version of this ddm template
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns the version user ID of this ddm template.
	 *
	 * @return the version user ID of this ddm template
	 */
	@Override
	public long getVersionUserId() {
		return model.getVersionUserId();
	}

	/**
	 * Returns the version user name of this ddm template.
	 *
	 * @return the version user name of this ddm template
	 */
	@Override
	public String getVersionUserName() {
		return model.getVersionUserName();
	}

	/**
	 * Returns the version user uuid of this ddm template.
	 *
	 * @return the version user uuid of this ddm template
	 */
	@Override
	public String getVersionUserUuid() {
		return model.getVersionUserUuid();
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
	public String getWebDavURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay,
		String webDAVToken) {

		return model.getWebDavURL(themeDisplay, webDAVToken);
	}

	/**
	 * Returns <code>true</code> if this ddm template is cacheable.
	 *
	 * @return <code>true</code> if this ddm template is cacheable; <code>false</code> otherwise
	 */
	@Override
	public boolean isCacheable() {
		return model.isCacheable();
	}

	/**
	 * Returns <code>true</code> if this ddm template is small image.
	 *
	 * @return <code>true</code> if this ddm template is small image; <code>false</code> otherwise
	 */
	@Override
	public boolean isSmallImage() {
		return model.isSmallImage();
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
	 * Sets whether this ddm template is cacheable.
	 *
	 * @param cacheable the cacheable of this ddm template
	 */
	@Override
	public void setCacheable(boolean cacheable) {
		model.setCacheable(cacheable);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this ddm template.
	 *
	 * @param classNameId the class name ID of this ddm template
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this ddm template.
	 *
	 * @param classPK the class pk of this ddm template
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this ddm template.
	 *
	 * @param companyId the company ID of this ddm template
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ddm template.
	 *
	 * @param createDate the create date of this ddm template
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this ddm template.
	 *
	 * @param ctCollectionId the ct collection ID of this ddm template
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this ddm template.
	 *
	 * @param description the description of this ddm template
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the localized description of this ddm template in the language.
	 *
	 * @param description the localized description of this ddm template
	 * @param locale the locale of the language
	 */
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		model.setDescription(description, locale);
	}

	/**
	 * Sets the localized description of this ddm template in the language, and sets the default locale.
	 *
	 * @param description the localized description of this ddm template
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescription(
		String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		model.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized descriptions of this ddm template from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this ddm template
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the localized descriptions of this ddm template from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this ddm template
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {

		model.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	 * Sets the group ID of this ddm template.
	 *
	 * @param groupId the group ID of this ddm template
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the language of this ddm template.
	 *
	 * @param language the language of this ddm template
	 */
	@Override
	public void setLanguage(String language) {
		model.setLanguage(language);
	}

	/**
	 * Sets the last publish date of this ddm template.
	 *
	 * @param lastPublishDate the last publish date of this ddm template
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the mode of this ddm template.
	 *
	 * @param mode the mode of this ddm template
	 */
	@Override
	public void setMode(String mode) {
		model.setMode(mode);
	}

	/**
	 * Sets the modified date of this ddm template.
	 *
	 * @param modifiedDate the modified date of this ddm template
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this ddm template.
	 *
	 * @param mvccVersion the mvcc version of this ddm template
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this ddm template.
	 *
	 * @param name the name of this ddm template
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the localized name of this ddm template in the language.
	 *
	 * @param name the localized name of this ddm template
	 * @param locale the locale of the language
	 */
	@Override
	public void setName(String name, java.util.Locale locale) {
		model.setName(name, locale);
	}

	/**
	 * Sets the localized name of this ddm template in the language, and sets the default locale.
	 *
	 * @param name the localized name of this ddm template
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
	 * Sets the localized names of this ddm template from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this ddm template
	 */
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		model.setNameMap(nameMap);
	}

	/**
	 * Sets the localized names of this ddm template from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this ddm template
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setNameMap(
		Map<java.util.Locale, String> nameMap, java.util.Locale defaultLocale) {

		model.setNameMap(nameMap, defaultLocale);
	}

	/**
	 * Sets the primary key of this ddm template.
	 *
	 * @param primaryKey the primary key of this ddm template
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public void setResourceClassName(String resourceClassName) {
		model.setResourceClassName(resourceClassName);
	}

	/**
	 * Sets the resource class name ID of this ddm template.
	 *
	 * @param resourceClassNameId the resource class name ID of this ddm template
	 */
	@Override
	public void setResourceClassNameId(long resourceClassNameId) {
		model.setResourceClassNameId(resourceClassNameId);
	}

	/**
	 * Sets the script of this ddm template.
	 *
	 * @param script the script of this ddm template
	 */
	@Override
	public void setScript(String script) {
		model.setScript(script);
	}

	/**
	 * Sets whether this ddm template is small image.
	 *
	 * @param smallImage the small image of this ddm template
	 */
	@Override
	public void setSmallImage(boolean smallImage) {
		model.setSmallImage(smallImage);
	}

	/**
	 * Sets the small image ID of this ddm template.
	 *
	 * @param smallImageId the small image ID of this ddm template
	 */
	@Override
	public void setSmallImageId(long smallImageId) {
		model.setSmallImageId(smallImageId);
	}

	@Override
	public void setSmallImageType(String smallImageType) {
		model.setSmallImageType(smallImageType);
	}

	/**
	 * Sets the small image url of this ddm template.
	 *
	 * @param smallImageURL the small image url of this ddm template
	 */
	@Override
	public void setSmallImageURL(String smallImageURL) {
		model.setSmallImageURL(smallImageURL);
	}

	/**
	 * Sets the template ID of this ddm template.
	 *
	 * @param templateId the template ID of this ddm template
	 */
	@Override
	public void setTemplateId(long templateId) {
		model.setTemplateId(templateId);
	}

	/**
	 * Sets the template key of this ddm template.
	 *
	 * @param templateKey the template key of this ddm template
	 */
	@Override
	public void setTemplateKey(String templateKey) {
		model.setTemplateKey(templateKey);
	}

	/**
	 * Sets the type of this ddm template.
	 *
	 * @param type the type of this ddm template
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this ddm template.
	 *
	 * @param userId the user ID of this ddm template
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this ddm template.
	 *
	 * @param userName the user name of this ddm template
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this ddm template.
	 *
	 * @param userUuid the user uuid of this ddm template
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this ddm template.
	 *
	 * @param uuid the uuid of this ddm template
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this ddm template.
	 *
	 * @param version the version of this ddm template
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	/**
	 * Sets the version user ID of this ddm template.
	 *
	 * @param versionUserId the version user ID of this ddm template
	 */
	@Override
	public void setVersionUserId(long versionUserId) {
		model.setVersionUserId(versionUserId);
	}

	/**
	 * Sets the version user name of this ddm template.
	 *
	 * @param versionUserName the version user name of this ddm template
	 */
	@Override
	public void setVersionUserName(String versionUserName) {
		model.setVersionUserName(versionUserName);
	}

	/**
	 * Sets the version user uuid of this ddm template.
	 *
	 * @param versionUserUuid the version user uuid of this ddm template
	 */
	@Override
	public void setVersionUserUuid(String versionUserUuid) {
		model.setVersionUserUuid(versionUserUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<DDMTemplate, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DDMTemplate, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DDMTemplateWrapper wrap(DDMTemplate ddmTemplate) {
		return new DDMTemplateWrapper(ddmTemplate);
	}

}
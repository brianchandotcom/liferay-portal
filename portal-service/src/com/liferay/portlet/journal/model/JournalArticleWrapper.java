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

package com.liferay.portlet.journal.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link JournalArticle}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticle
 * @generated
 */
public class JournalArticleWrapper implements JournalArticle,
	ModelWrapper<JournalArticle> {
	public JournalArticleWrapper(JournalArticle journalArticle) {
		_journalArticle = journalArticle;
	}

	public Class<?> getModelClass() {
		return JournalArticle.class;
	}

	public String getModelClassName() {
		return JournalArticle.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("id", getId());
		attributes.put("resourcePrimKey", getResourcePrimKey());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("folderId", getFolderId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("articleId", getArticleId());
		attributes.put("version", getVersion());
		attributes.put("title", getTitle());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("description", getDescription());
		attributes.put("content", getContent());
		attributes.put("type", getType());
		attributes.put("structureId", getStructureId());
		attributes.put("templateId", getTemplateId());
		attributes.put("layoutUuid", getLayoutUuid());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("reviewDate", getReviewDate());
		attributes.put("indexable", getIndexable());
		attributes.put("smallImage", getSmallImage());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("smallImageURL", getSmallImageURL());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
		}

		Long resourcePrimKey = (Long)attributes.get("resourcePrimKey");

		if (resourcePrimKey != null) {
			setResourcePrimKey(resourcePrimKey);
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

		Long folderId = (Long)attributes.get("folderId");

		if (folderId != null) {
			setFolderId(folderId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String articleId = (String)attributes.get("articleId");

		if (articleId != null) {
			setArticleId(articleId);
		}

		Double version = (Double)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String structureId = (String)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}

		String templateId = (String)attributes.get("templateId");

		if (templateId != null) {
			setTemplateId(templateId);
		}

		String layoutUuid = (String)attributes.get("layoutUuid");

		if (layoutUuid != null) {
			setLayoutUuid(layoutUuid);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date reviewDate = (Date)attributes.get("reviewDate");

		if (reviewDate != null) {
			setReviewDate(reviewDate);
		}

		Boolean indexable = (Boolean)attributes.get("indexable");

		if (indexable != null) {
			setIndexable(indexable);
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

	/**
	* Returns the primary key of this journal article.
	*
	* @return the primary key of this journal article
	*/
	public long getPrimaryKey() {
		return _journalArticle.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal article.
	*
	* @param primaryKey the primary key of this journal article
	*/
	public void setPrimaryKey(long primaryKey) {
		_journalArticle.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this journal article.
	*
	* @return the uuid of this journal article
	*/
	public java.lang.String getUuid() {
		return _journalArticle.getUuid();
	}

	/**
	* Sets the uuid of this journal article.
	*
	* @param uuid the uuid of this journal article
	*/
	public void setUuid(java.lang.String uuid) {
		_journalArticle.setUuid(uuid);
	}

	/**
	* Returns the ID of this journal article.
	*
	* @return the ID of this journal article
	*/
	public long getId() {
		return _journalArticle.getId();
	}

	/**
	* Sets the ID of this journal article.
	*
	* @param id the ID of this journal article
	*/
	public void setId(long id) {
		_journalArticle.setId(id);
	}

	/**
	* Returns the resource prim key of this journal article.
	*
	* @return the resource prim key of this journal article
	*/
	public long getResourcePrimKey() {
		return _journalArticle.getResourcePrimKey();
	}

	/**
	* Sets the resource prim key of this journal article.
	*
	* @param resourcePrimKey the resource prim key of this journal article
	*/
	public void setResourcePrimKey(long resourcePrimKey) {
		_journalArticle.setResourcePrimKey(resourcePrimKey);
	}

	public boolean isResourceMain() {
		return _journalArticle.isResourceMain();
	}

	/**
	* Returns the group ID of this journal article.
	*
	* @return the group ID of this journal article
	*/
	public long getGroupId() {
		return _journalArticle.getGroupId();
	}

	/**
	* Sets the group ID of this journal article.
	*
	* @param groupId the group ID of this journal article
	*/
	public void setGroupId(long groupId) {
		_journalArticle.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this journal article.
	*
	* @return the company ID of this journal article
	*/
	public long getCompanyId() {
		return _journalArticle.getCompanyId();
	}

	/**
	* Sets the company ID of this journal article.
	*
	* @param companyId the company ID of this journal article
	*/
	public void setCompanyId(long companyId) {
		_journalArticle.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this journal article.
	*
	* @return the user ID of this journal article
	*/
	public long getUserId() {
		return _journalArticle.getUserId();
	}

	/**
	* Sets the user ID of this journal article.
	*
	* @param userId the user ID of this journal article
	*/
	public void setUserId(long userId) {
		_journalArticle.setUserId(userId);
	}

	/**
	* Returns the user uuid of this journal article.
	*
	* @return the user uuid of this journal article
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getUserUuid();
	}

	/**
	* Sets the user uuid of this journal article.
	*
	* @param userUuid the user uuid of this journal article
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_journalArticle.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this journal article.
	*
	* @return the user name of this journal article
	*/
	public java.lang.String getUserName() {
		return _journalArticle.getUserName();
	}

	/**
	* Sets the user name of this journal article.
	*
	* @param userName the user name of this journal article
	*/
	public void setUserName(java.lang.String userName) {
		_journalArticle.setUserName(userName);
	}

	/**
	* Returns the create date of this journal article.
	*
	* @return the create date of this journal article
	*/
	public java.util.Date getCreateDate() {
		return _journalArticle.getCreateDate();
	}

	/**
	* Sets the create date of this journal article.
	*
	* @param createDate the create date of this journal article
	*/
	public void setCreateDate(java.util.Date createDate) {
		_journalArticle.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this journal article.
	*
	* @return the modified date of this journal article
	*/
	public java.util.Date getModifiedDate() {
		return _journalArticle.getModifiedDate();
	}

	/**
	* Sets the modified date of this journal article.
	*
	* @param modifiedDate the modified date of this journal article
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalArticle.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the folder ID of this journal article.
	*
	* @return the folder ID of this journal article
	*/
	public long getFolderId() {
		return _journalArticle.getFolderId();
	}

	/**
	* Sets the folder ID of this journal article.
	*
	* @param folderId the folder ID of this journal article
	*/
	public void setFolderId(long folderId) {
		_journalArticle.setFolderId(folderId);
	}

	/**
	* Returns the fully qualified class name of this journal article.
	*
	* @return the fully qualified class name of this journal article
	*/
	public java.lang.String getClassName() {
		return _journalArticle.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_journalArticle.setClassName(className);
	}

	/**
	* Returns the class name ID of this journal article.
	*
	* @return the class name ID of this journal article
	*/
	public long getClassNameId() {
		return _journalArticle.getClassNameId();
	}

	/**
	* Sets the class name ID of this journal article.
	*
	* @param classNameId the class name ID of this journal article
	*/
	public void setClassNameId(long classNameId) {
		_journalArticle.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this journal article.
	*
	* @return the class p k of this journal article
	*/
	public long getClassPK() {
		return _journalArticle.getClassPK();
	}

	/**
	* Sets the class p k of this journal article.
	*
	* @param classPK the class p k of this journal article
	*/
	public void setClassPK(long classPK) {
		_journalArticle.setClassPK(classPK);
	}

	/**
	* Returns the article ID of this journal article.
	*
	* @return the article ID of this journal article
	*/
	public java.lang.String getArticleId() {
		return _journalArticle.getArticleId();
	}

	/**
	* Sets the article ID of this journal article.
	*
	* @param articleId the article ID of this journal article
	*/
	public void setArticleId(java.lang.String articleId) {
		_journalArticle.setArticleId(articleId);
	}

	/**
	* Returns the version of this journal article.
	*
	* @return the version of this journal article
	*/
	public double getVersion() {
		return _journalArticle.getVersion();
	}

	/**
	* Sets the version of this journal article.
	*
	* @param version the version of this journal article
	*/
	public void setVersion(double version) {
		_journalArticle.setVersion(version);
	}

	/**
	* Returns the title of this journal article.
	*
	* @return the title of this journal article
	*/
	public java.lang.String getTitle() {
		return _journalArticle.getTitle();
	}

	/**
	* Returns the localized title of this journal article in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this journal article
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _journalArticle.getTitle(locale);
	}

	/**
	* Returns the localized title of this journal article in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this journal article. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _journalArticle.getTitle(locale, useDefault);
	}

	/**
	* Returns the localized title of this journal article in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this journal article
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _journalArticle.getTitle(languageId);
	}

	/**
	* Returns the localized title of this journal article in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this journal article
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _journalArticle.getTitle(languageId, useDefault);
	}

	public java.lang.String getTitleCurrentLanguageId() {
		return _journalArticle.getTitleCurrentLanguageId();
	}

	public java.lang.String getTitleCurrentValue() {
		return _journalArticle.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this journal article.
	*
	* @return the locales and localized titles of this journal article
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _journalArticle.getTitleMap();
	}

	/**
	* Sets the title of this journal article.
	*
	* @param title the title of this journal article
	*/
	public void setTitle(java.lang.String title) {
		_journalArticle.setTitle(title);
	}

	/**
	* Sets the localized title of this journal article in the language.
	*
	* @param title the localized title of this journal article
	* @param locale the locale of the language
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_journalArticle.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this journal article in the language, and sets the default locale.
	*
	* @param title the localized title of this journal article
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_journalArticle.setTitle(title, locale, defaultLocale);
	}

	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_journalArticle.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this journal article from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this journal article
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_journalArticle.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this journal article from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this journal article
	* @param defaultLocale the default locale
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_journalArticle.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Returns the url title of this journal article.
	*
	* @return the url title of this journal article
	*/
	public java.lang.String getUrlTitle() {
		return _journalArticle.getUrlTitle();
	}

	/**
	* Sets the url title of this journal article.
	*
	* @param urlTitle the url title of this journal article
	*/
	public void setUrlTitle(java.lang.String urlTitle) {
		_journalArticle.setUrlTitle(urlTitle);
	}

	/**
	* Returns the description of this journal article.
	*
	* @return the description of this journal article
	*/
	public java.lang.String getDescription() {
		return _journalArticle.getDescription();
	}

	/**
	* Returns the localized description of this journal article in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this journal article
	*/
	public java.lang.String getDescription(java.util.Locale locale) {
		return _journalArticle.getDescription(locale);
	}

	/**
	* Returns the localized description of this journal article in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this journal article. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _journalArticle.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this journal article in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this journal article
	*/
	public java.lang.String getDescription(java.lang.String languageId) {
		return _journalArticle.getDescription(languageId);
	}

	/**
	* Returns the localized description of this journal article in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this journal article
	*/
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _journalArticle.getDescription(languageId, useDefault);
	}

	public java.lang.String getDescriptionCurrentLanguageId() {
		return _journalArticle.getDescriptionCurrentLanguageId();
	}

	public java.lang.String getDescriptionCurrentValue() {
		return _journalArticle.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this journal article.
	*
	* @return the locales and localized descriptions of this journal article
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _journalArticle.getDescriptionMap();
	}

	/**
	* Sets the description of this journal article.
	*
	* @param description the description of this journal article
	*/
	public void setDescription(java.lang.String description) {
		_journalArticle.setDescription(description);
	}

	/**
	* Sets the localized description of this journal article in the language.
	*
	* @param description the localized description of this journal article
	* @param locale the locale of the language
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale) {
		_journalArticle.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this journal article in the language, and sets the default locale.
	*
	* @param description the localized description of this journal article
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_journalArticle.setDescription(description, locale, defaultLocale);
	}

	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_journalArticle.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this journal article from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this journal article
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap) {
		_journalArticle.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this journal article from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this journal article
	* @param defaultLocale the default locale
	*/
	public void setDescriptionMap(
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_journalArticle.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Returns the content of this journal article.
	*
	* @return the content of this journal article
	*/
	public java.lang.String getContent() {
		return _journalArticle.getContent();
	}

	/**
	* Sets the content of this journal article.
	*
	* @param content the content of this journal article
	*/
	public void setContent(java.lang.String content) {
		_journalArticle.setContent(content);
	}

	/**
	* Returns the type of this journal article.
	*
	* @return the type of this journal article
	*/
	public java.lang.String getType() {
		return _journalArticle.getType();
	}

	/**
	* Sets the type of this journal article.
	*
	* @param type the type of this journal article
	*/
	public void setType(java.lang.String type) {
		_journalArticle.setType(type);
	}

	/**
	* Returns the structure ID of this journal article.
	*
	* @return the structure ID of this journal article
	*/
	public java.lang.String getStructureId() {
		return _journalArticle.getStructureId();
	}

	/**
	* Sets the structure ID of this journal article.
	*
	* @param structureId the structure ID of this journal article
	*/
	public void setStructureId(java.lang.String structureId) {
		_journalArticle.setStructureId(structureId);
	}

	/**
	* Returns the template ID of this journal article.
	*
	* @return the template ID of this journal article
	*/
	public java.lang.String getTemplateId() {
		return _journalArticle.getTemplateId();
	}

	/**
	* Sets the template ID of this journal article.
	*
	* @param templateId the template ID of this journal article
	*/
	public void setTemplateId(java.lang.String templateId) {
		_journalArticle.setTemplateId(templateId);
	}

	/**
	* Returns the layout uuid of this journal article.
	*
	* @return the layout uuid of this journal article
	*/
	public java.lang.String getLayoutUuid() {
		return _journalArticle.getLayoutUuid();
	}

	/**
	* Sets the layout uuid of this journal article.
	*
	* @param layoutUuid the layout uuid of this journal article
	*/
	public void setLayoutUuid(java.lang.String layoutUuid) {
		_journalArticle.setLayoutUuid(layoutUuid);
	}

	/**
	* Returns the display date of this journal article.
	*
	* @return the display date of this journal article
	*/
	public java.util.Date getDisplayDate() {
		return _journalArticle.getDisplayDate();
	}

	/**
	* Sets the display date of this journal article.
	*
	* @param displayDate the display date of this journal article
	*/
	public void setDisplayDate(java.util.Date displayDate) {
		_journalArticle.setDisplayDate(displayDate);
	}

	/**
	* Returns the expiration date of this journal article.
	*
	* @return the expiration date of this journal article
	*/
	public java.util.Date getExpirationDate() {
		return _journalArticle.getExpirationDate();
	}

	/**
	* Sets the expiration date of this journal article.
	*
	* @param expirationDate the expiration date of this journal article
	*/
	public void setExpirationDate(java.util.Date expirationDate) {
		_journalArticle.setExpirationDate(expirationDate);
	}

	/**
	* Returns the review date of this journal article.
	*
	* @return the review date of this journal article
	*/
	public java.util.Date getReviewDate() {
		return _journalArticle.getReviewDate();
	}

	/**
	* Sets the review date of this journal article.
	*
	* @param reviewDate the review date of this journal article
	*/
	public void setReviewDate(java.util.Date reviewDate) {
		_journalArticle.setReviewDate(reviewDate);
	}

	/**
	* Returns the indexable of this journal article.
	*
	* @return the indexable of this journal article
	*/
	public boolean getIndexable() {
		return _journalArticle.getIndexable();
	}

	/**
	* Returns <code>true</code> if this journal article is indexable.
	*
	* @return <code>true</code> if this journal article is indexable; <code>false</code> otherwise
	*/
	public boolean isIndexable() {
		return _journalArticle.isIndexable();
	}

	/**
	* Sets whether this journal article is indexable.
	*
	* @param indexable the indexable of this journal article
	*/
	public void setIndexable(boolean indexable) {
		_journalArticle.setIndexable(indexable);
	}

	/**
	* Returns the small image of this journal article.
	*
	* @return the small image of this journal article
	*/
	public boolean getSmallImage() {
		return _journalArticle.getSmallImage();
	}

	/**
	* Returns <code>true</code> if this journal article is small image.
	*
	* @return <code>true</code> if this journal article is small image; <code>false</code> otherwise
	*/
	public boolean isSmallImage() {
		return _journalArticle.isSmallImage();
	}

	/**
	* Sets whether this journal article is small image.
	*
	* @param smallImage the small image of this journal article
	*/
	public void setSmallImage(boolean smallImage) {
		_journalArticle.setSmallImage(smallImage);
	}

	/**
	* Returns the small image ID of this journal article.
	*
	* @return the small image ID of this journal article
	*/
	public long getSmallImageId() {
		return _journalArticle.getSmallImageId();
	}

	/**
	* Sets the small image ID of this journal article.
	*
	* @param smallImageId the small image ID of this journal article
	*/
	public void setSmallImageId(long smallImageId) {
		_journalArticle.setSmallImageId(smallImageId);
	}

	/**
	* Returns the small image u r l of this journal article.
	*
	* @return the small image u r l of this journal article
	*/
	public java.lang.String getSmallImageURL() {
		return _journalArticle.getSmallImageURL();
	}

	/**
	* Sets the small image u r l of this journal article.
	*
	* @param smallImageURL the small image u r l of this journal article
	*/
	public void setSmallImageURL(java.lang.String smallImageURL) {
		_journalArticle.setSmallImageURL(smallImageURL);
	}

	/**
	* Returns the status of this journal article.
	*
	* @return the status of this journal article
	*/
	public int getStatus() {
		return _journalArticle.getStatus();
	}

	/**
	* Sets the status of this journal article.
	*
	* @param status the status of this journal article
	*/
	public void setStatus(int status) {
		_journalArticle.setStatus(status);
	}

	/**
	* Returns the status by user ID of this journal article.
	*
	* @return the status by user ID of this journal article
	*/
	public long getStatusByUserId() {
		return _journalArticle.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this journal article.
	*
	* @param statusByUserId the status by user ID of this journal article
	*/
	public void setStatusByUserId(long statusByUserId) {
		_journalArticle.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this journal article.
	*
	* @return the status by user uuid of this journal article
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this journal article.
	*
	* @param statusByUserUuid the status by user uuid of this journal article
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_journalArticle.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this journal article.
	*
	* @return the status by user name of this journal article
	*/
	public java.lang.String getStatusByUserName() {
		return _journalArticle.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this journal article.
	*
	* @param statusByUserName the status by user name of this journal article
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_journalArticle.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this journal article.
	*
	* @return the status date of this journal article
	*/
	public java.util.Date getStatusDate() {
		return _journalArticle.getStatusDate();
	}

	/**
	* Sets the status date of this journal article.
	*
	* @param statusDate the status date of this journal article
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_journalArticle.setStatusDate(statusDate);
	}

	/**
	* @deprecated Renamed to {@link #isApproved()}
	*/
	public boolean getApproved() {
		return _journalArticle.getApproved();
	}

	/**
	* Returns <code>true</code> if this journal article is approved.
	*
	* @return <code>true</code> if this journal article is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _journalArticle.isApproved();
	}

	/**
	* Returns <code>true</code> if this journal article is denied.
	*
	* @return <code>true</code> if this journal article is denied; <code>false</code> otherwise
	*/
	public boolean isDenied() {
		return _journalArticle.isDenied();
	}

	/**
	* Returns <code>true</code> if this journal article is a draft.
	*
	* @return <code>true</code> if this journal article is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _journalArticle.isDraft();
	}

	/**
	* Returns <code>true</code> if this journal article is expired.
	*
	* @return <code>true</code> if this journal article is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _journalArticle.isExpired();
	}

	/**
	* Returns <code>true</code> if this journal article is inactive.
	*
	* @return <code>true</code> if this journal article is inactive; <code>false</code> otherwise
	*/
	public boolean isInactive() {
		return _journalArticle.isInactive();
	}

	/**
	* Returns <code>true</code> if this journal article is incomplete.
	*
	* @return <code>true</code> if this journal article is incomplete; <code>false</code> otherwise
	*/
	public boolean isIncomplete() {
		return _journalArticle.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this journal article is in the Recycle Bin.
	*
	* @return <code>true</code> if this journal article is in the Recycle Bin; <code>false</code> otherwise
	*/
	public boolean isInTrash() {
		return _journalArticle.isInTrash();
	}

	/**
	* Returns <code>true</code> if this journal article is pending.
	*
	* @return <code>true</code> if this journal article is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _journalArticle.isPending();
	}

	/**
	* Returns <code>true</code> if this journal article is scheduled.
	*
	* @return <code>true</code> if this journal article is scheduled; <code>false</code> otherwise
	*/
	public boolean isScheduled() {
		return _journalArticle.isScheduled();
	}

	public boolean isNew() {
		return _journalArticle.isNew();
	}

	public void setNew(boolean n) {
		_journalArticle.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalArticle.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalArticle.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalArticle.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalArticle.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalArticle.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalArticle.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalArticle.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new JournalArticleWrapper((JournalArticle)_journalArticle.clone());
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalArticle journalArticle) {
		return _journalArticle.compareTo(journalArticle);
	}

	@Override
	public int hashCode() {
		return _journalArticle.hashCode();
	}

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_journalArticle.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.journal.model.JournalArticle> toCacheModel() {
		return _journalArticle.toCacheModel();
	}

	public com.liferay.portlet.journal.model.JournalArticle toEscapedModel() {
		return new JournalArticleWrapper(_journalArticle.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalArticle.toString();
	}

	public java.lang.String toXmlString() {
		return _journalArticle.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticle.persist();
	}

	public com.liferay.portlet.journal.model.JournalArticleResource getArticleResource()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getArticleResource();
	}

	public java.lang.String getArticleResourceUuid()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getArticleResourceUuid();
	}

	public java.lang.String[] getAvailableLocales() {
		return _journalArticle.getAvailableLocales();
	}

	public java.lang.String getContentByLocale(java.lang.String languageId) {
		return _journalArticle.getContentByLocale(languageId);
	}

	public java.lang.String getDefaultLocale() {
		return _journalArticle.getDefaultLocale();
	}

	public com.liferay.portlet.journal.model.JournalFolder getFolder() {
		return _journalArticle.getFolder();
	}

	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticle.getSmallImageType();
	}

	public boolean isTemplateDriven() {
		return _journalArticle.isTemplateDriven();
	}

	public void setSmallImageType(java.lang.String smallImageType) {
		_journalArticle.setSmallImageType(smallImageType);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public JournalArticle getWrappedJournalArticle() {
		return _journalArticle;
	}

	public JournalArticle getWrappedModel() {
		return _journalArticle;
	}

	public void resetOriginalValues() {
		_journalArticle.resetOriginalValues();
	}

	private JournalArticle _journalArticle;
}
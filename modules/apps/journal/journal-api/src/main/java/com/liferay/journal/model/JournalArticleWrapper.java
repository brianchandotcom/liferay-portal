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

package com.liferay.journal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link JournalArticle}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticle
 * @generated
 */
@ProviderType
public class JournalArticleWrapper implements JournalArticle,
	ModelWrapper<JournalArticle> {
	public JournalArticleWrapper(JournalArticle journalArticle) {
		_journalArticle = journalArticle;
	}

	@Override
	public Class<?> getModelClass() {
		return JournalArticle.class;
	}

	@Override
	public String getModelClassName() {
		return JournalArticle.class.getName();
	}

	@Override
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
		attributes.put("treePath", getTreePath());
		attributes.put("articleId", getArticleId());
		attributes.put("version", getVersion());
		attributes.put("title", getTitle());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("description", getDescription());
		attributes.put("content", getContent());
		attributes.put("DDMStructureKey", getDDMStructureKey());
		attributes.put("DDMTemplateKey", getDDMTemplateKey());
		attributes.put("layoutUuid", getLayoutUuid());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("reviewDate", getReviewDate());
		attributes.put("indexable", getIndexable());
		attributes.put("smallImage", getSmallImage());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("smallImageURL", getSmallImageURL());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

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

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
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

		String DDMStructureKey = (String)attributes.get("DDMStructureKey");

		if (DDMStructureKey != null) {
			setDDMStructureKey(DDMStructureKey);
		}

		String DDMTemplateKey = (String)attributes.get("DDMTemplateKey");

		if (DDMTemplateKey != null) {
			setDDMTemplateKey(DDMTemplateKey);
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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
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
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticle.buildTreePath();
	}

	@Override
	public java.lang.Object clone() {
		return new JournalArticleWrapper((JournalArticle)_journalArticle.clone());
	}

	@Override
	public int compareTo(
		com.liferay.journal.model.JournalArticle journalArticle) {
		return _journalArticle.compareTo(journalArticle);
	}

	/**
	* Returns the article ID of this journal article.
	*
	* @return the article ID of this journal article
	*/
	@Override
	public java.lang.String getArticleId() {
		return _journalArticle.getArticleId();
	}

	@Override
	public long getArticleImageId(java.lang.String elInstanceId,
		java.lang.String elName, java.lang.String languageId) {
		return _journalArticle.getArticleImageId(elInstanceId, elName,
			languageId);
	}

	@Override
	public java.lang.String getArticleImageURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return _journalArticle.getArticleImageURL(themeDisplay);
	}

	@Override
	public com.liferay.journal.model.JournalArticleResource getArticleResource()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticle.getArticleResource();
	}

	@Override
	public java.lang.String getArticleResourceUuid()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticle.getArticleResourceUuid();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _journalArticle.getAvailableLanguageIds();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getAvailableLanguageIds}
	*/
	@Deprecated
	@Override
	public java.lang.String[] getAvailableLocales() {
		return _journalArticle.getAvailableLocales();
	}

	/**
	* Returns the fully qualified class name of this journal article.
	*
	* @return the fully qualified class name of this journal article
	*/
	@Override
	public java.lang.String getClassName() {
		return _journalArticle.getClassName();
	}

	/**
	* Returns the class name ID of this journal article.
	*
	* @return the class name ID of this journal article
	*/
	@Override
	public long getClassNameId() {
		return _journalArticle.getClassNameId();
	}

	/**
	* Returns the class p k of this journal article.
	*
	* @return the class p k of this journal article
	*/
	@Override
	public long getClassPK() {
		return _journalArticle.getClassPK();
	}

	/**
	* Returns the company ID of this journal article.
	*
	* @return the company ID of this journal article
	*/
	@Override
	public long getCompanyId() {
		return _journalArticle.getCompanyId();
	}

	/**
	* Returns the content of this journal article.
	*
	* @return the content of this journal article
	*/
	@Override
	public java.lang.String getContent() {
		return _journalArticle.getContent();
	}

	@Override
	public java.lang.String getContentByLocale(java.lang.String languageId) {
		return _journalArticle.getContentByLocale(languageId);
	}

	/**
	* Returns the create date of this journal article.
	*
	* @return the create date of this journal article
	*/
	@Override
	public Date getCreateDate() {
		return _journalArticle.getCreateDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructure getDDMStructure()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticle.getDDMStructure();
	}

	/**
	* Returns the d d m structure key of this journal article.
	*
	* @return the d d m structure key of this journal article
	*/
	@Override
	public java.lang.String getDDMStructureKey() {
		return _journalArticle.getDDMStructureKey();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplate getDDMTemplate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticle.getDDMTemplate();
	}

	/**
	* Returns the d d m template key of this journal article.
	*
	* @return the d d m template key of this journal article
	*/
	@Override
	public java.lang.String getDDMTemplateKey() {
		return _journalArticle.getDDMTemplateKey();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _journalArticle.getDefaultLanguageId();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getDefaultLanguageId}
	*/
	@Deprecated
	@Override
	public java.lang.String getDefaultLocale() {
		return _journalArticle.getDefaultLocale();
	}

	/**
	* Returns the description of this journal article.
	*
	* @return the description of this journal article
	*/
	@Override
	public java.lang.String getDescription() {
		return _journalArticle.getDescription();
	}

	/**
	* Returns the localized description of this journal article in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this journal article
	*/
	@Override
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
	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _journalArticle.getDescription(languageId, useDefault);
	}

	/**
	* Returns the localized description of this journal article in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this journal article
	*/
	@Override
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
	@Override
	public java.lang.String getDescription(java.util.Locale locale,
		boolean useDefault) {
		return _journalArticle.getDescription(locale, useDefault);
	}

	@Override
	public java.lang.String getDescriptionCurrentLanguageId() {
		return _journalArticle.getDescriptionCurrentLanguageId();
	}

	@Override
	public java.lang.String getDescriptionCurrentValue() {
		return _journalArticle.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this journal article.
	*
	* @return the locales and localized descriptions of this journal article
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _journalArticle.getDescriptionMap();
	}

	/**
	* Returns the display date of this journal article.
	*
	* @return the display date of this journal article
	*/
	@Override
	public Date getDisplayDate() {
		return _journalArticle.getDisplayDate();
	}

	@Override
	public com.liferay.portal.kernel.xml.Document getDocument() {
		return _journalArticle.getDocument();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalArticle.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this journal article.
	*
	* @return the expiration date of this journal article
	*/
	@Override
	public Date getExpirationDate() {
		return _journalArticle.getExpirationDate();
	}

	@Override
	public com.liferay.journal.model.JournalFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticle.getFolder();
	}

	/**
	* Returns the folder ID of this journal article.
	*
	* @return the folder ID of this journal article
	*/
	@Override
	public long getFolderId() {
		return _journalArticle.getFolderId();
	}

	/**
	* Returns the group ID of this journal article.
	*
	* @return the group ID of this journal article
	*/
	@Override
	public long getGroupId() {
		return _journalArticle.getGroupId();
	}

	/**
	* Returns the ID of this journal article.
	*
	* @return the ID of this journal article
	*/
	@Override
	public long getId() {
		return _journalArticle.getId();
	}

	/**
	* Returns the indexable of this journal article.
	*
	* @return the indexable of this journal article
	*/
	@Override
	public boolean getIndexable() {
		return _journalArticle.getIndexable();
	}

	/**
	* Returns the last publish date of this journal article.
	*
	* @return the last publish date of this journal article
	*/
	@Override
	public Date getLastPublishDate() {
		return _journalArticle.getLastPublishDate();
	}

	@Override
	public com.liferay.portal.model.Layout getLayout() {
		return _journalArticle.getLayout();
	}

	/**
	* Returns the layout uuid of this journal article.
	*
	* @return the layout uuid of this journal article
	*/
	@Override
	public java.lang.String getLayoutUuid() {
		return _journalArticle.getLayoutUuid();
	}

	/**
	* Returns the modified date of this journal article.
	*
	* @return the modified date of this journal article
	*/
	@Override
	public Date getModifiedDate() {
		return _journalArticle.getModifiedDate();
	}

	/**
	* Returns the primary key of this journal article.
	*
	* @return the primary key of this journal article
	*/
	@Override
	public long getPrimaryKey() {
		return _journalArticle.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _journalArticle.getPrimaryKeyObj();
	}

	/**
	* Returns the resource prim key of this journal article.
	*
	* @return the resource prim key of this journal article
	*/
	@Override
	public long getResourcePrimKey() {
		return _journalArticle.getResourcePrimKey();
	}

	/**
	* Returns the review date of this journal article.
	*
	* @return the review date of this journal article
	*/
	@Override
	public Date getReviewDate() {
		return _journalArticle.getReviewDate();
	}

	/**
	* Returns the small image of this journal article.
	*
	* @return the small image of this journal article
	*/
	@Override
	public boolean getSmallImage() {
		return _journalArticle.getSmallImage();
	}

	/**
	* Returns the small image ID of this journal article.
	*
	* @return the small image ID of this journal article
	*/
	@Override
	public long getSmallImageId() {
		return _journalArticle.getSmallImageId();
	}

	@Override
	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticle.getSmallImageType();
	}

	/**
	* Returns the small image u r l of this journal article.
	*
	* @return the small image u r l of this journal article
	*/
	@Override
	public java.lang.String getSmallImageURL() {
		return _journalArticle.getSmallImageURL();
	}

	/**
	* Returns the status of this journal article.
	*
	* @return the status of this journal article
	*/
	@Override
	public int getStatus() {
		return _journalArticle.getStatus();
	}

	/**
	* Returns the status by user ID of this journal article.
	*
	* @return the status by user ID of this journal article
	*/
	@Override
	public long getStatusByUserId() {
		return _journalArticle.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this journal article.
	*
	* @return the status by user name of this journal article
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _journalArticle.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this journal article.
	*
	* @return the status by user uuid of this journal article
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _journalArticle.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this journal article.
	*
	* @return the status date of this journal article
	*/
	@Override
	public Date getStatusDate() {
		return _journalArticle.getStatusDate();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getDDMStructureKey()}
	*/
	@Deprecated
	@Override
	public java.lang.String getStructureId() {
		return _journalArticle.getStructureId();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getDDMTemplateKey()}
	*/
	@Deprecated
	@Override
	public java.lang.String getTemplateId() {
		return _journalArticle.getTemplateId();
	}

	/**
	* Returns the title of this journal article.
	*
	* @return the title of this journal article
	*/
	@Override
	public java.lang.String getTitle() {
		return _journalArticle.getTitle();
	}

	/**
	* Returns the localized title of this journal article in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this journal article
	*/
	@Override
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
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _journalArticle.getTitle(languageId, useDefault);
	}

	/**
	* Returns the localized title of this journal article in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this journal article
	*/
	@Override
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
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _journalArticle.getTitle(locale, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _journalArticle.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _journalArticle.getTitleCurrentValue();
	}

	/**
	* Returns a map of the locales and localized titles of this journal article.
	*
	* @return the locales and localized titles of this journal article
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _journalArticle.getTitleMap();
	}

	/**
	* Returns the trash entry created when this journal article was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this journal article.
	*
	* @return the trash entry created when this journal article was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.portlet.trash.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalArticle.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this journal article.
	*
	* @return the class primary key of the trash entry for this journal article
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _journalArticle.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this journal article.
	*
	* @return the trash handler for this journal article
	*/
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _journalArticle.getTrashHandler();
	}

	/**
	* Returns the tree path of this journal article.
	*
	* @return the tree path of this journal article
	*/
	@Override
	public java.lang.String getTreePath() {
		return _journalArticle.getTreePath();
	}

	/**
	* Returns the url title of this journal article.
	*
	* @return the url title of this journal article
	*/
	@Override
	public java.lang.String getUrlTitle() {
		return _journalArticle.getUrlTitle();
	}

	/**
	* Returns the user ID of this journal article.
	*
	* @return the user ID of this journal article
	*/
	@Override
	public long getUserId() {
		return _journalArticle.getUserId();
	}

	/**
	* Returns the user name of this journal article.
	*
	* @return the user name of this journal article
	*/
	@Override
	public java.lang.String getUserName() {
		return _journalArticle.getUserName();
	}

	/**
	* Returns the user uuid of this journal article.
	*
	* @return the user uuid of this journal article
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _journalArticle.getUserUuid();
	}

	/**
	* Returns the uuid of this journal article.
	*
	* @return the uuid of this journal article
	*/
	@Override
	public java.lang.String getUuid() {
		return _journalArticle.getUuid();
	}

	/**
	* Returns the version of this journal article.
	*
	* @return the version of this journal article
	*/
	@Override
	public double getVersion() {
		return _journalArticle.getVersion();
	}

	@Override
	public boolean hasApprovedVersion() {
		return _journalArticle.hasApprovedVersion();
	}

	@Override
	public int hashCode() {
		return _journalArticle.hashCode();
	}

	/**
	* Returns <code>true</code> if this journal article is approved.
	*
	* @return <code>true</code> if this journal article is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _journalArticle.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _journalArticle.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this journal article is denied.
	*
	* @return <code>true</code> if this journal article is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _journalArticle.isDenied();
	}

	/**
	* Returns <code>true</code> if this journal article is a draft.
	*
	* @return <code>true</code> if this journal article is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _journalArticle.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _journalArticle.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this journal article is expired.
	*
	* @return <code>true</code> if this journal article is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _journalArticle.isExpired();
	}

	/**
	* Returns <code>true</code> if this journal article is in the Recycle Bin.
	*
	* @return <code>true</code> if this journal article is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _journalArticle.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this journal article is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this journal article is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _journalArticle.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _journalArticle.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _journalArticle.isInTrashImplicitly();
	}

	/**
	* Returns <code>true</code> if this journal article is inactive.
	*
	* @return <code>true</code> if this journal article is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _journalArticle.isInactive();
	}

	/**
	* Returns <code>true</code> if this journal article is incomplete.
	*
	* @return <code>true</code> if this journal article is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _journalArticle.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this journal article is indexable.
	*
	* @return <code>true</code> if this journal article is indexable; <code>false</code> otherwise
	*/
	@Override
	public boolean isIndexable() {
		return _journalArticle.isIndexable();
	}

	@Override
	public boolean isNew() {
		return _journalArticle.isNew();
	}

	/**
	* Returns <code>true</code> if this journal article is pending.
	*
	* @return <code>true</code> if this journal article is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _journalArticle.isPending();
	}

	@Override
	public boolean isResourceMain() {
		return _journalArticle.isResourceMain();
	}

	/**
	* Returns <code>true</code> if this journal article is scheduled.
	*
	* @return <code>true</code> if this journal article is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _journalArticle.isScheduled();
	}

	/**
	* Returns <code>true</code> if this journal article is small image.
	*
	* @return <code>true</code> if this journal article is small image; <code>false</code> otherwise
	*/
	@Override
	public boolean isSmallImage() {
		return _journalArticle.isSmallImage();
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public boolean isTemplateDriven() {
		return _journalArticle.isTemplateDriven();
	}

	@Override
	public void persist() {
		_journalArticle.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.LocaleException {
		_journalArticle.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException {
		_journalArticle.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the article ID of this journal article.
	*
	* @param articleId the article ID of this journal article
	*/
	@Override
	public void setArticleId(java.lang.String articleId) {
		_journalArticle.setArticleId(articleId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_journalArticle.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_journalArticle.setClassName(className);
	}

	/**
	* Sets the class name ID of this journal article.
	*
	* @param classNameId the class name ID of this journal article
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_journalArticle.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this journal article.
	*
	* @param classPK the class p k of this journal article
	*/
	@Override
	public void setClassPK(long classPK) {
		_journalArticle.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this journal article.
	*
	* @param companyId the company ID of this journal article
	*/
	@Override
	public void setCompanyId(long companyId) {
		_journalArticle.setCompanyId(companyId);
	}

	/**
	* Sets the content of this journal article.
	*
	* @param content the content of this journal article
	*/
	@Override
	public void setContent(java.lang.String content) {
		_journalArticle.setContent(content);
	}

	/**
	* Sets the create date of this journal article.
	*
	* @param createDate the create date of this journal article
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_journalArticle.setCreateDate(createDate);
	}

	/**
	* Sets the d d m structure key of this journal article.
	*
	* @param DDMStructureKey the d d m structure key of this journal article
	*/
	@Override
	public void setDDMStructureKey(java.lang.String DDMStructureKey) {
		_journalArticle.setDDMStructureKey(DDMStructureKey);
	}

	/**
	* Sets the d d m template key of this journal article.
	*
	* @param DDMTemplateKey the d d m template key of this journal article
	*/
	@Override
	public void setDDMTemplateKey(java.lang.String DDMTemplateKey) {
		_journalArticle.setDDMTemplateKey(DDMTemplateKey);
	}

	@Override
	public void setDefaultLanguageId(java.lang.String defaultLanguageId) {
		_journalArticle.setDefaultLanguageId(defaultLanguageId);
	}

	/**
	* Sets the description of this journal article.
	*
	* @param description the description of this journal article
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_journalArticle.setDescription(description);
	}

	/**
	* Sets the localized description of this journal article in the language.
	*
	* @param description the localized description of this journal article
	* @param locale the locale of the language
	*/
	@Override
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
	@Override
	public void setDescription(java.lang.String description,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_journalArticle.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(java.lang.String languageId) {
		_journalArticle.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this journal article from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this journal article
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_journalArticle.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this journal article from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this journal article
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Locale defaultLocale) {
		_journalArticle.setDescriptionMap(descriptionMap, defaultLocale);
	}

	/**
	* Sets the display date of this journal article.
	*
	* @param displayDate the display date of this journal article
	*/
	@Override
	public void setDisplayDate(Date displayDate) {
		_journalArticle.setDisplayDate(displayDate);
	}

	@Override
	public void setDocument(com.liferay.portal.kernel.xml.Document document) {
		_journalArticle.setDocument(document);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_journalArticle.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_journalArticle.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalArticle.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this journal article.
	*
	* @param expirationDate the expiration date of this journal article
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_journalArticle.setExpirationDate(expirationDate);
	}

	/**
	* Sets the folder ID of this journal article.
	*
	* @param folderId the folder ID of this journal article
	*/
	@Override
	public void setFolderId(long folderId) {
		_journalArticle.setFolderId(folderId);
	}

	/**
	* Sets the group ID of this journal article.
	*
	* @param groupId the group ID of this journal article
	*/
	@Override
	public void setGroupId(long groupId) {
		_journalArticle.setGroupId(groupId);
	}

	/**
	* Sets the ID of this journal article.
	*
	* @param id the ID of this journal article
	*/
	@Override
	public void setId(long id) {
		_journalArticle.setId(id);
	}

	/**
	* Sets whether this journal article is indexable.
	*
	* @param indexable the indexable of this journal article
	*/
	@Override
	public void setIndexable(boolean indexable) {
		_journalArticle.setIndexable(indexable);
	}

	/**
	* Sets the last publish date of this journal article.
	*
	* @param lastPublishDate the last publish date of this journal article
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_journalArticle.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the layout uuid of this journal article.
	*
	* @param layoutUuid the layout uuid of this journal article
	*/
	@Override
	public void setLayoutUuid(java.lang.String layoutUuid) {
		_journalArticle.setLayoutUuid(layoutUuid);
	}

	/**
	* Sets the modified date of this journal article.
	*
	* @param modifiedDate the modified date of this journal article
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_journalArticle.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_journalArticle.setNew(n);
	}

	/**
	* Sets the primary key of this journal article.
	*
	* @param primaryKey the primary key of this journal article
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_journalArticle.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalArticle.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the resource prim key of this journal article.
	*
	* @param resourcePrimKey the resource prim key of this journal article
	*/
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		_journalArticle.setResourcePrimKey(resourcePrimKey);
	}

	/**
	* Sets the review date of this journal article.
	*
	* @param reviewDate the review date of this journal article
	*/
	@Override
	public void setReviewDate(Date reviewDate) {
		_journalArticle.setReviewDate(reviewDate);
	}

	/**
	* Sets whether this journal article is small image.
	*
	* @param smallImage the small image of this journal article
	*/
	@Override
	public void setSmallImage(boolean smallImage) {
		_journalArticle.setSmallImage(smallImage);
	}

	/**
	* Sets the small image ID of this journal article.
	*
	* @param smallImageId the small image ID of this journal article
	*/
	@Override
	public void setSmallImageId(long smallImageId) {
		_journalArticle.setSmallImageId(smallImageId);
	}

	@Override
	public void setSmallImageType(java.lang.String smallImageType) {
		_journalArticle.setSmallImageType(smallImageType);
	}

	/**
	* Sets the small image u r l of this journal article.
	*
	* @param smallImageURL the small image u r l of this journal article
	*/
	@Override
	public void setSmallImageURL(java.lang.String smallImageURL) {
		_journalArticle.setSmallImageURL(smallImageURL);
	}

	/**
	* Sets the status of this journal article.
	*
	* @param status the status of this journal article
	*/
	@Override
	public void setStatus(int status) {
		_journalArticle.setStatus(status);
	}

	/**
	* Sets the status by user ID of this journal article.
	*
	* @param statusByUserId the status by user ID of this journal article
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_journalArticle.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this journal article.
	*
	* @param statusByUserName the status by user name of this journal article
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_journalArticle.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this journal article.
	*
	* @param statusByUserUuid the status by user uuid of this journal article
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_journalArticle.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this journal article.
	*
	* @param statusDate the status date of this journal article
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_journalArticle.setStatusDate(statusDate);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #setDDMStructureKey(String)}
	*/
	@Deprecated
	@Override
	public void setStructureId(java.lang.String ddmStructureKey) {
		_journalArticle.setStructureId(ddmStructureKey);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #setDDMTemplateKey(String)}
	*/
	@Deprecated
	@Override
	public void setTemplateId(java.lang.String ddmTemplateKey) {
		_journalArticle.setTemplateId(ddmTemplateKey);
	}

	/**
	* Sets the title of this journal article.
	*
	* @param title the title of this journal article
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_journalArticle.setTitle(title);
	}

	/**
	* Sets the localized title of this journal article in the language.
	*
	* @param title the localized title of this journal article
	* @param locale the locale of the language
	*/
	@Override
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
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_journalArticle.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_journalArticle.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this journal article from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this journal article
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_journalArticle.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this journal article from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this journal article
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_journalArticle.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the tree path of this journal article.
	*
	* @param treePath the tree path of this journal article
	*/
	@Override
	public void setTreePath(java.lang.String treePath) {
		_journalArticle.setTreePath(treePath);
	}

	/**
	* Sets the url title of this journal article.
	*
	* @param urlTitle the url title of this journal article
	*/
	@Override
	public void setUrlTitle(java.lang.String urlTitle) {
		_journalArticle.setUrlTitle(urlTitle);
	}

	/**
	* Sets the user ID of this journal article.
	*
	* @param userId the user ID of this journal article
	*/
	@Override
	public void setUserId(long userId) {
		_journalArticle.setUserId(userId);
	}

	/**
	* Sets the user name of this journal article.
	*
	* @param userName the user name of this journal article
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_journalArticle.setUserName(userName);
	}

	/**
	* Sets the user uuid of this journal article.
	*
	* @param userUuid the user uuid of this journal article
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_journalArticle.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this journal article.
	*
	* @param uuid the uuid of this journal article
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_journalArticle.setUuid(uuid);
	}

	/**
	* Sets the version of this journal article.
	*
	* @param version the version of this journal article
	*/
	@Override
	public void setVersion(double version) {
		_journalArticle.setVersion(version);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.journal.model.JournalArticle> toCacheModel() {
		return _journalArticle.toCacheModel();
	}

	@Override
	public com.liferay.journal.model.JournalArticle toEscapedModel() {
		return new JournalArticleWrapper(_journalArticle.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalArticle.toString();
	}

	@Override
	public com.liferay.journal.model.JournalArticle toUnescapedModel() {
		return new JournalArticleWrapper(_journalArticle.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _journalArticle.toXmlString();
	}

	@Override
	public void updateTreePath(java.lang.String treePath) {
		_journalArticle.updateTreePath(treePath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalArticleWrapper)) {
			return false;
		}

		JournalArticleWrapper journalArticleWrapper = (JournalArticleWrapper)obj;

		if (Validator.equals(_journalArticle,
					journalArticleWrapper._journalArticle)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _journalArticle.getStagedModelType();
	}

	@Override
	public JournalArticle getWrappedModel() {
		return _journalArticle;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _journalArticle.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _journalArticle.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_journalArticle.resetOriginalValues();
	}

	private final JournalArticle _journalArticle;
}
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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class JournalArticleWrapper
	extends BaseModelWrapper<JournalArticle>
	implements JournalArticle, ModelWrapper<JournalArticle> {

	public JournalArticleWrapper(JournalArticle journalArticle) {
		super(journalArticle);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("content", getContent());
		attributes.put("DDMStructureKey", getDDMStructureKey());
		attributes.put("DDMTemplateKey", getDDMTemplateKey());
		attributes.put("defaultLanguageId", getDefaultLanguageId());
		attributes.put("layoutUuid", getLayoutUuid());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("reviewDate", getReviewDate());
		attributes.put("indexable", isIndexable());
		attributes.put("smallImage", isSmallImage());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
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

		String defaultLanguageId = (String)attributes.get("defaultLanguageId");

		if (defaultLanguageId != null) {
			setDefaultLanguageId(defaultLanguageId);
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
	public com.liferay.portal.kernel.repository.model.Folder addImagesFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.addImagesFolder();
	}

	@Override
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.buildTreePath();
	}

	@Override
	public Object clone() {
		return new JournalArticleWrapper((JournalArticle)model.clone());
	}

	/**
	 * Returns the article ID of this journal article.
	 *
	 * @return the article ID of this journal article
	 */
	@Override
	public String getArticleId() {
		return model.getArticleId();
	}

	@Override
	public String getArticleImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {

		return model.getArticleImageURL(themeDisplay);
	}

	@Override
	public JournalArticleResource getArticleResource()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getArticleResource();
	}

	@Override
	public String getArticleResourceUuid()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getArticleResourceUuid();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the fully qualified class name of this journal article.
	 *
	 * @return the fully qualified class name of this journal article
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this journal article.
	 *
	 * @return the class name ID of this journal article
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this journal article.
	 *
	 * @return the class pk of this journal article
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this journal article.
	 *
	 * @return the company ID of this journal article
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this journal article.
	 *
	 * @return the content of this journal article
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	@Override
	public String getContentByLocale(String languageId) {
		return model.getContentByLocale(languageId);
	}

	/**
	 * Returns the create date of this journal article.
	 *
	 * @return the create date of this journal article
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructure
		getDDMStructure() {

		return model.getDDMStructure();
	}

	/**
	 * Returns the ddm structure key of this journal article.
	 *
	 * @return the ddm structure key of this journal article
	 */
	@Override
	public String getDDMStructureKey() {
		return model.getDDMStructureKey();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplate getDDMTemplate() {
		return model.getDDMTemplate();
	}

	/**
	 * Returns the ddm template key of this journal article.
	 *
	 * @return the ddm template key of this journal article
	 */
	@Override
	public String getDDMTemplateKey() {
		return model.getDDMTemplateKey();
	}

	/**
	 * Returns the default language ID of this journal article.
	 *
	 * @return the default language ID of this journal article
	 */
	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	@Override
	public String getDescription() {
		return model.getDescription();
	}

	@Override
	public String getDescription(java.util.Locale locale) {
		return model.getDescription(locale);
	}

	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return model.getDescription(locale, useDefault);
	}

	@Override
	public String getDescription(String languageId) {
		return model.getDescription(languageId);
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return model.getDescription(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return model.getDescriptionMap();
	}

	@Override
	public String getDescriptionMapAsXML() {
		return model.getDescriptionMapAsXML();
	}

	/**
	 * Returns the display date of this journal article.
	 *
	 * @return the display date of this journal article
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	@Override
	public com.liferay.portal.kernel.xml.Document getDocument() {
		return model.getDocument();
	}

	/**
	 * Returns the expiration date of this journal article.
	 *
	 * @return the expiration date of this journal article
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	@Override
	public JournalFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFolder();
	}

	/**
	 * Returns the folder ID of this journal article.
	 *
	 * @return the folder ID of this journal article
	 */
	@Override
	public long getFolderId() {
		return model.getFolderId();
	}

	@Override
	public Map<java.util.Locale, String> getFriendlyURLMap()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFriendlyURLMap();
	}

	@Override
	public String getFriendlyURLsXML()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFriendlyURLsXML();
	}

	/**
	 * Returns the group ID of this journal article.
	 *
	 * @return the group ID of this journal article
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the ID of this journal article.
	 *
	 * @return the ID of this journal article
	 */
	@Override
	public long getId() {
		return model.getId();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getImagesFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getImagesFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getImagesFileEntries(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getImagesFileEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getImagesFileEntries(
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getImagesFileEntries(start, end, obc);
	}

	@Override
	public int getImagesFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getImagesFileEntriesCount();
	}

	@Override
	public long getImagesFolderId() {
		return model.getImagesFolderId();
	}

	/**
	 * Returns the indexable of this journal article.
	 *
	 * @return the indexable of this journal article
	 */
	@Override
	public boolean getIndexable() {
		return model.getIndexable();
	}

	/**
	 * Returns the last publish date of this journal article.
	 *
	 * @return the last publish date of this journal article
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	@Override
	public com.liferay.portal.kernel.model.Layout getLayout() {
		return model.getLayout();
	}

	/**
	 * Returns the layout uuid of this journal article.
	 *
	 * @return the layout uuid of this journal article
	 */
	@Override
	public String getLayoutUuid() {
		return model.getLayoutUuid();
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public String getLegacyDescription() {
		return model.getLegacyDescription();
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public String getLegacyTitle() {
		return model.getLegacyTitle();
	}

	/**
	 * Returns the modified date of this journal article.
	 *
	 * @return the modified date of this journal article
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this journal article.
	 *
	 * @return the mvcc version of this journal article
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this journal article.
	 *
	 * @return the primary key of this journal article
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the resource prim key of this journal article.
	 *
	 * @return the resource prim key of this journal article
	 */
	@Override
	public long getResourcePrimKey() {
		return model.getResourcePrimKey();
	}

	/**
	 * Returns the review date of this journal article.
	 *
	 * @return the review date of this journal article
	 */
	@Override
	public Date getReviewDate() {
		return model.getReviewDate();
	}

	/**
	 * Returns the small image of this journal article.
	 *
	 * @return the small image of this journal article
	 */
	@Override
	public boolean getSmallImage() {
		return model.getSmallImage();
	}

	/**
	 * Returns the small image ID of this journal article.
	 *
	 * @return the small image ID of this journal article
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
	 * Returns the small image url of this journal article.
	 *
	 * @return the small image url of this journal article
	 */
	@Override
	public String getSmallImageURL() {
		return model.getSmallImageURL();
	}

	/**
	 * Returns the status of this journal article.
	 *
	 * @return the status of this journal article
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this journal article.
	 *
	 * @return the status by user ID of this journal article
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this journal article.
	 *
	 * @return the status by user name of this journal article
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this journal article.
	 *
	 * @return the status by user uuid of this journal article
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this journal article.
	 *
	 * @return the status date of this journal article
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #getDDMStructureKey()}
	 */
	@Deprecated
	@Override
	public String getStructureId() {
		return model.getStructureId();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #getDDMTemplateKey()}
	 */
	@Deprecated
	@Override
	public String getTemplateId() {
		return model.getTemplateId();
	}

	@Override
	public String getTitle() {
		return model.getTitle();
	}

	@Override
	public String getTitle(java.util.Locale locale) {
		return model.getTitle(locale);
	}

	@Override
	public String getTitle(java.util.Locale locale, boolean useDefault) {
		return model.getTitle(locale, useDefault);
	}

	@Override
	public String getTitle(String languageId) {
		return model.getTitle(languageId);
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return model.getTitle(languageId, useDefault);
	}

	@Override
	public String getTitleCurrentValue() {
		return model.getTitleCurrentValue();
	}

	@Override
	public Map<java.util.Locale, String> getTitleMap() {
		return model.getTitleMap();
	}

	@Override
	public String getTitleMapAsXML() {
		return model.getTitleMapAsXML();
	}

	/**
	 * Returns the trash entry created when this journal article was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this journal article.
	 *
	 * @return the trash entry created when this journal article was moved to the Recycle Bin
	 */
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTrashEntry();
	}

	/**
	 * Returns the class primary key of the trash entry for this journal article.
	 *
	 * @return the class primary key of the trash entry for this journal article
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the trash handler for this journal article.
	 *
	 * @return the trash handler for this journal article
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return model.getTrashHandler();
	}

	/**
	 * Returns the tree path of this journal article.
	 *
	 * @return the tree path of this journal article
	 */
	@Override
	public String getTreePath() {
		return model.getTreePath();
	}

	/**
	 * Returns the url title of this journal article.
	 *
	 * @return the url title of this journal article
	 */
	@Override
	public String getUrlTitle() {
		return model.getUrlTitle();
	}

	@Override
	public String getUrlTitle(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getUrlTitle(locale);
	}

	/**
	 * Returns the user ID of this journal article.
	 *
	 * @return the user ID of this journal article
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this journal article.
	 *
	 * @return the user name of this journal article
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this journal article.
	 *
	 * @return the user uuid of this journal article
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this journal article.
	 *
	 * @return the uuid of this journal article
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this journal article.
	 *
	 * @return the version of this journal article
	 */
	@Override
	public double getVersion() {
		return model.getVersion();
	}

	@Override
	public boolean hasApprovedVersion() {
		return model.hasApprovedVersion();
	}

	/**
	 * Returns <code>true</code> if this journal article is approved.
	 *
	 * @return <code>true</code> if this journal article is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this journal article is denied.
	 *
	 * @return <code>true</code> if this journal article is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this journal article is a draft.
	 *
	 * @return <code>true</code> if this journal article is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this journal article is expired.
	 *
	 * @return <code>true</code> if this journal article is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this journal article is inactive.
	 *
	 * @return <code>true</code> if this journal article is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this journal article is incomplete.
	 *
	 * @return <code>true</code> if this journal article is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this journal article is indexable.
	 *
	 * @return <code>true</code> if this journal article is indexable; <code>false</code> otherwise
	 */
	@Override
	public boolean isIndexable() {
		return model.isIndexable();
	}

	/**
	 * Returns <code>true</code> if this journal article is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this journal article is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if the parent of this journal article is in the Recycle Bin.
	 *
	 * @return <code>true</code> if the parent of this journal article is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrashContainer() {
		return model.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return model.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return model.isInTrashImplicitly();
	}

	/**
	 * Returns <code>true</code> if this journal article is pending.
	 *
	 * @return <code>true</code> if this journal article is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	@Override
	public boolean isResourceMain() {
		return model.isResourceMain();
	}

	/**
	 * Returns <code>true</code> if this journal article is scheduled.
	 *
	 * @return <code>true</code> if this journal article is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	/**
	 * Returns <code>true</code> if this journal article is small image.
	 *
	 * @return <code>true</code> if this journal article is small image; <code>false</code> otherwise
	 */
	@Override
	public boolean isSmallImage() {
		return model.isSmallImage();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isTemplateDriven() {
		return model.isTemplateDriven();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a journal article model instance should use the <code>JournalArticle</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the article ID of this journal article.
	 *
	 * @param articleId the article ID of this journal article
	 */
	@Override
	public void setArticleId(String articleId) {
		model.setArticleId(articleId);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this journal article.
	 *
	 * @param classNameId the class name ID of this journal article
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this journal article.
	 *
	 * @param classPK the class pk of this journal article
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this journal article.
	 *
	 * @param companyId the company ID of this journal article
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this journal article.
	 *
	 * @param content the content of this journal article
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the create date of this journal article.
	 *
	 * @param createDate the create date of this journal article
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ddm structure key of this journal article.
	 *
	 * @param DDMStructureKey the ddm structure key of this journal article
	 */
	@Override
	public void setDDMStructureKey(String DDMStructureKey) {
		model.setDDMStructureKey(DDMStructureKey);
	}

	/**
	 * Sets the ddm template key of this journal article.
	 *
	 * @param DDMTemplateKey the ddm template key of this journal article
	 */
	@Override
	public void setDDMTemplateKey(String DDMTemplateKey) {
		model.setDDMTemplateKey(DDMTemplateKey);
	}

	/**
	 * Sets the default language ID of this journal article.
	 *
	 * @param defaultLanguageId the default language ID of this journal article
	 */
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		model.setDefaultLanguageId(defaultLanguageId);
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap) {

		model.setDescriptionMap(descriptionMap);
	}

	/**
	 * Sets the display date of this journal article.
	 *
	 * @param displayDate the display date of this journal article
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	@Override
	public void setDocument(com.liferay.portal.kernel.xml.Document document) {
		model.setDocument(document);
	}

	/**
	 * Sets the expiration date of this journal article.
	 *
	 * @param expirationDate the expiration date of this journal article
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the folder ID of this journal article.
	 *
	 * @param folderId the folder ID of this journal article
	 */
	@Override
	public void setFolderId(long folderId) {
		model.setFolderId(folderId);
	}

	/**
	 * Sets the group ID of this journal article.
	 *
	 * @param groupId the group ID of this journal article
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the ID of this journal article.
	 *
	 * @param id the ID of this journal article
	 */
	@Override
	public void setId(long id) {
		model.setId(id);
	}

	@Override
	public void setImagesFolderId(long imagesFolderId) {
		model.setImagesFolderId(imagesFolderId);
	}

	/**
	 * Sets whether this journal article is indexable.
	 *
	 * @param indexable the indexable of this journal article
	 */
	@Override
	public void setIndexable(boolean indexable) {
		model.setIndexable(indexable);
	}

	/**
	 * Sets the last publish date of this journal article.
	 *
	 * @param lastPublishDate the last publish date of this journal article
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the layout uuid of this journal article.
	 *
	 * @param layoutUuid the layout uuid of this journal article
	 */
	@Override
	public void setLayoutUuid(String layoutUuid) {
		model.setLayoutUuid(layoutUuid);
	}

	/**
	 * Sets the modified date of this journal article.
	 *
	 * @param modifiedDate the modified date of this journal article
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this journal article.
	 *
	 * @param mvccVersion the mvcc version of this journal article
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this journal article.
	 *
	 * @param primaryKey the primary key of this journal article
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the resource prim key of this journal article.
	 *
	 * @param resourcePrimKey the resource prim key of this journal article
	 */
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		model.setResourcePrimKey(resourcePrimKey);
	}

	/**
	 * Sets the review date of this journal article.
	 *
	 * @param reviewDate the review date of this journal article
	 */
	@Override
	public void setReviewDate(Date reviewDate) {
		model.setReviewDate(reviewDate);
	}

	/**
	 * Sets whether this journal article is small image.
	 *
	 * @param smallImage the small image of this journal article
	 */
	@Override
	public void setSmallImage(boolean smallImage) {
		model.setSmallImage(smallImage);
	}

	/**
	 * Sets the small image ID of this journal article.
	 *
	 * @param smallImageId the small image ID of this journal article
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
	 * Sets the small image url of this journal article.
	 *
	 * @param smallImageURL the small image url of this journal article
	 */
	@Override
	public void setSmallImageURL(String smallImageURL) {
		model.setSmallImageURL(smallImageURL);
	}

	/**
	 * Sets the status of this journal article.
	 *
	 * @param status the status of this journal article
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this journal article.
	 *
	 * @param statusByUserId the status by user ID of this journal article
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this journal article.
	 *
	 * @param statusByUserName the status by user name of this journal article
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this journal article.
	 *
	 * @param statusByUserUuid the status by user uuid of this journal article
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this journal article.
	 *
	 * @param statusDate the status date of this journal article
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #setDDMStructureKey(String)}
	 */
	@Deprecated
	@Override
	public void setStructureId(String ddmStructureKey) {
		model.setStructureId(ddmStructureKey);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #setDDMTemplateKey(String)}
	 */
	@Deprecated
	@Override
	public void setTemplateId(String ddmTemplateKey) {
		model.setTemplateId(ddmTemplateKey);
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	@Override
	public void setTitleMap(Map<java.util.Locale, String> titleMap) {
		model.setTitleMap(titleMap);
	}

	/**
	 * Sets the tree path of this journal article.
	 *
	 * @param treePath the tree path of this journal article
	 */
	@Override
	public void setTreePath(String treePath) {
		model.setTreePath(treePath);
	}

	/**
	 * Sets the url title of this journal article.
	 *
	 * @param urlTitle the url title of this journal article
	 */
	@Override
	public void setUrlTitle(String urlTitle) {
		model.setUrlTitle(urlTitle);
	}

	/**
	 * Sets the user ID of this journal article.
	 *
	 * @param userId the user ID of this journal article
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this journal article.
	 *
	 * @param userName the user name of this journal article
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this journal article.
	 *
	 * @param userUuid the user uuid of this journal article
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this journal article.
	 *
	 * @param uuid the uuid of this journal article
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this journal article.
	 *
	 * @param version the version of this journal article
	 */
	@Override
	public void setVersion(double version) {
		model.setVersion(version);
	}

	@Override
	public void updateTreePath(String treePath) {
		model.updateTreePath(treePath);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected JournalArticleWrapper wrap(JournalArticle journalArticle) {
		return new JournalArticleWrapper(journalArticle);
	}

}
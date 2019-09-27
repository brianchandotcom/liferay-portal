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

package com.liferay.knowledge.base.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link KBArticle}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KBArticle
 * @generated
 */
public class KBArticleWrapper
	extends BaseModelWrapper<KBArticle>
	implements KBArticle, ModelWrapper<KBArticle> {

	public KBArticleWrapper(KBArticle kbArticle) {
		super(kbArticle);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("kbArticleId", getKbArticleId());
		attributes.put("resourcePrimKey", getResourcePrimKey());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("rootResourcePrimKey", getRootResourcePrimKey());
		attributes.put(
			"parentResourceClassNameId", getParentResourceClassNameId());
		attributes.put("parentResourcePrimKey", getParentResourcePrimKey());
		attributes.put("kbFolderId", getKbFolderId());
		attributes.put("version", getVersion());
		attributes.put("title", getTitle());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("content", getContent());
		attributes.put("description", getDescription());
		attributes.put("priority", getPriority());
		attributes.put("sections", getSections());
		attributes.put("viewCount", getViewCount());
		attributes.put("latest", isLatest());
		attributes.put("main", isMain());
		attributes.put("sourceURL", getSourceURL());
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

		Long kbArticleId = (Long)attributes.get("kbArticleId");

		if (kbArticleId != null) {
			setKbArticleId(kbArticleId);
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

		Long rootResourcePrimKey = (Long)attributes.get("rootResourcePrimKey");

		if (rootResourcePrimKey != null) {
			setRootResourcePrimKey(rootResourcePrimKey);
		}

		Long parentResourceClassNameId = (Long)attributes.get(
			"parentResourceClassNameId");

		if (parentResourceClassNameId != null) {
			setParentResourceClassNameId(parentResourceClassNameId);
		}

		Long parentResourcePrimKey = (Long)attributes.get(
			"parentResourcePrimKey");

		if (parentResourcePrimKey != null) {
			setParentResourcePrimKey(parentResourcePrimKey);
		}

		Long kbFolderId = (Long)attributes.get("kbFolderId");

		if (kbFolderId != null) {
			setKbFolderId(kbFolderId);
		}

		Integer version = (Integer)attributes.get("version");

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

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String sections = (String)attributes.get("sections");

		if (sections != null) {
			setSections(sections);
		}

		Integer viewCount = (Integer)attributes.get("viewCount");

		if (viewCount != null) {
			setViewCount(viewCount);
		}

		Boolean latest = (Boolean)attributes.get("latest");

		if (latest != null) {
			setLatest(latest);
		}

		Boolean main = (Boolean)attributes.get("main");

		if (main != null) {
			setMain(main);
		}

		String sourceURL = (String)attributes.get("sourceURL");

		if (sourceURL != null) {
			setSourceURL(sourceURL);
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
	public java.util.List<Long> getAncestorResourcePrimaryKeys()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAncestorResourcePrimaryKeys();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntries();
	}

	@Override
	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFolderId();
	}

	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this kb article.
	 *
	 * @return the company ID of this kb article
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this kb article.
	 *
	 * @return the content of this kb article
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the create date of this kb article.
	 *
	 * @return the create date of this kb article
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this kb article.
	 *
	 * @return the description of this kb article
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the group ID of this kb article.
	 *
	 * @return the group ID of this kb article
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the kb article ID of this kb article.
	 *
	 * @return the kb article ID of this kb article
	 */
	@Override
	public long getKbArticleId() {
		return model.getKbArticleId();
	}

	/**
	 * Returns the kb folder ID of this kb article.
	 *
	 * @return the kb folder ID of this kb article
	 */
	@Override
	public long getKbFolderId() {
		return model.getKbFolderId();
	}

	/**
	 * Returns the last publish date of this kb article.
	 *
	 * @return the last publish date of this kb article
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the latest of this kb article.
	 *
	 * @return the latest of this kb article
	 */
	@Override
	public boolean getLatest() {
		return model.getLatest();
	}

	/**
	 * Returns the main of this kb article.
	 *
	 * @return the main of this kb article
	 */
	@Override
	public boolean getMain() {
		return model.getMain();
	}

	/**
	 * Returns the modified date of this kb article.
	 *
	 * @return the modified date of this kb article
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kb article.
	 *
	 * @return the mvcc version of this kb article
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	@Override
	public KBArticle getParentKBArticle()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentKBArticle();
	}

	/**
	 * Returns the parent resource class name ID of this kb article.
	 *
	 * @return the parent resource class name ID of this kb article
	 */
	@Override
	public long getParentResourceClassNameId() {
		return model.getParentResourceClassNameId();
	}

	/**
	 * Returns the parent resource prim key of this kb article.
	 *
	 * @return the parent resource prim key of this kb article
	 */
	@Override
	public long getParentResourcePrimKey() {
		return model.getParentResourcePrimKey();
	}

	@Override
	public String getParentTitle(java.util.Locale locale, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentTitle(locale, status);
	}

	/**
	 * Returns the primary key of this kb article.
	 *
	 * @return the primary key of this kb article
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this kb article.
	 *
	 * @return the priority of this kb article
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the resource prim key of this kb article.
	 *
	 * @return the resource prim key of this kb article
	 */
	@Override
	public long getResourcePrimKey() {
		return model.getResourcePrimKey();
	}

	/**
	 * Returns the root resource prim key of this kb article.
	 *
	 * @return the root resource prim key of this kb article
	 */
	@Override
	public long getRootResourcePrimKey() {
		return model.getRootResourcePrimKey();
	}

	/**
	 * Returns the sections of this kb article.
	 *
	 * @return the sections of this kb article
	 */
	@Override
	public String getSections() {
		return model.getSections();
	}

	/**
	 * Returns the source url of this kb article.
	 *
	 * @return the source url of this kb article
	 */
	@Override
	public String getSourceURL() {
		return model.getSourceURL();
	}

	/**
	 * Returns the status of this kb article.
	 *
	 * @return the status of this kb article
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this kb article.
	 *
	 * @return the status by user ID of this kb article
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this kb article.
	 *
	 * @return the status by user name of this kb article
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this kb article.
	 *
	 * @return the status by user uuid of this kb article
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this kb article.
	 *
	 * @return the status date of this kb article
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the title of this kb article.
	 *
	 * @return the title of this kb article
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the url title of this kb article.
	 *
	 * @return the url title of this kb article
	 */
	@Override
	public String getUrlTitle() {
		return model.getUrlTitle();
	}

	/**
	 * Returns the user ID of this kb article.
	 *
	 * @return the user ID of this kb article
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kb article.
	 *
	 * @return the user name of this kb article
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kb article.
	 *
	 * @return the user uuid of this kb article
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this kb article.
	 *
	 * @return the uuid of this kb article
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this kb article.
	 *
	 * @return the version of this kb article
	 */
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns the view count of this kb article.
	 *
	 * @return the view count of this kb article
	 */
	@Override
	public int getViewCount() {
		return model.getViewCount();
	}

	/**
	 * Returns <code>true</code> if this kb article is approved.
	 *
	 * @return <code>true</code> if this kb article is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this kb article is denied.
	 *
	 * @return <code>true</code> if this kb article is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this kb article is a draft.
	 *
	 * @return <code>true</code> if this kb article is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this kb article is expired.
	 *
	 * @return <code>true</code> if this kb article is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	@Override
	public boolean isFirstVersion() {
		return model.isFirstVersion();
	}

	/**
	 * Returns <code>true</code> if this kb article is inactive.
	 *
	 * @return <code>true</code> if this kb article is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this kb article is incomplete.
	 *
	 * @return <code>true</code> if this kb article is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this kb article is latest.
	 *
	 * @return <code>true</code> if this kb article is latest; <code>false</code> otherwise
	 */
	@Override
	public boolean isLatest() {
		return model.isLatest();
	}

	/**
	 * Returns <code>true</code> if this kb article is main.
	 *
	 * @return <code>true</code> if this kb article is main; <code>false</code> otherwise
	 */
	@Override
	public boolean isMain() {
		return model.isMain();
	}

	/**
	 * Returns <code>true</code> if this kb article is pending.
	 *
	 * @return <code>true</code> if this kb article is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	@Override
	public boolean isResourceMain() {
		return model.isResourceMain();
	}

	@Override
	public boolean isRoot() {
		return model.isRoot();
	}

	/**
	 * Returns <code>true</code> if this kb article is scheduled.
	 *
	 * @return <code>true</code> if this kb article is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kb article model instance should use the <code>KBArticle</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this kb article.
	 *
	 * @param companyId the company ID of this kb article
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this kb article.
	 *
	 * @param content the content of this kb article
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the create date of this kb article.
	 *
	 * @param createDate the create date of this kb article
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this kb article.
	 *
	 * @param description the description of this kb article
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the group ID of this kb article.
	 *
	 * @param groupId the group ID of this kb article
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the kb article ID of this kb article.
	 *
	 * @param kbArticleId the kb article ID of this kb article
	 */
	@Override
	public void setKbArticleId(long kbArticleId) {
		model.setKbArticleId(kbArticleId);
	}

	/**
	 * Sets the kb folder ID of this kb article.
	 *
	 * @param kbFolderId the kb folder ID of this kb article
	 */
	@Override
	public void setKbFolderId(long kbFolderId) {
		model.setKbFolderId(kbFolderId);
	}

	/**
	 * Sets the last publish date of this kb article.
	 *
	 * @param lastPublishDate the last publish date of this kb article
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets whether this kb article is latest.
	 *
	 * @param latest the latest of this kb article
	 */
	@Override
	public void setLatest(boolean latest) {
		model.setLatest(latest);
	}

	/**
	 * Sets whether this kb article is main.
	 *
	 * @param main the main of this kb article
	 */
	@Override
	public void setMain(boolean main) {
		model.setMain(main);
	}

	/**
	 * Sets the modified date of this kb article.
	 *
	 * @param modifiedDate the modified date of this kb article
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kb article.
	 *
	 * @param mvccVersion the mvcc version of this kb article
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent resource class name ID of this kb article.
	 *
	 * @param parentResourceClassNameId the parent resource class name ID of this kb article
	 */
	@Override
	public void setParentResourceClassNameId(long parentResourceClassNameId) {
		model.setParentResourceClassNameId(parentResourceClassNameId);
	}

	/**
	 * Sets the parent resource prim key of this kb article.
	 *
	 * @param parentResourcePrimKey the parent resource prim key of this kb article
	 */
	@Override
	public void setParentResourcePrimKey(long parentResourcePrimKey) {
		model.setParentResourcePrimKey(parentResourcePrimKey);
	}

	/**
	 * Sets the primary key of this kb article.
	 *
	 * @param primaryKey the primary key of this kb article
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this kb article.
	 *
	 * @param priority the priority of this kb article
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the resource prim key of this kb article.
	 *
	 * @param resourcePrimKey the resource prim key of this kb article
	 */
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		model.setResourcePrimKey(resourcePrimKey);
	}

	/**
	 * Sets the root resource prim key of this kb article.
	 *
	 * @param rootResourcePrimKey the root resource prim key of this kb article
	 */
	@Override
	public void setRootResourcePrimKey(long rootResourcePrimKey) {
		model.setRootResourcePrimKey(rootResourcePrimKey);
	}

	/**
	 * Sets the sections of this kb article.
	 *
	 * @param sections the sections of this kb article
	 */
	@Override
	public void setSections(String sections) {
		model.setSections(sections);
	}

	/**
	 * Sets the source url of this kb article.
	 *
	 * @param sourceURL the source url of this kb article
	 */
	@Override
	public void setSourceURL(String sourceURL) {
		model.setSourceURL(sourceURL);
	}

	/**
	 * Sets the status of this kb article.
	 *
	 * @param status the status of this kb article
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this kb article.
	 *
	 * @param statusByUserId the status by user ID of this kb article
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this kb article.
	 *
	 * @param statusByUserName the status by user name of this kb article
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this kb article.
	 *
	 * @param statusByUserUuid the status by user uuid of this kb article
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this kb article.
	 *
	 * @param statusDate the status date of this kb article
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the title of this kb article.
	 *
	 * @param title the title of this kb article
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the url title of this kb article.
	 *
	 * @param urlTitle the url title of this kb article
	 */
	@Override
	public void setUrlTitle(String urlTitle) {
		model.setUrlTitle(urlTitle);
	}

	/**
	 * Sets the user ID of this kb article.
	 *
	 * @param userId the user ID of this kb article
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kb article.
	 *
	 * @param userName the user name of this kb article
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kb article.
	 *
	 * @param userUuid the user uuid of this kb article
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this kb article.
	 *
	 * @param uuid the uuid of this kb article
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this kb article.
	 *
	 * @param version the version of this kb article
	 */
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	/**
	 * Sets the view count of this kb article.
	 *
	 * @param viewCount the view count of this kb article
	 */
	@Override
	public void setViewCount(int viewCount) {
		model.setViewCount(viewCount);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected KBArticleWrapper wrap(KBArticle kbArticle) {
		return new KBArticleWrapper(kbArticle);
	}

}
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

package com.liferay.portlet.wiki.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link WikiPage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPage
 * @generated
 */
public class WikiPageWrapper implements WikiPage, ModelWrapper<WikiPage> {
	public WikiPageWrapper(WikiPage wikiPage) {
		_wikiPage = wikiPage;
	}

	@Override
	public Class<?> getModelClass() {
		return WikiPage.class;
	}

	@Override
	public String getModelClassName() {
		return WikiPage.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("pageId", getPageId());
		attributes.put("resourcePrimKey", getResourcePrimKey());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("nodeId", getNodeId());
		attributes.put("title", getTitle());
		attributes.put("version", getVersion());
		attributes.put("minorEdit", getMinorEdit());
		attributes.put("content", getContent());
		attributes.put("summary", getSummary());
		attributes.put("format", getFormat());
		attributes.put("head", getHead());
		attributes.put("parentTitle", getParentTitle());
		attributes.put("redirectTitle", getRedirectTitle());
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

		Long pageId = (Long)attributes.get("pageId");

		if (pageId != null) {
			setPageId(pageId);
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

		Long nodeId = (Long)attributes.get("nodeId");

		if (nodeId != null) {
			setNodeId(nodeId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		Double version = (Double)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Boolean minorEdit = (Boolean)attributes.get("minorEdit");

		if (minorEdit != null) {
			setMinorEdit(minorEdit);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		String summary = (String)attributes.get("summary");

		if (summary != null) {
			setSummary(summary);
		}

		String format = (String)attributes.get("format");

		if (format != null) {
			setFormat(format);
		}

		Boolean head = (Boolean)attributes.get("head");

		if (head != null) {
			setHead(head);
		}

		String parentTitle = (String)attributes.get("parentTitle");

		if (parentTitle != null) {
			setParentTitle(parentTitle);
		}

		String redirectTitle = (String)attributes.get("redirectTitle");

		if (redirectTitle != null) {
			setRedirectTitle(redirectTitle);
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
	* Returns the primary key of this wiki page.
	*
	* @return the primary key of this wiki page
	*/
	@Override
	public long getPrimaryKey() {
		return _wikiPage.getPrimaryKey();
	}

	/**
	* Sets the primary key of this wiki page.
	*
	* @param primaryKey the primary key of this wiki page
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_wikiPage.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this wiki page.
	*
	* @return the uuid of this wiki page
	*/
	@Override
	public java.lang.String getUuid() {
		return _wikiPage.getUuid();
	}

	/**
	* Sets the uuid of this wiki page.
	*
	* @param uuid the uuid of this wiki page
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_wikiPage.setUuid(uuid);
	}

	/**
	* Returns the page ID of this wiki page.
	*
	* @return the page ID of this wiki page
	*/
	@Override
	public long getPageId() {
		return _wikiPage.getPageId();
	}

	/**
	* Sets the page ID of this wiki page.
	*
	* @param pageId the page ID of this wiki page
	*/
	@Override
	public void setPageId(long pageId) {
		_wikiPage.setPageId(pageId);
	}

	/**
	* Returns the resource prim key of this wiki page.
	*
	* @return the resource prim key of this wiki page
	*/
	@Override
	public long getResourcePrimKey() {
		return _wikiPage.getResourcePrimKey();
	}

	/**
	* Sets the resource prim key of this wiki page.
	*
	* @param resourcePrimKey the resource prim key of this wiki page
	*/
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		_wikiPage.setResourcePrimKey(resourcePrimKey);
	}

	@Override
	public boolean isResourceMain() {
		return _wikiPage.isResourceMain();
	}

	/**
	* Returns the group ID of this wiki page.
	*
	* @return the group ID of this wiki page
	*/
	@Override
	public long getGroupId() {
		return _wikiPage.getGroupId();
	}

	/**
	* Sets the group ID of this wiki page.
	*
	* @param groupId the group ID of this wiki page
	*/
	@Override
	public void setGroupId(long groupId) {
		_wikiPage.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this wiki page.
	*
	* @return the company ID of this wiki page
	*/
	@Override
	public long getCompanyId() {
		return _wikiPage.getCompanyId();
	}

	/**
	* Sets the company ID of this wiki page.
	*
	* @param companyId the company ID of this wiki page
	*/
	@Override
	public void setCompanyId(long companyId) {
		_wikiPage.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this wiki page.
	*
	* @return the user ID of this wiki page
	*/
	@Override
	public long getUserId() {
		return _wikiPage.getUserId();
	}

	/**
	* Sets the user ID of this wiki page.
	*
	* @param userId the user ID of this wiki page
	*/
	@Override
	public void setUserId(long userId) {
		_wikiPage.setUserId(userId);
	}

	/**
	* Returns the user uuid of this wiki page.
	*
	* @return the user uuid of this wiki page
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getUserUuid();
	}

	/**
	* Sets the user uuid of this wiki page.
	*
	* @param userUuid the user uuid of this wiki page
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_wikiPage.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this wiki page.
	*
	* @return the user name of this wiki page
	*/
	@Override
	public java.lang.String getUserName() {
		return _wikiPage.getUserName();
	}

	/**
	* Sets the user name of this wiki page.
	*
	* @param userName the user name of this wiki page
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_wikiPage.setUserName(userName);
	}

	/**
	* Returns the create date of this wiki page.
	*
	* @return the create date of this wiki page
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _wikiPage.getCreateDate();
	}

	/**
	* Sets the create date of this wiki page.
	*
	* @param createDate the create date of this wiki page
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_wikiPage.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this wiki page.
	*
	* @return the modified date of this wiki page
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _wikiPage.getModifiedDate();
	}

	/**
	* Sets the modified date of this wiki page.
	*
	* @param modifiedDate the modified date of this wiki page
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_wikiPage.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the node ID of this wiki page.
	*
	* @return the node ID of this wiki page
	*/
	@Override
	public long getNodeId() {
		return _wikiPage.getNodeId();
	}

	/**
	* Sets the node ID of this wiki page.
	*
	* @param nodeId the node ID of this wiki page
	*/
	@Override
	public void setNodeId(long nodeId) {
		_wikiPage.setNodeId(nodeId);
	}

	/**
	* Returns the title of this wiki page.
	*
	* @return the title of this wiki page
	*/
	@Override
	public java.lang.String getTitle() {
		return _wikiPage.getTitle();
	}

	/**
	* Sets the title of this wiki page.
	*
	* @param title the title of this wiki page
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_wikiPage.setTitle(title);
	}

	/**
	* Returns the version of this wiki page.
	*
	* @return the version of this wiki page
	*/
	@Override
	public double getVersion() {
		return _wikiPage.getVersion();
	}

	/**
	* Sets the version of this wiki page.
	*
	* @param version the version of this wiki page
	*/
	@Override
	public void setVersion(double version) {
		_wikiPage.setVersion(version);
	}

	/**
	* Returns the minor edit of this wiki page.
	*
	* @return the minor edit of this wiki page
	*/
	@Override
	public boolean getMinorEdit() {
		return _wikiPage.getMinorEdit();
	}

	/**
	* Returns <code>true</code> if this wiki page is minor edit.
	*
	* @return <code>true</code> if this wiki page is minor edit; <code>false</code> otherwise
	*/
	@Override
	public boolean isMinorEdit() {
		return _wikiPage.isMinorEdit();
	}

	/**
	* Sets whether this wiki page is minor edit.
	*
	* @param minorEdit the minor edit of this wiki page
	*/
	@Override
	public void setMinorEdit(boolean minorEdit) {
		_wikiPage.setMinorEdit(minorEdit);
	}

	/**
	* Returns the content of this wiki page.
	*
	* @return the content of this wiki page
	*/
	@Override
	public java.lang.String getContent() {
		return _wikiPage.getContent();
	}

	/**
	* Sets the content of this wiki page.
	*
	* @param content the content of this wiki page
	*/
	@Override
	public void setContent(java.lang.String content) {
		_wikiPage.setContent(content);
	}

	/**
	* Returns the summary of this wiki page.
	*
	* @return the summary of this wiki page
	*/
	@Override
	public java.lang.String getSummary() {
		return _wikiPage.getSummary();
	}

	/**
	* Sets the summary of this wiki page.
	*
	* @param summary the summary of this wiki page
	*/
	@Override
	public void setSummary(java.lang.String summary) {
		_wikiPage.setSummary(summary);
	}

	/**
	* Returns the format of this wiki page.
	*
	* @return the format of this wiki page
	*/
	@Override
	public java.lang.String getFormat() {
		return _wikiPage.getFormat();
	}

	/**
	* Sets the format of this wiki page.
	*
	* @param format the format of this wiki page
	*/
	@Override
	public void setFormat(java.lang.String format) {
		_wikiPage.setFormat(format);
	}

	/**
	* Returns the head of this wiki page.
	*
	* @return the head of this wiki page
	*/
	@Override
	public boolean getHead() {
		return _wikiPage.getHead();
	}

	/**
	* Returns <code>true</code> if this wiki page is head.
	*
	* @return <code>true</code> if this wiki page is head; <code>false</code> otherwise
	*/
	@Override
	public boolean isHead() {
		return _wikiPage.isHead();
	}

	/**
	* Sets whether this wiki page is head.
	*
	* @param head the head of this wiki page
	*/
	@Override
	public void setHead(boolean head) {
		_wikiPage.setHead(head);
	}

	/**
	* Returns the parent title of this wiki page.
	*
	* @return the parent title of this wiki page
	*/
	@Override
	public java.lang.String getParentTitle() {
		return _wikiPage.getParentTitle();
	}

	/**
	* Sets the parent title of this wiki page.
	*
	* @param parentTitle the parent title of this wiki page
	*/
	@Override
	public void setParentTitle(java.lang.String parentTitle) {
		_wikiPage.setParentTitle(parentTitle);
	}

	/**
	* Returns the redirect title of this wiki page.
	*
	* @return the redirect title of this wiki page
	*/
	@Override
	public java.lang.String getRedirectTitle() {
		return _wikiPage.getRedirectTitle();
	}

	/**
	* Sets the redirect title of this wiki page.
	*
	* @param redirectTitle the redirect title of this wiki page
	*/
	@Override
	public void setRedirectTitle(java.lang.String redirectTitle) {
		_wikiPage.setRedirectTitle(redirectTitle);
	}

	/**
	* Returns the status of this wiki page.
	*
	* @return the status of this wiki page
	*/
	@Override
	public int getStatus() {
		return _wikiPage.getStatus();
	}

	/**
	* Sets the status of this wiki page.
	*
	* @param status the status of this wiki page
	*/
	@Override
	public void setStatus(int status) {
		_wikiPage.setStatus(status);
	}

	/**
	* Returns the status by user ID of this wiki page.
	*
	* @return the status by user ID of this wiki page
	*/
	@Override
	public long getStatusByUserId() {
		return _wikiPage.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this wiki page.
	*
	* @param statusByUserId the status by user ID of this wiki page
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_wikiPage.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this wiki page.
	*
	* @return the status by user uuid of this wiki page
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this wiki page.
	*
	* @param statusByUserUuid the status by user uuid of this wiki page
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_wikiPage.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this wiki page.
	*
	* @return the status by user name of this wiki page
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _wikiPage.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this wiki page.
	*
	* @param statusByUserName the status by user name of this wiki page
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_wikiPage.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this wiki page.
	*
	* @return the status date of this wiki page
	*/
	@Override
	public java.util.Date getStatusDate() {
		return _wikiPage.getStatusDate();
	}

	/**
	* Sets the status date of this wiki page.
	*
	* @param statusDate the status date of this wiki page
	*/
	@Override
	public void setStatusDate(java.util.Date statusDate) {
		_wikiPage.setStatusDate(statusDate);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	*/
	@Override
	public boolean getApproved() {
		return _wikiPage.getApproved();
	}

	/**
	* Returns <code>true</code> if this wiki page is approved.
	*
	* @return <code>true</code> if this wiki page is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _wikiPage.isApproved();
	}

	/**
	* Returns <code>true</code> if this wiki page is denied.
	*
	* @return <code>true</code> if this wiki page is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _wikiPage.isDenied();
	}

	/**
	* Returns <code>true</code> if this wiki page is a draft.
	*
	* @return <code>true</code> if this wiki page is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _wikiPage.isDraft();
	}

	/**
	* Returns <code>true</code> if this wiki page is expired.
	*
	* @return <code>true</code> if this wiki page is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _wikiPage.isExpired();
	}

	/**
	* Returns <code>true</code> if this wiki page is inactive.
	*
	* @return <code>true</code> if this wiki page is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _wikiPage.isInactive();
	}

	/**
	* Returns <code>true</code> if this wiki page is incomplete.
	*
	* @return <code>true</code> if this wiki page is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _wikiPage.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this wiki page is in the Recycle Bin.
	*
	* @return <code>true</code> if this wiki page is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _wikiPage.isInTrash();
	}

	/**
	* Returns <code>true</code> if this wiki page is pending.
	*
	* @return <code>true</code> if this wiki page is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _wikiPage.isPending();
	}

	/**
	* Returns <code>true</code> if this wiki page is scheduled.
	*
	* @return <code>true</code> if this wiki page is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _wikiPage.isScheduled();
	}

	@Override
	public boolean isNew() {
		return _wikiPage.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_wikiPage.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _wikiPage.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_wikiPage.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _wikiPage.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _wikiPage.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_wikiPage.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _wikiPage.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_wikiPage.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_wikiPage.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_wikiPage.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new WikiPageWrapper((WikiPage)_wikiPage.clone());
	}

	@Override
	public int compareTo(com.liferay.portlet.wiki.model.WikiPage wikiPage) {
		return _wikiPage.compareTo(wikiPage);
	}

	@Override
	public int hashCode() {
		return _wikiPage.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.wiki.model.WikiPage> toCacheModel() {
		return _wikiPage.toCacheModel();
	}

	@Override
	public com.liferay.portlet.wiki.model.WikiPage toEscapedModel() {
		return new WikiPageWrapper(_wikiPage.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.wiki.model.WikiPage toUnescapedModel() {
		return new WikiPageWrapper(_wikiPage.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _wikiPage.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _wikiPage.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_wikiPage.persist();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.addAttachmentsFolder();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getAttachmentsFileEntries(start, end);
	}

	@Override
	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getAttachmentsFileEntriesCount();
	}

	@Override
	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getAttachmentsFolderId();
	}

	@Override
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildPages() {
		return _wikiPage.getChildPages();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getDeletedAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getDeletedAttachmentsFileEntries(start, end);
	}

	@Override
	public int getDeletedAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getDeletedAttachmentsFileEntriesCount();
	}

	@Override
	public com.liferay.portlet.wiki.model.WikiNode getNode() {
		return _wikiPage.getNode();
	}

	@Override
	public long getNodeAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getNodeAttachmentsFolderId();
	}

	@Override
	public com.liferay.portlet.wiki.model.WikiPage getParentPage() {
		return _wikiPage.getParentPage();
	}

	@Override
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getParentPages() {
		return _wikiPage.getParentPages();
	}

	@Override
	public com.liferay.portlet.wiki.model.WikiPage getRedirectPage() {
		return _wikiPage.getRedirectPage();
	}

	@Override
	public com.liferay.portlet.wiki.model.WikiNode getTrashContainer() {
		return _wikiPage.getTrashContainer();
	}

	@Override
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getViewableChildPages() {
		return _wikiPage.getViewableChildPages();
	}

	@Override
	public com.liferay.portlet.wiki.model.WikiPage getViewableParentPage() {
		return _wikiPage.getViewableParentPage();
	}

	@Override
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getViewableParentPages() {
		return _wikiPage.getViewableParentPages();
	}

	@Override
	public boolean isInTrashContainer() {
		return _wikiPage.isInTrashContainer();
	}

	@Override
	public void setAttachmentsFolderId(long attachmentsFolderId) {
		_wikiPage.setAttachmentsFolderId(attachmentsFolderId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public WikiPage getWrappedWikiPage() {
		return _wikiPage;
	}

	@Override
	public WikiPage getWrappedModel() {
		return _wikiPage;
	}

	@Override
	public void resetOriginalValues() {
		_wikiPage.resetOriginalValues();
	}

	private WikiPage _wikiPage;
}
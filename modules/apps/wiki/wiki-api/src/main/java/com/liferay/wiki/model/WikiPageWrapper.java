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

package com.liferay.wiki.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.io.Serializable;

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
@ProviderType
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
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.addAttachmentsFolder();
	}

	@Override
	public java.lang.Object clone() {
		return new WikiPageWrapper((WikiPage)_wikiPage.clone());
	}

	@Override
	public int compareTo(com.liferay.wiki.model.WikiPage wikiPage) {
		return _wikiPage.compareTo(wikiPage);
	}

	@Override
	public com.liferay.wiki.model.WikiPage fetchParentPage() {
		return _wikiPage.fetchParentPage();
	}

	@Override
	public com.liferay.wiki.model.WikiPage fetchRedirectPage() {
		return _wikiPage.fetchRedirectPage();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.getAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.getAttachmentsFileEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.getAttachmentsFileEntries(start, end, obc);
	}

	@Override
	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.getAttachmentsFileEntriesCount();
	}

	@Override
	public long getAttachmentsFolderId() {
		return _wikiPage.getAttachmentsFolderId();
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getChildPages() {
		return _wikiPage.getChildPages();
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
	* Returns the container model ID of this wiki page.
	*
	* @return the container model ID of this wiki page
	*/
	@Override
	public long getContainerModelId() {
		return _wikiPage.getContainerModelId();
	}

	/**
	* Returns the container name of this wiki page.
	*
	* @return the container name of this wiki page
	*/
	@Override
	public java.lang.String getContainerModelName() {
		return _wikiPage.getContainerModelName();
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
	* Returns the create date of this wiki page.
	*
	* @return the create date of this wiki page
	*/
	@Override
	public Date getCreateDate() {
		return _wikiPage.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.getDeletedAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.getDeletedAttachmentsFileEntries(start, end);
	}

	@Override
	public int getDeletedAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.getDeletedAttachmentsFileEntriesCount();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _wikiPage.getExpandoBridge();
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
	* Returns the group ID of this wiki page.
	*
	* @return the group ID of this wiki page
	*/
	@Override
	public long getGroupId() {
		return _wikiPage.getGroupId();
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
	* Returns the last publish date of this wiki page.
	*
	* @return the last publish date of this wiki page
	*/
	@Override
	public Date getLastPublishDate() {
		return _wikiPage.getLastPublishDate();
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
	* Returns the modified date of this wiki page.
	*
	* @return the modified date of this wiki page
	*/
	@Override
	public Date getModifiedDate() {
		return _wikiPage.getModifiedDate();
	}

	@Override
	public com.liferay.wiki.model.WikiNode getNode() {
		return _wikiPage.getNode();
	}

	@Override
	public long getNodeAttachmentsFolderId() {
		return _wikiPage.getNodeAttachmentsFolderId();
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
	* Returns the page ID of this wiki page.
	*
	* @return the page ID of this wiki page
	*/
	@Override
	public long getPageId() {
		return _wikiPage.getPageId();
	}

	/**
	* Returns the parent container model ID of this wiki page.
	*
	* @return the parent container model ID of this wiki page
	*/
	@Override
	public long getParentContainerModelId() {
		return _wikiPage.getParentContainerModelId();
	}

	@Override
	public com.liferay.wiki.model.WikiPage getParentPage()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.getParentPage();
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getParentPages() {
		return _wikiPage.getParentPages();
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
	* Returns the primary key of this wiki page.
	*
	* @return the primary key of this wiki page
	*/
	@Override
	public long getPrimaryKey() {
		return _wikiPage.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _wikiPage.getPrimaryKeyObj();
	}

	@Override
	public com.liferay.wiki.model.WikiPage getRedirectPage()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.getRedirectPage();
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
	* Returns the resource prim key of this wiki page.
	*
	* @return the resource prim key of this wiki page
	*/
	@Override
	public long getResourcePrimKey() {
		return _wikiPage.getResourcePrimKey();
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
	* Returns the status by user ID of this wiki page.
	*
	* @return the status by user ID of this wiki page
	*/
	@Override
	public long getStatusByUserId() {
		return _wikiPage.getStatusByUserId();
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
	* Returns the status by user uuid of this wiki page.
	*
	* @return the status by user uuid of this wiki page
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _wikiPage.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this wiki page.
	*
	* @return the status date of this wiki page
	*/
	@Override
	public Date getStatusDate() {
		return _wikiPage.getStatusDate();
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
	* Returns the title of this wiki page.
	*
	* @return the title of this wiki page
	*/
	@Override
	public java.lang.String getTitle() {
		return _wikiPage.getTitle();
	}

	/**
	* Returns the trash entry created when this wiki page was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this wiki page.
	*
	* @return the trash entry created when this wiki page was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPage.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this wiki page.
	*
	* @return the class primary key of the trash entry for this wiki page
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _wikiPage.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this wiki page.
	*
	* @return the trash handler for this wiki page
	*/
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _wikiPage.getTrashHandler();
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
	* Returns the user name of this wiki page.
	*
	* @return the user name of this wiki page
	*/
	@Override
	public java.lang.String getUserName() {
		return _wikiPage.getUserName();
	}

	/**
	* Returns the user uuid of this wiki page.
	*
	* @return the user uuid of this wiki page
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _wikiPage.getUserUuid();
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
	* Returns the version of this wiki page.
	*
	* @return the version of this wiki page
	*/
	@Override
	public double getVersion() {
		return _wikiPage.getVersion();
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getViewableChildPages() {
		return _wikiPage.getViewableChildPages();
	}

	@Override
	public com.liferay.wiki.model.WikiPage getViewableParentPage() {
		return _wikiPage.getViewableParentPage();
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getViewableParentPages() {
		return _wikiPage.getViewableParentPages();
	}

	@Override
	public int hashCode() {
		return _wikiPage.hashCode();
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

	@Override
	public boolean isCachedModel() {
		return _wikiPage.isCachedModel();
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

	@Override
	public boolean isEscapedModel() {
		return _wikiPage.isEscapedModel();
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
	* Returns <code>true</code> if this wiki page is head.
	*
	* @return <code>true</code> if this wiki page is head; <code>false</code> otherwise
	*/
	@Override
	public boolean isHead() {
		return _wikiPage.isHead();
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
	* Returns <code>true</code> if the parent of this wiki page is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this wiki page is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _wikiPage.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _wikiPage.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _wikiPage.isInTrashImplicitly();
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
	* Returns <code>true</code> if this wiki page is minor edit.
	*
	* @return <code>true</code> if this wiki page is minor edit; <code>false</code> otherwise
	*/
	@Override
	public boolean isMinorEdit() {
		return _wikiPage.isMinorEdit();
	}

	@Override
	public boolean isNew() {
		return _wikiPage.isNew();
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

	@Override
	public boolean isResourceMain() {
		return _wikiPage.isResourceMain();
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
	public void persist() {
		_wikiPage.persist();
	}

	@Override
	public void setAttachmentsFolderId(long attachmentsFolderId) {
		_wikiPage.setAttachmentsFolderId(attachmentsFolderId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_wikiPage.setCachedModel(cachedModel);
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
	* Sets the container model ID of this wiki page.
	*
	* @param containerModelId the container model ID of this wiki page
	*/
	@Override
	public void setContainerModelId(long containerModelId) {
		_wikiPage.setContainerModelId(containerModelId);
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
	* Sets the create date of this wiki page.
	*
	* @param createDate the create date of this wiki page
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_wikiPage.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_wikiPage.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_wikiPage.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_wikiPage.setExpandoBridgeAttributes(serviceContext);
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
	* Sets the group ID of this wiki page.
	*
	* @param groupId the group ID of this wiki page
	*/
	@Override
	public void setGroupId(long groupId) {
		_wikiPage.setGroupId(groupId);
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
	* Sets the last publish date of this wiki page.
	*
	* @param lastPublishDate the last publish date of this wiki page
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_wikiPage.setLastPublishDate(lastPublishDate);
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
	* Sets the modified date of this wiki page.
	*
	* @param modifiedDate the modified date of this wiki page
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_wikiPage.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_wikiPage.setNew(n);
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
	* Sets the page ID of this wiki page.
	*
	* @param pageId the page ID of this wiki page
	*/
	@Override
	public void setPageId(long pageId) {
		_wikiPage.setPageId(pageId);
	}

	/**
	* Sets the parent container model ID of this wiki page.
	*
	* @param parentContainerModelId the parent container model ID of this wiki page
	*/
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		_wikiPage.setParentContainerModelId(parentContainerModelId);
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
	* Sets the primary key of this wiki page.
	*
	* @param primaryKey the primary key of this wiki page
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_wikiPage.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_wikiPage.setPrimaryKeyObj(primaryKeyObj);
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
	* Sets the resource prim key of this wiki page.
	*
	* @param resourcePrimKey the resource prim key of this wiki page
	*/
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		_wikiPage.setResourcePrimKey(resourcePrimKey);
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
	* Sets the status by user ID of this wiki page.
	*
	* @param statusByUserId the status by user ID of this wiki page
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_wikiPage.setStatusByUserId(statusByUserId);
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
	* Sets the status by user uuid of this wiki page.
	*
	* @param statusByUserUuid the status by user uuid of this wiki page
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_wikiPage.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this wiki page.
	*
	* @param statusDate the status date of this wiki page
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_wikiPage.setStatusDate(statusDate);
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
	* Sets the title of this wiki page.
	*
	* @param title the title of this wiki page
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_wikiPage.setTitle(title);
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
	* Sets the user name of this wiki page.
	*
	* @param userName the user name of this wiki page
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_wikiPage.setUserName(userName);
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
	* Sets the uuid of this wiki page.
	*
	* @param uuid the uuid of this wiki page
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_wikiPage.setUuid(uuid);
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

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.wiki.model.WikiPage> toCacheModel() {
		return _wikiPage.toCacheModel();
	}

	@Override
	public com.liferay.wiki.model.WikiPage toEscapedModel() {
		return new WikiPageWrapper(_wikiPage.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _wikiPage.toString();
	}

	@Override
	public com.liferay.wiki.model.WikiPage toUnescapedModel() {
		return new WikiPageWrapper(_wikiPage.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _wikiPage.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WikiPageWrapper)) {
			return false;
		}

		WikiPageWrapper wikiPageWrapper = (WikiPageWrapper)obj;

		if (Validator.equals(_wikiPage, wikiPageWrapper._wikiPage)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _wikiPage.getStagedModelType();
	}

	@Override
	public WikiPage getWrappedModel() {
		return _wikiPage;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _wikiPage.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _wikiPage.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_wikiPage.resetOriginalValues();
	}

	private final WikiPage _wikiPage;
}
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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class WikiPageWrapper
	extends BaseModelWrapper<WikiPage>
	implements ModelWrapper<WikiPage>, WikiPage {

	public WikiPageWrapper(WikiPage wikiPage) {
		super(wikiPage);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		attributes.put("minorEdit", isMinorEdit());
		attributes.put("content", getContent());
		attributes.put("summary", getSummary());
		attributes.put("format", getFormat());
		attributes.put("head", isHead());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	public com.liferay.portal.kernel.repository.model.Folder
			addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.addAttachmentsFolder();
	}

	@Override
	public WikiPage fetchParentPage() {
		return model.fetchParentPage();
	}

	@Override
	public WikiPage fetchRedirectPage() {
		return model.fetchRedirectPage();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries(
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntries(start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries(
				String[] mimeTypes, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.FileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntries(mimeTypes, start, end, obc);
	}

	@Override
	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntriesCount();
	}

	@Override
	public int getAttachmentsFileEntriesCount(String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntriesCount(mimeTypes);
	}

	@Override
	public long getAttachmentsFolderId() {
		return model.getAttachmentsFolderId();
	}

	@Override
	public java.util.List<WikiPage> getChildPages() {
		return model.getChildPages();
	}

	/**
	 * Returns the company ID of this wiki page.
	 *
	 * @return the company ID of this wiki page
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this wiki page.
	 *
	 * @return the container model ID of this wiki page
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this wiki page.
	 *
	 * @return the container name of this wiki page
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the content of this wiki page.
	 *
	 * @return the content of this wiki page
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the create date of this wiki page.
	 *
	 * @return the create date of this wiki page
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getDeletedAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDeletedAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getDeletedAttachmentsFileEntries(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDeletedAttachmentsFileEntries(start, end);
	}

	@Override
	public int getDeletedAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDeletedAttachmentsFileEntriesCount();
	}

	/**
	 * Returns the format of this wiki page.
	 *
	 * @return the format of this wiki page
	 */
	@Override
	public String getFormat() {
		return model.getFormat();
	}

	/**
	 * Returns the group ID of this wiki page.
	 *
	 * @return the group ID of this wiki page
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the head of this wiki page.
	 *
	 * @return the head of this wiki page
	 */
	@Override
	public boolean getHead() {
		return model.getHead();
	}

	/**
	 * Returns the last publish date of this wiki page.
	 *
	 * @return the last publish date of this wiki page
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the minor edit of this wiki page.
	 *
	 * @return the minor edit of this wiki page
	 */
	@Override
	public boolean getMinorEdit() {
		return model.getMinorEdit();
	}

	/**
	 * Returns the modified date of this wiki page.
	 *
	 * @return the modified date of this wiki page
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this wiki page.
	 *
	 * @return the mvcc version of this wiki page
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	@Override
	public WikiNode getNode() {
		return model.getNode();
	}

	@Override
	public long getNodeAttachmentsFolderId() {
		return model.getNodeAttachmentsFolderId();
	}

	/**
	 * Returns the node ID of this wiki page.
	 *
	 * @return the node ID of this wiki page
	 */
	@Override
	public long getNodeId() {
		return model.getNodeId();
	}

	/**
	 * Returns the page ID of this wiki page.
	 *
	 * @return the page ID of this wiki page
	 */
	@Override
	public long getPageId() {
		return model.getPageId();
	}

	/**
	 * Returns the parent container model ID of this wiki page.
	 *
	 * @return the parent container model ID of this wiki page
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	@Override
	public WikiPage getParentPage()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentPage();
	}

	@Override
	public java.util.List<WikiPage> getParentPages() {
		return model.getParentPages();
	}

	/**
	 * Returns the parent title of this wiki page.
	 *
	 * @return the parent title of this wiki page
	 */
	@Override
	public String getParentTitle() {
		return model.getParentTitle();
	}

	/**
	 * Returns the primary key of this wiki page.
	 *
	 * @return the primary key of this wiki page
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public WikiPage getRedirectPage()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getRedirectPage();
	}

	/**
	 * Returns the redirect title of this wiki page.
	 *
	 * @return the redirect title of this wiki page
	 */
	@Override
	public String getRedirectTitle() {
		return model.getRedirectTitle();
	}

	/**
	 * Returns the resource prim key of this wiki page.
	 *
	 * @return the resource prim key of this wiki page
	 */
	@Override
	public long getResourcePrimKey() {
		return model.getResourcePrimKey();
	}

	/**
	 * Returns the status of this wiki page.
	 *
	 * @return the status of this wiki page
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this wiki page.
	 *
	 * @return the status by user ID of this wiki page
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this wiki page.
	 *
	 * @return the status by user name of this wiki page
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this wiki page.
	 *
	 * @return the status by user uuid of this wiki page
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this wiki page.
	 *
	 * @return the status date of this wiki page
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the summary of this wiki page.
	 *
	 * @return the summary of this wiki page
	 */
	@Override
	public String getSummary() {
		return model.getSummary();
	}

	/**
	 * Returns the title of this wiki page.
	 *
	 * @return the title of this wiki page
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the trash entry created when this wiki page was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this wiki page.
	 *
	 * @return the trash entry created when this wiki page was moved to the Recycle Bin
	 */
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTrashEntry();
	}

	/**
	 * Returns the class primary key of the trash entry for this wiki page.
	 *
	 * @return the class primary key of the trash entry for this wiki page
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the trash handler for this wiki page.
	 *
	 * @return the trash handler for this wiki page
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return model.getTrashHandler();
	}

	/**
	 * Returns the user ID of this wiki page.
	 *
	 * @return the user ID of this wiki page
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this wiki page.
	 *
	 * @return the user name of this wiki page
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this wiki page.
	 *
	 * @return the user uuid of this wiki page
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this wiki page.
	 *
	 * @return the uuid of this wiki page
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this wiki page.
	 *
	 * @return the version of this wiki page
	 */
	@Override
	public double getVersion() {
		return model.getVersion();
	}

	@Override
	public java.util.List<WikiPage> getViewableChildPages() {
		return model.getViewableChildPages();
	}

	@Override
	public WikiPage getViewableParentPage() {
		return model.getViewableParentPage();
	}

	@Override
	public java.util.List<WikiPage> getViewableParentPages() {
		return model.getViewableParentPages();
	}

	/**
	 * Returns <code>true</code> if this wiki page is approved.
	 *
	 * @return <code>true</code> if this wiki page is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this wiki page is denied.
	 *
	 * @return <code>true</code> if this wiki page is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this wiki page is a draft.
	 *
	 * @return <code>true</code> if this wiki page is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this wiki page is expired.
	 *
	 * @return <code>true</code> if this wiki page is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this wiki page is head.
	 *
	 * @return <code>true</code> if this wiki page is head; <code>false</code> otherwise
	 */
	@Override
	public boolean isHead() {
		return model.isHead();
	}

	/**
	 * Returns <code>true</code> if this wiki page is inactive.
	 *
	 * @return <code>true</code> if this wiki page is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this wiki page is incomplete.
	 *
	 * @return <code>true</code> if this wiki page is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this wiki page is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this wiki page is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if the parent of this wiki page is in the Recycle Bin.
	 *
	 * @return <code>true</code> if the parent of this wiki page is in the Recycle Bin; <code>false</code> otherwise
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
	 * Returns <code>true</code> if this wiki page is minor edit.
	 *
	 * @return <code>true</code> if this wiki page is minor edit; <code>false</code> otherwise
	 */
	@Override
	public boolean isMinorEdit() {
		return model.isMinorEdit();
	}

	/**
	 * Returns <code>true</code> if this wiki page is pending.
	 *
	 * @return <code>true</code> if this wiki page is pending; <code>false</code> otherwise
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
	 * Returns <code>true</code> if this wiki page is scheduled.
	 *
	 * @return <code>true</code> if this wiki page is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a wiki page model instance should use the <code>WikiPage</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setAttachmentsFolderId(long attachmentsFolderId) {
		model.setAttachmentsFolderId(attachmentsFolderId);
	}

	/**
	 * Sets the company ID of this wiki page.
	 *
	 * @param companyId the company ID of this wiki page
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this wiki page.
	 *
	 * @param containerModelId the container model ID of this wiki page
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the content of this wiki page.
	 *
	 * @param content the content of this wiki page
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the create date of this wiki page.
	 *
	 * @param createDate the create date of this wiki page
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the format of this wiki page.
	 *
	 * @param format the format of this wiki page
	 */
	@Override
	public void setFormat(String format) {
		model.setFormat(format);
	}

	/**
	 * Sets the group ID of this wiki page.
	 *
	 * @param groupId the group ID of this wiki page
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets whether this wiki page is head.
	 *
	 * @param head the head of this wiki page
	 */
	@Override
	public void setHead(boolean head) {
		model.setHead(head);
	}

	/**
	 * Sets the last publish date of this wiki page.
	 *
	 * @param lastPublishDate the last publish date of this wiki page
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets whether this wiki page is minor edit.
	 *
	 * @param minorEdit the minor edit of this wiki page
	 */
	@Override
	public void setMinorEdit(boolean minorEdit) {
		model.setMinorEdit(minorEdit);
	}

	/**
	 * Sets the modified date of this wiki page.
	 *
	 * @param modifiedDate the modified date of this wiki page
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this wiki page.
	 *
	 * @param mvccVersion the mvcc version of this wiki page
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the node ID of this wiki page.
	 *
	 * @param nodeId the node ID of this wiki page
	 */
	@Override
	public void setNodeId(long nodeId) {
		model.setNodeId(nodeId);
	}

	/**
	 * Sets the page ID of this wiki page.
	 *
	 * @param pageId the page ID of this wiki page
	 */
	@Override
	public void setPageId(long pageId) {
		model.setPageId(pageId);
	}

	/**
	 * Sets the parent container model ID of this wiki page.
	 *
	 * @param parentContainerModelId the parent container model ID of this wiki page
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the parent title of this wiki page.
	 *
	 * @param parentTitle the parent title of this wiki page
	 */
	@Override
	public void setParentTitle(String parentTitle) {
		model.setParentTitle(parentTitle);
	}

	/**
	 * Sets the primary key of this wiki page.
	 *
	 * @param primaryKey the primary key of this wiki page
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the redirect title of this wiki page.
	 *
	 * @param redirectTitle the redirect title of this wiki page
	 */
	@Override
	public void setRedirectTitle(String redirectTitle) {
		model.setRedirectTitle(redirectTitle);
	}

	/**
	 * Sets the resource prim key of this wiki page.
	 *
	 * @param resourcePrimKey the resource prim key of this wiki page
	 */
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		model.setResourcePrimKey(resourcePrimKey);
	}

	/**
	 * Sets the status of this wiki page.
	 *
	 * @param status the status of this wiki page
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this wiki page.
	 *
	 * @param statusByUserId the status by user ID of this wiki page
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this wiki page.
	 *
	 * @param statusByUserName the status by user name of this wiki page
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this wiki page.
	 *
	 * @param statusByUserUuid the status by user uuid of this wiki page
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this wiki page.
	 *
	 * @param statusDate the status date of this wiki page
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the summary of this wiki page.
	 *
	 * @param summary the summary of this wiki page
	 */
	@Override
	public void setSummary(String summary) {
		model.setSummary(summary);
	}

	/**
	 * Sets the title of this wiki page.
	 *
	 * @param title the title of this wiki page
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the user ID of this wiki page.
	 *
	 * @param userId the user ID of this wiki page
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this wiki page.
	 *
	 * @param userName the user name of this wiki page
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this wiki page.
	 *
	 * @param userUuid the user uuid of this wiki page
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this wiki page.
	 *
	 * @param uuid the uuid of this wiki page
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this wiki page.
	 *
	 * @param version the version of this wiki page
	 */
	@Override
	public void setVersion(double version) {
		model.setVersion(version);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected WikiPageWrapper wrap(WikiPage wikiPage) {
		return new WikiPageWrapper(wikiPage);
	}

}
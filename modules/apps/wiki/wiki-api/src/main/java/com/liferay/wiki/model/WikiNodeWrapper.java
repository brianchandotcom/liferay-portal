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

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link WikiNode}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiNode
 * @generated
 */
@ProviderType
public class WikiNodeWrapper extends BaseModelWrapper<WikiNode>
	implements WikiNode, ModelWrapper<WikiNode> {
	public WikiNodeWrapper(WikiNode wikiNode) {
		super(wikiNode);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.addAttachmentsFolder();
	}

	@Override
	public long getAttachmentsFolderId() {
		return model.getAttachmentsFolderId();
	}

	/**
	* Returns the company ID of this wiki node.
	*
	* @return the company ID of this wiki node
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the container model ID of this wiki node.
	*
	* @return the container model ID of this wiki node
	*/
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	* Returns the container name of this wiki node.
	*
	* @return the container name of this wiki node
	*/
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	* Returns the create date of this wiki node.
	*
	* @return the create date of this wiki node
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getDeletedAttachmentsFiles();
	}

	/**
	* Returns the description of this wiki node.
	*
	* @return the description of this wiki node
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the group ID of this wiki node.
	*
	* @return the group ID of this wiki node
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the last post date of this wiki node.
	*
	* @return the last post date of this wiki node
	*/
	@Override
	public Date getLastPostDate() {
		return model.getLastPostDate();
	}

	/**
	* Returns the last publish date of this wiki node.
	*
	* @return the last publish date of this wiki node
	*/
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	* Returns the modified date of this wiki node.
	*
	* @return the modified date of this wiki node
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name of this wiki node.
	*
	* @return the name of this wiki node
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the node ID of this wiki node.
	*
	* @return the node ID of this wiki node
	*/
	@Override
	public long getNodeId() {
		return model.getNodeId();
	}

	/**
	* Returns the parent container model ID of this wiki node.
	*
	* @return the parent container model ID of this wiki node
	*/
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	/**
	* Returns the primary key of this wiki node.
	*
	* @return the primary key of this wiki node
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the status of this wiki node.
	*
	* @return the status of this wiki node
	*/
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	* Returns the status by user ID of this wiki node.
	*
	* @return the status by user ID of this wiki node
	*/
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this wiki node.
	*
	* @return the status by user name of this wiki node
	*/
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this wiki node.
	*
	* @return the status by user uuid of this wiki node
	*/
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this wiki node.
	*
	* @return the status date of this wiki node
	*/
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	* Returns the trash entry created when this wiki node was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this wiki node.
	*
	* @return the trash entry created when this wiki node was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this wiki node.
	*
	* @return the class primary key of the trash entry for this wiki node
	*/
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this wiki node.
	*
	* @return the trash handler for this wiki node
	* @deprecated As of Judson (7.1.x), with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return model.getTrashHandler();
	}

	/**
	* Returns the user ID of this wiki node.
	*
	* @return the user ID of this wiki node
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this wiki node.
	*
	* @return the user name of this wiki node
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this wiki node.
	*
	* @return the user uuid of this wiki node
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this wiki node.
	*
	* @return the uuid of this wiki node
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	* Returns <code>true</code> if this wiki node is approved.
	*
	* @return <code>true</code> if this wiki node is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	* Returns <code>true</code> if this wiki node is denied.
	*
	* @return <code>true</code> if this wiki node is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	* Returns <code>true</code> if this wiki node is a draft.
	*
	* @return <code>true</code> if this wiki node is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	* Returns <code>true</code> if this wiki node is expired.
	*
	* @return <code>true</code> if this wiki node is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	* Returns <code>true</code> if this wiki node is inactive.
	*
	* @return <code>true</code> if this wiki node is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	* Returns <code>true</code> if this wiki node is incomplete.
	*
	* @return <code>true</code> if this wiki node is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this wiki node is in the Recycle Bin.
	*
	* @return <code>true</code> if this wiki node is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this wiki node is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this wiki node is in the Recycle Bin; <code>false</code> otherwise
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
	* Returns <code>true</code> if this wiki node is pending.
	*
	* @return <code>true</code> if this wiki node is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	* Returns <code>true</code> if this wiki node is scheduled.
	*
	* @return <code>true</code> if this wiki node is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this wiki node.
	*
	* @param companyId the company ID of this wiki node
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the container model ID of this wiki node.
	*
	* @param containerModelId the container model ID of this wiki node
	*/
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	* Sets the create date of this wiki node.
	*
	* @param createDate the create date of this wiki node
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the description of this wiki node.
	*
	* @param description the description of this wiki node
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the group ID of this wiki node.
	*
	* @param groupId the group ID of this wiki node
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the last post date of this wiki node.
	*
	* @param lastPostDate the last post date of this wiki node
	*/
	@Override
	public void setLastPostDate(Date lastPostDate) {
		model.setLastPostDate(lastPostDate);
	}

	/**
	* Sets the last publish date of this wiki node.
	*
	* @param lastPublishDate the last publish date of this wiki node
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this wiki node.
	*
	* @param modifiedDate the modified date of this wiki node
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this wiki node.
	*
	* @param name the name of this wiki node
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the node ID of this wiki node.
	*
	* @param nodeId the node ID of this wiki node
	*/
	@Override
	public void setNodeId(long nodeId) {
		model.setNodeId(nodeId);
	}

	/**
	* Sets the parent container model ID of this wiki node.
	*
	* @param parentContainerModelId the parent container model ID of this wiki node
	*/
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	* Sets the primary key of this wiki node.
	*
	* @param primaryKey the primary key of this wiki node
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the status of this wiki node.
	*
	* @param status the status of this wiki node
	*/
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	* Sets the status by user ID of this wiki node.
	*
	* @param statusByUserId the status by user ID of this wiki node
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this wiki node.
	*
	* @param statusByUserName the status by user name of this wiki node
	*/
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this wiki node.
	*
	* @param statusByUserUuid the status by user uuid of this wiki node
	*/
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this wiki node.
	*
	* @param statusDate the status date of this wiki node
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	* Sets the user ID of this wiki node.
	*
	* @param userId the user ID of this wiki node
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this wiki node.
	*
	* @param userName the user name of this wiki node
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this wiki node.
	*
	* @param userUuid the user uuid of this wiki node
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this wiki node.
	*
	* @param uuid the uuid of this wiki node
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected WikiNodeWrapper wrap(WikiNode wikiNode) {
		return new WikiNodeWrapper(wikiNode);
	}
}
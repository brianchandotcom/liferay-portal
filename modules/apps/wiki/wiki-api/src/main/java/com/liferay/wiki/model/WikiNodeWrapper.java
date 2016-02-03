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
 * This class is a wrapper for {@link WikiNode}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiNode
 * @generated
 */
@ProviderType
public class WikiNodeWrapper implements WikiNode, ModelWrapper<WikiNode> {
	public WikiNodeWrapper(WikiNode wikiNode) {
		_wikiNode = wikiNode;
	}

	@Override
	public Class<?> getModelClass() {
		return WikiNode.class;
	}

	@Override
	public String getModelClassName() {
		return WikiNode.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("nodeId", getNodeId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("lastPostDate", getLastPostDate());
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

		Long nodeId = (Long)attributes.get("nodeId");

		if (nodeId != null) {
			setNodeId(nodeId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date lastPostDate = (Date)attributes.get("lastPostDate");

		if (lastPostDate != null) {
			setLastPostDate(lastPostDate);
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
		return _wikiNode.addAttachmentsFolder();
	}

	@Override
	public java.lang.Object clone() {
		return new WikiNodeWrapper((WikiNode)_wikiNode.clone());
	}

	@Override
	public int compareTo(com.liferay.wiki.model.WikiNode wikiNode) {
		return _wikiNode.compareTo(wikiNode);
	}

	@Override
	public long getAttachmentsFolderId() {
		return _wikiNode.getAttachmentsFolderId();
	}

	/**
	* Returns the company ID of this wiki node.
	*
	* @return the company ID of this wiki node
	*/
	@Override
	public long getCompanyId() {
		return _wikiNode.getCompanyId();
	}

	/**
	* Returns the container model ID of this wiki node.
	*
	* @return the container model ID of this wiki node
	*/
	@Override
	public long getContainerModelId() {
		return _wikiNode.getContainerModelId();
	}

	/**
	* Returns the container name of this wiki node.
	*
	* @return the container name of this wiki node
	*/
	@Override
	public java.lang.String getContainerModelName() {
		return _wikiNode.getContainerModelName();
	}

	/**
	* Returns the create date of this wiki node.
	*
	* @return the create date of this wiki node
	*/
	@Override
	public Date getCreateDate() {
		return _wikiNode.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNode.getDeletedAttachmentsFiles();
	}

	/**
	* Returns the description of this wiki node.
	*
	* @return the description of this wiki node
	*/
	@Override
	public java.lang.String getDescription() {
		return _wikiNode.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _wikiNode.getExpandoBridge();
	}

	/**
	* Returns the group ID of this wiki node.
	*
	* @return the group ID of this wiki node
	*/
	@Override
	public long getGroupId() {
		return _wikiNode.getGroupId();
	}

	/**
	* Returns the last post date of this wiki node.
	*
	* @return the last post date of this wiki node
	*/
	@Override
	public Date getLastPostDate() {
		return _wikiNode.getLastPostDate();
	}

	/**
	* Returns the last publish date of this wiki node.
	*
	* @return the last publish date of this wiki node
	*/
	@Override
	public Date getLastPublishDate() {
		return _wikiNode.getLastPublishDate();
	}

	/**
	* Returns the modified date of this wiki node.
	*
	* @return the modified date of this wiki node
	*/
	@Override
	public Date getModifiedDate() {
		return _wikiNode.getModifiedDate();
	}

	/**
	* Returns the name of this wiki node.
	*
	* @return the name of this wiki node
	*/
	@Override
	public java.lang.String getName() {
		return _wikiNode.getName();
	}

	/**
	* Returns the node ID of this wiki node.
	*
	* @return the node ID of this wiki node
	*/
	@Override
	public long getNodeId() {
		return _wikiNode.getNodeId();
	}

	/**
	* Returns the parent container model ID of this wiki node.
	*
	* @return the parent container model ID of this wiki node
	*/
	@Override
	public long getParentContainerModelId() {
		return _wikiNode.getParentContainerModelId();
	}

	/**
	* Returns the primary key of this wiki node.
	*
	* @return the primary key of this wiki node
	*/
	@Override
	public long getPrimaryKey() {
		return _wikiNode.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _wikiNode.getPrimaryKeyObj();
	}

	/**
	* Returns the status of this wiki node.
	*
	* @return the status of this wiki node
	*/
	@Override
	public int getStatus() {
		return _wikiNode.getStatus();
	}

	/**
	* Returns the status by user ID of this wiki node.
	*
	* @return the status by user ID of this wiki node
	*/
	@Override
	public long getStatusByUserId() {
		return _wikiNode.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this wiki node.
	*
	* @return the status by user name of this wiki node
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _wikiNode.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this wiki node.
	*
	* @return the status by user uuid of this wiki node
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _wikiNode.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this wiki node.
	*
	* @return the status date of this wiki node
	*/
	@Override
	public Date getStatusDate() {
		return _wikiNode.getStatusDate();
	}

	/**
	* Returns the trash entry created when this wiki node was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this wiki node.
	*
	* @return the trash entry created when this wiki node was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiNode.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this wiki node.
	*
	* @return the class primary key of the trash entry for this wiki node
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _wikiNode.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this wiki node.
	*
	* @return the trash handler for this wiki node
	*/
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _wikiNode.getTrashHandler();
	}

	/**
	* Returns the user ID of this wiki node.
	*
	* @return the user ID of this wiki node
	*/
	@Override
	public long getUserId() {
		return _wikiNode.getUserId();
	}

	/**
	* Returns the user name of this wiki node.
	*
	* @return the user name of this wiki node
	*/
	@Override
	public java.lang.String getUserName() {
		return _wikiNode.getUserName();
	}

	/**
	* Returns the user uuid of this wiki node.
	*
	* @return the user uuid of this wiki node
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _wikiNode.getUserUuid();
	}

	/**
	* Returns the uuid of this wiki node.
	*
	* @return the uuid of this wiki node
	*/
	@Override
	public java.lang.String getUuid() {
		return _wikiNode.getUuid();
	}

	@Override
	public int hashCode() {
		return _wikiNode.hashCode();
	}

	/**
	* Returns <code>true</code> if this wiki node is approved.
	*
	* @return <code>true</code> if this wiki node is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _wikiNode.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _wikiNode.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this wiki node is denied.
	*
	* @return <code>true</code> if this wiki node is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _wikiNode.isDenied();
	}

	/**
	* Returns <code>true</code> if this wiki node is a draft.
	*
	* @return <code>true</code> if this wiki node is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _wikiNode.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _wikiNode.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this wiki node is expired.
	*
	* @return <code>true</code> if this wiki node is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _wikiNode.isExpired();
	}

	/**
	* Returns <code>true</code> if this wiki node is in the Recycle Bin.
	*
	* @return <code>true</code> if this wiki node is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _wikiNode.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this wiki node is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this wiki node is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _wikiNode.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _wikiNode.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _wikiNode.isInTrashImplicitly();
	}

	/**
	* Returns <code>true</code> if this wiki node is inactive.
	*
	* @return <code>true</code> if this wiki node is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _wikiNode.isInactive();
	}

	/**
	* Returns <code>true</code> if this wiki node is incomplete.
	*
	* @return <code>true</code> if this wiki node is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _wikiNode.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _wikiNode.isNew();
	}

	/**
	* Returns <code>true</code> if this wiki node is pending.
	*
	* @return <code>true</code> if this wiki node is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _wikiNode.isPending();
	}

	/**
	* Returns <code>true</code> if this wiki node is scheduled.
	*
	* @return <code>true</code> if this wiki node is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _wikiNode.isScheduled();
	}

	@Override
	public void persist() {
		_wikiNode.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_wikiNode.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this wiki node.
	*
	* @param companyId the company ID of this wiki node
	*/
	@Override
	public void setCompanyId(long companyId) {
		_wikiNode.setCompanyId(companyId);
	}

	/**
	* Sets the container model ID of this wiki node.
	*
	* @param containerModelId the container model ID of this wiki node
	*/
	@Override
	public void setContainerModelId(long containerModelId) {
		_wikiNode.setContainerModelId(containerModelId);
	}

	/**
	* Sets the create date of this wiki node.
	*
	* @param createDate the create date of this wiki node
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_wikiNode.setCreateDate(createDate);
	}

	/**
	* Sets the description of this wiki node.
	*
	* @param description the description of this wiki node
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_wikiNode.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_wikiNode.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_wikiNode.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_wikiNode.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this wiki node.
	*
	* @param groupId the group ID of this wiki node
	*/
	@Override
	public void setGroupId(long groupId) {
		_wikiNode.setGroupId(groupId);
	}

	/**
	* Sets the last post date of this wiki node.
	*
	* @param lastPostDate the last post date of this wiki node
	*/
	@Override
	public void setLastPostDate(Date lastPostDate) {
		_wikiNode.setLastPostDate(lastPostDate);
	}

	/**
	* Sets the last publish date of this wiki node.
	*
	* @param lastPublishDate the last publish date of this wiki node
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_wikiNode.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this wiki node.
	*
	* @param modifiedDate the modified date of this wiki node
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_wikiNode.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this wiki node.
	*
	* @param name the name of this wiki node
	*/
	@Override
	public void setName(java.lang.String name) {
		_wikiNode.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_wikiNode.setNew(n);
	}

	/**
	* Sets the node ID of this wiki node.
	*
	* @param nodeId the node ID of this wiki node
	*/
	@Override
	public void setNodeId(long nodeId) {
		_wikiNode.setNodeId(nodeId);
	}

	/**
	* Sets the parent container model ID of this wiki node.
	*
	* @param parentContainerModelId the parent container model ID of this wiki node
	*/
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		_wikiNode.setParentContainerModelId(parentContainerModelId);
	}

	/**
	* Sets the primary key of this wiki node.
	*
	* @param primaryKey the primary key of this wiki node
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_wikiNode.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_wikiNode.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the status of this wiki node.
	*
	* @param status the status of this wiki node
	*/
	@Override
	public void setStatus(int status) {
		_wikiNode.setStatus(status);
	}

	/**
	* Sets the status by user ID of this wiki node.
	*
	* @param statusByUserId the status by user ID of this wiki node
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_wikiNode.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this wiki node.
	*
	* @param statusByUserName the status by user name of this wiki node
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_wikiNode.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this wiki node.
	*
	* @param statusByUserUuid the status by user uuid of this wiki node
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_wikiNode.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this wiki node.
	*
	* @param statusDate the status date of this wiki node
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_wikiNode.setStatusDate(statusDate);
	}

	/**
	* Sets the user ID of this wiki node.
	*
	* @param userId the user ID of this wiki node
	*/
	@Override
	public void setUserId(long userId) {
		_wikiNode.setUserId(userId);
	}

	/**
	* Sets the user name of this wiki node.
	*
	* @param userName the user name of this wiki node
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_wikiNode.setUserName(userName);
	}

	/**
	* Sets the user uuid of this wiki node.
	*
	* @param userUuid the user uuid of this wiki node
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_wikiNode.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this wiki node.
	*
	* @param uuid the uuid of this wiki node
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_wikiNode.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.wiki.model.WikiNode> toCacheModel() {
		return _wikiNode.toCacheModel();
	}

	@Override
	public com.liferay.wiki.model.WikiNode toEscapedModel() {
		return new WikiNodeWrapper(_wikiNode.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _wikiNode.toString();
	}

	@Override
	public com.liferay.wiki.model.WikiNode toUnescapedModel() {
		return new WikiNodeWrapper(_wikiNode.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _wikiNode.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WikiNodeWrapper)) {
			return false;
		}

		WikiNodeWrapper wikiNodeWrapper = (WikiNodeWrapper)obj;

		if (Validator.equals(_wikiNode, wikiNodeWrapper._wikiNode)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _wikiNode.getStagedModelType();
	}

	@Override
	public WikiNode getWrappedModel() {
		return _wikiNode;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _wikiNode.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _wikiNode.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_wikiNode.resetOriginalValues();
	}

	private final WikiNode _wikiNode;
}
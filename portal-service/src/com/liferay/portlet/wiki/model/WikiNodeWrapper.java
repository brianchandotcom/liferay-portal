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
 * This class is a wrapper for {@link WikiNode}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiNode
 * @generated
 */
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
	* Returns the primary key of this wiki node.
	*
	* @return the primary key of this wiki node
	*/
	@Override
	public long getPrimaryKey() {
		return _wikiNode.getPrimaryKey();
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

	/**
	* Returns the uuid of this wiki node.
	*
	* @return the uuid of this wiki node
	*/
	@Override
	public java.lang.String getUuid() {
		return _wikiNode.getUuid();
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
	* Sets the node ID of this wiki node.
	*
	* @param nodeId the node ID of this wiki node
	*/
	@Override
	public void setNodeId(long nodeId) {
		_wikiNode.setNodeId(nodeId);
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
	* Sets the group ID of this wiki node.
	*
	* @param groupId the group ID of this wiki node
	*/
	@Override
	public void setGroupId(long groupId) {
		_wikiNode.setGroupId(groupId);
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
	* Sets the company ID of this wiki node.
	*
	* @param companyId the company ID of this wiki node
	*/
	@Override
	public void setCompanyId(long companyId) {
		_wikiNode.setCompanyId(companyId);
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
	* Sets the user ID of this wiki node.
	*
	* @param userId the user ID of this wiki node
	*/
	@Override
	public void setUserId(long userId) {
		_wikiNode.setUserId(userId);
	}

	/**
	* Returns the user uuid of this wiki node.
	*
	* @return the user uuid of this wiki node
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNode.getUserUuid();
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
	* Returns the user name of this wiki node.
	*
	* @return the user name of this wiki node
	*/
	@Override
	public java.lang.String getUserName() {
		return _wikiNode.getUserName();
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
	* Returns the create date of this wiki node.
	*
	* @return the create date of this wiki node
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _wikiNode.getCreateDate();
	}

	/**
	* Sets the create date of this wiki node.
	*
	* @param createDate the create date of this wiki node
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_wikiNode.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this wiki node.
	*
	* @return the modified date of this wiki node
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _wikiNode.getModifiedDate();
	}

	/**
	* Sets the modified date of this wiki node.
	*
	* @param modifiedDate the modified date of this wiki node
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_wikiNode.setModifiedDate(modifiedDate);
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
	* Sets the name of this wiki node.
	*
	* @param name the name of this wiki node
	*/
	@Override
	public void setName(java.lang.String name) {
		_wikiNode.setName(name);
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

	/**
	* Sets the description of this wiki node.
	*
	* @param description the description of this wiki node
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_wikiNode.setDescription(description);
	}

	/**
	* Returns the last post date of this wiki node.
	*
	* @return the last post date of this wiki node
	*/
	@Override
	public java.util.Date getLastPostDate() {
		return _wikiNode.getLastPostDate();
	}

	/**
	* Sets the last post date of this wiki node.
	*
	* @param lastPostDate the last post date of this wiki node
	*/
	@Override
	public void setLastPostDate(java.util.Date lastPostDate) {
		_wikiNode.setLastPostDate(lastPostDate);
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
	* Sets the status of this wiki node.
	*
	* @param status the status of this wiki node
	*/
	@Override
	public void setStatus(int status) {
		_wikiNode.setStatus(status);
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
	* Sets the status by user ID of this wiki node.
	*
	* @param statusByUserId the status by user ID of this wiki node
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_wikiNode.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this wiki node.
	*
	* @return the status by user uuid of this wiki node
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNode.getStatusByUserUuid();
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
	* Returns the status by user name of this wiki node.
	*
	* @return the status by user name of this wiki node
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _wikiNode.getStatusByUserName();
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
	* Returns the status date of this wiki node.
	*
	* @return the status date of this wiki node
	*/
	@Override
	public java.util.Date getStatusDate() {
		return _wikiNode.getStatusDate();
	}

	/**
	* Sets the status date of this wiki node.
	*
	* @param statusDate the status date of this wiki node
	*/
	@Override
	public void setStatusDate(java.util.Date statusDate) {
		_wikiNode.setStatusDate(statusDate);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	*/
	@Override
	public boolean getApproved() {
		return _wikiNode.getApproved();
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
	* Sets the container model ID of this wiki node.
	*
	* @param container model ID of this wiki node
	*/
	@Override
	public void setContainerModelId(long containerModelId) {
		_wikiNode.setContainerModelId(containerModelId);
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
	* Returns the parent container model ID of this wiki node.
	*
	* @return the parent container model ID of this wiki node
	*/
	@Override
	public long getParentContainerModelId() {
		return _wikiNode.getParentContainerModelId();
	}

	/**
	* Sets the parent container model ID of this wiki node.
	*
	* @param parent container model ID of this wiki node
	*/
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		_wikiNode.setParentContainerModelId(parentContainerModelId);
	}

	@Override
	public boolean isNew() {
		return _wikiNode.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_wikiNode.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _wikiNode.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_wikiNode.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _wikiNode.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _wikiNode.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_wikiNode.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _wikiNode.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_wikiNode.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_wikiNode.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_wikiNode.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new WikiNodeWrapper((WikiNode)_wikiNode.clone());
	}

	@Override
	public int compareTo(com.liferay.portlet.wiki.model.WikiNode wikiNode) {
		return _wikiNode.compareTo(wikiNode);
	}

	@Override
	public int hashCode() {
		return _wikiNode.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.wiki.model.WikiNode> toCacheModel() {
		return _wikiNode.toCacheModel();
	}

	@Override
	public com.liferay.portlet.wiki.model.WikiNode toEscapedModel() {
		return new WikiNodeWrapper(_wikiNode.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.wiki.model.WikiNode toUnescapedModel() {
		return new WikiNodeWrapper(_wikiNode.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _wikiNode.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _wikiNode.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_wikiNode.persist();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNode.addAttachmentsFolder();
	}

	@Override
	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNode.getAttachmentsFolderId();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNode.getDeletedAttachmentsFiles();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public WikiNode getWrappedWikiNode() {
		return _wikiNode;
	}

	@Override
	public WikiNode getWrappedModel() {
		return _wikiNode;
	}

	@Override
	public void resetOriginalValues() {
		_wikiNode.resetOriginalValues();
	}

	private WikiNode _wikiNode;
}
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link WikiNode}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiNode
 * @generated
 */
public class WikiNodeWrapper implements WikiNode, ModelWrapper<WikiNode> {
	public WikiNodeWrapper(WikiNode wikiNode) {
		_wikiNode = wikiNode;
	}

	public Class<?> getModelClass() {
		return WikiNode.class;
	}

	public String getModelClassName() {
		return WikiNode.class.getName();
	}

	/**
	* Returns the primary key of this wiki node.
	*
	* @return the primary key of this wiki node
	*/
	public long getPrimaryKey() {
		return _wikiNode.getPrimaryKey();
	}

	/**
	* Sets the primary key of this wiki node.
	*
	* @param primaryKey the primary key of this wiki node
	*/
	public void setPrimaryKey(long primaryKey) {
		_wikiNode.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this wiki node.
	*
	* @return the uuid of this wiki node
	*/
	public java.lang.String getUuid() {
		return _wikiNode.getUuid();
	}

	/**
	* Sets the uuid of this wiki node.
	*
	* @param uuid the uuid of this wiki node
	*/
	public void setUuid(java.lang.String uuid) {
		_wikiNode.setUuid(uuid);
	}

	/**
	* Returns the node ID of this wiki node.
	*
	* @return the node ID of this wiki node
	*/
	public long getNodeId() {
		return _wikiNode.getNodeId();
	}

	/**
	* Sets the node ID of this wiki node.
	*
	* @param nodeId the node ID of this wiki node
	*/
	public void setNodeId(long nodeId) {
		_wikiNode.setNodeId(nodeId);
	}

	/**
	* Returns the group ID of this wiki node.
	*
	* @return the group ID of this wiki node
	*/
	public long getGroupId() {
		return _wikiNode.getGroupId();
	}

	/**
	* Sets the group ID of this wiki node.
	*
	* @param groupId the group ID of this wiki node
	*/
	public void setGroupId(long groupId) {
		_wikiNode.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this wiki node.
	*
	* @return the company ID of this wiki node
	*/
	public long getCompanyId() {
		return _wikiNode.getCompanyId();
	}

	/**
	* Sets the company ID of this wiki node.
	*
	* @param companyId the company ID of this wiki node
	*/
	public void setCompanyId(long companyId) {
		_wikiNode.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this wiki node.
	*
	* @return the user ID of this wiki node
	*/
	public long getUserId() {
		return _wikiNode.getUserId();
	}

	/**
	* Sets the user ID of this wiki node.
	*
	* @param userId the user ID of this wiki node
	*/
	public void setUserId(long userId) {
		_wikiNode.setUserId(userId);
	}

	/**
	* Returns the user uuid of this wiki node.
	*
	* @return the user uuid of this wiki node
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiNode.getUserUuid();
	}

	/**
	* Sets the user uuid of this wiki node.
	*
	* @param userUuid the user uuid of this wiki node
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_wikiNode.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this wiki node.
	*
	* @return the user name of this wiki node
	*/
	public java.lang.String getUserName() {
		return _wikiNode.getUserName();
	}

	/**
	* Sets the user name of this wiki node.
	*
	* @param userName the user name of this wiki node
	*/
	public void setUserName(java.lang.String userName) {
		_wikiNode.setUserName(userName);
	}

	/**
	* Returns the create date of this wiki node.
	*
	* @return the create date of this wiki node
	*/
	public java.util.Date getCreateDate() {
		return _wikiNode.getCreateDate();
	}

	/**
	* Sets the create date of this wiki node.
	*
	* @param createDate the create date of this wiki node
	*/
	public void setCreateDate(java.util.Date createDate) {
		_wikiNode.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this wiki node.
	*
	* @return the modified date of this wiki node
	*/
	public java.util.Date getModifiedDate() {
		return _wikiNode.getModifiedDate();
	}

	/**
	* Sets the modified date of this wiki node.
	*
	* @param modifiedDate the modified date of this wiki node
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_wikiNode.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this wiki node.
	*
	* @return the name of this wiki node
	*/
	public java.lang.String getName() {
		return _wikiNode.getName();
	}

	/**
	* Sets the name of this wiki node.
	*
	* @param name the name of this wiki node
	*/
	public void setName(java.lang.String name) {
		_wikiNode.setName(name);
	}

	/**
	* Returns the description of this wiki node.
	*
	* @return the description of this wiki node
	*/
	public java.lang.String getDescription() {
		return _wikiNode.getDescription();
	}

	/**
	* Sets the description of this wiki node.
	*
	* @param description the description of this wiki node
	*/
	public void setDescription(java.lang.String description) {
		_wikiNode.setDescription(description);
	}

	/**
	* Returns the last post date of this wiki node.
	*
	* @return the last post date of this wiki node
	*/
	public java.util.Date getLastPostDate() {
		return _wikiNode.getLastPostDate();
	}

	/**
	* Sets the last post date of this wiki node.
	*
	* @param lastPostDate the last post date of this wiki node
	*/
	public void setLastPostDate(java.util.Date lastPostDate) {
		_wikiNode.setLastPostDate(lastPostDate);
	}

	public boolean isNew() {
		return _wikiNode.isNew();
	}

	public void setNew(boolean n) {
		_wikiNode.setNew(n);
	}

	public boolean isCachedModel() {
		return _wikiNode.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_wikiNode.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _wikiNode.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _wikiNode.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_wikiNode.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _wikiNode.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_wikiNode.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new WikiNodeWrapper((WikiNode)_wikiNode.clone());
	}

	public int compareTo(com.liferay.portlet.wiki.model.WikiNode wikiNode) {
		return _wikiNode.compareTo(wikiNode);
	}

	@Override
	public int hashCode() {
		return _wikiNode.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.wiki.model.WikiNode> toCacheModel() {
		return _wikiNode.toCacheModel();
	}

	public com.liferay.portlet.wiki.model.WikiNode toEscapedModel() {
		return new WikiNodeWrapper(_wikiNode.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _wikiNode.toString();
	}

	public java.lang.String toXmlString() {
		return _wikiNode.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_wikiNode.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public WikiNode getWrappedWikiNode() {
		return _wikiNode;
	}

	public WikiNode getWrappedModel() {
		return _wikiNode;
	}

	public void resetOriginalValues() {
		_wikiNode.resetOriginalValues();
	}

	private WikiNode _wikiNode;
}
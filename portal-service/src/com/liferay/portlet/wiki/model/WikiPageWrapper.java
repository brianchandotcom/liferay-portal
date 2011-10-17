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
 * This class is a wrapper for {@link WikiPage}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPage
 * @generated
 */
public class WikiPageWrapper implements WikiPage, ModelWrapper<WikiPage> {
	public WikiPageWrapper(WikiPage wikiPage) {
		_wikiPage = wikiPage;
	}

	public Class<?> getModelClass() {
		return WikiPage.class;
	}

	public String getModelClassName() {
		return WikiPage.class.getName();
	}

	/**
	* Returns the primary key of this wiki page.
	*
	* @return the primary key of this wiki page
	*/
	public long getPrimaryKey() {
		return _wikiPage.getPrimaryKey();
	}

	/**
	* Sets the primary key of this wiki page.
	*
	* @param primaryKey the primary key of this wiki page
	*/
	public void setPrimaryKey(long primaryKey) {
		_wikiPage.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this wiki page.
	*
	* @return the uuid of this wiki page
	*/
	public java.lang.String getUuid() {
		return _wikiPage.getUuid();
	}

	/**
	* Sets the uuid of this wiki page.
	*
	* @param uuid the uuid of this wiki page
	*/
	public void setUuid(java.lang.String uuid) {
		_wikiPage.setUuid(uuid);
	}

	/**
	* Returns the page ID of this wiki page.
	*
	* @return the page ID of this wiki page
	*/
	public long getPageId() {
		return _wikiPage.getPageId();
	}

	/**
	* Sets the page ID of this wiki page.
	*
	* @param pageId the page ID of this wiki page
	*/
	public void setPageId(long pageId) {
		_wikiPage.setPageId(pageId);
	}

	/**
	* Returns the resource prim key of this wiki page.
	*
	* @return the resource prim key of this wiki page
	*/
	public long getResourcePrimKey() {
		return _wikiPage.getResourcePrimKey();
	}

	/**
	* Sets the resource prim key of this wiki page.
	*
	* @param resourcePrimKey the resource prim key of this wiki page
	*/
	public void setResourcePrimKey(long resourcePrimKey) {
		_wikiPage.setResourcePrimKey(resourcePrimKey);
	}

	public boolean isResourceMain() {
		return _wikiPage.isResourceMain();
	}

	/**
	* Returns the group ID of this wiki page.
	*
	* @return the group ID of this wiki page
	*/
	public long getGroupId() {
		return _wikiPage.getGroupId();
	}

	/**
	* Sets the group ID of this wiki page.
	*
	* @param groupId the group ID of this wiki page
	*/
	public void setGroupId(long groupId) {
		_wikiPage.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this wiki page.
	*
	* @return the company ID of this wiki page
	*/
	public long getCompanyId() {
		return _wikiPage.getCompanyId();
	}

	/**
	* Sets the company ID of this wiki page.
	*
	* @param companyId the company ID of this wiki page
	*/
	public void setCompanyId(long companyId) {
		_wikiPage.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this wiki page.
	*
	* @return the user ID of this wiki page
	*/
	public long getUserId() {
		return _wikiPage.getUserId();
	}

	/**
	* Sets the user ID of this wiki page.
	*
	* @param userId the user ID of this wiki page
	*/
	public void setUserId(long userId) {
		_wikiPage.setUserId(userId);
	}

	/**
	* Returns the user uuid of this wiki page.
	*
	* @return the user uuid of this wiki page
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getUserUuid();
	}

	/**
	* Sets the user uuid of this wiki page.
	*
	* @param userUuid the user uuid of this wiki page
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_wikiPage.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this wiki page.
	*
	* @return the user name of this wiki page
	*/
	public java.lang.String getUserName() {
		return _wikiPage.getUserName();
	}

	/**
	* Sets the user name of this wiki page.
	*
	* @param userName the user name of this wiki page
	*/
	public void setUserName(java.lang.String userName) {
		_wikiPage.setUserName(userName);
	}

	/**
	* Returns the create date of this wiki page.
	*
	* @return the create date of this wiki page
	*/
	public java.util.Date getCreateDate() {
		return _wikiPage.getCreateDate();
	}

	/**
	* Sets the create date of this wiki page.
	*
	* @param createDate the create date of this wiki page
	*/
	public void setCreateDate(java.util.Date createDate) {
		_wikiPage.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this wiki page.
	*
	* @return the modified date of this wiki page
	*/
	public java.util.Date getModifiedDate() {
		return _wikiPage.getModifiedDate();
	}

	/**
	* Sets the modified date of this wiki page.
	*
	* @param modifiedDate the modified date of this wiki page
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_wikiPage.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the node ID of this wiki page.
	*
	* @return the node ID of this wiki page
	*/
	public long getNodeId() {
		return _wikiPage.getNodeId();
	}

	/**
	* Sets the node ID of this wiki page.
	*
	* @param nodeId the node ID of this wiki page
	*/
	public void setNodeId(long nodeId) {
		_wikiPage.setNodeId(nodeId);
	}

	/**
	* Returns the title of this wiki page.
	*
	* @return the title of this wiki page
	*/
	public java.lang.String getTitle() {
		return _wikiPage.getTitle();
	}

	/**
	* Sets the title of this wiki page.
	*
	* @param title the title of this wiki page
	*/
	public void setTitle(java.lang.String title) {
		_wikiPage.setTitle(title);
	}

	/**
	* Returns the version of this wiki page.
	*
	* @return the version of this wiki page
	*/
	public double getVersion() {
		return _wikiPage.getVersion();
	}

	/**
	* Sets the version of this wiki page.
	*
	* @param version the version of this wiki page
	*/
	public void setVersion(double version) {
		_wikiPage.setVersion(version);
	}

	/**
	* Returns the minor edit of this wiki page.
	*
	* @return the minor edit of this wiki page
	*/
	public boolean getMinorEdit() {
		return _wikiPage.getMinorEdit();
	}

	/**
	* Returns <code>true</code> if this wiki page is minor edit.
	*
	* @return <code>true</code> if this wiki page is minor edit; <code>false</code> otherwise
	*/
	public boolean isMinorEdit() {
		return _wikiPage.isMinorEdit();
	}

	/**
	* Sets whether this wiki page is minor edit.
	*
	* @param minorEdit the minor edit of this wiki page
	*/
	public void setMinorEdit(boolean minorEdit) {
		_wikiPage.setMinorEdit(minorEdit);
	}

	/**
	* Returns the content of this wiki page.
	*
	* @return the content of this wiki page
	*/
	public java.lang.String getContent() {
		return _wikiPage.getContent();
	}

	/**
	* Sets the content of this wiki page.
	*
	* @param content the content of this wiki page
	*/
	public void setContent(java.lang.String content) {
		_wikiPage.setContent(content);
	}

	/**
	* Returns the summary of this wiki page.
	*
	* @return the summary of this wiki page
	*/
	public java.lang.String getSummary() {
		return _wikiPage.getSummary();
	}

	/**
	* Sets the summary of this wiki page.
	*
	* @param summary the summary of this wiki page
	*/
	public void setSummary(java.lang.String summary) {
		_wikiPage.setSummary(summary);
	}

	/**
	* Returns the format of this wiki page.
	*
	* @return the format of this wiki page
	*/
	public java.lang.String getFormat() {
		return _wikiPage.getFormat();
	}

	/**
	* Sets the format of this wiki page.
	*
	* @param format the format of this wiki page
	*/
	public void setFormat(java.lang.String format) {
		_wikiPage.setFormat(format);
	}

	/**
	* Returns the head of this wiki page.
	*
	* @return the head of this wiki page
	*/
	public boolean getHead() {
		return _wikiPage.getHead();
	}

	/**
	* Returns <code>true</code> if this wiki page is head.
	*
	* @return <code>true</code> if this wiki page is head; <code>false</code> otherwise
	*/
	public boolean isHead() {
		return _wikiPage.isHead();
	}

	/**
	* Sets whether this wiki page is head.
	*
	* @param head the head of this wiki page
	*/
	public void setHead(boolean head) {
		_wikiPage.setHead(head);
	}

	/**
	* Returns the parent title of this wiki page.
	*
	* @return the parent title of this wiki page
	*/
	public java.lang.String getParentTitle() {
		return _wikiPage.getParentTitle();
	}

	/**
	* Sets the parent title of this wiki page.
	*
	* @param parentTitle the parent title of this wiki page
	*/
	public void setParentTitle(java.lang.String parentTitle) {
		_wikiPage.setParentTitle(parentTitle);
	}

	/**
	* Returns the redirect title of this wiki page.
	*
	* @return the redirect title of this wiki page
	*/
	public java.lang.String getRedirectTitle() {
		return _wikiPage.getRedirectTitle();
	}

	/**
	* Sets the redirect title of this wiki page.
	*
	* @param redirectTitle the redirect title of this wiki page
	*/
	public void setRedirectTitle(java.lang.String redirectTitle) {
		_wikiPage.setRedirectTitle(redirectTitle);
	}

	/**
	* Returns the status of this wiki page.
	*
	* @return the status of this wiki page
	*/
	public int getStatus() {
		return _wikiPage.getStatus();
	}

	/**
	* Sets the status of this wiki page.
	*
	* @param status the status of this wiki page
	*/
	public void setStatus(int status) {
		_wikiPage.setStatus(status);
	}

	/**
	* Returns the status by user ID of this wiki page.
	*
	* @return the status by user ID of this wiki page
	*/
	public long getStatusByUserId() {
		return _wikiPage.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this wiki page.
	*
	* @param statusByUserId the status by user ID of this wiki page
	*/
	public void setStatusByUserId(long statusByUserId) {
		_wikiPage.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this wiki page.
	*
	* @return the status by user uuid of this wiki page
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this wiki page.
	*
	* @param statusByUserUuid the status by user uuid of this wiki page
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_wikiPage.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this wiki page.
	*
	* @return the status by user name of this wiki page
	*/
	public java.lang.String getStatusByUserName() {
		return _wikiPage.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this wiki page.
	*
	* @param statusByUserName the status by user name of this wiki page
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_wikiPage.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this wiki page.
	*
	* @return the status date of this wiki page
	*/
	public java.util.Date getStatusDate() {
		return _wikiPage.getStatusDate();
	}

	/**
	* Sets the status date of this wiki page.
	*
	* @param statusDate the status date of this wiki page
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_wikiPage.setStatusDate(statusDate);
	}

	/**
	* @deprecated Renamed to {@link #isApproved()}
	*/
	public boolean getApproved() {
		return _wikiPage.getApproved();
	}

	/**
	* Returns <code>true</code> if this wiki page is approved.
	*
	* @return <code>true</code> if this wiki page is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _wikiPage.isApproved();
	}

	/**
	* Returns <code>true</code> if this wiki page is a draft.
	*
	* @return <code>true</code> if this wiki page is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _wikiPage.isDraft();
	}

	/**
	* Returns <code>true</code> if this wiki page is expired.
	*
	* @return <code>true</code> if this wiki page is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _wikiPage.isExpired();
	}

	/**
	* Returns <code>true</code> if this wiki page is pending.
	*
	* @return <code>true</code> if this wiki page is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _wikiPage.isPending();
	}

	public boolean isNew() {
		return _wikiPage.isNew();
	}

	public void setNew(boolean n) {
		_wikiPage.setNew(n);
	}

	public boolean isCachedModel() {
		return _wikiPage.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_wikiPage.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _wikiPage.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _wikiPage.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_wikiPage.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _wikiPage.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_wikiPage.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new WikiPageWrapper((WikiPage)_wikiPage.clone());
	}

	public int compareTo(com.liferay.portlet.wiki.model.WikiPage wikiPage) {
		return _wikiPage.compareTo(wikiPage);
	}

	@Override
	public int hashCode() {
		return _wikiPage.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.wiki.model.WikiPage> toCacheModel() {
		return _wikiPage.toCacheModel();
	}

	public com.liferay.portlet.wiki.model.WikiPage toEscapedModel() {
		return new WikiPageWrapper(_wikiPage.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _wikiPage.toString();
	}

	public java.lang.String toXmlString() {
		return _wikiPage.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_wikiPage.persist();
	}

	public java.lang.String getAttachmentsDir() {
		return _wikiPage.getAttachmentsDir();
	}

	public java.lang.String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPage.getAttachmentsFiles();
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildPages() {
		return _wikiPage.getChildPages();
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode() {
		return _wikiPage.getNode();
	}

	public com.liferay.portlet.wiki.model.WikiPage getParentPage() {
		return _wikiPage.getParentPage();
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getParentPages() {
		return _wikiPage.getParentPages();
	}

	public com.liferay.portlet.wiki.model.WikiPage getRedirectPage() {
		return _wikiPage.getRedirectPage();
	}

	public void setAttachmentsDir(java.lang.String attachmentsDir) {
		_wikiPage.setAttachmentsDir(attachmentsDir);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public WikiPage getWrappedWikiPage() {
		return _wikiPage;
	}

	public WikiPage getWrappedModel() {
		return _wikiPage;
	}

	public void resetOriginalValues() {
		_wikiPage.resetOriginalValues();
	}

	private WikiPage _wikiPage;
}
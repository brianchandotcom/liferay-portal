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

package com.liferay.wiki.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link WikiPageLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageLocalService
 * @generated
 */
@ProviderType
public class WikiPageLocalServiceWrapper implements WikiPageLocalService,
	ServiceWrapper<WikiPageLocalService> {
	public WikiPageLocalServiceWrapper(
		WikiPageLocalService wikiPageLocalService) {
		_wikiPageLocalService = wikiPageLocalService;
	}

	@Override
	public boolean hasDraftPage(long nodeId, java.lang.String title) {
		return _wikiPageLocalService.hasDraftPage(nodeId, title);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _wikiPageLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _wikiPageLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _wikiPageLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _wikiPageLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry addPageAttachment(
		long userId, long nodeId, java.lang.String title,
		java.lang.String fileName, java.io.File file, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.addPageAttachment(userId, nodeId, title,
			fileName, file, mimeType);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry addPageAttachment(
		long userId, long nodeId, java.lang.String title,
		java.lang.String fileName, java.io.InputStream inputStream,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.addPageAttachment(userId, nodeId, title,
			fileName, inputStream, mimeType);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry addTempFileEntry(
		long groupId, long userId, java.lang.String folderName,
		java.lang.String fileName, java.io.InputStream inputStream,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.addTempFileEntry(groupId, userId,
			folderName, fileName, inputStream, mimeType);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry movePageAttachmentToTrash(
		long userId, long nodeId, java.lang.String title,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.movePageAttachmentToTrash(userId, nodeId,
			title, fileName);
	}

	@Override
	public com.liferay.wiki.model.WikiPage addPage(long userId, long nodeId,
		java.lang.String title, double version, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		boolean head, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.addPage(userId, nodeId, title, version,
			content, summary, minorEdit, format, head, parentTitle,
			redirectTitle, serviceContext);
	}

	@Override
	public com.liferay.wiki.model.WikiPage addPage(long userId, long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.addPage(userId, nodeId, title, content,
			summary, minorEdit, serviceContext);
	}

	/**
	* Adds the wiki page to the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was added
	*/
	@Override
	public com.liferay.wiki.model.WikiPage addWikiPage(
		com.liferay.wiki.model.WikiPage wikiPage) {
		return _wikiPageLocalService.addWikiPage(wikiPage);
	}

	@Override
	public com.liferay.wiki.model.WikiPage changeParent(long userId,
		long nodeId, java.lang.String title, java.lang.String newParentTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.changeParent(userId, nodeId, title,
			newParentTitle, serviceContext);
	}

	/**
	* Creates a new wiki page with the primary key. Does not add the wiki page to the database.
	*
	* @param pageId the primary key for the new wiki page
	* @return the new wiki page
	*/
	@Override
	public com.liferay.wiki.model.WikiPage createWikiPage(long pageId) {
		return _wikiPageLocalService.createWikiPage(pageId);
	}

	/**
	* Deletes the wiki page from the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was removed
	*/
	@Override
	public com.liferay.wiki.model.WikiPage deleteWikiPage(
		com.liferay.wiki.model.WikiPage wikiPage) {
		return _wikiPageLocalService.deleteWikiPage(wikiPage);
	}

	/**
	* Deletes the wiki page with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page that was removed
	* @throws PortalException if a wiki page with the primary key could not be found
	*/
	@Override
	public com.liferay.wiki.model.WikiPage deleteWikiPage(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.deleteWikiPage(pageId);
	}

	@Override
	public com.liferay.wiki.model.WikiPage fetchLatestPage(long nodeId,
		java.lang.String title, int status, boolean preferApproved) {
		return _wikiPageLocalService.fetchLatestPage(nodeId, title, status,
			preferApproved);
	}

	@Override
	public com.liferay.wiki.model.WikiPage fetchLatestPage(
		long resourcePrimKey, int status, boolean preferApproved) {
		return _wikiPageLocalService.fetchLatestPage(resourcePrimKey, status,
			preferApproved);
	}

	@Override
	public com.liferay.wiki.model.WikiPage fetchLatestPage(
		long resourcePrimKey, long nodeId, int status, boolean preferApproved) {
		return _wikiPageLocalService.fetchLatestPage(resourcePrimKey, nodeId,
			status, preferApproved);
	}

	@Override
	public com.liferay.wiki.model.WikiPage fetchPage(long nodeId,
		java.lang.String title) {
		return _wikiPageLocalService.fetchPage(nodeId, title);
	}

	@Override
	public com.liferay.wiki.model.WikiPage fetchPage(long nodeId,
		java.lang.String title, double version) {
		return _wikiPageLocalService.fetchPage(nodeId, title, version);
	}

	@Override
	public com.liferay.wiki.model.WikiPage fetchPage(long resourcePrimKey) {
		return _wikiPageLocalService.fetchPage(resourcePrimKey);
	}

	@Override
	public com.liferay.wiki.model.WikiPage fetchWikiPage(long pageId) {
		return _wikiPageLocalService.fetchWikiPage(pageId);
	}

	/**
	* Returns the wiki page matching the UUID and group.
	*
	* @param uuid the wiki page's UUID
	* @param groupId the primary key of the group
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	@Override
	public com.liferay.wiki.model.WikiPage fetchWikiPageByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _wikiPageLocalService.fetchWikiPageByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getDraftPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getDraftPage(nodeId, title);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getLatestPage(long nodeId,
		java.lang.String title, int status, boolean preferApproved)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getLatestPage(nodeId, title, status,
			preferApproved);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getLatestPage(long resourcePrimKey,
		int status, boolean preferApproved)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getLatestPage(resourcePrimKey, status,
			preferApproved);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getLatestPage(long resourcePrimKey,
		long nodeId, int status, boolean preferApproved)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getLatestPage(resourcePrimKey, nodeId,
			status, preferApproved);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPage(nodeId, title);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPage(nodeId, title, version);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPage(nodeId, title, head);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getPage(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPage(resourcePrimKey);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getPage(long resourcePrimKey,
		java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPage(resourcePrimKey, head);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getPageByPageId(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPageByPageId(pageId);
	}

	@Override
	public com.liferay.wiki.model.WikiPage getPreviousVersionPage(
		com.liferay.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPreviousVersionPage(page);
	}

	/**
	* Returns the wiki page with the primary key.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page
	* @throws PortalException if a wiki page with the primary key could not be found
	*/
	@Override
	public com.liferay.wiki.model.WikiPage getWikiPage(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getWikiPage(pageId);
	}

	/**
	* Returns the wiki page matching the UUID and group.
	*
	* @param uuid the wiki page's UUID
	* @param groupId the primary key of the group
	* @return the matching wiki page
	* @throws PortalException if a matching wiki page could not be found
	*/
	@Override
	public com.liferay.wiki.model.WikiPage getWikiPageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getWikiPageByUuidAndGroupId(uuid, groupId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #movePageFromTrash(long,
	long, String, long, String)} *
	*/
	@Deprecated
	@Override
	public com.liferay.wiki.model.WikiPage movePageFromTrash(long userId,
		long nodeId, java.lang.String title, java.lang.String newParentTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.movePageFromTrash(userId, nodeId, title,
			newParentTitle, serviceContext);
	}

	@Override
	public com.liferay.wiki.model.WikiPage movePageFromTrash(long userId,
		long nodeId, java.lang.String title, long newNodeId,
		java.lang.String newParentTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.movePageFromTrash(userId, nodeId, title,
			newNodeId, newParentTitle);
	}

	@Override
	public com.liferay.wiki.model.WikiPage movePageToTrash(long userId,
		com.liferay.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.movePageToTrash(userId, page);
	}

	@Override
	public com.liferay.wiki.model.WikiPage movePageToTrash(long userId,
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.movePageToTrash(userId, nodeId, title);
	}

	@Override
	public com.liferay.wiki.model.WikiPage movePageToTrash(long userId,
		long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.movePageToTrash(userId, nodeId, title,
			version);
	}

	@Override
	public com.liferay.wiki.model.WikiPage revertPage(long userId, long nodeId,
		java.lang.String title, double version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.revertPage(userId, nodeId, title, version,
			serviceContext);
	}

	@Override
	public com.liferay.wiki.model.WikiPage updatePage(long userId, long nodeId,
		java.lang.String title, double version, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.updatePage(userId, nodeId, title, version,
			content, summary, minorEdit, format, parentTitle, redirectTitle,
			serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, WikiPage,
	int, ServiceContext, Map)}
	*/
	@Deprecated
	@Override
	public com.liferay.wiki.model.WikiPage updateStatus(long userId,
		com.liferay.wiki.model.WikiPage page, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.updateStatus(userId, page, status,
			serviceContext);
	}

	@Override
	public com.liferay.wiki.model.WikiPage updateStatus(long userId,
		com.liferay.wiki.model.WikiPage page, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.updateStatus(userId, page, status,
			serviceContext, workflowContext);
	}

	@Override
	public com.liferay.wiki.model.WikiPage updateStatus(long userId,
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.updateStatus(userId, resourcePrimKey,
			status, serviceContext);
	}

	/**
	* Updates the wiki page in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was updated
	*/
	@Override
	public com.liferay.wiki.model.WikiPage updateWikiPage(
		com.liferay.wiki.model.WikiPage wikiPage) {
		return _wikiPageLocalService.updateWikiPage(wikiPage);
	}

	@Override
	public com.liferay.wiki.model.WikiPageDisplay getPageDisplay(
		com.liferay.wiki.model.WikiPage page,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPageDisplay(page, viewPageURL,
			editPageURL, attachmentURLPrefix);
	}

	@Override
	public com.liferay.wiki.model.WikiPageDisplay getPageDisplay(
		com.liferay.wiki.model.WikiPage page,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPageDisplay(page, viewPageURL,
			editPageURL, attachmentURLPrefix, serviceContext);
	}

	@Override
	public com.liferay.wiki.model.WikiPageDisplay getPageDisplay(long nodeId,
		java.lang.String title, javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getPageDisplay(nodeId, title, viewPageURL,
			editPageURL, attachmentURLPrefix);
	}

	@Override
	public int getChildrenCount(long nodeId, boolean head,
		java.lang.String parentTitle) {
		return _wikiPageLocalService.getChildrenCount(nodeId, head, parentTitle);
	}

	@Override
	public int getChildrenCount(long nodeId, boolean head,
		java.lang.String parentTitle, int status) {
		return _wikiPageLocalService.getChildrenCount(nodeId, head,
			parentTitle, status);
	}

	@Override
	public int getPagesCount(java.lang.String format) {
		return _wikiPageLocalService.getPagesCount(format);
	}

	@Override
	public int getPagesCount(long nodeId) {
		return _wikiPageLocalService.getPagesCount(nodeId);
	}

	@Override
	public int getPagesCount(long nodeId, boolean head) {
		return _wikiPageLocalService.getPagesCount(nodeId, head);
	}

	@Override
	public int getPagesCount(long nodeId, boolean head, int status) {
		return _wikiPageLocalService.getPagesCount(nodeId, head, status);
	}

	@Override
	public int getPagesCount(long nodeId, int status) {
		return _wikiPageLocalService.getPagesCount(nodeId, status);
	}

	@Override
	public int getPagesCount(long nodeId, java.lang.String title) {
		return _wikiPageLocalService.getPagesCount(nodeId, title);
	}

	@Override
	public int getPagesCount(long nodeId, java.lang.String title, boolean head) {
		return _wikiPageLocalService.getPagesCount(nodeId, title, head);
	}

	@Override
	public int getPagesCount(long userId, long nodeId, int status) {
		return _wikiPageLocalService.getPagesCount(userId, nodeId, status);
	}

	@Override
	public int getRecentChangesCount(long groupId, long nodeId) {
		return _wikiPageLocalService.getRecentChangesCount(groupId, nodeId);
	}

	/**
	* Returns the number of wiki pages.
	*
	* @return the number of wiki pages
	*/
	@Override
	public int getWikiPagesCount() {
		return _wikiPageLocalService.getWikiPagesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _wikiPageLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.lang.String[] getTempFileNames(long groupId, long userId,
		java.lang.String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getTempFileNames(groupId, userId,
			folderName);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> addPageAttachments(
		long userId, long nodeId, java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.addPageAttachments(userId, nodeId, title,
			inputStreamOVPs);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _wikiPageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _wikiPageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _wikiPageLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle) {
		return _wikiPageLocalService.getChildren(nodeId, head, parentTitle);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle, int start,
		int end) {
		return _wikiPageLocalService.getChildren(nodeId, head, parentTitle,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle, int status) {
		return _wikiPageLocalService.getChildren(nodeId, head, parentTitle,
			status);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle, int status,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc) {
		return _wikiPageLocalService.getChildren(nodeId, head, parentTitle,
			status, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getDependentPages(
		long nodeId, boolean head, java.lang.String title, int status) {
		return _wikiPageLocalService.getDependentPages(nodeId, head, title,
			status);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getIncomingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getIncomingLinks(nodeId, title);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getNoAssetPages() {
		return _wikiPageLocalService.getNoAssetPages();
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getOrphans(
		long nodeId) throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getOrphans(nodeId);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getOutgoingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _wikiPageLocalService.getOutgoingLinks(nodeId, title);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		java.lang.String format) {
		return _wikiPageLocalService.getPages(format);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end) {
		return _wikiPageLocalService.getPages(nodeId, head, start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiPage> obc) {
		return _wikiPageLocalService.getPages(nodeId, head, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int status, int start, int end) {
		return _wikiPageLocalService.getPages(nodeId, head, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiPage> obc) {
		return _wikiPageLocalService.getPages(nodeId, head, status, start, end,
			obc);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end) {
		return _wikiPageLocalService.getPages(nodeId, start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiPage> obc) {
		return _wikiPageLocalService.getPages(nodeId, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, boolean head, int start, int end) {
		return _wikiPageLocalService.getPages(nodeId, title, head, start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end) {
		return _wikiPageLocalService.getPages(nodeId, title, start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiPage> obc) {
		return _wikiPageLocalService.getPages(nodeId, title, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long resourcePrimKey, long nodeId, int status) {
		return _wikiPageLocalService.getPages(resourcePrimKey, nodeId, status);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long userId, long nodeId, int status, int start, int end) {
		return _wikiPageLocalService.getPages(userId, nodeId, status, start, end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getRecentChanges(
		long groupId, long nodeId, int start, int end) {
		return _wikiPageLocalService.getRecentChanges(groupId, nodeId, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getRedirectorPages(
		long nodeId, boolean head, java.lang.String redirectTitle, int status) {
		return _wikiPageLocalService.getRedirectorPages(nodeId, head,
			redirectTitle, status);
	}

	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getRedirectorPages(
		long nodeId, java.lang.String redirectTitle) {
		return _wikiPageLocalService.getRedirectorPages(nodeId, redirectTitle);
	}

	/**
	* Returns a range of all the wiki pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wiki.model.impl.WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of wiki pages
	*/
	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getWikiPages(
		int start, int end) {
		return _wikiPageLocalService.getWikiPages(start, end);
	}

	/**
	* Returns all the wiki pages matching the UUID and company.
	*
	* @param uuid the UUID of the wiki pages
	* @param companyId the primary key of the company
	* @return the matching wiki pages, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getWikiPagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _wikiPageLocalService.getWikiPagesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of wiki pages matching the UUID and company.
	*
	* @param uuid the UUID of the wiki pages
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching wiki pages, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.wiki.model.WikiPage> getWikiPagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiPage> orderByComparator) {
		return _wikiPageLocalService.getWikiPagesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _wikiPageLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _wikiPageLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public void addPageResources(com.liferay.wiki.model.WikiPage page,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.addPageResources(page, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addPageResources(com.liferay.wiki.model.WikiPage page,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.addPageResources(page, groupPermissions,
			guestPermissions);
	}

	@Override
	public void addPageResources(long nodeId, java.lang.String title,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.addPageResources(nodeId, title,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addPageResources(long nodeId, java.lang.String title,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.addPageResources(nodeId, title, groupPermissions,
			guestPermissions);
	}

	/**
	* @deprecated As of 7.0.0 replaced by {@link #addTempFileEntry(long, long,
	String, String, InputStream, String)}
	*/
	@Deprecated
	@Override
	public void addTempPageAttachment(long groupId, long userId,
		java.lang.String fileName, java.lang.String tempFolderName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.addTempPageAttachment(groupId, userId, fileName,
			tempFolderName, inputStream, mimeType);
	}

	@Override
	public void copyPageAttachments(long userId, long templateNodeId,
		java.lang.String templateTitle, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.copyPageAttachments(userId, templateNodeId,
			templateTitle, nodeId, title);
	}

	@Override
	public void deletePage(com.liferay.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.deletePage(page);
	}

	@Override
	public void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.deletePage(nodeId, title);
	}

	@Override
	public void deletePageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.deletePageAttachment(nodeId, title, fileName);
	}

	@Override
	public void deletePageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.deletePageAttachments(nodeId, title);
	}

	@Override
	public void deletePages(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.deletePages(nodeId);
	}

	@Override
	public void deleteTempFileEntry(long groupId, long userId,
		java.lang.String folderName, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.deleteTempFileEntry(groupId, userId, folderName,
			fileName);
	}

	@Override
	public void deleteTrashPageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.deleteTrashPageAttachments(nodeId, title);
	}

	@Override
	public void discardDraft(long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.discardDraft(nodeId, title, version);
	}

	@Override
	public void moveDependentToTrash(com.liferay.wiki.model.WikiPage page,
		long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.moveDependentToTrash(page, trashEntryId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #renamePage(long, long,
	String, String, ServiceContext)}
	*/
	@Deprecated
	@Override
	public void movePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.movePage(userId, nodeId, title, newTitle,
			serviceContext);
	}

	@Override
	public void renamePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle, boolean strict,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.renamePage(userId, nodeId, title, newTitle,
			strict, serviceContext);
	}

	@Override
	public void renamePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.renamePage(userId, nodeId, title, newTitle,
			serviceContext);
	}

	@Override
	public void restorePageAttachmentFromTrash(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.restorePageAttachmentFromTrash(userId, nodeId,
			title, fileName);
	}

	@Override
	public void restorePageFromTrash(long userId,
		com.liferay.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.restorePageFromTrash(userId, page);
	}

	@Override
	public void subscribePage(long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.subscribePage(userId, nodeId, title);
	}

	@Override
	public void unsubscribePage(long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.unsubscribePage(userId, nodeId, title);
	}

	@Override
	public void updateAsset(long userId, com.liferay.wiki.model.WikiPage page,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.updateAsset(userId, page, assetCategoryIds,
			assetTagNames, assetLinkEntryIds, priority);
	}

	/**
	* @deprecated As of 7.0.0 replaced by {@link
	WikiPageTitleValidator#validate(String)}
	*/
	@Deprecated
	@Override
	public void validateTitle(java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.validateTitle(title);
	}

	@Override
	public WikiPageLocalService getWrappedService() {
		return _wikiPageLocalService;
	}

	@Override
	public void setWrappedService(WikiPageLocalService wikiPageLocalService) {
		_wikiPageLocalService = wikiPageLocalService;
	}

	private WikiPageLocalService _wikiPageLocalService;
}
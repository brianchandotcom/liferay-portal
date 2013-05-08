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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link WikiPageLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageLocalService
 * @generated
 */
public class WikiPageLocalServiceWrapper implements WikiPageLocalService,
	ServiceWrapper<WikiPageLocalService> {
	public WikiPageLocalServiceWrapper(
		WikiPageLocalService wikiPageLocalService) {
		_wikiPageLocalService = wikiPageLocalService;
	}

	/**
	* Adds the wiki page to the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPage addWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.addWikiPage(wikiPage);
	}

	/**
	* Creates a new wiki page with the primary key. Does not add the wiki page to the database.
	*
	* @param pageId the primary key for the new wiki page
	* @return the new wiki page
	*/
	public com.liferay.portlet.wiki.model.WikiPage createWikiPage(long pageId) {
		return _wikiPageLocalService.createWikiPage(pageId);
	}

	/**
	* Deletes the wiki page with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page that was removed
	* @throws PortalException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPage deleteWikiPage(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.deleteWikiPage(pageId);
	}

	/**
	* Deletes the wiki page from the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPage deleteWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.deleteWikiPage(wikiPage);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _wikiPageLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.wiki.model.impl.WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.wiki.model.impl.WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.wiki.model.WikiPage fetchWikiPage(long pageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.fetchWikiPage(pageId);
	}

	/**
	* Returns the wiki page with the primary key.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page
	* @throws PortalException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPage getWikiPage(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getWikiPage(pageId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the wiki page matching the UUID and group.
	*
	* @param uuid the wiki page's UUID
	* @param groupId the primary key of the group
	* @return the matching wiki page
	* @throws PortalException if a matching wiki page could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPage getWikiPageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getWikiPageByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the wiki pages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.wiki.model.impl.WikiPageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wiki pages
	* @param end the upper bound of the range of wiki pages (not inclusive)
	* @return the range of wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getWikiPages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getWikiPages(start, end);
	}

	/**
	* Returns the number of wiki pages.
	*
	* @return the number of wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public int getWikiPagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getWikiPagesCount();
	}

	/**
	* Updates the wiki page in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.wiki.model.WikiPage updateWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.updateWikiPage(wikiPage);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _wikiPageLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_wikiPageLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.wiki.model.WikiPage addPage(long userId,
		long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, boolean head, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.addPage(userId, nodeId, title, version,
			content, summary, minorEdit, format, head, parentTitle,
			redirectTitle, serviceContext);
	}

	public com.liferay.portlet.wiki.model.WikiPage addPage(long userId,
		long nodeId, java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.addPage(userId, nodeId, title, content,
			summary, minorEdit, serviceContext);
	}

	public void addPageAttachment(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName, java.io.File file,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageAttachment(userId, nodeId, title,
			fileName, file, mimeType);
	}

	public void addPageAttachment(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageAttachment(userId, nodeId, title,
			fileName, inputStream, mimeType);
	}

	public void addPageAttachments(long userId, long nodeId,
		java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageAttachments(userId, nodeId, title,
			inputStreamOVPs);
	}

	public void addPageResources(long nodeId, java.lang.String title,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageResources(nodeId, title,
			addGroupPermissions, addGuestPermissions);
	}

	public void addPageResources(long nodeId, java.lang.String title,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageResources(nodeId, title, groupPermissions,
			guestPermissions);
	}

	public void addPageResources(com.liferay.portlet.wiki.model.WikiPage page,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageResources(page, addGroupPermissions,
			addGuestPermissions);
	}

	public void addPageResources(com.liferay.portlet.wiki.model.WikiPage page,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addPageResources(page, groupPermissions,
			guestPermissions);
	}

	public void addTempPageAttachment(long groupId, long userId,
		java.lang.String fileName, java.lang.String tempFolderName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.addTempPageAttachment(groupId, userId, fileName,
			tempFolderName, inputStream, mimeType);
	}

	public void changeParent(long userId, long nodeId, java.lang.String title,
		java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.changeParent(userId, nodeId, title,
			newParentTitle, serviceContext);
	}

	public void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deletePage(nodeId, title);
	}

	/**
	* @deprecated As of 6.2.0 replaced by {@link #discardDraft(long, String,
	double)}
	*/
	public void deletePage(long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deletePage(nodeId, title, version);
	}

	public void deletePage(com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deletePage(page);
	}

	public void deletePageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deletePageAttachment(nodeId, title, fileName);
	}

	public void deletePageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deletePageAttachments(nodeId, title);
	}

	public void deletePages(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deletePages(nodeId);
	}

	public void deleteTempPageAttachment(long groupId, long userId,
		java.lang.String fileName, java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deleteTempPageAttachment(groupId, userId,
			fileName, tempFolderName);
	}

	public void deleteTrashPageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.deleteTrashPageAttachments(nodeId, title);
	}

	public void discardDraft(long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.discardDraft(nodeId, title, version);
	}

	public com.liferay.portlet.wiki.model.WikiPage fetchPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.fetchPage(nodeId, title, version);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getChildren(nodeId, head, parentTitle);
	}

	public com.liferay.portlet.wiki.model.WikiPage getDraftPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getDraftPage(nodeId, title);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getIncomingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getIncomingLinks(nodeId, title);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getNoAssetPages()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getNoAssetPages();
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOrphans(
		long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getOrphans(nodeId);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOutgoingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getOutgoingLinks(nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPage(resourcePrimKey);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(
		long resourcePrimKey, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPage(resourcePrimKey, head);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPage(nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPage(nodeId, title, head);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPage(nodeId, title, version);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPageByPageId(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPageByPageId(pageId);
	}

	public com.liferay.portlet.wiki.model.WikiPageDisplay getPageDisplay(
		long nodeId, java.lang.String title,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPageDisplay(nodeId, title, viewPageURL,
			editPageURL, attachmentURLPrefix);
	}

	public com.liferay.portlet.wiki.model.WikiPageDisplay getPageDisplay(
		com.liferay.portlet.wiki.model.WikiPage page,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPageDisplay(page, viewPageURL,
			editPageURL, attachmentURLPrefix);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, head, start, end);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, head, status, start, end,
			obc);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, head, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, start, end);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long resourcePrimKey, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(resourcePrimKey, nodeId, status);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long userId, long nodeId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(userId, nodeId, status, start, end);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, title, head, start, end);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, title, start, end);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(nodeId, title, start, end, obc);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPages(format);
	}

	public int getPagesCount(long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(nodeId);
	}

	public int getPagesCount(long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(nodeId, head);
	}

	public int getPagesCount(long nodeId, boolean head, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(nodeId, head, status);
	}

	public int getPagesCount(long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(nodeId, status);
	}

	public int getPagesCount(long userId, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(userId, nodeId, status);
	}

	public int getPagesCount(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(nodeId, title);
	}

	public int getPagesCount(long nodeId, java.lang.String title, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(nodeId, title, head);
	}

	public int getPagesCount(java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getPagesCount(format);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getRecentChanges(long, long,
	int, int)}
	*/
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getRecentChanges(nodeId, start, end);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long groupId, long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getRecentChanges(groupId, nodeId, start,
			end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getRecentChangesCount(long,
	long)}
	*/
	public int getRecentChangesCount(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getRecentChangesCount(nodeId);
	}

	public int getRecentChangesCount(long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getRecentChangesCount(groupId, nodeId);
	}

	public java.lang.String[] getTempPageAttachmentNames(long groupId,
		long userId, java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.getTempPageAttachmentNames(groupId,
			userId, tempFolderName);
	}

	public boolean hasDraftPage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.hasDraftPage(nodeId, title);
	}

	public void movePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle, boolean strict,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.movePage(userId, nodeId, title, newTitle, strict,
			serviceContext);
	}

	public void movePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.movePage(userId, nodeId, title, newTitle,
			serviceContext);
	}

	public long movePageAttachmentToTrash(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.movePageAttachmentToTrash(userId, nodeId,
			title, fileName);
	}

	public com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.movePageToTrash(userId, nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long userId, long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.movePageToTrash(userId, nodeId, title,
			version);
	}

	public com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long userId, com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.movePageToTrash(userId, page);
	}

	public void restorePageAttachmentFromTrash(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.restorePageAttachmentFromTrash(userId, nodeId,
			title, fileName);
	}

	public void restorePageFromTrash(long userId,
		com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.restorePageFromTrash(userId, page);
	}

	public com.liferay.portlet.wiki.model.WikiPage revertPage(long userId,
		long nodeId, java.lang.String title, double version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.revertPage(userId, nodeId, title, version,
			serviceContext);
	}

	public void subscribePage(long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.subscribePage(userId, nodeId, title);
	}

	public void unsubscribePage(long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.unsubscribePage(userId, nodeId, title);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.wiki.model.WikiPage page, long[] assetCategoryIds,
		java.lang.String[] assetTagNames, long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageLocalService.updateAsset(userId, page, assetCategoryIds,
			assetTagNames, assetLinkEntryIds);
	}

	public com.liferay.portlet.wiki.model.WikiPage updatePage(long userId,
		long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.updatePage(userId, nodeId, title, version,
			content, summary, minorEdit, format, parentTitle, redirectTitle,
			serviceContext);
	}

	public com.liferay.portlet.wiki.model.WikiPage updateStatus(long userId,
		long resourcePrimKey, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.updateStatus(userId, resourcePrimKey,
			status, serviceContext);
	}

	public com.liferay.portlet.wiki.model.WikiPage updateStatus(long userId,
		com.liferay.portlet.wiki.model.WikiPage page, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageLocalService.updateStatus(userId, page, status,
			serviceContext);
	}

	public void validateTitle(java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_wikiPageLocalService.validateTitle(title);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public WikiPageLocalService getWrappedWikiPageLocalService() {
		return _wikiPageLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {
		_wikiPageLocalService = wikiPageLocalService;
	}

	public WikiPageLocalService getWrappedService() {
		return _wikiPageLocalService;
	}

	public void setWrappedService(WikiPageLocalService wikiPageLocalService) {
		_wikiPageLocalService = wikiPageLocalService;
	}

	private WikiPageLocalService _wikiPageLocalService;
}
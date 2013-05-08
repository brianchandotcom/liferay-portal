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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for WikiPage. This utility wraps
 * {@link com.liferay.portlet.wiki.service.impl.WikiPageLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageLocalService
 * @see com.liferay.portlet.wiki.service.base.WikiPageLocalServiceBaseImpl
 * @see com.liferay.portlet.wiki.service.impl.WikiPageLocalServiceImpl
 * @generated
 */
public class WikiPageLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.wiki.service.impl.WikiPageLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the wiki page to the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage addWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addWikiPage(wikiPage);
	}

	/**
	* Creates a new wiki page with the primary key. Does not add the wiki page to the database.
	*
	* @param pageId the primary key for the new wiki page
	* @return the new wiki page
	*/
	public static com.liferay.portlet.wiki.model.WikiPage createWikiPage(
		long pageId) {
		return getService().createWikiPage(pageId);
	}

	/**
	* Deletes the wiki page with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page that was removed
	* @throws PortalException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage deleteWikiPage(
		long pageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteWikiPage(pageId);
	}

	/**
	* Deletes the wiki page from the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage deleteWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteWikiPage(wikiPage);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchWikiPage(
		long pageId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchWikiPage(pageId);
	}

	/**
	* Returns the wiki page with the primary key.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page
	* @throws PortalException if a wiki page with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage getWikiPage(
		long pageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getWikiPage(pageId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static com.liferay.portlet.wiki.model.WikiPage getWikiPageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getWikiPageByUuidAndGroupId(uuid, groupId);
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
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getWikiPages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWikiPages(start, end);
	}

	/**
	* Returns the number of wiki pages.
	*
	* @return the number of wiki pages
	* @throws SystemException if a system exception occurred
	*/
	public static int getWikiPagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWikiPagesCount();
	}

	/**
	* Updates the wiki page in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.wiki.model.WikiPage updateWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateWikiPage(wikiPage);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.wiki.model.WikiPage addPage(long userId,
		long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, boolean head, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPage(userId, nodeId, title, version, content, summary,
			minorEdit, format, head, parentTitle, redirectTitle, serviceContext);
	}

	public static com.liferay.portlet.wiki.model.WikiPage addPage(long userId,
		long nodeId, java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPage(userId, nodeId, title, content, summary, minorEdit,
			serviceContext);
	}

	public static void addPageAttachment(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName, java.io.File file,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addPageAttachment(userId, nodeId, title, fileName, file, mimeType);
	}

	public static void addPageAttachment(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addPageAttachment(userId, nodeId, title, fileName, inputStream,
			mimeType);
	}

	public static void addPageAttachments(long userId, long nodeId,
		java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addPageAttachments(userId, nodeId, title, inputStreamOVPs);
	}

	public static void addPageResources(long nodeId, java.lang.String title,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addPageResources(nodeId, title, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addPageResources(long nodeId, java.lang.String title,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addPageResources(nodeId, title, groupPermissions, guestPermissions);
	}

	public static void addPageResources(
		com.liferay.portlet.wiki.model.WikiPage page,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addPageResources(page, addGroupPermissions, addGuestPermissions);
	}

	public static void addPageResources(
		com.liferay.portlet.wiki.model.WikiPage page,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addPageResources(page, groupPermissions, guestPermissions);
	}

	public static void addTempPageAttachment(long groupId, long userId,
		java.lang.String fileName, java.lang.String tempFolderName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addTempPageAttachment(groupId, userId, fileName, tempFolderName,
			inputStream, mimeType);
	}

	public static void changeParent(long userId, long nodeId,
		java.lang.String title, java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.changeParent(userId, nodeId, title, newParentTitle, serviceContext);
	}

	public static void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePage(nodeId, title);
	}

	/**
	* @deprecated As of 6.2.0 replaced by {@link #discardDraft(long, String,
	double)}
	*/
	public static void deletePage(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePage(nodeId, title, version);
	}

	public static void deletePage(com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePage(page);
	}

	public static void deletePageAttachment(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePageAttachment(nodeId, title, fileName);
	}

	public static void deletePageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePageAttachments(nodeId, title);
	}

	public static void deletePages(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePages(nodeId);
	}

	public static void deleteTempPageAttachment(long groupId, long userId,
		java.lang.String fileName, java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteTempPageAttachment(groupId, userId, fileName, tempFolderName);
	}

	public static void deleteTrashPageAttachments(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTrashPageAttachments(nodeId, title);
	}

	public static void discardDraft(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().discardDraft(nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchPage(
		long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPage(nodeId, title, version);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getChildren(nodeId, head, parentTitle);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getDraftPage(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDraftPage(nodeId, title);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getIncomingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getIncomingLinks(nodeId, title);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getNoAssetPages()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNoAssetPages();
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOrphans(
		long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrphans(nodeId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOutgoingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOutgoingLinks(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPage(resourcePrimKey);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(
		long resourcePrimKey, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPage(resourcePrimKey, head);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPage(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPage(nodeId, title, head);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPage(nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPageByPageId(
		long pageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPageByPageId(pageId);
	}

	public static com.liferay.portlet.wiki.model.WikiPageDisplay getPageDisplay(
		long nodeId, java.lang.String title,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getPageDisplay(nodeId, title, viewPageURL, editPageURL,
			attachmentURLPrefix);
	}

	public static com.liferay.portlet.wiki.model.WikiPageDisplay getPageDisplay(
		com.liferay.portlet.wiki.model.WikiPage page,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getPageDisplay(page, viewPageURL, editPageURL,
			attachmentURLPrefix);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(nodeId, head, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(nodeId, head, status, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(nodeId, head, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(nodeId, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(nodeId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long resourcePrimKey, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(resourcePrimKey, nodeId, status);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long userId, long nodeId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(userId, nodeId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, boolean head, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(nodeId, title, head, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(nodeId, title, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(nodeId, title, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPages(format);
	}

	public static int getPagesCount(long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPagesCount(nodeId);
	}

	public static int getPagesCount(long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPagesCount(nodeId, head);
	}

	public static int getPagesCount(long nodeId, boolean head, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPagesCount(nodeId, head, status);
	}

	public static int getPagesCount(long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPagesCount(nodeId, status);
	}

	public static int getPagesCount(long userId, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPagesCount(userId, nodeId, status);
	}

	public static int getPagesCount(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPagesCount(nodeId, title);
	}

	public static int getPagesCount(long nodeId, java.lang.String title,
		boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPagesCount(nodeId, title, head);
	}

	public static int getPagesCount(java.lang.String format)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPagesCount(format);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getRecentChanges(long, long,
	int, int)}
	*/
	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecentChanges(nodeId, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long groupId, long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecentChanges(groupId, nodeId, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getRecentChangesCount(long,
	long)}
	*/
	public static int getRecentChangesCount(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecentChangesCount(nodeId);
	}

	public static int getRecentChangesCount(long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecentChangesCount(groupId, nodeId);
	}

	public static java.lang.String[] getTempPageAttachmentNames(long groupId,
		long userId, java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getTempPageAttachmentNames(groupId, userId, tempFolderName);
	}

	public static boolean hasDraftPage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasDraftPage(nodeId, title);
	}

	public static void movePage(long userId, long nodeId,
		java.lang.String title, java.lang.String newTitle, boolean strict,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.movePage(userId, nodeId, title, newTitle, strict, serviceContext);
	}

	public static void movePage(long userId, long nodeId,
		java.lang.String title, java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().movePage(userId, nodeId, title, newTitle, serviceContext);
	}

	public static long movePageAttachmentToTrash(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .movePageAttachmentToTrash(userId, nodeId, title, fileName);
	}

	public static com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().movePageToTrash(userId, nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long userId, long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().movePageToTrash(userId, nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long userId, com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().movePageToTrash(userId, page);
	}

	public static void restorePageAttachmentFromTrash(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.restorePageAttachmentFromTrash(userId, nodeId, title, fileName);
	}

	public static void restorePageFromTrash(long userId,
		com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().restorePageFromTrash(userId, page);
	}

	public static com.liferay.portlet.wiki.model.WikiPage revertPage(
		long userId, long nodeId, java.lang.String title, double version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .revertPage(userId, nodeId, title, version, serviceContext);
	}

	public static void subscribePage(long userId, long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().subscribePage(userId, nodeId, title);
	}

	public static void unsubscribePage(long userId, long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsubscribePage(userId, nodeId, title);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.wiki.model.WikiPage page, long[] assetCategoryIds,
		java.lang.String[] assetTagNames, long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateAsset(userId, page, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updatePage(
		long userId, long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePage(userId, nodeId, title, version, content,
			summary, minorEdit, format, parentTitle, redirectTitle,
			serviceContext);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updateStatus(
		long userId, long resourcePrimKey, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, resourcePrimKey, status, serviceContext);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updateStatus(
		long userId, com.liferay.portlet.wiki.model.WikiPage page, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateStatus(userId, page, status, serviceContext);
	}

	public static void validateTitle(java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().validateTitle(title);
	}

	public static WikiPageLocalService getService() {
		if (_service == null) {
			_service = (WikiPageLocalService)PortalBeanLocatorUtil.locate(WikiPageLocalService.class.getName());

			ReferenceRegistry.registerReference(WikiPageLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(WikiPageLocalService service) {
	}

	private static WikiPageLocalService _service;
}
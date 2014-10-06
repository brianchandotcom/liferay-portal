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

package com.liferay.portlet.wiki.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for WikiPage. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageLocalServiceUtil
 * @see com.liferay.portlet.wiki.service.base.WikiPageLocalServiceBaseImpl
 * @see com.liferay.portlet.wiki.service.impl.WikiPageLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface WikiPageLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiPageLocalServiceUtil} to access the wiki page local service. Add custom service methods to {@link com.liferay.portlet.wiki.service.impl.WikiPageLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portlet.wiki.model.WikiPage addPage(long userId,
		long nodeId, java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage addPage(long userId,
		long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, boolean head, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addPageAttachment(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName, java.io.File file,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addPageAttachment(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addPageAttachments(long userId, long nodeId,
		java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addPageResources(long nodeId, java.lang.String title,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addPageResources(long nodeId, java.lang.String title,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addPageResources(com.liferay.portlet.wiki.model.WikiPage page,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addPageResources(com.liferay.portlet.wiki.model.WikiPage page,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void addTempFileEntry(long groupId, long userId,
		java.lang.String folderName, java.lang.String fileName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0 replaced by {@link #addTempFileEntry(long, long,
	String, String, InputStream, String)}
	*/
	@java.lang.Deprecated
	public void addTempPageAttachment(long groupId, long userId,
		java.lang.String fileName, java.lang.String tempFolderName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Adds the wiki page to the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.wiki.model.WikiPage addWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage);

	public void changeNode(long userId, long nodeId, java.lang.String title,
		long newNodeId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage changeParent(long userId,
		long nodeId, java.lang.String title, java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void copyPageAttachments(long userId, long templateNodeId,
		java.lang.String templateTitle, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Creates a new wiki page with the primary key. Does not add the wiki page to the database.
	*
	* @param pageId the primary key for the new wiki page
	* @return the new wiki page
	*/
	public com.liferay.portlet.wiki.model.WikiPage createWikiPage(long pageId);

	public void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 6.2.0 replaced by {@link #discardDraft(long, String,
	double)}
	*/
	@java.lang.Deprecated
	public void deletePage(long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.systemevent.SystemEvent(action = SystemEventConstants.ACTION_SKIP, send = false, type = SystemEventConstants.TYPE_DELETE)
	public void deletePage(com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deletePageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deletePageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deletePages(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteTempFileEntry(long groupId, long userId,
		java.lang.String folderName, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteTrashPageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes the wiki page with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page that was removed
	* @throws PortalException if a wiki page with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.wiki.model.WikiPage deleteWikiPage(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Deletes the wiki page from the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.wiki.model.WikiPage deleteWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage);

	public void discardDraft(long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

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
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

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
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage fetchLatestPage(
		long nodeId, java.lang.String title, int status, boolean preferApproved);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage fetchLatestPage(
		long resourcePrimKey, long nodeId, int status, boolean preferApproved);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage fetchLatestPage(
		long resourcePrimKey, int status, boolean preferApproved);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage fetchPage(long nodeId,
		java.lang.String title);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage fetchPage(long nodeId,
		java.lang.String title, double version);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage fetchPage(
		long resourcePrimKey);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage fetchWikiPage(long pageId);

	/**
	* Returns the wiki page matching the UUID and group.
	*
	* @param uuid the wiki page's UUID
	* @param groupId the primary key of the group
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage fetchWikiPageByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildren(
		long nodeId, boolean head, java.lang.String parentTitle, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChildrenCount(long nodeId, boolean head,
		java.lang.String parentTitle);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChildrenCount(long nodeId, boolean head,
		java.lang.String parentTitle, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getDependentPages(
		long nodeId, boolean head, java.lang.String title, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage getDraftPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getIncomingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage getLatestPage(long nodeId,
		java.lang.String title, int status, boolean preferApproved)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage getLatestPage(
		long resourcePrimKey, long nodeId, int status, boolean preferApproved)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage getLatestPage(
		long resourcePrimKey, int status, boolean preferApproved)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getNoAssetPages();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOrphans(
		long nodeId) throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOutgoingLinks(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage getPage(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage getPage(
		long resourcePrimKey, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage getPageByPageId(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPageDisplay getPageDisplay(
		long nodeId, java.lang.String title,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPageDisplay getPageDisplay(
		com.liferay.portlet.wiki.model.WikiPage page,
		javax.portlet.PortletURL viewPageURL,
		javax.portlet.PortletURL editPageURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		java.lang.String format);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.wiki.model.WikiPage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.wiki.model.WikiPage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.wiki.model.WikiPage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, boolean head, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.wiki.model.WikiPage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long resourcePrimKey, long nodeId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long userId, long nodeId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(java.lang.String format);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId, boolean head);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId, boolean head, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId, java.lang.String title);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId, java.lang.String title, boolean head);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long userId, long nodeId, int status);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage getPreviousVersionPage(
		com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long groupId, long nodeId, int start, int end);

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getRecentChanges(long, long,
	int, int)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecentChangesCount(long groupId, long nodeId);

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getRecentChangesCount(long,
	long)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecentChangesCount(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRedirectPages(
		long nodeId, boolean head, java.lang.String redirectTitle, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTempFileNames(long groupId, long userId,
		java.lang.String folderName)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the wiki page with the primary key.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page
	* @throws PortalException if a wiki page with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage getWikiPage(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the wiki page matching the UUID and group.
	*
	* @param uuid the wiki page's UUID
	* @param groupId the primary key of the group
	* @return the matching wiki page
	* @throws PortalException if a matching wiki page could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.wiki.model.WikiPage getWikiPageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException;

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
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getWikiPages(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getWikiPagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getWikiPagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.wiki.model.WikiPage> orderByComparator);

	/**
	* Returns the number of wiki pages.
	*
	* @return the number of wiki pages
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWikiPagesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasDraftPage(long nodeId, java.lang.String title);

	public void moveDependentToTrash(
		com.liferay.portlet.wiki.model.WikiPage page, long trashEntryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #renamePage(long, long,
	String, String, ServiceContext)}
	*/
	@java.lang.Deprecated
	public void movePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 6.2.0, replaced by {@link #renamePage(long, long,
	String, String, boolean, ServiceContext)}
	*/
	@java.lang.Deprecated
	public void movePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle, boolean strict,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry movePageAttachmentToTrash(
		long userId, long nodeId, java.lang.String title,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage movePageFromTrash(
		long userId, long nodeId, java.lang.String title, long newNodeId,
		java.lang.String newParentTitle)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #movePageFromTrash(long,
	long, String, long, String)} *
	*/
	@java.lang.Deprecated
	public com.liferay.portlet.wiki.model.WikiPage movePageFromTrash(
		long userId, long nodeId, java.lang.String title,
		java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long userId, long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long userId, com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void renamePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void renamePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle, boolean strict,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void restorePageAttachmentFromTrash(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void restorePageFromTrash(long userId,
		com.liferay.portlet.wiki.model.WikiPage page)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage revertPage(long userId,
		long nodeId, java.lang.String title, double version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public void subscribePage(long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void unsubscribePage(long userId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void updateAsset(long userId,
		com.liferay.portlet.wiki.model.WikiPage page, long[] assetCategoryIds,
		java.lang.String[] assetTagNames, long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage updatePage(long userId,
		long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, WikiPage,
	int, ServiceContext, Map)}
	*/
	@java.lang.Deprecated
	public com.liferay.portlet.wiki.model.WikiPage updateStatus(long userId,
		com.liferay.portlet.wiki.model.WikiPage page, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage updateStatus(long userId,
		com.liferay.portlet.wiki.model.WikiPage page, int status,
		com.liferay.portal.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.wiki.model.WikiPage updateStatus(long userId,
		long resourcePrimKey, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Updates the wiki page in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.wiki.model.WikiPage updateWikiPage(
		com.liferay.portlet.wiki.model.WikiPage wikiPage);

	public void validateTitle(java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException;
}
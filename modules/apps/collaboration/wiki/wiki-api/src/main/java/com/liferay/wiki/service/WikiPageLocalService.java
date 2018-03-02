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

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedResourcedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageDisplay;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.portlet.PortletURL;

/**
 * Provides the local service interface for WikiPage. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageLocalServiceUtil
 * @see com.liferay.wiki.service.base.WikiPageLocalServiceBaseImpl
 * @see com.liferay.wiki.service.impl.WikiPageLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface WikiPageLocalService extends BaseLocalService,
	PersistedModelLocalService, PersistedResourcedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiPageLocalServiceUtil} to access the wiki page local service. Add custom service methods to {@link com.liferay.wiki.service.impl.WikiPageLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public WikiPage addPage(long userId, long nodeId, java.lang.String title,
		double version, java.lang.String content, java.lang.String summary,
		boolean minorEdit, java.lang.String format, boolean head,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		ServiceContext serviceContext) throws PortalException;

	public WikiPage addPage(long userId, long nodeId, java.lang.String title,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		ServiceContext serviceContext) throws PortalException;

	public FileEntry addPageAttachment(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName, File file,
		java.lang.String mimeType) throws PortalException;

	public FileEntry addPageAttachment(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName,
		InputStream inputStream, java.lang.String mimeType)
		throws PortalException;

	public List<FileEntry> addPageAttachments(long userId, long nodeId,
		java.lang.String title,
		List<ObjectValuePair<java.lang.String, InputStream>> inputStreamOVPs)
		throws PortalException;

	public void addPageResources(long nodeId, java.lang.String title,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addPageResources(long nodeId, java.lang.String title,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws PortalException;

	public void addPageResources(WikiPage page, boolean addGroupPermissions,
		boolean addGuestPermissions) throws PortalException;

	public void addPageResources(WikiPage page,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws PortalException;

	public FileEntry addTempFileEntry(long groupId, long userId,
		java.lang.String folderName, java.lang.String fileName,
		InputStream inputStream, java.lang.String mimeType)
		throws PortalException;

	/**
	* @deprecated As of 1.2.0, replaced by {@link #addTempFileEntry(long, long,
	String, String, InputStream, String)}
	*/
	@java.lang.Deprecated
	public void addTempPageAttachment(long groupId, long userId,
		java.lang.String fileName, java.lang.String tempFolderName,
		InputStream inputStream, java.lang.String mimeType)
		throws PortalException;

	/**
	* Adds the wiki page to the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public WikiPage addWikiPage(WikiPage wikiPage);

	public WikiPage changeParent(long userId, long nodeId,
		java.lang.String title, java.lang.String newParentTitle,
		ServiceContext serviceContext) throws PortalException;

	public void copyPageAttachments(long userId, long templateNodeId,
		java.lang.String templateTitle, long nodeId, java.lang.String title)
		throws PortalException;

	/**
	* Creates a new wiki page with the primary key. Does not add the wiki page to the database.
	*
	* @param pageId the primary key for the new wiki page
	* @return the new wiki page
	*/
	public WikiPage createWikiPage(long pageId);

	public void deletePage(long nodeId, java.lang.String title)
		throws PortalException;

	@SystemEvent(action = SystemEventConstants.ACTION_SKIP, send = false, type = SystemEventConstants.TYPE_DELETE)
	public void deletePage(WikiPage page) throws PortalException;

	public void deletePageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName) throws PortalException;

	public void deletePageAttachments(long nodeId, java.lang.String title)
		throws PortalException;

	public void deletePages(long nodeId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public void deleteTempFileEntry(long groupId, long userId,
		java.lang.String folderName, java.lang.String fileName)
		throws PortalException;

	public void deleteTrashPageAttachments(long nodeId, java.lang.String title)
		throws PortalException;

	/**
	* Deletes the wiki page with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page that was removed
	* @throws PortalException if a wiki page with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public WikiPage deleteWikiPage(long pageId) throws PortalException;

	/**
	* Deletes the wiki page from the database. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public WikiPage deleteWikiPage(WikiPage wikiPage);

	public void discardDraft(long nodeId, java.lang.String title, double version)
		throws PortalException;

	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchLatestPage(long resourcePrimKey, int status,
		boolean preferApproved);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchLatestPage(long resourcePrimKey, long nodeId,
		int status, boolean preferApproved);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchLatestPage(long nodeId, java.lang.String title,
		int status, boolean preferApproved);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchPage(long resourcePrimKey);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchPage(long nodeId, java.lang.String title);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchPage(long nodeId, java.lang.String title,
		double version);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchWikiPage(long pageId);

	/**
	* Returns the wiki page matching the UUID and group.
	*
	* @param uuid the wiki page's UUID
	* @param groupId the primary key of the group
	* @return the matching wiki page, or <code>null</code> if a matching wiki page could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchWikiPageByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getChildren(long nodeId, boolean head,
		java.lang.String parentTitle);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getChildren(long nodeId, boolean head,
		java.lang.String parentTitle, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getChildren(long nodeId, boolean head,
		java.lang.String parentTitle, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getChildren(long nodeId, boolean head,
		java.lang.String parentTitle, int status, int start, int end,
		OrderByComparator obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChildrenCount(long nodeId, boolean head,
		java.lang.String parentTitle);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChildrenCount(long nodeId, boolean head,
		java.lang.String parentTitle, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getDependentPages(long nodeId, boolean head,
		java.lang.String title, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPageDisplay getDisplay(long nodeId, java.lang.String title,
		PortletURL viewPageURL, Supplier<PortletURL> editPageURLSupplier,
		java.lang.String attachmentURLPrefix) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getDraftPage(long nodeId, java.lang.String title)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getIncomingLinks(long nodeId, java.lang.String title)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getLatestPage(long resourcePrimKey, int status,
		boolean preferApproved) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getLatestPage(long resourcePrimKey, long nodeId,
		int status, boolean preferApproved) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getLatestPage(long nodeId, java.lang.String title,
		int status, boolean preferApproved) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getNoAssetPages();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getOrphans(List<WikiPage> pages)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getOrphans(long nodeId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getOutgoingLinks(long nodeId, java.lang.String title)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long resourcePrimKey) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long resourcePrimKey, java.lang.Boolean head)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long nodeId, java.lang.String title)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long nodeId, java.lang.String title,
		java.lang.Boolean head) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long nodeId, java.lang.String title, double version)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPageByPageId(long pageId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPageDisplay getPageDisplay(long nodeId, java.lang.String title,
		PortletURL viewPageURL, PortletURL editPageURL,
		java.lang.String attachmentURLPrefix) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPageDisplay getPageDisplay(WikiPage page,
		PortletURL viewPageURL, PortletURL editPageURL,
		java.lang.String attachmentURLPrefix) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPageDisplay getPageDisplay(WikiPage page,
		PortletURL viewPageURL, PortletURL editPageURL,
		java.lang.String attachmentURLPrefix, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPageDisplay getPageDisplay(WikiPage page,
		PortletURL viewPageURL, Supplier<PortletURL> editPageURLSupplier,
		java.lang.String attachmentURLPrefix, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long nodeId, boolean head, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long nodeId, boolean head, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long nodeId, boolean head, int status,
		int start, int end, OrderByComparator<WikiPage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long nodeId, boolean head, int start,
		int end, OrderByComparator<WikiPage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long nodeId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long nodeId, int start, int end,
		OrderByComparator<WikiPage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long resourcePrimKey, long nodeId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long userId, long nodeId, int status,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long nodeId, java.lang.String title,
		boolean head, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long nodeId, java.lang.String title,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long nodeId, java.lang.String title,
		int start, int end, OrderByComparator<WikiPage> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(java.lang.String format);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId, boolean head);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId, boolean head, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long userId, long nodeId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId, java.lang.String title);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long nodeId, java.lang.String title, boolean head);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(java.lang.String format);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<?extends PersistedModel> getPersistedModel(long resourcePrimKey)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPreviousVersionPage(WikiPage page)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getRecentChanges(long groupId, long nodeId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecentChangesCount(long groupId, long nodeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getRedirectorPages(long nodeId, boolean head,
		java.lang.String redirectTitle, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getRedirectorPages(long nodeId,
		java.lang.String redirectTitle);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTempFileNames(long groupId, long userId,
		java.lang.String folderName) throws PortalException;

	/**
	* Returns the wiki page with the primary key.
	*
	* @param pageId the primary key of the wiki page
	* @return the wiki page
	* @throws PortalException if a wiki page with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getWikiPage(long pageId) throws PortalException;

	/**
	* Returns the wiki page matching the UUID and group.
	*
	* @param uuid the wiki page's UUID
	* @param groupId the primary key of the group
	* @return the matching wiki page
	* @throws PortalException if a matching wiki page could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getWikiPageByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getWikiPages(int start, int end);

	/**
	* Returns all the wiki pages matching the UUID and company.
	*
	* @param uuid the UUID of the wiki pages
	* @param companyId the primary key of the company
	* @return the matching wiki pages, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getWikiPagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getWikiPagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<WikiPage> orderByComparator);

	/**
	* Returns the number of wiki pages.
	*
	* @return the number of wiki pages
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWikiPagesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasDraftPage(long nodeId, java.lang.String title);

	public void moveDependentToTrash(WikiPage page, long trashEntryId)
		throws PortalException;

	/**
	* @deprecated As of 1.2.0, replaced by {@link #renamePage(long, long,
	String, String, ServiceContext)}
	*/
	@java.lang.Deprecated
	public void movePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle, ServiceContext serviceContext)
		throws PortalException;

	public FileEntry movePageAttachmentToTrash(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws PortalException;

	public WikiPage movePageFromTrash(long userId, long nodeId,
		java.lang.String title, long newNodeId, java.lang.String newParentTitle)
		throws PortalException;

	/**
	* @deprecated As of 1.2.0, replaced by {@link #movePageFromTrash(long,
	long, String, long, String)} *
	*/
	@java.lang.Deprecated
	public WikiPage movePageFromTrash(long userId, long nodeId,
		java.lang.String title, java.lang.String newParentTitle,
		ServiceContext serviceContext) throws PortalException;

	public WikiPage movePageToTrash(long userId, long nodeId,
		java.lang.String title) throws PortalException;

	public WikiPage movePageToTrash(long userId, long nodeId,
		java.lang.String title, double version) throws PortalException;

	public WikiPage movePageToTrash(long userId, WikiPage page)
		throws PortalException;

	public void renamePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle, boolean strict, ServiceContext serviceContext)
		throws PortalException;

	public void renamePage(long userId, long nodeId, java.lang.String title,
		java.lang.String newTitle, ServiceContext serviceContext)
		throws PortalException;

	public void restorePageAttachmentFromTrash(long userId, long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws PortalException;

	public void restorePageFromTrash(long userId, WikiPage page)
		throws PortalException;

	public WikiPage revertPage(long userId, long nodeId,
		java.lang.String title, double version, ServiceContext serviceContext)
		throws PortalException;

	public void subscribePage(long userId, long nodeId, java.lang.String title)
		throws PortalException;

	public void unsubscribePage(long userId, long nodeId, java.lang.String title)
		throws PortalException;

	public void updateAsset(long userId, WikiPage page,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws PortalException;

	public WikiPage updatePage(long userId, long nodeId,
		java.lang.String title, double version, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		ServiceContext serviceContext) throws PortalException;

	public WikiPage updateStatus(long userId, long resourcePrimKey, int status,
		ServiceContext serviceContext) throws PortalException;

	/**
	* @deprecated As of 1.2.0, replaced by {@link #updateStatus(long, WikiPage,
	int, ServiceContext, Map)}
	*/
	@java.lang.Deprecated
	public WikiPage updateStatus(long userId, WikiPage page, int status,
		ServiceContext serviceContext) throws PortalException;

	public WikiPage updateStatus(long userId, WikiPage page, int status,
		ServiceContext serviceContext,
		Map<java.lang.String, Serializable> workflowContext)
		throws PortalException;

	/**
	* Updates the wiki page in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param wikiPage the wiki page
	* @return the wiki page that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public WikiPage updateWikiPage(WikiPage wikiPage);

	/**
	* @deprecated As of 1.2.0, replaced by {@link
	WikiPageTitleValidator#validate(String)}
	*/
	@java.lang.Deprecated
	public void validateTitle(java.lang.String title) throws PortalException;
}
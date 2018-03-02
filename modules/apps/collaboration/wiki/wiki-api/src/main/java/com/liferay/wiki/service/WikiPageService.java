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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Locale;

/**
 * Provides the remote service interface for WikiPage. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageServiceUtil
 * @see com.liferay.wiki.service.base.WikiPageServiceBaseImpl
 * @see com.liferay.wiki.service.impl.WikiPageServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=wiki", "json.web.service.context.path=WikiPage"}, service = WikiPageService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface WikiPageService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiPageServiceUtil} to access the wiki page remote service. Add custom service methods to {@link com.liferay.wiki.service.impl.WikiPageServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public WikiPage addPage(long nodeId, java.lang.String title,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		ServiceContext serviceContext) throws PortalException;

	public WikiPage addPage(long nodeId, java.lang.String title,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, java.lang.String parentTitle,
		java.lang.String redirectTitle, ServiceContext serviceContext)
		throws PortalException;

	public FileEntry addPageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName, File file, java.lang.String mimeType)
		throws PortalException;

	public FileEntry addPageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName, InputStream inputStream,
		java.lang.String mimeType) throws PortalException;

	public List<FileEntry> addPageAttachments(long nodeId,
		java.lang.String title,
		List<ObjectValuePair<java.lang.String, InputStream>> inputStreamOVPs)
		throws PortalException;

	public FileEntry addTempFileEntry(long nodeId, java.lang.String folderName,
		java.lang.String fileName, InputStream inputStream,
		java.lang.String mimeType) throws PortalException;

	/**
	* @deprecated As of 1.2.0, replaced by {@link #addTempFileEntry(long,
	String, String, InputStream, String)}
	*/
	@java.lang.Deprecated
	public void addTempPageAttachment(long nodeId, java.lang.String fileName,
		java.lang.String tempFolderName, InputStream inputStream,
		java.lang.String mimeType) throws PortalException;

	public void changeParent(long nodeId, java.lang.String title,
		java.lang.String newParentTitle, ServiceContext serviceContext)
		throws PortalException;

	public void copyPageAttachments(long templateNodeId,
		java.lang.String templateTitle, long nodeId, java.lang.String title)
		throws PortalException;

	public void deletePage(long nodeId, java.lang.String title)
		throws PortalException;

	public void deletePageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName) throws PortalException;

	public void deletePageAttachments(long nodeId, java.lang.String title)
		throws PortalException;

	public void deleteTempFileEntry(long nodeId, java.lang.String folderName,
		java.lang.String fileName) throws PortalException;

	public void deleteTrashPageAttachments(long nodeId, java.lang.String title)
		throws PortalException;

	public void discardDraft(long nodeId, java.lang.String title, double version)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchPage(long nodeId, java.lang.String title,
		double version) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getChildren(long groupId, long nodeId, boolean head,
		java.lang.String parentTitle) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getDraftPage(long nodeId, java.lang.String title)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getNodePages(long nodeId, int max)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		java.lang.String attachmentURLPrefix) throws PortalException;

	/**
	* @deprecated As of 2.0.0, replaced by {@link #getOrphans(WikiNode)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getOrphans(long groupId, long nodeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getOrphans(WikiNode node) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long pageId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long groupId, long nodeId, java.lang.String title)
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
	public List<WikiPage> getPages(long groupId, long nodeId, boolean head,
		int status, int start, int end, OrderByComparator<WikiPage> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long groupId, long nodeId, boolean head,
		long userId, boolean includeOwner, int status, int start, int end,
		OrderByComparator<WikiPage> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(long groupId, long userId, long nodeId,
		int status, int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long groupId, long nodeId, boolean head)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long groupId, long nodeId, boolean head,
		long userId, boolean includeOwner, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long groupId, long userId, long nodeId, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getPagesRSS(long nodeId, java.lang.String title,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.lang.String attachmentURLPrefix,
		Locale locale) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getRecentChanges(long groupId, long nodeId,
		int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecentChangesCount(long groupId, long nodeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getTempFileNames(long nodeId,
		java.lang.String folderName) throws PortalException;

	public FileEntry movePageAttachmentToTrash(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws PortalException;

	public WikiPage movePageToTrash(long nodeId, java.lang.String title)
		throws PortalException;

	public WikiPage movePageToTrash(long nodeId, java.lang.String title,
		double version) throws PortalException;

	public void renamePage(long nodeId, java.lang.String title,
		java.lang.String newTitle, ServiceContext serviceContext)
		throws PortalException;

	public void restorePageAttachmentFromTrash(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws PortalException;

	public void restorePageFromTrash(long resourcePrimKey)
		throws PortalException;

	public WikiPage revertPage(long nodeId, java.lang.String title,
		double version, ServiceContext serviceContext)
		throws PortalException;

	public void subscribePage(long nodeId, java.lang.String title)
		throws PortalException;

	public void unsubscribePage(long nodeId, java.lang.String title)
		throws PortalException;

	public WikiPage updatePage(long nodeId, java.lang.String title,
		double version, java.lang.String content, java.lang.String summary,
		boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		ServiceContext serviceContext) throws PortalException;
}
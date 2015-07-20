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

package com.liferay.wiki.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import com.liferay.wiki.service.WikiPageService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for WikiPage. This utility wraps
 * {@link com.liferay.wiki.service.impl.WikiPageServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=wiki", "json.web.service.context.path=WikiPage"}, service = WikiPageJsonService.class)
@JSONWebService
@ProviderType
public class WikiPageJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.wiki.service.impl.WikiPageServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.wiki.model.WikiPage addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addPage(nodeId, title, content, summary, minorEdit,
			format, parentTitle, redirectTitle, serviceContext);
	}

	public com.liferay.wiki.model.WikiPage addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addPage(nodeId, title, content, summary, minorEdit,
			serviceContext);
	}

	public void addPageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName, java.io.File file, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.addPageAttachment(nodeId, title, fileName, file, mimeType);
	}

	public void addPageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName, java.io.InputStream inputStream,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.addPageAttachment(nodeId, title, fileName, inputStream,
			mimeType);
	}

	public void addPageAttachments(long nodeId, java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.addPageAttachments(nodeId, title, inputStreamOVPs);
	}

	public void addTempFileEntry(long nodeId, java.lang.String folderName,
		java.lang.String fileName, java.io.InputStream inputStream,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.addTempFileEntry(nodeId, folderName, fileName, inputStream,
			mimeType);
	}

	/**
	* @deprecated As of 7.0.0 replaced by {@link #addTempFileEntry(long,
	String, String, InputStream, String)}
	*/
	@Deprecated
	public void addTempPageAttachment(long nodeId, java.lang.String fileName,
		java.lang.String tempFolderName, java.io.InputStream inputStream,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.addTempPageAttachment(nodeId, fileName, tempFolderName,
			inputStream, mimeType);
	}

	public void changeNode(long nodeId, java.lang.String title, long newNodeId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.changeNode(nodeId, title, newNodeId, serviceContext);
	}

	public void changeParent(long nodeId, java.lang.String title,
		java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.changeParent(nodeId, title, newParentTitle, serviceContext);
	}

	public void copyPageAttachments(long templateNodeId,
		java.lang.String templateTitle, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.copyPageAttachments(templateNodeId, templateTitle, nodeId,
			title);
	}

	public void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deletePage(nodeId, title);
	}

	/**
	* @deprecated As of 6.2.0 replaced by {@link #discardDraft(long, String,
	double)}
	*/
	@Deprecated
	public void deletePage(long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deletePage(nodeId, title, version);
	}

	public void deletePageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deletePageAttachment(nodeId, title, fileName);
	}

	public void deletePageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deletePageAttachments(nodeId, title);
	}

	public void deleteTempFileEntry(long nodeId, java.lang.String folderName,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deleteTempFileEntry(nodeId, folderName, fileName);
	}

	public void deleteTrashPageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deleteTrashPageAttachments(nodeId, title);
	}

	public void discardDraft(long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.discardDraft(nodeId, title, version);
	}

	public com.liferay.wiki.model.WikiPage fetchPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.fetchPage(nodeId, title, version);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public java.util.List<com.liferay.wiki.model.WikiPage> getChildren(
		long groupId, long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getChildren(groupId, nodeId, head, parentTitle);
	}

	public com.liferay.wiki.model.WikiPage getDraftPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getDraftPage(nodeId, title);
	}

	public java.util.List<com.liferay.wiki.model.WikiPage> getNodePages(
		long nodeId, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getNodePages(nodeId, max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getNodePagesRSS(long, int,
	String, double, String, String, String, String)}
	*/
	@Deprecated
	public java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getNodePagesRSS(nodeId, max, type, version,
			displayStyle, feedURL, entryURL);
	}

	public java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getNodePagesRSS(nodeId, max, type, version,
			displayStyle, feedURL, entryURL, attachmentURLPrefix);
	}

	public java.util.List<com.liferay.wiki.model.WikiPage> getOrphans(
		long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getOrphans(groupId, nodeId);
	}

	public com.liferay.wiki.model.WikiPage getPage(long groupId, long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getPage(groupId, nodeId, title);
	}

	public com.liferay.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getPage(nodeId, title);
	}

	public com.liferay.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getPage(nodeId, title, head);
	}

	public com.liferay.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getPage(nodeId, title, version);
	}

	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long groupId, long nodeId, boolean head, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiPage> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getPages(groupId, nodeId, head, status, start, end, obc);
	}

	public java.util.List<com.liferay.wiki.model.WikiPage> getPages(
		long groupId, long userId, long nodeId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getPages(groupId, userId, nodeId, status, start, end);
	}

	public int getPagesCount(long groupId, long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getPagesCount(groupId, nodeId, head);
	}

	public int getPagesCount(long groupId, long userId, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getPagesCount(groupId, userId, nodeId, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getPagesRSS(long, String,
	int, String, double, String, String, String, String,
	java.util.Locale)}
	*/
	@Deprecated
	public java.lang.String getPagesRSS(long companyId, long nodeId,
		java.lang.String title, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getPagesRSS(companyId, nodeId, title, max, type,
			version, displayStyle, feedURL, entryURL, locale);
	}

	public java.lang.String getPagesRSS(long nodeId, java.lang.String title,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.lang.String attachmentURLPrefix,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getPagesRSS(nodeId, title, max, type, version,
			displayStyle, feedURL, entryURL, attachmentURLPrefix, locale);
	}

	public java.util.List<com.liferay.wiki.model.WikiPage> getRecentChanges(
		long groupId, long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getRecentChanges(groupId, nodeId, start, end);
	}

	public int getRecentChangesCount(long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getRecentChangesCount(groupId, nodeId);
	}

	public java.lang.String[] getTempFileNames(long nodeId,
		java.lang.String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getTempFileNames(nodeId, folderName);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #renamePage(long, String,
	String, ServiceContext)}
	*/
	@Deprecated
	public void movePage(long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.movePage(nodeId, title, newTitle, serviceContext);
	}

	public com.liferay.portal.kernel.repository.model.FileEntry movePageAttachmentToTrash(
		long nodeId, java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.movePageAttachmentToTrash(nodeId, title, fileName);
	}

	public com.liferay.wiki.model.WikiPage movePageToTrash(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.movePageToTrash(nodeId, title);
	}

	public com.liferay.wiki.model.WikiPage movePageToTrash(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.movePageToTrash(nodeId, title, version);
	}

	public void renamePage(long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.renamePage(nodeId, title, newTitle, serviceContext);
	}

	public void restorePageAttachmentFromTrash(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.restorePageAttachmentFromTrash(nodeId, title, fileName);
	}

	public void restorePageFromTrash(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.restorePageFromTrash(resourcePrimKey);
	}

	public com.liferay.wiki.model.WikiPage revertPage(long nodeId,
		java.lang.String title, double version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.revertPage(nodeId, title, version, serviceContext);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public void subscribePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.subscribePage(nodeId, title);
	}

	public void unsubscribePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.unsubscribePage(nodeId, title);
	}

	public com.liferay.wiki.model.WikiPage updatePage(long nodeId,
		java.lang.String title, double version, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updatePage(nodeId, title, version, content, summary,
			minorEdit, format, parentTitle, redirectTitle, serviceContext);
	}

	@Reference
	protected void setService(WikiPageService service) {
		_service = service;
	}

	private WikiPageService _service;
}
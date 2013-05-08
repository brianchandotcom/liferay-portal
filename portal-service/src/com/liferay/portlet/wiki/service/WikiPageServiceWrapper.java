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
 * Provides a wrapper for {@link WikiPageService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageService
 * @generated
 */
public class WikiPageServiceWrapper implements WikiPageService,
	ServiceWrapper<WikiPageService> {
	public WikiPageServiceWrapper(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _wikiPageService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_wikiPageService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.wiki.model.WikiPage addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.addPage(nodeId, title, content, summary,
			minorEdit, serviceContext);
	}

	public com.liferay.portlet.wiki.model.WikiPage addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.addPage(nodeId, title, content, summary,
			minorEdit, format, parentTitle, redirectTitle, serviceContext);
	}

	public void addPageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName, java.io.File file, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.addPageAttachment(nodeId, title, fileName, file,
			mimeType);
	}

	public void addPageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName, java.io.InputStream inputStream,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.addPageAttachment(nodeId, title, fileName,
			inputStream, mimeType);
	}

	public void addPageAttachments(long nodeId, java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.addPageAttachments(nodeId, title, inputStreamOVPs);
	}

	public void addTempPageAttachment(long nodeId, java.lang.String fileName,
		java.lang.String tempFolderName, java.io.InputStream inputStream,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.addTempPageAttachment(nodeId, fileName,
			tempFolderName, inputStream, mimeType);
	}

	public void changeParent(long nodeId, java.lang.String title,
		java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.changeParent(nodeId, title, newParentTitle,
			serviceContext);
	}

	public void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.deletePage(nodeId, title);
	}

	/**
	* @deprecated As of 6.2.0 replaced by {@link #discardDraft(long, String,
	double)}
	*/
	public void deletePage(long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.deletePage(nodeId, title, version);
	}

	public void deletePageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.deletePageAttachment(nodeId, title, fileName);
	}

	public void deletePageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.deletePageAttachments(nodeId, title);
	}

	public void deleteTempPageAttachment(long nodeId,
		java.lang.String fileName, java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.deleteTempPageAttachment(nodeId, fileName,
			tempFolderName);
	}

	public void deleteTrashPageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.deleteTrashPageAttachments(nodeId, title);
	}

	public void discardDraft(long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.discardDraft(nodeId, title, version);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildren(
		long groupId, long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getChildren(groupId, nodeId, head, parentTitle);
	}

	public com.liferay.portlet.wiki.model.WikiPage getDraftPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getDraftPage(nodeId, title);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getNodePages(
		long nodeId, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getNodePages(nodeId, max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getNodePagesRSS(long, int,
	String, double, String, String, String, String)}
	*/
	public java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getNodePagesRSS(nodeId, max, type, version,
			displayStyle, feedURL, entryURL);
	}

	public java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getNodePagesRSS(nodeId, max, type, version,
			displayStyle, feedURL, entryURL, attachmentURLPrefix);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOrphans(
		long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getOrphans(groupId, nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long groupId,
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getPage(groupId, nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getPage(nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getPage(nodeId, title, head);
	}

	public com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getPage(nodeId, title, version);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long groupId, long nodeId, boolean head, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getPages(groupId, nodeId, head, status, start,
			end, obc);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long groupId, long userId, long nodeId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getPages(groupId, userId, nodeId, status,
			start, end);
	}

	public int getPagesCount(long groupId, long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getPagesCount(groupId, nodeId, head);
	}

	public int getPagesCount(long groupId, long userId, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getPagesCount(groupId, userId, nodeId, status);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getPagesRSS(long, long,
	String, int, String, double, String, String, String, String,
	java.util.Locale)}
	*/
	public java.lang.String getPagesRSS(long companyId, long nodeId,
		java.lang.String title, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getPagesRSS(companyId, nodeId, title, max,
			type, version, displayStyle, feedURL, entryURL, locale);
	}

	public java.lang.String getPagesRSS(long companyId, long nodeId,
		java.lang.String title, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.lang.String attachmentURLPrefix,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getPagesRSS(companyId, nodeId, title, max,
			type, version, displayStyle, feedURL, entryURL,
			attachmentURLPrefix, locale);
	}

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long groupId, long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getRecentChanges(groupId, nodeId, start, end);
	}

	public int getRecentChangesCount(long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getRecentChangesCount(groupId, nodeId);
	}

	public java.lang.String[] getTempPageAttachmentNames(long nodeId,
		java.lang.String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.getTempPageAttachmentNames(nodeId,
			tempFolderName);
	}

	public void movePage(long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.movePage(nodeId, title, newTitle, serviceContext);
	}

	public long movePageAttachmentToTrash(long nodeId, java.lang.String title,
		java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.movePageAttachmentToTrash(nodeId, title,
			fileName);
	}

	public void movePageToTrash(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.movePageToTrash(nodeId, title);
	}

	public void movePageToTrash(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.movePageToTrash(nodeId, title, version);
	}

	public void restorePageAttachmentFromTrash(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.restorePageAttachmentFromTrash(nodeId, title, fileName);
	}

	public void restorePageFromTrash(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.restorePageFromTrash(resourcePrimKey);
	}

	public com.liferay.portlet.wiki.model.WikiPage revertPage(long nodeId,
		java.lang.String title, double version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.revertPage(nodeId, title, version,
			serviceContext);
	}

	public void subscribePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.subscribePage(nodeId, title);
	}

	public void unsubscribePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiPageService.unsubscribePage(nodeId, title);
	}

	public com.liferay.portlet.wiki.model.WikiPage updatePage(long nodeId,
		java.lang.String title, double version, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiPageService.updatePage(nodeId, title, version, content,
			summary, minorEdit, format, parentTitle, redirectTitle,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public WikiPageService getWrappedWikiPageService() {
		return _wikiPageService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedWikiPageService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	public WikiPageService getWrappedService() {
		return _wikiPageService;
	}

	public void setWrappedService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	private WikiPageService _wikiPageService;
}
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for WikiPage. This utility wraps
 * {@link com.liferay.portlet.wiki.service.impl.WikiPageServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageService
 * @see com.liferay.portlet.wiki.service.base.WikiPageServiceBaseImpl
 * @see com.liferay.portlet.wiki.service.impl.WikiPageServiceImpl
 * @generated
 */
@ProviderType
public class WikiPageServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.wiki.service.impl.WikiPageServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.wiki.model.WikiPage addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addPage(nodeId, title, content, summary, minorEdit, format,
			parentTitle, redirectTitle, serviceContext);
	}

	public static com.liferay.portlet.wiki.model.WikiPage addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addPage(nodeId, title, content, summary, minorEdit,
			serviceContext);
	}

	public static void addPageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName, java.io.File file, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addPageAttachment(nodeId, title, fileName, file, mimeType);
	}

	public static void addPageAttachment(long nodeId, java.lang.String title,
		java.lang.String fileName, java.io.InputStream inputStream,
		java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addPageAttachment(nodeId, title, fileName, inputStream, mimeType);
	}

	public static void addPageAttachments(long nodeId, java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addPageAttachments(nodeId, title, inputStreamOVPs);
	}

	public static void addTempFileEntry(long nodeId,
		java.lang.String folderName, java.lang.String fileName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addTempFileEntry(nodeId, folderName, fileName, inputStream,
			mimeType);
	}

	/**
	* @deprecated As of 7.0.0 replaced by {@link #addTempFileEntry(long,
	String, String, InputStream, String)}
	*/
	@Deprecated
	public static void addTempPageAttachment(long nodeId,
		java.lang.String fileName, java.lang.String tempFolderName,
		java.io.InputStream inputStream, java.lang.String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addTempPageAttachment(nodeId, fileName, tempFolderName,
			inputStream, mimeType);
	}

	public static void changeNode(long nodeId, java.lang.String title,
		long newNodeId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().changeNode(nodeId, title, newNodeId, serviceContext);
	}

	public static void changeParent(long nodeId, java.lang.String title,
		java.lang.String newParentTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().changeParent(nodeId, title, newParentTitle, serviceContext);
	}

	public static void copyPageAttachments(long templateNodeId,
		java.lang.String templateTitle, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.copyPageAttachments(templateNodeId, templateTitle, nodeId, title);
	}

	public static void deletePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deletePage(nodeId, title);
	}

	/**
	* @deprecated As of 6.2.0 replaced by {@link #discardDraft(long, String,
	double)}
	*/
	@Deprecated
	public static void deletePage(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deletePage(nodeId, title, version);
	}

	public static void deletePageAttachment(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deletePageAttachment(nodeId, title, fileName);
	}

	public static void deletePageAttachments(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deletePageAttachments(nodeId, title);
	}

	public static void deleteTempFileEntry(long nodeId,
		java.lang.String folderName, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTempFileEntry(nodeId, folderName, fileName);
	}

	public static void deleteTrashPageAttachments(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTrashPageAttachments(nodeId, title);
	}

	public static void discardDraft(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().discardDraft(nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchPage(
		long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchPage(nodeId, title, version);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getChildren(
		long groupId, long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getChildren(groupId, nodeId, head, parentTitle);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getDraftPage(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDraftPage(nodeId, title);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getNodePages(
		long nodeId, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getNodePages(nodeId, max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getNodePagesRSS(long, int,
	String, double, String, String, String, String)}
	*/
	@Deprecated
	public static java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getNodePagesRSS(nodeId, max, type, version, displayStyle,
			feedURL, entryURL);
	}

	public static java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		java.lang.String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getNodePagesRSS(nodeId, max, type, version, displayStyle,
			feedURL, entryURL, attachmentURLPrefix);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getOrphans(
		long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrphans(groupId, nodeId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(
		long groupId, long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPage(groupId, nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPage(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, java.lang.Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPage(nodeId, title, head);
	}

	public static com.liferay.portlet.wiki.model.WikiPage getPage(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPage(nodeId, title, version);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long groupId, long nodeId, boolean head, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.wiki.model.WikiPage> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getPages(groupId, nodeId, head, status, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getPages(
		long groupId, long userId, long nodeId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPages(groupId, userId, nodeId, status, start, end);
	}

	public static int getPagesCount(long groupId, long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPagesCount(groupId, nodeId, head);
	}

	public static int getPagesCount(long groupId, long userId, long nodeId,
		int status) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPagesCount(groupId, userId, nodeId, status);
	}

	public static java.lang.String getPagesRSS(long companyId, long nodeId,
		java.lang.String title, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.lang.String attachmentURLPrefix,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getPagesRSS(companyId, nodeId, title, max, type, version,
			displayStyle, feedURL, entryURL, attachmentURLPrefix, locale);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getPagesRSS(long, long,
	String, int, String, double, String, String, String, String,
	java.util.Locale)}
	*/
	@Deprecated
	public static java.lang.String getPagesRSS(long companyId, long nodeId,
		java.lang.String title, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getPagesRSS(companyId, nodeId, title, max, type, version,
			displayStyle, feedURL, entryURL, locale);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> getRecentChanges(
		long groupId, long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecentChanges(groupId, nodeId, start, end);
	}

	public static int getRecentChangesCount(long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecentChangesCount(groupId, nodeId);
	}

	public static java.lang.String[] getTempFileNames(long nodeId,
		java.lang.String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTempFileNames(nodeId, folderName);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #renamePage(long, String,
	String, ServiceContext)}
	*/
	public static void movePage(long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().movePage(nodeId, title, newTitle, serviceContext);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry movePageAttachmentToTrash(
		long nodeId, java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().movePageAttachmentToTrash(nodeId, title, fileName);
	}

	public static com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().movePageToTrash(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage movePageToTrash(
		long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().movePageToTrash(nodeId, title, version);
	}

	public static void renamePage(long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().renamePage(nodeId, title, newTitle, serviceContext);
	}

	public static void restorePageAttachmentFromTrash(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restorePageAttachmentFromTrash(nodeId, title, fileName);
	}

	public static void restorePageFromTrash(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restorePageFromTrash(resourcePrimKey);
	}

	public static com.liferay.portlet.wiki.model.WikiPage revertPage(
		long nodeId, java.lang.String title, double version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().revertPage(nodeId, title, version, serviceContext);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void subscribePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().subscribePage(nodeId, title);
	}

	public static void unsubscribePage(long nodeId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsubscribePage(nodeId, title);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updatePage(
		long nodeId, java.lang.String title, double version,
		java.lang.String content, java.lang.String summary, boolean minorEdit,
		java.lang.String format, java.lang.String parentTitle,
		java.lang.String redirectTitle,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePage(nodeId, title, version, content, summary,
			minorEdit, format, parentTitle, redirectTitle, serviceContext);
	}

	public static WikiPageService getService() {
		if (_service == null) {
			_service = (WikiPageService)PortalBeanLocatorUtil.locate(WikiPageService.class.getName());

			ReferenceRegistry.registerReference(WikiPageServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(WikiPageService service) {
	}

	private static WikiPageService _service;
}
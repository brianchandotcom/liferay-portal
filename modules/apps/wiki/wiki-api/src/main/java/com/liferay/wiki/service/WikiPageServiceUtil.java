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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for WikiPage. This utility wraps
 * <code>com.liferay.wiki.service.impl.WikiPageServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageService
 * @generated
 */
public class WikiPageServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.wiki.service.impl.WikiPageServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WikiPageServiceUtil} to access the wiki page remote service. Add custom service methods to <code>com.liferay.wiki.service.impl.WikiPageServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.wiki.model.WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addPage(
			nodeId, title, content, summary, minorEdit, serviceContext);
	}

	public static com.liferay.wiki.model.WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, String format, String parentTitle,
			String redirectTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addPage(
			nodeId, title, content, summary, minorEdit, format, parentTitle,
			redirectTitle, serviceContext);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addPageAttachment(
				long nodeId, String title, String fileName, java.io.File file,
				String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addPageAttachment(
			nodeId, title, fileName, file, mimeType);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addPageAttachment(
				long nodeId, String title, String fileName,
				java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addPageAttachment(
			nodeId, title, fileName, inputStream, mimeType);
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry>
				addPageAttachments(
					long nodeId, String title,
					java.util.List
						<com.liferay.portal.kernel.util.ObjectValuePair
							<String, java.io.InputStream>> inputStreamOVPs)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addPageAttachments(nodeId, title, inputStreamOVPs);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addTempFileEntry(
				long nodeId, String folderName, String fileName,
				java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTempFileEntry(
			nodeId, folderName, fileName, inputStream, mimeType);
	}

	public static void changeParent(
			long nodeId, String title, String newParentTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().changeParent(
			nodeId, title, newParentTitle, serviceContext);
	}

	public static void copyPageAttachments(
			long templateNodeId, String templateTitle, long nodeId,
			String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().copyPageAttachments(
			templateNodeId, templateTitle, nodeId, title);
	}

	public static void deletePage(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deletePage(nodeId, title);
	}

	public static void deletePageAttachment(
			long nodeId, String title, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deletePageAttachment(nodeId, title, fileName);
	}

	public static void deletePageAttachments(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deletePageAttachments(nodeId, title);
	}

	public static void deleteTempFileEntry(
			long nodeId, String folderName, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteTempFileEntry(nodeId, folderName, fileName);
	}

	public static void deleteTrashPageAttachments(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteTrashPageAttachments(nodeId, title);
	}

	public static void discardDraft(long nodeId, String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().discardDraft(nodeId, title, version);
	}

	public static com.liferay.wiki.model.WikiPage fetchPage(
			long nodeId, String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchPage(nodeId, title, version);
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getChildren(
			long groupId, long nodeId, boolean head, String parentTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getChildren(groupId, nodeId, head, parentTitle);
	}

	public static com.liferay.wiki.model.WikiPage getDraftPage(
			long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDraftPage(nodeId, title);
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getNodePages(
			long nodeId, int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNodePages(nodeId, max);
	}

	public static String getNodePagesRSS(
			long nodeId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getNodePagesRSS(
			nodeId, max, type, version, displayStyle, feedURL, entryURL,
			attachmentURLPrefix);
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getOrphans(
			com.liferay.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getOrphans(node);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.wiki.model.WikiPage getPage(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPage(pageId);
	}

	public static com.liferay.wiki.model.WikiPage getPage(
			long groupId, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPage(groupId, nodeId, title);
	}

	public static com.liferay.wiki.model.WikiPage getPage(
			long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPage(nodeId, title);
	}

	public static com.liferay.wiki.model.WikiPage getPage(
			long nodeId, String title, Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPage(nodeId, title, head);
	}

	public static com.liferay.wiki.model.WikiPage getPage(
			long nodeId, String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPage(nodeId, title, version);
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getPages(
			long groupId, long nodeId, boolean head, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.wiki.model.WikiPage> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPages(
			groupId, nodeId, head, status, start, end, obc);
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getPages(
			long groupId, long nodeId, boolean head, long userId,
			boolean includeOwner, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.wiki.model.WikiPage> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPages(
			groupId, nodeId, head, userId, includeOwner, status, start, end,
			obc);
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage> getPages(
			long groupId, long userId, long nodeId, int status, int start,
			int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPages(
			groupId, userId, nodeId, status, start, end);
	}

	public static int getPagesCount(long groupId, long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPagesCount(groupId, nodeId, head);
	}

	public static int getPagesCount(
			long groupId, long nodeId, boolean head, long userId,
			boolean includeOwner, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPagesCount(
			groupId, nodeId, head, userId, includeOwner, status);
	}

	public static int getPagesCount(
			long groupId, long userId, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPagesCount(groupId, userId, nodeId, status);
	}

	public static String getPagesRSS(
			long nodeId, String title, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPagesRSS(
			nodeId, title, max, type, version, displayStyle, feedURL, entryURL,
			attachmentURLPrefix, locale);
	}

	public static java.util.List<com.liferay.wiki.model.WikiPage>
			getRecentChanges(long groupId, long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecentChanges(groupId, nodeId, start, end);
	}

	public static int getRecentChangesCount(long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRecentChangesCount(groupId, nodeId);
	}

	public static String[] getTempFileNames(long nodeId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTempFileNames(nodeId, folderName);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			movePageAttachmentToTrash(
				long nodeId, String title, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().movePageAttachmentToTrash(nodeId, title, fileName);
	}

	public static com.liferay.wiki.model.WikiPage movePageToTrash(
			long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().movePageToTrash(nodeId, title);
	}

	public static com.liferay.wiki.model.WikiPage movePageToTrash(
			long nodeId, String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().movePageToTrash(nodeId, title, version);
	}

	public static void renamePage(
			long nodeId, String title, String newTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().renamePage(nodeId, title, newTitle, serviceContext);
	}

	public static void restorePageAttachmentFromTrash(
			long nodeId, String title, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().restorePageAttachmentFromTrash(nodeId, title, fileName);
	}

	public static void restorePageFromTrash(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().restorePageFromTrash(resourcePrimKey);
	}

	public static com.liferay.wiki.model.WikiPage revertPage(
			long nodeId, String title, double version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().revertPage(nodeId, title, version, serviceContext);
	}

	public static void subscribePage(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().subscribePage(nodeId, title);
	}

	public static void unsubscribePage(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unsubscribePage(nodeId, title);
	}

	public static com.liferay.wiki.model.WikiPage updatePage(
			long nodeId, String title, double version, String content,
			String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updatePage(
			nodeId, title, version, content, summary, minorEdit, format,
			parentTitle, redirectTitle, serviceContext);
	}

	public static WikiPageService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<WikiPageService, WikiPageService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(WikiPageService.class);

		ServiceTracker<WikiPageService, WikiPageService> serviceTracker =
			new ServiceTracker<WikiPageService, WikiPageService>(
				bundle.getBundleContext(), WikiPageService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
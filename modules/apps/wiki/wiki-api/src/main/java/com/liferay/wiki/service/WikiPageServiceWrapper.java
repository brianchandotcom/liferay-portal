/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.wiki.model.WikiPage;

/**
 * Provides a wrapper for {@link WikiPageService}.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageService
 * @generated
 */
public class WikiPageServiceWrapper
	implements ServiceWrapper<WikiPageService>, WikiPageService {

	public WikiPageServiceWrapper() {
		this(null);
	}

	public WikiPageServiceWrapper(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	@Override
	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.addPage(
			nodeId, title, content, summary, minorEdit, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #addPage(String,
	 long, String, String, String, boolean, String, String,
	 String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, String format, String parentTitle,
			String redirectTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.addPage(
			nodeId, title, content, summary, minorEdit, format, parentTitle,
			redirectTitle, serviceContext);
	}

	@Override
	public WikiPage addPage(
			String externalReferenceCode, long nodeId, String title,
			double version, String content, String summary, boolean minorEdit,
			String format, boolean head, String parentTitle,
			String redirectTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.addPage(
			externalReferenceCode, nodeId, title, version, content, summary,
			minorEdit, format, head, parentTitle, redirectTitle,
			serviceContext);
	}

	@Override
	public WikiPage addPage(
			String externalReferenceCode, long nodeId, String title,
			String content, String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.addPage(
			externalReferenceCode, nodeId, title, content, summary, minorEdit,
			format, parentTitle, redirectTitle, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			addPageAttachment(
				long nodeId, String title, String fileName, java.io.File file,
				String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.addPageAttachment(
			nodeId, title, fileName, file, mimeType);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			addPageAttachment(
				long nodeId, String title, String fileName,
				java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.addPageAttachment(
			nodeId, title, fileName, inputStream, mimeType);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			addPageAttachments(
				long nodeId, String title,
				java.util.List
					<com.liferay.portal.kernel.util.ObjectValuePair
						<String, java.io.InputStream>> inputStreamOVPs)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.addPageAttachments(
			nodeId, title, inputStreamOVPs);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			addTempFileEntry(
				long nodeId, String folderName, String fileName,
				java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.addTempFileEntry(
			nodeId, folderName, fileName, inputStream, mimeType);
	}

	@Override
	public void changeParent(
			long nodeId, String title, String newParentTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.changeParent(
			nodeId, title, newParentTitle, serviceContext);
	}

	@Override
	public void copyPageAttachments(
			long templateNodeId, String templateTitle, long nodeId,
			String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.copyPageAttachments(
			templateNodeId, templateTitle, nodeId, title);
	}

	@Override
	public void deletePage(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.deletePage(nodeId, title);
	}

	@Override
	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.deletePageAttachment(nodeId, title, fileName);
	}

	@Override
	public void deletePageAttachments(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.deletePageAttachments(nodeId, title);
	}

	@Override
	public void deleteTempFileEntry(
			long nodeId, String folderName, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.deleteTempFileEntry(nodeId, folderName, fileName);
	}

	@Override
	public void deleteTrashPageAttachments(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.deleteTrashPageAttachments(nodeId, title);
	}

	@Override
	public void discardDraft(long nodeId, String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.discardDraft(nodeId, title, version);
	}

	/**
	 * Returns the latest wiki page matching the group and the external
	 * reference code
	 *
	 * @param groupId the primary key of the group
	 * @param externalReferenceCode the wiki page external reference code
	 * @return the latest matching wiki page, or <code>null</code> if no
	 matching wiki page could be found
	 */
	@Override
	public WikiPage fetchLatestPageByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.fetchLatestPageByExternalReferenceCode(
			groupId, externalReferenceCode);
	}

	@Override
	public WikiPage fetchPage(long nodeId, String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.fetchPage(nodeId, title, version);
	}

	@Override
	public java.util.List<WikiPage> getChildren(
			long groupId, long nodeId, boolean head, String parentTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getChildren(groupId, nodeId, head, parentTitle);
	}

	@Override
	public WikiPage getDraftPage(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getDraftPage(nodeId, title);
	}

	/**
	 * Returns the latest wiki page matching the group and the external
	 * reference code
	 *
	 * @param groupId the primary key of the group
	 * @param externalReferenceCode the wiki page external reference code
	 * @return the latest matching wiki page
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public WikiPage getLatestPageByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getLatestPageByExternalReferenceCode(
			groupId, externalReferenceCode);
	}

	@Override
	public java.util.List<WikiPage> getNodePages(long nodeId, int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getNodePages(nodeId, max);
	}

	@Override
	public String getNodePagesRSS(
			long nodeId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getNodePagesRSS(
			nodeId, max, type, version, displayStyle, feedURL, entryURL,
			attachmentURLPrefix);
	}

	@Override
	public java.util.List<WikiPage> getOrphans(
			com.liferay.wiki.model.WikiNode node)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getOrphans(node);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _wikiPageService.getOSGiServiceIdentifier();
	}

	@Override
	public WikiPage getPage(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPage(pageId);
	}

	@Override
	public WikiPage getPage(long groupId, long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPage(groupId, nodeId, title);
	}

	@Override
	public WikiPage getPage(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPage(nodeId, title);
	}

	@Override
	public WikiPage getPage(long nodeId, String title, Boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPage(nodeId, title, head);
	}

	@Override
	public WikiPage getPage(long nodeId, String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPage(nodeId, title, version);
	}

	@Override
	public WikiPage getPageByPageId(long pageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPageByPageId(pageId);
	}

	@Override
	public java.util.List<WikiPage> getPages(
			long groupId, long nodeId, boolean head, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPages(
			groupId, nodeId, head, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<WikiPage> getPages(
			long groupId, long nodeId, boolean head, long userId,
			boolean includeOwner, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<WikiPage>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPages(
			groupId, nodeId, head, userId, includeOwner, status, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<WikiPage> getPages(
			long groupId, long userId, long nodeId, int status, int start,
			int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPages(
			groupId, userId, nodeId, status, start, end);
	}

	@Override
	public int getPagesCount(long groupId, long nodeId, boolean head)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPagesCount(groupId, nodeId, head);
	}

	@Override
	public int getPagesCount(
			long groupId, long nodeId, boolean head, long userId,
			boolean includeOwner, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPagesCount(
			groupId, nodeId, head, userId, includeOwner, status);
	}

	@Override
	public int getPagesCount(long groupId, long userId, long nodeId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPagesCount(groupId, userId, nodeId, status);
	}

	@Override
	public String getPagesRSS(
			long nodeId, String title, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getPagesRSS(
			nodeId, title, max, type, version, displayStyle, feedURL, entryURL,
			attachmentURLPrefix, locale);
	}

	@Override
	public java.util.List<WikiPage> getRecentChanges(
			long groupId, long nodeId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getRecentChanges(groupId, nodeId, start, end);
	}

	@Override
	public int getRecentChangesCount(long groupId, long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getRecentChangesCount(groupId, nodeId);
	}

	@Override
	public String[] getTempFileNames(long nodeId, String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.getTempFileNames(nodeId, folderName);
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			movePageAttachmentToTrash(
				long nodeId, String title, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.movePageAttachmentToTrash(
			nodeId, title, fileName);
	}

	@Override
	public WikiPage movePageToTrash(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.movePageToTrash(nodeId, title);
	}

	@Override
	public WikiPage movePageToTrash(long nodeId, String title, double version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.movePageToTrash(nodeId, title, version);
	}

	@Override
	public void renamePage(
			long nodeId, String title, String newTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.renamePage(nodeId, title, newTitle, serviceContext);
	}

	@Override
	public void restorePageAttachmentFromTrash(
			long nodeId, String title, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.restorePageAttachmentFromTrash(
			nodeId, title, fileName);
	}

	@Override
	public void restorePageFromTrash(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.restorePageFromTrash(resourcePrimKey);
	}

	@Override
	public WikiPage revertPage(
			long nodeId, String title, double version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.revertPage(
			nodeId, title, version, serviceContext);
	}

	@Override
	public void subscribePage(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.subscribePage(nodeId, title);
	}

	@Override
	public void unsubscribePage(long nodeId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		_wikiPageService.unsubscribePage(nodeId, title);
	}

	@Override
	public WikiPage updatePage(
			long nodeId, String title, double version, String content,
			String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _wikiPageService.updatePage(
			nodeId, title, version, content, summary, minorEdit, format,
			parentTitle, redirectTitle, serviceContext);
	}

	@Override
	public WikiPageService getWrappedService() {
		return _wikiPageService;
	}

	@Override
	public void setWrappedService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	private WikiPageService _wikiPageService;

}
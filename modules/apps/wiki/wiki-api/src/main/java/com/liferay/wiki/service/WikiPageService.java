/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.service;

import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
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

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for WikiPage. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface WikiPageService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.wiki.service.impl.WikiPageServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the wiki page remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link WikiPageServiceUtil} if injection and service tracking are not available.
	 */
	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #addPage(String,
	 long, String, String, String, boolean, String, String,
	 String, ServiceContext)}
	 */
	@Deprecated
	public WikiPage addPage(
			long nodeId, String title, String content, String summary,
			boolean minorEdit, String format, String parentTitle,
			String redirectTitle, ServiceContext serviceContext)
		throws PortalException;

	public WikiPage addPage(
			String externalReferenceCode, long nodeId, String title,
			double version, String content, String summary, boolean minorEdit,
			String format, boolean head, String parentTitle,
			String redirectTitle, ServiceContext serviceContext)
		throws PortalException;

	public WikiPage addPage(
			String externalReferenceCode, long nodeId, String title,
			String content, String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException;

	public FileEntry addPageAttachment(
			long nodeId, String title, String fileName, File file,
			String mimeType)
		throws PortalException;

	public FileEntry addPageAttachment(
			long nodeId, String title, String fileName, InputStream inputStream,
			String mimeType)
		throws PortalException;

	public List<FileEntry> addPageAttachments(
			long nodeId, String title,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException;

	public FileEntry addTempFileEntry(
			long nodeId, String folderName, String fileName,
			InputStream inputStream, String mimeType)
		throws PortalException;

	public void changeParent(
			long nodeId, String title, String newParentTitle,
			ServiceContext serviceContext)
		throws PortalException;

	public void copyPageAttachments(
			long templateNodeId, String templateTitle, long nodeId,
			String title)
		throws PortalException;

	public void deletePage(long nodeId, String title) throws PortalException;

	public void deletePageAttachment(long nodeId, String title, String fileName)
		throws PortalException;

	public void deletePageAttachments(long nodeId, String title)
		throws PortalException;

	public void deleteTempFileEntry(
			long nodeId, String folderName, String fileName)
		throws PortalException;

	public void deleteTrashPageAttachments(long nodeId, String title)
		throws PortalException;

	public void discardDraft(long nodeId, String title, double version)
		throws PortalException;

	/**
	 * Returns the latest wiki page matching the group and the external
	 * reference code
	 *
	 * @param groupId the primary key of the group
	 * @param externalReferenceCode the wiki page external reference code
	 * @return the latest matching wiki page, or <code>null</code> if no
	 matching wiki page could be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchLatestPageByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage fetchPage(long nodeId, String title, double version)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getChildren(
			long groupId, long nodeId, boolean head, String parentTitle)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getDraftPage(long nodeId, String title)
		throws PortalException;

	/**
	 * Returns the latest wiki page matching the group and the external
	 * reference code
	 *
	 * @param groupId the primary key of the group
	 * @param externalReferenceCode the wiki page external reference code
	 * @return the latest matching wiki page
	 * @throws PortalException if a portal exception occurred
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getLatestPageByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getNodePages(long nodeId, int max)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getNodePagesRSS(
			long nodeId, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getOrphans(WikiNode node) throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long pageId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long groupId, long nodeId, String title)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long nodeId, String title) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long nodeId, String title, Boolean head)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPage(long nodeId, String title, double version)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WikiPage getPageByPageId(long pageId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(
			long groupId, long nodeId, boolean head, int status, int start,
			int end, OrderByComparator<WikiPage> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(
			long groupId, long nodeId, boolean head, long userId,
			boolean includeOwner, int status, int start, int end,
			OrderByComparator<WikiPage> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getPages(
			long groupId, long userId, long nodeId, int status, int start,
			int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long groupId, long nodeId, boolean head)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(
			long groupId, long nodeId, boolean head, long userId,
			boolean includeOwner, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPagesCount(long groupId, long userId, long nodeId, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getPagesRSS(
			long nodeId, String title, int max, String type, double version,
			String displayStyle, String feedURL, String entryURL,
			String attachmentURLPrefix, Locale locale)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WikiPage> getRecentChanges(
			long groupId, long nodeId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getRecentChangesCount(long groupId, long nodeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String[] getTempFileNames(long nodeId, String folderName)
		throws PortalException;

	public FileEntry movePageAttachmentToTrash(
			long nodeId, String title, String fileName)
		throws PortalException;

	public WikiPage movePageToTrash(long nodeId, String title)
		throws PortalException;

	public WikiPage movePageToTrash(long nodeId, String title, double version)
		throws PortalException;

	public void renamePage(
			long nodeId, String title, String newTitle,
			ServiceContext serviceContext)
		throws PortalException;

	public void restorePageAttachmentFromTrash(
			long nodeId, String title, String fileName)
		throws PortalException;

	public void restorePageFromTrash(long resourcePrimKey)
		throws PortalException;

	public WikiPage revertPage(
			long nodeId, String title, double version,
			ServiceContext serviceContext)
		throws PortalException;

	public void subscribePage(long nodeId, String title) throws PortalException;

	public void unsubscribePage(long nodeId, String title)
		throws PortalException;

	public WikiPage updatePage(
			long nodeId, String title, double version, String content,
			String summary, boolean minorEdit, String format,
			String parentTitle, String redirectTitle,
			ServiceContext serviceContext)
		throws PortalException;

}
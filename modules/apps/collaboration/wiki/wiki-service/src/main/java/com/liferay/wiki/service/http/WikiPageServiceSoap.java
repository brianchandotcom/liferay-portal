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

package com.liferay.wiki.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import com.liferay.wiki.service.WikiPageServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link WikiPageServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.wiki.model.WikiPageSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.wiki.model.WikiPage}, that is translated to a
 * {@link com.liferay.wiki.model.WikiPageSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageServiceHttp
 * @see com.liferay.wiki.model.WikiPageSoap
 * @see WikiPageServiceUtil
 * @generated
 */
@ProviderType
public class WikiPageServiceSoap {
	public static com.liferay.wiki.model.WikiPageSoap addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.addPage(nodeId,
					title, content, summary, minorEdit, serviceContext);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap addPage(long nodeId,
		java.lang.String title, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.addPage(nodeId,
					title, content, summary, minorEdit, format, parentTitle,
					redirectTitle, serviceContext);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntrySoap[] addPageAttachments(
		long nodeId, java.lang.String title,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.String, java.io.InputStream>> inputStreamOVPs)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> returnValue =
				WikiPageServiceUtil.addPageAttachments(nodeId, title,
					inputStreamOVPs);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void changeParent(long nodeId, java.lang.String title,
		java.lang.String newParentTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			WikiPageServiceUtil.changeParent(nodeId, title, newParentTitle,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void copyPageAttachments(long templateNodeId,
		java.lang.String templateTitle, long nodeId, java.lang.String title)
		throws RemoteException {
		try {
			WikiPageServiceUtil.copyPageAttachments(templateNodeId,
				templateTitle, nodeId, title);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deletePage(long nodeId, java.lang.String title)
		throws RemoteException {
		try {
			WikiPageServiceUtil.deletePage(nodeId, title);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deletePageAttachment(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws RemoteException {
		try {
			WikiPageServiceUtil.deletePageAttachment(nodeId, title, fileName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deletePageAttachments(long nodeId, java.lang.String title)
		throws RemoteException {
		try {
			WikiPageServiceUtil.deletePageAttachments(nodeId, title);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTempFileEntry(long nodeId,
		java.lang.String folderName, java.lang.String fileName)
		throws RemoteException {
		try {
			WikiPageServiceUtil.deleteTempFileEntry(nodeId, folderName, fileName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTrashPageAttachments(long nodeId,
		java.lang.String title) throws RemoteException {
		try {
			WikiPageServiceUtil.deleteTrashPageAttachments(nodeId, title);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void discardDraft(long nodeId, java.lang.String title,
		double version) throws RemoteException {
		try {
			WikiPageServiceUtil.discardDraft(nodeId, title, version);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap fetchPage(long nodeId,
		java.lang.String title, double version) throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.fetchPage(nodeId,
					title, version);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap[] getChildren(
		long groupId, long nodeId, boolean head, java.lang.String parentTitle)
		throws RemoteException {
		try {
			java.util.List<com.liferay.wiki.model.WikiPage> returnValue = WikiPageServiceUtil.getChildren(groupId,
					nodeId, head, parentTitle);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap getDraftPage(
		long nodeId, java.lang.String title) throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.getDraftPage(nodeId,
					title);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap[] getNodePages(
		long nodeId, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.wiki.model.WikiPage> returnValue = WikiPageServiceUtil.getNodePages(nodeId,
					max);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getNodePagesRSS(long nodeId, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		java.lang.String attachmentURLPrefix) throws RemoteException {
		try {
			java.lang.String returnValue = WikiPageServiceUtil.getNodePagesRSS(nodeId,
					max, type, version, displayStyle, feedURL, entryURL,
					attachmentURLPrefix);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of 2.0.0, replaced by {@link #getOrphans(WikiNode)}
	*/
	@Deprecated
	public static com.liferay.wiki.model.WikiPageSoap[] getOrphans(
		long groupId, long nodeId) throws RemoteException {
		try {
			java.util.List<com.liferay.wiki.model.WikiPage> returnValue = WikiPageServiceUtil.getOrphans(groupId,
					nodeId);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap[] getOrphans(
		com.liferay.wiki.model.WikiNodeSoap node) throws RemoteException {
		try {
			java.util.List<com.liferay.wiki.model.WikiPage> returnValue = WikiPageServiceUtil.getOrphans(com.liferay.wiki.model.impl.WikiNodeModelImpl.toModel(
						node));

			return com.liferay.wiki.model.WikiPageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap getPage(long pageId)
		throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.getPage(pageId);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap getPage(long groupId,
		long nodeId, java.lang.String title) throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.getPage(groupId,
					nodeId, title);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap getPage(long nodeId,
		java.lang.String title) throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.getPage(nodeId,
					title);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap getPage(long nodeId,
		java.lang.String title, java.lang.Boolean head)
		throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.getPage(nodeId,
					title, head);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap getPage(long nodeId,
		java.lang.String title, double version) throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.getPage(nodeId,
					title, version);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap[] getPages(long groupId,
		long nodeId, boolean head, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiPage> obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.wiki.model.WikiPage> returnValue = WikiPageServiceUtil.getPages(groupId,
					nodeId, head, status, start, end, obc);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap[] getPages(long groupId,
		long nodeId, boolean head, long userId, boolean includeOwner,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiPage> obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.wiki.model.WikiPage> returnValue = WikiPageServiceUtil.getPages(groupId,
					nodeId, head, userId, includeOwner, status, start, end, obc);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap[] getPages(long groupId,
		long userId, long nodeId, int status, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.wiki.model.WikiPage> returnValue = WikiPageServiceUtil.getPages(groupId,
					userId, nodeId, status, start, end);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getPagesCount(long groupId, long nodeId, boolean head)
		throws RemoteException {
		try {
			int returnValue = WikiPageServiceUtil.getPagesCount(groupId,
					nodeId, head);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getPagesCount(long groupId, long nodeId, boolean head,
		long userId, boolean includeOwner, int status)
		throws RemoteException {
		try {
			int returnValue = WikiPageServiceUtil.getPagesCount(groupId,
					nodeId, head, userId, includeOwner, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getPagesCount(long groupId, long userId, long nodeId,
		int status) throws RemoteException {
		try {
			int returnValue = WikiPageServiceUtil.getPagesCount(groupId,
					userId, nodeId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getPagesRSS(long nodeId,
		java.lang.String title, int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String entryURL, java.lang.String attachmentURLPrefix,
		String locale) throws RemoteException {
		try {
			java.lang.String returnValue = WikiPageServiceUtil.getPagesRSS(nodeId,
					title, max, type, version, displayStyle, feedURL, entryURL,
					attachmentURLPrefix, LocaleUtil.fromLanguageId(locale));

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap[] getRecentChanges(
		long groupId, long nodeId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.wiki.model.WikiPage> returnValue = WikiPageServiceUtil.getRecentChanges(groupId,
					nodeId, start, end);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getRecentChangesCount(long groupId, long nodeId)
		throws RemoteException {
		try {
			int returnValue = WikiPageServiceUtil.getRecentChangesCount(groupId,
					nodeId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String[] getTempFileNames(long nodeId,
		java.lang.String folderName) throws RemoteException {
		try {
			java.lang.String[] returnValue = WikiPageServiceUtil.getTempFileNames(nodeId,
					folderName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntrySoap movePageAttachmentToTrash(
		long nodeId, java.lang.String title, java.lang.String fileName)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue = WikiPageServiceUtil.movePageAttachmentToTrash(nodeId,
					title, fileName);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap movePageToTrash(
		long nodeId, java.lang.String title) throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.movePageToTrash(nodeId,
					title);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap movePageToTrash(
		long nodeId, java.lang.String title, double version)
		throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.movePageToTrash(nodeId,
					title, version);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void renamePage(long nodeId, java.lang.String title,
		java.lang.String newTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			WikiPageServiceUtil.renamePage(nodeId, title, newTitle,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void restorePageAttachmentFromTrash(long nodeId,
		java.lang.String title, java.lang.String fileName)
		throws RemoteException {
		try {
			WikiPageServiceUtil.restorePageAttachmentFromTrash(nodeId, title,
				fileName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void restorePageFromTrash(long resourcePrimKey)
		throws RemoteException {
		try {
			WikiPageServiceUtil.restorePageFromTrash(resourcePrimKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap revertPage(long nodeId,
		java.lang.String title, double version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.revertPage(nodeId,
					title, version, serviceContext);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void subscribePage(long nodeId, java.lang.String title)
		throws RemoteException {
		try {
			WikiPageServiceUtil.subscribePage(nodeId, title);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsubscribePage(long nodeId, java.lang.String title)
		throws RemoteException {
		try {
			WikiPageServiceUtil.unsubscribePage(nodeId, title);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.wiki.model.WikiPageSoap updatePage(long nodeId,
		java.lang.String title, double version, java.lang.String content,
		java.lang.String summary, boolean minorEdit, java.lang.String format,
		java.lang.String parentTitle, java.lang.String redirectTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.wiki.model.WikiPage returnValue = WikiPageServiceUtil.updatePage(nodeId,
					title, version, content, summary, minorEdit, format,
					parentTitle, redirectTitle, serviceContext);

			return com.liferay.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WikiPageServiceSoap.class);
}
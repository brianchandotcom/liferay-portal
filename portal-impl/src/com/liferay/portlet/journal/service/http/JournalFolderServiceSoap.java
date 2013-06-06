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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import com.liferay.portlet.journal.service.JournalFolderServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.journal.service.JournalFolderServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.journal.model.JournalFolderSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.journal.model.JournalFolder}, that is translated to a
 * {@link com.liferay.portlet.journal.model.JournalFolderSoap}. Methods that SOAP cannot
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
 * @see JournalFolderServiceHttp
 * @see com.liferay.portlet.journal.model.JournalFolderSoap
 * @see com.liferay.portlet.journal.service.JournalFolderServiceUtil
 * @generated
 */
public class JournalFolderServiceSoap {
	public static com.liferay.portlet.journal.model.JournalFolderSoap addFolder(
		long groupId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFolder returnValue = JournalFolderServiceUtil.addFolder(groupId,
					parentFolderId, name, description, serviceContext);

			return com.liferay.portlet.journal.model.JournalFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long folderId) throws RemoteException {
		try {
			JournalFolderServiceUtil.deleteFolder(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws RemoteException {
		try {
			JournalFolderServiceUtil.deleteFolder(folderId,
				includeTrashedEntries);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFolderSoap getFolder(
		long folderId) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFolder returnValue = JournalFolderServiceUtil.getFolder(folderId);

			return com.liferay.portlet.journal.model.JournalFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Long[] getFolderIds(long groupId, long folderId)
		throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = JournalFolderServiceUtil.getFolderIds(groupId,
					folderId);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFolderSoap[] getFolders(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalFolder> returnValue =
				JournalFolderServiceUtil.getFolders(groupId);

			return com.liferay.portlet.journal.model.JournalFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFolderSoap[] getFolders(
		long groupId, long parentFolderId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalFolder> returnValue =
				JournalFolderServiceUtil.getFolders(groupId, parentFolderId);

			return com.liferay.portlet.journal.model.JournalFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFolderSoap[] getFolders(
		long groupId, long parentFolderId, int status)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalFolder> returnValue =
				JournalFolderServiceUtil.getFolders(groupId, parentFolderId,
					status);

			return com.liferay.portlet.journal.model.JournalFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFolderSoap[] getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalFolder> returnValue =
				JournalFolderServiceUtil.getFolders(groupId, parentFolderId,
					start, end);

			return com.liferay.portlet.journal.model.JournalFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFolderSoap[] getFolders(
		long groupId, long parentFolderId, int status, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.journal.model.JournalFolder> returnValue =
				JournalFolderServiceUtil.getFolders(groupId, parentFolderId,
					status, start, end);

			return com.liferay.portlet.journal.model.JournalFolderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndArticlesCount(long groupId,
		Long[] folderIds, int status) throws RemoteException {
		try {
			int returnValue = JournalFolderServiceUtil.getFoldersAndArticlesCount(groupId,
					ListUtil.toList(folderIds), status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndArticlesCount(long groupId, long folderId)
		throws RemoteException {
		try {
			int returnValue = JournalFolderServiceUtil.getFoldersAndArticlesCount(groupId,
					folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndArticlesCount(long groupId, long folderId,
		int status) throws RemoteException {
		try {
			int returnValue = JournalFolderServiceUtil.getFoldersAndArticlesCount(groupId,
					folderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersCount(long groupId, long parentFolderId)
		throws RemoteException {
		try {
			int returnValue = JournalFolderServiceUtil.getFoldersCount(groupId,
					parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void getSubfolderIds(Long[] folderIds, long groupId,
		long folderId) throws RemoteException {
		try {
			JournalFolderServiceUtil.getSubfolderIds(ListUtil.toList(folderIds),
				groupId, folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Long[] getSubfolderIds(long groupId, long folderId,
		boolean recurse) throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = JournalFolderServiceUtil.getSubfolderIds(groupId,
					folderId, recurse);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFolderSoap moveFolder(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFolder returnValue = JournalFolderServiceUtil.moveFolder(folderId,
					parentFolderId, serviceContext);

			return com.liferay.portlet.journal.model.JournalFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFolderSoap moveFolderFromTrash(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFolder returnValue = JournalFolderServiceUtil.moveFolderFromTrash(folderId,
					parentFolderId, serviceContext);

			return com.liferay.portlet.journal.model.JournalFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void moveFolderToTrash(long folderId)
		throws RemoteException {
		try {
			JournalFolderServiceUtil.moveFolderToTrash(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void restoreFolderFromTrash(long folderId)
		throws RemoteException {
		try {
			JournalFolderServiceUtil.restoreFolderFromTrash(folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFolderSoap updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFolder returnValue = JournalFolderServiceUtil.updateFolder(folderId,
					parentFolderId, name, description, mergeWithParentFolder,
					serviceContext);

			return com.liferay.portlet.journal.model.JournalFolderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JournalFolderServiceSoap.class);
}
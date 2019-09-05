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

package com.liferay.journal.service.http;

import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>JournalFolderServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.journal.model.JournalFolderSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.journal.model.JournalFolder</code>, that is translated to a
 * <code>com.liferay.journal.model.JournalFolderSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
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
 * @generated
 */
public class JournalFolderServiceSoap {

	public static com.liferay.journal.model.JournalFolderSoap addFolder(
			long groupId, long parentFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFolder returnValue =
				JournalFolderServiceUtil.addFolder(
					groupId, parentFolderId, name, description, serviceContext);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModel(
				returnValue);
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

	public static void deleteFolder(
			long folderId, boolean includeTrashedEntries)
		throws RemoteException {

		try {
			JournalFolderServiceUtil.deleteFolder(
				folderId, includeTrashedEntries);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap fetchFolder(
			long folderId)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFolder returnValue =
				JournalFolderServiceUtil.fetchFolder(folderId);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap[]
			getDDMStructures(
				long[] groupIds, long folderId, int restrictionType)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
				returnValue = JournalFolderServiceUtil.getDDMStructures(
					groupIds, folderId, restrictionType);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap getFolder(
			long folderId)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFolder returnValue =
				JournalFolderServiceUtil.getFolder(folderId);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static Long[] getFolderIds(long groupId, long folderId)
		throws RemoteException {

		try {
			java.util.List<Long> returnValue =
				JournalFolderServiceUtil.getFolderIds(groupId, folderId);

			return returnValue.toArray(new Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap[] getFolders(
			long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalFolder>
				returnValue = JournalFolderServiceUtil.getFolders(groupId);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap[] getFolders(
			long groupId, long parentFolderId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalFolder>
				returnValue = JournalFolderServiceUtil.getFolders(
					groupId, parentFolderId);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap[] getFolders(
			long groupId, long parentFolderId, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalFolder>
				returnValue = JournalFolderServiceUtil.getFolders(
					groupId, parentFolderId, status);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap[] getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalFolder>
				returnValue = JournalFolderServiceUtil.getFolders(
					groupId, parentFolderId, start, end);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap[] getFolders(
			long groupId, long parentFolderId, int status, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.journal.model.JournalFolder>
				returnValue = JournalFolderServiceUtil.getFolders(
					groupId, parentFolderId, status, start, end);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndArticlesCount(
			long groupId, Long[] folderIds, int status)
		throws RemoteException {

		try {
			int returnValue =
				JournalFolderServiceUtil.getFoldersAndArticlesCount(
					groupId, ListUtil.toList(folderIds), status);

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
			int returnValue =
				JournalFolderServiceUtil.getFoldersAndArticlesCount(
					groupId, folderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndArticlesCount(
			long groupId, long folderId, int status)
		throws RemoteException {

		try {
			int returnValue =
				JournalFolderServiceUtil.getFoldersAndArticlesCount(
					groupId, folderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersAndArticlesCount(
			long groupId, long userId, long folderId, int status)
		throws RemoteException {

		try {
			int returnValue =
				JournalFolderServiceUtil.getFoldersAndArticlesCount(
					groupId, userId, folderId, status);

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
			int returnValue = JournalFolderServiceUtil.getFoldersCount(
				groupId, parentFolderId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getFoldersCount(
			long groupId, long parentFolderId, int status)
		throws RemoteException {

		try {
			int returnValue = JournalFolderServiceUtil.getFoldersCount(
				groupId, parentFolderId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #getSubfolderIds(List, long, long, boolean)}
	 */
	@Deprecated
	public static void getSubfolderIds(
			Long[] folderIds, long groupId, long folderId)
		throws RemoteException {

		try {
			JournalFolderServiceUtil.getSubfolderIds(
				ListUtil.toList(folderIds), groupId, folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void getSubfolderIds(
			Long[] folderIds, long groupId, long folderId, boolean recurse)
		throws RemoteException {

		try {
			JournalFolderServiceUtil.getSubfolderIds(
				ListUtil.toList(folderIds), groupId, folderId, recurse);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static Long[] getSubfolderIds(
			long groupId, long folderId, boolean recurse)
		throws RemoteException {

		try {
			java.util.List<Long> returnValue =
				JournalFolderServiceUtil.getSubfolderIds(
					groupId, folderId, recurse);

			return returnValue.toArray(new Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap moveFolder(
			long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFolder returnValue =
				JournalFolderServiceUtil.moveFolder(
					folderId, parentFolderId, serviceContext);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap
			moveFolderFromTrash(
				long folderId, long parentFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFolder returnValue =
				JournalFolderServiceUtil.moveFolderFromTrash(
					folderId, parentFolderId, serviceContext);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap moveFolderToTrash(
			long folderId)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFolder returnValue =
				JournalFolderServiceUtil.moveFolderToTrash(folderId);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModel(
				returnValue);
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

	public static com.liferay.dynamic.data.mapping.model.DDMStructureSoap[]
			searchDDMStructures(
				long companyId, long[] groupIds, long folderId,
				int restrictionType, String keywords, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMStructure> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure>
				returnValue = JournalFolderServiceUtil.searchDDMStructures(
					companyId, groupIds, folderId, restrictionType, keywords,
					start, end, obc);

			return com.liferay.dynamic.data.mapping.model.DDMStructureSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void subscribe(long groupId, long folderId)
		throws RemoteException {

		try {
			JournalFolderServiceUtil.subscribe(groupId, folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsubscribe(long groupId, long folderId)
		throws RemoteException {

		try {
			JournalFolderServiceUtil.unsubscribe(groupId, folderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap updateFolder(
			long groupId, long folderId, long parentFolderId, String name,
			String description, boolean mergeWithParentFolder,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFolder returnValue =
				JournalFolderServiceUtil.updateFolder(
					groupId, folderId, parentFolderId, name, description,
					mergeWithParentFolder, serviceContext);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.journal.model.JournalFolderSoap updateFolder(
			long groupId, long folderId, long parentFolderId, String name,
			String description, long[] ddmStructureIds, int restrictionType,
			boolean mergeWithParentFolder,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.journal.model.JournalFolder returnValue =
				JournalFolderServiceUtil.updateFolder(
					groupId, folderId, parentFolderId, name, description,
					ddmStructureIds, restrictionType, mergeWithParentFolder,
					serviceContext);

			return com.liferay.journal.model.JournalFolderSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalFolderServiceSoap.class);

}
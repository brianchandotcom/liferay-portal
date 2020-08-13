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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.document.library.kernel.service.DLTrashServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>DLTrashServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
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
 * @see DLTrashServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DLTrashServiceSoap {

	/**
	 * Moves the file entry from a trashed folder to the new folder.
	 *
	 * @param fileEntryId the primary key of the file entry
	 * @param newFolderId the primary key of the new folder
	 * @param serviceContext the service context to be applied
	 * @return the file entry
	 */
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap
			moveFileEntryFromTrash(
				long fileEntryId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue =
				DLTrashServiceUtil.moveFileEntryFromTrash(
					fileEntryId, newFolderId, serviceContext);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Moves the file entry with the primary key to the trash portlet.
	 *
	 * @param fileEntryId the primary key of the file entry
	 * @return the file entry
	 */
	public static com.liferay.portal.kernel.repository.model.FileEntrySoap
			moveFileEntryToTrash(long fileEntryId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.repository.model.FileEntry returnValue =
				DLTrashServiceUtil.moveFileEntryToTrash(fileEntryId);

			return com.liferay.portal.kernel.repository.model.FileEntrySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Moves the folder with the primary key from the trash portlet to the new
	 * parent folder with the primary key.
	 *
	 * @param folderId the primary key of the folder
	 * @param parentFolderId the primary key of the new parent folder
	 * @param serviceContext the service context to be applied
	 * @return the file entry
	 */
	public static com.liferay.portal.kernel.repository.model.FolderSoap
			moveFolderFromTrash(
				long folderId, long parentFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.repository.model.Folder returnValue =
				DLTrashServiceUtil.moveFolderFromTrash(
					folderId, parentFolderId, serviceContext);

			return com.liferay.portal.kernel.repository.model.FolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Moves the folder with the primary key to the trash portlet.
	 *
	 * @param folderId the primary key of the folder
	 * @return the file entry
	 */
	public static com.liferay.portal.kernel.repository.model.FolderSoap
			moveFolderToTrash(long folderId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.repository.model.Folder returnValue =
				DLTrashServiceUtil.moveFolderToTrash(folderId);

			return com.liferay.portal.kernel.repository.model.FolderSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Restores the file entry with the primary key from the trash portlet.
	 *
	 * @param fileEntryId the primary key of the file entry
	 */
	public static void restoreFileEntryFromTrash(long fileEntryId)
		throws RemoteException {

		try {
			DLTrashServiceUtil.restoreFileEntryFromTrash(fileEntryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Restores the file shortcut with the primary key from the trash portlet.
	 *
	 * @param fileShortcutId the primary key of the file shortcut
	 */
	public static void restoreFileShortcutFromTrash(long fileShortcutId)
		throws RemoteException {

		try {
			DLTrashServiceUtil.restoreFileShortcutFromTrash(fileShortcutId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * Restores the folder with the primary key from the trash portlet.
	 *
	 * @param folderId the primary key of the folder
	 */
	public static void restoreFolderFromTrash(long folderId)
		throws RemoteException {

		try {
			DLTrashServiceUtil.restoreFolderFromTrash(folderId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLTrashServiceSoap.class);

}
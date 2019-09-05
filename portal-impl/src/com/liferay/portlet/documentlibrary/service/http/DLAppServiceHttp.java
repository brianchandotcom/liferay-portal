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

import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DLAppServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLAppServiceSoap
 * @generated
 */
public class DLAppServiceHttp {

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addFileEntry(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, byte[] bytes,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addFileEntry",
				_addFileEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, sourceFileName, mimeType,
				title, description, changeLog, bytes, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addFileEntry(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, java.io.File file,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addFileEntry",
				_addFileEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, sourceFileName, mimeType,
				title, description, changeLog, file, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addFileEntry(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, java.io.InputStream is,
				long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addFileEntry",
				_addFileEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, sourceFileName, mimeType,
				title, description, changeLog, is, size, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileShortcut
			addFileShortcut(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				long toFileEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addFileShortcut",
				_addFileShortcutParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, toFileEntryId,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileShortcut)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder addFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addFolder", _addFolderParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, name, description,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addTempFileEntry(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				String folderName, String fileName, java.io.File file,
				String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addTempFileEntry",
				_addTempFileEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, folderName, fileName, file,
				mimeType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addTempFileEntry(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				String folderName, String fileName,
				java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addTempFileEntry",
				_addTempFileEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, folderName, fileName, inputStream,
				mimeType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void cancelCheckOut(
			HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "cancelCheckOut",
				_cancelCheckOutParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkInFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId, boolean majorVersion,
			String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "checkInFileEntry",
				_checkInFileEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, majorVersion, changeLog,
				serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkInFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease
				dlVersionNumberIncrease,
			String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "checkInFileEntry",
				_checkInFileEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, dlVersionNumberIncrease, changeLog,
				serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkInFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId, String lockUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "checkInFileEntry",
				_checkInFileEntryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, lockUuid, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkOutFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "checkOutFileEntry",
				_checkOutFileEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			checkOutFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId, String owner,
				long expirationTime,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "checkOutFileEntry",
				_checkOutFileEntryParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, owner, expirationTime, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder copyFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long sourceFolderId,
			long parentFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "copyFolder",
				_copyFolderParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, sourceFolderId, parentFolderId, name,
				description, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "deleteFileEntry",
				_deleteFileEntryParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFileEntryByTitle(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "deleteFileEntryByTitle",
				_deleteFileEntryByTitleParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, title);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFileShortcut(
			HttpPrincipal httpPrincipal, long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "deleteFileShortcut",
				_deleteFileShortcutParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileShortcutId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFileVersion(
			HttpPrincipal httpPrincipal, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "deleteFileVersion",
				_deleteFileVersionParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileVersionId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFileVersion(
			HttpPrincipal httpPrincipal, long fileEntryId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "deleteFileVersion",
				_deleteFileVersionParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, version);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFolder(HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "deleteFolder",
				_deleteFolderParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "deleteFolder",
				_deleteFolderParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, name);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteTempFileEntry(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String folderName, String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "deleteTempFileEntry",
				_deleteTempFileEntryParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, folderName, fileName);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.FileEntry> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				long fileEntryTypeId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, fileEntryTypeId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				long fileEntryTypeId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, fileEntryTypeId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				long fileEntryTypeId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.FileEntry> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, fileEntryTypeId, start, end,
				obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				String[] mimeTypes)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, mimeTypes);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				String[] mimeTypes, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.FileEntry> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, mimeTypes, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object> getFileEntriesAndFileShortcuts(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntriesAndFileShortcuts",
				_getFileEntriesAndFileShortcutsParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntriesAndFileShortcutsCount",
				_getFileEntriesAndFileShortcutsCountParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			int status, String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntriesAndFileShortcutsCount",
				_getFileEntriesAndFileShortcutsCountParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, mimeTypes);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesCount(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntriesCount",
				_getFileEntriesCountParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesCount(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntriesCount",
				_getFileEntriesCountParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, fileEntryTypeId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesCount(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntriesCount",
				_getFileEntriesCountParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, mimeTypes);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			getFileEntry(HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntry",
				_getFileEntryParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			getFileEntry(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntry",
				_getFileEntryParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, title);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			getFileEntryByUuidAndGroupId(
				HttpPrincipal httpPrincipal, String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntryByUuidAndGroupId",
				_getFileEntryByUuidAndGroupIdParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, uuid, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileShortcut
			getFileShortcut(HttpPrincipal httpPrincipal, long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileShortcut",
				_getFileShortcutParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileShortcutId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileShortcut)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileVersion
			getFileVersion(HttpPrincipal httpPrincipal, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileVersion",
				_getFileVersionParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileVersionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileVersion)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder getFolder(
			HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolder",
				_getFolderParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder getFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolder",
				_getFolderParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolders",
				_getFoldersParameterTypes43);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, boolean includeMountFolders)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolders",
				_getFoldersParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, boolean includeMountFolders, int start,
				int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolders",
				_getFoldersParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, includeMountFolders,
				start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, boolean includeMountFolders, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.Folder> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolders",
				_getFoldersParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, includeMountFolders,
				start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, int status, boolean includeMountFolders,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.Folder> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolders",
				_getFoldersParameterTypes47);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, status,
				includeMountFolders, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolders",
				_getFoldersParameterTypes48);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.Folder> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolders",
				_getFoldersParameterTypes49);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object>
			getFoldersAndFileEntriesAndFileShortcuts(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				int status, boolean includeMountFolders, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes50);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, includeMountFolders,
				start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object>
			getFoldersAndFileEntriesAndFileShortcuts(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				int status, boolean includeMountFolders, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes51);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, includeMountFolders,
				start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object>
			getFoldersAndFileEntriesAndFileShortcuts(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				int status, String[] mimeTypes, boolean includeMountFolders,
				boolean includeOwner, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes52);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, mimeTypes,
				includeMountFolders, includeOwner, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object>
			getFoldersAndFileEntriesAndFileShortcuts(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				int status, String[] mimeTypes, boolean includeMountFolders,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes53);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, mimeTypes,
				includeMountFolders, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes54);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			int status, String[] mimeTypes, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes55);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, mimeTypes,
				includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			int status, String[] mimeTypes, boolean includeMountFolders,
			boolean includeOwner)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes56);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, mimeTypes,
				includeMountFolders, includeOwner);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersCount(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes57);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersCount(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes58);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersCount(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes59);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, status,
				includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersFileEntriesCount(
			HttpPrincipal httpPrincipal, long repositoryId,
			java.util.List<Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFoldersFileEntriesCount",
				_getFoldersFileEntriesCountParameterTypes60);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderIds, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry>
				getGroupFileEntries(
					HttpPrincipal httpPrincipal, long groupId, long userId,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes61);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry>
				getGroupFileEntries(
					HttpPrincipal httpPrincipal, long groupId, long userId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.kernel.repository.model.FileEntry>
							obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes62);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry>
				getGroupFileEntries(
					HttpPrincipal httpPrincipal, long groupId, long userId,
					long rootFolderId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes63);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry>
				getGroupFileEntries(
					HttpPrincipal httpPrincipal, long groupId, long userId,
					long rootFolderId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.kernel.repository.model.FileEntry>
							obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes64);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry>
				getGroupFileEntries(
					HttpPrincipal httpPrincipal, long groupId, long userId,
					long rootFolderId, String[] mimeTypes, int status,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.kernel.repository.model.FileEntry>
							obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes65);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, mimeTypes, status,
				start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupFileEntriesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntriesCount",
				_getGroupFileEntriesCountParameterTypes66);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupFileEntriesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntriesCount",
				_getGroupFileEntriesCountParameterTypes67);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupFileEntriesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long rootFolderId, String[] mimeTypes, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntriesCount",
				_getGroupFileEntriesCountParameterTypes68);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, mimeTypes, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getMountFolders",
				_getMountFoldersParameterTypes69);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getMountFolders",
				_getMountFoldersParameterTypes70);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.Folder> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getMountFolders",
				_getMountFoldersParameterTypes71);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getMountFoldersCount(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getMountFoldersCount",
				_getMountFoldersCountParameterTypes72);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void getSubfolderIds(
			HttpPrincipal httpPrincipal, long repositoryId,
			java.util.List<Long> folderIds, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes73);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderIds, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Long> getSubfolderIds(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes74);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Long>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Long> getSubfolderIds(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes75);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, recurse);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Long>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String[] getTempFileNames(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getTempFileNames",
				_getTempFileNamesParameterTypes76);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, folderName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (String[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "lockFolder",
				_lockFolderParameterTypes77);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "lockFolder",
				_lockFolderParameterTypes78);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, owner, inheritable,
				expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			moveFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "moveFileEntry",
				_moveFileEntryParameterTypes79);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, newFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder moveFolder(
			HttpPrincipal httpPrincipal, long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "moveFolder",
				_moveFolderParameterTypes80);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, parentFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFileEntryLock(
			HttpPrincipal httpPrincipal, String lockUuid, long companyId,
			long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "refreshFileEntryLock",
				_refreshFileEntryLockParameterTypes81);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, lockUuid, companyId, expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFolderLock(
			HttpPrincipal httpPrincipal, String lockUuid, long companyId,
			long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "refreshFolderLock",
				_refreshFolderLockParameterTypes82);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, lockUuid, companyId, expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void revertFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "revertFileEntry",
				_revertFileEntryParameterTypes83);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, version, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long repositoryId, long creatorUserId,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "search", _searchParameterTypes84);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, creatorUserId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long repositoryId, long creatorUserId,
			long folderId, String[] mimeTypes, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "search", _searchParameterTypes85);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, creatorUserId, folderId, mimeTypes,
				status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long repositoryId,
			com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.search.SearchException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "search", _searchParameterTypes86);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, searchContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.search.SearchException) {

					throw (com.liferay.portal.kernel.search.SearchException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long repositoryId,
			com.liferay.portal.kernel.search.SearchContext searchContext,
			com.liferay.portal.kernel.search.Query query)
		throws com.liferay.portal.kernel.search.SearchException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "search", _searchParameterTypes87);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, searchContext, query);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.search.SearchException) {

					throw (com.liferay.portal.kernel.search.SearchException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void subscribeFileEntryType(
			HttpPrincipal httpPrincipal, long groupId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "subscribeFileEntryType",
				_subscribeFileEntryTypeParameterTypes88);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, fileEntryTypeId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void subscribeFolder(
			HttpPrincipal httpPrincipal, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "subscribeFolder",
				_subscribeFolderParameterTypes89);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unlockFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "unlockFolder",
				_unlockFolderParameterTypes90);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, lockUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unlockFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			String name, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "unlockFolder",
				_unlockFolderParameterTypes91);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, name, lockUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsubscribeFileEntryType(
			HttpPrincipal httpPrincipal, long groupId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "unsubscribeFileEntryType",
				_unsubscribeFileEntryTypeParameterTypes92);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, fileEntryTypeId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsubscribeFolder(
			HttpPrincipal httpPrincipal, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "unsubscribeFolder",
				_unsubscribeFolderParameterTypes93);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, boolean majorVersion,
				byte[] bytes,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes94);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, bytes, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, boolean majorVersion,
				java.io.File file,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes95);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, file, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, boolean majorVersion,
				java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes96);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, is, size, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				byte[] bytes,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes97);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, dlVersionNumberIncrease, bytes,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				java.io.File file,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes98);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, dlVersionNumberIncrease, file,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes99);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, dlVersionNumberIncrease, is, size,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntryAndCheckIn(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, boolean majorVersion,
				java.io.File file,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntryAndCheckIn",
				_updateFileEntryAndCheckInParameterTypes100);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, file, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntryAndCheckIn(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog, boolean majorVersion,
				java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntryAndCheckIn",
				_updateFileEntryAndCheckInParameterTypes101);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, majorVersion, is, size, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntryAndCheckIn(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				java.io.File file,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntryAndCheckIn",
				_updateFileEntryAndCheckInParameterTypes102);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, dlVersionNumberIncrease, file,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntryAndCheckIn(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntryAndCheckIn",
				_updateFileEntryAndCheckInParameterTypes103);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, dlVersionNumberIncrease, is, size,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileShortcut
			updateFileShortcut(
				HttpPrincipal httpPrincipal, long fileShortcutId, long folderId,
				long toFileEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileShortcut",
				_updateFileShortcutParameterTypes104);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileShortcutId, folderId, toFileEntryId,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.FileShortcut)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder
			updateFolder(
				HttpPrincipal httpPrincipal, long folderId, String name,
				String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFolder",
				_updateFolderParameterTypes105);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, name, description, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean verifyFileEntryCheckOut(
			HttpPrincipal httpPrincipal, long repositoryId, long fileEntryId,
			String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "verifyFileEntryCheckOut",
				_verifyFileEntryCheckOutParameterTypes106);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, fileEntryId, lockUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean verifyFileEntryLock(
			HttpPrincipal httpPrincipal, long repositoryId, long fileEntryId,
			String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "verifyFileEntryLock",
				_verifyFileEntryLockParameterTypes107);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, fileEntryId, lockUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean verifyInheritableLock(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "verifyInheritableLock",
				_verifyInheritableLockParameterTypes108);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, lockUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceHttp.class);

	private static final Class<?>[] _addFileEntryParameterTypes0 = new Class[] {
		long.class, long.class, String.class, String.class, String.class,
		String.class, String.class, byte[].class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addFileEntryParameterTypes1 = new Class[] {
		long.class, long.class, String.class, String.class, String.class,
		String.class, String.class, java.io.File.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addFileEntryParameterTypes2 = new Class[] {
		long.class, long.class, String.class, String.class, String.class,
		String.class, String.class, java.io.InputStream.class, long.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addFileShortcutParameterTypes3 =
		new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addFolderParameterTypes4 = new Class[] {
		long.class, long.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addTempFileEntryParameterTypes5 =
		new Class[] {
			long.class, long.class, String.class, String.class,
			java.io.File.class, String.class
		};
	private static final Class<?>[] _addTempFileEntryParameterTypes6 =
		new Class[] {
			long.class, long.class, String.class, String.class,
			java.io.InputStream.class, String.class
		};
	private static final Class<?>[] _cancelCheckOutParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _checkInFileEntryParameterTypes8 =
		new Class[] {
			long.class, boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _checkInFileEntryParameterTypes9 =
		new Class[] {
			long.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _checkInFileEntryParameterTypes10 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _checkOutFileEntryParameterTypes11 =
		new Class[] {
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _checkOutFileEntryParameterTypes12 =
		new Class[] {
			long.class, String.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _copyFolderParameterTypes13 = new Class[] {
		long.class, long.class, long.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteFileEntryParameterTypes14 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteFileEntryByTitleParameterTypes15 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _deleteFileShortcutParameterTypes16 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteFileVersionParameterTypes17 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteFileVersionParameterTypes18 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _deleteFolderParameterTypes19 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteFolderParameterTypes20 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _deleteTempFileEntryParameterTypes21 =
		new Class[] {long.class, long.class, String.class, String.class};
	private static final Class<?>[] _getFileEntriesParameterTypes22 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getFileEntriesParameterTypes23 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getFileEntriesParameterTypes24 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes25 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getFileEntriesParameterTypes26 =
		new Class[] {long.class, long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getFileEntriesParameterTypes27 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFileEntriesParameterTypes28 =
		new Class[] {long.class, long.class, String[].class};
	private static final Class<?>[] _getFileEntriesParameterTypes29 =
		new Class[] {
			long.class, long.class, String[].class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getFileEntriesAndFileShortcutsParameterTypes30 = new Class[] {
			long.class, long.class, int.class, int.class, int.class
		};
	private static final Class<?>[]
		_getFileEntriesAndFileShortcutsCountParameterTypes31 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[]
		_getFileEntriesAndFileShortcutsCountParameterTypes32 = new Class[] {
			long.class, long.class, int.class, String[].class
		};
	private static final Class<?>[] _getFileEntriesCountParameterTypes33 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getFileEntriesCountParameterTypes34 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getFileEntriesCountParameterTypes35 =
		new Class[] {long.class, long.class, String[].class};
	private static final Class<?>[] _getFileEntryParameterTypes36 =
		new Class[] {long.class};
	private static final Class<?>[] _getFileEntryParameterTypes37 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[]
		_getFileEntryByUuidAndGroupIdParameterTypes38 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _getFileShortcutParameterTypes39 =
		new Class[] {long.class};
	private static final Class<?>[] _getFileVersionParameterTypes40 =
		new Class[] {long.class};
	private static final Class<?>[] _getFolderParameterTypes41 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getFolderParameterTypes42 = new Class[] {
		long.class, long.class, String.class
	};
	private static final Class<?>[] _getFoldersParameterTypes43 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _getFoldersParameterTypes44 = new Class[] {
		long.class, long.class, boolean.class
	};
	private static final Class<?>[] _getFoldersParameterTypes45 = new Class[] {
		long.class, long.class, boolean.class, int.class, int.class
	};
	private static final Class<?>[] _getFoldersParameterTypes46 = new Class[] {
		long.class, long.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getFoldersParameterTypes47 = new Class[] {
		long.class, long.class, int.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getFoldersParameterTypes48 = new Class[] {
		long.class, long.class, int.class, int.class
	};
	private static final Class<?>[] _getFoldersParameterTypes49 = new Class[] {
		long.class, long.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes50 =
			new Class[] {
				long.class, long.class, int.class, boolean.class, int.class,
				int.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes51 =
			new Class[] {
				long.class, long.class, int.class, boolean.class, int.class,
				int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes52 =
			new Class[] {
				long.class, long.class, int.class, String[].class,
				boolean.class, boolean.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes53 =
			new Class[] {
				long.class, long.class, int.class, String[].class,
				boolean.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes54 =
			new Class[] {long.class, long.class, int.class, boolean.class};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes55 =
			new Class[] {
				long.class, long.class, int.class, String[].class, boolean.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes56 =
			new Class[] {
				long.class, long.class, int.class, String[].class,
				boolean.class, boolean.class
			};
	private static final Class<?>[] _getFoldersCountParameterTypes57 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getFoldersCountParameterTypes58 =
		new Class[] {long.class, long.class, boolean.class};
	private static final Class<?>[] _getFoldersCountParameterTypes59 =
		new Class[] {long.class, long.class, int.class, boolean.class};
	private static final Class<?>[]
		_getFoldersFileEntriesCountParameterTypes60 = new Class[] {
			long.class, java.util.List.class, int.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes61 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes62 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes63 =
		new Class[] {long.class, long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes64 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes65 =
		new Class[] {
			long.class, long.class, long.class, String[].class, int.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes66 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes67 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes68 =
		new Class[] {
			long.class, long.class, long.class, String[].class, int.class
		};
	private static final Class<?>[] _getMountFoldersParameterTypes69 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getMountFoldersParameterTypes70 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getMountFoldersParameterTypes71 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getMountFoldersCountParameterTypes72 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getSubfolderIdsParameterTypes73 =
		new Class[] {long.class, java.util.List.class, long.class};
	private static final Class<?>[] _getSubfolderIdsParameterTypes74 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getSubfolderIdsParameterTypes75 =
		new Class[] {long.class, long.class, boolean.class};
	private static final Class<?>[] _getTempFileNamesParameterTypes76 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _lockFolderParameterTypes77 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _lockFolderParameterTypes78 = new Class[] {
		long.class, long.class, String.class, boolean.class, long.class
	};
	private static final Class<?>[] _moveFileEntryParameterTypes79 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFolderParameterTypes80 = new Class[] {
		long.class, long.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _refreshFileEntryLockParameterTypes81 =
		new Class[] {String.class, long.class, long.class};
	private static final Class<?>[] _refreshFolderLockParameterTypes82 =
		new Class[] {String.class, long.class, long.class};
	private static final Class<?>[] _revertFileEntryParameterTypes83 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _searchParameterTypes84 = new Class[] {
		long.class, long.class, int.class, int.class, int.class
	};
	private static final Class<?>[] _searchParameterTypes85 = new Class[] {
		long.class, long.class, long.class, String[].class, int.class,
		int.class, int.class
	};
	private static final Class<?>[] _searchParameterTypes86 = new Class[] {
		long.class, com.liferay.portal.kernel.search.SearchContext.class
	};
	private static final Class<?>[] _searchParameterTypes87 = new Class[] {
		long.class, com.liferay.portal.kernel.search.SearchContext.class,
		com.liferay.portal.kernel.search.Query.class
	};
	private static final Class<?>[] _subscribeFileEntryTypeParameterTypes88 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _subscribeFolderParameterTypes89 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _unlockFolderParameterTypes90 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _unlockFolderParameterTypes91 =
		new Class[] {long.class, long.class, String.class, String.class};
	private static final Class<?>[] _unsubscribeFileEntryTypeParameterTypes92 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _unsubscribeFolderParameterTypes93 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _updateFileEntryParameterTypes94 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, boolean.class, byte[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes95 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, boolean.class, java.io.File.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes96 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, boolean.class, java.io.InputStream.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes97 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			byte[].class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes98 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			java.io.File.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes99 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			java.io.InputStream.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateFileEntryAndCheckInParameterTypes100 = new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, boolean.class, java.io.File.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateFileEntryAndCheckInParameterTypes101 = new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, boolean.class, java.io.InputStream.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateFileEntryAndCheckInParameterTypes102 = new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			java.io.File.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateFileEntryAndCheckInParameterTypes103 = new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			java.io.InputStream.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileShortcutParameterTypes104 =
		new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFolderParameterTypes105 =
		new Class[] {
			long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _verifyFileEntryCheckOutParameterTypes106 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _verifyFileEntryLockParameterTypes107 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _verifyInheritableLockParameterTypes108 =
		new Class[] {long.class, long.class, String.class};

}
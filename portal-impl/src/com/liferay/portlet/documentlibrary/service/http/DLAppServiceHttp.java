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
 * @generated
 */
public class DLAppServiceHttp {

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
				_addFileEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, sourceFileName, mimeType,
				title, description, changeLog, file, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addFileEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long repositoryId, long folderId, String sourceFileName,
				String mimeType, String title, String urlTitle,
				String description, String changeLog, byte[] bytes,
				java.util.Date expirationDate, java.util.Date reviewDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addFileEntry",
				_addFileEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, repositoryId, folderId,
				sourceFileName, mimeType, title, urlTitle, description,
				changeLog, bytes, expirationDate, reviewDate, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addFileEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long repositoryId, long folderId, String sourceFileName,
				String mimeType, String title, String urlTitle,
				String description, String changeLog, java.io.File file,
				java.util.Date expirationDate, java.util.Date reviewDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addFileEntry",
				_addFileEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, repositoryId, folderId,
				sourceFileName, mimeType, title, urlTitle, description,
				changeLog, file, expirationDate, reviewDate, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			addFileEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long repositoryId, long folderId, String sourceFileName,
				String mimeType, String title, String urlTitle,
				String description, String changeLog,
				java.io.InputStream inputStream, long size,
				java.util.Date expirationDate, java.util.Date reviewDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addFileEntry",
				_addFileEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, repositoryId, folderId,
				sourceFileName, mimeType, title, urlTitle, description,
				changeLog, inputStream, size, expirationDate, reviewDate,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_addFileShortcutParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, toFileEntryId,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileShortcut)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder addFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "addFolder", _addFolderParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, name, description,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_addTempFileEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, folderName, fileName, file,
				mimeType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_addTempFileEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, folderName, fileName, inputStream,
				mimeType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void cancelCheckOut(
			HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "cancelCheckOut",
				_cancelCheckOutParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.FileEntry>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				long fileEntryTypeId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.FileEntry>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, fileEntryTypeId, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry> getFileEntries(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				String[] mimeTypes, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.FileEntry>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntries",
				_getFileEntriesParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, mimeTypes, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			getFileEntryByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long groupId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntryByExternalReferenceCode",
				_getFileEntryByExternalReferenceCodeParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, externalReferenceCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			getFileEntryByFileName(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntryByFileName",
				_getFileEntryByFileNameParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, fileName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			getFileEntryByUuidAndGroupId(
				HttpPrincipal httpPrincipal, String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileEntryByUuidAndGroupId",
				_getFileEntryByUuidAndGroupIdParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, uuid, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileShortcut
			getFileShortcut(HttpPrincipal httpPrincipal, long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileShortcut",
				_getFileShortcutParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileShortcutId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileShortcut)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileVersion
			getFileVersion(HttpPrincipal httpPrincipal, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFileVersion",
				_getFileVersionParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileVersionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileVersion)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder getFolder(
			HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolder",
				_getFolderParameterTypes43);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder getFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolder",
				_getFolderParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getFoldersParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getFoldersParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getFoldersParameterTypes47);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, includeMountFolders,
				start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, boolean includeMountFolders, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.Folder>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolders",
				_getFoldersParameterTypes48);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, includeMountFolders,
				start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, int status, boolean includeMountFolders,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.Folder>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolders",
				_getFoldersParameterTypes49);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, status,
				includeMountFolders, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getFoldersParameterTypes50);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.Folder>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFolders",
				_getFoldersParameterTypes51);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes52);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, includeMountFolders,
				start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<Object>
			getFoldersAndFileEntriesAndFileShortcuts(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				int status, boolean includeMountFolders, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?>
					orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes53);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, includeMountFolders,
				start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<Object>
			getFoldersAndFileEntriesAndFileShortcuts(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				int status, String[] mimeTypes, boolean includeMountFolders,
				boolean includeOwner, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?>
					orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes54);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, mimeTypes,
				includeMountFolders, includeOwner, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<Object>
			getFoldersAndFileEntriesAndFileShortcuts(
				HttpPrincipal httpPrincipal, long repositoryId, long folderId,
				int status, String[] mimeTypes, boolean includeMountFolders,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?>
					orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes55);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, mimeTypes,
				includeMountFolders, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes56);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes57);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, mimeTypes,
				includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes58);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, status, mimeTypes,
				includeMountFolders, includeOwner);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getFoldersCount(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes59);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getFoldersCount(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes60);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getFoldersCount(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes61);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, status,
				includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getFoldersFileEntriesCount(
			HttpPrincipal httpPrincipal, long repositoryId,
			java.util.List<Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getFoldersFileEntriesCount",
				_getFoldersFileEntriesCountParameterTypes62);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderIds, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getGroupFileEntriesParameterTypes63);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry>
				getGroupFileEntries(
					HttpPrincipal httpPrincipal, long groupId, long userId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.kernel.repository.model.FileEntry>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes64);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getGroupFileEntriesParameterTypes65);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.FileEntry>
				getGroupFileEntries(
					HttpPrincipal httpPrincipal, long groupId, long userId,
					long rootFolderId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.kernel.repository.model.FileEntry>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes66);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntries",
				_getGroupFileEntriesParameterTypes67);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, mimeTypes, status,
				start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.FileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getGroupFileEntriesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntriesCount",
				_getGroupFileEntriesCountParameterTypes68);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getGroupFileEntriesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntriesCount",
				_getGroupFileEntriesCountParameterTypes69);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getGroupFileEntriesCount(
			HttpPrincipal httpPrincipal, long groupId, long userId,
			long rootFolderId, String[] mimeTypes, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getGroupFileEntriesCount",
				_getGroupFileEntriesCountParameterTypes70);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, rootFolderId, mimeTypes, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getMountFoldersParameterTypes71);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getMountFoldersParameterTypes72);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.kernel.repository.model.Folder> getMountFolders(
				HttpPrincipal httpPrincipal, long repositoryId,
				long parentFolderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.repository.model.Folder>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getMountFolders",
				_getMountFoldersParameterTypes73);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.portal.kernel.repository.model.Folder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getMountFoldersCount(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getMountFoldersCount",
				_getMountFoldersCountParameterTypes74);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void getSubfolderIds(
			HttpPrincipal httpPrincipal, long repositoryId,
			java.util.List<Long> folderIds, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes75);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderIds, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<Long> getSubfolderIds(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes76);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<Long>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<Long> getSubfolderIds(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes77);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, recurse);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<Long>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static String[] getTempFileNames(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "getTempFileNames",
				_getTempFileNamesParameterTypes78);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, folderName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (String[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "lockFolder",
				_lockFolderParameterTypes79);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			String owner, boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "lockFolder",
				_lockFolderParameterTypes80);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, owner, inheritable,
				expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_moveFileEntryParameterTypes81);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, newFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder moveFolder(
			HttpPrincipal httpPrincipal, long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "moveFolder",
				_moveFolderParameterTypes82);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, parentFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFileEntryLock(
			HttpPrincipal httpPrincipal, String lockUuid, long companyId,
			long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "refreshFileEntryLock",
				_refreshFileEntryLockParameterTypes83);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, lockUuid, companyId, expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock refreshFolderLock(
			HttpPrincipal httpPrincipal, String lockUuid, long companyId,
			long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "refreshFolderLock",
				_refreshFolderLockParameterTypes84);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, lockUuid, companyId, expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void revertFileEntry(
			HttpPrincipal httpPrincipal, long fileEntryId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "revertFileEntry",
				_revertFileEntryParameterTypes85);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, version, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long repositoryId, long creatorUserId,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "search", _searchParameterTypes86);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, creatorUserId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long repositoryId, long creatorUserId,
			long folderId, String[] mimeTypes, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "search", _searchParameterTypes87);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, creatorUserId, folderId, mimeTypes,
				status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long repositoryId,
			com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.search.SearchException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "search", _searchParameterTypes88);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, searchContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.search.SearchException) {

					throw (com.liferay.portal.kernel.search.SearchException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long repositoryId,
			com.liferay.portal.kernel.search.SearchContext searchContext,
			com.liferay.portal.kernel.search.Query query)
		throws com.liferay.portal.kernel.search.SearchException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "search", _searchParameterTypes89);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, searchContext, query);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.search.SearchException) {

					throw (com.liferay.portal.kernel.search.SearchException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void subscribeFileEntryType(
			HttpPrincipal httpPrincipal, long groupId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "subscribeFileEntryType",
				_subscribeFileEntryTypeParameterTypes90);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, fileEntryTypeId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void subscribeFolder(
			HttpPrincipal httpPrincipal, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "subscribeFolder",
				_subscribeFolderParameterTypes91);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void unlockFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "unlockFolder",
				_unlockFolderParameterTypes92);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, lockUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void unlockFolder(
			HttpPrincipal httpPrincipal, long repositoryId, long parentFolderId,
			String name, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "unlockFolder",
				_unlockFolderParameterTypes93);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, parentFolderId, name, lockUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void unsubscribeFileEntryType(
			HttpPrincipal httpPrincipal, long groupId, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "unsubscribeFileEntryType",
				_unsubscribeFileEntryTypeParameterTypes94);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, fileEntryTypeId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void unsubscribeFolder(
			HttpPrincipal httpPrincipal, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "unsubscribeFolder",
				_unsubscribeFolderParameterTypes95);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String urlTitle, String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				byte[] bytes, java.util.Date expirationDate,
				java.util.Date reviewDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes96);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				urlTitle, description, changeLog, dlVersionNumberIncrease,
				bytes, expirationDate, reviewDate, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String urlTitle, String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				java.io.File file, java.util.Date expirationDate,
				java.util.Date reviewDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes97);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				urlTitle, description, changeLog, dlVersionNumberIncrease, file,
				expirationDate, reviewDate, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntry(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String urlTitle, String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				java.io.InputStream inputStream, long size,
				java.util.Date expirationDate, java.util.Date reviewDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntry",
				_updateFileEntryParameterTypes98);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				urlTitle, description, changeLog, dlVersionNumberIncrease,
				inputStream, size, expirationDate, reviewDate, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntryAndCheckIn(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String urlTitle, String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				java.io.File file, java.util.Date expirationDate,
				java.util.Date reviewDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntryAndCheckIn",
				_updateFileEntryAndCheckInParameterTypes99);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				urlTitle, description, changeLog, dlVersionNumberIncrease, file,
				expirationDate, reviewDate, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry
			updateFileEntryAndCheckIn(
				HttpPrincipal httpPrincipal, long fileEntryId,
				String sourceFileName, String mimeType, String title,
				String urlTitle, String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				java.io.InputStream inputStream, long size,
				java.util.Date expirationDate, java.util.Date reviewDate,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "updateFileEntryAndCheckIn",
				_updateFileEntryAndCheckInParameterTypes100);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, sourceFileName, mimeType, title,
				urlTitle, description, changeLog, dlVersionNumberIncrease,
				inputStream, size, expirationDate, reviewDate, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateFileShortcutParameterTypes101);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileShortcutId, folderId, toFileEntryId,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.FileShortcut)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateFolderParameterTypes102);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, name, description, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean verifyFileEntryCheckOut(
			HttpPrincipal httpPrincipal, long repositoryId, long fileEntryId,
			String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "verifyFileEntryCheckOut",
				_verifyFileEntryCheckOutParameterTypes103);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, fileEntryId, lockUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean verifyFileEntryLock(
			HttpPrincipal httpPrincipal, long repositoryId, long fileEntryId,
			String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "verifyFileEntryLock",
				_verifyFileEntryLockParameterTypes104);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, fileEntryId, lockUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean verifyInheritableLock(
			HttpPrincipal httpPrincipal, long repositoryId, long folderId,
			String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLAppServiceUtil.class, "verifyInheritableLock",
				_verifyInheritableLockParameterTypes105);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, folderId, lockUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceHttp.class);

	private static final Class<?>[] _addFileEntryParameterTypes0 = new Class[] {
		long.class, long.class, String.class, String.class, String.class,
		String.class, String.class, java.io.File.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addFileEntryParameterTypes1 = new Class[] {
		String.class, long.class, long.class, String.class, String.class,
		String.class, String.class, String.class, String.class, byte[].class,
		java.util.Date.class, java.util.Date.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addFileEntryParameterTypes2 = new Class[] {
		String.class, long.class, long.class, String.class, String.class,
		String.class, String.class, String.class, String.class,
		java.io.File.class, java.util.Date.class, java.util.Date.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addFileEntryParameterTypes3 = new Class[] {
		String.class, long.class, long.class, String.class, String.class,
		String.class, String.class, String.class, String.class,
		java.io.InputStream.class, long.class, java.util.Date.class,
		java.util.Date.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addFileShortcutParameterTypes4 =
		new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addFolderParameterTypes5 = new Class[] {
		long.class, long.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addTempFileEntryParameterTypes6 =
		new Class[] {
			long.class, long.class, String.class, String.class,
			java.io.File.class, String.class
		};
	private static final Class<?>[] _addTempFileEntryParameterTypes7 =
		new Class[] {
			long.class, long.class, String.class, String.class,
			java.io.InputStream.class, String.class
		};
	private static final Class<?>[] _cancelCheckOutParameterTypes8 =
		new Class[] {long.class};
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
		_getFileEntryByExternalReferenceCodeParameterTypes38 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getFileEntryByFileNameParameterTypes39 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[]
		_getFileEntryByUuidAndGroupIdParameterTypes40 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _getFileShortcutParameterTypes41 =
		new Class[] {long.class};
	private static final Class<?>[] _getFileVersionParameterTypes42 =
		new Class[] {long.class};
	private static final Class<?>[] _getFolderParameterTypes43 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getFolderParameterTypes44 = new Class[] {
		long.class, long.class, String.class
	};
	private static final Class<?>[] _getFoldersParameterTypes45 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _getFoldersParameterTypes46 = new Class[] {
		long.class, long.class, boolean.class
	};
	private static final Class<?>[] _getFoldersParameterTypes47 = new Class[] {
		long.class, long.class, boolean.class, int.class, int.class
	};
	private static final Class<?>[] _getFoldersParameterTypes48 = new Class[] {
		long.class, long.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getFoldersParameterTypes49 = new Class[] {
		long.class, long.class, int.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getFoldersParameterTypes50 = new Class[] {
		long.class, long.class, int.class, int.class
	};
	private static final Class<?>[] _getFoldersParameterTypes51 = new Class[] {
		long.class, long.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes52 =
			new Class[] {
				long.class, long.class, int.class, boolean.class, int.class,
				int.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes53 =
			new Class[] {
				long.class, long.class, int.class, boolean.class, int.class,
				int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes54 =
			new Class[] {
				long.class, long.class, int.class, String[].class,
				boolean.class, boolean.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes55 =
			new Class[] {
				long.class, long.class, int.class, String[].class,
				boolean.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes56 =
			new Class[] {long.class, long.class, int.class, boolean.class};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes57 =
			new Class[] {
				long.class, long.class, int.class, String[].class, boolean.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes58 =
			new Class[] {
				long.class, long.class, int.class, String[].class,
				boolean.class, boolean.class
			};
	private static final Class<?>[] _getFoldersCountParameterTypes59 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getFoldersCountParameterTypes60 =
		new Class[] {long.class, long.class, boolean.class};
	private static final Class<?>[] _getFoldersCountParameterTypes61 =
		new Class[] {long.class, long.class, int.class, boolean.class};
	private static final Class<?>[]
		_getFoldersFileEntriesCountParameterTypes62 = new Class[] {
			long.class, java.util.List.class, int.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes63 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes64 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes65 =
		new Class[] {long.class, long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes66 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesParameterTypes67 =
		new Class[] {
			long.class, long.class, long.class, String[].class, int.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes68 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes69 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _getGroupFileEntriesCountParameterTypes70 =
		new Class[] {
			long.class, long.class, long.class, String[].class, int.class
		};
	private static final Class<?>[] _getMountFoldersParameterTypes71 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getMountFoldersParameterTypes72 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getMountFoldersParameterTypes73 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getMountFoldersCountParameterTypes74 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getSubfolderIdsParameterTypes75 =
		new Class[] {long.class, java.util.List.class, long.class};
	private static final Class<?>[] _getSubfolderIdsParameterTypes76 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getSubfolderIdsParameterTypes77 =
		new Class[] {long.class, long.class, boolean.class};
	private static final Class<?>[] _getTempFileNamesParameterTypes78 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _lockFolderParameterTypes79 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _lockFolderParameterTypes80 = new Class[] {
		long.class, long.class, String.class, boolean.class, long.class
	};
	private static final Class<?>[] _moveFileEntryParameterTypes81 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFolderParameterTypes82 = new Class[] {
		long.class, long.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _refreshFileEntryLockParameterTypes83 =
		new Class[] {String.class, long.class, long.class};
	private static final Class<?>[] _refreshFolderLockParameterTypes84 =
		new Class[] {String.class, long.class, long.class};
	private static final Class<?>[] _revertFileEntryParameterTypes85 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _searchParameterTypes86 = new Class[] {
		long.class, long.class, int.class, int.class, int.class
	};
	private static final Class<?>[] _searchParameterTypes87 = new Class[] {
		long.class, long.class, long.class, String[].class, int.class,
		int.class, int.class
	};
	private static final Class<?>[] _searchParameterTypes88 = new Class[] {
		long.class, com.liferay.portal.kernel.search.SearchContext.class
	};
	private static final Class<?>[] _searchParameterTypes89 = new Class[] {
		long.class, com.liferay.portal.kernel.search.SearchContext.class,
		com.liferay.portal.kernel.search.Query.class
	};
	private static final Class<?>[] _subscribeFileEntryTypeParameterTypes90 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _subscribeFolderParameterTypes91 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _unlockFolderParameterTypes92 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _unlockFolderParameterTypes93 =
		new Class[] {long.class, long.class, String.class, String.class};
	private static final Class<?>[] _unsubscribeFileEntryTypeParameterTypes94 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _unsubscribeFolderParameterTypes95 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _updateFileEntryParameterTypes96 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			byte[].class, java.util.Date.class, java.util.Date.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes97 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			java.io.File.class, java.util.Date.class, java.util.Date.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryParameterTypes98 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			java.io.InputStream.class, long.class, java.util.Date.class,
			java.util.Date.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryAndCheckInParameterTypes99 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			java.io.File.class, java.util.Date.class, java.util.Date.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateFileEntryAndCheckInParameterTypes100 = new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, String.class,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease.
				class,
			java.io.InputStream.class, long.class, java.util.Date.class,
			java.util.Date.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileShortcutParameterTypes101 =
		new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFolderParameterTypes102 =
		new Class[] {
			long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _verifyFileEntryCheckOutParameterTypes103 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _verifyFileEntryLockParameterTypes104 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _verifyInheritableLockParameterTypes105 =
		new Class[] {long.class, long.class, String.class};

}
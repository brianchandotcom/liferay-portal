/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.document.library.kernel.service.DLFolderServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DLFolderServiceUtil</code> service
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
public class DLFolderServiceHttp {

	public static com.liferay.document.library.kernel.model.DLFolder addFolder(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			long groupId, long repositoryId, boolean mountPoint,
			long parentFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "addFolder",
				_addFolderParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, repositoryId,
				mountPoint, parentFolderId, name, description, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
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
				DLFolderServiceUtil.class, "deleteFolder",
				_deleteFolderParameterTypes1);

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
			HttpPrincipal httpPrincipal, long folderId,
			boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "deleteFolder",
				_deleteFolderParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, includeTrashedEntries);

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
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "deleteFolder",
				_deleteFolderParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, name);

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

	public static com.liferay.document.library.kernel.model.DLFolder
			getDLFolderByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getDLFolderByExternalReferenceCode",
				_getDLFolderByExternalReferenceCodeParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<Object> getFileEntriesAndFileShortcuts(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFileEntriesAndFileShortcuts",
				_getFileEntriesAndFileShortcutsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status, start, end);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFileEntriesAndFileShortcutsCount",
				_getFileEntriesAndFileShortcutsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String[] mimeTypes, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFileEntriesAndFileShortcutsCount",
				_getFileEntriesAndFileShortcutsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, status);

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

	public static com.liferay.document.library.kernel.model.DLFolder getFolder(
			HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolder",
				_getFolderParameterTypes8);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolder getFolder(
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolder",
				_getFolderParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, name);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<Long> getFolderIds(
			HttpPrincipal httpPrincipal, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolderIds",
				_getFolderIdsParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId);

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

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFolder> getFolders(
				HttpPrincipal httpPrincipal, long groupId, double score,
				int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolders",
				_getFoldersParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, score, start, end);

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
				<com.liferay.document.library.kernel.model.DLFolder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFolder> getFolders(
				HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
				boolean includeMountfolders, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFolder>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolders",
				_getFoldersParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, includeMountfolders, status,
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
				<com.liferay.document.library.kernel.model.DLFolder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFolder> getFolders(
				HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFolder>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolders",
				_getFoldersParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, start, end,
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
				<com.liferay.document.library.kernel.model.DLFolder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<Object>
			getFoldersAndFileEntriesAndFileShortcuts(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				boolean includeMountFolders, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?>
					orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, includeMountFolders, status,
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
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				String[] mimeTypes, boolean includeMountFolders, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?>
					orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, includeMountFolders,
				status, start, end, orderByComparator);

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
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				String[] mimeTypes, boolean includeMountFolders,
				com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
					queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, includeMountFolders,
				queryDefinition);

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
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				String[] mimeTypes, long fileEntryTypeId,
				boolean includeMountFolders, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?>
					orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, fileEntryTypeId,
				includeMountFolders, status, start, end, orderByComparator);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status, includeMountFolders);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			int status, String[] mimeTypes, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status, mimeTypes,
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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String[] mimeTypes, boolean includeMountFolders, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, includeMountFolders,
				status);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String[] mimeTypes, boolean includeMountFolders,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, includeMountFolders,
				queryDefinition);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String[] mimeTypes, long fileEntryTypeId,
			boolean includeMountFolders, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, fileEntryTypeId,
				includeMountFolders, status);

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
			HttpPrincipal httpPrincipal, long groupId, double score)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, score);

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
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId);

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
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
			boolean includeMountfolders, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, includeMountfolders,
				status);

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
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
			int status, boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, status,
				includeMountfolders);

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
		<com.liferay.document.library.kernel.model.DLFolder> getMountFolders(
				HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFolder>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getMountFolders",
				_getMountFoldersParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, start, end,
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
				<com.liferay.document.library.kernel.model.DLFolder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getMountFoldersCount(
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getMountFoldersCount",
				_getMountFoldersCountParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId);

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
			HttpPrincipal httpPrincipal, java.util.List<Long> folderIds,
			long groupId, long folderId, boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderIds, groupId, folderId, recurse);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, recurse);

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

	public static boolean hasFolderLock(
			HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "hasFolderLock",
				_hasFolderLockParameterTypes31);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean hasInheritableLock(
			HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "hasInheritableLock",
				_hasInheritableLockParameterTypes32);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean isFolderLocked(
		HttpPrincipal httpPrincipal, long folderId) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "isFolderLocked",
				_isFolderLockedParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
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

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
			HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "lockFolder",
				_lockFolderParameterTypes34);

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

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
			HttpPrincipal httpPrincipal, long folderId, String owner,
			boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "lockFolder",
				_lockFolderParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, owner, inheritable, expirationTime);

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

	public static com.liferay.document.library.kernel.model.DLFolder moveFolder(
			HttpPrincipal httpPrincipal, long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "moveFolder",
				_moveFolderParameterTypes36);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
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
				DLFolderServiceUtil.class, "refreshFolderLock",
				_refreshFolderLockParameterTypes37);

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

	public static void unlockFolder(
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
			String name, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "unlockFolder",
				_unlockFolderParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, name, lockUuid);

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
			HttpPrincipal httpPrincipal, long folderId, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "unlockFolder",
				_unlockFolderParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, lockUuid);

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

	public static com.liferay.document.library.kernel.model.DLFolder
			updateFolder(
				HttpPrincipal httpPrincipal, long folderId, long parentFolderId,
				String name, String description, long defaultFileEntryTypeId,
				java.util.List<Long> fileEntryTypeIds, int restrictionType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "updateFolder",
				_updateFolderParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, parentFolderId, name, description,
				defaultFileEntryTypeId, fileEntryTypeIds, restrictionType,
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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolder
			updateFolder(
				HttpPrincipal httpPrincipal, long folderId, String name,
				String description, long defaultFileEntryTypeId,
				java.util.List<Long> fileEntryTypeIds, int restrictionType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "updateFolder",
				_updateFolderParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, name, description, defaultFileEntryTypeId,
				fileEntryTypeIds, restrictionType, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean verifyInheritableLock(
			HttpPrincipal httpPrincipal, long folderId, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "verifyInheritableLock",
				_verifyInheritableLockParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, lockUuid);

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

	private static Log _log = LogFactoryUtil.getLog(DLFolderServiceHttp.class);

	private static final Class<?>[] _addFolderParameterTypes0 = new Class[] {
		String.class, long.class, long.class, boolean.class, long.class,
		String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteFolderParameterTypes1 = new Class[] {
		long.class
	};
	private static final Class<?>[] _deleteFolderParameterTypes2 = new Class[] {
		long.class, boolean.class
	};
	private static final Class<?>[] _deleteFolderParameterTypes3 = new Class[] {
		long.class, long.class, String.class
	};
	private static final Class<?>[]
		_getDLFolderByExternalReferenceCodeParameterTypes4 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[]
		_getFileEntriesAndFileShortcutsParameterTypes5 = new Class[] {
			long.class, long.class, int.class, int.class, int.class
		};
	private static final Class<?>[]
		_getFileEntriesAndFileShortcutsCountParameterTypes6 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[]
		_getFileEntriesAndFileShortcutsCountParameterTypes7 = new Class[] {
			long.class, long.class, String[].class, int.class
		};
	private static final Class<?>[] _getFolderParameterTypes8 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getFolderParameterTypes9 = new Class[] {
		long.class, long.class, String.class
	};
	private static final Class<?>[] _getFolderIdsParameterTypes10 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getFoldersParameterTypes11 = new Class[] {
		long.class, double.class, int.class, int.class
	};
	private static final Class<?>[] _getFoldersParameterTypes12 = new Class[] {
		long.class, long.class, boolean.class, int.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getFoldersParameterTypes13 = new Class[] {
		long.class, long.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes14 =
			new Class[] {
				long.class, long.class, boolean.class, int.class, int.class,
				int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes15 =
			new Class[] {
				long.class, long.class, String[].class, boolean.class,
				int.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes16 =
			new Class[] {
				long.class, long.class, String[].class, boolean.class,
				com.liferay.portal.kernel.dao.orm.QueryDefinition.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes17 =
			new Class[] {
				long.class, long.class, String[].class, long.class,
				boolean.class, int.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes18 =
			new Class[] {long.class, long.class, int.class, boolean.class};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes19 =
			new Class[] {
				long.class, long.class, int.class, String[].class, boolean.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes20 =
			new Class[] {
				long.class, long.class, String[].class, boolean.class, int.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes21 =
			new Class[] {
				long.class, long.class, String[].class, boolean.class,
				com.liferay.portal.kernel.dao.orm.QueryDefinition.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes22 =
			new Class[] {
				long.class, long.class, String[].class, long.class,
				boolean.class, int.class
			};
	private static final Class<?>[] _getFoldersCountParameterTypes23 =
		new Class[] {long.class, double.class};
	private static final Class<?>[] _getFoldersCountParameterTypes24 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getFoldersCountParameterTypes25 =
		new Class[] {long.class, long.class, boolean.class, int.class};
	private static final Class<?>[] _getFoldersCountParameterTypes26 =
		new Class[] {long.class, long.class, int.class, boolean.class};
	private static final Class<?>[] _getMountFoldersParameterTypes27 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getMountFoldersCountParameterTypes28 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getSubfolderIdsParameterTypes29 =
		new Class[] {
			java.util.List.class, long.class, long.class, boolean.class
		};
	private static final Class<?>[] _getSubfolderIdsParameterTypes30 =
		new Class[] {long.class, long.class, boolean.class};
	private static final Class<?>[] _hasFolderLockParameterTypes31 =
		new Class[] {long.class};
	private static final Class<?>[] _hasInheritableLockParameterTypes32 =
		new Class[] {long.class};
	private static final Class<?>[] _isFolderLockedParameterTypes33 =
		new Class[] {long.class};
	private static final Class<?>[] _lockFolderParameterTypes34 = new Class[] {
		long.class
	};
	private static final Class<?>[] _lockFolderParameterTypes35 = new Class[] {
		long.class, String.class, boolean.class, long.class
	};
	private static final Class<?>[] _moveFolderParameterTypes36 = new Class[] {
		long.class, long.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _refreshFolderLockParameterTypes37 =
		new Class[] {String.class, long.class, long.class};
	private static final Class<?>[] _unlockFolderParameterTypes38 =
		new Class[] {long.class, long.class, String.class, String.class};
	private static final Class<?>[] _unlockFolderParameterTypes39 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateFolderParameterTypes40 =
		new Class[] {
			long.class, long.class, String.class, String.class, long.class,
			java.util.List.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFolderParameterTypes41 =
		new Class[] {
			long.class, String.class, String.class, long.class,
			java.util.List.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _verifyInheritableLockParameterTypes42 =
		new Class[] {long.class, String.class};

}
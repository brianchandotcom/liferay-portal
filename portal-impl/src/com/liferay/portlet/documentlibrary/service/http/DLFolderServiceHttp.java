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
 * @see DLFolderServiceSoap
 * @generated
 */
public class DLFolderServiceHttp {

	public static com.liferay.document.library.kernel.model.DLFolder addFolder(
			HttpPrincipal httpPrincipal, long groupId, long repositoryId,
			boolean mountPoint, long parentFolderId, String name,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "addFolder",
				_addFolderParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, repositoryId, mountPoint, parentFolderId,
				name, description, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
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
				DLFolderServiceUtil.class, "deleteFolder",
				_deleteFolderParameterTypes1);

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

	public static java.util.List<Object> getFileEntriesAndFileShortcuts(
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFileEntriesAndFileShortcuts",
				_getFileEntriesAndFileShortcutsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status, start, end);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFileEntriesAndFileShortcutsCount",
				_getFileEntriesAndFileShortcutsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			int status, String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFileEntriesAndFileShortcutsCount",
				_getFileEntriesAndFileShortcutsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status, mimeTypes);

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

	public static com.liferay.document.library.kernel.model.DLFolder getFolder(
			HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolder",
				_getFolderParameterTypes7);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolder getFolder(
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolder",
				_getFolderParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, name);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Long> getFolderIds(
			HttpPrincipal httpPrincipal, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolderIds",
				_getFolderIdsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId);

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

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFolder> getFolders(
				HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
				int status, boolean includeMountfolders, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFolder> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolders",
				_getFoldersParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, status, includeMountfolders,
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
				<com.liferay.document.library.kernel.model.DLFolder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFolder> getFolders(
				HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFolder> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFolders",
				_getFoldersParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, start, end, obc);

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
				<com.liferay.document.library.kernel.model.DLFolder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object>
			getFoldersAndFileEntriesAndFileShortcuts(
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				int status, boolean includeMountFolders, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status, includeMountFolders,
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
				HttpPrincipal httpPrincipal, long groupId, long folderId,
				int status, String[] mimeTypes, boolean includeMountFolders,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcuts",
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status, mimeTypes,
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
				_getFoldersAndFileEntriesAndFileShortcutsParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, includeMountFolders,
				queryDefinition);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			int status, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status, includeMountFolders);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			int status, String[] mimeTypes, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, status, mimeTypes,
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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			String[] mimeTypes, boolean includeMountFolders,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class,
				"getFoldersAndFileEntriesAndFileShortcutsCount",
				_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, mimeTypes, includeMountFolders,
				queryDefinition);

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
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId);

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
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
			int status, boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getFoldersCount",
				_getFoldersCountParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, status,
				includeMountfolders);

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
		<com.liferay.document.library.kernel.model.DLFolder> getMountFolders(
				HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFolder> obc)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getMountFolders",
				_getMountFoldersParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, start, end, obc);

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
				<com.liferay.document.library.kernel.model.DLFolder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getMountFoldersCount(
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getMountFoldersCount",
				_getMountFoldersCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId);

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
			HttpPrincipal httpPrincipal, java.util.List<Long> folderIds,
			long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderIds, groupId, folderId);

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

	public static void getSubfolderIds(
			HttpPrincipal httpPrincipal, java.util.List<Long> folderIds,
			long groupId, long folderId, boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderIds, groupId, folderId, recurse);

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
			HttpPrincipal httpPrincipal, long groupId, long folderId,
			boolean recurse)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "getSubfolderIds",
				_getSubfolderIdsParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, folderId, recurse);

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

	public static boolean hasFolderLock(
			HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "hasFolderLock",
				_hasFolderLockParameterTypes25);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasInheritableLock(
			HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "hasInheritableLock",
				_hasInheritableLockParameterTypes26);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean isFolderLocked(
		HttpPrincipal httpPrincipal, long folderId) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "isFolderLocked",
				_isFolderLockedParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
			HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "lockFolder",
				_lockFolderParameterTypes28);

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

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.lock.Lock lockFolder(
			HttpPrincipal httpPrincipal, long folderId, String owner,
			boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "lockFolder",
				_lockFolderParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, owner, inheritable, expirationTime);

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

	public static com.liferay.document.library.kernel.model.DLFolder moveFolder(
			HttpPrincipal httpPrincipal, long folderId, long parentFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "moveFolder",
				_moveFolderParameterTypes30);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
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
				DLFolderServiceUtil.class, "refreshFolderLock",
				_refreshFolderLockParameterTypes31);

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

	public static void unlockFolder(
			HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
			String name, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "unlockFolder",
				_unlockFolderParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentFolderId, name, lockUuid);

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
			HttpPrincipal httpPrincipal, long folderId, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "unlockFolder",
				_unlockFolderParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, lockUuid);

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
				_updateFolderParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, parentFolderId, name, description,
				defaultFileEntryTypeId, fileEntryTypeIds, restrictionType,
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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFolder
			updateFolder(
				HttpPrincipal httpPrincipal, long folderId, String name,
				String description, long defaultFileEntryTypeId,
				java.util.List<Long> fileEntryTypeIds,
				boolean overrideFileEntryTypes,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "updateFolder",
				_updateFolderParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, name, description, defaultFileEntryTypeId,
				fileEntryTypeIds, overrideFileEntryTypes, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_updateFolderParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, name, description, defaultFileEntryTypeId,
				fileEntryTypeIds, restrictionType, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFolder)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean verifyInheritableLock(
			HttpPrincipal httpPrincipal, long folderId, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFolderServiceUtil.class, "verifyInheritableLock",
				_verifyInheritableLockParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, folderId, lockUuid);

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

	private static Log _log = LogFactoryUtil.getLog(DLFolderServiceHttp.class);

	private static final Class<?>[] _addFolderParameterTypes0 = new Class[] {
		long.class, long.class, boolean.class, long.class, String.class,
		String.class, com.liferay.portal.kernel.service.ServiceContext.class
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
		_getFileEntriesAndFileShortcutsParameterTypes4 = new Class[] {
			long.class, long.class, int.class, int.class, int.class
		};
	private static final Class<?>[]
		_getFileEntriesAndFileShortcutsCountParameterTypes5 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[]
		_getFileEntriesAndFileShortcutsCountParameterTypes6 = new Class[] {
			long.class, long.class, int.class, String[].class
		};
	private static final Class<?>[] _getFolderParameterTypes7 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getFolderParameterTypes8 = new Class[] {
		long.class, long.class, String.class
	};
	private static final Class<?>[] _getFolderIdsParameterTypes9 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _getFoldersParameterTypes10 = new Class[] {
		long.class, long.class, int.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getFoldersParameterTypes11 = new Class[] {
		long.class, long.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes12 =
			new Class[] {
				long.class, long.class, int.class, boolean.class, int.class,
				int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes13 =
			new Class[] {
				long.class, long.class, int.class, String[].class,
				boolean.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsParameterTypes14 =
			new Class[] {
				long.class, long.class, String[].class, boolean.class,
				com.liferay.portal.kernel.dao.orm.QueryDefinition.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes15 =
			new Class[] {long.class, long.class, int.class, boolean.class};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes16 =
			new Class[] {
				long.class, long.class, int.class, String[].class, boolean.class
			};
	private static final Class<?>[]
		_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes17 =
			new Class[] {
				long.class, long.class, String[].class, boolean.class,
				com.liferay.portal.kernel.dao.orm.QueryDefinition.class
			};
	private static final Class<?>[] _getFoldersCountParameterTypes18 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getFoldersCountParameterTypes19 =
		new Class[] {long.class, long.class, int.class, boolean.class};
	private static final Class<?>[] _getMountFoldersParameterTypes20 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getMountFoldersCountParameterTypes21 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getSubfolderIdsParameterTypes22 =
		new Class[] {java.util.List.class, long.class, long.class};
	private static final Class<?>[] _getSubfolderIdsParameterTypes23 =
		new Class[] {
			java.util.List.class, long.class, long.class, boolean.class
		};
	private static final Class<?>[] _getSubfolderIdsParameterTypes24 =
		new Class[] {long.class, long.class, boolean.class};
	private static final Class<?>[] _hasFolderLockParameterTypes25 =
		new Class[] {long.class};
	private static final Class<?>[] _hasInheritableLockParameterTypes26 =
		new Class[] {long.class};
	private static final Class<?>[] _isFolderLockedParameterTypes27 =
		new Class[] {long.class};
	private static final Class<?>[] _lockFolderParameterTypes28 = new Class[] {
		long.class
	};
	private static final Class<?>[] _lockFolderParameterTypes29 = new Class[] {
		long.class, String.class, boolean.class, long.class
	};
	private static final Class<?>[] _moveFolderParameterTypes30 = new Class[] {
		long.class, long.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _refreshFolderLockParameterTypes31 =
		new Class[] {String.class, long.class, long.class};
	private static final Class<?>[] _unlockFolderParameterTypes32 =
		new Class[] {long.class, long.class, String.class, String.class};
	private static final Class<?>[] _unlockFolderParameterTypes33 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateFolderParameterTypes34 =
		new Class[] {
			long.class, long.class, String.class, String.class, long.class,
			java.util.List.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFolderParameterTypes35 =
		new Class[] {
			long.class, String.class, String.class, long.class,
			java.util.List.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFolderParameterTypes36 =
		new Class[] {
			long.class, String.class, String.class, long.class,
			java.util.List.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _verifyInheritableLockParameterTypes37 =
		new Class[] {long.class, String.class};

}
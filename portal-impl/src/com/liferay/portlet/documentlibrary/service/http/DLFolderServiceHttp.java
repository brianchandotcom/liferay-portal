/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

/**
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
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
 * @author    Brian Wing Shun Chan
 * @see       DLFolderServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil
 * @generated
 */
public class DLFolderServiceHttp {
	public static com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		HttpPrincipal httpPrincipal, long groupId, long repositoryId,
		boolean mountPoint, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"addFolder", _addFolderParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					repositoryId, mountPoint, parentFolderId, name,
					description, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.documentlibrary.model.DLFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFolder(HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"deleteFolder", _deleteFolderParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFolder(HttpPrincipal httpPrincipal, long groupId,
		long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"deleteFolder", _deleteFolderParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentFolderId, name);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFileEntriesAndFileShortcuts",
					_getFileEntriesAndFileShortcutsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFileEntriesAndFileShortcutsCount",
					_getFileEntriesAndFileShortcutsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileEntriesAndFileShortcutsCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int status,
		java.lang.String[] mimeTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFileEntriesAndFileShortcutsCount",
					_getFileEntriesAndFileShortcutsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, status, mimeTypes);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFolder", _getFolderParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.documentlibrary.model.DLFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFolder", _getFolderParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentFolderId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.documentlibrary.model.DLFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long[] getFolderIds(HttpPrincipal httpPrincipal,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFolderIds", _getFolderIdsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
		boolean includeMountfolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFolders", _getFoldersParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentFolderId, includeMountfolders, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFolders", _getFoldersParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentFolderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int status,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFoldersAndFileEntriesAndFileShortcuts",
					_getFoldersAndFileEntriesAndFileShortcutsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, status, includeMountFolders, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcuts(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int status,
		java.lang.String[] mimeTypes, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFoldersAndFileEntriesAndFileShortcuts",
					_getFoldersAndFileEntriesAndFileShortcutsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, status, mimeTypes, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int status,
		java.lang.String[] mimeTypes, boolean includeMountFolders, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFoldersAndFileEntriesAndFileShortcuts",
					_getFoldersAndFileEntriesAndFileShortcutsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, status, mimeTypes, includeMountFolders, start,
					end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int status,
		boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFoldersAndFileEntriesAndFileShortcutsCount",
					_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, status, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersAndFileEntriesAndFileShortcutsCount(
		HttpPrincipal httpPrincipal, long groupId, long folderId, int status,
		java.lang.String[] mimeTypes, boolean includeMountFolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFoldersAndFileEntriesAndFileShortcutsCount",
					_getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, status, mimeTypes, includeMountFolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersCount(HttpPrincipal httpPrincipal,
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFoldersCount", _getFoldersCountParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFoldersCount(HttpPrincipal httpPrincipal,
		long groupId, long parentFolderId, boolean includeMountfolders)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getFoldersCount", _getFoldersCountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentFolderId, includeMountfolders);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getMountFolders(
		HttpPrincipal httpPrincipal, long groupId, long parentFolderId,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getMountFolders", _getMountFoldersParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentFolderId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getMountFoldersCount(HttpPrincipal httpPrincipal,
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getMountFoldersCount",
					_getMountFoldersCountParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void getSubfolderIds(HttpPrincipal httpPrincipal,
		java.util.List<java.lang.Long> folderIds, long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getSubfolderIds", _getSubfolderIdsParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					folderIds, groupId, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Long> getSubfolderIds(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		boolean recurse)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"getSubfolderIds", _getSubfolderIdsParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, recurse);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Long>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasFolderLock(HttpPrincipal httpPrincipal,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"hasFolderLock", _hasFolderLockParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasInheritableLock(HttpPrincipal httpPrincipal,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"hasInheritableLock", _hasInheritableLockParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean isFolderLocked(HttpPrincipal httpPrincipal,
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"isFolderLocked", _isFolderLockedParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Lock lockFolder(
		HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"lockFolder", _lockFolderParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Lock lockFolder(
		HttpPrincipal httpPrincipal, long folderId, java.lang.String owner,
		boolean inheritable, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"lockFolder", _lockFolderParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					folderId, owner, inheritable, expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder moveFolder(
		HttpPrincipal httpPrincipal, long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"moveFolder", _moveFolderParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					folderId, parentFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.documentlibrary.model.DLFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Lock refreshFolderLock(
		HttpPrincipal httpPrincipal, java.lang.String lockUuid, long companyId,
		long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"refreshFolderLock", _refreshFolderLockParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					lockUuid, companyId, expirationTime);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unlockFolder(HttpPrincipal httpPrincipal, long groupId,
		long folderId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"unlockFolder", _unlockFolderParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					folderId, lockUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unlockFolder(HttpPrincipal httpPrincipal, long groupId,
		long parentFolderId, java.lang.String name, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"unlockFolder", _unlockFolderParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentFolderId, name, lockUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		HttpPrincipal httpPrincipal, long folderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"updateFolder", _updateFolderParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					folderId, name, description, defaultFileEntryTypeId,
					fileEntryTypeIds, overrideFileEntryTypes, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.documentlibrary.model.DLFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean verifyInheritableLock(HttpPrincipal httpPrincipal,
		long folderId, java.lang.String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DLFolderServiceUtil.class.getName(),
					"verifyInheritableLock",
					_verifyInheritableLockParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					folderId, lockUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			long.class, long.class, boolean.class, long.class,
			java.lang.String.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFolderParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteFolderParameterTypes2 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _getFileEntriesAndFileShortcutsParameterTypes3 =
		new Class[] { long.class, long.class, int.class, int.class, int.class };
	private static final Class<?>[] _getFileEntriesAndFileShortcutsCountParameterTypes4 =
		new Class[] { long.class, long.class, int.class };
	private static final Class<?>[] _getFileEntriesAndFileShortcutsCountParameterTypes5 =
		new Class[] { long.class, long.class, int.class, java.lang.String[].class };
	private static final Class<?>[] _getFolderParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getFolderParameterTypes7 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _getFolderIdsParameterTypes8 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getFoldersParameterTypes9 = new Class[] {
			long.class, long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFoldersParameterTypes10 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFoldersAndFileEntriesAndFileShortcutsParameterTypes11 =
		new Class[] {
			long.class, long.class, int.class, boolean.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFoldersAndFileEntriesAndFileShortcutsParameterTypes12 =
		new Class[] {
			long.class, long.class, int.class, java.lang.String[].class,
			boolean.class
		};
	private static final Class<?>[] _getFoldersAndFileEntriesAndFileShortcutsParameterTypes13 =
		new Class[] {
			long.class, long.class, int.class, java.lang.String[].class,
			boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes14 =
		new Class[] { long.class, long.class, int.class, boolean.class };
	private static final Class<?>[] _getFoldersAndFileEntriesAndFileShortcutsCountParameterTypes15 =
		new Class[] {
			long.class, long.class, int.class, java.lang.String[].class,
			boolean.class
		};
	private static final Class<?>[] _getFoldersCountParameterTypes16 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getFoldersCountParameterTypes17 = new Class[] {
			long.class, long.class, boolean.class
		};
	private static final Class<?>[] _getMountFoldersParameterTypes18 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getMountFoldersCountParameterTypes19 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getSubfolderIdsParameterTypes20 = new Class[] {
			java.util.List.class, long.class, long.class
		};
	private static final Class<?>[] _getSubfolderIdsParameterTypes21 = new Class[] {
			long.class, long.class, boolean.class
		};
	private static final Class<?>[] _hasFolderLockParameterTypes22 = new Class[] {
			long.class
		};
	private static final Class<?>[] _hasInheritableLockParameterTypes23 = new Class[] {
			long.class
		};
	private static final Class<?>[] _isFolderLockedParameterTypes24 = new Class[] {
			long.class
		};
	private static final Class<?>[] _lockFolderParameterTypes25 = new Class[] {
			long.class
		};
	private static final Class<?>[] _lockFolderParameterTypes26 = new Class[] {
			long.class, java.lang.String.class, boolean.class, long.class
		};
	private static final Class<?>[] _moveFolderParameterTypes27 = new Class[] {
			long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _refreshFolderLockParameterTypes28 = new Class[] {
			java.lang.String.class, long.class, long.class
		};
	private static final Class<?>[] _unlockFolderParameterTypes29 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _unlockFolderParameterTypes30 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class
		};
	private static final Class<?>[] _updateFolderParameterTypes31 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			long.class, java.util.List.class, boolean.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _verifyInheritableLockParameterTypes32 = new Class[] {
			long.class, java.lang.String.class
		};
}
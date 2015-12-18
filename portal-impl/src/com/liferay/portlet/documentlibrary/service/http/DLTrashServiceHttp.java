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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.documentlibrary.service.DLTrashServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link DLTrashServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @see DLTrashServiceSoap
 * @see HttpPrincipal
 * @see DLTrashServiceUtil
 * @generated
 */
@ProviderType
public class DLTrashServiceHttp {
	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		HttpPrincipal httpPrincipal, long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLTrashServiceUtil.class,
					"moveFileEntryFromTrash",
					_moveFileEntryFromTrashParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId, newFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLTrashServiceUtil.class,
					"moveFileEntryToTrash", _moveFileEntryToTrashParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileShortcut moveFileShortcutFromTrash(
		HttpPrincipal httpPrincipal, long fileShortcutId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLTrashServiceUtil.class,
					"moveFileShortcutFromTrash",
					_moveFileShortcutFromTrashParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileShortcutId, newFolderId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileShortcut)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.FileShortcut moveFileShortcutToTrash(
		HttpPrincipal httpPrincipal, long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLTrashServiceUtil.class,
					"moveFileShortcutToTrash",
					_moveFileShortcutToTrashParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileShortcutId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.FileShortcut)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder moveFolderFromTrash(
		HttpPrincipal httpPrincipal, long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLTrashServiceUtil.class,
					"moveFolderFromTrash", _moveFolderFromTrashParameterTypes4);

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

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
		HttpPrincipal httpPrincipal, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLTrashServiceUtil.class,
					"moveFolderToTrash", _moveFolderToTrashParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.repository.model.Folder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreFileEntryFromTrash(HttpPrincipal httpPrincipal,
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLTrashServiceUtil.class,
					"restoreFileEntryFromTrash",
					_restoreFileEntryFromTrashParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileEntryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreFileShortcutFromTrash(
		HttpPrincipal httpPrincipal, long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLTrashServiceUtil.class,
					"restoreFileShortcutFromTrash",
					_restoreFileShortcutFromTrashParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					fileShortcutId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreFolderFromTrash(HttpPrincipal httpPrincipal,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DLTrashServiceUtil.class,
					"restoreFolderFromTrash",
					_restoreFolderFromTrashParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, folderId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLTrashServiceHttp.class);
	private static final Class<?>[] _moveFileEntryFromTrashParameterTypes0 = new Class[] {
			long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFileEntryToTrashParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _moveFileShortcutFromTrashParameterTypes2 = new Class[] {
			long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFileShortcutToTrashParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _moveFolderFromTrashParameterTypes4 = new Class[] {
			long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _moveFolderToTrashParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _restoreFileEntryFromTrashParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _restoreFileShortcutFromTrashParameterTypes7 =
		new Class[] { long.class };
	private static final Class<?>[] _restoreFolderFromTrashParameterTypes8 = new Class[] {
			long.class
		};
}
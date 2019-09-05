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

import com.liferay.document.library.kernel.service.DLFileShortcutServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DLFileShortcutServiceUtil</code> service
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
 * @see DLFileShortcutServiceSoap
 * @generated
 */
public class DLFileShortcutServiceHttp {

	public static com.liferay.document.library.kernel.model.DLFileShortcut
			addFileShortcut(
				HttpPrincipal httpPrincipal, long groupId, long repositoryId,
				long folderId, long toFileEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileShortcutServiceUtil.class, "addFileShortcut",
				_addFileShortcutParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, repositoryId, folderId, toFileEntryId,
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

			return (com.liferay.document.library.kernel.model.DLFileShortcut)
				returnObj;
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
				DLFileShortcutServiceUtil.class, "deleteFileShortcut",
				_deleteFileShortcutParameterTypes1);

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

	public static com.liferay.document.library.kernel.model.DLFileShortcut
			getFileShortcut(HttpPrincipal httpPrincipal, long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileShortcutServiceUtil.class, "getFileShortcut",
				_getFileShortcutParameterTypes2);

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

			return (com.liferay.document.library.kernel.model.DLFileShortcut)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileShortcut
			updateFileShortcut(
				HttpPrincipal httpPrincipal, long fileShortcutId,
				long repositoryId, long folderId, long toFileEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileShortcutServiceUtil.class, "updateFileShortcut",
				_updateFileShortcutParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileShortcutId, repositoryId, folderId,
				toFileEntryId, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFileShortcut)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateFileShortcuts(
			HttpPrincipal httpPrincipal, long oldToFileEntryId,
			long newToFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileShortcutServiceUtil.class, "updateFileShortcuts",
				_updateFileShortcutsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oldToFileEntryId, newToFileEntryId);

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

	private static Log _log = LogFactoryUtil.getLog(
		DLFileShortcutServiceHttp.class);

	private static final Class<?>[] _addFileShortcutParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFileShortcutParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getFileShortcutParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _updateFileShortcutParameterTypes3 =
		new Class[] {
			long.class, long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileShortcutsParameterTypes4 =
		new Class[] {long.class, long.class};

}
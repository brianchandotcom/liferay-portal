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

import com.liferay.document.library.kernel.service.DLFileVersionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DLFileVersionServiceUtil</code> service
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
 * @see DLFileVersionServiceSoap
 * @generated
 */
public class DLFileVersionServiceHttp {

	public static com.liferay.document.library.kernel.model.DLFileVersion
			getFileVersion(HttpPrincipal httpPrincipal, long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileVersionServiceUtil.class, "getFileVersion",
				_getFileVersionParameterTypes0);

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

			return (com.liferay.document.library.kernel.model.DLFileVersion)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileVersion>
				getFileVersions(
					HttpPrincipal httpPrincipal, long fileEntryId, int status)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileVersionServiceUtil.class, "getFileVersions",
				_getFileVersionsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, status);

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
				<com.liferay.document.library.kernel.model.DLFileVersion>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFileVersionsCount(
			HttpPrincipal httpPrincipal, long fileEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileVersionServiceUtil.class, "getFileVersionsCount",
				_getFileVersionsCountParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, status);

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

	public static com.liferay.document.library.kernel.model.DLFileVersion
			getLatestFileVersion(HttpPrincipal httpPrincipal, long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileVersionServiceUtil.class, "getLatestFileVersion",
				_getLatestFileVersionParameterTypes3);

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

			return (com.liferay.document.library.kernel.model.DLFileVersion)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileVersion
			getLatestFileVersion(
				HttpPrincipal httpPrincipal, long fileEntryId,
				boolean excludeWorkingCopy)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileVersionServiceUtil.class, "getLatestFileVersion",
				_getLatestFileVersionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryId, excludeWorkingCopy);

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

			return (com.liferay.document.library.kernel.model.DLFileVersion)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFileVersionServiceHttp.class);

	private static final Class<?>[] _getFileVersionParameterTypes0 =
		new Class[] {long.class};
	private static final Class<?>[] _getFileVersionsParameterTypes1 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getFileVersionsCountParameterTypes2 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getLatestFileVersionParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getLatestFileVersionParameterTypes4 =
		new Class[] {long.class, boolean.class};

}
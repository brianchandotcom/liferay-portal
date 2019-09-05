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

package com.liferay.dynamic.data.mapping.service.http;

import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDMFormInstanceVersionServiceUtil</code> service
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
 * @see DDMFormInstanceVersionServiceSoap
 * @generated
 */
public class DDMFormInstanceVersionServiceHttp {

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
			getFormInstanceVersion(
				HttpPrincipal httpPrincipal, long ddmFormInstanceVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceVersionServiceUtil.class,
				"getFormInstanceVersion",
				_getFormInstanceVersionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceVersionId);

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

			return (com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion>
				getFormInstanceVersions(
					HttpPrincipal httpPrincipal, long ddmFormInstanceId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.dynamic.data.mapping.model.
							DDMFormInstanceVersion> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceVersionServiceUtil.class,
				"getFormInstanceVersions",
				_getFormInstanceVersionsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceId, start, end, orderByComparator);

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
				<com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFormInstanceVersionsCount(
			HttpPrincipal httpPrincipal, long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceVersionServiceUtil.class,
				"getFormInstanceVersionsCount",
				_getFormInstanceVersionsCountParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceId);

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

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
			getLatestFormInstanceVersion(
				HttpPrincipal httpPrincipal, long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceVersionServiceUtil.class,
				"getLatestFormInstanceVersion",
				_getLatestFormInstanceVersionParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceId);

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

			return (com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion
			getLatestFormInstanceVersion(
				HttpPrincipal httpPrincipal, long ddmFormInstanceId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceVersionServiceUtil.class,
				"getLatestFormInstanceVersion",
				_getLatestFormInstanceVersionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceId, status);

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

			return (com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceVersionServiceHttp.class);

	private static final Class<?>[] _getFormInstanceVersionParameterTypes0 =
		new Class[] {long.class};
	private static final Class<?>[] _getFormInstanceVersionsParameterTypes1 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getFormInstanceVersionsCountParameterTypes2 = new Class[] {long.class};
	private static final Class<?>[]
		_getLatestFormInstanceVersionParameterTypes3 = new Class[] {long.class};
	private static final Class<?>[]
		_getLatestFormInstanceVersionParameterTypes4 = new Class[] {
			long.class, int.class
		};

}
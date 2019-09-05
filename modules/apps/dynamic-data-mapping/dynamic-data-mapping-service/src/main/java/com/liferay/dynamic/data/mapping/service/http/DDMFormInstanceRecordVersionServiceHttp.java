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

import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDMFormInstanceRecordVersionServiceUtil</code> service
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
 * @see DDMFormInstanceRecordVersionServiceSoap
 * @generated
 */
public class DDMFormInstanceRecordVersionServiceHttp {

	public static
		com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
				fetchLatestFormInstanceRecordVersion(
					HttpPrincipal httpPrincipal, long userId,
					long formInstanceId, String formInstanceVersion, int status)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordVersionServiceUtil.class,
				"fetchLatestFormInstanceRecordVersion",
				_fetchLatestFormInstanceRecordVersionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, formInstanceId, formInstanceVersion, status);

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
				DDMFormInstanceRecordVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
				getFormInstanceRecordVersion(
					HttpPrincipal httpPrincipal,
					long ddmFormInstanceRecordVersionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordVersionServiceUtil.class,
				"getFormInstanceRecordVersion",
				_getFormInstanceRecordVersionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceRecordVersionId);

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
				DDMFormInstanceRecordVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion
				getFormInstanceRecordVersion(
					HttpPrincipal httpPrincipal, long ddmFormInstanceRecordId,
					String version)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordVersionServiceUtil.class,
				"getFormInstanceRecordVersion",
				_getFormInstanceRecordVersionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceRecordId, version);

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
				DDMFormInstanceRecordVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion>
				getFormInstanceRecordVersions(
					HttpPrincipal httpPrincipal, long ddmFormInstanceRecordId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordVersionServiceUtil.class,
				"getFormInstanceRecordVersions",
				_getFormInstanceRecordVersionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceRecordId);

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
				<com.liferay.dynamic.data.mapping.model.
					DDMFormInstanceRecordVersion>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion>
				getFormInstanceRecordVersions(
					HttpPrincipal httpPrincipal, long ddmFormInstanceRecordId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.dynamic.data.mapping.model.
							DDMFormInstanceRecordVersion> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordVersionServiceUtil.class,
				"getFormInstanceRecordVersions",
				_getFormInstanceRecordVersionsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceRecordId, start, end,
				orderByComparator);

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
				<com.liferay.dynamic.data.mapping.model.
					DDMFormInstanceRecordVersion>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFormInstanceRecordVersionsCount(
			HttpPrincipal httpPrincipal, long ddmFormInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordVersionServiceUtil.class,
				"getFormInstanceRecordVersionsCount",
				_getFormInstanceRecordVersionsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceRecordId);

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

	private static Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordVersionServiceHttp.class);

	private static final Class<?>[]
		_fetchLatestFormInstanceRecordVersionParameterTypes0 = new Class[] {
			long.class, long.class, String.class, int.class
		};
	private static final Class<?>[]
		_getFormInstanceRecordVersionParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[]
		_getFormInstanceRecordVersionParameterTypes2 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getFormInstanceRecordVersionsParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getFormInstanceRecordVersionsParameterTypes4 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getFormInstanceRecordVersionsCountParameterTypes5 = new Class[] {
			long.class
		};

}
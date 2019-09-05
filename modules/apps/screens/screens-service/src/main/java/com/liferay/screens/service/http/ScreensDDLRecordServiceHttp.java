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

package com.liferay.screens.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.screens.service.ScreensDDLRecordServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>ScreensDDLRecordServiceUtil</code> service
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
 * @author José Manuel Navarro
 * @see ScreensDDLRecordServiceSoap
 * @generated
 */
public class ScreensDDLRecordServiceHttp {

	public static com.liferay.portal.kernel.json.JSONObject getDDLRecord(
			HttpPrincipal httpPrincipal, long ddlRecordId,
			java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ScreensDDLRecordServiceUtil.class, "getDDLRecord",
				_getDDLRecordParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddlRecordId, locale);

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

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONArray getDDLRecords(
			HttpPrincipal httpPrincipal, long ddlRecordSetId,
			java.util.Locale locale, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecord> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ScreensDDLRecordServiceUtil.class, "getDDLRecords",
				_getDDLRecordsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddlRecordSetId, locale, start, end, obc);

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

			return (com.liferay.portal.kernel.json.JSONArray)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONArray getDDLRecords(
			HttpPrincipal httpPrincipal, long ddlRecordSetId, long userId,
			java.util.Locale locale, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecord> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ScreensDDLRecordServiceUtil.class, "getDDLRecords",
				_getDDLRecordsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddlRecordSetId, userId, locale, start, end, obc);

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

			return (com.liferay.portal.kernel.json.JSONArray)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getDDLRecordsCount(
			HttpPrincipal httpPrincipal, long ddlRecordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ScreensDDLRecordServiceUtil.class, "getDDLRecordsCount",
				_getDDLRecordsCountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddlRecordSetId);

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

	public static int getDDLRecordsCount(
			HttpPrincipal httpPrincipal, long ddlRecordSetId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ScreensDDLRecordServiceUtil.class, "getDDLRecordsCount",
				_getDDLRecordsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddlRecordSetId, userId);

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
		ScreensDDLRecordServiceHttp.class);

	private static final Class<?>[] _getDDLRecordParameterTypes0 = new Class[] {
		long.class, java.util.Locale.class
	};
	private static final Class<?>[] _getDDLRecordsParameterTypes1 =
		new Class[] {
			long.class, java.util.Locale.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getDDLRecordsParameterTypes2 =
		new Class[] {
			long.class, long.class, java.util.Locale.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getDDLRecordsCountParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getDDLRecordsCountParameterTypes4 =
		new Class[] {long.class, long.class};

}
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

import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDMFormInstanceRecordServiceUtil</code> service
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
 * @see DDMFormInstanceRecordServiceSoap
 * @generated
 */
public class DDMFormInstanceRecordServiceHttp {

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord
			addFormInstanceRecord(
				HttpPrincipal httpPrincipal, long groupId,
				long ddmFormInstanceId,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					ddmFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordServiceUtil.class, "addFormInstanceRecord",
				_addFormInstanceRecordParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, ddmFormInstanceId, ddmFormValues,
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

			return (com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFormInstanceRecord(
			HttpPrincipal httpPrincipal, long ddmFormInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordServiceUtil.class,
				"deleteFormInstanceRecord",
				_deleteFormInstanceRecordParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceRecordId);

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

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord
			getFormInstanceRecord(
				HttpPrincipal httpPrincipal, long ddmFormInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordServiceUtil.class, "getFormInstanceRecord",
				_getFormInstanceRecordParameterTypes2);

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

			return (com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord>
				getFormInstanceRecords(
					HttpPrincipal httpPrincipal, long ddmFormInstanceId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordServiceUtil.class,
				"getFormInstanceRecords",
				_getFormInstanceRecordsParameterTypes3);

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

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord>
				getFormInstanceRecords(
					HttpPrincipal httpPrincipal, long ddmFormInstanceId,
					int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.dynamic.data.mapping.model.
							DDMFormInstanceRecord> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordServiceUtil.class,
				"getFormInstanceRecords",
				_getFormInstanceRecordsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceId, status, start, end,
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
				<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFormInstanceRecordsCount(
			HttpPrincipal httpPrincipal, long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordServiceUtil.class,
				"getFormInstanceRecordsCount",
				_getFormInstanceRecordsCountParameterTypes5);

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

	public static void revertFormInstanceRecord(
			HttpPrincipal httpPrincipal, long ddmFormInstanceRecordId,
			String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordServiceUtil.class,
				"revertFormInstanceRecord",
				_revertFormInstanceRecordParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceRecordId, version, serviceContext);

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

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord
			updateFormInstanceRecord(
				HttpPrincipal httpPrincipal, long ddmFormInstanceRecordId,
				boolean majorVersion,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					ddmFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceRecordServiceUtil.class,
				"updateFormInstanceRecord",
				_updateFormInstanceRecordParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceRecordId, majorVersion, ddmFormValues,
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

			return (com.liferay.dynamic.data.mapping.model.
				DDMFormInstanceRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordServiceHttp.class);

	private static final Class<?>[] _addFormInstanceRecordParameterTypes0 =
		new Class[] {
			long.class, long.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFormInstanceRecordParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getFormInstanceRecordParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getFormInstanceRecordsParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getFormInstanceRecordsParameterTypes4 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getFormInstanceRecordsCountParameterTypes5 = new Class[] {long.class};
	private static final Class<?>[] _revertFormInstanceRecordParameterTypes6 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFormInstanceRecordParameterTypes7 =
		new Class[] {
			long.class, boolean.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
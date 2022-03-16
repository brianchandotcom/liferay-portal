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

package com.liferay.batch.planner.service.http;

import com.liferay.batch.planner.service.BatchPlannerLogServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>BatchPlannerLogServiceUtil</code> service
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
 * @author Igor Beslic
 * @generated
 */
public class BatchPlannerLogServiceHttp {

	public static com.liferay.batch.planner.model.BatchPlannerLog
			getBatchPlannerLog(
				HttpPrincipal httpPrincipal, long batchPlannerLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerLogServiceUtil.class, "getBatchPlannerLog",
				_getBatchPlannerLogParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerLogId);

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

			return (com.liferay.batch.planner.model.BatchPlannerLog)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerLog
			getBatchPlannerPlanBatchPlannerLog(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerLogServiceUtil.class,
				"getBatchPlannerPlanBatchPlannerLog",
				_getBatchPlannerPlanBatchPlannerLogParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId);

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

			return (com.liferay.batch.planner.model.BatchPlannerLog)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerLog>
				getCompanyBatchPlannerLogs(
					HttpPrincipal httpPrincipal, long companyId, boolean export,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.batch.planner.model.BatchPlannerLog>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerLogServiceUtil.class, "getCompanyBatchPlannerLogs",
				_getCompanyBatchPlannerLogsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, export, start, end, orderByComparator);

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
				<com.liferay.batch.planner.model.BatchPlannerLog>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerLog>
				getCompanyBatchPlannerLogs(
					HttpPrincipal httpPrincipal, long companyId, boolean export,
					String searchByField, String searchByKeyword, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.batch.planner.model.BatchPlannerLog>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerLogServiceUtil.class, "getCompanyBatchPlannerLogs",
				_getCompanyBatchPlannerLogsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, export, searchByField, searchByKeyword,
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
				<com.liferay.batch.planner.model.BatchPlannerLog>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerLog>
				getCompanyBatchPlannerLogs(
					HttpPrincipal httpPrincipal, long companyId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.batch.planner.model.BatchPlannerLog>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerLogServiceUtil.class, "getCompanyBatchPlannerLogs",
				_getCompanyBatchPlannerLogsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end, orderByComparator);

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
				<com.liferay.batch.planner.model.BatchPlannerLog>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerLog>
				getCompanyBatchPlannerLogs(
					HttpPrincipal httpPrincipal, long companyId,
					String searchByField, String searchByKeyword, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.batch.planner.model.BatchPlannerLog>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerLogServiceUtil.class, "getCompanyBatchPlannerLogs",
				_getCompanyBatchPlannerLogsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, searchByField, searchByKeyword, start,
				end, orderByComparator);

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
				<com.liferay.batch.planner.model.BatchPlannerLog>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCompanyBatchPlannerLogsCount(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerLogServiceUtil.class,
				"getCompanyBatchPlannerLogsCount",
				_getCompanyBatchPlannerLogsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

	public static int getCompanyBatchPlannerLogsCount(
			HttpPrincipal httpPrincipal, long companyId, boolean export)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerLogServiceUtil.class,
				"getCompanyBatchPlannerLogsCount",
				_getCompanyBatchPlannerLogsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, export);

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

	public static int getCompanyBatchPlannerLogsCount(
			HttpPrincipal httpPrincipal, long companyId, boolean export,
			String searchByField, String searchByKeyword)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerLogServiceUtil.class,
				"getCompanyBatchPlannerLogsCount",
				_getCompanyBatchPlannerLogsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, export, searchByField, searchByKeyword);

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

	public static int getCompanyBatchPlannerLogsCount(
			HttpPrincipal httpPrincipal, long companyId, String searchByField,
			String searchByKeyword)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerLogServiceUtil.class,
				"getCompanyBatchPlannerLogsCount",
				_getCompanyBatchPlannerLogsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, searchByField, searchByKeyword);

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

	private static Log _log = LogFactoryUtil.getLog(
		BatchPlannerLogServiceHttp.class);

	private static final Class<?>[] _getBatchPlannerLogParameterTypes0 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getBatchPlannerPlanBatchPlannerLogParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCompanyBatchPlannerLogsParameterTypes2 =
		new Class[] {
			long.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCompanyBatchPlannerLogsParameterTypes3 =
		new Class[] {
			long.class, boolean.class, String.class, String.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCompanyBatchPlannerLogsParameterTypes4 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCompanyBatchPlannerLogsParameterTypes5 =
		new Class[] {
			long.class, String.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCompanyBatchPlannerLogsCountParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCompanyBatchPlannerLogsCountParameterTypes7 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[]
		_getCompanyBatchPlannerLogsCountParameterTypes8 = new Class[] {
			long.class, boolean.class, String.class, String.class
		};
	private static final Class<?>[]
		_getCompanyBatchPlannerLogsCountParameterTypes9 = new Class[] {
			long.class, String.class, String.class
		};

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.http;

import com.liferay.batch.engine.service.BatchEngineImportTaskServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>BatchEngineImportTaskServiceUtil</code> service
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
 * @author Shuyang Zhou
 * @generated
 */
public class BatchEngineImportTaskServiceHttp {

	public static com.liferay.batch.engine.model.BatchEngineImportTask
			addBatchEngineImportTask(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId, long userId, long batchSize, String callbackURL,
				String className, byte[] content, String contentType,
				String executeStatus,
				java.util.Map<String, String> fieldNameMappingMap,
				int importStrategy, String operation,
				java.util.Map<String, java.io.Serializable> parameters,
				String taskItemDelegateName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchEngineImportTaskServiceUtil.class,
				"addBatchEngineImportTask",
				_addBatchEngineImportTaskParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId, userId, batchSize,
				callbackURL, className, content, contentType, executeStatus,
				fieldNameMappingMap, importStrategy, operation, parameters,
				taskItemDelegateName);

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

			return (com.liferay.batch.engine.model.BatchEngineImportTask)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.engine.model.BatchEngineImportTask
			addBatchEngineImportTask(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId, long userId, long batchSize, String callbackURL,
				String className, byte[] content, String contentType,
				String executeStatus,
				java.util.Map<String, String> fieldNameMappingMap,
				int importStrategy, String operation,
				java.util.Map<String, java.io.Serializable> parameters,
				String taskItemDelegateName,
				com.liferay.batch.engine.BatchEngineTaskItemDelegate<?>
					batchEngineTaskItemDelegate)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchEngineImportTaskServiceUtil.class,
				"addBatchEngineImportTask",
				_addBatchEngineImportTaskParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId, userId, batchSize,
				callbackURL, className, content, contentType, executeStatus,
				fieldNameMappingMap, importStrategy, operation, parameters,
				taskItemDelegateName, batchEngineTaskItemDelegate);

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

			return (com.liferay.batch.engine.model.BatchEngineImportTask)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.engine.model.BatchEngineImportTask
			getBatchEngineImportTask(
				HttpPrincipal httpPrincipal, long batchEngineImportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchEngineImportTaskServiceUtil.class,
				"getBatchEngineImportTask",
				_getBatchEngineImportTaskParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchEngineImportTaskId);

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

			return (com.liferay.batch.engine.model.BatchEngineImportTask)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.engine.model.BatchEngineImportTask
			getBatchEngineImportTaskByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchEngineImportTaskServiceUtil.class,
				"getBatchEngineImportTaskByExternalReferenceCode",
				_getBatchEngineImportTaskByExternalReferenceCodeParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId);

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

			return (com.liferay.batch.engine.model.BatchEngineImportTask)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.engine.model.BatchEngineImportTask>
				getBatchEngineImportTasks(
					HttpPrincipal httpPrincipal, long companyId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchEngineImportTaskServiceUtil.class,
				"getBatchEngineImportTasks",
				_getBatchEngineImportTasksParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

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
				<com.liferay.batch.engine.model.BatchEngineImportTask>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.engine.model.BatchEngineImportTask>
				getBatchEngineImportTasks(
					HttpPrincipal httpPrincipal, long companyId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.batch.engine.model.BatchEngineImportTask>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchEngineImportTaskServiceUtil.class,
				"getBatchEngineImportTasks",
				_getBatchEngineImportTasksParameterTypes5);

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
				<com.liferay.batch.engine.model.BatchEngineImportTask>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getBatchEngineImportTasksCount(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchEngineImportTaskServiceUtil.class,
				"getBatchEngineImportTasksCount",
				_getBatchEngineImportTasksCountParameterTypes6);

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

	public static java.io.InputStream openContentInputStream(
			HttpPrincipal httpPrincipal, long batchEngineImportTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchEngineImportTaskServiceUtil.class,
				"openContentInputStream",
				_openContentInputStreamParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchEngineImportTaskId);

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

			return (java.io.InputStream)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BatchEngineImportTaskServiceHttp.class);

	private static final Class<?>[] _addBatchEngineImportTaskParameterTypes0 =
		new Class[] {
			String.class, long.class, long.class, long.class, String.class,
			String.class, byte[].class, String.class, String.class,
			java.util.Map.class, int.class, String.class, java.util.Map.class,
			String.class
		};
	private static final Class<?>[] _addBatchEngineImportTaskParameterTypes1 =
		new Class[] {
			String.class, long.class, long.class, long.class, String.class,
			String.class, byte[].class, String.class, String.class,
			java.util.Map.class, int.class, String.class, java.util.Map.class,
			String.class,
			com.liferay.batch.engine.BatchEngineTaskItemDelegate.class
		};
	private static final Class<?>[] _getBatchEngineImportTaskParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getBatchEngineImportTaskByExternalReferenceCodeParameterTypes3 =
			new Class[] {String.class, long.class};
	private static final Class<?>[] _getBatchEngineImportTasksParameterTypes4 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getBatchEngineImportTasksParameterTypes5 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getBatchEngineImportTasksCountParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _openContentInputStreamParameterTypes7 =
		new Class[] {long.class};

}
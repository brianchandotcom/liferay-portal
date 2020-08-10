/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.service.http;

import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLogServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceDataIntegrationProcessLogServiceUtil</code> service
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
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessLogServiceSoap
 * @generated
 */
public class CommerceDataIntegrationProcessLogServiceHttp {

	public static com.liferay.commerce.data.integration.model.
		CommerceDataIntegrationProcessLog addCommerceDataIntegrationProcessLog(
				HttpPrincipal httpPrincipal, long userId,
				long commerceDataIntegrationProcessId, String error,
				String output, int status, java.util.Date startDate,
				java.util.Date endDate)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDataIntegrationProcessLogServiceUtil.class,
				"addCommerceDataIntegrationProcessLog",
				_addCommerceDataIntegrationProcessLogParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, commerceDataIntegrationProcessId, error,
				output, status, startDate, endDate);

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

			return (com.liferay.commerce.data.integration.model.
				CommerceDataIntegrationProcessLog)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceDataIntegrationProcessLog(
			HttpPrincipal httpPrincipal, long cDataIntegrationProcessLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDataIntegrationProcessLogServiceUtil.class,
				"deleteCommerceDataIntegrationProcessLog",
				_deleteCommerceDataIntegrationProcessLogParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cDataIntegrationProcessLogId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
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
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.data.integration.model.
		CommerceDataIntegrationProcessLog getCommerceDataIntegrationProcessLog(
				HttpPrincipal httpPrincipal, long cDataIntegrationProcessLogId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDataIntegrationProcessLogServiceUtil.class,
				"getCommerceDataIntegrationProcessLog",
				_getCommerceDataIntegrationProcessLogParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cDataIntegrationProcessLogId);

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

			return (com.liferay.commerce.data.integration.model.
				CommerceDataIntegrationProcessLog)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcessLog>
					getCommerceDataIntegrationProcessLogs(
						HttpPrincipal httpPrincipal,
						long commerceDataIntegrationProcessId, int start,
						int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDataIntegrationProcessLogServiceUtil.class,
				"getCommerceDataIntegrationProcessLogs",
				_getCommerceDataIntegrationProcessLogsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDataIntegrationProcessId, start, end);

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
				<com.liferay.commerce.data.integration.model.
					CommerceDataIntegrationProcessLog>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceDataIntegrationProcessLogsCount(
			HttpPrincipal httpPrincipal, long commerceDataIntegrationProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDataIntegrationProcessLogServiceUtil.class,
				"getCommerceDataIntegrationProcessLogsCount",
				_getCommerceDataIntegrationProcessLogsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDataIntegrationProcessId);

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

	public static com.liferay.commerce.data.integration.model.
		CommerceDataIntegrationProcessLog
				updateCommerceDataIntegrationProcessLog(
					HttpPrincipal httpPrincipal,
					long cDataIntegrationProcessLogId, String error,
					String output, int status, java.util.Date endDate)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDataIntegrationProcessLogServiceUtil.class,
				"updateCommerceDataIntegrationProcessLog",
				_updateCommerceDataIntegrationProcessLogParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cDataIntegrationProcessLogId, error, output, status,
				endDate);

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

			return (com.liferay.commerce.data.integration.model.
				CommerceDataIntegrationProcessLog)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceDataIntegrationProcessLogServiceHttp.class);

	private static final Class<?>[]
		_addCommerceDataIntegrationProcessLogParameterTypes0 = new Class[] {
			long.class, long.class, String.class, String.class, int.class,
			java.util.Date.class, java.util.Date.class
		};
	private static final Class<?>[]
		_deleteCommerceDataIntegrationProcessLogParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceDataIntegrationProcessLogParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceDataIntegrationProcessLogsParameterTypes3 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceDataIntegrationProcessLogsCountParameterTypes4 =
			new Class[] {long.class};
	private static final Class<?>[]
		_updateCommerceDataIntegrationProcessLogParameterTypes5 = new Class[] {
			long.class, String.class, String.class, int.class,
			java.util.Date.class
		};

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.http;

import com.liferay.change.tracking.service.CTProcessServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CTProcessServiceUtil</code> service
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
 * @generated
 */
public class CTProcessServiceHttp {

	public static java.util.List<com.liferay.change.tracking.model.CTProcess>
			getCTProcesses(
				HttpPrincipal httpPrincipal, long companyId, long userId,
				String keywords, int status, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.change.tracking.model.CTProcess>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CTProcessServiceUtil.class, "getCTProcesses",
				_getCTProcessesParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, userId, keywords, status, type, start,
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

			return (java.util.List<com.liferay.change.tracking.model.CTProcess>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.change.tracking.model.CTProcess>
			getCTProcesses(
				HttpPrincipal httpPrincipal, long companyId, long userId,
				String keywords, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.change.tracking.model.CTProcess>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CTProcessServiceUtil.class, "getCTProcesses",
				_getCTProcessesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, userId, keywords, status, start, end,
				orderByComparator);

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

			return (java.util.List<com.liferay.change.tracking.model.CTProcess>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCTProcessesCount(
		HttpPrincipal httpPrincipal, long companyId, long userId,
		String keywords, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				CTProcessServiceUtil.class, "getCTProcessesCount",
				_getCTProcessesCountParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, userId, keywords, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
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

	public static int getCTProcessesCount(
		HttpPrincipal httpPrincipal, long companyId, long userId,
		String keywords, int status, int type) {

		try {
			MethodKey methodKey = new MethodKey(
				CTProcessServiceUtil.class, "getCTProcessesCount",
				_getCTProcessesCountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, userId, keywords, status, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
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

	private static Log _log = LogFactoryUtil.getLog(CTProcessServiceHttp.class);

	private static final Class<?>[] _getCTProcessesParameterTypes0 =
		new Class[] {
			long.class, long.class, String.class, int.class, int.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCTProcessesParameterTypes1 =
		new Class[] {
			long.class, long.class, String.class, int.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCTProcessesCountParameterTypes2 =
		new Class[] {long.class, long.class, String.class, int.class};
	private static final Class<?>[] _getCTProcessesCountParameterTypes3 =
		new Class[] {
			long.class, long.class, String.class, int.class, int.class
		};

}
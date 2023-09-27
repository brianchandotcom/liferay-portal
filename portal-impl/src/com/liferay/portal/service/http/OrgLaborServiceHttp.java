/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.OrgLaborServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>OrgLaborServiceUtil</code> service
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
public class OrgLaborServiceHttp {

	public static com.liferay.portal.kernel.model.OrgLabor addOrgLabor(
			HttpPrincipal httpPrincipal, long organizationId, long typeId,
			int sunOpen, int sunClose, int monOpen, int monClose, int tueOpen,
			int tueClose, int wedOpen, int wedClose, int thuOpen, int thuClose,
			int friOpen, int friClose, int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OrgLaborServiceUtil.class, "addOrgLabor",
				_addOrgLaborParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId, typeId, sunOpen, sunClose, monOpen,
				monClose, tueOpen, tueClose, wedOpen, wedClose, thuOpen,
				thuClose, friOpen, friClose, satOpen, satClose);

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

			return (com.liferay.portal.kernel.model.OrgLabor)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteOrgLabor(
			HttpPrincipal httpPrincipal, long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OrgLaborServiceUtil.class, "deleteOrgLabor",
				_deleteOrgLaborParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, orgLaborId);

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

	public static com.liferay.portal.kernel.model.OrgLabor getOrgLabor(
			HttpPrincipal httpPrincipal, long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OrgLaborServiceUtil.class, "getOrgLabor",
				_getOrgLaborParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, orgLaborId);

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

			return (com.liferay.portal.kernel.model.OrgLabor)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.OrgLabor>
			getOrgLabors(HttpPrincipal httpPrincipal, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OrgLaborServiceUtil.class, "getOrgLabors",
				_getOrgLaborsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId);

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

			return (java.util.List<com.liferay.portal.kernel.model.OrgLabor>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.OrgLabor updateOrgLabor(
			HttpPrincipal httpPrincipal, long orgLaborId, long typeId,
			int sunOpen, int sunClose, int monOpen, int monClose, int tueOpen,
			int tueClose, int wedOpen, int wedClose, int thuOpen, int thuClose,
			int friOpen, int friClose, int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OrgLaborServiceUtil.class, "updateOrgLabor",
				_updateOrgLaborParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, orgLaborId, typeId, sunOpen, sunClose, monOpen,
				monClose, tueOpen, tueClose, wedOpen, wedClose, thuOpen,
				thuClose, friOpen, friClose, satOpen, satClose);

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

			return (com.liferay.portal.kernel.model.OrgLabor)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(OrgLaborServiceHttp.class);

	private static final Class<?>[] _addOrgLaborParameterTypes0 = new Class[] {
		long.class, long.class, int.class, int.class, int.class, int.class,
		int.class, int.class, int.class, int.class, int.class, int.class,
		int.class, int.class, int.class, int.class
	};
	private static final Class<?>[] _deleteOrgLaborParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getOrgLaborParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getOrgLaborsParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _updateOrgLaborParameterTypes4 =
		new Class[] {
			long.class, long.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class
		};

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.search.experiences.service.SXPElementServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SXPElementServiceUtil</code> service
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
public class SXPElementServiceHttp {

	public static com.liferay.search.experiences.model.SXPElement addSXPElement(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String elementDefinitionJSON, boolean readOnly,
			String schemaVersion,
			java.util.Map<java.util.Locale, String> titleMap, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPElementServiceUtil.class, "addSXPElement",
				_addSXPElementParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, descriptionMap,
				elementDefinitionJSON, readOnly, schemaVersion, titleMap, type,
				serviceContext);

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

			return (com.liferay.search.experiences.model.SXPElement)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPElement
			deleteSXPElement(HttpPrincipal httpPrincipal, long sxpElementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPElementServiceUtil.class, "deleteSXPElement",
				_deleteSXPElementParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sxpElementId);

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

			return (com.liferay.search.experiences.model.SXPElement)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPElement
			fetchSXPElement(HttpPrincipal httpPrincipal, long sxpElementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPElementServiceUtil.class, "fetchSXPElement",
				_fetchSXPElementParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sxpElementId);

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

			return (com.liferay.search.experiences.model.SXPElement)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPElement
			fetchSXPElementByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPElementServiceUtil.class,
				"fetchSXPElementByExternalReferenceCode",
				_fetchSXPElementByExternalReferenceCodeParameterTypes3);

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

			return (com.liferay.search.experiences.model.SXPElement)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPElement getSXPElement(
			HttpPrincipal httpPrincipal, long sxpElementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPElementServiceUtil.class, "getSXPElement",
				_getSXPElementParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sxpElementId);

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

			return (com.liferay.search.experiences.model.SXPElement)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPElement
			getSXPElementByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPElementServiceUtil.class,
				"getSXPElementByExternalReferenceCode",
				_getSXPElementByExternalReferenceCodeParameterTypes5);

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

			return (com.liferay.search.experiences.model.SXPElement)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPElement
			updateSXPElement(
				HttpPrincipal httpPrincipal, long sxpElementId,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String elementDefinitionJSON, String schemaVersion,
				boolean hidden,
				java.util.Map<java.util.Locale, String> titleMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPElementServiceUtil.class, "updateSXPElement",
				_updateSXPElementParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sxpElementId, descriptionMap, elementDefinitionJSON,
				schemaVersion, hidden, titleMap, serviceContext);

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

			return (com.liferay.search.experiences.model.SXPElement)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SXPElementServiceHttp.class);

	private static final Class<?>[] _addSXPElementParameterTypes0 =
		new Class[] {
			String.class, java.util.Map.class, String.class, boolean.class,
			String.class, java.util.Map.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSXPElementParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchSXPElementParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchSXPElementByExternalReferenceCodeParameterTypes3 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _getSXPElementParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getSXPElementByExternalReferenceCodeParameterTypes5 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _updateSXPElementParameterTypes6 =
		new Class[] {
			long.class, java.util.Map.class, String.class, String.class,
			boolean.class, java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
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
import com.liferay.search.experiences.service.SXPBlueprintServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SXPBlueprintServiceUtil</code> service
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
public class SXPBlueprintServiceHttp {

	public static com.liferay.search.experiences.model.SXPBlueprint
			addSXPBlueprint(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				String configurationJSON,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String elementInstancesJSON, String schemaVersion,
				java.util.Map<java.util.Locale, String> titleMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPBlueprintServiceUtil.class, "addSXPBlueprint",
				_addSXPBlueprintParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, configurationJSON,
				descriptionMap, elementInstancesJSON, schemaVersion, titleMap,
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

			return (com.liferay.search.experiences.model.SXPBlueprint)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPBlueprint
			deleteSXPBlueprint(HttpPrincipal httpPrincipal, long sxpBlueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPBlueprintServiceUtil.class, "deleteSXPBlueprint",
				_deleteSXPBlueprintParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sxpBlueprintId);

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

			return (com.liferay.search.experiences.model.SXPBlueprint)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPBlueprint
			fetchSXPBlueprint(HttpPrincipal httpPrincipal, long sxpBlueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPBlueprintServiceUtil.class, "fetchSXPBlueprint",
				_fetchSXPBlueprintParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sxpBlueprintId);

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

			return (com.liferay.search.experiences.model.SXPBlueprint)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPBlueprint
			fetchSXPBlueprintByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPBlueprintServiceUtil.class,
				"fetchSXPBlueprintByExternalReferenceCode",
				_fetchSXPBlueprintByExternalReferenceCodeParameterTypes3);

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

			return (com.liferay.search.experiences.model.SXPBlueprint)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPBlueprint
			getSXPBlueprint(HttpPrincipal httpPrincipal, long sxpBlueprintId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPBlueprintServiceUtil.class, "getSXPBlueprint",
				_getSXPBlueprintParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sxpBlueprintId);

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

			return (com.liferay.search.experiences.model.SXPBlueprint)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPBlueprint
			getSXPBlueprintByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long companyId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPBlueprintServiceUtil.class,
				"getSXPBlueprintByExternalReferenceCode",
				_getSXPBlueprintByExternalReferenceCodeParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, externalReferenceCode);

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

			return (com.liferay.search.experiences.model.SXPBlueprint)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.search.experiences.model.SXPBlueprint
			updateSXPBlueprint(
				HttpPrincipal httpPrincipal, long sxpBlueprintId,
				String configurationJSON,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String elementInstancesJSON, String schemaVersion,
				java.util.Map<java.util.Locale, String> titleMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPBlueprintServiceUtil.class, "updateSXPBlueprint",
				_updateSXPBlueprintParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sxpBlueprintId, configurationJSON, descriptionMap,
				elementInstancesJSON, schemaVersion, titleMap, serviceContext);

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

			return (com.liferay.search.experiences.model.SXPBlueprint)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SXPBlueprintServiceHttp.class);

	private static final Class<?>[] _addSXPBlueprintParameterTypes0 =
		new Class[] {
			String.class, String.class, java.util.Map.class, String.class,
			String.class, java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSXPBlueprintParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchSXPBlueprintParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchSXPBlueprintByExternalReferenceCodeParameterTypes3 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _getSXPBlueprintParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getSXPBlueprintByExternalReferenceCodeParameterTypes5 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _updateSXPBlueprintParameterTypes6 =
		new Class[] {
			long.class, String.class, java.util.Map.class, String.class,
			String.class, java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
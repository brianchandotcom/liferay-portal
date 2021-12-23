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
 * @see SXPElementServiceSoap
 * @generated
 */
public class SXPElementServiceHttp {

	public static com.liferay.search.experiences.model.SXPElement addSXPElement(
			HttpPrincipal httpPrincipal,
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
				methodKey, descriptionMap, elementDefinitionJSON, readOnly,
				schemaVersion, titleMap, type, serviceContext);

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

	public static com.liferay.search.experiences.model.SXPElement getSXPElement(
			HttpPrincipal httpPrincipal, long sxpElementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SXPElementServiceUtil.class, "getSXPElement",
				_getSXPElementParameterTypes2);

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
				_updateSXPElementParameterTypes3);

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
			java.util.Map.class, String.class, boolean.class, String.class,
			java.util.Map.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSXPElementParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getSXPElementParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _updateSXPElementParameterTypes3 =
		new Class[] {
			long.class, java.util.Map.class, String.class, String.class,
			boolean.class, java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
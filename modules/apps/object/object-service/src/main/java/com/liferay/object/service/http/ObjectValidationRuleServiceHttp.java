/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.http;

import com.liferay.object.service.ObjectValidationRuleServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ObjectValidationRuleServiceUtil</code> service
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
 * @author Marco Leo
 * @generated
 */
public class ObjectValidationRuleServiceHttp {

	public static com.liferay.object.model.ObjectValidationRule
			addObjectValidationRule(
				HttpPrincipal httpPrincipal, long objectDefinitionId,
				boolean active, String engine,
				java.util.Map<java.util.Locale, String> errorLabelMap,
				java.util.Map<java.util.Locale, String> nameMap,
				String outputType, String script, boolean system,
				java.util.List
					<com.liferay.object.model.ObjectValidationRuleSetting>
						objectValidationRuleSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectValidationRuleServiceUtil.class,
				"addObjectValidationRule",
				_addObjectValidationRuleParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectDefinitionId, active, engine, errorLabelMap,
				nameMap, outputType, script, system,
				objectValidationRuleSettings);

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

			return (com.liferay.object.model.ObjectValidationRule)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectValidationRule
			deleteObjectValidationRule(
				HttpPrincipal httpPrincipal, long objectValidationRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectValidationRuleServiceUtil.class,
				"deleteObjectValidationRule",
				_deleteObjectValidationRuleParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectValidationRuleId);

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

			return (com.liferay.object.model.ObjectValidationRule)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectValidationRule
			getObjectValidationRule(
				HttpPrincipal httpPrincipal, long objectValidationRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectValidationRuleServiceUtil.class,
				"getObjectValidationRule",
				_getObjectValidationRuleParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectValidationRuleId);

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

			return (com.liferay.object.model.ObjectValidationRule)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectValidationRule
			updateObjectValidationRule(
				HttpPrincipal httpPrincipal, long objectValidationRuleId,
				boolean active, String engine,
				java.util.Map<java.util.Locale, String> errorLabelMap,
				java.util.Map<java.util.Locale, String> nameMap,
				String outputType, String script,
				java.util.List
					<com.liferay.object.model.ObjectValidationRuleSetting>
						objectValidationRuleSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectValidationRuleServiceUtil.class,
				"updateObjectValidationRule",
				_updateObjectValidationRuleParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectValidationRuleId, active, engine,
				errorLabelMap, nameMap, outputType, script,
				objectValidationRuleSettings);

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

			return (com.liferay.object.model.ObjectValidationRule)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ObjectValidationRuleServiceHttp.class);

	private static final Class<?>[] _addObjectValidationRuleParameterTypes0 =
		new Class[] {
			long.class, boolean.class, String.class, java.util.Map.class,
			java.util.Map.class, String.class, String.class, boolean.class,
			java.util.List.class
		};
	private static final Class<?>[] _deleteObjectValidationRuleParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getObjectValidationRuleParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _updateObjectValidationRuleParameterTypes3 =
		new Class[] {
			long.class, boolean.class, String.class, java.util.Map.class,
			java.util.Map.class, String.class, String.class,
			java.util.List.class
		};

}
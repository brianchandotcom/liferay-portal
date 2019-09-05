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

package com.liferay.mobile.device.rules.service.http;

import com.liferay.mobile.device.rules.service.MDRRuleServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>MDRRuleServiceUtil</code> service
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
 * @author Edward C. Han
 * @see MDRRuleServiceSoap
 * @generated
 */
public class MDRRuleServiceHttp {

	public static com.liferay.mobile.device.rules.model.MDRRule addRule(
			HttpPrincipal httpPrincipal, long ruleGroupId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, String type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleServiceUtil.class, "addRule", _addRuleParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleGroupId, nameMap, descriptionMap, type,
				typeSettings, serviceContext);

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

			return (com.liferay.mobile.device.rules.model.MDRRule)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRule addRule(
			HttpPrincipal httpPrincipal, long ruleGroupId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, String type,
			com.liferay.portal.kernel.util.UnicodeProperties typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleServiceUtil.class, "addRule", _addRuleParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleGroupId, nameMap, descriptionMap, type,
				typeSettings, serviceContext);

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

			return (com.liferay.mobile.device.rules.model.MDRRule)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteRule(HttpPrincipal httpPrincipal, long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleServiceUtil.class, "deleteRule",
				_deleteRuleParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, ruleId);

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

	public static com.liferay.mobile.device.rules.model.MDRRule fetchRule(
			HttpPrincipal httpPrincipal, long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleServiceUtil.class, "fetchRule",
				_fetchRuleParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, ruleId);

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

			return (com.liferay.mobile.device.rules.model.MDRRule)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRule getRule(
			HttpPrincipal httpPrincipal, long ruleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleServiceUtil.class, "getRule", _getRuleParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, ruleId);

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

			return (com.liferay.mobile.device.rules.model.MDRRule)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRule updateRule(
			HttpPrincipal httpPrincipal, long ruleId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, String type,
			String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleServiceUtil.class, "updateRule",
				_updateRuleParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleId, nameMap, descriptionMap, type, typeSettings,
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

			return (com.liferay.mobile.device.rules.model.MDRRule)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRule updateRule(
			HttpPrincipal httpPrincipal, long ruleId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleServiceUtil.class, "updateRule",
				_updateRuleParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleId, nameMap, descriptionMap, type,
				typeSettingsProperties, serviceContext);

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

			return (com.liferay.mobile.device.rules.model.MDRRule)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MDRRuleServiceHttp.class);

	private static final Class<?>[] _addRuleParameterTypes0 = new Class[] {
		long.class, java.util.Map.class, java.util.Map.class, String.class,
		String.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addRuleParameterTypes1 = new Class[] {
		long.class, java.util.Map.class, java.util.Map.class, String.class,
		com.liferay.portal.kernel.util.UnicodeProperties.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteRuleParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _fetchRuleParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getRuleParameterTypes4 = new Class[] {
		long.class
	};
	private static final Class<?>[] _updateRuleParameterTypes5 = new Class[] {
		long.class, java.util.Map.class, java.util.Map.class, String.class,
		String.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _updateRuleParameterTypes6 = new Class[] {
		long.class, java.util.Map.class, java.util.Map.class, String.class,
		com.liferay.portal.kernel.util.UnicodeProperties.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};

}
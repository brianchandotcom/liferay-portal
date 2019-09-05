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

import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>MDRRuleGroupInstanceServiceUtil</code> service
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
 * @see MDRRuleGroupInstanceServiceSoap
 * @generated
 */
public class MDRRuleGroupInstanceServiceHttp {

	public static com.liferay.mobile.device.rules.model.MDRRuleGroupInstance
			addRuleGroupInstance(
				HttpPrincipal httpPrincipal, long groupId, String className,
				long classPK, long ruleGroupId, int priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupInstanceServiceUtil.class, "addRuleGroupInstance",
				_addRuleGroupInstanceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK, ruleGroupId, priority,
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

			return (com.liferay.mobile.device.rules.model.MDRRuleGroupInstance)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleGroupInstance
			addRuleGroupInstance(
				HttpPrincipal httpPrincipal, long groupId, String className,
				long classPK, long ruleGroupId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupInstanceServiceUtil.class, "addRuleGroupInstance",
				_addRuleGroupInstanceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK, ruleGroupId,
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

			return (com.liferay.mobile.device.rules.model.MDRRuleGroupInstance)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteRuleGroupInstance(
			HttpPrincipal httpPrincipal, long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupInstanceServiceUtil.class,
				"deleteRuleGroupInstance",
				_deleteRuleGroupInstanceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleGroupInstanceId);

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

	public static java.util.List
		<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance>
			getRuleGroupInstances(
				HttpPrincipal httpPrincipal, String className, long classPK,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance>
						orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupInstanceServiceUtil.class, "getRuleGroupInstances",
				_getRuleGroupInstancesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getRuleGroupInstancesCount(
		HttpPrincipal httpPrincipal, String className, long classPK) {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupInstanceServiceUtil.class,
				"getRuleGroupInstancesCount",
				_getRuleGroupInstancesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleGroupInstance
			updateRuleGroupInstance(
				HttpPrincipal httpPrincipal, long ruleGroupInstanceId,
				int priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupInstanceServiceUtil.class,
				"updateRuleGroupInstance",
				_updateRuleGroupInstanceParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleGroupInstanceId, priority);

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

			return (com.liferay.mobile.device.rules.model.MDRRuleGroupInstance)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MDRRuleGroupInstanceServiceHttp.class);

	private static final Class<?>[] _addRuleGroupInstanceParameterTypes0 =
		new Class[] {
			long.class, String.class, long.class, long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addRuleGroupInstanceParameterTypes1 =
		new Class[] {
			long.class, String.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteRuleGroupInstanceParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getRuleGroupInstancesParameterTypes3 =
		new Class[] {
			String.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getRuleGroupInstancesCountParameterTypes4 =
		new Class[] {String.class, long.class};
	private static final Class<?>[] _updateRuleGroupInstanceParameterTypes5 =
		new Class[] {long.class, int.class};

}
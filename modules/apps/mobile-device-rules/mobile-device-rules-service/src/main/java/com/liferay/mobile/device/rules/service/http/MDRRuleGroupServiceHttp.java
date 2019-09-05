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

import com.liferay.mobile.device.rules.service.MDRRuleGroupServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>MDRRuleGroupServiceUtil</code> service
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
 * @see MDRRuleGroupServiceSoap
 * @generated
 */
public class MDRRuleGroupServiceHttp {

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup
			addRuleGroup(
				HttpPrincipal httpPrincipal, long groupId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupServiceUtil.class, "addRuleGroup",
				_addRuleGroupParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nameMap, descriptionMap, serviceContext);

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

			return (com.liferay.mobile.device.rules.model.MDRRuleGroup)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup
			copyRuleGroup(
				HttpPrincipal httpPrincipal, long ruleGroupId, long groupId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupServiceUtil.class, "copyRuleGroup",
				_copyRuleGroupParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleGroupId, groupId, serviceContext);

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

			return (com.liferay.mobile.device.rules.model.MDRRuleGroup)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteRuleGroup(
			HttpPrincipal httpPrincipal, long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupServiceUtil.class, "deleteRuleGroup",
				_deleteRuleGroupParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleGroupId);

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

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup
			fetchRuleGroup(HttpPrincipal httpPrincipal, long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupServiceUtil.class, "fetchRuleGroup",
				_fetchRuleGroupParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleGroupId);

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

			return (com.liferay.mobile.device.rules.model.MDRRuleGroup)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup
			getRuleGroup(HttpPrincipal httpPrincipal, long ruleGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupServiceUtil.class, "getRuleGroup",
				_getRuleGroupParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleGroupId);

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

			return (com.liferay.mobile.device.rules.model.MDRRuleGroup)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.mobile.device.rules.model.MDRRuleGroup> getRuleGroups(
			HttpPrincipal httpPrincipal, long[] groupIds, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupServiceUtil.class, "getRuleGroups",
				_getRuleGroupsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.mobile.device.rules.model.MDRRuleGroup>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getRuleGroupsCount(
		HttpPrincipal httpPrincipal, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupServiceUtil.class, "getRuleGroupsCount",
				_getRuleGroupsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds);

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

	public static com.liferay.mobile.device.rules.model.MDRRuleGroup
			updateRuleGroup(
				HttpPrincipal httpPrincipal, long ruleGroupId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MDRRuleGroupServiceUtil.class, "updateRuleGroup",
				_updateRuleGroupParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ruleGroupId, nameMap, descriptionMap,
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

			return (com.liferay.mobile.device.rules.model.MDRRuleGroup)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MDRRuleGroupServiceHttp.class);

	private static final Class<?>[] _addRuleGroupParameterTypes0 = new Class[] {
		long.class, java.util.Map.class, java.util.Map.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _copyRuleGroupParameterTypes1 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteRuleGroupParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchRuleGroupParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getRuleGroupParameterTypes4 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getRuleGroupsParameterTypes5 =
		new Class[] {long[].class, int.class, int.class};
	private static final Class<?>[] _getRuleGroupsCountParameterTypes6 =
		new Class[] {long[].class};
	private static final Class<?>[] _updateRuleGroupParameterTypes7 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
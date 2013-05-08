/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
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
 * @author    Edward C. Han
 * @see       MDRRuleGroupInstanceServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil
 * @generated
 */
public class MDRRuleGroupInstanceServiceHttp {
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance addRuleGroupInstance(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String className,
		long classPK, long ruleGroupId, int priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(MDRRuleGroupInstanceServiceUtil.class,
					"addRuleGroupInstance", _addRuleGroupInstanceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					className, classPK, ruleGroupId, priority, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance addRuleGroupInstance(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String className,
		long classPK, long ruleGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(MDRRuleGroupInstanceServiceUtil.class,
					"addRuleGroupInstance", _addRuleGroupInstanceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					className, classPK, ruleGroupId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteRuleGroupInstance(HttpPrincipal httpPrincipal,
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(MDRRuleGroupInstanceServiceUtil.class,
					"deleteRuleGroupInstance",
					_deleteRuleGroupInstanceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					ruleGroupInstanceId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> getRuleGroupInstances(
		HttpPrincipal httpPrincipal, java.lang.String className, long classPK,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(MDRRuleGroupInstanceServiceUtil.class,
					"getRuleGroupInstances",
					_getRuleGroupInstancesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getRuleGroupInstancesCount(HttpPrincipal httpPrincipal,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(MDRRuleGroupInstanceServiceUtil.class,
					"getRuleGroupInstancesCount",
					_getRuleGroupInstancesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance updateRuleGroupInstance(
		HttpPrincipal httpPrincipal, long ruleGroupInstanceId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(MDRRuleGroupInstanceServiceUtil.class,
					"updateRuleGroupInstance",
					_updateRuleGroupInstanceParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					ruleGroupInstanceId, priority);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MDRRuleGroupInstanceServiceHttp.class);
	private static final Class<?>[] _addRuleGroupInstanceParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, long.class, long.class,
			int.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addRuleGroupInstanceParameterTypes1 = new Class[] {
			long.class, java.lang.String.class, long.class, long.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteRuleGroupInstanceParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getRuleGroupInstancesParameterTypes3 = new Class[] {
			java.lang.String.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getRuleGroupInstancesCountParameterTypes4 = new Class[] {
			java.lang.String.class, long.class
		};
	private static final Class<?>[] _updateRuleGroupInstanceParameterTypes5 = new Class[] {
			long.class, int.class
		};
}
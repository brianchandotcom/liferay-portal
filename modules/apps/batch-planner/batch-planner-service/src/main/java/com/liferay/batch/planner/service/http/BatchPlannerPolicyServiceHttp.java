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

package com.liferay.batch.planner.service.http;

import com.liferay.batch.planner.service.BatchPlannerPolicyServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>BatchPlannerPolicyServiceUtil</code> service
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
 * @author Igor Beslic
 * @see BatchPlannerPolicyServiceSoap
 * @generated
 */
public class BatchPlannerPolicyServiceHttp {

	public static com.liferay.batch.planner.model.BatchPlannerPolicy
			addBatchPlannerPolicy(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId,
				String name, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPolicyServiceUtil.class, "addBatchPlannerPolicy",
				_addBatchPlannerPolicyParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId, name, value);

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

			return (com.liferay.batch.planner.model.BatchPlannerPolicy)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPolicy
			deleteBatchPlannerPolicy(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId,
				String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPolicyServiceUtil.class, "deleteBatchPlannerPolicy",
				_deleteBatchPlannerPolicyParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId, name);

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

			return (com.liferay.batch.planner.model.BatchPlannerPolicy)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerPolicy>
				getBatchPlannerPolicies(
					HttpPrincipal httpPrincipal, long batchPlannerPlanId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPolicyServiceUtil.class, "getBatchPlannerPolicies",
				_getBatchPlannerPoliciesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId);

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

			return (java.util.List
				<com.liferay.batch.planner.model.BatchPlannerPolicy>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPolicy
			getBatchPlannerPolicy(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId,
				String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPolicyServiceUtil.class, "getBatchPlannerPolicy",
				_getBatchPlannerPolicyParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId, name);

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

			return (com.liferay.batch.planner.model.BatchPlannerPolicy)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static boolean hasBatchPlannerPolicy(
			HttpPrincipal httpPrincipal, long batchPlannerPlanId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPolicyServiceUtil.class, "hasBatchPlannerPolicy",
				_hasBatchPlannerPolicyParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId, name);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerPolicy
			updateBatchPlannerPolicy(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId,
				String name, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerPolicyServiceUtil.class, "updateBatchPlannerPolicy",
				_updateBatchPlannerPolicyParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId, name, value);

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

			return (com.liferay.batch.planner.model.BatchPlannerPolicy)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BatchPlannerPolicyServiceHttp.class);

	private static final Class<?>[] _addBatchPlannerPolicyParameterTypes0 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _deleteBatchPlannerPolicyParameterTypes1 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getBatchPlannerPoliciesParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getBatchPlannerPolicyParameterTypes3 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _hasBatchPlannerPolicyParameterTypes4 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateBatchPlannerPolicyParameterTypes5 =
		new Class[] {long.class, String.class, String.class};

}
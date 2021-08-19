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

import com.liferay.batch.planner.service.BatchPlannerMappingServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>BatchPlannerMappingServiceUtil</code> service
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
 * @see BatchPlannerMappingServiceSoap
 * @generated
 */
public class BatchPlannerMappingServiceHttp {

	public static com.liferay.batch.planner.model.BatchPlannerMapping
			addBatchPlannerMapping(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId,
				String externalFieldName, String externalFieldType,
				String internalFieldName, String internalFieldType,
				String script)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerMappingServiceUtil.class, "addBatchPlannerMapping",
				_addBatchPlannerMappingParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId, externalFieldName,
				externalFieldType, internalFieldName, internalFieldType,
				script);

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

			return (com.liferay.batch.planner.model.BatchPlannerMapping)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerMapping
			deleteBatchPlannerMapping(
				HttpPrincipal httpPrincipal, long batchPlannerPlanId,
				String externalFieldName, String internalFieldName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerMappingServiceUtil.class,
				"deleteBatchPlannerMapping",
				_deleteBatchPlannerMappingParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerPlanId, externalFieldName,
				internalFieldName);

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

			return (com.liferay.batch.planner.model.BatchPlannerMapping)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.batch.planner.model.BatchPlannerMapping>
				getBatchPlannerMappings(
					HttpPrincipal httpPrincipal, long batchPlannerPlanId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerMappingServiceUtil.class, "getBatchPlannerMappings",
				_getBatchPlannerMappingsParameterTypes2);

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
				<com.liferay.batch.planner.model.BatchPlannerMapping>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.batch.planner.model.BatchPlannerMapping
			updateBatchPlannerMapping(
				HttpPrincipal httpPrincipal, long batchPlannerMappingId,
				String externalFieldName, String externalFieldType,
				String script)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				BatchPlannerMappingServiceUtil.class,
				"updateBatchPlannerMapping",
				_updateBatchPlannerMappingParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, batchPlannerMappingId, externalFieldName,
				externalFieldType, script);

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

			return (com.liferay.batch.planner.model.BatchPlannerMapping)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BatchPlannerMappingServiceHttp.class);

	private static final Class<?>[] _addBatchPlannerMappingParameterTypes0 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class
		};
	private static final Class<?>[] _deleteBatchPlannerMappingParameterTypes1 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _getBatchPlannerMappingsParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _updateBatchPlannerMappingParameterTypes3 =
		new Class[] {long.class, String.class, String.class, String.class};

}
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

package com.liferay.cluster.test.module.internel;

import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.concurrent.Future;

/**
 * @author Tina Tian
 */
public class ClusterMethodInvoker {

	public static void invokeMethodModule(
		ClusterExecutor clusterExecutor, ClusterNode targetClusterNode) {

		try {
			MethodKey methodKey = new MethodKey(
				ClusterTestClass.class, "getPortalLocalPort", new Class<?>[0]);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, new Object[0]);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler,
				new String[] {targetClusterNode.getClusterNodeId()});

			FutureClusterResponses futureClusterResponses =
				clusterExecutor.execute(clusterRequest);

			ClusterNodeResponses clusterNodeResponses =
				futureClusterResponses.get();

			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponses.getClusterResponse(
					targetClusterNode.getClusterNodeId());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Result of invoke-method-module is :" +
						clusterNodeResponse.getResult());
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public static void invokeMethodModuleOnMaster(
		ClusterMasterExecutor clusterMasterExecutor) {

		try {
			MethodKey methodKey = new MethodKey(
				ClusterTestClass.class, "getPortalLocalPort", new Class<?>[0]);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, new Object[0]);

			Future<Integer> future = clusterMasterExecutor.executeOnMaster(
				methodHandler);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Result of invoke-method-module-on-master is :" +
						future.get());
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public static void invokeMethodPortal(
		ClusterExecutor clusterExecutor, ClusterNode targetClusterNode) {

		try {
			MethodKey methodKey = new MethodKey(
				PortalUtil.class, "getPortalLocalPort",
				new Class<?>[] {Boolean.TYPE});

			MethodHandler methodHandler = new MethodHandler(
				methodKey, new Object[] {Boolean.valueOf(false)});

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler,
				new String[] {targetClusterNode.getClusterNodeId()});

			FutureClusterResponses futureClusterResponses =
				clusterExecutor.execute(clusterRequest);

			ClusterNodeResponses clusterNodeResponses =
				futureClusterResponses.get();

			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponses.getClusterResponse(
					targetClusterNode.getClusterNodeId());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Result of invoke-method-portal is :" +
						clusterNodeResponse.getResult());
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public static void invokeMethodPortalOnMaster(
		ClusterMasterExecutor clusterMasterExecutor) {

		try {
			MethodKey methodKey = new MethodKey(
				PortalUtil.class, "getPortalLocalPort",
				new Class<?>[] {Boolean.TYPE});

			MethodHandler methodHandler = new MethodHandler(
				methodKey, new Object[] {Boolean.valueOf(false)});

			Future<Integer> future = clusterMasterExecutor.executeOnMaster(
				methodHandler);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Result of invoke-method-portal-on-master is :" +
						future.get());
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterMethodInvoker.class);

}
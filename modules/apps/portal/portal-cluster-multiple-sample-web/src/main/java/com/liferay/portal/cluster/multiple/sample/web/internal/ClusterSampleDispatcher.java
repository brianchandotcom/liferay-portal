/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.sample.web.internal;

import com.liferay.portal.cluster.multiple.sample.web.internal.configuration.ClusterSampleConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
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
import com.liferay.portal.kernel.util.Validator;

import java.util.concurrent.Future;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.cluster.multiple.sample.web.internal.configuration.ClusterSampleConfiguration",
	service = {}
)
public class ClusterSampleDispatcher {

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		ClusterSampleConfiguration clusterSampleConfiguration =
			(ClusterSampleConfiguration)ConfigurableUtil.createConfigurable(
				ClusterSampleConfiguration.class,
				componentContext.getProperties());

		String clusterSampleCommand =
			clusterSampleConfiguration.clusterSampleCommand();

		if (Validator.isNull(clusterSampleCommand)) {
			if (_log.isInfoEnabled()) {
				_log.info("Cluster sample command is null");
			}

			return;
		}

		try {
			if (clusterSampleCommand.equals("invoke-method-module")) {
				_invokeMethodModule(_clusterExecutor, _getTargetClusterNode());
			}
			else if (clusterSampleCommand.equals(
						"invoke-method-module-on-master")) {

				_invokeMethodModuleOnMaster(_clusterMasterExecutor);
			}
			else if (clusterSampleCommand.equals("invoke-method-portal")) {
				_invokeMethodPortal(_clusterExecutor, _getTargetClusterNode());
			}
			else if (clusterSampleCommand.equals(
						"invoke-method-portal-on-master")) {

				_invokeMethodPortalOnMaster(_clusterMasterExecutor);
			}
			else {
				_log.error(
					"Unable to find cluster sample command " +
						clusterSampleCommand);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private ClusterNode _getTargetClusterNode() throws Exception {
		ClusterNode localClusterNode = _clusterExecutor.getLocalClusterNode();

		for (ClusterNode clusterNode : _clusterExecutor.getClusterNodes()) {
			if (!clusterNode.equals(localClusterNode)) {
				return clusterNode;
			}
		}

		throw new Exception("Unable to get target cluster node");
	}

	private void _invokeMethodModule(
		ClusterExecutor clusterExecutor, ClusterNode targetClusterNode) {

		try {
			MethodKey methodKey = new MethodKey(
				ClusterSampleClass.class, "getPortalLocalPort",
				new Class<?>[0]);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, new Object[0]);

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler,
				new String[] {targetClusterNode.getClusterNodeId()});

			FutureClusterResponses futureClusterResponses =
				clusterExecutor.execute(clusterRequest);

			if (_log.isInfoEnabled()) {
				ClusterNodeResponses clusterNodeResponses =
					futureClusterResponses.get();

				ClusterNodeResponse clusterNodeResponse =
					clusterNodeResponses.getClusterResponse(
						targetClusterNode.getClusterNodeId());

				_log.info(
					"Result of invoke-method-module: " +
						clusterNodeResponse.getResult());
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _invokeMethodModuleOnMaster(
		ClusterMasterExecutor clusterMasterExecutor) {

		try {
			MethodKey methodKey = new MethodKey(
				ClusterSampleClass.class, "getPortalLocalPort",
				new Class<?>[0]);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, new Object[0]);

			Future<Integer> future = clusterMasterExecutor.executeOnMaster(
				methodHandler);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Result of invoke-method-module-on-master: " +
						future.get());
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _invokeMethodPortal(
		ClusterExecutor clusterExecutor, ClusterNode targetClusterNode) {

		try {
			MethodKey methodKey = new MethodKey(
				PortalUtil.class, "getPortalLocalPort",
				new Class<?>[] {Boolean.TYPE});

			MethodHandler methodHandler = new MethodHandler(
				methodKey, new Object[] {Boolean.FALSE});

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				methodHandler,
				new String[] {targetClusterNode.getClusterNodeId()});

			FutureClusterResponses futureClusterResponses =
				clusterExecutor.execute(clusterRequest);

			if (_log.isInfoEnabled()) {
				ClusterNodeResponses clusterNodeResponses =
					futureClusterResponses.get();

				ClusterNodeResponse clusterNodeResponse =
					clusterNodeResponses.getClusterResponse(
						targetClusterNode.getClusterNodeId());

				_log.info(
					"Result of invoke-method-portal: " +
						clusterNodeResponse.getResult());
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _invokeMethodPortalOnMaster(
		ClusterMasterExecutor clusterMasterExecutor) {

		try {
			MethodKey methodKey = new MethodKey(
				PortalUtil.class, "getPortalLocalPort",
				new Class<?>[] {Boolean.TYPE});

			MethodHandler methodHandler = new MethodHandler(
				methodKey, new Object[] {Boolean.FALSE});

			Future<Integer> future = clusterMasterExecutor.executeOnMaster(
				methodHandler);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Result of invoke-method-portal-on-master: " +
						future.get());
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterSampleDispatcher.class);

	@Reference
	private ClusterExecutor _clusterExecutor;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

}
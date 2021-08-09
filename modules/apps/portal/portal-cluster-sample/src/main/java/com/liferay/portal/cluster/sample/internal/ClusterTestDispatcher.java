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

package com.liferay.portal.cluster.sample.internal;

import com.liferay.portal.cluster.sample.configuration.ClusterTestConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.cluster.sample.configuration.ClusterTestConfiguration",
	immediate = true, service = {}
)
public class ClusterTestDispatcher {

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		ClusterTestConfiguration clusterTestConfiguration =
			(ClusterTestConfiguration)ConfigurableUtil.createConfigurable(
				ClusterTestConfiguration.class,
				componentContext.getProperties());

		String clusterTestCommand =
			clusterTestConfiguration.clusterTestCommand();

		if (Validator.isNull(clusterTestCommand)) {
			if (_log.isInfoEnabled()) {
				_log.info("Cluster Test Command is null");
			}

			return;
		}

		try {
			if (clusterTestCommand.equals("invoke-method-module")) {
				ClusterMethodInvoker.invokeMethodModule(
					_clusterExecutor, getTargetClusterNode());
			}
			else if (clusterTestCommand.equals(
						"invoke-method-module-on-master")) {

				ClusterMethodInvoker.invokeMethodModuleOnMaster(
					_clusterMasterExecutor);
			}
			else if (clusterTestCommand.equals("invoke-method-portal")) {
				ClusterMethodInvoker.invokeMethodPortal(
					_clusterExecutor, getTargetClusterNode());
			}
			else if (clusterTestCommand.equals(
						"invoke-method-portal-on-master")) {

				ClusterMethodInvoker.invokeMethodPortalOnMaster(
					_clusterMasterExecutor);
			}
			else {
				_log.error("Unable to find command " + clusterTestCommand);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	protected ClusterNode getTargetClusterNode() throws Exception {
		ClusterNode localClusterNode = _clusterExecutor.getLocalClusterNode();

		for (ClusterNode clusterNode : _clusterExecutor.getClusterNodes()) {
			if (!clusterNode.equals(localClusterNode)) {
				return clusterNode;
			}
		}

		throw new Exception("Unable to find another cluster node");
	}

	@Reference(unbind = "-")
	protected void setClusterExecutor(ClusterExecutor clusterExecutor) {
		_clusterExecutor = clusterExecutor;
	}

	@Reference(unbind = "-")
	protected void setClusterMasterExecutor(
		ClusterMasterExecutor clusterMasterExecutor) {

		_clusterMasterExecutor = clusterMasterExecutor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterTestDispatcher.class);

	private ClusterExecutor _clusterExecutor;
	private ClusterMasterExecutor _clusterMasterExecutor;

}
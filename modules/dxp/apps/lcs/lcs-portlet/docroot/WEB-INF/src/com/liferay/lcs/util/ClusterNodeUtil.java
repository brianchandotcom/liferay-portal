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

package com.liferay.lcs.util;

import com.liferay.lcs.advisor.InstallationEnvironmentAdvisor;
import com.liferay.lcs.advisor.InstallationEnvironmentAdvisorFactory;
import com.liferay.lcs.rest.client.LCSClusterNode;
import com.liferay.lcs.rest.LCSClusterNodeServiceUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class ClusterNodeUtil {

	public static Map<String, Object> getClusterNodeInfo() {
		if (_log.isDebugEnabled()) {
			_log.debug("Get cluster node information");
		}

		Map<String, Object> clusterNodeInfo = new HashMap<>();

		try {
			ClusterNode localClusterNode =
				ClusterExecutorUtil.getLocalClusterNode();

			InetAddress inetAddress = localClusterNode.getBindInetAddress();

			clusterNodeInfo.put("address", inetAddress.getHostAddress());

			clusterNodeInfo.put("key", _keyGenerator.getKey());

			if (LCSPortletPreferencesUtil.isCredentialsSet()) {
				LCSUtil.setUpJSONWebServiceClientCredentials();

				clusterNodeInfo.put(
					"registered", LCSUtil.isLCSClusterNodeRegistered());
			}
			else {
				clusterNodeInfo.put("registered", false);
			}

			clusterNodeInfo.put("ready", LCSConnectionManagerUtil.isReady());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Cluster node " + MapUtil.toString(clusterNodeInfo));
		}

		return clusterNodeInfo;
	}

	public static List<Map<String, Object>> getClusterNodeInfos()
		throws Exception {

		List<Map<String, Object>> clusterNodeInfos = new ArrayList<>();

		try {
			ClusterNode localClusterNode =
				ClusterExecutorUtil.getLocalClusterNode();

			String localClusterNodeId = localClusterNode.getClusterNodeId();

			List<ClusterNode> clusterNodes =
				ClusterExecutorUtil.getClusterNodes();

			for (ClusterNode clusterNode : clusterNodes) {
				String clusterNodeId = clusterNode.getClusterNodeId();

				if (clusterNodeId.equals(localClusterNodeId)) {
					continue;
				}

				Map<String, Object> clusterNodeInfo = new HashMap<>();

				if (_hasClusterNodeLCSPortletServletContext(clusterNodeId)) {
					clusterNodeInfo = _getClusterNodeInfo(clusterNodeId);
				}
				else {
					clusterNodeInfo.put("lcsPortletMissing", null);
					clusterNodeInfo.put("registered", false);
				}

				clusterNodeInfo.put("clusterNodeId", clusterNodeId);

				clusterNodeInfos.add(clusterNodeInfo);
			}
		}
		catch (ClassNotFoundException cnfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(cnfe.getMessage(), cnfe);
			}
		}

		return clusterNodeInfos;
	}

	public static List<String> getRegisteredClusterNodeKeys() throws Exception {
		List<String> clusterNodeKeys = new ArrayList<>();

		if (!ClusterExecutorUtil.isEnabled()) {
			return clusterNodeKeys;
		}

		List<Map<String, Object>> clusterNodeInfos = getClusterNodeInfos();

		for (Map<String, Object> clusterNodeInfo : clusterNodeInfos) {
			if (GetterUtil.getBoolean(clusterNodeInfo.get("registered"))) {
				clusterNodeKeys.add((String)clusterNodeInfo.get("key"));
			}
		}

		return clusterNodeKeys;
	}

	public static String registerClusterNode(long lcsClusterEntryId)
		throws Exception {

		return registerClusterNode(
			lcsClusterEntryId, _generateLCSClusterNodeName(), StringPool.BLANK,
			StringPool.BLANK);
	}

	public static String registerClusterNode(
			long lcsClusterEntryId, String name, String description,
			String location)
		throws Exception {

		InstallationEnvironmentAdvisor installationEnvironmentAdvisor =
			InstallationEnvironmentAdvisorFactory.getInstance();

		LCSClusterNode lcsClusterNode =
			LCSClusterNodeServiceUtil.addLCSClusterNode(
				lcsClusterEntryId, name, description,
				ReleaseInfo.getBuildNumber(), location,
				installationEnvironmentAdvisor.getProcessorCoresTotal());

		return lcsClusterNode.getKey();
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	private static String _generateLCSClusterNodeName() {
		return LicenseManagerUtil.getHostName() + StringPool.DASH +
			System.currentTimeMillis();
	}

	private static Map<String, Object> _getClusterNodeInfo(String clusterNodeId)
		throws Exception {

		ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
			_getClusterNodeInfoMethodHandler, clusterNodeId);

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		ClusterNodeResponses clusterNodeResponses = futureClusterResponses.get(
			20000, TimeUnit.MILLISECONDS);

		ClusterNodeResponse clusterNodeResponse =
			clusterNodeResponses.getClusterResponse(clusterNodeId);

		return (Map<String, Object>)clusterNodeResponse.getResult();
	}

	private static boolean _hasClusterNodeLCSPortletServletContext(
			String clusterNodeId)
		throws Exception {

		ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
			_containsKeyMethodHandler, clusterNodeId);

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		ClusterNodeResponses clusterNodeResponses = futureClusterResponses.get(
			20000, TimeUnit.MILLISECONDS);

		ClusterNodeResponse clusterNodeResponse =
			clusterNodeResponses.getClusterResponse(clusterNodeId);

		return (Boolean)clusterNodeResponse.getResult();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterNodeUtil.class);

	private static final MethodHandler _containsKeyMethodHandler =
		new MethodHandler(
			new MethodKey(
				ServletContextPool.class.getName(), "containsKey",
				String.class),
			"lcs-portlet");
	private static final MethodHandler _getClusterNodeInfoMethodHandler =
		new MethodHandler(
			new MethodKey(ClusterNodeUtil.class, "getClusterNodeInfo"));
	private static KeyGenerator _keyGenerator;

}
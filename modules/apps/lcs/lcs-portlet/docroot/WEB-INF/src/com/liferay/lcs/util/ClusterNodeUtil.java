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
import com.liferay.lcs.rest.LCSClusterNode;
import com.liferay.lcs.rest.LCSClusterNodeServiceUtil;
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
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
			clusterNodeInfo.put("key", KeyGeneratorUtil.getKey());

			if (LCSUtil.getCredentialsStatus() == LCSUtil.CREDENTIALS_SET) {
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

				if (hasClusterNodeLCSPortletServletContext(clusterNodeId)) {
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

	public static boolean isFirstClusterNode() throws Exception {
		if (!ClusterExecutorUtil.isEnabled()) {
			return false;
		}

		if (_getSiblingKey() != null) {
			return false;
		}

		return true;
	}

	public static String registerClusterNode() throws Exception {
		if (!ClusterExecutorUtil.isEnabled()) {
			throw new ClusterException("Not in cluster environment");
		}

		String siblingKey = _getSiblingKey();

		if (siblingKey == null) {
			throw new ClusterException("No sibling nodes in cluster");
		}

		InstallationEnvironmentAdvisor installationEnvironmentAdvisor =
			InstallationEnvironmentAdvisorFactory.getInstance();

		LCSClusterNode lcsClusterNode =
			LCSClusterNodeServiceUtil.addLCSClusterNode(
				siblingKey, generateLCSClusterNodeName(), StringPool.BLANK,
				KeyGeneratorUtil.getKey(),
				StringUtil.merge(LicenseManagerUtil.getIpAddresses()),
				installationEnvironmentAdvisor.getProcessorCoresTotal());

		LCSUtil.sendServiceAvailabilityNotification(
			LCSPortletState.NO_SUBSCRIPTION);

		return lcsClusterNode.getKey();
	}

	public static String registerClusterNode(long lcsClusterEntryId)
		throws Exception {

		return registerClusterNode(
			lcsClusterEntryId, generateLCSClusterNodeName(), StringPool.BLANK,
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
				ReleaseInfo.getBuildNumber(), KeyGeneratorUtil.getKey(),
				location,
				installationEnvironmentAdvisor.getProcessorCoresTotal());

		LCSUtil.sendServiceAvailabilityNotification(
			LCSPortletState.NO_SUBSCRIPTION);

		return lcsClusterNode.getKey();
	}

	public static Map<String, Object> registerUnregisteredClusterNodes(
			String siblingKey)
		throws Exception {

		if ((siblingKey == null) || !ClusterExecutorUtil.isEnabled()) {
			return Collections.emptyMap();
		}

		Map<String, Object> map = new HashMap<>();

		MethodHandler registerClusterNodeMethodHandler = new MethodHandler(
			_registerClusterNodeMethodKey, siblingKey);

		List<String> unregisteredClusterNodeIds =
			_getUnregisteredClusterNodeIds();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Register " + unregisteredClusterNodeIds.size() +
					" sibling cluster nodes");
		}

		for (String clusterNodeId : unregisteredClusterNodeIds) {
			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				registerClusterNodeMethodHandler, clusterNodeId);

			FutureClusterResponses futureClusterResponses =
				ClusterExecutorUtil.execute(clusterRequest);

			ClusterNodeResponses clusterNodeResponses =
				futureClusterResponses.get(20000, TimeUnit.MILLISECONDS);

			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponses.getClusterResponse(clusterNodeId);

			Map<String, Object> result =
				(Map<String, Object>)clusterNodeResponse.getResult();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Invoked register cluster node on cluster node " +
						clusterNodeId);
			}

			for (Map.Entry<String, Object> entry : result.entrySet()) {
				map.put(
					clusterNodeId + StringPool.UNDERLINE + entry.getKey(),
					entry.getValue());
			}
		}

		return map;
	}

	public static void restartPosts(boolean applyToSiblingClusterNodes)
		throws Exception {

		stopPosts(applyToSiblingClusterNodes);

		try {
			Thread.sleep(5000);
		}
		catch (Exception e) {
		}

		startPosts(applyToSiblingClusterNodes);
	}

	public static void startPosts(boolean applyToSiblingClusterNodes)
		throws Exception {

		LCSConnectionManagerUtil.start();

		if (!applyToSiblingClusterNodes || !ClusterExecutorUtil.isEnabled()) {
			return;
		}

		invokeOnSiblingClusterNodes(_startPostsMethodKey);
	}

	public static void stopPosts(boolean applyToSiblingClusterNodes)
		throws Exception {

		LCSConnectionManagerUtil.stop();

		if (!applyToSiblingClusterNodes || !ClusterExecutorUtil.isEnabled()) {
			return;
		}

		invokeOnSiblingClusterNodes(_stopPostsMethodKey);
	}

	protected static String _getSiblingKey() throws Exception {
		List<Map<String, Object>> clusterNodeInfos = getClusterNodeInfos();

		for (Map<String, Object> clusterNodeInfo : clusterNodeInfos) {
			if (GetterUtil.getBoolean(clusterNodeInfo.get("registered"))) {
				return (String)clusterNodeInfo.get("key");
			}
		}

		return null;
	}

	protected static void invokeOnSiblingClusterNodes(MethodKey remoteMethodKey)
		throws Exception {

		ClusterNode localClusterNode =
			ClusterExecutorUtil.getLocalClusterNode();

		String localClusterNodeId = localClusterNode.getClusterNodeId();

		List<String> registeredClusterNodeIds = new ArrayList<>();

		List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

		if (_log.isDebugEnabled()) {
			_log.debug("Filter registered cluster nodes");
		}

		for (ClusterNode clusterNode : clusterNodes) {
			String clusterNodeId = clusterNode.getClusterNodeId();

			if (!clusterNodeId.equals(localClusterNodeId)) {
				Map<String, Object> clusterNodeInfo = _getClusterNodeInfo(
					clusterNodeId);

				if ((Boolean)clusterNodeInfo.get("registered")) {
					registeredClusterNodeIds.add(clusterNodeId);
				}
			}
		}

		MethodHandler remoteMethodHandler = new MethodHandler(remoteMethodKey);

		for (String clusterNodeId : registeredClusterNodeIds) {
			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				remoteMethodHandler, clusterNodeId);

			ClusterExecutorUtil.execute(clusterRequest);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Invoked " + remoteMethodKey.getMethodName() +
						" on cluster node " + clusterNodeId);
			}
		}
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

	private static String _getServletContextName() {
		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		return ClassLoaderPool.getContextName(classLoader);
	}

	private static List<String> _getUnregisteredClusterNodeIds()
		throws Exception {

		List<String> unregisteredClusterNodeIds = new ArrayList<>();

		List<Map<String, Object>> clusterNodeInfos = getClusterNodeInfos();

		for (Map<String, Object> clusterNodeInfo : clusterNodeInfos) {
			if (clusterNodeInfo.containsKey("lcsPortletMissing")) {
				continue;
			}

			boolean registered = (Boolean)clusterNodeInfo.get("registered");

			if (!registered) {
				String clusterNodeId = (String)clusterNodeInfo.get(
					"clusterNodeId");

				unregisteredClusterNodeIds.add(clusterNodeId);
			}
		}

		return unregisteredClusterNodeIds;
	}

	@SuppressWarnings("unused")
	private static Map<String, Object> _registerClusterNode(String siblingKey) {
		if (_log.isDebugEnabled()) {
			_log.debug("Register cluster node via cluster executor");
		}

		Map<String, Object> clusterNodeInfo = new HashMap<>();

		String key = KeyGeneratorUtil.getKey();

		try {
			LCSUtil.setUpJSONWebServiceClientCredentials();

			InstallationEnvironmentAdvisor installationEnvironmentAdvisor =
				InstallationEnvironmentAdvisorFactory.getInstance();

			LCSClusterNodeServiceUtil.addLCSClusterNode(
				siblingKey, generateLCSClusterNodeName(), StringPool.BLANK, key,
				StringUtil.merge(LicenseManagerUtil.getIpAddresses()),
				installationEnvironmentAdvisor.getProcessorCoresTotal());

			LCSUtil.sendServiceAvailabilityNotification(
				LCSPortletState.NO_SUBSCRIPTION);

			LCSConnectionManagerUtil.start();

			clusterNodeInfo.put("success", key);
		}
		catch (Exception e) {
			_log.error(e, e);

			clusterNodeInfo.put("error", key);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Register cluster node " + MapUtil.toString(clusterNodeInfo));
		}

		return clusterNodeInfo;
	}

	@SuppressWarnings("unused")
	private static void _restartPosts() {
		if (_log.isDebugEnabled()) {
			_log.debug("Restart posting data from this cluster node");
		}

		try {
			LCSConnectionManagerUtil.restart();

			if (_log.isDebugEnabled()) {
				_log.debug("Restarted posts");
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@SuppressWarnings("unused")
	private static void _startPosts() {
		if (_log.isDebugEnabled()) {
			_log.debug("Start posting data from this cluster node");
		}

		try {
			LCSUtil.setUpJSONWebServiceClientCredentials();

			LCSConnectionManagerUtil.start();

			if (_log.isDebugEnabled()) {
				_log.debug("Started posts");
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@SuppressWarnings("unused")
	private static void _stopPosts() {
		if (_log.isDebugEnabled()) {
			_log.debug("Stop posting data from this cluster node");
		}

		try {
			LCSConnectionManagerUtil.stop();

			if (_log.isDebugEnabled()) {
				_log.debug("Stopped posts");
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static String generateLCSClusterNodeName() {
		return
			LicenseManagerUtil.getHostName() + StringPool.DASH +
				System.currentTimeMillis();
	}

	private static boolean hasClusterNodeLCSPortletServletContext(
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

	private static Log _log = LogFactoryUtil.getLog(ClusterNodeUtil.class);

	private static MethodHandler _containsKeyMethodHandler = new MethodHandler(
		new MethodKey(
			ServletContextPool.class.getName(), "containsKey",
			String.class),
		"lcs-portlet");
	private static MethodHandler _getClusterNodeInfoMethodHandler =
		new MethodHandler(
			new MethodKey(ClusterNodeUtil.class, "getClusterNodeInfo"));
	private static MethodKey _registerClusterNodeMethodKey = new MethodKey(
		ClusterNodeUtil.class, "_registerClusterNode", String.class);
	private static MethodKey _restartPostsMethodKey = new MethodKey(
		ClusterNodeUtil.class, "_restartPosts");
	private static MethodKey _startPostsMethodKey = new MethodKey(
		ClusterNodeUtil.class, "_startPosts");
	private static MethodKey _stopPostsMethodKey = new MethodKey(
		ClusterNodeUtil.class, "_stopPosts");

}
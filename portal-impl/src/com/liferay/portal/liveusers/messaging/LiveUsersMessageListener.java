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

package com.liferay.portal.liveusers.messaging;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.concurrent.BaseFutureListener;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.liveusers.LiveUsers;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 */
public class LiveUsersMessageListener extends BaseMessageListener {

	protected void doCommandAddClusterNode(JSONObject jsonObject)
		throws Exception {

		String clusterNodeId = jsonObject.getString("clusterNodeId");

		ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
			_getLocalClusterUsersMethodHandler, clusterNodeId);

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		futureClusterResponses.addFutureListener(
			new LiveUsersClusterResponseFutureListener(clusterNodeId));
	}

	protected void doCommandRemoveClusterNode(JSONObject jsonObject)
		throws Exception {

		String clusterNodeId = jsonObject.getString("clusterNodeId");

		LiveUsers.removeClusterNode(clusterNodeId);
	}

	protected void doCommandSignIn(JSONObject jsonObject) throws Exception {
		String clusterNodeId = jsonObject.getString("clusterNodeId");
		long companyId = jsonObject.getLong("companyId");
		long userId = jsonObject.getLong("userId");
		String sessionId = jsonObject.getString("sessionId");
		String remoteAddr = jsonObject.getString("remoteAddr");
		String remoteHost = jsonObject.getString("remoteHost");
		String userAgent = jsonObject.getString("userAgent");

		LiveUsers.signIn(
			clusterNodeId, companyId, userId, sessionId, remoteAddr, remoteHost,
			userAgent);
	}

	protected void doCommandSignOut(JSONObject jsonObject) throws Exception {
		String clusterNodeId = jsonObject.getString("clusterNodeId");
		long companyId = jsonObject.getLong("companyId");
		long userId = jsonObject.getLong("userId");
		String sessionId = jsonObject.getString("sessionId");

		LiveUsers.signOut(clusterNodeId, companyId, userId, sessionId);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String payload = (String)message.getPayload();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(payload);

		String command = jsonObject.getString("command");

		if (command.equals("addClusterNode")) {
			doCommandAddClusterNode(jsonObject);
		}
		else if (command.equals("removeClusterNode")) {
			doCommandRemoveClusterNode(jsonObject);
		}
		else if (command.equals("signIn")) {
			doCommandSignIn(jsonObject);
		}
		else if (command.equals("signOut")) {
			doCommandSignOut(jsonObject);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiveUsersMessageListener.class);

	private static final MethodHandler _getLocalClusterUsersMethodHandler =
		new MethodHandler(
			new MethodKey(LiveUsers.class, "getLocalClusterUsers"));

	private static class LiveUsersClusterResponseFutureListener
		extends BaseFutureListener<ClusterNodeResponses> {

		public LiveUsersClusterResponseFutureListener(String clusterNodeId) {
			_clusterNodeId = clusterNodeId;
		}

		@Override
		public void completeWithException(
			Future<ClusterNodeResponses> future, Throwable throwable) {

			_log.error(
				"Uanble to add cluster node " + _clusterNodeId, throwable);
		}

		@Override
		public void completeWithResult(
			Future<ClusterNodeResponses> future,
			ClusterNodeResponses clusterNodeResponses) {

			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponses.getClusterResponse(_clusterNodeId);

			try {
				Object result = clusterNodeResponse.getResult();

				if (result == null) {
					return;
				}

				Map<Long, Map<Long, Set<String>>> clusterUsers =
					(Map<Long, Map<Long, Set<String>>>)result;

				LiveUsers.addClusterNode(_clusterNodeId, clusterUsers);
			}
			catch (Exception e) {
				_log.error("Unable to add cluster node " + _clusterNodeId, e);
			}
		}

		private final String _clusterNodeId;

	}

}
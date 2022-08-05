/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.oauth;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cristina González
 */
public class AccessTokenStoreUtil {

	public static void add(
		long companyId, long userId, AccessToken accessToken) {

		_add(companyId, userId, accessToken);

		if (ClusterExecutorUtil.isEnabled()) {
			_executeOnCluster(
				new MethodHandler(
					_addMethodKey, companyId, userId, accessToken));
		}
	}

	public static void delete(long companyId, long userId) {
		_delete(companyId, userId);

		if (ClusterExecutorUtil.isEnabled()) {
			_executeOnCluster(
				new MethodHandler(_deleteMethodKey, companyId, userId));
		}
	}

	public static Optional<AccessToken> getAccessTokenOptional(
		long companyId, long userId) {

		Map<Long, AccessToken> companyAccessTokens =
			_accessTokenMap.getOrDefault(companyId, new HashMap<>());

		return Optional.ofNullable(companyAccessTokens.get(userId));
	}

	private static void _add(
		long companyId, long userId, AccessToken accessToken) {

		Map<Long, AccessToken> companyAccessTokens =
			_accessTokenMap.computeIfAbsent(
				companyId, key -> new ConcurrentHashMap<>());

		companyAccessTokens.put(userId, accessToken);
	}

	private static void _delete(long companyId, long userId) {
		Map<Long, AccessToken> companyAccessTokens =
			_accessTokenMap.computeIfAbsent(
				companyId, key -> new ConcurrentHashMap<>());

		companyAccessTokens.remove(userId);
	}

	private static void _executeOnCluster(MethodHandler methodHandler) {
		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			methodHandler, true);

		clusterRequest.setFireAndForget(true);

		ClusterExecutorUtil.execute(clusterRequest);
	}

	private static final Map<Long, Map<Long, AccessToken>> _accessTokenMap =
		new ConcurrentHashMap<>();
	private static final MethodKey _addMethodKey = new MethodKey(
		AccessTokenStoreUtil.class, "_add", long.class, long.class,
		AccessToken.class);
	private static final MethodKey _deleteMethodKey = new MethodKey(
		AccessTokenStoreUtil.class, "_delete", long.class, long.class);

}
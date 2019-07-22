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

package com.liferay.document.library.opener.one.drive.web.internal.oauth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cristina González
 */
public class AccessTokenStore {

	public void add(long companyId, long userId, AccessToken accessToken) {
		Map<Long, AccessToken> companyAccessTokenMap =
			_accessTokenMap.computeIfAbsent(
				companyId, key -> new ConcurrentHashMap<>());

		companyAccessTokenMap.put(userId, accessToken);
	}

	public void delete(long companyId, long userId) {
		Map<Long, AccessToken> companyAccessTokenMap =
			_accessTokenMap.computeIfAbsent(
				companyId, key -> new ConcurrentHashMap<>());

		companyAccessTokenMap.remove(userId);
	}

	public Optional<AccessToken> getAccessTokenOptional(
		long companyId, long userId) {

		Map<Long, AccessToken> companyAccessTokenMap =
			_accessTokenMap.getOrDefault(companyId, new HashMap<>());

		return Optional.ofNullable(companyAccessTokenMap.get(userId));
	}

	private final Map<Long, Map<Long, AccessToken>> _accessTokenMap =
		new ConcurrentHashMap<>();

}
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.auth.AuthenticatedUserUUIDStore;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class DefaultAuthenticatedUserUUIDStoreImpl
	implements AuthenticatedUserUUIDStore {

	@Override
	public boolean exists(String userUUID) {
		return _userUUIDStore.contains(userUUID);
	}

	@Override
	public boolean register(String userUUID) {
		return _userUUIDStore.add(userUUID);
	}

	@Override
	public boolean unregister(String userUUID) {
		return _userUUIDStore.remove(userUUID);
	}

	private final Set<String> _userUUIDStore = Collections.newSetFromMap(
		new ConcurrentHashMap<>());

}
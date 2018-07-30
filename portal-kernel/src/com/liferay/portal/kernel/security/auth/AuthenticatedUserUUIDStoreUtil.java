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

package com.liferay.portal.kernel.security.auth;

/**
 * @author     Michael C. Han
 * @author     Raymond Augé
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class AuthenticatedUserUUIDStoreUtil {

	public static boolean exists(String userUUID) {
		return getAuthenticatedUserUUIDStore().exists(userUUID);
	}

	public static AuthenticatedUserUUIDStore getAuthenticatedUserUUIDStore() {
		return _authenticatedUserUUIDStore;
	}

	public static boolean register(String userUUID) {
		return getAuthenticatedUserUUIDStore().register(userUUID);
	}

	public static boolean unregister(String userUUID) {
		return getAuthenticatedUserUUIDStore().unregister(userUUID);
	}

	public void setAuthenticatedUserUUIDStore(
		AuthenticatedUserUUIDStore authenticatedUserUUIDStore) {

		_authenticatedUserUUIDStore = authenticatedUserUUIDStore;
	}

	private static AuthenticatedUserUUIDStore _authenticatedUserUUIDStore;

}
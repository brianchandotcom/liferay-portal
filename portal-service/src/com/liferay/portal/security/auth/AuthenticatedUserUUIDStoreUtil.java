/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * @author Michael C. Han
 */
public class AuthenticatedUserUUIDStoreUtil {

	public static boolean exists(String userUUID) {
		return _authenticatedUserUUIDStore.exists(userUUID);
	}

	public static boolean register(String userUUID) {
		return _authenticatedUserUUIDStore.register(userUUID);
	}

	public static boolean unregister(String userUUID) {
		return _authenticatedUserUUIDStore.unregister(userUUID);
	}

	public void setAuthenticatedUserUUIDStore(
		AuthenticatedUserUUIDStore authenticatedUserUUIDStore) {

		_authenticatedUserUUIDStore = authenticatedUserUUIDStore;
	}

	private static AuthenticatedUserUUIDStore _authenticatedUserUUIDStore;

}
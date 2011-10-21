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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Mate Thurzo
 */
public class SubscriptionPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException, SystemException {

		getSubscriptionPermission().check(
			permissionChecker, className, classPK);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException, SystemException {

		return getSubscriptionPermission().contains(
			permissionChecker, className, classPK);
	}

	public static SubscriptionPermission getSubscriptionPermission() {
		return _subscriptionPermission;
	}

	public void setSubscriptionPermission(
		SubscriptionPermission subscriptionPermission) {

		_subscriptionPermission = subscriptionPermission;
	}

	private static SubscriptionPermission _subscriptionPermission;

}
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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;

/**
 * @author Mate Thurzo
 */
public class SubscriptionPermissionUtil {

	public static void check(User user, String className, long classPK,
			String actionId)
		throws PortalException, SystemException {

		getSubscriptionPermission().check(user, className, classPK, actionId);
	}

	public static boolean contains(User user, String className, long classPK,
			String actionId)
		throws PortalException, SystemException {

		return getSubscriptionPermission().contains(
				user, className, classPK, actionId);
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
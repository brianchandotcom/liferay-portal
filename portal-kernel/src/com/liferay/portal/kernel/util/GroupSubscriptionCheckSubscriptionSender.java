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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourcePermissionCheckerUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;

/**
 * @author Roberto Díaz
 */
public class GroupSubscriptionCheckSubscriptionSender
	extends SubscriptionSender {

	public GroupSubscriptionCheckSubscriptionSender(String resourceName) {
		_resourceName = resourceName;
	}

	@Override
	protected Boolean hasSubscribePermission(
			PermissionChecker permissionChecker, Subscription subscription)
		throws PortalException {

		Group group = GroupLocalServiceUtil.fetchGroup(
			subscription.getClassPK());

		if (group != null) {
			ResourceAction resourceAction =
				ResourceActionLocalServiceUtil.fetchResourceAction(
					subscription.getClassName(), ActionKeys.SUBSCRIBE);

			if (resourceAction == null) {
				return true;
			}

			return ResourcePermissionCheckerUtil.containsResourcePermission(
				permissionChecker, _resourceName, subscription.getClassPK(),
				ActionKeys.SUBSCRIBE);
		}

		return super.hasSubscribePermission(permissionChecker, subscription);
	}

	private final String _resourceName;

}
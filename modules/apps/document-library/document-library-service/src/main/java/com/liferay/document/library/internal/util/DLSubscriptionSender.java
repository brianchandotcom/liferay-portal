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

package com.liferay.document.library.internal.util;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.util.GroupSubscriptionCheckSubscriptionSender;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author tototrinh
 */
public class DLSubscriptionSender
	extends GroupSubscriptionCheckSubscriptionSender {

	public DLSubscriptionSender() {
	}

	public DLSubscriptionSender(String resourceName, long targetFolderId) {
		super(resourceName);

		_targetFolderId = targetFolderId;
	}

	@Override
	protected Boolean hasSubscribePermission(
			PermissionChecker permissionChecker, Subscription subscription)
		throws PortalException {

		if (!ModelResourcePermissionUtil.contains(
				_dlFolderModelResourcePermission, permissionChecker,
				subscription.getGroupId(), _targetFolderId,
				ActionKeys.SUBSCRIBE)) {

			return false;
		}

		return super.hasSubscribePermission(permissionChecker, subscription);
	}

	private static volatile ModelResourcePermission<DLFolder>
		_dlFolderModelResourcePermission =
			ServiceProxyFactory.newServiceTrackedInstance(
				ModelResourcePermission.class, DLSubscriptionSender.class,
				"_dlFolderModelResourcePermission",
				"(model.class.name=" + DLFolder.class.getName() + ")", true);

	private long _targetFolderId;

}
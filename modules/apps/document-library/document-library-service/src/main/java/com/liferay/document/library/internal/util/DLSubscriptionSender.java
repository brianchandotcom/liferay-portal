/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.util.GroupSubscriptionCheckSubscriptionSender;

/**
 * @author To Trinh
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
				_dlFolderModelResourcePermissionSnapshot.get(),
				permissionChecker, subscription.getGroupId(), _targetFolderId,
				ActionKeys.SUBSCRIBE)) {

			return false;
		}

		return super.hasSubscribePermission(permissionChecker, subscription);
	}

	private static final Snapshot<ModelResourcePermission<DLFolder>>
		_dlFolderModelResourcePermissionSnapshot = new Snapshot<>(
			DLSubscriptionSender.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=" + DLFolder.class.getName() + ")");

	private long _targetFolderId;

}
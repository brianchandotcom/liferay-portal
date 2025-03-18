/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.permission.provider;

import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoPermissionProvider.class)
public class ObjectEntryFolderInfoPermissionProvider
	implements InfoPermissionProvider<ObjectEntryFolder> {

	@Override
	public boolean hasAddPermission(
		long groupId, PermissionChecker permissionChecker) {

		if (FeatureFlagManagerUtil.isEnabled(
				permissionChecker.getCompanyId(), "LPD-17564")) {

			return true;
		}

		return false;
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		if (FeatureFlagManagerUtil.isEnabled(
				permissionChecker.getCompanyId(), "LPD-17564")) {

			return true;
		}

		return false;
	}

}
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

package com.liferay.layout.internal.permission;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.util.permission.LayoutClassedModelUsagePermission;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portlet.asset.service.permission.AssetEntryPermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutClassedModelUsagePermission.class)
public class LayoutClassedModelUsagePermissionImpl
	implements LayoutClassedModelUsagePermission {

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long plid, String actionId) {

		List<LayoutClassedModelUsage> layoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesByPlid(plid);

		try {
			for (LayoutClassedModelUsage layoutClassedModelUsage :
					layoutClassedModelUsages) {

				AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
					layoutClassedModelUsage.getClassNameId(),
					layoutClassedModelUsage.getClassPK());

				if (assetEntry == null) {
					continue;
				}

				if (AssetEntryPermission.contains(
						permissionChecker, assetEntry, actionId)) {

					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error("An error occurred while getting mapped contents", e);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutClassedModelUsagePermissionImpl.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

}
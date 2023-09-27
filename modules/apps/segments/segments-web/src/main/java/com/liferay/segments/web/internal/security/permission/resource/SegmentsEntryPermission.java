/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.security.permission.resource;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.segments.model.SegmentsEntry;

/**
 * @author Eduardo García
 */
public class SegmentsEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		ModelResourcePermission<SegmentsEntry> modelResourcePermission =
			_segmentsEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, entryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, SegmentsEntry segmentsEntry,
			String actionId)
		throws PortalException {

		ModelResourcePermission<SegmentsEntry> modelResourcePermission =
			_segmentsEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, segmentsEntry, actionId);
	}

	private static final Snapshot<ModelResourcePermission<SegmentsEntry>>
		_segmentsEntryModelResourcePermissionSnapshot = new Snapshot<>(
			SegmentsEntryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.segments.model.SegmentsEntry)");

}
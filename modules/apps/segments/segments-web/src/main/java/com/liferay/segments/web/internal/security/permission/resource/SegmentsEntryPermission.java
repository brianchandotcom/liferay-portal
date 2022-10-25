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

package com.liferay.segments.web.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.segments.model.SegmentsEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = {})
public class SegmentsEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		return _segmentsEntryModelResourcePermission.contains(
			permissionChecker, entryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, SegmentsEntry segmentsEntry,
			String actionId)
		throws PortalException {

		return _segmentsEntryModelResourcePermission.contains(
			permissionChecker, segmentsEntry, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.segments.model.SegmentsEntry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<SegmentsEntry> modelResourcePermission) {

		_segmentsEntryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<SegmentsEntry>
		_segmentsEntryModelResourcePermission;

}
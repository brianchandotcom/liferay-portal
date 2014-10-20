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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureVersionServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructurePermission;

import java.util.List;

/**
 * @author Pablo Carvalho
 */
@ProviderType
public class DDMStructureVersionServiceImpl
	extends DDMStructureVersionServiceBaseImpl {

	@Override
	public DDMStructureVersion getLatestStructureVersion(long structureId)
		throws PortalException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.VIEW);

		return ddmStructureVersionLocalService.getLatestStructureVersion(
			structureId);
	}

	@Override
	public DDMStructureVersion getStructureVersion(long structureVersionId)
		throws PortalException {

		DDMStructureVersion structureVersion =
			ddmStructureVersionLocalService.getStructureVersion(
				structureVersionId);

		DDMStructurePermission.check(
			getPermissionChecker(), structureVersion.getStructureId(),
			ActionKeys.VIEW);

		return structureVersion;
	}

	@Override
	public List<DDMStructureVersion> getStructureVersions(
			long structureId, int start, int end,
			OrderByComparator<DDMStructureVersion> orderByComparator)
		throws PortalException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.VIEW);

		return ddmStructureVersionLocalService.getStructureVersions(
			structureId, start, end, orderByComparator);
	}

	@Override
	public int getStructureVersionsCount(long structureId)
		throws PortalException {

		DDMStructurePermission.check(
			getPermissionChecker(), structureId, ActionKeys.VIEW);

		return ddmStructureVersionLocalService.getStructureVersionsCount(
			structureId);
	}

}
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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureVersionServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Pablo Carvalho
 */
public class DDMStructureVersionServiceImpl
	extends DDMStructureVersionServiceBaseImpl {

	@Override
	public DDMStructureVersion getLatestStructureVersion(long structureId)
		throws PortalException {

		_ddmStructureModelResourcePermission.check(
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

		_ddmStructureModelResourcePermission.check(
			getPermissionChecker(), structureVersion.getStructureId(),
			ActionKeys.VIEW);

		return structureVersion;
	}

	@Override
	public List<DDMStructureVersion> getStructureVersions(
			long structureId, int start, int end,
			OrderByComparator<DDMStructureVersion> orderByComparator)
		throws PortalException {

		_ddmStructureModelResourcePermission.check(
			getPermissionChecker(), structureId, ActionKeys.VIEW);

		return ddmStructureVersionLocalService.getStructureVersions(
			structureId, start, end, orderByComparator);
	}

	@Override
	public int getStructureVersionsCount(long structureId)
		throws PortalException {

		_ddmStructureModelResourcePermission.check(
			getPermissionChecker(), structureId, ActionKeys.VIEW);

		return ddmStructureVersionLocalService.getStructureVersionsCount(
			structureId);
	}

	private static volatile ModelResourcePermission<DDMStructure>
		_ddmStructureModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DDMStructureVersionServiceImpl.class,
				"_ddmStructureModelResourcePermission", DDMStructure.class);

}
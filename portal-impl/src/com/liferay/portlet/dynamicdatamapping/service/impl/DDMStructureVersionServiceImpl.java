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
 * The implementation of the d d m structure version remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.dynamicdatamapping.service.DDMStructureVersionService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Pablo Carvalho
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureVersionServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.DDMStructureVersionServiceUtil
 */
@ProviderType
public class DDMStructureVersionServiceImpl
	extends DDMStructureVersionServiceBaseImpl {

	@Override
	public DDMStructureVersion getDDMStructureVersion(
			long ddmStructureVersionId)
		throws PortalException {

		DDMStructurePermission.check(
			getPermissionChecker(), ddmStructureVersionId, ActionKeys.VIEW);

		return ddmStructureVersionLocalService.getDDMStructureVersion(
			ddmStructureVersionId);
	}

	@Override
	public List<DDMStructureVersion> getDDMStructureVersions(
			long ddmStructureId, int start, int end,
			OrderByComparator<DDMStructureVersion> orderByComparator)
		throws PortalException {

		DDMStructurePermission.check(
			getPermissionChecker(), ddmStructureId, ActionKeys.VIEW);

		return ddmStructureVersionLocalService.getDDMStructureVersions(
			ddmStructureId, start, end, orderByComparator);
	}

	@Override
	public int getDDMStructureVersionsCount(long ddmStructureId)
		throws PortalException {

		DDMStructurePermission.check(
			getPermissionChecker(), ddmStructureId, ActionKeys.VIEW);

		return ddmStructureVersionLocalService.getDDMStructureVersionsCount(
			ddmStructureId);
	}

	@Override
	public DDMStructureVersion getLatestVersion(long ddmStructureId)
		throws PortalException {

		DDMStructurePermission.check(
			getPermissionChecker(), ddmStructureId, ActionKeys.VIEW);

		return ddmStructureVersionLocalService.getLatestVersion(ddmStructureId);
	}

}
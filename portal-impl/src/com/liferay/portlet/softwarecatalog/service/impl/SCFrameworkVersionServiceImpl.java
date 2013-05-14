/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.service.base.SCFrameworkVersionServiceBaseImpl;
import com.liferay.portlet.softwarecatalog.service.permission.SCFrameworkVersionPermission;
import com.liferay.portlet.softwarecatalog.service.permission.SCPermission;

import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SCFrameworkVersionServiceImpl
	extends SCFrameworkVersionServiceBaseImpl {

	@Override
	public SCFrameworkVersion addFrameworkVersion(
			String name, String url, boolean active, int priority,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		SCPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_FRAMEWORK_VERSION);

		return scFrameworkVersionLocalService.addFrameworkVersion(
			getUserId(), name, url, active, priority, serviceContext);
	}

	@Override
	public void deleteFrameworkVersion(long frameworkVersionId)
		throws PortalException, SystemException {

		SCFrameworkVersionPermission.check(
			getPermissionChecker(), frameworkVersionId, ActionKeys.DELETE);

		scFrameworkVersionLocalService.deleteFrameworkVersion(
			frameworkVersionId);
	}

	@Override
	public SCFrameworkVersion getFrameworkVersion(long frameworkVersionId)
		throws PortalException, SystemException {

		return scFrameworkVersionLocalService.getFrameworkVersion(
			frameworkVersionId);
	}

	@Override
	public List<SCFrameworkVersion> getFrameworkVersions(
			long groupId, boolean active)
		throws SystemException {

		return scFrameworkVersionLocalService.getFrameworkVersions(
			groupId, active);
	}

	@Override
	public List<SCFrameworkVersion> getFrameworkVersions(
			long groupId, boolean active, int start, int end)
		throws SystemException {

		return scFrameworkVersionLocalService.getFrameworkVersions(
			groupId, active, start, end);
	}

	@Override
	public SCFrameworkVersion updateFrameworkVersion(
			long frameworkVersionId, String name, String url, boolean active,
			int priority)
		throws PortalException, SystemException {

		SCFrameworkVersionPermission.check(
			getPermissionChecker(), frameworkVersionId, ActionKeys.UPDATE);

		return scFrameworkVersionLocalService.updateFrameworkVersion(
			frameworkVersionId, name, url, active, priority);
	}

}
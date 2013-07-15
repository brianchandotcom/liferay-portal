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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.StagingServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class StagingServiceImpl extends StagingServiceBaseImpl {

	@Override
	public void cleanup(long stagingRequestId)
		throws PortalException, SystemException {

		checkPermission(stagingRequestId);

		stagingLocalService.cleanup(stagingRequestId);
	}

	@Override
	public long prepare(long groupId, String checksum)
		throws PortalException, SystemException {

		long userId = getUserId();

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return stagingLocalService.prepare(userId, groupId, checksum);
	}

	@Override
	public void publish(
			long stagingRequestId, boolean privateLayout,
			Map<String, String[]> parameterMap)
		throws PortalException, SystemException {

		long userId = getUserId();

		checkPermission(stagingRequestId);

		stagingLocalService.publish(
			userId, stagingRequestId, privateLayout, parameterMap);
	}

	@Override
	public void stage(long stagingRequestId, String fileName, byte[] byteBuffer)
		throws PortalException, SystemException {

		long userId = getUserId();

		checkPermission(stagingRequestId);

		stagingLocalService.stage(
			userId, stagingRequestId, fileName, byteBuffer);
	}

	protected void checkPermission(long stagingRequestId)
		throws PortalException, SystemException {

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			stagingRequestId);

		GroupPermissionUtil.check(
			getPermissionChecker(), folder.getGroupId(),
			ActionKeys.EXPORT_IMPORT_LAYOUTS);
	}

}
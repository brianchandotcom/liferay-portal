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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.MissingReferences;
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
	public void cleanUpStagingRequest(long stagingRequestId)
		throws PortalException {

		checkPermission(stagingRequestId);

		stagingLocalService.cleanUpStagingRequest(stagingRequestId);
	}

	@Override
	public long createStagingRequest(long groupId, String checksum)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.EXPORT_IMPORT_LAYOUTS);

		return stagingLocalService.createStagingRequest(
			getUserId(), groupId, checksum);
	}

	@Override
	public void publishStagingRequest(
			long stagingRequestId,
			Map<String, String[]> parameterMap)
		throws PortalException {

		checkPermission(stagingRequestId);

		stagingLocalService.publishStagingRequest(
			getUserId(), stagingRequestId, parameterMap);
	}

	@Override
	public void updateStagingRequest(
			long stagingRequestId, String fileName, byte[] bytes)
		throws PortalException {

		checkPermission(stagingRequestId);

		stagingLocalService.updateStagingRequest(
			getUserId(), stagingRequestId, fileName, bytes);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #publishStagingRequest(long,
	 *             java.util.Map)}
	 */
	@Deprecated
	@Override
	public MissingReferences validateStagingRequest(
			long stagingRequestId, boolean privateLayout,
			Map<String, String[]> parameterMap)
		throws PortalException {

		checkPermission(stagingRequestId);

		return stagingLocalService.validateStagingRequest(
			getUserId(), stagingRequestId, privateLayout, parameterMap);
	}

	protected void checkPermission(long stagingRequestId)
		throws PortalException {

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			stagingRequestId);

		GroupPermissionUtil.check(
			getPermissionChecker(), folder.getGroupId(),
			ActionKeys.EXPORT_IMPORT_LAYOUTS);
	}

}
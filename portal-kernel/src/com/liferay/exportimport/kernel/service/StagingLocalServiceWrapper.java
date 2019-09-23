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

package com.liferay.exportimport.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link StagingLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see StagingLocalService
 * @generated
 */
public class StagingLocalServiceWrapper
	implements ServiceWrapper<StagingLocalService>, StagingLocalService {

	public StagingLocalServiceWrapper(StagingLocalService stagingLocalService) {
		_stagingLocalService = stagingLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link StagingLocalServiceUtil} to access the staging local service. Add custom service methods to <code>com.liferay.portlet.exportimport.service.impl.StagingLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public void checkDefaultLayoutSetBranches(
			long userId, com.liferay.portal.kernel.model.Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate, boolean remote,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_stagingLocalService.checkDefaultLayoutSetBranches(
			userId, liveGroup, branchingPublic, branchingPrivate, remote,
			serviceContext);
	}

	@Override
	public void cleanUpStagingRequest(long stagingRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_stagingLocalService.cleanUpStagingRequest(stagingRequestId);
	}

	@Override
	public long createStagingRequest(long userId, long groupId, String checksum)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stagingLocalService.createStagingRequest(
			userId, groupId, checksum);
	}

	@Override
	public void disableStaging(
			com.liferay.portal.kernel.model.Group liveGroup,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_stagingLocalService.disableStaging(liveGroup, serviceContext);
	}

	@Override
	public void disableStaging(
			javax.portlet.PortletRequest portletRequest,
			com.liferay.portal.kernel.model.Group liveGroup,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_stagingLocalService.disableStaging(
			portletRequest, liveGroup, serviceContext);
	}

	@Override
	public void enableLocalStaging(
			long userId, com.liferay.portal.kernel.model.Group liveGroup,
			boolean branchingPublic, boolean branchingPrivate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_stagingLocalService.enableLocalStaging(
			userId, liveGroup, branchingPublic, branchingPrivate,
			serviceContext);
	}

	@Override
	public void enableRemoteStaging(
			long userId, com.liferay.portal.kernel.model.Group stagingGroup,
			boolean branchingPublic, boolean branchingPrivate,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_stagingLocalService.enableRemoteStaging(
			userId, stagingGroup, branchingPublic, branchingPrivate,
			remoteAddress, remotePort, remotePathContext, secureConnection,
			remoteGroupId, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _stagingLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
			publishStagingRequest(
				long userId, long stagingRequestId, boolean privateLayout,
				java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stagingLocalService.publishStagingRequest(
			userId, stagingRequestId, privateLayout, parameterMap);
	}

	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
			publishStagingRequest(
				long userId, long stagingRequestId,
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _stagingLocalService.publishStagingRequest(
			userId, stagingRequestId, exportImportConfiguration);
	}

	@Override
	public void updateStagingRequest(
			long userId, long stagingRequestId, String fileName, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		_stagingLocalService.updateStagingRequest(
			userId, stagingRequestId, fileName, bytes);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #publishStagingRequest(long, long, boolean, Map)}
	 */
	@Deprecated
	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
		validateStagingRequest(
			long userId, long stagingRequestId, boolean privateLayout,
			java.util.Map<String, String[]> parameterMap) {

		return _stagingLocalService.validateStagingRequest(
			userId, stagingRequestId, privateLayout, parameterMap);
	}

	@Override
	public StagingLocalService getWrappedService() {
		return _stagingLocalService;
	}

	@Override
	public void setWrappedService(StagingLocalService stagingLocalService) {
		_stagingLocalService = stagingLocalService;
	}

	private StagingLocalService _stagingLocalService;

}
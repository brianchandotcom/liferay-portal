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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link StagingLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see StagingLocalService
 * @generated
 */
@ProviderType
public class StagingLocalServiceWrapper implements StagingLocalService,
	ServiceWrapper<StagingLocalService> {
	public StagingLocalServiceWrapper(StagingLocalService stagingLocalService) {
		_stagingLocalService = stagingLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _stagingLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_stagingLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public void checkDefaultLayoutSetBranches(long userId,
		com.liferay.portal.model.Group liveGroup, boolean branchingPublic,
		boolean branchingPrivate, boolean remote,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_stagingLocalService.checkDefaultLayoutSetBranches(userId, liveGroup,
			branchingPublic, branchingPrivate, remote, serviceContext);
	}

	@Override
	public void cleanUpStagingRequest(long stagingRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_stagingLocalService.cleanUpStagingRequest(stagingRequestId);
	}

	@Override
	public long createStagingRequest(long userId, long groupId,
		java.lang.String checksum)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _stagingLocalService.createStagingRequest(userId, groupId,
			checksum);
	}

	@Override
	public void disableStaging(com.liferay.portal.model.Group liveGroup,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_stagingLocalService.disableStaging(liveGroup, serviceContext);
	}

	@Override
	public void disableStaging(javax.portlet.PortletRequest portletRequest,
		com.liferay.portal.model.Group liveGroup,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_stagingLocalService.disableStaging(portletRequest, liveGroup,
			serviceContext);
	}

	@Override
	public void enableLocalStaging(long userId,
		com.liferay.portal.model.Group liveGroup, boolean branchingPublic,
		boolean branchingPrivate,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_stagingLocalService.enableLocalStaging(userId, liveGroup,
			branchingPublic, branchingPrivate, serviceContext);
	}

	@Override
	public void enableRemoteStaging(long userId,
		com.liferay.portal.model.Group liveGroup, boolean branchingPublic,
		boolean branchingPrivate, java.lang.String remoteAddress,
		int remotePort, java.lang.String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_stagingLocalService.enableRemoteStaging(userId, liveGroup,
			branchingPublic, branchingPrivate, remoteAddress, remotePort,
			remotePathContext, secureConnection, remoteGroupId, serviceContext);
	}

	@Override
	public void publishStagingRequest(long userId, long stagingRequestId,
		boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		_stagingLocalService.publishStagingRequest(userId, stagingRequestId,
			privateLayout, parameterMap);
	}

	@Override
	public void updateStagingRequest(long userId, long stagingRequestId,
		java.lang.String fileName, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		_stagingLocalService.updateStagingRequest(userId, stagingRequestId,
			fileName, bytes);
	}

	@Override
	public com.liferay.portal.kernel.lar.MissingReferences validateStagingRequest(
		long userId, long stagingRequestId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _stagingLocalService.validateStagingRequest(userId,
			stagingRequestId, privateLayout, parameterMap);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public StagingLocalService getWrappedStagingLocalService() {
		return _stagingLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedStagingLocalService(
		StagingLocalService stagingLocalService) {
		_stagingLocalService = stagingLocalService;
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
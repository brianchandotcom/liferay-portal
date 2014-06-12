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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Staging. This utility wraps
 * {@link com.liferay.portal.service.impl.StagingLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see StagingLocalService
 * @see com.liferay.portal.service.base.StagingLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.StagingLocalServiceImpl
 * @generated
 */
@ProviderType
public class StagingLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.StagingLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void checkDefaultLayoutSetBranches(long userId,
		com.liferay.portal.model.Group liveGroup, boolean branchingPublic,
		boolean branchingPrivate, boolean remote,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.checkDefaultLayoutSetBranches(userId, liveGroup, branchingPublic,
			branchingPrivate, remote, serviceContext);
	}

	public static void cleanUpStagingRequest(long stagingRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().cleanUpStagingRequest(stagingRequestId);
	}

	public static long createStagingRequest(long userId, long groupId,
		java.lang.String checksum)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().createStagingRequest(userId, groupId, checksum);
	}

	public static void disableStaging(
		com.liferay.portal.model.Group liveGroup,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().disableStaging(liveGroup, serviceContext);
	}

	public static void disableStaging(
		javax.portlet.PortletRequest portletRequest,
		com.liferay.portal.model.Group liveGroup,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().disableStaging(portletRequest, liveGroup, serviceContext);
	}

	public static void enableLocalStaging(long userId,
		com.liferay.portal.model.Group liveGroup, boolean branchingPublic,
		boolean branchingPrivate,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.enableLocalStaging(userId, liveGroup, branchingPublic,
			branchingPrivate, serviceContext);
	}

	public static void enableRemoteStaging(long userId,
		com.liferay.portal.model.Group liveGroup, boolean branchingPublic,
		boolean branchingPrivate, java.lang.String remoteAddress,
		int remotePort, java.lang.String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.enableRemoteStaging(userId, liveGroup, branchingPublic,
			branchingPrivate, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId, serviceContext);
	}

	public static void publishStagingRequest(long userId,
		long stagingRequestId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.publishStagingRequest(userId, stagingRequestId, privateLayout,
			parameterMap);
	}

	public static void updateStagingRequest(long userId, long stagingRequestId,
		java.lang.String fileName, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateStagingRequest(userId, stagingRequestId, fileName, bytes);
	}

	public static com.liferay.portal.kernel.lar.MissingReferences validateStagingRequest(
		long userId, long stagingRequestId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateStagingRequest(userId, stagingRequestId,
			privateLayout, parameterMap);
	}

	public static StagingLocalService getService() {
		if (_service == null) {
			_service = (StagingLocalService)PortalBeanLocatorUtil.locate(StagingLocalService.class.getName());

			ReferenceRegistry.registerReference(StagingLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(StagingLocalService service) {
	}

	private static StagingLocalService _service;
}
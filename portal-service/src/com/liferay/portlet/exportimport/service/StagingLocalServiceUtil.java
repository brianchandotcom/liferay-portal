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

package com.liferay.portlet.exportimport.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Staging. This utility wraps
 * {@link com.liferay.portlet.exportimport.service.impl.StagingLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see StagingLocalService
 * @see com.liferay.portlet.exportimport.service.base.StagingLocalServiceBaseImpl
 * @see com.liferay.portlet.exportimport.service.impl.StagingLocalServiceImpl
 * @generated
 */
@ProviderType
public class StagingLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.exportimport.service.impl.StagingLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
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
		com.liferay.portal.model.Group stagingGroup, boolean branchingPublic,
		boolean branchingPrivate, java.lang.String remoteAddress,
		int remotePort, java.lang.String remotePathContext,
		boolean secureConnection, long remoteGroupId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.enableRemoteStaging(userId, stagingGroup, branchingPublic,
			branchingPrivate, remoteAddress, remotePort, remotePathContext,
			secureConnection, remoteGroupId, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portlet.exportimport.lar.MissingReferences publishStagingRequest(
		long userId, long stagingRequestId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .publishStagingRequest(userId, stagingRequestId,
			exportImportConfiguration);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static com.liferay.portlet.exportimport.lar.MissingReferences publishStagingRequest(
		long userId, long stagingRequestId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .publishStagingRequest(userId, stagingRequestId,
			privateLayout, parameterMap);
	}

	public static void updateStagingRequest(long userId, long stagingRequestId,
		java.lang.String fileName, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateStagingRequest(userId, stagingRequestId, fileName, bytes);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #publishStagingRequest(long,
	long, boolean, Map)}
	*/
	@Deprecated
	public static com.liferay.portlet.exportimport.lar.MissingReferences validateStagingRequest(
		long userId, long stagingRequestId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap) {
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

	private static StagingLocalService _service;
}
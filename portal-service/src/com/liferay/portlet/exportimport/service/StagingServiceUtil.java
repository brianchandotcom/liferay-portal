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
 * Provides the remote service utility for Staging. This utility wraps
 * {@link com.liferay.portlet.exportimport.service.impl.StagingServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see StagingService
 * @see com.liferay.portlet.exportimport.service.base.StagingServiceBaseImpl
 * @see com.liferay.portlet.exportimport.service.impl.StagingServiceImpl
 * @generated
 */
@ProviderType
public class StagingServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.exportimport.service.impl.StagingServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void cleanUpStagingRequest(long stagingRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().cleanUpStagingRequest(stagingRequestId);
	}

	public static long createStagingRequest(long groupId,
		java.lang.String checksum)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().createStagingRequest(groupId, checksum);
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
		long stagingRequestId,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .publishStagingRequest(stagingRequestId,
			exportImportConfiguration);
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	public static com.liferay.portlet.exportimport.lar.MissingReferences publishStagingRequest(
		long stagingRequestId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .publishStagingRequest(stagingRequestId, privateLayout,
			parameterMap);
	}

	public static void updateStagingRequest(long stagingRequestId,
		java.lang.String fileName, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateStagingRequest(stagingRequestId, fileName, bytes);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #publishStagingRequest(long,
	boolean, Map)}
	*/
	@Deprecated
	public static com.liferay.portlet.exportimport.lar.MissingReferences validateStagingRequest(
		long stagingRequestId, boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .validateStagingRequest(stagingRequestId, privateLayout,
			parameterMap);
	}

	public static StagingService getService() {
		if (_service == null) {
			_service = (StagingService)PortalBeanLocatorUtil.locate(StagingService.class.getName());

			ReferenceRegistry.registerReference(StagingServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static StagingService _service;
}
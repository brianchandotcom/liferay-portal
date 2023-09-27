/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the remote service utility for Staging. This utility wraps
 * <code>com.liferay.portlet.exportimport.service.impl.StagingServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see StagingService
 * @generated
 */
public class StagingServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.exportimport.service.impl.StagingServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void cleanUpStagingRequest(long stagingRequestId)
		throws PortalException {

		getService().cleanUpStagingRequest(stagingRequestId);
	}

	public static long createStagingRequest(long groupId, String checksum)
		throws PortalException {

		return getService().createStagingRequest(groupId, checksum);
	}

	public static void enableLocalStaging(
			long groupId, boolean branchingPublic, boolean branchingPrivate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().enableLocalStaging(
			groupId, branchingPublic, branchingPrivate, serviceContext);
	}

	public static void enableRemoteStaging(
			long groupId, boolean branchingPublic, boolean branchingPrivate,
			String remoteAddress, int remotePort, String remotePathContext,
			boolean secureConnection, long remoteGroupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().enableRemoteStaging(
			groupId, branchingPublic, branchingPrivate, remoteAddress,
			remotePort, remotePathContext, secureConnection, remoteGroupId,
			serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static boolean hasRemoteLayout(
			String uuid, long groupId, boolean privateLayout)
		throws PortalException {

		return getService().hasRemoteLayout(uuid, groupId, privateLayout);
	}

	public static void propagateExportImportLifecycleEvent(
			int code, int processFlag, String processId,
			List<Serializable> arguments)
		throws PortalException {

		getService().propagateExportImportLifecycleEvent(
			code, processFlag, processId, arguments);
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences
			publishStagingRequest(
				long stagingRequestId,
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration)
		throws PortalException {

		return getService().publishStagingRequest(
			stagingRequestId, exportImportConfiguration);
	}

	public static void updateStagingRequest(
			long stagingRequestId, String fileName, byte[] bytes)
		throws PortalException {

		getService().updateStagingRequest(stagingRequestId, fileName, bytes);
	}

	public static StagingService getService() {
		return _service;
	}

	public static void setService(StagingService service) {
		_service = service;
	}

	private static volatile StagingService _service;

}
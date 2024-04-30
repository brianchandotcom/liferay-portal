/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * Provides the remote service utility for CPDAvailabilityEstimate. This utility wraps
 * <code>com.liferay.commerce.service.impl.CPDAvailabilityEstimateServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CPDAvailabilityEstimateService
 * @generated
 */
public class CPDAvailabilityEstimateServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CPDAvailabilityEstimateServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDAvailabilityEstimate
			fetchCPDAvailabilityEstimateByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		return getService().fetchCPDAvailabilityEstimateByCPDefinitionId(
			cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDAvailabilityEstimate updateCPDAvailabilityEstimate(
			long cpdAvailabilityEstimateId, long cpDefinitionId,
			long commerceAvailabilityEstimateId)
		throws PortalException {

		return getService().updateCPDAvailabilityEstimate(
			cpdAvailabilityEstimateId, cpDefinitionId,
			commerceAvailabilityEstimateId);
	}

	public static CPDAvailabilityEstimateService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<CPDAvailabilityEstimateService>
		_serviceSnapshot = new Snapshot<>(
			CPDAvailabilityEstimateServiceUtil.class,
			CPDAvailabilityEstimateService.class);

}
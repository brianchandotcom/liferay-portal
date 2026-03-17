/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

/**
 * Provides a wrapper for {@link KaleoTaskInstanceTokenService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskInstanceTokenService
 * @generated
 */
public class KaleoTaskInstanceTokenServiceWrapper
	implements KaleoTaskInstanceTokenService,
			   ServiceWrapper<KaleoTaskInstanceTokenService> {

	public KaleoTaskInstanceTokenServiceWrapper() {
		this(null);
	}

	public KaleoTaskInstanceTokenServiceWrapper(
		KaleoTaskInstanceTokenService kaleoTaskInstanceTokenService) {

		_kaleoTaskInstanceTokenService = kaleoTaskInstanceTokenService;
	}

	@Override
	public KaleoTaskInstanceToken getKaleoTaskInstanceToken(long workflowTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskInstanceTokenService.getKaleoTaskInstanceToken(
			workflowTaskId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoTaskInstanceTokenService.getOSGiServiceIdentifier();
	}

	@Override
	public KaleoTaskInstanceTokenService getWrappedService() {
		return _kaleoTaskInstanceTokenService;
	}

	@Override
	public void setWrappedService(
		KaleoTaskInstanceTokenService kaleoTaskInstanceTokenService) {

		_kaleoTaskInstanceTokenService = kaleoTaskInstanceTokenService;
	}

	private KaleoTaskInstanceTokenService _kaleoTaskInstanceTokenService;

}
// SB-Hash:-1005836962
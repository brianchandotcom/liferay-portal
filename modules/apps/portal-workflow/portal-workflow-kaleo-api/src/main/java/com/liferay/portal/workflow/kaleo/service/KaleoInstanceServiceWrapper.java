/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KaleoInstanceService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoInstanceService
 * @generated
 */
public class KaleoInstanceServiceWrapper
	implements KaleoInstanceService, ServiceWrapper<KaleoInstanceService> {

	public KaleoInstanceServiceWrapper() {
		this(null);
	}

	public KaleoInstanceServiceWrapper(
		KaleoInstanceService kaleoInstanceService) {

		_kaleoInstanceService = kaleoInstanceService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoInstanceService.getOSGiServiceIdentifier();
	}

	@Override
	public KaleoInstanceService getWrappedService() {
		return _kaleoInstanceService;
	}

	@Override
	public void setWrappedService(KaleoInstanceService kaleoInstanceService) {
		_kaleoInstanceService = kaleoInstanceService;
	}

	private KaleoInstanceService _kaleoInstanceService;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.service;

import com.liferay.depot.model.DepotEntryPin;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DepotEntryPinService}.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryPinService
 * @generated
 */
public class DepotEntryPinServiceWrapper
	implements DepotEntryPinService, ServiceWrapper<DepotEntryPinService> {

	public DepotEntryPinServiceWrapper() {
		this(null);
	}

	public DepotEntryPinServiceWrapper(
		DepotEntryPinService depotEntryPinService) {

		_depotEntryPinService = depotEntryPinService;
	}

	@Override
	public DepotEntryPin addDepotEntryPin(long userId, long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryPinService.addDepotEntryPin(userId, depotEntryId);
	}

	@Override
	public DepotEntryPin deleteDepotEntryPin(long userId, long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryPinService.deleteDepotEntryPin(userId, depotEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _depotEntryPinService.getOSGiServiceIdentifier();
	}

	@Override
	public DepotEntryPinService getWrappedService() {
		return _depotEntryPinService;
	}

	@Override
	public void setWrappedService(DepotEntryPinService depotEntryPinService) {
		_depotEntryPinService = depotEntryPinService;
	}

	private DepotEntryPinService _depotEntryPinService;

}
// SB-Hash:-771580565
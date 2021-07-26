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

package com.liferay.change.tracking.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CTProcessService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTProcessService
 * @generated
 */
public class CTProcessServiceWrapper
	implements CTProcessService, ServiceWrapper<CTProcessService> {

	public CTProcessServiceWrapper(CTProcessService ctProcessService) {
		_ctProcessService = ctProcessService;
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTProcess>
			getCTProcesses(
				long companyId, long userId, String keywords, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.change.tracking.model.CTProcess>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctProcessService.getCTProcesses(
			companyId, userId, keywords, status, start, end, orderByComparator);
	}

	@Override
	public int getCTProcessesCount(
		long companyId, long userId, String keywords, int status) {

		return _ctProcessService.getCTProcessesCount(
			companyId, userId, keywords, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctProcessService.getOSGiServiceIdentifier();
	}

	@Override
	public CTProcessService getWrappedService() {
		return _ctProcessService;
	}

	@Override
	public void setWrappedService(CTProcessService ctProcessService) {
		_ctProcessService = ctProcessService;
	}

	private CTProcessService _ctProcessService;

}
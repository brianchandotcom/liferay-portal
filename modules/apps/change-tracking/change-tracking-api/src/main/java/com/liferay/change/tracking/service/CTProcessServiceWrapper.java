/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public CTProcessServiceWrapper() {
		this(null);
	}

	public CTProcessServiceWrapper(CTProcessService ctProcessService) {
		_ctProcessService = ctProcessService;
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTProcess>
			getCTProcesses(
				long companyId, long userId, String keywords, int status,
				int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.change.tracking.model.CTProcess>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctProcessService.getCTProcesses(
			companyId, userId, keywords, status, type, start, end,
			orderByComparator);
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

	@Override
	public int getCTProcessesCount(
		long companyId, long userId, String keywords, int status, int type) {

		return _ctProcessService.getCTProcessesCount(
			companyId, userId, keywords, status, type);
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
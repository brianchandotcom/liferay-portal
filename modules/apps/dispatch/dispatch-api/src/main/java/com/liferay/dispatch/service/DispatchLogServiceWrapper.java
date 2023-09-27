/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DispatchLogService}.
 *
 * @author Matija Petanjek
 * @see DispatchLogService
 * @generated
 */
public class DispatchLogServiceWrapper
	implements DispatchLogService, ServiceWrapper<DispatchLogService> {

	public DispatchLogServiceWrapper() {
		this(null);
	}

	public DispatchLogServiceWrapper(DispatchLogService dispatchLogService) {
		_dispatchLogService = dispatchLogService;
	}

	@Override
	public void deleteDispatchLog(long dispatchLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dispatchLogService.deleteDispatchLog(dispatchLogId);
	}

	@Override
	public com.liferay.dispatch.model.DispatchLog getDispatchLog(
			long dispatchLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dispatchLogService.getDispatchLog(dispatchLogId);
	}

	@Override
	public java.util.List<com.liferay.dispatch.model.DispatchLog>
			getDispatchLogs(long dispatchTriggerId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dispatchLogService.getDispatchLogs(
			dispatchTriggerId, start, end);
	}

	@Override
	public java.util.List<com.liferay.dispatch.model.DispatchLog>
			getDispatchLogs(
				long dispatchTriggerId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dispatch.model.DispatchLog> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dispatchLogService.getDispatchLogs(
			dispatchTriggerId, start, end, orderByComparator);
	}

	@Override
	public int getDispatchLogsCount(long dispatchTriggerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dispatchLogService.getDispatchLogsCount(dispatchTriggerId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dispatchLogService.getOSGiServiceIdentifier();
	}

	@Override
	public DispatchLogService getWrappedService() {
		return _dispatchLogService;
	}

	@Override
	public void setWrappedService(DispatchLogService dispatchLogService) {
		_dispatchLogService = dispatchLogService;
	}

	private DispatchLogService _dispatchLogService;

}
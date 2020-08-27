/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceDataIntegrationProcessLogService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessLogService
 * @generated
 */
public class CommerceDataIntegrationProcessLogServiceWrapper
	implements CommerceDataIntegrationProcessLogService,
			   ServiceWrapper<CommerceDataIntegrationProcessLogService> {

	public CommerceDataIntegrationProcessLogServiceWrapper(
		CommerceDataIntegrationProcessLogService
			commerceDataIntegrationProcessLogService) {

		_commerceDataIntegrationProcessLogService =
			commerceDataIntegrationProcessLogService;
	}

	@Override
	public com.liferay.commerce.data.integration.model.
		CommerceDataIntegrationProcessLog addCommerceDataIntegrationProcessLog(
				long userId, long commerceDataIntegrationProcessId,
				String error, String output, int status,
				java.util.Date startDate, java.util.Date endDate)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLogService.
			addCommerceDataIntegrationProcessLog(
				userId, commerceDataIntegrationProcessId, error, output, status,
				startDate, endDate);
	}

	@Override
	public void deleteCommerceDataIntegrationProcessLog(
			long cDataIntegrationProcessLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceDataIntegrationProcessLogService.
			deleteCommerceDataIntegrationProcessLog(
				cDataIntegrationProcessLogId);
	}

	@Override
	public com.liferay.commerce.data.integration.model.
		CommerceDataIntegrationProcessLog getCommerceDataIntegrationProcessLog(
				long cDataIntegrationProcessLogId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLogService.
			getCommerceDataIntegrationProcessLog(cDataIntegrationProcessLogId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcessLog>
					getCommerceDataIntegrationProcessLogs(
						long commerceDataIntegrationProcessId, int start,
						int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLogService.
			getCommerceDataIntegrationProcessLogs(
				commerceDataIntegrationProcessId, start, end);
	}

	@Override
	public int getCommerceDataIntegrationProcessLogsCount(
			long commerceDataIntegrationProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLogService.
			getCommerceDataIntegrationProcessLogsCount(
				commerceDataIntegrationProcessId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDataIntegrationProcessLogService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.data.integration.model.
		CommerceDataIntegrationProcessLog
				updateCommerceDataIntegrationProcessLog(
					long cDataIntegrationProcessLogId, String error,
					String output, int status, java.util.Date endDate)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLogService.
			updateCommerceDataIntegrationProcessLog(
				cDataIntegrationProcessLogId, error, output, status, endDate);
	}

	@Override
	public CommerceDataIntegrationProcessLogService getWrappedService() {
		return _commerceDataIntegrationProcessLogService;
	}

	@Override
	public void setWrappedService(
		CommerceDataIntegrationProcessLogService
			commerceDataIntegrationProcessLogService) {

		_commerceDataIntegrationProcessLogService =
			commerceDataIntegrationProcessLogService;
	}

	private CommerceDataIntegrationProcessLogService
		_commerceDataIntegrationProcessLogService;

}
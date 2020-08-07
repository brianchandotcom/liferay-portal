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
 * Provides a wrapper for {@link CommerceDataIntegrationProcessService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessService
 * @generated
 */
public class CommerceDataIntegrationProcessServiceWrapper
	implements CommerceDataIntegrationProcessService,
			   ServiceWrapper<CommerceDataIntegrationProcessService> {

	public CommerceDataIntegrationProcessServiceWrapper(
		CommerceDataIntegrationProcessService
			commerceDataIntegrationProcessService) {

		_commerceDataIntegrationProcessService =
			commerceDataIntegrationProcessService;
	}

	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess addCommerceDataIntegrationProcess(
					long userId, String name, String type,
					com.liferay.portal.kernel.util.UnicodeProperties
						typeSettingsUnicodeProperties)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessService.
			addCommerceDataIntegrationProcess(
				userId, name, type, typeSettingsUnicodeProperties);
	}

	@Override
	public void deleteCommerceDataIntegrationProcess(
			long commerceDataIntegrationProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceDataIntegrationProcessService.
			deleteCommerceDataIntegrationProcess(
				commerceDataIntegrationProcessId);
	}

	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess fetchCommerceDataIntegrationProcess(
					long commerceDataIntegrationProcessId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessService.
			fetchCommerceDataIntegrationProcess(
				commerceDataIntegrationProcessId);
	}

	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess getCommerceDataIntegrationProcess(
					long commerceDataIntegrationProcessId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessService.
			getCommerceDataIntegrationProcess(commerceDataIntegrationProcessId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess> getCommerceDataIntegrationProcesses(
					long companyId, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessService.
			getCommerceDataIntegrationProcesses(companyId, start, end);
	}

	@Override
	public int getCommerceDataIntegrationProcessesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessService.
			getCommerceDataIntegrationProcessesCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDataIntegrationProcessService.
			getOSGiServiceIdentifier();
	}

	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess updateCommerceDataIntegrationProcess(
					long commerceDataIntegrationProcessId, String name,
					com.liferay.portal.kernel.util.UnicodeProperties
						typeSettingsUnicodeProperties)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessService.
			updateCommerceDataIntegrationProcess(
				commerceDataIntegrationProcessId, name,
				typeSettingsUnicodeProperties);
	}

	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess
					updateCommerceDataIntegrationProcessTrigger(
						long commerceDataIntegrationProcessId, boolean active,
						String cronExpression, int startDateMonth,
						int startDateDay, int startDateYear, int startDateHour,
						int startDateMinute, int endDateMonth, int endDateDay,
						int endDateYear, int endDateHour, int endDateMinute,
						boolean neverEnd)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessService.
			updateCommerceDataIntegrationProcessTrigger(
				commerceDataIntegrationProcessId, active, cronExpression,
				startDateMonth, startDateDay, startDateYear, startDateHour,
				startDateMinute, endDateMonth, endDateDay, endDateYear,
				endDateHour, endDateMinute, neverEnd);
	}

	@Override
	public CommerceDataIntegrationProcessService getWrappedService() {
		return _commerceDataIntegrationProcessService;
	}

	@Override
	public void setWrappedService(
		CommerceDataIntegrationProcessService
			commerceDataIntegrationProcessService) {

		_commerceDataIntegrationProcessService =
			commerceDataIntegrationProcessService;
	}

	private CommerceDataIntegrationProcessService
		_commerceDataIntegrationProcessService;

}
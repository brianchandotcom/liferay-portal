/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.forecast.alert.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceMLForecastAlertEntryService}.
 *
 * @author Riccardo Ferrari
 * @see CommerceMLForecastAlertEntryService
 * @generated
 */
public class CommerceMLForecastAlertEntryServiceWrapper
	implements CommerceMLForecastAlertEntryService,
			   ServiceWrapper<CommerceMLForecastAlertEntryService> {

	public CommerceMLForecastAlertEntryServiceWrapper() {
		this(null);
	}

	public CommerceMLForecastAlertEntryServiceWrapper(
		CommerceMLForecastAlertEntryService
			commerceMLForecastAlertEntryService) {

		_commerceMLForecastAlertEntryService =
			commerceMLForecastAlertEntryService;
	}

	@Override
	public java.util.List
		<com.liferay.commerce.machine.learning.forecast.alert.model.
			CommerceMLForecastAlertEntry>
					getAboveThresholdCommerceMLForecastAlertEntries(
						long companyId, long userId, int status,
						double relativeChange, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceMLForecastAlertEntryService.
			getAboveThresholdCommerceMLForecastAlertEntries(
				companyId, userId, status, relativeChange, start, end);
	}

	@Override
	public int getAboveThresholdCommerceMLForecastAlertEntriesCount(
			long companyId, long userId, int status, double relativeChange)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceMLForecastAlertEntryService.
			getAboveThresholdCommerceMLForecastAlertEntriesCount(
				companyId, userId, status, relativeChange);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.machine.learning.forecast.alert.model.
			CommerceMLForecastAlertEntry>
					getBelowThresholdCommerceMLForecastAlertEntries(
						long companyId, long userId, int status,
						double relativeChange, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceMLForecastAlertEntryService.
			getBelowThresholdCommerceMLForecastAlertEntries(
				companyId, userId, status, relativeChange, start, end);
	}

	@Override
	public int getBelowThresholdCommerceMLForecastAlertEntriesCount(
			long companyId, long userId, int status, double relativeChange)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceMLForecastAlertEntryService.
			getBelowThresholdCommerceMLForecastAlertEntriesCount(
				companyId, userId, status, relativeChange);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.machine.learning.forecast.alert.model.
			CommerceMLForecastAlertEntry> getCommerceMLForecastAlertEntries(
					long companyId, long userId, int status, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceMLForecastAlertEntryService.
			getCommerceMLForecastAlertEntries(
				companyId, userId, status, start, end);
	}

	@Override
	public int getCommerceMLForecastAlertEntriesCount(
			long companyId, long userId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceMLForecastAlertEntryService.
			getCommerceMLForecastAlertEntriesCount(companyId, userId, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceMLForecastAlertEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.machine.learning.forecast.alert.model.
		CommerceMLForecastAlertEntry updateStatus(
				long commerceMLForecastAlertEntryId, int status)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceMLForecastAlertEntryService.updateStatus(
			commerceMLForecastAlertEntryId, status);
	}

	@Override
	public CommerceMLForecastAlertEntryService getWrappedService() {
		return _commerceMLForecastAlertEntryService;
	}

	@Override
	public void setWrappedService(
		CommerceMLForecastAlertEntryService
			commerceMLForecastAlertEntryService) {

		_commerceMLForecastAlertEntryService =
			commerceMLForecastAlertEntryService;
	}

	private CommerceMLForecastAlertEntryService
		_commerceMLForecastAlertEntryService;

}
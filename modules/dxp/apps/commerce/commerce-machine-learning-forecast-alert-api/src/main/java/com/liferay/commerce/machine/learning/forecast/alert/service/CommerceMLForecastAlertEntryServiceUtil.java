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

package com.liferay.commerce.machine.learning.forecast.alert.service;

import com.liferay.commerce.machine.learning.forecast.alert.model.CommerceMLForecastAlertEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceMLForecastAlertEntry. This utility wraps
 * <code>com.liferay.commerce.machine.learning.forecast.alert.service.impl.CommerceMLForecastAlertEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Riccardo Ferrari
 * @see CommerceMLForecastAlertEntryService
 * @generated
 */
public class CommerceMLForecastAlertEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.machine.learning.forecast.alert.service.impl.CommerceMLForecastAlertEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<CommerceMLForecastAlertEntry>
			getAboveThresholdCommerceMLForecastAlertEntries(
				long companyId, long userId, int status, double relativeChange,
				int start, int end)
		throws PortalException {

		return getService().getAboveThresholdCommerceMLForecastAlertEntries(
			companyId, userId, status, relativeChange, start, end);
	}

	public static int getAboveThresholdCommerceMLForecastAlertEntriesCount(
			long companyId, long userId, int status, double relativeChange)
		throws PortalException {

		return getService().
			getAboveThresholdCommerceMLForecastAlertEntriesCount(
				companyId, userId, status, relativeChange);
	}

	public static List<CommerceMLForecastAlertEntry>
			getBelowThresholdCommerceMLForecastAlertEntries(
				long companyId, long userId, int status, double relativeChange,
				int start, int end)
		throws PortalException {

		return getService().getBelowThresholdCommerceMLForecastAlertEntries(
			companyId, userId, status, relativeChange, start, end);
	}

	public static int getBelowThresholdCommerceMLForecastAlertEntriesCount(
			long companyId, long userId, int status, double relativeChange)
		throws PortalException {

		return getService().
			getBelowThresholdCommerceMLForecastAlertEntriesCount(
				companyId, userId, status, relativeChange);
	}

	public static List<CommerceMLForecastAlertEntry>
			getCommerceMLForecastAlertEntries(
				long companyId, long userId, int status, int start, int end)
		throws PortalException {

		return getService().getCommerceMLForecastAlertEntries(
			companyId, userId, status, start, end);
	}

	public static int getCommerceMLForecastAlertEntriesCount(
			long companyId, long userId, int status)
		throws PortalException {

		return getService().getCommerceMLForecastAlertEntriesCount(
			companyId, userId, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceMLForecastAlertEntry updateStatus(
			long commerceMLForecastAlertEntryId, int status)
		throws PortalException {

		return getService().updateStatus(
			commerceMLForecastAlertEntryId, status);
	}

	public static CommerceMLForecastAlertEntryService getService() {
		return _service;
	}

	private static volatile CommerceMLForecastAlertEntryService _service;

}
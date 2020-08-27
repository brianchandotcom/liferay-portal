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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceDataIntegrationProcess. This utility wraps
 * <code>com.liferay.commerce.data.integration.service.impl.CommerceDataIntegrationProcessServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessService
 * @generated
 */
public class CommerceDataIntegrationProcessServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.data.integration.service.impl.CommerceDataIntegrationProcessServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess addCommerceDataIntegrationProcess(
					long userId, String name, String type,
					com.liferay.portal.kernel.util.UnicodeProperties
						typeSettingsUnicodeProperties)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceDataIntegrationProcess(
			userId, name, type, typeSettingsUnicodeProperties);
	}

	public static void deleteCommerceDataIntegrationProcess(
			long commerceDataIntegrationProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceDataIntegrationProcess(
			commerceDataIntegrationProcessId);
	}

	public static
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess fetchCommerceDataIntegrationProcess(
					long commerceDataIntegrationProcessId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceDataIntegrationProcess(
			commerceDataIntegrationProcessId);
	}

	public static
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess getCommerceDataIntegrationProcess(
					long commerceDataIntegrationProcessId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDataIntegrationProcess(
			commerceDataIntegrationProcessId);
	}

	public static java.util.List
		<com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess> getCommerceDataIntegrationProcesses(
					long companyId, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDataIntegrationProcesses(
			companyId, start, end);
	}

	public static int getCommerceDataIntegrationProcessesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDataIntegrationProcessesCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess updateCommerceDataIntegrationProcess(
					long commerceDataIntegrationProcessId, String name,
					com.liferay.portal.kernel.util.UnicodeProperties
						typeSettingsUnicodeProperties)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceDataIntegrationProcess(
			commerceDataIntegrationProcessId, name,
			typeSettingsUnicodeProperties);
	}

	public static
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

		return getService().updateCommerceDataIntegrationProcessTrigger(
			commerceDataIntegrationProcessId, active, cronExpression,
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, endDateMonth, endDateDay, endDateYear, endDateHour,
			endDateMinute, neverEnd);
	}

	public static CommerceDataIntegrationProcessService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceDataIntegrationProcessService,
		 CommerceDataIntegrationProcessService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceDataIntegrationProcessService.class);

		ServiceTracker
			<CommerceDataIntegrationProcessService,
			 CommerceDataIntegrationProcessService> serviceTracker =
				new ServiceTracker
					<CommerceDataIntegrationProcessService,
					 CommerceDataIntegrationProcessService>(
						 bundle.getBundleContext(),
						 CommerceDataIntegrationProcessService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
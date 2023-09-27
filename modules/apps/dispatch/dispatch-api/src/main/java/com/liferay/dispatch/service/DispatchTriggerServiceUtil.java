/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.service;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for DispatchTrigger. This utility wraps
 * <code>com.liferay.dispatch.service.impl.DispatchTriggerServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Matija Petanjek
 * @see DispatchTriggerService
 * @generated
 */
public class DispatchTriggerServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dispatch.service.impl.DispatchTriggerServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DispatchTrigger addDispatchTrigger(
			String externalReferenceCode, long userId,
			String dispatchTaskExecutorType,
			com.liferay.portal.kernel.util.UnicodeProperties
				dispatchTaskSettingsUnicodeProperties,
			String name)
		throws PortalException {

		return getService().addDispatchTrigger(
			externalReferenceCode, userId, dispatchTaskExecutorType,
			dispatchTaskSettingsUnicodeProperties, name);
	}

	public static void deleteDispatchTrigger(long dispatchTriggerId)
		throws PortalException {

		getService().deleteDispatchTrigger(dispatchTriggerId);
	}

	public static DispatchTrigger getDispatchTrigger(long dispatchTriggerId)
		throws PortalException {

		return getService().getDispatchTrigger(dispatchTriggerId);
	}

	public static List<DispatchTrigger> getDispatchTriggers(int start, int end)
		throws PortalException {

		return getService().getDispatchTriggers(start, end);
	}

	public static int getDispatchTriggersCount() throws PortalException {
		return getService().getDispatchTriggersCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId, boolean active, String cronExpression,
			com.liferay.dispatch.executor.DispatchTaskClusterMode
				dispatchTaskClusterMode,
			int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
			int endDateMinute, boolean neverEnd, boolean overlapAllowed,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, String timeZoneId)
		throws PortalException {

		return getService().updateDispatchTrigger(
			dispatchTriggerId, active, cronExpression, dispatchTaskClusterMode,
			endDateMonth, endDateDay, endDateYear, endDateHour, endDateMinute,
			neverEnd, overlapAllowed, startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, timeZoneId);
	}

	public static DispatchTrigger updateDispatchTrigger(
			long dispatchTriggerId,
			com.liferay.portal.kernel.util.UnicodeProperties
				dispatchTaskSettingsUnicodeProperties,
			String name)
		throws PortalException {

		return getService().updateDispatchTrigger(
			dispatchTriggerId, dispatchTaskSettingsUnicodeProperties, name);
	}

	public static DispatchTriggerService getService() {
		return _service;
	}

	public static void setService(DispatchTriggerService service) {
		_service = service;
	}

	private static volatile DispatchTriggerService _service;

}
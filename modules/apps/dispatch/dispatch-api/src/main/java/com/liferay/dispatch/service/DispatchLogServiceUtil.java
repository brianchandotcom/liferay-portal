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

package com.liferay.dispatch.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for DispatchLog. This utility wraps
 * <code>com.liferay.dispatch.service.impl.DispatchLogServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see DispatchLogService
 * @generated
 */
public class DispatchLogServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dispatch.service.impl.DispatchLogServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteDispatchLog(long dispatchLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteDispatchLog(dispatchLogId);
	}

	public static com.liferay.dispatch.model.DispatchLog getDispatchLog(
			long dispatchLogId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDispatchLog(dispatchLogId);
	}

	public static java.util.List<com.liferay.dispatch.model.DispatchLog>
			getDispatchLogs(long dispatchTriggerId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDispatchLogs(dispatchTriggerId, start, end);
	}

	public static int getDispatchLogsCount(long dispatchTriggerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDispatchLogsCount(dispatchTriggerId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DispatchLogService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DispatchLogService, DispatchLogService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DispatchLogService.class);

		ServiceTracker<DispatchLogService, DispatchLogService> serviceTracker =
			new ServiceTracker<DispatchLogService, DispatchLogService>(
				bundle.getBundleContext(), DispatchLogService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
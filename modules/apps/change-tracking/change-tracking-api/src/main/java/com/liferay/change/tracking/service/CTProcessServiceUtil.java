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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CTProcess. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTProcessServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CTProcessService
 * @generated
 */
public class CTProcessServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTProcessServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.util.List<com.liferay.change.tracking.model.CTProcess>
		getCTProcesses(
			long companyId, long userId, String keywords, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTProcess>
					orderByComparator) {

		return getService().getCTProcesses(
			companyId, userId, keywords, status, start, end, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CTProcessService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTProcessService, CTProcessService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTProcessService.class);

		ServiceTracker<CTProcessService, CTProcessService> serviceTracker =
			new ServiceTracker<CTProcessService, CTProcessService>(
				bundle.getBundleContext(), CTProcessService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
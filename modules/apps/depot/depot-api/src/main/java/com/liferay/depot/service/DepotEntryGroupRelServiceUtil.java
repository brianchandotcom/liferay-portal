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

package com.liferay.depot.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for DepotEntryGroupRel. This utility wraps
 * <code>com.liferay.depot.service.impl.DepotEntryGroupRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryGroupRelService
 * @generated
 */
public class DepotEntryGroupRelServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.depot.service.impl.DepotEntryGroupRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DepotEntryGroupRelServiceUtil} to access the depot entry group rel remote service. Add custom service methods to <code>com.liferay.depot.service.impl.DepotEntryGroupRelServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static java.util.List<com.liferay.depot.model.DepotEntryGroupRel>
			getDepotEntryGroupRels(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDepotEntryGroupRels(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DepotEntryGroupRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DepotEntryGroupRelService, DepotEntryGroupRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DepotEntryGroupRelService.class);

		ServiceTracker<DepotEntryGroupRelService, DepotEntryGroupRelService>
			serviceTracker =
				new ServiceTracker
					<DepotEntryGroupRelService, DepotEntryGroupRelService>(
						bundle.getBundleContext(),
						DepotEntryGroupRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
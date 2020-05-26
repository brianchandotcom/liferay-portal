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

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.depot.service.impl.DepotEntryGroupRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.depot.model.DepotEntryGroupRel
			addDepotEntryGroupRel(long depotEntryId, long toGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDepotEntryGroupRel(depotEntryId, toGroupId);
	}

	public static com.liferay.depot.model.DepotEntryGroupRel
			deleteDepotEntryGroupRel(long depotEntryGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDepotEntryGroupRel(depotEntryGroupRelId);
	}

	public static java.util.List<com.liferay.depot.model.DepotEntryGroupRel>
			getDepotEntryGroupRels(long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDepotEntryGroupRels(groupId, start, end);
	}

	public static int getDepotEntryGroupRelsCount(
			com.liferay.depot.model.DepotEntry depotEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDepotEntryGroupRelsCount(depotEntry);
	}

	public static int getDepotEntryGroupRelsCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDepotEntryGroupRelsCount(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.depot.model.DepotEntryGroupRel updateSearchable(
			long depotEntryGroupRelId, boolean searchable)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSearchable(depotEntryGroupRelId, searchable);
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
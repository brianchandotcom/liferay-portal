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
 * Provides the remote service utility for DepotEntry. This utility wraps
 * <code>com.liferay.depot.service.impl.DepotEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryService
 * @generated
 */
public class DepotEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.depot.service.impl.DepotEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.depot.model.DepotEntry addDepotEntry(
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addDepotEntry(
			nameMap, descriptionMap, serviceContext);
	}

	public static com.liferay.depot.model.DepotEntry deleteDepotEntry(
			long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDepotEntry(depotEntryId);
	}

	public static com.liferay.depot.model.DepotEntry getDepotEntry(
			long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDepotEntry(depotEntryId);
	}

	public static java.util.List<com.liferay.depot.model.DepotEntry>
			getGroupConnectedDepotEntries(long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupConnectedDepotEntries(groupId, start, end);
	}

	public static int getGroupConnectedDepotEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupConnectedDepotEntriesCount(groupId);
	}

	public static com.liferay.depot.model.DepotEntry getGroupDepotEntry(
			long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupDepotEntry(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.depot.model.DepotEntry updateDepotEntry(
			long depotEntryId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<String, Boolean> depotAppCustomizationMap,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateDepotEntry(
			depotEntryId, nameMap, descriptionMap, depotAppCustomizationMap,
			typeSettingsProperties, serviceContext);
	}

	public static DepotEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DepotEntryService, DepotEntryService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DepotEntryService.class);

		ServiceTracker<DepotEntryService, DepotEntryService> serviceTracker =
			new ServiceTracker<DepotEntryService, DepotEntryService>(
				bundle.getBundleContext(), DepotEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
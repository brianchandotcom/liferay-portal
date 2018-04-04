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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPMeasurementUnit. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPMeasurementUnitServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPMeasurementUnitService
 * @see com.liferay.commerce.product.service.base.CPMeasurementUnitServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPMeasurementUnitServiceImpl
 * @generated
 */
@ProviderType
public class CPMeasurementUnitServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPMeasurementUnitServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPMeasurementUnit addCPMeasurementUnit(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String key, double rate, boolean primary, double priority,
		int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPMeasurementUnit(nameMap, key, rate, primary, priority,
			type, serviceContext);
	}

	public static void deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPMeasurementUnit(cpMeasurementUnitId);
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit fetchPrimaryCPMeasurementUnit(
		long groupId, int type) {
		return getService().fetchPrimaryCPMeasurementUnit(groupId, type);
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit getCPMeasurementUnit(
		long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPMeasurementUnit(cpMeasurementUnitId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPMeasurementUnit> getCPMeasurementUnits(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPMeasurementUnit> orderByComparator) {
		return getService()
				   .getCPMeasurementUnits(groupId, type, start, end,
			orderByComparator);
	}

	public static int getCPMeasurementUnitsCount(long groupId, int type) {
		return getService().getCPMeasurementUnitsCount(groupId, type);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit updateCPMeasurementUnit(
		long cpMeasurementUnitId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String key, double rate, boolean primary, double priority,
		int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPMeasurementUnit(cpMeasurementUnitId, nameMap, key,
			rate, primary, priority, type, serviceContext);
	}

	public static CPMeasurementUnitService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPMeasurementUnitService, CPMeasurementUnitService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPMeasurementUnitService.class);

		ServiceTracker<CPMeasurementUnitService, CPMeasurementUnitService> serviceTracker =
			new ServiceTracker<CPMeasurementUnitService, CPMeasurementUnitService>(bundle.getBundleContext(),
				CPMeasurementUnitService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPMeasurementUnit. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPMeasurementUnitServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPMeasurementUnitService
 * @generated
 */
public class CPMeasurementUnitServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPMeasurementUnitServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPMeasurementUnit
			addCPMeasurementUnit(
				java.util.Map<java.util.Locale, String> nameMap, String key,
				double rate, boolean primary, double priority, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCPMeasurementUnit(
			nameMap, key, rate, primary, priority, type, serviceContext);
	}

	public static void deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCPMeasurementUnit(cpMeasurementUnitId);
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			fetchPrimaryCPMeasurementUnit(long companyId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchPrimaryCPMeasurementUnit(companyId, type);
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			getCPMeasurementUnit(long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPMeasurementUnit(cpMeasurementUnitId);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPMeasurementUnit>
				getCPMeasurementUnits(
					long companyId, int type, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.model.CPMeasurementUnit>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPMeasurementUnits(
			companyId, type, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPMeasurementUnit>
				getCPMeasurementUnits(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.model.CPMeasurementUnit>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPMeasurementUnits(
			companyId, start, end, orderByComparator);
	}

	public static int getCPMeasurementUnitsCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPMeasurementUnitsCount(companyId);
	}

	public static int getCPMeasurementUnitsCount(long companyId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPMeasurementUnitsCount(companyId, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			setPrimary(long cpMeasurementUnitId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().setPrimary(cpMeasurementUnitId, primary);
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			updateCPMeasurementUnit(
				long cpMeasurementUnitId,
				java.util.Map<java.util.Locale, String> nameMap, String key,
				double rate, boolean primary, double priority, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPMeasurementUnit(
			cpMeasurementUnitId, nameMap, key, rate, primary, priority, type,
			serviceContext);
	}

	public static CPMeasurementUnitService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CPMeasurementUnitService, CPMeasurementUnitService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPMeasurementUnitService.class);

		ServiceTracker<CPMeasurementUnitService, CPMeasurementUnitService>
			serviceTracker =
				new ServiceTracker
					<CPMeasurementUnitService, CPMeasurementUnitService>(
						bundle.getBundleContext(),
						CPMeasurementUnitService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
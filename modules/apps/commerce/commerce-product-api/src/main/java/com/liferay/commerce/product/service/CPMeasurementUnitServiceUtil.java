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

import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

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
	public static CPMeasurementUnit addCPMeasurementUnit(
			Map<java.util.Locale, String> nameMap, String key, double rate,
			boolean primary, double priority, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCPMeasurementUnit(
			nameMap, key, rate, primary, priority, type, serviceContext);
	}

	public static void deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException {

		getService().deleteCPMeasurementUnit(cpMeasurementUnitId);
	}

	public static CPMeasurementUnit fetchPrimaryCPMeasurementUnit(
			long companyId, int type)
		throws PortalException {

		return getService().fetchPrimaryCPMeasurementUnit(companyId, type);
	}

	public static CPMeasurementUnit getCPMeasurementUnit(
			long cpMeasurementUnitId)
		throws PortalException {

		return getService().getCPMeasurementUnit(cpMeasurementUnitId);
	}

	public static List<CPMeasurementUnit> getCPMeasurementUnits(
			long companyId, int type, int start, int end,
			OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws PortalException {

		return getService().getCPMeasurementUnits(
			companyId, type, start, end, orderByComparator);
	}

	public static List<CPMeasurementUnit> getCPMeasurementUnits(
			long companyId, int start, int end,
			OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws PortalException {

		return getService().getCPMeasurementUnits(
			companyId, start, end, orderByComparator);
	}

	public static int getCPMeasurementUnitsCount(long companyId)
		throws PortalException {

		return getService().getCPMeasurementUnitsCount(companyId);
	}

	public static int getCPMeasurementUnitsCount(long companyId, int type)
		throws PortalException {

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

	public static CPMeasurementUnit setPrimary(
			long cpMeasurementUnitId, boolean primary)
		throws PortalException {

		return getService().setPrimary(cpMeasurementUnitId, primary);
	}

	public static CPMeasurementUnit updateCPMeasurementUnit(
			long cpMeasurementUnitId, Map<java.util.Locale, String> nameMap,
			String key, double rate, boolean primary, double priority, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPMeasurementUnit(
			cpMeasurementUnitId, nameMap, key, rate, primary, priority, type,
			serviceContext);
	}

	public static CPMeasurementUnitService getService() {
		return _service;
	}

	private static volatile CPMeasurementUnitService _service;

}
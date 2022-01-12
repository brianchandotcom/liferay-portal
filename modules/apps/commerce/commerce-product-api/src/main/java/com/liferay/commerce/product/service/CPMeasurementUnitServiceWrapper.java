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
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPMeasurementUnitService}.
 *
 * @author Marco Leo
 * @see CPMeasurementUnitService
 * @generated
 */
public class CPMeasurementUnitServiceWrapper
	implements CPMeasurementUnitService,
			   ServiceWrapper<CPMeasurementUnitService> {

	public CPMeasurementUnitServiceWrapper() {
		this(null);
	}

	public CPMeasurementUnitServiceWrapper(
		CPMeasurementUnitService cpMeasurementUnitService) {

		_cpMeasurementUnitService = cpMeasurementUnitService;
	}

	@Override
	public CPMeasurementUnit addCPMeasurementUnit(
			java.util.Map<java.util.Locale, String> nameMap, String key,
			double rate, boolean primary, double priority, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.addCPMeasurementUnit(
			nameMap, key, rate, primary, priority, type, serviceContext);
	}

	@Override
	public void deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpMeasurementUnitService.deleteCPMeasurementUnit(cpMeasurementUnitId);
	}

	@Override
	public CPMeasurementUnit fetchPrimaryCPMeasurementUnit(
			long companyId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.fetchPrimaryCPMeasurementUnit(
			companyId, type);
	}

	@Override
	public CPMeasurementUnit getCPMeasurementUnit(long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.getCPMeasurementUnit(
			cpMeasurementUnitId);
	}

	@Override
	public java.util.List<CPMeasurementUnit> getCPMeasurementUnits(
			long companyId, int type, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.getCPMeasurementUnits(
			companyId, type, start, end, orderByComparator);
	}

	@Override
	public java.util.List<CPMeasurementUnit> getCPMeasurementUnits(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.getCPMeasurementUnits(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCPMeasurementUnitsCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.getCPMeasurementUnitsCount(companyId);
	}

	@Override
	public int getCPMeasurementUnitsCount(long companyId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.getCPMeasurementUnitsCount(
			companyId, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpMeasurementUnitService.getOSGiServiceIdentifier();
	}

	@Override
	public CPMeasurementUnit setPrimary(
			long cpMeasurementUnitId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.setPrimary(
			cpMeasurementUnitId, primary);
	}

	@Override
	public CPMeasurementUnit updateCPMeasurementUnit(
			long cpMeasurementUnitId,
			java.util.Map<java.util.Locale, String> nameMap, String key,
			double rate, boolean primary, double priority, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.updateCPMeasurementUnit(
			cpMeasurementUnitId, nameMap, key, rate, primary, priority, type,
			serviceContext);
	}

	@Override
	public CPMeasurementUnitService getWrappedService() {
		return _cpMeasurementUnitService;
	}

	@Override
	public void setWrappedService(
		CPMeasurementUnitService cpMeasurementUnitService) {

		_cpMeasurementUnitService = cpMeasurementUnitService;
	}

	private CPMeasurementUnitService _cpMeasurementUnitService;

}
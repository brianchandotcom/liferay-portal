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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPMeasurementUnitService}.
 *
 * @author Marco Leo
 * @see CPMeasurementUnitService
 * @generated
 */
@ProviderType
public class CPMeasurementUnitServiceWrapper implements CPMeasurementUnitService,
	ServiceWrapper<CPMeasurementUnitService> {
	public CPMeasurementUnitServiceWrapper(
		CPMeasurementUnitService cpMeasurementUnitService) {
		_cpMeasurementUnitService = cpMeasurementUnitService;
	}

	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit addCPMeasurementUnit(
		java.util.Map<java.util.Locale, String> nameMap, String key,
		double rate, boolean primary, double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMeasurementUnitService.addCPMeasurementUnit(nameMap, key,
			rate, primary, priority, type, serviceContext);
	}

	@Override
	public void deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpMeasurementUnitService.deleteCPMeasurementUnit(cpMeasurementUnitId);
	}

	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit fetchPrimaryCPMeasurementUnit(
		long groupId, int type) {
		return _cpMeasurementUnitService.fetchPrimaryCPMeasurementUnit(groupId,
			type);
	}

	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit getCPMeasurementUnit(
		long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMeasurementUnitService.getCPMeasurementUnit(cpMeasurementUnitId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMeasurementUnit> getCPMeasurementUnits(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPMeasurementUnit> orderByComparator) {
		return _cpMeasurementUnitService.getCPMeasurementUnits(groupId, type,
			start, end, orderByComparator);
	}

	@Override
	public int getCPMeasurementUnitsCount(long groupId, int type) {
		return _cpMeasurementUnitService.getCPMeasurementUnitsCount(groupId,
			type);
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
	public com.liferay.commerce.product.model.CPMeasurementUnit updateCPMeasurementUnit(
		long cpMeasurementUnitId,
		java.util.Map<java.util.Locale, String> nameMap, String key,
		double rate, boolean primary, double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMeasurementUnitService.updateCPMeasurementUnit(cpMeasurementUnitId,
			nameMap, key, rate, primary, priority, type, serviceContext);
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
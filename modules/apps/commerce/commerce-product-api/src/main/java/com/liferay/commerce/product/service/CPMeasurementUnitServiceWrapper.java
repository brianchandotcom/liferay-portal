/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			String externalReferenceCode,
			java.util.Map<java.util.Locale, String> nameMap, String key,
			double rate, boolean primary, double priority, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.addCPMeasurementUnit(
			externalReferenceCode, nameMap, key, rate, primary, priority, type,
			serviceContext);
	}

	@Override
	public void deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpMeasurementUnitService.deleteCPMeasurementUnit(cpMeasurementUnitId);
	}

	@Override
	public CPMeasurementUnit fetchCPMeasurementUnit(long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.fetchCPMeasurementUnit(
			cpMeasurementUnitId);
	}

	@Override
	public CPMeasurementUnit fetchCPMeasurementUnitByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.
			fetchCPMeasurementUnitByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public CPMeasurementUnit fetchCPMeasurementUnitByKey(
			long companyId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.fetchCPMeasurementUnitByKey(
			companyId, key);
	}

	@Override
	public CPMeasurementUnit fetchPrimaryCPMeasurementUnit(
			long companyId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.fetchPrimaryCPMeasurementUnit(
			companyId, type);
	}

	@Override
	public CPMeasurementUnit fetchPrimaryCPMeasurementUnitByType(
			long companyId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.fetchPrimaryCPMeasurementUnitByType(
			companyId, type);
	}

	@Override
	public CPMeasurementUnit getCPMeasurementUnit(long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.getCPMeasurementUnit(
			cpMeasurementUnitId);
	}

	@Override
	public CPMeasurementUnit getCPMeasurementUnitByKey(
			long companyId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.getCPMeasurementUnitByKey(
			companyId, key);
	}

	@Override
	public java.util.List<CPMeasurementUnit> getCPMeasurementUnits(
			long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.getCPMeasurementUnits(companyId);
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
	public java.util.List<CPMeasurementUnit> getCPMeasurementUnitsByType(
			long companyId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.getCPMeasurementUnitsByType(
			companyId, type);
	}

	@Override
	public java.util.List<CPMeasurementUnit> getCPMeasurementUnitsByType(
			long companyId, int type, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.getCPMeasurementUnitsByType(
			companyId, type, start, end, orderByComparator);
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
			String externalReferenceCode, long cpMeasurementUnitId,
			java.util.Map<java.util.Locale, String> nameMap, String key,
			double rate, boolean primary, double priority, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpMeasurementUnitService.updateCPMeasurementUnit(
			externalReferenceCode, cpMeasurementUnitId, nameMap, key, rate,
			primary, priority, type, serviceContext);
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
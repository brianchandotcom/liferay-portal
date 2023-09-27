/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPInstanceUnitOfMeasureService}.
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasureService
 * @generated
 */
public class CPInstanceUnitOfMeasureServiceWrapper
	implements CPInstanceUnitOfMeasureService,
			   ServiceWrapper<CPInstanceUnitOfMeasureService> {

	public CPInstanceUnitOfMeasureServiceWrapper() {
		this(null);
	}

	public CPInstanceUnitOfMeasureServiceWrapper(
		CPInstanceUnitOfMeasureService cpInstanceUnitOfMeasureService) {

		_cpInstanceUnitOfMeasureService = cpInstanceUnitOfMeasureService;
	}

	@Override
	public CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
			long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			java.util.Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.addCPInstanceUnitOfMeasure(
			cpInstanceId, active, incrementalOrderQuantity, key, nameMap,
			precision, primary, priority, rate, sku);
	}

	@Override
	public CPInstanceUnitOfMeasure addOrUpdateCPInstanceUnitOfMeasure(
			long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			java.util.Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.
			addOrUpdateCPInstanceUnitOfMeasure(
				cpInstanceId, active, incrementalOrderQuantity, key, nameMap,
				precision, primary, priority, rate, sku);
	}

	@Override
	public CPInstanceUnitOfMeasure deleteCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.deleteCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasureId);
	}

	@Override
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.fetchCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasureId);
	}

	@Override
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.fetchCPInstanceUnitOfMeasure(
			cpInstanceId, key);
	}

	@Override
	public CPInstanceUnitOfMeasure fetchPrimaryCPInstanceUnitOfMeasure(
			long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.
			fetchPrimaryCPInstanceUnitOfMeasure(cpInstanceId);
	}

	@Override
	public java.util.List<CPInstanceUnitOfMeasure>
			getActiveCPInstanceUnitOfMeasures(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.
			getActiveCPInstanceUnitOfMeasures(cpInstanceId);
	}

	@Override
	public int getActiveCPInstanceUnitOfMeasuresCount(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.
			getActiveCPInstanceUnitOfMeasuresCount(cpInstanceId);
	}

	@Override
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasureId);
	}

	@Override
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasure(
			cpInstanceId, key);
	}

	@Override
	public java.util.List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
			long cpInstanceId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPInstanceUnitOfMeasure> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasures(
			cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getCPInstanceUnitOfMeasuresCount(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasuresCount(
			cpInstanceId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpInstanceUnitOfMeasureService.getOSGiServiceIdentifier();
	}

	@Override
	public CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId, long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			java.util.Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.updateCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasureId, cpInstanceId, active,
			incrementalOrderQuantity, key, nameMap, precision, primary,
			priority, rate, sku);
	}

	@Override
	public CPInstanceUnitOfMeasureService getWrappedService() {
		return _cpInstanceUnitOfMeasureService;
	}

	@Override
	public void setWrappedService(
		CPInstanceUnitOfMeasureService cpInstanceUnitOfMeasureService) {

		_cpInstanceUnitOfMeasureService = cpInstanceUnitOfMeasureService;
	}

	private CPInstanceUnitOfMeasureService _cpInstanceUnitOfMeasureService;

}
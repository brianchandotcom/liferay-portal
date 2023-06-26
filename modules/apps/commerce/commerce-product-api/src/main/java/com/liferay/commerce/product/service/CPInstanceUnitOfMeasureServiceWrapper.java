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
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpInstanceUnitOfMeasureService.fetchCPInstanceUnitOfMeasure(
			cpInstanceId, key);
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
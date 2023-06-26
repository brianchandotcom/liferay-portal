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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CPInstanceUnitOfMeasure. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPInstanceUnitOfMeasureServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasureService
 * @generated
 */
public class CPInstanceUnitOfMeasureServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPInstanceUnitOfMeasureServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
			long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws PortalException {

		return getService().addCPInstanceUnitOfMeasure(
			cpInstanceId, active, incrementalOrderQuantity, key, nameMap,
			precision, primary, priority, rate, sku);
	}

	public static CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws PortalException {

		return getService().fetchCPInstanceUnitOfMeasure(cpInstanceId, key);
	}

	public static CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws PortalException {

		return getService().getCPInstanceUnitOfMeasure(cpInstanceId, key);
	}

	public static List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
			long cpInstanceId, int start, int end,
			OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator)
		throws PortalException {

		return getService().getCPInstanceUnitOfMeasures(
			cpInstanceId, start, end, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId, long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws PortalException {

		return getService().updateCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasureId, cpInstanceId, active,
			incrementalOrderQuantity, key, nameMap, precision, primary,
			priority, rate, sku);
	}

	public static CPInstanceUnitOfMeasureService getService() {
		return _service;
	}

	public static void setService(CPInstanceUnitOfMeasureService service) {
		_service = service;
	}

	private static volatile CPInstanceUnitOfMeasureService _service;

}
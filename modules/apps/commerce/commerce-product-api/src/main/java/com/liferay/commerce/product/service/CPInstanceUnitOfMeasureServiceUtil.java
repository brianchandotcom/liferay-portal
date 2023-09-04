/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static CPInstanceUnitOfMeasure addOrUpdateCPInstanceUnitOfMeasure(
			long cpInstanceId, boolean active,
			java.math.BigDecimal incrementalOrderQuantity, String key,
			Map<java.util.Locale, String> nameMap, int precision,
			boolean primary, double priority, java.math.BigDecimal rate,
			String sku)
		throws PortalException {

		return getService().addOrUpdateCPInstanceUnitOfMeasure(
			cpInstanceId, active, incrementalOrderQuantity, key, nameMap,
			precision, primary, priority, rate, sku);
	}

	public static CPInstanceUnitOfMeasure deleteCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId)
		throws PortalException {

		return getService().deleteCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasureId);
	}

	public static CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId)
		throws PortalException {

		return getService().fetchCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasureId);
	}

	public static CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws PortalException {

		return getService().fetchCPInstanceUnitOfMeasure(cpInstanceId, key);
	}

	public static CPInstanceUnitOfMeasure fetchPrimaryCPInstanceUnitOfMeasure(
			long cpInstanceId)
		throws PortalException {

		return getService().fetchPrimaryCPInstanceUnitOfMeasure(cpInstanceId);
	}

	public static List<CPInstanceUnitOfMeasure>
			getActiveCPInstanceUnitOfMeasures(long cpInstanceId)
		throws PortalException {

		return getService().getActiveCPInstanceUnitOfMeasures(cpInstanceId);
	}

	public static int getActiveCPInstanceUnitOfMeasuresCount(long cpInstanceId)
		throws PortalException {

		return getService().getActiveCPInstanceUnitOfMeasuresCount(
			cpInstanceId);
	}

	public static CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId)
		throws PortalException {

		return getService().getCPInstanceUnitOfMeasure(
			cpInstanceUnitOfMeasureId);
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

	public static int getCPInstanceUnitOfMeasuresCount(long cpInstanceId)
		throws PortalException {

		return getService().getCPInstanceUnitOfMeasuresCount(cpInstanceId);
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
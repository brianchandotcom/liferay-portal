/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CPDefinitionSpecificationOptionValue. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValueService
 * @generated
 */
public class CPDefinitionSpecificationOptionValueServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDefinitionSpecificationOptionValue
			addCPDefinitionSpecificationOptionValue(
				long cpDefinitionId, long cpSpecificationOptionId,
				long cpOptionCategoryId, Map<java.util.Locale, String> valueMap,
				double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCPDefinitionSpecificationOptionValue(
			cpDefinitionId, cpSpecificationOptionId, cpOptionCategoryId,
			valueMap, priority, serviceContext);
	}

	public static void deleteCPDefinitionSpecificationOptionValue(
			long cpDefinitionSpecificationOptionValueId)
		throws PortalException {

		getService().deleteCPDefinitionSpecificationOptionValue(
			cpDefinitionSpecificationOptionValueId);
	}

	public static void deleteCPDefinitionSpecificationOptionValues(
			long cpDefinitionId)
		throws PortalException {

		getService().deleteCPDefinitionSpecificationOptionValues(
			cpDefinitionId);
	}

	public static CPDefinitionSpecificationOptionValue
			fetchCPDefinitionSpecificationOptionValue(
				long cpDefinitionSpecificationOptionValueId)
		throws PortalException {

		return getService().fetchCPDefinitionSpecificationOptionValue(
			cpDefinitionSpecificationOptionValueId);
	}

	public static CPDefinitionSpecificationOptionValue
			getCPDefinitionSpecificationOptionValue(
				long cpDefinitionSpecificationOptionValueId)
		throws PortalException {

		return getService().getCPDefinitionSpecificationOptionValue(
			cpDefinitionSpecificationOptionValueId);
	}

	public static List<CPDefinitionSpecificationOptionValue>
			getCPDefinitionSpecificationOptionValues(
				long cpDefinitionId, int start, int end,
				OrderByComparator<CPDefinitionSpecificationOptionValue>
					orderByComparator)
		throws PortalException {

		return getService().getCPDefinitionSpecificationOptionValues(
			cpDefinitionId, start, end, orderByComparator);
	}

	public static List<CPDefinitionSpecificationOptionValue>
			getCPDefinitionSpecificationOptionValues(
				long cpDefinitionId, long cpOptionCategoryId)
		throws PortalException {

		return getService().getCPDefinitionSpecificationOptionValues(
			cpDefinitionId, cpOptionCategoryId);
	}

	public static int getCPDefinitionSpecificationOptionValuesCount(
			long cpDefinitionId)
		throws PortalException {

		return getService().getCPDefinitionSpecificationOptionValuesCount(
			cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDefinitionSpecificationOptionValue
			updateCPDefinitionSpecificationOptionValue(
				long cpDefinitionSpecificationOptionValueId,
				long cpOptionCategoryId, Map<java.util.Locale, String> valueMap,
				double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPDefinitionSpecificationOptionValue(
			cpDefinitionSpecificationOptionValueId, cpOptionCategoryId,
			valueMap, priority, serviceContext);
	}

	public static CPDefinitionSpecificationOptionValueService getService() {
		return _service;
	}

	public static void setService(
		CPDefinitionSpecificationOptionValueService service) {

		_service = service;
	}

	private static volatile CPDefinitionSpecificationOptionValueService
		_service;

}
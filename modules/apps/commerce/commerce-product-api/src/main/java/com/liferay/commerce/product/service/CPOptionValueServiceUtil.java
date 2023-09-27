/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CPOptionValue. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPOptionValueService
 * @generated
 */
public class CPOptionValueServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPOptionValueServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPOptionValue addCPOptionValue(
			long cpOptionId, Map<java.util.Locale, String> nameMap,
			double priority, String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCPOptionValue(
			cpOptionId, nameMap, priority, key, serviceContext);
	}

	public static CPOptionValue addOrUpdateCPOptionValue(
			String externalReferenceCode, long cpOptionId,
			Map<java.util.Locale, String> nameMap, double priority, String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateCPOptionValue(
			externalReferenceCode, cpOptionId, nameMap, priority, key,
			serviceContext);
	}

	public static void deleteCPOptionValue(long cpOptionValueId)
		throws PortalException {

		getService().deleteCPOptionValue(cpOptionValueId);
	}

	public static CPOptionValue fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CPOptionValue fetchCPOptionValue(long cpOptionValueId)
		throws PortalException {

		return getService().fetchCPOptionValue(cpOptionValueId);
	}

	public static CPOptionValue getCPOptionValue(long cpOptionValueId)
		throws PortalException {

		return getService().getCPOptionValue(cpOptionValueId);
	}

	public static List<CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end)
		throws PortalException {

		return getService().getCPOptionValues(cpOptionId, start, end);
	}

	public static int getCPOptionValuesCount(long cpOptionId)
		throws PortalException {

		return getService().getCPOptionValuesCount(cpOptionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CPOptionValue> searchCPOptionValues(
				long companyId, long cpOptionId, String keywords, int start,
				int end, com.liferay.portal.kernel.search.Sort[] sorts)
			throws PortalException {

		return getService().searchCPOptionValues(
			companyId, cpOptionId, keywords, start, end, sorts);
	}

	public static int searchCPOptionValuesCount(
			long companyId, long cpOptionId, String keywords)
		throws PortalException {

		return getService().searchCPOptionValuesCount(
			companyId, cpOptionId, keywords);
	}

	public static CPOptionValue updateCPOptionValue(
			long cpOptionValueId, Map<java.util.Locale, String> nameMap,
			double priority, String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCPOptionValue(
			cpOptionValueId, nameMap, priority, key, serviceContext);
	}

	public static CPOptionValueService getService() {
		return _service;
	}

	public static void setService(CPOptionValueService service) {
		_service = service;
	}

	private static volatile CPOptionValueService _service;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPOptionValueService}.
 *
 * @author Marco Leo
 * @see CPOptionValueService
 * @generated
 */
public class CPOptionValueServiceWrapper
	implements CPOptionValueService, ServiceWrapper<CPOptionValueService> {

	public CPOptionValueServiceWrapper() {
		this(null);
	}

	public CPOptionValueServiceWrapper(
		CPOptionValueService cpOptionValueService) {

		_cpOptionValueService = cpOptionValueService;
	}

	@Override
	public CPOptionValue addCPOptionValue(
			long cpOptionId, java.util.Map<java.util.Locale, String> nameMap,
			double priority, String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.addCPOptionValue(
			cpOptionId, nameMap, priority, key, serviceContext);
	}

	@Override
	public CPOptionValue addOrUpdateCPOptionValue(
			String externalReferenceCode, long cpOptionId,
			java.util.Map<java.util.Locale, String> nameMap, double priority,
			String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.addOrUpdateCPOptionValue(
			externalReferenceCode, cpOptionId, nameMap, priority, key,
			serviceContext);
	}

	@Override
	public void deleteCPOptionValue(long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpOptionValueService.deleteCPOptionValue(cpOptionValueId);
	}

	@Override
	public CPOptionValue fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public CPOptionValue fetchCPOptionValue(long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.fetchCPOptionValue(cpOptionValueId);
	}

	@Override
	public CPOptionValue getCPOptionValue(long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.getCPOptionValue(cpOptionValueId);
	}

	@Override
	public java.util.List<CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.getCPOptionValues(cpOptionId, start, end);
	}

	@Override
	public int getCPOptionValuesCount(long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.getCPOptionValuesCount(cpOptionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpOptionValueService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<CPOptionValue>
			searchCPOptionValues(
				long companyId, long cpOptionId, String keywords, int start,
				int end, com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.searchCPOptionValues(
			companyId, cpOptionId, keywords, start, end, sorts);
	}

	@Override
	public int searchCPOptionValuesCount(
			long companyId, long cpOptionId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.searchCPOptionValuesCount(
			companyId, cpOptionId, keywords);
	}

	@Override
	public CPOptionValue updateCPOptionValue(
			long cpOptionValueId,
			java.util.Map<java.util.Locale, String> nameMap, double priority,
			String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.updateCPOptionValue(
			cpOptionValueId, nameMap, priority, key, serviceContext);
	}

	@Override
	public CPOptionValueService getWrappedService() {
		return _cpOptionValueService;
	}

	@Override
	public void setWrappedService(CPOptionValueService cpOptionValueService) {
		_cpOptionValueService = cpOptionValueService;
	}

	private CPOptionValueService _cpOptionValueService;

}
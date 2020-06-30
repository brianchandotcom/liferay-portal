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

	public CPOptionValueServiceWrapper(
		CPOptionValueService cpOptionValueService) {

		_cpOptionValueService = cpOptionValueService;
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue addCPOptionValue(
			long cpOptionId, java.util.Map<java.util.Locale, String> titleMap,
			double priority, String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.addCPOptionValue(
			cpOptionId, titleMap, priority, key, serviceContext);
	}

	@Override
	public void deleteCPOptionValue(long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpOptionValueService.deleteCPOptionValue(cpOptionValueId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue fetchCPOptionValue(
			long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.fetchCPOptionValue(cpOptionValueId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue getCPOptionValue(
			long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.getCPOptionValue(cpOptionValueId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionValue>
			getCPOptionValues(long cpOptionId, int start, int end)
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
	public com.liferay.commerce.product.model.CPOptionValue updateCPOptionValue(
			long cpOptionValueId,
			java.util.Map<java.util.Locale, String> titleMap, double priority,
			String key,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.updateCPOptionValue(
			cpOptionValueId, titleMap, priority, key, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue upsertCPOptionValue(
			long cpOptionId, java.util.Map<java.util.Locale, String> nameMap,
			double priority, String key, String externalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpOptionValueService.upsertCPOptionValue(
			cpOptionId, nameMap, priority, key, externalReferenceCode,
			serviceContext);
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
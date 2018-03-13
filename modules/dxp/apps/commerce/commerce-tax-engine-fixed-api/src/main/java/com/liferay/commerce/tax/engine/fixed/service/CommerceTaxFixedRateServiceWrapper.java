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

package com.liferay.commerce.tax.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTaxFixedRateService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateService
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateServiceWrapper
	implements CommerceTaxFixedRateService,
		ServiceWrapper<CommerceTaxFixedRateService> {
	public CommerceTaxFixedRateServiceWrapper(
		CommerceTaxFixedRateService commerceTaxFixedRateService) {
		_commerceTaxFixedRateService = commerceTaxFixedRateService;
	}

	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate addCommerceTaxFixedRate(
		long commerceTaxMethodId, long commerceTaxCategoryId, double rate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateService.addCommerceTaxFixedRate(commerceTaxMethodId,
			commerceTaxCategoryId, rate, serviceContext);
	}

	@Override
	public void deleteCommerceTaxFixedRate(long commerceTaxFixedRateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceTaxFixedRateService.deleteCommerceTaxFixedRate(commerceTaxFixedRateId);
	}

	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate fetchCommerceTaxFixedRate(
		long commerceTaxFixedRateId) {
		return _commerceTaxFixedRateService.fetchCommerceTaxFixedRate(commerceTaxFixedRateId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxCategory> getAvailableCommerceTaxCategories(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateService.getAvailableCommerceTaxCategories(groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate> getCommerceTaxFixedRates(
		long commerceTaxMethodId, int start, int end) {
		return _commerceTaxFixedRateService.getCommerceTaxFixedRates(commerceTaxMethodId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate> getCommerceTaxFixedRates(
		long commerceTaxMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate> orderByComparator) {
		return _commerceTaxFixedRateService.getCommerceTaxFixedRates(commerceTaxMethodId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxFixedRatesCount(long commerceTaxMethodId) {
		return _commerceTaxFixedRateService.getCommerceTaxFixedRatesCount(commerceTaxMethodId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceTaxFixedRateService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate updateCommerceTaxFixedRate(
		long commerceTaxFixedRateId, double rate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxFixedRateService.updateCommerceTaxFixedRate(commerceTaxFixedRateId,
			rate);
	}

	@Override
	public CommerceTaxFixedRateService getWrappedService() {
		return _commerceTaxFixedRateService;
	}

	@Override
	public void setWrappedService(
		CommerceTaxFixedRateService commerceTaxFixedRateService) {
		_commerceTaxFixedRateService = commerceTaxFixedRateService;
	}

	private CommerceTaxFixedRateService _commerceTaxFixedRateService;
}
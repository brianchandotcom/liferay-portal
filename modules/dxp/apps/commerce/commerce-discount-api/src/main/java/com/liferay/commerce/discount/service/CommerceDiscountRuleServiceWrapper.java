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

package com.liferay.commerce.discount.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceDiscountRuleService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountRuleService
 * @generated
 */
@ProviderType
public class CommerceDiscountRuleServiceWrapper
	implements CommerceDiscountRuleService,
		ServiceWrapper<CommerceDiscountRuleService> {
	public CommerceDiscountRuleServiceWrapper(
		CommerceDiscountRuleService commerceDiscountRuleService) {
		_commerceDiscountRuleService = commerceDiscountRuleService;
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule addCommerceDiscountRule(
		long commerceDiscountId, String type, String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleService.addCommerceDiscountRule(commerceDiscountId,
			type, typeSettings, serviceContext);
	}

	@Override
	public void deleteCommerceDiscountRule(long commerceDiscountRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceDiscountRuleService.deleteCommerceDiscountRule(commerceDiscountRuleId);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule getCommerceDiscountRule(
		long commerceDiscountRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleService.getCommerceDiscountRule(commerceDiscountRuleId);
	}

	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRule> getCommerceDiscountRules(
		long commerceDiscountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscountRule> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleService.getCommerceDiscountRules(commerceDiscountId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountRulesCount(long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleService.getCommerceDiscountRulesCount(commerceDiscountId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDiscountRuleService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRule updateCommerceDiscountRule(
		long commerceDiscountRuleId, String type, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRuleService.updateCommerceDiscountRule(commerceDiscountRuleId,
			type, typeSettings);
	}

	@Override
	public CommerceDiscountRuleService getWrappedService() {
		return _commerceDiscountRuleService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountRuleService commerceDiscountRuleService) {
		_commerceDiscountRuleService = commerceDiscountRuleService;
	}

	private CommerceDiscountRuleService _commerceDiscountRuleService;
}
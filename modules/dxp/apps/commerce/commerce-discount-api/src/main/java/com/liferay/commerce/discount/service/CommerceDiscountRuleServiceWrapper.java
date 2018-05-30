/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
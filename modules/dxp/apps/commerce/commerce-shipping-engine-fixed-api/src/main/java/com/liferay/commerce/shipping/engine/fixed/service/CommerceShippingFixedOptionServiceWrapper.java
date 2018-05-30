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

package com.liferay.commerce.shipping.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShippingFixedOptionService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionService
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionServiceWrapper
	implements CommerceShippingFixedOptionService,
		ServiceWrapper<CommerceShippingFixedOptionService> {
	public CommerceShippingFixedOptionServiceWrapper(
		CommerceShippingFixedOptionService commerceShippingFixedOptionService) {
		_commerceShippingFixedOptionService = commerceShippingFixedOptionService;
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption addCommerceShippingFixedOption(
		long commerceShippingMethodId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.math.BigDecimal amount, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionService.addCommerceShippingFixedOption(commerceShippingMethodId,
			nameMap, descriptionMap, amount, priority, serviceContext);
	}

	@Override
	public void deleteCommerceShippingFixedOption(
		long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceShippingFixedOptionService.deleteCommerceShippingFixedOption(commerceShippingFixedOptionId);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption fetchCommerceShippingFixedOption(
		long commerceShippingFixedOptionId) {
		return _commerceShippingFixedOptionService.fetchCommerceShippingFixedOption(commerceShippingFixedOptionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end) {
		return _commerceShippingFixedOptionService.getCommerceShippingFixedOptions(commerceShippingMethodId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> orderByComparator) {
		return _commerceShippingFixedOptionService.getCommerceShippingFixedOptions(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingFixedOptionsCount(
		long commerceShippingMethodId) {
		return _commerceShippingFixedOptionService.getCommerceShippingFixedOptionsCount(commerceShippingMethodId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShippingFixedOptionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption updateCommerceShippingFixedOption(
		long commerceShippingFixedOptionId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.math.BigDecimal amount, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionService.updateCommerceShippingFixedOption(commerceShippingFixedOptionId,
			nameMap, descriptionMap, amount, priority);
	}

	@Override
	public CommerceShippingFixedOptionService getWrappedService() {
		return _commerceShippingFixedOptionService;
	}

	@Override
	public void setWrappedService(
		CommerceShippingFixedOptionService commerceShippingFixedOptionService) {
		_commerceShippingFixedOptionService = commerceShippingFixedOptionService;
	}

	private CommerceShippingFixedOptionService _commerceShippingFixedOptionService;
}
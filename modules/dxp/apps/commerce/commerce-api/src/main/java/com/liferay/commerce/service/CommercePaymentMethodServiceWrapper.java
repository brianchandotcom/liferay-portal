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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePaymentMethodService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePaymentMethodService
 * @generated
 */
@ProviderType
public class CommercePaymentMethodServiceWrapper
	implements CommercePaymentMethodService,
		ServiceWrapper<CommercePaymentMethodService> {
	public CommercePaymentMethodServiceWrapper(
		CommercePaymentMethodService commercePaymentMethodService) {
		_commercePaymentMethodService = commercePaymentMethodService;
	}

	@Override
	public com.liferay.commerce.model.CommercePaymentMethod addCommercePaymentMethod(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.io.File imageFile, String engineKey,
		java.util.Map<String, String> engineParameterMap, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePaymentMethodService.addCommercePaymentMethod(nameMap,
			descriptionMap, imageFile, engineKey, engineParameterMap, priority,
			active, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommercePaymentMethod createCommercePaymentMethod(
		long commercePaymentMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePaymentMethodService.createCommercePaymentMethod(commercePaymentMethodId);
	}

	@Override
	public void deleteCommercePaymentMethod(long commercePaymentMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePaymentMethodService.deleteCommercePaymentMethod(commercePaymentMethodId);
	}

	@Override
	public com.liferay.commerce.model.CommercePaymentMethod getCommercePaymentMethod(
		long commercePaymentMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePaymentMethodService.getCommercePaymentMethod(commercePaymentMethodId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePaymentMethod> getCommercePaymentMethods(
		long groupId) {
		return _commercePaymentMethodService.getCommercePaymentMethods(groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePaymentMethod> getCommercePaymentMethods(
		long groupId, boolean active) {
		return _commercePaymentMethodService.getCommercePaymentMethods(groupId,
			active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePaymentMethod> getCommercePaymentMethods(
		long groupId, long commerceCountryId, boolean active) {
		return _commercePaymentMethodService.getCommercePaymentMethods(groupId,
			commerceCountryId, active);
	}

	@Override
	public int getCommercePaymentMethodsCount(long groupId, boolean active) {
		return _commercePaymentMethodService.getCommercePaymentMethodsCount(groupId,
			active);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePaymentMethodService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommercePaymentMethod setActive(
		long commercePaymentMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePaymentMethodService.setActive(commercePaymentMethodId,
			active);
	}

	@Override
	public com.liferay.commerce.model.CommercePaymentMethod updateCommercePaymentMethod(
		long commercePaymentMethodId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.io.File imageFile,
		java.util.Map<String, String> engineParameterMap, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePaymentMethodService.updateCommercePaymentMethod(commercePaymentMethodId,
			nameMap, descriptionMap, imageFile, engineParameterMap, priority,
			active, serviceContext);
	}

	@Override
	public CommercePaymentMethodService getWrappedService() {
		return _commercePaymentMethodService;
	}

	@Override
	public void setWrappedService(
		CommercePaymentMethodService commercePaymentMethodService) {
		_commercePaymentMethodService = commercePaymentMethodService;
	}

	private CommercePaymentMethodService _commercePaymentMethodService;
}
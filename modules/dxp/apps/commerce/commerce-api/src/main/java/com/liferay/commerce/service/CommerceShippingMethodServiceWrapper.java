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
 * Provides a wrapper for {@link CommerceShippingMethodService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethodService
 * @generated
 */
@ProviderType
public class CommerceShippingMethodServiceWrapper
	implements CommerceShippingMethodService,
		ServiceWrapper<CommerceShippingMethodService> {
	public CommerceShippingMethodServiceWrapper(
		CommerceShippingMethodService commerceShippingMethodService) {
		_commerceShippingMethodService = commerceShippingMethodService;
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod addCommerceShippingMethod(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.io.File imageFile, String engineKey, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingMethodService.addCommerceShippingMethod(nameMap,
			descriptionMap, imageFile, engineKey, priority, active,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod createCommerceShippingMethod(
		long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingMethodService.createCommerceShippingMethod(commerceShippingMethodId);
	}

	@Override
	public void deleteCommerceShippingMethod(long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceShippingMethodService.deleteCommerceShippingMethod(commerceShippingMethodId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod fetchCommerceShippingMethod(
		long groupId, String engineKey) {
		return _commerceShippingMethodService.fetchCommerceShippingMethod(groupId,
			engineKey);
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod getCommerceShippingMethod(
		long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingMethodService.getCommerceShippingMethod(commerceShippingMethodId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShippingMethod> getCommerceShippingMethods(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingMethodService.getCommerceShippingMethods(groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShippingMethod> getCommerceShippingMethods(
		long groupId, boolean active) {
		return _commerceShippingMethodService.getCommerceShippingMethods(groupId,
			active);
	}

	@Override
	public int getCommerceShippingMethodsCount(long groupId, boolean active) {
		return _commerceShippingMethodService.getCommerceShippingMethodsCount(groupId,
			active);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShippingMethodService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod setActive(
		long commerceShippingMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingMethodService.setActive(commerceShippingMethodId,
			active);
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingMethod updateCommerceShippingMethod(
		long commerceShippingMethodId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.io.File imageFile, double priority, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingMethodService.updateCommerceShippingMethod(commerceShippingMethodId,
			nameMap, descriptionMap, imageFile, priority, active);
	}

	@Override
	public CommerceShippingMethodService getWrappedService() {
		return _commerceShippingMethodService;
	}

	@Override
	public void setWrappedService(
		CommerceShippingMethodService commerceShippingMethodService) {
		_commerceShippingMethodService = commerceShippingMethodService;
	}

	private CommerceShippingMethodService _commerceShippingMethodService;
}
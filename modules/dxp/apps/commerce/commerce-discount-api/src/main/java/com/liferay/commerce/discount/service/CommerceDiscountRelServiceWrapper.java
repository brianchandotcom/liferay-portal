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
 * Provides a wrapper for {@link CommerceDiscountRelService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountRelService
 * @generated
 */
@ProviderType
public class CommerceDiscountRelServiceWrapper
	implements CommerceDiscountRelService,
		ServiceWrapper<CommerceDiscountRelService> {
	public CommerceDiscountRelServiceWrapper(
		CommerceDiscountRelService commerceDiscountRelService) {
		_commerceDiscountRelService = commerceDiscountRelService;
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRel addCommerceDiscountRel(
		long commerceDiscountId, String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRelService.addCommerceDiscountRel(commerceDiscountId,
			className, classPK, serviceContext);
	}

	@Override
	public void deleteCommerceDiscountRel(long commerceDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceDiscountRelService.deleteCommerceDiscountRel(commerceDiscountRelId);
	}

	@Override
	public long[] getClassPKs(long commerceDiscountId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRelService.getClassPKs(commerceDiscountId,
			className);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountRel getCommerceDiscountRel(
		long commerceDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRelService.getCommerceDiscountRel(commerceDiscountRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRelService.getCommerceDiscountRels(commerceDiscountId,
			className);
	}

	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscountRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRelService.getCommerceDiscountRels(commerceDiscountId,
			className, start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountRelsCount(long commerceDiscountId,
		String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountRelService.getCommerceDiscountRelsCount(commerceDiscountId,
			className);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDiscountRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceDiscountRelService getWrappedService() {
		return _commerceDiscountRelService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountRelService commerceDiscountRelService) {
		_commerceDiscountRelService = commerceDiscountRelService;
	}

	private CommerceDiscountRelService _commerceDiscountRelService;
}
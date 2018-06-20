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
 * Provides a wrapper for {@link CommerceShipmentService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentService
 * @generated
 */
@ProviderType
public class CommerceShipmentServiceWrapper implements CommerceShipmentService,
	ServiceWrapper<CommerceShipmentService> {
	public CommerceShipmentServiceWrapper(
		CommerceShipmentService commerceShipmentService) {
		_commerceShipmentService = commerceShipmentService;
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment addCommerceShipment(
		long commerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentService.addCommerceShipment(commerceOrderId,
			serviceContext);
	}

	@Override
	public void deleteCommerceShipment(long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceShipmentService.deleteCommerceShipment(commerceShipmentId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment getCommerceShipment(
		long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentService.getCommerceShipment(commerceShipmentId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipment> getCommerceShipmentsByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipment> orderByComparator) {
		return _commerceShipmentService.getCommerceShipmentsByGroupId(groupId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipment> getCommerceShipmentsBySiteGroupId(
		long siteGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipment> orderByComparator) {
		return _commerceShipmentService.getCommerceShipmentsBySiteGroupId(siteGroupId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceShipmentsCountByGroupId(long groupId) {
		return _commerceShipmentService.getCommerceShipmentsCountByGroupId(groupId);
	}

	@Override
	public int getCommerceShipmentsCountBySiteGroupId(long siteGroupId) {
		return _commerceShipmentService.getCommerceShipmentsCountBySiteGroupId(siteGroupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShipmentService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment updateCommerceShipment(
		long commerceShipmentId, String carrier, String trackingNumber,
		int status, int shippingDateMonth, int shippingDateDay,
		int shippingDateYear, int shippingDateHour, int shippingDateMinute,
		int expectedDateMonth, int expectedDateDay, int expectedDateYear,
		int expectedDateHour, int expectedDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentService.updateCommerceShipment(commerceShipmentId,
			carrier, trackingNumber, status, shippingDateMonth,
			shippingDateDay, shippingDateYear, shippingDateHour,
			shippingDateMinute, expectedDateMonth, expectedDateDay,
			expectedDateYear, expectedDateHour, expectedDateMinute);
	}

	@Override
	public CommerceShipmentService getWrappedService() {
		return _commerceShipmentService;
	}

	@Override
	public void setWrappedService(
		CommerceShipmentService commerceShipmentService) {
		_commerceShipmentService = commerceShipmentService;
	}

	private CommerceShipmentService _commerceShipmentService;
}
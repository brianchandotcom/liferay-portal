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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.base.CommerceShipmentServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentServiceImpl
	extends CommerceShipmentServiceBaseImpl {

	@Override
	public CommerceShipment addCommerceShipment(
			long commerceOrderId, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.addCommerceShipment(
			commerceOrderId, serviceContext);
	}

	@Override
	public void deleteCommerceShipment(long commerceShipmentId)
		throws PortalException {

		CommerceShipment commerceShipment =
			commerceShipmentPersistence.findByPrimaryKey(commerceShipmentId);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceShipment.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		commerceShipmentLocalService.deleteCommerceShipment(commerceShipment);
	}

	@Override
	public CommerceShipment getCommerceShipment(long commerceShipmentId)
		throws PortalException {

		CommerceShipment commerceShipment =
			commerceShipmentPersistence.findByPrimaryKey(commerceShipmentId);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceShipment.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipment(
			commerceShipmentId);
	}

	@Override
	public List<CommerceShipment> getCommerceShipmentsByG_S(
			long groupId, int status, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipmentsByG_S(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public List<CommerceShipment> getCommerceShipmentsByGroupId(
			long groupId, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipmentsByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceShipment> getCommerceShipmentsByS_S(
			long siteGroupId, int status, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), siteGroupId,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipmentsByS_S(
			siteGroupId, status, start, end, orderByComparator);
	}

	@Override
	public List<CommerceShipment> getCommerceShipmentsBySiteGroupId(
			long siteGroupId, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), siteGroupId,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipmentsBySiteGroupId(
			siteGroupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShipmentsCountByG_S(long groupId, int status)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipmentsCountByG_S(
			groupId, status);
	}

	@Override
	public int getCommerceShipmentsCountByGroupId(long groupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipmentsCountByGroupId(
			groupId);
	}

	@Override
	public int getCommerceShipmentsCountByS_S(long siteGroupId, int status)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), siteGroupId,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipmentsCountByS_S(
			siteGroupId, status);
	}

	@Override
	public int getCommerceShipmentsCountBySiteGroupId(long siteGroupId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), siteGroupId,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return
			commerceShipmentLocalService.getCommerceShipmentsCountBySiteGroupId(
				siteGroupId);
	}

	@Override
	public CommerceShipment updateCommerceShipment(
			long commerceShipmentId, String carrier, String trackingNumber,
			int status, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute,
			int expectedDateMonth, int expectedDateDay, int expectedDateYear,
			int expectedDateHour, int expectedDateMinute)
		throws PortalException {

		CommerceShipment commerceShipment =
			commerceShipmentPersistence.findByPrimaryKey(commerceShipmentId);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceShipment.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateCommerceShipment(
			commerceShipmentId, carrier, trackingNumber, status,
			shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, expectedDateMonth,
			expectedDateDay, expectedDateYear, expectedDateHour,
			expectedDateMinute);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceShipmentServiceImpl.class, "_portletResourcePermission",
				CommerceConstants.RESOURCE_NAME);

}
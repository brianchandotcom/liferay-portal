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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceShipmentExpectedDateException;
import com.liferay.commerce.exception.CommerceShipmentShippingDateException;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.base.CommerceShipmentLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentLocalServiceImpl
	extends CommerceShipmentLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShipment addCommerceShipment(
			long shipmentUserId, long commerceAddressId,
			long commerceShippingMethodId, long commerceWarehouseId,
			String carrier, String trackingNumber, int expectedDuration,
			int status, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute,
			int expectedDateMonth, int expectedDateDay, int expectedDateYear,
			int expectedDateHour, int expectedDateMinute,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		Date shippingDate = PortalUtil.getDate(
			shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, user.getTimeZone(),
			CommerceShipmentShippingDateException.class);

		Date expectedDate = PortalUtil.getDate(
			expectedDateMonth, expectedDateDay, expectedDateYear,
			expectedDateHour, expectedDateMinute, user.getTimeZone(),
			CommerceShipmentExpectedDateException.class);

		long commerceShipmentId = counterLocalService.increment();

		CommerceShipment commerceShipment = commerceShipmentPersistence.create(
			commerceShipmentId);

		commerceShipment.setGroupId(groupId);
		commerceShipment.setCompanyId(user.getCompanyId());
		commerceShipment.setUserId(user.getUserId());
		commerceShipment.setUserName(user.getFullName());
		commerceShipment.setShipmentUserId(shipmentUserId);
		commerceShipment.setCommerceAddressId(commerceAddressId);
		commerceShipment.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceShipment.setCommerceWarehouseId(commerceWarehouseId);
		commerceShipment.setCarrier(carrier);
		commerceShipment.setTrackingNumber(trackingNumber);
		commerceShipment.setExpectedDuration(expectedDuration);
		commerceShipment.setStatus(status);
		commerceShipment.setShippingDate(shippingDate);
		commerceShipment.setExpectedDate(expectedDate);

		return commerceShipmentPersistence.update(commerceShipment);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceShipment deleteCommerceShipment(
		CommerceShipment commerceShipment) {

		// Commerce shipment

		commerceShipmentPersistence.remove(commerceShipment);

		// Commerce shipment items

		commerceShipmentItemLocalService.deleteCommerceShipmentItems(
			commerceShipment.getCommerceShipmentId());

		return commerceShipment;
	}

	@Override
	public CommerceShipment deleteCommerceShipment(long commerceShipmentId)
		throws PortalException {

		CommerceShipment commerceShipment =
			commerceShipmentPersistence.findByPrimaryKey(commerceShipmentId);

		return commerceShipmentLocalService.deleteCommerceShipment(
			commerceShipment);
	}

	@Override
	public List<CommerceShipment> getCommerceShipments(
		long groupId, int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator) {

		return commerceShipmentPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShipmentsCount(long groupId) {
		return commerceShipmentPersistence.countByGroupId(groupId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShipment updateCommerceShipment(
			long commerceShipmentId, long shipmentUserId,
			long commerceAddressId, long commerceShippingMethodId,
			String carrier, String trackingNumber, int expectedDuration,
			int status, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute,
			int expectedDateMonth, int expectedDateDay, int expectedDateYear,
			int expectedDateHour, int expectedDateMinute)
		throws PortalException {

		CommerceShipment commerceShipment =
			commerceShipmentPersistence.findByPrimaryKey(commerceShipmentId);

		User user = userLocalService.getUser(commerceShipment.getUserId());

		Date shippingDate = PortalUtil.getDate(
			shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, user.getTimeZone(),
			CommerceShipmentShippingDateException.class);

		Date expectedDate = PortalUtil.getDate(
			expectedDateMonth, expectedDateDay, expectedDateYear,
			expectedDateHour, expectedDateMinute, user.getTimeZone(),
			CommerceShipmentExpectedDateException.class);

		commerceShipment.setShipmentUserId(shipmentUserId);
		commerceShipment.setCommerceAddressId(commerceAddressId);
		commerceShipment.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceShipment.setCarrier(carrier);
		commerceShipment.setTrackingNumber(trackingNumber);
		commerceShipment.setExpectedDuration(expectedDuration);
		commerceShipment.setStatus(status);
		commerceShipment.setShippingDate(shippingDate);
		commerceShipment.setExpectedDate(expectedDate);

		return commerceShipmentPersistence.update(commerceShipment);
	}

}
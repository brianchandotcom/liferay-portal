/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.base.CommerceShipmentServiceBaseImpl;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceShipment"
	},
	service = AopService.class
)
public class CommerceShipmentServiceImpl
	extends CommerceShipmentServiceBaseImpl {

	@Override
	public CommerceShipment addCommerceShipment(
			long commerceOrderId, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.addCommerceShipment(
			commerceOrderId, serviceContext);
	}

	@Override
	public CommerceShipment addCommerceShipment(
			String externalReferenceCode, long groupId, long commerceAccountId,
			long commerceAddressId, long commerceShippingMethodId,
			String commerceShippingOptionName, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.addCommerceShipment(
			externalReferenceCode, groupId, commerceAccountId,
			commerceAddressId, commerceShippingMethodId,
			commerceShippingOptionName, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	@Override
	public void deleteCommerceShipment(long commerceShipmentId)
		throws PortalException {

		deleteCommerceShipment(commerceShipmentId, false);
	}

	@Override
	public void deleteCommerceShipment(
			long commerceShipmentId, boolean restoreStockQuantity)
		throws PortalException {

		CommerceShipment commerceShipment =
			commerceShipmentPersistence.findByPrimaryKey(commerceShipmentId);

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		commerceShipmentLocalService.deleteCommerceShipment(
			commerceShipment, restoreStockQuantity);
	}

	@Override
	public CommerceShipment fetchCommerceShipmentByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.
			fetchCommerceShipmentByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public CommerceShipment getCommerceShipment(long commerceShipmentId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipment(
			commerceShipmentId);
	}

	@Override
	public List<CommerceShipment> getCommerceShipments(
			long companyId, int status, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		List<CommerceChannel> commerceChannels = _commerceChannelService.search(
			companyId);

		if (commerceChannels.isEmpty()) {
			return Collections.emptyList();
		}

		return commerceShipmentLocalService.getCommerceShipments(
			TransformUtil.transformToLongArray(
				commerceChannels, CommerceChannel::getGroupId),
			status, start, end, orderByComparator);
	}

	@Override
	public List<CommerceShipment> getCommerceShipments(
			long companyId, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		List<CommerceChannel> commerceChannels = _commerceChannelService.search(
			companyId);

		if (commerceChannels.isEmpty()) {
			return Collections.emptyList();
		}

		return commerceShipmentLocalService.getCommerceShipments(
			TransformUtil.transformToLongArray(
				commerceChannels, CommerceChannel::getGroupId),
			start, end, orderByComparator);
	}

	@Override
	public List<CommerceShipment> getCommerceShipments(
			long companyId, long commerceAddressId, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		List<CommerceChannel> commerceChannels = _commerceChannelService.search(
			companyId);

		if (commerceChannels.isEmpty()) {
			return Collections.emptyList();
		}

		return commerceShipmentLocalService.getCommerceShipments(
			TransformUtil.transformToLongArray(
				commerceChannels, CommerceChannel::getGroupId),
			commerceAddressId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceShipment> getCommerceShipments(
			long companyId, long[] groupIds, long[] commerceAccountIds,
			String keywords, int[] shipmentStatuses,
			boolean excludeShipmentStatus, int start, int end)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipments(
			companyId, groupIds, commerceAccountIds, keywords, shipmentStatuses,
			excludeShipmentStatus, start, end);
	}

	@Override
	public List<CommerceShipment> getCommerceShipmentsByOrderId(
		long commerceOrderId, int start, int end) {

		return commerceShipmentLocalService.getCommerceShipments(
			commerceOrderId, start, end);
	}

	@Override
	public int getCommerceShipmentsCount(long companyId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		List<CommerceChannel> commerceChannels = _commerceChannelService.search(
			companyId);

		if (commerceChannels.isEmpty()) {
			return 0;
		}

		return commerceShipmentLocalService.getCommerceShipmentsCount(
			TransformUtil.transformToLongArray(
				commerceChannels, CommerceChannel::getGroupId));
	}

	@Override
	public int getCommerceShipmentsCount(long companyId, int status)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		List<CommerceChannel> commerceChannels = _commerceChannelService.search(
			companyId);

		if (commerceChannels.isEmpty()) {
			return 0;
		}

		return commerceShipmentLocalService.getCommerceShipmentsCount(
			TransformUtil.transformToLongArray(
				commerceChannels, CommerceChannel::getGroupId),
			status);
	}

	@Override
	public int getCommerceShipmentsCount(long companyId, long commerceAddressId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		List<CommerceChannel> commerceChannels = _commerceChannelService.search(
			companyId);

		if (commerceChannels.isEmpty()) {
			return 0;
		}

		return commerceShipmentLocalService.getCommerceShipmentsCount(
			TransformUtil.transformToLongArray(
				commerceChannels, CommerceChannel::getGroupId),
			commerceAddressId);
	}

	@Override
	public int getCommerceShipmentsCount(
			long companyId, long[] groupIds, long[] commerceAccountIds,
			String keywords, int[] shipmentStatuses,
			boolean excludeShipmentStatus)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.getCommerceShipmentsCount(
			companyId, groupIds, commerceAccountIds, keywords, shipmentStatuses,
			excludeShipmentStatus);
	}

	@Override
	public int getCommerceShipmentsCountByOrderId(long commerceOrderId) {
		return commerceShipmentLocalService.getCommerceShipmentsCount(
			commerceOrderId);
	}

	@Override
	public CommerceShipment reprocessCommerceShipment(long commerceShipmentId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.reprocessCommerceShipment(
			commerceShipmentId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * #updateAddress(long, String, String, String, String, String, String,
	 * String, long, long, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CommerceShipment updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber)
		throws PortalException {

		return updateAddress(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, null);
	}

	@Override
	public CommerceShipment updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateAddress(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, serviceContext);
	}

	@Override
	public CommerceShipment updateCarrierDetails(
			long commerceShipmentId, long commerceShippingMethodId,
			String carrier, String trackingNumber, String trackingURL)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateCarrierDetails(
			commerceShipmentId, commerceShippingMethodId, carrier,
			trackingNumber, trackingURL);
	}

	@Override
	public CommerceShipment updateCommerceShipment(
			CommerceShipment commerceShipment)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateCommerceShipment(
			commerceShipment);
	}

	@Override
	public CommerceShipment updateCommerceShipment(
			long commerceShipmentId, long commerceShippingMethodId,
			String carrier, int expectedDateMonth, int expectedDateDay,
			int expectedDateYear, int expectedDateHour, int expectedDateMinute,
			int shippingDateMonth, int shippingDateDay, int shippingDateYear,
			int shippingDateHour, int shippingDateMinute, String trackingNumber,
			String trackingURL, int status, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateCommerceShipment(
			commerceShipmentId, commerceShippingMethodId, carrier,
			expectedDateMonth, expectedDateDay, expectedDateYear,
			expectedDateHour, expectedDateMinute, shippingDateMonth,
			shippingDateDay, shippingDateYear, shippingDateHour,
			shippingDateMinute, trackingNumber, trackingURL, status,
			serviceContext);
	}

	@Override
	public CommerceShipment updateCommerceShipment(
			long commerceShipmentId, long commerceShippingMethodId,
			String carrier, int expectedDateMonth, int expectedDateDay,
			int expectedDateYear, int expectedDateHour, int expectedDateMinute,
			int shippingDateMonth, int shippingDateDay, int shippingDateYear,
			int shippingDateHour, int shippingDateMinute, String trackingNumber,
			String trackingURL, int status, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateCommerceShipment(
			commerceShipmentId, commerceShippingMethodId, carrier,
			expectedDateMonth, expectedDateDay, expectedDateYear,
			expectedDateHour, expectedDateMinute, shippingDateMonth,
			shippingDateDay, shippingDateYear, shippingDateHour,
			shippingDateMinute, trackingNumber, trackingURL, status, name,
			description, street1, street2, street3, city, zip, regionId,
			countryId, phoneNumber, serviceContext);
	}

	@Override
	public CommerceShipment updateExpectedDate(
			long commerceShipmentId, int expectedDateMonth, int expectedDateDay,
			int expectedDateYear, int expectedDateHour, int expectedDateMinute)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateExpectedDate(
			commerceShipmentId, expectedDateMonth, expectedDateDay,
			expectedDateYear, expectedDateHour, expectedDateMinute);
	}

	@Override
	public CommerceShipment updateExternalReferenceCode(
			long commerceShipmentId, String externalReferenceCode)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateExternalReferenceCode(
			commerceShipmentId, externalReferenceCode);
	}

	@Override
	public CommerceShipment updateShippingDate(
			long commerceShipmentId, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateShippingDate(
			commerceShipmentId, shippingDateMonth, shippingDateDay,
			shippingDateYear, shippingDateHour, shippingDateMinute);
	}

	@Override
	public CommerceShipment updateStatus(long commerceShipmentId, int status)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentLocalService.updateStatus(
			commerceShipmentId, status);
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_COMMERCE_SHIPMENT + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}
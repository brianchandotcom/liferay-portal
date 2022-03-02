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

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommerceShipment. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceShipment"
	},
	service = CommerceShipmentService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceShipmentService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceShipmentServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce shipment remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceShipmentServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceShipment addCommerceShipment(
			long commerceOrderId, ServiceContext serviceContext)
		throws PortalException;

	public CommerceShipment addCommerceShipment(
			String externalReferenceCode, long groupId, long commerceAccountId,
			long commerceAddressId, long commerceShippingMethodId,
			String commerceShippingOptionName, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	public void deleteCommerceShipment(long commerceShipmentId)
		throws PortalException;

	public void deleteCommerceShipment(
			long commerceShipmentId, boolean restoreStockQuantity)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipment fetchCommerceShipment(
			long companyId, String externalReferenceCode)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipment getCommerceShipment(long commerceShipmentId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipment> getCommerceShipments(
			long companyId, int status, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipment> getCommerceShipments(
			long companyId, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipment> getCommerceShipments(
			long companyId, long commerceAddressId, int start, int end,
			OrderByComparator<CommerceShipment> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipment> getCommerceShipments(
			long companyId, long[] groupIds, long[] commerceAccountIds,
			String keywords, int[] shipmentStatuses,
			boolean excludeShipmentStatus, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipment> getCommerceShipmentsByOrderId(
		long commerceOrderId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentsCount(long companyId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentsCount(long companyId, int status)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentsCount(long companyId, long commerceAddressId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentsCount(
			long companyId, long[] groupIds, long[] commerceAccountIds,
			String keywords, int[] shipmentStatuses,
			boolean excludeShipmentStatus)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentsCountByOrderId(long commerceOrderId);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommerceShipment reprocessCommerceShipment(long commerceShipmentId)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #updateAddress(long, String, String, String, String, String, String,
	 String, long, long, String, ServiceContext)}
	 */
	@Deprecated
	public CommerceShipment updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber)
		throws PortalException;

	public CommerceShipment updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceShipment updateCarrierDetails(
			long commerceShipmentId, String carrier, String trackingNumber)
		throws PortalException;

	public CommerceShipment updateCommerceShipment(
			long commerceShipmentId, String carrier, String trackingNumber,
			int status, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute,
			int expectedDateMonth, int expectedDateDay, int expectedDateYear,
			int expectedDateHour, int expectedDateMinute)
		throws PortalException;

	public CommerceShipment updateCommerceShipment(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			String carrier, String trackingNumber, int status,
			int shippingDateMonth, int shippingDateDay, int shippingDateYear,
			int shippingDateHour, int shippingDateMinute, int expectedDateMonth,
			int expectedDateDay, int expectedDateYear, int expectedDateHour,
			int expectedDateMinute)
		throws PortalException;

	public CommerceShipment updateCommerceShipmentExternalReferenceCode(
			String externalReferenceCode, long commerceShipmentId)
		throws PortalException;

	public CommerceShipment updateExpectedDate(
			long commerceShipmentId, int expectedDateMonth, int expectedDateDay,
			int expectedDateYear, int expectedDateHour, int expectedDateMinute)
		throws PortalException;

	public CommerceShipment updateShippingDate(
			long commerceShipmentId, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute)
		throws PortalException;

	public CommerceShipment updateStatus(long commerceShipmentId, int status)
		throws PortalException;

}
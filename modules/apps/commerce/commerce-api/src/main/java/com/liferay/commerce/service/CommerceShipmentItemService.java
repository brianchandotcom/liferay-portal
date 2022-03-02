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

import com.liferay.commerce.model.CommerceShipmentItem;
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
 * Provides the remote service interface for CommerceShipmentItem. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceShipmentItem"
	},
	service = CommerceShipmentItemService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceShipmentItemService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceShipmentItemServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce shipment item remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceShipmentItemServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceShipmentItem addCommerceShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId, long commerceInventoryWarehouseId,
			int quantity, ServiceContext serviceContext)
		throws PortalException;

	public CommerceShipmentItem addOrUpdateCommerceShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId, long commerceInventoryWarehouseId,
			int quantity, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	public void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws PortalException;

	public void deleteCommerceShipmentItem(
			long commerceShipmentItemId, boolean restoreStockQuantity)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipmentItem fetchCommerceShipmentItem(
			long commerceShipmentId, long commerceOrderItemId,
			long commerceInventoryWarehouseId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipmentItem fetchCommerceShipmentItem(
			long companyId, String externalReferenceCode)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipmentItem getCommerceShipmentItem(
			long commerceShipmentItemId)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipmentItem> getCommerceShipmentItems(
			long commerceOrderItemId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipmentItem> getCommerceShipmentItems(
			long commerceShipmentId, int start, int end,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipmentItem>
			getCommerceShipmentItemsByCommerceOrderItemId(
				long commerceOrderItemId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentItemsCount(long commerceShipmentId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentItemsCountByCommerceOrderItemId(
			long commerceOrderItemId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentOrderItemsQuantity(
			long commerceShipmentId, long commerceOrderItemId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, int quantity)
		throws PortalException;

	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, long commerceInventoryWarehouseId,
			int quantity)
		throws PortalException;

	public CommerceShipmentItem updateCommerceShipmentItemExternalReferenceCode(
			String externalReferenceCode, long commerceShipmentItemId)
		throws PortalException;

}
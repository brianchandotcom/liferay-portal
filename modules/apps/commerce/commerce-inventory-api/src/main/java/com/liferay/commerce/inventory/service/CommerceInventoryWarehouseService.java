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

package com.liferay.commerce.inventory.service;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.auth.PrincipalException;
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
 * Provides the remote service interface for CommerceInventoryWarehouse. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceInventoryWarehouse"
	},
	service = CommerceInventoryWarehouseService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceInventoryWarehouseService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce inventory warehouse remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceInventoryWarehouseServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommerceInventoryWarehouse(String, String, String,
	 boolean, String, String, String, String, String, String,
	 String, double, double, serviceContext)}
	 */
	@Deprecated
	public CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String name, String description, boolean active, String street1,
			String street2, String street3, String city, String zip,
			String commerceRegionCode, String commerceCountryCode,
			double latitude, double longitude, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String externalReferenceCode, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, String commerceRegionCode,
			String commerceCountryCode, double latitude, double longitude,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceInventoryWarehouse deleteCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceInventoryWarehouse fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceInventoryWarehouse fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException;

	public CommerceInventoryWarehouse geolocateCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId, double latitude,
			double longitude)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceInventoryWarehouse getCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
			long companyId, boolean active, int start, int end,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws PrincipalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
			long companyId, boolean active, String commerceCountryCode,
			int start, int end,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
			long companyId, int start, int end,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
			long companyId, long groupId, boolean active)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceInventoryWarehousesCount(long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceInventoryWarehousesCount(
			long companyId, boolean active, String commerceCountryCode)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceInventoryWarehouse> searchCommerceInventoryWarehouses(
			long companyId, Boolean active, String commerceCountryCode,
			String keywords, int start, int end, Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCommerceInventoryWarehousesCount(
			long companyId, Boolean active, String commerceCountryCode,
			String keywords)
		throws PortalException;

	public CommerceInventoryWarehouse setActive(
			long commerceInventoryWarehouseId, boolean active)
		throws PortalException;

	public CommerceInventoryWarehouse updateCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, String commerceRegionCode,
			String commerceCountryCode, double latitude, double longitude,
			long mvccVersion, ServiceContext serviceContext)
		throws PortalException;

}
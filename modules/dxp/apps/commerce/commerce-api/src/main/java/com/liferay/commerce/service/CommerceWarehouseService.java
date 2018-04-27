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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceWarehouse;

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

/**
 * Provides the remote service interface for CommerceWarehouse. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseServiceUtil
 * @see com.liferay.commerce.service.base.CommerceWarehouseServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceWarehouseServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceWarehouse"}, service = CommerceWarehouseService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceWarehouseService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceWarehouseServiceUtil} to access the commerce warehouse remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceWarehouseServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceWarehouse addCommerceWarehouse(String name,
		String description, boolean active, String street1, String street2,
		String street3, String city, String zip, long commerceRegionId,
		long commerceCountryId, double latitude, double longitude,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCommerceWarehouse(long commerceWarehouseId)
		throws PortalException;

	public CommerceWarehouse geolocateCommerceWarehouse(
		long commerceWarehouseId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceWarehouse getCommerceWarehouse(long commerceWarehouseId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWarehouse> getCommerceWarehouses(long groupId,
		boolean active, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWarehouse> getCommerceWarehouses(long groupId,
		boolean active, long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWarehouse> getCommerceWarehouses(long groupId,
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceWarehousesCount(long groupId, boolean active,
		long commerceCountryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceWarehousesCount(long groupId, long commerceCountryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceWarehouse getDefaultCommerceWarehouse(
		ServiceContext serviceContext) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWarehouse> search(long groupId, String keywords,
		boolean all, long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long groupId, String keywords, boolean all,
		long commerceCountryId) throws PortalException;

	public CommerceWarehouse setActive(long commerceWarehouseId, boolean active)
		throws PortalException;

	public CommerceWarehouse updateCommerceWarehouse(long commerceWarehouseId,
		String name, String description, boolean active, String street1,
		String street2, String street3, String city, String zip,
		long commerceRegionId, long commerceCountryId, double latitude,
		double longitude, ServiceContext serviceContext)
		throws PortalException;

	public CommerceWarehouse updateDefaultCommerceWarehouse(
		long commerceWarehouseId, String name, String street1, String street2,
		String street3, String city, String zip, long commerceRegionId,
		long commerceCountryId, double latitude, double longitude,
		ServiceContext serviceContext) throws PortalException;
}
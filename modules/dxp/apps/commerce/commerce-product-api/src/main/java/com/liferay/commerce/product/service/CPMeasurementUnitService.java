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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPMeasurementUnit;

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
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CPMeasurementUnit. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPMeasurementUnitServiceUtil
 * @see com.liferay.commerce.product.service.base.CPMeasurementUnitServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPMeasurementUnitServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPMeasurementUnit"}, service = CPMeasurementUnitService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPMeasurementUnitService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPMeasurementUnitServiceUtil} to access the cp measurement unit remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPMeasurementUnitServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPMeasurementUnit addCPMeasurementUnit(Map<Locale, String> nameMap,
		String key, double rate, boolean primary, double priority, int type,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPMeasurementUnit fetchPrimaryCPMeasurementUnit(long groupId,
		int type);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPMeasurementUnit getCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPMeasurementUnit> getCPMeasurementUnits(long groupId,
		int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPMeasurementUnitsCount(long groupId, int type);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CPMeasurementUnit updateCPMeasurementUnit(long cpMeasurementUnitId,
		Map<Locale, String> nameMap, String key, double rate, boolean primary,
		double priority, int type, ServiceContext serviceContext)
		throws PortalException;
}
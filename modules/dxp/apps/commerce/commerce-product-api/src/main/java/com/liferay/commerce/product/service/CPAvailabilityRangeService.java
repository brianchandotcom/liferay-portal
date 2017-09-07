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

import com.liferay.commerce.product.model.CPAvailabilityRange;

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
 * Provides the remote service interface for CPAvailabilityRange. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPAvailabilityRangeServiceUtil
 * @see com.liferay.commerce.product.service.base.CPAvailabilityRangeServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPAvailabilityRangeServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPAvailabilityRange"}, service = CPAvailabilityRangeService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPAvailabilityRangeService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPAvailabilityRangeServiceUtil} to access the cp availability range remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPAvailabilityRangeServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPAvailabilityRange addCPAvailabilityRange(
		Map<Locale, java.lang.String> titleMap, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCPAvailabilityRange(long cpAvailabilityRangeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPAvailabilityRange getCPAvailabilityRange(
		long cpAvailabilityRangeId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPAvailabilityRange> getCPAvailabilityRanges(long groupId,
		int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPAvailabilityRangesCount(long groupId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public CPAvailabilityRange updateCPAvailabilityRange(
		long cpAvailabilityRangeId, Map<Locale, java.lang.String> titleMap,
		ServiceContext serviceContext) throws PortalException;
}
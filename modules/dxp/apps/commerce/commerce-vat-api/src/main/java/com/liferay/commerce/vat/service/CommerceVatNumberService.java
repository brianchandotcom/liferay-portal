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

package com.liferay.commerce.vat.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.vat.model.CommerceVatNumber;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
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
 * Provides the remote service interface for CommerceVatNumber. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumberServiceUtil
 * @see com.liferay.commerce.vat.service.base.CommerceVatNumberServiceBaseImpl
 * @see com.liferay.commerce.vat.service.impl.CommerceVatNumberServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceVatNumber"}, service = CommerceVatNumberService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceVatNumberService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceVatNumberServiceUtil} to access the commerce vat number remote service. Add custom service methods to {@link com.liferay.commerce.vat.service.impl.CommerceVatNumberServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceVatNumber addCommerceVatNumber(java.lang.String className,
		long classPK, java.lang.String vatNumber, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommerceVatNumber(long commerceVatNumberId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceVatNumber fetchCommerceVatNumber(long commerceVatNumberId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceVatNumber fetchCommerceVatNumber(long groupId,
		java.lang.String className, long classPK) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceVatNumber> getCommerceVatNumbers(long groupId,
		int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceVatNumbersCount(long groupId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommerceVatNumber> searchCommerceVatNumbers(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, Sort sort) throws PortalException;

	public CommerceVatNumber updateCommerceVatNumber(long commerceVatNumberId,
		java.lang.String vatNumber) throws PortalException;
}
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

package com.liferay.commerce.tax.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;

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
 * Provides the remote service interface for CommerceTaxFixedRateAddressRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelServiceUtil
 * @see com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateAddressRelServiceBaseImpl
 * @see com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceTaxFixedRateAddressRel"}, service = CommerceTaxFixedRateAddressRelService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceTaxFixedRateAddressRelService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTaxFixedRateAddressRelServiceUtil} to access the commerce tax fixed rate address rel remote service. Add custom service methods to {@link com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long cpTaxCategoryId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double rate,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTaxFixedRateAddressRel> getCommerceTaxMethodFixedRateAddressRels(
		long groupId, long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceTaxMethodFixedRateAddressRelsCount(long groupId,
		long commerceTaxMethodId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double rate)
		throws PortalException;
}
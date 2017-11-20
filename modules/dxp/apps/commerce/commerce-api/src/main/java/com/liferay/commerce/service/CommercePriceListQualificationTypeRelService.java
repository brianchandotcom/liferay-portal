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

import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;

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
 * Provides the remote service interface for CommercePriceListQualificationTypeRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListQualificationTypeRelServiceUtil
 * @see com.liferay.commerce.service.base.CommercePriceListQualificationTypeRelServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePriceListQualificationTypeRelServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommercePriceListQualificationTypeRel"}, service = CommercePriceListQualificationTypeRelService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommercePriceListQualificationTypeRelService
	extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListQualificationTypeRelServiceUtil} to access the commerce price list qualification type rel remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePriceListQualificationTypeRelServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommercePriceListQualificationTypeRel addCommercePriceListQualificationTypeRel(
		long commercePriceListId,
		java.lang.String commercePriceListQualificationType, int order,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceListQualificationTypeRel fetchCommercePriceListQualificationTypeRel(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		long commercePriceListId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceListQualificationTypeRelsCount(
		long commercePriceListId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public CommercePriceListQualificationTypeRel updateCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId, int order,
		ServiceContext serviceContext) throws PortalException;
}
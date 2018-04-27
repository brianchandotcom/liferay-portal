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

import com.liferay.commerce.product.model.CPDefinitionLink;

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
 * Provides the remote service interface for CPDefinitionLink. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionLinkServiceUtil
 * @see com.liferay.commerce.product.service.base.CPDefinitionLinkServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionLinkServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPDefinitionLink"}, service = CPDefinitionLinkService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionLinkService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionLinkServiceUtil} to access the cp definition link remote service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionLinkServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CPDefinitionLink deleteCPDefinitionLink(long cpDefinitionLinkId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionLink fetchCPDefinitionLink(long cpDefinitionLinkId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionLink getCPDefinitionLink(long cpDefinitionLinkId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionLink> getCPDefinitionLinks(long cpDefinitionId1,
		String type) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionLink> getCPDefinitionLinks(long cpDefinitionId1,
		String type, int start, int end,
		OrderByComparator<CPDefinitionLink> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionLinksCount(long cpDefinitionId1, String type)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CPDefinitionLink updateCPDefinitionLink(long cpDefinitionLinkId,
		double priority, ServiceContext serviceContext)
		throws PortalException;

	public void updateCPDefinitionLinks(long cpDefinitionId1,
		long[] cpDefinitionIds2, String type, ServiceContext serviceContext)
		throws PortalException;
}
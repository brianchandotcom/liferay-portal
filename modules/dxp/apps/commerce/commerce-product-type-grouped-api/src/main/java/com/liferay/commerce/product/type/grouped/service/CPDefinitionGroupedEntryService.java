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

package com.liferay.commerce.product.type.grouped.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;

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
 * Provides the remote service interface for CPDefinitionGroupedEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryServiceUtil
 * @see com.liferay.commerce.product.type.grouped.service.base.CPDefinitionGroupedEntryServiceBaseImpl
 * @see com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CPDefinitionGroupedEntry"}, service = CPDefinitionGroupedEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionGroupedEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionGroupedEntryServiceUtil} to access the cp definition grouped entry remote service. Add custom service methods to {@link com.liferay.commerce.product.type.grouped.service.impl.CPDefinitionGroupedEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addCPDefinitionGroupedEntries(long cpDefinitionId,
		long[] entryCPDefinitionIds, ServiceContext serviceContext)
		throws PortalException;

	public CPDefinitionGroupedEntry deleteCPDefinitionGroupedEntry(
		long cpDefinitionGroupedEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		long cpDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionGroupedEntriesCount(long cpDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionGroupedEntry getCPDefinitionGroupedEntry(
		long cpDefinitionGroupedEntryId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
		long cpDefinitionGroupedEntryId, double priority, int quantity)
		throws PortalException;
}
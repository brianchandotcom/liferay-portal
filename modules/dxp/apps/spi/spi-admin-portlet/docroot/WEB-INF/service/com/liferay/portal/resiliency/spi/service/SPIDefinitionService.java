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

package com.liferay.portal.resiliency.spi.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;

import java.util.List;

/**
 * Provides the remote service interface for SPIDefinition. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Michael C. Han
 * @see SPIDefinitionServiceUtil
 * @see com.liferay.portal.resiliency.spi.service.base.SPIDefinitionServiceBaseImpl
 * @see com.liferay.portal.resiliency.spi.service.impl.SPIDefinitionServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SPIDefinitionService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SPIDefinitionServiceUtil} to access the spi definition remote service. Add custom service methods to {@link com.liferay.portal.resiliency.spi.service.impl.SPIDefinitionServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public SPIDefinition addSPIDefinition(java.lang.String name,
		java.lang.String connectorAddress, int connectorPort,
		java.lang.String description, java.lang.String jvmArguments,
		java.lang.String portletIds, java.lang.String servletContextNames,
		java.lang.String typeSettings, ServiceContext serviceContext)
		throws PortalException;

	public SPIDefinition deleteSPIDefinition(long spiDefinitionId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Tuple getPortletIdsAndServletContextNames()
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SPIDefinition getSPIDefinition(long spiDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SPIDefinition getSPIDefinition(long companyId, java.lang.String name)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SPIDefinition> getSPIDefinitions() throws PortalException;

	public void startSPI(long spiDefinitionId) throws PortalException;

	public long startSPIinBackground(long spiDefinitionId)
		throws PortalException;

	public void stopSPI(long spiDefinitionId) throws PortalException;

	public long stopSPIinBackground(long spiDefinitionId)
		throws PortalException;

	public SPIDefinition updateSPIDefinition(long spiDefinitionId,
		java.lang.String connectorAddress, int connectorPort,
		java.lang.String description, java.lang.String jvmArguments,
		java.lang.String portletIds, java.lang.String servletContextNames,
		java.lang.String typeSettings, ServiceContext serviceContext)
		throws PortalException;

	public SPIDefinition updateTypeSettings(long userId, long spiDefinitionId,
		java.lang.String recoveryOptions, ServiceContext serviceContext)
		throws PortalException;
}
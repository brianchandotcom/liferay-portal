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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SPIDefinitionService}.
 *
 * @author Michael C. Han
 * @see SPIDefinitionService
 * @generated
 */
@ProviderType
public class SPIDefinitionServiceWrapper implements SPIDefinitionService,
	ServiceWrapper<SPIDefinitionService> {
	public SPIDefinitionServiceWrapper(
		SPIDefinitionService spiDefinitionService) {
		_spiDefinitionService = spiDefinitionService;
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition addSPIDefinition(
		java.lang.String name, java.lang.String connectorAddress,
		int connectorPort, java.lang.String description,
		java.lang.String jvmArguments, java.lang.String portletIds,
		java.lang.String servletContextNames, java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.addSPIDefinition(name, connectorAddress,
			connectorPort, description, jvmArguments, portletIds,
			servletContextNames, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition deleteSPIDefinition(
		long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.deleteSPIDefinition(spiDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _spiDefinitionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.util.Tuple getPortletIdsAndServletContextNames()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.getPortletIdsAndServletContextNames();
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition getSPIDefinition(
		long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.getSPIDefinition(spiDefinitionId);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition getSPIDefinition(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.getSPIDefinition(companyId, name);
	}

	@Override
	public java.util.List<com.liferay.portal.resiliency.spi.model.SPIDefinition> getSPIDefinitions()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.getSPIDefinitions();
	}

	@Override
	public void startSPI(long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_spiDefinitionService.startSPI(spiDefinitionId);
	}

	@Override
	public long startSPIinBackground(long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.startSPIinBackground(spiDefinitionId);
	}

	@Override
	public void stopSPI(long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_spiDefinitionService.stopSPI(spiDefinitionId);
	}

	@Override
	public long stopSPIinBackground(long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.stopSPIinBackground(spiDefinitionId);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition updateSPIDefinition(
		long spiDefinitionId, java.lang.String connectorAddress,
		int connectorPort, java.lang.String description,
		java.lang.String jvmArguments, java.lang.String portletIds,
		java.lang.String servletContextNames, java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.updateSPIDefinition(spiDefinitionId,
			connectorAddress, connectorPort, description, jvmArguments,
			portletIds, servletContextNames, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition updateTypeSettings(
		long userId, long spiDefinitionId, java.lang.String recoveryOptions,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.updateTypeSettings(userId,
			spiDefinitionId, recoveryOptions, serviceContext);
	}

	@Override
	public SPIDefinitionService getWrappedService() {
		return _spiDefinitionService;
	}

	@Override
	public void setWrappedService(SPIDefinitionService spiDefinitionService) {
		_spiDefinitionService = spiDefinitionService;
	}

	private SPIDefinitionService _spiDefinitionService;
}
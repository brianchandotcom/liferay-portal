/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		String name, String connectorAddress, int connectorPort,
		String description, String jvmArguments, String portletIds,
		String servletContextNames, String typeSettings,
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
	public String getOSGiServiceIdentifier() {
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
		long companyId, String name)
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
		long spiDefinitionId, String connectorAddress, int connectorPort,
		String description, String jvmArguments, String portletIds,
		String servletContextNames, String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _spiDefinitionService.updateSPIDefinition(spiDefinitionId,
			connectorAddress, connectorPort, description, jvmArguments,
			portletIds, servletContextNames, typeSettings, serviceContext);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition updateTypeSettings(
		long userId, long spiDefinitionId, String recoveryOptions,
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
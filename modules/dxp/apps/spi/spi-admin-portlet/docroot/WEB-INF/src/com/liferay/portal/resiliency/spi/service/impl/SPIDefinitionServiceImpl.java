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

package com.liferay.portal.resiliency.spi.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.service.base.SPIDefinitionServiceBaseImpl;
import com.liferay.portal.resiliency.spi.service.permission.SPIDefinitionPermissionUtil;
import com.liferay.portal.resiliency.spi.util.ActionKeys;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class SPIDefinitionServiceImpl extends SPIDefinitionServiceBaseImpl {

	@Override
	public SPIDefinition addSPIDefinition(
			String name, String connectorAddress, int connectorPort,
			String description, String jvmArguments, String portletIds,
			String servletContextNames, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), ActionKeys.ADD_SPI_DEFINITION);

		return spiDefinitionLocalService.addSPIDefinition(
			getUserId(), name, connectorAddress, connectorPort, description,
			jvmArguments, portletIds, servletContextNames, typeSettings,
			serviceContext);
	}

	@Override
	public SPIDefinition deleteSPIDefinition(long spiDefinitionId)
		throws PortalException {

		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), spiDefinitionId, ActionKeys.DELETE);

		return spiDefinitionLocalService.deleteSPIDefinition(spiDefinitionId);
	}

	@Override
	public Tuple getPortletIdsAndServletContextNames() throws PortalException {
		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), ActionKeys.VIEW_SPI_DEFINITIONS);

		return spiDefinitionLocalService.getPortletIdsAndServletContextNames();
	}

	@Override
	public SPIDefinition getSPIDefinition(long spiDefinitionId)
		throws PortalException {

		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), spiDefinitionId, ActionKeys.VIEW);

		return spiDefinitionLocalService.getSPIDefinition(spiDefinitionId);
	}

	@Override
	public SPIDefinition getSPIDefinition(long companyId, String name)
		throws PortalException {

		SPIDefinition spiDefinition =
			spiDefinitionLocalService.getSPIDefinition(companyId, name);

		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), spiDefinition, ActionKeys.VIEW);

		return spiDefinition;
	}

	@Override
	public List<SPIDefinition> getSPIDefinitions() throws PortalException {
		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), ActionKeys.VIEW_SPI_DEFINITIONS);

		return spiDefinitionLocalService.getSPIDefinitions();
	}

	@Override
	public void startSPI(long spiDefinitionId) throws PortalException {
		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), spiDefinitionId, ActionKeys.MANAGE);

		spiDefinitionLocalService.startSPI(spiDefinitionId);
	}

	@Override
	public long startSPIinBackground(long spiDefinitionId)
		throws PortalException {

		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), spiDefinitionId, ActionKeys.MANAGE);

		return spiDefinitionLocalService.startSPIinBackground(
			getUser().getUserId(), spiDefinitionId);
	}

	@Override
	public void stopSPI(long spiDefinitionId) throws PortalException {
		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), spiDefinitionId, ActionKeys.MANAGE);

		spiDefinitionLocalService.stopSPI(spiDefinitionId);
	}

	@Override
	public long stopSPIinBackground(long spiDefinitionId)
		throws PortalException {

		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), spiDefinitionId, ActionKeys.MANAGE);

		return spiDefinitionLocalService.stopSPIinBackground(
			getUser().getUserId(), spiDefinitionId);
	}

	@Override
	public SPIDefinition updateSPIDefinition(
			long spiDefinitionId, String connectorAddress, int connectorPort,
			String description, String jvmArguments, String portletIds,
			String servletContextNames, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), spiDefinitionId, ActionKeys.UPDATE);

		return spiDefinitionLocalService.updateSPIDefinition(
			getUserId(), spiDefinitionId, connectorAddress, connectorPort,
			description, jvmArguments, portletIds, servletContextNames,
			typeSettings, serviceContext);
	}

	@Override
	public SPIDefinition updateTypeSettings(
			long userId, long spiDefinitionId, String recoveryOptions,
			ServiceContext serviceContext)
		throws PortalException {

		SPIDefinitionPermissionUtil.check(
			getPermissionChecker(), spiDefinitionId, ActionKeys.UPDATE);

		return spiDefinitionLocalService.updateTypeSettings(
			getUserId(), spiDefinitionId, recoveryOptions, serviceContext);
	}

}
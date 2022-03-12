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

package com.liferay.commerce.shop.by.diagram.service.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.base.CSDiagramPinServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CSDiagramPin"
	},
	service = AopService.class
)
public class CSDiagramPinServiceImpl extends CSDiagramPinServiceBaseImpl {

	@Override
	public CSDiagramPin addCSDiagramPin(
			long cpDefinitionId, double positionX, double positionY,
			String sequence)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramPinLocalService.addCSDiagramPin(
			getUserId(), cpDefinitionId, positionX, positionY, sequence);
	}

	@Override
	public void deleteCSDiagramPin(CSDiagramPin csDiagramPin)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), csDiagramPin.getCPDefinitionId(),
			ActionKeys.UPDATE);

		csDiagramPinLocalService.deleteCSDiagramPin(csDiagramPin);
	}

	@Override
	public void deleteCSDiagramPins(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		csDiagramPinLocalService.deleteCSDiagramPins(cpDefinitionId);
	}

	@Override
	public CSDiagramPin fetchCSDiagramPin(long csDiagramPinId) {
		return csDiagramPinLocalService.fetchCSDiagramPin(csDiagramPinId);
	}

	@Override
	public CSDiagramPin getCSDiagramPin(long csDiagramPinId)
		throws PortalException {

		CSDiagramPin csDiagramPin = csDiagramPinLocalService.getCSDiagramPin(
			csDiagramPinId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), csDiagramPin.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return csDiagramPin;
	}

	@Override
	public List<CSDiagramPin> getCSDiagramPins(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramPinLocalService.getCSDiagramPins(
			cpDefinitionId, start, end);
	}

	@Override
	public int getCSDiagramPinsCount(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramPinLocalService.getCSDiagramPinsCount(cpDefinitionId);
	}

	@Override
	public CSDiagramPin updateCSDiagramPin(
			long csDiagramPinId, double positionX, double positionY,
			String sequence)
		throws PortalException {

		CSDiagramPin csDiagramPin = csDiagramPinLocalService.getCSDiagramPin(
			csDiagramPinId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), csDiagramPin.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return csDiagramPinLocalService.updateCSDiagramPin(
			csDiagramPinId, positionX, positionY, sequence);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CSDiagramPinServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}
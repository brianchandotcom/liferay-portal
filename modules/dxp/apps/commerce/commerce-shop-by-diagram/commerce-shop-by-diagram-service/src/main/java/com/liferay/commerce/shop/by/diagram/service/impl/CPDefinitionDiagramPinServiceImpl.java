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

package com.liferay.commerce.shop.by.diagram.service.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.base.CPDefinitionDiagramPinServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CPDefinitionDiagramPin"
	},
	service = AopService.class
)
public class CPDefinitionDiagramPinServiceImpl
	extends CPDefinitionDiagramPinServiceBaseImpl {

	@Override
	public CPDefinitionDiagramPin addCPDefinitionDiagramPin(
			long cpDefinitionId, double positionX, double positionY,
			String sequence)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramPinLocalService.addCPDefinitionDiagramPin(
			getUserId(), cpDefinitionId, positionX, positionY, sequence);
	}

	@Override
	public void deleteCPDefinitionDiagramPin(long cpDefinitionDiagramPinId)
		throws PortalException {

		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			cpDefinitionDiagramPinLocalService.getCPDefinitionDiagramPin(
				cpDefinitionDiagramPinId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionDiagramPin.getCPDefinitionId(),
			ActionKeys.UPDATE);

		cpDefinitionDiagramPinLocalService.deleteCPDefinitionDiagramPin(
			cpDefinitionDiagramPin);
	}

	@Override
	public CPDefinitionDiagramPin getCPDefinitionDiagramPin(
			long cpDefinitionDiagramPinId)
		throws PortalException {

		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			cpDefinitionDiagramPinLocalService.getCPDefinitionDiagramPin(
				cpDefinitionDiagramPinId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionDiagramPin.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return cpDefinitionDiagramPin;
	}

	@Override
	public List<CPDefinitionDiagramPin> getCPDefinitionDiagramPins(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramPinLocalService.getCPDefinitionDiagramPins(
			cpDefinitionId, start, end);
	}

	@Override
	public int getCPDefinitionDiagramPinsCount(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramPinLocalService.
			getCPDefinitionDiagramPinsCount(cpDefinitionId);
	}

	@Override
	public CPDefinitionDiagramPin updateCPDefinitionDiagramPin(
			long cpDefinitionDiagramPinId, double positionX, double positionY,
			String sequence)
		throws PortalException {

		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			cpDefinitionDiagramPinLocalService.getCPDefinitionDiagramPin(
				cpDefinitionDiagramPinId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionDiagramPin.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return cpDefinitionDiagramPinLocalService.updateCPDefinitionDiagramPin(
			cpDefinitionDiagramPinId, positionX, positionY, sequence);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPDefinitionDiagramPinServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}
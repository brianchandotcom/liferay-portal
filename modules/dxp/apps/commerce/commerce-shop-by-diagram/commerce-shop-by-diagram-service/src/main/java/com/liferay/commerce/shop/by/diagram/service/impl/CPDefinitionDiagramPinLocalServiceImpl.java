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

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.base.CPDefinitionDiagramPinLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin",
	service = AopService.class
)
public class CPDefinitionDiagramPinLocalServiceImpl
	extends CPDefinitionDiagramPinLocalServiceBaseImpl {

	@Override
	public CPDefinitionDiagramPin addCPDefinitionDiagramPin(
			long userId, long cpDefinitionId, double positionX,
			double positionY, String sequence)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long cpDefinitionDiagramPinId = counterLocalService.increment();

		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			cpDefinitionDiagramPinPersistence.create(cpDefinitionDiagramPinId);

		cpDefinitionDiagramPin.setCompanyId(user.getCompanyId());
		cpDefinitionDiagramPin.setUserId(user.getUserId());
		cpDefinitionDiagramPin.setUserName(user.getFullName());
		cpDefinitionDiagramPin.setCPDefinitionId(cpDefinitionId);
		cpDefinitionDiagramPin.setSequence(sequence);
		cpDefinitionDiagramPin.setPositionX(positionX);
		cpDefinitionDiagramPin.setPositionY(positionY);

		return cpDefinitionDiagramPinPersistence.update(cpDefinitionDiagramPin);
	}

	@Override
	public void deleteCPDefinitionDiagramPins(long cpDefinitionId) {
		cpDefinitionDiagramPinPersistence.removeByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public List<CPDefinitionDiagramPin> getCPDefinitionDiagramPins(
		long cpDefinitionId, int start, int end) {

		return cpDefinitionDiagramPinPersistence.findByCPDefinitionId(
			cpDefinitionId, start, end);
	}

	@Override
	public int getCPDefinitionDiagramPinsCount(long cpDefinitionId) {
		return cpDefinitionDiagramPinPersistence.countByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CPDefinitionDiagramPin updateCPDefinitionDiagramPin(
			long cpDefinitionDiagramPinId, double positionX,
			double positionY, String sequence)
		throws PortalException {

		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			cpDefinitionDiagramPinLocalService.getCPDefinitionDiagramPin(
				cpDefinitionDiagramPinId);

		cpDefinitionDiagramPin.setPositionX(positionX);
		cpDefinitionDiagramPin.setPositionY(positionY);
		cpDefinitionDiagramPin.setSequence(sequence);

		return cpDefinitionDiagramPinPersistence.update(cpDefinitionDiagramPin);
	}

}
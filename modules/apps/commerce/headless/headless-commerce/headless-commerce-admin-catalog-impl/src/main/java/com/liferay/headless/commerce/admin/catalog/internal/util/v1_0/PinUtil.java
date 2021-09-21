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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramPinService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Pin;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class PinUtil {

	public static CSDiagramPin addCSDiagramPin(
			long cpDefinitionId, CSDiagramPinService csDiagramPinService,
			Pin pin)
		throws PortalException {

		return csDiagramPinService.addCSDiagramPin(
			cpDefinitionId, GetterUtil.getDouble(pin.getPositionX()),
			GetterUtil.getDouble(pin.getPositionY()),
			GetterUtil.getString(pin.getSequence()));
	}

	public static CSDiagramPin addOrUpdateCSDiagramPin(
			long cpDefinitionId, CSDiagramPinService csDiagramPinService,
			Pin pin)
		throws PortalException {

		CSDiagramPin csDiagramPin = csDiagramPinService.fetchCSDiagramPin(
			cpDefinitionId);

		if (csDiagramPin == null) {
			return addCSDiagramPin(cpDefinitionId, csDiagramPinService, pin);
		}

		return updateCSDiagramPin(csDiagramPin, csDiagramPinService, pin);
	}

	public static CSDiagramPin updateCSDiagramPin(
			CSDiagramPin csDiagramPin, CSDiagramPinService csDiagramPinService,
			Pin pin)
		throws PortalException {

		return csDiagramPinService.updateCSDiagramPin(
			csDiagramPin.getCSDiagramPinId(),
			GetterUtil.get(pin.getPositionX(), csDiagramPin.getPositionX()),
			GetterUtil.get(pin.getPositionY(), csDiagramPin.getPositionY()),
			GetterUtil.get(pin.getSequence(), csDiagramPin.getSequence()));
	}

}
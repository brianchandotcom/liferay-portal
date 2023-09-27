/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
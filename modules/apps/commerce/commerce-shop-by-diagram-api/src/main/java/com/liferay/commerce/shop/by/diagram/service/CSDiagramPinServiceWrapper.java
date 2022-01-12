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

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CSDiagramPinService}.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramPinService
 * @generated
 */
public class CSDiagramPinServiceWrapper
	implements CSDiagramPinService, ServiceWrapper<CSDiagramPinService> {

	public CSDiagramPinServiceWrapper() {
		this(null);
	}

	public CSDiagramPinServiceWrapper(CSDiagramPinService csDiagramPinService) {
		_csDiagramPinService = csDiagramPinService;
	}

	@Override
	public CSDiagramPin addCSDiagramPin(
			long cpDefinitionId, double positionX, double positionY,
			String sequence)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinService.addCSDiagramPin(
			cpDefinitionId, positionX, positionY, sequence);
	}

	@Override
	public void deleteCSDiagramPin(CSDiagramPin csDiagramPin)
		throws com.liferay.portal.kernel.exception.PortalException {

		_csDiagramPinService.deleteCSDiagramPin(csDiagramPin);
	}

	@Override
	public void deleteCSDiagramPins(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_csDiagramPinService.deleteCSDiagramPins(cpDefinitionId);
	}

	@Override
	public CSDiagramPin fetchCSDiagramPin(long csDiagramPinId) {
		return _csDiagramPinService.fetchCSDiagramPin(csDiagramPinId);
	}

	@Override
	public CSDiagramPin getCSDiagramPin(long csDiagramPinId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinService.getCSDiagramPin(csDiagramPinId);
	}

	@Override
	public java.util.List<CSDiagramPin> getCSDiagramPins(
			long cpDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinService.getCSDiagramPins(
			cpDefinitionId, start, end);
	}

	@Override
	public int getCSDiagramPinsCount(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinService.getCSDiagramPinsCount(cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _csDiagramPinService.getOSGiServiceIdentifier();
	}

	@Override
	public CSDiagramPin updateCSDiagramPin(
			long csDiagramPinId, double positionX, double positionY,
			String sequence)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinService.updateCSDiagramPin(
			csDiagramPinId, positionX, positionY, sequence);
	}

	@Override
	public CSDiagramPinService getWrappedService() {
		return _csDiagramPinService;
	}

	@Override
	public void setWrappedService(CSDiagramPinService csDiagramPinService) {
		_csDiagramPinService = csDiagramPinService;
	}

	private CSDiagramPinService _csDiagramPinService;

}
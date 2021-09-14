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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CSDiagramSettingService}.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramSettingService
 * @generated
 */
public class CSDiagramSettingServiceWrapper
	implements CSDiagramSettingService,
			   ServiceWrapper<CSDiagramSettingService> {

	public CSDiagramSettingServiceWrapper(
		CSDiagramSettingService csDiagramSettingService) {

		_csDiagramSettingService = csDiagramSettingService;
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting
			addCSDiagramSetting(
				long cpDefinitionId, long cpAttachmentFileEntryId, String color,
				double radius, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramSettingService.addCSDiagramSetting(
			cpDefinitionId, cpAttachmentFileEntryId, color, radius, type);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting
			fetchCSDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramSettingService.fetchCSDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting
			getCSDiagramSetting(long csDiagramSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramSettingService.getCSDiagramSetting(csDiagramSettingId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting
			getCSDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramSettingService.getCSDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _csDiagramSettingService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting
			updateCSDiagramSetting(
				long csDiagramSettingId, long cpAttachmentFileEntryId,
				String color, double radius, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramSettingService.updateCSDiagramSetting(
			csDiagramSettingId, cpAttachmentFileEntryId, color, radius, type);
	}

	@Override
	public CSDiagramSettingService getWrappedService() {
		return _csDiagramSettingService;
	}

	@Override
	public void setWrappedService(
		CSDiagramSettingService csDiagramSettingService) {

		_csDiagramSettingService = csDiagramSettingService;
	}

	private CSDiagramSettingService _csDiagramSettingService;

}
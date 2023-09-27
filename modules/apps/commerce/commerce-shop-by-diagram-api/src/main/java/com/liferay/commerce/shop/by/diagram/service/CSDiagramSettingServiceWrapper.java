/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
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

	public CSDiagramSettingServiceWrapper() {
		this(null);
	}

	public CSDiagramSettingServiceWrapper(
		CSDiagramSettingService csDiagramSettingService) {

		_csDiagramSettingService = csDiagramSettingService;
	}

	@Override
	public CSDiagramSetting addCSDiagramSetting(
			long cpDefinitionId, long cpAttachmentFileEntryId, String color,
			double radius, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramSettingService.addCSDiagramSetting(
			cpDefinitionId, cpAttachmentFileEntryId, color, radius, type);
	}

	@Override
	public CSDiagramSetting fetchCSDiagramSettingByCPDefinitionId(
			long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramSettingService.fetchCSDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CSDiagramSetting getCSDiagramSetting(long csDiagramSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramSettingService.getCSDiagramSetting(csDiagramSettingId);
	}

	@Override
	public CSDiagramSetting getCSDiagramSettingByCPDefinitionId(
			long cpDefinitionId)
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
	public CSDiagramSetting updateCSDiagramSetting(
			long csDiagramSettingId, long cpAttachmentFileEntryId, String color,
			double radius, String type)
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
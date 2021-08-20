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

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionDiagramSettingService}.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSettingService
 * @generated
 */
public class CPDefinitionDiagramSettingServiceWrapper
	implements CPDefinitionDiagramSettingService,
			   ServiceWrapper<CPDefinitionDiagramSettingService> {

	public CPDefinitionDiagramSettingServiceWrapper(
		CPDefinitionDiagramSettingService cpDefinitionDiagramSettingService) {

		_cpDefinitionDiagramSettingService = cpDefinitionDiagramSettingService;
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			addCPDefinitionDiagramSetting(
				long cpDefinitionId, long cpAttachmentFileEntryId, String color,
				double radius, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingService.addCPDefinitionDiagramSetting(
			cpDefinitionId, cpAttachmentFileEntryId, color, radius, type);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			fetchCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingService.
			fetchCPDefinitionDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			getCPDefinitionDiagramSetting(long cpDefinitionDiagramSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingService.getCPDefinitionDiagramSetting(
			cpDefinitionDiagramSettingId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			getCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingService.
			getCPDefinitionDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionDiagramSettingService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting
			updateCPDefinitionDiagramSetting(
				long cpDefinitionDiagramSettingId, long cpAttachmentFileEntryId,
				String color, double radius, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingService.
			updateCPDefinitionDiagramSetting(
				cpDefinitionDiagramSettingId, cpAttachmentFileEntryId, color,
				radius, type);
	}

	@Override
	public CPDefinitionDiagramSettingService getWrappedService() {
		return _cpDefinitionDiagramSettingService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionDiagramSettingService cpDefinitionDiagramSettingService) {

		_cpDefinitionDiagramSettingService = cpDefinitionDiagramSettingService;
	}

	private CPDefinitionDiagramSettingService
		_cpDefinitionDiagramSettingService;

}
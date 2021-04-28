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
 * Provides a wrapper for {@link CPDefinitionDiagramSettingsService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramSettingsService
 * @generated
 */
public class CPDefinitionDiagramSettingsServiceWrapper
	implements CPDefinitionDiagramSettingsService,
			   ServiceWrapper<CPDefinitionDiagramSettingsService> {

	public CPDefinitionDiagramSettingsServiceWrapper(
		CPDefinitionDiagramSettingsService cpDefinitionDiagramSettingsService) {

		_cpDefinitionDiagramSettingsService =
			cpDefinitionDiagramSettingsService;
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				addCPDefinitionDiagramSettings(
					long userId, long cpDefinitionId, long assetCategoryId,
					String name)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsService.
			addCPDefinitionDiagramSettings(
				userId, cpDefinitionId, assetCategoryId, name);
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				fetchCPDefinitionDiagramSettingsByCPDefinitionId(
					long cpDefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsService.
			fetchCPDefinitionDiagramSettingsByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				getCPDefinitionDiagramSettings(
					long cpDefinitionDiagramSettingsId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsService.
			getCPDefinitionDiagramSettings(cpDefinitionDiagramSettingsId);
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				getCPDefinitionDiagramSettingsByCPDefinitionId(
					long cpDefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsService.
			getCPDefinitionDiagramSettingsByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings>
			getCPDefinitionDiagramSettingsList(
				long assetCategoryId, int start, int end) {

		return _cpDefinitionDiagramSettingsService.
			getCPDefinitionDiagramSettingsList(assetCategoryId, start, end);
	}

	@Override
	public int getCPDefinitionDiagramSettingsListCount(long assetCategoryId) {
		return _cpDefinitionDiagramSettingsService.
			getCPDefinitionDiagramSettingsListCount(assetCategoryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionDiagramSettingsService.getOSGiServiceIdentifier();
	}

	@Override
	public
		com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings
				updateCPDefinitionDiagramSettings(
					long cpDefinitionDiagramSettingsId, long assetCategoryId,
					String name)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramSettingsService.
			updateCPDefinitionDiagramSettings(
				cpDefinitionDiagramSettingsId, assetCategoryId, name);
	}

	@Override
	public CPDefinitionDiagramSettingsService getWrappedService() {
		return _cpDefinitionDiagramSettingsService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionDiagramSettingsService cpDefinitionDiagramSettingsService) {

		_cpDefinitionDiagramSettingsService =
			cpDefinitionDiagramSettingsService;
	}

	private CPDefinitionDiagramSettingsService
		_cpDefinitionDiagramSettingsService;

}
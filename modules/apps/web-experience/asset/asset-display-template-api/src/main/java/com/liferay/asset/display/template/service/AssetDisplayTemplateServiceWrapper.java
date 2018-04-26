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

package com.liferay.asset.display.template.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetDisplayTemplateService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayTemplateService
 * @generated
 */
@ProviderType
public class AssetDisplayTemplateServiceWrapper
	implements AssetDisplayTemplateService,
		ServiceWrapper<AssetDisplayTemplateService> {
	public AssetDisplayTemplateServiceWrapper(
		AssetDisplayTemplateService assetDisplayTemplateService) {
		_assetDisplayTemplateService = assetDisplayTemplateService;
	}

	@Override
	public com.liferay.asset.display.template.model.AssetDisplayTemplate addAssetDisplayTemplate(
		long groupId, long userId, String name, long classNameId,
		String language, String scriptContent, boolean main,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayTemplateService.addAssetDisplayTemplate(groupId,
			userId, name, classNameId, language, scriptContent, main,
			serviceContext);
	}

	@Override
	public com.liferay.asset.display.template.model.AssetDisplayTemplate deleteAssetDisplayTemplate(
		long assetDisplayTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayTemplateService.deleteAssetDisplayTemplate(assetDisplayTemplateId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetDisplayTemplateService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.asset.display.template.model.AssetDisplayTemplate updateAssetDisplayTemplate(
		long assetDisplayTemplateId, String name, long classNameId,
		String language, String scriptContent, boolean main,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetDisplayTemplateService.updateAssetDisplayTemplate(assetDisplayTemplateId,
			name, classNameId, language, scriptContent, main, serviceContext);
	}

	@Override
	public AssetDisplayTemplateService getWrappedService() {
		return _assetDisplayTemplateService;
	}

	@Override
	public void setWrappedService(
		AssetDisplayTemplateService assetDisplayTemplateService) {
		_assetDisplayTemplateService = assetDisplayTemplateService;
	}

	private AssetDisplayTemplateService _assetDisplayTemplateService;
}
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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link WebsiteService}.
 *
 * @author Brian Wing Shun Chan
 * @see WebsiteService
 * @generated
 */
@ProviderType
public class WebsiteServiceWrapper implements WebsiteService,
	ServiceWrapper<WebsiteService> {
	public WebsiteServiceWrapper(WebsiteService websiteService) {
		_websiteService = websiteService;
	}

	@Override
	public com.liferay.portal.kernel.model.Website addWebsite(
		String className, long classPK, String url, long typeId,
		boolean primary, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteService.addWebsite(className, classPK, url, typeId,
			primary, serviceContext);
	}

	@Override
	public void deleteWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_websiteService.deleteWebsite(websiteId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _websiteService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.Website getWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteService.getWebsite(websiteId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Website> getWebsites(
		String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteService.getWebsites(className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.model.Website updateWebsite(
		long websiteId, String url, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteService.updateWebsite(websiteId, url, typeId, primary);
	}

	@Override
	public WebsiteService getWrappedService() {
		return _websiteService;
	}

	@Override
	public void setWrappedService(WebsiteService websiteService) {
		_websiteService = websiteService;
	}

	private WebsiteService _websiteService;
}
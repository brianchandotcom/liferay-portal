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

/**
 * Provides a wrapper for {@link WebsiteService}.
 *
 * @author Brian Wing Shun Chan
 * @see WebsiteService
 * @generated
 */
public class WebsiteServiceWrapper
	implements ServiceWrapper<WebsiteService>, WebsiteService {

	public WebsiteServiceWrapper(WebsiteService websiteService) {
		_websiteService = websiteService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WebsiteServiceUtil} to access the website remote service. Add custom service methods to <code>com.liferay.portal.service.impl.WebsiteServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.kernel.model.Website addWebsite(
			java.lang.String className, long classPK, java.lang.String url,
			long typeId, boolean primary, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _websiteService.addWebsite(
			className, classPK, url, typeId, primary, serviceContext);
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
	public java.lang.String getOSGiServiceIdentifier() {
		return _websiteService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.Website getWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _websiteService.getWebsite(websiteId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Website> getWebsites(
			java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _websiteService.getWebsites(className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.model.Website updateWebsite(
			long websiteId, java.lang.String url, long typeId, boolean primary)
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
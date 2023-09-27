/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public WebsiteServiceWrapper() {
		this(null);
	}

	public WebsiteServiceWrapper(WebsiteService websiteService) {
		_websiteService = websiteService;
	}

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
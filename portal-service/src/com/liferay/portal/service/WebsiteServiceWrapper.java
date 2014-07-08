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

package com.liferay.portal.service;

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

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _websiteService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_websiteService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addWebsite(String, long,
	String, int, boolean, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.Website addWebsite(
		java.lang.String className, long classPK, java.lang.String url,
		int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteService.addWebsite(className, classPK, url, typeId,
			primary);
	}

	@Override
	public com.liferay.portal.model.Website addWebsite(
		java.lang.String className, long classPK, java.lang.String url,
		int typeId, boolean primary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteService.addWebsite(className, classPK, url, typeId,
			primary, serviceContext);
	}

	@Override
	public void deleteWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_websiteService.deleteWebsite(websiteId);
	}

	@Override
	public com.liferay.portal.model.Website getWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteService.getWebsite(websiteId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Website> getWebsites(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteService.getWebsites(className, classPK);
	}

	@Override
	public com.liferay.portal.model.Website updateWebsite(long websiteId,
		java.lang.String url, int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _websiteService.updateWebsite(websiteId, url, typeId, primary);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public WebsiteService getWrappedWebsiteService() {
		return _websiteService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedWebsiteService(WebsiteService websiteService) {
		_websiteService = websiteService;
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
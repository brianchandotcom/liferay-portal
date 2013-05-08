/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

/**
 * Provides a wrapper for {@link WebsiteService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       WebsiteService
 * @generated
 */
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
	public java.lang.String getBeanIdentifier() {
		return _websiteService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_websiteService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Website addWebsite(
		java.lang.String className, long classPK, java.lang.String url,
		int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _websiteService.addWebsite(className, classPK, url, typeId,
			primary);
	}

	public void deleteWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_websiteService.deleteWebsite(websiteId);
	}

	public com.liferay.portal.model.Website getWebsite(long websiteId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _websiteService.getWebsite(websiteId);
	}

	public java.util.List<com.liferay.portal.model.Website> getWebsites(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _websiteService.getWebsites(className, classPK);
	}

	public com.liferay.portal.model.Website updateWebsite(long websiteId,
		java.lang.String url, int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _websiteService.updateWebsite(websiteId, url, typeId, primary);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public WebsiteService getWrappedWebsiteService() {
		return _websiteService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedWebsiteService(WebsiteService websiteService) {
		_websiteService = websiteService;
	}

	public WebsiteService getWrappedService() {
		return _websiteService;
	}

	public void setWrappedService(WebsiteService websiteService) {
		_websiteService = websiteService;
	}

	private WebsiteService _websiteService;
}
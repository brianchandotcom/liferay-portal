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

package com.liferay.marketplace.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import com.liferay.marketplace.service.AppService;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for App. This utility wraps
 * {@link com.liferay.marketplace.service.impl.AppServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Ryan Park
 * @see AppService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=marketplace", "json.web.service.context.path=App"}, service = AppJsonService.class)
@JSONWebService
@ProviderType
public class AppJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.marketplace.service.impl.AppServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.marketplace.model.App deleteApp(long appId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.deleteApp(appId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public void installApp(long remoteAppId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.installApp(remoteAppId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public void uninstallApp(long remoteAppId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.uninstallApp(remoteAppId);
	}

	public com.liferay.marketplace.model.App updateApp(long remoteAppId,
		java.lang.String version, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateApp(remoteAppId, version, file);
	}

	@Reference
	protected void setService(AppService service) {
		_service = service;
	}

	private AppService _service;
}
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

package com.liferay.portal.security.wedeploy.auth.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for WeDeployAuthApp. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthAppServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface WeDeployAuthAppService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthAppServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the we deploy auth app remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link WeDeployAuthAppServiceUtil} if injection and service tracking are not available.
	 */
	public WeDeployAuthApp addWeDeployAuthApp(
			String name, String redirectURI, ServiceContext serviceContext)
		throws PortalException;

	public WeDeployAuthApp deleteWeDeployAuthApp(long weDeployAuthAppId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

}
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

import com.liferay.asset.display.template.model.AssetDisplayTemplate;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the remote service interface for AssetDisplayTemplate. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayTemplateServiceUtil
 * @see com.liferay.asset.display.template.service.base.AssetDisplayTemplateServiceBaseImpl
 * @see com.liferay.asset.display.template.service.impl.AssetDisplayTemplateServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=asset", "json.web.service.context.path=AssetDisplayTemplate"}, service = AssetDisplayTemplateService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AssetDisplayTemplateService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetDisplayTemplateServiceUtil} to access the asset display template remote service. Add custom service methods to {@link com.liferay.asset.display.template.service.impl.AssetDisplayTemplateServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public AssetDisplayTemplate addAssetDisplayTemplate(long groupId,
		long userId, String name, long classNameId, String language,
		String scriptContent, boolean main, ServiceContext serviceContext)
		throws PortalException;

	public AssetDisplayTemplate deleteAssetDisplayTemplate(
		long assetDisplayTemplateId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public AssetDisplayTemplate updateAssetDisplayTemplate(
		long assetDisplayTemplateId, String name, long classNameId,
		String language, String scriptContent, boolean main,
		ServiceContext serviceContext) throws PortalException;
}
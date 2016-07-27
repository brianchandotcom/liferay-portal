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

package com.liferay.screens.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.Locale;

/**
 * Provides the remote service interface for ScreensAssetEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author José Manuel Navarro
 * @see ScreensAssetEntryServiceUtil
 * @see com.liferay.screens.service.base.ScreensAssetEntryServiceBaseImpl
 * @see com.liferay.screens.service.impl.ScreensAssetEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=screens", "json.web.service.context.path=ScreensAssetEntry"}, service = ScreensAssetEntryService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ScreensAssetEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ScreensAssetEntryServiceUtil} to access the screens asset entry remote service. Add custom service methods to {@link com.liferay.screens.service.impl.ScreensAssetEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray getAssetEntries(AssetEntryQuery assetEntryQuery,
		Locale locale) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray getAssetEntries(long companyId, long groupId,
		java.lang.String portletItemName, Locale locale, int max)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONObject getAssetEntry(long entryId, Locale locale)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();
}
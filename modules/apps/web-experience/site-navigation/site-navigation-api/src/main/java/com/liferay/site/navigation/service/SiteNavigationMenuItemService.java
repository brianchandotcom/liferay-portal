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

package com.liferay.site.navigation.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import java.util.List;

/**
 * Provides the remote service interface for SiteNavigationMenuItem. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItemServiceUtil
 * @see com.liferay.site.navigation.service.base.SiteNavigationMenuItemServiceBaseImpl
 * @see com.liferay.site.navigation.service.impl.SiteNavigationMenuItemServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=sitenavigation", "json.web.service.context.path=SiteNavigationMenuItem"}, service = SiteNavigationMenuItemService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SiteNavigationMenuItemService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SiteNavigationMenuItemServiceUtil} to access the site navigation menu item remote service. Add custom service methods to {@link com.liferay.site.navigation.service.impl.SiteNavigationMenuItemServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public SiteNavigationMenuItem addSiteNavigationMenuItem(long groupId,
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		java.lang.String type, java.lang.String typeSettings,
		ServiceContext serviceContext) throws PortalException;

	public SiteNavigationMenuItem deleteSiteNavigationMenuItem(
		long siteNavigationMenuItemId) throws PortalException;

	public void deleteSiteNavigationMenuItems(long siteNavigationMenuId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SiteNavigationMenuItem> getSiteNavigationMenuItems(
		long siteNavigationMenuId);

	public SiteNavigationMenuItem updateSiteNavigationMenuItem(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		int order) throws PortalException;

	public SiteNavigationMenuItem updateSiteNavigationMenuItem(
		long siteNavigationMenuId, java.lang.String typeSettings,
		ServiceContext serviceContext) throws PortalException;
}
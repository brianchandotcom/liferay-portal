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
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.site.navigation.model.SiteNavigationMenu;

import java.util.List;

/**
 * Provides the remote service interface for SiteNavigationMenu. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuServiceUtil
 * @see com.liferay.site.navigation.service.base.SiteNavigationMenuServiceBaseImpl
 * @see com.liferay.site.navigation.service.impl.SiteNavigationMenuServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=sitenavigation", "json.web.service.context.path=SiteNavigationMenu"}, service = SiteNavigationMenuService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SiteNavigationMenuService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SiteNavigationMenuServiceUtil} to access the site navigation menu remote service. Add custom service methods to {@link com.liferay.site.navigation.service.impl.SiteNavigationMenuServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public SiteNavigationMenu addSiteNavigationMenu(long groupId,
		java.lang.String name, int type, ServiceContext serviceContext)
		throws PortalException;

	public SiteNavigationMenu addSiteNavigationMenu(long groupId,
		java.lang.String name, ServiceContext serviceContext)
		throws PortalException;

	public SiteNavigationMenu deleteSiteNavigationMenu(
		long siteNavigationMenuId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SiteNavigationMenu fetchSiteNavigationMenu(long siteNavigationMenuId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SiteNavigationMenu> getSiteNavigationMenus(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SiteNavigationMenu> getSiteNavigationMenus(long groupId,
		int start, int end, OrderByComparator orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SiteNavigationMenu> getSiteNavigationMenus(long groupId,
		java.lang.String keywords, int start, int end,
		OrderByComparator orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSiteNavigationMenusCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSiteNavigationMenusCount(long groupId,
		java.lang.String keywords);

	public SiteNavigationMenu updateSiteNavigationMenu(
		long siteNavigationMenuId, int type, boolean auto,
		ServiceContext serviceContext) throws PortalException;

	public SiteNavigationMenu updateSiteNavigationMenu(
		long siteNavigationMenuId, java.lang.String name,
		ServiceContext serviceContext) throws PortalException;
}
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

package com.liferay.commerce.wish.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.wish.list.model.CommerceWishList;

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

import java.util.List;

/**
 * Provides the remote service interface for CommerceWishList. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListServiceUtil
 * @see com.liferay.commerce.wish.list.service.base.CommerceWishListServiceBaseImpl
 * @see com.liferay.commerce.wish.list.service.impl.CommerceWishListServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceWishList"}, service = CommerceWishListService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceWishListService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceWishListServiceUtil} to access the commerce wish list remote service. Add custom service methods to {@link com.liferay.commerce.wish.list.service.impl.CommerceWishListServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceWishList addCommerceWishList(String name,
		boolean defaultWishList, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommerceWishList(long commerceWishListId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceWishList getCommerceWishList(long commerceWishListId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWishList> getCommerceWishLists(long groupId, int start,
		int end, OrderByComparator<CommerceWishList> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceWishList> getCommerceWishLists(long groupId,
		long userId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceWishListsCount(long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceWishListsCount(long groupId, long userId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommerceWishList updateCommerceWishList(long commerceWishListId,
		String name, boolean defaultWishList) throws PortalException;
}
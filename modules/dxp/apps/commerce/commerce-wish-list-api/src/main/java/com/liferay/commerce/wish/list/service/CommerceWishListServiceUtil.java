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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceWishList. This utility wraps
 * {@link com.liferay.commerce.wish.list.service.impl.CommerceWishListServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListService
 * @see com.liferay.commerce.wish.list.service.base.CommerceWishListServiceBaseImpl
 * @see com.liferay.commerce.wish.list.service.impl.CommerceWishListServiceImpl
 * @generated
 */
@ProviderType
public class CommerceWishListServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.wish.list.service.impl.CommerceWishListServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.wish.list.model.CommerceWishList addCommerceWishList(
		java.lang.String name, boolean defaultWishList,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceWishList(name, defaultWishList, serviceContext);
	}

	public static void deleteCommerceWishList(long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceWishList(commerceWishListId);
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishList getCommerceWishList(
		long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWishList(commerceWishListId);
	}

	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList> getCommerceWishLists(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishList> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWishLists(groupId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList> getCommerceWishLists(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishList> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWishLists(groupId, userId, start, end,
			orderByComparator);
	}

	public static int getCommerceWishListsCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWishListsCount(groupId);
	}

	public static int getCommerceWishListsCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWishListsCount(groupId, userId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishList updateCommerceWishList(
		long commerceWishListId, java.lang.String name, boolean defaultWishList)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceWishList(commerceWishListId, name,
			defaultWishList);
	}

	public static CommerceWishListService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWishListService, CommerceWishListService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceWishListService.class);

		ServiceTracker<CommerceWishListService, CommerceWishListService> serviceTracker =
			new ServiceTracker<CommerceWishListService, CommerceWishListService>(bundle.getBundleContext(),
				CommerceWishListService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
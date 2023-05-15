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

import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceWishList. This utility wraps
 * <code>com.liferay.commerce.wish.list.service.impl.CommerceWishListServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListService
 * @generated
 */
public class CommerceWishListServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.wish.list.service.impl.CommerceWishListServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceWishList addCommerceWishList(
			String name, boolean defaultWishList,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceWishList(
			name, defaultWishList, serviceContext);
	}

	public static void deleteCommerceWishList(long commerceWishListId)
		throws PortalException {

		getService().deleteCommerceWishList(commerceWishListId);
	}

	public static CommerceWishList fetchCommerceWishList(
			long groupId, long userId, boolean defaultWishList,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws PortalException {

		return getService().fetchCommerceWishList(
			groupId, userId, defaultWishList, orderByComparator);
	}

	public static CommerceWishList getCommerceWishList(long commerceWishListId)
		throws PortalException {

		return getService().getCommerceWishList(commerceWishListId);
	}

	public static List<CommerceWishList> getCommerceWishLists(
			long groupId, int start, int end,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws PortalException {

		return getService().getCommerceWishLists(
			groupId, start, end, orderByComparator);
	}

	public static List<CommerceWishList> getCommerceWishLists(
			long groupId, long userId, int start, int end,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws PortalException {

		return getService().getCommerceWishLists(
			groupId, userId, start, end, orderByComparator);
	}

	public static int getCommerceWishListsCount(long groupId)
		throws PortalException {

		return getService().getCommerceWishListsCount(groupId);
	}

	public static int getCommerceWishListsCount(long groupId, long userId)
		throws PortalException {

		return getService().getCommerceWishListsCount(groupId, userId);
	}

	public static CommerceWishList getDefaultCommerceWishList(
			long groupId, long userId)
		throws PortalException {

		return getService().getDefaultCommerceWishList(groupId, userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceWishList updateCommerceWishList(
			long commerceWishListId, String name, boolean defaultWishList)
		throws PortalException {

		return getService().updateCommerceWishList(
			commerceWishListId, name, defaultWishList);
	}

	public static CommerceWishListService getService() {
		return _service;
	}

	public static void setService(CommerceWishListService service) {
		_service = service;
	}

	private static volatile CommerceWishListService _service;

}
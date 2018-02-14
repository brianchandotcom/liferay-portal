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

package com.liferay.commerce.wish.list.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.wish.list.service.CommerceWishListServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceWishListServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.wish.list.model.CommerceWishListSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.wish.list.model.CommerceWishList}, that is translated to a
 * {@link com.liferay.commerce.wish.list.model.CommerceWishListSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListServiceHttp
 * @see com.liferay.commerce.wish.list.model.CommerceWishListSoap
 * @see CommerceWishListServiceUtil
 * @generated
 */
@ProviderType
public class CommerceWishListServiceSoap {
	public static com.liferay.commerce.wish.list.model.CommerceWishListSoap addCommerceWishList(
		java.lang.String name, boolean defaultWishList,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.wish.list.model.CommerceWishList returnValue = CommerceWishListServiceUtil.addCommerceWishList(name,
					defaultWishList, serviceContext);

			return com.liferay.commerce.wish.list.model.CommerceWishListSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceWishList(long commerceWishListId)
		throws RemoteException {
		try {
			CommerceWishListServiceUtil.deleteCommerceWishList(commerceWishListId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishListSoap getCommerceWishList(
		long commerceWishListId) throws RemoteException {
		try {
			com.liferay.commerce.wish.list.model.CommerceWishList returnValue = CommerceWishListServiceUtil.getCommerceWishList(commerceWishListId);

			return com.liferay.commerce.wish.list.model.CommerceWishListSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishListSoap[] getCommerceWishLists(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishList> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList> returnValue =
				CommerceWishListServiceUtil.getCommerceWishLists(groupId,
					start, end, orderByComparator);

			return com.liferay.commerce.wish.list.model.CommerceWishListSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishListSoap[] getCommerceWishLists(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishList> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList> returnValue =
				CommerceWishListServiceUtil.getCommerceWishLists(groupId,
					userId, start, end, orderByComparator);

			return com.liferay.commerce.wish.list.model.CommerceWishListSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceWishListsCount(long groupId)
		throws RemoteException {
		try {
			int returnValue = CommerceWishListServiceUtil.getCommerceWishListsCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceWishListsCount(long groupId, long userId)
		throws RemoteException {
		try {
			int returnValue = CommerceWishListServiceUtil.getCommerceWishListsCount(groupId,
					userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishListSoap updateCommerceWishList(
		long commerceWishListId, java.lang.String name, boolean defaultWishList)
		throws RemoteException {
		try {
			com.liferay.commerce.wish.list.model.CommerceWishList returnValue = CommerceWishListServiceUtil.updateCommerceWishList(commerceWishListId,
					name, defaultWishList);

			return com.liferay.commerce.wish.list.model.CommerceWishListSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceWishListServiceSoap.class);
}
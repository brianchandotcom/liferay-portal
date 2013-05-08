/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import com.liferay.portlet.shopping.service.ShoppingItemServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.shopping.service.ShoppingItemServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.shopping.model.ShoppingItemSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.shopping.model.ShoppingItem}, that is translated to a
 * {@link com.liferay.portlet.shopping.model.ShoppingItemSoap}. Methods that SOAP cannot
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
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemServiceHttp
 * @see       com.liferay.portlet.shopping.model.ShoppingItemSoap
 * @see       com.liferay.portlet.shopping.service.ShoppingItemServiceUtil
 * @generated
 */
public class ShoppingItemServiceSoap {
	public static void addBookItems(long groupId, long categoryId,
		java.lang.String[] isbns) throws RemoteException {
		try {
			ShoppingItemServiceUtil.addBookItems(groupId, categoryId, isbns);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteItem(long itemId) throws RemoteException {
		try {
			ShoppingItemServiceUtil.deleteItem(itemId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCategoriesItemsCount(long groupId, Long[] categoryIds)
		throws RemoteException {
		try {
			int returnValue = ShoppingItemServiceUtil.getCategoriesItemsCount(groupId,
					ListUtil.toList(categoryIds));

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemSoap getItem(
		long itemId) throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingItem returnValue = ShoppingItemServiceUtil.getItem(itemId);

			return com.liferay.portlet.shopping.model.ShoppingItemSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemSoap[] getItems(
		long groupId, long categoryId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> returnValue =
				ShoppingItemServiceUtil.getItems(groupId, categoryId);

			return com.liferay.portlet.shopping.model.ShoppingItemSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemSoap[] getItems(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> returnValue =
				ShoppingItemServiceUtil.getItems(groupId, categoryId, start,
					end, obc);

			return com.liferay.portlet.shopping.model.ShoppingItemSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getItemsCount(long groupId, long categoryId)
		throws RemoteException {
		try {
			int returnValue = ShoppingItemServiceUtil.getItemsCount(groupId,
					categoryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingItemSoap[] getItemsPrevAndNext(
		long itemId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingItem[] returnValue = ShoppingItemServiceUtil.getItemsPrevAndNext(itemId,
					obc);

			return com.liferay.portlet.shopping.model.ShoppingItemSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingItemServiceSoap.class);
}
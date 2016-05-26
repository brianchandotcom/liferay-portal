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

package com.liferay.shopping.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import com.liferay.shopping.service.ShoppingCategoryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link ShoppingCategoryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.shopping.model.ShoppingCategorySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.shopping.model.ShoppingCategory}, that is translated to a
 * {@link com.liferay.shopping.model.ShoppingCategorySoap}. Methods that SOAP cannot
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
 * @author Brian Wing Shun Chan
 * @see ShoppingCategoryServiceHttp
 * @see com.liferay.shopping.model.ShoppingCategorySoap
 * @see ShoppingCategoryServiceUtil
 * @generated
 */
@ProviderType
public class ShoppingCategoryServiceSoap {
	public static com.liferay.shopping.model.ShoppingCategorySoap addCategory(
		long parentCategoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.shopping.model.ShoppingCategory returnValue = ShoppingCategoryServiceUtil.addCategory(parentCategoryId,
					name, description, serviceContext);

			return com.liferay.shopping.model.ShoppingCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCategory(long categoryId)
		throws RemoteException {
		try {
			ShoppingCategoryServiceUtil.deleteCategory(categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.shopping.model.ShoppingCategorySoap[] getCategories(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.shopping.model.ShoppingCategory> returnValue =
				ShoppingCategoryServiceUtil.getCategories(groupId);

			return com.liferay.shopping.model.ShoppingCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.shopping.model.ShoppingCategorySoap[] getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.shopping.model.ShoppingCategory> returnValue =
				ShoppingCategoryServiceUtil.getCategories(groupId,
					parentCategoryId, start, end);

			return com.liferay.shopping.model.ShoppingCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCategoriesAndItemsCount(long groupId, long categoryId)
		throws RemoteException {
		try {
			int returnValue = ShoppingCategoryServiceUtil.getCategoriesAndItemsCount(groupId,
					categoryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId)
		throws RemoteException {
		try {
			int returnValue = ShoppingCategoryServiceUtil.getCategoriesCount(groupId,
					parentCategoryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.shopping.model.ShoppingCategorySoap getCategory(
		long categoryId) throws RemoteException {
		try {
			com.liferay.shopping.model.ShoppingCategory returnValue = ShoppingCategoryServiceUtil.getCategory(categoryId);

			return com.liferay.shopping.model.ShoppingCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void getSubcategoryIds(Long[] categoryIds, long groupId,
		long categoryId) throws RemoteException {
		try {
			ShoppingCategoryServiceUtil.getSubcategoryIds(ListUtil.toList(
					categoryIds), groupId, categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.shopping.model.ShoppingCategorySoap updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentCategory,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.shopping.model.ShoppingCategory returnValue = ShoppingCategoryServiceUtil.updateCategory(categoryId,
					parentCategoryId, name, description,
					mergeWithParentCategory, serviceContext);

			return com.liferay.shopping.model.ShoppingCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingCategoryServiceSoap.class);
}
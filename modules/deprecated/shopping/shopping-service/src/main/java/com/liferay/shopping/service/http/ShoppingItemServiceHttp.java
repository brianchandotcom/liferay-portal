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
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import com.liferay.shopping.service.ShoppingItemServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link ShoppingItemServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemServiceSoap
 * @see HttpPrincipal
 * @see ShoppingItemServiceUtil
 * @generated
 */
@ProviderType
public class ShoppingItemServiceHttp {
	public static com.liferay.shopping.model.ShoppingItem addItem(
		HttpPrincipal httpPrincipal, long groupId, long categoryId,
		java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean mediumImage,
		java.lang.String mediumImageURL, java.io.File mediumFile,
		boolean largeImage, java.lang.String largeImageURL,
		java.io.File largeFile,
		java.util.List<com.liferay.shopping.model.ShoppingItemField> itemFields,
		java.util.List<com.liferay.shopping.model.ShoppingItemPrice> itemPrices,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"addItem", _addItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryId, sku, name, description, properties,
					fieldsQuantities, requiresShipping, stockQuantity,
					featured, sale, smallImage, smallImageURL, smallFile,
					mediumImage, mediumImageURL, mediumFile, largeImage,
					largeImageURL, largeFile, itemFields, itemPrices,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.shopping.model.ShoppingItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteItem(HttpPrincipal httpPrincipal, long itemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"deleteItem", _deleteItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesItemsCount(HttpPrincipal httpPrincipal,
		long groupId, java.util.List<java.lang.Long> categoryIds) {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getCategoriesItemsCount",
					_getCategoriesItemsCountParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.shopping.model.ShoppingItem getItem(
		HttpPrincipal httpPrincipal, long itemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getItem", _getItemParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.shopping.model.ShoppingItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.shopping.model.ShoppingItem> getItems(
		HttpPrincipal httpPrincipal, long groupId, long categoryId) {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getItems", _getItemsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.shopping.model.ShoppingItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.shopping.model.ShoppingItem> getItems(
		HttpPrincipal httpPrincipal, long groupId, long categoryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.shopping.model.ShoppingItem> obc) {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getItems", _getItemsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.shopping.model.ShoppingItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getItemsCount(HttpPrincipal httpPrincipal, long groupId,
		long categoryId) {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getItemsCount", _getItemsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.shopping.model.ShoppingItem[] getItemsPrevAndNext(
		HttpPrincipal httpPrincipal, long itemId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.shopping.model.ShoppingItem> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getItemsPrevAndNext", _getItemsPrevAndNextParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId,
					obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.shopping.model.ShoppingItem[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.shopping.model.ShoppingItem updateItem(
		HttpPrincipal httpPrincipal, long itemId, long groupId,
		long categoryId, java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile, boolean mediumImage,
		java.lang.String mediumImageURL, java.io.File mediumFile,
		boolean largeImage, java.lang.String largeImageURL,
		java.io.File largeFile,
		java.util.List<com.liferay.shopping.model.ShoppingItemField> itemFields,
		java.util.List<com.liferay.shopping.model.ShoppingItemPrice> itemPrices,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"updateItem", _updateItemParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId,
					groupId, categoryId, sku, name, description, properties,
					fieldsQuantities, requiresShipping, stockQuantity,
					featured, sale, smallImage, smallImageURL, smallFile,
					mediumImage, mediumImageURL, mediumFile, largeImage,
					largeImageURL, largeFile, itemFields, itemPrices,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.shopping.model.ShoppingItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingItemServiceHttp.class);
	private static final Class<?>[] _addItemParameterTypes0 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, boolean.class,
			int.class, boolean.class, java.lang.Boolean.class, boolean.class,
			java.lang.String.class, java.io.File.class, boolean.class,
			java.lang.String.class, java.io.File.class, boolean.class,
			java.lang.String.class, java.io.File.class, java.util.List.class,
			java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteItemParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCategoriesItemsCountParameterTypes2 = new Class[] {
			long.class, java.util.List.class
		};
	private static final Class<?>[] _getItemParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getItemsParameterTypes4 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getItemsParameterTypes5 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getItemsCountParameterTypes6 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getItemsPrevAndNextParameterTypes7 = new Class[] {
			long.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _updateItemParameterTypes8 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, boolean.class,
			int.class, boolean.class, java.lang.Boolean.class, boolean.class,
			java.lang.String.class, java.io.File.class, boolean.class,
			java.lang.String.class, java.io.File.class, boolean.class,
			java.lang.String.class, java.io.File.class, java.util.List.class,
			java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
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
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.shopping.service.ShoppingItemServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.shopping.service.ShoppingItemServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
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
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.shopping.service.ShoppingItemServiceUtil
 * @generated
 */
public class ShoppingItemServiceHttp {
	public static void addBookItems(HttpPrincipal httpPrincipal, long groupId,
		long categoryId, java.lang.String[] isbns)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"addBookItems", _addBookItemsParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryId, isbns);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem addItem(
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
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> itemFields,
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> itemPrices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"addItem", _addItemParameterTypes1);

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

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteItem(HttpPrincipal httpPrincipal, long itemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"deleteItem", _deleteItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
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
		long groupId, java.util.List<java.lang.Long> categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getCategoriesItemsCount",
					_getCategoriesItemsCountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem getItem(
		HttpPrincipal httpPrincipal, long itemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getItem", _getItemParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, itemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> getItems(
		HttpPrincipal httpPrincipal, long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getItems", _getItemsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.shopping.model.ShoppingItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> getItems(
		HttpPrincipal httpPrincipal, long groupId, long categoryId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getItems", _getItemsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.shopping.model.ShoppingItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getItemsCount(HttpPrincipal httpPrincipal, long groupId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getItemsCount", _getItemsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem[] getItemsPrevAndNext(
		HttpPrincipal httpPrincipal, long itemId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"getItemsPrevAndNext", _getItemsPrevAndNextParameterTypes8);

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

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingItem[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingItem updateItem(
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
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemField> itemFields,
		java.util.List<com.liferay.portlet.shopping.model.ShoppingItemPrice> itemPrices,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingItemServiceUtil.class,
					"updateItem", _updateItemParameterTypes9);

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

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingItemServiceHttp.class);
	private static final Class<?>[] _addBookItemsParameterTypes0 = new Class[] {
			long.class, long.class, java.lang.String[].class
		};
	private static final Class<?>[] _addItemParameterTypes1 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, boolean.class,
			int.class, boolean.class, java.lang.Boolean.class, boolean.class,
			java.lang.String.class, java.io.File.class, boolean.class,
			java.lang.String.class, java.io.File.class, boolean.class,
			java.lang.String.class, java.io.File.class, java.util.List.class,
			java.util.List.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteItemParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCategoriesItemsCountParameterTypes3 = new Class[] {
			long.class, java.util.List.class
		};
	private static final Class<?>[] _getItemParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getItemsParameterTypes5 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getItemsParameterTypes6 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getItemsCountParameterTypes7 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getItemsPrevAndNextParameterTypes8 = new Class[] {
			long.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _updateItemParameterTypes9 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, boolean.class,
			int.class, boolean.class, java.lang.Boolean.class, boolean.class,
			java.lang.String.class, java.io.File.class, boolean.class,
			java.lang.String.class, java.io.File.class, boolean.class,
			java.lang.String.class, java.io.File.class, java.util.List.class,
			java.util.List.class,
			com.liferay.portal.service.ServiceContext.class
		};
}
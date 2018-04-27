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

import com.liferay.commerce.wish.list.service.CommerceWishListItemServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceWishListItemServiceUtil} service utility. The
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
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemServiceSoap
 * @see HttpPrincipal
 * @see CommerceWishListItemServiceUtil
 * @generated
 */
@ProviderType
public class CommerceWishListItemServiceHttp {
	public static com.liferay.commerce.wish.list.model.CommerceWishListItem addCommerceWishListItem(
		HttpPrincipal httpPrincipal, long commerceWishListId,
		long cpDefinitionId, long cpInstanceId, String json,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWishListItemServiceUtil.class,
					"addCommerceWishListItem",
					_addCommerceWishListItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWishListId, cpDefinitionId, cpInstanceId, json,
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

			return (com.liferay.commerce.wish.list.model.CommerceWishListItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceWishListItem(HttpPrincipal httpPrincipal,
		long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWishListItemServiceUtil.class,
					"deleteCommerceWishListItem",
					_deleteCommerceWishListItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWishListItemId);

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

	public static com.liferay.commerce.wish.list.model.CommerceWishListItem getCommerceWishListItem(
		HttpPrincipal httpPrincipal, long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWishListItemServiceUtil.class,
					"getCommerceWishListItem",
					_getCommerceWishListItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWishListItemId);

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

			return (com.liferay.commerce.wish.list.model.CommerceWishListItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishListItem> getCommerceWishListItems(
		HttpPrincipal httpPrincipal, long commerceWishListId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishListItem> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWishListItemServiceUtil.class,
					"getCommerceWishListItems",
					_getCommerceWishListItemsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWishListId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.wish.list.model.CommerceWishListItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceWishListItemsCount(
		HttpPrincipal httpPrincipal, long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceWishListItemServiceUtil.class,
					"getCommerceWishListItemsCount",
					_getCommerceWishListItemsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceWishListId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceWishListItemServiceHttp.class);
	private static final Class<?>[] _addCommerceWishListItemParameterTypes0 = new Class[] {
			long.class, long.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceWishListItemParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceWishListItemParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceWishListItemsParameterTypes3 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceWishListItemsCountParameterTypes4 =
		new Class[] { long.class };
}
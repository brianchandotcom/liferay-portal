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

package com.liferay.commerce.cart.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.service.CommerceCartItemServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceCartItemServiceUtil} service utility. The
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
 * @author Marco Leo
 * @see CommerceCartItemServiceSoap
 * @see HttpPrincipal
 * @see CommerceCartItemServiceUtil
 * @generated
 */
@ProviderType
public class CommerceCartItemServiceHttp {
	public static java.util.List<com.liferay.commerce.cart.model.CommerceCartItem> getCommerceCartItems(
		HttpPrincipal httpPrincipal, long commerceCartId, int start, int end) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCartItemServiceUtil.class,
					"getCommerceCartItems", _getCommerceCartItemsParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCartId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.cart.model.CommerceCartItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceCartItemsCount(HttpPrincipal httpPrincipal,
		long commerceCartId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCartItemServiceUtil.class,
					"getCommerceCartItemsCount",
					_getCommerceCartItemsCountParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCartId);

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

	public static com.liferay.commerce.cart.model.CommerceCartItem deleteCommerceCartItem(
		HttpPrincipal httpPrincipal, long commerceCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCartItemServiceUtil.class,
					"deleteCommerceCartItem",
					_deleteCommerceCartItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCartItemId);

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

			return (com.liferay.commerce.cart.model.CommerceCartItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.cart.model.CommerceCartItem> getCommerceCartItems(
		HttpPrincipal httpPrincipal, long commerceCartId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CommerceCartItem> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCartItemServiceUtil.class,
					"getCommerceCartItems", _getCommerceCartItemsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCartId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.cart.model.CommerceCartItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceCartItemServiceHttp.class);
	private static final Class<?>[] _getCommerceCartItemsParameterTypes0 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCommerceCartItemsCountParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteCommerceCartItemParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceCartItemsParameterTypes3 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
}
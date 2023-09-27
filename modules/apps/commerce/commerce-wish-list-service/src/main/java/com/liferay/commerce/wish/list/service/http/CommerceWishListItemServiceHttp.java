/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.service.http;

import com.liferay.commerce.wish.list.service.CommerceWishListItemServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceWishListItemServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
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
 * @generated
 */
public class CommerceWishListItemServiceHttp {

	public static com.liferay.commerce.wish.list.model.CommerceWishListItem
			addCommerceWishListItem(
				HttpPrincipal httpPrincipal, long commerceAccountId,
				long commerceWishListId, long cProductId, String cpInstanceUuid,
				String json,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceWishListItemServiceUtil.class,
				"addCommerceWishListItem",
				_addCommerceWishListItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, commerceWishListId, cProductId,
				cpInstanceUuid, json, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.wish.list.model.CommerceWishListItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceWishListItem(
			HttpPrincipal httpPrincipal, long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceWishListItemServiceUtil.class,
				"deleteCommerceWishListItem",
				_deleteCommerceWishListItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceWishListItemId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishListItem
			getCommerceWishListItem(
				HttpPrincipal httpPrincipal, long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceWishListItemServiceUtil.class,
				"getCommerceWishListItem",
				_getCommerceWishListItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceWishListItemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.wish.list.model.CommerceWishListItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishListItem
			getCommerceWishListItem(
				HttpPrincipal httpPrincipal, long commerceWishListId,
				String cpInstanceUuid, long cProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceWishListItemServiceUtil.class,
				"getCommerceWishListItem",
				_getCommerceWishListItemParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceWishListId, cpInstanceUuid, cProductId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.wish.list.model.CommerceWishListItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceWishListItemByContainsCPInstanceCount(
			HttpPrincipal httpPrincipal, long commerceWishListId,
			String cpInstanceUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceWishListItemServiceUtil.class,
				"getCommerceWishListItemByContainsCPInstanceCount",
				_getCommerceWishListItemByContainsCPInstanceCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceWishListId, cpInstanceUuid);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceWishListItemByContainsCProductCount(
			HttpPrincipal httpPrincipal, long commerceWishListId,
			long cProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceWishListItemServiceUtil.class,
				"getCommerceWishListItemByContainsCProductCount",
				_getCommerceWishListItemByContainsCProductCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceWishListId, cProductId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.wish.list.model.CommerceWishListItem>
				getCommerceWishListItems(
					HttpPrincipal httpPrincipal, long commerceWishListId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.wish.list.model.
							CommerceWishListItem> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceWishListItemServiceUtil.class,
				"getCommerceWishListItems",
				_getCommerceWishListItemsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceWishListId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.wish.list.model.CommerceWishListItem>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceWishListItemsCount(
			HttpPrincipal httpPrincipal, long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceWishListItemServiceUtil.class,
				"getCommerceWishListItemsCount",
				_getCommerceWishListItemsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceWishListId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceWishListItemServiceHttp.class);

	private static final Class<?>[] _addCommerceWishListItemParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceWishListItemParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceWishListItemParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceWishListItemParameterTypes3 =
		new Class[] {long.class, String.class, long.class};
	private static final Class<?>[]
		_getCommerceWishListItemByContainsCPInstanceCountParameterTypes4 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCommerceWishListItemByContainsCProductCountParameterTypes5 =
			new Class[] {long.class, long.class};
	private static final Class<?>[] _getCommerceWishListItemsParameterTypes6 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceWishListItemsCountParameterTypes7 = new Class[] {
			long.class
		};

}
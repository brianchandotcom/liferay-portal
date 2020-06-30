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

package com.liferay.commerce.discount.service.http;

import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceDiscountCommerceAccountGroupRelServiceUtil</code> service
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
 * @author Marco Leo
 * @see CommerceDiscountCommerceAccountGroupRelServiceSoap
 * @generated
 */
public class CommerceDiscountCommerceAccountGroupRelServiceHttp {

	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					addCommerceDiscountCommerceAccountGroupRel(
						HttpPrincipal httpPrincipal, long commerceDiscountId,
						long commerceAccountGroupId,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountCommerceAccountGroupRelServiceUtil.class,
				"addCommerceDiscountCommerceAccountGroupRel",
				_addCommerceDiscountCommerceAccountGroupRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, commerceAccountGroupId,
				serviceContext);

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

			return (com.liferay.commerce.discount.model.
				CommerceDiscountCommerceAccountGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceDiscountCommerceAccountGroupRel(
			HttpPrincipal httpPrincipal,
			long commerceDiscountCommerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountCommerceAccountGroupRelServiceUtil.class,
				"deleteCommerceDiscountCommerceAccountGroupRel",
				_deleteCommerceDiscountCommerceAccountGroupRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountCommerceAccountGroupRelId);

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

	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					fetchCommerceDiscountCommerceAccountGroupRel(
						HttpPrincipal httpPrincipal, long commerceDiscountId,
						long commerceAccountGroupId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountCommerceAccountGroupRelServiceUtil.class,
				"fetchCommerceDiscountCommerceAccountGroupRel",
				_fetchCommerceDiscountCommerceAccountGroupRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, commerceAccountGroupId);

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

			return (com.liferay.commerce.discount.model.
				CommerceDiscountCommerceAccountGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					getCommerceDiscountCommerceAccountGroupRel(
						HttpPrincipal httpPrincipal,
						long commerceDiscountCommerceAccountGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountCommerceAccountGroupRelServiceUtil.class,
				"getCommerceDiscountCommerceAccountGroupRel",
				_getCommerceDiscountCommerceAccountGroupRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountCommerceAccountGroupRelId);

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

			return (com.liferay.commerce.discount.model.
				CommerceDiscountCommerceAccountGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel>
					getCommerceDiscountCommerceAccountGroupRels(
						HttpPrincipal httpPrincipal, long commerceDiscountId,
						int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.discount.model.
								CommerceDiscountCommerceAccountGroupRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountCommerceAccountGroupRelServiceUtil.class,
				"getCommerceDiscountCommerceAccountGroupRels",
				_getCommerceDiscountCommerceAccountGroupRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, start, end, orderByComparator);

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
				<com.liferay.commerce.discount.model.
					CommerceDiscountCommerceAccountGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceDiscountCommerceAccountGroupRelsCount(
			HttpPrincipal httpPrincipal, long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountCommerceAccountGroupRelServiceUtil.class,
				"getCommerceDiscountCommerceAccountGroupRelsCount",
				_getCommerceDiscountCommerceAccountGroupRelsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId);

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
		CommerceDiscountCommerceAccountGroupRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceDiscountCommerceAccountGroupRelParameterTypes0 =
			new Class[] {
				long.class, long.class,
				com.liferay.portal.kernel.service.ServiceContext.class
			};
	private static final Class<?>[]
		_deleteCommerceDiscountCommerceAccountGroupRelParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_fetchCommerceDiscountCommerceAccountGroupRelParameterTypes2 =
			new Class[] {long.class, long.class};
	private static final Class<?>[]
		_getCommerceDiscountCommerceAccountGroupRelParameterTypes3 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceDiscountCommerceAccountGroupRelsParameterTypes4 =
			new Class[] {
				long.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getCommerceDiscountCommerceAccountGroupRelsCountParameterTypes5 =
			new Class[] {long.class};

}
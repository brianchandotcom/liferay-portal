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

package com.liferay.commerce.price.list.service.http;

import com.liferay.commerce.price.list.service.CommercePriceListOrderTypeRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommercePriceListOrderTypeRelServiceUtil</code> service
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
 * @author Alessio Antonio Rendina
 * @see CommercePriceListOrderTypeRelServiceSoap
 * @generated
 */
public class CommercePriceListOrderTypeRelServiceHttp {

	public static
		com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel
				addCommercePriceListOrderTypeRel(
					HttpPrincipal httpPrincipal, long commercePriceListId,
					long commerceOrderTypeId, int priority,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListOrderTypeRelServiceUtil.class,
				"addCommercePriceListOrderTypeRel",
				_addCommercePriceListOrderTypeRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, commerceOrderTypeId, priority,
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

			return (com.liferay.commerce.price.list.model.
				CommercePriceListOrderTypeRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommercePriceListOrderTypeRel(
			HttpPrincipal httpPrincipal, long commercePriceListOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListOrderTypeRelServiceUtil.class,
				"deleteCommercePriceListOrderTypeRel",
				_deleteCommercePriceListOrderTypeRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListOrderTypeRelId);

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
		com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel
				fetchCommercePriceListOrderTypeRel(
					HttpPrincipal httpPrincipal, long commercePriceListId,
					long commerceOrderTypeId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListOrderTypeRelServiceUtil.class,
				"fetchCommercePriceListOrderTypeRel",
				_fetchCommercePriceListOrderTypeRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, commerceOrderTypeId);

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

			return (com.liferay.commerce.price.list.model.
				CommercePriceListOrderTypeRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel
				getCommercePriceListOrderTypeRel(
					HttpPrincipal httpPrincipal,
					long commercePriceListOrderTypeRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListOrderTypeRelServiceUtil.class,
				"getCommercePriceListOrderTypeRel",
				_getCommercePriceListOrderTypeRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListOrderTypeRelId);

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

			return (com.liferay.commerce.price.list.model.
				CommercePriceListOrderTypeRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel>
				getCommercePriceListOrderTypeRels(
					HttpPrincipal httpPrincipal, long commercePriceListId,
					String name, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceListOrderTypeRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListOrderTypeRelServiceUtil.class,
				"getCommercePriceListOrderTypeRels",
				_getCommercePriceListOrderTypeRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, name, start, end,
				orderByComparator);

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
				<com.liferay.commerce.price.list.model.
					CommercePriceListOrderTypeRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePriceListOrderTypeRelsCount(
			HttpPrincipal httpPrincipal, long commercePriceListId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListOrderTypeRelServiceUtil.class,
				"getCommercePriceListOrderTypeRelsCount",
				_getCommercePriceListOrderTypeRelsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, name);

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
		CommercePriceListOrderTypeRelServiceHttp.class);

	private static final Class<?>[]
		_addCommercePriceListOrderTypeRelParameterTypes0 = new Class[] {
			long.class, long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCommercePriceListOrderTypeRelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCommercePriceListOrderTypeRelParameterTypes2 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_getCommercePriceListOrderTypeRelParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommercePriceListOrderTypeRelsParameterTypes4 = new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommercePriceListOrderTypeRelsCountParameterTypes5 = new Class[] {
			long.class, String.class
		};

}
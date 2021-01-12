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

import com.liferay.commerce.price.list.service.CommercePriceListChannelRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommercePriceListChannelRelServiceUtil</code> service
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
 * @see CommercePriceListChannelRelServiceSoap
 * @generated
 */
public class CommercePriceListChannelRelServiceHttp {

	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRel
				addCommercePriceListChannelRel(
					HttpPrincipal httpPrincipal, long commercePriceListId,
					long commerceChannelId, int order,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListChannelRelServiceUtil.class,
				"addCommercePriceListChannelRel",
				_addCommercePriceListChannelRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, commerceChannelId, order,
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
				CommercePriceListChannelRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommercePriceListChannelRel(
			HttpPrincipal httpPrincipal, long commercePriceListChannelRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListChannelRelServiceUtil.class,
				"deleteCommercePriceListChannelRel",
				_deleteCommercePriceListChannelRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListChannelRelId);

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

	public static void deleteCommercePriceListChannelRelsByCommercePriceListId(
			HttpPrincipal httpPrincipal, long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListChannelRelServiceUtil.class,
				"deleteCommercePriceListChannelRelsByCommercePriceListId",
				_deleteCommercePriceListChannelRelsByCommercePriceListIdParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId);

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
		com.liferay.commerce.price.list.model.CommercePriceListChannelRel
				fetchCommercePriceListChannelRel(
					HttpPrincipal httpPrincipal, long commerceChannelId,
					long commercePriceListId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListChannelRelServiceUtil.class,
				"fetchCommercePriceListChannelRel",
				_fetchCommercePriceListChannelRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceChannelId, commercePriceListId);

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
				CommercePriceListChannelRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRel
				getCommercePriceListChannelRel(
					HttpPrincipal httpPrincipal,
					long commercePriceListChannelRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListChannelRelServiceUtil.class,
				"getCommercePriceListChannelRel",
				_getCommercePriceListChannelRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListChannelRelId);

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
				CommercePriceListChannelRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListChannelRel>
				getCommercePriceListChannelRels(
					HttpPrincipal httpPrincipal, long commercePriceListId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListChannelRelServiceUtil.class,
				"getCommercePriceListChannelRels",
				_getCommercePriceListChannelRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId);

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
					CommercePriceListChannelRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListChannelRel>
				getCommercePriceListChannelRels(
					HttpPrincipal httpPrincipal, long commercePriceListId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceListChannelRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListChannelRelServiceUtil.class,
				"getCommercePriceListChannelRels",
				_getCommercePriceListChannelRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, start, end, orderByComparator);

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
					CommercePriceListChannelRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListChannelRel>
			getCommercePriceListChannelRels(
				HttpPrincipal httpPrincipal, long commercePriceListId,
				String name, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListChannelRelServiceUtil.class,
				"getCommercePriceListChannelRels",
				_getCommercePriceListChannelRelsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, name, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.price.list.model.
					CommercePriceListChannelRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePriceListChannelRelsCount(
			HttpPrincipal httpPrincipal, long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListChannelRelServiceUtil.class,
				"getCommercePriceListChannelRelsCount",
				_getCommercePriceListChannelRelsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId);

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

	public static int getCommercePriceListChannelRelsCount(
		HttpPrincipal httpPrincipal, long commercePriceListId, String name) {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListChannelRelServiceUtil.class,
				"getCommercePriceListChannelRelsCount",
				_getCommercePriceListChannelRelsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
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
		CommercePriceListChannelRelServiceHttp.class);

	private static final Class<?>[]
		_addCommercePriceListChannelRelParameterTypes0 = new Class[] {
			long.class, long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCommercePriceListChannelRelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommercePriceListChannelRelsByCommercePriceListIdParameterTypes2 =
			new Class[] {long.class};
	private static final Class<?>[]
		_fetchCommercePriceListChannelRelParameterTypes3 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_getCommercePriceListChannelRelParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommercePriceListChannelRelsParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommercePriceListChannelRelsParameterTypes6 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommercePriceListChannelRelsParameterTypes7 = new Class[] {
			long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommercePriceListChannelRelsCountParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommercePriceListChannelRelsCountParameterTypes9 = new Class[] {
			long.class, String.class
		};

}
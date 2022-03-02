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

package com.liferay.commerce.payment.service.http;

import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommercePaymentMethodGroupRelQualifierServiceUtil</code> service
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
 * @author Luca Pellizzon
 * @generated
 */
public class CommercePaymentMethodGroupRelQualifierServiceHttp {

	public static
		com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier
					addCommercePaymentMethodGroupRelQualifier(
						HttpPrincipal httpPrincipal, String className,
						long classPK, long commercePaymentMethodGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"addCommercePaymentMethodGroupRelQualifier",
				_addCommercePaymentMethodGroupRelQualifierParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, commercePaymentMethodGroupRelId);

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

			return (com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifier)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommercePaymentMethodGroupRelQualifier(
			HttpPrincipal httpPrincipal,
			long commercePaymentMethodGroupRelQualifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"deleteCommercePaymentMethodGroupRelQualifier",
				_deleteCommercePaymentMethodGroupRelQualifierParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelQualifierId);

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

	public static void deleteCommercePaymentMethodGroupRelQualifiers(
			HttpPrincipal httpPrincipal, String className,
			long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"deleteCommercePaymentMethodGroupRelQualifiers",
				_deleteCommercePaymentMethodGroupRelQualifiersParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, commercePaymentMethodGroupRelId);

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

	public static void
			deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelId(
				HttpPrincipal httpPrincipal,
				long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelId",
				_deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelIdParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId);

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
		com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier
					fetchCommercePaymentMethodGroupRelQualifier(
						HttpPrincipal httpPrincipal, String className,
						long classPK, long commercePaymentMethodGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"fetchCommercePaymentMethodGroupRelQualifier",
				_fetchCommercePaymentMethodGroupRelQualifierParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, commercePaymentMethodGroupRelId);

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

			return (com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifier)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier>
					getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiers(
						HttpPrincipal httpPrincipal,
						long commercePaymentMethodGroupRelId, String keywords,
						int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiers",
				_getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId, keywords, start,
				end);

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
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRelQualifier>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int
			getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount(
				HttpPrincipal httpPrincipal,
				long commercePaymentMethodGroupRelId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount",
				_getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId, keywords);

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

	public static
		com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier
					getCommercePaymentMethodGroupRelQualifier(
						HttpPrincipal httpPrincipal,
						long commercePaymentMethodGroupRelQualifierId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"getCommercePaymentMethodGroupRelQualifier",
				_getCommercePaymentMethodGroupRelQualifierParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelQualifierId);

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

			return (com.liferay.commerce.payment.model.
				CommercePaymentMethodGroupRelQualifier)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier>
					getCommercePaymentMethodGroupRelQualifiers(
						HttpPrincipal httpPrincipal,
						long commercePaymentMethodGroupRelId, int start,
						int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.payment.model.
								CommercePaymentMethodGroupRelQualifier>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"getCommercePaymentMethodGroupRelQualifiers",
				_getCommercePaymentMethodGroupRelQualifiersParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId, start, end,
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
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRelQualifier>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier>
					getCommercePaymentMethodGroupRelQualifiers(
						HttpPrincipal httpPrincipal, String className,
						long commercePaymentMethodGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"getCommercePaymentMethodGroupRelQualifiers",
				_getCommercePaymentMethodGroupRelQualifiersParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, commercePaymentMethodGroupRelId);

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
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRelQualifier>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePaymentMethodGroupRelQualifiersCount(
			HttpPrincipal httpPrincipal, long commercePaymentMethodGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"getCommercePaymentMethodGroupRelQualifiersCount",
				_getCommercePaymentMethodGroupRelQualifiersCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId);

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
		<com.liferay.commerce.payment.model.
			CommercePaymentMethodGroupRelQualifier>
					getCommerceTermEntryCommercePaymentMethodGroupRelQualifiers(
						HttpPrincipal httpPrincipal,
						long commercePaymentMethodGroupRelId, String keywords,
						int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"getCommerceTermEntryCommercePaymentMethodGroupRelQualifiers",
				_getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId, keywords, start,
				end);

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
				<com.liferay.commerce.payment.model.
					CommercePaymentMethodGroupRelQualifier>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int
			getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount(
				HttpPrincipal httpPrincipal,
				long commercePaymentMethodGroupRelId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePaymentMethodGroupRelQualifierServiceUtil.class,
				"getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount",
				_getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePaymentMethodGroupRelId, keywords);

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
		CommercePaymentMethodGroupRelQualifierServiceHttp.class);

	private static final Class<?>[]
		_addCommercePaymentMethodGroupRelQualifierParameterTypes0 =
			new Class[] {String.class, long.class, long.class};
	private static final Class<?>[]
		_deleteCommercePaymentMethodGroupRelQualifierParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_deleteCommercePaymentMethodGroupRelQualifiersParameterTypes2 =
			new Class[] {String.class, long.class};
	private static final Class<?>[]
		_deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelIdParameterTypes3 =
			new Class[] {long.class};
	private static final Class<?>[]
		_fetchCommercePaymentMethodGroupRelQualifierParameterTypes4 =
			new Class[] {String.class, long.class, long.class};
	private static final Class<?>[]
		_getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersParameterTypes5 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCountParameterTypes6 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelQualifierParameterTypes7 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelQualifiersParameterTypes8 =
			new Class[] {
				long.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelQualifiersParameterTypes9 =
			new Class[] {String.class, long.class};
	private static final Class<?>[]
		_getCommercePaymentMethodGroupRelQualifiersCountParameterTypes10 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersParameterTypes11 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCountParameterTypes12 =
			new Class[] {long.class, String.class};

}
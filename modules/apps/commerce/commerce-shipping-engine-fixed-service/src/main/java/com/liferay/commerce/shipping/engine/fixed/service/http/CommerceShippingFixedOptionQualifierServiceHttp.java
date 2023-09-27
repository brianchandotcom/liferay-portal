/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.service.http;

import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceShippingFixedOptionQualifierServiceUtil</code> service
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
 * @generated
 */
public class CommerceShippingFixedOptionQualifierServiceHttp {

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifier
				addCommerceShippingFixedOptionQualifier(
					HttpPrincipal httpPrincipal, String className, long classPK,
					long commerceShippingFixedOptionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"addCommerceShippingFixedOptionQualifier",
				_addCommerceShippingFixedOptionQualifierParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, commerceShippingFixedOptionId);

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

			return (com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifier)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceShippingFixedOptionQualifier(
			HttpPrincipal httpPrincipal,
			long commerceShippingFixedOptionQualifierId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"deleteCommerceShippingFixedOptionQualifier",
				_deleteCommerceShippingFixedOptionQualifierParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionQualifierId);

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

	public static void deleteCommerceShippingFixedOptionQualifiers(
			HttpPrincipal httpPrincipal, long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"deleteCommerceShippingFixedOptionQualifiers",
				_deleteCommerceShippingFixedOptionQualifiersParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionId);

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

	public static void deleteCommerceShippingFixedOptionQualifiers(
			HttpPrincipal httpPrincipal, String className,
			long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"deleteCommerceShippingFixedOptionQualifiers",
				_deleteCommerceShippingFixedOptionQualifiersParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, commerceShippingFixedOptionId);

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

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifier
				fetchCommerceShippingFixedOptionQualifier(
					HttpPrincipal httpPrincipal, String className, long classPK,
					long commerceShippingFixedOptionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"fetchCommerceShippingFixedOptionQualifier",
				_fetchCommerceShippingFixedOptionQualifierParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, commerceShippingFixedOptionId);

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

			return (com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifier)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionQualifier>
					getCommerceOrderTypeCommerceShippingFixedOptionQualifiers(
						HttpPrincipal httpPrincipal,
						long commerceShippingFixedOptionId, String keywords,
						int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"getCommerceOrderTypeCommerceShippingFixedOptionQualifiers",
				_getCommerceOrderTypeCommerceShippingFixedOptionQualifiersParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionId, keywords, start, end);

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
				<com.liferay.commerce.shipping.engine.fixed.model.
					CommerceShippingFixedOptionQualifier>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int
			getCommerceOrderTypeCommerceShippingFixedOptionQualifiersCount(
				HttpPrincipal httpPrincipal, long commerceShippingFixedOptionId,
				String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"getCommerceOrderTypeCommerceShippingFixedOptionQualifiersCount",
				_getCommerceOrderTypeCommerceShippingFixedOptionQualifiersCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionId, keywords);

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

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionQualifier
				getCommerceShippingFixedOptionQualifier(
					HttpPrincipal httpPrincipal,
					long commerceShippingFixedOptionQualifierId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"getCommerceShippingFixedOptionQualifier",
				_getCommerceShippingFixedOptionQualifierParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionQualifierId);

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

			return (com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionQualifier)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionQualifier>
					getCommerceShippingFixedOptionQualifiers(
						HttpPrincipal httpPrincipal,
						long commerceShippingFixedOptionId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"getCommerceShippingFixedOptionQualifiers",
				_getCommerceShippingFixedOptionQualifiersParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionId);

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
				<com.liferay.commerce.shipping.engine.fixed.model.
					CommerceShippingFixedOptionQualifier>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionQualifier>
					getCommerceShippingFixedOptionQualifiers(
						HttpPrincipal httpPrincipal,
						long commerceShippingFixedOptionId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.shipping.engine.fixed.model.
								CommerceShippingFixedOptionQualifier>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"getCommerceShippingFixedOptionQualifiers",
				_getCommerceShippingFixedOptionQualifiersParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionId, start, end,
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
				<com.liferay.commerce.shipping.engine.fixed.model.
					CommerceShippingFixedOptionQualifier>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceShippingFixedOptionQualifiersCount(
			HttpPrincipal httpPrincipal, long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"getCommerceShippingFixedOptionQualifiersCount",
				_getCommerceShippingFixedOptionQualifiersCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionId);

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
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionQualifier>
					getCommerceTermEntryCommerceShippingFixedOptionQualifiers(
						HttpPrincipal httpPrincipal,
						long commerceShippingFixedOptionId, String keywords,
						int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"getCommerceTermEntryCommerceShippingFixedOptionQualifiers",
				_getCommerceTermEntryCommerceShippingFixedOptionQualifiersParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionId, keywords, start, end);

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
				<com.liferay.commerce.shipping.engine.fixed.model.
					CommerceShippingFixedOptionQualifier>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int
			getCommerceTermEntryCommerceShippingFixedOptionQualifiersCount(
				HttpPrincipal httpPrincipal, long commerceShippingFixedOptionId,
				String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionQualifierServiceUtil.class,
				"getCommerceTermEntryCommerceShippingFixedOptionQualifiersCount",
				_getCommerceTermEntryCommerceShippingFixedOptionQualifiersCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionId, keywords);

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
		CommerceShippingFixedOptionQualifierServiceHttp.class);

	private static final Class<?>[]
		_addCommerceShippingFixedOptionQualifierParameterTypes0 = new Class[] {
			String.class, long.class, long.class
		};
	private static final Class<?>[]
		_deleteCommerceShippingFixedOptionQualifierParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_deleteCommerceShippingFixedOptionQualifiersParameterTypes2 =
			new Class[] {long.class};
	private static final Class<?>[]
		_deleteCommerceShippingFixedOptionQualifiersParameterTypes3 =
			new Class[] {String.class, long.class};
	private static final Class<?>[]
		_fetchCommerceShippingFixedOptionQualifierParameterTypes4 =
			new Class[] {String.class, long.class, long.class};
	private static final Class<?>[]
		_getCommerceOrderTypeCommerceShippingFixedOptionQualifiersParameterTypes5 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceOrderTypeCommerceShippingFixedOptionQualifiersCountParameterTypes6 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCommerceShippingFixedOptionQualifierParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceShippingFixedOptionQualifiersParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceShippingFixedOptionQualifiersParameterTypes9 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceShippingFixedOptionQualifiersCountParameterTypes10 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceTermEntryCommerceShippingFixedOptionQualifiersParameterTypes11 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceTermEntryCommerceShippingFixedOptionQualifiersCountParameterTypes12 =
			new Class[] {long.class, String.class};

}
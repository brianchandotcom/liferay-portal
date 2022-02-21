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

package com.liferay.commerce.shipping.engine.fixed.service.http;

import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceShippingFixedOptionServiceUtil</code> service
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
public class CommerceShippingFixedOptionServiceHttp {

	public static
		com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOption addCommerceShippingFixedOption(
					HttpPrincipal httpPrincipal, long groupId,
					long commerceShippingMethodId, java.math.BigDecimal amount,
					java.util.Map<java.util.Locale, String> descriptionMap,
					java.util.Map<java.util.Locale, String> nameMap,
					double priority)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionServiceUtil.class,
				"addCommerceShippingFixedOption",
				_addCommerceShippingFixedOptionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceShippingMethodId, amount,
				descriptionMap, nameMap, priority);

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
				CommerceShippingFixedOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOption addCommerceShippingFixedOption(
					HttpPrincipal httpPrincipal, long commerceShippingMethodId,
					java.util.Map<java.util.Locale, String> nameMap,
					java.util.Map<java.util.Locale, String> descriptionMap,
					java.math.BigDecimal amount, double priority,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionServiceUtil.class,
				"addCommerceShippingFixedOption",
				_addCommerceShippingFixedOptionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId, nameMap, descriptionMap,
				amount, priority, serviceContext);

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
				CommerceShippingFixedOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceShippingFixedOption(
			HttpPrincipal httpPrincipal, long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionServiceUtil.class,
				"deleteCommerceShippingFixedOption",
				_deleteCommerceShippingFixedOptionParameterTypes2);

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

	public static
		com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOption fetchCommerceShippingFixedOption(
					HttpPrincipal httpPrincipal,
					long commerceShippingFixedOptionId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionServiceUtil.class,
				"fetchCommerceShippingFixedOption",
				_fetchCommerceShippingFixedOptionParameterTypes3);

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

			return (com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOption> getCommerceShippingFixedOptions(
					HttpPrincipal httpPrincipal, long commerceShippingMethodId,
					int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionServiceUtil.class,
				"getCommerceShippingFixedOptions",
				_getCommerceShippingFixedOptionsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId, start, end);

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
					CommerceShippingFixedOption>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOption> getCommerceShippingFixedOptions(
					HttpPrincipal httpPrincipal, long commerceShippingMethodId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.shipping.engine.fixed.model.
							CommerceShippingFixedOption> orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionServiceUtil.class,
				"getCommerceShippingFixedOptions",
				_getCommerceShippingFixedOptionsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId, start, end,
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
					CommerceShippingFixedOption>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOption> getCommerceShippingFixedOptions(
					HttpPrincipal httpPrincipal, long companyId, long groupId,
					long commerceShippingMethodId, String keywords, int start,
					int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionServiceUtil.class,
				"getCommerceShippingFixedOptions",
				_getCommerceShippingFixedOptionsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, commerceShippingMethodId,
				keywords, start, end);

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
					CommerceShippingFixedOption>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceShippingFixedOptionsCount(
			HttpPrincipal httpPrincipal, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionServiceUtil.class,
				"getCommerceShippingFixedOptionsCount",
				_getCommerceShippingFixedOptionsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId);

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

	public static long getCommerceShippingFixedOptionsCount(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			long commerceShippingMethodId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionServiceUtil.class,
				"getCommerceShippingFixedOptionsCount",
				_getCommerceShippingFixedOptionsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, commerceShippingMethodId,
				keywords);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOption updateCommerceShippingFixedOption(
					HttpPrincipal httpPrincipal,
					long commerceShippingFixedOptionId,
					java.math.BigDecimal amount,
					java.util.Map<java.util.Locale, String> descriptionMap,
					java.util.Map<java.util.Locale, String> nameMap,
					double priority)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionServiceUtil.class,
				"updateCommerceShippingFixedOption",
				_updateCommerceShippingFixedOptionParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionId, amount,
				descriptionMap, nameMap, priority);

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
				CommerceShippingFixedOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceShippingFixedOptionServiceHttp.class);

	private static final Class<?>[]
		_addCommerceShippingFixedOptionParameterTypes0 = new Class[] {
			long.class, long.class, java.math.BigDecimal.class,
			java.util.Map.class, java.util.Map.class, double.class
		};
	private static final Class<?>[]
		_addCommerceShippingFixedOptionParameterTypes1 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.math.BigDecimal.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCommerceShippingFixedOptionParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCommerceShippingFixedOptionParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceShippingFixedOptionsParameterTypes4 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceShippingFixedOptionsParameterTypes5 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceShippingFixedOptionsParameterTypes6 = new Class[] {
			long.class, long.class, long.class, String.class, int.class,
			int.class
		};
	private static final Class<?>[]
		_getCommerceShippingFixedOptionsCountParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceShippingFixedOptionsCountParameterTypes8 = new Class[] {
			long.class, long.class, long.class, String.class
		};
	private static final Class<?>[]
		_updateCommerceShippingFixedOptionParameterTypes9 = new Class[] {
			long.class, java.math.BigDecimal.class, java.util.Map.class,
			java.util.Map.class, double.class
		};

}
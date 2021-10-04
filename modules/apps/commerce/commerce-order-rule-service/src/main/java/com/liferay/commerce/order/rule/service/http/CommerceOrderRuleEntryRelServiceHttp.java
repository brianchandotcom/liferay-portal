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

package com.liferay.commerce.order.rule.service.http;

import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceOrderRuleEntryRelServiceUtil</code> service
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
 * @see CommerceOrderRuleEntryRelServiceSoap
 * @generated
 */
public class CommerceOrderRuleEntryRelServiceHttp {

	public static
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
				addCommerceOrderRuleEntryRel(
					HttpPrincipal httpPrincipal, String className, long classPK,
					long commerceOrderRuleEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"addCommerceOrderRuleEntryRel",
				_addCommerceOrderRuleEntryRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, commerceOrderRuleEntryId);

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

			return (com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceOrderRuleEntryRel(
			HttpPrincipal httpPrincipal, long commerceOrderRuleEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"deleteCommerceOrderRuleEntryRel",
				_deleteCommerceOrderRuleEntryRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryRelId);

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
			deleteCommerceOrderRuleEntryRelsByCommerceOrderRuleEntryId(
				HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"deleteCommerceOrderRuleEntryRelsByCommerceOrderRuleEntryId",
				_deleteCommerceOrderRuleEntryRelsByCommerceOrderRuleEntryIdParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId);

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
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
				fetchCommerceOrderRuleEntryRel(
					HttpPrincipal httpPrincipal, String className, long classPK,
					long commerceOrderRuleEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"fetchCommerceOrderRuleEntryRel",
				_fetchCommerceOrderRuleEntryRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, commerceOrderRuleEntryId);

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

			return (com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getAccountEntryCommerceOrderRuleEntryRels(
					HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId,
					String keywords, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getAccountEntryCommerceOrderRuleEntryRels",
				_getAccountEntryCommerceOrderRuleEntryRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId, keywords, start, end);

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
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getAccountEntryCommerceOrderRuleEntryRelsCount(
			HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getAccountEntryCommerceOrderRuleEntryRelsCount",
				_getAccountEntryCommerceOrderRuleEntryRelsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId, keywords);

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
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getAccountGroupCommerceOrderRuleEntryRels(
					HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId,
					String keywords, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getAccountGroupCommerceOrderRuleEntryRels",
				_getAccountGroupCommerceOrderRuleEntryRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId, keywords, start, end);

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
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getAccountGroupCommerceOrderRuleEntryRelsCount(
			HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getAccountGroupCommerceOrderRuleEntryRelsCount",
				_getAccountGroupCommerceOrderRuleEntryRelsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId, keywords);

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
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceChannelCommerceOrderRuleEntryRels(
					HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId,
					String keywords, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getCommerceChannelCommerceOrderRuleEntryRels",
				_getCommerceChannelCommerceOrderRuleEntryRelsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId, keywords, start, end);

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
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceChannelCommerceOrderRuleEntryRelsCount(
			HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getCommerceChannelCommerceOrderRuleEntryRelsCount",
				_getCommerceChannelCommerceOrderRuleEntryRelsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId, keywords);

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
		com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel
				getCommerceOrderRuleEntryRel(
					HttpPrincipal httpPrincipal,
					long commerceOrderRuleEntryRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getCommerceOrderRuleEntryRel",
				_getCommerceOrderRuleEntryRelParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryRelId);

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

			return (com.liferay.commerce.order.rule.model.
				CommerceOrderRuleEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryRels(
					HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getCommerceOrderRuleEntryRels",
				_getCommerceOrderRuleEntryRelsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId);

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
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderRuleEntryRels(
					HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.order.rule.model.
							CommerceOrderRuleEntryRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getCommerceOrderRuleEntryRels",
				_getCommerceOrderRuleEntryRelsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId, start, end,
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
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceOrderRuleEntryRelsCount(
			HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getCommerceOrderRuleEntryRelsCount",
				_getCommerceOrderRuleEntryRelsCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId);

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
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel>
				getCommerceOrderTypeCommerceOrderRuleEntryRels(
					HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId,
					String keywords, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getCommerceOrderTypeCommerceOrderRuleEntryRels",
				_getCommerceOrderTypeCommerceOrderRuleEntryRelsParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId, keywords, start, end);

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
				<com.liferay.commerce.order.rule.model.
					CommerceOrderRuleEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceOrderTypeCommerceOrderRuleEntryRelsCount(
			HttpPrincipal httpPrincipal, long commerceOrderRuleEntryId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderRuleEntryRelServiceUtil.class,
				"getCommerceOrderTypeCommerceOrderRuleEntryRelsCount",
				_getCommerceOrderTypeCommerceOrderRuleEntryRelsCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderRuleEntryId, keywords);

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
		CommerceOrderRuleEntryRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceOrderRuleEntryRelParameterTypes0 = new Class[] {
			String.class, long.class, long.class
		};
	private static final Class<?>[]
		_deleteCommerceOrderRuleEntryRelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommerceOrderRuleEntryRelsByCommerceOrderRuleEntryIdParameterTypes2 =
			new Class[] {long.class};
	private static final Class<?>[]
		_fetchCommerceOrderRuleEntryRelParameterTypes3 = new Class[] {
			String.class, long.class, long.class
		};
	private static final Class<?>[]
		_getAccountEntryCommerceOrderRuleEntryRelsParameterTypes4 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getAccountEntryCommerceOrderRuleEntryRelsCountParameterTypes5 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getAccountGroupCommerceOrderRuleEntryRelsParameterTypes6 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getAccountGroupCommerceOrderRuleEntryRelsCountParameterTypes7 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCommerceChannelCommerceOrderRuleEntryRelsParameterTypes8 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceChannelCommerceOrderRuleEntryRelsCountParameterTypes9 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCommerceOrderRuleEntryRelParameterTypes10 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceOrderRuleEntryRelsParameterTypes11 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceOrderRuleEntryRelsParameterTypes12 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceOrderRuleEntryRelsCountParameterTypes13 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceOrderTypeCommerceOrderRuleEntryRelsParameterTypes14 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceOrderTypeCommerceOrderRuleEntryRelsCountParameterTypes15 =
			new Class[] {long.class, String.class};

}
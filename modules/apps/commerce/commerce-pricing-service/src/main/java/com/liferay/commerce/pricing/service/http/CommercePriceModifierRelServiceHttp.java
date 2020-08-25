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

package com.liferay.commerce.pricing.service.http;

import com.liferay.commerce.pricing.service.CommercePriceModifierRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommercePriceModifierRelServiceUtil</code> service
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
 * @author Riccardo Alberti
 * @see CommercePriceModifierRelServiceSoap
 * @generated
 */
public class CommercePriceModifierRelServiceHttp {

	public static com.liferay.commerce.pricing.model.CommercePriceModifierRel
			addCommercePriceModifierRel(
				HttpPrincipal httpPrincipal, long commercePriceModifierId,
				String className, long classPK,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceModifierRelServiceUtil.class,
				"addCommercePriceModifierRel",
				_addCommercePriceModifierRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceModifierId, className, classPK,
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

			return (com.liferay.commerce.pricing.model.CommercePriceModifierRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommercePriceModifierRel(
			HttpPrincipal httpPrincipal, long commercePriceModifierRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceModifierRelServiceUtil.class,
				"deleteCommercePriceModifierRel",
				_deleteCommercePriceModifierRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceModifierRelId);

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

	public static com.liferay.commerce.pricing.model.CommercePriceModifierRel
			fetchCommercePriceModifierRel(
				HttpPrincipal httpPrincipal, long commercePriceModifierId,
				String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceModifierRelServiceUtil.class,
				"fetchCommercePriceModifierRel",
				_fetchCommercePriceModifierRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceModifierId, className, classPK);

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

			return (com.liferay.commerce.pricing.model.CommercePriceModifierRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long[] getClassPKs(
			HttpPrincipal httpPrincipal, long commercePriceModifierRelId,
			String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceModifierRelServiceUtil.class, "getClassPKs",
				_getClassPKsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceModifierRelId, className);

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.pricing.model.CommercePriceModifierRel
			getCommercePriceModifierRel(
				HttpPrincipal httpPrincipal, long commercePriceModifierRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceModifierRelServiceUtil.class,
				"getCommercePriceModifierRel",
				_getCommercePriceModifierRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceModifierRelId);

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

			return (com.liferay.commerce.pricing.model.CommercePriceModifierRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
				getCommercePriceModifierRels(
					HttpPrincipal httpPrincipal,
					long commercePriceModifierRelId, String className)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceModifierRelServiceUtil.class,
				"getCommercePriceModifierRels",
				_getCommercePriceModifierRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceModifierRelId, className);

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
				<com.liferay.commerce.pricing.model.CommercePriceModifierRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePriceModifierRel>
				getCommercePriceModifierRels(
					HttpPrincipal httpPrincipal,
					long commercePriceModifierRelId, String className,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.pricing.model.
							CommercePriceModifierRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceModifierRelServiceUtil.class,
				"getCommercePriceModifierRels",
				_getCommercePriceModifierRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceModifierRelId, className, start, end,
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
				<com.liferay.commerce.pricing.model.CommercePriceModifierRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePriceModifierRelsCount(
			HttpPrincipal httpPrincipal, long commercePriceModifierRelId,
			String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceModifierRelServiceUtil.class,
				"getCommercePriceModifierRelsCount",
				_getCommercePriceModifierRelsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceModifierRelId, className);

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
		CommercePriceModifierRelServiceHttp.class);

	private static final Class<?>[]
		_addCommercePriceModifierRelParameterTypes0 = new Class[] {
			long.class, String.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCommercePriceModifierRelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCommercePriceModifierRelParameterTypes2 = new Class[] {
			long.class, String.class, long.class
		};
	private static final Class<?>[] _getClassPKsParameterTypes3 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[]
		_getCommercePriceModifierRelParameterTypes4 = new Class[] {long.class};
	private static final Class<?>[]
		_getCommercePriceModifierRelsParameterTypes5 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getCommercePriceModifierRelsParameterTypes6 = new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommercePriceModifierRelsCountParameterTypes7 = new Class[] {
			long.class, String.class
		};

}
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

package com.liferay.commerce.account.service.http;

import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceAccountGroupCommerceAccountRelServiceUtil</code> service
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
 * @generated
 */
public class CommerceAccountGroupCommerceAccountRelServiceHttp {

	public static
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					addCommerceAccountGroupCommerceAccountRel(
						HttpPrincipal httpPrincipal,
						long commerceAccountGroupId, long commerceAccountId,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountGroupCommerceAccountRelServiceUtil.class,
				"addCommerceAccountGroupCommerceAccountRel",
				_addCommerceAccountGroupCommerceAccountRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountGroupId, commerceAccountId,
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

			return (com.liferay.commerce.account.model.
				CommerceAccountGroupCommerceAccountRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceAccountGroupCommerceAccountRel(
			HttpPrincipal httpPrincipal,
			long commerceAccountGroupCommerceAccountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountGroupCommerceAccountRelServiceUtil.class,
				"deleteCommerceAccountGroupCommerceAccountRel",
				_deleteCommerceAccountGroupCommerceAccountRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountGroupCommerceAccountRelId);

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
		com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel
					getCommerceAccountGroupCommerceAccountRel(
						HttpPrincipal httpPrincipal,
						long commerceAccountGroupId, long commerceAccountId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountGroupCommerceAccountRelServiceUtil.class,
				"getCommerceAccountGroupCommerceAccountRel",
				_getCommerceAccountGroupCommerceAccountRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountGroupId, commerceAccountId);

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

			return (com.liferay.commerce.account.model.
				CommerceAccountGroupCommerceAccountRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.account.model.
			CommerceAccountGroupCommerceAccountRel>
					getCommerceAccountGroupCommerceAccountRels(
						HttpPrincipal httpPrincipal,
						long commerceAccountGroupId, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountGroupCommerceAccountRelServiceUtil.class,
				"getCommerceAccountGroupCommerceAccountRels",
				_getCommerceAccountGroupCommerceAccountRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountGroupId, start, end);

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
				<com.liferay.commerce.account.model.
					CommerceAccountGroupCommerceAccountRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceAccountGroupCommerceAccountRelsCount(
			HttpPrincipal httpPrincipal, long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountGroupCommerceAccountRelServiceUtil.class,
				"getCommerceAccountGroupCommerceAccountRelsCount",
				_getCommerceAccountGroupCommerceAccountRelsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountGroupId);

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
		CommerceAccountGroupCommerceAccountRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceAccountGroupCommerceAccountRelParameterTypes0 =
			new Class[] {
				long.class, long.class,
				com.liferay.portal.kernel.service.ServiceContext.class
			};
	private static final Class<?>[]
		_deleteCommerceAccountGroupCommerceAccountRelParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceAccountGroupCommerceAccountRelParameterTypes2 =
			new Class[] {long.class, long.class};
	private static final Class<?>[]
		_getCommerceAccountGroupCommerceAccountRelsParameterTypes3 =
			new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceAccountGroupCommerceAccountRelsCountParameterTypes4 =
			new Class[] {long.class};

}
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

package com.liferay.commerce.service.http;

import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceShippingOptionAccountEntryRelServiceUtil</code> service
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
public class CommerceShippingOptionAccountEntryRelServiceHttp {

	public static
		com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel
				addCommerceShippingOptionAccountEntryRel(
					HttpPrincipal httpPrincipal, long accountEntryId,
					long commerceChannelId, String commerceShippingMethodKey,
					String commerceShippingOptionKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingOptionAccountEntryRelServiceUtil.class,
				"addCommerceShippingOptionAccountEntryRel",
				_addCommerceShippingOptionAccountEntryRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, commerceChannelId,
				commerceShippingMethodKey, commerceShippingOptionKey);

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

			return (com.liferay.commerce.model.
				CommerceShippingOptionAccountEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceShippingOptionAccountEntryRel(
			HttpPrincipal httpPrincipal,
			long commerceShippingOptionAccountEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingOptionAccountEntryRelServiceUtil.class,
				"deleteCommerceShippingOptionAccountEntryRel",
				_deleteCommerceShippingOptionAccountEntryRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingOptionAccountEntryRelId);

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
		com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel
				fetchCommerceShippingOptionAccountEntryRel(
					HttpPrincipal httpPrincipal, long accountEntryId,
					long commerceChannelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingOptionAccountEntryRelServiceUtil.class,
				"fetchCommerceShippingOptionAccountEntryRel",
				_fetchCommerceShippingOptionAccountEntryRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, commerceChannelId);

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

			return (com.liferay.commerce.model.
				CommerceShippingOptionAccountEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel
				updateCommerceShippingOptionAccountEntryRel(
					HttpPrincipal httpPrincipal,
					long commerceShippingOptionAccountEntryRelId,
					String commerceShippingMethodKey,
					String commerceShippingOptionKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingOptionAccountEntryRelServiceUtil.class,
				"updateCommerceShippingOptionAccountEntryRel",
				_updateCommerceShippingOptionAccountEntryRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingOptionAccountEntryRelId,
				commerceShippingMethodKey, commerceShippingOptionKey);

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

			return (com.liferay.commerce.model.
				CommerceShippingOptionAccountEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceShippingOptionAccountEntryRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceShippingOptionAccountEntryRelParameterTypes0 = new Class[] {
			long.class, long.class, String.class, String.class
		};
	private static final Class<?>[]
		_deleteCommerceShippingOptionAccountEntryRelParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_fetchCommerceShippingOptionAccountEntryRelParameterTypes2 =
			new Class[] {long.class, long.class};
	private static final Class<?>[]
		_updateCommerceShippingOptionAccountEntryRelParameterTypes3 =
			new Class[] {long.class, String.class, String.class};

}
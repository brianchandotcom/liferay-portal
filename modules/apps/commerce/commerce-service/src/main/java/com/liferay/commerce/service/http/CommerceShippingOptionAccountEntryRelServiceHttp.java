/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				getCommerceShippingOptionAccountEntryRel(
					HttpPrincipal httpPrincipal,
					long commerceShippingOptionAccountEntryRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingOptionAccountEntryRelServiceUtil.class,
				"getCommerceShippingOptionAccountEntryRel",
				_getCommerceShippingOptionAccountEntryRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingOptionAccountEntryRelId);

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

	public static java.util.List
		<com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel>
				getCommerceShippingOptionAccountEntryRels(
					HttpPrincipal httpPrincipal, long accountEntryId)
			throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingOptionAccountEntryRelServiceUtil.class,
				"getCommerceShippingOptionAccountEntryRels",
				_getCommerceShippingOptionAccountEntryRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof Exception) {
					throw (Exception)exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.model.
					CommerceShippingOptionAccountEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceShippingOptionAccountEntryRelsCount(
			HttpPrincipal httpPrincipal, long accountEntryId)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingOptionAccountEntryRelServiceUtil.class,
				"getCommerceShippingOptionAccountEntryRelsCount",
				_getCommerceShippingOptionAccountEntryRelsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof Exception) {
					throw (Exception)exception;
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
				_updateCommerceShippingOptionAccountEntryRelParameterTypes6);

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
		_getCommerceShippingOptionAccountEntryRelParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceShippingOptionAccountEntryRelsParameterTypes4 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceShippingOptionAccountEntryRelsCountParameterTypes5 =
			new Class[] {long.class};
	private static final Class<?>[]
		_updateCommerceShippingOptionAccountEntryRelParameterTypes6 =
			new Class[] {long.class, String.class, String.class};

}
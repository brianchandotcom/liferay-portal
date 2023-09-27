/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceChannelAccountEntryRelServiceUtil</code> service
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
public class CommerceChannelAccountEntryRelServiceHttp {

	public static
		com.liferay.commerce.product.model.CommerceChannelAccountEntryRel
				addCommerceChannelAccountEntryRel(
					HttpPrincipal httpPrincipal, long accountEntryId,
					String className, long classPK, long commerceChannelId,
					boolean overrideEligibility, double priority, int type)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceChannelAccountEntryRelServiceUtil.class,
				"addCommerceChannelAccountEntryRel",
				_addCommerceChannelAccountEntryRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, className, classPK,
				commerceChannelId, overrideEligibility, priority, type);

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

			return (com.liferay.commerce.product.model.
				CommerceChannelAccountEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceChannelAccountEntryRel(
			HttpPrincipal httpPrincipal, long commerceChannelAccountEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceChannelAccountEntryRelServiceUtil.class,
				"deleteCommerceChannelAccountEntryRel",
				_deleteCommerceChannelAccountEntryRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceChannelAccountEntryRelId);

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
		com.liferay.commerce.product.model.CommerceChannelAccountEntryRel
				fetchCommerceChannelAccountEntryRel(
					HttpPrincipal httpPrincipal,
					long commerceChannelAccountEntryRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceChannelAccountEntryRelServiceUtil.class,
				"fetchCommerceChannelAccountEntryRel",
				_fetchCommerceChannelAccountEntryRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceChannelAccountEntryRelId);

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

			return (com.liferay.commerce.product.model.
				CommerceChannelAccountEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.model.CommerceChannelAccountEntryRel
				fetchCommerceChannelAccountEntryRel(
					HttpPrincipal httpPrincipal, long accountEntryId,
					long commerceChannelId, int type)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceChannelAccountEntryRelServiceUtil.class,
				"fetchCommerceChannelAccountEntryRel",
				_fetchCommerceChannelAccountEntryRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, commerceChannelId, type);

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

			return (com.liferay.commerce.product.model.
				CommerceChannelAccountEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.model.CommerceChannelAccountEntryRel
				getCommerceChannelAccountEntryRel(
					HttpPrincipal httpPrincipal,
					long commerceChannelAccountEntryRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceChannelAccountEntryRelServiceUtil.class,
				"getCommerceChannelAccountEntryRel",
				_getCommerceChannelAccountEntryRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceChannelAccountEntryRelId);

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

			return (com.liferay.commerce.product.model.
				CommerceChannelAccountEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannelAccountEntryRel>
				getCommerceChannelAccountEntryRels(
					HttpPrincipal httpPrincipal, long accountEntryId, int type,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.model.
							CommerceChannelAccountEntryRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceChannelAccountEntryRelServiceUtil.class,
				"getCommerceChannelAccountEntryRels",
				_getCommerceChannelAccountEntryRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, type, start, end, orderByComparator);

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
				<com.liferay.commerce.product.model.
					CommerceChannelAccountEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannelAccountEntryRel>
			getCommerceChannelAccountEntryRels(
				HttpPrincipal httpPrincipal, String className, long classPK,
				long commerceChannelId, int type) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceChannelAccountEntryRelServiceUtil.class,
				"getCommerceChannelAccountEntryRels",
				_getCommerceChannelAccountEntryRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, commerceChannelId, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.product.model.
					CommerceChannelAccountEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceChannelAccountEntryRelsCount(
			HttpPrincipal httpPrincipal, long accountEntryId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceChannelAccountEntryRelServiceUtil.class,
				"getCommerceChannelAccountEntryRelsCount",
				_getCommerceChannelAccountEntryRelsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, type);

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
		com.liferay.commerce.product.model.CommerceChannelAccountEntryRel
				updateCommerceChannelAccountEntryRel(
					HttpPrincipal httpPrincipal,
					long commerceChannelAccountEntryRelId,
					long commerceChannelId, long classPK,
					boolean overrideEligibility, double priority)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceChannelAccountEntryRelServiceUtil.class,
				"updateCommerceChannelAccountEntryRel",
				_updateCommerceChannelAccountEntryRelParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceChannelAccountEntryRelId, commerceChannelId,
				classPK, overrideEligibility, priority);

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

			return (com.liferay.commerce.product.model.
				CommerceChannelAccountEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceChannelAccountEntryRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceChannelAccountEntryRelParameterTypes0 = new Class[] {
			long.class, String.class, long.class, long.class, boolean.class,
			double.class, int.class
		};
	private static final Class<?>[]
		_deleteCommerceChannelAccountEntryRelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCommerceChannelAccountEntryRelParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCommerceChannelAccountEntryRelParameterTypes3 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[]
		_getCommerceChannelAccountEntryRelParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceChannelAccountEntryRelsParameterTypes5 = new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceChannelAccountEntryRelsParameterTypes6 = new Class[] {
			String.class, long.class, long.class, int.class
		};
	private static final Class<?>[]
		_getCommerceChannelAccountEntryRelsCountParameterTypes7 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[]
		_updateCommerceChannelAccountEntryRelParameterTypes8 = new Class[] {
			long.class, long.class, long.class, boolean.class, double.class
		};

}
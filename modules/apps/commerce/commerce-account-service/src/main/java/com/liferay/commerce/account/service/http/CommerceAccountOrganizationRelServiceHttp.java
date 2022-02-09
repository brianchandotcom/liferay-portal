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

import com.liferay.commerce.account.service.CommerceAccountOrganizationRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceAccountOrganizationRelServiceUtil</code> service
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
public class CommerceAccountOrganizationRelServiceHttp {

	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				addCommerceAccountOrganizationRel(
					HttpPrincipal httpPrincipal, long commerceAccountId,
					long organizationId,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"addCommerceAccountOrganizationRel",
				_addCommerceAccountOrganizationRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, organizationId, serviceContext);

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
				CommerceAccountOrganizationRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void addCommerceAccountOrganizationRels(
			HttpPrincipal httpPrincipal, long commerceAccountId,
			long[] organizationIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"addCommerceAccountOrganizationRels",
				_addCommerceAccountOrganizationRelsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, organizationIds, serviceContext);

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

	public static void deleteCommerceAccountOrganizationRel(
			HttpPrincipal httpPrincipal, long commerceAccountId,
			long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"deleteCommerceAccountOrganizationRel",
				_deleteCommerceAccountOrganizationRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, organizationId);

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

	public static void deleteCommerceAccountOrganizationRels(
			HttpPrincipal httpPrincipal, long commerceAccountId,
			long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"deleteCommerceAccountOrganizationRels",
				_deleteCommerceAccountOrganizationRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, organizationIds);

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
		com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				fetchCommerceAccountOrganizationRel(
					HttpPrincipal httpPrincipal,
					com.liferay.commerce.account.service.persistence.
						CommerceAccountOrganizationRelPK
							commerceAccountOrganizationRelPK)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"fetchCommerceAccountOrganizationRel",
				_fetchCommerceAccountOrganizationRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountOrganizationRelPK);

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
				CommerceAccountOrganizationRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				getCommerceAccountOrganizationRel(
					HttpPrincipal httpPrincipal,
					com.liferay.commerce.account.service.persistence.
						CommerceAccountOrganizationRelPK
							commerceAccountOrganizationRelPK)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"getCommerceAccountOrganizationRel",
				_getCommerceAccountOrganizationRelParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountOrganizationRelPK);

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
				CommerceAccountOrganizationRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
				getCommerceAccountOrganizationRels(
					HttpPrincipal httpPrincipal, long commerceAccountId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"getCommerceAccountOrganizationRels",
				_getCommerceAccountOrganizationRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId);

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
					CommerceAccountOrganizationRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
				getCommerceAccountOrganizationRels(
					HttpPrincipal httpPrincipal, long commerceAccountId,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"getCommerceAccountOrganizationRels",
				_getCommerceAccountOrganizationRelsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, start, end);

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
					CommerceAccountOrganizationRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountOrganizationRel>
				getCommerceAccountOrganizationRelsByOrganizationId(
					HttpPrincipal httpPrincipal, long organizationId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"getCommerceAccountOrganizationRelsByOrganizationId",
				_getCommerceAccountOrganizationRelsByOrganizationIdParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId, start, end);

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
					CommerceAccountOrganizationRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceAccountOrganizationRelsByOrganizationIdCount(
			HttpPrincipal httpPrincipal, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"getCommerceAccountOrganizationRelsByOrganizationIdCount",
				_getCommerceAccountOrganizationRelsByOrganizationIdCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId);

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

	public static int getCommerceAccountOrganizationRelsCount(
			HttpPrincipal httpPrincipal, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountOrganizationRelServiceUtil.class,
				"getCommerceAccountOrganizationRelsCount",
				_getCommerceAccountOrganizationRelsCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId);

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
		CommerceAccountOrganizationRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceAccountOrganizationRelParameterTypes0 = new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_addCommerceAccountOrganizationRelsParameterTypes1 = new Class[] {
			long.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCommerceAccountOrganizationRelParameterTypes2 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_deleteCommerceAccountOrganizationRelsParameterTypes3 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[]
		_fetchCommerceAccountOrganizationRelParameterTypes4 = new Class[] {
			com.liferay.commerce.account.service.persistence.
				CommerceAccountOrganizationRelPK.class
		};
	private static final Class<?>[]
		_getCommerceAccountOrganizationRelParameterTypes5 = new Class[] {
			com.liferay.commerce.account.service.persistence.
				CommerceAccountOrganizationRelPK.class
		};
	private static final Class<?>[]
		_getCommerceAccountOrganizationRelsParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceAccountOrganizationRelsParameterTypes7 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceAccountOrganizationRelsByOrganizationIdParameterTypes8 =
			new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceAccountOrganizationRelsByOrganizationIdCountParameterTypes9 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceAccountOrganizationRelsCountParameterTypes10 = new Class[] {
			long.class
		};

}
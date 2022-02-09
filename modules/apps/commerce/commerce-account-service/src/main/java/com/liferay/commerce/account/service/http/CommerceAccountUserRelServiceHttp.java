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

import com.liferay.commerce.account.service.CommerceAccountUserRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceAccountUserRelServiceUtil</code> service
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
public class CommerceAccountUserRelServiceHttp {

	public static com.liferay.commerce.account.model.CommerceAccountUserRel
			addCommerceAccountUserRel(
				HttpPrincipal httpPrincipal, long commerceAccountId,
				long commerceAccountUserId, long[] roleIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountUserRelServiceUtil.class,
				"addCommerceAccountUserRel",
				_addCommerceAccountUserRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, commerceAccountUserId, roleIds,
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

			return (com.liferay.commerce.account.model.CommerceAccountUserRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void addCommerceAccountUserRels(
			HttpPrincipal httpPrincipal, long commerceAccountId, long[] userIds,
			String[] emailAddresses, long[] roleIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountUserRelServiceUtil.class,
				"addCommerceAccountUserRels",
				_addCommerceAccountUserRelsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, userIds, emailAddresses, roleIds,
				serviceContext);

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

	public static void deleteCommerceAccountUserRel(
			HttpPrincipal httpPrincipal, long commerceAccountId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountUserRelServiceUtil.class,
				"deleteCommerceAccountUserRel",
				_deleteCommerceAccountUserRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, userId);

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

	public static void deleteCommerceAccountUserRels(
			HttpPrincipal httpPrincipal, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountUserRelServiceUtil.class,
				"deleteCommerceAccountUserRels",
				_deleteCommerceAccountUserRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId);

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

	public static void deleteCommerceAccountUserRels(
			HttpPrincipal httpPrincipal, long commerceAccountId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountUserRelServiceUtil.class,
				"deleteCommerceAccountUserRels",
				_deleteCommerceAccountUserRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, userIds);

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

	public static com.liferay.commerce.account.model.CommerceAccountUserRel
			fetchCommerceAccountUserRel(
				HttpPrincipal httpPrincipal,
				com.liferay.commerce.account.service.persistence.
					CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountUserRelServiceUtil.class,
				"fetchCommerceAccountUserRel",
				_fetchCommerceAccountUserRelParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountUserRelPK);

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

			return (com.liferay.commerce.account.model.CommerceAccountUserRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountUserRel
			getCommerceAccountUserRel(
				HttpPrincipal httpPrincipal,
				com.liferay.commerce.account.service.persistence.
					CommerceAccountUserRelPK commerceAccountUserRelPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountUserRelServiceUtil.class,
				"getCommerceAccountUserRel",
				_getCommerceAccountUserRelParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountUserRelPK);

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

			return (com.liferay.commerce.account.model.CommerceAccountUserRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccountUserRel>
				getCommerceAccountUserRels(
					HttpPrincipal httpPrincipal, long commerceAccountId,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountUserRelServiceUtil.class,
				"getCommerceAccountUserRels",
				_getCommerceAccountUserRelsParameterTypes7);

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
				<com.liferay.commerce.account.model.CommerceAccountUserRel>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceAccountUserRelsCount(
			HttpPrincipal httpPrincipal, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountUserRelServiceUtil.class,
				"getCommerceAccountUserRelsCount",
				_getCommerceAccountUserRelsCountParameterTypes8);

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

	public static com.liferay.commerce.account.model.CommerceAccountUserRel
			inviteUser(
				HttpPrincipal httpPrincipal, long commerceAccountId,
				String emailAddress, long[] roleIds,
				String userExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountUserRelServiceUtil.class, "inviteUser",
				_inviteUserParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, emailAddress, roleIds,
				userExternalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.account.model.CommerceAccountUserRel)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceAccountUserRelServiceHttp.class);

	private static final Class<?>[] _addCommerceAccountUserRelParameterTypes0 =
		new Class[] {
			long.class, long.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommerceAccountUserRelsParameterTypes1 =
		new Class[] {
			long.class, long[].class, String[].class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCommerceAccountUserRelParameterTypes2 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_deleteCommerceAccountUserRelsParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommerceAccountUserRelsParameterTypes4 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[]
		_fetchCommerceAccountUserRelParameterTypes5 = new Class[] {
			com.liferay.commerce.account.service.persistence.
				CommerceAccountUserRelPK.class
		};
	private static final Class<?>[] _getCommerceAccountUserRelParameterTypes6 =
		new Class[] {
			com.liferay.commerce.account.service.persistence.
				CommerceAccountUserRelPK.class
		};
	private static final Class<?>[] _getCommerceAccountUserRelsParameterTypes7 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceAccountUserRelsCountParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[] _inviteUserParameterTypes9 = new Class[] {
		long.class, String.class, long[].class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};

}
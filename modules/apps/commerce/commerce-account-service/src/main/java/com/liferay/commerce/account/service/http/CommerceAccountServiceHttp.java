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

import com.liferay.commerce.account.service.CommerceAccountServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceAccountServiceUtil</code> service
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
 * @see CommerceAccountServiceSoap
 * @generated
 */
public class CommerceAccountServiceHttp {

	public static com.liferay.commerce.account.model.CommerceAccount
			addBusinessCommerceAccount(
				HttpPrincipal httpPrincipal, String name,
				long parentCommerceAccountId, String email, String taxId,
				boolean active, String externalReferenceCode, long[] userIds,
				String[] emailAddresses,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "addBusinessCommerceAccount",
				_addBusinessCommerceAccountParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, name, parentCommerceAccountId, email, taxId, active,
				externalReferenceCode, userIds, emailAddresses, serviceContext);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			addCommerceAccount(
				HttpPrincipal httpPrincipal, String name,
				long parentCommerceAccountId, String email, String taxId,
				int type, boolean active, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "addCommerceAccount",
				_addCommerceAccountParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, name, parentCommerceAccountId, email, taxId, type,
				active, externalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceAccount(
			HttpPrincipal httpPrincipal, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "deleteCommerceAccount",
				_deleteCommerceAccountParameterTypes2);

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

	public static com.liferay.commerce.account.model.CommerceAccount
			fetchByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long companyId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class,
				"fetchByExternalReferenceCode",
				_fetchByExternalReferenceCodeParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, externalReferenceCode);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			fetchCommerceAccount(
				HttpPrincipal httpPrincipal, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "fetchCommerceAccount",
				_fetchCommerceAccountParameterTypes4);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			getCommerceAccount(
				HttpPrincipal httpPrincipal, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "getCommerceAccount",
				_getCommerceAccountParameterTypes5);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			getPersonalCommerceAccount(HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "getPersonalCommerceAccount",
				_getPersonalCommerceAccountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccount>
				getUserCommerceAccounts(
					HttpPrincipal httpPrincipal, long userId,
					long parentCommerceAccountId, int commerceSiteType,
					String keywords, Boolean active, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "getUserCommerceAccounts",
				_getUserCommerceAccountsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, parentCommerceAccountId, commerceSiteType,
				keywords, active, start, end);

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
				<com.liferay.commerce.account.model.CommerceAccount>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.account.model.CommerceAccount>
				getUserCommerceAccounts(
					HttpPrincipal httpPrincipal, long userId,
					long parentCommerceAccountId, int commerceSiteType,
					String keywords, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "getUserCommerceAccounts",
				_getUserCommerceAccountsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, parentCommerceAccountId, commerceSiteType,
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
				<com.liferay.commerce.account.model.CommerceAccount>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getUserCommerceAccountsCount(
			HttpPrincipal httpPrincipal, long userId,
			long parentCommerceAccountId, int commerceSiteType, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class,
				"getUserCommerceAccountsCount",
				_getUserCommerceAccountsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, parentCommerceAccountId, commerceSiteType,
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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getUserCommerceAccountsCount(
			HttpPrincipal httpPrincipal, long userId,
			long parentCommerceAccountId, int commerceSiteType, String keywords,
			Boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class,
				"getUserCommerceAccountsCount",
				_getUserCommerceAccountsCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, parentCommerceAccountId, commerceSiteType,
				keywords, active);

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

	public static com.liferay.commerce.account.model.CommerceAccount setActive(
			HttpPrincipal httpPrincipal, long commerceAccountId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "setActive",
				_setActiveParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, active);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				HttpPrincipal httpPrincipal, long commerceAccountId,
				String name, boolean logo, byte[] logoBytes, String email,
				String taxId, boolean active, long defaultBillingAddressId,
				long defaultShippingAddressId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "updateCommerceAccount",
				_updateCommerceAccountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, name, logo, logoBytes, email,
				taxId, active, defaultBillingAddressId,
				defaultShippingAddressId, serviceContext);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				HttpPrincipal httpPrincipal, long commerceAccountId,
				String name, boolean logo, byte[] logoBytes, String email,
				String taxId, boolean active, long defaultBillingAddressId,
				long defaultShippingAddressId, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "updateCommerceAccount",
				_updateCommerceAccountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, name, logo, logoBytes, email,
				taxId, active, defaultBillingAddressId,
				defaultShippingAddressId, externalReferenceCode,
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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateCommerceAccount(
				HttpPrincipal httpPrincipal, long commerceAccountId,
				String name, boolean logo, byte[] logoBytes, String email,
				String taxId, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "updateCommerceAccount",
				_updateCommerceAccountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, name, logo, logoBytes, email,
				taxId, active, serviceContext);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateDefaultBillingAddress(
				HttpPrincipal httpPrincipal, long commerceAccountId,
				long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "updateDefaultBillingAddress",
				_updateDefaultBillingAddressParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, commerceAddressId);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			updateDefaultShippingAddress(
				HttpPrincipal httpPrincipal, long commerceAccountId,
				long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class,
				"updateDefaultShippingAddress",
				_updateDefaultShippingAddressParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, commerceAddressId);

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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccount
			upsertCommerceAccount(
				HttpPrincipal httpPrincipal, String name,
				long parentCommerceAccountId, boolean logo, byte[] logoBytes,
				String email, String taxId, int type, boolean active,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceAccountServiceUtil.class, "upsertCommerceAccount",
				_upsertCommerceAccountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, name, parentCommerceAccountId, logo, logoBytes,
				email, taxId, type, active, externalReferenceCode,
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

			return (com.liferay.commerce.account.model.CommerceAccount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceAccountServiceHttp.class);

	private static final Class<?>[] _addBusinessCommerceAccountParameterTypes0 =
		new Class[] {
			String.class, long.class, String.class, String.class, boolean.class,
			String.class, long[].class, String[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommerceAccountParameterTypes1 =
		new Class[] {
			String.class, long.class, String.class, String.class, int.class,
			boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceAccountParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchByExternalReferenceCodeParameterTypes3 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _fetchCommerceAccountParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceAccountParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _getPersonalCommerceAccountParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _getUserCommerceAccountsParameterTypes7 =
		new Class[] {
			long.class, long.class, int.class, String.class, Boolean.class,
			int.class, int.class
		};
	private static final Class<?>[] _getUserCommerceAccountsParameterTypes8 =
		new Class[] {
			long.class, long.class, int.class, String.class, int.class,
			int.class
		};
	private static final Class<?>[]
		_getUserCommerceAccountsCountParameterTypes9 = new Class[] {
			long.class, long.class, int.class, String.class
		};
	private static final Class<?>[]
		_getUserCommerceAccountsCountParameterTypes10 = new Class[] {
			long.class, long.class, int.class, String.class, Boolean.class
		};
	private static final Class<?>[] _setActiveParameterTypes11 = new Class[] {
		long.class, boolean.class
	};
	private static final Class<?>[] _updateCommerceAccountParameterTypes12 =
		new Class[] {
			long.class, String.class, boolean.class, byte[].class, String.class,
			String.class, boolean.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCommerceAccountParameterTypes13 =
		new Class[] {
			long.class, String.class, boolean.class, byte[].class, String.class,
			String.class, boolean.class, long.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCommerceAccountParameterTypes14 =
		new Class[] {
			long.class, String.class, boolean.class, byte[].class, String.class,
			String.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateDefaultBillingAddressParameterTypes15 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_updateDefaultShippingAddressParameterTypes16 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _upsertCommerceAccountParameterTypes17 =
		new Class[] {
			String.class, long.class, boolean.class, byte[].class, String.class,
			String.class, int.class, boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
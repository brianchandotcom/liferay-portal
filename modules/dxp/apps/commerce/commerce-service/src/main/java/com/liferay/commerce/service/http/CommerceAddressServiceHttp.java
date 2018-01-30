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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceAddressServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceAddressServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @see CommerceAddressServiceSoap
 * @see HttpPrincipal
 * @see CommerceAddressServiceUtil
 * @generated
 */
@ProviderType
public class CommerceAddressServiceHttp {
	public static com.liferay.commerce.model.CommerceAddress addCommerceAddress(
		HttpPrincipal httpPrincipal, java.lang.String className, long classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId,
		java.lang.String phoneNumber, boolean defaultBilling,
		boolean defaultShipping,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressServiceUtil.class,
					"addCommerceAddress", _addCommerceAddressParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, name, description, street1, street2,
					street3, city, zip, commerceRegionId, commerceCountryId,
					phoneNumber, defaultBilling, defaultShipping, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceAddress)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceAddress(HttpPrincipal httpPrincipal,
		long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressServiceUtil.class,
					"deleteCommerceAddress",
					_deleteCommerceAddressParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAddressId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceAddress fetchCommerceAddress(
		HttpPrincipal httpPrincipal, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressServiceUtil.class,
					"fetchCommerceAddress", _fetchCommerceAddressParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAddressId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceAddress)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceAddress getCommerceAddress(
		HttpPrincipal httpPrincipal, long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressServiceUtil.class,
					"getCommerceAddress", _getCommerceAddressParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAddressId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceAddress)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceAddress> getCommerceAddresses(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressServiceUtil.class,
					"getCommerceAddresses", _getCommerceAddressesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					className, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceAddress>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceAddress> searchCommerceAddresses(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.lang.String className, long classPK, java.lang.String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressServiceUtil.class,
					"searchCommerceAddresses",
					_searchCommerceAddressesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, className, classPK, keywords, start,
					end, sort);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceAddress>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceAddress> getCommerceAddresses(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String className,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAddress> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressServiceUtil.class,
					"getCommerceAddresses", _getCommerceAddressesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					className, classPK, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceAddress>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceAddressesCount(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressServiceUtil.class,
					"getCommerceAddressesCount",
					_getCommerceAddressesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					className, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceAddress updateCommerceAddress(
		HttpPrincipal httpPrincipal, long commerceAddressId,
		java.lang.String name, java.lang.String description,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId,
		java.lang.String phoneNumber, boolean defaultBilling,
		boolean defaultShipping,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressServiceUtil.class,
					"updateCommerceAddress",
					_updateCommerceAddressParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAddressId, name, description, street1, street2,
					street3, city, zip, commerceRegionId, commerceCountryId,
					phoneNumber, defaultBilling, defaultShipping, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceAddress)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceAddressServiceHttp.class);
	private static final Class<?>[] _addCommerceAddressParameterTypes0 = new Class[] {
			java.lang.String.class, long.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, long.class,
			long.class, java.lang.String.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceAddressParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceAddressParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceAddressParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceAddressesParameterTypes4 = new Class[] {
			long.class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _searchCommerceAddressesParameterTypes5 = new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _getCommerceAddressesParameterTypes6 = new Class[] {
			long.class, java.lang.String.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceAddressesCountParameterTypes7 = new Class[] {
			long.class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _updateCommerceAddressParameterTypes8 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, long.class, long.class,
			java.lang.String.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
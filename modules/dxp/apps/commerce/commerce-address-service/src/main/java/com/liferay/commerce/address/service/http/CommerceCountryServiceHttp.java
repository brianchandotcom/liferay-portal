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

package com.liferay.commerce.address.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.address.service.CommerceCountryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceCountryServiceUtil} service utility. The
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
 * @see CommerceCountryServiceSoap
 * @see HttpPrincipal
 * @see CommerceCountryServiceUtil
 * @generated
 */
@ProviderType
public class CommerceCountryServiceHttp {
	public static com.liferay.commerce.address.model.CommerceCountry addCommerceCountry(
		HttpPrincipal httpPrincipal, java.lang.String name,
		boolean allowsBilling, boolean allowsShipping,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean published,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"addCommerceCountry", _addCommerceCountryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, name,
					allowsBilling, allowsShipping, twoLettersISOCode,
					threeLettersISOCode, numericISOCode, subjectToVAT,
					priority, published, serviceContext);

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

			return (com.liferay.commerce.address.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.address.model.CommerceCountry deleteCommerceCountry(
		HttpPrincipal httpPrincipal, long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"deleteCommerceCountry",
					_deleteCommerceCountryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId);

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

			return (com.liferay.commerce.address.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.address.model.CommerceCountry fetchCommerceCountry(
		HttpPrincipal httpPrincipal, long commerceCountryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"fetchCommerceCountry", _fetchCommerceCountryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.address.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.address.model.CommerceCountry> getCommerceCountries(
		HttpPrincipal httpPrincipal, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.address.model.CommerceCountry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getCommerceCountries", _getCommerceCountriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, start,
					end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.address.model.CommerceCountry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceCountriesCount(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"getCommerceCountriesCount",
					_getCommerceCountriesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.address.model.CommerceCountry updateCommerceCountry(
		HttpPrincipal httpPrincipal, long commerceCountryId,
		java.lang.String name, boolean allowsBilling, boolean allowsShipping,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean published)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCountryServiceUtil.class,
					"updateCommerceCountry",
					_updateCommerceCountryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId, name, allowsBilling, allowsShipping,
					twoLettersISOCode, threeLettersISOCode, numericISOCode,
					subjectToVAT, priority, published);

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

			return (com.liferay.commerce.address.model.CommerceCountry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceCountryServiceHttp.class);
	private static final Class<?>[] _addCommerceCountryParameterTypes0 = new Class[] {
			java.lang.String.class, boolean.class, boolean.class,
			java.lang.String.class, java.lang.String.class, int.class,
			boolean.class, double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceCountryParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceCountryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceCountriesParameterTypes3 = new Class[] {
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceCountriesCountParameterTypes4 = new Class[] {
			
		};
	private static final Class<?>[] _updateCommerceCountryParameterTypes5 = new Class[] {
			long.class, java.lang.String.class, boolean.class, boolean.class,
			java.lang.String.class, java.lang.String.class, int.class,
			boolean.class, double.class, boolean.class
		};
}
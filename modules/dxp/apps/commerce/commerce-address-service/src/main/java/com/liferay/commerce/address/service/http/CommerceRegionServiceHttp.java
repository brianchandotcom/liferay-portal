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

import com.liferay.commerce.address.service.CommerceRegionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceRegionServiceUtil} service utility. The
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
 * @see CommerceRegionServiceSoap
 * @see HttpPrincipal
 * @see CommerceRegionServiceUtil
 * @generated
 */
@ProviderType
public class CommerceRegionServiceHttp {
	public static com.liferay.commerce.address.model.CommerceRegion addCommerceRegion(
		HttpPrincipal httpPrincipal, long commerceCountryId,
		java.lang.String name, java.lang.String abbreviation, double priority,
		boolean published,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"addCommerceRegion", _addCommerceRegionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId, name, abbreviation, priority, published,
					serviceContext);

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

			return (com.liferay.commerce.address.model.CommerceRegion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.address.model.CommerceRegion deleteCommerceRegion(
		HttpPrincipal httpPrincipal, long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"deleteCommerceRegion", _deleteCommerceRegionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceRegionId);

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

			return (com.liferay.commerce.address.model.CommerceRegion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.address.model.CommerceRegion fetchCommerceRegion(
		HttpPrincipal httpPrincipal, long commerceRegionId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"fetchCommerceRegion", _fetchCommerceRegionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceRegionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.address.model.CommerceRegion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.address.model.CommerceRegion> getCommerceRegions(
		HttpPrincipal httpPrincipal, long commerceCountryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.address.model.CommerceRegion> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"getCommerceRegions", _getCommerceRegionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.address.model.CommerceRegion>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceRegionsCount(HttpPrincipal httpPrincipal,
		long commerceCountryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"getCommerceRegionsCount",
					_getCommerceRegionsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCountryId);

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

	public static com.liferay.commerce.address.model.CommerceRegion updateCommerceRegion(
		HttpPrincipal httpPrincipal, long commerceRegionId,
		java.lang.String name, java.lang.String abbreviation, double priority,
		boolean published)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceRegionServiceUtil.class,
					"updateCommerceRegion", _updateCommerceRegionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceRegionId, name, abbreviation, priority, published);

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

			return (com.liferay.commerce.address.model.CommerceRegion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceRegionServiceHttp.class);
	private static final Class<?>[] _addCommerceRegionParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceRegionParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceRegionParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceRegionsParameterTypes3 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceRegionsCountParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateCommerceRegionParameterTypes5 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			double.class, boolean.class
		};
}
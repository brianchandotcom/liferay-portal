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

import com.liferay.commerce.service.CommerceAvailabilityRangeServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceAvailabilityRangeServiceUtil} service utility. The
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
 * @see CommerceAvailabilityRangeServiceSoap
 * @see HttpPrincipal
 * @see CommerceAvailabilityRangeServiceUtil
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeServiceHttp {
	public static com.liferay.commerce.model.CommerceAvailabilityRange addCommerceAvailabilityRange(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityRangeServiceUtil.class,
					"addCommerceAvailabilityRange",
					_addCommerceAvailabilityRangeParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					titleMap, priority, serviceContext);

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

			return (com.liferay.commerce.model.CommerceAvailabilityRange)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceAvailabilityRange(
		HttpPrincipal httpPrincipal, long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityRangeServiceUtil.class,
					"deleteCommerceAvailabilityRange",
					_deleteCommerceAvailabilityRangeParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAvailabilityRangeId);

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

	public static com.liferay.commerce.model.CommerceAvailabilityRange getCommerceAvailabilityRange(
		HttpPrincipal httpPrincipal, long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityRangeServiceUtil.class,
					"getCommerceAvailabilityRange",
					_getCommerceAvailabilityRangeParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAvailabilityRangeId);

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

			return (com.liferay.commerce.model.CommerceAvailabilityRange)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRanges(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityRange> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityRangeServiceUtil.class,
					"getCommerceAvailabilityRanges",
					_getCommerceAvailabilityRangesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceAvailabilityRangesCount(
		HttpPrincipal httpPrincipal, long groupId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityRangeServiceUtil.class,
					"getCommerceAvailabilityRangesCount",
					_getCommerceAvailabilityRangesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static com.liferay.commerce.model.CommerceAvailabilityRange updateCommerceAvailabilityRange(
		HttpPrincipal httpPrincipal, long commerceAvailabilityRangeId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAvailabilityRangeServiceUtil.class,
					"updateCommerceAvailabilityRange",
					_updateCommerceAvailabilityRangeParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAvailabilityRangeId, titleMap, priority,
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

			return (com.liferay.commerce.model.CommerceAvailabilityRange)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceAvailabilityRangeServiceHttp.class);
	private static final Class<?>[] _addCommerceAvailabilityRangeParameterTypes0 =
		new Class[] {
			java.util.Map.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceAvailabilityRangeParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceAvailabilityRangeParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceAvailabilityRangesParameterTypes3 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceAvailabilityRangesCountParameterTypes4 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommerceAvailabilityRangeParameterTypes5 =
		new Class[] {
			long.class, java.util.Map.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
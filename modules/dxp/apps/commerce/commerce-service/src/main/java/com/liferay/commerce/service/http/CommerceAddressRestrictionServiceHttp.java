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

import com.liferay.commerce.service.CommerceAddressRestrictionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceAddressRestrictionServiceUtil} service utility. The
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
 * @see CommerceAddressRestrictionServiceSoap
 * @see HttpPrincipal
 * @see CommerceAddressRestrictionServiceUtil
 * @generated
 */
@ProviderType
public class CommerceAddressRestrictionServiceHttp {
	public static com.liferay.commerce.model.CommerceAddressRestriction addCommerceAddressRestriction(
		HttpPrincipal httpPrincipal, String className, long classPK,
		long commerceCountryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressRestrictionServiceUtil.class,
					"addCommerceAddressRestriction",
					_addCommerceAddressRestrictionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, commerceCountryId, serviceContext);

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

			return (com.liferay.commerce.model.CommerceAddressRestriction)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceAddressRestriction(
		HttpPrincipal httpPrincipal, long commerceAddressRestrictionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressRestrictionServiceUtil.class,
					"deleteCommerceAddressRestriction",
					_deleteCommerceAddressRestrictionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceAddressRestrictionId);

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

	public static com.liferay.commerce.model.CommerceAddressRestriction fetchCommerceAddressRestriction(
		HttpPrincipal httpPrincipal, String className, long classPK,
		long commerceCountryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressRestrictionServiceUtil.class,
					"fetchCommerceAddressRestriction",
					_fetchCommerceAddressRestrictionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, commerceCountryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceAddressRestriction)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceAddressRestriction> getCommerceAddressRestrictions(
		HttpPrincipal httpPrincipal, String className, long classPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAddressRestriction> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressRestrictionServiceUtil.class,
					"getCommerceAddressRestrictions",
					_getCommerceAddressRestrictionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceAddressRestriction>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceAddressRestrictionsCount(
		HttpPrincipal httpPrincipal, String className, long classPK) {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressRestrictionServiceUtil.class,
					"getCommerceAddressRestrictionsCount",
					_getCommerceAddressRestrictionsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK);

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

	public static boolean isCommerceAddressRestricted(
		HttpPrincipal httpPrincipal, String className, long classPK,
		long commerceCountryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressRestrictionServiceUtil.class,
					"isCommerceAddressRestricted",
					_isCommerceAddressRestrictedParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, commerceCountryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean isCommercePaymentMethodRestricted(
		HttpPrincipal httpPrincipal, long commercePaymentMethodId,
		long commerceCountryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressRestrictionServiceUtil.class,
					"isCommercePaymentMethodRestricted",
					_isCommercePaymentMethodRestrictedParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePaymentMethodId, commerceCountryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean isCommerceShippingMethodRestricted(
		HttpPrincipal httpPrincipal, long commerceShippingMethodId,
		long commerceCountryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceAddressRestrictionServiceUtil.class,
					"isCommerceShippingMethodRestricted",
					_isCommerceShippingMethodRestrictedParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingMethodId, commerceCountryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceAddressRestrictionServiceHttp.class);
	private static final Class<?>[] _addCommerceAddressRestrictionParameterTypes0 =
		new Class[] {
			String.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceAddressRestrictionParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCommerceAddressRestrictionParameterTypes2 =
		new Class[] { String.class, long.class, long.class };
	private static final Class<?>[] _getCommerceAddressRestrictionsParameterTypes3 =
		new Class[] {
			String.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceAddressRestrictionsCountParameterTypes4 =
		new Class[] { String.class, long.class };
	private static final Class<?>[] _isCommerceAddressRestrictedParameterTypes5 = new Class[] {
			String.class, long.class, long.class
		};
	private static final Class<?>[] _isCommercePaymentMethodRestrictedParameterTypes6 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _isCommerceShippingMethodRestrictedParameterTypes7 =
		new Class[] { long.class, long.class };
}
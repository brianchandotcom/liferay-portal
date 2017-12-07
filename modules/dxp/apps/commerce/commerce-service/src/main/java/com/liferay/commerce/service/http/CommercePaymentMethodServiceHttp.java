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

import com.liferay.commerce.service.CommercePaymentMethodServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommercePaymentMethodServiceUtil} service utility. The
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
 * @see CommercePaymentMethodServiceSoap
 * @see HttpPrincipal
 * @see CommercePaymentMethodServiceUtil
 * @generated
 */
@ProviderType
public class CommercePaymentMethodServiceHttp {
	public static com.liferay.commerce.model.CommercePaymentMethod addCommercePaymentMethod(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String engineKey,
		java.util.Map<java.lang.String, java.lang.String> engineParameterMap,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePaymentMethodServiceUtil.class,
					"addCommercePaymentMethod",
					_addCommercePaymentMethodParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, nameMap,
					descriptionMap, engineKey, engineParameterMap, priority,
					active, serviceContext);

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

			return (com.liferay.commerce.model.CommercePaymentMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommercePaymentMethod(
		HttpPrincipal httpPrincipal, long commercePaymentMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePaymentMethodServiceUtil.class,
					"deleteCommercePaymentMethod",
					_deleteCommercePaymentMethodParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePaymentMethodId);

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

	public static com.liferay.commerce.model.CommercePaymentMethod getCommercePaymentMethod(
		HttpPrincipal httpPrincipal, long commercePaymentMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePaymentMethodServiceUtil.class,
					"getCommercePaymentMethod",
					_getCommercePaymentMethodParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePaymentMethodId);

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

			return (com.liferay.commerce.model.CommercePaymentMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommercePaymentMethod> getCommercePaymentMethods(
		HttpPrincipal httpPrincipal, long groupId, boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommercePaymentMethodServiceUtil.class,
					"getCommercePaymentMethods",
					_getCommercePaymentMethodsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommercePaymentMethod>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommercePaymentMethod updateCommercePaymentMethod(
		HttpPrincipal httpPrincipal, long commercePaymentMethodId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.lang.String, java.lang.String> engineParameterMap,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePaymentMethodServiceUtil.class,
					"updateCommercePaymentMethod",
					_updateCommercePaymentMethodParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePaymentMethodId, nameMap, descriptionMap,
					engineParameterMap, priority, active, serviceContext);

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

			return (com.liferay.commerce.model.CommercePaymentMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommercePaymentMethodServiceHttp.class);
	private static final Class<?>[] _addCommercePaymentMethodParameterTypes0 = new Class[] {
			java.util.Map.class, java.util.Map.class, java.lang.String.class,
			java.util.Map.class, double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommercePaymentMethodParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommercePaymentMethodParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommercePaymentMethodsParameterTypes3 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateCommercePaymentMethodParameterTypes4 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
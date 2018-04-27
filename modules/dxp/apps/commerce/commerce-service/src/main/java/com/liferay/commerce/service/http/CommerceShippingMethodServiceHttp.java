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

import com.liferay.commerce.service.CommerceShippingMethodServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceShippingMethodServiceUtil} service utility. The
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
 * @see CommerceShippingMethodServiceSoap
 * @see HttpPrincipal
 * @see CommerceShippingMethodServiceUtil
 * @generated
 */
@ProviderType
public class CommerceShippingMethodServiceHttp {
	public static com.liferay.commerce.model.CommerceShippingMethod addCommerceShippingMethod(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.io.File imageFile, String engineKey, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShippingMethodServiceUtil.class,
					"addCommerceShippingMethod",
					_addCommerceShippingMethodParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, nameMap,
					descriptionMap, imageFile, engineKey, priority, active,
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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethod createCommerceShippingMethod(
		HttpPrincipal httpPrincipal, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShippingMethodServiceUtil.class,
					"createCommerceShippingMethod",
					_createCommerceShippingMethodParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingMethodId);

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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceShippingMethod(
		HttpPrincipal httpPrincipal, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShippingMethodServiceUtil.class,
					"deleteCommerceShippingMethod",
					_deleteCommerceShippingMethodParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingMethodId);

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

	public static com.liferay.commerce.model.CommerceShippingMethod fetchCommerceShippingMethod(
		HttpPrincipal httpPrincipal, long groupId, String engineKey) {
		try {
			MethodKey methodKey = new MethodKey(CommerceShippingMethodServiceUtil.class,
					"fetchCommerceShippingMethod",
					_fetchCommerceShippingMethodParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					engineKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethod getCommerceShippingMethod(
		HttpPrincipal httpPrincipal, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShippingMethodServiceUtil.class,
					"getCommerceShippingMethod",
					_getCommerceShippingMethodParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingMethodId);

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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShippingMethod> getCommerceShippingMethods(
		HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShippingMethodServiceUtil.class,
					"getCommerceShippingMethods",
					_getCommerceShippingMethodsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

			return (java.util.List<com.liferay.commerce.model.CommerceShippingMethod>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShippingMethod> getCommerceShippingMethods(
		HttpPrincipal httpPrincipal, long groupId, boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceShippingMethodServiceUtil.class,
					"getCommerceShippingMethods",
					_getCommerceShippingMethodsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceShippingMethod>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceShippingMethodsCount(
		HttpPrincipal httpPrincipal, long groupId, boolean active) {
		try {
			MethodKey methodKey = new MethodKey(CommerceShippingMethodServiceUtil.class,
					"getCommerceShippingMethodsCount",
					_getCommerceShippingMethodsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					active);

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

	public static com.liferay.commerce.model.CommerceShippingMethod setActive(
		HttpPrincipal httpPrincipal, long commerceShippingMethodId,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShippingMethodServiceUtil.class,
					"setActive", _setActiveParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingMethodId, active);

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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethod updateCommerceShippingMethod(
		HttpPrincipal httpPrincipal, long commerceShippingMethodId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.io.File imageFile, double priority, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShippingMethodServiceUtil.class,
					"updateCommerceShippingMethod",
					_updateCommerceShippingMethodParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingMethodId, nameMap, descriptionMap,
					imageFile, priority, active);

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

			return (com.liferay.commerce.model.CommerceShippingMethod)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceShippingMethodServiceHttp.class);
	private static final Class<?>[] _addCommerceShippingMethodParameterTypes0 = new Class[] {
			java.util.Map.class, java.util.Map.class, java.io.File.class,
			String.class, double.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _createCommerceShippingMethodParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _deleteCommerceShippingMethodParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCommerceShippingMethodParameterTypes3 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getCommerceShippingMethodParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceShippingMethodsParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceShippingMethodsParameterTypes6 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _getCommerceShippingMethodsCountParameterTypes7 =
		new Class[] { long.class, boolean.class };
	private static final Class<?>[] _setActiveParameterTypes8 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateCommerceShippingMethodParameterTypes9 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.io.File.class, double.class, boolean.class
		};
}
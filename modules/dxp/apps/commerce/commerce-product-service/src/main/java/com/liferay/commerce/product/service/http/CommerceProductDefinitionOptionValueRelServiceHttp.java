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

package com.liferay.commerce.product.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CommerceProductDefinitionOptionValueRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceProductDefinitionOptionValueRelServiceUtil} service utility. The
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
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionValueRelServiceSoap
 * @see HttpPrincipal
 * @see CommerceProductDefinitionOptionValueRelServiceUtil
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelServiceHttp {
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel addCommerceProductDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionOptionRelId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionValueRelServiceUtil.class,
					"addCommerceProductDefinitionOptionValueRel",
					_addCommerceProductDefinitionOptionValueRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionRelId, titleMap, priority,
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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal,
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionValueRelServiceUtil.class,
					"deleteCommerceProductDefinitionOptionValueRel",
					_deleteCommerceProductDefinitionOptionValueRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionValueRel);

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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal,
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionValueRelServiceUtil.class,
					"deleteCommerceProductDefinitionOptionValueRel",
					_deleteCommerceProductDefinitionOptionValueRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionValueRelId);

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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel fetchCommerceProductDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal,
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionValueRelServiceUtil.class,
					"fetchCommerceProductDefinitionOptionValueRel",
					_fetchCommerceProductDefinitionOptionValueRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionValueRelId);

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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel getCommerceProductDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal,
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionValueRelServiceUtil.class,
					"getCommerceProductDefinitionOptionValueRel",
					_getCommerceProductDefinitionOptionValueRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionValueRelId);

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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionOptionRelId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionValueRelServiceUtil.class,
					"getCommerceProductDefinitionOptionValueRels",
					_getCommerceProductDefinitionOptionValueRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionRelId, start, end);

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

			return (java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionOptionRelId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionValueRelServiceUtil.class,
					"getCommerceProductDefinitionOptionValueRels",
					_getCommerceProductDefinitionOptionValueRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionRelId, start, end,
					orderByComparator);

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

			return (java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceProductDefinitionOptionValueRelsCount(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionValueRelServiceUtil.class,
					"getCommerceProductDefinitionOptionValueRelsCount",
					_getCommerceProductDefinitionOptionValueRelsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionRelId);

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

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel updateCommerceProductDefinitionOptionValueRel(
		HttpPrincipal httpPrincipal,
		long commerceProductDefinitionOptionValueRelId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionValueRelServiceUtil.class,
					"updateCommerceProductDefinitionOptionValueRel",
					_updateCommerceProductDefinitionOptionValueRelParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionValueRelId, titleMap,
					priority, serviceContext);

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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceProductDefinitionOptionValueRelServiceHttp.class);
	private static final Class<?>[] _addCommerceProductDefinitionOptionValueRelParameterTypes0 =
		new Class[] {
			long.class, java.util.Map.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceProductDefinitionOptionValueRelParameterTypes1 =
		new Class[] {
			com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel.class
		};
	private static final Class<?>[] _deleteCommerceProductDefinitionOptionValueRelParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCommerceProductDefinitionOptionValueRelParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceProductDefinitionOptionValueRelParameterTypes4 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceProductDefinitionOptionValueRelsParameterTypes5 =
		new Class[] { long.class, int.class, int.class };
	private static final Class<?>[] _getCommerceProductDefinitionOptionValueRelsParameterTypes6 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceProductDefinitionOptionValueRelsCountParameterTypes7 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommerceProductDefinitionOptionValueRelParameterTypes8 =
		new Class[] {
			long.class, java.util.Map.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
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

package com.liferay.commerce.discount.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.service.CommerceDiscountRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceDiscountRelServiceUtil} service utility. The
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
 * @see CommerceDiscountRelServiceSoap
 * @see HttpPrincipal
 * @see CommerceDiscountRelServiceUtil
 * @generated
 */
@ProviderType
public class CommerceDiscountRelServiceHttp {
	public static com.liferay.commerce.discount.model.CommerceDiscountRel addCommerceDiscountRel(
		HttpPrincipal httpPrincipal, long commerceDiscountId, String className,
		long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountRelServiceUtil.class,
					"addCommerceDiscountRel",
					_addCommerceDiscountRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountId, className, classPK, serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscountRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceDiscountRel(HttpPrincipal httpPrincipal,
		long commerceDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountRelServiceUtil.class,
					"deleteCommerceDiscountRel",
					_deleteCommerceDiscountRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountRelId);

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

	public static long[] getClassPKs(HttpPrincipal httpPrincipal,
		long commerceDiscountId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountRelServiceUtil.class,
					"getClassPKs", _getClassPKsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountId, className);

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRel getCommerceDiscountRel(
		HttpPrincipal httpPrincipal, long commerceDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountRelServiceUtil.class,
					"getCommerceDiscountRel",
					_getCommerceDiscountRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountRelId);

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

			return (com.liferay.commerce.discount.model.CommerceDiscountRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRel> getCommerceDiscountRels(
		HttpPrincipal httpPrincipal, long commerceDiscountId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountRelServiceUtil.class,
					"getCommerceDiscountRels",
					_getCommerceDiscountRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountId, className);

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

			return (java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRel> getCommerceDiscountRels(
		HttpPrincipal httpPrincipal, long commerceDiscountId, String className,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscountRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountRelServiceUtil.class,
					"getCommerceDiscountRels",
					_getCommerceDiscountRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountId, className, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceDiscountRelsCount(
		HttpPrincipal httpPrincipal, long commerceDiscountId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountRelServiceUtil.class,
					"getCommerceDiscountRelsCount",
					_getCommerceDiscountRelsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountId, className);

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

	private static Log _log = LogFactoryUtil.getLog(CommerceDiscountRelServiceHttp.class);
	private static final Class<?>[] _addCommerceDiscountRelParameterTypes0 = new Class[] {
			long.class, String.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceDiscountRelParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getClassPKsParameterTypes2 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getCommerceDiscountRelParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceDiscountRelsParameterTypes4 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getCommerceDiscountRelsParameterTypes5 = new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceDiscountRelsCountParameterTypes6 =
		new Class[] { long.class, String.class };
}
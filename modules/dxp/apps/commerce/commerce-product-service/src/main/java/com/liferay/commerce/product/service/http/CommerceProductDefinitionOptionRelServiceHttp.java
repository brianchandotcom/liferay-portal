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

import com.liferay.commerce.product.service.CommerceProductDefinitionOptionRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceProductDefinitionOptionRelServiceUtil} service utility. The
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
 * @see CommerceProductDefinitionOptionRelServiceSoap
 * @see HttpPrincipal
 * @see CommerceProductDefinitionOptionRelServiceUtil
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionRelServiceHttp {
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionId,
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionRelServiceUtil.class,
					"addCommerceProductDefinitionOptionRel",
					_addCommerceProductDefinitionOptionRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionId, commerceProductOptionId,
					nameMap, descriptionMap, ddmFormFieldTypeName, priority,
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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionId,
		long commerceProductOptionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionRelServiceUtil.class,
					"addCommerceProductDefinitionOptionRel",
					_addCommerceProductDefinitionOptionRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionId, commerceProductOptionId,
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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		HttpPrincipal httpPrincipal,
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionRelServiceUtil.class,
					"deleteCommerceProductDefinitionOptionRel",
					_deleteCommerceProductDefinitionOptionRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionRel);

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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionRelServiceUtil.class,
					"deleteCommerceProductDefinitionOptionRel",
					_deleteCommerceProductDefinitionOptionRelParameterTypes3);

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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel fetchCommerceProductDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionRelServiceUtil.class,
					"fetchCommerceProductDefinitionOptionRel",
					_fetchCommerceProductDefinitionOptionRelParameterTypes4);

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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel getCommerceProductDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionRelServiceUtil.class,
					"getCommerceProductDefinitionOptionRel",
					_getCommerceProductDefinitionOptionRelParameterTypes5);

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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionRelServiceUtil.class,
					"getCommerceProductDefinitionOptionRels",
					_getCommerceProductDefinitionOptionRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionId, start, end);

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

			return (java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionRelServiceUtil.class,
					"getCommerceProductDefinitionOptionRels",
					_getCommerceProductDefinitionOptionRelsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceProductDefinitionOptionRelsCount(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionRelServiceUtil.class,
					"getCommerceProductDefinitionOptionRelsCount",
					_getCommerceProductDefinitionOptionRelsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionId);

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

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel updateCommerceProductDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long commerceProductDefinitionOptionRelId,
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceProductDefinitionOptionRelServiceUtil.class,
					"updateCommerceProductDefinitionOptionRel",
					_updateCommerceProductDefinitionOptionRelParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceProductDefinitionOptionRelId,
					commerceProductOptionId, nameMap, descriptionMap,
					ddmFormFieldTypeName, priority, serviceContext);

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

			return (com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceProductDefinitionOptionRelServiceHttp.class);
	private static final Class<?>[] _addCommerceProductDefinitionOptionRelParameterTypes0 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommerceProductDefinitionOptionRelParameterTypes1 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceProductDefinitionOptionRelParameterTypes2 =
		new Class[] {
			com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel.class
		};
	private static final Class<?>[] _deleteCommerceProductDefinitionOptionRelParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCommerceProductDefinitionOptionRelParameterTypes4 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceProductDefinitionOptionRelParameterTypes5 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceProductDefinitionOptionRelsParameterTypes6 =
		new Class[] { long.class, int.class, int.class };
	private static final Class<?>[] _getCommerceProductDefinitionOptionRelsParameterTypes7 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceProductDefinitionOptionRelsCountParameterTypes8 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommerceProductDefinitionOptionRelParameterTypes9 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
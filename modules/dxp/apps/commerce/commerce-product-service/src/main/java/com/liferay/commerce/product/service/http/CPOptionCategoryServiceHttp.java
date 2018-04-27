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

import com.liferay.commerce.product.service.CPOptionCategoryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPOptionCategoryServiceUtil} service utility. The
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
 * @see CPOptionCategoryServiceSoap
 * @see HttpPrincipal
 * @see CPOptionCategoryServiceUtil
 * @generated
 */
@ProviderType
public class CPOptionCategoryServiceHttp {
	public static com.liferay.commerce.product.model.CPOptionCategory addCPOptionCategory(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionCategoryServiceUtil.class,
					"addCPOptionCategory", _addCPOptionCategoryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					titleMap, descriptionMap, priority, key, serviceContext);

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

			return (com.liferay.commerce.product.model.CPOptionCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionCategory deleteCPOptionCategory(
		HttpPrincipal httpPrincipal,
		com.liferay.commerce.product.model.CPOptionCategory cpOptionCategory)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionCategoryServiceUtil.class,
					"deleteCPOptionCategory",
					_deleteCPOptionCategoryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionCategory);

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

			return (com.liferay.commerce.product.model.CPOptionCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionCategory deleteCPOptionCategory(
		HttpPrincipal httpPrincipal, long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionCategoryServiceUtil.class,
					"deleteCPOptionCategory",
					_deleteCPOptionCategoryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionCategoryId);

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

			return (com.liferay.commerce.product.model.CPOptionCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionCategory fetchCPOptionCategory(
		HttpPrincipal httpPrincipal, long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionCategoryServiceUtil.class,
					"fetchCPOptionCategory",
					_fetchCPOptionCategoryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionCategoryId);

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

			return (com.liferay.commerce.product.model.CPOptionCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		HttpPrincipal httpPrincipal, long groupId, int start, int end) {
		try {
			MethodKey methodKey = new MethodKey(CPOptionCategoryServiceUtil.class,
					"getCPOptionCategories",
					_getCPOptionCategoriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.product.model.CPOptionCategory>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOptionCategory> getCPOptionCategories(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionCategory> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CPOptionCategoryServiceUtil.class,
					"getCPOptionCategories",
					_getCPOptionCategoriesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.product.model.CPOptionCategory>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPOptionCategoriesCount(HttpPrincipal httpPrincipal,
		long groupId) {
		try {
			MethodKey methodKey = new MethodKey(CPOptionCategoryServiceUtil.class,
					"getCPOptionCategoriesCount",
					_getCPOptionCategoriesCountParameterTypes6);

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

	public static com.liferay.commerce.product.model.CPOptionCategory getCPOptionCategory(
		HttpPrincipal httpPrincipal, long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionCategoryServiceUtil.class,
					"getCPOptionCategory", _getCPOptionCategoryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionCategoryId);

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

			return (com.liferay.commerce.product.model.CPOptionCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionCategory updateCPOptionCategory(
		HttpPrincipal httpPrincipal, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionCategoryServiceUtil.class,
					"updateCPOptionCategory",
					_updateCPOptionCategoryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionCategoryId, titleMap, descriptionMap, priority,
					key, serviceContext);

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

			return (com.liferay.commerce.product.model.CPOptionCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPOptionCategoryServiceHttp.class);
	private static final Class<?>[] _addCPOptionCategoryParameterTypes0 = new Class[] {
			java.util.Map.class, java.util.Map.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPOptionCategoryParameterTypes1 = new Class[] {
			com.liferay.commerce.product.model.CPOptionCategory.class
		};
	private static final Class<?>[] _deleteCPOptionCategoryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPOptionCategoryParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPOptionCategoriesParameterTypes4 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCPOptionCategoriesParameterTypes5 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPOptionCategoriesCountParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPOptionCategoryParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateCPOptionCategoryParameterTypes8 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, double.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
}
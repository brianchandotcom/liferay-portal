/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.shopping.service.ShoppingCategoryServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.shopping.service.ShoppingCategoryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
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
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCategoryServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.shopping.service.ShoppingCategoryServiceUtil
 * @generated
 */
public class ShoppingCategoryServiceHttp {
	public static com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		HttpPrincipal httpPrincipal, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"addCategory", _addCategoryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					parentCategoryId, name, description, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCategory(HttpPrincipal httpPrincipal,
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"deleteCategory", _deleteCategoryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					categoryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getCategories(
		HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getCategories", _getCategoriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getCategories(
		HttpPrincipal httpPrincipal, long groupId, long parentCategoryId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getCategories", _getCategoriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentCategoryId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesCount(HttpPrincipal httpPrincipal,
		long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getCategoriesCount", _getCategoriesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentCategoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
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

	public static com.liferay.portlet.shopping.model.ShoppingCategory getCategory(
		HttpPrincipal httpPrincipal, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getCategory", _getCategoryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void getSubcategoryIds(HttpPrincipal httpPrincipal,
		java.util.List<java.lang.Long> categoryIds, long groupId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getSubcategoryIds", _getSubcategoryIdsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					categoryIds, groupId, categoryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory updateCategory(
		HttpPrincipal httpPrincipal, long categoryId, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentCategory,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"updateCategory", _updateCategoryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					categoryId, parentCategoryId, name, description,
					mergeWithParentCategory, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.shopping.model.ShoppingCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingCategoryServiceHttp.class);
	private static final Class<?>[] _addCategoryParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCategoryParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCategoriesParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCategoriesParameterTypes3 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getCategoriesCountParameterTypes4 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getCategoryParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getSubcategoryIdsParameterTypes6 = new Class[] {
			java.util.List.class, long.class, long.class
		};
	private static final Class<?>[] _updateCategoryParameterTypes7 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, boolean.class,
			com.liferay.portal.service.ServiceContext.class
		};
}
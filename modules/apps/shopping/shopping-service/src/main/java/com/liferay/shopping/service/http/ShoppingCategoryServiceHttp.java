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

package com.liferay.shopping.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import com.liferay.shopping.service.ShoppingCategoryServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link ShoppingCategoryServiceUtil} service utility. The
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
 * @author Brian Wing Shun Chan
 * @see ShoppingCategoryServiceSoap
 * @see HttpPrincipal
 * @see ShoppingCategoryServiceUtil
 * @generated
 */
@ProviderType
public class ShoppingCategoryServiceHttp {
	public static com.liferay.shopping.model.ShoppingCategory addCategory(
		HttpPrincipal httpPrincipal, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
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

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.shopping.model.ShoppingCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCategory(HttpPrincipal httpPrincipal,
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
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

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.shopping.model.ShoppingCategory> getCategories(
		HttpPrincipal httpPrincipal, long groupId) {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getCategories", _getCategoriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.shopping.model.ShoppingCategory>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.shopping.model.ShoppingCategory> getCategories(
		HttpPrincipal httpPrincipal, long groupId, long parentCategoryId,
		int start, int end) {
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
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.shopping.model.ShoppingCategory>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<java.lang.Object> getCategoriesAndItems(
		HttpPrincipal httpPrincipal, long groupId, long categoryId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator<?> obc) {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getCategoriesAndItems",
					_getCategoriesAndItemsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<java.lang.Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesAndItemsCount(HttpPrincipal httpPrincipal,
		long groupId, long categoryId) {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getCategoriesAndItemsCount",
					_getCategoriesAndItemsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					categoryId);

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

	public static int getCategoriesCount(HttpPrincipal httpPrincipal,
		long groupId, long parentCategoryId) {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getCategoriesCount", _getCategoriesCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentCategoryId);

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

	public static com.liferay.shopping.model.ShoppingCategory getCategory(
		HttpPrincipal httpPrincipal, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getCategory", _getCategoryParameterTypes7);

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

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.shopping.model.ShoppingCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void getSubcategoryIds(HttpPrincipal httpPrincipal,
		java.util.List<java.lang.Long> categoryIds, long groupId,
		long categoryId) {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"getSubcategoryIds", _getSubcategoryIdsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					categoryIds, groupId, categoryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.shopping.model.ShoppingCategory updateCategory(
		HttpPrincipal httpPrincipal, long categoryId, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentCategory,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCategoryServiceUtil.class,
					"updateCategory", _updateCategoryParameterTypes9);

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

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.shopping.model.ShoppingCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingCategoryServiceHttp.class);
	private static final Class<?>[] _addCategoryParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
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
	private static final Class<?>[] _getCategoriesAndItemsParameterTypes4 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCategoriesAndItemsCountParameterTypes5 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getCategoriesCountParameterTypes6 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getCategoryParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getSubcategoryIdsParameterTypes8 = new Class[] {
			java.util.List.class, long.class, long.class
		};
	private static final Class<?>[] _updateCategoryParameterTypes9 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
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

package com.liferay.message.boards.service.http;

import com.liferay.message.boards.service.MBCategoryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>MBCategoryServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
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
 * @see MBCategoryServiceSoap
 * @generated
 */
public class MBCategoryServiceHttp {

	public static com.liferay.message.boards.model.MBCategory addCategory(
			HttpPrincipal httpPrincipal, long userId, long parentCategoryId,
			String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "addCategory",
				_addCategoryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, parentCategoryId, name, description,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBCategory addCategory(
			HttpPrincipal httpPrincipal, long parentCategoryId, String name,
			String description, String displayStyle, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean mailingListActive,
			boolean allowAnonymousEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "addCategory",
				_addCategoryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentCategoryId, name, description, displayStyle,
				emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
				inUserName, inPassword, inReadInterval, outEmailAddress,
				outCustom, outServerName, outServerPort, outUseSSL, outUserName,
				outPassword, mailingListActive, allowAnonymousEmail,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCategory(
			HttpPrincipal httpPrincipal, long categoryId,
			boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "deleteCategory",
				_deleteCategoryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryId, includeTrashedEntries);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCategory(
			HttpPrincipal httpPrincipal, long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "deleteCategory",
				_deleteCategoryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory>
		getCategories(HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategories",
				_getCategoriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBCategory>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory>
		getCategories(HttpPrincipal httpPrincipal, long groupId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategories",
				_getCategoriesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBCategory>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory>
		getCategories(
			HttpPrincipal httpPrincipal, long groupId, long parentCategoryId,
			int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategories",
				_getCategoriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentCategoryId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBCategory>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory>
		getCategories(
			HttpPrincipal httpPrincipal, long groupId, long parentCategoryId,
			int status, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategories",
				_getCategoriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentCategoryId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBCategory>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory>
		getCategories(
			HttpPrincipal httpPrincipal, long groupId, long excludedCategoryId,
			long parentCategoryId, int status, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategories",
				_getCategoriesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, excludedCategoryId, parentCategoryId,
				status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBCategory>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory>
			getCategories(
				HttpPrincipal httpPrincipal, long groupId,
				long parentCategoryId,
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.message.boards.model.MBCategory>
						queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategories",
				_getCategoriesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentCategoryId, queryDefinition);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBCategory>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory>
		getCategories(
			HttpPrincipal httpPrincipal, long groupId, long[] parentCategoryIds,
			int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategories",
				_getCategoriesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentCategoryIds, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBCategory>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory>
		getCategories(
			HttpPrincipal httpPrincipal, long groupId, long[] parentCategoryIds,
			int status, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategories",
				_getCategoriesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentCategoryIds, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBCategory>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory>
		getCategories(
			HttpPrincipal httpPrincipal, long groupId,
			long[] excludedCategoryIds, long[] parentCategoryIds, int status,
			int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategories",
				_getCategoriesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, excludedCategoryIds, parentCategoryIds,
				status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBCategory>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object> getCategoriesAndThreads(
		HttpPrincipal httpPrincipal, long groupId, long categoryId) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesAndThreads",
				_getCategoriesAndThreadsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object> getCategoriesAndThreads(
		HttpPrincipal httpPrincipal, long groupId, long categoryId,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesAndThreads",
				_getCategoriesAndThreadsParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object> getCategoriesAndThreads(
		HttpPrincipal httpPrincipal, long groupId, long categoryId, int status,
		int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesAndThreads",
				_getCategoriesAndThreadsParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, status, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object> getCategoriesAndThreads(
		HttpPrincipal httpPrincipal, long groupId, long categoryId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesAndThreads",
				_getCategoriesAndThreadsParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, status, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Object> getCategoriesAndThreads(
			HttpPrincipal httpPrincipal, long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesAndThreads",
				_getCategoriesAndThreadsParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, queryDefinition);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesAndThreadsCount(
		HttpPrincipal httpPrincipal, long groupId, long categoryId) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesAndThreadsCount",
				_getCategoriesAndThreadsCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesAndThreadsCount(
		HttpPrincipal httpPrincipal, long groupId, long categoryId,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesAndThreadsCount",
				_getCategoriesAndThreadsCountParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesAndThreadsCount(
			HttpPrincipal httpPrincipal, long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesAndThreadsCount",
				_getCategoriesAndThreadsCountParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId, queryDefinition);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesCount(
		HttpPrincipal httpPrincipal, long groupId, long parentCategoryId) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesCount",
				_getCategoriesCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentCategoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesCount(
		HttpPrincipal httpPrincipal, long groupId, long parentCategoryId,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesCount",
				_getCategoriesCountParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentCategoryId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesCount(
		HttpPrincipal httpPrincipal, long groupId, long excludedCategoryId,
		long parentCategoryId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesCount",
				_getCategoriesCountParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, excludedCategoryId, parentCategoryId,
				status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesCount(
			HttpPrincipal httpPrincipal, long groupId, long parentCategoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesCount",
				_getCategoriesCountParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentCategoryId, queryDefinition);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesCount(
		HttpPrincipal httpPrincipal, long groupId, long[] parentCategoryIds) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesCount",
				_getCategoriesCountParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentCategoryIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesCount(
		HttpPrincipal httpPrincipal, long groupId, long[] parentCategoryIds,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesCount",
				_getCategoriesCountParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentCategoryIds, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesCount(
		HttpPrincipal httpPrincipal, long groupId, long[] excludedCategoryIds,
		long[] parentCategoryIds, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoriesCount",
				_getCategoriesCountParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, excludedCategoryIds, parentCategoryIds,
				status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBCategory getCategory(
			HttpPrincipal httpPrincipal, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategory",
				_getCategoryParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long[] getCategoryIds(
		HttpPrincipal httpPrincipal, long groupId, long categoryId) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getCategoryIds",
				_getCategoryIdsParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<Long> getSubcategoryIds(
		HttpPrincipal httpPrincipal, java.util.List<Long> categoryIds,
		long groupId, long categoryId) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getSubcategoryIds",
				_getSubcategoryIdsParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryIds, groupId, categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<Long>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBCategory>
		getSubscribedCategories(
			HttpPrincipal httpPrincipal, long groupId, long userId, int start,
			int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getSubscribedCategories",
				_getSubscribedCategoriesParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.message.boards.model.MBCategory>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getSubscribedCategoriesCount(
		HttpPrincipal httpPrincipal, long groupId, long userId) {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "getSubscribedCategoriesCount",
				_getSubscribedCategoriesCountParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBCategory moveCategory(
			HttpPrincipal httpPrincipal, long categoryId, long parentCategoryId,
			boolean mergeWithParentCategory)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "moveCategory",
				_moveCategoryParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryId, parentCategoryId,
				mergeWithParentCategory);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBCategory
			moveCategoryFromTrash(
				HttpPrincipal httpPrincipal, long categoryId,
				long newCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "moveCategoryFromTrash",
				_moveCategoryFromTrashParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryId, newCategoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBCategory
			moveCategoryToTrash(HttpPrincipal httpPrincipal, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "moveCategoryToTrash",
				_moveCategoryToTrashParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreCategoryFromTrash(
			HttpPrincipal httpPrincipal, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "restoreCategoryFromTrash",
				_restoreCategoryFromTrashParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void subscribeCategory(
			HttpPrincipal httpPrincipal, long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "subscribeCategory",
				_subscribeCategoryParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsubscribeCategory(
			HttpPrincipal httpPrincipal, long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "unsubscribeCategory",
				_unsubscribeCategoryParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, categoryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBCategory updateCategory(
			HttpPrincipal httpPrincipal, long categoryId, long parentCategoryId,
			String name, String description, String displayStyle,
			String emailAddress, String inProtocol, String inServerName,
			int inServerPort, boolean inUseSSL, String inUserName,
			String inPassword, int inReadInterval, String outEmailAddress,
			boolean outCustom, String outServerName, int outServerPort,
			boolean outUseSSL, String outUserName, String outPassword,
			boolean mailingListActive, boolean allowAnonymousEmail,
			boolean mergeWithParentCategory,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBCategoryServiceUtil.class, "updateCategory",
				_updateCategoryParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryId, parentCategoryId, name, description,
				displayStyle, emailAddress, inProtocol, inServerName,
				inServerPort, inUseSSL, inUserName, inPassword, inReadInterval,
				outEmailAddress, outCustom, outServerName, outServerPort,
				outUseSSL, outUserName, outPassword, mailingListActive,
				allowAnonymousEmail, mergeWithParentCategory, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.message.boards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBCategoryServiceHttp.class);

	private static final Class<?>[] _addCategoryParameterTypes0 = new Class[] {
		long.class, long.class, String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addCategoryParameterTypes1 = new Class[] {
		long.class, String.class, String.class, String.class, String.class,
		String.class, String.class, int.class, boolean.class, String.class,
		String.class, int.class, String.class, boolean.class, String.class,
		int.class, boolean.class, String.class, String.class, boolean.class,
		boolean.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteCategoryParameterTypes2 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _deleteCategoryParameterTypes3 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getCategoriesParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getCategoriesParameterTypes5 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getCategoriesParameterTypes6 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getCategoriesParameterTypes7 =
		new Class[] {long.class, long.class, int.class, int.class, int.class};
	private static final Class<?>[] _getCategoriesParameterTypes8 =
		new Class[] {
			long.class, long.class, long.class, int.class, int.class, int.class
		};
	private static final Class<?>[] _getCategoriesParameterTypes9 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.dao.orm.QueryDefinition.class
		};
	private static final Class<?>[] _getCategoriesParameterTypes10 =
		new Class[] {long.class, long[].class, int.class, int.class};
	private static final Class<?>[] _getCategoriesParameterTypes11 =
		new Class[] {long.class, long[].class, int.class, int.class, int.class};
	private static final Class<?>[] _getCategoriesParameterTypes12 =
		new Class[] {
			long.class, long[].class, long[].class, int.class, int.class,
			int.class
		};
	private static final Class<?>[] _getCategoriesAndThreadsParameterTypes13 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getCategoriesAndThreadsParameterTypes14 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _getCategoriesAndThreadsParameterTypes15 =
		new Class[] {long.class, long.class, int.class, int.class, int.class};
	private static final Class<?>[] _getCategoriesAndThreadsParameterTypes16 =
		new Class[] {
			long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCategoriesAndThreadsParameterTypes17 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.dao.orm.QueryDefinition.class
		};
	private static final Class<?>[]
		_getCategoriesAndThreadsCountParameterTypes18 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_getCategoriesAndThreadsCountParameterTypes19 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[]
		_getCategoriesAndThreadsCountParameterTypes20 = new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.dao.orm.QueryDefinition.class
		};
	private static final Class<?>[] _getCategoriesCountParameterTypes21 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getCategoriesCountParameterTypes22 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _getCategoriesCountParameterTypes23 =
		new Class[] {long.class, long.class, long.class, int.class};
	private static final Class<?>[] _getCategoriesCountParameterTypes24 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.dao.orm.QueryDefinition.class
		};
	private static final Class<?>[] _getCategoriesCountParameterTypes25 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _getCategoriesCountParameterTypes26 =
		new Class[] {long.class, long[].class, int.class};
	private static final Class<?>[] _getCategoriesCountParameterTypes27 =
		new Class[] {long.class, long[].class, long[].class, int.class};
	private static final Class<?>[] _getCategoryParameterTypes28 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getCategoryIdsParameterTypes29 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getSubcategoryIdsParameterTypes30 =
		new Class[] {java.util.List.class, long.class, long.class};
	private static final Class<?>[] _getSubscribedCategoriesParameterTypes31 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[]
		_getSubscribedCategoriesCountParameterTypes32 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _moveCategoryParameterTypes33 =
		new Class[] {long.class, long.class, boolean.class};
	private static final Class<?>[] _moveCategoryFromTrashParameterTypes34 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _moveCategoryToTrashParameterTypes35 =
		new Class[] {long.class};
	private static final Class<?>[] _restoreCategoryFromTrashParameterTypes36 =
		new Class[] {long.class};
	private static final Class<?>[] _subscribeCategoryParameterTypes37 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _unsubscribeCategoryParameterTypes38 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _updateCategoryParameterTypes39 =
		new Class[] {
			long.class, long.class, String.class, String.class, String.class,
			String.class, String.class, String.class, int.class, boolean.class,
			String.class, String.class, int.class, String.class, boolean.class,
			String.class, int.class, boolean.class, String.class, String.class,
			boolean.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
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

import com.liferay.message.boards.service.MBThreadServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>MBThreadServiceUtil</code> service
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
 * @see MBThreadServiceSoap
 * @generated
 */
public class MBThreadServiceHttp {

	public static void deleteThread(HttpPrincipal httpPrincipal, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "deleteThread",
				_deleteThreadParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, threadId);

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

	public static java.util.List<com.liferay.message.boards.model.MBThread>
			getGroupThreads(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				java.util.Date modifiedDate, boolean includeAnonymous,
				int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getGroupThreads",
				_getGroupThreadsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, modifiedDate, includeAnonymous,
				status, start, end);

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

			return (java.util.List<com.liferay.message.boards.model.MBThread>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread>
			getGroupThreads(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				java.util.Date modifiedDate, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getGroupThreads",
				_getGroupThreadsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, modifiedDate, status, start, end);

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

			return (java.util.List<com.liferay.message.boards.model.MBThread>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread>
			getGroupThreads(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				int status, boolean subscribed, boolean includeAnonymous,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getGroupThreads",
				_getGroupThreadsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, status, subscribed,
				includeAnonymous, start, end);

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

			return (java.util.List<com.liferay.message.boards.model.MBThread>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread>
			getGroupThreads(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				int status, boolean subscribed, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getGroupThreads",
				_getGroupThreadsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, status, subscribed, start, end);

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

			return (java.util.List<com.liferay.message.boards.model.MBThread>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread>
			getGroupThreads(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getGroupThreads",
				_getGroupThreadsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, status, start, end);

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

			return (java.util.List<com.liferay.message.boards.model.MBThread>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupThreadsCount(
		HttpPrincipal httpPrincipal, long groupId, long userId,
		java.util.Date modifiedDate, boolean includeAnonymous, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getGroupThreadsCount",
				_getGroupThreadsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, modifiedDate, includeAnonymous,
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

	public static int getGroupThreadsCount(
		HttpPrincipal httpPrincipal, long groupId, long userId,
		java.util.Date modifiedDate, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getGroupThreadsCount",
				_getGroupThreadsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, modifiedDate, status);

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

	public static int getGroupThreadsCount(
		HttpPrincipal httpPrincipal, long groupId, long userId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getGroupThreadsCount",
				_getGroupThreadsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, status);

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

	public static int getGroupThreadsCount(
		HttpPrincipal httpPrincipal, long groupId, long userId, int status,
		boolean subscribed) {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getGroupThreadsCount",
				_getGroupThreadsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, status, subscribed);

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

	public static int getGroupThreadsCount(
		HttpPrincipal httpPrincipal, long groupId, long userId, int status,
		boolean subscribed, boolean includeAnonymous) {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getGroupThreadsCount",
				_getGroupThreadsCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, status, subscribed,
				includeAnonymous);

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

	public static java.util.List<com.liferay.message.boards.model.MBThread>
		getThreads(
			HttpPrincipal httpPrincipal, long groupId, long categoryId,
			int status, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getThreads",
				_getThreadsParameterTypes11);

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

			return (java.util.List<com.liferay.message.boards.model.MBThread>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.message.boards.model.MBThread>
			getThreads(
				HttpPrincipal httpPrincipal, long groupId, long categoryId,
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.message.boards.model.MBThread> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getThreads",
				_getThreadsParameterTypes12);

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

			return (java.util.List<com.liferay.message.boards.model.MBThread>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getThreadsCount(
		HttpPrincipal httpPrincipal, long groupId, long categoryId,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getThreadsCount",
				_getThreadsCountParameterTypes13);

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

	public static int getThreadsCount(
			HttpPrincipal httpPrincipal, long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.message.boards.model.MBThread> queryDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "getThreadsCount",
				_getThreadsCountParameterTypes14);

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

	public static com.liferay.portal.kernel.lock.Lock lockThread(
			HttpPrincipal httpPrincipal, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "lockThread",
				_lockThreadParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, threadId);

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

			return (com.liferay.portal.kernel.lock.Lock)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBThread moveThread(
			HttpPrincipal httpPrincipal, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "moveThread",
				_moveThreadParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryId, threadId);

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

			return (com.liferay.message.boards.model.MBThread)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBThread moveThreadFromTrash(
			HttpPrincipal httpPrincipal, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "moveThreadFromTrash",
				_moveThreadFromTrashParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, categoryId, threadId);

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

			return (com.liferay.message.boards.model.MBThread)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBThread moveThreadToTrash(
			HttpPrincipal httpPrincipal, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "moveThreadToTrash",
				_moveThreadToTrashParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, threadId);

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

			return (com.liferay.message.boards.model.MBThread)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreThreadFromTrash(
			HttpPrincipal httpPrincipal, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "restoreThreadFromTrash",
				_restoreThreadFromTrashParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, threadId);

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

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long groupId, long creatorUserId,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "search", _searchParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, creatorUserId, status, start, end);

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

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
			HttpPrincipal httpPrincipal, long groupId, long creatorUserId,
			long startDate, long endDate, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "search", _searchParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, creatorUserId, startDate, endDate, status,
				start, end);

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

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.message.boards.model.MBThread splitThread(
			HttpPrincipal httpPrincipal, long messageId, String subject,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "splitThread",
				_splitThreadParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId, subject, serviceContext);

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

			return (com.liferay.message.boards.model.MBThread)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unlockThread(HttpPrincipal httpPrincipal, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBThreadServiceUtil.class, "unlockThread",
				_unlockThreadParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, threadId);

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

	private static Log _log = LogFactoryUtil.getLog(MBThreadServiceHttp.class);

	private static final Class<?>[] _deleteThreadParameterTypes0 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getGroupThreadsParameterTypes1 =
		new Class[] {
			long.class, long.class, java.util.Date.class, boolean.class,
			int.class, int.class, int.class
		};
	private static final Class<?>[] _getGroupThreadsParameterTypes2 =
		new Class[] {
			long.class, long.class, java.util.Date.class, int.class, int.class,
			int.class
		};
	private static final Class<?>[] _getGroupThreadsParameterTypes3 =
		new Class[] {
			long.class, long.class, int.class, boolean.class, boolean.class,
			int.class, int.class
		};
	private static final Class<?>[] _getGroupThreadsParameterTypes4 =
		new Class[] {
			long.class, long.class, int.class, boolean.class, int.class,
			int.class
		};
	private static final Class<?>[] _getGroupThreadsParameterTypes5 =
		new Class[] {long.class, long.class, int.class, int.class, int.class};
	private static final Class<?>[] _getGroupThreadsCountParameterTypes6 =
		new Class[] {
			long.class, long.class, java.util.Date.class, boolean.class,
			int.class
		};
	private static final Class<?>[] _getGroupThreadsCountParameterTypes7 =
		new Class[] {long.class, long.class, java.util.Date.class, int.class};
	private static final Class<?>[] _getGroupThreadsCountParameterTypes8 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _getGroupThreadsCountParameterTypes9 =
		new Class[] {long.class, long.class, int.class, boolean.class};
	private static final Class<?>[] _getGroupThreadsCountParameterTypes10 =
		new Class[] {
			long.class, long.class, int.class, boolean.class, boolean.class
		};
	private static final Class<?>[] _getThreadsParameterTypes11 = new Class[] {
		long.class, long.class, int.class, int.class, int.class
	};
	private static final Class<?>[] _getThreadsParameterTypes12 = new Class[] {
		long.class, long.class,
		com.liferay.portal.kernel.dao.orm.QueryDefinition.class
	};
	private static final Class<?>[] _getThreadsCountParameterTypes13 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _getThreadsCountParameterTypes14 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.dao.orm.QueryDefinition.class
		};
	private static final Class<?>[] _lockThreadParameterTypes15 = new Class[] {
		long.class
	};
	private static final Class<?>[] _moveThreadParameterTypes16 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _moveThreadFromTrashParameterTypes17 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _moveThreadToTrashParameterTypes18 =
		new Class[] {long.class};
	private static final Class<?>[] _restoreThreadFromTrashParameterTypes19 =
		new Class[] {long.class};
	private static final Class<?>[] _searchParameterTypes20 = new Class[] {
		long.class, long.class, int.class, int.class, int.class
	};
	private static final Class<?>[] _searchParameterTypes21 = new Class[] {
		long.class, long.class, long.class, long.class, int.class, int.class,
		int.class
	};
	private static final Class<?>[] _splitThreadParameterTypes22 = new Class[] {
		long.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _unlockThreadParameterTypes23 =
		new Class[] {long.class};

}
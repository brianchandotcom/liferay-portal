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

package com.liferay.journal.service.http;

import com.liferay.journal.service.JournalFeedServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>JournalFeedServiceUtil</code> service
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
 * @see JournalFeedServiceSoap
 * @generated
 */
public class JournalFeedServiceHttp {

	public static com.liferay.journal.model.JournalFeed addFeed(
			HttpPrincipal httpPrincipal, long groupId, String feedId,
			boolean autoFeedId, String name, String description,
			String ddmStructureKey, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalFeedServiceUtil.class, "addFeed",
				_addFeedParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, feedId, autoFeedId, name, description,
				ddmStructureKey, ddmTemplateKey, ddmRendererTemplateKey, delta,
				orderByCol, orderByType, targetLayoutFriendlyUrl,
				targetPortletId, contentField, feedType, feedVersion,
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

			return (com.liferay.journal.model.JournalFeed)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFeed(HttpPrincipal httpPrincipal, long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalFeedServiceUtil.class, "deleteFeed",
				_deleteFeedParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, feedId);

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

	public static void deleteFeed(
			HttpPrincipal httpPrincipal, long groupId, String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalFeedServiceUtil.class, "deleteFeed",
				_deleteFeedParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, feedId);

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

	public static com.liferay.journal.model.JournalFeed getFeed(
			HttpPrincipal httpPrincipal, long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalFeedServiceUtil.class, "getFeed",
				_getFeedParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, feedId);

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

			return (com.liferay.journal.model.JournalFeed)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalFeed getFeed(
			HttpPrincipal httpPrincipal, long groupId, String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalFeedServiceUtil.class, "getFeed",
				_getFeedParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, feedId);

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

			return (com.liferay.journal.model.JournalFeed)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.journal.model.JournalFeed updateFeed(
			HttpPrincipal httpPrincipal, long groupId, String feedId,
			String name, String description, String ddmStructureKey,
			String ddmTemplateKey, String ddmRendererTemplateKey, int delta,
			String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				JournalFeedServiceUtil.class, "updateFeed",
				_updateFeedParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, feedId, name, description, ddmStructureKey,
				ddmTemplateKey, ddmRendererTemplateKey, delta, orderByCol,
				orderByType, targetLayoutFriendlyUrl, targetPortletId,
				contentField, feedType, feedVersion, serviceContext);

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

			return (com.liferay.journal.model.JournalFeed)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalFeedServiceHttp.class);

	private static final Class<?>[] _addFeedParameterTypes0 = new Class[] {
		long.class, String.class, boolean.class, String.class, String.class,
		String.class, String.class, String.class, int.class, String.class,
		String.class, String.class, String.class, String.class, String.class,
		double.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteFeedParameterTypes1 = new Class[] {
		long.class
	};
	private static final Class<?>[] _deleteFeedParameterTypes2 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _getFeedParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getFeedParameterTypes4 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _updateFeedParameterTypes5 = new Class[] {
		long.class, String.class, String.class, String.class, String.class,
		String.class, String.class, int.class, String.class, String.class,
		String.class, String.class, String.class, String.class, double.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};

}
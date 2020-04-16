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

package com.liferay.redirect.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.redirect.service.RedirectEntryServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>RedirectEntryServiceUtil</code> service
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
 * @see RedirectEntryServiceSoap
 * @generated
 */
public class RedirectEntryServiceHttp {

	public static com.liferay.redirect.model.RedirectEntry addRedirectEntry(
			HttpPrincipal httpPrincipal, long groupId, String destinationURL,
			java.util.Date expirationDate, boolean permanent, String sourceURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RedirectEntryServiceUtil.class, "addRedirectEntry",
				_addRedirectEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, destinationURL, expirationDate, permanent,
				sourceURL, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.redirect.model.RedirectEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.redirect.model.RedirectEntry deleteRedirectEntry(
			HttpPrincipal httpPrincipal, long redirectEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RedirectEntryServiceUtil.class, "deleteRedirectEntry",
				_deleteRedirectEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, redirectEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.redirect.model.RedirectEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.redirect.model.RedirectEntry fetchRedirectEntry(
			HttpPrincipal httpPrincipal, long redirectEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RedirectEntryServiceUtil.class, "fetchRedirectEntry",
				_fetchRedirectEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, redirectEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.redirect.model.RedirectEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.redirect.model.RedirectEntry>
			getRedirectEntries(
				HttpPrincipal httpPrincipal, long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.redirect.model.RedirectEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RedirectEntryServiceUtil.class, "getRedirectEntries",
				_getRedirectEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.redirect.model.RedirectEntry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getRedirectEntriesCount(
			HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RedirectEntryServiceUtil.class, "getRedirectEntriesCount",
				_getRedirectEntriesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.redirect.model.RedirectEntry>
		getRedirectEntriesDestinationURL(
			HttpPrincipal httpPrincipal, String destinationURL, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				RedirectEntryServiceUtil.class,
				"getRedirectEntriesDestinationURL",
				_getRedirectEntriesDestinationURLParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, destinationURL, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.redirect.model.RedirectEntry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.redirect.model.RedirectEntry updateRedirectEntry(
			HttpPrincipal httpPrincipal, long redirectEntryId,
			String destinationURL, java.util.Date expirationDate,
			boolean permanent, String sourceURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RedirectEntryServiceUtil.class, "updateRedirectEntry",
				_updateRedirectEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, redirectEntryId, destinationURL, expirationDate,
				permanent, sourceURL);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.redirect.model.RedirectEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		RedirectEntryServiceHttp.class);

	private static final Class<?>[] _addRedirectEntryParameterTypes0 =
		new Class[] {
			long.class, String.class, java.util.Date.class, boolean.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteRedirectEntryParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchRedirectEntryParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getRedirectEntriesParameterTypes3 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getRedirectEntriesCountParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getRedirectEntriesDestinationURLParameterTypes5 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _updateRedirectEntryParameterTypes6 =
		new Class[] {
			long.class, String.class, java.util.Date.class, boolean.class,
			String.class
		};

}
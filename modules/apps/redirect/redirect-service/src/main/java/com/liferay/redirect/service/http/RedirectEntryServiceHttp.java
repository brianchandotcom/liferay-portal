/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static com.liferay.redirect.model.RedirectEntry addRedirectEntry(
			HttpPrincipal httpPrincipal, long groupId, String destinationURL,
			java.util.Date expirationDate, String groupBaseURL,
			boolean permanent, String sourceURL,
			boolean updateChainedRedirectEntries,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RedirectEntryServiceUtil.class, "addRedirectEntry",
				_addRedirectEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, destinationURL, expirationDate,
				groupBaseURL, permanent, sourceURL,
				updateChainedRedirectEntries, serviceContext);

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
				_deleteRedirectEntryParameterTypes2);

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
				_fetchRedirectEntryParameterTypes3);

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
					<com.liferay.redirect.model.RedirectEntry>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RedirectEntryServiceUtil.class, "getRedirectEntries",
				_getRedirectEntriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end, orderByComparator);

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
				_getRedirectEntriesCountParameterTypes5);

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

	public static com.liferay.redirect.model.RedirectEntry updateRedirectEntry(
			HttpPrincipal httpPrincipal, long redirectEntryId,
			String destinationURL, java.util.Date expirationDate,
			String groupBaseURL, boolean permanent, String sourceURL,
			boolean updateChainedRedirectEntries)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RedirectEntryServiceUtil.class, "updateRedirectEntry",
				_updateRedirectEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, redirectEntryId, destinationURL, expirationDate,
				groupBaseURL, permanent, sourceURL,
				updateChainedRedirectEntries);

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
	private static final Class<?>[] _addRedirectEntryParameterTypes1 =
		new Class[] {
			long.class, String.class, java.util.Date.class, String.class,
			boolean.class, String.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteRedirectEntryParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchRedirectEntryParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getRedirectEntriesParameterTypes4 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getRedirectEntriesCountParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _updateRedirectEntryParameterTypes6 =
		new Class[] {
			long.class, String.class, java.util.Date.class, boolean.class,
			String.class
		};
	private static final Class<?>[] _updateRedirectEntryParameterTypes7 =
		new Class[] {
			long.class, String.class, java.util.Date.class, String.class,
			boolean.class, String.class, boolean.class
		};

}
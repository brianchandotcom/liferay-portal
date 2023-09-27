/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.style.book.service.StyleBookEntryServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>StyleBookEntryServiceUtil</code> service
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
public class StyleBookEntryServiceHttp {

	public static com.liferay.style.book.model.StyleBookEntry addStyleBookEntry(
			HttpPrincipal httpPrincipal, long groupId, String name,
			String styleBookEntryKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "addStyleBookEntry",
				_addStyleBookEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, styleBookEntryKey, serviceContext);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry addStyleBookEntry(
			HttpPrincipal httpPrincipal, long groupId,
			String frontendTokensValues, String name, String styleBookEntryKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "addStyleBookEntry",
				_addStyleBookEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, frontendTokensValues, name,
				styleBookEntryKey, serviceContext);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry
			copyStyleBookEntry(
				HttpPrincipal httpPrincipal, long groupId,
				long sourceStyleBookEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "copyStyleBookEntry",
				_copyStyleBookEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, sourceStyleBookEntryId, serviceContext);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry
			deleteStyleBookEntry(
				HttpPrincipal httpPrincipal, long styleBookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "deleteStyleBookEntry",
				_deleteStyleBookEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, styleBookEntryId);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry
			deleteStyleBookEntry(
				HttpPrincipal httpPrincipal,
				com.liferay.style.book.model.StyleBookEntry styleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "deleteStyleBookEntry",
				_deleteStyleBookEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, styleBookEntry);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry
			discardDraftStyleBookEntry(
				HttpPrincipal httpPrincipal, long styleBookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "discardDraftStyleBookEntry",
				_discardDraftStyleBookEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, styleBookEntryId);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry publishDraft(
			HttpPrincipal httpPrincipal, long styleBookEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "publishDraft",
				_publishDraftParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, styleBookEntryId);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry
			updateDefaultStyleBookEntry(
				HttpPrincipal httpPrincipal, long styleBookEntryId,
				boolean defaultStyleBookEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "updateDefaultStyleBookEntry",
				_updateDefaultStyleBookEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, styleBookEntryId, defaultStyleBookEntry);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry
			updateFrontendTokensValues(
				HttpPrincipal httpPrincipal, long styleBookEntryId,
				String frontendTokensValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "updateFrontendTokensValues",
				_updateFrontendTokensValuesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, styleBookEntryId, frontendTokensValues);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry updateName(
			HttpPrincipal httpPrincipal, long styleBookEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "updateName",
				_updateNameParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, styleBookEntryId, name);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry
			updatePreviewFileEntryId(
				HttpPrincipal httpPrincipal, long styleBookEntryId,
				long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "updatePreviewFileEntryId",
				_updatePreviewFileEntryIdParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, styleBookEntryId, previewFileEntryId);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.style.book.model.StyleBookEntry
			updateStyleBookEntry(
				HttpPrincipal httpPrincipal, long styleBookEntryId,
				String frontendTokensValues, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StyleBookEntryServiceUtil.class, "updateStyleBookEntry",
				_updateStyleBookEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, styleBookEntryId, frontendTokensValues, name);

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

			return (com.liferay.style.book.model.StyleBookEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		StyleBookEntryServiceHttp.class);

	private static final Class<?>[] _addStyleBookEntryParameterTypes0 =
		new Class[] {
			long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addStyleBookEntryParameterTypes1 =
		new Class[] {
			long.class, String.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _copyStyleBookEntryParameterTypes2 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteStyleBookEntryParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteStyleBookEntryParameterTypes4 =
		new Class[] {com.liferay.style.book.model.StyleBookEntry.class};
	private static final Class<?>[] _discardDraftStyleBookEntryParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _publishDraftParameterTypes6 = new Class[] {
		long.class
	};
	private static final Class<?>[]
		_updateDefaultStyleBookEntryParameterTypes7 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateFrontendTokensValuesParameterTypes8 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateNameParameterTypes9 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _updatePreviewFileEntryIdParameterTypes10 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _updateStyleBookEntryParameterTypes11 =
		new Class[] {long.class, String.class, String.class};

}
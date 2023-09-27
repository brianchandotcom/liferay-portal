/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.reports.engine.console.service.EntryServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>EntryServiceUtil</code> service
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
public class EntryServiceHttp {

	public static com.liferay.portal.reports.engine.console.model.Entry
			addEntry(
				HttpPrincipal httpPrincipal, long groupId, long definitionId,
				String format, boolean schedulerRequest,
				java.util.Date startDate, java.util.Date endDate,
				boolean repeating, String recurrence, String emailNotifications,
				String emailDelivery, String portletId, String pageURL,
				String reportName, String reportParameters,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EntryServiceUtil.class, "addEntry", _addEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, definitionId, format, schedulerRequest,
				startDate, endDate, repeating, recurrence, emailNotifications,
				emailDelivery, portletId, pageURL, reportName, reportParameters,
				serviceContext);

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

			return (com.liferay.portal.reports.engine.console.model.Entry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteAttachment(
			HttpPrincipal httpPrincipal, long companyId, long entryId,
			String fileName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EntryServiceUtil.class, "deleteAttachment",
				_deleteAttachmentParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, entryId, fileName);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
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
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.reports.engine.console.model.Entry
			deleteEntry(HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EntryServiceUtil.class, "deleteEntry",
				_deleteEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId);

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

			return (com.liferay.portal.reports.engine.console.model.Entry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.reports.engine.console.model.Entry> getEntries(
				HttpPrincipal httpPrincipal, long groupId,
				String definitionName, String userName,
				java.util.Date createDateGT, java.util.Date createDateLT,
				boolean andSearch, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.reports.engine.console.model.Entry>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EntryServiceUtil.class, "getEntries",
				_getEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, definitionName, userName, createDateGT,
				createDateLT, andSearch, start, end, orderByComparator);

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

			return (java.util.List
				<com.liferay.portal.reports.engine.console.model.Entry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getEntriesCount(
			HttpPrincipal httpPrincipal, long groupId, String definitionName,
			String userName, java.util.Date createDateGT,
			java.util.Date createDateLT, boolean andSearch)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EntryServiceUtil.class, "getEntriesCount",
				_getEntriesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, definitionName, userName, createDateGT,
				createDateLT, andSearch);

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

	public static void sendEmails(
			HttpPrincipal httpPrincipal, long entryId, String fileName,
			String[] emailAddresses, boolean notification)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EntryServiceUtil.class, "sendEmails",
				_sendEmailsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, entryId, fileName, emailAddresses, notification);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
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
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void unscheduleEntry(
			HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EntryServiceUtil.class, "unscheduleEntry",
				_unscheduleEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
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
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(EntryServiceHttp.class);

	private static final Class<?>[] _addEntryParameterTypes0 = new Class[] {
		long.class, long.class, String.class, boolean.class,
		java.util.Date.class, java.util.Date.class, boolean.class, String.class,
		String.class, String.class, String.class, String.class, String.class,
		String.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteAttachmentParameterTypes1 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _deleteEntryParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getEntriesParameterTypes3 = new Class[] {
		long.class, String.class, String.class, java.util.Date.class,
		java.util.Date.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getEntriesCountParameterTypes4 =
		new Class[] {
			long.class, String.class, String.class, java.util.Date.class,
			java.util.Date.class, boolean.class
		};
	private static final Class<?>[] _sendEmailsParameterTypes5 = new Class[] {
		long.class, String.class, String[].class, boolean.class
	};
	private static final Class<?>[] _unscheduleEntryParameterTypes6 =
		new Class[] {long.class};

}
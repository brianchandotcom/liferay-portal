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

import com.liferay.commerce.product.service.CPAttachmentFileEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPAttachmentFileEntryServiceUtil</code> service
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
 * @author Marco Leo
 * @see CPAttachmentFileEntryServiceSoap
 * @generated
 */
public class CPAttachmentFileEntryServiceHttp {

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry
			addCPAttachmentFileEntry(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				long classPK, long fileEntryId, boolean cdnEnabled,
				String cdnURL, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				java.util.Map<java.util.Locale, String> titleMap, String json,
				double priority, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPAttachmentFileEntryServiceUtil.class,
				"addCPAttachmentFileEntry",
				_addCPAttachmentFileEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, classPK, fileEntryId,
				cdnEnabled, cdnURL, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire, titleMap,
				json, priority, type, serviceContext);

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

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry
			addOrUpdateCPAttachmentFileEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long classNameId, long classPK,
				long cpAttachmentFileEntryId, long fileEntryId,
				boolean cdnEnabled, String cdnURL, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				java.util.Map<java.util.Locale, String> titleMap, String json,
				double priority, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPAttachmentFileEntryServiceUtil.class,
				"addOrUpdateCPAttachmentFileEntry",
				_addOrUpdateCPAttachmentFileEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, classNameId, classPK,
				cpAttachmentFileEntryId, fileEntryId, cdnEnabled, cdnURL,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, titleMap, json, priority,
				type, serviceContext);

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

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCPAttachmentFileEntry(
			HttpPrincipal httpPrincipal, long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPAttachmentFileEntryServiceUtil.class,
				"deleteCPAttachmentFileEntry",
				_deleteCPAttachmentFileEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpAttachmentFileEntryId);

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

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry
			fetchByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPAttachmentFileEntryServiceUtil.class,
				"fetchByExternalReferenceCode",
				_fetchByExternalReferenceCodeParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId);

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

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry
			fetchCPAttachmentFileEntry(
				HttpPrincipal httpPrincipal, long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPAttachmentFileEntryServiceUtil.class,
				"fetchCPAttachmentFileEntry",
				_fetchCPAttachmentFileEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpAttachmentFileEntryId);

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

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPAttachmentFileEntry>
				getCPAttachmentFileEntries(
					HttpPrincipal httpPrincipal, long classNameId, long classPK,
					int type, int status, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPAttachmentFileEntryServiceUtil.class,
				"getCPAttachmentFileEntries",
				_getCPAttachmentFileEntriesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, classPK, type, status, start, end);

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
				<com.liferay.commerce.product.model.CPAttachmentFileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPAttachmentFileEntry>
				getCPAttachmentFileEntries(
					HttpPrincipal httpPrincipal, long classNameId, long classPK,
					int type, int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.model.
							CPAttachmentFileEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPAttachmentFileEntryServiceUtil.class,
				"getCPAttachmentFileEntries",
				_getCPAttachmentFileEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, classPK, type, status, start, end,
				orderByComparator);

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
				<com.liferay.commerce.product.model.CPAttachmentFileEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPAttachmentFileEntriesCount(
			HttpPrincipal httpPrincipal, long classNameId, long classPK,
			int type, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPAttachmentFileEntryServiceUtil.class,
				"getCPAttachmentFileEntriesCount",
				_getCPAttachmentFileEntriesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, classPK, type, status);

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

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry
			getCPAttachmentFileEntry(
				HttpPrincipal httpPrincipal, long cpAttachmentFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPAttachmentFileEntryServiceUtil.class,
				"getCPAttachmentFileEntry",
				_getCPAttachmentFileEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpAttachmentFileEntryId);

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

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry
			updateCPAttachmentFileEntry(
				HttpPrincipal httpPrincipal, long cpAttachmentFileEntryId,
				long fileEntryId, boolean cdnEnabled, String cdnURL,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				java.util.Map<java.util.Locale, String> titleMap, String json,
				double priority, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPAttachmentFileEntryServiceUtil.class,
				"updateCPAttachmentFileEntry",
				_updateCPAttachmentFileEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpAttachmentFileEntryId, fileEntryId, cdnEnabled,
				cdnURL, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, titleMap, json, priority,
				type, serviceContext);

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

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPAttachmentFileEntryServiceHttp.class);

	private static final Class<?>[] _addCPAttachmentFileEntryParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, long.class, boolean.class,
			String.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class,
			boolean.class, java.util.Map.class, String.class, double.class,
			int.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_addOrUpdateCPAttachmentFileEntryParameterTypes1 = new Class[] {
			String.class, long.class, long.class, long.class, long.class,
			long.class, boolean.class, String.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class, java.util.Map.class,
			String.class, double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCPAttachmentFileEntryParameterTypes2 = new Class[] {long.class};
	private static final Class<?>[]
		_fetchByExternalReferenceCodeParameterTypes3 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _fetchCPAttachmentFileEntryParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getCPAttachmentFileEntriesParameterTypes5 =
		new Class[] {
			long.class, long.class, int.class, int.class, int.class, int.class
		};
	private static final Class<?>[] _getCPAttachmentFileEntriesParameterTypes6 =
		new Class[] {
			long.class, long.class, int.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCPAttachmentFileEntriesCountParameterTypes7 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getCPAttachmentFileEntryParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[]
		_updateCPAttachmentFileEntryParameterTypes9 = new Class[] {
			long.class, long.class, boolean.class, String.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class, java.util.Map.class,
			String.class, double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
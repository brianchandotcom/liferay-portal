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

package com.liferay.list.type.service.http;

import com.liferay.list.type.service.ListTypeEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ListTypeEntryServiceUtil</code> service
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
 * @author Gabriel Albuquerque
 * @generated
 */
public class ListTypeEntryServiceHttp {

	public static com.liferay.list.type.model.ListTypeEntry addListTypeEntry(
			HttpPrincipal httpPrincipal, long listTypeDefinitionId, String key,
			java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeEntryServiceUtil.class, "addListTypeEntry",
				_addListTypeEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, listTypeDefinitionId, key, nameMap);

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

			return (com.liferay.list.type.model.ListTypeEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.list.type.model.ListTypeEntry deleteListTypeEntry(
			HttpPrincipal httpPrincipal, long listTypeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeEntryServiceUtil.class, "deleteListTypeEntry",
				_deleteListTypeEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, listTypeEntryId);

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

			return (com.liferay.list.type.model.ListTypeEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.list.type.model.ListTypeEntry>
			getListTypeEntries(
				HttpPrincipal httpPrincipal, long listTypeDefinitionId,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeEntryServiceUtil.class, "getListTypeEntries",
				_getListTypeEntriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, listTypeDefinitionId, start, end);

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

			return (java.util.List<com.liferay.list.type.model.ListTypeEntry>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getListTypeEntriesCount(
			HttpPrincipal httpPrincipal, long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeEntryServiceUtil.class, "getListTypeEntriesCount",
				_getListTypeEntriesCountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, listTypeDefinitionId);

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

	public static com.liferay.list.type.model.ListTypeEntry getListTypeEntry(
			HttpPrincipal httpPrincipal, long listTypeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeEntryServiceUtil.class, "getListTypeEntry",
				_getListTypeEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, listTypeEntryId);

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

			return (com.liferay.list.type.model.ListTypeEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.list.type.model.ListTypeEntry updateListTypeEntry(
			HttpPrincipal httpPrincipal, long listTypeEntryId,
			java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeEntryServiceUtil.class, "updateListTypeEntry",
				_updateListTypeEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, listTypeEntryId, nameMap);

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

			return (com.liferay.list.type.model.ListTypeEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ListTypeEntryServiceHttp.class);

	private static final Class<?>[] _addListTypeEntryParameterTypes0 =
		new Class[] {long.class, String.class, java.util.Map.class};
	private static final Class<?>[] _deleteListTypeEntryParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getListTypeEntriesParameterTypes2 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getListTypeEntriesCountParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getListTypeEntryParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _updateListTypeEntryParameterTypes5 =
		new Class[] {long.class, java.util.Map.class};

}
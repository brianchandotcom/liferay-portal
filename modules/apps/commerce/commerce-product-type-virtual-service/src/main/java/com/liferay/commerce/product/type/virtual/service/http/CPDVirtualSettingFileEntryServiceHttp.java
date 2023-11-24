/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.service.http;

import com.liferay.commerce.product.type.virtual.service.CPDVirtualSettingFileEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDVirtualSettingFileEntryServiceUtil</code> service
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
 * @generated
 */
public class CPDVirtualSettingFileEntryServiceHttp {

	public static
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry addCPDefinitionVirtualSetting(
					HttpPrincipal httpPrincipal, long groupId, String className,
					long classPK, long cpDefinitionVirtualSettingId,
					long fileEntryId, String url, String version)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDVirtualSettingFileEntryServiceUtil.class,
				"addCPDefinitionVirtualSetting",
				_addCPDefinitionVirtualSettingParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, className, classPK,
				cpDefinitionVirtualSettingId, fileEntryId, url, version);

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

			return (com.liferay.commerce.product.type.virtual.model.
				CPDVirtualSettingFileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry deleteCPDVirtualSettingFileEntry(
					HttpPrincipal httpPrincipal, String className, long classPK,
					long cpdVirtualSettingFileEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDVirtualSettingFileEntryServiceUtil.class,
				"deleteCPDVirtualSettingFileEntry",
				_deleteCPDVirtualSettingFileEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, cpdVirtualSettingFileEntryId);

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

			return (com.liferay.commerce.product.type.virtual.model.
				CPDVirtualSettingFileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry fetchCPDVirtualSettingFileEntry(
					HttpPrincipal httpPrincipal,
					long cpdVirtualSettingFileEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDVirtualSettingFileEntryServiceUtil.class,
				"fetchCPDVirtualSettingFileEntry",
				_fetchCPDVirtualSettingFileEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpdVirtualSettingFileEntryId);

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

			return (com.liferay.commerce.product.type.virtual.model.
				CPDVirtualSettingFileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry> getCPDVirtualSettingFileEntries(
					HttpPrincipal httpPrincipal, String className, long classPK,
					long cpDefinitionVirtualSettingId, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDVirtualSettingFileEntryServiceUtil.class,
				"getCPDVirtualSettingFileEntries",
				_getCPDVirtualSettingFileEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, cpDefinitionVirtualSettingId,
				start, end);

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
				<com.liferay.commerce.product.type.virtual.model.
					CPDVirtualSettingFileEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry getCPDVirtualSettingFileEntry(
					HttpPrincipal httpPrincipal,
					long cpdVirtualSettingFileEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDVirtualSettingFileEntryServiceUtil.class,
				"getCPDVirtualSettingFileEntry",
				_getCPDVirtualSettingFileEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpdVirtualSettingFileEntryId);

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

			return (com.liferay.commerce.product.type.virtual.model.
				CPDVirtualSettingFileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.type.virtual.model.
			CPDVirtualSettingFileEntry updateCPDefinitionVirtualSetting(
					HttpPrincipal httpPrincipal,
					long cpdVirtualSettingFileEntryId, long fileEntryId,
					String url, String version)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDVirtualSettingFileEntryServiceUtil.class,
				"updateCPDefinitionVirtualSetting",
				_updateCPDefinitionVirtualSettingParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpdVirtualSettingFileEntryId, fileEntryId, url,
				version);

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

			return (com.liferay.commerce.product.type.virtual.model.
				CPDVirtualSettingFileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDVirtualSettingFileEntryServiceHttp.class);

	private static final Class<?>[]
		_addCPDefinitionVirtualSettingParameterTypes0 = new Class[] {
			long.class, String.class, long.class, long.class, long.class,
			String.class, String.class
		};
	private static final Class<?>[]
		_deleteCPDVirtualSettingFileEntryParameterTypes1 = new Class[] {
			String.class, long.class, long.class
		};
	private static final Class<?>[]
		_fetchCPDVirtualSettingFileEntryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCPDVirtualSettingFileEntriesParameterTypes3 = new Class[] {
			String.class, long.class, long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCPDVirtualSettingFileEntryParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_updateCPDefinitionVirtualSettingParameterTypes5 = new Class[] {
			long.class, long.class, String.class, String.class
		};

}
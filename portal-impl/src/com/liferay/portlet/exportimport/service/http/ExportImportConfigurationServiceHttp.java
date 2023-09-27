/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.exportimport.service.http;

import com.liferay.exportimport.kernel.service.ExportImportConfigurationServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ExportImportConfigurationServiceUtil</code> service
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
public class ExportImportConfigurationServiceHttp {

	public static void deleteExportImportConfiguration(
			HttpPrincipal httpPrincipal, long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportConfigurationServiceUtil.class,
				"deleteExportImportConfiguration",
				_deleteExportImportConfigurationParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfigurationId);

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

	public static
		com.liferay.exportimport.kernel.model.ExportImportConfiguration
				moveExportImportConfigurationToTrash(
					HttpPrincipal httpPrincipal,
					long exportImportConfigurationId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportConfigurationServiceUtil.class,
				"moveExportImportConfigurationToTrash",
				_moveExportImportConfigurationToTrashParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfigurationId);

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

			return (com.liferay.exportimport.kernel.model.
				ExportImportConfiguration)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.exportimport.kernel.model.ExportImportConfiguration
				restoreExportImportConfigurationFromTrash(
					HttpPrincipal httpPrincipal,
					long exportImportConfigurationId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportConfigurationServiceUtil.class,
				"restoreExportImportConfigurationFromTrash",
				_restoreExportImportConfigurationFromTrashParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfigurationId);

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

			return (com.liferay.exportimport.kernel.model.
				ExportImportConfiguration)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ExportImportConfigurationServiceHttp.class);

	private static final Class<?>[]
		_deleteExportImportConfigurationParameterTypes0 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_moveExportImportConfigurationToTrashParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_restoreExportImportConfigurationFromTrashParameterTypes2 =
			new Class[] {long.class};

}
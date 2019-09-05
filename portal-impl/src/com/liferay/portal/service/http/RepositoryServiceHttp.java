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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.RepositoryServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>RepositoryServiceUtil</code> service
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
 * @see RepositoryServiceSoap
 * @generated
 */
public class RepositoryServiceHttp {

	public static com.liferay.portal.kernel.model.Repository addRepository(
			HttpPrincipal httpPrincipal, long groupId, long classNameId,
			long parentFolderId, String name, String description,
			String portletId,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RepositoryServiceUtil.class, "addRepository",
				_addRepositoryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, parentFolderId, name,
				description, portletId, typeSettingsProperties, serviceContext);

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

			return (com.liferay.portal.kernel.model.Repository)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkRepository(
			HttpPrincipal httpPrincipal, long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RepositoryServiceUtil.class, "checkRepository",
				_checkRepositoryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId);

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

	public static void deleteRepository(
			HttpPrincipal httpPrincipal, long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RepositoryServiceUtil.class, "deleteRepository",
				_deleteRepositoryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId);

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

	public static com.liferay.portal.kernel.model.Repository getRepository(
			HttpPrincipal httpPrincipal, long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RepositoryServiceUtil.class, "getRepository",
				_getRepositoryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId);

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

			return (com.liferay.portal.kernel.model.Repository)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.util.UnicodeProperties
			getTypeSettingsProperties(
				HttpPrincipal httpPrincipal, long repositoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RepositoryServiceUtil.class, "getTypeSettingsProperties",
				_getTypeSettingsPropertiesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId);

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

			return (com.liferay.portal.kernel.util.UnicodeProperties)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateRepository(
			HttpPrincipal httpPrincipal, long repositoryId, String name,
			String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RepositoryServiceUtil.class, "updateRepository",
				_updateRepositoryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, repositoryId, name, description);

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

	private static Log _log = LogFactoryUtil.getLog(
		RepositoryServiceHttp.class);

	private static final Class<?>[] _addRepositoryParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, String.class, String.class,
			String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _checkRepositoryParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteRepositoryParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getRepositoryParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getTypeSettingsPropertiesParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _updateRepositoryParameterTypes5 =
		new Class[] {long.class, String.class, String.class};

}
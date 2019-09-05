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

package com.liferay.portal.security.wedeploy.auth.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.wedeploy.auth.service.WeDeployAuthAppServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>WeDeployAuthAppServiceUtil</code> service
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
 * @author Supritha Sundaram
 * @see WeDeployAuthAppServiceSoap
 * @generated
 */
public class WeDeployAuthAppServiceHttp {

	public static
		com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp
				addWeDeployAuthApp(
					HttpPrincipal httpPrincipal, String name,
					String redirectURI,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WeDeployAuthAppServiceUtil.class, "addWeDeployAuthApp",
				_addWeDeployAuthAppParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, name, redirectURI, serviceContext);

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

			return (com.liferay.portal.security.wedeploy.auth.model.
				WeDeployAuthApp)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static
		com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp
				deleteWeDeployAuthApp(
					HttpPrincipal httpPrincipal, long weDeployAuthAppId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				WeDeployAuthAppServiceUtil.class, "deleteWeDeployAuthApp",
				_deleteWeDeployAuthAppParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, weDeployAuthAppId);

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

			return (com.liferay.portal.security.wedeploy.auth.model.
				WeDeployAuthApp)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		WeDeployAuthAppServiceHttp.class);

	private static final Class<?>[] _addWeDeployAuthAppParameterTypes0 =
		new Class[] {
			String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteWeDeployAuthAppParameterTypes1 =
		new Class[] {long.class};

}
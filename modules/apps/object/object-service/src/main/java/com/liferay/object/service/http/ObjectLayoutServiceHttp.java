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

package com.liferay.object.service.http;

import com.liferay.object.service.ObjectLayoutServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ObjectLayoutServiceUtil</code> service
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
 * @see ObjectLayoutServiceSoap
 * @generated
 */
public class ObjectLayoutServiceHttp {

	public static com.liferay.object.model.ObjectLayout addObjectLayout(
			HttpPrincipal httpPrincipal, long objectDefinitionId,
			boolean defaultObjectLayout,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.List<com.liferay.object.model.ObjectLayoutTab>
				objectLayoutTabs)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectLayoutServiceUtil.class, "addObjectLayout",
				_addObjectLayoutParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectDefinitionId, defaultObjectLayout, nameMap,
				objectLayoutTabs);

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

			return (com.liferay.object.model.ObjectLayout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectLayout deleteObjectLayout(
			HttpPrincipal httpPrincipal, long objectLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectLayoutServiceUtil.class, "deleteObjectLayout",
				_deleteObjectLayoutParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectLayoutId);

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

			return (com.liferay.object.model.ObjectLayout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectLayout getObjectLayout(
			HttpPrincipal httpPrincipal, long objectLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectLayoutServiceUtil.class, "getObjectLayout",
				_getObjectLayoutParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectLayoutId);

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

			return (com.liferay.object.model.ObjectLayout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectLayout updateObjectLayout(
			HttpPrincipal httpPrincipal, long objectLayoutId,
			boolean defaultObjectLayout,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.List<com.liferay.object.model.ObjectLayoutTab>
				objectLayoutTabs)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectLayoutServiceUtil.class, "updateObjectLayout",
				_updateObjectLayoutParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectLayoutId, defaultObjectLayout, nameMap,
				objectLayoutTabs);

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

			return (com.liferay.object.model.ObjectLayout)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ObjectLayoutServiceHttp.class);

	private static final Class<?>[] _addObjectLayoutParameterTypes0 =
		new Class[] {
			long.class, boolean.class, java.util.Map.class, java.util.List.class
		};
	private static final Class<?>[] _deleteObjectLayoutParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getObjectLayoutParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _updateObjectLayoutParameterTypes3 =
		new Class[] {
			long.class, boolean.class, java.util.Map.class, java.util.List.class
		};

}
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

import com.liferay.object.service.ObjectFieldServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ObjectFieldServiceUtil</code> service
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
public class ObjectFieldServiceHttp {

	public static com.liferay.object.model.ObjectField addCustomObjectField(
			HttpPrincipal httpPrincipal, long listTypeDefinitionId,
			long objectDefinitionId, String businessType, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId,
			java.util.Map<java.util.Locale, String> labelMap, String name,
			boolean required, boolean state,
			java.util.List<com.liferay.object.model.ObjectFieldSetting>
				objectFieldSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectFieldServiceUtil.class, "addCustomObjectField",
				_addCustomObjectFieldParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, listTypeDefinitionId, objectDefinitionId,
				businessType, dbType, defaultValue, indexed, indexedAsKeyword,
				indexedLanguageId, labelMap, name, required, state,
				objectFieldSettings);

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

			return (com.liferay.object.model.ObjectField)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectField deleteObjectField(
			HttpPrincipal httpPrincipal, long objectFieldId)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectFieldServiceUtil.class, "deleteObjectField",
				_deleteObjectFieldParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectFieldId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof Exception) {
					throw (Exception)exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.object.model.ObjectField)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectField getObjectField(
			HttpPrincipal httpPrincipal, long objectFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectFieldServiceUtil.class, "getObjectField",
				_getObjectFieldParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectFieldId);

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

			return (com.liferay.object.model.ObjectField)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectField updateObjectField(
			HttpPrincipal httpPrincipal, long objectFieldId,
			String externalReferenceCode, long listTypeDefinitionId,
			String businessType, String dbType, String defaultValue,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			java.util.Map<java.util.Locale, String> labelMap, String name,
			boolean required, boolean state,
			java.util.List<com.liferay.object.model.ObjectFieldSetting>
				objectFieldSettings,
			com.liferay.object.model.ObjectStateFlow objectStateFlow)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectFieldServiceUtil.class, "updateObjectField",
				_updateObjectFieldParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectFieldId, externalReferenceCode,
				listTypeDefinitionId, businessType, dbType, defaultValue,
				indexed, indexedAsKeyword, indexedLanguageId, labelMap, name,
				required, state, objectFieldSettings, objectStateFlow);

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

			return (com.liferay.object.model.ObjectField)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ObjectFieldServiceHttp.class);

	private static final Class<?>[] _addCustomObjectFieldParameterTypes0 =
		new Class[] {
			long.class, long.class, String.class, String.class, String.class,
			boolean.class, boolean.class, String.class, java.util.Map.class,
			String.class, boolean.class, boolean.class, java.util.List.class
		};
	private static final Class<?>[] _deleteObjectFieldParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getObjectFieldParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _updateObjectFieldParameterTypes3 =
		new Class[] {
			long.class, String.class, long.class, String.class, String.class,
			String.class, boolean.class, boolean.class, String.class,
			java.util.Map.class, String.class, boolean.class, boolean.class,
			java.util.List.class, com.liferay.object.model.ObjectStateFlow.class
		};

}
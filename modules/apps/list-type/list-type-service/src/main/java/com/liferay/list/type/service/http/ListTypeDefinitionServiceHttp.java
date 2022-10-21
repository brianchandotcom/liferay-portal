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

import com.liferay.list.type.service.ListTypeDefinitionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ListTypeDefinitionServiceUtil</code> service
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
public class ListTypeDefinitionServiceHttp {

	public static com.liferay.list.type.model.ListTypeDefinition
			addListTypeDefinition(
				HttpPrincipal httpPrincipal,
				java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeDefinitionServiceUtil.class, "addListTypeDefinition",
				_addListTypeDefinitionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, nameMap);

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

			return (com.liferay.list.type.model.ListTypeDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.list.type.model.ListTypeDefinition
			deleteListTypeDefinition(
				HttpPrincipal httpPrincipal,
				com.liferay.list.type.model.ListTypeDefinition
					listTypeDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeDefinitionServiceUtil.class, "deleteListTypeDefinition",
				_deleteListTypeDefinitionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, listTypeDefinition);

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

			return (com.liferay.list.type.model.ListTypeDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.list.type.model.ListTypeDefinition
			deleteListTypeDefinition(
				HttpPrincipal httpPrincipal, long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeDefinitionServiceUtil.class, "deleteListTypeDefinition",
				_deleteListTypeDefinitionParameterTypes2);

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

			return (com.liferay.list.type.model.ListTypeDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.list.type.model.ListTypeDefinition
			getListTypeDefinition(
				HttpPrincipal httpPrincipal, long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeDefinitionServiceUtil.class, "getListTypeDefinition",
				_getListTypeDefinitionParameterTypes3);

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

			return (com.liferay.list.type.model.ListTypeDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.list.type.model.ListTypeDefinition>
		getListTypeDefinitions(
			HttpPrincipal httpPrincipal, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeDefinitionServiceUtil.class, "getListTypeDefinitions",
				_getListTypeDefinitionsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.list.type.model.ListTypeDefinition>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getListTypeDefinitionsCount(HttpPrincipal httpPrincipal) {
		try {
			MethodKey methodKey = new MethodKey(
				ListTypeDefinitionServiceUtil.class,
				"getListTypeDefinitionsCount",
				_getListTypeDefinitionsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
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

	public static com.liferay.list.type.model.ListTypeDefinition
			updateListTypeDefinition(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long listTypeDefinitionId,
				java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ListTypeDefinitionServiceUtil.class, "updateListTypeDefinition",
				_updateListTypeDefinitionParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, listTypeDefinitionId,
				nameMap);

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

			return (com.liferay.list.type.model.ListTypeDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ListTypeDefinitionServiceHttp.class);

	private static final Class<?>[] _addListTypeDefinitionParameterTypes0 =
		new Class[] {java.util.Map.class};
	private static final Class<?>[] _deleteListTypeDefinitionParameterTypes1 =
		new Class[] {com.liferay.list.type.model.ListTypeDefinition.class};
	private static final Class<?>[] _deleteListTypeDefinitionParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getListTypeDefinitionParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getListTypeDefinitionsParameterTypes4 =
		new Class[] {int.class, int.class};
	private static final Class<?>[]
		_getListTypeDefinitionsCountParameterTypes5 = new Class[] {};
	private static final Class<?>[] _updateListTypeDefinitionParameterTypes6 =
		new Class[] {String.class, long.class, java.util.Map.class};

}
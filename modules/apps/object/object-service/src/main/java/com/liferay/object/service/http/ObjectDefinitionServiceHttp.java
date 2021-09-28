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

import com.liferay.object.service.ObjectDefinitionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ObjectDefinitionServiceUtil</code> service
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
 * @see ObjectDefinitionServiceSoap
 * @generated
 */
public class ObjectDefinitionServiceHttp {

	public static com.liferay.object.model.ObjectDefinition
			addCustomObjectDefinition(
				HttpPrincipal httpPrincipal,
				java.util.Map<java.util.Locale, String> labelMap, String name,
				String panelAppOrder, String panelCategoryKey,
				java.util.Map<java.util.Locale, String> pluralLabelMap,
				String scope,
				java.util.List<com.liferay.object.model.ObjectField>
					objectFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectDefinitionServiceUtil.class, "addCustomObjectDefinition",
				_addCustomObjectDefinitionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, labelMap, name, panelAppOrder, panelCategoryKey,
				pluralLabelMap, scope, objectFields);

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

			return (com.liferay.object.model.ObjectDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectDefinition
			deleteObjectDefinition(
				HttpPrincipal httpPrincipal, long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectDefinitionServiceUtil.class, "deleteObjectDefinition",
				_deleteObjectDefinitionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectDefinitionId);

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

			return (com.liferay.object.model.ObjectDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectDefinition getObjectDefinition(
			HttpPrincipal httpPrincipal, long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectDefinitionServiceUtil.class, "getObjectDefinition",
				_getObjectDefinitionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectDefinitionId);

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

			return (com.liferay.object.model.ObjectDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.object.model.ObjectDefinition>
		getObjectDefinitions(HttpPrincipal httpPrincipal, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectDefinitionServiceUtil.class, "getObjectDefinitions",
				_getObjectDefinitionsParameterTypes3);

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

			return (java.util.List<com.liferay.object.model.ObjectDefinition>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.object.model.ObjectDefinition>
		getObjectDefinitions(
			HttpPrincipal httpPrincipal, long companyId, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectDefinitionServiceUtil.class, "getObjectDefinitions",
				_getObjectDefinitionsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.object.model.ObjectDefinition>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getObjectDefinitionsCount(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectDefinitionServiceUtil.class, "getObjectDefinitionsCount",
				_getObjectDefinitionsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey);

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

	public static int getObjectDefinitionsCount(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectDefinitionServiceUtil.class, "getObjectDefinitionsCount",
				_getObjectDefinitionsCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

	public static com.liferay.object.model.ObjectDefinition
			publishCustomObjectDefinition(
				HttpPrincipal httpPrincipal, long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectDefinitionServiceUtil.class,
				"publishCustomObjectDefinition",
				_publishCustomObjectDefinitionParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectDefinitionId);

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

			return (com.liferay.object.model.ObjectDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectDefinition
			updateCustomObjectDefinition(
				HttpPrincipal httpPrincipal, Long objectDefinitionId,
				long descriptionObjectFieldId, long titleObjectFieldId,
				boolean active,
				java.util.Map<java.util.Locale, String> labelMap, String name,
				String panelAppOrder, String panelCategoryKey,
				java.util.Map<java.util.Locale, String> pluralLabelMap,
				String scope)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectDefinitionServiceUtil.class,
				"updateCustomObjectDefinition",
				_updateCustomObjectDefinitionParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectDefinitionId, descriptionObjectFieldId,
				titleObjectFieldId, active, labelMap, name, panelAppOrder,
				panelCategoryKey, pluralLabelMap, scope);

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

			return (com.liferay.object.model.ObjectDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionServiceHttp.class);

	private static final Class<?>[] _addCustomObjectDefinitionParameterTypes0 =
		new Class[] {
			java.util.Map.class, String.class, String.class, String.class,
			java.util.Map.class, String.class, java.util.List.class
		};
	private static final Class<?>[] _deleteObjectDefinitionParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getObjectDefinitionParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getObjectDefinitionsParameterTypes3 =
		new Class[] {int.class, int.class};
	private static final Class<?>[] _getObjectDefinitionsParameterTypes4 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getObjectDefinitionsCountParameterTypes5 =
		new Class[] {};
	private static final Class<?>[] _getObjectDefinitionsCountParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[]
		_publishCustomObjectDefinitionParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_updateCustomObjectDefinitionParameterTypes8 = new Class[] {
			Long.class, long.class, long.class, boolean.class,
			java.util.Map.class, String.class, String.class, String.class,
			java.util.Map.class, String.class
		};

}
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

import com.liferay.object.service.ObjectRelationshipServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ObjectRelationshipServiceUtil</code> service
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
public class ObjectRelationshipServiceHttp {

	public static com.liferay.object.model.ObjectRelationship
			addObjectRelationship(
				HttpPrincipal httpPrincipal, long objectDefinitionId1,
				long objectDefinitionId2, String deletionType,
				java.util.Map<java.util.Locale, String> labelMap, String name,
				String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectRelationshipServiceUtil.class, "addObjectRelationship",
				_addObjectRelationshipParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectDefinitionId1, objectDefinitionId2,
				deletionType, labelMap, name, type);

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

			return (com.liferay.object.model.ObjectRelationship)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void addObjectRelationshipMappingTableValues(
			HttpPrincipal httpPrincipal, long objectRelationshipId,
			long primaryKey1, long primaryKey2,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectRelationshipServiceUtil.class,
				"addObjectRelationshipMappingTableValues",
				_addObjectRelationshipMappingTableValuesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectRelationshipId, primaryKey1, primaryKey2,
				serviceContext);

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

	public static com.liferay.object.model.ObjectRelationship
			deleteObjectRelationship(
				HttpPrincipal httpPrincipal, long objectRelationshipId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectRelationshipServiceUtil.class, "deleteObjectRelationship",
				_deleteObjectRelationshipParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectRelationshipId);

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

			return (com.liferay.object.model.ObjectRelationship)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectRelationship
			getObjectRelationship(
				HttpPrincipal httpPrincipal, long objectRelationshipId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectRelationshipServiceUtil.class, "getObjectRelationship",
				_getObjectRelationshipParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectRelationshipId);

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

			return (com.liferay.object.model.ObjectRelationship)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectRelationship
			getObjectRelationship(
				HttpPrincipal httpPrincipal, long objectDefinitionId1,
				String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectRelationshipServiceUtil.class, "getObjectRelationship",
				_getObjectRelationshipParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectDefinitionId1, name);

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

			return (com.liferay.object.model.ObjectRelationship)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.object.model.ObjectRelationship>
			getObjectRelationships(
				HttpPrincipal httpPrincipal, long objectDefinitionId1,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectRelationshipServiceUtil.class, "getObjectRelationships",
				_getObjectRelationshipsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectDefinitionId1, start, end);

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

			return (java.util.List<com.liferay.object.model.ObjectRelationship>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectRelationship
			updateObjectRelationship(
				HttpPrincipal httpPrincipal, long objectRelationshipId,
				String deletionType,
				java.util.Map<java.util.Locale, String> labelMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectRelationshipServiceUtil.class, "updateObjectRelationship",
				_updateObjectRelationshipParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectRelationshipId, deletionType, labelMap);

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

			return (com.liferay.object.model.ObjectRelationship)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ObjectRelationshipServiceHttp.class);

	private static final Class<?>[] _addObjectRelationshipParameterTypes0 =
		new Class[] {
			long.class, long.class, String.class, java.util.Map.class,
			String.class, String.class
		};
	private static final Class<?>[]
		_addObjectRelationshipMappingTableValuesParameterTypes1 = new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteObjectRelationshipParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getObjectRelationshipParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getObjectRelationshipParameterTypes4 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getObjectRelationshipsParameterTypes5 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _updateObjectRelationshipParameterTypes6 =
		new Class[] {long.class, String.class, java.util.Map.class};

}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.dynamicdatalists.service.DDLRecordSetServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
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
 * @author    Brian Wing Shun Chan
 * @see       DDLRecordSetServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.dynamicdatalists.service.DDLRecordSetServiceUtil
 * @generated
 */
public class DDLRecordSetServiceHttp {
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordSet addRecordSet(
		HttpPrincipal httpPrincipal, long groupId, long ddmStructureId,
		java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows, int scope,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDLRecordSetServiceUtil.class,
					"addRecordSet", _addRecordSetParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					ddmStructureId, recordSetKey, nameMap, descriptionMap,
					minDisplayRows, scope, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatalists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteRecordSet(HttpPrincipal httpPrincipal,
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDLRecordSetServiceUtil.class,
					"deleteRecordSet", _deleteRecordSetParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					recordSetId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordSet getRecordSet(
		HttpPrincipal httpPrincipal, long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDLRecordSetServiceUtil.class,
					"getRecordSet", _getRecordSetParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					recordSetId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatalists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordSet updateMinDisplayRows(
		HttpPrincipal httpPrincipal, long recordSetId, int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDLRecordSetServiceUtil.class,
					"updateMinDisplayRows", _updateMinDisplayRowsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					recordSetId, minDisplayRows, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatalists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordSet updateRecordSet(
		HttpPrincipal httpPrincipal, long recordSetId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDLRecordSetServiceUtil.class,
					"updateRecordSet", _updateRecordSetParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					recordSetId, ddmStructureId, nameMap, descriptionMap,
					minDisplayRows, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatalists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordSet updateRecordSet(
		HttpPrincipal httpPrincipal, long groupId, long ddmStructureId,
		java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDLRecordSetServiceUtil.class,
					"updateRecordSet", _updateRecordSetParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					ddmStructureId, recordSetKey, nameMap, descriptionMap,
					minDisplayRows, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatalists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDLRecordSetServiceHttp.class);
	private static final Class<?>[] _addRecordSetParameterTypes0 = new Class[] {
			long.class, long.class, java.lang.String.class, java.util.Map.class,
			java.util.Map.class, int.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteRecordSetParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getRecordSetParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateMinDisplayRowsParameterTypes3 = new Class[] {
			long.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateRecordSetParameterTypes4 = new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			int.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateRecordSetParameterTypes5 = new Class[] {
			long.class, long.class, java.lang.String.class, java.util.Map.class,
			java.util.Map.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
}
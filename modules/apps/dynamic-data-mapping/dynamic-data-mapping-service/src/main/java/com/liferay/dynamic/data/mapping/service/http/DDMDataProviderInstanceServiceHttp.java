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

package com.liferay.dynamic.data.mapping.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * Provides the HTTP utility for the
 * {@link DDMDataProviderInstanceServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @see DDMDataProviderInstanceServiceSoap
 * @see HttpPrincipal
 * @see DDMDataProviderInstanceServiceUtil
 * @generated
 */
@ProviderType
public class DDMDataProviderInstanceServiceHttp {
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance addDataProviderInstance(
		HttpPrincipal httpPrincipal, long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMDataProviderInstanceServiceUtil.class,
					"addDataProviderInstance",
					_addDataProviderInstanceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					nameMap, descriptionMap, ddmFormValues, type, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteDataProviderInstance(HttpPrincipal httpPrincipal,
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMDataProviderInstanceServiceUtil.class,
					"deleteDataProviderInstance",
					_deleteDataProviderInstanceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					dataProviderInstanceId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance fetchDataProviderInstance(
		HttpPrincipal httpPrincipal, long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMDataProviderInstanceServiceUtil.class,
					"fetchDataProviderInstance",
					_fetchDataProviderInstanceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					dataProviderInstanceId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance getDataProviderInstance(
		HttpPrincipal httpPrincipal, long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMDataProviderInstanceServiceUtil.class,
					"getDataProviderInstance",
					_getDataProviderInstanceParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					dataProviderInstanceId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(DDMDataProviderInstanceServiceUtil.class,
					"search", _searchParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, keywords, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(DDMDataProviderInstanceServiceUtil.class,
					"search", _searchParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, name, description, andOperator, start,
					end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, java.lang.String keywords) {
		try {
			MethodKey methodKey = new MethodKey(DDMDataProviderInstanceServiceUtil.class,
					"searchCount", _searchCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, keywords);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, java.lang.String name, java.lang.String description,
		boolean andOperator) {
		try {
			MethodKey methodKey = new MethodKey(DDMDataProviderInstanceServiceUtil.class,
					"searchCount", _searchCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, name, description, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance updateDataProviderInstance(
		HttpPrincipal httpPrincipal, long dataProviderInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMDataProviderInstanceServiceUtil.class,
					"updateDataProviderInstance",
					_updateDataProviderInstanceParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					dataProviderInstanceId, nameMap, descriptionMap,
					ddmFormValues, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMDataProviderInstanceServiceHttp.class);
	private static final Class<?>[] _addDataProviderInstanceParameterTypes0 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteDataProviderInstanceParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchDataProviderInstanceParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getDataProviderInstanceParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _searchParameterTypes4 = new Class[] {
			long.class, long[].class, java.lang.String.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes5 = new Class[] {
			long.class, long[].class, java.lang.String.class,
			java.lang.String.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchCountParameterTypes6 = new Class[] {
			long.class, long[].class, java.lang.String.class
		};
	private static final Class<?>[] _searchCountParameterTypes7 = new Class[] {
			long.class, long[].class, java.lang.String.class,
			java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _updateDataProviderInstanceParameterTypes8 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			com.liferay.portal.service.ServiceContext.class
		};
}
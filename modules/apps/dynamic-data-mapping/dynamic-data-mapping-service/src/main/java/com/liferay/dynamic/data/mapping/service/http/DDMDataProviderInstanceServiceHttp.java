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

import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDMDataProviderInstanceServiceUtil</code> service
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
 * @see DDMDataProviderInstanceServiceSoap
 * @generated
 */
public class DDMDataProviderInstanceServiceHttp {

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
			addDataProviderInstance(
				HttpPrincipal httpPrincipal, long groupId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					ddmFormValues,
				String type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class,
				"addDataProviderInstance",
				_addDataProviderInstanceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nameMap, descriptionMap, ddmFormValues,
				type, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteDataProviderInstance(
			HttpPrincipal httpPrincipal, long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class,
				"deleteDataProviderInstance",
				_deleteDataProviderInstanceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, dataProviderInstanceId);

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

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
			fetchDataProviderInstance(
				HttpPrincipal httpPrincipal, long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class,
				"fetchDataProviderInstance",
				_fetchDataProviderInstanceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, dataProviderInstanceId);

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

			return (com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
			fetchDataProviderInstanceByUuid(
				HttpPrincipal httpPrincipal, String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class,
				"fetchDataProviderInstanceByUuid",
				_fetchDataProviderInstanceByUuidParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, uuid);

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

			return (com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
			getDataProviderInstance(
				HttpPrincipal httpPrincipal, long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class,
				"getDataProviderInstance",
				_getDataProviderInstanceParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, dataProviderInstanceId);

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

			return (com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
			getDataProviderInstanceByUuid(
				HttpPrincipal httpPrincipal, String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class,
				"getDataProviderInstanceByUuid",
				_getDataProviderInstanceByUuidParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, uuid);

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

			return (com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
			getDataProviderInstances(
				HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
				int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class,
				"getDataProviderInstances",
				_getDataProviderInstancesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.
					DDMDataProviderInstance>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getDataProviderInstancesCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class,
				"getDataProviderInstancesCount",
				_getDataProviderInstancesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class, "search",
				_searchParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, keywords, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.
					DDMDataProviderInstance>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			String name, String description, boolean andOperator, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class, "search",
				_searchParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, name, description, andOperator,
				start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.
					DDMDataProviderInstance>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		String keywords) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class, "searchCount",
				_searchCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, keywords);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		String name, String description, boolean andOperator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class, "searchCount",
				_searchCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, name, description, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
			updateDataProviderInstance(
				HttpPrincipal httpPrincipal, long dataProviderInstanceId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					ddmFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMDataProviderInstanceServiceUtil.class,
				"updateDataProviderInstance",
				_updateDataProviderInstanceParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, dataProviderInstanceId, nameMap, descriptionMap,
				ddmFormValues, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMDataProviderInstanceServiceHttp.class);

	private static final Class<?>[] _addDataProviderInstanceParameterTypes0 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteDataProviderInstanceParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchDataProviderInstanceParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchDataProviderInstanceByUuidParameterTypes3 = new Class[] {
			String.class
		};
	private static final Class<?>[] _getDataProviderInstanceParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getDataProviderInstanceByUuidParameterTypes5 = new Class[] {
			String.class
		};
	private static final Class<?>[] _getDataProviderInstancesParameterTypes6 =
		new Class[] {long.class, long[].class, int.class, int.class};
	private static final Class<?>[]
		_getDataProviderInstancesCountParameterTypes7 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _searchParameterTypes8 = new Class[] {
		long.class, long[].class, String.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes9 = new Class[] {
		long.class, long[].class, String.class, String.class, boolean.class,
		int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes10 = new Class[] {
		long.class, long[].class, String.class
	};
	private static final Class<?>[] _searchCountParameterTypes11 = new Class[] {
		long.class, long[].class, String.class, String.class, boolean.class
	};
	private static final Class<?>[]
		_updateDataProviderInstanceParameterTypes12 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
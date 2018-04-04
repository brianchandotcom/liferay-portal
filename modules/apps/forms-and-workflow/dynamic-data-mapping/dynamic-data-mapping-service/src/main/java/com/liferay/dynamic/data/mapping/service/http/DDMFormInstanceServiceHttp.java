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

import com.liferay.dynamic.data.mapping.service.DDMFormInstanceServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link DDMFormInstanceServiceUtil} service utility. The
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
 * @see DDMFormInstanceServiceSoap
 * @see HttpPrincipal
 * @see DDMFormInstanceServiceUtil
 * @generated
 */
@ProviderType
public class DDMFormInstanceServiceHttp {
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance addFormInstance(
		HttpPrincipal httpPrincipal, long groupId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"addFormInstance", _addFormInstanceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					ddmStructureId, nameMap, descriptionMap,
					settingsDDMFormValues, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteFormInstance(HttpPrincipal httpPrincipal,
		long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"deleteFormInstance", _deleteFormInstanceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					ddmFormInstanceId);

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

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance fetchFormInstance(
		HttpPrincipal httpPrincipal, long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"fetchFormInstance", _fetchFormInstanceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					ddmFormInstanceId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance getFormInstance(
		HttpPrincipal httpPrincipal, long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"getFormInstance", _getFormInstanceParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					ddmFormInstanceId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance> getFormInstances(
		HttpPrincipal httpPrincipal, long companyId, long groupId, int start,
		int end) {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"getFormInstances", _getFormInstancesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getFormInstancesCount(HttpPrincipal httpPrincipal,
		long companyId, long groupId) {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"getFormInstancesCount",
					_getFormInstancesCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId);

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

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMFormInstance> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"search", _searchParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, keywords, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMFormInstance> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"search", _searchParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, names, descriptions, andOperator,
					start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstance>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long groupId, java.lang.String keywords) {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"searchCount", _searchCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, keywords);

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
		long groupId, java.lang.String[] names,
		java.lang.String[] descriptions, boolean andOperator) {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"searchCount", _searchCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, names, descriptions, andOperator);

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

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance updateFormInstance(
		HttpPrincipal httpPrincipal, long formInstanceId,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"updateFormInstance", _updateFormInstanceParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					formInstanceId, settingsDDMFormValues);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance updateFormInstance(
		HttpPrincipal httpPrincipal, long ddmFormInstanceId,
		long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues settingsDDMFormValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMFormInstanceServiceUtil.class,
					"updateFormInstance", _updateFormInstanceParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					ddmFormInstanceId, ddmStructureId, nameMap, descriptionMap,
					settingsDDMFormValues, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMFormInstanceServiceHttp.class);
	private static final Class<?>[] _addFormInstanceParameterTypes0 = new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFormInstanceParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchFormInstanceParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getFormInstanceParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getFormInstancesParameterTypes4 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getFormInstancesCountParameterTypes5 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _searchParameterTypes6 = new Class[] {
			long.class, long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes7 = new Class[] {
			long.class, long.class, java.lang.String[].class,
			java.lang.String[].class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchCountParameterTypes8 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _searchCountParameterTypes9 = new Class[] {
			long.class, long.class, java.lang.String[].class,
			java.lang.String[].class, boolean.class
		};
	private static final Class<?>[] _updateFormInstanceParameterTypes10 = new Class[] {
			long.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class
		};
	private static final Class<?>[] _updateFormInstanceParameterTypes11 = new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
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

import com.liferay.dynamic.data.mapping.service.DDMFormInstanceServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDMFormInstanceServiceUtil</code> service
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
 * @see DDMFormInstanceServiceSoap
 * @generated
 */
public class DDMFormInstanceServiceHttp {

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance
			addFormInstance(
				HttpPrincipal httpPrincipal, long groupId, long ddmStructureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "addFormInstance",
				_addFormInstanceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, ddmStructureId, nameMap, descriptionMap,
				settingsDDMFormValues, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance
			addFormInstance(
				HttpPrincipal httpPrincipal, long groupId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "addFormInstance",
				_addFormInstanceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nameMap, descriptionMap, ddmForm,
				ddmFormLayout, settingsDDMFormValues, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteFormInstance(
			HttpPrincipal httpPrincipal, long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "deleteFormInstance",
				_deleteFormInstanceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceId);

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

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance
			fetchFormInstance(
				HttpPrincipal httpPrincipal, long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "fetchFormInstance",
				_fetchFormInstanceParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance
			getFormInstance(HttpPrincipal httpPrincipal, long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "getFormInstance",
				_getFormInstanceParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
			getFormInstances(
				HttpPrincipal httpPrincipal, long companyId, long groupId,
				int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "getFormInstances",
				_getFormInstancesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getFormInstancesCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "getFormInstancesCount",
				_getFormInstancesCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId);

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

	public static int getFormInstancesCount(
			HttpPrincipal httpPrincipal, String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "getFormInstancesCount",
				_getFormInstancesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, uuid);

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

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String keywords, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "search",
				_searchParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "search",
				_searchParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String[] names, String[] descriptions, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "search",
				_searchParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, names, descriptions, andOperator,
				start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		String keywords) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "searchCount",
				_searchCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords);

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

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		String keywords, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "searchCount",
				_searchCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords, status);

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

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		String[] names, String[] descriptions, boolean andOperator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "searchCount",
				_searchCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, names, descriptions,
				andOperator);

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

	public static void sendEmail(
			HttpPrincipal httpPrincipal, long formInstanceId, String message,
			String subject, String[] toEmailAddresses)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "sendEmail",
				_sendEmailParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formInstanceId, message, subject, toEmailAddresses);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof Exception) {
					throw (Exception)exception;
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

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance
			updateFormInstance(
				HttpPrincipal httpPrincipal, long formInstanceId,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "updateFormInstance",
				_updateFormInstanceParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, formInstanceId, settingsDDMFormValues);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstance
			updateFormInstance(
				HttpPrincipal httpPrincipal, long ddmFormInstanceId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMFormInstanceServiceUtil.class, "updateFormInstance",
				_updateFormInstanceParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, ddmFormInstanceId, nameMap, descriptionMap, ddmForm,
				ddmFormLayout, settingsDDMFormValues, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMFormInstance)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceServiceHttp.class);

	private static final Class<?>[] _addFormInstanceParameterTypes0 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addFormInstanceParameterTypes1 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.model.DDMForm.class,
			com.liferay.dynamic.data.mapping.model.DDMFormLayout.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFormInstanceParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchFormInstanceParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getFormInstanceParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getFormInstancesParameterTypes5 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getFormInstancesCountParameterTypes6 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getFormInstancesCountParameterTypes7 =
		new Class[] {String.class};
	private static final Class<?>[] _searchParameterTypes8 = new Class[] {
		long.class, long.class, String.class, int.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes9 = new Class[] {
		long.class, long.class, String.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes10 = new Class[] {
		long.class, long.class, String[].class, String[].class, boolean.class,
		int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes11 = new Class[] {
		long.class, long.class, String.class
	};
	private static final Class<?>[] _searchCountParameterTypes12 = new Class[] {
		long.class, long.class, String.class, int.class
	};
	private static final Class<?>[] _searchCountParameterTypes13 = new Class[] {
		long.class, long.class, String[].class, String[].class, boolean.class
	};
	private static final Class<?>[] _sendEmailParameterTypes14 = new Class[] {
		long.class, String.class, String.class, String[].class
	};
	private static final Class<?>[] _updateFormInstanceParameterTypes15 =
		new Class[] {
			long.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class
		};
	private static final Class<?>[] _updateFormInstanceParameterTypes16 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.model.DDMForm.class,
			com.liferay.dynamic.data.mapping.model.DDMFormLayout.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
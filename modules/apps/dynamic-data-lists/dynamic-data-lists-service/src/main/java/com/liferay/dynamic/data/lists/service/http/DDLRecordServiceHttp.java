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

package com.liferay.dynamic.data.lists.service.http;

import com.liferay.dynamic.data.lists.service.DDLRecordServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDLRecordServiceUtil</code> service
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
 * @see DDLRecordServiceSoap
 * @generated
 */
public class DDLRecordServiceHttp {

	public static com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
			HttpPrincipal httpPrincipal, long groupId, long recordSetId,
			int displayIndex,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "addRecord",
				_addRecordParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, recordSetId, displayIndex, ddmFormValues,
				serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
			HttpPrincipal httpPrincipal, long groupId, long recordSetId,
			int displayIndex,
			com.liferay.dynamic.data.mapping.storage.Fields fields,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "addRecord",
				_addRecordParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, recordSetId, displayIndex, fields,
				serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
			HttpPrincipal httpPrincipal, long groupId, long recordSetId,
			int displayIndex,
			java.util.Map<String, java.io.Serializable> fieldsMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "addRecord",
				_addRecordParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, recordSetId, displayIndex, fieldsMap,
				serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteRecord(HttpPrincipal httpPrincipal, long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "deleteRecord",
				_deleteRecordParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordId);

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

	public static com.liferay.dynamic.data.lists.model.DDLRecord
			deleteRecordLocale(
				HttpPrincipal httpPrincipal, long recordId,
				java.util.Locale locale,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "deleteRecordLocale",
				_deleteRecordLocaleParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordId, locale, serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecord getRecord(
			HttpPrincipal httpPrincipal, long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "getRecord",
				_getRecordParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordId);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
			getRecords(HttpPrincipal httpPrincipal, long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "getRecords",
				_getRecordsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordSetId);

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

			return (java.util.List
				<com.liferay.dynamic.data.lists.model.DDLRecord>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void revertRecord(
			HttpPrincipal httpPrincipal, long recordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "revertRecord",
				_revertRecordParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordId, version, serviceContext);

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

	public static void revertRecordVersion(
			HttpPrincipal httpPrincipal, long recordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "revertRecordVersion",
				_revertRecordVersionParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordId, version, serviceContext);

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

	public static com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
			HttpPrincipal httpPrincipal, long recordId, boolean majorVersion,
			int displayIndex,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "updateRecord",
				_updateRecordParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordId, majorVersion, displayIndex, ddmFormValues,
				serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
			HttpPrincipal httpPrincipal, long recordId, boolean majorVersion,
			int displayIndex,
			com.liferay.dynamic.data.mapping.storage.Fields fields,
			boolean mergeFields,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "updateRecord",
				_updateRecordParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordId, majorVersion, displayIndex, fields,
				mergeFields, serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
			HttpPrincipal httpPrincipal, long recordId, int displayIndex,
			java.util.Map<String, java.io.Serializable> fieldsMap,
			boolean mergeFields,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordServiceUtil.class, "updateRecord",
				_updateRecordParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordId, displayIndex, fieldsMap, mergeFields,
				serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecord)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDLRecordServiceHttp.class);

	private static final Class<?>[] _addRecordParameterTypes0 = new Class[] {
		long.class, long.class, int.class,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addRecordParameterTypes1 = new Class[] {
		long.class, long.class, int.class,
		com.liferay.dynamic.data.mapping.storage.Fields.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addRecordParameterTypes2 = new Class[] {
		long.class, long.class, int.class, java.util.Map.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteRecordParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _deleteRecordLocaleParameterTypes4 =
		new Class[] {
			long.class, java.util.Locale.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _getRecordParameterTypes5 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getRecordsParameterTypes6 = new Class[] {
		long.class
	};
	private static final Class<?>[] _revertRecordParameterTypes7 = new Class[] {
		long.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _revertRecordVersionParameterTypes8 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateRecordParameterTypes9 = new Class[] {
		long.class, boolean.class, int.class,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _updateRecordParameterTypes10 =
		new Class[] {
			long.class, boolean.class, int.class,
			com.liferay.dynamic.data.mapping.storage.Fields.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateRecordParameterTypes11 =
		new Class[] {
			long.class, int.class, java.util.Map.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
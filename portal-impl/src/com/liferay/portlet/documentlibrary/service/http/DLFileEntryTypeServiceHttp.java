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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.document.library.kernel.service.DLFileEntryTypeServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DLFileEntryTypeServiceUtil</code> service
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
 * @generated
 */
public class DLFileEntryTypeServiceHttp {

	public static com.liferay.document.library.kernel.model.DLFileEntryType
			addFileEntryType(
				HttpPrincipal httpPrincipal, long groupId,
				long dataDefinitionId, String fileEntryTypeKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "addFileEntryType",
				_addFileEntryTypeParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, dataDefinitionId, fileEntryTypeKey, nameMap,
				descriptionMap, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFileEntryType)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntryType
			addFileEntryType(
				HttpPrincipal httpPrincipal, long groupId,
				String fileEntryTypeKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				long[] ddmStructureIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "addFileEntryType",
				_addFileEntryTypeParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, fileEntryTypeKey, nameMap, descriptionMap,
				ddmStructureIds, serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFileEntryType)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.document.library.kernel.model.DLFileEntryType
			addFileEntryType(
				HttpPrincipal httpPrincipal, long groupId, String name,
				String description, long[] ddmStructureIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "addFileEntryType",
				_addFileEntryTypeParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, description, ddmStructureIds,
				serviceContext);

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

			return (com.liferay.document.library.kernel.model.DLFileEntryType)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteFileEntryType(
			HttpPrincipal httpPrincipal, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "deleteFileEntryType",
				_deleteFileEntryTypeParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryTypeId);

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

	public static com.liferay.document.library.kernel.model.DLFileEntryType
			getFileEntryType(HttpPrincipal httpPrincipal, long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "getFileEntryType",
				_getFileEntryTypeParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryTypeId);

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

			return (com.liferay.document.library.kernel.model.DLFileEntryType)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType>
			getFileEntryTypes(HttpPrincipal httpPrincipal, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "getFileEntryTypes",
				_getFileEntryTypesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntryType>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType>
			getFileEntryTypes(
				HttpPrincipal httpPrincipal, long[] groupIds, int start,
				int end) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "getFileEntryTypes",
				_getFileEntryTypesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntryType>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getFileEntryTypesCount(
		HttpPrincipal httpPrincipal, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "getFileEntryTypesCount",
				_getFileEntryTypesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds);

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

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType>
				getFolderFileEntryTypes(
					HttpPrincipal httpPrincipal, long[] groupIds, long folderId,
					boolean inherited)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "getFolderFileEntryTypes",
				_getFolderFileEntryTypesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, folderId, inherited);

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

			return (java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntryType>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType> search(
				HttpPrincipal httpPrincipal, long companyId, long folderId,
				long[] groupIds, String keywords,
				boolean includeBasicFileEntryType, boolean inherited, int start,
				int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "search",
				_searchParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, folderId, groupIds, keywords,
				includeBasicFileEntryType, inherited, start, end);

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

			return (java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntryType>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType> search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			String keywords, boolean includeBasicFileEntryType, int scope,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.document.library.kernel.model.DLFileEntryType>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "search",
				_searchParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, keywords,
				includeBasicFileEntryType, scope, start, end,
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
				<com.liferay.document.library.kernel.model.DLFileEntryType>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryType> search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			String keywords, boolean includeBasicFileEntryType, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.document.library.kernel.model.DLFileEntryType>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "search",
				_searchParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, keywords,
				includeBasicFileEntryType, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.document.library.kernel.model.DLFileEntryType>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long folderId,
		long[] groupIds, String keywords, boolean includeBasicFileEntryType,
		boolean inherited) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "searchCount",
				_searchCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, folderId, groupIds, keywords,
				includeBasicFileEntryType, inherited);

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
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		String keywords, boolean includeBasicFileEntryType) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "searchCount",
				_searchCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, keywords,
				includeBasicFileEntryType);

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
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		String keywords, boolean includeBasicFileEntryType, int scope) {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "searchCount",
				_searchCountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, keywords,
				includeBasicFileEntryType, scope);

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

	public static com.liferay.document.library.kernel.model.DLFileEntryType
			updateFileEntryType(
				HttpPrincipal httpPrincipal, long fileEntryTypeId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "updateFileEntryType",
				_updateFileEntryTypeParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryTypeId, nameMap, descriptionMap);

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

			return (com.liferay.document.library.kernel.model.DLFileEntryType)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void updateFileEntryType(
			HttpPrincipal httpPrincipal, long fileEntryTypeId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			long[] ddmStructureIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "updateFileEntryType",
				_updateFileEntryTypeParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryTypeId, nameMap, descriptionMap,
				ddmStructureIds, serviceContext);

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

	public static void updateFileEntryType(
			HttpPrincipal httpPrincipal, long fileEntryTypeId, String name,
			String description, long[] ddmStructureIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DLFileEntryTypeServiceUtil.class, "updateFileEntryType",
				_updateFileEntryTypeParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileEntryTypeId, name, description, ddmStructureIds,
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

	private static Log _log = LogFactoryUtil.getLog(
		DLFileEntryTypeServiceHttp.class);

	private static final Class<?>[] _addFileEntryTypeParameterTypes0 =
		new Class[] {
			long.class, long.class, String.class, java.util.Map.class,
			java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addFileEntryTypeParameterTypes1 =
		new Class[] {
			long.class, String.class, java.util.Map.class, java.util.Map.class,
			long[].class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addFileEntryTypeParameterTypes2 =
		new Class[] {
			long.class, String.class, String.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteFileEntryTypeParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getFileEntryTypeParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getFileEntryTypesParameterTypes5 =
		new Class[] {long[].class};
	private static final Class<?>[] _getFileEntryTypesParameterTypes6 =
		new Class[] {long[].class, int.class, int.class};
	private static final Class<?>[] _getFileEntryTypesCountParameterTypes7 =
		new Class[] {long[].class};
	private static final Class<?>[] _getFolderFileEntryTypesParameterTypes8 =
		new Class[] {long[].class, long.class, boolean.class};
	private static final Class<?>[] _searchParameterTypes9 = new Class[] {
		long.class, long.class, long[].class, String.class, boolean.class,
		boolean.class, int.class, int.class
	};
	private static final Class<?>[] _searchParameterTypes10 = new Class[] {
		long.class, long[].class, String.class, boolean.class, int.class,
		int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes11 = new Class[] {
		long.class, long[].class, String.class, boolean.class, int.class,
		int.class, com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes12 = new Class[] {
		long.class, long.class, long[].class, String.class, boolean.class,
		boolean.class
	};
	private static final Class<?>[] _searchCountParameterTypes13 = new Class[] {
		long.class, long[].class, String.class, boolean.class
	};
	private static final Class<?>[] _searchCountParameterTypes14 = new Class[] {
		long.class, long[].class, String.class, boolean.class, int.class
	};
	private static final Class<?>[] _updateFileEntryTypeParameterTypes15 =
		new Class[] {long.class, java.util.Map.class, java.util.Map.class};
	private static final Class<?>[] _updateFileEntryTypeParameterTypes16 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateFileEntryTypeParameterTypes17 =
		new Class[] {
			long.class, String.class, String.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.http;

import com.liferay.object.service.ObjectEntryFolderServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ObjectEntryFolderServiceUtil</code> service
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
public class ObjectEntryFolderServiceHttp {

	public static com.liferay.object.model.ObjectEntryFolder
			addObjectEntryFolder(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long parentObjectEntryFolderId,
				String description,
				java.util.Map<java.util.Locale, String> labelMap, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class, "addObjectEntryFolder",
				_addObjectEntryFolderParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId,
				parentObjectEntryFolderId, description, labelMap, name,
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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectEntryFolder
			copyObjectEntryFolder(
				HttpPrincipal httpPrincipal, long objectEntryFolderId,
				long parentObjectEntryFolderId, boolean replace,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class, "copyObjectEntryFolder",
				_copyObjectEntryFolderParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectEntryFolderId, parentObjectEntryFolderId,
				replace, serviceContext);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectEntryFolder
			deleteObjectEntryFolder(
				HttpPrincipal httpPrincipal, long objectEntryFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class, "deleteObjectEntryFolder",
				_deleteObjectEntryFolderParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectEntryFolderId);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectEntryFolder
			deleteObjectEntryFolderByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class,
				"deleteObjectEntryFolderByExternalReferenceCode",
				_deleteObjectEntryFolderByExternalReferenceCodeParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, companyId);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectEntryFolder
			fetchObjectEntryFolder(
				HttpPrincipal httpPrincipal, long objectEntryFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class, "fetchObjectEntryFolder",
				_fetchObjectEntryFolderParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectEntryFolderId);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectEntryFolder
			fetchObjectEntryFolderByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class,
				"fetchObjectEntryFolderByExternalReferenceCode",
				_fetchObjectEntryFolderByExternalReferenceCodeParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, companyId);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectEntryFolder
			getObjectEntryFolder(
				HttpPrincipal httpPrincipal, long objectEntryFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class, "getObjectEntryFolder",
				_getObjectEntryFolderParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectEntryFolderId);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectEntryFolder
			getObjectEntryFolderByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class,
				"getObjectEntryFolderByExternalReferenceCode",
				_getObjectEntryFolderByExternalReferenceCodeParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, companyId);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.object.model.ObjectEntryFolder>
			getObjectEntryFolders(
				HttpPrincipal httpPrincipal, long groupId, long companyId,
				long parentObjectEntryFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class, "getObjectEntryFolders",
				_getObjectEntryFoldersParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, companyId, parentObjectEntryFolderId, start,
				end);

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

			return (java.util.List<com.liferay.object.model.ObjectEntryFolder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getObjectEntryFoldersCount(
			HttpPrincipal httpPrincipal, long groupId, long companyId,
			long parentObjectEntryFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class,
				"getObjectEntryFoldersCount",
				_getObjectEntryFoldersCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, companyId, parentObjectEntryFolderId);

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

	public static com.liferay.object.model.ObjectEntryFolder
			getOrAddEmptyObjectEntryFolder(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long companyId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class,
				"getOrAddEmptyObjectEntryFolder",
				_getOrAddEmptyObjectEntryFolderParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, companyId,
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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectEntryFolder
			moveObjectEntryFolder(
				HttpPrincipal httpPrincipal, long objectEntryFolderId,
				long parentObjectEntryFolderId, boolean replace,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class, "moveObjectEntryFolder",
				_moveObjectEntryFolderParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectEntryFolderId, parentObjectEntryFolderId,
				replace, serviceContext);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectEntryFolder
			moveObjectEntryFolderToTrash(
				HttpPrincipal httpPrincipal,
				com.liferay.object.model.ObjectEntryFolder objectEntryFolder,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class,
				"moveObjectEntryFolderToTrash",
				_moveObjectEntryFolderToTrashParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectEntryFolder, serviceContext);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.object.model.ObjectEntryFolder
			restoreObjectEntryFolderFromTrash(
				HttpPrincipal httpPrincipal,
				com.liferay.object.model.ObjectEntryFolder objectEntryFolder,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class,
				"restoreObjectEntryFolderFromTrash",
				_restoreObjectEntryFolderFromTrashParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectEntryFolder, serviceContext);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void subscribeObjectEntryFolder(
			HttpPrincipal httpPrincipal, long groupId, long objectEntryFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class,
				"subscribeObjectEntryFolder",
				_subscribeObjectEntryFolderParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, objectEntryFolderId);

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

	public static void unsubscribeObjectEntryFolder(
			HttpPrincipal httpPrincipal, long groupId, long objectEntryFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class,
				"unsubscribeObjectEntryFolder",
				_unsubscribeObjectEntryFolderParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, objectEntryFolderId);

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

	public static com.liferay.object.model.ObjectEntryFolder
			updateObjectEntryFolder(
				HttpPrincipal httpPrincipal, long objectEntryFolderId,
				long parentObjectEntryFolderId, String description,
				java.util.Map<java.util.Locale, String> labelMap, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ObjectEntryFolderServiceUtil.class, "updateObjectEntryFolder",
				_updateObjectEntryFolderParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, objectEntryFolderId, parentObjectEntryFolderId,
				description, labelMap, name, serviceContext);

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

			return (com.liferay.object.model.ObjectEntryFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ObjectEntryFolderServiceHttp.class);

	private static final Class<?>[] _addObjectEntryFolderParameterTypes0 =
		new Class[] {
			String.class, long.class, long.class, String.class,
			java.util.Map.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _copyObjectEntryFolderParameterTypes1 =
		new Class[] {
			long.class, long.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteObjectEntryFolderParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_deleteObjectEntryFolderByExternalReferenceCodeParameterTypes3 =
			new Class[] {String.class, long.class, long.class};
	private static final Class<?>[] _fetchObjectEntryFolderParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchObjectEntryFolderByExternalReferenceCodeParameterTypes5 =
			new Class[] {String.class, long.class, long.class};
	private static final Class<?>[] _getObjectEntryFolderParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getObjectEntryFolderByExternalReferenceCodeParameterTypes7 =
			new Class[] {String.class, long.class, long.class};
	private static final Class<?>[] _getObjectEntryFoldersParameterTypes8 =
		new Class[] {long.class, long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getObjectEntryFoldersCountParameterTypes9 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[]
		_getOrAddEmptyObjectEntryFolderParameterTypes10 = new Class[] {
			String.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _moveObjectEntryFolderParameterTypes11 =
		new Class[] {
			long.class, long.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_moveObjectEntryFolderToTrashParameterTypes12 = new Class[] {
			com.liferay.object.model.ObjectEntryFolder.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_restoreObjectEntryFolderFromTrashParameterTypes13 = new Class[] {
			com.liferay.object.model.ObjectEntryFolder.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_subscribeObjectEntryFolderParameterTypes14 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_unsubscribeObjectEntryFolderParameterTypes15 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _updateObjectEntryFolderParameterTypes16 =
		new Class[] {
			long.class, long.class, String.class, java.util.Map.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};

}
// SB-Hash:446815518
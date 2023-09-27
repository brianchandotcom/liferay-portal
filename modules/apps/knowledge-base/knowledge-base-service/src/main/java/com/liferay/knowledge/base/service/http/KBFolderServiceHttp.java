/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.service.http;

import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>KBFolderServiceUtil</code> service
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
public class KBFolderServiceHttp {

	public static com.liferay.knowledge.base.model.KBFolder addKBFolder(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			long groupId, long parentResourceClassNameId,
			long parentResourcePrimKey, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "addKBFolder",
				_addKBFolderParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId,
				parentResourceClassNameId, parentResourcePrimKey, name,
				description, serviceContext);

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

			return (com.liferay.knowledge.base.model.KBFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.knowledge.base.model.KBFolder deleteKBFolder(
			HttpPrincipal httpPrincipal, long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "deleteKBFolder",
				_deleteKBFolderParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbFolderId);

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

			return (com.liferay.knowledge.base.model.KBFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.knowledge.base.model.KBFolder
			fetchFirstChildKBFolder(
				HttpPrincipal httpPrincipal, long groupId, long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "fetchFirstChildKBFolder",
				_fetchFirstChildKBFolderParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, kbFolderId);

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

			return (com.liferay.knowledge.base.model.KBFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.knowledge.base.model.KBFolder
			fetchFirstChildKBFolder(
				HttpPrincipal httpPrincipal, long groupId, long kbFolderId,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBFolder>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "fetchFirstChildKBFolder",
				_fetchFirstChildKBFolderParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, kbFolderId, orderByComparator);

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

			return (com.liferay.knowledge.base.model.KBFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.knowledge.base.model.KBFolder fetchKBFolder(
			HttpPrincipal httpPrincipal, long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "fetchKBFolder",
				_fetchKBFolderParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbFolderId);

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

			return (com.liferay.knowledge.base.model.KBFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.knowledge.base.model.KBFolder
			fetchKBFolderByUrlTitle(
				HttpPrincipal httpPrincipal, long groupId,
				long parentKbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "fetchKBFolderByUrlTitle",
				_fetchKBFolderByUrlTitleParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentKbFolderId, urlTitle);

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

			return (com.liferay.knowledge.base.model.KBFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.knowledge.base.model.KBFolder getKBFolder(
			HttpPrincipal httpPrincipal, long kbFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "getKBFolder",
				_getKBFolderParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbFolderId);

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

			return (com.liferay.knowledge.base.model.KBFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.knowledge.base.model.KBFolder
			getKBFolderByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long groupId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "getKBFolderByExternalReferenceCode",
				_getKBFolderByExternalReferenceCodeParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, externalReferenceCode);

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

			return (com.liferay.knowledge.base.model.KBFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.knowledge.base.model.KBFolder
			getKBFolderByUrlTitle(
				HttpPrincipal httpPrincipal, long groupId,
				long parentKbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "getKBFolderByUrlTitle",
				_getKBFolderByUrlTitleParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentKbFolderId, urlTitle);

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

			return (com.liferay.knowledge.base.model.KBFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBFolder>
			getKBFolders(
				HttpPrincipal httpPrincipal, long groupId,
				long parentKBFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "getKBFolders",
				_getKBFoldersParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentKBFolderId, start, end);

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

			return (java.util.List<com.liferay.knowledge.base.model.KBFolder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<Object> getKBFoldersAndKBArticles(
		HttpPrincipal httpPrincipal, long groupId, long parentResourcePrimKey,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "getKBFoldersAndKBArticles",
				_getKBFoldersAndKBArticlesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentResourcePrimKey, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<Object>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getKBFoldersAndKBArticlesCount(
		HttpPrincipal httpPrincipal, long groupId, long parentResourcePrimKey,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "getKBFoldersAndKBArticlesCount",
				_getKBFoldersAndKBArticlesCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentResourcePrimKey, status);

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

	public static int getKBFoldersCount(
			HttpPrincipal httpPrincipal, long groupId, long parentKBFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "getKBFoldersCount",
				_getKBFoldersCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentKBFolderId);

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

	public static void moveKBFolder(
			HttpPrincipal httpPrincipal, long kbFolderId, long parentKBFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "moveKBFolder",
				_moveKBFolderParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, kbFolderId, parentKBFolderId);

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

	public static com.liferay.knowledge.base.model.KBFolder updateKBFolder(
			HttpPrincipal httpPrincipal, long parentResourceClassNameId,
			long parentResourcePrimKey, long kbFolderId, String name,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				KBFolderServiceUtil.class, "updateKBFolder",
				_updateKBFolderParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentResourceClassNameId, parentResourcePrimKey,
				kbFolderId, name, description, serviceContext);

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

			return (com.liferay.knowledge.base.model.KBFolder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(KBFolderServiceHttp.class);

	private static final Class<?>[] _addKBFolderParameterTypes0 = new Class[] {
		String.class, long.class, long.class, long.class, String.class,
		String.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteKBFolderParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchFirstChildKBFolderParameterTypes2 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _fetchFirstChildKBFolderParameterTypes3 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _fetchKBFolderParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchKBFolderByUrlTitleParameterTypes5 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _getKBFolderParameterTypes6 = new Class[] {
		long.class
	};
	private static final Class<?>[]
		_getKBFolderByExternalReferenceCodeParameterTypes7 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getKBFolderByUrlTitleParameterTypes8 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _getKBFoldersParameterTypes9 = new Class[] {
		long.class, long.class, int.class, int.class
	};
	private static final Class<?>[] _getKBFoldersAndKBArticlesParameterTypes10 =
		new Class[] {
			long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getKBFoldersAndKBArticlesCountParameterTypes11 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[] _getKBFoldersCountParameterTypes12 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _moveKBFolderParameterTypes13 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _updateKBFolderParameterTypes14 =
		new Class[] {
			long.class, long.class, long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
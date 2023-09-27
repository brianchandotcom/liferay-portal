/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.exportimport.service.http;

import com.liferay.exportimport.kernel.service.StagingServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>StagingServiceUtil</code> service
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
public class StagingServiceHttp {

	public static void cleanUpStagingRequest(
			HttpPrincipal httpPrincipal, long stagingRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StagingServiceUtil.class, "cleanUpStagingRequest",
				_cleanUpStagingRequestParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, stagingRequestId);

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

	public static long createStagingRequest(
			HttpPrincipal httpPrincipal, long groupId, String checksum)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StagingServiceUtil.class, "createStagingRequest",
				_createStagingRequestParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, checksum);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void enableLocalStaging(
			HttpPrincipal httpPrincipal, long groupId, boolean branchingPublic,
			boolean branchingPrivate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StagingServiceUtil.class, "enableLocalStaging",
				_enableLocalStagingParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, branchingPublic, branchingPrivate,
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

	public static void enableRemoteStaging(
			HttpPrincipal httpPrincipal, long groupId, boolean branchingPublic,
			boolean branchingPrivate, String remoteAddress, int remotePort,
			String remotePathContext, boolean secureConnection,
			long remoteGroupId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StagingServiceUtil.class, "enableRemoteStaging",
				_enableRemoteStagingParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, branchingPublic, branchingPrivate,
				remoteAddress, remotePort, remotePathContext, secureConnection,
				remoteGroupId, serviceContext);

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

	public static boolean hasRemoteLayout(
			HttpPrincipal httpPrincipal, String uuid, long groupId,
			boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StagingServiceUtil.class, "hasRemoteLayout",
				_hasRemoteLayoutParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, uuid, groupId, privateLayout);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void propagateExportImportLifecycleEvent(
			HttpPrincipal httpPrincipal, int code, int processFlag,
			String processId, java.util.List<java.io.Serializable> arguments)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StagingServiceUtil.class, "propagateExportImportLifecycleEvent",
				_propagateExportImportLifecycleEventParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, code, processFlag, processId, arguments);

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

	public static com.liferay.exportimport.kernel.lar.MissingReferences
			publishStagingRequest(
				HttpPrincipal httpPrincipal, long stagingRequestId,
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StagingServiceUtil.class, "publishStagingRequest",
				_publishStagingRequestParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, stagingRequestId, exportImportConfiguration);

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

			return (com.liferay.exportimport.kernel.lar.MissingReferences)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void updateStagingRequest(
			HttpPrincipal httpPrincipal, long stagingRequestId, String fileName,
			byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				StagingServiceUtil.class, "updateStagingRequest",
				_updateStagingRequestParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, stagingRequestId, fileName, bytes);

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

	private static Log _log = LogFactoryUtil.getLog(StagingServiceHttp.class);

	private static final Class<?>[] _cleanUpStagingRequestParameterTypes0 =
		new Class[] {long.class};
	private static final Class<?>[] _createStagingRequestParameterTypes1 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _enableLocalStagingParameterTypes2 =
		new Class[] {
			long.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _enableRemoteStagingParameterTypes3 =
		new Class[] {
			long.class, boolean.class, boolean.class, String.class, int.class,
			String.class, boolean.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _hasRemoteLayoutParameterTypes4 =
		new Class[] {String.class, long.class, boolean.class};
	private static final Class<?>[]
		_propagateExportImportLifecycleEventParameterTypes5 = new Class[] {
			int.class, int.class, String.class, java.util.List.class
		};
	private static final Class<?>[] _publishStagingRequestParameterTypes6 =
		new Class[] {
			long.class,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class
		};
	private static final Class<?>[] _updateStagingRequestParameterTypes7 =
		new Class[] {long.class, String.class, byte[].class};

}
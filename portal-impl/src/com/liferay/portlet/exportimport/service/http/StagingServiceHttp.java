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

package com.liferay.portlet.exportimport.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.service.StagingServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link StagingServiceUtil} service utility. The
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
 * @see StagingServiceSoap
 * @see HttpPrincipal
 * @see StagingServiceUtil
 * @generated
 */
@ProviderType
public class StagingServiceHttp {
	public static void cleanUpStagingRequest(HttpPrincipal httpPrincipal,
		long stagingRequestId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(StagingServiceUtil.class,
					"cleanUpStagingRequest",
					_cleanUpStagingRequestParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					stagingRequestId);

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

	public static long createStagingRequest(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String checksum)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(StagingServiceUtil.class,
					"createStagingRequest", _createStagingRequestParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					checksum);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasRemoteLayout(HttpPrincipal httpPrincipal,
		java.lang.String uuid, long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(StagingServiceUtil.class,
					"hasRemoteLayout", _hasRemoteLayoutParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, uuid,
					groupId, privateLayout);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void propagateExportImportLifecycleEvent(
		HttpPrincipal httpPrincipal, int code, int processFlag,
		java.lang.String processId,
		java.util.List<java.io.Serializable> arguments)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(StagingServiceUtil.class,
					"propagateExportImportLifecycleEvent",
					_propagateExportImportLifecycleEventParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, code,
					processFlag, processId, arguments);

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

	public static com.liferay.exportimport.kernel.lar.MissingReferences publishStagingRequest(
		HttpPrincipal httpPrincipal, long stagingRequestId,
		boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(StagingServiceUtil.class,
					"publishStagingRequest",
					_publishStagingRequestParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					stagingRequestId, privateLayout, parameterMap);

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

			return (com.liferay.exportimport.kernel.lar.MissingReferences)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences publishStagingRequest(
		HttpPrincipal httpPrincipal, long stagingRequestId,
		com.liferay.exportimport.kernel.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(StagingServiceUtil.class,
					"publishStagingRequest",
					_publishStagingRequestParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					stagingRequestId, exportImportConfiguration);

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

			return (com.liferay.exportimport.kernel.lar.MissingReferences)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateStagingRequest(HttpPrincipal httpPrincipal,
		long stagingRequestId, java.lang.String fileName, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(StagingServiceUtil.class,
					"updateStagingRequest", _updateStagingRequestParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					stagingRequestId, fileName, bytes);

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

	public static com.liferay.exportimport.kernel.lar.MissingReferences validateStagingRequest(
		HttpPrincipal httpPrincipal, long stagingRequestId,
		boolean privateLayout,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(StagingServiceUtil.class,
					"validateStagingRequest",
					_validateStagingRequestParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					stagingRequestId, privateLayout, parameterMap);

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

			return (com.liferay.exportimport.kernel.lar.MissingReferences)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(StagingServiceHttp.class);
	private static final Class<?>[] _cleanUpStagingRequestParameterTypes0 = new Class[] {
			long.class
		};
	private static final Class<?>[] _createStagingRequestParameterTypes1 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _hasRemoteLayoutParameterTypes2 = new Class[] {
			java.lang.String.class, long.class, boolean.class
		};
	private static final Class<?>[] _propagateExportImportLifecycleEventParameterTypes3 =
		new Class[] {
			int.class, int.class, java.lang.String.class, java.util.List.class
		};
	private static final Class<?>[] _publishStagingRequestParameterTypes4 = new Class[] {
			long.class, boolean.class, java.util.Map.class
		};
	private static final Class<?>[] _publishStagingRequestParameterTypes5 = new Class[] {
			long.class,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.class
		};
	private static final Class<?>[] _updateStagingRequestParameterTypes6 = new Class[] {
			long.class, java.lang.String.class, byte[].class
		};
	private static final Class<?>[] _validateStagingRequestParameterTypes7 = new Class[] {
			long.class, boolean.class, java.util.Map.class
		};
}
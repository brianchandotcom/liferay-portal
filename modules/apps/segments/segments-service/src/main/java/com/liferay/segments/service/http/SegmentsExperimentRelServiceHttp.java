/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.segments.service.SegmentsExperimentRelServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SegmentsExperimentRelServiceUtil</code> service
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
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsExperimentRelServiceHttp {

	public static com.liferay.segments.model.SegmentsExperimentRel
			addSegmentsExperimentRel(
				HttpPrincipal httpPrincipal, long segmentsExperimentId,
				long segmentsExperienceId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentRelServiceUtil.class,
				"addSegmentsExperimentRel",
				_addSegmentsExperimentRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentId, segmentsExperienceId,
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

			return (com.liferay.segments.model.SegmentsExperimentRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentRel
			deleteSegmentsExperimentRel(
				HttpPrincipal httpPrincipal, long segmentsExperimentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentRelServiceUtil.class,
				"deleteSegmentsExperimentRel",
				_deleteSegmentsExperimentRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentRelId);

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

			return (com.liferay.segments.model.SegmentsExperimentRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentRel
			getSegmentsExperimentRel(
				HttpPrincipal httpPrincipal, long segmentsExperimentId,
				long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentRelServiceUtil.class,
				"getSegmentsExperimentRel",
				_getSegmentsExperimentRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentId, segmentsExperienceId);

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

			return (com.liferay.segments.model.SegmentsExperimentRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.segments.model.SegmentsExperimentRel>
				getSegmentsExperimentRels(
					HttpPrincipal httpPrincipal, long segmentsExperimentId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentRelServiceUtil.class,
				"getSegmentsExperimentRels",
				_getSegmentsExperimentRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentId);

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
				<com.liferay.segments.model.SegmentsExperimentRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentRel
			updateSegmentsExperimentRel(
				HttpPrincipal httpPrincipal, long segmentsExperimentRelId,
				double split)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentRelServiceUtil.class,
				"updateSegmentsExperimentRel",
				_updateSegmentsExperimentRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentRelId, split);

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

			return (com.liferay.segments.model.SegmentsExperimentRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.segments.model.SegmentsExperimentRel
			updateSegmentsExperimentRel(
				HttpPrincipal httpPrincipal, long segmentsExperimentRelId,
				String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SegmentsExperimentRelServiceUtil.class,
				"updateSegmentsExperimentRel",
				_updateSegmentsExperimentRelParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, segmentsExperimentRelId, name, serviceContext);

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

			return (com.liferay.segments.model.SegmentsExperimentRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentRelServiceHttp.class);

	private static final Class<?>[] _addSegmentsExperimentRelParameterTypes0 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteSegmentsExperimentRelParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[] _getSegmentsExperimentRelParameterTypes2 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getSegmentsExperimentRelsParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[]
		_updateSegmentsExperimentRelParameterTypes4 = new Class[] {
			long.class, double.class
		};
	private static final Class<?>[]
		_updateSegmentsExperimentRelParameterTypes5 = new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
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

package com.liferay.portlet.dynamicdatamapping.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.dynamicdatamapping.service.DDMStructureVersionServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.dynamicdatamapping.service.DDMStructureVersionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
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
 * @see DDMStructureVersionServiceSoap
 * @see com.liferay.portal.security.auth.HttpPrincipal
 * @see com.liferay.portlet.dynamicdatamapping.service.DDMStructureVersionServiceUtil
 * @generated
 */
@ProviderType
public class DDMStructureVersionServiceHttp {
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion getDDMStructureVersion(
		HttpPrincipal httpPrincipal, long ddmStructureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureVersionServiceUtil.class,
					"getDDMStructureVersion",
					_getDDMStructureVersionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					ddmStructureVersionId);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion> getDDMStructureVersions(
		HttpPrincipal httpPrincipal, long ddmStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureVersionServiceUtil.class,
					"getDDMStructureVersions",
					_getDDMStructureVersionsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					ddmStructureId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getDDMStructureVersionsCount(
		HttpPrincipal httpPrincipal, long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureVersionServiceUtil.class,
					"getDDMStructureVersionsCount",
					_getDDMStructureVersionsCountParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					ddmStructureId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion getLatestVersion(
		HttpPrincipal httpPrincipal, long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureVersionServiceUtil.class,
					"getLatestVersion", _getLatestVersionParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					ddmStructureId);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStructureVersionServiceHttp.class);
	private static final Class<?>[] _getDDMStructureVersionParameterTypes0 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getDDMStructureVersionsParameterTypes1 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getDDMStructureVersionsCountParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _getLatestVersionParameterTypes3 = new Class[] {
			long.class
		};
}
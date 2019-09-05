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

import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDMTemplateVersionServiceUtil</code> service
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
 * @see DDMTemplateVersionServiceSoap
 * @generated
 */
public class DDMTemplateVersionServiceHttp {

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateVersion
			getLatestTemplateVersion(
				HttpPrincipal httpPrincipal, long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateVersionServiceUtil.class, "getLatestTemplateVersion",
				_getLatestTemplateVersionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplateVersion)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateVersion
			getTemplateVersion(
				HttpPrincipal httpPrincipal, long templateVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateVersionServiceUtil.class, "getTemplateVersion",
				_getTemplateVersionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateVersionId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplateVersion)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplateVersion>
				getTemplateVersions(
					HttpPrincipal httpPrincipal, long templateId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.dynamic.data.mapping.model.
							DDMTemplateVersion> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateVersionServiceUtil.class, "getTemplateVersions",
				_getTemplateVersionsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId, start, end, orderByComparator);

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
				<com.liferay.dynamic.data.mapping.model.DDMTemplateVersion>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getTemplateVersionsCount(
			HttpPrincipal httpPrincipal, long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateVersionServiceUtil.class, "getTemplateVersionsCount",
				_getTemplateVersionsCountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMTemplateVersionServiceHttp.class);

	private static final Class<?>[] _getLatestTemplateVersionParameterTypes0 =
		new Class[] {long.class};
	private static final Class<?>[] _getTemplateVersionParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getTemplateVersionsParameterTypes2 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getTemplateVersionsCountParameterTypes3 =
		new Class[] {long.class};

}
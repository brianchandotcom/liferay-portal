/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.http;

import com.liferay.asset.kernel.service.AssetTagGroupRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>AssetTagGroupRelServiceUtil</code> service
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
public class AssetTagGroupRelServiceHttp {

	public static com.liferay.asset.kernel.model.AssetTagGroupRel
			addAssetTagGroupRel(
				HttpPrincipal httpPrincipal, long groupId, long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetTagGroupRelServiceUtil.class, "addAssetTagGroupRel",
				_addAssetTagGroupRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, tagId);

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

			return (com.liferay.asset.kernel.model.AssetTagGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.asset.kernel.model.AssetTagGroupRel>
				getAssetTagGroupRelsByTagId(
					HttpPrincipal httpPrincipal, long tagId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetTagGroupRelServiceUtil.class,
				"getAssetTagGroupRelsByTagId",
				_getAssetTagGroupRelsByTagIdParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, tagId);

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
				<com.liferay.asset.kernel.model.AssetTagGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void setAssetTagGroupRels(
			HttpPrincipal httpPrincipal, long tagId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AssetTagGroupRelServiceUtil.class, "setAssetTagGroupRels",
				_setAssetTagGroupRelsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, tagId, groupIds);

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
		AssetTagGroupRelServiceHttp.class);

	private static final Class<?>[] _addAssetTagGroupRelParameterTypes0 =
		new Class[] {long.class, long.class};
	private static final Class<?>[]
		_getAssetTagGroupRelsByTagIdParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[] _setAssetTagGroupRelsParameterTypes2 =
		new Class[] {long.class, long[].class};

}
// LIFERAY-SERVICE-BUILDER-HASH:-233832462
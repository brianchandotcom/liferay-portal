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

package com.liferay.screens.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.screens.service.ScreensRatingsEntryServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>ScreensRatingsEntryServiceUtil</code> service
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
 * @author José Manuel Navarro
 * @see ScreensRatingsEntryServiceSoap
 * @generated
 */
public class ScreensRatingsEntryServiceHttp {

	public static com.liferay.portal.kernel.json.JSONObject deleteRatingsEntry(
			HttpPrincipal httpPrincipal, long classPK, String className,
			int ratingsLength)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ScreensRatingsEntryServiceUtil.class, "deleteRatingsEntry",
				_deleteRatingsEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classPK, className, ratingsLength);

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

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONObject getRatingsEntries(
			HttpPrincipal httpPrincipal, long assetEntryId, int ratingsLength)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ScreensRatingsEntryServiceUtil.class, "getRatingsEntries",
				_getRatingsEntriesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetEntryId, ratingsLength);

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

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONObject getRatingsEntries(
			HttpPrincipal httpPrincipal, long classPK, String className,
			int ratingsLength)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ScreensRatingsEntryServiceUtil.class, "getRatingsEntries",
				_getRatingsEntriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classPK, className, ratingsLength);

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

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONObject updateRatingsEntry(
			HttpPrincipal httpPrincipal, long classPK, String className,
			double score, int ratingsLength)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ScreensRatingsEntryServiceUtil.class, "updateRatingsEntry",
				_updateRatingsEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classPK, className, score, ratingsLength);

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

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ScreensRatingsEntryServiceHttp.class);

	private static final Class<?>[] _deleteRatingsEntryParameterTypes0 =
		new Class[] {long.class, String.class, int.class};
	private static final Class<?>[] _getRatingsEntriesParameterTypes1 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getRatingsEntriesParameterTypes2 =
		new Class[] {long.class, String.class, int.class};
	private static final Class<?>[] _updateRatingsEntryParameterTypes3 =
		new Class[] {long.class, String.class, double.class, int.class};

}
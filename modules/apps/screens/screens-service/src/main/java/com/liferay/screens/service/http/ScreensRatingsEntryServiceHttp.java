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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import com.liferay.screens.service.ScreensRatingsEntryServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link ScreensRatingsEntryServiceUtil} service utility. The
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
 * @author José Manuel Navarro
 * @see ScreensRatingsEntryServiceSoap
 * @see HttpPrincipal
 * @see ScreensRatingsEntryServiceUtil
 * @generated
 */
@ProviderType
public class ScreensRatingsEntryServiceHttp {
	public static com.liferay.portal.kernel.json.JSONObject deleteRatingEntry(
		HttpPrincipal httpPrincipal, long classPK, java.lang.String className,
		int stepCount)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ScreensRatingsEntryServiceUtil.class,
					"deleteRatingEntry", _deleteRatingEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, classPK,
					className, stepCount);

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

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONObject updateRatingEntry(
		HttpPrincipal httpPrincipal, long classPK, java.lang.String className,
		double score, int stepCount)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ScreensRatingsEntryServiceUtil.class,
					"updateRatingEntry", _updateRatingEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, classPK,
					className, score, stepCount);

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

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONObject getRatingsEntries(
		HttpPrincipal httpPrincipal, long entryId, int stepCount)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ScreensRatingsEntryServiceUtil.class,
					"getRatingsEntries", _getRatingsEntriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, entryId,
					stepCount);

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

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.json.JSONObject getRatingsEntries(
		HttpPrincipal httpPrincipal, long classPK, java.lang.String className,
		int stepCount)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ScreensRatingsEntryServiceUtil.class,
					"getRatingsEntries", _getRatingsEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, classPK,
					className, stepCount);

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

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ScreensRatingsEntryServiceHttp.class);
	private static final Class<?>[] _deleteRatingEntryParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, int.class
		};
	private static final Class<?>[] _updateRatingEntryParameterTypes1 = new Class[] {
			long.class, java.lang.String.class, double.class, int.class
		};
	private static final Class<?>[] _getRatingsEntriesParameterTypes2 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _getRatingsEntriesParameterTypes3 = new Class[] {
			long.class, java.lang.String.class, int.class
		};
}
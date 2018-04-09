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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceTaxCategoryRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceTaxCategoryRelServiceUtil} service utility. The
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
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryRelServiceSoap
 * @see HttpPrincipal
 * @see CommerceTaxCategoryRelServiceUtil
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryRelServiceHttp {
	public static com.liferay.commerce.model.CommerceTaxCategoryRel updateCommerceTaxCategoryRel(
		HttpPrincipal httpPrincipal, long commerceTaxCategoryId,
		java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxCategoryRelServiceUtil.class,
					"updateCommerceTaxCategoryRel",
					_updateCommerceTaxCategoryRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTaxCategoryId, className, classPK, serviceContext);

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

			return (com.liferay.commerce.model.CommerceTaxCategoryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceTaxCategoryRel(
		HttpPrincipal httpPrincipal, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxCategoryRelServiceUtil.class,
					"deleteCommerceTaxCategoryRel",
					_deleteCommerceTaxCategoryRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK);

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

	public static com.liferay.commerce.model.CommerceTaxCategoryRel fetchCommerceTaxCategoryRel(
		HttpPrincipal httpPrincipal, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxCategoryRelServiceUtil.class,
					"fetchCommerceTaxCategoryRel",
					_fetchCommerceTaxCategoryRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK);

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

			return (com.liferay.commerce.model.CommerceTaxCategoryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceTaxCategoryRelServiceHttp.class);
	private static final Class<?>[] _updateCommerceTaxCategoryRelParameterTypes0 =
		new Class[] {
			long.class, java.lang.String.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceTaxCategoryRelParameterTypes1 =
		new Class[] { java.lang.String.class, long.class };
	private static final Class<?>[] _fetchCommerceTaxCategoryRelParameterTypes2 = new Class[] {
			java.lang.String.class, long.class
		};
}
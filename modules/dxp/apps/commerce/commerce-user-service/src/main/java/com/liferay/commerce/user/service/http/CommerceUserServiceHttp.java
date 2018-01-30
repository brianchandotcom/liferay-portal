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

package com.liferay.commerce.user.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.service.CommerceUserServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceUserServiceUtil} service utility. The
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
 * @author Marco Leo
 * @see CommerceUserServiceSoap
 * @see HttpPrincipal
 * @see CommerceUserServiceUtil
 * @generated
 */
@ProviderType
public class CommerceUserServiceHttp {
	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizations(
		HttpPrincipal httpPrincipal,
		com.liferay.portal.kernel.model.Organization organization,
		java.lang.String type, java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserServiceUtil.class,
					"searchOrganizations", _searchOrganizationsParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organization, type, keywords, start, end, sorts);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Organization addOrganization(
		HttpPrincipal httpPrincipal, long parentOrganization,
		java.lang.String name, java.lang.String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserServiceUtil.class,
					"addOrganization", _addOrganizationParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					parentOrganization, name, type, serviceContext);

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

			return (com.liferay.portal.kernel.model.Organization)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Address getOrganizationPrimaryAddress(
		HttpPrincipal httpPrincipal, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserServiceUtil.class,
					"getOrganizationPrimaryAddress",
					_getOrganizationPrimaryAddressParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationId);

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

			return (com.liferay.portal.kernel.model.Address)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.EmailAddress getOrganizationPrimaryEmailAddress(
		HttpPrincipal httpPrincipal, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserServiceUtil.class,
					"getOrganizationPrimaryEmailAddress",
					_getOrganizationPrimaryEmailAddressParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationId);

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

			return (com.liferay.portal.kernel.model.EmailAddress)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceUserServiceHttp.class);
	private static final Class<?>[] _searchOrganizationsParameterTypes0 = new Class[] {
			com.liferay.portal.kernel.model.Organization.class,
			java.lang.String.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort[].class
		};
	private static final Class<?>[] _addOrganizationParameterTypes2 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _getOrganizationPrimaryAddressParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _getOrganizationPrimaryEmailAddressParameterTypes4 =
		new Class[] { long.class };
}
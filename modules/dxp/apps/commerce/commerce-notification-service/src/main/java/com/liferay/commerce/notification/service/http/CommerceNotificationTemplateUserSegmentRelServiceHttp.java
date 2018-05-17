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

package com.liferay.commerce.notification.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.service.CommerceNotificationTemplateUserSegmentRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceNotificationTemplateUserSegmentRelServiceUtil} service utility. The
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
 * @see CommerceNotificationTemplateUserSegmentRelServiceSoap
 * @see HttpPrincipal
 * @see CommerceNotificationTemplateUserSegmentRelServiceUtil
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateUserSegmentRelServiceHttp {
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel addCommerceNotificationTemplateUserSegmentRel(
		HttpPrincipal httpPrincipal, long commerceNotificationTemplateId,
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceNotificationTemplateUserSegmentRelServiceUtil.class,
					"addCommerceNotificationTemplateUserSegmentRel",
					_addCommerceNotificationTemplateUserSegmentRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceNotificationTemplateId, commerceUserSegmentEntryId,
					serviceContext);

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

			return (com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceNotificationTemplateUserSegmentRel(
		HttpPrincipal httpPrincipal,
		long commerceNotificationTemplateUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceNotificationTemplateUserSegmentRelServiceUtil.class,
					"deleteCommerceNotificationTemplateUserSegmentRel",
					_deleteCommerceNotificationTemplateUserSegmentRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceNotificationTemplateUserSegmentRelId);

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

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel fetchCommerceNotificationTemplateUserSegmentRel(
		HttpPrincipal httpPrincipal, long commerceNotificationTemplateId,
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceNotificationTemplateUserSegmentRelServiceUtil.class,
					"fetchCommerceNotificationTemplateUserSegmentRel",
					_fetchCommerceNotificationTemplateUserSegmentRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceNotificationTemplateId, commerceUserSegmentEntryId);

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

			return (com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> getCommerceNotificationTemplateUserSegmentRels(
		HttpPrincipal httpPrincipal, long commerceNotificationTemplateId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceNotificationTemplateUserSegmentRelServiceUtil.class,
					"getCommerceNotificationTemplateUserSegmentRels",
					_getCommerceNotificationTemplateUserSegmentRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceNotificationTemplateId, start, end,
					orderByComparator);

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

			return (java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceNotificationTemplateUserSegmentRelServiceHttp.class);
	private static final Class<?>[] _addCommerceNotificationTemplateUserSegmentRelParameterTypes0 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceNotificationTemplateUserSegmentRelParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCommerceNotificationTemplateUserSegmentRelParameterTypes2 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _getCommerceNotificationTemplateUserSegmentRelsParameterTypes3 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
}
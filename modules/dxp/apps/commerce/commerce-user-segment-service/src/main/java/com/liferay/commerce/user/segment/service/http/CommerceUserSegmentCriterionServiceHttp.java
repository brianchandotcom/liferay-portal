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

package com.liferay.commerce.user.segment.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceUserSegmentCriterionServiceUtil} service utility. The
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
 * @see CommerceUserSegmentCriterionServiceSoap
 * @see HttpPrincipal
 * @see CommerceUserSegmentCriterionServiceUtil
 * @generated
 */
@ProviderType
public class CommerceUserSegmentCriterionServiceHttp {
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion addCommerceUserSegmentCriterion(
		HttpPrincipal httpPrincipal, long commerceUserSegmentEntryId,
		String type, String typeSettings, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserSegmentCriterionServiceUtil.class,
					"addCommerceUserSegmentCriterion",
					_addCommerceUserSegmentCriterionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceUserSegmentEntryId, type, typeSettings, priority,
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

			return (com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceUserSegmentCriterion(
		HttpPrincipal httpPrincipal, long commerceUserSegmentCriterionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserSegmentCriterionServiceUtil.class,
					"deleteCommerceUserSegmentCriterion",
					_deleteCommerceUserSegmentCriterionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceUserSegmentCriterionId);

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

	public static java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> getCommerceUserSegmentCriteria(
		HttpPrincipal httpPrincipal, long commerceUserSegmentEntryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserSegmentCriterionServiceUtil.class,
					"getCommerceUserSegmentCriteria",
					_getCommerceUserSegmentCriteriaParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceUserSegmentEntryId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceUserSegmentCriteriaCount(
		HttpPrincipal httpPrincipal, long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserSegmentCriterionServiceUtil.class,
					"getCommerceUserSegmentCriteriaCount",
					_getCommerceUserSegmentCriteriaCountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceUserSegmentEntryId);

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

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion getCommerceUserSegmentCriterion(
		HttpPrincipal httpPrincipal, long commerceUserSegmentCriterionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserSegmentCriterionServiceUtil.class,
					"getCommerceUserSegmentCriterion",
					_getCommerceUserSegmentCriterionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceUserSegmentCriterionId);

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

			return (com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion updateCommerceUserSegmentCriterion(
		HttpPrincipal httpPrincipal, long commerceUserSegmentCriterionId,
		String type, String typeSettings, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserSegmentCriterionServiceUtil.class,
					"updateCommerceUserSegmentCriterion",
					_updateCommerceUserSegmentCriterionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceUserSegmentCriterionId, type, typeSettings,
					priority, serviceContext);

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

			return (com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceUserSegmentCriterionServiceHttp.class);
	private static final Class<?>[] _addCommerceUserSegmentCriterionParameterTypes0 =
		new Class[] {
			long.class, String.class, String.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceUserSegmentCriterionParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceUserSegmentCriteriaParameterTypes2 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceUserSegmentCriteriaCountParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceUserSegmentCriterionParameterTypes4 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommerceUserSegmentCriterionParameterTypes5 =
		new Class[] {
			long.class, String.class, String.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
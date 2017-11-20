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

import com.liferay.commerce.service.CommercePriceListQualificationTypeRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommercePriceListQualificationTypeRelServiceUtil} service utility. The
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
 * @see CommercePriceListQualificationTypeRelServiceSoap
 * @see HttpPrincipal
 * @see CommercePriceListQualificationTypeRelServiceUtil
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelServiceHttp {
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel addCommercePriceListQualificationTypeRel(
		HttpPrincipal httpPrincipal, long commercePriceListId,
		java.lang.String commercePriceListQualificationType, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListQualificationTypeRelServiceUtil.class,
					"addCommercePriceListQualificationTypeRel",
					_addCommercePriceListQualificationTypeRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId, commercePriceListQualificationType,
					order, serviceContext);

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

			return (com.liferay.commerce.model.CommercePriceListQualificationTypeRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommercePriceListQualificationTypeRel(
		HttpPrincipal httpPrincipal,
		long commercePriceListQualificationTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListQualificationTypeRelServiceUtil.class,
					"deleteCommercePriceListQualificationTypeRel",
					_deleteCommercePriceListQualificationTypeRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListQualificationTypeRelId);

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

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel fetchCommercePriceListQualificationTypeRel(
		HttpPrincipal httpPrincipal,
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListQualificationTypeRelServiceUtil.class,
					"fetchCommercePriceListQualificationTypeRel",
					_fetchCommercePriceListQualificationTypeRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListQualificationType, commercePriceListId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommercePriceListQualificationTypeRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		HttpPrincipal httpPrincipal, long commercePriceListId) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListQualificationTypeRelServiceUtil.class,
					"getCommercePriceListQualificationTypeRels",
					_getCommercePriceListQualificationTypeRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		HttpPrincipal httpPrincipal, long commercePriceListId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListQualificationTypeRelServiceUtil.class,
					"getCommercePriceListQualificationTypeRels",
					_getCommercePriceListQualificationTypeRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommercePriceListQualificationTypeRelsCount(
		HttpPrincipal httpPrincipal, long commercePriceListId) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListQualificationTypeRelServiceUtil.class,
					"getCommercePriceListQualificationTypeRelsCount",
					_getCommercePriceListQualificationTypeRelsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel updateCommercePriceListQualificationTypeRel(
		HttpPrincipal httpPrincipal,
		long commercePriceListQualificationTypeRelId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListQualificationTypeRelServiceUtil.class,
					"updateCommercePriceListQualificationTypeRel",
					_updateCommercePriceListQualificationTypeRelParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListQualificationTypeRelId, order,
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

			return (com.liferay.commerce.model.CommercePriceListQualificationTypeRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommercePriceListQualificationTypeRelServiceHttp.class);
	private static final Class<?>[] _addCommercePriceListQualificationTypeRelParameterTypes0 =
		new Class[] {
			long.class, java.lang.String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommercePriceListQualificationTypeRelParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCommercePriceListQualificationTypeRelParameterTypes2 =
		new Class[] { java.lang.String.class, long.class };
	private static final Class<?>[] _getCommercePriceListQualificationTypeRelsParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommercePriceListQualificationTypeRelsParameterTypes4 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommercePriceListQualificationTypeRelsCountParameterTypes5 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommercePriceListQualificationTypeRelParameterTypes6 =
		new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
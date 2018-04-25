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

package com.liferay.commerce.price.list.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.service.CommercePriceListUserSegmentEntryRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommercePriceListUserSegmentEntryRelServiceUtil} service utility. The
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
 * @see CommercePriceListUserSegmentEntryRelServiceSoap
 * @see HttpPrincipal
 * @see CommercePriceListUserSegmentEntryRelServiceUtil
 * @generated
 */
@ProviderType
public class CommercePriceListUserSegmentEntryRelServiceHttp {
	public static com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel addCommercePriceListUserSegmentEntryRel(
		HttpPrincipal httpPrincipal, long commercePriceListId,
		long commerceUserSegmentEntryId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserSegmentEntryRelServiceUtil.class,
					"addCommercePriceListUserSegmentEntryRel",
					_addCommercePriceListUserSegmentEntryRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId, commerceUserSegmentEntryId, order,
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

			return (com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommercePriceListUserSegmentEntryRel(
		HttpPrincipal httpPrincipal, long commercePriceListUserSegmentEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserSegmentEntryRelServiceUtil.class,
					"deleteCommercePriceListUserSegmentEntryRel",
					_deleteCommercePriceListUserSegmentEntryRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListUserSegmentEntryRelId);

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

	public static com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRel(
		HttpPrincipal httpPrincipal, long commercePriceListId,
		long commerceUserSegmentEntryId) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserSegmentEntryRelServiceUtil.class,
					"fetchCommercePriceListUserSegmentEntryRel",
					_fetchCommercePriceListUserSegmentEntryRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId, commerceUserSegmentEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		HttpPrincipal httpPrincipal, long commercePriceListId) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserSegmentEntryRelServiceUtil.class,
					"getCommercePriceListUserSegmentEntryRels",
					_getCommercePriceListUserSegmentEntryRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		HttpPrincipal httpPrincipal, long commercePriceListId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserSegmentEntryRelServiceUtil.class,
					"getCommercePriceListUserSegmentEntryRels",
					_getCommercePriceListUserSegmentEntryRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommercePriceListUserSegmentEntryRelsCount(
		HttpPrincipal httpPrincipal, long commercePriceListId) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserSegmentEntryRelServiceUtil.class,
					"getCommercePriceListUserSegmentEntryRelsCount",
					_getCommercePriceListUserSegmentEntryRelsCountParameterTypes5);

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

	public static com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel updateCommercePriceListUserSegmentEntryRel(
		HttpPrincipal httpPrincipal,
		long commercePriceListUserSegmentEntryRelId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserSegmentEntryRelServiceUtil.class,
					"updateCommercePriceListUserSegmentEntryRel",
					_updateCommercePriceListUserSegmentEntryRelParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListUserSegmentEntryRelId, order,
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

			return (com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommercePriceListUserSegmentEntryRelServiceHttp.class);
	private static final Class<?>[] _addCommercePriceListUserSegmentEntryRelParameterTypes0 =
		new Class[] {
			long.class, long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommercePriceListUserSegmentEntryRelParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCommercePriceListUserSegmentEntryRelParameterTypes2 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _getCommercePriceListUserSegmentEntryRelsParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommercePriceListUserSegmentEntryRelsParameterTypes4 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommercePriceListUserSegmentEntryRelsCountParameterTypes5 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommercePriceListUserSegmentEntryRelParameterTypes6 =
		new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
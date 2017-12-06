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

package com.liferay.commerce.price.list.qualification.type.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.qualification.type.service.CommercePriceListUserRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommercePriceListUserRelServiceUtil} service utility. The
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
 * @see CommercePriceListUserRelServiceSoap
 * @see HttpPrincipal
 * @see CommercePriceListUserRelServiceUtil
 * @generated
 */
@ProviderType
public class CommercePriceListUserRelServiceHttp {
	public static com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel addCommercePriceListUserRel(
		HttpPrincipal httpPrincipal,
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserRelServiceUtil.class,
					"addCommercePriceListUserRel",
					_addCommercePriceListUserRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListQualificationTypeRelId, className,
					classPK, serviceContext);

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

			return (com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommercePriceListUserRel(
		HttpPrincipal httpPrincipal, long commercePriceListUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserRelServiceUtil.class,
					"deleteCommercePriceListUserRel",
					_deleteCommercePriceListUserRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListUserRelId);

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

	public static void deleteCommercePriceListUserRels(
		HttpPrincipal httpPrincipal,
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, long classPK) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserRelServiceUtil.class,
					"deleteCommercePriceListUserRels",
					_deleteCommercePriceListUserRelsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListQualificationTypeRelId, className, classPK);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel getCommercePriceListUserRel(
		HttpPrincipal httpPrincipal, long commercePriceListUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserRelServiceUtil.class,
					"getCommercePriceListUserRel",
					_getCommercePriceListUserRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListUserRelId);

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

			return (com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRels(
		HttpPrincipal httpPrincipal,
		long commercePriceListQualificationTypeRelId, java.lang.String className) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserRelServiceUtil.class,
					"getCommercePriceListUserRels",
					_getCommercePriceListUserRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListQualificationTypeRelId, className);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRels(
		HttpPrincipal httpPrincipal,
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, int start, int end) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserRelServiceUtil.class,
					"getCommercePriceListUserRels",
					_getCommercePriceListUserRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListQualificationTypeRelId, className, start,
					end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> getCommercePriceListUserRels(
		HttpPrincipal httpPrincipal,
		long commercePriceListQualificationTypeRelId,
		java.lang.String className, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserRelServiceUtil.class,
					"getCommercePriceListUserRels",
					_getCommercePriceListUserRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListQualificationTypeRelId, className, start,
					end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommercePriceListUserRelsCount(
		HttpPrincipal httpPrincipal,
		long commercePriceListQualificationTypeRelId, java.lang.String className) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserRelServiceUtil.class,
					"getCommercePriceListUserRelsCount",
					_getCommercePriceListUserRelsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListQualificationTypeRelId, className);

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

	public static com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel updateCommercePriceListUserRel(
		HttpPrincipal httpPrincipal, long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceListUserRelServiceUtil.class,
					"updateCommercePriceListUserRel",
					_updateCommercePriceListUserRelParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListUserRelId,
					commercePriceListQualificationTypeRelId, serviceContext);

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

			return (com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommercePriceListUserRelServiceHttp.class);
	private static final Class<?>[] _addCommercePriceListUserRelParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommercePriceListUserRelParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _deleteCommercePriceListUserRelsParameterTypes2 =
		new Class[] { long.class, java.lang.String.class, long.class };
	private static final Class<?>[] _getCommercePriceListUserRelParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommercePriceListUserRelsParameterTypes4 =
		new Class[] { long.class, java.lang.String.class };
	private static final Class<?>[] _getCommercePriceListUserRelsParameterTypes5 =
		new Class[] { long.class, java.lang.String.class, int.class, int.class };
	private static final Class<?>[] _getCommercePriceListUserRelsParameterTypes6 =
		new Class[] {
			long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommercePriceListUserRelsCountParameterTypes7 =
		new Class[] { long.class, java.lang.String.class };
	private static final Class<?>[] _updateCommercePriceListUserRelParameterTypes8 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
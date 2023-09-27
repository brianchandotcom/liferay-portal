/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.service.http;

import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommercePriceListCommerceAccountGroupRelServiceUtil</code> service
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
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListCommerceAccountGroupRelServiceHttp {

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRel
				addCommercePriceListCommerceAccountGroupRel(
					HttpPrincipal httpPrincipal, long commercePriceListId,
					long commerceAccountGroupId, int order,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"addCommercePriceListCommerceAccountGroupRel",
				_addCommercePriceListCommerceAccountGroupRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, commerceAccountGroupId, order,
				serviceContext);

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

			return (com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void
			deleteCommercePriceListAccountGroupRelsByCommercePriceListId(
				HttpPrincipal httpPrincipal, long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"deleteCommercePriceListAccountGroupRelsByCommercePriceListId",
				_deleteCommercePriceListAccountGroupRelsByCommercePriceListIdParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId);

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

	public static void deleteCommercePriceListCommerceAccountGroupRel(
			HttpPrincipal httpPrincipal,
			long commercePriceListCommerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"deleteCommercePriceListCommerceAccountGroupRel",
				_deleteCommercePriceListCommerceAccountGroupRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListCommerceAccountGroupRelId);

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

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRel
				fetchCommercePriceListCommerceAccountGroupRel(
					HttpPrincipal httpPrincipal, long commercePriceListId,
					long commerceAccountGroupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"fetchCommercePriceListCommerceAccountGroupRel",
				_fetchCommercePriceListCommerceAccountGroupRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, commerceAccountGroupId);

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

			return (com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRel
				getCommercePriceListCommerceAccountGroupRel(
					HttpPrincipal httpPrincipal,
					long commercePriceListCommerceAccoungGroupRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"getCommercePriceListCommerceAccountGroupRel",
				_getCommercePriceListCommerceAccountGroupRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListCommerceAccoungGroupRelId);

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

			return (com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.
			CommercePriceListCommerceAccountGroupRel>
					getCommercePriceListCommerceAccountGroupRels(
						HttpPrincipal httpPrincipal, long commercePriceListId)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"getCommercePriceListCommerceAccountGroupRels",
				_getCommercePriceListCommerceAccountGroupRelsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId);

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
				<com.liferay.commerce.price.list.model.
					CommercePriceListCommerceAccountGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.
			CommercePriceListCommerceAccountGroupRel>
					getCommercePriceListCommerceAccountGroupRels(
						HttpPrincipal httpPrincipal, long commercePriceListId,
						int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.price.list.model.
								CommercePriceListCommerceAccountGroupRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"getCommercePriceListCommerceAccountGroupRels",
				_getCommercePriceListCommerceAccountGroupRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, start, end, orderByComparator);

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
				<com.liferay.commerce.price.list.model.
					CommercePriceListCommerceAccountGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.
			CommercePriceListCommerceAccountGroupRel>
				getCommercePriceListCommerceAccountGroupRels(
					HttpPrincipal httpPrincipal, long commercePriceListId,
					String name, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"getCommercePriceListCommerceAccountGroupRels",
				_getCommercePriceListCommerceAccountGroupRelsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, name, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.price.list.model.
					CommercePriceListCommerceAccountGroupRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePriceListCommerceAccountGroupRelsCount(
			HttpPrincipal httpPrincipal, long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"getCommercePriceListCommerceAccountGroupRelsCount",
				_getCommercePriceListCommerceAccountGroupRelsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePriceListCommerceAccountGroupRelsCount(
		HttpPrincipal httpPrincipal, long commercePriceListId, String name) {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"getCommercePriceListCommerceAccountGroupRelsCount",
				_getCommercePriceListCommerceAccountGroupRelsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.
		CommercePriceListCommerceAccountGroupRel
				updateCommercePriceListCommerceAccountGroupRel(
					HttpPrincipal httpPrincipal,
					long commercePriceListCommerceAccountGroupRelId, int order,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceListCommerceAccountGroupRelServiceUtil.class,
				"updateCommercePriceListCommerceAccountGroupRel",
				_updateCommercePriceListCommerceAccountGroupRelParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListCommerceAccountGroupRelId, order,
				serviceContext);

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

			return (com.liferay.commerce.price.list.model.
				CommercePriceListCommerceAccountGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePriceListCommerceAccountGroupRelServiceHttp.class);

	private static final Class<?>[]
		_addCommercePriceListCommerceAccountGroupRelParameterTypes0 =
			new Class[] {
				long.class, long.class, int.class,
				com.liferay.portal.kernel.service.ServiceContext.class
			};
	private static final Class<?>[]
		_deleteCommercePriceListAccountGroupRelsByCommercePriceListIdParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_deleteCommercePriceListCommerceAccountGroupRelParameterTypes2 =
			new Class[] {long.class};
	private static final Class<?>[]
		_fetchCommercePriceListCommerceAccountGroupRelParameterTypes3 =
			new Class[] {long.class, long.class};
	private static final Class<?>[]
		_getCommercePriceListCommerceAccountGroupRelParameterTypes4 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommercePriceListCommerceAccountGroupRelsParameterTypes5 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommercePriceListCommerceAccountGroupRelsParameterTypes6 =
			new Class[] {
				long.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getCommercePriceListCommerceAccountGroupRelsParameterTypes7 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommercePriceListCommerceAccountGroupRelsCountParameterTypes8 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommercePriceListCommerceAccountGroupRelsCountParameterTypes9 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_updateCommercePriceListCommerceAccountGroupRelParameterTypes10 =
			new Class[] {
				long.class, int.class,
				com.liferay.portal.kernel.service.ServiceContext.class
			};

}
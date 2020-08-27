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

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CPDefinitionLinkServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDefinitionLinkServiceUtil</code> service
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
 * @author Marco Leo
 * @see CPDefinitionLinkServiceSoap
 * @generated
 */
public class CPDefinitionLinkServiceHttp {

	public static com.liferay.commerce.product.model.CPDefinitionLink
			addCPDefinitionLink(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				long cProductId, double priority, String type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "addCPDefinitionLink",
				_addCPDefinitionLinkParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, cProductId, priority, type,
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

			return (com.liferay.commerce.product.model.CPDefinitionLink)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCPDefinitionLink(
			HttpPrincipal httpPrincipal, long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "deleteCPDefinitionLink",
				_deleteCPDefinitionLinkParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionLinkId);

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

	public static com.liferay.commerce.product.model.CPDefinitionLink
			fetchCPDefinitionLink(
				HttpPrincipal httpPrincipal, long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "fetchCPDefinitionLink",
				_fetchCPDefinitionLinkParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionLinkId);

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

			return (com.liferay.commerce.product.model.CPDefinitionLink)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink
			getCPDefinitionLink(
				HttpPrincipal httpPrincipal, long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "getCPDefinitionLink",
				_getCPDefinitionLinkParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionLinkId);

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

			return (com.liferay.commerce.product.model.CPDefinitionLink)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPDefinitionLink>
				getCPDefinitionLinks(
					HttpPrincipal httpPrincipal, long cpDefinitionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "getCPDefinitionLinks",
				_getCPDefinitionLinksParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId);

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
				<com.liferay.commerce.product.model.CPDefinitionLink>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPDefinitionLink>
				getCPDefinitionLinks(
					HttpPrincipal httpPrincipal, long cpDefinitionId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "getCPDefinitionLinks",
				_getCPDefinitionLinksParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, start, end);

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
				<com.liferay.commerce.product.model.CPDefinitionLink>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPDefinitionLink>
				getCPDefinitionLinks(
					HttpPrincipal httpPrincipal, long cpDefinitionId,
					String type)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "getCPDefinitionLinks",
				_getCPDefinitionLinksParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, type);

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
				<com.liferay.commerce.product.model.CPDefinitionLink>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPDefinitionLink>
				getCPDefinitionLinks(
					HttpPrincipal httpPrincipal, long cpDefinitionId,
					String type, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.model.CPDefinitionLink>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "getCPDefinitionLinks",
				_getCPDefinitionLinksParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, type, start, end, orderByComparator);

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
				<com.liferay.commerce.product.model.CPDefinitionLink>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPDefinitionLinksCount(
			HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "getCPDefinitionLinksCount",
				_getCPDefinitionLinksCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId);

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

	public static int getCPDefinitionLinksCount(
			HttpPrincipal httpPrincipal, long cpDefinitionId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "getCPDefinitionLinksCount",
				_getCPDefinitionLinksCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, type);

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

	public static com.liferay.commerce.product.model.CPDefinitionLink
			updateCPDefinitionLink(
				HttpPrincipal httpPrincipal, long cpDefinitionLinkId,
				double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "updateCPDefinitionLink",
				_updateCPDefinitionLinkParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionLinkId, priority, serviceContext);

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

			return (com.liferay.commerce.product.model.CPDefinitionLink)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void updateCPDefinitionLinks(
			HttpPrincipal httpPrincipal, long cpDefinitionId,
			long[] cpDefinitionIds2, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionLinkServiceUtil.class, "updateCPDefinitionLinks",
				_updateCPDefinitionLinksParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, cpDefinitionIds2, type,
				serviceContext);

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

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionLinkServiceHttp.class);

	private static final Class<?>[] _addCPDefinitionLinkParameterTypes0 =
		new Class[] {
			long.class, long.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPDefinitionLinkParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchCPDefinitionLinkParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getCPDefinitionLinkParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getCPDefinitionLinksParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getCPDefinitionLinksParameterTypes5 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getCPDefinitionLinksParameterTypes6 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getCPDefinitionLinksParameterTypes7 =
		new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPDefinitionLinksCountParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _getCPDefinitionLinksCountParameterTypes9 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateCPDefinitionLinkParameterTypes10 =
		new Class[] {
			long.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCPDefinitionLinksParameterTypes11 =
		new Class[] {
			long.class, long[].class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
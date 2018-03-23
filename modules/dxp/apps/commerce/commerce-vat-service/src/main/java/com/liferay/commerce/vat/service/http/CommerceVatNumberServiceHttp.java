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

package com.liferay.commerce.vat.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.vat.service.CommerceVatNumberServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceVatNumberServiceUtil} service utility. The
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
 * @see CommerceVatNumberServiceSoap
 * @see HttpPrincipal
 * @see CommerceVatNumberServiceUtil
 * @generated
 */
@ProviderType
public class CommerceVatNumberServiceHttp {
	public static com.liferay.commerce.vat.model.CommerceVatNumber addCommerceVatNumber(
		HttpPrincipal httpPrincipal, java.lang.String className, long classPK,
		java.lang.String value,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceVatNumberServiceUtil.class,
					"addCommerceVatNumber", _addCommerceVatNumberParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, value, serviceContext);

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

			return (com.liferay.commerce.vat.model.CommerceVatNumber)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceVatNumber(HttpPrincipal httpPrincipal,
		long commerceVatNumberId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceVatNumberServiceUtil.class,
					"deleteCommerceVatNumber",
					_deleteCommerceVatNumberParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceVatNumberId);

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

	public static com.liferay.commerce.vat.model.CommerceVatNumber fetchCommerceVatNumber(
		HttpPrincipal httpPrincipal, long commerceVatNumberId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceVatNumberServiceUtil.class,
					"fetchCommerceVatNumber",
					_fetchCommerceVatNumberParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceVatNumberId);

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

			return (com.liferay.commerce.vat.model.CommerceVatNumber)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumber fetchCommerceVatNumber(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceVatNumberServiceUtil.class,
					"fetchCommerceVatNumber",
					_fetchCommerceVatNumberParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
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

			return (com.liferay.commerce.vat.model.CommerceVatNumber)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.vat.model.CommerceVatNumber> getCommerceVatNumbers(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.vat.model.CommerceVatNumber> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceVatNumberServiceUtil.class,
					"getCommerceVatNumbers",
					_getCommerceVatNumbersParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.vat.model.CommerceVatNumber>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceVatNumbersCount(HttpPrincipal httpPrincipal,
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceVatNumberServiceUtil.class,
					"getCommerceVatNumbersCount",
					_getCommerceVatNumbersCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.vat.model.CommerceVatNumber> searchCommerceVatNumbers(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceVatNumberServiceUtil.class,
					"searchCommerceVatNumbers",
					_searchCommerceVatNumbersParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, keywords, start, end, sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.vat.model.CommerceVatNumber>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumber updateCommerceVatNumber(
		HttpPrincipal httpPrincipal, long commerceVatNumberId,
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceVatNumberServiceUtil.class,
					"updateCommerceVatNumber",
					_updateCommerceVatNumberParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceVatNumberId, value);

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

			return (com.liferay.commerce.vat.model.CommerceVatNumber)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceVatNumberServiceHttp.class);
	private static final Class<?>[] _addCommerceVatNumberParameterTypes0 = new Class[] {
			java.lang.String.class, long.class, java.lang.String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceVatNumberParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceVatNumberParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceVatNumberParameterTypes3 = new Class[] {
			long.class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _getCommerceVatNumbersParameterTypes4 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceVatNumbersCountParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _searchCommerceVatNumbersParameterTypes6 = new Class[] {
			long.class, long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCommerceVatNumberParameterTypes7 = new Class[] {
			long.class, java.lang.String.class
		};
}
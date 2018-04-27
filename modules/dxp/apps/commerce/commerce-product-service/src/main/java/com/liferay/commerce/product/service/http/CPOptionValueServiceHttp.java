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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPOptionValueServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPOptionValueServiceUtil} service utility. The
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
 * @see CPOptionValueServiceSoap
 * @see HttpPrincipal
 * @see CPOptionValueServiceUtil
 * @generated
 */
@ProviderType
public class CPOptionValueServiceHttp {
	public static com.liferay.commerce.product.model.CPOptionValue addCPOptionValue(
		HttpPrincipal httpPrincipal, long cpOptionId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"addCPOptionValue", _addCPOptionValueParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId, titleMap, priority, key, serviceContext);

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

			return (com.liferay.commerce.product.model.CPOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValue deleteCPOptionValue(
		HttpPrincipal httpPrincipal,
		com.liferay.commerce.product.model.CPOptionValue cpOptionValue)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"deleteCPOptionValue", _deleteCPOptionValueParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionValue);

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

			return (com.liferay.commerce.product.model.CPOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValue deleteCPOptionValue(
		HttpPrincipal httpPrincipal, long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"deleteCPOptionValue", _deleteCPOptionValueParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionValueId);

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

			return (com.liferay.commerce.product.model.CPOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValue fetchCPOptionValue(
		HttpPrincipal httpPrincipal, long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"fetchCPOptionValue", _fetchCPOptionValueParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionValueId);

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

			return (com.liferay.commerce.product.model.CPOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValue getCPOptionValue(
		HttpPrincipal httpPrincipal, long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"getCPOptionValue", _getCPOptionValueParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionValueId);

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

			return (com.liferay.commerce.product.model.CPOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
		HttpPrincipal httpPrincipal, long cpOptionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"getCPOptionValues", _getCPOptionValuesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId, start, end);

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

			return (java.util.List<com.liferay.commerce.product.model.CPOptionValue>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
		HttpPrincipal httpPrincipal, long cpOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionValue> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"getCPOptionValues", _getCPOptionValuesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.product.model.CPOptionValue>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPOptionValuesCount(HttpPrincipal httpPrincipal,
		long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"getCPOptionValuesCount",
					_getCPOptionValuesCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId);

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

	public static com.liferay.portal.kernel.search.Hits search(
		HttpPrincipal httpPrincipal,
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"search", _searchParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					searchContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPOptionValue> searchCPOptionValues(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long cpOptionId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"searchCPOptionValues", _searchCPOptionValuesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, cpOptionId, keywords, start, end, sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPOptionValue>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOptionValue updateCPOptionValue(
		HttpPrincipal httpPrincipal, long cpOptionValueId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionValueServiceUtil.class,
					"updateCPOptionValue", _updateCPOptionValueParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionValueId, titleMap, priority, key, serviceContext);

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

			return (com.liferay.commerce.product.model.CPOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPOptionValueServiceHttp.class);
	private static final Class<?>[] _addCPOptionValueParameterTypes0 = new Class[] {
			long.class, java.util.Map.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPOptionValueParameterTypes1 = new Class[] {
			com.liferay.commerce.product.model.CPOptionValue.class
		};
	private static final Class<?>[] _deleteCPOptionValueParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPOptionValueParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPOptionValueParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPOptionValuesParameterTypes5 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCPOptionValuesParameterTypes6 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPOptionValuesCountParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[] _searchParameterTypes8 = new Class[] {
			com.liferay.portal.kernel.search.SearchContext.class
		};
	private static final Class<?>[] _searchCPOptionValuesParameterTypes9 = new Class[] {
			long.class, long.class, long.class, String.class, int.class,
			int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCPOptionValueParameterTypes10 = new Class[] {
			long.class, java.util.Map.class, double.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
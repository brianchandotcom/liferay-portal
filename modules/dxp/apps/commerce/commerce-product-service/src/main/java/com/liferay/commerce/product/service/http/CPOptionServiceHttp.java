/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPOptionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPOptionServiceUtil} service utility. The
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
 * @see CPOptionServiceSoap
 * @see HttpPrincipal
 * @see CPOptionServiceUtil
 * @generated
 */
@ProviderType
public class CPOptionServiceHttp {
	public static com.liferay.commerce.product.model.CPOption addCPOption(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, boolean facetable, boolean required,
		boolean skuContributor, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"addCPOption", _addCPOptionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, nameMap,
					descriptionMap, ddmFormFieldTypeName, facetable, required,
					skuContributor, key, serviceContext);

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

			return (com.liferay.commerce.product.model.CPOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCPOption(HttpPrincipal httpPrincipal,
		long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"deleteCPOption", _deleteCPOptionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId);

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

	public static com.liferay.commerce.product.model.CPOption fetchCPOption(
		HttpPrincipal httpPrincipal, long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"fetchCPOption", _fetchCPOptionParameterTypes2);

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

			return (com.liferay.commerce.product.model.CPOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOption fetchCPOption(
		HttpPrincipal httpPrincipal, long groupId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"fetchCPOption", _fetchCPOptionParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					key);

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

			return (com.liferay.commerce.product.model.CPOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOption getCPOption(
		HttpPrincipal httpPrincipal, long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"getCPOption", _getCPOptionParameterTypes4);

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

			return (com.liferay.commerce.product.model.CPOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptions(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOption> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"getCPOptions", _getCPOptionsParameterTypes5);

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

			return (java.util.List<com.liferay.commerce.product.model.CPOption>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPOptionsCount(HttpPrincipal httpPrincipal,
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"getCPOptionsCount", _getCPOptionsCountParameterTypes6);

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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPOption> searchCPOptions(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"searchCPOptions", _searchCPOptionsParameterTypes7);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPOption>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPOption updateCPOption(
		HttpPrincipal httpPrincipal, long cpOptionId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, boolean facetable, boolean required,
		boolean skuContributor, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"updateCPOption", _updateCPOptionParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
					facetable, required, skuContributor, key, serviceContext);

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

			return (com.liferay.commerce.product.model.CPOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPOptionServiceHttp.class);
	private static final Class<?>[] _addCPOptionParameterTypes0 = new Class[] {
			java.util.Map.class, java.util.Map.class, String.class,
			boolean.class, boolean.class, boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPOptionParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPOptionParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPOptionParameterTypes3 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getCPOptionParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPOptionsParameterTypes5 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPOptionsCountParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _searchCPOptionsParameterTypes7 = new Class[] {
			long.class, long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCPOptionParameterTypes8 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, String.class,
			boolean.class, boolean.class, boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
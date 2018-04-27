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
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, boolean facetable, boolean required,
		boolean skuContributor, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"addCPOption", _addCPOptionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					titleMap, descriptionMap, ddmFormFieldTypeName, facetable,
					required, skuContributor, key, serviceContext);

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
		HttpPrincipal httpPrincipal, long groupId, int start, int end) {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"getCPOptions", _getCPOptionsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.product.model.CPOption>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptions(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOption> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"getCPOptions", _getCPOptionsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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
		long groupId) {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"getCPOptionsCount", _getCPOptionsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static com.liferay.portal.kernel.search.Hits search(
		HttpPrincipal httpPrincipal,
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPOption> searchCPOptions(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"searchCPOptions", _searchCPOptionsParameterTypes9);

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

	public static com.liferay.commerce.product.model.CPOption setFacetable(
		HttpPrincipal httpPrincipal, long cpOptionId, boolean facetable)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"setFacetable", _setFacetableParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId, facetable);

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

	public static com.liferay.commerce.product.model.CPOption setRequired(
		HttpPrincipal httpPrincipal, long cpOptionId, boolean required)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"setRequired", _setRequiredParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId, required);

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

	public static com.liferay.commerce.product.model.CPOption setSkuContributor(
		HttpPrincipal httpPrincipal, long cpOptionId, boolean skuContributor)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"setSkuContributor", _setSkuContributorParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId, skuContributor);

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

	public static com.liferay.commerce.product.model.CPOption updateCPOption(
		HttpPrincipal httpPrincipal, long cpOptionId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, boolean facetable, boolean required,
		boolean skuContributor, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPOptionServiceUtil.class,
					"updateCPOption", _updateCPOptionParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionId, titleMap, descriptionMap, ddmFormFieldTypeName,
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
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCPOptionsParameterTypes6 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPOptionsCountParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[] _searchParameterTypes8 = new Class[] {
			com.liferay.portal.kernel.search.SearchContext.class
		};
	private static final Class<?>[] _searchCPOptionsParameterTypes9 = new Class[] {
			long.class, long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _setFacetableParameterTypes10 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _setRequiredParameterTypes11 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _setSkuContributorParameterTypes12 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateCPOptionParameterTypes13 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, String.class,
			boolean.class, boolean.class, boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
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

import com.liferay.commerce.product.service.CPSpecificationOptionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPSpecificationOptionServiceUtil} service utility. The
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
 * @see CPSpecificationOptionServiceSoap
 * @see HttpPrincipal
 * @see CPSpecificationOptionServiceUtil
 * @generated
 */
@ProviderType
public class CPSpecificationOptionServiceHttp {
	public static com.liferay.commerce.product.model.CPSpecificationOption addCPSpecificationOption(
		HttpPrincipal httpPrincipal, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean facetable, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPSpecificationOptionServiceUtil.class,
					"addCPSpecificationOption",
					_addCPSpecificationOptionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpOptionCategoryId, titleMap, descriptionMap, facetable,
					key, serviceContext);

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

			return (com.liferay.commerce.product.model.CPSpecificationOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCPSpecificationOption(
		HttpPrincipal httpPrincipal, long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPSpecificationOptionServiceUtil.class,
					"deleteCPSpecificationOption",
					_deleteCPSpecificationOptionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpSpecificationOptionId);

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

	public static com.liferay.commerce.product.model.CPSpecificationOption fetchCPSpecificationOption(
		HttpPrincipal httpPrincipal, long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPSpecificationOptionServiceUtil.class,
					"fetchCPSpecificationOption",
					_fetchCPSpecificationOptionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpSpecificationOptionId);

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

			return (com.liferay.commerce.product.model.CPSpecificationOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPSpecificationOption fetchCPSpecificationOption(
		HttpPrincipal httpPrincipal, long groupId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPSpecificationOptionServiceUtil.class,
					"fetchCPSpecificationOption",
					_fetchCPSpecificationOptionParameterTypes3);

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

			return (com.liferay.commerce.product.model.CPSpecificationOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPSpecificationOption getCPSpecificationOption(
		HttpPrincipal httpPrincipal, long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPSpecificationOptionServiceUtil.class,
					"getCPSpecificationOption",
					_getCPSpecificationOptionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpSpecificationOptionId);

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

			return (com.liferay.commerce.product.model.CPSpecificationOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPSpecificationOption> getCPSpecificationOptions(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPSpecificationOption> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPSpecificationOptionServiceUtil.class,
					"getCPSpecificationOptions",
					_getCPSpecificationOptionsParameterTypes5);

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

			return (java.util.List<com.liferay.commerce.product.model.CPSpecificationOption>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPSpecificationOptionsCount(
		HttpPrincipal httpPrincipal, long groupId) {
		try {
			MethodKey methodKey = new MethodKey(CPSpecificationOptionServiceUtil.class,
					"getCPSpecificationOptionsCount",
					_getCPSpecificationOptionsCountParameterTypes6);

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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPSpecificationOption> searchCPSpecificationOptions(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		Boolean facetable, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPSpecificationOptionServiceUtil.class,
					"searchCPSpecificationOptions",
					_searchCPSpecificationOptionsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, facetable, keywords, start, end, sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPSpecificationOption>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPSpecificationOption updateCPSpecificationOption(
		HttpPrincipal httpPrincipal, long cpSpecificationOptionId,
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean facetable, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPSpecificationOptionServiceUtil.class,
					"updateCPSpecificationOption",
					_updateCPSpecificationOptionParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpSpecificationOptionId, cpOptionCategoryId, titleMap,
					descriptionMap, facetable, key, serviceContext);

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

			return (com.liferay.commerce.product.model.CPSpecificationOption)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPSpecificationOptionServiceHttp.class);
	private static final Class<?>[] _addCPSpecificationOptionParameterTypes0 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, boolean.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPSpecificationOptionParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPSpecificationOptionParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPSpecificationOptionParameterTypes3 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getCPSpecificationOptionParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPSpecificationOptionsParameterTypes5 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPSpecificationOptionsCountParameterTypes6 =
		new Class[] { long.class };
	private static final Class<?>[] _searchCPSpecificationOptionsParameterTypes7 =
		new Class[] {
			long.class, long.class, Boolean.class, String.class, int.class,
			int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCPSpecificationOptionParameterTypes8 = new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
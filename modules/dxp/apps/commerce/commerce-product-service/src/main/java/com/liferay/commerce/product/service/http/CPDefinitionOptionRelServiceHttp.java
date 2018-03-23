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

import com.liferay.commerce.product.service.CPDefinitionOptionRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPDefinitionOptionRelServiceUtil} service utility. The
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
 * @see CPDefinitionOptionRelServiceSoap
 * @see HttpPrincipal
 * @see CPDefinitionOptionRelServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelServiceHttp {
	public static com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long cpDefinitionId, long cpOptionId,
		boolean importOptionValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"addCPDefinitionOptionRel",
					_addCPDefinitionOptionRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, cpOptionId, importOptionValue,
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

			return (com.liferay.commerce.product.model.CPDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long cpDefinitionId, long cpOptionId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, double priority,
		boolean facetable, boolean required, boolean skuContributor,
		boolean importOptionValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"addCPDefinitionOptionRel",
					_addCPDefinitionOptionRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, cpOptionId, titleMap, descriptionMap,
					ddmFormFieldTypeName, priority, facetable, required,
					skuContributor, importOptionValue, serviceContext);

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

			return (com.liferay.commerce.product.model.CPDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long cpDefinitionId, long cpOptionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"addCPDefinitionOptionRel",
					_addCPDefinitionOptionRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, cpOptionId, serviceContext);

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

			return (com.liferay.commerce.product.model.CPDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		HttpPrincipal httpPrincipal,
		com.liferay.commerce.product.model.CPDefinitionOptionRel cpDefinitionOptionRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"deleteCPDefinitionOptionRel",
					_deleteCPDefinitionOptionRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionRel);

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

			return (com.liferay.commerce.product.model.CPDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"deleteCPDefinitionOptionRel",
					_deleteCPDefinitionOptionRelParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionRelId);

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

			return (com.liferay.commerce.product.model.CPDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel fetchCPDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"fetchCPDefinitionOptionRel",
					_fetchCPDefinitionOptionRelParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionRelId);

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

			return (com.liferay.commerce.product.model.CPDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel getCPDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"getCPDefinitionOptionRel",
					_getCPDefinitionOptionRelParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionRelId);

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

			return (com.liferay.commerce.product.model.CPDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		HttpPrincipal httpPrincipal, long cpDefinitionId, boolean skuContributor)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"getCPDefinitionOptionRels",
					_getCPDefinitionOptionRelsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, skuContributor);

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

			return (java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		HttpPrincipal httpPrincipal, long cpDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"getCPDefinitionOptionRels",
					_getCPDefinitionOptionRelsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, start, end);

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

			return (java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		HttpPrincipal httpPrincipal, long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"getCPDefinitionOptionRels",
					_getCPDefinitionOptionRelsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPDefinitionOptionRelsCount(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"getCPDefinitionOptionRelsCount",
					_getCPDefinitionOptionRelsCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

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

	public static int getCPDefinitionOptionRelsCount(
		HttpPrincipal httpPrincipal, long cpDefinitionId, boolean skuContributor)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"getCPDefinitionOptionRelsCount",
					_getCPDefinitionOptionRelsCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, skuContributor);

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
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"search", _searchParameterTypes12);

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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionRel> searchCPDefinitionOptionRels(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long cpDefinitionId, java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"searchCPDefinitionOptionRels",
					_searchCPDefinitionOptionRelsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, cpDefinitionId, keywords, start, end,
					sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionOptionRel updateCPDefinitionOptionRel(
		HttpPrincipal httpPrincipal, long cpDefinitionOptionRelId,
		long cpOptionId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, double priority,
		boolean facetable, boolean required, boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionOptionRelServiceUtil.class,
					"updateCPDefinitionOptionRel",
					_updateCPDefinitionOptionRelParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionOptionRelId, cpOptionId, titleMap,
					descriptionMap, ddmFormFieldTypeName, priority, facetable,
					required, skuContributor, serviceContext);

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

			return (com.liferay.commerce.product.model.CPDefinitionOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionOptionRelServiceHttp.class);
	private static final Class<?>[] _addCPDefinitionOptionRelParameterTypes0 = new Class[] {
			long.class, long.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCPDefinitionOptionRelParameterTypes1 = new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, double.class, boolean.class, boolean.class,
			boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCPDefinitionOptionRelParameterTypes2 = new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPDefinitionOptionRelParameterTypes3 = new Class[] {
			com.liferay.commerce.product.model.CPDefinitionOptionRel.class
		};
	private static final Class<?>[] _deleteCPDefinitionOptionRelParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPDefinitionOptionRelParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPDefinitionOptionRelParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPDefinitionOptionRelsParameterTypes7 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _getCPDefinitionOptionRelsParameterTypes8 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCPDefinitionOptionRelsParameterTypes9 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPDefinitionOptionRelsCountParameterTypes10 =
		new Class[] { long.class };
	private static final Class<?>[] _getCPDefinitionOptionRelsCountParameterTypes11 =
		new Class[] { long.class, boolean.class };
	private static final Class<?>[] _searchParameterTypes12 = new Class[] {
			com.liferay.portal.kernel.search.SearchContext.class
		};
	private static final Class<?>[] _searchCPDefinitionOptionRelsParameterTypes13 =
		new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			int.class, int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCPDefinitionOptionRelParameterTypes14 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, double.class, boolean.class, boolean.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
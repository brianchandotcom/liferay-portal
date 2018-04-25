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

import com.liferay.commerce.price.list.service.CommerceTierPriceEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceTierPriceEntryServiceUtil} service utility. The
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
 * @see CommerceTierPriceEntryServiceSoap
 * @see HttpPrincipal
 * @see CommerceTierPriceEntryServiceUtil
 * @generated
 */
@ProviderType
public class CommerceTierPriceEntryServiceHttp {
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry addCommerceTierPriceEntry(
		HttpPrincipal httpPrincipal, long commercePriceEntryId,
		java.math.BigDecimal price, java.math.BigDecimal promoPrice,
		int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTierPriceEntryServiceUtil.class,
					"addCommerceTierPriceEntry",
					_addCommerceTierPriceEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceEntryId, price, promoPrice, minQuantity,
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

			return (com.liferay.commerce.price.list.model.CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceTierPriceEntry(
		HttpPrincipal httpPrincipal, long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTierPriceEntryServiceUtil.class,
					"deleteCommerceTierPriceEntry",
					_deleteCommerceTierPriceEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTierPriceEntryId);

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

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry fetchCommerceTierPriceEntry(
		HttpPrincipal httpPrincipal, long commerceTierPriceEntryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTierPriceEntryServiceUtil.class,
					"fetchCommerceTierPriceEntry",
					_fetchCommerceTierPriceEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTierPriceEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.price.list.model.CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.price.list.model.CommerceTierPriceEntry> getCommerceTierPriceEntries(
		HttpPrincipal httpPrincipal, long commercePriceEntryId, int start,
		int end) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTierPriceEntryServiceUtil.class,
					"getCommerceTierPriceEntries",
					_getCommerceTierPriceEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceEntryId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.price.list.model.CommerceTierPriceEntry> getCommerceTierPriceEntries(
		HttpPrincipal httpPrincipal, long commercePriceEntryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommerceTierPriceEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTierPriceEntryServiceUtil.class,
					"getCommerceTierPriceEntries",
					_getCommerceTierPriceEntriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceEntryId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceTierPriceEntriesCount(
		HttpPrincipal httpPrincipal, long commercePriceEntryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTierPriceEntryServiceUtil.class,
					"getCommerceTierPriceEntriesCount",
					_getCommerceTierPriceEntriesCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceEntryId);

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
			MethodKey methodKey = new MethodKey(CommerceTierPriceEntryServiceUtil.class,
					"search", _searchParameterTypes6);

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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.price.list.model.CommerceTierPriceEntry> searchCommerceTierPriceEntries(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long commercePriceEntryId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTierPriceEntryServiceUtil.class,
					"searchCommerceTierPriceEntries",
					_searchCommerceTierPriceEntriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, commercePriceEntryId, keywords, start,
					end, sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry updateCommerceTierPriceEntry(
		HttpPrincipal httpPrincipal, long commerceTierPriceEntryId,
		java.math.BigDecimal price, java.math.BigDecimal promoPrice,
		int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTierPriceEntryServiceUtil.class,
					"updateCommerceTierPriceEntry",
					_updateCommerceTierPriceEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTierPriceEntryId, price, promoPrice, minQuantity,
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

			return (com.liferay.commerce.price.list.model.CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceTierPriceEntryServiceHttp.class);
	private static final Class<?>[] _addCommerceTierPriceEntryParameterTypes0 = new Class[] {
			long.class, java.math.BigDecimal.class, java.math.BigDecimal.class,
			int.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceTierPriceEntryParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCommerceTierPriceEntryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceTierPriceEntriesParameterTypes3 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCommerceTierPriceEntriesParameterTypes4 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceTierPriceEntriesCountParameterTypes5 =
		new Class[] { long.class };
	private static final Class<?>[] _searchParameterTypes6 = new Class[] {
			com.liferay.portal.kernel.search.SearchContext.class
		};
	private static final Class<?>[] _searchCommerceTierPriceEntriesParameterTypes7 =
		new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			int.class, int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCommerceTierPriceEntryParameterTypes8 =
		new Class[] {
			long.class, java.math.BigDecimal.class, java.math.BigDecimal.class,
			int.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
}
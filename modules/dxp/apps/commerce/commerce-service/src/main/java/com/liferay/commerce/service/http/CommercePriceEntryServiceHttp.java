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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommercePriceEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommercePriceEntryServiceUtil} service utility. The
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
 * @see CommercePriceEntryServiceSoap
 * @see HttpPrincipal
 * @see CommercePriceEntryServiceUtil
 * @generated
 */
@ProviderType
public class CommercePriceEntryServiceHttp {
	public static com.liferay.commerce.model.CommercePriceEntry addCommercePriceEntry(
		HttpPrincipal httpPrincipal, long cpInstanceId,
		long commercePriceListId, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"addCommercePriceEntry",
					_addCommercePriceEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpInstanceId, commercePriceListId, price, promoPrice,
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

			return (com.liferay.commerce.model.CommercePriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommercePriceEntry(HttpPrincipal httpPrincipal,
		long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"deleteCommercePriceEntry",
					_deleteCommercePriceEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceEntryId);

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

	public static com.liferay.commerce.model.CommercePriceEntry fetchCommercePriceEntry(
		HttpPrincipal httpPrincipal, long commercePriceEntryId) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"fetchCommercePriceEntry",
					_fetchCommercePriceEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommercePriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceEntry> getCommercePriceEntries(
		HttpPrincipal httpPrincipal, long commercePriceListId, int start,
		int end) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"getCommercePriceEntries",
					_getCommercePriceEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommercePriceEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceEntry> getCommercePriceEntries(
		HttpPrincipal httpPrincipal, long commercePriceListId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"getCommercePriceEntries",
					_getCommercePriceEntriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommercePriceEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommercePriceEntriesCount(
		HttpPrincipal httpPrincipal, long commercePriceListId) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"getCommercePriceEntriesCount",
					_getCommercePriceEntriesCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceListId);

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

	public static java.util.List<com.liferay.commerce.model.CommercePriceEntry> getInstanceCommercePriceEntries(
		HttpPrincipal httpPrincipal, long cpInstanceId, int start, int end) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"getInstanceCommercePriceEntries",
					_getInstanceCommercePriceEntriesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpInstanceId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommercePriceEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceEntry> getInstanceCommercePriceEntries(
		HttpPrincipal httpPrincipal, long cpInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"getInstanceCommercePriceEntries",
					_getInstanceCommercePriceEntriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpInstanceId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommercePriceEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getInstanceCommercePriceEntriesCount(
		HttpPrincipal httpPrincipal, long cpInstanceId) {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"getInstanceCommercePriceEntriesCount",
					_getInstanceCommercePriceEntriesCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpInstanceId);

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
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"search", _searchParameterTypes9);

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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommercePriceEntry> searchCommercePriceEntries(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long commercePriceListId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"searchCommercePriceEntries",
					_searchCommercePriceEntriesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, commercePriceListId, keywords, start,
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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommercePriceEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommercePriceEntry updateCommercePriceEntry(
		HttpPrincipal httpPrincipal, long commercePriceEntryId,
		java.math.BigDecimal price, java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommercePriceEntryServiceUtil.class,
					"updateCommercePriceEntry",
					_updateCommercePriceEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceEntryId, price, promoPrice, serviceContext);

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

			return (com.liferay.commerce.model.CommercePriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommercePriceEntryServiceHttp.class);
	private static final Class<?>[] _addCommercePriceEntryParameterTypes0 = new Class[] {
			long.class, long.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommercePriceEntryParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommercePriceEntryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommercePriceEntriesParameterTypes3 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCommercePriceEntriesParameterTypes4 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommercePriceEntriesCountParameterTypes5 =
		new Class[] { long.class };
	private static final Class<?>[] _getInstanceCommercePriceEntriesParameterTypes6 =
		new Class[] { long.class, int.class, int.class };
	private static final Class<?>[] _getInstanceCommercePriceEntriesParameterTypes7 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getInstanceCommercePriceEntriesCountParameterTypes8 =
		new Class[] { long.class };
	private static final Class<?>[] _searchParameterTypes9 = new Class[] {
			com.liferay.portal.kernel.search.SearchContext.class
		};
	private static final Class<?>[] _searchCommercePriceEntriesParameterTypes10 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			int.class, int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCommercePriceEntryParameterTypes11 = new Class[] {
			long.class, java.math.BigDecimal.class, java.math.BigDecimal.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
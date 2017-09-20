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

import com.liferay.commerce.service.CommerceTirePriceEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceTirePriceEntryServiceUtil} service utility. The
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
 * @see CommerceTirePriceEntryServiceSoap
 * @see HttpPrincipal
 * @see CommerceTirePriceEntryServiceUtil
 * @generated
 */
@ProviderType
public class CommerceTirePriceEntryServiceHttp {
	public static com.liferay.commerce.model.CommerceTirePriceEntry addCommerceTirePriceEntry(
		HttpPrincipal httpPrincipal, long commercePriceEntryId, double price,
		int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTirePriceEntryServiceUtil.class,
					"addCommerceTirePriceEntry",
					_addCommerceTirePriceEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceEntryId, price, minQuantity, serviceContext);

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

			return (com.liferay.commerce.model.CommerceTirePriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceTirePriceEntry(
		HttpPrincipal httpPrincipal, long commerceTirePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTirePriceEntryServiceUtil.class,
					"deleteCommerceTirePriceEntry",
					_deleteCommerceTirePriceEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTirePriceEntryId);

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

	public static com.liferay.commerce.model.CommerceTirePriceEntry fetchCommerceTirePriceEntry(
		HttpPrincipal httpPrincipal, long commerceTirePriceEntryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTirePriceEntryServiceUtil.class,
					"fetchCommerceTirePriceEntry",
					_fetchCommerceTirePriceEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTirePriceEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceTirePriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntries(
		HttpPrincipal httpPrincipal, long commercePriceEntryId, int start,
		int end) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTirePriceEntryServiceUtil.class,
					"getCommerceTirePriceEntries",
					_getCommerceTirePriceEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceEntryId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntries(
		HttpPrincipal httpPrincipal, long commercePriceEntryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTirePriceEntry> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTirePriceEntryServiceUtil.class,
					"getCommerceTirePriceEntries",
					_getCommerceTirePriceEntriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commercePriceEntryId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry>)returnObj;
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
			MethodKey methodKey = new MethodKey(CommerceTirePriceEntryServiceUtil.class,
					"search", _searchParameterTypes5);

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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceTirePriceEntry> searchCommerceTirePriceEntries(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long commercePriceEntryId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTirePriceEntryServiceUtil.class,
					"searchCommerceTirePriceEntries",
					_searchCommerceTirePriceEntriesParameterTypes6);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceTirePriceEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceTirePriceEntriesCount(
		HttpPrincipal httpPrincipal, long commercePriceEntryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceTirePriceEntryServiceUtil.class,
					"getCommerceTirePriceEntriesCount",
					_getCommerceTirePriceEntriesCountParameterTypes7);

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

	public static com.liferay.commerce.model.CommerceTirePriceEntry updateCommerceTirePriceEntry(
		HttpPrincipal httpPrincipal, long commerceTirePriceEntryId,
		double price, int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTirePriceEntryServiceUtil.class,
					"updateCommerceTirePriceEntry",
					_updateCommerceTirePriceEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTirePriceEntryId, price, minQuantity, serviceContext);

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

			return (com.liferay.commerce.model.CommerceTirePriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceTirePriceEntryServiceHttp.class);
	private static final Class<?>[] _addCommerceTirePriceEntryParameterTypes0 = new Class[] {
			long.class, double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceTirePriceEntryParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCommerceTirePriceEntryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceTirePriceEntriesParameterTypes3 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCommerceTirePriceEntriesParameterTypes4 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes5 = new Class[] {
			com.liferay.portal.kernel.search.SearchContext.class
		};
	private static final Class<?>[] _searchCommerceTirePriceEntriesParameterTypes6 =
		new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			int.class, int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _getCommerceTirePriceEntriesCountParameterTypes7 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommerceTirePriceEntryParameterTypes8 =
		new Class[] {
			long.class, double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}
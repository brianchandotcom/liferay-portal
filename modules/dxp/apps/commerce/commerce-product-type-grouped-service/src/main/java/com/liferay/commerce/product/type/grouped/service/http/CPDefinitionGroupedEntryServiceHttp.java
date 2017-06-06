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

package com.liferay.commerce.product.type.grouped.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPDefinitionGroupedEntryServiceUtil} service utility. The
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
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryServiceSoap
 * @see HttpPrincipal
 * @see CPDefinitionGroupedEntryServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionGroupedEntryServiceHttp {
	public static void addCPDefinitionGroupedEntries(
		HttpPrincipal httpPrincipal, long cpDefinitionId,
		long[] entryCPDefinitionIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionGroupedEntryServiceUtil.class,
					"addCPDefinitionGroupedEntries",
					_addCPDefinitionGroupedEntriesParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, entryCPDefinitionIds, serviceContext);

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

	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry deleteCPDefinitionGroupedEntry(
		HttpPrincipal httpPrincipal, long cpDefinitionGroupedEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionGroupedEntryServiceUtil.class,
					"deleteCPDefinitionGroupedEntry",
					_deleteCPDefinitionGroupedEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionGroupedEntryId);

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

			return (com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		HttpPrincipal httpPrincipal, long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionGroupedEntryServiceUtil.class,
					"getCPDefinitionGroupedEntries",
					_getCPDefinitionGroupedEntriesParameterTypes2);

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

			return (java.util.List<com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPDefinitionGroupedEntriesCount(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionGroupedEntryServiceUtil.class,
					"getCPDefinitionGroupedEntriesCount",
					_getCPDefinitionGroupedEntriesCountParameterTypes3);

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

	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry getCPDefinitionGroupedEntry(
		HttpPrincipal httpPrincipal, long cpDefinitionGroupedEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionGroupedEntryServiceUtil.class,
					"getCPDefinitionGroupedEntry",
					_getCPDefinitionGroupedEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionGroupedEntryId);

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

			return (com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
		HttpPrincipal httpPrincipal, long cpDefinitionGroupedEntryId,
		double priority, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionGroupedEntryServiceUtil.class,
					"updateCPDefinitionGroupedEntry",
					_updateCPDefinitionGroupedEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionGroupedEntryId, priority, quantity);

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

			return (com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionGroupedEntryServiceHttp.class);
	private static final Class<?>[] _addCPDefinitionGroupedEntriesParameterTypes0 =
		new Class[] {
			long.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPDefinitionGroupedEntryParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _getCPDefinitionGroupedEntriesParameterTypes2 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPDefinitionGroupedEntriesCountParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _getCPDefinitionGroupedEntryParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateCPDefinitionGroupedEntryParameterTypes5 =
		new Class[] { long.class, double.class, int.class };
}
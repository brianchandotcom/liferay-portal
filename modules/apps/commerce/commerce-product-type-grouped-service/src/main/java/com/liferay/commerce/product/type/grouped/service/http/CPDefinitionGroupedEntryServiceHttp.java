/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.service.http;

import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDefinitionGroupedEntryServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
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
 * @generated
 */
public class CPDefinitionGroupedEntryServiceHttp {

	public static void addCPDefinitionGroupedEntries(
			HttpPrincipal httpPrincipal, long cpDefinitionId,
			long[] entryCPDefinitionIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"addCPDefinitionGroupedEntries",
				_addCPDefinitionGroupedEntriesParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, entryCPDefinitionIds,
				serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry
				addCPDefinitionGroupedEntry(
					HttpPrincipal httpPrincipal, long cpDefinitionId,
					long entryCProductId, double priority, int quantity,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"addCPDefinitionGroupedEntry",
				_addCPDefinitionGroupedEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, entryCProductId, priority, quantity,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.product.type.grouped.model.
				CPDefinitionGroupedEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry
				deleteCPDefinitionGroupedEntry(
					HttpPrincipal httpPrincipal,
					long cpDefinitionGroupedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"deleteCPDefinitionGroupedEntry",
				_deleteCPDefinitionGroupedEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionGroupedEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.product.type.grouped.model.
				CPDefinitionGroupedEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.type.grouped.model.
			CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
					HttpPrincipal httpPrincipal, long cpDefinitionId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.type.grouped.model.
							CPDefinitionGroupedEntry> orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"getCPDefinitionGroupedEntries",
				_getCPDefinitionGroupedEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.product.type.grouped.model.
					CPDefinitionGroupedEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.type.grouped.model.
			CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
					HttpPrincipal httpPrincipal, long companyId,
					long cpDefinitionId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"getCPDefinitionGroupedEntries",
				_getCPDefinitionGroupedEntriesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, cpDefinitionId, keywords, start, end,
				sort);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.product.type.grouped.model.
					CPDefinitionGroupedEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPDefinitionGroupedEntriesCount(
			HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"getCPDefinitionGroupedEntriesCount",
				_getCPDefinitionGroupedEntriesCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPDefinitionGroupedEntriesCount(
			HttpPrincipal httpPrincipal, long companyId, long cpDefinitionId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"getCPDefinitionGroupedEntriesCount",
				_getCPDefinitionGroupedEntriesCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, cpDefinitionId, keywords);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry
				getCPDefinitionGroupedEntry(
					HttpPrincipal httpPrincipal,
					long cpDefinitionGroupedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"getCPDefinitionGroupedEntry",
				_getCPDefinitionGroupedEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionGroupedEntryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.product.type.grouped.model.
				CPDefinitionGroupedEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.type.grouped.model.
			CPDefinitionGroupedEntry>
					getEntryCProductCPDefinitionGroupedEntries(
						HttpPrincipal httpPrincipal, long entryCProductId,
						int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.product.type.grouped.model.
								CPDefinitionGroupedEntry> orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"getEntryCProductCPDefinitionGroupedEntries",
				_getEntryCProductCPDefinitionGroupedEntriesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, entryCProductId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.product.type.grouped.model.
					CPDefinitionGroupedEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getEntryCProductCPDefinitionGroupedEntriesCount(
			HttpPrincipal httpPrincipal, long entryCProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"getEntryCProductCPDefinitionGroupedEntriesCount",
				_getEntryCProductCPDefinitionGroupedEntriesCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, entryCProductId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry
				updateCPDefinitionGroupedEntry(
					HttpPrincipal httpPrincipal,
					long cpDefinitionGroupedEntryId, double priority,
					int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionGroupedEntryServiceUtil.class,
				"updateCPDefinitionGroupedEntry",
				_updateCPDefinitionGroupedEntryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionGroupedEntryId, priority, quantity);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.product.type.grouped.model.
				CPDefinitionGroupedEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionGroupedEntryServiceHttp.class);

	private static final Class<?>[]
		_addCPDefinitionGroupedEntriesParameterTypes0 = new Class[] {
			long.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_addCPDefinitionGroupedEntryParameterTypes1 = new Class[] {
			long.class, long.class, double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCPDefinitionGroupedEntryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCPDefinitionGroupedEntriesParameterTypes3 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCPDefinitionGroupedEntriesParameterTypes4 = new Class[] {
			long.class, long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[]
		_getCPDefinitionGroupedEntriesCountParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCPDefinitionGroupedEntriesCountParameterTypes6 = new Class[] {
			long.class, long.class, String.class
		};
	private static final Class<?>[]
		_getCPDefinitionGroupedEntryParameterTypes7 = new Class[] {long.class};
	private static final Class<?>[]
		_getEntryCProductCPDefinitionGroupedEntriesParameterTypes8 =
			new Class[] {
				long.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};
	private static final Class<?>[]
		_getEntryCProductCPDefinitionGroupedEntriesCountParameterTypes9 =
			new Class[] {long.class};
	private static final Class<?>[]
		_updateCPDefinitionGroupedEntryParameterTypes10 = new Class[] {
			long.class, double.class, int.class
		};

}
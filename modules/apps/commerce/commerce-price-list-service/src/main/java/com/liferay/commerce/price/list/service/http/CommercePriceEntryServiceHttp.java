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

import com.liferay.commerce.price.list.service.CommercePriceEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommercePriceEntryServiceUtil</code> service
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
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryServiceSoap
 * @generated
 */
public class CommercePriceEntryServiceHttp {

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			addCommercePriceEntry(
				HttpPrincipal httpPrincipal, long cpInstanceId,
				long commercePriceListId, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "addCommercePriceEntry",
				_addCommercePriceEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId, commercePriceListId, price, promoPrice,
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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			addCommercePriceEntry(
				HttpPrincipal httpPrincipal, long cpInstanceId,
				long commercePriceListId, String externalReferenceCode,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "addCommercePriceEntry",
				_addCommercePriceEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId, commercePriceListId,
				externalReferenceCode, price, promoPrice, serviceContext);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			addCommercePriceEntry(
				HttpPrincipal httpPrincipal, long cProductId,
				String cpInstanceUuid, long commercePriceListId,
				String externalReferenceCode, java.math.BigDecimal price,
				boolean discountDiscovery, java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "addCommercePriceEntry",
				_addCommercePriceEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cProductId, cpInstanceUuid, commercePriceListId,
				externalReferenceCode, price, discountDiscovery, discountLevel1,
				discountLevel2, discountLevel3, discountLevel4,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			addCommercePriceEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long cProductId, String cpInstanceUuid,
				long commercePriceListId, java.math.BigDecimal price,
				boolean discountDiscovery, java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "addCommercePriceEntry",
				_addCommercePriceEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, cProductId, cpInstanceUuid,
				commercePriceListId, price, discountDiscovery, discountLevel1,
				discountLevel2, discountLevel3, discountLevel4,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommercePriceEntry(
			HttpPrincipal httpPrincipal, long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "deleteCommercePriceEntry",
				_deleteCommercePriceEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId);

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

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			fetchByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long companyId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"fetchByExternalReferenceCode",
				_fetchByExternalReferenceCodeParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, externalReferenceCode);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			fetchByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"fetchByExternalReferenceCode",
				_fetchByExternalReferenceCodeParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			fetchCommercePriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "fetchCommercePriceEntry",
				_fetchCommercePriceEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				getCommercePriceEntries(
					HttpPrincipal httpPrincipal, long commercePriceListId,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "getCommercePriceEntries",
				_getCommercePriceEntriesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, start, end);

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
				<com.liferay.commerce.price.list.model.CommercePriceEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				getCommercePriceEntries(
					HttpPrincipal httpPrincipal, long commercePriceListId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "getCommercePriceEntries",
				_getCommercePriceEntriesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId, start, end, orderByComparator);

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
				<com.liferay.commerce.price.list.model.CommercePriceEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				getCommercePriceEntriesByCompanyId(
					HttpPrincipal httpPrincipal, long companyId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"getCommercePriceEntriesByCompanyId",
				_getCommercePriceEntriesByCompanyIdParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

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
				<com.liferay.commerce.price.list.model.CommercePriceEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommercePriceEntriesCount(
			HttpPrincipal httpPrincipal, long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"getCommercePriceEntriesCount",
				_getCommercePriceEntriesCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceListId);

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

	public static int getCommercePriceEntriesCountByCompanyId(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"getCommercePriceEntriesCountByCompanyId",
				_getCommercePriceEntriesCountByCompanyIdParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			getCommercePriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "getCommercePriceEntry",
				_getCommercePriceEntryParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
		getInstanceBaseCommercePriceEntry(
			HttpPrincipal httpPrincipal, String cpInstanceUuid,
			String priceListType) {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"getInstanceBaseCommercePriceEntry",
				_getInstanceBaseCommercePriceEntryParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceUuid, priceListType);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				getInstanceCommercePriceEntries(
					HttpPrincipal httpPrincipal, long cpInstanceId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"getInstanceCommercePriceEntries",
				_getInstanceCommercePriceEntriesParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId, start, end);

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
				<com.liferay.commerce.price.list.model.CommercePriceEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				getInstanceCommercePriceEntries(
					HttpPrincipal httpPrincipal, long cpInstanceId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"getInstanceCommercePriceEntries",
				_getInstanceCommercePriceEntriesParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId, start, end, orderByComparator);

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
				<com.liferay.commerce.price.list.model.CommercePriceEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getInstanceCommercePriceEntriesCount(
			HttpPrincipal httpPrincipal, long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"getInstanceCommercePriceEntriesCount",
				_getInstanceCommercePriceEntriesCountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId);

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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				searchCommercePriceEntries(
					HttpPrincipal httpPrincipal, long companyId,
					long commercePriceListId, String keywords, int start,
					int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"searchCommercePriceEntries",
				_searchCommercePriceEntriesParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, commercePriceListId, keywords, start, end,
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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult
				<com.liferay.commerce.price.list.model.CommercePriceEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCommercePriceEntriesCount(
			HttpPrincipal httpPrincipal, long companyId,
			long commercePriceListId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"searchCommercePriceEntriesCount",
				_searchCommercePriceEntriesCountParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, commercePriceListId, keywords);

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

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			updateCommercePriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "updateCommercePriceEntry",
				_updateCommercePriceEntryParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, price, promoPrice,
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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			updateCommercePriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId,
				java.math.BigDecimal price, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, boolean bulkPricing,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "updateCommercePriceEntry",
				_updateCommercePriceEntryParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, price, discountDiscovery,
				discountLevel1, discountLevel2, discountLevel3, discountLevel4,
				bulkPricing, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			updateCommercePriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId,
				java.math.BigDecimal price, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "updateCommercePriceEntry",
				_updateCommercePriceEntryParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, price, discountDiscovery,
				discountLevel1, discountLevel2, discountLevel3, discountLevel4,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			updateExternalReferenceCode(
				HttpPrincipal httpPrincipal,
				com.liferay.commerce.price.list.model.CommercePriceEntry
					commercePriceEntry,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"updateExternalReferenceCode",
				_updateExternalReferenceCodeParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntry, externalReferenceCode);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			updateExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				com.liferay.commerce.price.list.model.CommercePriceEntry
					commercePriceEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class,
				"updateExternalReferenceCode",
				_updateExternalReferenceCodeParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commercePriceEntry);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			upsertCommercePriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId,
				long cpInstanceId, long commercePriceListId,
				String externalReferenceCode, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice,
				String skuExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "upsertCommercePriceEntry",
				_upsertCommercePriceEntryParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, cpInstanceId,
				commercePriceListId, externalReferenceCode, price, promoPrice,
				skuExternalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			upsertCommercePriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId,
				long cProductId, String cpInstanceUuid,
				long commercePriceListId, String externalReferenceCode,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				String skuExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "upsertCommercePriceEntry",
				_upsertCommercePriceEntryParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, cProductId, cpInstanceUuid,
				commercePriceListId, externalReferenceCode, price, promoPrice,
				skuExternalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			upsertCommercePriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId,
				long cProductId, String cpInstanceUuid,
				long commercePriceListId, String externalReferenceCode,
				java.math.BigDecimal price, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String skuExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "upsertCommercePriceEntry",
				_upsertCommercePriceEntryParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, cProductId, cpInstanceUuid,
				commercePriceListId, externalReferenceCode, price,
				discountDiscovery, discountLevel1, discountLevel2,
				discountLevel3, discountLevel4, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, skuExternalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			upsertCommercePriceEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long commercePriceEntryId, long cProductId,
				String cpInstanceUuid, long commercePriceListId,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				String skuExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "upsertCommercePriceEntry",
				_upsertCommercePriceEntryParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commercePriceEntryId,
				cProductId, cpInstanceUuid, commercePriceListId, price,
				promoPrice, skuExternalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			upsertCommercePriceEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long commercePriceEntryId, long cProductId,
				String cpInstanceUuid, long commercePriceListId,
				java.math.BigDecimal price, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String skuExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommercePriceEntryServiceUtil.class, "upsertCommercePriceEntry",
				_upsertCommercePriceEntryParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commercePriceEntryId,
				cProductId, cpInstanceUuid, commercePriceListId, price,
				discountDiscovery, discountLevel1, discountLevel2,
				discountLevel3, discountLevel4, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, skuExternalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.price.list.model.CommercePriceEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePriceEntryServiceHttp.class);

	private static final Class<?>[] _addCommercePriceEntryParameterTypes0 =
		new Class[] {
			long.class, long.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommercePriceEntryParameterTypes1 =
		new Class[] {
			long.class, long.class, String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommercePriceEntryParameterTypes2 =
		new Class[] {
			long.class, String.class, long.class, String.class,
			java.math.BigDecimal.class, boolean.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommercePriceEntryParameterTypes3 =
		new Class[] {
			String.class, long.class, String.class, long.class,
			java.math.BigDecimal.class, boolean.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommercePriceEntryParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchByExternalReferenceCodeParameterTypes5 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_fetchByExternalReferenceCodeParameterTypes6 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _fetchCommercePriceEntryParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommercePriceEntriesParameterTypes8 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getCommercePriceEntriesParameterTypes9 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommercePriceEntriesByCompanyIdParameterTypes10 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommercePriceEntriesCountParameterTypes11 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommercePriceEntriesCountByCompanyIdParameterTypes12 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommercePriceEntryParameterTypes13 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getInstanceBaseCommercePriceEntryParameterTypes14 = new Class[] {
			String.class, String.class
		};
	private static final Class<?>[]
		_getInstanceCommercePriceEntriesParameterTypes15 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getInstanceCommercePriceEntriesParameterTypes16 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getInstanceCommercePriceEntriesCountParameterTypes17 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_searchCommercePriceEntriesParameterTypes18 = new Class[] {
			long.class, long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[]
		_searchCommercePriceEntriesCountParameterTypes19 = new Class[] {
			long.class, long.class, String.class
		};
	private static final Class<?>[] _updateCommercePriceEntryParameterTypes20 =
		new Class[] {
			long.class, java.math.BigDecimal.class, java.math.BigDecimal.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCommercePriceEntryParameterTypes21 =
		new Class[] {
			long.class, java.math.BigDecimal.class, boolean.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			boolean.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCommercePriceEntryParameterTypes22 =
		new Class[] {
			long.class, java.math.BigDecimal.class, boolean.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateExternalReferenceCodeParameterTypes23 = new Class[] {
			com.liferay.commerce.price.list.model.CommercePriceEntry.class,
			String.class
		};
	private static final Class<?>[]
		_updateExternalReferenceCodeParameterTypes24 = new Class[] {
			String.class,
			com.liferay.commerce.price.list.model.CommercePriceEntry.class
		};
	private static final Class<?>[] _upsertCommercePriceEntryParameterTypes25 =
		new Class[] {
			long.class, long.class, long.class, String.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _upsertCommercePriceEntryParameterTypes26 =
		new Class[] {
			long.class, long.class, String.class, long.class, String.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _upsertCommercePriceEntryParameterTypes27 =
		new Class[] {
			long.class, long.class, String.class, long.class, String.class,
			java.math.BigDecimal.class, boolean.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _upsertCommercePriceEntryParameterTypes28 =
		new Class[] {
			String.class, long.class, long.class, String.class, long.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _upsertCommercePriceEntryParameterTypes29 =
		new Class[] {
			String.class, long.class, long.class, String.class, long.class,
			java.math.BigDecimal.class, boolean.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
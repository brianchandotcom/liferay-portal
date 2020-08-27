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

import com.liferay.commerce.price.list.service.CommerceTierPriceEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceTierPriceEntryServiceUtil</code> service
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
 * @see CommerceTierPriceEntryServiceSoap
 * @generated
 */
public class CommerceTierPriceEntryServiceHttp {

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			addCommerceTierPriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				int minQuantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"addCommerceTierPriceEntry",
				_addCommerceTierPriceEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, price, promoPrice, minQuantity,
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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			addCommerceTierPriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId,
				String externalReferenceCode, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice, int minQuantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"addCommerceTierPriceEntry",
				_addCommerceTierPriceEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, externalReferenceCode, price,
				promoPrice, minQuantity, serviceContext);

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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			addCommerceTierPriceEntry(
				HttpPrincipal httpPrincipal, long commercePriceEntryId,
				String externalReferenceCode, java.math.BigDecimal price,
				int minQuantity, boolean bulkPricing, boolean discountDiscovery,
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
				CommerceTierPriceEntryServiceUtil.class,
				"addCommerceTierPriceEntry",
				_addCommerceTierPriceEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, externalReferenceCode, price,
				minQuantity, bulkPricing, discountDiscovery, discountLevel1,
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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceTierPriceEntry(
			HttpPrincipal httpPrincipal, long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"deleteCommerceTierPriceEntry",
				_deleteCommerceTierPriceEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTierPriceEntryId);

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

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			fetchByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long companyId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"fetchByExternalReferenceCode",
				_fetchByExternalReferenceCodeParameterTypes4);

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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
				fetchCommerceTierPriceEntries(
					HttpPrincipal httpPrincipal, long companyId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"fetchCommerceTierPriceEntries",
				_fetchCommerceTierPriceEntriesParameterTypes5);

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
				<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			fetchCommerceTierPriceEntry(
				HttpPrincipal httpPrincipal, long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"fetchCommerceTierPriceEntry",
				_fetchCommerceTierPriceEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTierPriceEntryId);

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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
				getCommerceTierPriceEntries(
					HttpPrincipal httpPrincipal, long commercePriceEntryId,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"getCommerceTierPriceEntries",
				_getCommerceTierPriceEntriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, start, end);

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
				<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
				getCommerceTierPriceEntries(
					HttpPrincipal httpPrincipal, long commercePriceEntryId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommerceTierPriceEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"getCommerceTierPriceEntries",
				_getCommerceTierPriceEntriesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePriceEntryId, start, end, orderByComparator);

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
				<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceTierPriceEntriesCount(
			HttpPrincipal httpPrincipal, long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"getCommerceTierPriceEntriesCount",
				_getCommerceTierPriceEntriesCountParameterTypes9);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceTierPriceEntriesCountByCompanyId(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"getCommerceTierPriceEntriesCountByCompanyId",
				_getCommerceTierPriceEntriesCountByCompanyIdParameterTypes10);

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

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			getCommerceTierPriceEntry(
				HttpPrincipal httpPrincipal, long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"getCommerceTierPriceEntry",
				_getCommerceTierPriceEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTierPriceEntryId);

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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
				searchCommerceTierPriceEntries(
					HttpPrincipal httpPrincipal, long companyId,
					long commercePriceEntryId, String keywords, int start,
					int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"searchCommerceTierPriceEntries",
				_searchCommerceTierPriceEntriesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, commercePriceEntryId, keywords, start,
				end, sort);

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
				<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCommerceTierPriceEntriesCount(
			HttpPrincipal httpPrincipal, long companyId,
			long commercePriceEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"searchCommerceTierPriceEntriesCount",
				_searchCommerceTierPriceEntriesCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, commercePriceEntryId, keywords);

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

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			updateCommerceTierPriceEntry(
				HttpPrincipal httpPrincipal, long commerceTierPriceEntryId,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				int minQuantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"updateCommerceTierPriceEntry",
				_updateCommerceTierPriceEntryParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTierPriceEntryId, price, promoPrice,
				minQuantity, serviceContext);

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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			updateCommerceTierPriceEntry(
				HttpPrincipal httpPrincipal, long commerceTierPriceEntryId,
				java.math.BigDecimal price, int minQuantity,
				boolean bulkPricing, boolean discountDiscovery,
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
				CommerceTierPriceEntryServiceUtil.class,
				"updateCommerceTierPriceEntry",
				_updateCommerceTierPriceEntryParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTierPriceEntryId, price, minQuantity,
				bulkPricing, discountDiscovery, discountLevel1, discountLevel2,
				discountLevel3, discountLevel4, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);

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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			updateExternalReferenceCode(
				HttpPrincipal httpPrincipal,
				com.liferay.commerce.price.list.model.CommerceTierPriceEntry
					commerceTierPriceEntry,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"updateExternalReferenceCode",
				_updateExternalReferenceCodeParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTierPriceEntry, externalReferenceCode);

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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			upsertCommerceTierPriceEntry(
				HttpPrincipal httpPrincipal, long commerceTierPriceEntryId,
				long commercePriceEntryId, String externalReferenceCode,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				int minQuantity, String priceEntryExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"upsertCommerceTierPriceEntry",
				_upsertCommerceTierPriceEntryParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTierPriceEntryId, commercePriceEntryId,
				externalReferenceCode, price, promoPrice, minQuantity,
				priceEntryExternalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			upsertCommerceTierPriceEntry(
				HttpPrincipal httpPrincipal, long commerceTierPriceEntryId,
				long commercePriceEntryId, String externalReferenceCode,
				java.math.BigDecimal price, int minQuantity,
				boolean bulkPricing, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String priceEntryExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceTierPriceEntryServiceUtil.class,
				"upsertCommerceTierPriceEntry",
				_upsertCommerceTierPriceEntryParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceTierPriceEntryId, commercePriceEntryId,
				externalReferenceCode, price, minQuantity, bulkPricing,
				discountDiscovery, discountLevel1, discountLevel2,
				discountLevel3, discountLevel4, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, priceEntryExternalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.price.list.model.
				CommerceTierPriceEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceTierPriceEntryServiceHttp.class);

	private static final Class<?>[] _addCommerceTierPriceEntryParameterTypes0 =
		new Class[] {
			long.class, java.math.BigDecimal.class, java.math.BigDecimal.class,
			int.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommerceTierPriceEntryParameterTypes1 =
		new Class[] {
			long.class, String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommerceTierPriceEntryParameterTypes2 =
		new Class[] {
			long.class, String.class, java.math.BigDecimal.class, int.class,
			boolean.class, boolean.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCommerceTierPriceEntryParameterTypes3 = new Class[] {long.class};
	private static final Class<?>[]
		_fetchByExternalReferenceCodeParameterTypes4 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_fetchCommerceTierPriceEntriesParameterTypes5 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_fetchCommerceTierPriceEntryParameterTypes6 = new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceTierPriceEntriesParameterTypes7 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceTierPriceEntriesParameterTypes8 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceTierPriceEntriesCountParameterTypes9 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceTierPriceEntriesCountByCompanyIdParameterTypes10 =
			new Class[] {long.class};
	private static final Class<?>[] _getCommerceTierPriceEntryParameterTypes11 =
		new Class[] {long.class};
	private static final Class<?>[]
		_searchCommerceTierPriceEntriesParameterTypes12 = new Class[] {
			long.class, long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[]
		_searchCommerceTierPriceEntriesCountParameterTypes13 = new Class[] {
			long.class, long.class, String.class
		};
	private static final Class<?>[]
		_updateCommerceTierPriceEntryParameterTypes14 = new Class[] {
			long.class, java.math.BigDecimal.class, java.math.BigDecimal.class,
			int.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCommerceTierPriceEntryParameterTypes15 = new Class[] {
			long.class, java.math.BigDecimal.class, int.class, boolean.class,
			boolean.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateExternalReferenceCodeParameterTypes16 = new Class[] {
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry.class,
			String.class
		};
	private static final Class<?>[]
		_upsertCommerceTierPriceEntryParameterTypes17 = new Class[] {
			long.class, long.class, String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, int.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_upsertCommerceTierPriceEntryParameterTypes18 = new Class[] {
			long.class, long.class, String.class, java.math.BigDecimal.class,
			int.class, boolean.class, boolean.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}
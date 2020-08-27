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

import com.liferay.commerce.product.service.CPDefinitionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDefinitionServiceUtil</code> service
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
 * @author Marco Leo
 * @see CPDefinitionServiceSoap
 * @generated
 */
public class CPDefinitionServiceHttp {

	public static com.liferay.commerce.product.model.CPDefinition
			addCPDefinition(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> shortDescriptionMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.util.Map<java.util.Locale, String> urlTitleMap,
				java.util.Map<java.util.Locale, String> metaTitleMap,
				java.util.Map<java.util.Locale, String> metaDescriptionMap,
				java.util.Map<java.util.Locale, String> metaKeywordsMap,
				String productTypeName, boolean ignoreSKUCombinations,
				boolean shippable, boolean freeShipping, boolean shipSeparately,
				double shippingExtraPrice, double width, double height,
				double depth, double weight, long cpTaxCategoryId,
				boolean taxExempt, boolean telcoOrElectronics,
				String ddmStructureKey, boolean published, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String defaultSku,
				boolean subscriptionEnabled, int subscriptionLength,
				String subscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					subscriptionTypeSettingsUnicodeProperties,
				long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
				int deliverySubscriptionLength, String deliverySubscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					deliverySubscriptionTypeSettingsUnicodeProperties,
				long deliveryMaxSubscriptionCycles,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "addCPDefinition",
				_addCPDefinitionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, nameMap, shortDescriptionMap,
				descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap,
				metaKeywordsMap, productTypeName, ignoreSKUCombinations,
				shippable, freeShipping, shipSeparately, shippingExtraPrice,
				width, height, depth, weight, cpTaxCategoryId, taxExempt,
				telcoOrElectronics, ddmStructureKey, published,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, defaultSku,
				subscriptionEnabled, subscriptionLength, subscriptionType,
				subscriptionTypeSettingsUnicodeProperties,
				maxSubscriptionCycles, deliverySubscriptionEnabled,
				deliverySubscriptionLength, deliverySubscriptionType,
				deliverySubscriptionTypeSettingsUnicodeProperties,
				deliveryMaxSubscriptionCycles, externalReferenceCode,
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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			addCPDefinition(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> shortDescriptionMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.util.Map<java.util.Locale, String> urlTitleMap,
				java.util.Map<java.util.Locale, String> metaTitleMap,
				java.util.Map<java.util.Locale, String> metaDescriptionMap,
				java.util.Map<java.util.Locale, String> metaKeywordsMap,
				String productTypeName, boolean ignoreSKUCombinations,
				boolean shippable, boolean freeShipping, boolean shipSeparately,
				double shippingExtraPrice, double width, double height,
				double depth, double weight, long cpTaxCategoryId,
				boolean taxExempt, boolean telcoOrElectronics,
				String ddmStructureKey, boolean published, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String defaultSku,
				boolean subscriptionEnabled, int subscriptionLength,
				String subscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					subscriptionTypeSettingsUnicodeProperties,
				long maxSubscriptionCycles, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "addCPDefinition",
				_addCPDefinitionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, nameMap, shortDescriptionMap,
				descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap,
				metaKeywordsMap, productTypeName, ignoreSKUCombinations,
				shippable, freeShipping, shipSeparately, shippingExtraPrice,
				width, height, depth, weight, cpTaxCategoryId, taxExempt,
				telcoOrElectronics, ddmStructureKey, published,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, defaultSku,
				subscriptionEnabled, subscriptionLength, subscriptionType,
				subscriptionTypeSettingsUnicodeProperties,
				maxSubscriptionCycles, externalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			copyCPDefinition(
				HttpPrincipal httpPrincipal, long cpDefinitionId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "copyCPDefinition",
				_copyCPDefinitionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, groupId);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteAssetCategoryCPDefinition(
			HttpPrincipal httpPrincipal, long cpDefinitionId, long categoryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class,
				"deleteAssetCategoryCPDefinition",
				_deleteAssetCategoryCPDefinitionParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, categoryId, serviceContext);

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

	public static void deleteCPDefinition(
			HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "deleteCPDefinition",
				_deleteCPDefinitionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId);

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

	public static com.liferay.commerce.product.model.CPDefinition
			fetchCPDefinition(HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "fetchCPDefinition",
				_fetchCPDefinitionParameterTypes5);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			fetchCPDefinitionByCProductExternalReferenceCode(
				HttpPrincipal httpPrincipal, long companyId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class,
				"fetchCPDefinitionByCProductExternalReferenceCode",
				_fetchCPDefinitionByCProductExternalReferenceCodeParameterTypes6);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			fetchCPDefinitionByCProductId(
				HttpPrincipal httpPrincipal, long cProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "fetchCPDefinitionByCProductId",
				_fetchCPDefinitionByCProductIdParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cProductId);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			getCPDefinition(HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "getCPDefinition",
				_getCPDefinitionParameterTypes8);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPDefinition> getCPDefinitions(
				HttpPrincipal httpPrincipal, long groupId, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CPDefinition>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "getCPDefinitions",
				_getCPDefinitionsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status, start, end, orderByComparator);

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
				<com.liferay.commerce.product.model.CPDefinition>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPDefinitionsCount(
			HttpPrincipal httpPrincipal, long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "getCPDefinitionsCount",
				_getCPDefinitionsCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status);

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

	public static String getLayoutUuid(
			HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "getLayoutUuid",
				_getLayoutUuidParameterTypes11);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static String getUrlTitleMapAsXML(
			HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "getUrlTitleMapAsXML",
				_getUrlTitleMapAsXMLParameterTypes12);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPDefinition> searchCPDefinitions(
				HttpPrincipal httpPrincipal, long companyId, String keywords,
				int status, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "searchCPDefinitions",
				_searchCPDefinitionsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, status, start, end, sort);

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
				<com.liferay.commerce.product.model.CPDefinition>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPDefinition> searchCPDefinitions(
				HttpPrincipal httpPrincipal, long companyId, String keywords,
				String filterFields, String filterValues, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "searchCPDefinitions",
				_searchCPDefinitionsParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, filterFields, filterValues,
				start, end, sort);

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
				<com.liferay.commerce.product.model.CPDefinition>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateCPDefinition(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> shortDescriptionMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.util.Map<java.util.Locale, String> urlTitleMap,
				java.util.Map<java.util.Locale, String> metaTitleMap,
				java.util.Map<java.util.Locale, String> metaDescriptionMap,
				java.util.Map<java.util.Locale, String> metaKeywordsMap,
				boolean ignoreSKUCombinations, String ddmStructureKey,
				boolean published, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "updateCPDefinition",
				_updateCPDefinitionParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, nameMap, shortDescriptionMap,
				descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap,
				metaKeywordsMap, ignoreSKUCombinations, ddmStructureKey,
				published, displayDateMonth, displayDateDay, displayDateYear,
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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateCPDefinitionAccountGroupFilter(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				boolean enable)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class,
				"updateCPDefinitionAccountGroupFilter",
				_updateCPDefinitionAccountGroupFilterParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, enable);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateCPDefinitionCategorization(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class,
				"updateCPDefinitionCategorization",
				_updateCPDefinitionCategorizationParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, serviceContext);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateCPDefinitionChannelFilter(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				boolean enable)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class,
				"updateCPDefinitionChannelFilter",
				_updateCPDefinitionChannelFilterParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, enable);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void updateCPDisplayLayout(
			HttpPrincipal httpPrincipal, long cpDefinitionId, String layoutUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "updateCPDisplayLayout",
				_updateCPDisplayLayoutParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, layoutUuid, serviceContext);

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

	public static com.liferay.commerce.product.model.CPDefinition
			updateShippingInfo(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				boolean shippable, boolean freeShipping, boolean shipSeparately,
				double shippingExtraPrice, double width, double height,
				double depth, double weight,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "updateShippingInfo",
				_updateShippingInfoParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, shippable, freeShipping,
				shipSeparately, shippingExtraPrice, width, height, depth,
				weight, serviceContext);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition updateStatus(
			HttpPrincipal httpPrincipal, long userId, long cpDefinitionId,
			int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "updateStatus",
				_updateStatusParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, cpDefinitionId, status, serviceContext,
				workflowContext);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateSubscriptionInfo(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				boolean subscriptionEnabled, int subscriptionLength,
				String subscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					subscriptionTypeSettingsUnicodeProperties,
				long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
				int deliverySubscriptionLength, String deliverySubscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					deliverySubscriptionTypeSettingsUnicodeProperties,
				long deliveryMaxSubscriptionCycles)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "updateSubscriptionInfo",
				_updateSubscriptionInfoParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, subscriptionEnabled,
				subscriptionLength, subscriptionType,
				subscriptionTypeSettingsUnicodeProperties,
				maxSubscriptionCycles, deliverySubscriptionEnabled,
				deliverySubscriptionLength, deliverySubscriptionType,
				deliverySubscriptionTypeSettingsUnicodeProperties,
				deliveryMaxSubscriptionCycles);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateSubscriptionInfo(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				boolean subscriptionEnabled, int subscriptionLength,
				String subscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					subscriptionTypeSettingsUnicodeProperties,
				long maxSubscriptionCycles,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "updateSubscriptionInfo",
				_updateSubscriptionInfoParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, subscriptionEnabled,
				subscriptionLength, subscriptionType,
				subscriptionTypeSettingsUnicodeProperties,
				maxSubscriptionCycles, serviceContext);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateTaxCategoryInfo(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				long cpTaxCategoryId, boolean taxExempt,
				boolean telcoOrElectronics)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "updateTaxCategoryInfo",
				_updateTaxCategoryInfoParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, cpTaxCategoryId, taxExempt,
				telcoOrElectronics);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			upsertCPDefinition(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> shortDescriptionMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.util.Map<java.util.Locale, String> urlTitleMap,
				java.util.Map<java.util.Locale, String> metaTitleMap,
				java.util.Map<java.util.Locale, String> metaDescriptionMap,
				java.util.Map<java.util.Locale, String> metaKeywordsMap,
				String productTypeName, boolean ignoreSKUCombinations,
				boolean shippable, boolean freeShipping, boolean shipSeparately,
				double shippingExtraPrice, double width, double height,
				double depth, double weight, long cpTaxCategoryId,
				boolean taxExempt, boolean telcoOrElectronics,
				String ddmStructureKey, boolean published, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String defaultSku,
				boolean subscriptionEnabled, int subscriptionLength,
				String subscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					subscriptionTypeSettingsUnicodeProperties,
				long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
				int deliverySubscriptionLength, String deliverySubscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					deliverySubscriptionTypeSettingsUnicodeProperties,
				long deliveryMaxSubscriptionCycles,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "upsertCPDefinition",
				_upsertCPDefinitionParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, nameMap, shortDescriptionMap,
				descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap,
				metaKeywordsMap, productTypeName, ignoreSKUCombinations,
				shippable, freeShipping, shipSeparately, shippingExtraPrice,
				width, height, depth, weight, cpTaxCategoryId, taxExempt,
				telcoOrElectronics, ddmStructureKey, published,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, defaultSku,
				subscriptionEnabled, subscriptionLength, subscriptionType,
				subscriptionTypeSettingsUnicodeProperties,
				maxSubscriptionCycles, deliverySubscriptionEnabled,
				deliverySubscriptionLength, deliverySubscriptionType,
				deliverySubscriptionTypeSettingsUnicodeProperties,
				deliveryMaxSubscriptionCycles, externalReferenceCode,
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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition
			upsertCPDefinition(
				HttpPrincipal httpPrincipal, long groupId, long userId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> shortDescriptionMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				java.util.Map<java.util.Locale, String> urlTitleMap,
				java.util.Map<java.util.Locale, String> metaTitleMap,
				java.util.Map<java.util.Locale, String> metaDescriptionMap,
				java.util.Map<java.util.Locale, String> metaKeywordsMap,
				String productTypeName, boolean ignoreSKUCombinations,
				boolean shippable, boolean freeShipping, boolean shipSeparately,
				double shippingExtraPrice, double width, double height,
				double depth, double weight, long cpTaxCategoryId,
				boolean taxExempt, boolean telcoOrElectronics,
				String ddmStructureKey, boolean published, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String defaultSku,
				boolean subscriptionEnabled, int subscriptionLength,
				String subscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					subscriptionTypeSettingsUnicodeProperties,
				long maxSubscriptionCycles, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionServiceUtil.class, "upsertCPDefinition",
				_upsertCPDefinitionParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId, nameMap, shortDescriptionMap,
				descriptionMap, urlTitleMap, metaTitleMap, metaDescriptionMap,
				metaKeywordsMap, productTypeName, ignoreSKUCombinations,
				shippable, freeShipping, shipSeparately, shippingExtraPrice,
				width, height, depth, weight, cpTaxCategoryId, taxExempt,
				telcoOrElectronics, ddmStructureKey, published,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, defaultSku,
				subscriptionEnabled, subscriptionLength, subscriptionType,
				subscriptionTypeSettingsUnicodeProperties,
				maxSubscriptionCycles, externalReferenceCode, serviceContext);

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

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionServiceHttp.class);

	private static final Class<?>[] _addCPDefinitionParameterTypes0 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, String.class,
			boolean.class, boolean.class, boolean.class, boolean.class,
			double.class, double.class, double.class, double.class,
			double.class, long.class, boolean.class, boolean.class,
			String.class, boolean.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class, String.class, boolean.class, int.class,
			String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class, long.class,
			boolean.class, int.class, String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCPDefinitionParameterTypes1 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, String.class,
			boolean.class, boolean.class, boolean.class, boolean.class,
			double.class, double.class, double.class, double.class,
			double.class, long.class, boolean.class, boolean.class,
			String.class, boolean.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class, String.class, boolean.class, int.class,
			String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _copyCPDefinitionParameterTypes2 =
		new Class[] {long.class, long.class};
	private static final Class<?>[]
		_deleteAssetCategoryCPDefinitionParameterTypes3 = new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPDefinitionParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchCPDefinitionParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchCPDefinitionByCProductExternalReferenceCodeParameterTypes6 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_fetchCPDefinitionByCProductIdParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPDefinitionParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _getCPDefinitionsParameterTypes9 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPDefinitionsCountParameterTypes10 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getLayoutUuidParameterTypes11 =
		new Class[] {long.class};
	private static final Class<?>[] _getUrlTitleMapAsXMLParameterTypes12 =
		new Class[] {long.class};
	private static final Class<?>[] _searchCPDefinitionsParameterTypes13 =
		new Class[] {
			long.class, String.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _searchCPDefinitionsParameterTypes14 =
		new Class[] {
			long.class, String.class, String.class, String.class, int.class,
			int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCPDefinitionParameterTypes15 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, boolean.class,
			String.class, boolean.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCPDefinitionAccountGroupFilterParameterTypes16 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[]
		_updateCPDefinitionCategorizationParameterTypes17 = new Class[] {
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCPDefinitionChannelFilterParameterTypes18 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateCPDisplayLayoutParameterTypes19 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateShippingInfoParameterTypes20 =
		new Class[] {
			long.class, boolean.class, boolean.class, boolean.class,
			double.class, double.class, double.class, double.class,
			double.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateStatusParameterTypes21 =
		new Class[] {
			long.class, long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class,
			java.util.Map.class
		};
	private static final Class<?>[] _updateSubscriptionInfoParameterTypes22 =
		new Class[] {
			long.class, boolean.class, int.class, String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class, long.class,
			boolean.class, int.class, String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class, long.class
		};
	private static final Class<?>[] _updateSubscriptionInfoParameterTypes23 =
		new Class[] {
			long.class, boolean.class, int.class, String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateTaxCategoryInfoParameterTypes24 =
		new Class[] {long.class, long.class, boolean.class, boolean.class};
	private static final Class<?>[] _upsertCPDefinitionParameterTypes25 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, String.class,
			boolean.class, boolean.class, boolean.class, boolean.class,
			double.class, double.class, double.class, double.class,
			double.class, long.class, boolean.class, boolean.class,
			String.class, boolean.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class, String.class, boolean.class, int.class,
			String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class, long.class,
			boolean.class, int.class, String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _upsertCPDefinitionParameterTypes26 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, String.class,
			boolean.class, boolean.class, boolean.class, boolean.class,
			double.class, double.class, double.class, double.class,
			double.class, long.class, boolean.class, boolean.class,
			String.class, boolean.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class, String.class, boolean.class, int.class,
			String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};

}
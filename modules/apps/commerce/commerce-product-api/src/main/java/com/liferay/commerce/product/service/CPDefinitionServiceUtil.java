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

package com.liferay.commerce.product.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPDefinition. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CPDefinitionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionService
 * @generated
 */
public class CPDefinitionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPDefinitionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPDefinition
			addCPDefinition(
				long groupId, long userId,
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

		return getService().addCPDefinition(
			groupId, userId, nameMap, shortDescriptionMap, descriptionMap,
			urlTitleMap, metaTitleMap, metaDescriptionMap, metaKeywordsMap,
			productTypeName, ignoreSKUCombinations, shippable, freeShipping,
			shipSeparately, shippingExtraPrice, width, height, depth, weight,
			cpTaxCategoryId, taxExempt, telcoOrElectronics, ddmStructureKey,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, defaultSku, subscriptionEnabled,
			subscriptionLength, subscriptionType,
			subscriptionTypeSettingsUnicodeProperties, maxSubscriptionCycles,
			deliverySubscriptionEnabled, deliverySubscriptionLength,
			deliverySubscriptionType,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliveryMaxSubscriptionCycles, externalReferenceCode,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			addCPDefinition(
				long groupId, long userId,
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

		return getService().addCPDefinition(
			groupId, userId, nameMap, shortDescriptionMap, descriptionMap,
			urlTitleMap, metaTitleMap, metaDescriptionMap, metaKeywordsMap,
			productTypeName, ignoreSKUCombinations, shippable, freeShipping,
			shipSeparately, shippingExtraPrice, width, height, depth, weight,
			cpTaxCategoryId, taxExempt, telcoOrElectronics, ddmStructureKey,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, defaultSku, subscriptionEnabled,
			subscriptionLength, subscriptionType,
			subscriptionTypeSettingsUnicodeProperties, maxSubscriptionCycles,
			externalReferenceCode, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			copyCPDefinition(long cpDefinitionId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().copyCPDefinition(cpDefinitionId, groupId);
	}

	public static void deleteAssetCategoryCPDefinition(
			long cpDefinitionId, long categoryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteAssetCategoryCPDefinition(
			cpDefinitionId, categoryId, serviceContext);
	}

	public static void deleteCPDefinition(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCPDefinition(cpDefinitionId);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			fetchCPDefinition(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCPDefinition(cpDefinitionId);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			fetchCPDefinitionByCProductExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCPDefinitionByCProductExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			fetchCPDefinitionByCProductId(long cProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCPDefinitionByCProductId(cProductId);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			getCPDefinition(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPDefinition(cpDefinitionId);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPDefinition> getCPDefinitions(
				long groupId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CPDefinition>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPDefinitions(
			groupId, status, start, end, orderByComparator);
	}

	public static int getCPDefinitionsCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCPDefinitionsCount(groupId, status);
	}

	public static String getLayoutUuid(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutUuid(cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static String getUrlTitleMapAsXML(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUrlTitleMapAsXML(cpDefinitionId);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPDefinition> searchCPDefinitions(
				long companyId, String keywords, int status, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCPDefinitions(
			companyId, keywords, status, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.product.model.CPDefinition> searchCPDefinitions(
				long companyId, String keywords, String filterFields,
				String filterValues, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCPDefinitions(
			companyId, keywords, filterFields, filterValues, start, end, sort);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateCPDefinition(
				long cpDefinitionId,
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

		return getService().updateCPDefinition(
			cpDefinitionId, nameMap, shortDescriptionMap, descriptionMap,
			urlTitleMap, metaTitleMap, metaDescriptionMap, metaKeywordsMap,
			ignoreSKUCombinations, ddmStructureKey, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateCPDefinitionAccountGroupFilter(
				long cpDefinitionId, boolean enable)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPDefinitionAccountGroupFilter(
			cpDefinitionId, enable);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateCPDefinitionCategorization(
				long cpDefinitionId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPDefinitionCategorization(
			cpDefinitionId, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateCPDefinitionChannelFilter(long cpDefinitionId, boolean enable)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCPDefinitionChannelFilter(
			cpDefinitionId, enable);
	}

	public static void updateCPDisplayLayout(
			long cpDefinitionId, String layoutUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateCPDisplayLayout(
			cpDefinitionId, layoutUuid, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateShippingInfo(
				long cpDefinitionId, boolean shippable, boolean freeShipping,
				boolean shipSeparately, double shippingExtraPrice, double width,
				double height, double depth, double weight,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateShippingInfo(
			cpDefinitionId, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition updateStatus(
			long userId, long cpDefinitionId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, cpDefinitionId, status, serviceContext, workflowContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateSubscriptionInfo(
				long cpDefinitionId, boolean subscriptionEnabled,
				int subscriptionLength, String subscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					subscriptionTypeSettingsUnicodeProperties,
				long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
				int deliverySubscriptionLength, String deliverySubscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					deliverySubscriptionTypeSettingsUnicodeProperties,
				long deliveryMaxSubscriptionCycles)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSubscriptionInfo(
			cpDefinitionId, subscriptionEnabled, subscriptionLength,
			subscriptionType, subscriptionTypeSettingsUnicodeProperties,
			maxSubscriptionCycles, deliverySubscriptionEnabled,
			deliverySubscriptionLength, deliverySubscriptionType,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliveryMaxSubscriptionCycles);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.product.model.CPDefinition
			updateSubscriptionInfo(
				long cpDefinitionId, boolean subscriptionEnabled,
				int subscriptionLength, String subscriptionType,
				com.liferay.portal.kernel.util.UnicodeProperties
					subscriptionTypeSettingsUnicodeProperties,
				long maxSubscriptionCycles,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSubscriptionInfo(
			cpDefinitionId, subscriptionEnabled, subscriptionLength,
			subscriptionType, subscriptionTypeSettingsUnicodeProperties,
			maxSubscriptionCycles, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			updateTaxCategoryInfo(
				long cpDefinitionId, long cpTaxCategoryId, boolean taxExempt,
				boolean telcoOrElectronics)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateTaxCategoryInfo(
			cpDefinitionId, cpTaxCategoryId, taxExempt, telcoOrElectronics);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			upsertCPDefinition(
				long groupId, long userId,
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

		return getService().upsertCPDefinition(
			groupId, userId, nameMap, shortDescriptionMap, descriptionMap,
			urlTitleMap, metaTitleMap, metaDescriptionMap, metaKeywordsMap,
			productTypeName, ignoreSKUCombinations, shippable, freeShipping,
			shipSeparately, shippingExtraPrice, width, height, depth, weight,
			cpTaxCategoryId, taxExempt, telcoOrElectronics, ddmStructureKey,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, defaultSku, subscriptionEnabled,
			subscriptionLength, subscriptionType,
			subscriptionTypeSettingsUnicodeProperties, maxSubscriptionCycles,
			deliverySubscriptionEnabled, deliverySubscriptionLength,
			deliverySubscriptionType,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliveryMaxSubscriptionCycles, externalReferenceCode,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinition
			upsertCPDefinition(
				long groupId, long userId,
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

		return getService().upsertCPDefinition(
			groupId, userId, nameMap, shortDescriptionMap, descriptionMap,
			urlTitleMap, metaTitleMap, metaDescriptionMap, metaKeywordsMap,
			productTypeName, ignoreSKUCombinations, shippable, freeShipping,
			shipSeparately, shippingExtraPrice, width, height, depth, weight,
			cpTaxCategoryId, taxExempt, telcoOrElectronics, ddmStructureKey,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, defaultSku, subscriptionEnabled,
			subscriptionLength, subscriptionType,
			subscriptionTypeSettingsUnicodeProperties, maxSubscriptionCycles,
			externalReferenceCode, serviceContext);
	}

	public static CPDefinitionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionService, CPDefinitionService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionService.class);

		ServiceTracker<CPDefinitionService, CPDefinitionService>
			serviceTracker =
				new ServiceTracker<CPDefinitionService, CPDefinitionService>(
					bundle.getBundleContext(), CPDefinitionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
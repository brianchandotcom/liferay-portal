/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuOption;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuSubscriptionConfiguration;
import com.liferay.headless.commerce.admin.catalog.internal.util.DateConfigUtil;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 */
public class SkuUtil {

	public static CPInstance addOrUpdateCPInstance(
			CPInstanceService cpInstanceService, Sku sku,
			CPDefinition cpDefinition,
			CPDefinitionOptionRelService cpDefinitionOptionRelService,
			CPDefinitionOptionValueRelService cpDefinitionOptionValueRelService,
			ServiceContext serviceContext)
		throws PortalException {

		long replacementCProductId = 0;
		String replacementCPInstanceUuid = null;
		int discontinuedDateMonth = 0;
		int discontinuedDateDay = 0;
		int discontinuedDateYear = 0;

		if (GetterUtil.getBoolean(sku.getDiscontinued())) {
			CPInstance discontinuedCPInstance = null;

			if (Validator.isNotNull(
					sku.getReplacementSkuExternalReferenceCode())) {

				discontinuedCPInstance =
					cpInstanceService.fetchByExternalReferenceCode(
						sku.getReplacementSkuExternalReferenceCode(),
						cpDefinition.getCompanyId());
			}

			long replacementSkuId = GetterUtil.getLong(
				sku.getReplacementSkuId());

			if ((discontinuedCPInstance == null) && (replacementSkuId > 0)) {
				discontinuedCPInstance = cpInstanceService.fetchCPInstance(
					replacementSkuId);
			}

			if (discontinuedCPInstance != null) {
				CPDefinition discontinuedCPDefinition =
					discontinuedCPInstance.getCPDefinition();

				replacementCProductId =
					discontinuedCPDefinition.getCProductId();

				replacementCPInstanceUuid =
					discontinuedCPInstance.getCPInstanceUuid();
			}

			if (sku.getDiscontinuedDate() != null) {
				Date discontinuedDate = GetterUtil.getDate(
					sku.getDiscontinuedDate(),
					DateFormatFactoryUtil.getSimpleDateFormat("MM/dd/yyyy"),
					null);

				Calendar discontinuedCalendar = CalendarFactoryUtil.getCalendar(
					discontinuedDate.getTime());

				discontinuedDateDay = discontinuedCalendar.get(
					Calendar.DAY_OF_MONTH);
				discontinuedDateMonth = discontinuedCalendar.get(
					Calendar.MONTH);
				discontinuedDateYear = discontinuedCalendar.get(Calendar.YEAR);
			}
		}

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		if (sku.getDisplayDate() != null) {
			displayCalendar = DateConfigUtil.convertDateToCalendar(
				sku.getDisplayDate());
		}

		DateConfig displayDateConfig = new DateConfig(displayCalendar);

		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			sku.getExpirationDate(), serviceContext.getTimeZone());

		SkuSubscriptionConfiguration skuSubscriptionConfiguration =
			sku.getSkuSubscriptionConfiguration();

		boolean deliverySubscriptionEnable = false;
		int deliverySubscriptionLength = 1;
		long deliverySubscriptionMaxSubscriptionCycles = 0;
		UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties =
			null;
		String deliverySubscriptionTypeValue = StringPool.BLANK;
		boolean overrideSubscriptionInfo = false;
		boolean subscriptionEnable = false;
		int subscriptionLength = 1;
		long subscriptionMaxSubscriptionCycles = 0;
		UnicodeProperties subscriptionTypeSettingsUnicodeProperties = null;
		String subscriptionTypeValue = StringPool.BLANK;

		if (skuSubscriptionConfiguration != null) {
			deliverySubscriptionEnable = GetterUtil.getBoolean(
				skuSubscriptionConfiguration.getDeliverySubscriptionEnable(),
				deliverySubscriptionEnable);
			deliverySubscriptionLength = GetterUtil.getInteger(
				skuSubscriptionConfiguration.getDeliverySubscriptionLength(),
				deliverySubscriptionLength);
			deliverySubscriptionMaxSubscriptionCycles = GetterUtil.getLong(
				skuSubscriptionConfiguration.
					getDeliverySubscriptionNumberOfLength(),
				deliverySubscriptionMaxSubscriptionCycles);

			if (Validator.isNotNull(
					skuSubscriptionConfiguration.
						getDeliverySubscriptionTypeSettings())) {

				deliverySubscriptionTypeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.create(
						skuSubscriptionConfiguration.
							getDeliverySubscriptionTypeSettings(),
						true
					).build();
			}

			SkuSubscriptionConfiguration.DeliverySubscriptionType
				deliverySubscriptionType =
					skuSubscriptionConfiguration.getDeliverySubscriptionType();

			if (deliverySubscriptionType != null) {
				deliverySubscriptionTypeValue =
					deliverySubscriptionType.getValue();
			}

			overrideSubscriptionInfo = GetterUtil.getBoolean(
				skuSubscriptionConfiguration.getOverrideSubscriptionInfo(),
				overrideSubscriptionInfo);
			subscriptionEnable = GetterUtil.getBoolean(
				skuSubscriptionConfiguration.getEnable(), subscriptionEnable);
			subscriptionLength = GetterUtil.getInteger(
				skuSubscriptionConfiguration.getLength(), subscriptionLength);
			subscriptionMaxSubscriptionCycles = GetterUtil.getLong(
				skuSubscriptionConfiguration.getNumberOfLength(),
				subscriptionMaxSubscriptionCycles);

			if (Validator.isNotNull(
					skuSubscriptionConfiguration.
						getSubscriptionTypeSettings())) {

				subscriptionTypeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.create(
						skuSubscriptionConfiguration.
							getSubscriptionTypeSettings(),
						true
					).build();
			}

			SkuSubscriptionConfiguration.SubscriptionType subscriptionType =
				skuSubscriptionConfiguration.getSubscriptionType();

			if (subscriptionType != null) {
				subscriptionTypeValue = subscriptionType.getValue();
			}
		}

		return cpInstanceService.addOrUpdateCPInstance(
			sku.getExternalReferenceCode(), cpDefinition.getCPDefinitionId(),
			cpDefinition.getGroupId(), sku.getSku(), sku.getGtin(),
			sku.getManufacturerPartNumber(),
			GetterUtil.get(sku.getPurchasable(), false),
			_getOptions(
				cpDefinitionOptionRelService, cpDefinitionOptionValueRelService,
				sku),
			GetterUtil.get(sku.getWidth(), 0.0),
			GetterUtil.get(sku.getHeight(), 0.0),
			GetterUtil.get(sku.getDepth(), 0.0),
			GetterUtil.get(sku.getWeight(), 0.0),
			(BigDecimal)GetterUtil.get(sku.getPrice(), BigDecimal.ZERO),
			(BigDecimal)GetterUtil.get(sku.getPromoPrice(), BigDecimal.ZERO),
			(BigDecimal)GetterUtil.get(sku.getCost(), BigDecimal.ZERO),
			GetterUtil.get(sku.getPublished(), false),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.get(sku.getNeverExpire(), false),
			overrideSubscriptionInfo, subscriptionEnable, subscriptionLength,
			subscriptionTypeValue, subscriptionTypeSettingsUnicodeProperties,
			subscriptionMaxSubscriptionCycles, deliverySubscriptionEnable,
			deliverySubscriptionLength, deliverySubscriptionTypeValue,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliverySubscriptionMaxSubscriptionCycles, sku.getUnspsc(),
			GetterUtil.get(sku.getDiscontinued(), false),
			replacementCPInstanceUuid, replacementCProductId,
			discontinuedDateMonth, discontinuedDateDay, discontinuedDateYear,
			serviceContext);
	}

	public static void updateCommercePriceEntries(
			CommercePriceEntryLocalService commercePriceEntryLocalService,
			CommercePriceListLocalService commercePriceListLocalService,
			ConfigurationProvider configurationProvider, CPInstance cpInstance,
			BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws Exception {

		if (Objects.equals(
				_getCommercePricingConfigurationKey(configurationProvider),
				CommercePricingConstants.VERSION_2_0)) {

			_updateCommercePriceEntry(
				commercePriceEntryLocalService, commercePriceListLocalService,
				cpInstance, CommercePriceListConstants.TYPE_PRICE_LIST, price,
				serviceContext);
			_updateCommercePriceEntry(
				commercePriceEntryLocalService, commercePriceListLocalService,
				cpInstance, CommercePriceListConstants.TYPE_PROMOTION,
				promoPrice, serviceContext);
		}
	}

	private static String _getCommercePricingConfigurationKey(
			ConfigurationProvider configurationProvider)
		throws Exception {

		CommercePricingConfiguration commercePricingConfiguration =
			configurationProvider.getConfiguration(
				CommercePricingConfiguration.class,
				new SystemSettingsLocator(
					CommercePricingConstants.SERVICE_NAME));

		return commercePricingConfiguration.commercePricingCalculationKey();
	}

	private static String _getCPDefinitionOptionRelKey(
			long optionId,
			CPDefinitionOptionRelService cpDefinitionOptionRelService)
		throws Exception {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelService.fetchCPDefinitionOptionRel(optionId);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel.getKey();
		}

		return null;
	}

	private static String _getCPDefinitionOptionValueRelKey(
			long optionValueId,
			CPDefinitionOptionValueRelService cpDefinitionOptionValueRelService)
		throws Exception {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelService.fetchCPDefinitionOptionValueRel(
				optionValueId);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel.getKey();
		}

		return null;
	}

	private static String _getOptions(
		CPDefinitionOptionRelService cpDefinitionOptionRelService,
		CPDefinitionOptionValueRelService cpDefinitionOptionValueRelService,
		Sku sku) {

		SkuOption[] skuOptions = sku.getSkuOptions();

		if (skuOptions == null) {
			return StringPool.BLANK;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SkuOption skuOption : skuOptions) {
			jsonArray.put(
				JSONUtil.put(
					"key",
					() -> {
						if (Validator.isNull(skuOption.getKey())) {
							return _getCPDefinitionOptionRelKey(
								GetterUtil.getLong(skuOption.getOptionId()),
								cpDefinitionOptionRelService);
						}

						try {
							String cpDefinitionOptionRelKey =
								_getCPDefinitionOptionRelKey(
									GetterUtil.getLongStrict(
										skuOption.getKey()),
									cpDefinitionOptionRelService);

							if (Validator.isNotNull(cpDefinitionOptionRelKey)) {
								return cpDefinitionOptionRelKey;
							}
						}
						catch (NumberFormatException numberFormatException) {
							if (_log.isDebugEnabled()) {
								_log.debug(numberFormatException);
							}
						}

						return skuOption.getKey();
					}
				).put(
					"value",
					JSONUtil.put(
						() -> {
							if (Validator.isNull(skuOption.getValue())) {
								return _getCPDefinitionOptionValueRelKey(
									GetterUtil.getLong(
										skuOption.getOptionValueId()),
									cpDefinitionOptionValueRelService);
							}

							try {
								String cpDefinitionOptionValueRelKey =
									_getCPDefinitionOptionValueRelKey(
										GetterUtil.getLongStrict(
											skuOption.getValue()),
										cpDefinitionOptionValueRelService);

								if (Validator.isNotNull(
										cpDefinitionOptionValueRelKey)) {

									return cpDefinitionOptionValueRelKey;
								}
							}
							catch (NumberFormatException
										numberFormatException) {

								if (_log.isDebugEnabled()) {
									_log.debug(numberFormatException);
								}
							}

							return skuOption.getValue();
						})
				));
		}

		return jsonArray.toString();
	}

	private static void _updateCommercePriceEntry(
			CommercePriceEntryLocalService commercePriceEntryLocalService,
			CommercePriceListLocalService commercePriceListLocalService,
			CPInstance cpInstance, String type, BigDecimal price,
			ServiceContext serviceContext)
		throws Exception {

		CommercePriceList commercePriceList =
			commercePriceListLocalService.getCatalogBaseCommercePriceListByType(
				cpInstance.getGroupId(), type);

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid(), StringPool.BLANK);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		if (commercePriceEntry == null) {
			CPDefinition cpDefinition = cpInstance.getCPDefinition();

			commercePriceEntryLocalService.addCommercePriceEntry(
				null, cpDefinition.getCProductId(),
				cpInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), price, false, null,
				null, serviceContext);
		}
		else {
			commercePriceEntryLocalService.updatePricingInfo(
				commercePriceEntry.getCommercePriceEntryId(),
				commercePriceEntry.isBulkPricing(), price,
				commercePriceEntry.isPriceOnApplication(), null, null,
				serviceContext);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(SkuUtil.class);

}
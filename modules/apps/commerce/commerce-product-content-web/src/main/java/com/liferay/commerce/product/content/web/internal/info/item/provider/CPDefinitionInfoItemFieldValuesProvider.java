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

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.content.web.internal.info.CPDefinitionInfoItemFields;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Marco Leo
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	service = InfoItemFieldValuesProvider.class
)
public class CPDefinitionInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<CPDefinition> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		CPDefinition cpDefinition) {

		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getCPDefinitionInfoFieldValues(cpDefinition)
		).infoFieldValues(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				CPDefinition.class.getName(), cpDefinition)
		).infoItemReference(
			new InfoItemReference(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId())
		).build();
	}

	private String _getAvailabilityStatus(
			CPInstance cpInstance, ThemeDisplay themeDisplay)
		throws PortalException {

		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				themeDisplay.getScopeGroupId());

		if (commerceChannel == null) {
			return StringPool.BLANK;
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		boolean displayAvailability =
			cpDefinitionInventoryEngine.isDisplayAvailability(cpInstance);

		if (displayAvailability) {
			return _commerceInventoryEngine.getAvailabilityStatus(
				cpInstance.getCompanyId(), commerceChannel.getGroupId(),
				cpDefinitionInventoryEngine.getMinStockQuantity(cpInstance),
				cpInstance.getSku());
		}

		return StringPool.BLANK;
	}

	private List<InfoFieldValue<Object>> _getCPDefinitionInfoFieldValues(
		CPDefinition cpDefinition) {

		List<InfoFieldValue<Object>> cpDefinitionInfoFieldValues =
			new ArrayList<>();

		try {
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.
						accountGroupFilterEnabledInfoField,
					cpDefinition.isAccountGroupFilterEnabled()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.approvedInfoField,
					cpDefinition.isApproved()));

			CPInstance cpInstance = null;

			if (cpDefinition.isIgnoreSKUCombinations() &&
				!_cpContentHelper.hasChildCPDefinitions(
					cpDefinition.getCPDefinitionId())) {

				cpInstance = _cpInstanceHelper.getDefaultCPInstance(
					cpDefinition.getCPDefinitionId());
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			if (themeDisplay != null) {
				cpDefinitionInfoFieldValues.add(
					new InfoFieldValue<>(
						CPDefinitionInfoItemFields.availabilityStatusInfoField,
						_getAvailabilityStatus(cpInstance, themeDisplay)));
			}

			List<AssetCategory> assetCategories =
				_assetCategoryLocalService.getCategories(
					CPDefinition.class.getName(),
					cpDefinition.getCPDefinitionId());

			Stream<AssetCategory> stream = assetCategories.stream();

			Stream<Map<Locale, String>> assetCategoriesTitleMapStream =
				stream.map(AssetCategory::getTitleMap);

			Optional<Map<Locale, String>> assetCategoriesTitleMapOptional =
				assetCategoriesTitleMapStream.findAny();

			assetCategoriesTitleMapOptional.ifPresent(
				assetCategoriesTitleMap -> cpDefinitionInfoFieldValues.add(
					new InfoFieldValue<>(
						CPDefinitionInfoItemFields.categoriesInfoField,
						InfoLocalizedValue.<String>builder(
						).defaultLocale(
							LocaleUtil.fromLanguageId(
								cpDefinition.getDefaultLanguageId())
						).values(
							assetCategoriesTitleMap
						).build())));

			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.channelFilterEnabledInfoField,
					cpDefinition.isChannelFilterEnabled()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.companyIdInfoField,
					cpDefinition.getCompanyId()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.cpDefinitionIdInfoField,
					cpDefinition.getCPDefinitionId()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.cProductIdInfoField,
					cpDefinition.getCProductId()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.cpTaxCategoryIdInfoField,
					cpDefinition.getCPTaxCategoryId()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.createDateInfoField,
					cpDefinition.getCreateDate()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.ddmStructureKeyInfoField,
					cpDefinition.getDDMStructureKey()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.defaultLanguageIdInfoField,
					cpDefinition.getDefaultLanguageId()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.descriptionInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinition.getDefaultLanguageId())
					).values(
						cpDefinition.getDescriptionMap()
					).build()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.
						deliveryMaxSubscriptionCyclesInfoField,
					cpDefinition.getDeliveryMaxSubscriptionCycles()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.
						deliverySubscriptionEnabledInfoField,
					cpDefinition.isDeliverySubscriptionEnabled()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.
						deliverySubscriptionLengthInfoField,
					cpDefinition.getDeliverySubscriptionLength()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.
						deliverySubscriptionTypeInfoField,
					cpDefinition.getDeliverySubscriptionType()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.
						deliverySubscriptionTypeSettingsInfoField,
					cpDefinition.getDeliverySubscriptionTypeSettings()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.deniedInfoField,
					cpDefinition.isDenied()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.depthInfoField,
					cpDefinition.getDepth()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.displayDateInfoField,
					cpDefinition.getDisplayDate()));

			if (themeDisplay != null) {
				cpDefinitionInfoFieldValues.add(
					new InfoFieldValue<>(
						CPDefinitionInfoItemFields.displayPageUrlInfoField,
						_cpDefinitionHelper.getFriendlyURL(
							cpDefinition.getCPDefinitionId(), themeDisplay)));
			}

			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.draftInfoField,
					cpDefinition.isDraft()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.expirationDateInfoField,
					cpDefinition.getExpirationDate()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.expiredInfoField,
					cpDefinition.isExpired()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.freeShippingInfoField,
					cpDefinition.isFreeShipping()));

			if (themeDisplay != null) {
				cpDefinitionInfoFieldValues.add(
					new InfoFieldValue<>(
						CPDefinitionInfoItemFields.finalPriceInfoField,
						_getFinalPrice(cpInstance, themeDisplay)));
			}

			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.groupIdInfoField,
					cpDefinition.getGroupId()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.heightInfoField,
					cpDefinition.getHeight()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.ignoreSKUCombinationsInfoField,
					cpDefinition.isIgnoreSKUCombinations()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.inactiveInfoField,
					cpDefinition.isInactive()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.incompleteInfoField,
					cpDefinition.isIncomplete()));

			if (themeDisplay != null) {
				cpDefinitionInfoFieldValues.add(
					new InfoFieldValue<>(
						CPDefinitionInfoItemFields.inventoryInfoField,
						_getInventory(cpInstance, themeDisplay)));
			}

			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.lastPublishDateInfoField,
					cpDefinition.getLastPublishDate()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.maxSubscriptionCyclesInfoField,
					cpDefinition.getMaxSubscriptionCycles()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.metaDescriptionInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinition.getDefaultLanguageId())
					).values(
						cpDefinition.getMetaDescriptionMap()
					).build()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.metaKeywordsInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinition.getDefaultLanguageId())
					).values(
						cpDefinition.getMetaKeywordsMap()
					).build()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.metaTitleInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinition.getDefaultLanguageId())
					).values(
						cpDefinition.getMetaTitleMap()
					).build()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.modifiedDateInfoField,
					cpDefinition.getModifiedDate()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.nameInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinition.getDefaultLanguageId())
					).values(
						cpDefinition.getNameMap()
					).build()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.pendingInfoField,
					cpDefinition.isPending()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.publishedInfoField,
					cpDefinition.isPublished()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.productTypeNameInfoField,
					cpDefinition.getProductTypeName()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.shortDescriptionInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							cpDefinition.getDefaultLanguageId())
					).values(
						cpDefinition.getShortDescriptionMap()
					).build()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.scheduledInfoField,
					cpDefinition.isScheduled()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.shippableInfoField,
					cpDefinition.isShippable()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.shippingExtraPriceInfoField,
					cpDefinition.getShippingExtraPrice()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.shipSeparatelyPriceInfoField,
					cpDefinition.isShipSeparately()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.skuInfoField,
					_getSKU(cpInstance)));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.stagedModelTypeInfoField,
					cpDefinition.getStagedModelType()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.statusInfoField,
					cpDefinition.getStatus()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.statusByUserIdInfoField,
					cpDefinition.getStatusByUserId()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.statusByUserNameInfoField,
					cpDefinition.getStatusByUserName()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.statusByUserUuidInfoField,
					cpDefinition.getStatusByUserUuid()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.statusDateInfoField,
					cpDefinition.getStatusDate()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.subscriptionEnabledInfoField,
					cpDefinition.isSubscriptionEnabled()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.subscriptionLengthInfoField,
					cpDefinition.getSubscriptionLength()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.subscriptionTypeInfoField,
					cpDefinition.getSubscriptionType()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.
						subscriptionTypeSettingsInfoField,
					cpDefinition.getSubscriptionTypeSettings()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.taxExemptInfoField,
					cpDefinition.isTaxExempt()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.telcoOrElectronicsInfoField,
					cpDefinition.isTelcoOrElectronics()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.userIdInfoField,
					cpDefinition.getUserId()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.userUuidInfoField,
					cpDefinition.getUserUuid()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.userNameInfoField,
					cpDefinition.getUserName()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.uuidInfoField,
					cpDefinition.getUuid()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.versionInfoField,
					cpDefinition.getVersion()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.weightInfoField,
					cpDefinition.getWeight()));
			cpDefinitionInfoFieldValues.add(
				new InfoFieldValue<>(
					CPDefinitionInfoItemFields.widthInfoField,
					cpDefinition.getWidth()));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		return cpDefinitionInfoFieldValues;
	}

	private String _getFinalPrice(
			CPInstance cpInstance, ThemeDisplay themeDisplay)
		throws PortalException {

		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		CommerceContext commerceContext = _commerceContextFactory.create(
			themeDisplay.getCompanyId(),
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				themeDisplay.getScopeGroupId()),
			themeDisplay.getUserId(), 0, 0);

		CommerceMoney commerceMoney =
			_commerceProductPriceCalculation.getFinalPrice(
				cpInstance.getCPInstanceId(), 1, commerceContext);

		if (commerceMoney.isEmpty()) {
			return StringPool.BLANK;
		}

		return commerceMoney.format(themeDisplay.getLocale());
	}

	private Integer _getInventory(
			CPInstance cpInstance, ThemeDisplay themeDisplay)
		throws PortalException {

		if (cpInstance == null) {
			return null;
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		boolean displayStockQuantity =
			cpDefinitionInventoryEngine.isDisplayStockQuantity(cpInstance);

		if (displayStockQuantity) {
			long commerceChannelGroupId =
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(
						themeDisplay.getScopeGroupId());

			return _commerceInventoryEngine.getStockQuantity(
				cpInstance.getCompanyId(), commerceChannelGroupId,
				cpInstance.getSku());
		}

		return null;
	}

	private String _getSKU(CPInstance cpInstance) throws PortalException {
		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		return cpInstance.getSku();
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private CPContentHelper _cpContentHelper;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private Portal _portal;

}